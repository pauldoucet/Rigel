package ch.epfl.rigel.astronomy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;

class MySideralTimeTest {

	@Test
	public void greenwichWorksOnKnownValues() {
        Assertions.assertEquals(Angle.ofHr(4.668119327), SiderealTime.greenwich(ZonedDateTime.of(
        		LocalDate.of(1980, Month.APRIL, 22),
        		LocalTime.of(14, 36, 51, (int) 6.7e8), 
        		ZoneOffset.UTC)), 1e-6);		
		
		Assertions.assertEquals(Angle.ofDeg(33.6715399087), SiderealTime.greenwich(ZonedDateTime.of(
				LocalDate.of(2020, Month.MARCH, 1),
				LocalTime.of(15, 35, 5),
				ZoneOffset.UTC)), 1e-6);

	}
	
	@Test
	public void localTimeWorksOnKnownValues() {
		Assertions.assertEquals(Angle.ofHr((24*60+5.23)/3600), SiderealTime.local(ZonedDateTime.of(
				LocalDate.of(1980, Month.APRIL, 22),
				LocalTime.of(14, 36, 51, (int) 6.7e8),
				ZoneOffset.UTC),
				GeographicCoordinates.ofDeg(-64,0)), 1e-6);
	}

}
