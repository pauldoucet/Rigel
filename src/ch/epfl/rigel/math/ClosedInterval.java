package ch.epfl.rigel.math;

import static ch.epfl.rigel.Preconditions.checkArgument;
import ch.epfl.rigel.math.Interval;

import java.util.Locale;


/**
 * @author Paul Doucet (316442)
 *
 */
public final class ClosedInterval extends Interval{
	
	private ClosedInterval(double low, double high){
		super(low,high);
	}
	
	/**
	 * @param low : lower bound of the interval
	 * @param high : upper bound of the interval
	 * @return a closed interval
	 */
	public static ClosedInterval of(double low, double high) {
		checkArgument(low<high);
		return new ClosedInterval(low,high);
	}
	
	/**
	 * @param size : size of the interval
	 * @return a symmetric closed interval of size : (size)
	 */
	public static ClosedInterval symmetric(double size) {
		checkArgument(size>0);
		return new ClosedInterval(-(size/2),size/2);
	}
	
	/**
	 *@return if (v) is contained in the interval
	 */
	@Override
	public boolean contains(double v) {
		return (v>=low()&&v<=high());
	}
	
	/**
	 * @param v : a real number
	 * @return the clip of v in the interval
	 */
	public double clip(double v) {
		if(v>high()) {
			return high();
		}
		else if(v<low()) {
			return low();
		}
		else {
			return v;
		}
	}
	
	/**
	 * @return a string with the lower and upper bound of the interval
	 */
	public String toString() {
		return String.format(Locale.ROOT, "[%.4f,%.4f]",low(),high());
	}
}