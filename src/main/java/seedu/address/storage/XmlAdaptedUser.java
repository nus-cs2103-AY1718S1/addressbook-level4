package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.user.ReadOnlyUser;
import seedu.address.model.user.User;

//@@author quanle1994

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedUser {
    @XmlElement(required = true)
    private String userId;
    @XmlElement(required = true)
    private String salt;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedUser(ReadOnlyUser source) {
        userId = source.getUserId();
        salt = source.getSalt();
        password = source.getPassword();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     */
    public User toModelType() {
        return new User(this.userId, this.salt, this.password);
    }
}
