package setup;
import java.sql.*;
import java.io.*;  
import java.util.Scanner;  
import gui.City;
import gui.Interface;
import de.fhpotsdam.unfolding.geo.Location;


// This class will be creating tables 
// And filling them with data
// It is part of initial setupd of data bases in 
// initial setup

public class Tables {

	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	public static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	public static final String USER = "GroupProject";
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
		   se.printStackTrace();
		}
	}

	// Get query Statement
	public static void executeStQuery(){
		try{
			rs = stmt.executeQuery(sql);
		}
		catch(SQLException se) {
		   //Handle errors for JDBC
		   se.printStackTrace();
		}	
	}

	// A function to create markers 
	// on all the cities
	public static void fillMarkers(){
		try{
			rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();

		
			while (rs.next()) {

				String city = rs.getString(2);
				String state = rs.getString(5);

				String lat_ = rs.getString(3);
				double lat = Double.parseDouble(lat_);

				String lng_ = rs.getString(4);
				double lng = Double.parseDouble(lng_);

				String population_ = rs.getString(6);
				int population = Integer.parseInt(population_);

				// System.out.println(city+" "+state+" "+lat+" "+lng+" "+population);

				City temp = new City(new Location(lat,lng),lat,lng,city);
				Interface.map.addMarkers(temp);
				
			}

		}
		catch(Exception e){
			System.out.println("Unable to extract query");
			e.printStackTrace();
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

	// This fills main table
	// It takes in.csv as a 
	// input and fills it into
	// database
	public static void fillMainTable(){

		// Creating table cities
		sql = "CREATE TABLE cities ("
									+"city VARCHAR(100),"
									+"lat DECIMAL(20,10),"
									+"lng DECIMAL(20,10),"
									+"state VARCHAR(100),"
									+"population INT,"
									+"PRIMARY KEY (city,state))";
		

		// Filling table cities
		try{

			DatabaseMetaData dbm = conn.getMetaData();
			// check if "cities" table is there
			ResultSet tables = dbm.getTables(null, null, "cities", null);
			if (tables.next()) {
			  	// Table exists do nothing
			  	return;
			}

			// Create Table
			executeStatement();

			String temp_sql = "INSERT INTO cities (city,lat,lng,state,population) VALUES (";
			createScanner(new String("./databases/in.csv"));
			while(in.hasNext()){
				
				String[] vs = in.next().split(",");

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

	static void fillCrimeTables(String TableName){

		// Creating table cities
		String tempSql = "("
				+"city VARCHAR(100),"
				+"state VARCHAR(100),"
				+"2001 INT,"
				+"2002 INT,"
				+"2003 INT,"
				+"2004 INT,"
				+"2005 INT,"
				+"2006 INT,"
				+"2007 INT,"
				+"2008 INT,"
				+"2009 INT,"
				+"2010 INT,"
				+"2011 INT,"
				+"2012 INT,"
				+"average INT AS ((2001+2002+2003+2004+2005"
				+"+2006+2007+2008+2009+2010+2011+2012)/12),"
				+"PRIMARY KEY (city,state)"
				+"FOREIGN KEY (city,state) REFRENCES cities(city,state)"
				+")";


		createScanner(new String("crime.csv"));
		while(in.hasNext()){
			
			String[] vs = in.next().split(",");
			String temp_sql = "";
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
	
	}

}