package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the application.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private ObjectProperty<Description> description;
    private ObjectProperty<StartDate> startDate;
    private ObjectProperty<Deadline> deadline;
    private ObjectProperty<UniqueTagList> taskTags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, StartDate startDate, Deadline deadline,
                Set<Tag> taskTags) {
        requireAllNonNull(description, startDate, deadline);
        this.description = new SimpleObjectProperty<>(description);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.deadline = new SimpleObjectProperty<>(deadline);
        // protect internal tags from changes in the arg list
        this.taskTags = new SimpleObjectProperty<>(new UniqueTagList(taskTags));
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getStartDate(), source.getDeadline(), source.getTags());
    }

    public void setDescription(Description description) {
        this.description.set(requireNonNull(description));
    }

    @Override
    public ObjectProperty<Description> descriptionProperty() {
        return description;
    }

    @Override
    public Description getDescription() {
        return description.get();
    }

    public void setStartDate(StartDate startDate) {
        this.startDate.set(requireNonNull(startDate));
    }

    @Override
    public ObjectProperty<StartDate> startDateProperty() {
        return startDate;
    }

    @Override
    public StartDate getStartDate() {
        return startDate.get();
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(taskTags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return taskTags;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        taskTags.set(new UniqueTagList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, startDate, deadline);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
