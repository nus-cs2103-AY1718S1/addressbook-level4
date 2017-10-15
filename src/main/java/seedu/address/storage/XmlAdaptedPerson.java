package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateBorrow;
import seedu.address.model.person.DateRepaid;
import seedu.address.model.person.DeadLine;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
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
    private String postalCode;
    @XmlElement (required = true)
    private String debt;
    @XmlElement (required = true)
    private String dateBorrow;
    @XmlElement (required = true)
    private String deadLine;
    @XmlElement (required = true)
    private String dateRepaid;

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
        email = source.getEmail().value;
        address = source.getAddress().value;
        postalCode = source.getPostalCode().value;
        debt = source.getDebt().value;
        dateBorrow = source.getDateBorrow().value;
        deadLine = source.getDeadLine().value;
        dateRepaid = source.getDateRepaid().value;
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
        final PostalCode postalCode = new PostalCode(this.postalCode);
        final Debt debt = new Debt(this.debt);
        final DateBorrow dateBorrow = new DateBorrow(this.dateBorrow);
        final DeadLine deadLine = new DeadLine(this.deadLine);
        final DateRepaid dateRepaid = new DateRepaid(this.dateRepaid);
        final Set<Tag> tags = new HashSet<>(personTags);
        Person adaptedPerson = new Person(name, phone, email, address, postalCode, debt, deadLine, tags);
        adaptedPerson.setDateBorrow(dateBorrow);
        adaptedPerson.setDateRepaid(dateRepaid);
        return adaptedPerson;
    }
}
