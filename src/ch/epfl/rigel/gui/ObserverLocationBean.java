package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;

public class ObserverLocationBean {

	DoubleProperty lonDeg = new SimpleDoubleProperty();
	DoubleProperty latDeg = new SimpleDoubleProperty();
	ObservableObjectValue<GeographicCoordinates> coordinates = new SimpleObjectProperty<GeographicCoordinates>();
	
	public ObserverLocationBean(){
		coordinates= Bindings.createObjectBinding(() -> GeographicCoordinates.ofDeg(lonDeg.get(), latDeg.get()),
						lonDeg, latDeg);
	}
	
	public double getLonDeg() {
		return lonDeg.get();
	}
	
	public double getLatDeg() {
		return latDeg.get();
	}
	
	public GeographicCoordinates getCoordinates() {
		return coordinates.get();
	}
	
	public void setLonDeg(double lonDeg) {
		this.lonDeg.set(lonDeg);
	}
	
	public void setLatDeg(double latDeg) {
		this.latDeg.set(latDeg);
	}
	
	public DoubleProperty latDegProperty() {
		return latDeg;
	}
	
	public DoubleProperty lonDegProperty() {
		return lonDeg;
	}
	
	public ObservableObjectValue<GeographicCoordinates> coordinatesProperty() {
		return coordinates;
	}
	
	public void setCoordinates(GeographicCoordinates coord) {
		setLatDeg(coord.latDeg());
		setLonDeg(coord.lonDeg());
	}
	
}
