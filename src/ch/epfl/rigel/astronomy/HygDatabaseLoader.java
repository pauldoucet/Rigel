package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.epfl.rigel.astronomy.StarCatalogue.Builder;
import ch.epfl.rigel.astronomy.StarCatalogue.Loader;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * @author Paul Doucet (316442)
 *
 */
public enum HygDatabaseLoader implements Loader{
	INSTANCE();

	/**
	 * @param inputStream : input stream to read
	 * @param builder : star catalogue builder
	 */
	@Override
	public void load(InputStream inputStream, Builder builder) throws IOException {
		BufferedReader stream = new BufferedReader(new InputStreamReader(inputStream));
		String b = "";
		
		stream.readLine();
		while((b = stream.readLine()) != null) {
			String[] infos = b.split(",");
			
			int hipparcosId = infos[Column.HIP.ordinal()].isEmpty() ? 0 : Integer.parseInt(infos[Column.HIP.ordinal()]); //HIP COL
			
			String name;
			
			if(! infos[Column.PROPER.ordinal()].isEmpty()) {
				name = infos[Column.PROPER.ordinal()];
			}
			else {
				name = (infos[Column.BAYER.ordinal()].isEmpty()) 
						? "? " + infos[Column.CON.ordinal()] 
						: infos[Column.BAYER.ordinal()] + " " + infos[Column.CON.ordinal()];
			}
			
			double ra = Double.parseDouble(infos[Column.RARAD.ordinal()]);
			double dec = Double.parseDouble(infos[Column.DECRAD.ordinal()]);
			
			EquatorialCoordinates equ = EquatorialCoordinates.of(ra, dec);
			
			float mag = infos[Column.MAG.ordinal()].isEmpty() ? 0 : Float.parseFloat(infos[Column.MAG.ordinal()]);
			float colorIndex = infos[Column.CI.ordinal()].isEmpty() ? 0 : Float.parseFloat(infos[Column.CI.ordinal()]);
			
			builder.addStar(new Star(hipparcosId, name, equ, mag, colorIndex));
		}
	}
	
	private enum Column {
		ID, HIP, HD, HR, GL, BF, PROPER, RA, DEC, DIST, PMRA, PMDEC,
		RV, MAG, ABSMAG, SPECT, CI, X, Y, Z, VX, VY, VZ,
		RARAD, DECRAD, PMRARAD, PMDECRAD, BAYER, FLAM, CON,
		COMP, COMP_PRIMARY, BASE, LUM, VAR, VAR_MIN, VAR_MAX;
	}
}
