package at.fhj.criteria.entities;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> firstname;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, String> lastname;

	public static final String FIRSTNAME = "firstname";
	public static final String ID = "id";
	public static final String LASTNAME = "lastname";

}

