package seedu.address.model.event;

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
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Title> title;
    private ObjectProperty<Timing> timing;
    private ObjectProperty<Description> description;
    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Timing timing, Description description, Set<Tag> tags) {
        requireAllNonNull(title, timing, description, tags);
        this.title = new SimpleObjectProperty<>(title);
        this.timing = new SimpleObjectProperty<>(timing);
        this.description = new SimpleObjectProperty<>(description
        );
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getTiming(), source.getDescription(), source.getTags());
    }

    @Override
    public ObjectProperty<Title> titleProperty() {
        return title;
    }

    @Override
    public Title getTitle() {
        return title.get();
    }

    public void setTitle(Title title) {
        this.title.set(requireNonNull(title));
    }

    @Override
    public ObjectProperty<Timing> timingProperty() {
        return timing;
    }

    @Override
    public Timing getTiming() {
        return timing.get();
    }

    public void setTiming(Timing timing) {
        this.timing.set(requireNonNull(timing));
    }

    @Override
    public ObjectProperty<Description> descriptionProperty() {
        return description;
    }

    @Override
    public Description getDescription() {
        return description.get();
    }

    public void setDescription(Description description) {
        this.description.set(requireNonNull(description));
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    /**
     * Removes a tag from this person's list of tags if the list contains the tag.
     *
     * @param toRemove Tag to be removed
     */
    public void removeTag(Tag toRemove) {
        tags.get().remove(toRemove);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, timing, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
