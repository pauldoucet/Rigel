package ch.epfl.rigel.coordinates;

import java.util.Locale;

import javafx.geometry.Point2D;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class CartesianCoordinates {
	
	private final double x;
	private final double y;
	
	private CartesianCoordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @param x : absiss
	 * @param y : ordinate
	 * @return cartesian coordinates
	 */
	public static CartesianCoordinates of(double x, double y) {
		return new CartesianCoordinates(x, y);
	}
	
	/**
	 * @param point : Point2D
	 * @return cartesian coordinates
	 */
	public static CartesianCoordinates of(Point2D point) {
		return new CartesianCoordinates(point.getX(), point.getY());
	}
	
	/**
	 * @param that : cartesian coordinates
	 * @return distance from this to that
	 */
	public double distanceTo(CartesianCoordinates that) {
		double d = Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
		return d;
	}
	
	/**
	 * @return absiss
	 */
	public double x() {
		return x;
	}
	
	/**
	 * @return ordinate
	 */
	public double y() {
		return y;
	}
	
	public CartesianCoordinates transform(Transform affine) {
		return CartesianCoordinates.of(affine.transform(x, y));
	}
	
	public CartesianCoordinates inverseTransform(Transform affine) throws NonInvertibleTransformException {
		return CartesianCoordinates.of(affine.inverseTransform(x, y));
	}
	
	/*@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}*/
	
	/*@Override
	public boolean equals(Object o) {
		CartesianCoordinates c = (CartesianCoordinates)o;
		return this.x() == c.x() && this.y() == c.y();
	}*/
	
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "(x=%.4f, y=%.4f)", x, y);
	}

}
