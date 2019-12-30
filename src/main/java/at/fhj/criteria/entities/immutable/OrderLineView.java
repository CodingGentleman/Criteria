package at.fhj.criteria.entities.immutable;

import at.fhj.criteria.entities.OrderLine;

import java.util.List;

public interface OrderLineView extends EntityView<OrderLine> {
    int getId();
    int getQuantity();
    String getName();
    OrderView getOrder();
    List<VoucherView> getVouchers();
}
