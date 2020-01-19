package at.fhj.criteria.entities.immutable;

import at.fhj.criteria.entities.Address;

public interface AddressView extends EntityView<Address> {
    long getId();
    String getFirstname();
    String getLastname();
}
