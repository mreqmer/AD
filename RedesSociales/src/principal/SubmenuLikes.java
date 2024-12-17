package principal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import base.datos.ConsultasLikes;
import base.datos.ConsultasPosts;
import base.datos.ConsultasUsuario;
import base.datos.Utiles;
import entidades.ClsLike;
import entidades.ClsPost;

public class SubmenuLikes {

	static Scanner sc = new Scanner(System.in);

	// logica del menu likes
	public static void listadoLikes(Connection conn) {

		int subOpc = 0;
		int id = 0;
		String username = "";

		System.out.println("¿Que quiere buscar?");
		System.out.println("1. Todos");
		System.out.println("2. Buscar por id");
		System.out.println("3. Buscar por id de usuario");
		System.out.println("4. Buscar por id de Post");
		subOpc = sc.nextInt();

		switch (subOpc) {

		case 1 -> {
			try {
				base.datos.ConsultasLikes.selectLikes(conn);
			} catch (SQLException e) {
				System.out.println("No se encontraron Likes");
			}
		}
		case 2 -> {
			try {
				System.out.println("Introduce el id: ");
				id = sc.nextInt();

				ConsultasUsuario.selectUsuario(conn, id);
			} catch (SQLException e) {
				System.out.println("Error");
			}
		}
		case 3 -> {
			try {
				System.out.println("Introduce el username: ");
				sc.nextLine();
				username = sc.nextLine();
				ConsultasUsuario.selectUsuario(conn, username);
			} catch (SQLException e) {
				System.out.println("Error");
			}
		}

		}

	}
	
	/**
	 * Submenu para el main para insertar un nuevo like
	 * @param conn
	 */
	public static void insercionLikes(Connection conn) {
		
		int idPost;
		int idUsuario;
		
		System.out.println("Usuario que creó el post: ");
		idUsuario = sc.nextInt();
		
		try {
			if(ConsultasUsuario.existeUsuarioId(conn, idUsuario)) {
				
				System.out.println("Post del like: ");
				idPost = sc.nextInt();
				
				if(ConsultasPosts.existePostId(conn, idPost)) {
					
					ClsLike l = new ClsLike(idUsuario, idPost);
					ConsultasLikes.insertaLike(conn, l);
					System.out.println("Like creado correctamente");
				}else {
					System.out.println("No existe el post.");
				}
			}else {
				System.out.println("No existe el usuario.");
			}
		} catch (SQLException e) {
			System.out.println("Error.");
		}
	
	}
	
	public static void borraLikes(Connection conn) {
		
		int subOpc;
		int filasAfectadas;
		int id;
		String idUsuario;
		String confirmacion;
		String username;
		
		System.out.println("¿Como desea borrar?");
		System.out.println("1. Borrar tabla");
		System.out.println("2. Borrar todos los likes");
		System.out.println("3. Borrar por id");
		System.out.println("4. Borrar por idPost");
		System.out.println("5. Borrar por username");
		subOpc = sc.nextInt();
		sc.nextLine();
		
		switch (subOpc) {
	    case 1 -> {
	    	
	    	try {

				ConsultasLikes.borradoTablaLikes(conn);

			} catch (SQLException e) {
				System.out.println("Error.");
			}
	    }
	    case 2 -> {
	    	
	    	try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasLikes.borradoLikes(conn);
				System.out.println(filasAfectadas + " filas Afectadas");

				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Borrado correcto.");
					}
					case "N" -> {
						Utiles.hacerRollback(conn);
						System.out.println("Cambios descartados.");
					}
					default -> {
						System.out.println("Debe introducir S para confirmar o N para deshacer cambios.");
					}
					}
				} while (!confirmacion.equals("S") && !confirmacion.equals("N"));

			} catch (SQLException e) {
				System.out.println("Error");
			}
	    }
	    case 3 -> {
	    	
	    	System.out.println("Like a borrar: ");
	    	id = sc.nextInt();
	    	sc.nextLine();
	    	
	    	try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasLikes.borradoLikesIdLike(conn, id);
				System.out.println(filasAfectadas + " filas Afectadas");

				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Borrado correcto.");
					}
					case "N" -> {
						Utiles.hacerRollback(conn);
						System.out.println("Cambios descartados.");
					}
					default -> {
						System.out.println("Debe introducir S para confirmar o N para deshacer cambios.");
					}
					}
				} while (!confirmacion.equals("S") && !confirmacion.equals("N"));

			} catch (SQLException e) {
				System.out.println("Error");
			}
	    	
	    }
	    case 4 -> {
	    	
	    	System.out.println("id del Post para borrar los likes:  ");
	    	id = sc.nextInt();
	    	sc.nextLine();
	    	
	    	try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasLikes.borradoLikesIdPost(conn, id);
				System.out.println(filasAfectadas + " filas Afectadas");

				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Borrado correcto.");
					}
					case "N" -> {
						Utiles.hacerRollback(conn);
						System.out.println("Cambios descartados.");
					}
					default -> {
						System.out.println("Debe introducir S para confirmar o N para deshacer cambios.");
					}
					}
				} while (!confirmacion.equals("S") && !confirmacion.equals("N"));

			} catch (SQLException e) {
				System.out.println("Error");
			}
	    	
	    }
	    case 5 -> {
	    	
	    	System.out.println("username para borrar los likes:  ");
	    	username = sc.nextLine();
	    	
	    	try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasLikes.borradoLikesUsername(conn, username);
				System.out.println(filasAfectadas + " filas Afectadas");

				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Borrado correcto.");
					}
					case "N" -> {
						Utiles.hacerRollback(conn);
						System.out.println("Cambios descartados.");
					}
					default -> {
						System.out.println("Debe introducir S para confirmar o N para deshacer cambios.");
					}
					}
				} while (!confirmacion.equals("S") && !confirmacion.equals("N"));

			} catch (SQLException e) {
				System.out.println("Error");
			}
	    	
	    }
	    default -> {
	    	
	    	System.out.println("Opción no válida.");
	    }
	}
		
	}
}