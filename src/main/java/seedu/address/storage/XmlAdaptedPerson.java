package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.customField.CustomField;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.phone.Phone;
import seedu.address.model.person.phone.UniquePhoneList;
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
    private String photoPath;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedPhone> optionalPhones = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedCustomField> customised = new ArrayList<>();

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
        phone = source.getPhone().number;
        email = source.getEmail().value;
        address = source.getAddress().value;
        photoPath = source.getPhoto().pathName;
        tagged = new ArrayList<>();
        optionalPhones = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        for (Phone phone : source.getPhoneList()) {
            optionalPhones.add(new XmlAdaptedPhone(phone));
        }
        for (CustomField customField : source.getCustomFields()) {
            customised.add(new XmlAdaptedCustomField(customField));
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
        final List<CustomField> personCustomFields = new ArrayList<>();
        for (XmlAdaptedCustomField customField : customised) {
            personCustomFields.add(customField.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final Photo photo = new Photo(this.photoPath);
        final Set<Tag> tags = new HashSet<>(personTags);
        UniquePhoneList phoneList = new UniquePhoneList();
        for (XmlAdaptedPhone optionalPhone : optionalPhones) {
            phoneList.add(optionalPhone.toModelType());
        }
        final Set<CustomField> customFields = new HashSet<>(personCustomFields);

        return new Person(name, phone, email, address, photo, phoneList, tags, customFields);
    }
}
