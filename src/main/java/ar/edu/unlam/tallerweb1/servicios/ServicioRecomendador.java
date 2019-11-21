package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.PuntoDeInteres;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;

public interface ServicioRecomendador {
  List<PuntoDeInteres> obtenerPuntosDeInteres(String tipo, String destino) throws InterruptedException, ApiException, IOException;
}
