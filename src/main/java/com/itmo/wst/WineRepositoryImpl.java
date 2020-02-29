package com.itmo.wst;

import com.itmo.wst.model.Sugar;
import com.itmo.wst.model.Wine;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

import java.util.List;

public class WineRepositoryImpl implements  WineRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    public List<Wine> findByWine(Wine wine) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Wine> q = cb.createQuery(Wine.class);
        Root<Wine> c = q.from(Wine.class);
   //     ParameterExpression<BigInteger> id = cb.parameter(BigInteger.class);
    //    ParameterExpression<String> name = cb.parameter(String.class);
        ParameterExpression<Sugar> sugar = cb.parameter(Sugar.class);
   //     ParameterExpression<String> color = cb.parameter(String.class);
  //      ParameterExpression<Float> rating = cb.parameter(Float.class);

        q.select(c)//.where(cb.like(c.get("id"), id.toString()))
                   // .where(cb.equal(c.get("id"), id))
                  //  .where(cb.equal(c.get("name"), name))
                    .where(cb.equal(c.get("sugar"), sugar))
                  //  .where(cb.equal(c.get("color"), color))
                  //  .where(cb.equal(c.get("rating"), rating))
        ;

        TypedQuery<Wine> query = entityManager.createQuery(q);
        try {
            query.setParameter(sugar, wine.getSugar());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return query.getResultList();
    }
}
