package ar.edu.unlam.tallerweb1.modelo;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pais")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "capital")
    private String capital;

/*
    @Column(name = "timezones")
    private List timezones;

*/
    //relacion con el objeto Border
    // Un pais puede tener varios paises limitrofes
    // cierto grupo de paises corresponden a un pais limitrofe
    //EN DUDA
    @OneToMany(cascade=CascadeType.ALL)
   @JoinColumn(name="border_id")
   private List<Border> borders;

    //@OneToMany

   //public List<Border> borders = new ArrayList<Border>();

    // Getters and Setters


    public List<Border> getBorders() {
        return borders;
    }

    public void setBorders(List<Border> borders) {
        this.borders = borders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
/*
    public List getTimezones() {
        return timezones;
    }

    public void setTimezones(List timezones) {
        this.timezones = timezones;
    }


    */

}