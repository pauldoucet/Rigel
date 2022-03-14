package ch.epfl.rigel.astronomy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

class MyStarCatalogueTest {
	/*
	public final static Star RIGEL = new Star(24436, "Rigel", 
			EquatorialCoordinates.of(1.372430369, -0.14314563),
			0.18f, -0.03f);
	public final static Star BETELGEUSE = new Star(27989, "Betelgeuse", 
			EquatorialCoordinates.of(1.549729118,0.129277632),
			0.45f, 1.5f);
	public final static Star BELLATRIX = new Star(25336, "Bellatrix",
			EquatorialCoordinates.of(1.418651834, 0.11082321),
			1.64f, -0.224f);
	public final static Star ORI1 = new Star(25336, "Ori",
			EquatorialCoordinates.of(-0.00000475, 1.5173738927528553),
			21f, -0.9f);
	public final static Star ORI2 = new Star(26727, "Ori",
			EquatorialCoordinates.of(0.00001879, 0.00000215),
			18.0f, -0.9f);
	public final static Star ORI2 = new Star(26727, "Ori",
			EquatorialCoordinates.of(0.00001879, 0.00000215),
			18.0f, -0.9f);

	@Test
	public void constructorWorksOnValidArgument() {
		var L1 = Arrays.asList(RIGEL);
		var L2 = Arrays.asList(RIGEL, BETELGEUSE);
		var L3 = Arrays.asList(RIGEL, BETELGEUSE, BELLATRIX);
		
		var A1 = new ArrayList<Asterism>();
		var A2 = new ArrayList<Asterism>();
		var A3 = new ArrayList<Asterism>();
		
		A1.add(new Asterism(L1));
		A2.add(new Asterism(Arrays.asList(RIGEL, BETELGEUSE)));
		A3.add(new Asterism(Arrays.asList(RIGEL, BETELGEUSE)));
		A3.add(new Asterism(Arrays.asList(BELLATRIX)));
		
		new StarCatalogue(L1, A1);
		new StarCatalogue(L2, A2);
		new StarCatalogue(L3, A3);
	}
	
	@Test
	public void constructorThrowIAE() {
		var L1 = Arrays.asList(RIGEL, BELLATRIX);
		var L2 = Arrays.asList(RIGEL);
		var L3 = Arrays.asList(RIGEL);
		
		var A1 = new ArrayList<Asterism>();
		var A2 = new ArrayList<Asterism>();
		var A3 = new ArrayList<Asterism>();
		
		A1.add(new Asterism(Arrays.asList(BETELGEUSE)));
		A2.add(new Asterism(Arrays.asList(RIGEL, BELLATRIX)));
		A3.add(new Asterism(Arrays.asList(BETELGEUSE)));
		A3.add(new Asterism(Arrays.asList(BELLATRIX)));
		
		assertThrows(IllegalArgumentException.class, () -> {
			new StarCatalogue(L1, A1);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new StarCatalogue(L2, A2);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new StarCatalogue(L3, A3);
		});
	}
	
	@Test
	public void starsWorksOnKnownValues() {
		var L1 = Arrays.asList(RIGEL);
		var L2 = Arrays.asList(RIGEL, BETELGEUSE);
		var L3 = Arrays.asList(RIGEL, BETELGEUSE, BELLATRIX);
		
		var A1 = new ArrayList<Asterism>();
		var A2 = new ArrayList<Asterism>();
		var A3 = new ArrayList<Asterism>();
		
		A1.add(new Asterism(L1));
		A2.add(new Asterism(Arrays.asList(RIGEL, BETELGEUSE)));
		A3.add(new Asterism(Arrays.asList(RIGEL, BETELGEUSE)));
		A3.add(new Asterism(Arrays.asList(BELLATRIX)));
		
		var S1 = new StarCatalogue(L1, A1);
		var S2 = new StarCatalogue(L2, A2);
		var S3 = new StarCatalogue(L3, A3);
		
		assertEquals(L1, S1.stars());
		assertEquals(L2, S2.stars());
		assertEquals(L3, S3.stars());
	}
	
	@Test
	public void asterismsWorksOnKnownValues() {
		var L1 = Arrays.asList(RIGEL);
		var L2 = Arrays.asList(RIGEL, BETELGEUSE);
		//var L3 = Arrays.asList(RIGEL, BETELGEUSE, BELLATRIX);
		
		var A1 = new ArrayList<Asterism>();
		var A2 = new ArrayList<Asterism>();
		var A3 = new ArrayList<Asterism>();
		
		A1.add(new Asterism(L1));
		A2.add(new Asterism(Arrays.asList(RIGEL, BETELGEUSE)));
		A3.add(new Asterism(Arrays.asList(RIGEL, BETELGEUSE)));
		A3.add(new Asterism(Arrays.asList(BELLATRIX)));
		
		var S1 = new StarCatalogue(L1, A1);
		var S2 = new StarCatalogue(L2, A2);
		//var S3 = new StarCatalogue(L3, A3);
		
		assertEquals(A1, new ArrayList<Asterism>(S1.asterisms()));
		assertEquals(A2, new ArrayList<Asterism>(S2.asterisms()));
		//assertEquals(A3, new ArrayList<Asterism>(S3.asterisms()));
	}
	
	@Test
	public void asterismIndicesWorksOnKnownValues() {
		var L1 = Arrays.asList(RIGEL);
		var L2 = Arrays.asList(RIGEL, BETELGEUSE);
		var L3 = Arrays.asList(RIGEL, BETELGEUSE, BELLATRIX);
		
		var A1 = new ArrayList<Asterism>();
		var A2 = new ArrayList<Asterism>();
		var A3 = new ArrayList<Asterism>();
		
		var a1 = new Asterism(L1);
		var a2 = new Asterism(Arrays.asList(RIGEL, BETELGEUSE));
		var a3 = new Asterism(Arrays.asList(RIGEL, BETELGEUSE));
		var a3Bis = new Asterism(Arrays.asList(BELLATRIX));
		
		A1.add(a1);
		
		A2.add(a2);
		
		A3.add(a3);
		A3.add(a3Bis);
		
		var S1 = new StarCatalogue(L1, A1);
		var S2 = new StarCatalogue(L2, A2);
		var S3 = new StarCatalogue(L3, A3);
		
		assertEquals(S1.asterismIndices(a1), Arrays.asList(0));
		assertEquals(S2.asterismIndices(a2), Arrays.asList(0, 1));
		assertEquals(S3.asterismIndices(a3), Arrays.asList(0, 1));
		assertEquals(S3.asterismIndices(a3Bis), Arrays.asList(2));
	}
	
	@Test
	public void builderAddStarAndStarWorksOnKnownValues() {
		var B1 = new StarCatalogue.Builder()
				.addStar(RIGEL);
		var B2 = new StarCatalogue.Builder()
				.addStar(BETELGEUSE)
				.addStar(BELLATRIX);
		var B3 = new StarCatalogue.Builder()
				.addStar(RIGEL)
				.addStar(BETELGEUSE)
				.addStar(BELLATRIX);
		
		assertEquals(Arrays.asList(RIGEL), B1.stars());
		assertEquals(Arrays.asList(BETELGEUSE, BELLATRIX), B2.stars());
		assertEquals(Arrays.asList(RIGEL, BETELGEUSE, BELLATRIX), B3.stars());
	}
	
	@Test
	public void builderAddAsterismAndAsterismWorksOnKnownValues() {
		var A1 = new Asterism(Arrays.asList(RIGEL));
		var A2 = new Asterism(Arrays.asList(BETELGEUSE, BELLATRIX));
		var A3 = new Asterism(Arrays.asList(RIGEL, BELLATRIX));
		var A3Bis = new Asterism(Arrays.asList(RIGEL, BETELGEUSE));
		
		var B1 = new StarCatalogue.Builder()
				.addStar(BETELGEUSE)
				.addStar(BELLATRIX)
				.addStar(RIGEL)
				.addAsterism(A1)
				.build();
		var B2 = new StarCatalogue.Builder()
				.addStar(BETELGEUSE)
				.addStar(BELLATRIX)
				.addStar(RIGEL)
				.addAsterism(A2)
				.build();
		var B3 = new StarCatalogue.Builder()
				.addStar(RIGEL)
				.addStar(BETELGEUSE)
				.addStar(BELLATRIX)
				.addAsterism(A3)
				.addAsterism(A3Bis);
		
		assertEquals(A1, B1.asterisms());
		assertEquals(Arrays.asList(A2), B2.asterisms());
		assertEquals(Arrays.asList(A3, A3Bis), B3.asterisms());
	}*/
}