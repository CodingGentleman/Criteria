package at.fhj.criteria.dao;

import at.fhj.criteria.entities.Entity;
import at.fhj.criteria.entities.immutable.EntityView;
import at.fhj.criteria.persistence.Criteria;

import java.util.List;

abstract class BaseDao {
    <T extends Entity<U>, U extends EntityView<T>> List<U> findAll(Class<T> entityClass) {
        var q = Criteria.getCriteriaBuilder().createQuery(entityClass);
        q.select(q.from(entityClass));
        return Criteria.createQuery(q).getResultList();
    }
}
