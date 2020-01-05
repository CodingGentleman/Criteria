package at.fhj.criteria;

import static at.fhj.criteria.entities.builder.OrderBuilder.anOrder;

import static at.fhj.criteria.entities.builder.AddressBuilder.anAddress;
import static org.junit.jupiter.api.Assertions.assertEquals;

import at.fhj.criteria.dao.AddressDao;
import at.fhj.criteria.entities.Address;
import at.fhj.criteria.entities.Address_;
import at.fhj.criteria.entities.OrderType;
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
        var uc = new TestDataInsert(10);
        uc.batchInsert();
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
        query.where(cb.equal(root.get(Address_.firstname), "F1"));
        var tq = Criteria.createQuery(query);
        var resultList = tq.getResultList();
        assertEquals(1, resultList.size());
    }

    @Test
    public void test() {
        var addressDao = AddressDao.create();
        addressDao.whereLastnameEquals("L2");
        var invoice = anAddress().withFirstname("f1").withLastname("l1").build();
        var order = anOrder().withType(OrderType.B2B).withInvoiceAddress(invoice).build();
    }

    @Test
    public void testUpdate() {
        var addressDao = AddressDao.create();
        var addressView = addressDao.whereLastnameEquals("L2");
        addressView.getEntity().setFirstname("updated");
        addressDao.update(addressView);
        addressView = addressDao.whereLastnameEquals("L2");
        assertEquals("updated", addressView.getFirstname());
    }
}
