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

@javax.persistence.Entity
@Table(name = "orderline")
public class OrderLine implements Entity<OrderLineView>, OrderLineView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    private String name;

    @ManyToMany(mappedBy = "orderLines")
    private Collection<Voucher> vouchers = new ArrayList<>();

    protected OrderLine(){}
    public OrderLine(Builder<OrderLine> builder) {
        Logger.getLogger(OrderLine.class.getName()).log(Level.FINEST,
                "instantiated by "+builder.getClass().getName());
    }

    @Override
    public OrderLineView view() {
        return this;
    }

    @Override
    public OrderLine getEntity() {
        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public OrderView getOrder() {
        return order.view();
    }

    public void setOrder(OrderView order) {
        if(order != null) {
            this.order = order.getEntity();
            order.getEntity().addLine(this.getEntity());
        } else {
            this.order = null;
        }
    }

    public void addVoucher(VoucherView voucher) {
        vouchers.add(voucher.getEntity());
        voucher.getEntity().addOrderLine(this.view());
    }
}
