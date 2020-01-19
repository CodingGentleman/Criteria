package at.fhj.criteria.entities.immutable;

import at.fhj.criteria.entities.Order;
import at.fhj.criteria.entities.OrderType;

import java.util.List;

public interface OrderView extends EntityView<Order> {
    long getId();
    OrderType getType();
    List<OrderLineView> getLines();
    AddressView getInvoiceAddress();
    AddressView getDeliveryAddress();
    List<VoucherView> getVouchers();
}
