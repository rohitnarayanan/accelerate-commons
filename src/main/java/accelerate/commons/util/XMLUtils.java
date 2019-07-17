package accelerate.commons.util;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for XML operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 14, 2015
 */
public final class XMLUtils {
	/**
	 * {@link DocumentBuilderFactory} singleton instance
	 */
	private static DocumentBuilderFactory builderFactory = null;

	/**
	 * {@link XPathFactory} singleton instance
	 */
	private static XPathFactory xPathFactory = null;

	/**
	 * {@link TransformerFactory} singleton instance
	 */
	private static TransformerFactory transformerFactory = null;

	/**
	 * static block
	 */
	static {
		try {
			builderFactory = DocumentBuilderFactory.newInstance();
			xPathFactory = XPathFactory.newInstance();
			transformerFactory = TransformerFactory.newInstance();
		} catch (Exception error) {
			throw new ApplicationException(error);
		}

	}

	/**
	 * @param aInputStream
	 * @return {@link Document}
	 * @throws ApplicationException
	 */
	public static Document loadXML(InputStream aInputStream) throws ApplicationException {
		try {
			return builderFactory.newDocumentBuilder().parse(aInputStream);
		} catch (Exception error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aDocument
	 * @return
	 * @throws ApplicationException
	 */
	public static String serialzeXML(Document aDocument) throws ApplicationException {
		try {
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(aDocument);

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, result);

			return result.getWriter().toString();
		} catch (Exception error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aInstanceType
	 * @param aXMLClassPath
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshalXML(Class<T> aInstanceType, String aXMLClassPath) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(aInstanceType);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			return (T) jaxbUnmarshaller.unmarshal(aInstanceType.getResource(aXMLClassPath));
		} catch (JAXBException error) {
			throw new ApplicationException(error, "error unmarshalling XML");
		}
	}

	/**
	 * @param aXPathExpr
	 * @param aContext
	 * @return
	 * @throws ApplicationException
	 */
	public static NodeList queryNodeSet(String aXPathExpr, Node aContext) throws ApplicationException {
		LOGGER.trace("xPath Expression: {}", aXPathExpr);

		XPath xPath = xPathFactory.newXPath();
		try {
			return (NodeList) xPath.evaluate(aXPathExpr, aContext, XPathConstants.NODESET);
		} catch (XPathExpressionException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aXPathExpr
	 * @param aContext
	 * @return
	 * @throws ApplicationException
	 */
	public static Node queryNode(String aXPathExpr, Node aContext) throws ApplicationException {
		LOGGER.trace("xPath Expression: {}", aXPathExpr);

		XPath xPath = xPathFactory.newXPath();
		try {
			return (Node) xPath.evaluate(aXPathExpr, aContext, XPathConstants.NODE);
		} catch (XPathExpressionException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aXPathExpr
	 * @param aContext
	 * @return
	 * @throws ApplicationException
	 */
	public static String queryValue(String aXPathExpr, Node aContext) throws ApplicationException {
		LOGGER.trace("xPath Expression: {}", aXPathExpr);

		XPath xPath = xPathFactory.newXPath();
		try {
			String value = (String) xPath.evaluate(aXPathExpr + "/text()", aContext, XPathConstants.STRING);

			return StringUtils.trim(value);
		} catch (XPathExpressionException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aXPathExpr
	 * @param aAttributeName
	 * @param aContext
	 * @return
	 * @throws ApplicationException
	 */
	public static String queryAttribute(String aXPathExpr, String aAttributeName, Node aContext)
			throws ApplicationException {
		LOGGER.trace("xPath Expression: {}, attribute: {}", aXPathExpr, aAttributeName);

		XPath xPath = xPathFactory.newXPath();
		try {
			return (String) xPath.evaluate(aXPathExpr + "/@" + aAttributeName, aContext, XPathConstants.STRING);
		} catch (XPathExpressionException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * {@link Logger} instance
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtils.class);

	/**
	 * hidden constructor
	 */
	private XMLUtils() {
	}
}