package seedu.address.storage;

import static seedu.address.storage.XmlAddressBookStorage.DEFAULT_MERGE_FILE_PATH;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Stores addressbook data in an XML file
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
     * Merges the given two addressbook data into one data file
     */
    public static void mergeDataToFile(XmlSerializableAddressBook defaultFileData, XmlSerializableAddressBook newFileData) throws IOException {
        ObservableList<ReadOnlyPerson> defaultFilePersonList = defaultFileData.getPersonList();
        ObservableList<ReadOnlyPerson> newFilePersonList = newFileData.getPersonList();

        AddressBook mergedAddressBook = new AddressBook();

        for (ReadOnlyPerson defaultDataPerson: defaultFilePersonList) {
            try {
                mergedAddressBook.addPerson(new Person(defaultDataPerson));
            } catch (DuplicatePersonException dpe) {
                // default addressbook file should not have duplicates
                assert false : "Unexpected exception " + dpe.getMessage();
            }
        }

        for (ReadOnlyPerson newDataPerson: newFilePersonList) {
            boolean isSamePerson = false;
            for (ReadOnlyPerson defaultDataPerson: defaultFilePersonList) {
                if (defaultDataPerson.equals(newDataPerson)) {
                    isSamePerson = true;
                    break;
                }
            }
            if (!isSamePerson) {
                try {
                    mergedAddressBook.addPerson(new Person(newDataPerson));
                } catch (DuplicatePersonException dpe) {
                    assert false : "Unexpected exception " + dpe.getMessage();
                }
            }
        }

        File mergeFile = new File(DEFAULT_MERGE_FILE_PATH);
        FileUtil.createIfMissing(mergeFile);

        try {
            XmlUtil.saveDataToFile(mergeFile, new XmlSerializableAddressBook(mergedAddressBook));
        } catch (FileNotFoundException fnfe) {
            assert false : "Unexpected exception " + fnfe.getMessage();
        } catch (JAXBException e) {
            throw new IOException(e.getMessage());
        }
    }
}
