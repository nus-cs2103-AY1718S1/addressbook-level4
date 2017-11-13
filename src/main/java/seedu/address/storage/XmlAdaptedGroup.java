//@@author hthjthtrh
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * JAXB-friendly version of the Group.
 */
public class XmlAdaptedGroup {

    @XmlElement(required = true)
    private String groupName;
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
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {
        groupName = source.getGrpName();
        persons.addAll(source.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Group toModelType() throws IllegalValueException {
        Group grp =  new Group(this.groupName);
        this.persons.forEach(person -> {
            try {
                grp.add(person.toModelType());
            } catch (DuplicatePersonException e) {
                throw new AssertionError("Shouldn't exist duplicate person");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        });
        return grp;
    }
}
//@@author
