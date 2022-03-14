package ch.epfl.rigel.astronomy;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class SiderealTime {
	
	private final static double HOURS_PER_MILLIS = 1d / (3600 * 1000);
	private static Polynomial poly = Polynomial.of(0.000025862, 2400.051336, 6.697374558);
	private final static double C = 1.002737909;
	
	/**
	 * @param when : zoned date time we want to get the sideral time from
	 * @return the greenwitch sideral time 
	 */
	public static double greenwich(ZonedDateTime when) {
		when = when.withZoneSameInstant(ZoneOffset.UTC); //handles time zones
		double T = Epoch.J2000.julianCenturiesUntil(when.truncatedTo(ChronoUnit.DAYS));

		double t = (double)when.truncatedTo(ChronoUnit.DAYS).until(when, ChronoUnit.MILLIS) * HOURS_PER_MILLIS;
		//double t = (((double)when.getNano()/1e9)+(double)when.getSecond()+(double)when.getMinute()*60+(double)when.getHour()*3600)/3600;
		//double s0 = 0.000025862 * Math.pow(T, 2) + 2400.051336 * T + 6.697374558;
		double s0 = poly.at(T);
		double s1 = C * t;
		
		double sg = s0 + s1 /*- (double)when.getOffset().getTotalSeconds() / 3600*/;
		
		sg = Angle.ofHr(sg);
		
		return Angle.normalizePositive(sg);
	}
	
	/**
	 * @param when : zoned date time we want to get the local sideral time from
	 * @param where : geographic coordinates of the observator
	 * @return local sideral time
	 */
	public static double local(ZonedDateTime when, GeographicCoordinates where) {
		return Angle.normalizePositive(greenwich(when) + where.lon());
	}
}
