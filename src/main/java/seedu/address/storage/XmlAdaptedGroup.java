package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedGroup {

    @XmlElement(required = true)
    private String groupName;
    @XmlElement
    private List<XmlAdaptedPerson> members = new ArrayList<>();
    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(ReadOnlyGroup source) {
        groupName = source.getName().toString();
        for (ReadOnlyPerson person: source.getMembers()) {
            members.add(new XmlAdaptedPerson(person));
        }

    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Group toModelType() throws IllegalValueException {
        final List<Person> personList = new ArrayList<>();
        for (XmlAdaptedPerson person: members) {
            personList.add(person.toModelType());
        }

        final GroupName groupName = new GroupName(this.groupName);
        final Set<Person> persons = new HashSet<>(personList);
        return new Group(groupName, persons);
    }

}
