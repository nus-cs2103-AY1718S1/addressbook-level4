//@@author hthjthtrh
package seedu.address.model.group;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents a Group in the address book
 */

public class Group extends UniquePersonList {

    private String grpName;

    public Group(String groupName) {
        this.grpName = groupName;
    }

    public Group(Group grp) throws DuplicatePersonException {
        setGrpName(grp.getGrpName());
        setPersons(grp.getPersonList());
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public ObservableList<ReadOnlyPerson> getPersonList() {
        return this.asObservableList();
    }

}
//@@author
