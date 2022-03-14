package ch.epfl.rigel.coordinates;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyStereographicProjectionTest {

	@Test
	public void circleCenterForParallelWorksOnKnownValues() {
	    HorizontalCoordinates h1 = HorizontalCoordinates.of(Math.PI/4, Math.PI/6);
	    HorizontalCoordinates center1 = HorizontalCoordinates.of(0,0);
	    StereographicProjection s = new StereographicProjection(center1);
	    CartesianCoordinates a1 = s.circleCenterForParallel(h1);
	    assertEquals(0, a1.x(), 1e-10);
	    assertEquals(2, a1.y(), 1e-10);
	}
	
	@Test
	public void circleRadiusForParallelWorksOnKnownValues() {
		
	}
	
	@Test
	public void applyToAngleWorksOnKnownValues() {
	    HorizontalCoordinates center2 = HorizontalCoordinates.of(Math.PI/4, Math.PI/4);
	    StereographicProjection e2 = new StereographicProjection(center2);
	    double z = e2.applyToAngle(Math.PI/2);
	}
	
	@Test
	public void inverseApplyWorksOnKnownValues() {
	    HorizontalCoordinates h1 = HorizontalCoordinates.of(Math.PI/4, Math.PI/6);
	    HorizontalCoordinates center1 = HorizontalCoordinates.of(0,0);
	    StereographicProjection e = new StereographicProjection(center1);
	    double p = Math.sqrt(6);
	    CartesianCoordinates a1 = CartesianCoordinates.of(p/(4+p), 2/(4+p));
	    HorizontalCoordinates c1 = e.inverseApply(a1);
	    assertEquals(h1.az(), c1.az(), 1e-8);
	    assertEquals(h1.alt(), c1.alt(), 1e-8);
	}
	
	@Test
	void applyWorksOnKnownValues(){
	    HorizontalCoordinates h1 = HorizontalCoordinates.of(Math.PI/4, Math.PI/6);
	    HorizontalCoordinates center1 = HorizontalCoordinates.of(0,0);
	    StereographicProjection e = new StereographicProjection(center1);
	    double p = Math.sqrt(6);
	    CartesianCoordinates a1 = CartesianCoordinates.of(p/(4+p), 2/(4+p));
	    CartesianCoordinates c1 = e.apply(h1);
	    assertEquals(a1.x(), c1.x(), 1e-8);
	    assertEquals(a1.y(), c1.y(), 1e-8);

	    HorizontalCoordinates h2 = HorizontalCoordinates.of(Math.PI/2, Math.PI/2);
	    HorizontalCoordinates center2 = HorizontalCoordinates.of(Math.PI/4, Math.PI/4);
	    StereographicProjection e2 = new StereographicProjection(center2);
	    double p2 = Math.sqrt(2);
	    CartesianCoordinates a2 = CartesianCoordinates.of(0, p2/(2+p2));
	    CartesianCoordinates c2 = e2.apply(h2);
	    assertEquals(a2.x(), c2.x(), 1e-8);
	    assertEquals(a2.y(), c2.y(), 1e-8);
	}
	
	@Test
	public void hashCodeThrowUOE() {
		var S = new StereographicProjection(HorizontalCoordinates.of(1, 1));
		assertThrows(UnsupportedOperationException.class, () -> {
			S.hashCode();
		});
	}
	
	@Test
	public void equalsThrowsUOE() {
		var S = new StereographicProjection(HorizontalCoordinates.of(1, 1));
		assertThrows(UnsupportedOperationException.class, () -> {
			S.equals(new Object());
		});
	}
}
