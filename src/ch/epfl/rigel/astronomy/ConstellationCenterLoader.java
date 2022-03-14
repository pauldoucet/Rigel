package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.epfl.rigel.astronomy.StarCatalogue.Builder;
import ch.epfl.rigel.astronomy.StarCatalogue.Loader;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

public enum ConstellationCenterLoader implements Loader {

	INSTANCE();
	
	private final static int RA_START_INDEX = 0;
	private final static int RA_END_INDEX = 10;
	private final static int DEC_START_INDEX = 11;
	private final static int DEC_END_INDEX = 21;
	private final static int ABREV_START_INDEX = 33;
	private final static int ABREV_END_INDEX = 37;

	@Override
	public void load(InputStream inputStream, Builder builder) throws IOException {
		BufferedReader stream = new BufferedReader(new InputStreamReader(inputStream));
		String s = "";
		
		while((s = stream.readLine()) != null) {
			double ra = Angle.normalizePositive(Angle.ofHr(Double.parseDouble(s.substring(RA_START_INDEX, RA_END_INDEX))));
			
			double dec = Angle.ofDeg(Double.parseDouble((s.substring(DEC_START_INDEX, DEC_END_INDEX))));
			
			String abrev = s.substring(ABREV_START_INDEX);
			
			builder.setCenter(abrev, EquatorialCoordinates.of(ra, dec));
			
		}
		
	}
	
	
	
}
