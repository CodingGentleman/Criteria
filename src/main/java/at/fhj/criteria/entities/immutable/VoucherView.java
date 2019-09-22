package at.fhj.criteria.entities.immutable;

import at.fhj.criteria.entities.Voucher;

import java.util.Collection;

public interface VoucherView extends EntityView<Voucher> {
    String getCode();
    double getValue();
    Collection<OrderView> getOrders();
    Collection<OrderLineView> getOrderLines();
}
