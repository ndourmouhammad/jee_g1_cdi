package sn.gl.gestion_gl_g2_2026;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Démarrage de l'initialisation de la base de données...");

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE");

            System.out.println("Base de données initialisée avec succès !");

            emf.close();
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}