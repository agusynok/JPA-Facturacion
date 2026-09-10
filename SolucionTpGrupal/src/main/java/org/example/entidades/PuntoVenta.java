package org.example.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class PuntoVenta extends AuditoriaApp {

    @Column(nullable = false)
    private int numero;
    private String descripcion;
    private String tipoEmision;
    private String domicilioComercial;

    protected PuntoVenta() {
    }

    public PuntoVenta(int numero, String descripcion, String tipoEmision, String domicilioComercial, Usuario usuarioCarga) {
        super(usuarioCarga);
        this.numero = numero;
        this.descripcion = descripcion;
        this.tipoEmision = tipoEmision;
        this.domicilioComercial = domicilioComercial;
    }

    public PuntoVenta(int numero, String descripcion, String tipoEmision, String domicilioComercial) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.tipoEmision = tipoEmision;
        this.domicilioComercial = domicilioComercial;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoEmision() {
        return tipoEmision;
    }

    public void setTipoEmision(String tipoEmision) {
        this.tipoEmision = tipoEmision;
    }

    public String getDomicilioComercial() {
        return domicilioComercial;
    }

    public void setDomicilioComercial(String domicilioComercial) {
        this.domicilioComercial = domicilioComercial;
    }
}
