package org.example.entidades;

import jakarta.persistence.*;

@Entity
@Table
public class ListaPrecioArticulo extends AuditoriaApp {

    @ManyToOne
    @JoinColumn(nullable = false)
    private ListaPrecio listaPrecio;

    @Column(nullable = false)
    private double precioVenta;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Articulo articulo;

    protected ListaPrecioArticulo() {
    }

    public ListaPrecioArticulo(ListaPrecio listaPrecio, double precioVenta, Articulo articulo, Usuario usuarioCarga) {
        super(usuarioCarga);
        this.listaPrecio = listaPrecio;
        this.precioVenta = precioVenta;
        this.articulo = articulo;
    }

    public ListaPrecioArticulo(ListaPrecio listaPrecio, double precioVenta, Articulo articulo) {
        this.listaPrecio = listaPrecio;
        this.precioVenta = precioVenta;
        this.articulo = articulo;
    }

    public ListaPrecio getListaPrecio() {
        return listaPrecio;
    }

    public void setListaPrecio(ListaPrecio listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
