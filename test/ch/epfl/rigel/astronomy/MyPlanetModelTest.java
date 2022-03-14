package ch.epfl.rigel.astronomy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

class MyPlanetModelTest {
	
	private static double accuracy = 1e-4;

	@Test
	public void mercruryAtWorksOnBookExample() {
		var when = ZonedDateTime.of(LocalDate.of(
				2003, 11, 22),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var X = PlanetModel.MERCURY.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(253.9297583), 
				Angle.ofDeg(-2.044057468)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void mercruryAtWorksOnKnownValues() {
		var when = ZonedDateTime.of(LocalDate.of(
				2015, 7, 2),
				LocalTime.of(13, 16, 9),
				ZoneOffset.UTC);
		var X = PlanetModel.MERCURY.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(80.25827829), 
				Angle.ofDeg(-1.85560366)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void venusAtWorksOnBookExample() {
		var when = ZonedDateTime.of(LocalDate.of(
				2003, 11, 22),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var X = PlanetModel.VENUS.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(263.7826232), 
				Angle.ofDeg(-1.087689847)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void venusAtWorksOnKnownValues() {
		var when = ZonedDateTime.of(LocalDate.of(
				2015, 7, 2),
				LocalTime.of(13, 16, 9),
				ZoneOffset.UTC);
		var X = PlanetModel.VENUS.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(142.4822494), 
				Angle.ofDeg(0.358099274)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void earthAtWorksOnBookExample() {
		var when = ZonedDateTime.of(LocalDate.of(
				2003, 11, 22),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var X = PlanetModel.EARTH.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(0), 
				Angle.ofDeg(0)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	
	}
	
	@Test
	public void earthAtWorksOnKnownValues() {
		var when = ZonedDateTime.of(LocalDate.of(
				2015, 7, 2),
				LocalTime.of(13, 16, 9),
				ZoneOffset.UTC);
		var X = PlanetModel.EARTH.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(0), 
				Angle.ofDeg(0)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	
	}
	
	@Test
	public void marsAtWorksOnBookExample() {
		var when = ZonedDateTime.of(LocalDate.of(
				2003, 11, 22),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var X = PlanetModel.MARS.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(345.675492), 
				Angle.ofDeg(-1.273448386)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void marsAtWorksOnKnownValues() {
		var when = ZonedDateTime.of(LocalDate.of(
				2015, 7, 2),
				LocalTime.of(13, 16, 9),
				ZoneOffset.UTC);
		var X = PlanetModel.MARS.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(95.58918211), 
				Angle.ofDeg(0.766781614)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void jupiterAtWorksOnBookExample() {
		var when = ZonedDateTime.of(LocalDate.of(
				2003, 11, 22),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var X = PlanetModel.JUPITER.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(166.31051), 
				Angle.ofDeg(1.036465596)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);
	
	}
	
	@Test
	public void jupiterAtWorksOnKnownValues() {
		var when = ZonedDateTime.of(LocalDate.of(
				2015, 7, 2),
				LocalTime.of(13, 16, 9),
				ZoneOffset.UTC);
		var X = PlanetModel.JUPITER.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(142.0021264), 
				Angle.ofDeg(0.863068801)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void saturnAtWorksOnBookExample() {
		var when = ZonedDateTime.of(LocalDate.of(
				2003, 11, 22),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var X = PlanetModel.SATURN.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(102.5396787), 
				Angle.ofDeg(-0.726196376)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void saturnAtWorksOnKnownValues() {
		var when = ZonedDateTime.of(LocalDate.of(
				2015, 7, 2),
				LocalTime.of(13, 16, 9),
				ZoneOffset.UTC);
		var X = PlanetModel.SATURN.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(239.1946992), 
				Angle.ofDeg(2.090583863)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void uranusAtWorksOnBookExample() {
		var when = ZonedDateTime.of(LocalDate.of(
				2003, 11, 22),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var X = PlanetModel.URANUS.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(249.4207263), 
				Angle.ofDeg(0.051058562)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void uranusAtWorksOnKnownValues() {
		var when = ZonedDateTime.of(LocalDate.of(
				2015, 7, 2),
				LocalTime.of(13, 16, 9),
				ZoneOffset.UTC);
		var X = PlanetModel.URANUS.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(300.1424868), 
				Angle.ofDeg(-0.576784512)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void neptuneAtWorksOnBookExample() {
		var when = ZonedDateTime.of(LocalDate.of(
				2003, 11, 22),
				LocalTime.of(0, 0),
				ZoneOffset.UTC);
		var X = PlanetModel.NEPTUNE.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(310.7273246), 
				Angle.ofDeg(-0.019304902)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
	
	@Test
	public void neptuneAtWorksOnKnownValues() {
		var when = ZonedDateTime.of(LocalDate.of(
				2015, 7, 2),
				LocalTime.of(13, 16, 9),
				ZoneOffset.UTC);
		var X = PlanetModel.NEPTUNE.at(Epoch.J2010.daysUntil(when), 
				new EclipticToEquatorialConversion(when));
		var eclToEqu = new EclipticToEquatorialConversion(when);
		var Y = eclToEqu.apply(EclipticCoordinates.of(
				Angle.ofDeg(339.6606942), 
				Angle.ofDeg(-0.792051537)));
		assertEquals(Y.ra(), X.equatorialPos().ra(),accuracy);
		assertEquals(Y.dec(), X.equatorialPos().dec(),accuracy);	}
}