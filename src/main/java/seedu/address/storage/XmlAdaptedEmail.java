package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.email.Email;

/**
 * JAXB-friendly adapted version of the Email.
 */
public class XmlAdaptedEmail {
    @XmlValue
    private String emailName;

    /**
     * Constructs an XmlAdaptedEmail.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEmail() {}

    /**
     * Converts a given Email into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEmail(Email source) {
        emailName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Email object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Email toModelType() throws IllegalValueException {
        return new Email(emailName);
    }
}
