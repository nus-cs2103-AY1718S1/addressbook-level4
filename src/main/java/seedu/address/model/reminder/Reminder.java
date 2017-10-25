package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Reminder in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Reminder implements ReadOnlyReminder {
    private ObjectProperty<Task> task;
    private ObjectProperty<Priority> priority;
    private ObjectProperty<Date> date;
    private ObjectProperty<Message> message;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Reminder(Task task, Priority priority, Date date, Message message, Set<Tag> tags) {
        requireAllNonNull(task, priority, date, message, tags);
        this.task = new SimpleObjectProperty<>(task);
        this.priority = new SimpleObjectProperty<>(priority);
        this.date = new SimpleObjectProperty<>(date);
        this.message = new SimpleObjectProperty<>(message);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyReminder.
     */
    public Reminder(ReadOnlyReminder source) {
        this(source.getTask(), source.getPriority(), source.getDate(), source.getMessage(), source.getTags());
    }

    public void setTask(Task task) {
        this.task.set(requireNonNull(task));
    }

    @Override
    public ObjectProperty<Task> taskProperty() {
        return task;
    }

    @Override
    public Task getTask() {
        return task.get();
    }

    public void setPriority(Priority priority) {
        this.priority.set(requireNonNull(priority));
    }

    @Override
    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    @Override
    public Priority getPriority() {
        return priority.get();
    }

    public void setDate(Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    @Override
    public Date getDate() {
        return date.get();
    }

    public void setMessage(Message message) {
        this.message.set(requireNonNull(message));
    }

    @Override
    public ObjectProperty<Message> messageProperty() {
        return message;
    }

    @Override
    public Message getMessage() {
        return message.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this reminder's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyReminder // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyReminder) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(task, priority, date, message, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * @param replacement
     */
    public void resetData(ReadOnlyReminder replacement) {
        requireNonNull(replacement);

        this.setTask(replacement.getTask());
        this.setPriority(replacement.getPriority());
        this.setDate(replacement.getDate());
        this.setMessage(replacement.getMessage());
        this.setTags(replacement.getTags());
    }
}
