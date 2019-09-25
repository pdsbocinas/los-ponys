package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "pais", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
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

    // Getters and Setters


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
    public List<Border> getBorders() {
        return borders;
    }

    public void setBorders(List<Border> borders) {
        this.borders = borders;
    }
}