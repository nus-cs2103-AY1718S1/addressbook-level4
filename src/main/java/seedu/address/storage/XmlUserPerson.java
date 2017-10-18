package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.model.person.UserPerson;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "userperson")
public class XmlUserPerson {

    @XmlElement
    private XmlAdaptedPerson user;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String remark;
    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlUserPerson() {
        user = new XmlAdaptedPerson();
    }

    /**
     * Conversion
     */
    public XmlUserPerson(UserPerson src) {
        this();
        user = new XmlAdaptedPerson(src);
    }

}
