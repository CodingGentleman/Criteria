package at.fhj.criteria.entities;

import at.fhj.criteria.entities.builder.Builder;
import at.fhj.criteria.entities.immutable.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@javax.persistence.Entity
@Table(name = Order.TABLE_NAME)
public class Order implements Entity<OrderView>, OrderView {
    public final static String TABLE_NAME = "orders";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated
    @Column(name = "order_type")
    private OrderType type;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval=true)
    private final List<OrderLine> lines = new ArrayList<>();

    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    private Address invoiceAddress;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Address deliveryAddress;

    @ManyToMany(mappedBy = "orders", cascade = CascadeType.REMOVE)
    private List<Voucher> vouchers = new ArrayList<>();

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
    public long getId() {
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
    public List<OrderLineView> getLines() {
        return lines.stream().map(OrderLine::view).collect(Collectors.toList());
    }

    public void addLine(OrderLineView line) {
        this.lines.add(line.getEntity());
    }

    public void addLines(Collection<OrderLineView> lines) {
        this.lines.addAll(lines.stream().map(EntityView::getEntity).collect(Collectors.toList()));
    }

    @Override
    public AddressView getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(AddressView invoiceAddress) {
        this.invoiceAddress = invoiceAddress.getEntity();
    }

    @Override
    public AddressView getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(AddressView deliveryAddress) {
        if(deliveryAddress == null) {
            this.deliveryAddress = null;
        } else {
            this.deliveryAddress = deliveryAddress.getEntity();
        }
    }

    @Override
    public List<VoucherView> getVouchers() {
        return vouchers.stream().map(Voucher::view).collect(Collectors.toList());
    }

    public void addVoucher(VoucherView voucher) {
        vouchers.add(voucher.getEntity());
        voucher.getEntity().addOrder(this.view());
    }
}
