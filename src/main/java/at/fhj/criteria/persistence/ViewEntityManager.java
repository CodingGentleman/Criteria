package at.fhj.criteria.persistence;

import at.fhj.criteria.entities.Entity;
import at.fhj.criteria.entities.immutable.EntityView;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;

public interface ViewEntityManager extends EntityManager {
    <T extends Entity<U>, U extends EntityView<T>> ViewTypedQuery<T, U> createViewQuery(CriteriaQuery<T> criteriaQuery);
    <T extends Entity<U>, U extends EntityView<T>> ViewTypedQuery<T, U> createViewQuery(String qlString, Class<T> resultClass);
    <T extends Entity<U>, U extends EntityView<T>> ViewTypedQuery<T, U> createNamedViewQuery(String name, Class<T> resultClass);
}
