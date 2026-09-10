package org.example.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Articulo extends AuditoriaApp {

    @ManyToOne
    private Rubro rubro;

    @ManyToOne
    private Marca marca;

    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String denominacion;


    protected Articulo() {}

    public Articulo(Rubro rubro, Marca marca, String codigo, String denominacion, Usuario usuarioCarga) {
        super(usuarioCarga);
        this.rubro = rubro;
        this.marca = marca;
        this.codigo = codigo;
        this.denominacion = denominacion;
    }

    public Articulo(Rubro rubro, Marca marca, String codigo, String denominacion) {
        this.rubro = rubro;
        this.marca = marca;
        this.codigo = codigo;
        this.denominacion = denominacion;
    }


    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
}
