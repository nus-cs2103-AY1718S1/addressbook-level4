package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;

/**
 * JAXB-friendly adapted version of the Email.
 */
public class XmlAdaptedEmail {

    @XmlValue
    private String email;

    /**
     * Constructs an XmlAdaptedEmail.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEmail() {}
    /**
     * Converts a given Mod into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEmail(Email source) {
        email = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted phone object into the model's Email object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Email toModelType() throws IllegalValueException {
        return new Email(email);
    }
}
