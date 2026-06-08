package sn.gl.gestion_gl_g2_2026.repository.impl;



import jakarta.persistence.EntityManager;
import sn.gl.gestion_gl_g2_2026.entity.TypeAssurance;
import sn.gl.gestion_gl_g2_2026.repository.ICrud;
import sn.gl.gestion_gl_g2_2026.utils.JPAUtil;

import java.util.List;

public class TypeAssuranceRepository implements ICrud<TypeAssurance> {
    private EntityManager entityManager;

    public TypeAssuranceRepository() {
        this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<TypeAssurance> getAll() {
        List<TypeAssurance> TypeAssurances ;

        entityManager.getTransaction().begin();
        TypeAssurances = entityManager.createQuery("FROM TypeAssurance").getResultList();       //JPQL

        return TypeAssurances;
    }

    @Override
    public void insert(TypeAssurance TypeAssurance) {
        this.entityManager.getTransaction().begin(); //demarrer la transaction
        this.entityManager.persist(TypeAssurance);
        this.entityManager.getTransaction().commit(); //save la transaction
    }

    @Override
    public void delete(int id) {
        this.entityManager.getTransaction().begin();
        entityManager.remove(get(id));
        this.entityManager.getTransaction().commit();
    }

    @Override
    public TypeAssurance get(int id) {
        return  entityManager.find(TypeAssurance.class, id);
    }

    @Override
    public void update(TypeAssurance TypeAssurance) {
        entityManager.getTransaction().begin();
        entityManager.merge(TypeAssurance);
        entityManager.getTransaction().commit();
    }
}
