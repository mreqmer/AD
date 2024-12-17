package base.datos;

import java.sql.Connection;
import java.sql.SQLException;

public class Utiles {

	/**
	 * Empieza una transaccion
	 * @param conn
	 * @throws SQLException
	 */
	public static void abrirTransaccion(Connection conn) throws SQLException {
	    conn.setAutoCommit(false);
	}
	
	/**
	 * Hace commit a una transaccion
	 * @param conn
	 * @throws SQLException
	 */
	public static void hacerCommit(Connection conn) throws SQLException {
	    conn.commit();
	}
	
	/**
	 * Hace rollback de una transaccion
	 * @param conn
	 * @throws SQLException
	 */
	public static void hacerRollback(Connection conn) throws SQLException {
	    conn.rollback();
	}
	
}
