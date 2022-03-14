package ch.epfl.rigel.coordinates;

import java.time.ZonedDateTime;
import java.util.function.Function;

import ch.epfl.rigel.astronomy.SiderealTime;
import ch.epfl.rigel.math.Angle;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates>{
	
	private final double cosPhi;
	private final double sinPhi;
	private final double offset;
	
	/**
	 * @param when : zoned date time
	 * @param where : position in geographic coordinates
	 */
	public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where){
		double phi = where.lat();
		cosPhi = Math.cos(phi);
		sinPhi = Math.sin(phi);
		offset = SiderealTime.local(when, where);
	}

	/**
	 * @param equ : equatorial coordinates to convert
	 * @return conversion of (equ) in horizontal coordinates
	 */
	@Override
	public HorizontalCoordinates apply(EquatorialCoordinates equ) {
		double delta= equ.dec();
		
		double H = offset - equ.ra();
		
		double h = Math.asin(Math.sin(delta) * sinPhi + 
				Math.cos(delta) * cosPhi * Math.cos(H));
		
		double A = Math.atan2(-Math.cos(delta) * cosPhi * Math.sin(H), 
				Math.sin(delta) - sinPhi * Math.sin(h));
		
		A = Angle.normalizePositive(A);
		
		return HorizontalCoordinates.of(A, h);
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
