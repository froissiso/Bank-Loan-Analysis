package views;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * The class LoanView allows to create a graphical user interface to display information about the bank records.
 * The class LoanView extends JFrame.
 * 
 * @author Francisco Rois Siso
 *
 */
public class LoanView extends JFrame{
	static final long serialVersionUID = 2158941944719428789L;
	private String[] columnNames;
	private String[][] tableData;

	/**
	 * Constructor with no input parameters.
	 */
	public LoanView(){}
	
	/**
	 * Constructor with one input parameter: a ResultSet with data to be graphically displayed.
	 * @param rs ResultSet with data to be graphically displayed.
	 */
	public LoanView(ResultSet rs){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Loan View");

		//	    Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
		//	        { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
		//	    Object columnNames[] = { "Column One", "Column Two", "Column Three" };

		// Get a metadata object for the result set
		ResultSetMetaData metadata;
		try {
			// Get the number of rows.
			rs.last();                 // Move to last row
			int numRows = rs.getRow(); // Get row number
			rs.first();                // Move to first row
			//System.out.println("Number of rows: "+numRows);

			metadata = rs.getMetaData();
			// Create an array of Strings for the column names
			columnNames = new String[metadata.getColumnCount()];
			// Get the names of the columns
			//System.out.println("\nColumn names: ");
			for (int i = 0; i<metadata.getColumnCount(); i++){
				columnNames[i] = metadata.getColumnName(i+1);
				//System.out.println("\t"+columnNames[i]);
			}
			// Create 2D String array for the data from the table
			tableData = new String[numRows][metadata.getColumnCount()];
			//System.out.println("\nTable Data: ");

			// Get data from columns in the tableData array
			for (int row = 0; row < numRows; row++){
				for (int col = 0; col < metadata.getColumnCount(); col++){
					tableData[row][col] = rs.getString(col + 1);
					//System.out.println("\t"+tableData[row][col]);
				}
				// Go to the next row in the ResultSet.
				rs.next();
			}

			// create JTable and JScrollPane with the given rows and columns
			JTable table = new JTable(tableData, columnNames);
			JScrollPane scrollPane = new JScrollPane(table);
			frame.add(scrollPane, BorderLayout.CENTER);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception ex){
			System.out.println(ex.getMessage());
		}

		// Set other graphical features
		frame.setSize(300, 400);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}
}
