package at.fhj.criteria.entities.immutable;

import at.fhj.criteria.entities.Voucher;

import java.util.List;

public interface VoucherView extends EntityView<Voucher> {
    String getCode();
    double getValue();
    List<OrderView> getOrders();
    List<OrderLineView> getOrderLines();
}
