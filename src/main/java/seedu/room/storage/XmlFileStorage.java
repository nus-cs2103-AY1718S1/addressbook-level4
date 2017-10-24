package seedu.room.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.commons.util.XmlUtil;

/**
 * Stores roombook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given roombook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableRoomBook roomBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, roomBook);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns room book in the file or an empty room book
     */
    public static XmlSerializableRoomBook loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableRoomBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
