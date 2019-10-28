package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.Column;
import java.util.Date;

public class ReservaDto {

  private Integer alojamiento_id;

  private Integer user_id;

  private Date checkin;

  private Date checkout;

  public Integer getAlojamiento_id() {
    return alojamiento_id;
  }

  public void setAlojamiento_id(Integer alojamiento_id) {
    this.alojamiento_id = alojamiento_id;
  }

  public Integer getUser_id() {
    return user_id;
  }

  public void setUser_id(Integer user_id) {
    this.user_id = user_id;
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
