package ar.edu.unlam.tallerweb1.modelo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileFormBean {

  CommonsMultipartFile fichero;

  public CommonsMultipartFile getFichero() {
    return fichero;
  }

  public void setFichero(CommonsMultipartFile fichero) {
    this.fichero = fichero;
  }
}
