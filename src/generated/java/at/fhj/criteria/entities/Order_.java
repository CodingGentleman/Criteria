package at.fhj.criteria.entities;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Address> deliveryAddress;
	public static volatile SingularAttribute<Order, Address> invoiceAddress;
	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, OrderType> type;
	public static volatile ListAttribute<Order, OrderLine> lines;
	public static volatile ListAttribute<Order, Voucher> vouchers;

	public static final String DELIVERY_ADDRESS = "deliveryAddress";
	public static final String INVOICE_ADDRESS = "invoiceAddress";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String LINES = "lines";
	public static final String VOUCHERS = "vouchers";

}

