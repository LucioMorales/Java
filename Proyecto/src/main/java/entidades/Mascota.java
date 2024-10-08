package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    @Column(name = "nivel_energia")
    private int nivelEnergia;
    @Column(name = "nivel_humor")
    private int nivelHumor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Habilidades_x_Mascota",
            joinColumns = @JoinColumn(name = "id_mascota"),
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")
    )
    private Set<Habilidad> habilidades;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<HistoriaMascota> historialAcciones;

    @Transient int ingestasConsecutivas = 0;
    @Transient int ejercitacionesConsecutivas = 0;
    @Transient int mayorRachaEjercitacion = 0;

    public Mascota() {
        this.historialAcciones = new ArrayList<>();
    }

    public void crearHistorico(int energiaInicial, int humorInicial,
                               int energiaFinal, int humorFinal,
                               String tipoHistorico){
        HistoriaMascota historico = new HistoriaMascota();
        historico.setMascota(this);
        historico.setEnergiaInicio(energiaInicial);
        historico.setEnergiaFin(energiaFinal);
        historico.setHumorInicio(humorInicial);
        historico.setHumorFin(humorFinal);
        historico.setTipoHistorico(tipoHistorico);
        historico.setFechaHora(LocalDateTime.now());

        historialAcciones.add(historico);
    }

    public boolean estaViva(){
        return this.getNivelEnergia() > 0;
    };

    public int getRachaFitness(){
        return mayorRachaEjercitacion;
    };

    public float getEstadoFisico(){
        float cantEjercitaciones = this.historialAcciones.stream()
                .filter(h -> h.getTipoHistorico().equals("Ejercitar"))
                .count();

        float cantIngestas = this.historialAcciones.stream()
                .filter(h -> h.getTipoHistorico().equals("Ingerir"))
                .count();

        return cantEjercitaciones / cantIngestas;
    }

    public void ingerir(){
        if(estaViva()){
            this.ejercitacionesConsecutivas = 0;
            int energiaInicio = this.getNivelEnergia();
            int humorInicio = this.getNivelHumor();
            if (ingestasConsecutivas < 2) {
                this.setNivelEnergia(this.getNivelEnergia()+10);
                this.setNivelHumor(this.getNivelHumor()+1);
                this.ingestasConsecutivas++;
            }
            if (ingestasConsecutivas >= 2) {
                this.setNivelEnergia(this.getNivelEnergia()+5);
                this.ingestasConsecutivas++;
            }
            if (ingestasConsecutivas == 5) {
                this.setNivelEnergia(0);
            }
            int energiaFin = this.getNivelEnergia();
            int humorFin = this.getNivelHumor();

            crearHistorico(energiaInicio, humorInicio, energiaFin, humorFin, "Ingerir");
        } else {
            throw new MascotaMuertaException();
        }

    }

    public void ejercitar(){
        if(this.estaViva()){
            this.ingestasConsecutivas = 0;

            int energiaInicio = this.getNivelEnergia();
            int humorInicio = this.getNivelHumor();
            this.setNivelEnergia(this.getNivelEnergia()-10);
            int energiaFin = this.getNivelEnergia();
            int humorFin = this.getNivelHumor();
            this.incrementarEjercitacionesConsecutivas();

            crearHistorico(energiaInicio, humorInicio, energiaFin, humorFin, "Ejercitar");
        } else {
            throw new MascotaMuertaException();
        }

    }

    public void demostrarHabilidad(String habilidad){
        if(estaViva()){
            if (this.habilidades.stream().anyMatch(h -> h.getNombreHabilidad().equals(habilidad))){
                this.ingestasConsecutivas = 0;
                this.ejercitacionesConsecutivas = 0;
                int energiaInicio = this.getNivelEnergia();
                int humorInicio = this.getNivelHumor();
                System.out.println("Mirá como puedo "+habilidad+"!");
                this.setNivelHumor(this.getNivelHumor()+1);
                int energiaFin = this.getNivelEnergia();
                int humorFin = this.getNivelHumor();
                crearHistorico(energiaInicio, humorInicio, energiaFin, humorFin, "DemostrarHabilidad");
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new MascotaMuertaException();
        }
    }

    public void incrementarEjercitacionesConsecutivas(){
        this.ejercitacionesConsecutivas++;
        if(this.ejercitacionesConsecutivas>mayorRachaEjercitacion){
            this.mayorRachaEjercitacion = ejercitacionesConsecutivas;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivelEnergia() {
        return nivelEnergia;
    }

    public void setNivelEnergia(int nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    public int getNivelHumor() {
        return nivelHumor;
    }

    public void setNivelHumor(int nivelHumor) {
        this.nivelHumor = nivelHumor;
    }

    public Set<Habilidad> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(Set<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    public List<HistoriaMascota> getHistorialAcciones() {
        return historialAcciones;
    }

    public void setHistorialAcciones(List<HistoriaMascota> historialAcciones) {
        this.historialAcciones = historialAcciones;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nivelEnergia=" + nivelEnergia +
                ", nivelHumor=" + nivelHumor +
                ", habilidades=" + habilidades +
                ", historialAcciones=" + historialAcciones +
                '}';
    }

    public static class MascotaMuertaException extends RuntimeException {
        public MascotaMuertaException(){
            super();
        }
    }
}