package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

public class ValidacionAbstractFecha implements ValidacionFecha {
  private ValidacionFecha proxima;

  @Override
  public void proximaValidacion(ValidacionFecha validacion) {
    this.proxima = validacion;
  }

  @Override
  public void validar(Date inicio, Date fin) {
      if(proxima != null){
        proxima.validar(inicio,fin);
      }

  }
}
