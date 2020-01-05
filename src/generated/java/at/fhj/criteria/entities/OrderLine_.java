package at.fhj.criteria.entities;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderLine.class)
public abstract class OrderLine_ {

	public static volatile SingularAttribute<OrderLine, Integer> quantity;
	public static volatile SingularAttribute<OrderLine, String> name;
	public static volatile SingularAttribute<OrderLine, Integer> id;
	public static volatile ListAttribute<OrderLine, Voucher> vouchers;
	public static volatile SingularAttribute<OrderLine, Order> order;

	public static final String QUANTITY = "quantity";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String VOUCHERS = "vouchers";
	public static final String ORDER = "order";

}

