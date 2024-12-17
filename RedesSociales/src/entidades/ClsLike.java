package entidades;

public class ClsLike {
	
	int idLike;
	int idUsuarios;
	int idPost;
	
	public ClsLike(int idLike, int idUsuarios, int idPost) {
		this.idLike = idLike;
		this.idUsuarios = idUsuarios;
		this.idPost = idPost;
	}
	
	public ClsLike(int idUsuarios, int idPost) {
		this.idUsuarios = idUsuarios;
		this.idPost = idPost;
	}

	public int getIdLike() {
		return idLike;
	}

	public int getIdUsuarios() {
		return idUsuarios;
	}

	public int getIdPost() {
		return idPost;
	}
	
	

}
