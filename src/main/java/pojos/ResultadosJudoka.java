package pojos;

import jakarta.persistence.*;

@Entity
@Table(name = "resultados_judokas")
public class ResultadosJudoka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "judoka_id", nullable = false)
    private Judoka judoka;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "competicion_id", nullable = false)
    private Competition competicion;

    @Column(name = "posicion")
    private Integer posicion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Judoka getJudoka() {
        return judoka;
    }

    public void setJudoka(Judoka judoka) {
        this.judoka = judoka;
    }

    public Competition getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competition competicion) {
        this.competicion = competicion;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

}