package ch.epfl.rigel.gui;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.ConstellationBoundsLoader;
import ch.epfl.rigel.astronomy.ConstellationCenterLoader;
import ch.epfl.rigel.astronomy.ConstellationNameLoader;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

public class Main extends Application {
	
	private final static GeographicCoordinates DEFAULT_OBSERVER_COORDINATES = GeographicCoordinates.ofDeg(6.57, 46.52); // Observer coordinates at start up
	private final static HorizontalCoordinates DEFAULT_PROJECTION_COORDINATES = HorizontalCoordinates.ofDeg(180, 15); // Projection center coordinates at start up
	private final static NamedTimeAccelerator DEFAULT_NAMED_TIME_ACCELERATOR = NamedTimeAccelerator.TIMES_300; // Time accelerator at start up
	private final static String RESET_BUTTON_UNICODE = "\uf0e2";
	private final static String PLAY_BUTTON_UNICODE = "\uf04b";
	private final static String PAUSE_BUTTON_UNICODE = "\uf04c";
	private final static String FONT_RESOURCE_PATH = "/Font Awesome 5 Free-Solid-900.otf";
	private static final double DEFAULT_FOV = 100d;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		try (InputStream hs = getClass().getResourceAsStream("/hygdata_v3.csv")) {
			try(InputStream as = getClass().getResourceAsStream("/asterisms.txt")) {
				try(InputStream cs = getClass().getResourceAsStream("/bound_in_20.txt")) {
					try(InputStream center = getClass().getResourceAsStream("/centers_20.txt")) {
						try(InputStream names = getClass().getResourceAsStream("/constellations_names.csv")) {
							// Load the star and asterism catalogue
							StarCatalogue catalogue = new StarCatalogue.Builder()
									.loadFrom(hs, HygDatabaseLoader.INSTANCE)
									.loadFrom(as, AsterismLoader.INSTANCE)
									.loadFrom(cs, ConstellationBoundsLoader.INSTANCE)
									.loadFrom(center, ConstellationCenterLoader.INSTANCE)
									.loadFrom(names, ConstellationNameLoader.INSTANCE)
									.build();
							
							// Initialize time, location and view beans
							DateTimeBean timeBean = new DateTimeBean();
							timeBean.setZonedDateTime(ZonedDateTime.now());
							
							ObserverLocationBean locBean = new ObserverLocationBean();
							locBean.setCoordinates(DEFAULT_OBSERVER_COORDINATES);
							
							ViewingParametersBean viewBean = new ViewingParametersBean();
							viewBean.setFieldOfViewDeg(DEFAULT_FOV);
							viewBean.setCenter(DEFAULT_PROJECTION_COORDINATES);
							
							TimeAnimator timeAnimator = new TimeAnimator(timeBean);
							timeAnimator.setAccelerator(DEFAULT_NAMED_TIME_ACCELERATOR.getAccelerator());
							
							SkyCanvasManager canvasManager = new SkyCanvasManager(catalogue, timeBean, locBean, viewBean);
							
							Canvas sky = canvasManager.canvas();
							
							// Initialize the up-side screen bar (control bar)
							HBox controlBar = initializeControlBar(timeBean, locBean, viewBean, timeAnimator);
							
							// Initialize the down-side screen bar (info bar)
							BorderPane infoBar = initInfoBar(viewBean, canvasManager);
							
							Pane skyPane = new Pane(canvasManager.canvas());
							
							sky.widthProperty().bind(skyPane.widthProperty());
							
							BorderPane root = new BorderPane(skyPane, controlBar, null, infoBar, null);
							
							// set window parameters
							primaryStage.setMinWidth(800);
							primaryStage.setMinHeight(600);
							
							primaryStage.setScene(new Scene(root));
							primaryStage.show();
		
							// request mouse focus
							sky.requestFocus();							
						}
					}
				}
			}
		}
	}
	
	/**
	 *  Initialize the control bar containing : 
	 *  	- Observer location infos box
	 *  	- Date, time and zone box
	 *  	- Buttons (reset and play/pause)
	 */
	private HBox initializeControlBar(DateTimeBean timeBean, ObserverLocationBean locBean, 
			ViewingParametersBean viewBean, TimeAnimator animator) {
		HBox controlBar = new HBox();
		controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
		
		HBox locationBox = initLocationBox(locBean);
		
		Font fontAwesome = loadFont(FONT_RESOURCE_PATH);
		
		HBox timeBox = initTimeBox(timeBean, animator, fontAwesome);
		
		
		Button playPauseButton = initPlayPauseButton(fontAwesome, animator);
		
		controlBar.getChildren().addAll(locationBox, timeBox, playPauseButton);
		return controlBar;		
	}
	
	/**
	 *  Initialize the time box containing :
	 *  	- Date box
	 *  	- time text field
	 *  	- zone box
	 *  	- reset button
	 *  	- accelerator box
	 */
	private HBox initTimeBox(DateTimeBean timeBean, TimeAnimator animator, Font fontAwesome) {
		HBox zoneDateTimeBox = new HBox();
		zoneDateTimeBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
		
		DatePicker datePicker = initDatePicker(timeBean);
		
		TextField timeTextField = initTimeTextField(timeBean, animator);
		
		ComboBox<String> zoneBox = initZoneBox(timeBean);
		
		ChoiceBox<NamedTimeAccelerator> accelerator = initAcceleratorBox(animator);
		
		Button resetButton = initResetButton(fontAwesome, timeBean);
		
		zoneDateTimeBox.getChildren().addAll(new Label("Date :"), datePicker, 
				new Label("Heure :"), timeTextField, zoneBox, accelerator, resetButton);
		
		zoneDateTimeBox.disableProperty().bind(animator.runningProperty());
		return zoneDateTimeBox;
	}
	
	/*
	 * Initialize the reset button
	 */
	private Button initResetButton(Font fontAwesome, DateTimeBean timeBean) {
		Button resetButton = new Button(RESET_BUTTON_UNICODE);
		resetButton.setFont(fontAwesome);	
		resetButton.setOnAction(e -> timeBean.setTime(LocalTime.now()));
		return resetButton;
	}
	
	/*
	 * Initialize the play/pause button
	 */
	private Button initPlayPauseButton(Font fontAwesome, TimeAnimator timeAnimator) {
		Button playPauseButton = new Button();
		playPauseButton.textProperty().bind(Bindings
				.when(timeAnimator.runningProperty())
				.then(PAUSE_BUTTON_UNICODE)
				.otherwise(PLAY_BUTTON_UNICODE));
		playPauseButton.setFont(fontAwesome);
		playPauseButton.setOnAction(e -> {
			if(timeAnimator.getRunning()) {
				timeAnimator.stop();
			}
			else {
				timeAnimator.start();
			}
		});
		return playPauseButton;
	}
	
	/*
	 * load a font
	 */
	private Font loadFont(String resourceName) {
		try(InputStream fontStream = getClass().getResourceAsStream(resourceName)) {
			Font fontAwesome = Font.loadFont(fontStream, 15);
			fontStream.close();
			return fontAwesome;
		}
		catch(IOException e) {
			throw new Error("cannot load this font");
		}
	}
	
	/*
	 * Initialize the horizontal box information bar containing : 
	 * 		- the fov text
	 * 		- the mouse text
	 * 		- the object under mouse text
	 */
	private BorderPane initInfoBar(ViewingParametersBean viewBean, SkyCanvasManager canvasManager) {
		Text fovText = initFovText(viewBean);
		
		Text mouseText = initMouseText(canvasManager);
		
		Text objectUnderMouseText = initObjectUnderMouseText(canvasManager);
		
		BorderPane infoBar = new BorderPane(objectUnderMouseText, null, mouseText, null, fovText);
		infoBar.setStyle("-fx-padding: 4; -fx-background-color: white;");
		return infoBar;
	}
	
	/*
	 * Initialize the field of view text
	 */
	private Text initFovText(ViewingParametersBean viewBean) {
		Text fovText = new Text();
		fovText.textProperty().bind(Bindings.format(Locale.ROOT, "Champ de vue : %.2f° ", viewBean.fieldOfViewDegProperty()));
		return fovText;
	}
	
	/*
	 * Initialize the mouse text
	 */
	private Text initMouseText(SkyCanvasManager canvasManager) {
		Text mouseText = new Text();
		mouseText.textProperty().bind(Bindings.format(Locale.ROOT, "Azimut : %.2f°, hauteur : %.2f°", canvasManager.mouseAzDegProperty(), canvasManager.mouseAltDegProperty()));
		return mouseText;
	}
	
	/*
	 * Initialize the object under mouse text
	 */
	private Text initObjectUnderMouseText(SkyCanvasManager canvasManager) {
		Text objectUnderMouseText = new Text();
		objectUnderMouseText.textProperty().bind(canvasManager.objectUnderMouseProperty());
		return objectUnderMouseText;	
	}
	
	/*
	 * Initialize the accelerator choice box
	 */
	private ChoiceBox<NamedTimeAccelerator> initAcceleratorBox(TimeAnimator timeAnimator) {
		ChoiceBox<NamedTimeAccelerator> acceleratorBox = new ChoiceBox<NamedTimeAccelerator>();
		acceleratorBox.setStyle("-fx-spacing: inherit;");
		acceleratorBox.getItems().addAll(NamedTimeAccelerator.values());
		acceleratorBox.getSelectionModel().select(DEFAULT_NAMED_TIME_ACCELERATOR);
		
		acceleratorBox.getSelectionModel().selectedItemProperty().addListener((p, o, n) ->
				timeAnimator.setAccelerator(n.getAccelerator()));
		
		return acceleratorBox;	
	}
	
	/*
	 * Initialize the location horizontal box containing :
	 *		- the longitude label
	 *		- the longitude box
	 *		- the latitude label
	 *		- the latitude box
	 */
	private HBox initLocationBox(ObserverLocationBean locBean) {
		HBox locationBox = new HBox();
		locationBox.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
		locationBox.getChildren().addAll(new Label("Longitude (°) :"), 
				initializeLonBox(locBean), 
				new Label("Latitude (°) :"),
				initializeLatBox(locBean));
		return locationBox;
	}
	
	/*
	 *  Initialize the zone combo box
	 */
	private ComboBox<String> initZoneBox(DateTimeBean timeBean) {
		ComboBox<String> zoneComboBox = new ComboBox<String>();
		List<String> zoneIds = new ArrayList<String>(ZoneId.getAvailableZoneIds());
		Collections.sort(zoneIds);
		zoneComboBox.getItems().addAll(zoneIds);
		zoneComboBox.setStyle("-fx-pref-width: 180;");
		zoneComboBox.getSelectionModel().select(timeBean.getZone().getId());
		zoneComboBox.getSelectionModel().selectedItemProperty().addListener((p, o, n) ->
				timeBean.setZone(ZoneId.of(n)));
		return zoneComboBox;
		
	}
	
	/*
	 *  Initialize the text
	 *  
	 */
	private TextField initTimeTextField(DateTimeBean timeBean, TimeAnimator animator) {
		DateTimeFormatter hmsFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTimeStringConverter stringConverter = new LocalTimeStringConverter(hmsFormatter, hmsFormatter);
		TextFormatter<LocalTime> timeFormatter = new TextFormatter<>(stringConverter);
		timeFormatter.valueProperty().bindBidirectional(timeBean.timeProperty());
		
		TextField timeTextField = new TextField();
		timeTextField.setTextFormatter(timeFormatter);
		timeTextField.disableProperty().bind(animator.runningProperty());
		timeTextField.setStyle("-fx-pref-width: 75; -fx-alignment: baseline-right;");
		
		return timeTextField;
	}
	
	/*
	 *  Initialize the date picker
	 * 
	 */
	private DatePicker initDatePicker(DateTimeBean timeBean) {
		DatePicker datePicker = new DatePicker();
		datePicker.setStyle("-fx-pref-width: 120;");
		datePicker.valueProperty().bindBidirectional(timeBean.dateProperty());
		
		datePicker.setValue(timeBean.getDate());
		return datePicker;
	}
	
	/*
	 *  Text formatter (for latDeg and lonDeg) modularization using predicates
	 * 
	 */
	private TextFormatter<Number> createLocFormatter(Predicate<Double> predicate) {
		NumberStringConverter stringConverter = new NumberStringConverter("#0.00");

		UnaryOperator<TextFormatter.Change> filter = (change -> {
			try {
				String newText = change.getControlNewText();
				double newDeg = stringConverter.fromString(newText).doubleValue();
				return predicate.test(newDeg) ? change : null;
			} catch (Exception e) {
				return null;
			}
		});
	
		return new TextFormatter<>(stringConverter, 0, filter);
		
	}
	
	/*
	 *  Initialize the longitude text field
	 * 
	 */
	private TextField initializeLonBox(ObserverLocationBean locBean) {
		TextFormatter<Number> lonTextFormatter = createLocFormatter(GeographicCoordinates::isValidLonDeg);
	 	lonTextFormatter.valueProperty().addListener((p, o, n) -> locBean.setLonDeg(n.doubleValue()));

		TextField lonTextField = new TextField();
		lonTextField.setTextFormatter(lonTextFormatter);
		lonTextFormatter.setValue(locBean.getLonDeg());
		lonTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
		return lonTextField;
	}
	
	/*
	 * Initialize the latitude text field
	 * 
	 */
	private TextField initializeLatBox(ObserverLocationBean locBean) {
		TextFormatter<Number> latTextFormatter = createLocFormatter(GeographicCoordinates::isValidLatDeg);
		latTextFormatter.valueProperty().addListener((p, o, n) -> locBean.setLatDeg(n.intValue()));
		
		TextField latTextField = new TextField();
		latTextField.setTextFormatter(latTextFormatter);
		latTextField.setStyle("-fx-pref-width: 60; -fx-alignment: baseline-right;");
		latTextFormatter.setValue(locBean.getLatDeg());
		return latTextField;
	}

}
