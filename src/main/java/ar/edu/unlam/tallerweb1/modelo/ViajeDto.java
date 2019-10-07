package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;
import java.util.List;

public class ViajeDto {

  private Long id;

  private String titulo;

  private Date fechaInicio;

  private Date fechaFin;

  private List<String> destinos;

  private List<Integer> usuarios;

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

  public List<Integer> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<Integer> usuarios) {
    this.usuarios = usuarios;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
