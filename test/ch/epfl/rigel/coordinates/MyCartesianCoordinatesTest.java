package ch.epfl.rigel.coordinates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import ch.epfl.test.TestRandomizer;

class MyCartesianCoordinatesTest {

	@Test
	public void hashCodeThrowUOE(){
		var C = CartesianCoordinates.of(0, 0);
		assertThrows(UnsupportedOperationException.class, () -> {
			C.hashCode();
		});
	}
	
	@Test
	public void equalsThrowsUOE() {
		var C = CartesianCoordinates.of(0, 0);
		assertThrows(UnsupportedOperationException.class, () -> {
			C.equals(new Object());
		});
	}
	
	@Test
	public void toStringWorksOnKnownValues() {
		var rng = TestRandomizer.newRandom();
		for(int i=0; i < TestRandomizer.RANDOM_ITERATIONS; ++i) {
			var x = rng.nextDouble(-1000, 1000);
			var y = rng.nextDouble(-1000, 1000);
			var C = CartesianCoordinates.of(x, y);
			assertEquals(String.format(Locale.ROOT, "(x=%.4f, y=%.4f)", x, y), C.toString());
		}
	}

}
