package ch.epfl.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;

class MyStereographicProjectionTest {

	@Test
	public void circleCenterForParallelWorksOnKnownValues() {
		
	}
	
	@Test
	public void circleRadiusForParallelWorksOnKnownValues() {
		
	}
	
	@Test
	public void applyToAngleWorksOnKnownValues() {
		
	}
	
	@Test
	public void applyWoksOnKnownValues() {
		
	}
	
	@Test
	public void inverseApplyWorksOnKnownValues() {
		
	}
	
	@Test
	public void hashCodeThrowUOE(){
		var C = new StereographicProjection(HorizontalCoordinates.of(0, 0));
		assertThrows(UnsupportedOperationException.class, () -> {
			C.hashCode();
		});
	}
	
	@Test
	public void equalsThrowsUOE() {
		var C = new StereographicProjection(HorizontalCoordinates.of(0, 0));
		assertThrows(UnsupportedOperationException.class, () -> {
			C.equals(new Object());
		});
	}
	
	/*@Test
	public void toStringWorksOnKnownValues() {
		var C = new StereographicProjection(HorizontalCoordinates.of(34.8, -1000));
		assertEquals("StereographicProjection (az=50.3, alt=-1000)", C.toString());
	}*/

}
