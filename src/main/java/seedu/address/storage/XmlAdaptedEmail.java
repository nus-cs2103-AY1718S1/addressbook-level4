package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.email.Email;

import javax.xml.bind.annotation.XmlValue;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedEmail {

    @XmlValue
    private String email;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEmail() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEmail(Email source) {
        email = source.email;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Email toModelType() throws IllegalValueException {
        return new Email(email);
    }

}
