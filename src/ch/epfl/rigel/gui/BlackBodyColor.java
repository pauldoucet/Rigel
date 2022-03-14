/**
 * 
 */
package ch.epfl.rigel.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.paint.Color;

/**
 * @author Paul Doucet (316442)
 *
 */
public class BlackBodyColor {
	
	private final static int START_INDEX_TEMP = 1;
	private final static int END_INDEX_TEMP = 6;
	private final static int START_INDEX_10DEG = 10;
	private final static int END_INDEX_10DEG = 15;
	private final static int START_INDEX_RGB = 80;
	private final static int END_INDEX_RGB = 87;
	private static Map<Integer, Color> colors = new HashMap<Integer, Color>();

	private BlackBodyColor() {}
	
	/**
	 * @param temperature : temperature in K°
	 * @return the color corresponding to the black body's color
	 */
	public static Color colorForTemperature(int temperature) {
		Preconditions.checkInInterval(ClosedInterval.of(1000, 40099), temperature);
		
		// closest multiple of 10 
		temperature = temperature - (temperature % 100);
		
		return colors.get(temperature);
	}
	
	public static void initializeTemperatures() {
		try {
			BufferedReader stream = new BufferedReader(new InputStreamReader(BlackBodyColor.class
					.getResourceAsStream("/bbr_color.txt")));
			
			String s = "";
			while((s = stream.readLine()) != null) {
				// ignoring commentary lines
				if(s.charAt(0) != '#') {
					// ignoring 2deg lines
					if(s.substring(START_INDEX_10DEG, END_INDEX_10DEG).replaceAll("\\s", "").equals("10deg")) {
						Color color = Color.web(s.substring(START_INDEX_RGB, END_INDEX_RGB));
						int temperature = Integer.parseInt(s.substring(START_INDEX_TEMP, END_INDEX_TEMP).replaceAll("\\s", ""));
							colors.put(temperature, color);
					}
				}
			}
		stream.close();		
		}
		catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
}