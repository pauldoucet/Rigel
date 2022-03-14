package ch.epfl.rigel.coordinates;

import java.util.Locale;
import java.util.function.Function;

import ch.epfl.rigel.math.Angle;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates>{

	private final HorizontalCoordinates center;
	
	/**
	 * @param center : center of the stereographic projection
	 */
	public StereographicProjection(HorizontalCoordinates center) {
		this.center = center;
	}
	
	/**
	 * @param hor : horizontal coordinates of a point
	 * @return cartesian coordinates of the center of the circle containing hor and projected in the 2D space 
	 */
	public CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor) {
		double psi = hor.alt();
		double psi1 = center.alt();
		
		double cx = 0;
		double cy = (Math.cos(psi1))/(Math.sin(psi)+Math.sin(psi1));
		
		return CartesianCoordinates.of(cx, cy);
	}
	
	/**
	 * @param parallel : projection of a parallel
	 * @return radius of the circle containing the projection of the parallel also containing hor
	 */
	public double circleRadiusForParallel(HorizontalCoordinates parallel) {
		double phi = parallel.lat();
		double phi1 = center.alt();
		
		double rho = Math.cos(phi)/(Math.sin(phi) + Math.sin(phi1));
		
		return rho;
	}
	
	/**
	 * @param rad : angular size of a sphere
	 * @return projected diameter of a sphere of angular size rad
	 */
	public double applyToAngle(double rad) {
		double d = 2 * Math.tan(rad / 4);
		return d;
	}
	
	/**
	 * @return cartesian coordinates of the projection of the point azAlt
	 */
	@Override
	public CartesianCoordinates apply(HorizontalCoordinates azAlt) {
		double lambdaDelta = azAlt.az() - center.az();
		double psi1 = center.alt();
		double psi = azAlt.alt();
		double cosPsi = Math.cos(psi);
		double sinPsi = Math.sin(psi);
		double d = 1d/(1 + sinPsi * Math.sin(psi1) + cosPsi * Math.cos(psi1) * Math.cos(lambdaDelta));
		
		double x = d * cosPsi * Math.sin(lambdaDelta);
		double y = d * (sinPsi * Math.cos(psi1) - cosPsi * Math.sin(psi1) * Math.cos(lambdaDelta));
	
		return CartesianCoordinates.of(x, y);
	}
	
	/**
	 * @param xy : cartesian coordinates of a point
	 * @return horizontal coordinates of xy
	 */
	public HorizontalCoordinates inverseApply(CartesianCoordinates xy) {
		double phi1 = center.alt();
		double rho = Math.sqrt(Math.pow(xy.x(), 2) + Math.pow(xy.y(), 2));
		double sinC = (2 * rho) / (Math.pow(rho, 2) + 1);
		double cosC = (1 - Math.pow(rho, 2)) / (Math.pow(rho, 2) + 1);
		double lambda0 = center.az();
		double lambda = 0;
		double phi = 0;
		
		if((xy.x() == 0) && (xy.y() == 0)) {
			lambda = lambda0;
			phi = phi1;
		}
		else {
			lambda = Angle.normalizePositive(Math.atan2(xy.x() * sinC,
					rho * Math.cos(phi1) * cosC- xy.y() * Math.sin(phi1) * sinC) + lambda0);
			phi = Math.asin(cosC * Math.sin(phi1) + (xy.y() * sinC * Math.cos(phi1))/rho);	
		}
		
		return HorizontalCoordinates.of(lambda, phi);
	}
	
	/**
	 *
	 */
	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 *
	 */
	/*@Override
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}*/
	
	/**
	 *
	 */
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "Stereographic Projection center : (az=%.4f, alt=%.4f)", center.az(), center.alt());
	}

}
