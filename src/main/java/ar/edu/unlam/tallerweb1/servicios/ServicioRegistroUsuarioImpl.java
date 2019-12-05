package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Service("servicioRegistroUsuario")
@Transactional
public class ServicioRegistroUsuarioImpl implements ServicioRegistroUsuario {

    @Inject
    private UsuarioDao usuarioDao;

    @Override
    public Integer crearUsuario(String email, String password) throws InterruptedException, ApiException, IOException {

        Usuario usuario = new Usuario(email, password);
        usuarioDao.guardarUsuario(usuario);
        return usuario.getId();
    }

    // hacer refactor aca
    @Override
    public Boolean consultarUsuarioPorMail(Usuario usuario) {
        if(usuarioDao.consultarUsuarioPorMail(usuario) != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        usuarioDao.guardarUsuario(usuario);
    }

    // hacer refactor aca
    @Override
    public Usuario obtenerUsuarioPorMail(String email) {
        Usuario usuario = usuarioDao.obtenerUsuarioPorMail(email);
        return usuario;
    }

    @Override
    public Usuario consultarUsuarioPorId(Integer id) {
        Usuario usuario = usuarioDao.consultarUsuarioPorId(id);
        return usuario;
    }
    @Override
    public void setUsuarioAndErrors (HttpServletRequest request, ModelMap model) throws JsonProcessingException {
        ObjectMapper usuarioJson = new ObjectMapper();

        HashMap<String, String> errors = new HashMap<>();
        errors.put("errorLogin", "no hubo errores");

        String errorLogin = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(errors);

        Usuario usuario = (Usuario) request.getSession().getAttribute("USER");
        model.put("usuario", usuarioJson.writerWithDefaultPrettyPrinter().writeValueAsString(usuario));
        model.put("errorLogin", errorLogin);
    }
}
