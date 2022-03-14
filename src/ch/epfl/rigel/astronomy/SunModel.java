package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

/**
 * @author Paul Doucet (316442)
 *
 */
public enum SunModel implements CelestialObjectModel<Sun>{
	SUN();
	
	public static final double epsilonG = Angle.ofDeg(279.557208);
	public static final double littleOmegaG = Angle.ofDeg(283.112438);
	public static final double e = 0.016705;
	public static final float theta0 = (float)Angle.ofDeg(0.533128);

	/**
	 *@param daysSinceJ2010 : days since the epoch J2010
	 *@param eclipticToEquatorialConversion : ecliptic to equatorial conversion
	 */
	@Override
	public Sun at(double daysSinceJ2010, 
			EclipticToEquatorialConversion eclipticToEquatorialConversion) {
		
		double meanAnomaly = Angle.normalizePositive((Angle.TAU / 365.242191) * daysSinceJ2010
				+ epsilonG - littleOmegaG);
		
		double trueAnomaly = Angle.normalizePositive(meanAnomaly + 2 * e * Math.sin(meanAnomaly));
		
		double eclLon = Angle.normalizePositive(trueAnomaly + littleOmegaG);
		double eclLat = 0;
		
		double angularSize = (theta0 * (1 + e * Math.cos(trueAnomaly)) / 
				(1 - Math.pow(e, 2)));
		
		EclipticCoordinates ecl = EclipticCoordinates.of(eclLon, eclLat);
		EquatorialCoordinates equ = eclipticToEquatorialConversion.apply(ecl);
		
		return new Sun(ecl, equ, (float)angularSize, (float)meanAnomaly);
	}

}
