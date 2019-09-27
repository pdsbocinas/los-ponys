package ar.edu.unlam.tallerweb1.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="pais_id")
    private List<Border> borders;

    //@OneToMany

    //public List<Border> borders = new ArrayList<Border>();

    // Getters and Setters
    @OneToMany(cascade=CascadeType.ALL)
    // esto quiere decir que cuando te trae el pais te trae la colleccion de translations
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="pais_id")
    private List<Translations> translations;

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

    public List<Translations> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translations> translations) {
        this.translations = translations;
    }
}