package seedu.address.model.task;

import java.util.ArrayList;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<String> nameProperty();
    String getName();
    ObjectProperty<String> descriptionProperty();
    String getDescription();
    ObjectProperty<String> startTimeProperty();
    String getStartDateTime();
    ObjectProperty<String> endTimeProperty();
    String getEndDateTime();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Boolean> completeProperty();
    Boolean getComplete();
    ObjectProperty<Integer> idProperty();
    Integer getId();
    ObjectProperty<ArrayList<Integer>> peopleIndicesProperty();
    ArrayList<Integer> getPeopleIndices();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime())
                && other.getComplete().equals(this.getComplete())
                && other.getId().equals(this.getId()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Start: ")
                .append(getStartDateTime())
                .append(" End: ")
                .append(getEndDateTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
