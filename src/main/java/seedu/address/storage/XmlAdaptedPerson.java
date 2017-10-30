package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Country;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.email.Email;
import seedu.address.model.schedule.Schedule;
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
    private String country;
    @XmlElement
    private List<XmlAdaptedEmail> emails = new ArrayList<>();
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedSchedule> scheduled = new ArrayList<>();
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
        country = source.getCountry().value;
        emails = new ArrayList<>();
        for (Email email : source.getEmails()) {
            emails.add(new XmlAdaptedEmail(email));
        }
        address = source.getAddress().value;
        scheduled =  new ArrayList<>();
        for (Schedule schedule : source.getSchedules()) {
            scheduled.add(new XmlAdaptedSchedule(schedule));
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
        final List<Email> personEmails = new ArrayList<>();
        for (XmlAdaptedEmail email : emails) {
            personEmails.add(email.toModelType());
        }
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final List<Schedule> schedules = new ArrayList<>();
        for (XmlAdaptedSchedule schedule : scheduled) {
            schedules.add(schedule.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Country country = new Country(this.country);
        final Set<Email> emails = new HashSet<>(personEmails);
        final Address address = new Address(this.address);
        final Set<Schedule> schedule = new HashSet<>(schedules);
        final Set<Tag> tags = new HashSet<>(personTags);

        return new Person(name, phone, country, emails, address, schedule, tags);
    }
}
