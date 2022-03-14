package ch.epfl.rigel.astronomy;

import java.util.Locale;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class Moon extends CelestialObject{

	private float phase;
	
	/**
	 * @param equatorialPos : equatorial position of the moon
	 * @param angularSize : angular size of the moon
	 * @param magnitude : magnitude
	 * @param phase : phase of the moon in [0,1]
	 */
	public Moon(EquatorialCoordinates equatorialPos, 
			float angularSize, float magnitude, float phase) {
		super("Lune", equatorialPos, angularSize, magnitude);
		Preconditions.checkInInterval(ClosedInterval.of(0, 1), phase);
		this.phase = phase;
	}
	
	public float phase() {
		return phase;
	}
	
	/**
	 *@return informations about the moon
	 */
	@Override
	public String info() {
		return String.format(Locale.ROOT, "Lune (%.1f", phase * 100) + "%)";
	}
	
	
}
