package at.fhj.criteria.persistence;

import at.fhj.criteria.entities.Entity;
import at.fhj.criteria.entities.immutable.EntityView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public final class Criteria {
    public static CriteriaBuilder getCriteriaBuilder() {
        return Persistence.INST.getEntityManager().getCriteriaBuilder();
    }

    public static <T extends Entity<U>, U extends EntityView<T>> ViewTypedQuery<T, U> createQuery(CriteriaQuery<T> criteriaQuery) {
        return Persistence.INST.getEntityManager().createViewQuery(criteriaQuery);
    }
}
