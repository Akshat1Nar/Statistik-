package setup;
import java.sql.*;
import java.io.*;  
import java.util.Scanner;  
import gui.City;
import gui.Interface;
import de.fhpotsdam.unfolding.geo.Location;
import java.util.ArrayList;


// This class will be creating tables 
// And filling them with data
// It is part of initial setupd of data bases in 
// initial setup

public class Tables {

	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	public static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	public static final String USER = "dbProject";
	public static final String PASS = "EasyPeasy";

	public static Connection conn = null;
	public static Statement stmt = null;

	// Variable to keep sql
	// statements
	public static String sql;

	// Variable to keep result
	// of sql queries
	public static ResultSet rs;

	// In betweeen varible to 
	// access data from result
	// variable
	public static ResultSetMetaData rsmd;

	public static Scanner in;


	// A utility function that
	// executes statement from
	// given query as a string
	// Update Statement
	public static void executeStatement(){

		try{
			stmt.executeUpdate(sql);
		}
		catch(SQLException se) {
		   //Handle errors for JDBC
		   // se.printStackTrace();
		}
	}

	// Get query Statement
	public static void executeStQuery(){
		try{
			rs = stmt.executeQuery(sql);
		}
		catch(SQLException se) {
		   //Handle errors for JDBC
		   // se.printStackTrace();
		}	
	}

	// A function to create markers 
	// on all the cities
	public static void fillMarkers(){
		try{
			rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

		
			while (rs.next()) {

				String city = rs.getString(1);
				String state = rs.getString(4);

				String lat_ = rs.getString(2);
				double lat = Double.parseDouble(lat_);

				String lng_ = rs.getString(3);
				double lng = Double.parseDouble(lng_);

				String population_ = rs.getString(5);
				int population = Integer.parseInt(population_);

				// System.out.println(city+" "+state+" "+lat+" "+lng+" "+population);

				City temp = new City(new Location(lat,lng),lat,lng,city);
				Interface.map.addMarkers(temp);
				
			}

		}
		catch(Exception e){
			System.out.println("Unable to extract query");
			// e.printStackTrace();
		}
		
	}


	public static void printQuery(){
		try{
			rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

		
			System.out.println("-----------------------------------------------------------------------------------");
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
			System.out.println("");
			}


			System.out.println("-----------------------------------------------------------------------------------");
			

		}
		catch(Exception e){
			System.out.println("Unable to extract query");
		}
		
	}

	public static void createScanner(String file){

		try{
			in = new Scanner(new File(file));
			// Read line by line
			in.useDelimiter("\n");
			in.next();
		}
		catch(Exception e){
			System.out.println("Unable to open csv file");
			e.printStackTrace();
		}
	}

	public static boolean checkTableIfExists(String TableName){

		try{

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TableName, null);
			if (tables.next()) {
			  	// Table exists do nothing
			  	return true;
			}
			return false;
		}
		catch(Exception e){
			System.out.println("Error in finding table");
			e.printStackTrace();
			return false;
			
		}

	}

	// This fills main table
	// It takes in.csv as a 
	// input and fills it into
	// database
	public static void fillMainTable(){

		// Creating table cities
		sql = "CREATE TABLE cities ("
									+"city VARCHAR(100),"
									+"lat DECIMAL(10,5),"
									+"lng DECIMAL(10,5),"
									+"state VARCHAR(100),"
									+"population INT,"
									+"PRIMARY KEY (city,state))";
		

		// Filling table cities
		try{

			// check if "cities" table is there
			if(checkTableIfExists(new String("cities")))
				return;

			// Create Table
			executeStatement();

			String temp_sql = "INSERT INTO cities (city,lat,lng,state,population) VALUES (";
			createScanner(new String("./databases/in.csv"));
			while(in.hasNext()){
				
				String[] vs = in.next().split(",");
				vs[0] = vs[0].toUpperCase();
				vs[3] = vs[3].toUpperCase();

				// Syncing the csv file data
				// with sql syntax and
				// inserting into cities table				
				sql = temp_sql
					  +"\'"+vs[0]+"\'"+","
					  +vs[1]+","
					  +vs[2]+","
					  +"\'"+vs[3]+"\'"+","
					  +vs[4]
					  +")";
				executeStatement();
			}
		}
		catch(Exception e){
			System.out.println("Unable to fill main Table");
			e.printStackTrace();
		}
	}

	static void fillCrimeTables(String TableName,String file){

		// Creating table cities
		String temp_sql = " ("
				+"city VARCHAR(100),"
				+"state VARCHAR(100),"
				+"`2001` INT DEFAULT 0,"
				+"`2002` INT DEFAULT 0,"
				+"`2003` INT DEFAULT 0,"
				+"`2004` INT DEFAULT 0,"
				+"`2005` INT DEFAULT 0,"
				+"`2006` INT DEFAULT 0,"
				+"`2007` INT DEFAULT 0,"
				+"`2008` INT DEFAULT 0,"
				+"`2009` INT DEFAULT 0,"
				+"`2010` INT DEFAULT 0,"
				+"`2011` INT DEFAULT 0,"
				+"`2012` INT DEFAULT 0,"
				+"average INT AS ((`2001`+`2002`+`2003`+`2004`+`2005`"
				+"+`2006`+`2007`+`2008`+`2009`+`2010`+`2011`+`2012`)/12),"
				+"FOREIGN KEY (city,state) REFERENCES cities(city,state)"
				+")";

		if(checkTableIfExists(TableName))
			return;


		sql = "CREATE TABLE " + TableName + temp_sql;
		executeStatement();
		createScanner(file);

		String vs[] = in.next().split(",");
		ArrayList<String> Year = new ArrayList<String>();
		ArrayList<String> YearValues = new ArrayList<String>();
		String city = "";
		String state = "";

		city = vs[1];
		state = vs[0];
		Year.add(vs[2]);
		YearValues.add(vs[3]);
		try{

			while(in.hasNext()){

				// If talking about same city
				// keep collecting data
				String temp[] = in.next().split(",");
				if(temp[0].equals(state) && temp[1].equals(city)){
					Year.add(temp[2]);
					YearValues.add(temp[3]);
				}
				else{

					// else fill the data
					// into the table
					String y = "";
					String yv = "";

					for(String each: Year){
						y+=",";
						y+="`";
						y+=each;
						y+="`";
					}
					for(String each: YearValues){
						yv+=",";
						yv+=each;
					}



					sql = "INSERT INTO "
						  +TableName
						  +" (city,state"
						  +y
						  +")"
						  +" VALUES ("
						  +"\'"
						  +city
						  +"\'"
						  +","
						  +"\'"
						  +state
						  +"\'"
						  +yv
						  +")";

					
					executeStatement();
					Year.clear();
					YearValues.clear();


					if(in.hasNext()){
						vs = in.next().split(",");
						
						city = vs[1];
						state = vs[0];
						Year.add(vs[2]);
						YearValues.add(vs[3]);
					}
				}
				
				
			}
		}
		catch(Exception e){
			System.out.println("Error filling crime table "+TableName);
			// e.printStackTrace();
		}

	}
	// Constructor
	// that initializes everything
	public Tables(){

		try {
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String DATABASE_NAME = "RECORDS";
			stmt = conn.createStatement();


			sql = "USE RECORDS";
			executeStatement();

		}
		catch(Exception e){
			System.out.println("Error in connecting to DATABASE when creating or accessing tables");
		}

		fillMainTable();
		fillCrimeTables("rape","./databases/rape.csv");
		fillCrimeTables("theft","./databases/theft.csv");
		fillCrimeTables("kidnapping","./databases/kidnapping.csv");
		fillCrimeTables("murder","./databases/murder.csv");
	
	}

}