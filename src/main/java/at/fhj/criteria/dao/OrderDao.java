package at.fhj.criteria.dao;

import at.fhj.criteria.entities.immutable.OrderView;

public interface OrderDao extends Dao<OrderView> {
    static OrderDao create() {
        return new OrderDaoImpl();
    }
}
