package ch.epfl.rigel.coordinates;

import java.util.Locale;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class EclipticCoordinates extends SphericalCoordinates{

	private EclipticCoordinates(double lon, double lat) {
		super(lon, lat);
	}
	
	/**
	 * @param lon : longitude in radian in [0, 2PI[
	 * @param lat : latitude in radian in [-PI/2, PI/2]
	 * @return ecliptic coordinates
	 */
	public static EclipticCoordinates of(double lon, double lat) {
		Preconditions.checkInInterval(RightOpenInterval.of(0, 2 * Math.PI), lon);
		Preconditions.checkInInterval(ClosedInterval.symmetric(Math.PI), lat);
		return new EclipticCoordinates(lon, lat);
	}
	
	public double lon() {
		return super.lon();
	}
	/**
	 * @return longitude in radian
	 */
	
	public double lonDeg() {
		return super.lonDeg();
	}
	/**
	 * @return longitude in degres
	 */
	
	public double lat() {
		return super.lat();
	}
	/**
	 * @return latitude in radian
	 */
	
	public double latDeg() {
		return super.latDeg();
	}
	/**
	 * @return latitude in degres
	 */
	
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "(λ=%.4f°, β=%.4f°)", lonDeg(), latDeg());
	}
	/**
	 * @return a string with longitude and latitude in degres
	 **/
}
