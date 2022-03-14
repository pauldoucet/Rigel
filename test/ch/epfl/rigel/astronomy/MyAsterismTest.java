package ch.epfl.rigel.astronomy;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyAsterismTest {

	@Test
	public void constructorThrowIAE() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			var A = new Asterism(new ArrayList<Star>());
		});
	}
	

}
