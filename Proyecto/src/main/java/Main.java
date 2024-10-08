import entidades.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import servicios.ServicioMascotas;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mascotas-pu");
        EntityManager em = emf.createEntityManager();

        ServicioMascotas servicio = new ServicioMascotas(em);
        servicio.cargarMascotas();

        List<Mascota> mascotas = servicio.getMascotas();
        Mascota m1 = mascotas.get(10);
        System.out.println(m1);
        m1.ejercitar();
        m1.ingerir();
        //System.out.println(m1.getHistorialAcciones());


        //servicio.guardarVivas();
        servicio.getFelices().forEach(System.out::println);

    }
}
