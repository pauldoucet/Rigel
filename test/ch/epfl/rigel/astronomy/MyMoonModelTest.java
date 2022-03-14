package ch.epfl.rigel.astronomy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

class MyMoonModelTest {

	@Test
	public void MoonModelWorksOnBookExample() {
		var when = ZonedDateTime.of(
				LocalDate.of(2003, 9, 1),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var conversion = new EclipticToEquatorialConversion(when);
		var M = MoonModel.MOON.at(Epoch.J2010.daysUntil(when),
				conversion);
		var equ = conversion.apply(EclipticCoordinates.of(
				Angle.ofDeg(214.862515), Angle.ofDeg(1.716257)));
		assertEquals(equ.ra(), M.equatorialPos().ra(), 1e-6);
		assertEquals(equ.dec(), M.equatorialPos().dec(), 1e-6);
	}
	
	@Test
	public void hasMagnitudeOfZero() {
		var when = ZonedDateTime.of(
				LocalDate.of(2003, 9, 1),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var conversion = new EclipticToEquatorialConversion(when);
		var M = MoonModel.MOON.at(Epoch.J2010.daysUntil(when),
				conversion);
		assertEquals(0, M.magnitude());
	}
	
	@Test
	public void angularSizeWorksOnKnownValues() {
		var when1 = ZonedDateTime.of(
				LocalDate.of(2003, 9, 1),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var when2 = ZonedDateTime.of(
				LocalDate.of(2001, 4, 23),
				LocalTime.of(13, 3),
				ZoneOffset.UTC);
		var when3 = ZonedDateTime.of(
				LocalDate.of(2011, 1, 5),
				LocalTime.of(1, 20, 4),
				ZoneOffset.UTC);
		
		var conversion1 = new EclipticToEquatorialConversion(when1);
		var conversion2 = new EclipticToEquatorialConversion(when2);
		var conversion3 = new EclipticToEquatorialConversion(when3);
		
		var M1 = MoonModel.MOON.at(Epoch.J2010.daysUntil(when1),
				conversion1);
		var M2 = MoonModel.MOON.at(Epoch.J2010.daysUntil(when2),
				conversion2);
		var M3 = MoonModel.MOON.at(Epoch.J2010.daysUntil(when3),
				conversion3);
		
		assertEquals(Angle.ofDMS(0, 32, 0), M1.angularSize(), 1e-2);
		assertEquals(Angle.ofDMS(0, 31, 0), M2.angularSize(), 1e-2);
		assertEquals(Angle.ofDMS(0, 30, 0), M3.angularSize(), 1e-2);
	}
	
	@Test
	public void moonPhaseWorksOnBookExample() {
		var when = ZonedDateTime.of(
				LocalDate.of(2003, 9, 1),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var conversion = new EclipticToEquatorialConversion(when);
		var M = MoonModel.MOON.at(Epoch.J2010.daysUntil(when),
				conversion);
		assertEquals(0.22, M.phase(), 1e-2);
	}
	
	@Test
	public void moonPhaseWorksOnKnownValues() {
		var when1 = ZonedDateTime.of(
				LocalDate.of(1995, 1, 5),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var when2 = ZonedDateTime.of(
				LocalDate.of(2001, 4, 23),
				LocalTime.of(13, 3),
				ZoneOffset.UTC);
		var when3 = ZonedDateTime.of(
				LocalDate.of(2011, 1, 5),
				LocalTime.of(23, 20, 4),
				ZoneOffset.UTC);
		
		var conversion1 = new EclipticToEquatorialConversion(when1);
		var conversion2 = new EclipticToEquatorialConversion(when2);
		var conversion3 = new EclipticToEquatorialConversion(when3);
		
		var M1 = MoonModel.MOON.at(Epoch.J2010.daysUntil(when1),
				conversion1);
		var M2 = MoonModel.MOON.at(Epoch.J2010.daysUntil(when2),
				conversion2);
		var M3 = MoonModel.MOON.at(Epoch.J2010.daysUntil(when3),
				conversion3);
		
		assertEquals(0.16, M1.phase(), 1e-2);
		assertEquals(0, M2.phase(), 1e-2);
		assertEquals(0.03, M3.phase(), 1e-2);
	}
}
