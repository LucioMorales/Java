package repositorios;

import entidades.Mascota;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RepositorioMascotas {
    private EntityManager em;

    public RepositorioMascotas(EntityManager entityManager){
        this.em = entityManager;
    };

    public void guardar(Mascota mascota){
        em.getTransaction().begin();
        em.persist(mascota);
        em.getTransaction().commit();
        System.out.println(mascota);
    }

    public void guardarTodo(List<Mascota> mascotas){
        mascotas.forEach(this::guardar);
    }

    public List getFelices(){
        return em.createQuery("SELECT m FROM Mascota m WHERE m.nivelHumor > :number")
                .setParameter("number", 3)
                .getResultList();
    }
}
