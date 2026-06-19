package sn.gl.gestion_gl_g2_2026;

import sn.gl.gestion_gl_g2_2026.utils.JPAUtil;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Démarrage de l'initialisation de la base de données...");
            
            // L'appel à getEntityManagerFactory déclenche la lecture du persistence.xml
            // et la génération du schéma si hibernate.hbm2ddl.auto est à "update" ou "create"
            JPAUtil.getEntityManagerFactory();
            
            System.out.println("Base de données initialisée avec succès !");
            
            // Fermeture proprement
            JPAUtil.shutdown();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de la base de données : " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
