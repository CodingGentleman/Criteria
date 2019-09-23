package at.fhj.criteria.dao;

import at.fhj.criteria.entities.immutable.AddressView;

public interface AddressDao extends Dao<AddressView> {
    static AddressDao create() {
        return new AddressDaoImpl();
    }
}
