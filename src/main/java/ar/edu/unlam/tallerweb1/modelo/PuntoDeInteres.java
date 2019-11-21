package ar.edu.unlam.tallerweb1.modelo;
import com.google.maps.model.Photo;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PuntoDeInteres")
public class PuntoDeInteres {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "direccion")
  private String direccion;

  @Column(name = "descripcion")
  private String descripcion;

  @Column(name = "placeId")
  private String placeId;

  @Column(name = "ciudad")
  private String ciudad;

  @Column(name = "icon")
  private URL icon;

  @Column(name = "rating")
  private Integer rating;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn
  private Destino destino;

  @Column(name = "photoReferences")
  public String photoReferences;

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

  public String getPlaceId() {
    return placeId;
  }

  public void setPlaceId(String placeId) {
    this.placeId = placeId;
  }

  public String getCiudad() {
    return ciudad;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public URL getIcon() {
    return icon;
  }

  public void setIcon(URL icon) {
    this.icon = icon;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public Destino getDestino() {
    return destino;
  }

  public void setDestino(Destino destino) {
    this.destino = destino;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getPhotoReferences() {
    return photoReferences;
  }

  public void setPhotoReferences(String photoReferences) {
    this.photoReferences = photoReferences;
  }
}
