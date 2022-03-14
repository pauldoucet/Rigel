package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.epfl.rigel.math.Angle;

public class MyEquatorialCoordinatesTest {

	@Test
	public void toStringWorksOnKnownValues(){
		EquatorialCoordinates G = EquatorialCoordinates.of(Angle.ofHr(6),Angle.ofDeg(1));
		Assertions.assertEquals("(ra=6.0000h, dec=1.0000°)", G.toString());
		G = EquatorialCoordinates.of(Angle.ofHr(0),Angle.ofDeg(4));
		Assertions.assertEquals("(ra=0.0000h, dec=4.0000°)", G.toString());
		G = EquatorialCoordinates.of(Angle.ofHr(2.1987),Angle.ofDeg(-8.1901));
		Assertions.assertEquals("(ra=2.1987h, dec=-8.1901°)", G.toString());
	}

}
