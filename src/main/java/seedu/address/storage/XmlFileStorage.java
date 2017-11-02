package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given xml data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableData data)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, data);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns data in the file
     */
    public static <T> T loadDataFromSaveFile(File file, Class<T> classToConvert)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, classToConvert);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
