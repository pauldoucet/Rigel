package ch.epfl.rigel.math;

import static ch.epfl.rigel.Preconditions.checkArgument;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class Angle {
	
	// Tau : 2 * PI
	public final static double TAU = 2*Math.PI;
	
	private final static double RAD_PER_SEC = TAU/(360*3600);
	private final static double DEG_PER_HR = 15;
	private final static double SEC_PER_DEG = 3600;
	private final static double HR_PER_RAD = 24/TAU;
	private final static RightOpenInterval INTERVAL = RightOpenInterval.of(0,TAU);
	
	private Angle() {}
	
	/**
	 * @param rad : angle in radian
	 * @return normalisation of (rad) in [0,2pi[
	 */
	public static double normalizePositive(double rad) {
		return  INTERVAL.reduce(rad);
	}
	
	/**
	 * @param sec : angle in seconds
	 * @return angle in radian
	 */
	public static double ofArcsec(double sec) {
		return sec*RAD_PER_SEC;
	}
	
	/**
	 * @param deg : angle in degrees
	 * @param min : angle in minutes degrees
	 * @param sec : angle in second degrees
	 * @return angle in radian
	 */
	public static double ofDMS(int deg, int min, double sec) {
		checkArgument((deg >= 0)
				&& (0 <= min && min < 60)
				&& (0 <= sec && sec < 60));
		return ofArcsec(deg*SEC_PER_DEG+min*60+sec);
	}
	
	/**
	 * @param deg : angle in degrees
	 * @return conversion of (deg) in radian
	 */
	public static double ofDeg(double deg) {
		return Math.toRadians(deg);
	}
	
	/**
	 * @param rad : angle in radian
	 * @return conversion of (rad) in degrees
	 */
	public static double toDeg(double rad) {
		return Math.toDegrees(rad);
	}
	
	/**
	 * @param hr : angle in hour
	 * @return conversion of (hr) in radian
	 */
	public static double ofHr(double hr) {
		return ofDeg(hr*DEG_PER_HR);
	}
	
	/**
	 * @param rad : angle in radian
	 * @return conversion of (rad) in hour
	 */
	public static double toHr(double rad) {
		return rad*HR_PER_RAD;
	}
}
