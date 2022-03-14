package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class Planet extends CelestialObject{

	/**
	 * @param name : name
	 * @param equatorialPos : equatorial position
	 * @param angularSize : angular size
	 * @param magnitude : magnitude
	 */
	public Planet(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
		super(name, equatorialPos, angularSize, magnitude);
	}
	
	

}
