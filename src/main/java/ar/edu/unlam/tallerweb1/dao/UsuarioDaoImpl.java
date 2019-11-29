package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Repository("usuarioDao")
@Transactional
public class UsuarioDaoImpl implements UsuarioDao {

  @Inject
  private SessionFactory sessionFactory;

  @Override
  public Usuario consultarUsuario(Usuario usuario) {

    final Session session = sessionFactory.getCurrentSession();
    return (Usuario) session.createCriteria(Usuario.class)
        .add(Restrictions.eq("email", usuario.getEmail()))
        .add(Restrictions.eq("password", usuario.getPassword()))
        .uniqueResult();
  }

  @Override
  public Usuario consultarUsuarioPorMail(Usuario usuario) {
    final Session session = sessionFactory.getCurrentSession();
    return (Usuario) session.createCriteria(Usuario.class)
            .add(Restrictions.eq("email", usuario.getEmail()))
            .uniqueResult();
  }

  @Override
  public Usuario obtenerUsuarioPorMail(String email) {
    final Session session = sessionFactory.getCurrentSession();
    return (Usuario) session.createCriteria(Usuario.class)
        .add(Restrictions.eq("email", email))
        .uniqueResult();
  }

  @Override
  public Usuario consultarUsuarioPorId(Integer id) {
    final Session session = sessionFactory.getCurrentSession();
    return (Usuario) session.createCriteria(Usuario.class)
        .add(Restrictions.eq("id", id))
        .uniqueResult();
  }

  @Override
  public void guardarUsuario(Usuario usuario) {
    final Session session = sessionFactory.getCurrentSession();
    session.save(usuario);
  }

  @Override
  public List<Usuario> obtenerTodosPorId(List<String> usuariosId) {
    List<Usuario> usuarios = new ArrayList<>();
    final Session session = sessionFactory.getCurrentSession();
    for (String email : usuariosId) {
      Usuario user = (Usuario) session.createCriteria(Usuario.class)
          .add(Restrictions.eq("email", email))
          .uniqueResult();
      usuarios.add(user);
    }
    return usuarios;
  }
}
