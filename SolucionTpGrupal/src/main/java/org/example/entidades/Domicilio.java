package org.example.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class Domicilio extends EntityId {
    private String nombreCalle;
    private String numeroCalle;

    public Domicilio() {
    }

    public Domicilio(String nombreCalle, String numeroCalle) {
        this.nombreCalle = nombreCalle;
        this.numeroCalle = numeroCalle;
    }

    public Domicilio(Long id, String nombreCalle, String numeroCalle) {
        super(id);
        this.nombreCalle = nombreCalle;
        this.numeroCalle = numeroCalle;
    }

    public String getNombreCalle() {
        return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public String getNumeroCalle() {
        return numeroCalle;
    }

    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }
}
