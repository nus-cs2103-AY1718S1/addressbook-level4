package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.AccessCount;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.SocialMedia;
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
    private String remark;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private Date createdAt;
    @XmlElement
    private int accessCount;
    @XmlElement (required = true)
    private String facebook;
    @XmlElement (required = true)
    private String twitter;
    @XmlElement (required = true)
    private String instagram;

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
        remark = source.getRemark().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        accessCount = source.getAccessCount().numAccess();
        createdAt = source.getCreatedAt();
        facebook = source.getSocialMedia().facebook;
        twitter = source.getSocialMedia().twitter;
        instagram = source.getSocialMedia().instagram;
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
        final Remark remark = new Remark(this.remark);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Date createdAt;
        final AccessCount accessCount = new AccessCount(this.accessCount);
        final SocialMedia socialMedia = new SocialMedia(
                facebook == null ? "" : facebook, twitter == null ? "" : twitter, instagram == null ? "" : instagram);

        if (this.createdAt == null) {
            // In the event that there is no createdAt attribute for that person
            createdAt = new Date();
        } else {
            createdAt = new Date(this.createdAt.getTime());
        }

        return new Person(name, phone, email, address, remark, tags, createdAt, socialMedia, accessCount);

    }
}
