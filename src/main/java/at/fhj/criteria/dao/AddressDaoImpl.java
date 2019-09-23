package at.fhj.criteria.dao;

import at.fhj.criteria.entities.Address;
import at.fhj.criteria.entities.immutable.AddressView;

import java.util.List;

class AddressDaoImpl extends BaseDao implements AddressDao {
    @Override
    public List<AddressView> findAll() {
        return findAll(Address.class);
    }
}
