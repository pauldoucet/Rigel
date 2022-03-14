package ch.epfl.rigel.astronomy;

import java.util.Objects;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * @author Paul Doucet (316442)
 *
 */
public abstract class CelestialObject {
	
	private String name;
	private EquatorialCoordinates equatorialPos;
	private float angularSize;
	private float magnitude;
	
	/**
	 * @param name : name
	 * @param equatorialPos : equatorial position of the object
	 * @param angularSize : angular size
	 * @param magnitude : magnitude
	 */
	CelestialObject(String name, EquatorialCoordinates equatorialPos,
			float angularSize, float magnitude){
		Preconditions.checkArgument(angularSize >= 0);
		this.name = Objects.requireNonNull(name);
		this.equatorialPos = Objects.requireNonNull(equatorialPos);
		this.angularSize = angularSize;
		this.magnitude = magnitude;
	}
	
	/**
	 * @return the name
	 */
	public String name() {
		return name;
	}
	
	/**
	 * @return the angular size
	 */
	public double angularSize() {
		return angularSize;
	}
	
	/**
	 * @return the magnitude
	 */
	public double magnitude() {
		return magnitude;
	}
	
	/**
	 * @return the equatorial position
	 */
	public EquatorialCoordinates equatorialPos() {
		return equatorialPos;
	}
	
	/**
	 * @return the informations about the celestial object
	 */
	public String info() {
		return name;
	}
	
	/**
	 *@return a string
	 */
	@Override
	public String toString() {
		return info();
	}
}
