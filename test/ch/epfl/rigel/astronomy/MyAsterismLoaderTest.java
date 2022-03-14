package ch.epfl.rigel.astronomy;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class MyAsterismLoaderTest {
	private static final String HYG_CATALOGUE_NAME =
			"/asterisms.txt";
	private static final String STAR_CATALOGUE_NAME =
			"/hygdata_v3.csv";

	@Test
    void asterismDatabaseIsCorrectlyInstalled() throws IOException {
		try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {
			assertNotNull(hygStream);
		}
	}

	/*@Test
	void asterismLoaderLoadsCoorectly() throws IOException{
		try(InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {
			try(InputStream starStream = getClass().getResourceAsStream(STAR_CATALOGUE_NAME)) {
				StarCatalogue catalogue = new StarCatalogue.Builder()
						.loadFrom(starStream, HygDatabaseLoader.INSTANCE)
						.loadFrom(hygStream, AsterismLoader.INSTANCE)
						.build();
				var asterisms = new ArrayList<Asterism>(catalogue.asterisms());
				var A1 = new Asterism(List.of(
						MyStarCatalogueTest.RIGEL, 
						MyStarCatalogueTest.BETELGEUSE,
						new Star(27366, ),
						new Star(26727),
						new Star(29426),
						new Star(28614),
						new Star(28716)));
			}
		}
	}*/
}
