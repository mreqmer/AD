package principal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import base.datos.ConsultasPosts;
import base.datos.ConsultasUsuario;
import base.datos.Utiles;
import entidades.ClsPost;
import entidades.ClsUsuario;

public class SubmenuPosts {

	static Scanner sc = new Scanner(System.in);

	/**
	 * Opcion del menu  para los listados
	 * @param conn
	 */
	public static void listadoPosts(Connection conn) {

		int subOpc = 0;
		int id = 0;
		long dia;
		long mes;
		long ano;
		String fecha = "";
		String username = "";

		System.out.println("¿Que quiere buscar?");
		System.out.println("1. Todos");
		System.out.println("2. Buscar por id de Post");
		System.out.println("3. Buscar id de usuario");
		System.out.println("4. Buscar fecha de creacion");
		System.out.println("5. Buscar fecha de actualizacion");
		subOpc = sc.nextInt();

		switch (subOpc) {

		case 1 -> {
			try {
				ConsultasPosts.selectPost(conn);
			} catch (SQLException e) {
				System.out.println("No se encontraron Posts");
			}
		}
		case 2 -> {
			try {
				System.out.println("Introduce el id: ");
				id = sc.nextInt();
				ConsultasPosts.selectPostId(conn, id);
			} catch (SQLException e) {
				System.out.println("Error");
			}
		}
		case 3 -> {
			try {
				System.out.println("Introduce el id: ");
				id = sc.nextInt();
				ConsultasPosts.selectPostIdUsuario(conn, id);
			} catch (SQLException e) {
				System.out.println("Error");
			}
		}
		case 4 -> {
			try {
				// TODO
				System.out.println("Introduce la fecha de creacion: ");
				System.out.println("Dia: ");
				fecha = sc.nextLine();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					java.util.Date fechaUtil = sdf.parse(fecha);
					Date fechaSql = new Date(fechaUtil.getTime());
				} catch (Exception e) {

					System.out.println("Fecha invalida");
				}

				ConsultasPosts.selectPostIdUsuario(conn, id);
			} catch (SQLException e) {
				System.out.println("Error");
			}
		}

		}

		sc.close();

	}

	/**
	 * Opcion del menu para insertar un post
	 * 
	 * @param conn
	 */
	public static void insercionPost(Connection conn) {

		int idUsuario;

		System.out.println("Ingrese el ID del usuario que lo creo:");
		idUsuario = sc.nextInt();
		sc.nextLine();

		try {
			if (ConsultasUsuario.existeUsuarioId(conn, idUsuario)) {

				try {
					ClsPost post = new ClsPost(idUsuario);
					ConsultasPosts.insertaPost(conn, post);
					System.out.println("Post insertado correctamente.");
				} catch (Exception e) {
					System.out.println("Error al crear el post: ");
				}

			} else {
				System.out.println("Ese usuario no existe.");
			}
		} catch (SQLException e) {
			System.out.println("Error con el id");
		} finally {
			sc.close();
		}

	}

	/**
	 * subopcion del menú para updatear un post
	 * 
	 * @param conn
	 */
	public static void updatePost(Connection conn) {

		int idPost;
		int idUsuario;
		String confirmar;

		System.out.println("Ingrese el Id del post a modificar");
		idPost = sc.nextInt();
		sc.nextLine();

		try {

			if (ConsultasPosts.existePostId(conn, idPost)) {
				ClsPost p = ConsultasPosts.selectPostIdObjeto(conn, idPost);
				System.out.println("Nuevo creador del post: ");
				idUsuario = sc.nextInt();
				sc.nextLine();

				if (ConsultasUsuario.existeUsuarioId(conn, idUsuario)) {

					p.setIdUsuario(idUsuario);

					Utiles.abrirTransaccion(conn);
					ConsultasPosts.updatePost(conn, p);

					do {
						System.out.println("¿Desea guardar los cambios? (S/N)");
						confirmar = sc.nextLine().toUpperCase();
						switch (confirmar) {
						case "S" -> {
							Utiles.hacerCommit(conn);
							System.out.println("Post updateado correctamente.");
						}
						case "N" -> {
							Utiles.hacerRollback(conn);
							System.out.println("Cambios descartados.");
						}
						default -> {
							System.out.println("Debe introducir S para confirmar o N para deshacer cambios.");
						}
						}
					} while (!confirmar.equals("S") && !confirmar.equals("N"));

				} else {
					System.out.println("Ese post no existe");
				}
			} else {
				System.out.println("Ese post no existe.");
			}
		} catch (SQLException e) {
			System.out.println("Error.");
		}

	}

	/**
	 * Opcion del menú para borrar un post
	 * @param conn
	 */
	public static void borradoPost(Connection conn) {

		int id;
		int filasAfectadas;
		int subOpc;
		String username;
		String confirmacion;

		System.out.println("1.Borrar tabla");
		System.out.println("2.Borrar TODOS los posts");
		System.out.println("3. Borrar por id.");
		System.out.println("4. Borrar por idUsuario");
		System.out.println("5. Borrar por username");

		subOpc = sc.nextInt();
		sc.nextLine();

		switch (subOpc) {

		case 1 -> {
			try {

				ConsultasPosts.deleteTablePosts(conn);

			} catch (SQLException e) {
				System.out.println("Error.");
			}

		}
		case 2 -> {

			try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasPosts.deleteAllPosts(conn);
				System.out.println(filasAfectadas + " filas Afectadas");

				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Posts borrados correctamente.");
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

			System.out.println("Introduce el id del post:");
			id = sc.nextInt();
			sc.nextLine();

			try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasPosts.deletePostsId(conn, id);
				System.out.println(filasAfectadas + " filas Afectadas");

				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Posts borrados correctamente.");
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

			System.out.println("Introduce el id del usuario:");
			id = sc.nextInt();
			sc.nextLine();

			try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasPosts.deletePostsUserId(conn, id);
				System.out.println(filasAfectadas + " filas Afectadas");

				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Posts borrados correctamente.");
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

		}case 5 ->{
			
			System.out.println("Introduce el username:");
			username = sc.nextLine();
		

			try {
				Utiles.abrirTransaccion(conn);
				
				if(ConsultasUsuario.existeUsuarioUsername(conn, username)) {
					
					filasAfectadas = ConsultasPosts.deletePostUsername(conn, username);
					
					System.out.println(filasAfectadas + " filas Afectadas");
					
					do {
						System.out.println("¿Quiere guardar los cambios? (S/N)");
						confirmacion = sc.nextLine().toUpperCase();

						switch (confirmacion) {
						case "S" -> {
							Utiles.hacerCommit(conn);
							System.out.println("Posts borrados correctamente.");
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
					
				}else {
					System.out.println("No existe ese usuario");
				}

				

			} catch (SQLException e) {
				System.out.println("Error");
			}
			
		}
		default -> {
			System.out.println("Opción no válida");
		}
		}
	}
}
