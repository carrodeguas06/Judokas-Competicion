package pojos;

import jakarta.persistence.*;

@Entity
@Table(name = "judokas")
public class Judoka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 55)
    private String name;

    @Column(name = "last_name", nullable = false, length = 55)
    private String lastName;

    @Column(name = "country", nullable = false, length = 45)
    private String country;

    @Lob
    @Column(name = "belt", nullable = false)
    private String belt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especial_tecnique", nullable = false)
    private Tecnique especialTecnique;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public Tecnique getEspecialTecnique() {
        return especialTecnique;
    }

    public void setEspecialTecnique(Tecnique especialTecnique) {
        this.especialTecnique = especialTecnique;
    }

}