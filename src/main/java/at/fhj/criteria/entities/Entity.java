package at.fhj.criteria.entities;

import at.fhj.criteria.entities.immutable.EntityView;
import at.fhj.criteria.persistence.Persistence;

import javax.persistence.criteria.CriteriaQuery;

/**
 * Interface for all Entities
 */
public interface Entity<T extends EntityView> {
    T view();
}
