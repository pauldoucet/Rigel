package ch.epfl.rigel.coordinates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import ch.epfl.rigel.math.Angle;

class MyEquatorialToHorizontalConversionTest {

	@Test
	public void HashCodeThrowUOE() {
		var E = new EquatorialToHorizontalConversion(ZonedDateTime.now(),
				GeographicCoordinates.ofDeg(5, -2));
		assertThrows(UnsupportedOperationException.class, () -> {
			E.hashCode();
		});
	}
	
	@Test
	public void EqualsThrowsUOE() {
		var E = new EquatorialToHorizontalConversion(ZonedDateTime.now(),
				GeographicCoordinates.ofDeg(179, -25));
		assertThrows(UnsupportedOperationException.class, () -> {
			E.equals(new Object());
		});
	}
/*
	@Test
	public void applyWorksOnKnownValues() {
		var E = new EquatorialToHorizontalConversion(ZonedDateTime.of(
				LocalDate.of(2020, Month.MARCH, 6),
				LocalTime.of(17, 0, 0),
				ZoneOffset.UTC
				),
				GeographicCoordinates.ofDeg(45, 52));
		var Equ = EquatorialCoordinates.of(Angle.ofHr(((double)5*3600+51*60+44)/3600), Angle.ofDMS(23, 13, 10));
		assertEquals(Angle.toDeg(Angle.ofDMS(283, 16, 15.7)),E.apply(Equ).azDeg(), 1e-4);
		assertEquals(Angle.toDeg(Angle.ofDMS(19, 20, 3.64)),E.apply(Equ).altDeg(), 1e-4);
	}
*/
}
