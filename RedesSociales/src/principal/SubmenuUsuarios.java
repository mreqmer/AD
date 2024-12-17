package principal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import base.datos.ConsultasUsuario;
import base.datos.Utiles;
import entidades.ClsUsuario;

public class SubmenuUsuarios {

	static Scanner sc = new Scanner(System.in);

	/**
	 * Subopción para listar los usuarios
	 * @param conn
	 */
	public static void listadoUsuarios(Connection conn) {

		int subOpc = 0;
		int id = 0;
		String username = "";

		System.out.println("¿Que quiere buscar?");
		System.out.println("1. Todos");
		System.out.println("2. Buscar por id");
		System.out.println("3. Buscar por username");
		subOpc = sc.nextInt();

		switch (subOpc) {

		case 1 -> {
			try {
				ConsultasUsuario.selectUsuario(conn);
			} catch (SQLException e) {
				System.out.println("No se encontraron usuarios");
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
	 * Insercion de usuario con comprobaciones
	 * 
	 * @param conn
	 */
	public static void insercionUsuarios(Connection conn) {

		String nombre;
		String apellidos;
		String username;
		String password;
		String email;

		System.out.println("Nombre: ");
		nombre = sc.nextLine();

		System.out.println("Apellidos: ");
		apellidos = sc.nextLine();

		System.out.println("Nombre de usuario (Username): ");
		username = sc.nextLine();

		System.out.println("Contraseña: ");
		password = sc.nextLine();

		System.out.println("Email: ");
		email = sc.nextLine();

		// Los campos no pueden estar vacios
		try {
			ClsUsuario p = new ClsUsuario(nombre, apellidos, username, password, email);
			
			if(!ConsultasUsuario.existeUsuarioUsername(conn, username)) {
				ConsultasUsuario.insertUsuario(conn, p);
				System.out.println("Usuario insertado correctamente.");
			}else {
				System.out.println("Ya existe un usuario con ese username");
			}
			
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		} catch (SQLException e) {

			System.out.println("No se pudieron insertar los datos.");
		}

	}

	/**
	 * Submenu del main para el update de un usuario si existe
	 * 
	 * @param conn
	 */
	public static void updateUsuario(Connection conn) {

		String originalUsername;
		String nombre;
		String apellidos;
		String username;
		String password;
		String email;
		String confirmar;

		try {
			System.out.println("¿Que usuario desea modificar?");
			originalUsername = sc.nextLine();
			
			if (ConsultasUsuario.existeUsuarioUsername(conn, originalUsername)) {

				System.out.println("Nombre: ");
				nombre = sc.nextLine();

				System.out.println("Apellidos: ");
				apellidos = sc.nextLine();

				System.out.println("Nombre de usuario (Username): ");
				username = sc.nextLine();

				System.out.println("Contraseña: ");
				password = sc.nextLine();

				System.out.println("Email: ");
				email = sc.nextLine();

				ClsUsuario u = new ClsUsuario(nombre, apellidos, username, password, email);

				Utiles.abrirTransaccion(conn);
				ConsultasUsuario.updateUsuario(conn, u, originalUsername);

				do {
					System.out.println("¿Desea guardar los cambios? (S/N)");
					confirmar = sc.nextLine().toUpperCase();
					switch (confirmar) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Usuario updateado correctamente.");
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
				System.out.println("El usuario no existe.");
			}
		} catch (SQLException e) {
			System.out.println("Error." + e);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Submenu del main para el borrado de un usuario si existe
	 * 
	 * @param conn
	 */
	public static void borradoUsuarios(Connection conn) {

		int subOpc;
		String nombreColumna = "";
		String dato;
		int id;
		String confirmacion = "";
		int filasAfectadas = 0;

		System.out.println("1.Borrar tabla");
		System.out.println("2.Borrar TODOS los usuarios");
		System.out.println("3. Borrar un usuario por id.");
		System.out.println("4. Borrar un usuario por un campo.");

		subOpc = sc.nextInt();
		sc.nextLine();

		switch (subOpc) {
		case 1 -> {

			try {

				ConsultasUsuario.deleteTablaUsuario(conn);

			} catch (SQLException e) {
				System.out.println("No se pudo borrar la tabla.");
			}

		}
		case 2 -> {

			try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasUsuario.deletAllUsuarios(conn);
				System.out.println(filasAfectadas + " filas Afectadas");
				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Usuarios borrados correctamente.");
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
				System.out.println("No se pudo borrar la tabla");
			}

		}
		case 3 -> {
			
			System.out.println("¿Que usuario desea borrar?");
			id = sc.nextInt();
			sc.nextLine();
			
			try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasUsuario.deleteUsuarios(conn, "idUsuario", id);
				System.out.println(filasAfectadas + " filas Afectadas");
				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Usuarios borrados correctamente.");
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
				System.out.println("No se pudo borrar");
			}

		}case 4 ->{
			
			System.out.println("¿Por que campo desea filtrar?");
			nombreColumna = sc.nextLine();
			
			System.out.println("¿Que usuario desea borrar?");
			dato = sc.nextLine();

			try {
				Utiles.abrirTransaccion(conn);
				filasAfectadas = ConsultasUsuario.deleteUsuarios(conn, nombreColumna, dato);
				System.out.println(filasAfectadas + " filas Afectadas");
				do {
					System.out.println("¿Quiere guardar los cambios? (S/N)");
					confirmacion = sc.nextLine().toUpperCase();

					switch (confirmacion) {
					case "S" -> {
						Utiles.hacerCommit(conn);
						System.out.println("Usuario borrados correctamente.");
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
				System.out.println("No se pudo borrar la tabla");
			}
			
		}default -> {
			System.out.println("Opción no válida.");
		}
		}

	}
}
