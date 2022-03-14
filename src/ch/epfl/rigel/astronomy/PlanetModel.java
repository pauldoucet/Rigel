package ch.epfl.rigel.astronomy;

import java.util.List;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

/**
 * @author Paul Doucet (316442)
 *
 */
public enum PlanetModel implements CelestialObjectModel<Planet> {
	MERCURY("Mercure", 0.24085, 75.5671, 77.612, 0.205627,
	        0.387098, 7.0051, 48.449, 6.74, -0.42),
	VENUS("Vénus", 0.615207, 272.30044, 131.54, 0.006812,
	      0.723329, 3.3947, 76.769, 16.92, -4.40),
	EARTH("Terre", 0.999996, 99.556772, 103.2055, 0.016671,
	      0.999985, 0, 0, 0, 0),
	MARS("Mars", 1.880765, 109.09646, 336.217, 0.093348,
	     1.523689, 1.8497, 49.632, 9.36, -1.52),
	JUPITER("Jupiter", 11.857911, 337.917132, 14.6633, 0.048907,
	        5.20278, 1.3035, 100.595, 196.74, -9.40),
	SATURN("Saturne", 29.310579, 172.398316, 89.567, 0.053853,
	       9.51134, 2.4873, 113.752, 165.60, -8.88),
	URANUS("Uranus", 84.039492, 356.135400, 172.884833, 0.046321,
	       19.21814, 0.773059, 73.926961, 65.80, -7.19),
	NEPTUNE("Neptune", 165.84539, 326.895127, 23.07, 0.010483,
	        30.1985, 1.7673, 131.879, 62.20, -6.87);
	
	private String name;
	private final double tp;
	private final double epsilon;
	private final double littleOmega;
	private final double e;
	private final double a;
	private final double i;
	private final double bigOmega;
	private final double angularSize;
	private final double magnitude;
	
	public static final List<PlanetModel> ALL = List.of(values());
	
	private PlanetModel(String name, double tp, double epsilon, 
			double littleOmega,double e, double a, double i, 
			double bigOmega, double angularSize, double magnitude) {
		this.name = name;
		this.tp = tp;
		this.epsilon = Angle.ofDeg(epsilon);
		this.littleOmega = Angle.ofDeg(littleOmega);
		this.e = e;
		this.a = a;
		this.i = Angle.ofDeg(i);
		this.bigOmega = Angle.ofDeg(bigOmega);
		this.angularSize = Angle.ofDMS(0, (int)Math.floor(angularSize/60), angularSize%60);
		this.magnitude = magnitude;
	}
		
	/**
	 *@param daysSinceJ2010 : days since the epoch J2010
	 *@param eclipticToEquatorialConversion : ecliptic to equatorial conversion
	 */
	@Override
	public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
		double M = Angle.normalizePositive((Angle.TAU / 365.242191) * 
				(daysSinceJ2010 / tp) + epsilon - littleOmega);
		double trueAnomaly = Angle.normalizePositive(M + 2 * e * Math.sin(M));
		
		double r = (a * (1 - Math.pow(e, 2))) / 
				(1 + e * Math.cos(trueAnomaly));
		double l = Angle.normalizePositive(trueAnomaly + littleOmega);
		
		double psi = Math.asin(Math.sin(l - bigOmega) * Math.sin(i));
		double rPrim = r * Math.cos(psi);
		double lPrim = Angle.normalizePositive(Math.atan2(Math.sin(l - bigOmega) * Math.cos(i), 
				Math.cos(l - bigOmega)) + bigOmega);
		
		double earthMeanAnomaly = (Angle.TAU / 365.242191) *
				(daysSinceJ2010 / EARTH.tp) + EARTH.epsilon - EARTH.littleOmega;
		double earthTrueAnomaly = Angle.normalizePositive(earthMeanAnomaly + 2 * EARTH.e * Math.sin(earthMeanAnomaly));
		
		double R = (EARTH.a * (1 - Math.pow(EARTH.e, 2)) / 
				(1 + EARTH.e * Math.cos(earthTrueAnomaly)));
		double L = Angle.normalizePositive(earthTrueAnomaly + EARTH.littleOmega);
		
		double lambda = 0;
		
		if(name.equals("Mercure") || name.equals("Vénus")) {
			lambda = Angle.normalizePositive(Math.PI + L + Math.atan2(rPrim * Math.sin(L - lPrim),
					R - rPrim * Math.cos(L - lPrim)));
		}
		else if(!name.equals("Terre")){
			lambda = Angle.normalizePositive(lPrim + Math.atan2(R * Math.sin(lPrim - L), 
					rPrim - R * Math.cos(lPrim - L)));
		}
		
		double beta = Math.atan((rPrim * Math.tan(psi) * Math.sin(lambda - lPrim)) /
				(R * Math.sin(lPrim - L)));
		
		double rho = Math.sqrt(Math.pow(R, 2) + Math.pow(r, 2) - 2 * R * r * 
				Math.cos(l - L) * Math.cos(psi));
		float theta = (float)(angularSize / rho);
		double F = (1 + Math.cos(lambda - l)) / 2;
		float m  = (float)(magnitude + 5 * Math.log10((r * rho)/Math.sqrt(F)));
		
		EquatorialCoordinates equ = eclipticToEquatorialConversion.apply(EclipticCoordinates.of(lambda, beta));
		
		if(name.equals("Terre")) {
			m = 0;
			theta = 0;
		}
		
		return new Planet(name, equ, theta, m);
	}

}
