package base.datos;
import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		
		
		Connection conn = Conexion.conecta();
		
		CreacionModificacion.creaTablaUsuarios(conn);
		
		CreacionModificacion.creaTablaPosts(conn);
		
		CreacionModificacion.creaTablaLikes(conn);
		

	}

}
