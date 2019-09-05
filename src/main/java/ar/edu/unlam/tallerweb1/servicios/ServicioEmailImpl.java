package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.EmailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ar.edu.unlam.tallerweb1.modelo.Mail;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service("servicioEmail")
@Transactional
public class ServicioEmailImpl implements ServicioEmail {

    @Autowired
    private JavaMailSender emailSender;

    @Inject
    private EmailDao emailDao;

    public void mandarMail(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getAsunto());
        message.setText(mail.getContenido());
        message.setTo(mail.getPara());
        message.setFrom(mail.getDesde());

        emailSender.send(message);
    }

    @Override
    public void guardarEmail(Mail mail) {
        emailDao.guardarMail(mail);
    }
}
