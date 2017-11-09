package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UserPerson;
import seedu.address.model.person.weblink.WebLink;

//@@author bladerail
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
    @XmlElement
    private List<XmlAdaptedWebLink> webLinkList = new ArrayList<>();

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
        webLinkList = new ArrayList<>();
        for (WebLink webLink : source.getWebLinks()) {
            this.webLinkList.add(new XmlAdaptedWebLink(webLink));
        }
    }

    public UserPerson getUser() throws IllegalValueException {
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final ArrayList<Email> email = new ArrayList<>(this.emailList);
        final Address address = new Address(this.address);

        final List<WebLink> webLinkInputs = new ArrayList<>();
        for (XmlAdaptedWebLink webLink: webLinkList) {
            webLinkInputs.add(webLink.toModelType());
        }

        final Set<WebLink> webLinks = new HashSet<>(webLinkInputs);
        return new UserPerson(name, phone, email, address, webLinks);
    }
}
