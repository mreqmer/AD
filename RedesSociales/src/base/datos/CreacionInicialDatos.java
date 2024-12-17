package base.datos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreacionInicialDatos {

	/**
	 * Crea la tabla usuarios en la base de datos
	 * @param conn
	 */
	public static void creaTablaUsuarios(Connection conn) {
		
		String crearTablaSQL =  "CREATE TABLE Usuarios (\n"
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
	
	/**
	 * Crea la tabla posts en la base de datos
	 * @param conn
	 */
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
	
		/**
		 * Crea la tabla likes en la base de datos
		 * @param conn
		 */
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
		
		/**
		 * Insercion de datos en la tabla de usuarios, lee los datos de un fichero para poder sacar los datos
		 * @param conn
		 */
		public static void insertaInicialTablaUsuarios(Connection conn) {
			BufferedReader br = null;
			String linea =  "";
			String insert = "";
			int filasAfectadas = 0;
			
			//intenta leer el fichero
			try {
				br = new BufferedReader (new FileReader("src/inserciones/usuarios.txt"));
				linea = br.readLine();
				//Lee hasta que no haya mas lineas
				while(linea!=null) {
					insert =  linea ;
					//preparacion e insercion
					Statement insertUsuario = conn.createStatement();
					insertUsuario.executeUpdate(insert);
					//contador para las filas afectadas
					filasAfectadas++;
					
					linea = br.readLine();
				}
				
				System.out.println("Filas afectadas: " + filasAfectadas);

				br.close();				
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el archivo");
			} catch (IOException e) {
				System.out.println("Ocurrió algún error");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Fallo en la insercion");
			}
		}
		
		/**
		 * Insert inicial en la tabla posts, lee los datos d eun fichero para pdoer sacar los datos
		 * @param conn
		 */
		public static void insertaInicialTablaPosts(Connection conn) {
			BufferedReader br = null;
			String linea =  "";
			String insert = "";
			int filasAfectadas = 0;
			
			//intenta leer el fichero
			try {
				br = new BufferedReader (new FileReader("src/inserciones/posts.txt"));
				linea = br.readLine();
				//Lee hasta que no haya mas lineas
				while(linea!=null) {
					insert =  linea ;
					//preparacion e insercion
					Statement insertPost = conn.createStatement();
					insertPost.executeUpdate(insert);
					//contador para las filas afectadas
					filasAfectadas++;
					linea = br.readLine();
				}
				
				System.out.println("Filas afectadas: " + filasAfectadas);

				br.close();				
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el archivo");
			} catch (IOException e) {
				System.out.println("Ocurrió algún error");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Fallo en la insercion");
			}
		}
		
		/**
		 * Insert inicial en la tabla likes, lee los datos de un fichero para pdoer sacar los datos
		 * @param conn
		 */
		public static void insertaInicialTablaLikes(Connection conn) {
			BufferedReader br = null;
			String linea =  "";
			String insert = "";
			int filasAfectadas = 0;
			
			//intenta leer el fichero
			try {
				br = new BufferedReader (new FileReader("src/inserciones/likes.txt"));
				linea = br.readLine();
				//Lee hasta que no haya mas lineas
				while(linea!=null) {
					insert =  linea ;
					//preparacion e insercion
					Statement insertLikes = conn.createStatement();
					insertLikes.executeUpdate(insert);
					//contador para las filas afectadas
					filasAfectadas++;
					linea = br.readLine();
				}
				
				System.out.println("Filas afectadas: " + filasAfectadas);

				br.close();				
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el archivo");
			} catch (IOException e) {
				System.out.println("Ocurrió algún error");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Fallo en la insercion");
			}
		}
		
		
		
		
	}
	
	

