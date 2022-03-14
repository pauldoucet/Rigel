/**
 * 
 */
package ch.epfl.rigel.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

/**
 * @author Paul Doucet (316442)
 *
 */
class MyBlackBodyColorTest {
	
	@Test
	public void colorTemperatureWorksOnKnownValues() throws IOException {
		var C1 = BlackBodyColor.colorForTemperature(1000);
		var C2 = BlackBodyColor.colorForTemperature(1099);
		var C3 = BlackBodyColor.colorForTemperature(8713);
		var C4 = BlackBodyColor.colorForTemperature(20302);
		var C5 = BlackBodyColor.colorForTemperature(27890);
		var C6 = BlackBodyColor.colorForTemperature(31234);
		var C7 = BlackBodyColor.colorForTemperature(38900);
		var C8 = BlackBodyColor.colorForTemperature(40099);
		
		assertEquals(C1, Color.web("#ff3800"));
		assertEquals(C2, Color.web("#ff3800"));
		assertEquals(C3, Color.web("#d9e3ff"));
		assertEquals(C4, Color.web("#a8c4ff"));
		assertEquals(C5, Color.web("#a0c0ff"));
		assertEquals(C6, Color.web("#9ebeff"));
		assertEquals(C7, Color.web("#9bbcff"));
		assertEquals(C8, Color.web("#9bbcff"));			
	}
}
