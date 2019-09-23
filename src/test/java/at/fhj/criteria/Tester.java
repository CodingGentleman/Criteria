package at.fhj.criteria;

import static at.fhj.criteria.entities.builder.OrderBuilder.anOrder;

import static at.fhj.criteria.entities.builder.AddressBuilder.anAddress;

import at.fhj.criteria.dao.AddressDao;
import at.fhj.criteria.entities.Address;
import at.fhj.criteria.entities.OrderType;
import at.fhj.criteria.entities.immutable.AddressView;
import at.fhj.criteria.entities.immutable.OrderView;
import at.fhj.criteria.persistence.Criteria;
import at.fhj.criteria.persistence.DatabaseManagementSystem;
import at.fhj.criteria.persistence.Persistence;
import at.fhj.criteria.persistence.PersistenceProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Tester {

    static {Persistence.INST.register(DatabaseManagementSystem.POSTGRESQL, PersistenceProvider.ECLIPSELINK);}

    @BeforeAll
    static void setup() {
        var uc = new UseCases();
        uc.batchInsert(10);
    }

    @AfterAll
    static void teardown() {
        Persistence.INST.close();
    }

    @Test
    public void criteria() {
        var cb = Criteria.getCriteriaBuilder();
        var query = cb.createQuery(Address.class);
        var root = query.from(Address.class);
        query.select(root);
        query.where(cb.equal(root.get(Address.Field.FIRSTNAME), "F1"));
        var tq = Criteria.createQuery(query);
        var resultList = tq.getResultList();
        for(var result : resultList) {
            System.out.println(result.getFirstname());
        }
    }

    @Test
    public void test() {
        AddressDao.create().findAll();
        var invoice = anAddress().withFirstname("f1").withLastname("l1").build();
        var order = anOrder().withType(OrderType.B2B).withInvoiceAddress(invoice).build();
    }
}
