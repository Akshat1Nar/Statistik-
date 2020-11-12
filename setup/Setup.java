package setup;
import java.sql.*;


public class Setup {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "GroupProject";
	static final String PASS = "EasyPeasy";

	static Connection conn = null;
	static Statement stmt = null;
   
	public Setup() {

		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("Welcome to Our Software");
		System.out.println("Befor begining make sure you have following as an user");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("USER : GroupProject");
		System.out.println("PASSWORD : EasyPeasy");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("");
			
		
		ResultSet rs = null;

		try {
			//STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String DATABASE_NAME = "RECORDS";

			rs = conn.getMetaData().getCatalogs();
 
			while(rs.next()){
				String catalogs = rs.getString(1);
		
					if(DATABASE_NAME.equals(catalogs)){
						System.out.println("Database already existed");
						System.out.println("-----------------------------------------------------------------------------------");
						System.out.println("Checking for Tables...");
						System.out.println("-----------------------------------------------------------------------------------");
						return;
					}
			}

			//STEP 4: Execute a query
			System.out.println("Creating database...");
			System.out.println("-----------------------------------------------------------------------------------");
			stmt = conn.createStatement();
			
			String sql = "CREATE DATABASE RECORDS";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully!\n");
			System.out.println("-----------------------------------------------------------------------------------");

			System.out.println("Filling Tables...");
			System.out.println("-----------------------------------------------------------------------------------");
			new Tables();
		}

		catch(SQLException se) {
		   //Handle errors for JDBC
		   se.printStackTrace();
		}

		catch(Exception e) {
		   //Handle errors for Class.forName
		   e.printStackTrace();
		}

		finally {
		   //finally block used to close resources
		   try {
		      if(stmt!=null)
		        	stmt.close();
		    }
		   catch(SQLException se2){
		   }// nothing we can do
		   
		   try {
		      if(conn!=null)
		        	conn.close();
		    }
		    catch(SQLException se) {
		      se.printStackTrace();
		    }//end finally try
		}//end try

		System.out.println("Database didn't exist earlier, It is now created!");
		System.out.println("");
		System.out.println("");
	}//end main
}
