package seedu.address.model.group;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a Group in the address book
 */

public class Group extends UniquePersonList {

    private final String grpName;

    public Group(String groupName) {
        this.grpName = groupName;
    }

    public String getGrpName() {
        return grpName;
    }

    public ObservableList<ReadOnlyPerson> getPersonList() {
        return this.asObservableList();
    }

}
