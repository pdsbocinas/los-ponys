package ar.edu.unlam.tallerweb1.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Destino")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    // normalizar
    //@Column(name = "region")
    //private String region;

    @Column(name = "placeId")
    private String placeId;

    // normalizar
    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "icon")
    private URL icon;

    // @ManyToOne
    // @JsonIgnore
    // private Reserva reserva;

    @Column(name = "photoReferences")
    public String photoReferences;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "fechaInicio")
    private Date fechaInicio;

    @Column(name = "fechaHasta")
    private Date fechaHasta;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

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

    //public Pais getPais() {
    //    return pais;
    //}

    //public void setPais(Pais pais) {
    //    this.pais = pais;
    //}

    //public String getRegion() {
    //    return region;
    //}

    //public void setRegion(String region) {
    //     this.region = region;
    //  }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    //public Reserva getReserva() {
    //    return reserva;
    //}

    //public void setReserva(Reserva reserva) {
    //    this.reserva = reserva;
    //}

    public URL getIcon() {
        return icon;
    }

    public void setIcon(URL icon) {
        this.icon = icon;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaFin(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getPhotoReferences() {
        return photoReferences;
    }

    public void setPhotoReferences(String photoReferences) {
        this.photoReferences = photoReferences;
    }
    
}