package seedu.address.storage;

import java.text.ParseException;

import java.util.Calendar;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.person.Appointment;

//@@author Eric
/**
 * AXB-friendly version of the appointment list of a person
 */
public class XmlAdaptedAppointment {

    @XmlElement(required = true)
    private String description;

    @XmlElement(required = true)
    private String appointmentStart;

    @XmlElement(required = true)
    private String appointmentEnd;

    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Converts a given appointment into this class for JAXB use
     */
    public XmlAdaptedAppointment(Appointment source) {
        description = source.getDescription();
        appointmentStart = source.getDateInStringFormat();
        appointmentEnd = source.getDateEndInStringFormat();
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's appointment object.
     *
     * @throws ParseException if there were any data constraints violated in the adapted person
     */
    public Appointment toModelType() throws ParseException {
        String description = this.description;

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(Appointment.DATE_FORMATTER.parse(appointmentStart));

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(Appointment.DATE_FORMATTER.parse(appointmentEnd));

        return new Appointment(description, calendarStart, calendarEnd);
    }

}
