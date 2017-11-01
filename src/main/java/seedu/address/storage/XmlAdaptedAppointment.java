package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * JAXB-friendly adapted version of the Appointments.
 */
public class XmlAdaptedAppointment {

    @XmlValue
    private String appointmentName;

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAppointment(Appointment source) {
        appointmentName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Appointment toModelType() throws IllegalValueException {
        return new Appointment(appointmentName);
    }

}
