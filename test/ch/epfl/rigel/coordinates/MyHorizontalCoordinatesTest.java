package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.epfl.rigel.math.Angle;

public class MyHorizontalCoordinatesTest {

	@Test
	public void constructorOfThrowIAEOnInvalidArguments() {
		HorizontalCoordinates.of(Angle.ofDeg(359.9990), 0);
		HorizontalCoordinates.of(2, -Math.PI/2+0.0001);
		HorizontalCoordinates.of(2, Math.PI/2-0.0001);
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			HorizontalCoordinates.of(Math.PI*2, 0);
		});
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			HorizontalCoordinates.of(Angle.ofDeg(-90.0001), 0);
		});
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			HorizontalCoordinates.of(0, Angle.ofDeg(90.0001));
		});
	}
	
	@Test
	public void constructorOfDegThrowIAEOnInvalidArguments() {
		HorizontalCoordinates.ofDeg(359.9990, 0);
		HorizontalCoordinates.ofDeg(0, 5);
		HorizontalCoordinates.ofDeg(5, -90);
		HorizontalCoordinates.ofDeg(5, 90);
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			HorizontalCoordinates.ofDeg(360, 0);
		});
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			HorizontalCoordinates.ofDeg(-90.0001, 0);
		});
		Assertions.assertThrows(IllegalArgumentException.class, ()->{
			HorizontalCoordinates.ofDeg(0, 90.0001);
		});
	}
	
	@Test
	public void azDegWorksOnKnownValues() {
		for (int i = 0; i < 359; ++i) {
			HorizontalCoordinates G = HorizontalCoordinates.ofDeg(i, 0);
			Assertions.assertEquals(i, G.azDeg(), 1e-4);
		}
	}

	@Test
	public void altDegWorksOnKnownValues() {
		for (int i = -90; i < 90; ++i) {
			HorizontalCoordinates G = HorizontalCoordinates.ofDeg(0, i);
			Assertions.assertEquals(i, G.altDeg(), 1e-4);
		}
	}
	
	@Test
	public void azAndAltWorksOnKnownValues() {
		HorizontalCoordinates G = HorizontalCoordinates.of(1.1453, -1.1498);
		Assertions.assertEquals(1.1453, G.az(), 1e-4);
		Assertions.assertEquals(-1.1498, G.alt(), 1e-4);
	}
	
	@Test
	public void azOctantNameWorksOnKnownValues() {
		HorizontalCoordinates G = HorizontalCoordinates.ofDeg(350, 0);
		Assertions.assertEquals("N", G.azOctantName("N", "E", "S", "W"));
		G = HorizontalCoordinates.ofDeg(22.7, 0);
		Assertions.assertEquals("NE", G.azOctantName("N", "E", "S", "W"));
		G = HorizontalCoordinates.ofDeg(89, 0);
		Assertions.assertEquals("E", G.azOctantName("N", "E", "S", "W"));
		G = HorizontalCoordinates.ofDeg(278, 0);
		Assertions.assertEquals("W", G.azOctantName("N", "E", "S", "W"));
		G = HorizontalCoordinates.ofDeg(359, 0);
		Assertions.assertEquals("N", G.azOctantName("N", "E", "S", "W"));
	}
	
	@Test
	public void angularDistanceToWorksOnKnownValues() {
		HorizontalCoordinates G = HorizontalCoordinates.ofDeg(11.0001, 25.0019);
		Assertions.assertEquals(Angle.ofDeg(116.2232), G.angularDistanceTo(HorizontalCoordinates.ofDeg(233.7383, 23.5026)),1e-4);
		G = HorizontalCoordinates.ofDeg(0, -90);
		Assertions.assertEquals(Angle.ofDeg(135.0981), G.angularDistanceTo(HorizontalCoordinates.ofDeg(30, 45.0981)),1e-4);
		G = HorizontalCoordinates.ofDeg(0, -89.3);
		Assertions.assertEquals(Angle.ofDeg(44.3012), G.angularDistanceTo(HorizontalCoordinates.ofDeg(3.4, -45)),1e-4);
		G = HorizontalCoordinates.ofDeg(0, 89.3);
		Assertions.assertEquals(Angle.ofDeg(134.3012), G.angularDistanceTo(HorizontalCoordinates.ofDeg(3.4, -45)),1e-4);
	}
	
	@Test
	public void toStringWorksOnKnownValues(){
		HorizontalCoordinates G = HorizontalCoordinates.ofDeg(6,1);
		Assertions.assertEquals("(az=6.0000°, alt=1.0000°)", G.toString());
		G = HorizontalCoordinates.ofDeg(4,-7);
		Assertions.assertEquals("(az=4.0000°, alt=-7.0000°)", G.toString());
		G = HorizontalCoordinates.ofDeg(2.1987,-8.1901);
		Assertions.assertEquals("(az=2.1987°, alt=-8.1901°)", G.toString());
	}
}
