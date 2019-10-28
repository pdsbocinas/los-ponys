package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;

@Entity
@Table(name = "TipoDeViaje")
public class TipoDeViaje {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "tipo")
  private String tipo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}