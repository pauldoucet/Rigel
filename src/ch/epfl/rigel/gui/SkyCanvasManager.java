package ch.epfl.rigel.gui;

import java.util.Optional;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

/**
 * @author Paul Doucet (316442)
 *
 *	create mouse and keyboard bindings and draw sky when it should 
 */
public class SkyCanvasManager {
	
	private Canvas canvas;
	private SkyCanvasPainter painter;
	private ObservableObjectValue<StereographicProjection> projection;
	private ObjectProperty<CartesianCoordinates> mousePosition;
	private ObservableObjectValue<ObservedSky> observedSky;
	private ObservableObjectValue<Transform> planeToCanvas;
	private ObservableObjectValue<HorizontalCoordinates> mouseHorizontalPosition;
	private ObservableStringValue objectUnderMouse;
	private ObservableDoubleValue mouseAzDeg;
	private ObservableDoubleValue mouseAltDeg;
	private CartesianCoordinates anchor;
	private HorizontalCoordinates centerDrag;
	boolean dragEntered;
	
	private static CartesianCoordinates BEGINING_MOUSE_POSITION = CartesianCoordinates.of(10, 10);
	private final static int MAX_FOV = 150; // max fov in deg
	private final static int MIN_FOV = 30; // min fov in deg
	private static final ClosedInterval ALT_INTERVAL = ClosedInterval.of(Angle.ofDeg(5), Angle.ofDeg(90)); // the center of projection altitude stay in this interval
	private final static int AZ_DEG_STEP = 10; // center of projection azimut step when moving with the arrow keys (LEFT RIGHT)
	private final static int ALT_DEG_STEP = 5; // center of projection altitude step when moving with the arrow keys (UP DOWN)
	private final static int OBJECT_UNDER_MOUSE_RANGE = 10; // range within we can detect celestial object under mouse
	private final static int DRAG_SENSITIVITY = 3; // mouse drag sensitivity (higher DRAG_SENSITIVITY means higher sensitivity)
	
	/**
	 * @param catalogue : star catalogue containing asterisms and stars to draw
	 * @param timeBean : time bean
	 * @param locBean : location bean
	 * @param viewBean : viewing parameters bean
	 */
	public SkyCanvasManager(StarCatalogue catalogue,
			DateTimeBean timeBean, ObserverLocationBean locBean, ViewingParametersBean viewBean){
		
		initCanvas(viewBean);
		
		initPlaneToCanvas(viewBean);
		
		initProjection(viewBean);
		
		initObservedSky(locBean, timeBean, catalogue);
	
		setMouseBindings(viewBean);
		
		setMouseDragBindings(viewBean);
		
		// tell bindings when they should draw the sky
		initDrawingBindings();
		
	}

	private void initPlaneToCanvas(ViewingParametersBean viewBean) {
		double defaultDilatationFactor = (double)canvas.getWidth() / (2d * Math.tan(Angle.ofDeg(viewBean.getFieldOfViewDeg()) / 4d));
		planeToCanvas = new SimpleObjectProperty<Transform>(Transform.affine(defaultDilatationFactor, 0, 0, - defaultDilatationFactor, canvas.getWidth() / 2, canvas.getHeight() / 2));
		planeToCanvas = Bindings.createObjectBinding(() -> {
			double dilatationFactor = (double)canvas.getWidth() / (2d * Math.tan(Angle.ofDeg(viewBean.getFieldOfViewDeg()) / 4d));
			return Transform.affine(dilatationFactor, 0, 0, -dilatationFactor, canvas.getWidth() / 2, canvas.getHeight() / 2);
		}, canvas.widthProperty(), viewBean.fieldOfViewDegProperty(), canvas.heightProperty());		
	}
	
	private void initObservedSky(ObserverLocationBean locBean, DateTimeBean timeBean, 
			StarCatalogue catalogue) {
		observedSky = new SimpleObjectProperty<ObservedSky>(new ObservedSky(timeBean.getZonedDateTime(), locBean.getCoordinates(), projection.get(), catalogue));
		observedSky = Bindings.createObjectBinding(() ->
			new ObservedSky(timeBean.getZonedDateTime(), locBean.getCoordinates(),
					projection.get(), catalogue), 
				locBean.coordinatesProperty(),
				timeBean.dateProperty(),
				timeBean.timeProperty(),
				timeBean.zoneProperty(),
				projection);
	}
	
	private void initProjection(ViewingParametersBean viewBean) {
		projection = new SimpleObjectProperty<StereographicProjection>(new StereographicProjection(viewBean.getCenter()));
		projection = Bindings.createObjectBinding(() -> 
			new StereographicProjection(viewBean.getCenter()), 
			viewBean.centerProperty());		
	}
	
	private void initCanvas(ViewingParametersBean viewBean) {
		canvas = new Canvas(800, 600);
		painter = new SkyCanvasPainter(canvas);	
		
		canvas.setOnScroll(e -> {
			double fovDifference = Math.abs(e.getDeltaX()) > Math.abs(e.getDeltaY()) 
					? e.getDeltaX() 
					: e.getDeltaY();
			double newFov = ClosedInterval.of(MIN_FOV, MAX_FOV).clip(viewBean.getFieldOfViewDeg() + fovDifference);
			viewBean.setFieldOfViewDeg(newFov);
		});		
		canvas.setOnKeyPressed(e -> {
			KeyCode k = e.getCode();
			double az = viewBean.getCenter().az();
			double alt = viewBean.getCenter().alt();
			if(k.equals(KeyCode.LEFT)) {
				viewBean.setCenter(HorizontalCoordinates.of(Angle.normalizePositive(az - Angle.ofDeg(AZ_DEG_STEP)), alt));
			}
			else if(k.equals(KeyCode.RIGHT)) {
				viewBean.setCenter(HorizontalCoordinates.of(Angle.normalizePositive(az + Angle.ofDeg(AZ_DEG_STEP)), alt));
			}
			else if(k.equals(KeyCode.UP)) {
				viewBean.setCenter(HorizontalCoordinates.of(az, ALT_INTERVAL.clip(alt + Angle.ofDeg(ALT_DEG_STEP))));
			}
			else if(k.equals(KeyCode.DOWN)) {
				viewBean.setCenter(HorizontalCoordinates.of(az, ALT_INTERVAL.clip(alt - Angle.ofDeg(ALT_DEG_STEP))));
			}
			e.consume();
		});
	}
	
	/*
	 * Bonus number 3 (change the view with the mouse)
	 */
	private void setMouseDragBindings(ViewingParametersBean viewBean) {
		// New Drag detected
		canvas.setOnDragDetected(e -> {
			dragEntered = true;
			// set the anchor
			anchor = CartesianCoordinates.of(e.getX(), e.getY());
			// set the current projection center
			centerDrag = viewBean.getCenter();
		});
		canvas.setOnMouseDragged(e -> {
			if(dragEntered) {
				// scaling according to the current zoom and sensitivity
				double scalingX = - 1d / planeToCanvas.get().getMxx() * DRAG_SENSITIVITY;
				double scalingY = - 1d / planeToCanvas.get().getMyy() * DRAG_SENSITIVITY;				
				// difference between the anchor and the cursor
				Point2D deltaXY = new Point2D((e.getX() - anchor.x()) * scalingX, (e.getY() - anchor.y()) * scalingY);
				// set the new projection center
				viewBean.setCenter(HorizontalCoordinates.of(Angle.normalizePositive(centerDrag.az() + deltaXY.getX()), 
						ALT_INTERVAL.clip(centerDrag.alt() + deltaXY.getY())));
			}
		});	
		canvas.setOnMouseDragExited(e -> {
			dragEntered = false;
		});		
	}
	
	private void setMouseBindings(ViewingParametersBean viewBean) {
		mousePosition = new SimpleObjectProperty<CartesianCoordinates>(BEGINING_MOUSE_POSITION);	
		canvas.setOnMouseMoved(e -> {
			mousePosition.set(CartesianCoordinates.of(e.getX(), e.getY()));
		});

		canvas.setOnMouseReleased(e -> dragEntered = false);
		canvas.setOnMouseClicked(e -> canvas.requestFocus());
		mouseHorizontalPosition = Bindings.createObjectBinding(() -> {
				try {
					return projection.get().inverseApply(mousePosition.get().inverseTransform(planeToCanvas.get()));
				}
				catch(NonInvertibleTransformException e) {
					return HorizontalCoordinates.of(0, 0);
				}
			}, mousePosition, projection, planeToCanvas);
		
		mouseAzDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().azDeg(), mouseHorizontalPosition);
		mouseAltDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().altDeg(), mouseHorizontalPosition);
		
		objectUnderMouse = Bindings.createStringBinding(() -> {
			Optional<CelestialObject> closestObject;
			try {
				closestObject = observedSky.get().objectClosestTo(mousePosition.get().inverseTransform(planeToCanvas.get()), planeToCanvas.get().inverseDeltaTransform(OBJECT_UNDER_MOUSE_RANGE, 0).magnitude());
			}
			catch(NonInvertibleTransformException e) {
				closestObject = Optional.empty();
			}
			if(closestObject.isEmpty()) {
				return "none";
			}
			else {
				return closestObject.get().name();
			}
		}
			, observedSky, mousePosition, planeToCanvas);
	}
	
	private void initDrawingBindings() {
		planeToCanvas.addListener((p, o, n) -> {
			painter.drawAll(observedSky.get(), projection.get(), n);
		});
		observedSky.addListener((p, o, n) -> {
			painter.drawAll(n, projection.get(), planeToCanvas.get());
		});
		projection.addListener((p, o, n) -> {
			painter.drawAll(observedSky.get(), n, planeToCanvas.get());
		});		
	}
	
	/**
	 * @return object under mouse property
	 */
	public ObservableObjectValue<String> objectUnderMouseProperty(){
		return objectUnderMouse;
	}
	
	/**
	 * @return mouse az (horizontal coordinates) deg property
	 */
	public ObservableDoubleValue mouseAzDegProperty() {
		return mouseAzDeg;
	}
	
	/**
	 * @return mouse alt (horizontal coordinatess) deg property
	 */
	public ObservableDoubleValue mouseAltDegProperty() {
		return mouseAltDeg;
	}
	
	/**
	 * @return mouse az deg value
	 */
	public double getMouseAzDeg() {
		return mouseAzDeg.get();
	}
	
	/**
	 * @return mouse alt deg value
	 */
	public double getMouseAltDeg() {
		return mouseAltDeg.get();
	}
	
	/**
	 * @return canvas
	 */
	public Canvas canvas() {
		return canvas;
	}
}