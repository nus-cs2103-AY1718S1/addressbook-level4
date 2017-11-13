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
     * Saves the given data to the specified file.
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

    //@@author HanYaodong
    /**
     * Returns person list in the file or an empty address book
     */
    public static XmlSerializablePersonList loadPersonListFromSaveFile(File file) throws DataConversionException,
        FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializablePersonList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
