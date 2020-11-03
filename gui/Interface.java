package gui;
import setup.Setup;
import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class Interface extends PApplet{
	private static final long serialVersionUID = 1L;
  	private static int mapWidth = 900;
    private static int mapHeight = 600;
    
	public static UnfoldingMap map;


	public void setup() {
        this.size(900, 600);
  		this.background(0, 0, 128);
  		AbstractMapProvider provider = new OpenStreetMap.OpenStreetMapProvider();
  		// AbstractMapProvider provider = new Google.GoogleSimplifiedProvider();
  
        int zoomLevel = 7;
  
        map = new UnfoldingMap(this, 40, 50, mapWidth, mapHeight, provider);
        map.zoomAndPanTo( zoomLevel, new Location(28.7041f, 77.1025f));

        MapUtils.createDefaultEventDispatcher( this, map);
        new Setup();
	}
  
    // Function to draw the applet window 
    public void draw() { 
        map.draw();
    } 
	
	public static void main(String[] args){
		String[] processingArgs = {"Interface"};
		Interface face = new Interface();
		PApplet.runSketch(processingArgs, face);
	}
}