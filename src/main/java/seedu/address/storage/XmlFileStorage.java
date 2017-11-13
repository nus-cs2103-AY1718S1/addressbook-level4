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
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableAddressBookCombined addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    //@@author frozventus
    /**
     * Returns address book in the file or an empty address book
     */
    public static AddressBookData loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            XmlSerializableAddressBookCombined data =
                    XmlUtil.getDataFromFile(file, XmlSerializableAddressBookCombined.class);
            return new AddressBookData(data.getAddressBook(), data.getRecycleBin());
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
