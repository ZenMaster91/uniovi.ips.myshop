package es.uniovi.ips.myshop.database.client;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class Database {

	public static final String PROTOCOL_JDBC = "jdbc";
	public static final String VENDOR_ORACLE = "oracle";
	public static final String DRIVER_THIN = "thin";
	public static final String DEFAULT_SERVER = "156.35.94.99";
	public static final String DEFAULT_PORT = "1521";
	public static final String DEFAULT_DATABASE = "DESA";

	public static final String DESA_USER = "UO236856";
	public static final String DESA_PASS = "UO236856";

	protected Connection conn = null;
	protected Statement stat = null;
	protected PreparedStatement psQuery = null;
	protected CallableStatement cs = null;
	protected ResultSet rs = null;

	protected Database() {}

	/**
	 * Closes a given connection.
	 * 
	 * @param connection to be closed.
	 */
	public void closeConnection() {
		try {
			this.conn.commit();
			this.conn.close();
			System.out.println("--> Connection " + this.conn.toString()
					+ " has been closed succesfully.");
		} catch (SQLException e) {
			System.err.println("The connection couldn't be closed.");
			e.printStackTrace();
		}
	}

	/**
	 * Opens a database connection.
	 * 
	 * @param protocol to use by the connection.
	 * @param vendor of the database.
	 * @param driver for the connection.
	 * @param server address.
	 * @param port where the connection must call.
	 * @param database name.
	 * @param user of the connection.
	 * @param password of the connection.
	 * 
	 * @return the opened connection.
	 * @throws SQLException if there is any error while opening the database.
	 */
	public abstract Connection connectDatabase(String protocol, String vendor,
			String driver, String server, String port, String databaseName,
			String user, String password) throws SQLException;

	/**
	 * Executes a sql in a given connection.
	 * 
	 * @param sql sentence to execute.
	 * @param connection where the sql will be executed.
	 * @return the result set.
	 * 
	 * @throws SQLException
	 */
	public ResultSet executeSQL(String sql) throws SQLException {
		this.stat = this.conn.createStatement();
		this.rs = this.stat.executeQuery(sql);
		return getResultSet();
	}

	/**
	 * Executes a sql prepared sentence in a given connection.
	 * 
	 * @param sql to be executed
	 * @param parameters for the sentence.
	 * @return the result of the sentence execution.
	 * @throws SQLException if any error.
	 */
	public ResultSet executeSQL(String sql, Object... parameters)
			throws SQLException {
		this.psQuery = this.conn.prepareStatement(sql);
		for (int i = 0; i < parameters.length; i++) {
			this.psQuery.setObject(i + 1, parameters[i]);
		}
		this.rs = this.psQuery.executeQuery();
		return getResultSet();
	}

	public void executeUpdate(String sql) throws SQLException {
		this.conn.createStatement().executeUpdate(sql);
	}

	/**
	 * Executes an update in the database from a sql an a parameters array.
	 * 
	 * @param sql
	 * @param parameters
	 * @throws SQLException
	 */
	public void executeUpdate(String sql, Object... parameters)
			throws SQLException {
		this.psQuery = this.conn.prepareStatement(sql);
		for (int i = 0; i < parameters.length; i++) {
			this.psQuery.setObject(i + 1, parameters[i]);
		}
		this.psQuery.executeUpdate();
	}

	/**
	 * Returns the result set object.
	 * 
	 * @return the result set object.
	 */
	public ResultSet getResultSet() {
		return this.rs;
	}

	/**
	 * Returns the number of columns the ResultSet has.
	 * 
	 * @param rs to compute.
	 * @return the number of rows that it contains.
	 * @throws SQLException
	 */
	public int getResutSetSize() throws SQLException {
		int aux = 0;
		while (this.rs != null) {
			this.rs.beforeFirst();
			this.rs.last();
			aux = this.rs.getRow();
		}
		return aux;
	}

	/**
	 * Converts the result set of the consult to a matrix of values.
	 * 
	 * @return the matrix with the values of the consult.
	 * @throws SQLException
	 */
	public Object[][] resultSetToObjectArray() throws SQLException {
		List<Object[]> res = new ArrayList<Object[]>();
		int columns = this.getResutSetSize();
		Object[] register;
		while (rs.next()) {
			register = new Object[columns];
			for (int i = 0; i < columns; i++) {
				register[i] = rs.getObject(i + 1);
				res.add(register);
			}
		}
		Object[][] toReturn = new Object[res.size()][columns];
		for (int i = 0; i < res.size(); i++) {
			for (int j = 0; j < columns; j++) {
				toReturn[i][j] = res.get(i)[j];
			}
		}
		return toReturn;
	}
}
