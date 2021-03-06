package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.DestinoDto;
import ar.edu.unlam.tallerweb1.modelo.Foto;

import java.util.List;

public interface ServicioDestino {
  void guardarDestinos(List<Destino> destinos);

  Destino obtenerDestinoPorId(Integer id);

  void guardarDestino(Destino destino);

  void guardarFoto(Foto foto);


}
