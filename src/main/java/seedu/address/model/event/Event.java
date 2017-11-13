//@@author erik0704
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Name;

/**
 * Main class of Event
 */
public class Event implements Comparable<Event> {
    /**
     * Reuse name class of person because they have the exact behavior for now
     *
     */
    private ObjectProperty<Name> name;
    private ObjectProperty<Dates> date;
    private ObjectProperty<String> description;

    public Event(Name name, Dates date, String description) {
        requireAllNonNull(name, date, description);
        this.name = new SimpleObjectProperty<>(name);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleObjectProperty<>(description);

    }

    public Event(Event source) {
        this(source.getName(), source.getDate(), source.getDescription());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    public Name getName() {
        return name.get();
    }

    public void setDate(Dates date) {
        this.date.set(requireNonNull(date));
    }

    public ObjectProperty<Dates> dateProperty() {
        return date;
    }

    public Dates getDate() {
        return date.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public ObjectProperty<String> descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (other instanceof Event) {
            // state check
            Event e = (Event) other;
            return e.getName().equals(this.getName())
                    && e.getDate().equals(this.getDate())
                    && e.getDescription().equals(this.getDescription());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

    /**
     *
     * @param anotherevent
     * @return 0, -1 or 1 if the date is same, before or after
     *
     */
    public int compareTo(Event anotherevent) {
        Dates anotherdate = anotherevent.getDate();
        if (this.getDate().equals(anotherdate)) {
            return 0;
        } else if (this.getDate().getDate().after(anotherdate.getDate())) {
            return 1;
        } else {
            return -1;
        }
    }
}
