package ch.epfl.rigel.astronomy;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * A contellation
 * @author Paul Doucet (316442)
 *
 */
public class Constellation {
	
	private final String name;
	private List<EquatorialCoordinates> vertices;
	private final EquatorialCoordinates center;

	/**
	 * @param name : name of the constellation in french
	 * @param vertices : equatorial coordinates of the vertices (bounds) of the constellation
	 * @param center : equatorial coordinates of the barycenter of the constellation
	 */
	public Constellation(String name, List<EquatorialCoordinates> vertices, EquatorialCoordinates center) {
		this.name = name;
		this.center = center;
		this.vertices = Collections.unmodifiableList(vertices);
	}
	
	/**
	 * @return the name of the constellation in french
	 */
	public String name() {
		return name;
	}
	
	/**
	 * @return the equatorial coordinates of the vertices (bounds) of the constellation
	 */
	public List<EquatorialCoordinates> vertices() {
		return vertices;
	}
	
	/**
	 * @return the equatorial coordinates of the barycenter of the constellation
	 */
	public EquatorialCoordinates center() {
		return center;
	}
	
	@Override
	public String toString() {
		return String.format("Name:%s Center:%s Vertices:%s", name, center.toString(), vertices.toString());
	}
	
	/**
	 * @author Paul Doucet (316442)
	 * 
	 * constellation builder
	 *
	 */
	public final static class ConstellationBuilder {
		private String name;
		private List<EquatorialCoordinates> vertices;
		private EquatorialCoordinates center;
		
		public ConstellationBuilder() {
			vertices = new ArrayList<EquatorialCoordinates>();
		}
		
		public ConstellationBuilder addVertice(EquatorialCoordinates vertice) {
			vertices.add(vertice);
			return this;
		}
		
		public ConstellationBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public ConstellationBuilder setCenter(EquatorialCoordinates center) {
			this.center = center;
			return this;
		}
		
		public Constellation build() {
			return new Constellation(name, vertices, center);
		}
		
		@Override
		public String toString() {
			return String.format("Name:%s Center:%s Vertices:%s", name, center.toString(), vertices.toString());
		}
		
	}

}
