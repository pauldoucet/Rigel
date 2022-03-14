package ch.epfl.rigel.astronomy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

class MySunModelTest {
	
	@Test
	public void atWorksOnBookExample() {
		var S = SunModel.SUN;
		var Time1 = ZonedDateTime.of(
				LocalDate.of(2003, 7, 27), 
				LocalTime.of(0, 0, 0),
				ZoneOffset.UTC);
		var EclToEqu = new EclipticToEquatorialConversion(Time1);		
		var X1 = S.at(Epoch.J2010.daysUntil(Time1), 
				new EclipticToEquatorialConversion(Time1));	
		var Y1 = EclToEqu.apply(EclipticCoordinates.of(Angle.ofDeg(123.5806048), 0));
		Assertions.assertEquals(Y1.ra(), X1.equatorialPos().ra(), 1e-4);
		Assertions.assertEquals(Y1.dec(), X1.equatorialPos().dec(), 1e-4);
	
	}

	@Test
	public void atWorksOnKnownValues() {
		var S = SunModel.SUN;
		var Time2 = ZonedDateTime.of(
				LocalDate.of(2015, 3, 2), 
				LocalTime.of(12, 8, 1),
				ZoneOffset.UTC);
		var EclToEqu = new EclipticToEquatorialConversion(Time2);
		var X2 = S.at(Epoch.J2010.daysUntil(Time2), 
				new EclipticToEquatorialConversion(Time2));
		var Y2 = EclToEqu.apply(EclipticCoordinates.of(Angle.ofDeg(341.5749401), 0));	
		Assertions.assertEquals(Y2.ra(), X2.equatorialPos().ra(), 1e-4);
		Assertions.assertEquals(Y2.dec(), X2.equatorialPos().dec(), 1e-4);
	}

}
