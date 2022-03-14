package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.rigel.astronomy.StarCatalogue.Builder;

/**
 * @author Paul Doucet (316442)
 *
 */
public enum AsterismLoader implements StarCatalogue.Loader {
	INSTANCE;

	/**
	 *@param inputStream : csv file input stream
	 *@parma builder : star catalogue builder
	 */
	@Override
	public void load(InputStream inputStream, Builder builder) throws IOException {
		BufferedReader stream = new BufferedReader(new InputStreamReader(inputStream));
		
		String s = "";
		while((s = stream.readLine()) != null) {
			String[] a = s.split(",");
			
			/**
			 * LIST CONTAINING THE STARS OF THE ASTERISM
			 */
			List<Star> stars = new ArrayList<Star>();
			
			/**
			 * FOR EACH OF THE HYPPARCOSID FROM THE FILE, FIND AN EQUIVALENT STAR
			 */
			for(String i : a) {
				for(Star star : builder.stars()) {
					if(Integer.parseInt(i) == star.hipparcosId()) {
						stars.add(star);
					}
				}
			}
			builder.addAsterism(new Asterism(stars));
		}
	}
}
