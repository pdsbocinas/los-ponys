package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

import java.util.List;

// Interface que define los metodos del DAO de Usuarios.
public interface UsuarioDao {
	
	Usuario consultarUsuario (Usuario usuario);
	Usuario consultarUsuarioPorMail(Usuario usuario);
    void guardarUsuario(Usuario usuario);
  List<Usuario> obtenerTodosPorId(List<Integer> usuariosId);
}
