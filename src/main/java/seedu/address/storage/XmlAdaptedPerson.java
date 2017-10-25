package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Status;
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
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String company;
    @XmlElement(required = true)
    private String position;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String note;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {
    }


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        company = source.getCompany().value;
        position = source.getPosition().value;
        status = source.getStatus().value;
        priority = source.getPriority().value;
        note = source.getNote().value;
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
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        Company company = new Company("NIL"); //to handle legacy versions where these optional fields were not stored
        if (this.company != null) {
            company = new Company(this.company);
        }
        Position position = new Position("NIL"); //to handle legacy versions where these optional fields were not stored
        if (this.position != null) {
            position = new Position(this.position);
        }
        Status status = new Status("NIL"); //to handle legacy versions where these optional fields were not stored
        if (this.status != null) {
            status = new Status(this.status);
        }
        Priority priority = new Priority("L"); //to handle legacy versions where these optional fields were not stored
        if (this.priority != null) {
            priority = new Priority(this.priority);
        }
        Note note = new Note("NIL"); //to handle legacy versions where these optional fields were not stored
        if (this.note != null) {
            note = new Note(this.note);
        }
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, company, position, status, priority, note, tags);
    }
}
