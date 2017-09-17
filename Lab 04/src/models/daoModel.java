package models;
import java.sql.*;
import java.util.List;

import bank.BankRecords;

/**
 * The class daoModel allows to process all necessary CRUD operations.
 * 
 * @author Francisco Rois Siso
 *
 */
public class daoModel {
	/**
	 * Method createTable creates a new table in the database, if it does not exist yet, using the connection already established. 
	 * @param connection Connection already established to a database
	 * @param table_name Name of the table to be created, as String
	 */
	public void createTable(Connection connection, String table_name){
		try{
			// Execute a query to create the table
			System.out.println("Creating table in database ...");
			Statement statement = connection.createStatement();

			String sql = "CREATE TABLE "+ table_name +
					"(id VARCHAR(255) not NULL, " +
					" income DOUBLE, " + 
					" pep VARCHAR(255), " +
					" PRIMARY KEY ( id ))"; 

			statement.executeUpdate(sql);
			System.out.println("Created table in given database...");
			statement.close();
		}
		catch(SQLException se){
			//Handle errors for JDBC
			System.out.println(se.getMessage());
		}catch(Exception e){
			//Handle errors for Class.forName
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method dropTable allows to drop a table from a database by using the connection provided.
	 * @param connection Connection already established to a database.
	 * @param table_name Name of the table to be dropped, as String.
	 */
	public void dropTable(Connection connection, String table_name){
		try{
			// Execute a query to drop the table from the database
			System.out.println("Droping table in database ...");
			Statement statement = connection.createStatement();

			String sql = "DROP TABLE "+ table_name; 

			statement.executeUpdate(sql);
			System.out.println("Droped table in given database...");
			statement.close();
		}
		catch(SQLException se){
			//Handle errors for JDBC
			System.out.println(se.getMessage());
		}catch(Exception e){
			//Handle errors for Class.forName
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method deleteAllFromTable allows to delete all the rows of a certain database.
	 * @param connection Connection already established to a database.
	 * @param table_name Name of the table to be cleared, as String.
	 */
	public void deleteAllFromTable(Connection connection, String table_name){
		try{
			// Execute a query to delete all from the table
			System.out.println("Deleting all from table in database ...");
			Statement statement = connection.createStatement();

			String sql = "DELETE FROM "+ table_name; 

			statement.executeUpdate(sql);
			System.out.println("Deleted all rows from table in given database...");
			statement.close();
		}
		catch(SQLException se){
			//Handle errors for JDBC
			System.out.println(se.getMessage());
		}catch(Exception e){
			//Handle errors for Class.forName
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method inserts allows for a list of BankRecords objects to be passed to it and thus insert all record object
	 * field data consisting of the id, income and pep from the list into a database.
	 * @param connection Connection already established to a database.
	 * @param records List of BankRecords to be stored into the database.
	 * @param table_name Name of the table where the items are inserted.
	 */
	public void inserts(Connection connection, List<BankRecords> records, String table_name){
		String sql = "";   
		try{
			// Execute a query
			System.out.println("Inserting records into the table...");
			Statement statement = connection.createStatement();

			for(int i = 0; i<records.size(); i++){
				String id = records.get(i).getId();
				double income = records.get(i).getIncome();
				String pep = records.get(i).getPep();

				sql = "INSERT INTO "+table_name+" " +
						"VALUES ("+"'"+id+"'"+", "+"'"+String.valueOf(income)+"'"+", "+"'"+pep+"'"+")";
				//System.out.println("sql: "+sql);
				statement.executeUpdate(sql);
			}
			System.out.println("Inserted records into the table...");
			statement.close();

		}catch(SQLException se){
			//Handle errors for JDBC
			System.out.println(se.getMessage());
		}catch(Exception e){
			//Handle errors for Class.forName
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method getResultSet returns a ResultSet object containing the record data (id, income and pep).
	 * The result is sorted in descending order by pep, in order to show first the premium users.
	 * @param connection Connection already established to a database.
	 * @param table_name Table from which the data is retrieved to make the ResultSet.
	 * @return
	 */
	// sorted descending by pep
	public ResultSet getResultSet(Connection connection, String table_name){
		ResultSet rs = null;
		try{
			// Execute a query
			Statement statement = connection.createStatement();

			// Select by id, income and pep and sort by pep in descending order in order to show first the premium users (pep = YES)
			String sql = "SELECT id, income, pep FROM "+table_name+
					" ORDER BY pep DESC";
			rs = statement.executeQuery(sql);
					
		}catch(SQLException se){
			//Handle errors for JDBC
			System.out.println(se.getMessage());
		}catch(Exception e){
			//Handle errors for Class.forName
			System.out.println(e.getMessage());
		}
		return rs;
	}
}