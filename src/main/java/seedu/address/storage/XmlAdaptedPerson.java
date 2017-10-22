package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SchEmail;
import seedu.address.model.person.Website;
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
    private String homeNumber;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String schEmail;
    @XmlElement(required = true)
    private String website;
    @XmlElement(required = true)
    private String address;
    @XmlElement (required = true)
    private String birthday;
    @XmlElement (required = true)
    private String favourite;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

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
        homeNumber = source.getHomeNumber().value;
        email = source.getEmail().value;
        schEmail = source.getSchEmail().value;
        website = source.getWebsite().value;
        address = source.getAddress().value;
        birthday = source.getBirthday().value;
        favourite = source.getFavourite().toString();
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
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
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final HomeNumber homeNumber = new HomeNumber(this.homeNumber);
        final Email email = new Email(this.email);
        final SchEmail schEmail = new SchEmail(this.schEmail);
        final Website website = new Website(this.website);
        final Address address = new Address(this.address);
        final Birthday birthday = new Birthday(this.birthday);
        final Boolean favourite = new Boolean(this.favourite);
        final Set<Tag> tags = new HashSet<>(personTags);

        return new Person(name, phone, homeNumber, email, schEmail, website, address, birthday, favourite, tags);
    }
}
