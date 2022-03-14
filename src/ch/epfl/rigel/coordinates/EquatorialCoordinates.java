package ch.epfl.rigel.coordinates;

import java.util.Locale;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class EquatorialCoordinates extends SphericalCoordinates{

	private EquatorialCoordinates(double ra, double dec) {
		super(ra, dec);
	}
	
	/**
	 * @param ra : right ascension in radian in [0, 2*pi[
	 * @param dec : declination in radian in ]-pi, pi[
	 * @return equatorial coordinates
	 */
	public static EquatorialCoordinates of(double ra, double dec) {
		Preconditions.checkInInterval(RightOpenInterval.of(0, 2 * Math.PI), ra);
		Preconditions.checkInInterval(ClosedInterval.symmetric(Math.PI), dec);
		return new EquatorialCoordinates(ra, dec);
	}
	
	/**
	 * @return longitude in radian
	 */
	public double ra() {
		return super.lon();
	}
	
	/**
	 * @return longitude in degres
	 */
	public double raDeg() {
		return super.lonDeg();
	}
	
	/**
	 * @return right ascension in hour
	 */
	public double raHr() {
		return Angle.toHr(super.lon());
	}
	
	/**
	 * @return declination
	 */
	public double dec() {
		return super.lat();
	}
	
	/**
	 * @return declination in degres
	 */
	public double decDeg() {
		return super.latDeg();
	}
	
	/**
	 * return a string with right ascension in hour and declination in degres
	 */
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "(ra=%.4fh, dec=%.4f°)", raHr(), decDeg());
	}

}
