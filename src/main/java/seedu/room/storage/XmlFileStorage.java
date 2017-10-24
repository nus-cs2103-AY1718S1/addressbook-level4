package seedu.room.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.commons.util.XmlUtil;

/**
 * Stores residentbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given residentbook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableResidentBook residentBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, residentBook);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns resident book in the file or an empty resident book
     */
    public static XmlSerializableResidentBook loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableResidentBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
