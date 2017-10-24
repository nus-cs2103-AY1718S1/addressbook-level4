package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;


/**
 * A list of events that enforces no nulls and uniqueness between its elements.
 * Ensures that there is no overlap of events in the list
 * <p>
 * Supports minimal set of list operations for the app's features.
 *
 * @see Event#equals(Object)
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    //used by asObservableList()
    private final ObservableList<Event> mappedList = EasyBind.map(internalList, (event) -> event);

    /**
     * Constructs empty UniqueEventList.
     */
    public UniqueEventList() {}

    /**
     * Creates a UniqueEventList using given tags.
     * Enforces no nulls.
     */
    public UniqueEventList(Set<Event> events) {
        requireAllNonNull(events);
        internalList.addAll(events);

        sort(LocalDate.now());

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all event in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Event> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueEventList from) {
        final Set<Event> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(event -> !alreadyInside.contains(event))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }


    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Checks if event clashes exist and adds an event to the list.
     *
     * @throws DuplicateEventException if the event to add is a duplicate of an existing event in the list.
     */
    public void add(Event toAdd) throws DuplicateEventException {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateEventException();
        }
        internalList.add(new Event(toAdd));

        assert CollectionUtil.elementsAreUnique(internalList);

        this.sort(LocalDate.now());
    }


    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     *
     * @throws DuplicateEventException if the replacement is equivalent to another existing event in the list.
     * @throws EventNotFoundException  if {@code target} could not be found in the list.
     */
    public void setEvent(Event target, Event editedEvent) throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, new Event(editedEvent));
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws EventNotFoundException if no such event could be found in the list.
     */
    public boolean remove(Event toRemove) throws EventNotFoundException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new EventNotFoundException();
        }
        return eventFoundAndDeleted;
    }

    /**
     * Checks for event clashes when events of the same date is found.
     *
     * @param toCheck
     * @return true when clash exist; else return false
     */
    public boolean hasClashes(Event toCheck) {
        for (Event e : internalList) {
            if (e.getEventTime().getStart().toLocalDate().equals(
                    toCheck.getEventTime().getStart().toLocalDate())) {
                if (DateTimeUtil.checkEventClash(toCheck, e)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Events from selected date always appears at the top, followed by upcoming
     * events then past events. Within the three different groups of Events,
     * namely selected date, upcoming events and past events, the events will
     * be sorted by their starting time in ascending order, and suppose Events
     * share same starting time shorter events will appear first.
     */
    public void sort(LocalDate selectedDate) {
        requireNonNull(selectedDate);

        internalList.sort(new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                LocalDateTime today = LocalDate.now().atStartOfDay();
                if (o1.getEventTime().getStart().toLocalDate().equals(selectedDate)
                        && o2.getEventTime().getStart().toLocalDate().equals(selectedDate)) {
                    if (o1.getEventTime().getStart().isBefore(o2.getEventTime().getStart())) {
                        return -1;
                    } else if (o2.getEventTime().getStart().isBefore(o1.getEventTime().getStart())) {
                        return 1;
                    } else {
                        return o1.getEventDuration().getDuration().compareTo(o2.getEventDuration().getDuration());
                    }
                } else if (o1.getEventTime().getStart().toLocalDate().equals(selectedDate)) {
                    return -1;
                } else if (o2.getEventTime().getStart().toLocalDate().equals(selectedDate)) {
                    return 1;
                } else {
                    //Both not within selected date

                    if (o1.getEventTime().getStart().isAfter(today) && o2.getEventTime().getStart().isAfter(today)) {
                        if (o1.getEventTime().getStart().isBefore(o2.getEventTime().getStart())) {
                            return -1;
                        } else if (o2.getEventTime().getStart().isBefore(o1.getEventTime().getStart())) {
                            return 1;
                        } else {
                            return o1.getEventDuration().getDuration().compareTo(o2.getEventDuration().getDuration());
                        }
                    } else if (o1.getEventTime().getStart().isAfter(today)) {
                        return -1;
                    } else if (o2.getEventTime().getStart().isAfter(today)) {
                        return 1;
                    } else {
                        if (o1.getEventTime().getStart().isBefore(o2.getEventTime().getStart())) {
                            return -1;
                        } else if (o2.getEventTime().getStart().isBefore(o1.getEventTime().getStart())) {
                            return 1;
                        } else {
                            return o1.getEventDuration().getDuration().compareTo(o2.getEventDuration().getDuration());
                        }
                    }
                }
            }
        });
    }

    public void setEvents(UniqueEventList replacement) {
        this.internalList.setAll(replacement.internalList);
        sort(LocalDate.now());
    }

    public void setEvents(List<? extends Event> events) throws DuplicateEventException {
        final UniqueEventList replacement = new UniqueEventList();
        for (final Event event : events) {
            replacement.add(new Event(event));
        }
        setEvents(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && this.internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
