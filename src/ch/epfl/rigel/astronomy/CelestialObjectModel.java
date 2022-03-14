package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;

/**
 * @author Paul Doucet (316442)
 *
 * @param <O> type of coordinates
 */
public interface CelestialObjectModel<O> {

	/**
	 * @param daysSinceJ2010 : days since the epoch J2010
	 * @param eclipticToEquatorialConversion : ecliptic to equatorial conversion
	 * @return coordinates of the object
	 */
	public abstract O at(double daysSinceJ2010, 
			EclipticToEquatorialConversion eclipticToEquatorialConversion);
}
