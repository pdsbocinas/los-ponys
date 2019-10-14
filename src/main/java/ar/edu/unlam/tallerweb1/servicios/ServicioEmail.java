package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Mail;

public interface ServicioEmail {
  void mandarMail(Mail mail);
  void guardarEmail(Mail mail);
}