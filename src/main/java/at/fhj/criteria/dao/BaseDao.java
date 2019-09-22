package at.fhj.criteria.dao;

import at.fhj.criteria.entities.immutable.EntityView;
import at.fhj.criteria.persistence.Persistence;

abstract class BaseDao<T extends EntityView> implements Dao<T> {
    protected void merge(T entity) {
        Persistence.INST.merge(entity.getEntity());
    }
}
