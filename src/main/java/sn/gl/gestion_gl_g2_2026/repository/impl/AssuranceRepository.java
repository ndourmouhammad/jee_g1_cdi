package sn.gl.gestion_gl_g2_2026.repository.impl;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import sn.gl.gestion_gl_g2_2026.entity.Assurance;
import sn.gl.gestion_gl_g2_2026.repository.ICrud;

import java.util.List;

@ApplicationScoped
public class AssuranceRepository implements ICrud<Assurance> {

    @Inject
    private EntityManager entityManager;

    @Override
    public List<Assurance> getAll() {
        List<Assurance> assurances ;
        assurances = entityManager.createQuery("FROM Assurance").getResultList();

       return assurances;
    }

    @Override
    public void insert(Assurance assurance) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(assurance);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
       this.entityManager.getTransaction().begin();
        entityManager.remove(get(id));
        this.entityManager.getTransaction().commit();
    }

    @Override
    public Assurance get(int id) {
       return  entityManager.find(Assurance.class, id);
    }

    @Override
    public void update(Assurance assurance) {
        entityManager.getTransaction().begin();
        entityManager.merge(assurance);
        entityManager.getTransaction().commit();
    }

}
