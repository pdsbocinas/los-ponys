package ar.edu.unlam.tallerweb1.controladores;

import java.util.Date;

public class ValidacionInicioDelViaje extends ValidacionAbstractFecha {
  private final Date fechaInicioDelViaje;

  public ValidacionInicioDelViaje(Date fechaInicioDelViaje){
    this.fechaInicioDelViaje = fechaInicioDelViaje;
  }

  public void validar(Date inicio, Date fin){
    if(inicio.before(fechaInicioDelViaje)){
      throw new RuntimeException("La fecha de inicio del destino no puede ser anterior a la fecha de inico del viaje");
    }
    super.validar(inicio, fin);
  }

}
