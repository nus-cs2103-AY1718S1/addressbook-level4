package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook or eventbook data in an XML file
 */
public class XmlFileStorage {

    /**
     * Saves the given eventbook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableEventBook eventBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, eventBook);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableAddressBook addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Saves the given account data to the specified file.
     */
    public static void saveAccountToFile(File file, XmlSerializableAccount account)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, account);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns event book in the file or an empty event book
     */
    public static XmlSerializableEventBook loadEventDataFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableEventBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableAddressBook loadDataFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns accounts in the file or an empty account list
     */
    public static XmlSerializableAccount loadAccountFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAccount.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    //@@author kaiyu92
    /**
     * Export Addressbook XML Data into CSV file
     */
    public static void exportAddressbook(String source, String destination, String header)
            throws FileNotFoundException, ParserConfigurationException, IOException, SAXException {

        File addressbookXmlFile = new File(source);

        if (!addressbookXmlFile.exists()) {
            throw new FileNotFoundException("File not found : " + addressbookXmlFile.getAbsolutePath());
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(addressbookXmlFile);

        doc.getDocumentElement().normalize();

        NodeList personList = doc.getElementsByTagName("persons");

        StringBuilder sb = new StringBuilder();

        //Append the header to the CSV file
        sb.append(header);
        sb.append(XmlUtil.NEW_LINE_SEPARATOR);

        for (int i = 0; i < personList.getLength(); i++) {
            Node personNode = personList.item(i);

            if (personNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elemPerson = (Element) personNode;

                sb.append("\"" + elemPerson.getElementsByTagName("name").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemPerson.getElementsByTagName("phone").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemPerson.getElementsByTagName("address").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemPerson.getElementsByTagName("birthday").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemPerson.getElementsByTagName("email").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemPerson.getElementsByTagName("group").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemPerson.getElementsByTagName("remark").item(0).getTextContent() + "\"");

                NodeList tagList = elemPerson.getElementsByTagName("tagged");
                for (int j = 0; j < tagList.getLength(); j++) {
                    Node tagNode = tagList.item(j);
                    if (tagNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eTag = (Element) tagNode;
                        sb.append(XmlUtil.COMMA_DELIMITER);
                        sb.append("\"" + eTag.getTextContent() + "\"");
                    }
                }
                sb.append(XmlUtil.NEW_LINE_SEPARATOR);
            }
        }
        XmlUtil.exportDataToFile(destination, sb);
    }

    //@@author kaiyu92
    /**
     * Export eventbook XML Data into CSV file
     */
    public static void exportEventbook(String source, String destination, String header)
            throws FileNotFoundException, ParserConfigurationException, IOException, SAXException {

        File eventbookXmlFile = new File(source);

        if (!eventbookXmlFile.exists()) {
            throw new FileNotFoundException("File not found : " + eventbookXmlFile.getAbsolutePath());
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(eventbookXmlFile);

        doc.getDocumentElement().normalize();

        NodeList eventList = doc.getElementsByTagName("events");

        StringBuilder sb = new StringBuilder();

        //Append the header to the CSV file
        sb.append(header);
        sb.append(XmlUtil.NEW_LINE_SEPARATOR);

        for (int i = 0; i < eventList.getLength(); i++) {
            Node eventNode = eventList.item(i);

            if (eventNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elemEvent = (Element) eventNode;

                sb.append("\"" + elemEvent.getElementsByTagName("title").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemEvent.getElementsByTagName("description").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemEvent.getElementsByTagName("location").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.COMMA_DELIMITER);
                sb.append("\"" + elemEvent.getElementsByTagName("datetime").item(0).getTextContent() + "\"");
                sb.append(XmlUtil.NEW_LINE_SEPARATOR);
            }
        }
        XmlUtil.exportDataToFile(destination, sb);
    }
}
