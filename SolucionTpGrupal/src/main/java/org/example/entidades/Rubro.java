package org.example.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class Rubro extends AuditoriaApp {

    @Column(nullable = false)
    private String denominacion;
    @Column(nullable = false)
    private Integer codigo;

    protected Rubro() {
    }

    public Rubro(String denominacion, Integer codigo, Usuario usuarioCarga) {
        super(usuarioCarga);
        this.denominacion = denominacion;
        this.codigo = codigo;
    }

    public Rubro(String denominacion, Integer codigo) {
        this.denominacion = denominacion;
        this.codigo = codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
}
