package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

/**
 * @author Paul Doucet (316442)
 *
 */
public enum MoonModel implements CelestialObjectModel<Moon>{
	MOON();
	
	private final static double l0 = Angle.ofDeg(91.929336);
	private final static double P0 = Angle.ofDeg(130.143076);
	private final static double N0 = Angle.ofDeg(291.682547);
	private final static double i = Angle.ofDeg(5.145396);
	private final static double e = 0.0549;

	/**
	 *@param daysSinceJ2010 : days since the epoch J2010
	 *@param eclipticToEquatorialConversion : ecliptic to equatorial conversion
	 *@return position of the moon at this moment
	 */
	@Override
	public Moon at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
		
		
		/**
		 * sun orbital position and mean anomaly computation
		 */
		Sun sun = SunModel.SUN.at(daysSinceJ2010, eclipticToEquatorialConversion);
		double meanAnomaly = sun.meanAnomaly();
		double lambda = sun.eclipticPos().lon();
		
		double l = Angle.normalizePositive(Angle.ofDeg(13.1763966) * daysSinceJ2010 + l0);
		double Mm = Angle.normalizePositive(l - Angle.ofDeg(0.1114041) * daysSinceJ2010 - P0);
		
		double Ev = Angle.ofDeg(1.2739) * Math.sin(2 * (l - lambda) - Mm);
		double Ae = Angle.ofDeg(0.1858) * Math.sin(meanAnomaly);
		double A3 = Angle.ofDeg(0.37) * Math.sin(meanAnomaly);
		double Mmd = Mm + Ev - Ae - A3;
		double Ec = Angle.ofDeg(6.2886) * Math.sin(Mmd);
		double A4 = Angle.ofDeg(0.214) * Math.sin(2 * Mmd);
		double ld = l + Ev + Ec - Ae + A4;
		double V = Angle.ofDeg(0.6583) * Math.sin(2 * (ld - lambda));
		double ldd = ld + V;
		
		/**
		 * ecliptic position computation
		 */
		double N = Angle.normalizePositive(N0 - Angle.ofDeg(0.0529539) * daysSinceJ2010);
		double Nd = N - Angle.ofDeg(0.16) * Math.sin(meanAnomaly);
		double lambdaM = Angle.normalizePositive(Math.atan2(Math.sin(ldd - Nd) * Math.cos(i),
				Math.cos(ldd - Nd)) + Nd);
		double betaM = Math.asin(Math.sin(ldd - Nd) * Math.sin(i));

		/**
		 * Moon phase computation
		 */
		double F = (1 - Math.cos(ldd - lambda)) / 2;

		/**
		 * Angular size computation
		 */
		double rho = (1 - Math.pow(e, 2)) / (1 + e * Math.cos(Mmd + Ec));
		double theta = Angle.ofDeg(0.5181) / rho;
		
		EquatorialCoordinates equ = eclipticToEquatorialConversion.apply(EclipticCoordinates.of(lambdaM,  betaM));
		
		return new Moon(equ, (float)theta, 0f, (float)F);
	}
	
	
}
