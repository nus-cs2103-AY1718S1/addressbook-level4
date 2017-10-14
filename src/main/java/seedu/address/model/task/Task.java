package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Task implements ReadOnlyTask {

    private ObjectProperty<Description> description;
    private ObjectProperty<StartDate> startDate;
    private ObjectProperty<Deadline> deadline;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, StartDate startDate, Deadline deadline) {
        requireAllNonNull(description, startDate, deadline);
        this.description = new SimpleObjectProperty<>(description);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.deadline = new SimpleObjectProperty<>(deadline);
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getStartDate(), source.getDeadline());
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
