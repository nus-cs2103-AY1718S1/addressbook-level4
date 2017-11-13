package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfPic;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.schedule.ReadOnlySchedule;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.socialmedia.ReadOnlySocialMedia;
import seedu.address.model.socialmedia.SocialMedia;
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
    private Boolean favourite;
    @XmlElement(required = true)
    private String profPic;


    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedGroup> grouped = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedSchedule> schedule = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedSocialMedia> socialMedia = new ArrayList<>();

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
        favourite = source.getFavourite().value;
        profPic = source.getProfPic().path;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }

        grouped = new ArrayList<>();
        for (ReadOnlyGroup group: source.getGroups()) {
            grouped.add(new XmlAdaptedGroup(group));
        }

        schedule = new ArrayList<>();
        for (ReadOnlySchedule event: source.getSchedule()) {
            schedule.add(new XmlAdaptedSchedule(event));
        }

        socialMedia = new ArrayList<>();
        for (ReadOnlySocialMedia sm: source.getSocialMedia()) {
            socialMedia.add(new XmlAdaptedSocialMedia(sm));
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

        final List<Group> personGroups = new ArrayList<>();
        for (XmlAdaptedGroup group: grouped) {
            personGroups.add(group.toModelType());
        }

        final List<Schedule> personSchedule = new ArrayList<>();
        for (XmlAdaptedSchedule event: schedule) {
            personSchedule.add(event.toModelType());
        }

        final List<SocialMedia> personSocialMedia = new ArrayList<>();
        for (XmlAdaptedSocialMedia socialMediaUrl: socialMedia) {
            personSocialMedia.add(socialMediaUrl.toModelType());
        }

        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final Favourite favourite = new Favourite(this.favourite);
        final ProfPic profPic = new ProfPic(this.profPic);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Set<Group> groups = new HashSet<>(personGroups);
        final Set<Schedule> schedules = new LinkedHashSet<>(personSchedule);
        final Set<SocialMedia> socialMediaUrls = new HashSet<>(personSocialMedia);
        return new Person(name, phone, email, address, favourite, profPic, tags, groups, schedules, socialMediaUrls);

    }
}
