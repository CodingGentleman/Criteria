package at.fhj.criteria;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Validates if db connections can be made with persistence.xml")
class DatabaseConnectionTest {
    private EntityManagerFactory managerFactory;
    private EntityManager entityManager;

    private static Stream<String> getPersistenceUnitNames() throws Exception {
        var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/persistence.xml");
        var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        var xPath = XPathFactory.newInstance().newXPath().compile("/persistence/persistence-unit/@name");
        var nodeList = (NodeList)xPath.evaluate(document, XPathConstants.NODESET);
        assertFalse(nodeList.getLength() == 0, "persistence.xml not read");
        return IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item).map(Node::getNodeValue);
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
