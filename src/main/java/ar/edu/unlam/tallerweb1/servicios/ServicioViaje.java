package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Viaje;

import java.util.Date;
import java.util.List;

public interface ServicioViaje {
    String iniciarViaje(Date inicio, Date fin, String destino);
    void guardarViaje(Viaje viaje);
    List<Viaje> obtenerViajes();
}
