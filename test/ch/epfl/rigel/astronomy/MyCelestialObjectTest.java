package ch.epfl.rigel.astronomy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

class MyCelestialObjectTest {

	/*@Test
	public void constructorThrowIAE() {
		assertThrows(IllegalArgumentException.class, () -> {
			var C = new CelestialObject(null, EquatorialCoordinates.of(1, -1),
					2, 1);
		});
	}*/
	
	/*@Test
    void CelestialObject(){
        CelestialObject celestial = new CelestialObject("Arion", EquatorialCoordinates.of(Angle.ofDeg(55.7),
        		Angle.ofDeg(19.7)), 0.4f, 1.2f);
        assertEquals("Arion", celestial.info());
        assertEquals("Arion", celestial.name());
    }*/
	
    @Test
    public void moonTest(){
        Moon moon = new Moon(EquatorialCoordinates.of(Angle.ofDeg(55.7),
                Angle.ofDeg(19.7)), 37.5f, -1, 0.3752f);
        assertEquals("Lune", moon.name());
        assertEquals("Lune (37.5%)", moon.info());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.7),
                Angle.ofDeg(19.7)).dec(), moon.equatorialPos().dec());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.7),
                Angle.ofDeg(19.7)).ra(), moon.equatorialPos().ra()); //checking equatorial position
        assertThrows(IllegalArgumentException.class, () -> {new Moon(EquatorialCoordinates.of(Angle.ofDeg(55.7),
                Angle.ofDeg(19.7)), 37.5f, -1, -0.1f); });
    }

    @Test
    public void sunTest(){

        //tests variés pour sun et moon
        Sun sun = new Sun(EclipticCoordinates.of(Angle.ofDeg(53), Angle.ofDeg(38)),
                EquatorialCoordinates.of(Angle.ofDeg(55.7),Angle.ofDeg(24)),
                0.4f, 5.f);
        assertEquals("Soleil", sun.info());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.7),
                Angle.ofDeg(24)).dec(), sun.equatorialPos().dec());
        assertEquals(EquatorialCoordinates.of(Angle.ofDeg(55.7),
                Angle.ofDeg(19.7)).ra(), sun.equatorialPos().ra()); //checking equatorial position
        assertEquals(5.f, sun.meanAnomaly());
        assertEquals(-26.7f, sun.magnitude());

        //test pour eclipticPos throws un null
        assertThrows(NullPointerException.class, () -> { new Sun(null,
                EquatorialCoordinates.of(Angle.ofDeg(55.7),Angle.ofDeg(24)),
                0.4f, 5.f); });

    }

}
