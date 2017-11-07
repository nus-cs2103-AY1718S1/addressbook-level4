package seedu.address.storage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.ArrayList;
import java.util.List;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedGroup {

    @XmlElement(required = true)
    private String GroupName;
    @XmlElement
    private List<XmlAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {

        GroupName = source.getName().fullName;
        persons = new ArrayList<>();
        for (ReadOnlyPerson person : source.getPersonList()) {
            persons.add(new XmlAdaptedPerson(person));
        }
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Group toModelType() throws IllegalValueException {
        Name name=new Name(GroupName);
        return new Group(name);
    }
}
