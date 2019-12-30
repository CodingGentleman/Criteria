package at.fhj.criteria.entities;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Voucher.class)
public abstract class Voucher_ {

	public static volatile SingularAttribute<Voucher, String> code;
	public static volatile CollectionAttribute<Voucher, OrderLine> orderLines;
	public static volatile CollectionAttribute<Voucher, Order> orders;
	public static volatile SingularAttribute<Voucher, Double> value;

	public static final String CODE = "code";
	public static final String ORDER_LINES = "orderLines";
	public static final String ORDERS = "orders";
	public static final String VALUE = "value";

}

