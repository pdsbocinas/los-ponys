package ar.edu.unlam.tallerweb1.dao;


import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.Foto;

import java.util.List;

public interface FotoDao {
  void guardarFoto(Foto foto);
  Foto obtenerFoto(Foto foto);
  List<Foto> obtenerFotosPorDestinoId(Integer destino_id);

  List<Foto> obtenerFotosDeDestinosDelViaje(Long viajeId);

  Boolean elegirFotoComoPortada(Foto foto);

  Foto obtenerFotoDePortada(Long viajeId);
}
