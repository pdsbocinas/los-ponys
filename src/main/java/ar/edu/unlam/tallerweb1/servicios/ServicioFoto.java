package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.FileFormBean;
import ar.edu.unlam.tallerweb1.modelo.Foto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ServicioFoto {
  String subirFotoAUnDestino(FileFormBean fileFormBean, Integer destino_id) throws IOException;

  List<Foto> obtenerFotosPorDestinoId(Integer destino_id);

}
