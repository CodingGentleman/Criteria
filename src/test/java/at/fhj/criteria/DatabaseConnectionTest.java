package at.fhj.criteria;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
public class DatabaseConnectionTest {
    private static final String persistenceUnitName = "maria_hibernate";
    EntityManagerFactory managerFactory;
    EntityManager entityManager;

    @BeforeAll
    private void setup() {
        managerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        entityManager = managerFactory.createEntityManager();
    }

    @Test
    public void testConnection() {
        assertTrue(entityManager.isOpen(), "entityManager not open!");
    }

    @AfterAll
    private void tearDown() {
        entityManager.close();
        managerFactory.close();
    }
}
