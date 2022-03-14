package ch.epfl.rigel.astronomy;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.test.TestRandomizer;

class MyStarTest {

	@Test
	public void constructFailsWithWrongArgument(){
		assertThrows(IllegalArgumentException.class, () -> {
			var S = new Star(-1, "star", 
					EquatorialCoordinates.of(0, 0), 0, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			var S = new Star(0, "star", 
					EquatorialCoordinates.of(0, 0), 0, (float)-0.50001);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			var S = new Star(-1, "star", 
					EquatorialCoordinates.of(0, 0), 0, (float)0.50001);
		});
	}
	
	@Test
	public void constructWorksWithValidArgument() {
		var rng = TestRandomizer.newRandom();
		for(int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
			var hipparcosId = rng.nextInt(0, 1000);
			var raDeg = rng.nextDouble(0, 359);
			var decDeg = rng.nextDouble(-90, 90);
			var magnitude = (float)rng.nextDouble(-1000, 1000);
			var colorIndex = (float)rng.nextDouble(-0.5, 0.5);
			
			var S = new Star(hipparcosId, "star", EquatorialCoordinates.of(
					Angle.ofDeg(raDeg), Angle.ofDeg(decDeg)), magnitude, colorIndex);
		}
	}
	
	@Test
	public void hasAngularSizeOfZero() {
		var S = new Star(2, "star", EquatorialCoordinates.of(
				Angle.ofDeg(0), Angle.ofDeg(5)), 5, 0);
		assertEquals(0, S.angularSize());
	}
	
	@Test
	public void hipparcosIdWorsOnKnownValues() {
		var rng = TestRandomizer.newRandom();
		for(int i = 0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
			var hipparcosId = rng.nextInt(0, 1000);
			var raDeg = rng.nextDouble(0, 359);
			var decDeg = rng.nextDouble(-90, 90);
			var magnitude = (float)rng.nextDouble(-1000, 1000);
			var colorIndex = (float)rng.nextDouble(-0.5, 0.5);
			
			var S = new Star(hipparcosId, "star", EquatorialCoordinates.of(
					Angle.ofDeg(raDeg), Angle.ofDeg(decDeg)), magnitude, colorIndex);
			assertEquals(hipparcosId, S.hipparcosId());
		}
	}
	
	/*@Test
	public void colorTemperatureWorksOnKnownValues() {
			var S1 = new Star(0, "star", EquatorialCoordinates.of(
					Angle.ofDeg(0), Angle.ofDeg(0)), 0, (float)-0.016429);
			var S2 = new Star(0, "star", EquatorialCoordinates.of(
					Angle.ofDeg(0), Angle.ofDeg(0)), 0, (float)0.042968);
			var S3 = new Star(0, "star", EquatorialCoordinates.of(
					Angle.ofDeg(0), Angle.ofDeg(0)), 0, (float)0.3277503);
			assertEquals(9000, S1.colorTemperature(), 1000);
			assertEquals(8672, S2.colorTemperature(), 1000);
			assertEquals(7258, S3.colorTemperature(), 1000);	
	}*/

}
