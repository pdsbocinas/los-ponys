package ar.edu.unlam.tallerweb1.dao;


import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.Foto;

import java.util.List;

public interface FotoDao {
  void guardarFoto(Foto foto);

  List<Foto> obtenerFotosPorDestinoId(Integer destino_id);

}
