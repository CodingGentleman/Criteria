package at.fhj.criteria.entities;

import at.fhj.criteria.entities.builder.Builder;
import at.fhj.criteria.entities.immutable.AddressView;
import at.fhj.criteria.persistence.Persistence;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.logging.Level;
import java.util.logging.Logger;

@javax.persistence.Entity
@Table(name = "address")
public class Address implements Entity<AddressView>, AddressView {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;
    private String lastname;

    protected Address(){}
    public Address(Builder<Address> builder) {
        Logger.getLogger(Address.class.getName()).log(Level.FINEST,
                "instantiated by "+builder.getClass().getName());
    }

    @Override
    public AddressView view() {
        return this;
    }

    @Override
    public Address getEntity() {
        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
