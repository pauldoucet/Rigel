package ch.epfl.rigel.coordinates;

import java.time.ZonedDateTime;
import java.util.function.Function;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates, EquatorialCoordinates>{
	private final double T;
	private final double sinEpsilon;
	private final double cosEpsilon;
	private static Polynomial poly = Polynomial.of(Angle.ofArcsec(0.00181),
												   - Angle.ofArcsec(0.0006),
												   - Angle.ofArcsec(46.815),
												   Angle.ofDMS(23, 26, 21.45));
	
	/**
	 * @param when : zoned date time
	 */
	public EclipticToEquatorialConversion(ZonedDateTime when) {
		T = Epoch.J2000.julianCenturiesUntil(when);
		
		double epsilon = poly.at(T);
		cosEpsilon = Math.cos(epsilon);
		sinEpsilon = Math.sin(epsilon);
	}

	/**
	 * converts ecliptic coordinates to equatorial coordinates
	 */
	@Override
	public EquatorialCoordinates apply(EclipticCoordinates ecl) {		
		double lambda = ecl.lon();
		
		double beta   = ecl.lat();
		
		double alpha = Math.atan2((Math.sin(lambda) * cosEpsilon
				- Math.tan(beta) * sinEpsilon),Math.cos(lambda));
		
		double delta = Math.asin(Math.sin(beta) * cosEpsilon
				+ Math.cos(beta) * sinEpsilon * Math.sin(lambda));
		
		alpha = Angle.normalizePositive(alpha);
		
		return EquatorialCoordinates.of(alpha, delta);
	}
	
	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}
}