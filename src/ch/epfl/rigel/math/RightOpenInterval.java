package ch.epfl.rigel.math;

import static ch.epfl.rigel.Preconditions.checkArgument;

import java.util.Locale;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class RightOpenInterval extends Interval{
	
	private RightOpenInterval(double low, double high) {
		super(low,high);
	}
	
	/**
	 * @param low : lower bound of the interval
	 * @param high : upper bound of the interval
	 * @return a right open interval
	 */
	public static RightOpenInterval of(double low, double high) {
		checkArgument(low<high);
		return new RightOpenInterval(low,high);
	}
	
	/**
	 * @param size : size of the interval
	 * @return a symmetric right open interval
	 */
	public static RightOpenInterval symmetric(double size) {
		checkArgument(size>0);
		return new RightOpenInterval(-size/2,size/2);
	}
	
	/**
	 * @return if the interval contains v
	 */
	@Override
	public boolean contains(double v) {
		return (v>=low()&&v<high());
	}
	
	/**
	 * @param v : a real number
	 * @return v reduced
	 */
	public double reduce(double v) {
		return low() + floorMod(v-low(),high()-low());
	}
	
	private static double floorMod(double x,double y) {
		return x - y * Math.floor(x / y);
	}
	
	/**
	 * @return a string of the interval
	 */
	public String toString() {
		return String.format(Locale.ROOT, "[%.4f,%.4f[", super.low(), super.high());
	}
	
}