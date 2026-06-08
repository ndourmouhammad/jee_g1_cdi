package sn.gl.gestion_gl_g2_2026.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assurances")
public class Assurance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "number")
    protected String numero;

    protected String nomClient;
    protected double montant;

    @ManyToOne
    @JoinColumn(name ="type_id")
    TypeAssurance typeAssurance;


    @Override
    public String toString() {
        return "Assurance{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", nomClient='" + nomClient + '\'' +
                ", montant=" + montant +
                ", typeAssurance=" + typeAssurance +
                '}';
    }
}
