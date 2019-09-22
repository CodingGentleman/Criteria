package at.fhj.criteria.entities.builder;

import at.fhj.criteria.entities.Entity;
import at.fhj.criteria.entities.immutable.EntityView;
import at.fhj.criteria.persistence.Persistence;

abstract class BaseBuilder<T extends Entity<U>, U extends EntityView<T>> implements Builder<T> {
    protected abstract T buildInstance();

    public U build() {
        T entity = buildInstance();
        Persistence.INST.persist(entity);
        return entity.view();
    }
}
