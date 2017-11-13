//@@author hthjthtrh
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents a Group in the address book
 */

public class Group extends UniquePersonList {

    private ObjectProperty<String> grpName;
    private ObjectProperty<String> firstPreview;
    private ObjectProperty<String> secondPreview;
    private ObjectProperty<String> thirdPreview;


    public Group(String groupName) {
        requireNonNull(groupName);
        this.grpName = new SimpleObjectProperty<>(groupName);
        initPreviews();
    }

    public Group(Group grp) throws DuplicatePersonException {
        this.grpName = new SimpleObjectProperty<>(grp.getGrpName());
        setPersons(grp.getPersonList());

        initPreviews();
        updatePreviews();
    }

    private void initPreviews() {
        this.firstPreview = new SimpleObjectProperty<>(" ");
        this.secondPreview = new SimpleObjectProperty<>(" ");
        this.thirdPreview = new SimpleObjectProperty<>(" ");
    }


    public ObjectProperty<String> firstPreviewProperty() {
        return firstPreview;
    }

    public ObjectProperty<String> secondPreviewProperty() {
        return secondPreview;
    }

    public ObjectProperty<String> thirdPreviewProperty() {
        return thirdPreview;
    }

    public ObjectProperty<String> grpNameProperty() {
        return grpName;
    }

    public String getGrpName() {
        return grpName.get();
    }

    public void setGrpName(String grpName) {
        this.grpName.set(grpName);
    }

    /**
     * Helper function for updatePreviews to facilitate the use of for loop
     * @return preview properties of this group as a list
     */
    private List<ObjectProperty<String>> getPersonPreviews() {
        return Arrays.asList(firstPreview, secondPreview, thirdPreview);
    }

    public ObservableList<ReadOnlyPerson> getPersonList() {
        return this.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || ((other instanceof Group)
                && this.getGrpName().equals(((Group) other).getGrpName()));
    }

    /**
     * Update preview properties for GroupCard
     */
    public void updatePreviews() {
        int i;
        for (i = 0; i < 3 && i < this.getPersonList().size(); i++) {
            getPersonPreviews().get(i).set(this.getPersonList().get(i).getName().toString());
        }
        for (i = this.getPersonList().size(); i < 3; i++) {
            getPersonPreviews().get(i).set("");
        }
    }

}
//@@author
