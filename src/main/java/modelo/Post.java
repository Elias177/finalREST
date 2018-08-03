package modelo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class Post {

    private Long id;

    private String texto;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp tiempo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Timestamp getTiempo() {
        return tiempo;
    }

    public void setTiempo(Timestamp tiempo) {
        this.tiempo = tiempo;
    }
    public Post() {
    }
}
