package base.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entidades.ClsUsuario;

public class ConsultasUsuario {

	// EMPIEZAN LOS SELECT

	/**
	 * Comprueba que exista el usuario
	 * 
	 * @param conn
	 * @param idUsuario
	 * @return
	 * @throws SQLException
	 */
	public static boolean existeUsuarioId(Connection conn, int idUsuario) throws SQLException {
		boolean existe = false;

		PreparedStatement buscaUsuarioId = conn.prepareStatement("SELECT idUsuario FROM Usuarios WHERE idUsuario = ?;");
		buscaUsuarioId.setInt(1, idUsuario);
		ResultSet resultado = buscaUsuarioId.executeQuery();

		if (resultado.next()) {
			existe = true;
		}

		resultado.close();
		buscaUsuarioId.close();

		return existe;
	}
	
	/**
	 * Comprueba que no exista ya un usuario con ese username
	 * @param conn
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public static boolean existeUsuarioUsername (Connection conn, String username) throws SQLException {
		boolean existe = false;
		
		PreparedStatement buscaUsuarioId = conn.prepareStatement("SELECT username FROM Usuarios WHERE Username = ?;");
		buscaUsuarioId.setString(1, username);
		ResultSet resultado = buscaUsuarioId.executeQuery();
		
		if (resultado.next()) {
			existe = true;
		}

		resultado.close();
		buscaUsuarioId.close();

		return existe;
		
	}

	/**
	 * Muestra a todos los usuarios de la base de datos
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public static void selectUsuario(Connection conn) throws SQLException {

		PreparedStatement buscaUsuario = conn.prepareStatement("SELECT * FROM Usuarios;");
		ResultSet resultado = buscaUsuario.executeQuery();

		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idUsuario") + " ");
			System.out.println("Nombre: " + resultado.getString("nombre") + " ");
			System.out.println("Apellidos: " + resultado.getString("apellidos") + " ");
			System.out.println("Username: " + resultado.getString("Username") + " ");
			System.out.println("email: " + resultado.getString("email") + " ");
		}

		resultado.close();
		buscaUsuario.close();

	}

	/**
	 * Muestra al usuario con el id especificado
	 * 
	 * @param conn
	 * @param idUsuario
	 * @throws SQLException
	 */
	public static void selectUsuario(Connection conn, int idUsuario) throws SQLException {

		PreparedStatement buscaUsuario = conn.prepareStatement("SELECT * FROM Usuarios WHERE idUsuario = ?");
		buscaUsuario.setInt(1, idUsuario);
		ResultSet resultado = buscaUsuario.executeQuery();

		while (resultado.next()) {

			System.out.println("id: " + resultado.getInt("idUsuario") + " ");
			System.out.println("Nombre: " + resultado.getString("nombre") + " ");
			System.out.println("Apellidos: " + resultado.getString("apellidos") + " ");
			System.out.println("Username: " + resultado.getString("Username") + " ");
			System.out.println("email: " + resultado.getString("email") + " ");
		}

		resultado.close();
		buscaUsuario.close();

	}

	/**
	 * Muestra al usuario con el username especificado
	 * 
	 * @param conn
	 * @param username
	 * @throws SQLException
	 */
	public static void selectUsuario(Connection conn, String username) throws SQLException {

		PreparedStatement buscaUsuario = conn.prepareStatement("SELECT * FROM Usuarios WHERE username = ?");
		buscaUsuario.setString(1, username);
		ResultSet resultado = buscaUsuario.executeQuery();

		while (resultado.next()) {
			System.out.println("idUsuario: " + resultado.getInt("idUsuario") + " ");
			System.out.println("Nombre: " + resultado.getString("nombre") + " ");
			System.out.println("Apellidos: " + resultado.getString("apellidos") + " ");
			System.out.println("Username: " + resultado.getString("Username") + " ");
			System.out.println("email: " + resultado.getString("email") + " ");
		}
		resultado.close();
		buscaUsuario.close();

	}

	// EMPIEZAN LOS INSERT

	/**
	 * Recibe un usuario y lo inserta
	 * 
	 * @param conn
	 * @param u
	 * @throws SQLException
	 */
	public static void insertUsuario(Connection conn, ClsUsuario u) throws SQLException {

		String nombre = u.getNombre();
		String apellidos = u.getApellidos();
		String username = u.getUsername();
		String password = u.getPassword();
		String email = u.getEmail();

		String insercion = "INSERT INTO Usuarios (Nombre, Apellidos, Username, Password, email) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement insertaUsuario = conn.prepareStatement(insercion);

		insertaUsuario.setString(1, nombre);
		insertaUsuario.setString(2, apellidos);
		insertaUsuario.setString(3, username);
		insertaUsuario.setString(4, password);
		insertaUsuario.setString(5, email);

		insertaUsuario.executeUpdate();

		insertaUsuario.close();
	}

	// EMPIEZAN LOS UPDATE

	/**
	 * hace un update de un usuario y muestra sus datos
	 * 
	 * @param conn
	 * @param u
	 * @throws SQLException
	 */
	public static void updateUsuario(Connection conn, ClsUsuario u, String originalUsername) throws SQLException {

		String nombre = u.getNombre();
		String apellidos = u.getApellidos();
		String username = u.getUsername();
		String password = u.getPassword();
		String email = u.getEmail();

		String update = "UPDATE Usuarios SET Nombre = ?, Apellidos = ?, Username = ?, Password = ?, email = ? WHERE username = ?";

		PreparedStatement updateUsuario = conn.prepareStatement(update);

		updateUsuario.setString(1, nombre);
		updateUsuario.setString(2, apellidos);
		updateUsuario.setString(3, username);
		updateUsuario.setString(4, password);
		updateUsuario.setString(5, email);
		updateUsuario.setString(6, originalUsername);

		updateUsuario.executeUpdate();
		updateUsuario.close();

		selectUsuario(conn, username);

	}

	// EMPIEZAN LOS DELETE

	/**
	 * Borra la tabla usuarios
	 * @param conn
	 * @throws SQLException
	 */
	public static void deleteTablaUsuario(Connection conn) throws SQLException {
		
		String query = "DROP TABLE IF EXISTS Usuarios";
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		 borraTabla.executeUpdate();
		borraTabla.close();
	}
	/**
	 * Deja vacía la tabla Usuarios
	 * @param conn
	 * @throws SQLException
	 */
	public static int deletAllUsuarios(Connection conn) throws SQLException {
		int filasAfectadas = 0;
		String query = "DELETE FROM Usuarios";
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		return filasAfectadas;
	}
	
	/**
	 * Borra un usuario por su nombre de columna y un int
	 * @param conn
	 * @param columna
	 * @param id
	 * @throws SQLException
	 */
	public static int deleteUsuarios (Connection conn, String columna, int id) throws SQLException {
		int filasAfectadas = 0;
		String query = "DELETE FROM Usuarios WHERE " + columna + " = ?;";
		
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.setInt(1, id);
		borraTabla.executeUpdate();
		borraTabla.close();
		return filasAfectadas;
		
	}
	
	/**
	 * Borra un usuario por un nombre de columna y un string
	 * @param conn
	 * @param columna
	 * @param dato
	 * @throws SQLException
	 */
	public static int deleteUsuarios (Connection conn, String columna, String dato) throws SQLException {
		int filasAfectadas = 0;
		String query = "DELETE FROM Usuarios WHERE " + columna + " = ?";
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.setString(1, dato);
		borraTabla.executeUpdate();
		borraTabla.close();
		return filasAfectadas;
		
	}


}
