package at.fhj.criteria.entities;

import at.fhj.criteria.entities.builder.Builder;
import at.fhj.criteria.entities.immutable.OrderLineView;
import at.fhj.criteria.entities.immutable.OrderView;
import at.fhj.criteria.entities.immutable.VoucherView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = "voucher")
public class Voucher implements Entity<VoucherView>, VoucherView {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private double value;

    @ManyToMany
    @JoinTable(
            name = "voucher_order",
            joinColumns = { @JoinColumn(name = "voucher_code") },
            inverseJoinColumns = { @JoinColumn(name = "order_id") }
    )
    private Collection<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "voucher_orderline",
            joinColumns = { @JoinColumn(name = "voucher_code") },
            inverseJoinColumns = { @JoinColumn(name = "orderline_id") }
    )
    private Collection<OrderLine> orderLines = new ArrayList<>();;

    protected Voucher(){}
    public Voucher(Builder<Voucher> builder) {
        Logger.getLogger(Voucher.class.getName()).log(Level.FINEST,
                "instantiated by "+builder.getClass().getName());
    }

    @Override
    public VoucherView view() {
        return this;
    }

    @Override
    public Voucher getEntity() {
        return this;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public Collection<OrderView> getOrders() {
        return orders.stream().map(Order::view).collect(Collectors.toList());
    }

    @Override
    public Collection<OrderLineView> getOrderLines() {
        return orderLines.stream().map(OrderLine::view).collect(Collectors.toList());
    }

    public void addOrder(OrderView order) {
        orders.add(order.getEntity());
    }

    public void addOrderLine(OrderLineView orderLine) {
        orderLines.add(orderLine.getEntity());
    }
}
