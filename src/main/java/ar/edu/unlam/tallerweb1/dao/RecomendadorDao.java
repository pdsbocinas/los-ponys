package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.PuntoDeInteres;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;

public interface RecomendadorDao {
  List<PuntoDeInteres> obtenerPuntosDeInteres(String tipo, String destino) throws InterruptedException, ApiException, IOException;
}
