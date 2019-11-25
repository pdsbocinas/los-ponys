package ar.edu.unlam.tallerweb1.controladores;

import java.util.Date;

public interface ValidacionFecha {

  void proximaValidacion(ValidacionFecha validacion);
  void validar(Date inicio, Date fin);
}
