package setup;
import java.sql.*;
import java.io.*;  
import setup.Setup;
import java.util.Scanner;  
import gui.City;
import gui.Interface;
import de.fhpotsdam.unfolding.geo.Location;


// This class will be creating tables 
// And filling them with data
// It is part of initial setupd of data bases in 
// initial setup

public class Tables {

	// Variable to keep sql
	// statements
	String sql;

	// Variable to keep result
	// of sql queries
	ResultSet rs;

	// In betweeen varible to 
	// access data from result
	// variable
	ResultSetMetaData rsmd;


	// A utility function that
	// executes statement from
	// given query as a string
	// Update Statement
	void executeStatement(){

		try{
			Setup.stmt.executeUpdate(sql);
		}
		catch(SQLException se) {
		   //Handle errors for JDBC
		   se.printStackTrace();
		}
	}

	// Get query Statement
	void executeStQuery(){
		try{
			rs = Setup.stmt.executeQuery(sql);
		}
		catch(SQLException se) {
		   //Handle errors for JDBC
		   se.printStackTrace();
		}	
	}

	// A function to create markers 
	// on all the cities
	void fillMarkers(){
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


	void printQuery(){
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

	// This fills main table
	// It takes in.csv as a 
	// input and fills it into
	// database
	void fillMainTable(){

		// Creating table cities
		sql = "CREATE TABLE cities (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
										  +"city VARCHAR(100),"
										  +"lat DECIMAL(20,10),"
										  +"lng DECIMAL(20,10),"
										  +"state VARCHAR(100),"
										  +"population INT)";
		executeStatement();


		// Filling table cities
		try{

			Scanner in = new Scanner(new File("./databases/in.csv"));
			// Read line by line
			in.useDelimiter("\n");
			in.next();
			String temp_sql = "INSERT INTO cities (city,lat,lng,state,population) VALUES (";
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

	// Constructor
	// that initializes everything
	public Tables(){
		sql = "USE RECORDS";
		executeStatement();

		fillMainTable();
		

		sql = "SELECT * FROM cities";
		executeStQuery();
		fillMarkers();
		
		sql = "DROP DATABASE RECORDS";
		executeStatement();
		
	}

}