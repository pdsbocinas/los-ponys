package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;

public class ComentarioDto {

  private Long id;

  private String texto;

  private Long viaje_id;

  private String usuario_email;

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

  public Long getViaje_id() {
    return viaje_id;
  }

  public void setViaje_id(Long viaje_id) {
    this.viaje_id = viaje_id;
  }

  public String getUsuario_email() {
    return usuario_email;
  }

  public void setUsuario_email(String usuario_email) {
    this.usuario_email = usuario_email;
  }
}
