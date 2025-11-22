package pojos;

import jakarta.persistence.*;

@Entity
@Table(name = "judoka_users")
public class JudokaUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusers", nullable = false)
    private User idusers;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idjudoka", nullable = false)
    private Judoka idjudoka;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getIdusers() {
        return idusers;
    }

    public void setIdusers(User idusers) {
        this.idusers = idusers;
    }

    public Judoka getIdjudoka() {
        return idjudoka;
    }

    public void setIdjudoka(Judoka idjudoka) {
        this.idjudoka = idjudoka;
    }

}