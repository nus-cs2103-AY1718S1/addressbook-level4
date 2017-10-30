package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
    private String dob;
    @XmlElement(required = true)
    private String gender;

    @XmlElement(name = "tagged")
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement(name = "lifeInsuranceId")
    private List<String> lifeInsuranceIds = new ArrayList<>();

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
        gender = source.getGender().toString();
        dob = source.getDateOfBirth().toString();

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        lifeInsuranceIds = new ArrayList<>();
        for (UUID id : source.getLifeInsuranceIds()) {
            lifeInsuranceIds.add(id.toString());
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
        final List<UUID> personLifeInsuranceIds = new ArrayList<>();
        for (String lifeInsuranceId: lifeInsuranceIds) {
            personLifeInsuranceIds.add(UUID.fromString(lifeInsuranceId));
        }
        final Name name = new Name(this.name);
        final Phone phone = this.phone.equals("") ? new Phone() : new Phone(this.phone);
        final Email email = this.email.equals("") ? new Email() : new Email(this.email);
        final Address address = this.address.equals("") ? new Address() : new Address(this.address);
        final DateOfBirth dob = this.dob.equals("") ? new DateOfBirth() : new DateOfBirth(this.dob);
        final Gender gender = this.gender.equals("") ? new Gender() : new Gender(this.gender);
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, dob, gender, tags, personLifeInsuranceIds);
    }

}
