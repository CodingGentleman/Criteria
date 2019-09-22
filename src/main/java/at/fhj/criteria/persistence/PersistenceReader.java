package at.fhj.criteria.persistence;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PersistenceReader {
    public static Stream<String> getPersistenceUnitNames() {
        try {
            var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/persistence.xml");
            var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            var xPath = XPathFactory.newInstance().newXPath().compile("/persistence/persistence-unit/@name");
            var nodeList = (NodeList) xPath.evaluate(document, XPathConstants.NODESET);
            return IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item).map(Node::getNodeValue);
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException e) {
            throw new IllegalStateException(e);
        }
    }
}
