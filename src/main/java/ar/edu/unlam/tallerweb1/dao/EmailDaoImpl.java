package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.Mail;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("EmailDao")
public class EmailDaoImpl implements EmailDao {

  @Inject
  private SessionFactory sessionFactory;

  @Override
  public void guardarMail(Mail mail) {
    final Session session = sessionFactory.getCurrentSession();
    session.save(mail);
  }

  @Override
  public List<Mail> obtenerEmailsPorUsuario(Integer userId) {
    final Session session = sessionFactory.getCurrentSession();
    Criteria cr = session.createCriteria(Mail.class)
        .createAlias("usuario", "user")
        .add(Restrictions.eq("user.id", userId));
    List<Mail> results = cr.list();
    return results;
  }

  @Override
  @Transactional
  public void borrarEmails(List<Mail> mails) {
    Session session = sessionFactory.getCurrentSession();
    for (Mail mail : mails) {
      session.delete(mail);
    }
  }
}