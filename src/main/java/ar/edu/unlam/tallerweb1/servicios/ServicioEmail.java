package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Mail;

import java.util.List;

public interface ServicioEmail {
  void mandarMail(Mail mail);
  void guardarEmail(Mail mail);

  List<Mail> obtenerEmailsPorUsuario(Integer userId);

  void borrarEmails(List<Mail> mails);
}