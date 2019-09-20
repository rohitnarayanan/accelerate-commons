package accelerate.commons.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import accelerate.commons.exception.ApplicationException;

/**
 * Class providing utility methods for XML operations
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since October 2, 2017
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
	 * @param aXMLPath
	 * @return
	 */
	public static Document loadXML(String aXMLPath) {
		if (StringUtils.isEmpty(aXMLPath)) {
			throw new ApplicationException("Parameter XMLPath is required");
		}

		return StreamUtils.loadInputStream(aXMLPath, aInputStream -> {
			try {
				return builderFactory.newDocumentBuilder().parse(aInputStream);
			} catch (IOException | SAXException | ParserConfigurationException error) {
				throw new ApplicationException(error);
			}
		});
	}

	/**
	 * @param aXMLString
	 * @return
	 */
	public static Document stringToXML(String aXMLString) {
		if (StringUtils.isEmpty(aXMLString)) {
			throw new ApplicationException("Parameter aXMLString is required");
		}

		try {
			return builderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(aXMLString)));
		} catch (IOException | SAXException | ParserConfigurationException error) {
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
		} catch (TransformerException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aExpression
	 * @param aContext
	 * @return
	 * @throws ApplicationException
	 */
	public static NodeList xPathNodeList(String aExpression, Node aContext) throws ApplicationException {
		LOGGER.trace("xPath Expression: {}", aExpression);

		XPath xPath = xPathFactory.newXPath();
		try {
			return (NodeList) xPath.evaluate(aExpression, aContext, XPathConstants.NODESET);
		} catch (XPathExpressionException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aExpression
	 * @param aContext
	 * @return
	 * @throws ApplicationException
	 */
	public static Node xPathNode(String aExpression, Node aContext) throws ApplicationException {
		LOGGER.trace("xPath Expression: {}", aExpression);

		XPath xPath = xPathFactory.newXPath();
		try {
			return (Node) xPath.evaluate(aExpression, aContext, XPathConstants.NODE);
		} catch (XPathExpressionException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aExpression
	 * @param aContext
	 * @return
	 * @throws ApplicationException
	 */
	public static String xPathNodeValue(String aExpression, Node aContext) throws ApplicationException {
		LOGGER.trace("xPath Expression: {}", aExpression);

		XPath xPath = xPathFactory.newXPath();
		try {
			String value = StringUtils
					.trim((String) xPath.evaluate(aExpression + "/text()", aContext, XPathConstants.STRING));
			LOGGER.trace("xPath Value: {}", value);

			return value;
		} catch (XPathExpressionException error) {
			throw new ApplicationException(error);
		}
	}

	/**
	 * @param aExpression
	 * @param aAttributeName
	 * @param aContext
	 * @return
	 * @throws ApplicationException
	 */
	public static String xPathNodeAttribute(String aExpression, String aAttributeName, Node aContext)
			throws ApplicationException {
		LOGGER.trace("xPath Expression: {}, attribute: {}", aExpression, aAttributeName);

		XPath xPath = xPathFactory.newXPath();
		try {
			String attribute = StringUtils.trim(
					(String) xPath.evaluate(aExpression + "/@" + aAttributeName, aContext, XPathConstants.STRING));
			LOGGER.trace("xPath Value: {}", attribute);

			return attribute;
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