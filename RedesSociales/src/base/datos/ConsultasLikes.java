package base.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entidades.ClsLike;
import entidades.ClsPost;

public class ConsultasLikes {

	// EMPIEZAN LOS SELECT

	/**
	 * Lista todos los likes
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public static void selectLikes(Connection conn) throws SQLException {

		PreparedStatement buscaLikes = conn.prepareStatement("SELECT * FROM Likes");

		ResultSet resultado = buscaLikes.executeQuery();

		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idLikes") + " ");
			System.out.println("idUsuario: " + resultado.getInt("idUsuarios") + " ");
			System.out.println("idPost: " + resultado.getInt("idPost") + " ");
			System.out.println();
		}

		resultado.close();
		buscaLikes.close();

	}

	/**
	 * Busca por un idLike concreto
	 * 
	 * @param conn
	 * @param idLikes
	 * @throws SQLException
	 */
	public static void selectLikesIdLikes(Connection conn, int idLikes) throws SQLException {

		PreparedStatement buscaLikes = conn.prepareStatement("SELECT * FROM Likes WHERE idLikes = ?");
		buscaLikes.setInt(1, idLikes);
		ResultSet resultado = buscaLikes.executeQuery();
		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idLikes") + " ");
			System.out.println("idUsuario: " + resultado.getInt("idUsuarios") + " ");
			System.out.println("idPost: " + resultado.getInt("idPost") + " ");
			System.out.println();
		}
		resultado.close();
		buscaLikes.close();
	}

	/**
	 * Busca por un id de usuario
	 * 
	 * @param conn
	 * @param idUsuario
	 * @throws SQLException
	 */
	public static void selectLikesIdUsuario(Connection conn, int idUsuario) throws SQLException {

		PreparedStatement buscaLikes = conn.prepareStatement("SELECT * FROM Likes WHERE idUsuario = ?");
		buscaLikes.setInt(1, idUsuario);
		ResultSet resultado = buscaLikes.executeQuery();
		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idLikes") + " ");
			System.out.println("idUsuario: " + resultado.getInt("idUsuarios") + " ");
			System.out.println("idPost: " + resultado.getInt("idPost") + " ");
			System.out.println();
		}
		resultado.close();
		buscaLikes.close();
	}
	
	/**
	 * Muestra todos los likes de un ususario
	 * @param conn
	 * @param username
	 * @throws SQLException
	 */
	public static void selectLikesUsername(Connection conn, String username) throws SQLException {

		String query = "SELECT * FROM Likes AS L JOIN Usuarios AS U ON L.IdUsuarios = U.IdUsuario WHERE username = ?;";
		
		PreparedStatement buscaLikes = conn.prepareStatement(query);
		buscaLikes.setString(1, username);
		ResultSet resultado = buscaLikes.executeQuery();
		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idLikes") + " ");
			System.out.println("idUsuario: " + resultado.getInt("idUsuarios") + " ");
			System.out.println("idPost: " + resultado.getInt("idPost") + " ");
			System.out.println();
		}
		resultado.close();
		buscaLikes.close();
	}

	/**
	 * Busca por un id de Post
	 * 
	 * @param conn
	 * @param idPosts
	 * @throws SQLException
	 */
	public static void selectLikesIdPosts(Connection conn, int idPosts) throws SQLException {

		PreparedStatement buscaLikes = conn.prepareStatement("SELECT * FROM Likes WHERE idPosts = ?");
		buscaLikes.setInt(1, idPosts);
		ResultSet resultado = buscaLikes.executeQuery();
		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idLikes") + " ");
			System.out.println("idUsuario: " + resultado.getInt("idUsuarios") + " ");
			System.out.println("idPost: " + resultado.getInt("idPost") + " ");
			System.out.println();
		}
		resultado.close();
		buscaLikes.close();
	}

	// EMPIEXAN LOS INSERTS

	/**
	 * Inserta un nuevo like
	 * @param conn
	 * @param l
	 * @throws SQLException
	 */
	public static void insertaLike(Connection conn, ClsLike l) throws SQLException {

		String insercion = "INSERT INTO Likes (idUsuarios, idPost) VALUES (?, ?)";

		PreparedStatement insercionLike = conn.prepareStatement(insercion);

		insercionLike.setInt(1, l.getIdUsuarios());
		insercionLike.setInt(2, l.getIdPost());

		insercionLike.executeUpdate();

		insercionLike.close();

	}

	// EMPIEZAN LOS DELETE

	/**
	 * Borra la tabla de likes
	 * @param conn
	 * @throws SQLException
	 */
	public static void borradoTablaLikes(Connection conn) throws SQLException {

		String query = "DROP TABLE IF EXISTS Likes";

		PreparedStatement borraTabla = conn.prepareStatement(query);

		borraTabla.executeUpdate();
		borraTabla.close();

	}
	
	/**
	 * Borra todos los likes
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static int borradoLikes(Connection conn) throws SQLException {

		int filasAfectadas = 0;
		String query = "DELETE FROM Likes";
	
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		
		return filasAfectadas;

	}
	
	
	
	/**
	 * Borra un like por su id
	 * @param conn
	 * @param idLike
	 * @return
	 * @throws SQLException
	 */
	public static int borradoLikesIdLike(Connection conn, int idLike) throws SQLException {
		int filasAfectadas = 0;
		selectLikesIdLikes(conn, idLike);
		String query = "DELETE FROM Likes WHERE idLikes = ? ";
	
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		borraTabla.setInt(1, idLike);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		
		
		
		return filasAfectadas;
	}
	
	/**
	 * Borra los likes de un post
	 * @param conn
	 * @param idPost
	 * @return
	 * @throws SQLException
	 */
	public static int borradoLikesIdPost(Connection conn, int idPost) throws SQLException {
		int filasAfectadas = 0;
		selectLikesIdPosts(conn, idPost);
		String query = "DELETE FROM Likes WHERE idPost = ? ";
	
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		borraTabla.setInt(1, idPost);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		
		
		
		return filasAfectadas;
	}
	
	/**
	 * Borra los likes de un usuario
	 * @param conn
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public static int borradoLikesUsername(Connection conn, String username) throws SQLException {
		int filasAfectadas = 0;
		selectLikesUsername(conn, username);
		String query = "DELETE FROM Likes WHERE IdUsuarios = (SELECT IdUsuario FROM Usuarios WHERE username = ?);";
	
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		borraTabla.setString(1, username);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		
		
		
		return filasAfectadas;
	}
	

}
