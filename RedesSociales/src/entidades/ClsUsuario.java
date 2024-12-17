package entidades;

public class ClsUsuario {
	// Atributos
	private int idUsuario;
	private String nombre;
	private String apellidos;
	private String username;
	private String password;
	private String email;

	// Propiedades
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdUsuario() {
		return idUsuario;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	// constructores
	public ClsUsuario() {

	}

	public ClsUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public ClsUsuario(int idUsuario, String nombre, String apellidos, String username, String password, String email) {
		this.idUsuario = idUsuario;
		if (nombre == null || nombre.equals("")) {
			throw new IllegalArgumentException("El nombre no puede ser vacío o nulo.");
		} else {
			this.nombre = nombre;
		}
		if (apellidos == null || apellidos.equals("")) {
			throw new IllegalArgumentException("Los apellidos no pueden ser vacíos o nulos.");
		} else {
			this.apellidos = apellidos;
		}
		if (username == null || username.equals("")) {
			throw new IllegalArgumentException("El username no puede ser vacío o nulo.");
		} else {
			this.username = username;
		}
		if (password == null |password.equals("")) {
			throw new IllegalArgumentException("La constraseña no puede ser vacía o nula.");
		} else {
			this.password = password;
		}
		if (email == null || email.equals("")) {
			throw new IllegalArgumentException("El email no puede ser vacío o nulo.");
		} else {
			this.email = email;
		}
	}

	public ClsUsuario(String nombre, String apellidos, String username, String password, String email) {
		if (nombre == null || nombre.equals("")) {
			throw new IllegalArgumentException("El nombre no puede ser vacío o nulo.");
		} else {
			this.nombre = nombre;
		}
		if (apellidos == null || apellidos.equals("")) {
			throw new IllegalArgumentException("Los apellidos no pueden ser vacíos o nulos.");
		} else {
			this.apellidos = apellidos;
		}
		if (username == null || username.equals("")) {
			throw new IllegalArgumentException("El username no puede ser vacío o nulo.");
		} else {
			this.username = username;
		}
		if (password == null |password.equals("")) {
			throw new IllegalArgumentException("La constraseña no puede ser vacía o nula.");
		} else {
			this.password = password;
		}
		if (email == null || email.equals("")) {
			throw new IllegalArgumentException("El email no puede ser vacío o nulo.");
		} else {
			this.email = email;
		}
	}

}
