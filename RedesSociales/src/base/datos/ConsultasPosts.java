package base.datos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entidades.ClsPost;

public class ConsultasPosts {

	// EMPIEZAN LOS SELECT

	public static boolean existePostId(Connection conn, int idPost) throws SQLException {

		boolean existe = false;

		PreparedStatement buscaPostId = conn.prepareStatement("SELECT idPost FROM Posts WHERE idPost = ?;");
		buscaPostId.setInt(1, idPost);
		ResultSet resultado = buscaPostId.executeQuery();

		if (resultado.next()) {
			existe = true;
		}

		resultado.close();
		buscaPostId.close();

		return existe;
	}

	/**
	 * Lista todos los posts de la base de datos
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public static void selectPost(Connection conn) throws SQLException {

		PreparedStatement buscaPost = conn.prepareStatement("SELECT * FROM Posts;");
		ResultSet resultado = buscaPost.executeQuery();

		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idPost") + " ");
			System.out.println("idUsuario: " + resultado.getString("idUsuario") + " ");
			System.out.println("created_at: " + resultado.getDate("created_at").toString() + " ");
			System.out.println("updated_at: " + resultado.getDate("updated_at").toString() + " ");
			System.out.println();
		}

		resultado.close();
		buscaPost.close();

	}

	/**
	 * Busca un post por su id
	 * 
	 * @param conn
	 * @param idPost
	 * @throws SQLException
	 */
	public static void selectPostId(Connection conn, int idPost) throws SQLException {

		PreparedStatement buscaPost = conn.prepareStatement("SELECT * FROM Posts WHERE idPost = ?;");
		buscaPost.setInt(1, idPost);
		ResultSet resultado = buscaPost.executeQuery();

		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idPost") + " ");
			System.out.println("idUsuario: " + resultado.getString("idUsuario") + " ");
			System.out.println("created_at: " + resultado.getDate("created_at").toString() + " ");
			System.out.println("updated_at: " + resultado.getDate("updated_at").toString() + " ");
			System.out.println();
		}

		resultado.close();
		buscaPost.close();

	}
	
	public static ClsPost selectPostIdObjeto(Connection conn, int idPost) throws SQLException {

		int id;
		int idUsuario;
		Date createdAt;
		Date updatedAt;
		ClsPost p = null;
		PreparedStatement buscaPost = conn.prepareStatement("SELECT * FROM Posts WHERE idPost = ?;");
		buscaPost.setInt(1, idPost);
		ResultSet resultado = buscaPost.executeQuery();
		
		
		while (resultado.next()) {
			id = resultado.getInt("idPost");
			idUsuario = resultado.getInt("idUsuario");
			createdAt = resultado.getDate("created_at");
			updatedAt = resultado.getDate("updated_at");
			p = new ClsPost(id, idUsuario, createdAt, updatedAt);
		}

		resultado.close();
		buscaPost.close();
		
		return p;

	}

	/**
	 * Lista los posts de un usuario
	 * 
	 * @param conn
	 * @param idUsuario
	 * @throws SQLException
	 */
	public static void selectPostIdUsuario(Connection conn, int idUsuario) throws SQLException {

		PreparedStatement buscaPost = conn.prepareStatement("SELECT * FROM Posts WHERE idUsuario = ?;");
		buscaPost.setInt(1, idUsuario);
		ResultSet resultado = buscaPost.executeQuery();

		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idPost") + " ");
			System.out.println("idUsuario: " + resultado.getString("idUsuario") + " ");
			System.out.println("created_at: " + resultado.getDate("created_at").toString() + " ");
			System.out.println("updated_at: " + resultado.getDate("updated_at").toString() + " ");
			System.out.println();
		}

		resultado.close();
		buscaPost.close();

	}
	
	/**
	 * Lista los posts de un usuario
	 * @param conn
	 * @param username
	 * @throws SQLException
	 */
	public static void selectPostUsername(Connection conn, String username) throws SQLException {
		
		String query = "SELECT * FROM Posts AS P JOIN Usuarios AS U ON P.IdUsuario = U.IdUsuario WHERE username = ?;";
		
		PreparedStatement buscaPost = conn.prepareStatement(query);
		buscaPost.setString(1, username);
		ResultSet resultado = buscaPost.executeQuery();

		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idPost") + " ");
			System.out.println("idUsuario: " + resultado.getString("idUsuario") + " ");
			System.out.println("created_at: " + resultado.getDate("created_at").toString() + " ");
			System.out.println("updated_at: " + resultado.getDate("updated_at").toString() + " ");
			System.out.println();
		}

		resultado.close();
		buscaPost.close();
	}

	/**
	 * Busca posts por fecha de creacion
	 * 
	 * @param conn
	 * @param fechaCreacion
	 * @throws SQLException
	 */
	public static void selectPostsCreatedDate(Connection conn, Date fechaCreacion) throws SQLException {
		PreparedStatement buscaPost = conn.prepareStatement("SELECT * FROM Posts WHERE created_at = ?;");
		buscaPost.setDate(1, fechaCreacion);
		ResultSet resultado = buscaPost.executeQuery();
		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idPost") + " ");
			System.out.println("idUsuario: " + resultado.getString("idUsuario") + " ");
			System.out.println("created_at: " + resultado.getDate("created_at").toString() + " ");
			System.out.println("updated_at: " + resultado.getDate("updated_at").toString() + " ");
			System.out.println();
		}
		resultado.close();
		buscaPost.close();

	}

	/**
	 * Busca posts por fecha de update
	 * 
	 * @param conn
	 * @param fechaUpdate
	 * @throws SQLException
	 */
	public static void selectPostsUpdateDate(Connection conn, Date fechaUpdate) throws SQLException {
		PreparedStatement buscaPost = conn.prepareStatement("SELECT * FROM Posts WHERE updated_at = ?;");
		buscaPost.setDate(1, fechaUpdate);
		ResultSet resultado = buscaPost.executeQuery();
		while (resultado.next()) {
			System.out.println("id: " + resultado.getInt("idPost") + " ");
			System.out.println("idUsuario: " + resultado.getString("idUsuario") + " ");
			System.out.println("created_at: " + resultado.getDate("created_at").toString() + " ");
			System.out.println("updated_at: " + resultado.getDate("updated_at").toString() + " ");
			System.out.println();
		}
		resultado.close();
		buscaPost.close();

	}

	// EMPIEZAN LOS INSERT

	/**
	 * Inserta un nuevo post
	 * @param conn
	 * @param p
	 * @throws SQLException
	 */
	public static void insertaPost(Connection conn, ClsPost p) throws SQLException {

		String insercion = "INSERT INTO Posts (idUsuario, created_at, updated_at) VALUES (?, ?, ?)";

		PreparedStatement insercionPost = conn.prepareStatement(insercion);

		insercionPost.setInt(1, p.getIdUsuario());
		insercionPost.setDate(2, p.getCreatedAt());
		insercionPost.setDate(3, p.getUpdatedAt());

		insercionPost.executeUpdate();

		insercionPost.close();

	}
	
	//EMPIEZAN LOS UPDATE
	
	/**
	 * hace update del creador de un post
	 * @param conn
	 * @param p
	 * @throws SQLException
	 */
	public static void updatePost(Connection conn, ClsPost p)throws SQLException {
		
		int idPost = p.getIdPost();
		int idUsuario = p.getIdUsuario();
		Date updatedAt = new java.sql.Date(System.currentTimeMillis());
		
		String update = "UPDATE Posts SET idUsuario = ?, updated_at = ? WHERE idPost = ?";
		
		PreparedStatement updatePost = conn.prepareStatement(update);
		
		updatePost.setInt(1, idUsuario);
		updatePost.setDate(2, updatedAt);
		updatePost.setInt(3, idPost);
		
		updatePost.executeUpdate();
		updatePost.close();
		
		selectPostId(conn, idPost);
	}
	
	//EMPIEZAN LOS DELETE
	
	/**
	 * Borra la tabla Posts
	 * @param conn
	 * @throws SQLException
	 */
	public static void deleteTablePosts(Connection conn) throws SQLException {
	
		String query = "DROP TABLE IF EXISTS Posts";
		
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		borraTabla.executeUpdate();
		borraTabla.close();

	}
	
	/**
	 * Borra todos los posts
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static int  deleteAllPosts(Connection conn) throws SQLException {
		int filasAfectadas = 0;
		String query = "DELETE FROM Posts";
		
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		
		return filasAfectadas;
	}
	
	/**
	 * Borra los posts por el id de post
	 * @param conn
	 * @param idPost
	 * @return
	 * @throws SQLException
	 */
	public static int  deletePostsId(Connection conn, int idPost) throws SQLException {
		int filasAfectadas = 0;
		selectPostId(conn, idPost);
		String query = "DELETE FROM Posts WHERE idPost = ?";
		
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		borraTabla.setInt(1, idPost);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		
		return filasAfectadas;
	}
	
	/**
	 * Borra un post por id de usuario
	 * @param conn
	 * @param idUsuario
	 * @return
	 * @throws SQLException
	 */
	public static int deletePostsUserId(Connection conn, int idUsuario) throws SQLException {
		int filasAfectadas = 0;
		selectPostIdUsuario(conn, idUsuario);
		String query = "DELETE FROM Posts WHERE idUsuario = ?";
		
		
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		borraTabla.setInt(1, idUsuario);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		
		return filasAfectadas;
	}
	
	public static int deletePostUsername(Connection conn, String username) throws SQLException {
		int filasAfectadas = 0;
		selectPostUsername(conn, username);
		String query = "DELETE FROM Posts WHERE IdUsuario = (SELECT IdUsuario FROM Usuarios WHERE username = ?);";
	
		PreparedStatement borraTabla = conn.prepareStatement(query);
		
		borraTabla.setString(1, username);
		
		filasAfectadas = borraTabla.executeUpdate();
		borraTabla.close();
		
		
		
		return filasAfectadas;
	}
	

}
