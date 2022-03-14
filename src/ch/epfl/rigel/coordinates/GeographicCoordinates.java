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
public final class GeographicCoordinates extends SphericalCoordinates{

	private GeographicCoordinates(double longitude, double latitude) {
		super(longitude, latitude);
	}
	
	/**
	 * @param lonDeg : longitude in degres
	 * @param latDeg : latitude in degres
	 * @return geographic coordinates
	 */
	public static GeographicCoordinates ofDeg(double lonDeg, double latDeg) {
		Preconditions.checkInInterval(RightOpenInterval.of(-180, 180), lonDeg);
		Preconditions.checkInInterval(ClosedInterval.symmetric(180), latDeg);
		return new GeographicCoordinates(Angle.ofDeg(lonDeg),Angle.ofDeg(latDeg));
	}
	
	/**
	 * @param lonDeg : longitude in degres
	 * @return if the longitude given is valid
	 */
	public static boolean isValidLonDeg(double lonDeg) {
		return RightOpenInterval.of(-180, 180).contains(lonDeg);
	}
	
	/**
	 * @param latDeg : latitude in degres
	 * @return if the latitude given is valid
	 */
	public static boolean isValidLatDeg(double latDeg) {
		return ClosedInterval.symmetric(180).contains(latDeg);
	}
	
	/**
	 *@return longitude in radian
	 */
	public double lon() {
		return super.lon();
	}
	
	/**
	 *@return longitude in degres
	 */
	public double lonDeg() {
		return super.lonDeg();
	}
	
	/**
	 *@return latitude
	 */
	public double lat() {
		return super.lat();
	}
	
	/**
	 *@return latitude in degres
	 */
	public double latDeg() {
		return super.latDeg();
	}
	
	/**
	 *@return string with longitude in degres and latitude in degres
	 */
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "(lon=%.4f°, lat=%.4f°)",lonDeg(),latDeg());	
	}
}
