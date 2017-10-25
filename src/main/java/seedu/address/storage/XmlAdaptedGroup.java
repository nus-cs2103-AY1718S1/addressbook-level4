package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Group;

/**
 * JAXB friendly version of Group
 */
public class XmlAdaptedGroup {

    @XmlValue
    private String groupName;

    /**
     * Required no-args constructor
     */
    public XmlAdaptedGroup () {}

    /**
     * Converts group to into JAXB usable object
     */
    public XmlAdaptedGroup (Group group) {
        this.groupName = group.getGroupName();
    }

    /**
     * Convert from jax-b back to model's Group object
     *
     * @throws IllegalValueException
     */
    public Group toModelType() throws IllegalValueException {
        return new Group (groupName);
    }


}
