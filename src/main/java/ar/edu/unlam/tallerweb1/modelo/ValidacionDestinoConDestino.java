package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;
import java.util.List;

public class ValidacionDestinoConDestino extends ValidacionAbstractFecha {
  private final Destino destino;
  private final List<Destino> destinos;

  public ValidacionDestinoConDestino(Destino destino, List<Destino> destinos){
    this.destino = destino;
    this.destinos = destinos;
  }

  public void validar(Date inicio, Date fin){

    for (Destino d: destinos
         ) {
      if(destino.getId() != d.getId()){
        if(d.getFechaInicio() != null && d.getFechaHasta() != null) {
          if (inicio.after(d.getFechaInicio()) && inicio.before(d.getFechaHasta())) {
            throw new RuntimeException("El destino se pisa con otro destino 1");
          } else if (fin.after(d.getFechaInicio()) && fin.before(d.getFechaHasta())) {
            throw new RuntimeException("El destino se pisa con otro destino 2");
          }
        }
      }
    }

    super.validar(inicio, fin);
  }

}
