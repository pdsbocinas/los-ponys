package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Viaje;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ServicioViaje {
    String iniciarViaje(Date inicio, Date fin, String destino);
    List<Viaje> obtenerViajes();
    Long crearViaje(String titulo, Date fechaInicio, Date fechaFin, List<String> destinos, List<Integer> usuarios) throws InterruptedException, ApiException, IOException;
}
