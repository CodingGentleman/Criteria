package at.fhj.criteria.entities.immutable;

import at.fhj.criteria.entities.Entity;
import at.fhj.criteria.persistence.Persistence;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Interface for immutable Entity Views
 */
public interface EntityView<T extends Entity> {
    T getEntity();
}
