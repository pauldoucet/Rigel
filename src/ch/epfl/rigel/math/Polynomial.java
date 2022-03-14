package ch.epfl.rigel.math;
import static ch.epfl.rigel.Preconditions.checkArgument;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class Polynomial {
	
	private final double[] coefficients;
	
	private Polynomial(double coefficientN, double...coefficients) {
		this.coefficients = new double[coefficients.length + 1];
		this.coefficients[0] = coefficientN;
		for(int i = 0; i < coefficients.length; ++i) {
			this.coefficients[i + 1] = coefficients[i];
		}
	}
	
	/**
	 * @param coefficientN : higher coefficient
	 * @param coefficients : coefficients
	 * @return a polynomial
	 */
	public static Polynomial of(double coefficientN, double... coefficients) {
		checkArgument(coefficientN!=0);
		return new Polynomial(coefficientN, coefficients);
	}
	
	/**
	 * @param x : a real number
	 * @return return the value of P(x)
	 */
	public double at(double x) {
		double sum = 0;
		for(int i = 0; i < coefficients.length; ++i) {
			sum = sum * x + coefficients[i];
		}
		return sum;
	}
	
	/**
	 * @return a string of the polynomial
	 */
	public String toString() {
		StringBuilder string = new StringBuilder("");
		
		for(int i = 0; i < coefficients.length; ++i) {
			if((coefficients[i] > 0.1 || coefficients[i] < -0.1)) {
				if(coefficients[i] > 0 && i != 0) {
					string.append("+");
				}
				if(i != coefficients.length - 2 && coefficients[i] < 0 && Math.abs(coefficients[i])<1.1&&Math.abs(coefficients[i])>0.9) {
					string.append("-");
				}
				if(Math.abs(coefficients[i]) > 1.1 || Math.abs(coefficients[i]) < 0.9 || i == coefficients.length-1) {
					string.append(coefficients[i]);
				}
				if(i == coefficients.length - 2) {
					string.append("x");
				}
				else if(i < coefficients.length - 2) {
					string.append("x^").append((coefficients.length - i - 1));
				}				
			}
		}
		return string.toString();
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
