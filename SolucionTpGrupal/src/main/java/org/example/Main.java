package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.entidades.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println(">>> Iniciando prueba de persistencia JPA/Hibernate...");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FacturacionPU");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 1. Usuario para auditoría obligatoria (nullable = false en AuditoriaApp)
            Usuario usuario = new Usuario("jperez", "123456", "Juan", "Perez");
            em.persist(usuario);

            Date ahora = new Date();

            // 2. Dependencias de Articulo: Rubro y Marca (auditadas)
            Rubro rubro = new Rubro("Alimentos", 101);
            rubro.setFechaAlta(ahora);
            rubro.setFechaModificacion(ahora);
            rubro.setUsuarioCarga(usuario);
            rubro.setUsuarioModificacion(usuario);
            em.persist(rubro);

            Marca marca = new Marca("Arcor", 501);
            marca.setFechaAlta(ahora);
            marca.setFechaModificacion(ahora);
            marca.setUsuarioCarga(usuario);
            marca.setUsuarioModificacion(usuario);
            em.persist(marca);

            // 3. Articulo (auditado)
            Articulo articulo = new Articulo(rubro, marca, "ART-001", "Galletitas Dulces");
            articulo.setFechaAlta(ahora);
            articulo.setFechaModificacion(ahora);
            articulo.setUsuarioCarga(usuario);
            articulo.setUsuarioModificacion(usuario);
            em.persist(articulo);

            // 4. Lista de Precios y ListaPrecioArticulo (auditadas)
            ListaPrecio listaPrecio = new ListaPrecio("LP-MAY", "Mayorista");
            listaPrecio.setFechaAlta(ahora);
            listaPrecio.setFechaModificacion(ahora);
            listaPrecio.setUsuarioCarga(usuario);
            listaPrecio.setUsuarioModificacion(usuario);
            em.persist(listaPrecio);

            ListaPrecioArticulo listaPrecioArticulo = new ListaPrecioArticulo(listaPrecio, 1500.0, articulo);
            listaPrecioArticulo.setFechaAlta(ahora);
            listaPrecioArticulo.setFechaModificacion(ahora);
            listaPrecioArticulo.setUsuarioCarga(usuario);
            listaPrecioArticulo.setUsuarioModificacion(usuario);
            em.persist(listaPrecioArticulo);

            // 5. Punto de Venta (auditado)
            PuntoVenta puntoVenta = new PuntoVenta(1, "Sucursal Central", "Electronica", "Av. San Martin 123");
            puntoVenta.setFechaAlta(ahora);
            puntoVenta.setFechaModificacion(ahora);
            puntoVenta.setUsuarioCarga(usuario);
            puntoVenta.setUsuarioModificacion(usuario);
            em.persist(puntoVenta);

            // 6. Factura de Venta (cabecera)
            FacturaVenta facturaVenta = new FacturaVenta();
            facturaVenta.setPuntoVenta(puntoVenta);
            facturaVenta.setNumero(1001L);
            facturaVenta.setFechaEmision(ahora);
            facturaVenta.setImporteCobrado(0.0);
            facturaVenta.setImporteSaldo(3000.0);
            facturaVenta.setImporteTotal(3000.0);
            facturaVenta.setEstado("EMITIDA");
            facturaVenta.setObservaciones("Factura de prueba de persistencia por cascada");

            // Auditoría de FacturaVenta
            facturaVenta.setFechaAlta(ahora);
            facturaVenta.setFechaModificacion(ahora);
            facturaVenta.setUsuarioCarga(usuario);
            facturaVenta.setUsuarioModificacion(usuario);

            // Inicializar la lista de detalles
            facturaVenta.setDetalles(new ArrayList<>());

            // 7. FacturaVentaDetalle (renglones)
            FacturaVentaDetalle detalle1 = new FacturaVentaDetalle();
            detalle1.setListaPrecioArticulo(listaPrecioArticulo);
            detalle1.setDescripcion("Galletitas Dulces x1");
            detalle1.setCantidad(1.0);
            detalle1.setPrecioUnitario(1500.0);
            detalle1.setPorcentajeBonificacion(0.0);
            detalle1.setImporteNeto(1239.67);
            detalle1.setImporteIva(260.33);
            detalle1.setImporteSubtotal(1500.0);

            FacturaVentaDetalle detalle2 = new FacturaVentaDetalle();
            detalle2.setListaPrecioArticulo(listaPrecioArticulo);
            detalle2.setDescripcion("Galletitas Dulces x1");
            detalle2.setCantidad(1.0);
            detalle2.setPrecioUnitario(1500.0);
            detalle2.setPorcentajeBonificacion(0.0);
            detalle2.setImporteNeto(1239.67);
            detalle2.setImporteIva(260.33);
            detalle2.setImporteSubtotal(1500.0);

//          // 8. Asociación BIDIRECCIONAL: Factura <-> Detalles
//          aca se usa el metodo helper
            facturaVenta.addDetalle(detalle1);
            facturaVenta.addDetalle(detalle2);
            System.out.println(">>> Factura creada con " + facturaVenta.getDetalles().size() + " detalles asociados bidireccionalmente.");

            // 9. ÚNICO persist para FacturaVenta y sus detalles (propagación vía CascadeType.ALL)
            System.out.println(">>> Ejecutando em.persist(facturaVenta) [los detalles NO se persisten individualmente]...");
            em.persist(facturaVenta);

            // 10. Commit de la transacción
            tx.commit();
            System.out.println(">>> Transaccion confirmada (commit) exitosamente.");
            System.out.println(">>> ID Factura persistida: " + facturaVenta.getId());
            for (FacturaVentaDetalle det : facturaVenta.getDetalles()) {
                System.out.println("    -> ID Detalle persistido por cascada: " + det.getId());
            }

            em.clear(); // Limpia el contexto de persistencia; las entidades gestionadas
                        // dejan de estar asociadas al EntityManager.

//            busqueda con Find

            System.out.println("=================Ejemplo Busqueda=================");

            FacturaVenta fv = em.find(FacturaVenta.class, 1);
            Usuario uc = fv.getUsuarioCarga();
            List<FacturaVentaDetalle> fvd = new ArrayList<>();
            fvd = fv.getDetalles();

            int cont = 0;
            for(FacturaVentaDetalle dv : fvd){
                cont = cont + 1;
                System.out.println( "El detalle numero "+ cont + " " +dv.toString());
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
            System.out.println(">>> Recursos cerrados correctamente. Fin de la prueba.");
        }
    }
}