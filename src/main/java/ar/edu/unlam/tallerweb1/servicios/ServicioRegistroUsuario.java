package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.maps.errors.ApiException;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ServicioRegistroUsuario {
    void guardar(Usuario usuario);
    Integer crearUsuario(String email, String password) throws InterruptedException, ApiException, IOException;
    Boolean consultarUsuarioPorMail(Usuario usuario);
    Usuario obtenerUsuarioPorMail(String email);
    Usuario consultarUsuarioPorId(Integer id);
    void setUsuarioAndErrors (HttpServletRequest request, ModelMap model)throws JsonProcessingException;
}
