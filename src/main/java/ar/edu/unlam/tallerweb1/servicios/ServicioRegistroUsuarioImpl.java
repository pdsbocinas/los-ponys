package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;

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
}
