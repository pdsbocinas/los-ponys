package ar.edu.unlam.tallerweb1.modelo;

import java.net.URL;
import java.util.Date;
import java.util.List;

public class DestinoDto {

  private Integer id;

  private String nombre;

  private String placeId;

  private String ciudad;

  private URL icon;

  private Double lat;

  private Double lng;

  private Date fechaInicio;

  private Date fechaHasta;

  private List<String> destinos;


  public List<String> getDestinos() {
    return destinos;
  }

  public void setDestinos(List<String> destinos) {
    this.destinos = destinos;
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

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaHasta() {
    return fechaHasta;
  }

  public void setFechaHasta(Date fechaHasta) {
    this.fechaHasta = fechaHasta;
  }
}
