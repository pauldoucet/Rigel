package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class Star extends CelestialObject{
	
	private final int hipparcosId;
	private final float colorIndex;

	/**
	 * @param hipparcosId : hipparcos ID
	 * @param name : name
	 * @param equatorialPos : equatorial position
	 * @param magnitude : magnitude
	 * @param colorIndex : color index
	 */
	public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos,
			float magnitude, float colorIndex) {
			super(name, equatorialPos, 0, magnitude);
			Preconditions.checkArgument(hipparcosId >= 0);
			Preconditions.checkInInterval(ClosedInterval.of(-0.5, 5.5), colorIndex);
			this.hipparcosId = hipparcosId;
			this.colorIndex = colorIndex;
	}
	
	/**
	 * @return the hipparcos ID
	 */
	public int hipparcosId() {
		return hipparcosId;
	}
	
	/**
	 * @return the color temperature
	 */
	public int colorTemperature() {
		int T = (int)(4600 * ((1f / (0.92 * colorIndex + 1.7)) + (1f / (0.92 * colorIndex + 0.62))));
		return T;
	}

}
