package at.fhj.criteria.dao;

import at.fhj.criteria.entities.immutable.AddressView;

import java.util.List;

public interface AddressDao extends Dao<AddressView> {
    static AddressDao create() {
        return new AddressDaoImpl();
    }
    AddressView whereLastnameEquals(String lastname);
    List<AddressView> whereLastnameLike(String lastname);
}
