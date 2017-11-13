package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores rolodex data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given rolodex data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableRolodex rolodex)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, rolodex);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns rolodex in the file or an empty rolodex
     */
    public static XmlSerializableRolodex loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableRolodex.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
