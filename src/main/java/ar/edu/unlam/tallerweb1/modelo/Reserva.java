package ar.edu.unlam.tallerweb1.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reserva")
public class Reserva {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Integer id;

  @OneToOne
  @JsonIgnore
  private Alojamiento alojamiento;

  @Column(name = "checkin")
  private Date checkin;

  @Column(name = "checkout")
  private Date checkout;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Alojamiento getAlojamiento() {
    return alojamiento;
  }

  public void setAlojamiento(Alojamiento alojamiento) {
    this.alojamiento = alojamiento;
  }

  public Date getCheckin() {
    return checkin;
  }

  public void setCheckin(Date checkin) {
    this.checkin = checkin;
  }

  public Date getCheckout() {
    return checkout;
  }

  public void setCheckout(Date checkout) {
    this.checkout = checkout;
  }
}
