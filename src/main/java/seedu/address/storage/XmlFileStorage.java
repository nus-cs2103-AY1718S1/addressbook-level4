package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook and reminder data in an XML file
 */
public class XmlFileStorage {
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

    //@@author justinpoh
    /**
     * Saves the given reminders data to the specified file.
     */
    public static void saveRemindersToFile(File file, XmlSerializableReminders reminders)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, reminders);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }
    //@@author

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

    //@@author justinpoh
    /**
     * Returns reminders in the file or an empty reminder list
     */
    public static XmlSerializableReminders loadRemindersFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableReminders.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
    //@@author

}
