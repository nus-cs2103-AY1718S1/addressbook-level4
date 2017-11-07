package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

//@@author chairz
/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    //    private ObjectProperty<Name> name;
    //    private ObjectProperty<Date> date;
    private ObjectProperty<Description> description;
    private ObjectProperty<Priority> priority;
    private ObjectProperty<DueDate> duedate;

    public Task(Description description, Priority priority, DueDate duedate) {
        requireAllNonNull(description, priority, duedate);
        //        this.name = new SimpleObjectProperty<>(name);
        //        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleObjectProperty<>(description);
        this.priority = new SimpleObjectProperty<>(priority);
        this.duedate = new SimpleObjectProperty<>(duedate);

    }



    //    public void setName(Name name) {
    //        this.name.set(requireNonNull(name));
    //    }


    //    public ObjectProperty<Name> nameProperty() {
    //        return name;
    //    }


    //    public Name getName() {
    //        return name.get();
    //    }

    //    public void setDate(Date date) {
    //        this.date.set(requireNonNull(date));
    //    }

    //    public ObjectProperty<Date> dateProperty() {
    //        return date;
    //    }

    //    public Date getDate() {
    //        return date.get();
    //    }

    public Task(ReadOnlyTask in) {
        this(in.getDescription(), in.getPriority(), in.getDueDate());
    }

    public void setDescription(Description description) {
        this.description.set(requireNonNull(description));
    }

    public ObjectProperty<Description> descriptionProperty() {
        return description;
    }

    public Description getDescription() {
        return description.get();
    }

    public void setPriority(Priority priority) {
        this.priority.set(requireNonNull(priority));
    }

    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    public Priority getPriority() {
        return priority.get();
    }

    public void setDuedate(DueDate dueddate) {
        this.duedate.set(dueddate);
    }

    @Override
    public ObjectProperty<DueDate> dueDateProperty() {
        return duedate;
    }

    @Override
    public DueDate getDueDate() {
        return duedate.get();
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
        return Objects.hash(description, priority);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
