package ch.epfl.rigel.astronomy;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Paul Doucet (316442)
 *
 */
public class MyEpochTest {
	
	@Test
	public void daysUntilWorksOnKnownValues() {
		Assertions.assertEquals(2.25, Epoch.J2000.daysUntil(ZonedDateTime.of(
				LocalDate.of(2000, Month.JANUARY, 3), LocalTime.of(18, 0),
				ZoneOffset.UTC)), 1e-4);
		Assertions.assertEquals(-2.25, Epoch.J2000.daysUntil(ZonedDateTime.of(
				LocalDate.of(1999, Month.DECEMBER, 30), LocalTime.of(6, 0),
				ZoneOffset.UTC)), 1e-4);
		Assertions.assertEquals(0, Epoch.J2000.daysUntil(ZonedDateTime.of(
				LocalDate.of(2000, Month.JANUARY, 1), LocalTime.of(12, 0),
				ZoneOffset.UTC)), 1e-4);
		
		Assertions.assertEquals(2.25, Epoch.J2010.daysUntil(ZonedDateTime.of(
				LocalDate.of(2010, Month.JANUARY, 2), LocalTime.of(6, 0),
				ZoneOffset.UTC)), 1e-4);
		Assertions.assertEquals(-2.25, Epoch.J2010.daysUntil(ZonedDateTime.of(
				LocalDate.of(2009, Month.DECEMBER, 28), LocalTime.of(18, 0),
				ZoneOffset.UTC)), 1e-4);
		Assertions.assertEquals(0, Epoch.J2010.daysUntil(ZonedDateTime.of(
				LocalDate.of(2009, Month.DECEMBER, 31), LocalTime.of(0, 0),
				ZoneOffset.UTC)), 1e-4);
		
		ZonedDateTime a = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 30),
                LocalTime.of(15, 0),
                ZoneOffset.UTC);
        ZonedDateTime b = ZonedDateTime.of(
                LocalDate.of(2020, Month.MARCH, 20),
                LocalTime.of(0, 0),
                ZoneOffset.UTC);
        ZonedDateTime c = ZonedDateTime.of(
                LocalDate.of(2006, Month.JUNE, 16),
                LocalTime.of(18, 13),
                ZoneOffset.UTC);
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);
        ZonedDateTime e = ZonedDateTime.of(
                LocalDate.of(1999, Month.DECEMBER, 6),
                LocalTime.of(23, 3),
                ZoneOffset.UTC);

        assertEquals(1306.125, Epoch.J2000.daysUntil(a));
        assertEquals(7383.5, Epoch.J2000.daysUntil(b));
        assertEquals(2358.259028, Epoch.J2000.daysUntil(c), 1e-6);
        assertEquals(2.25, Epoch.J2000.daysUntil(d));
        assertEquals(-25.539583, Epoch.J2000.daysUntil(e), 1e-6);

        assertEquals(-2345.375, Epoch.J2010.daysUntil(a), 1e-6);
        assertEquals(3732, Epoch.J2010.daysUntil(b));
        assertEquals(-1293.240972, Epoch.J2010.daysUntil(c), 1e-6);
        assertEquals(-3649.25, Epoch.J2010.daysUntil(d), 1e-6);
        assertEquals(-3677.039583, Epoch.J2010.daysUntil(e), 1e-6);
	}
	
	@Test
	public void julianCenturiesUntilWorksOnKnownValues() {
		Assertions.assertEquals(0.201646, Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(
				LocalDate.of(2020, Month.MARCH, 1), LocalTime.of(15, 13, 57),
				ZoneOffset.UTC)), 1e-5);	
		/*Assertions.assertEquals(1, Epoch.J2000.julianCenturiesUntil(ZonedDateTime.of(
				LocalDate.of(2001, Month.JANUARY, 1), LocalTime.of(18, 0),
				ZoneOffset.UTC)), 1e-4);*/
	}

}
