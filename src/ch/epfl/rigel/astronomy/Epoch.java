package ch.epfl.rigel.astronomy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Paul Doucet (316442)
 * 
 * Astronomic epoch 
 *
 */
public enum Epoch {
	
	J2000(ZonedDateTime.of(LocalDate.of(2000, Month.JANUARY, 1),
			LocalTime.NOON,
			ZoneOffset.UTC)),
	J2010(ZonedDateTime.of(LocalDate.of(2009, Month.DECEMBER, 31),
			LocalTime.MIDNIGHT,
			ZoneOffset.UTC));
	
	private final ZonedDateTime d;
	private final static double NANOSECONDS_PER_DAYS = 3600*24*1000*1e6;
	
	private Epoch(ZonedDateTime d) {
		this.d = d;
	}
	
	/**
	 * @param when : zoned date time
	 * @return days between the epoch and the instant when
	 */
	public double daysUntil(ZonedDateTime when) {
		double a = d.until(when, ChronoUnit.NANOS);
		return a/(NANOSECONDS_PER_DAYS);
	}
	
	/**
	 * @param when : zoned date time
	 * @return julian centuries between the epoch and the instant when
	 */
	public double julianCenturiesUntil(ZonedDateTime when) {
		return daysUntil(when)/36525;
	}
}
