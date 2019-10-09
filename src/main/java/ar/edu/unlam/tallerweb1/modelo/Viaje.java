package ar.edu.unlam.tallerweb1.modelo;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Viaje")
public class Viaje {

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

    @OneToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="viaje_id")
    private List<Destino> destinos;

    @ManyToMany(cascade=CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "viaje_usuario",
        joinColumns = @JoinColumn(name = "viaje_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios;

    public Viaje(String titulo, Date fechaInicio, Date fechaFin, List<Destino> destinos, List<Usuario> usuarios) {
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.destinos = destinos;
        this.usuarios = usuarios;
    }
    public Viaje() {

    }

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
}
