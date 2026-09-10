package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.entidades.*;

import java.time.LocalDateTime;
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

            Usuario usuario2 = new Usuario("Batman", "123", "Bruce", "Wayne" );
            em.persist(usuario2);

            //1.1 Crear Cliente

            Contacto contacto1 = new Contacto("Battman@gmail.com", "918192", "12121");
            Domicilio domicilio1 = new Domicilio("Ameghino", "121" );
            Cliente cliente1 = new Cliente("23323", "Denominado", contacto1, domicilio1, usuario2);
            em.persist(cliente1);

            // 2. Dependencias de Articulo: Rubro y Marca (auditadas via constructor)
            Rubro rubro = new Rubro("Alimentos", 101, usuario);
            em.persist(rubro);

            Marca marca = new Marca("Arcor", 501, usuario);
            em.persist(marca);

            // 3. Articulo (auditado via constructor)
            Articulo articulo = new Articulo(rubro, marca, "ART-001", "Galletitas Dulces", usuario);
            em.persist(articulo);

            // 4. Lista de Precios y ListaPrecioArticulo (auditadas via constructor)
            ListaPrecio listaPrecio = new ListaPrecio("LP-MAY", "Mayorista", usuario);
            em.persist(listaPrecio);

            ListaPrecioArticulo listaPrecioArticulo = new ListaPrecioArticulo(listaPrecio, 1500.0, articulo, usuario);
            em.persist(listaPrecioArticulo);

            // 5. Punto de Venta (auditado via constructor)
            PuntoVenta puntoVenta = new PuntoVenta(1, "Sucursal Central", "Electronica", "Av. San Martin 123", usuario);
            em.persist(puntoVenta);

            //5.1 CondicionIva y TipoMoneda para la FacturaVenta

            CondicionIva condicionIva1 = new CondicionIva(2938293, "denominado",usuario);
            em.persist(condicionIva1);
            TipoMoneda tipoMoneda1 = new TipoMoneda("298392", "denominado", "escudo", usuario);
            em.persist(tipoMoneda1);

            // 6. Factura de Venta (cabecera con auditoría, estado y lista instanciados)
            FacturaVenta facturaVenta = new FacturaVenta(cliente1,condicionIva1,tipoMoneda1,puntoVenta, 98293L, 38923, 23892, "cae?", LocalDateTime.now(), "APROVE", "INICIADO", "No hay observaciones por ahora", usuario2);

            // 7. FacturaVentaDetalle (renglones instanciados por constructor)
            FacturaVentaDetalle detalle1 = new FacturaVentaDetalle(
                    listaPrecioArticulo, "Galletitas Dulces x1", 1.0, 1500.0, 0.0, 1239.67, 260.33, 1500.0
            );
            FacturaVentaDetalle detalle2 = new FacturaVentaDetalle(
                    listaPrecioArticulo, "Galletitas Dulces x1", 1.0, 1500.0, 0.0, 1239.67, 260.33, 1500.0
            );

            // 8. Asociación BIDIRECCIONAL: Factura <-> Detalles mediante helper
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
            FacturaVenta fv = em.find(FacturaVenta.class, facturaVenta.getId());
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