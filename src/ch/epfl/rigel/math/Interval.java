package ch.epfl.rigel.math;

/**
 * @author Paul Doucet (316442)
 *
 */
public abstract class Interval{
	private final double a;
	private final double b;
	
	protected Interval(double a,double b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * @return lower bound of the interval
	 */
	public double low() {
		return a;
	}
	
	/**
	 * @return upper bound of the interval
	 */
	public double high() {
		return b;
	}
	
	/**
	 * @return size of the interval
	 */
	public double size() {
		return b-a;
	}
	
	/**
	 * @param v : a real number
	 * @return if v is contained in the interval
	 */
	public abstract boolean contains(double v);
	
	@Override
	final public int hashCode(){
		throw new UnsupportedOperationException();
	}
	
	@Override
	final public boolean equals(Object obj) {
		throw new UnsupportedOperationException();
	}
}