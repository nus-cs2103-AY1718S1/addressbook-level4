package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UserPerson;
/**
 * An Immutable UserPerson that is serializable to XML format
 */
@XmlRootElement(name = "userperson")
public class XmlUserPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private ArrayList<Email> emailList;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    /**
     *
     * This empty constructor is required for marshalling.
     */
    public XmlUserPerson() {
    }

    public XmlUserPerson(UserPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        emailList = new ArrayList<Email>();
        for (Email email : source.getEmail()) {
            emailList.add(email);
        }
        address = source.getAddress().value;
    }

    public UserPerson getUser() throws IllegalValueException {
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final ArrayList<Email> email = new ArrayList<>(this.emailList);
        final Address address = new Address(this.address);
        return new UserPerson(name, phone, email, address);
    }
}
