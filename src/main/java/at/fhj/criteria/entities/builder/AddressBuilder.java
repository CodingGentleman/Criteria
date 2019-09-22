package at.fhj.criteria.entities.builder;

import at.fhj.criteria.entities.Address;
import at.fhj.criteria.entities.immutable.AddressView;

public class AddressBuilder extends BaseBuilder<Address, AddressView> {
    private String firstname;
    private String lastname;

    private AddressBuilder() {}

    public static AddressBuilder anAddress() {
        return new AddressBuilder();
    }

    public AddressBuilder withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public AddressBuilder withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    @Override
    protected Address buildInstance() {
        var address = new Address(this);
        address.setFirstname(this.firstname);
        address.setLastname(this.lastname);
        return address;
    }
}
