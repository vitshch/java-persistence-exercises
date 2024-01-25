package com.bobocode.dao;

import com.bobocode.exception.CompanyDaoException;
import com.bobocode.model.Company;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class CompanyDaoImpl implements CompanyDao {

    private EntityManagerFactory entityManagerFactory;

    public CompanyDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Company findByIdFetchProducts(Long id) {
        try (var em = entityManagerFactory.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                Company company = em.createQuery("select c from Company c join fetch c.products where c.id = :company_id", Company.class)
                        .setParameter("company_id", id)
                        .getSingleResult();
                tx.commit();
                return company;
            } catch (Exception e) {
                tx.rollback();
                throw new CompanyDaoException("Error with performing of fetching company", e);
            }
        }
    }
}
