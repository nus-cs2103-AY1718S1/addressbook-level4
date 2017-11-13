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
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
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
    private String birthday;
    @XmlElement(required = true)
    private boolean isPrivate;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private boolean isPinned;

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
        birthday = source.getBirthday().value;
        remark = source.getRemark().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        isPrivate = source.isPrivate();
        isPinned = source.isPinned();
    }

    //@@author aziziazfar
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
        Name name = new Name(0);
        Birthday birthday = new Birthday(0);
        Phone phone = new Phone(0);
        Email email = new Email(0);
        Address address = new Address(0);

        if (!isEmptyField(this.name)) {
            name = new Name(this.name);
        }
        if (!isEmptyField(this.phone)) {
            phone = new Phone(this.phone);
        }
        if (!isEmptyField(this.email)) {
            email = new Email(this.email);
        }
        if (!isEmptyField(this.address)) {
            address = new Address(this.address);
        }
        if (!isEmptyField(this.birthday)) {
            birthday = new Birthday(this.birthday);
        }

        final Remark remark = new Remark(this.remark);
        final Set<Tag> tags = new HashSet<>(personTags);
        final boolean isPrivate = this.isPrivate;
        final boolean isPinned = this.isPinned;
        final boolean isSelected = false;
        return new Person(name, phone, email, address, birthday, remark, tags, isPrivate, isPinned, isSelected);
    }

    /**
     * Checks whether the person field is empty.
     */
    public boolean isEmptyField(String input) {
        return input.equals(" ");
    }
}
