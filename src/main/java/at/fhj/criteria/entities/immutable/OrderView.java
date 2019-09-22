package at.fhj.criteria.entities.immutable;

import at.fhj.criteria.entities.Address;
import at.fhj.criteria.entities.Order;
import at.fhj.criteria.entities.OrderLine;
import at.fhj.criteria.entities.OrderType;

import java.util.List;

public interface OrderView extends EntityView<Order> {
    int getId();
    OrderType getType();
    List<OrderLine> getLines();
    AddressView getInvoiceAddress();
    AddressView getDeliveryAddress();
}
