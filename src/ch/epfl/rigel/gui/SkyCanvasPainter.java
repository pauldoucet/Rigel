package ch.epfl.rigel.gui;

import java.util.List;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.Planet;
import ch.epfl.rigel.astronomy.Star;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Transform;

/**
 * @author Paul Doucet (316442)
 *
 */
public class SkyCanvasPainter {
	
	private final static double SUN_DIAMETER = 4.36e-3;
	private final static double UP_BOUND_MAGNITUDE = 1;
	private final static double ASTRONOMICAL_TWILIGHT_LOWER_BOUND = -18;
	private final ClosedInterval BRIGHTNESS_INTERVAL = ClosedInterval.of(0, 1);
	
	private Canvas canvas;
	private GraphicsContext ctx;

	/**
	 * @param canvas : canvas
	 */
	public SkyCanvasPainter(Canvas canvas) {
		this.canvas = canvas;
		ctx = canvas.getGraphicsContext2D();
		BlackBodyColor.initializeTemperatures();
	}
	
	/**
	 * clear the sky with black
	 */
	public void clear() {
		ctx.setFill(Color.BLACK);
		ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	
	/**
	 * 
	 * @param sky : observed sky
	 * @param projection : stereographic projection
	 * @param planeToCanvas : plane to canvas transformation
	 */
	public void drawSkyColor(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {
		double horizonBrightness, skyBrightness;
		HorizontalCoordinates sunPosition = sky.sunHorizontalPosition();
		Stop[] stops = new Stop[2];
		
		// compute horizon and sky brightness
		horizonBrightness = BRIGHTNESS_INTERVAL.clip((ASTRONOMICAL_TWILIGHT_LOWER_BOUND - sunPosition.altDeg()) / ASTRONOMICAL_TWILIGHT_LOWER_BOUND);
		skyBrightness = BRIGHTNESS_INTERVAL.clip((sunPosition.altDeg() - 2d )/ 10d);
		
		// compute the shades of blue according to brightness
		Color horizonColor = Color.hsb(210, 0.99, horizonBrightness);
		Color skyColor = Color.hsb(210, 0.99, skyBrightness);
		stops[0] = new Stop(1, horizonColor);
		stops[1] = new Stop(0, skyColor);
		
		// compute Horizon center and radius
		CartesianCoordinates center = projection.circleCenterForParallel(HorizontalCoordinates.of(0, 0));
		double radius = projection.circleRadiusForParallel(HorizontalCoordinates.of(0, 0));
		radius = planeToCanvas.deltaTransform(radius, 0).magnitude();
		center = CartesianCoordinates.of(planeToCanvas.transform(center.x(), center.y()));
		
		// create the color gradient
		RadialGradient gradient = new RadialGradient(75, 100, center.x(), center.y(), radius, false, CycleMethod.NO_CYCLE, stops);
		
		ctx.setFill(gradient);
		ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	/**
	 * @param sky : observed sky
	 * @param projection : stereographic projection
	 * @param planeToCanvas : transformation
	 */
	public void drawStars(ObservedSky sky, StereographicProjection projection,
			Transform planeToCanvas) {
		List<Star> stars = sky.stars();
		// Transform stars position to screen size
		double[] transformedStarsPositions = new double[stars.size() * 2];
		planeToCanvas.transform2DPoints(sky.starsPositions(), 0, 
				transformedStarsPositions, 0, stars.size());
		
		drawAsterisms(sky, projection, transformedStarsPositions);
		
		drawHighMagnitude(sky, transformedStarsPositions);
		
		// Display stars
		for(int i = 0; i < stars.size(); ++i) {
			// Draw stars and get size and color according to magnitude and temperature
			double clipedMagnitude = ClosedInterval.of(-2, 5)
					.clip(stars.get(i).magnitude());
			double sizeFactor = (99d - 17d * clipedMagnitude) / 140d;
			double radius = sizeFactor * Math.tan(Angle.ofDeg(0.5) / 4);
			radius = planeToCanvas.deltaTransform(radius, 0).magnitude();
			Color color = BlackBodyColor.colorForTemperature(stars.get(i).colorTemperature());
			ctx.setFill(color);
			fillCircle(transformedStarsPositions[i * 2], 
					transformedStarsPositions[i * 2 + 1], radius);
		}
	}
	
	private void drawAsterisms(ObservedSky sky, StereographicProjection projection, double[] transformedStarsPositions) {
		// Draw Asterisms
		ctx.setLineWidth(1);
		ctx.setStroke(Color.BLUE);
		for(Asterism a : sky.asterisms()) {
			List<Integer> indices = sky.asterismIndices(a);
			ctx.beginPath();
			double cursorX = 0;
			double cursorY = 0;
			for(int i : indices) {
				double x = transformedStarsPositions[i * 2];
				double y = transformedStarsPositions[i * 2 + 1];
				if(canvas.getBoundsInLocal().contains(x, y) || canvas.getBoundsInLocal().contains(cursorX, cursorY)) {
					ctx.lineTo(x, y);
				}
				else {
					ctx.moveTo(x, y);
				}
				cursorX = x;
				cursorY = y;
			}
			ctx.stroke();
		}
	}
	
	/**
	 * @param sky : observed sky
	 * @param projection : stereographic projection
	 * @param planeToCanvas : transformation
	 */
	public void drawPlanets(ObservedSky sky, StereographicProjection projection,
			Transform planeToCanvas) {
		List<Planet> planets = sky.planets();
		double[] transformedPlanetsPositions = new double[planets.size() * 2];
		planeToCanvas.transform2DPoints(sky.planetsPositions(), 0, 
				transformedPlanetsPositions, 0, planets.size());
		for(int i = 0; i < planets.size(); ++i) {
			double clipedMagnitude = ClosedInterval.of(-2, 5)
					.clip(planets.get(i).magnitude());
			double sizeFactor = (99d - 17 * clipedMagnitude) / 140d;
			double radius = sizeFactor * Math.tan(Angle.ofDeg(0.5) / 4);
			radius = planeToCanvas.deltaTransform(radius, 0).magnitude();
			
			ctx.setFill(Color.LIGHTGRAY);
			fillCircle(transformedPlanetsPositions[i * 2], 
					transformedPlanetsPositions[i * 2 + 1], radius);
		}
	}

	/**
	 * @param sky : observed sky
	 * @param projection : stereographic projection
	 * @param planeToCanvas : transformation
	 */
	public void drawSun(ObservedSky sky, StereographicProjection projection,
			Transform planeToCanvas) {
		double radius = planeToCanvas.deltaTransform(SUN_DIAMETER, 0).magnitude() / 2;
		CartesianCoordinates coords = sky.sunPosition();
		Point2D center = planeToCanvas.transform(coords.x(), coords.y());
		
		ctx.setFill(Color.YELLOW.deriveColor(1, 1, 1, 0.25));
		fillCircle(center.getX(), center.getY(), radius * 2.2);
		
		ctx.setFill(Color.YELLOW);
		fillCircle(center.getX(), center.getY(), radius + 1);
		
		ctx.setFill(Color.WHITE);
		fillCircle(center.getX(), center.getY(), radius);
	}
	
	private void fillCircle(double x, double y, double radius) {
		double diameter = radius * 2;
		ctx.fillOval(x - radius, y - radius, diameter, diameter);
	}
	
	private void strokeCircle(double x, double y, double radius) {
		double diameter = radius * 2;
		ctx.strokeOval(x - radius, y - radius, diameter, diameter);
	}
	
	/**
	 * @param sky : observed sky
	 * @param projection : stereographic projection
	 * @param planeToCanvas : transformation
	 */
	public void drawMoon(ObservedSky sky, StereographicProjection projection,
			Transform planeToCanvas) {
		// Compute moon radius and center
		double radius = projection.applyToAngle(sky.moon().angularSize()) / 2;
		radius = planeToCanvas.deltaTransform(radius, 0).magnitude();
		CartesianCoordinates coords = sky.moonPosition();
		Point2D center = planeToCanvas.transform(coords.x(), coords.y());
		
		// Draw the moon
		ctx.setFill(Color.WHITE);
		fillCircle(center.getX(), center.getY(), radius);
	}
	
	/**
	 * @param projection : stereographic projection
	 * @param planeToCanvas : transformation
	 */
	public void drawHorizon(StereographicProjection projection,
			Transform planeToCanvas) {
		// Compute Horizon center and radius
		CartesianCoordinates center = projection.circleCenterForParallel(HorizontalCoordinates.of(0, 0));
		double radius = projection.circleRadiusForParallel(HorizontalCoordinates.of(0, 0));
		radius = planeToCanvas.deltaTransform(radius, 0).magnitude();
		center = CartesianCoordinates.of(planeToCanvas.transform(center.x(), center.y()));
		
		// Draw Horizon
		ctx.setLineWidth(2);
		ctx.setStroke(Color.RED);
		strokeCircle(center.x(), center.y(), radius);
		
		// Display cardinates informations
		List<String> cardinates = List.of("N", "NE", "E", "SE", "S", "SO", "O", "NO");
		ctx.setLineWidth(1);
		ctx.setTextBaseline(VPos.TOP);
		ctx.setTextAlign(TextAlignment.CENTER);
		for(int i = 0; i < cardinates.size(); ++i) {
			CartesianCoordinates xy = projection.apply(HorizontalCoordinates.of(Angle.ofDeg(i * 45), -Angle.ofDeg(0.5)));
			xy = xy.transform(planeToCanvas);
			ctx.strokeText(cardinates.get(i), xy.x(), xy.y());
		}
	}
	
	/**
	 *  Performs the operations in the following order : draw the sky color, draw the stars, draw the sun, draw the planets, draw the horizon
	 *  
	 * @param sky : observed sky
	 * @param projection : stereographic projection
	 * @param planeToCanvas : plane to canvas transformation
	 */
	public void drawAll(ObservedSky sky, StereographicProjection projection,
			Transform planeToCanvas) {
		drawSkyColor(sky, projection, planeToCanvas);
		drawStars(sky, projection, planeToCanvas);
		drawSun(sky, projection, planeToCanvas);
		drawPlanets(sky, projection, planeToCanvas);
		drawConstellationsBounds(sky, planeToCanvas);
		drawHorizon(projection, planeToCanvas);
		drawConstellationsNames(sky, planeToCanvas);
	}
	
	/**
	 *  Draw the name of the stars with a magnitude less or equals than UP_BOUND_MAGNITUDE
	 * 
	 * @param sky : observed sky
	 * @param transformedStarsPosition : array of transformed stars position 
	 */
	private void drawHighMagnitude(ObservedSky sky, double[] transformedStarsPosition){
		for(int i = 0; i < sky.stars().size(); ++i) {
			if(sky.stars().get(i).magnitude() < UP_BOUND_MAGNITUDE) {
				double x = transformedStarsPosition[i * 2];
				double y = transformedStarsPosition[i * 2 + 1];
				ctx.setLineWidth(0.8);
				ctx.setStroke(Color.WHITE);
				ctx.strokeText(sky.stars().get(i).name(), x, y);
			}
		}
	}
	
	/**
	 *  Draw constellations bounds and names
	 */
	/*private void drawConstellations(ObservedSky sky, Transform planeToCanvas) {
		drawConstellationsBounds(sky, planeToCanvas);
		drawConstellationsNames(sky, planeToCanvas);
	}*/
	
	/**
	 * Draw constellations bounds
	 */
	private void drawConstellationsBounds(ObservedSky sky, Transform planeToCanvas) {
		ctx.setLineWidth(1);
		ctx.setStroke(Color.YELLOW);
		ctx.setGlobalAlpha(0.2);
		for(int i = 0; i < sky.constellations().size(); ++i) {
				double[] transformedPositions = new double[sky.constellationsPosition().get(i).length];
				planeToCanvas.transform2DPoints(sky.constellationsPosition().get(i), 0, transformedPositions, 0, transformedPositions.length / 2);
				double cursorX = 0;
				double cursorY = 0;
				ctx.beginPath();
				for(int j = 0; j < transformedPositions.length / 2; ++j) {
					double x = transformedPositions[j * 2];
					double y = transformedPositions[j * 2 + 1];
					if(canvas.getBoundsInLocal().contains(x, y) || canvas.getBoundsInLocal().contains(cursorX, cursorY)) {
						ctx.lineTo(x, y);
					}
					else {
						ctx.moveTo(x, y);
					}
					cursorX = x;
					cursorY = y;
				}
				ctx.stroke();
		}
		ctx.setGlobalAlpha(1);

	}
	
	/**
	 *  Draw constellations names at barycenters position
	 */
	private void drawConstellationsNames(ObservedSky sky, Transform planeToCanvas) {
		// Transform the barycenters of the constellations from plane to canvas
		double[] transformedCenters = new double[sky.constellationsCenter().length];
		planeToCanvas.transform2DPoints(sky.constellationsCenter(), 0, transformedCenters, 0, transformedCenters.length / 2);
		// For each constellation draw its name at barycenter position
		for(int i = 0; i < sky.constellations().size(); ++i) {
			ctx.setStroke(Color.YELLOW);
			ctx.setGlobalAlpha(0.4);
			ctx.strokeText(sky.constellations().get(i).name(), transformedCenters[i * 2], transformedCenters[i * 2 + 1]);
			ctx.setGlobalAlpha(1);
		}
	}
}