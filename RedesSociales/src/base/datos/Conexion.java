package base.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	/**
	 * Conexion a la base de datos
	 * @param args
	 */
	public static Connection conecta() {
		String url = "jdbc:mysql://dns11036.phdns11.es:3306/ad2425_mrequejo";
		String usuario= "mrequejo";
		String contrasena = "12345";
		Connection conn = null;
		try {
			
			conn = DriverManager.getConnection(url, usuario, contrasena);
			System.out.println(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	

}
