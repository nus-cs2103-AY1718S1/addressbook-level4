package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Helps with reading from and writing to XML files.
 */
public class XmlUtil {

    //Delimiter used in CSV file
    public static final String COMMA_DELIMITER = ",";
    public static final String NEW_LINE_SEPARATOR = "\n";

    /**
     * Returns the xml data in the file as an object of the specified type.
     *
     * @param file           Points to a valid xml file containing data that match the {@code classToConvert}.
     *                       Cannot be null.
     * @param classToConvert The class corresponding to the xml data.
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if the file is empty or does not have the correct format.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDataFromFile(File file, Class<T> classToConvert)
            throws FileNotFoundException, JAXBException {

        requireNonNull(file);
        requireNonNull(classToConvert);

        if (!FileUtil.isFileExists(file)) {
            throw new FileNotFoundException("File not found : " + file.getAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(classToConvert);
        Unmarshaller um = context.createUnmarshaller();

        return ((T) um.unmarshal(file));
    }

    /**
     * Saves the data in the file in xml format.
     *
     * @param file Points to a valid xml file containing data that match the {@code classToConvert}.
     *             Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if there is an error during converting the data
     *                               into xml and writing to the file.
     */
    public static <T> void saveDataToFile(File file, T data) throws FileNotFoundException, JAXBException {

        requireNonNull(file);
        requireNonNull(data);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found : " + file.getAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(data.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(data, file);
    }

    //@@author kaiyu92
    /**
     * Export the data in the xml file to csv.
     */
    public static void exportDataToFile(String destination, StringBuilder content)
            throws IOException {

        requireNonNull(destination);
        requireNonNull(content);

        FileWriter fileWriter = new FileWriter(destination);
        fileWriter.write(content.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    //@@author kaiyu92
    /**
     * return the specific child list of the xml root
     * @param file parsing the file to become a Document
     * @param nodeName a specific child of the root element
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static NodeList getNodeListFromFile(File file, String nodeName) throws SAXException,
            IOException, ParserConfigurationException {

        requireNonNull(file);
        requireNonNull(nodeName);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();

        return doc.getElementsByTagName(nodeName);
    }

    //@@author kaiyu92
    /**
     * Appending the header to the CSV file
     * E.g. header: title,age,DOB
     * @param sb using StringBuilder to append the header
     * @param header
     */
    public static void appendHeader(StringBuilder sb, String header) {

        requireNonNull(sb);
        requireNonNull(header);

        //Append the header to the CSV file
        sb.append(header);
        sb.append(XmlUtil.NEW_LINE_SEPARATOR);
    }

    //@@author kaiyu92
    /**
     * Appending the content to the CSV file
     * @param sb using StringBuilder to append the content
     * @param element
     * @param fields using varargs as events book and address book have different number of fields
     */
    public static void appendContent(StringBuilder sb, Element element, String ... fields) {

        requireNonNull(sb);
        requireNonNull(element);
        requireNonNull(fields);

        for (String f: fields) {
            // need "\"" at the front and back as some fields uses commas in their text
            // without it "\"", it will treat commas as the separation into different columns
            sb.append("\"" + element.getElementsByTagName(f).item(0).getTextContent() + "\"");
            sb.append(XmlUtil.COMMA_DELIMITER);
        }
    }
}
