package com.itmo.wst;

import com.itmo.wst.model.Sugar;
import com.itmo.wst.model.Wine;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class WineRepositoryImpl implements  WineRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    public List<Wine> findByWine(Wine wine) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Wine> q = cb.createQuery(Wine.class);
        Root<Wine> table = q.from(Wine.class);
        List<Predicate> predicates = new ArrayList<>();
        if (wine.getId() != null) {
            predicates.add(cb.equal(table.get("id"), wine.getId()));
        }
        if (wine.getName() != null) {
            predicates.add(cb.like(table.get("name"), "%" + wine.getName() + "%"));
        }
        if (wine.getSugar() != null) {
            predicates.add(cb.equal(table.get("sugar"), wine.getSugar()));
        }
        if (wine.getColor() != null) {
            predicates.add(cb.equal(table.get("color"), wine.getColor()));
        }
        if (wine.getRating() != null) {
            predicates.add(cb.equal(table.get("rating"), wine.getRating()));
        }

        q.where(cb.and(predicates.toArray(Predicate[]::new)));

        TypedQuery<Wine> query = entityManager.createQuery(q);

        return query.getResultList();
    }
}
