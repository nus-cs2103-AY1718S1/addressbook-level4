package seedu.address.storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Bloodtype;
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
    private String remark;
    @XmlElement(required = true)
    private String bloodType;
    @XmlElement
    private String appointmentDate;
    @XmlElement
    private String appointmentEndDate;

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
        bloodType = source.getBloodType().type;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        remark = source.getRemark().value;
        if (source.getAppointment().getDate() != null) {
            appointmentDate = Appointment.DATE_FORMATTER.format(source.getAppointment().getDate());
        } else {
            appointmentDate = null;
        }
        if (source.getAppointment().getEndDate() != null) {
            appointmentEndDate = Appointment.DATE_FORMATTER.format(source.getAppointment().getEndDate());
        } else {
            appointmentEndDate = null;
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
        final Bloodtype bloodType = new Bloodtype(this.bloodType);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Remark remark = new Remark(this.remark);
        final Appointment appointment;
        // if there is previously an appointment date, the constructor with appointment date is called
        try {
            if (appointmentDate != null && appointmentEndDate != null) {
                Calendar calendar = Calendar.getInstance();
                Calendar endCalendar = Calendar.getInstance();
                calendar.setTime(Appointment.DATE_FORMATTER.parse(this.appointmentDate));
                endCalendar.setTime(Appointment.DATE_FORMATTER.parse(this.appointmentEndDate));
                appointment = new Appointment(name.toString(), calendar, endCalendar);
            } else if (appointmentDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Appointment.DATE_FORMATTER.parse(this.appointmentDate));
                appointment = new Appointment(name.toString(), calendar);
            } else {
                appointment = new Appointment(name.toString());
            }
            return new Person(name, phone, email, address, bloodType, tags, remark, appointment);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Person(name, phone, email, address, bloodType, tags, remark, new Appointment(name.toString()));
        }

    }
}
