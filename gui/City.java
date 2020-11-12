package gui;

import gui.Interface;
import gui.Popup;
import processing.core.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.AbstractMarker;

public class City extends AbstractMarker{

	String name;
	private float locx,locy;
	
	public City(Location location,double lat,double lng,String name){
		super(location);
		this.name = name;
		// this.location = location;

	}

	public String getName(){
		return name;
	}

	@Override
	protected boolean isInside(float checkX, float checkY, float x, float y){
		
		if(checkY<=locy+(double)5
		&& checkY>=locy-(double)5
		&& checkX<=locx+(double)5
		&& checkX>=locx-(double)5){
			return true;
		}
		return false;
	};

	@Override
	public void draw(PGraphics pg,float x,float y) {
		locx =x;
		locy =y;
		pg.pushStyle();
		pg.fill(255, 255, 0, 100);
		pg.ellipse(x, y, 10, 10);
		pg.popStyle();
	}

}