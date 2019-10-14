package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;
import java.util.List;

public class ViajeDto {

  private Long id;

  private String titulo;

  private Date fechaInicio;

  private Date fechaFin;

  private List<String> destinos;

  private List<String> usuarios;

  private String icon;

  private String privacidad;

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
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

  public List<String> getDestinos() {
    return destinos;
  }

  public void setDestinos(List<String> destinos) {
    this.destinos = destinos;
  }

  public Long getId() {
    return id;
  }

  public List<String> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<String> usuarios) {
    this.usuarios = usuarios;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getPrivacidad() {
    return privacidad;
  }

  public void setPrivacidad(String privacidad) {
    this.privacidad = privacidad;
  }
}
