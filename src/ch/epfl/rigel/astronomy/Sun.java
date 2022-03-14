package ch.epfl.rigel.astronomy;

import java.util.Objects;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class Sun extends CelestialObject{
	
	private EclipticCoordinates eclipticPos;
	private float meanAnomaly;

	/**
	 * @param eclipticPos : ecliptic position
	 * @param equatorialPos : equatorial position
	 * @param angularSize : angular size
	 * @param meanAnomaly : mean anomaly
	 */
	public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, 
			float angularSize, float meanAnomaly) {
		super("Soleil", equatorialPos, angularSize, (float)-26.7);
		this.eclipticPos = Objects.requireNonNull(eclipticPos);
		this.meanAnomaly = meanAnomaly;
	}
	
	/**
	 * @return the ecliptic position
	 */
	public EclipticCoordinates eclipticPos() {
		return eclipticPos;
	}
	
	/**
	 * @return the mean anomaly
	 */
	public double meanAnomaly() {
		return meanAnomaly;
	}

}
