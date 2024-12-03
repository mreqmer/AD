package base.datos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreacionModificacion {

	
	public static void creaTablaUsuarios(Connection conn) {
		
		String crearTablaSQL = "USE ad2425_mrequejo;\n"
		        + "CREATE TABLE Usuarios (\n"
		        + "    idUsuario INT AUTO_INCREMENT PRIMARY KEY, \n"
		        + "    Nombre VARCHAR(50),\n"
		        + "    Apellidos VARCHAR(50),\n"
		        + "    Username VARCHAR(50),\n"
		        + "    Password VARCHAR(50),\n"
		        + "    email VARCHAR(50)\n"
		        + ");";
		
		try {
			Statement tablaUsuarios = conn.createStatement();
			tablaUsuarios.executeUpdate(crearTablaSQL);
			
			System.out.println("Tabla usuarios creada correctamente");
			
		} catch (SQLException e) {

			System.out.println("Fallo en la creación de la table");
			
			e.printStackTrace();
		}
	}
	
	public static void creaTablaPosts( Connection conn) {
		String crearTablaSQL = "CREATE TABLE Posts ("
			    + "idPost INT AUTO_INCREMENT PRIMARY KEY, "
			    + "idUsuario INT, "
			    + "created_at DATE, "
			    + "updated_at DATE, "
			    + "FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario)"
			    + ");";
		
		try {
			Statement tablaPosts = conn.createStatement();
			tablaPosts.executeUpdate(crearTablaSQL);
			
			System.out.println("Tabla Posts creada correctamente");
			
		} catch (SQLException e) {

			System.out.println("Fallo en la creación de la table");
			
			e.printStackTrace();
		}
	}
		public static void creaTablaLikes(Connection conn) {
			
			String crearTablaSQL = "CREATE TABLE Likes ("
				    + "idLikes INT AUTO_INCREMENT PRIMARY KEY, "
				    + "idUsuarios INT, "
				    + "idPost INT, "
				    + "FOREIGN KEY (idUsuarios) REFERENCES Usuarios(idUsuario), "
				    + "FOREIGN KEY (idPost) REFERENCES Posts(idPost)"
				    + ");";
			
			try {
				Statement tablaLikes = conn.createStatement();
				tablaLikes.executeUpdate(crearTablaSQL);
				
				System.out.println("Tabla likes creada correctamente");
				
			} catch (SQLException e) {

				System.out.println("Fallo en la creación de la table");
				
				e.printStackTrace();
			}
			
		}
		
		public static void insertaTablaUsuarios() {
			
		}
		
	}
	
	

