package org.example.entidades;

import jakarta.persistence.*;

@Entity
@Table
public class Cliente extends AuditoriaApp {
    @Column(nullable = false)
    private String cuitCuil;
    @Column(nullable = false)
    private String denominacion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Contacto contacto;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Domicilio domicilio;

    protected Cliente() {
    }

    public Cliente(String cuitCuil, String denominacion, Contacto contacto, Domicilio domicilio, Usuario usuarioCarga) {
        super(usuarioCarga);
        this.cuitCuil = cuitCuil;
        this.denominacion = denominacion;
        this.contacto = contacto;
        this.domicilio = domicilio;
    }

    public Cliente(String cuitCuil, String denominacion, Contacto contacto, Domicilio domicilio) {
        this.cuitCuil = cuitCuil;
        this.denominacion = denominacion;
        this.contacto = contacto;
        this.domicilio = domicilio;
    }

    public String getCuitCuil() {
        return cuitCuil;
    }

    public void setCuitCuil(String cuitCuil) {
        this.cuitCuil = cuitCuil;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
}
