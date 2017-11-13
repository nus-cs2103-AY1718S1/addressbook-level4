package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
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
    private Boolean nameIsPrivate;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private Boolean phoneIsPrivate;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private Boolean emailIsPrivate;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private Boolean addressIsPrivate;
    @XmlElement (required = true)
    private String favourite;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private Boolean remarkIsPrivate;
    @XmlElement(required = true)
    private String avatar;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    //@@author jeffreygohkw
    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        favourite = source.getFavourite().toString();
        remark = source.getRemark().value;
        avatar = source.getAvatar().value;

        nameIsPrivate = source.getName().getIsPrivate();
        phoneIsPrivate = source.getPhone().getIsPrivate();
        emailIsPrivate = source.getEmail().getIsPrivate();
        addressIsPrivate = source.getAddress().getIsPrivate();
        remarkIsPrivate = source.getRemark().getIsPrivate();

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
        if (nameIsPrivate == null) {
            nameIsPrivate = false;
        }
        if (phoneIsPrivate == null) {
            phoneIsPrivate = false;
        }
        if (emailIsPrivate == null) {
            emailIsPrivate = false;
        }
        if (addressIsPrivate == null) {
            addressIsPrivate = false;
        }
        if (remarkIsPrivate == null) {
            remarkIsPrivate = false;
        }
        final Name name = new Name(this.name, this.nameIsPrivate);
        final Phone phone = new Phone(this.phone, this.phoneIsPrivate);
        final Email email = new Email(this.email, this.emailIsPrivate);
        final Address address = new Address(this.address, this.addressIsPrivate);
        final Boolean favourite = new Boolean(this.favourite);
        final Remark remark = new Remark(this.remark, this.remarkIsPrivate);
        final Avatar avatar = new Avatar(this.avatar);
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, favourite, remark, avatar, tags);
    }
}
