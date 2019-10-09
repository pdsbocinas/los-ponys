package ar.edu.unlam.tallerweb1.modelo;

import java.util.List;

public class DestinoDto {
  private Long id;
  private List<String> destinos;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<String> getDestinos() {
    return destinos;
  }

  public void setDestinos(List<String> destinos) {
    this.destinos = destinos;
  }
}
