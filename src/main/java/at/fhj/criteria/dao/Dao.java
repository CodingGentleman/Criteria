package at.fhj.criteria.dao;

import at.fhj.criteria.entities.immutable.EntityView;
import at.fhj.criteria.persistence.Persistence;

import java.util.List;

public interface Dao<T extends EntityView> {
    List<T> findAll();
    default void delete(T view) {
        Persistence.INST.remove(view.getEntity());
    }
    default void update(T view) {
        Persistence.INST.merge(view.getEntity());
    }
}
