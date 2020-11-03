package gui;

import processing.core.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

public class City extends SimplePointMarker{

	String Name;
	double lat;
	double lng;
	Location location;

	public City(Location location,double lat,double lng,String Name){
		super(location);

		this.lat = lat;
		this.lng = lng;
		this.Name = Name;
		this.location = location;

	}

	public void draw(PGraphics pg,float x,float y) {
		pg.pushStyle();
		pg.noStroke();
		pg.fill(200, 200, 0, 100);
		pg.ellipse(x, y, 40, 40);
		pg.fill(255, 100);
		pg.ellipse(x, y, 30, 30);
		pg.popStyle();
	}
}