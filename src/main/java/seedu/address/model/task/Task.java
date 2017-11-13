//@@author srishag
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private ObjectProperty<Header> header;
    private ObjectProperty<Desc> desc;
    private ObjectProperty<Deadline> deadline;

    /**
     * Every field must be present and not null.
     */
    public Task(Header header, Desc desc, Deadline deadline) {
        requireAllNonNull(header, desc, deadline);
        this.header = new SimpleObjectProperty<>(header);
        this.desc = new SimpleObjectProperty<>(desc);
        this.deadline = new SimpleObjectProperty<>(deadline);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getHeader(), source.getDesc(), source.getDeadline());
    }

    public void setHeader(Header header) {
        this.header.set(requireNonNull(header));
    }

    @Override
    public ObjectProperty<Header> headerProperty() {
        return header;
    }

    @Override
    public Header getHeader() {
        return header.get();
    }

    public void setDesc(Desc desc) {
        this.desc.set(requireNonNull(desc));
    }

    @Override
    public ObjectProperty<Desc> descProperty() {
        return desc;
    }

    @Override
    public Desc getDesc() {
        return desc.get();
    }

    public void setDeadline(Deadline deadline) {
        this.deadline.set(requireNonNull(deadline));
    }

    @Override
    public ObjectProperty<Deadline> deadlineProperty() {
        return deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(header, desc, deadline);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
