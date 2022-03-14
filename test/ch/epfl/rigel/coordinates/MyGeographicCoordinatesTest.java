package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyGeographicCoordinatesTest {

	@Test
	public void constructorThrowIAE() {
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			GeographicCoordinates.ofDeg(180, 0);
		});
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			GeographicCoordinates.ofDeg(185, 0);
		});
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			GeographicCoordinates.ofDeg(-181, 0);
		});
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			GeographicCoordinates.ofDeg(0, -91);
		});
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			GeographicCoordinates.ofDeg(0, 91);
		});
	}
	
	@Test
	public void isValidLonDegWorksOnKnownValues() {
		Assertions.assertEquals(true, GeographicCoordinates.isValidLonDeg(-180));
		Assertions.assertEquals(true, GeographicCoordinates.isValidLonDeg(179));
		Assertions.assertEquals(true, GeographicCoordinates.isValidLonDeg(-1.87));
		
		Assertions.assertEquals(false, GeographicCoordinates.isValidLonDeg(-181));
		Assertions.assertEquals(false, GeographicCoordinates.isValidLonDeg(-2194));
		Assertions.assertEquals(false, GeographicCoordinates.isValidLonDeg(180));
	}
	
	@Test
	public void isValidLatDegWorksOnKnownValues() {
		Assertions.assertEquals(true, GeographicCoordinates.isValidLatDeg(-90));
		Assertions.assertEquals(true, GeographicCoordinates.isValidLatDeg(90));
		Assertions.assertEquals(true, GeographicCoordinates.isValidLatDeg(-1.87));
		
		Assertions.assertEquals(false, GeographicCoordinates.isValidLatDeg(-91.1289));
		Assertions.assertEquals(false, GeographicCoordinates.isValidLatDeg(-95));
		Assertions.assertEquals(false, GeographicCoordinates.isValidLatDeg(90.215));
	}
	
	@Test
	public void lonWorksOnKnownValues() {
		GeographicCoordinates G = GeographicCoordinates.ofDeg(90, 1);
		Assertions.assertEquals(Math.PI/2,G.lon(), 1e-4);
	}

	@Test
	public void latWorksOnKnownValues() {
		GeographicCoordinates G = GeographicCoordinates.ofDeg(1, 90);
		Assertions.assertEquals(Math.PI/2,G.lat(), 1e-4);
	}

	@Test
	public void latDegWorksOnKnownValue() {
		for (int i = -90; i < 90; ++i) {
			GeographicCoordinates G = GeographicCoordinates.ofDeg(0, i);
			Assertions.assertEquals(i, G.latDeg(), 1e-4);
		}
	}

	@Test
	public void lonDegWorksOnKnownValue() {
		for (int i = -180; i < 179; ++i) {
			GeographicCoordinates G = GeographicCoordinates.ofDeg(i, 0);
			Assertions.assertEquals(i, G.lonDeg(), 1e-4);
		}
	}

	@Test
	public void toStringWorksOnKnownValues(){
		GeographicCoordinates G = GeographicCoordinates.ofDeg(6,1);
		Assertions.assertEquals("(lon=6.0000°, lat=1.0000°)", G.toString());
		G = GeographicCoordinates.ofDeg(-7,4);
		Assertions.assertEquals("(lon=-7.0000°, lat=4.0000°)", G.toString());
		G = GeographicCoordinates.ofDeg(2.1987,-8.1901);
		Assertions.assertEquals("(lon=2.1987°, lat=-8.1901°)", G.toString());
	}
}
