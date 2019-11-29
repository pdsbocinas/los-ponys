package ar.edu.unlam.tallerweb1.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.net.URL;
import java.util.Date;

@Entity
@Table(name = "Alojamiento")
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    private Float latitud;

    private Float longitud;

    private String descripcion;

    private URL url;

    private URL imagen;

    private String email;

    private Integer rating;

    private Integer precio;

    private Integer descuento;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Pais pais;

    @ManyToOne
    private CategoriaAlojamiento categoria;

    @ManyToOne
    private TipoDeViaje tipo;

    private boolean bookeable;

    private Date desde;

    private Date hasta;

    private Integer piezas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public CategoriaAlojamiento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAlojamiento categoria) {
        this.categoria = categoria;
    }

    public boolean getBookeable() {
        return bookeable;
    }

    public void setBookeable(boolean bookeable) {
        this.bookeable = bookeable;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getPiezas() {
        return piezas;
    }

    public void setPiezas(Integer piezas) {
        this.piezas = piezas;
    }

    public URL getImagen() {
        return imagen;
    }

    public void setImagen(URL imagen) {
        this.imagen = imagen;
    }

    public TipoDeViaje getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeViaje tipo) {
        this.tipo = tipo;
    }
}
