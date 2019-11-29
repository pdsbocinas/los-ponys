package ar.edu.unlam.tallerweb1.persistencia;
import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

public class TestViaje extends SpringTest {


  @Test
  @Transactional
  @Rollback
  public void borrarUnViajeConComentarios(){
/*    Viaje viaje = new Viaje();

    viaje.agregarComentario(new Comentario());
    getSession().save(viaje);


    getSession().delete(viaje);
    Viaje buscado = getSession().get(Viaje.class, viaje.getId());

    assertThat(buscado).isNull();*/
  }
}
