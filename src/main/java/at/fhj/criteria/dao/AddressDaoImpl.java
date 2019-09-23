package at.fhj.criteria.dao;

import at.fhj.criteria.entities.Address;
import at.fhj.criteria.entities.immutable.AddressView;
import at.fhj.criteria.persistence.Criteria;

import java.util.List;

class AddressDaoImpl extends BaseDao implements AddressDao {
    @Override
    public List<AddressView> findAll() {
        return findAll(Address.class);
    }

    @Override
    public AddressView whereLastnameEquals(String lastname) {
        var cb = Criteria.getCriteriaBuilder();
        var query = cb.createQuery(Address.class);
        var root = query.from(Address.class);
        query.select(root);
        query.where(cb.equal(root.get(Address.Field.LASTNAME), lastname));
        return Criteria.createQuery(query).getSingleResult();
    }

    @Override
    public List<AddressView> whereLastnameLike(String lastname) {
        var cb = Criteria.getCriteriaBuilder();
        var query = cb.createQuery(Address.class);
        var root = query.from(Address.class);
        query.select(root);
        query.where(cb.like(root.get(Address.Field.LASTNAME), lastname));
        return Criteria.createQuery(query).getResultList();
    }
}
