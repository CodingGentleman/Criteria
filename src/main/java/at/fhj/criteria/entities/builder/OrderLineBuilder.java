package at.fhj.criteria.entities.builder;

import at.fhj.criteria.entities.OrderLine;
import at.fhj.criteria.entities.immutable.OrderLineView;
import at.fhj.criteria.entities.immutable.OrderView;

public class OrderLineBuilder extends BaseBuilder<OrderLine, OrderLineView> {
    private OrderView order;
    private int quantity;
    private String name;

    private OrderLineBuilder() {}

    public static OrderLineBuilder anOrderLine() {
        return new OrderLineBuilder();
    }

    public OrderLineBuilder withOrder(OrderView order) {
        this.order = order;
        return this;
    }

    public OrderLineBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderLineBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected OrderLine buildInstance() {
        var orderLine = new OrderLine(this);
        orderLine.setOrder(this.order);
        orderLine.setName(this.name);
        orderLine.setQuantity(this.quantity);
        return orderLine;
    }
}
