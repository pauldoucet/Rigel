package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.rigel.astronomy.StarCatalogue.Builder;
import ch.epfl.rigel.astronomy.StarCatalogue.Loader;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

public enum ConstellationBoundsLoader implements Loader {
	
	INSTANCE();
	
	private static final int RA_START = 0;
	private static final int RA_END = 10;
	private static final int DEC_START = 11;
	private static final int DEC_END = 21;
	private static final int NAME_START = 22;

	@Override
	public void load(InputStream inputStream, Builder builder) throws IOException {
		BufferedReader stream = new BufferedReader(new InputStreamReader(inputStream));
		String s;
		
		while((s = stream.readLine()) != null) {
			
			double ra = Angle.normalizePositive(Angle.ofHr(Double.parseDouble(s.substring(RA_START, RA_END))));
			
			double dec = Angle.ofDeg(Double.parseDouble(s.substring(DEC_START, DEC_END)));
			
			String name = s.substring(NAME_START);
			
			builder.addVertices(name, EquatorialCoordinates.of(ra, dec));
			
		}
	}
}


