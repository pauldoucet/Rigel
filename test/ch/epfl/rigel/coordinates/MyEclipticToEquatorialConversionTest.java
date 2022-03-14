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

class MyEclipticToEquatorialConversionTest {

	@Test
	public void HashCodeThrowUOE() {
		var E = new EclipticToEquatorialConversion(ZonedDateTime.now());
		assertThrows(UnsupportedOperationException.class, () -> {
			E.hashCode();
		});
	}
	
	@Test
	public void EqualsThrowsUOE() {
		var E = new EclipticToEquatorialConversion(ZonedDateTime.now());
		assertThrows(UnsupportedOperationException.class, () -> {
			E.equals(new Object());
		});
	}
	
	/*@Test
	public void applyWorksOnKnownValues() {
		
		var E2 = new EclipticToEquatorialConversion(ZonedDateTime.of(
				LocalDate.of(2009, Month.JULY, 6),
				LocalTime.of(0, 0, 0),
				ZoneOffset.UTC
				));
		
		var Ecl2 = EclipticCoordinates.of(Angle.ofDMS(139, 41, 10),
				Angle.ofDMS(4, 52, 31));
		assertEquals(Angle.ofHr((9*3600+34*60+53.4)/3600), 
				E2.apply(Ecl2).ra());
		assertEquals(Angle.ofHr((19*3600+32*60+8.52)/3600), 
				E2.apply(Ecl2).dec());
		
		var E = new EclipticToEquatorialConversion(ZonedDateTime.of(
				LocalDate.of(2020, Month.MARCH, 4),
				LocalTime.of(16, 18, 3),
				ZoneId.of("Europe/Paris")
				));
		var Ecl = EclipticCoordinates.of(Angle.ofDeg(33.7112), Angle.ofDeg(-10.3901));
		assertEquals(2.33234,E.apply(Ecl).raHr(), 1e-4);
		assertEquals(2.96248,E.apply(Ecl).decDeg(), 1e-4);

	}*/
}
