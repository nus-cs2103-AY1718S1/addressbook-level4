//@@author ShaocongDong
package seedu.address.model.task;

import java.util.ArrayList;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a task in the taskBook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    // Content property
    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Description> descriptionProperty();
    Description getDescription();
    ObjectProperty<DateTime> startTimeProperty();
    DateTime getStartDateTime();
    ObjectProperty<DateTime> endTimeProperty();
    DateTime getEndDateTime();

    // functional property
    ObjectProperty<Integer> priorityProperty();
    Integer getPriority();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Boolean> completeProperty();
    Boolean getComplete();
    ObjectProperty<Integer> idProperty();
    Integer getId();
    ObjectProperty<ArrayList<Integer>> peopleIdsProperty();
    ArrayList<Integer> getPeopleIds();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     * state checking involves content property and functional property (priority, tag, and complete)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime())
                && other.getComplete().equals(this.getComplete())
                //&& other.getId().equals(this.getId())
                && other.getPriority().equals(this.getPriority())
                && other.getTags().equals(this.getTags()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Start: ")
                .append(getStartDateTime())
                .append(" End: ")
                .append(getEndDateTime())
                .append(" Complete: ")
                .append(getComplete())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * return true if the underlying two tasks have the same content.
     * @param other
     * @return
     */
    boolean equals(Object other);

}
