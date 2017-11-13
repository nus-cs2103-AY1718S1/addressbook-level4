package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

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
     * Overloading method
     * @param file
     * @param eventBook
     * @throws FileNotFoundException
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
     * Overloading method
     * @param file
     * @param addressBook
     * @throws FileNotFoundException
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
     * @param file
     * @param account
     * @throws FileNotFoundException
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
     * @param file
     * @return
     * @throws DataConversionException
     * @throws FileNotFoundException
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
     * @param file
     * @return
     * @throws DataConversionException
     * @throws FileNotFoundException
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
     * @param file
     * @return
     * @throws DataConversionException
     * @throws FileNotFoundException
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
     * @param source source path of the XML file
     * @param destination destination path of the CSV file
     * @param header Header of the CSV file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void exportAddressbook(String source, String destination, String header)
            throws ParserConfigurationException, IOException, SAXException {

        requireNonNull(source);
        requireNonNull(destination);
        requireNonNull(header);

        File addressbookXmlFile = new File(source);

        if (!addressbookXmlFile.exists()) {
            throw new FileNotFoundException("File not found : " + addressbookXmlFile.getAbsolutePath());
        }

        //Get the nodelist of the persons tag
        NodeList personList = XmlUtil.getNodeListFromFile(addressbookXmlFile, "persons");

        StringBuilder sb = new StringBuilder();

        //Append the header to the CSV file
        XmlUtil.appendHeader(sb, header);

        //Append individual person data to the CSV file
        for (int i = 0; i < personList.getLength(); i++) {
            Node personNode = personList.item(i);

            if (personNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elemPerson = (Element) personNode;

                XmlUtil.appendContent(sb, elemPerson, "name", "phone", "address", "birthday",
                    "email", "group", "remark");

                //Append tagged list into the StringBuilder
                NodeList tagList = elemPerson.getElementsByTagName("tagged");

                for (int j = 0; j < tagList.getLength(); j++) {
                    Node tagNode = tagList.item(j);
                    if (tagNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemTag = (Element) tagNode;

                        sb.append("\"" + elemTag.getTextContent() + "\"");
                        sb.append(XmlUtil.COMMA_DELIMITER);
                    }
                }
                //Enter a new line in the CSV file
                sb.append(XmlUtil.NEW_LINE_SEPARATOR);
            }
        }
        XmlUtil.exportDataToFile(destination, sb);
    }

    //@@author kaiyu92
    /**
     * Export eventbook XML Data into CSV file
     * @param source source path of the XML file
     * @param destination destination path of the CSV file
     * @param header Header of the CSV file
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static void exportEventbook(String source, String destination, String header)
            throws ParserConfigurationException, IOException, SAXException {

        File eventbookXmlFile = new File(source);

        if (!eventbookXmlFile.exists()) {
            throw new FileNotFoundException("File not found : " + eventbookXmlFile.getAbsolutePath());
        }

        //Get the nodelist of the events tag
        NodeList eventList = XmlUtil.getNodeListFromFile(eventbookXmlFile, "events");

        StringBuilder sb = new StringBuilder();

        //Append the header to the CSV file
        XmlUtil.appendHeader(sb, header);

        //Append individual event data to the CSV file
        for (int i = 0; i < eventList.getLength(); i++) {
            Node eventNode = eventList.item(i);

            if (eventNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elemEvent = (Element) eventNode;
                XmlUtil.appendContent(sb, elemEvent, "title", "description", "location", "datetime");

                //Enter a new line in the CSV file
                sb.append(XmlUtil.NEW_LINE_SEPARATOR);
            }
        }
        XmlUtil.exportDataToFile(destination, sb);
    }
}
