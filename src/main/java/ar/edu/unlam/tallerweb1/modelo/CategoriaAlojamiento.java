package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;

@Entity
@Table(name = "CategoriaAlojamiento")
public class CategoriaAlojamiento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "categoria")
  private String categoria;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }
}
