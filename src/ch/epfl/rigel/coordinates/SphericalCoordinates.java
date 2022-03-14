package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

/**
 * @author Paul Doucet (316442)
 *
 */
abstract class SphericalCoordinates {
	
	private final double longitude;
	private final double latitude;
	
	/**
	 * @param longitude : longitude in radian
	 * @param latitude : latitude in radian
	 */
	SphericalCoordinates(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	double lon() {
		return longitude;
	}
	
	double lonDeg() {
		return Angle.toDeg(longitude);
	}
	
	double lat() {
		return latitude;
	}
	
	double latDeg() {
		return Angle.toDeg(latitude);
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
