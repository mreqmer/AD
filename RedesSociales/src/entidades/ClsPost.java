package entidades;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClsPost {

	private int idPost;
	private int idUsuario;
	private Date createdAt;
	private Date updatedAt;




    public ClsPost(int idPost, int idUsuario, Date createdAt, Date updatedAt){
        this.idPost = idPost;
        this.idUsuario = idUsuario;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public ClsPost(int idUsuario) throws ParseException {
        this.idUsuario = idUsuario;
        this.createdAt = new java.sql.Date(System.currentTimeMillis());
        this.updatedAt = new java.sql.Date(System.currentTimeMillis());
    }
   


    public int getIdPost() {
        return idPost;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public Date getUpdatedAt() {
        return updatedAt;
    }


	}

