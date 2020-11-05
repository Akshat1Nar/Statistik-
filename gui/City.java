package gui;

import gui.Interface;
import processing.core.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.AbstractMarker;

public class City extends AbstractMarker{

	String Name;
	double lat;
	double lng;
	
	public City(Location location,double lat,double lng,String Name){
		super(location);

		this.lat = lat;
		this.lng = lng;
		this.Name = Name;
		// this.location = location;

	}

	protected boolean isInside(float checkX, float checkY, float x, float y){
		Location location = Interface.map.getLocation(checkX, checkY);
		checkY = location.getLat();
		checkX = location.getLon();
		
    		
		if(checkY<=this.lat+1 && checkY>=this.lat-1 && checkX<=this.lng+1 && checkX>=this.lng-1){
			return true;
		}
		return false;
	};

	public void draw(PGraphics pg,float x,float y) {
		pg.pushStyle();
		pg.fill(255, 255, 0, 100);
		pg.ellipse(x, y, 10, 10);
		pg.fill(155, 100);
		pg.ellipse(x, y, 5, 5);
		pg.popStyle();
	}

}