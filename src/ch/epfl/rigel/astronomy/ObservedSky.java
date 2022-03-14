package ch.epfl.rigel.astronomy;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialToHorizontalConversion;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
/**
 * @author Paul Doucet (316442)
 *
 */
public class ObservedSky {

	private Sun sun;
	private Moon moon;
	private List<Planet> planets = new ArrayList<Planet>();
	private List<Star> stars;
	private Map<CartesianCoordinates, CelestialObject> coordinates;
	private final StarCatalogue catalogue;
	private CartesianCoordinates moonPosition;
	private CartesianCoordinates sunPosition;
	private double[] planetsPositions;
	private double[] starsPositions;
	private EquatorialToHorizontalConversion equatorialToHorizontalConversion;
	private List<double[]> constellationsPosition;
	private double[] constellationsCenter;
	
	/**
	 * @param when : zoned date time
	 * @param where : geographic coordinates of the observator
	 * @param stereo : stereographic projection
	 * @param catalogue : star catalogue
	 */
	public ObservedSky(ZonedDateTime when, GeographicCoordinates where, StereographicProjection stereo,
			StarCatalogue catalogue) {
		this.catalogue = catalogue;
		coordinates = new HashMap<CartesianCoordinates, CelestialObject>();

		// Computation of days since J2010 and the conversions
		double daysSinceJ2010 = Epoch.J2010.daysUntil(when);
		double daysSinceJ2000 = Epoch.J2000.daysUntil(when);
		EclipticToEquatorialConversion eclipticToEquatorialConversion = new EclipticToEquatorialConversion(when);
		equatorialToHorizontalConversion = new EquatorialToHorizontalConversion(when, where);
		
		moonAndSunInitialization(daysSinceJ2010, eclipticToEquatorialConversion, 
				equatorialToHorizontalConversion, stereo);
		
		planetsInitialization(daysSinceJ2010, eclipticToEquatorialConversion, 
				equatorialToHorizontalConversion, stereo);
		
		starsInitialization(daysSinceJ2010, equatorialToHorizontalConversion, stereo);
		
		constellationsInit(daysSinceJ2000, equatorialToHorizontalConversion, stereo);
	}
	
	private void moonAndSunInitialization(double daysSinceJ2010, 
			EclipticToEquatorialConversion eclipticToEquatorialConversion,
			EquatorialToHorizontalConversion equatorialToHorizontalConversion,
			StereographicProjection stereo) {
		// Moon and sun computation
		sun = SunModel.SUN.at(daysSinceJ2010, eclipticToEquatorialConversion);
		moon = MoonModel.MOON.at(daysSinceJ2010, eclipticToEquatorialConversion);
		sunPosition = stereo.apply(sunHorizontalPosition());
		moonPosition = stereo.apply(equatorialToHorizontalConversion.apply(moon.equatorialPos()));
		coordinates.put(sunPosition, sun);
		coordinates.put(moonPosition, moon);		
	}
	
	private void planetsInitialization(double daysSinceJ2010,
			EclipticToEquatorialConversion eclipticToEquatorialConversion,
			EquatorialToHorizontalConversion equatorialToHorizontalConversion,
			StereographicProjection stereo) {
		// Planets computation
		planetsPositions = new double[planets.size() * 2];
		for(int i = 0; i < planets.size(); ++i) {
			PlanetModel p = PlanetModel.ALL.get(i);
			planets.add(p.at(daysSinceJ2010, eclipticToEquatorialConversion));
			CartesianCoordinates s = stereo.apply(equatorialToHorizontalConversion.apply(
					planets.get(i).equatorialPos()));
			planetsPositions[i * 2]     = s.x();
			planetsPositions[i * 2 + 1] = s.y();
			coordinates.put(s, planets.get(i));
		}
		planets = Collections.unmodifiableList(new ArrayList<Planet>(planets));		
	}
	
	private void starsInitialization(double daysSinceJ2010,
			EquatorialToHorizontalConversion equatorialToHorizontalConversion,
			StereographicProjection stereo) {
		// Stars computation
		stars = Collections.unmodifiableList(new ArrayList<Star>(catalogue.stars()));
		starsPositions = new double[stars.size() * 2];
		for(int i = 0; i < stars.size(); ++i) {
			CartesianCoordinates s = stereo.apply(equatorialToHorizontalConversion.apply(
					stars.get(i).equatorialPos()));
			starsPositions[i * 2]     = s.x();
			starsPositions[i * 2 + 1] = s.y();
			coordinates.put(s, stars.get(i));
		}
	}
	
	private void constellationsInit(double daysSinceJ2010, 
			EquatorialToHorizontalConversion equatorialToHorizontalConversion,
			StereographicProjection stereo) {
		constellationsPosition = new ArrayList<double[]>();
		constellationsCenter = new double[catalogue.constellations().size() * 2];
		for(int i = 0; i < catalogue.constellations().size(); ++i) {
			CartesianCoordinates center = stereo.apply(equatorialToHorizontalConversion.apply(catalogue.constellations().get(i).center()));
			constellationsCenter[i * 2] = center.x();
			constellationsCenter[i * 2 + 1] = center.y();
			double[] vertices = new double[catalogue.constellations().get(i).vertices().size() * 2];
			for(int j = 0; j < catalogue.constellations().get(i).vertices().size(); ++j) {
				CartesianCoordinates c = stereo.apply(equatorialToHorizontalConversion.apply(catalogue.constellations().get(i).vertices().get(j)));
				vertices[j * 2] = c.x();
				vertices[j * 2 + 1] = c.y();
			}
			constellationsPosition.add(vertices);
		}
	}
	
	/**
	 * @return sun as observed
	 */
	public Sun sun() {
		return sun;
	}
	
	/**
	 * @return sun position
	 */
	public CartesianCoordinates sunPosition() {
		return sunPosition;
	}
	
	public HorizontalCoordinates sunHorizontalPosition() {
		return equatorialToHorizontalConversion.apply(sun.equatorialPos());
	}
	
	/**
	 * @return moon as observed
	 */
	public Moon moon() {
		return moon;
	}
	
	/**
	 * @return moon position
	 */
	public CartesianCoordinates moonPosition() {
		return moonPosition;
	}
	
	/**
	 * 
	 * @return planets as observed
	 */
	public List<Planet> planets() {
		return planets;
	}
	
	/**
	 * @return planet position in a double array ([0] is mercury x, [1] is mercury y, [2] is venus x, ect...)
	 */
	public double[] planetsPositions() {
		return planetsPositions;
	}
	
	/**
	 * 
	 * @return stars as observed
	 */
	public List<Star> stars() {
		return stars;
	}
	
	/**
	 * @return stars positions
	 */
	public double[] starsPositions() {
		return starsPositions;
	}
	
	
	/**
	 * @return asterisms set
	 */
	public Set<Asterism> asterisms() {
		return catalogue.asterisms();
	}
	
	/**
	 * @param asterism
	 * @return asterism's stars indices in the star catalogue
	 */
	public List<Integer> asterismIndices (Asterism asterism) {
		return catalogue.asterismIndices(asterism);
	}
	
	/**
	 * @param hor : horizontal coordinates of cursor
	 * @param max : max distance 
	 * @return closest celestial object from hor
	 */
	public Optional<CelestialObject> objectClosestTo(CartesianCoordinates c2, double max) {
		double closestDistance = Double.POSITIVE_INFINITY;
		Optional<CelestialObject> closestObject = Optional.empty();
		
		for(CartesianCoordinates c1 : coordinates.keySet()) {
			double currentDistance = c1.distanceTo(c2);
			if(closestDistance > currentDistance) {
				closestDistance = currentDistance;
				closestObject = Optional.of(coordinates.get(c1));
			}
		}
		return closestDistance <= max ? closestObject : Optional.empty();		
	}
	
	public List<double[]> constellationsPosition() {
		return constellationsPosition;
	}
	
	public double[] constellationsCenter() {
		return constellationsCenter;
	}
	
	public List<Constellation> constellations() {
		return catalogue.constellations();
	}

}
