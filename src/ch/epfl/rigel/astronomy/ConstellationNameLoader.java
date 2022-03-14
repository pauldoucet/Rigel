package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.epfl.rigel.astronomy.StarCatalogue.Builder;
import ch.epfl.rigel.astronomy.StarCatalogue.Loader;

public enum ConstellationNameLoader implements Loader {
	INSTANCE();
	
	private final static int NAME_COLUMN = 0;
	private final static int ABREV_COLUMN = 1;

	@Override
	public void load(InputStream inputStream, Builder builder) throws IOException {
		BufferedReader stream = new BufferedReader(new InputStreamReader(inputStream));
		String s;
		
		while((s = stream.readLine()) != null) {
			String[] infos = s.split(",");
			
			String name = infos[NAME_COLUMN];
			String abrev = infos[ABREV_COLUMN];
			
			builder.setName(abrev, name);
			
		}
		
	}

}
