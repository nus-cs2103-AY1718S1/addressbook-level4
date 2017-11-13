package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.ReadOnlyAddressBook;

//@@author frozventus
/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBookCombined {

    @XmlElement
    private XmlSerializableAddressBook addressBook;
    @XmlElement
    private XmlSerializableAddressBook recycleBin;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBookCombined() {
        addressBook = new XmlSerializableAddressBook();
        recycleBin = new XmlSerializableAddressBook();
    }

    /**
     * Conversion
     */

    public XmlSerializableAddressBookCombined(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin) {
        this();
        this.addressBook = new XmlSerializableAddressBook(addressBook);
        this.recycleBin = new XmlSerializableAddressBook(recycleBin);
    }

    public XmlSerializableAddressBook getAddressBook () {
        return addressBook;
    }

    public XmlSerializableAddressBook getRecycleBin () {
        return recycleBin;
    }
}
