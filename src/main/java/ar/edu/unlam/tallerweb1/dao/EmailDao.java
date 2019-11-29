package ar.edu.unlam.tallerweb1.dao;
import ar.edu.unlam.tallerweb1.modelo.Mail;

import java.util.List;

public interface EmailDao {
  void guardarMail(Mail mail);

  List<Mail> obtenerEmailsPorUsuario(Integer userId);

  void borrarEmails(List<Mail> mails);
}