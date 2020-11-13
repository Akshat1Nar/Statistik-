package base;
import setup.Tables;
import javax.swing.ImageIcon;
import java.io.*;

public class Query {


	public static String CITY;
	public static String ALL[] = {"Rape","Theft","Kidnappings","Murders"};
	public static String ALLVALUES[] = {"","","",""};
	public static ImageIcon ALLICONS[] = {new ImageIcon("./png/gallows.png"),new ImageIcon("./png/theft.png"),
										  new ImageIcon("./png/shield.png"),new ImageIcon("./png/prisoner.png")};

	// Function to get average values 
	// for each table
	public static void fillValues(){

		try{
			while (Tables.rs.next()) {
				CITY = Tables.rs.getString(1);
				for (int i = 1; i <= ALL.length; i++) {
					String columnValue = Tables.rs.getString(i+1);
					ALLVALUES[i-1] = columnValue;
				}
			}
			CITY = Tables.rs.getString(ALL.length+1);
		}
		catch(Exception e){
			// e.printStackTrace();
			// System.out.println("Unable to get statistics");
		}
	}

	// It will use connections and statements from tables.java

	public static void GetQuery(double lat,double lng){

		// This query can eaisly be automated 
		// for any number of tables by using the fact 
		// that 

		Tables.sql = "SELECT cities.city,rape.average,theft.average,kidnapping.average,murder.average"
					 +" FROM (((("
					 +"cities INNER JOIN theft on "
					 +"cities.city = theft.city and cities.state = theft.state) "
					 +"INNER JOIN rape on cities.city = rape.city and cities.state = rape.state)"
					 +"INNER JOIN kidnapping on cities.city = kidnapping.city and cities.state = kidnapping.state)"
					 +"INNER JOIN murder on cities.city = murder.city and cities.state = murder.state)"
					 +" where cities.lat = "
					 +String.format("%.5f",lat)
					 +" and cities.lng = "
					 +String.format("%.5f",lng);

		Tables.executeStQuery();

		fillValues();
		
	}
}