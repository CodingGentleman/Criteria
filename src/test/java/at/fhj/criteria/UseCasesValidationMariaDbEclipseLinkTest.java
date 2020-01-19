package at.fhj.criteria;

import at.fhj.criteria.persistence.DatabaseManagementSystem;
import at.fhj.criteria.persistence.Persistence;
import at.fhj.criteria.persistence.PersistenceProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class UseCasesValidationMariaDbEclipseLinkTest {
    private static UseCases useCases;

    static {
        Persistence.INST.register(DatabaseManagementSystem.MARIADB, PersistenceProvider.ECLIPSELINK);
    }

    @BeforeAll
    static void setup() {
        useCases = new UseCases(50);
    }

    @AfterAll
    static void teardown() {
        Persistence.INST.close();
    }

    @Test
    void testSelect() {
        var c = useCases.selectCriteria();
        var s = useCases.selectSql();
        var h = useCases.selectHql();
        Assertions.assertTrue(c == s && s == h);
    }

    @Test
    void testUpdate() {
        AtomicInteger counter = new AtomicInteger(0);
        Persistence.INST.inTransaction(() -> counter.addAndGet(useCases.updateCriteria()));
        Persistence.INST.inTransaction(() -> counter.addAndGet(useCases.updateSql()));
        Persistence.INST.inTransaction(() -> counter.addAndGet(useCases.updateHql()));
        Assertions.assertEquals(3, counter.get());
    }

    @Test
    void testDelete() {
        AtomicInteger counter = new AtomicInteger(0);
        Persistence.INST.inTransaction(() -> counter.addAndGet(useCases.deleteCriteria()));
        Persistence.INST.inTransaction(() -> counter.addAndGet(useCases.deleteSql()));
        Persistence.INST.inTransaction(() -> counter.addAndGet(useCases.deleteHql()));
        Assertions.assertEquals(3, counter.get());
    }

    @Test
    void testQuery() {
        var c = useCases.queryCriteria();
        var h = useCases.queryHql();
        var s = useCases.querySql();
        Assertions.assertTrue(c == s && s == h);
    }

    @Test
    void testSubQuery() {
        var c = useCases.subQueryCriteria();
        var h = useCases.subQueryHql();
        var s = useCases.subQuerySql();
        Assertions.assertTrue(c == s && s == h);
    }

}
