package ch.epfl.rigel.astronomy;

import java.util.List;

import ch.epfl.rigel.Preconditions;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class Asterism {
	
	List<Star> stars;

	/**
	 * create a Asterism from a list of stars
	 * @param stars : list of stars
	 */
	public Asterism(List<Star> stars){
		Preconditions.checkArgument(!stars.isEmpty());
		this.stars = List.copyOf(stars);
	}
	
	/**
	 * @return list of stars contained in the asterism
	 */
	public List<Star> stars() {
		return stars;
	}
}
