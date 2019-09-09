package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.List;

public class Pais {

    private String name;
    private String capital;
    private List timezones;

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

    public List getTimezones() {
        return timezones;
    }

    public void setTimezones(List timezones) {
        this.timezones = timezones;
    }
}
