package at.fhj.criteria.entities;

import at.fhj.criteria.entities.builder.Builder;
import at.fhj.criteria.entities.immutable.OrderView;
import at.fhj.criteria.entities.immutable.VoucherView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@javax.persistence.Entity
@Table(name = "orders")
public class Order implements Entity<OrderView>, OrderView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated
    @Column(name = "order_type")
    private OrderType type;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval=true)
    private final List<OrderLine> lines = new ArrayList<>();

    @OneToOne(optional = false)
    private Address invoiceAddress;

    @OneToOne
    private Address deliveryAddress;

    @ManyToMany(mappedBy = "orders")
    private Collection<Voucher> vouchers = new ArrayList<>();

    protected Order(){}
    public Order(Builder<Order> builder) {
        Logger.getLogger(Order.class.getName()).log(Level.FINEST,
                "instantiated by "+builder.getClass().getName());
    }

    @Override
    public OrderView view() {
        return this;
    }

    @Override
    public Order getEntity() {
        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    @Override
    public List<OrderLine> getLines() {
        return lines;
    }

    public void addLine(OrderLine line) {
        this.lines.add(line);
    }

    public void addLines(Collection<OrderLine> lines) {
        this.lines.addAll(lines);
    }

    @Override
    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(Address invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    @Override
    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void addVoucher(VoucherView voucher) {
        vouchers.add(voucher.getEntity());
        voucher.getEntity().addOrder(this.view());
    }
}
