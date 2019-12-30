package at.fhj.criteria.entities.immutable;

import at.fhj.criteria.entities.Entity;

/**
 * Interface for immutable Entity Views
 */
public interface EntityView<T extends Entity> {
    T getEntity();
}
