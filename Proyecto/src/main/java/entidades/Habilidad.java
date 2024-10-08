package entidades;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Habilidades")
public class Habilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre_habilidad")
    private String nombreHabilidad;

    public Habilidad(String habilidad) {
        this.nombreHabilidad = habilidad;
    }

    public Habilidad() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreHabilidad() {
        return nombreHabilidad;
    }

    public void setNombreHabilidad(String nombreHabilidad) {
        this.nombreHabilidad = nombreHabilidad;
    }

    @Override
    public String toString() {
        return "Habilidad{" +
                "id=" + id +
                ", nombreHabilidad='" + nombreHabilidad + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habilidad habilidad = (Habilidad) o;
        return id == habilidad.id && Objects.equals(nombreHabilidad, habilidad.nombreHabilidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreHabilidad);
    }
}
