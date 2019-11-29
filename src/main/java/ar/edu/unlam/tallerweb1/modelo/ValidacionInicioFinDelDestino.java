package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

public class ValidacionInicioFinDelDestino extends ValidacionAbstractFecha {

  public void validar(Date inicio, Date fin){
    // valida que la fecha de inicio no sea posterior a la de fin
    if(inicio.after(fin)){
      throw new RuntimeException("la fecha de inicio del destino es posterior a la de fin");
    }
    super.validar(inicio, fin);
  }

}
