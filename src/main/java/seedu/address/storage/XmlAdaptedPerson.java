package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.weblink.WebLink;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private ArrayList<Email> emailList;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String remark;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedWebLink> webLink = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        emailList = new ArrayList<>();
        for (Email email : source.getEmail()) {
            emailList.add(email);
        }
        address = source.getAddress().value;
        remark = source.getRemark().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        webLink = new ArrayList<>();
        for (WebLink webLink : source.getWebLinks()) {
            this.webLink.add(new XmlAdaptedWebLink(webLink));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final List<WebLink> webLinkInputs = new ArrayList<>();
        for (XmlAdaptedWebLink webLink: webLink) {
            webLinkInputs.add(webLink.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final ArrayList<Email> email = new ArrayList<>(this.emailList);
        final Address address = new Address(this.address);
        final Remark remark = new Remark(this.remark);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Set<WebLink> webLinks = new HashSet<>(webLinkInputs);
        return new Person(name, phone, email, address, remark, tags, webLinks);
    }
}
