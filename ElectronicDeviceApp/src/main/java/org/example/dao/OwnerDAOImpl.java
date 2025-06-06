package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import org.example.model.Owner;

import java.util.List;

public class OwnerDAOImpl implements OwnerDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("your-persistence-unit");

    @Override
    public void save(Owner owner) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(owner);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Owner findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Owner.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Owner findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT o FROM Owner o WHERE o.name = :name", Owner.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Owner> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Owner", Owner.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Owner owner) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(owner);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Owner owner) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Owner managed = em.merge(owner);
            em.remove(managed);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
