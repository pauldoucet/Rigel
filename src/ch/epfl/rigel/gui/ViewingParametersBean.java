package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;


public class ViewingParametersBean {
	
	private DoubleProperty fieldOfViewDeg;
	private ObjectProperty<HorizontalCoordinates> center;

	public ViewingParametersBean() {
		fieldOfViewDeg = new SimpleDoubleProperty();
		center = new SimpleObjectProperty<HorizontalCoordinates>();
	}
	
	public double getFieldOfViewDeg() {
		return fieldOfViewDeg.get();
	}
	
	public void setFieldOfViewDeg(double fieldOfView) {
		this.fieldOfViewDeg.set(fieldOfView);
	}
	
	public DoubleProperty fieldOfViewDegProperty() {
		return fieldOfViewDeg;
	}
	
	public HorizontalCoordinates getCenter() {
		return center.get();
	}
	
	public void setCenter(HorizontalCoordinates center) {
		this.center.set(center);
	}
	
	public ObjectProperty<HorizontalCoordinates> centerProperty(){
		return center;
	}
}
