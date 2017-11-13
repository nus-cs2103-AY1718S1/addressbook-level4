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
import seedu.address.model.person.Gender;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.photo.PhotoPath;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String gender;
    @XmlElement(required = true)
    private String matricNo;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String birthday;
    @XmlElement(required = true)
    private String timetable;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private String photoPath;

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
        gender = source.getGender().value;
        matricNo = source.getMatricNo().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        timetable = source.getTimetable().value;
        remark = source.getRemark().value;
        photoPath = source.getPhotoPath().value;
        tagged = new ArrayList<>();
        birthday = source.getBirthday().getUnformattedDate();
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
        final Gender gender = new Gender(this.gender);
        final MatricNo matricNo = new MatricNo(this.matricNo);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final Timetable timetable = new Timetable(this.timetable);
        final Remark remark = new Remark(this.remark);
        final PhotoPath photoPath = new PhotoPath(this.photoPath);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Birthday birthday = new Birthday(this.birthday);
        return new Person(name, gender, matricNo, phone, email, address, timetable, remark, photoPath, tags, birthday);
    }
}
