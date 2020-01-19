package at.fhj.criteria.entities.builder;

import at.fhj.criteria.entities.Order;
import at.fhj.criteria.entities.OrderType;
import at.fhj.criteria.entities.immutable.AddressView;
import at.fhj.criteria.entities.immutable.OrderLineView;
import at.fhj.criteria.entities.immutable.OrderView;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder extends BaseBuilder<Order, OrderView> {
    private OrderType type;
    private final List<OrderLineView> lines = new ArrayList<>();
    private AddressView invoiceAddress;
    private AddressView deliveryAddress;

    private OrderBuilder() {}

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withType(OrderType type) {
        this.type = type;
        return this;
    }

    public OrderBuilder withLine(OrderLineView line) {
        this.lines.add(line);
        return this;
    }

    public OrderBuilder withInvoiceAddress(AddressView invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
        return this;
    }

    public OrderBuilder withDeliveryAddress(AddressView deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    @Override
    protected Order buildInstance() {
        var order = new Order(this);
        order.setType(this.type);
        order.setDeliveryAddress(this.deliveryAddress);
        order.addLines(this.lines);
        order.setInvoiceAddress(this.invoiceAddress);
        return order;
    }
}
