package gui;
import setup.Setup;
import setup.Tables;
import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import java.net.URL;



import java.awt.event.*;
import javax.swing.*;
import javax.swing.Popup.*;
import javax.swing.event.*;
import java.awt.*;

public class Interface extends PApplet{

	private static Marker markedCity = null;
	private static final long serialVersionUID = 1L;
  	private static int mapWidth = 900;
    private static int mapHeight = 600;
    private static int zoomLevel = 7;
	private Popup popup = null;
  
    
	public static UnfoldingMap map;


	@Override
	public void setup() {

        this.size(900, 600);
  		this.background(0, 0, 128);
  		AbstractMapProvider provider = new OpenStreetMap.OpenStreetMapProvider();
  		// AbstractMapProvider provider = new Google.GoogleSimplifiedProvider();
  
        map = new UnfoldingMap(this, 40, 50, mapWidth, mapHeight, provider);
        map.zoomAndPanTo( zoomLevel, new Location(28.7041f, 77.1025f));
        MapUtils.createDefaultEventDispatcher( this, map);

        new Setup();

        // If tables was not called
        // and there was no connection
        // then create connection
        if(Tables.conn == null){
	        new Tables();
        }

        Tables.sql = "SELECT * FROM cities";
		Tables.executeStQuery();
		Tables.fillMarkers();
	}
  
    // Function to draw the applet window 
	@Override
    public void draw() { 
        map.draw();
        Location location = map.getLocation(mouseX, mouseY);
    	fill(0);
    	text(location.getLat() + ", " + location.getLon(), mouseX, mouseY);
    } 

	@Override
    public void mouseClicked(MouseEvent e) {

    	if(mouseButton==LEFT){
    		return;
    	}

    	Marker hitMarker = map.getFirstHitMarker(mouseX, mouseY);
    	
    	if (hitMarker != null) {
    		
    		// Select current marker 
        	hitMarker.setSelected(true);
        	popup = new Popup(mouseX, mouseY,e,hitMarker.getLocation().getLat(),hitMarker.getLocation().getLon()); 
        	markedCity = hitMarker;
    	} 
    	else {
        	// Deselect all other markers
        	if(markedCity!=null)
	        	markedCity.setSelected(false);
    	}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if(popup!=null){
			popup.setVisible(false);
			popup = null;
		}
	}
	
	public static void main(String[] args){

		String[] processingArgs = {"Interface"};
		Interface face = new Interface();
		PApplet.runSketch(processingArgs, face);
	}
}