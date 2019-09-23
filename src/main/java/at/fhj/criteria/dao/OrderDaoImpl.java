package at.fhj.criteria.dao;

import at.fhj.criteria.entities.Order;
import at.fhj.criteria.entities.immutable.OrderView;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public List<OrderView> findAll() {
        return findAll(Order.class);
    }
}
