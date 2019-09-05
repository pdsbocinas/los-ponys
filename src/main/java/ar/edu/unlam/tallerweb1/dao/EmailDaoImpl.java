package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Mail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository("EmailDao")
public class EmailDaoImpl implements EmailDao {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void guardarMail(Mail mail) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(mail);
    }
}
