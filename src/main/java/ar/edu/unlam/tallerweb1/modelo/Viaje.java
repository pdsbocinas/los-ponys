package ar.edu.unlam.tallerweb1.modelo;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Viaje")
public class Viaje {

    public Viaje() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @Column(name = "fechaFin")
    private Date fechaFin;

    @OneToMany(cascade = javax.persistence.CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Destino> destinos = new ArrayList<>();

/*    @OneToMany(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<Comentario> comentarios = new ArrayList<>();*/

    @ManyToMany(fetch=FetchType.EAGER)
    @Cascade(CascadeType.PERSIST)
    @JoinTable(name = "viaje_usuario")
    private List<Usuario> usuarios;

    @Column(name = "privacidad")
    private String privacidad;

/*    public void agregarComentario(Comentario comentario){
        // comentario.setViaje(this);
        comentarios.add(comentario);
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }*/

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Destino> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<Destino> destinos) {
        this.destinos = destinos;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }
}
