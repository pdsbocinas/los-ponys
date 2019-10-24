package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

import java.util.List;

// Interface que define los metodos del DAO de Usuarios.
public interface UsuarioDao {
	
	Usuario consultarUsuario (Usuario usuario);
	Usuario consultarUsuarioPorMail(Usuario usuario);
  Usuario consultarUsuarioPorId(Integer id);
  Usuario obtenerUsuarioPorMail(String email);
    void guardarUsuario(Usuario usuario);
  List<Usuario> obtenerTodosPorId(List<String> usuariosId);
}
