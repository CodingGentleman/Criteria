package at.fhj.criteria;

import at.fhj.criteria.persistence.PersistenceReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Validates if db connections can be made with persistence.xml")
class DatabaseConnectionTest {
    private EntityManagerFactory managerFactory;
    private EntityManager entityManager;

    private static Stream<String> getPersistenceUnitNames() {
        return PersistenceReader.getPersistenceUnitNames();
    }

    @DisplayName("test connections")
    @ParameterizedTest(name = "connect via {0}")
    @MethodSource("getPersistenceUnitNames")
    void testConnection(String persistenceUnitName) {
        managerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        entityManager = managerFactory.createEntityManager();
        assertTrue(entityManager.isOpen(), "entityManager not open!");
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        managerFactory.close();
    }
}
