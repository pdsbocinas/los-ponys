package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import com.google.maps.errors.ApiException;

import java.io.IOException;

public interface ServicioRegistroUsuario {
    void guardar(Usuario usuario);
    Integer crearUsuario(String email, String password) throws InterruptedException, ApiException, IOException;
    Boolean consultarUsuarioPorMail(Usuario usuario);
}
