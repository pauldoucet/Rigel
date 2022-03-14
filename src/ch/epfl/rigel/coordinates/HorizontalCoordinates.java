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
public final class HorizontalCoordinates extends SphericalCoordinates{
	
	private HorizontalCoordinates(double az, double alt) {
		super(az, alt);
	}
	
	/**
	 * @param az : azimut in radian in [0, 2*PI[
	 * @param alt : altitude in radian in [-PI/2, PI/2]
	 * @return horizontal coordinates
	 */
	public static HorizontalCoordinates of(double az, double alt) {
		Preconditions.checkInInterval(RightOpenInterval.of(0, 2*Math.PI), az);
		Preconditions.checkInInterval(ClosedInterval.symmetric(Math.PI), alt);
		return new HorizontalCoordinates(az, alt);
	}
	
	/**
	 * @param az : azimut in degres
	 * @param alt : azimut in degres
	 * @return horizontal coordinates
	 */
	public static HorizontalCoordinates ofDeg(double az, double alt) {
		Preconditions.checkInInterval(RightOpenInterval.of(0, 360), az);
		Preconditions.checkInInterval(ClosedInterval.symmetric(180), alt);
		return new HorizontalCoordinates(Angle.ofDeg(az), Angle.ofDeg(alt));
	}
	
	/**
	 * @return azimut in radian
	 */
	public double az() {
		return super.lon();
	}
	
	/**
	 * @return azimut in degres
	 */
	public double azDeg() {
		return super.lonDeg();
	}
	
	/**
	 * @param n : North Octant name
	 * @param e : East Octant name
	 * @param s : South Octant name
	 * @param w : West Octant name
	 * @return in wich octant is the observator 
	 */
	public String azOctantName(String n, String e, String s, String w) {	
		String output = "";

		if(ClosedInterval.of(0, 67.5).contains(azDeg())||ClosedInterval.of(315-22.5, 360).contains(azDeg())) {
			output += n;
		}
		else if(ClosedInterval.of(135-22.5, 225+22.5).contains(azDeg())) {
			output += s;
		}
		
		if(ClosedInterval.of(45-22.5, 135+22.5).contains(azDeg())) {
			output += e;
		}
		else if(ClosedInterval.of(225-22.5, 315+22.5).contains(azDeg())) {
			output += w;
		}
		
		return output;
	}
	
	/**
	 * @return altitude in radian
	 */
	public double alt() {
		return super.lat();
	}
	
	/**
	 * @return altitude in degres
	 */
	public double altDeg() {
		return super.latDeg();
	}
	
	/**
	 * @param that : object we are observing
	 * @return distance between this and that
	 */
	public double angularDistanceTo(HorizontalCoordinates that) {
		return Math.acos(Math.sin(this.alt())*Math.sin(that.alt())+Math.cos(this.alt())*Math.cos(that.alt())*Math.cos(that.az()-this.az()));
	}
	
	/**
	 *@return string with azimut in degres and altitude in degres
	 */
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "(az=%.4f°, alt=%.4f°)", azDeg(), altDeg());
	}
}
