package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
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
    private String profilePicturePath;
    @XmlElement
    private String appointment;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedGroup> grouped = new ArrayList<>();
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
        appointment = Appointment.getOriginalAppointment(source.getAppointment().value);
        profilePicturePath = source.getProfilePicture().value;
        grouped = new ArrayList<>();
        for (Group group : source.getGroups()) {
            grouped.add(new XmlAdaptedGroup(group));
        }
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
        final List<Group> personGroups = new ArrayList<>();
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedGroup group : grouped) {
            personGroups.add(group.toModelType());
        }
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final Appointment appointment = new Appointment(this.appointment);
        final ProfilePicture profilePicture = new ProfilePicture(this.profilePicturePath);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Set<Group> groups = new HashSet<>(personGroups);
        return new Person(name, phone, email, address, appointment, profilePicture, groups, tags);
    }
}
