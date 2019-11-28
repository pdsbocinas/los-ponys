package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

public class ValidacionFinDelViaje extends ValidacionAbstractFecha {
  private final Date fechaFinDelViaje;

  public ValidacionFinDelViaje(Date fechaFinDelViaje){
    this.fechaFinDelViaje = fechaFinDelViaje;
  }

  public void validar(Date inicio, Date fin){
    if(fin.after(fechaFinDelViaje) || inicio.after(fechaFinDelViaje)){
      throw new RuntimeException("La fecha de inicio o fin del destino no puede ser posterior a la fecha de fin del viaje");
    }
    super.validar(inicio, fin);
  }

}
