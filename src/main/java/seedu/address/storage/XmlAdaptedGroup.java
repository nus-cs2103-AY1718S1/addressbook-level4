package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * JAXB-friendly version of the Group.
 */
public class XmlAdaptedGroup {

    @XmlElement(required = true)
    private String name;

    @XmlElement
    private List<XmlAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedGroup
     */
    public XmlAdaptedGroup(ReadOnlyGroup source) {
        this.name = source.getGroupName().fullName;
        persons = new ArrayList<>();
        for (ReadOnlyPerson person: source.getGroupMembers()) {
            persons.add(new XmlAdaptedPerson(person));
        }
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted group
     */
    public Group toModelType() throws IllegalValueException {
        final GroupName groupName = new GroupName(this.name);
        final List<ReadOnlyPerson> groupMembers = new ArrayList<>();
        for (XmlAdaptedPerson person: this.persons) {
            groupMembers.add(person.toModelType());
        }
        return new Group(groupName, groupMembers);
    }
}
