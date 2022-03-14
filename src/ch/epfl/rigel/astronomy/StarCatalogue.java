package ch.epfl.rigel.astronomy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ch.epfl.rigel.astronomy.Constellation.ConstellationBuilder;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * @author Paul Doucet (316442)
 *
 */
public final class StarCatalogue {
	
	private List<Star> stars;
	private Map<Asterism, List<Integer>> asterisms = new HashMap<Asterism, List<Integer>>();
	private List<Constellation> constellations;

	/**
	 * @param stars : list of stars
	 * @param asterisms : list of asterisms
	 */
	public StarCatalogue(List<Star> stars, List<Asterism> asterisms, List<Constellation> constellations) {
		/**
		 * check that every stars contained in the list of asterism is contained in the list of stars
		 */
		for(Asterism a : asterisms) {
			for(Star s : a.stars()) {
				if(! stars.contains(s)) {
					throw new IllegalArgumentException();
				}
			}
		}
		this.stars = List.copyOf(stars);
		/**
		 * put the values and keys in the map
		 */
		for(Asterism a : asterisms) {
			List<Integer> index = new ArrayList<Integer>();
			for(Star s : a.stars()) {
				index.add(stars.indexOf(s));
			}
			this.asterisms.put(a, index);
		}
		this.asterisms = Collections.unmodifiableMap(this.asterisms);
		
		this.constellations = Collections.unmodifiableList(constellations);
		
	}
	
	/**
	 * @return stars
	 */
	public List<Star> stars() {
		return stars;
	}
	
	/**
	 * @return asterisms
	 */
	public Set<Asterism> asterisms() {
		return asterisms.keySet();
	}
	
	public List<Constellation> constellations(){
		return constellations;
	}
	
	/**
	 * @param asterism : asterism
	 * @return the stars indices in the catalogue
	 */
	public List<Integer> asterismIndices(Asterism asterism) {
		return asterisms.get(asterism);
	}
	
	public final static class Builder {

		private List<Star> stars;
		private List<Asterism> asterisms;
		private List<Constellation> constellations;
		private Map<String, ConstellationBuilder> constellationsMap;
		
		public Builder() {
			this.stars = new ArrayList<Star>();
			this.asterisms = new ArrayList<Asterism>();
			this.constellations = new ArrayList<Constellation>();
			this.constellationsMap = new TreeMap<String, ConstellationBuilder>();
		}
		
		public Builder addStar(Star star) {
			stars.add(star);
			return this;
		}
		
		public List<Star> stars() {
			return List.copyOf(stars);
		}
		
		public Builder addAsterism(Asterism asterism) {
			asterisms.add(asterism);
			return this;
		}
		
		public List<Asterism> asterisms() {
			return List.copyOf(asterisms);
		}
		
		public Builder addVertices(String abrev, EquatorialCoordinates vertice) {
			if(constellationsMap.containsKey(abrev)) {
				constellationsMap.get(abrev).addVertice(vertice);
			}
			else {
				constellationsMap.put(abrev, new ConstellationBuilder().addVertice(vertice));
			}
			return this;
		}
		
		public Builder setCenter(String abrev, EquatorialCoordinates center) {
			if(constellationsMap.containsKey(abrev)) {
				constellationsMap.get(abrev).setCenter(center);
			}
			else {
				constellationsMap.put(abrev, new ConstellationBuilder().setCenter(center));
			}
			return this;
		}
		
		public Builder setName(String abrev, String name) {
			if(constellationsMap.containsKey(abrev)) {
				constellationsMap.get(abrev).setName(name);
			}
			else {
				constellationsMap.put(abrev, new ConstellationBuilder().setName(name));
			}
			return this;
		}
		
		public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException{
			loader.load(inputStream, this);
			return this;
		}
		
		public StarCatalogue build() {
			for(String s : constellationsMap.keySet()) {
				constellations.add(constellationsMap.get(s).build());
			}
			return new StarCatalogue(stars, asterisms, constellations);
		}
	}
	
	public interface Loader {
		void load(InputStream inputStream, Builder builder) throws IOException;
	}
}
