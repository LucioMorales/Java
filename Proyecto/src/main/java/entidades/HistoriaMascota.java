package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Historia_Mascota")
public class HistoriaMascota {
    @Id
    @Column(name = "id_historico")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_mascota", referencedColumnName = "id")
    private Mascota mascota;

    @Column(name = "tipo_historico")
    private String tipoHistorico;
    @Column(name = "energia_inicio")
    private int energiaInicio;
    @Column(name = "humor_inicio")
    private int humorInicio;
    @Column(name = "energia_fin")
    private int energiaFin;
    @Column(name = "humor_fin")
    private int humorFin;
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaHora;

    public HistoriaMascota() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getTipoHistorico() {
        return tipoHistorico;
    }

    public void setTipoHistorico(String tipoHistorico) {
        this.tipoHistorico = tipoHistorico;
    }

    public int getEnergiaInicio() {
        return energiaInicio;
    }

    public void setEnergiaInicio(int energiaInicio) {
        this.energiaInicio = energiaInicio;
    }

    public int getHumorInicio() {
        return humorInicio;
    }

    public void setHumorInicio(int humorInicio) {
        this.humorInicio = humorInicio;
    }

    public int getEnergiaFin() {
        return energiaFin;
    }

    public void setEnergiaFin(int energiaFin) {
        this.energiaFin = energiaFin;
    }

    public int getHumorFin() {
        return humorFin;
    }

    public void setHumorFin(int humorFin) {
        this.humorFin = humorFin;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "HistoriaMascota{" +
                "id=" + id +
                ", mascota=" + mascota.getNombre() +
                ", tipoHistorico='" + tipoHistorico + '\'' +
                ", energiaInicio=" + energiaInicio +
                ", humorInicio=" + humorInicio +
                ", energiaFin=" + energiaFin +
                ", humorFin=" + humorFin +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
