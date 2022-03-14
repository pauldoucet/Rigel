package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.epfl.rigel.math.Angle;

public class MyEclipticCoordinatesTest {

	@Test
	public void toStringWorksOnKnownValues(){
		EclipticCoordinates G = EclipticCoordinates.of(Angle.ofDeg(6),Angle.ofDeg(1));
		Assertions.assertEquals("(λ=6.0000°, β=1.0000°)", G.toString());
		G = EclipticCoordinates.of(Angle.ofDeg(0),Angle.ofDeg(4));
		Assertions.assertEquals("(λ=0.0000°, β=4.0000°)", G.toString());
		G = EclipticCoordinates.of(Angle.ofDeg(2.1987),Angle.ofDeg(-8.1901));
		Assertions.assertEquals("(λ=2.1987°, β=-8.1901°)", G.toString());
	}

}
