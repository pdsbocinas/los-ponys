package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

public class ValidacionFechaNoVacia extends ValidacionAbstractFecha {

  public void validar(Date inicio, Date fin){
    // valida que la fecha de inicio no sea posterior a la de fin
    if(inicio == null){
      throw new RuntimeException("la fecha de inicio está vacia");
    }else if(fin == null){
      throw new RuntimeException("la fecha de fin está vacia");
    }
    super.validar(inicio, fin);
  }

}
