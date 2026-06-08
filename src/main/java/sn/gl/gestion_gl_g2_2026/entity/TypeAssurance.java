package sn.gl.gestion_gl_g2_2026.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "types")
public class TypeAssurance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "libelle")
    protected String libelle;

    @OneToMany(mappedBy = "typeAssurance",fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    List<Assurance> assurances;

    @Override
    public String toString() {
        return "TypeAssurance{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +

                '}';
    }
}