package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;
import java.util.List;

public class UsuarioDto {
  private Integer id;
  private String email;
  private String password;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
