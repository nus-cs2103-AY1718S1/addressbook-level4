# shuang-yang
###### \java\seedu\address\commons\events\ui\HideCalendarEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class HideCalendarEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowCalendarEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowCalendarEvent extends BaseEvent {


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowPhotoSelectionEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Request to display a photo selection window in the main window.
 */
public class ShowPhotoSelectionEvent extends BaseEvent {

    public final int index;

    public ShowPhotoSelectionEvent(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\event\RepeatCommand.java
``` java
package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Period;


/**
 * Change the repeat period of an event.
 */
public class RepeatCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "repeat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the repeat period of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[PERIOD OF REPEAT] "
            + "Example: " + COMMAND_WORD + " 1 " + " 7 ";

    public static final String MESSAGE_REPEAT_EVENT_SUCCESS = "Scheduled repeated Event: %1$s";
    public static final String MESSAGE_NOT_REPEATED = "Period of repetition must be provided.";
    public static final String MESSAGE_TIME_CLASH = "The repeated event has time clash with an existing event";

    private final Index index;
    private final Optional<Period> period;

    /**
     * @param index               of the event in the filtered event list to edit
     * @param period of repetition
     */
    public RepeatCommand(Index index, Optional<Period> period) {
        requireNonNull(index);
        requireNonNull(period);

        this.index = index;
        this.period = period;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        return new CommandResult(String.format(MESSAGE_REPEAT_EVENT_SUCCESS, period));
    }

}
```
###### \java\seedu\address\logic\commands\UpdatePhotoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowPhotoSelectionEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Displays a file chooser for updating of photo.
 */
public class UpdatePhotoCommand extends Command {

    public static final String COMMAND_WORD = "updatephoto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update photo of the person identified by the index "
            + "number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UPDATE_PHOTO_SUCCESS = "Updated photo of person: %1$s";

    private final int index;

    public UpdatePhotoCommand(int index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        EventsCenter.getInstance().post(new ShowPhotoSelectionEvent(index));
        return new CommandResult(String.format(MESSAGE_UPDATE_PHOTO_SUCCESS, lastShownList.get(index)));
    }
}
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_PERIOD = new Prefix("p/");
```
###### \java\seedu\address\logic\parser\event\AddEventCommandParser.java
``` java

            //Initialize period to "0"
            Period period = new Period("0");

            //Since period is optional, set it if it's present
            if (arePrefixesPresent(argMultimap, PREFIX_PERIOD)) {
                period = ParserUtil.parsePeriod(argMultimap.getValue(PREFIX_PERIOD)).get();
            }

            ReadOnlyEvent event = new Event(title, timeslot, description, period);

            return new AddEventCommand(event);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}
```
###### \java\seedu\address\logic\parser\event\RepeatCommandParser.java
``` java
package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.RepeatCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Period;

/**
 * Parses input arguments and creates a new RepeatCommand object
 */
public class RepeatCommandParser implements Parser<RepeatCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RepeatCommand
     * and returns a RepeatCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RepeatCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] tokens = trimmedArgs.split("\\s+");

        Index index;
        Optional<Period> period;

        try {
            index = ParserUtil.parseIndex(tokens[0]);
            period = ParserUtil.parsePeriod(Optional.of(tokens[1]));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE));
        }

        return new RepeatCommand(index, period);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> photo} into an {@code Optional<Photo>}
     * if {@code photo} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Photo> parsePhoto(Optional<String> filePath)
            throws IllegalValueException {
        requireNonNull(filePath);

        String originalFilePath;
        String destFilePath = null;

        if (filePath.isPresent()) {
            originalFilePath = filePath.get();

            //Get the file name.
            String s = File.separator;
            int lastDelimiterPosition = originalFilePath.lastIndexOf(s);
            String fileName = originalFilePath.substring
                    (lastDelimiterPosition + 1);

            if (lastDelimiterPosition == -1 || !fileName.matches
                    (".*\\.(jpg|png|jpeg)")) {
                throw new IllegalValueException(Photo.MESSAGE_PHOTO_CONSTRAINTS);
            } else {
                try {
                    destFilePath = "src" + s + "main" + s + "resources" + s
                            + "images" + s + fileName;
                    File originalFile = new File(originalFilePath);
                    File destFile = new File(destFilePath);

                    // Copy source image file to specified destination.
                    FileUtil.copyFile(originalFile, destFile);
                } catch (IOException e) {
                    throw new IllegalValueException("Invalid file. "
                            + "Please try again.");
                }
            }
        }
        return  filePath.isPresent() ? Optional.of(new Photo(destFilePath))
                : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> photoURL} into an {@code
     * Optional<Photo>} if {@code photoURL} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Photo> parsePhotoUrl(Optional<String> photoUrl)
            throws IllegalValueException {
        requireNonNull(photoUrl);
        return photoUrl.isPresent() ? Optional.of(new Photo(photoUrl.get()))
                : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\UpdatePhotoCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UpdatePhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdatePhotoCommand object
 */
public class UpdatePhotoCommandParser implements Parser<UpdatePhotoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UpdatePhotoCommand
     * and returns an UpdatePhotoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UpdatePhotoCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePhotoCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePhotoCommand.MESSAGE_USAGE));
        }

        return new UpdatePhotoCommand(index.getOneBased());
    }
}
```
###### \java\seedu\address\model\event\Event.java
``` java
    @Override
    public ObjectProperty<Period> periodProperty() {
        return period;
    }

    @Override
    public Period getPeriod() {
        return period.get();
    }

    public void setPeriod(Period period) {
        this.period.set(requireNonNull(period));
    }

    @Override
    public Optional<ReadOnlyEvent> getTemplateEvent() {
        return this.templateEvent;
    }

    @Override
    public void setTemplateEvent(Optional<ReadOnlyEvent> templateEvent) {
        this.templateEvent = templateEvent;
    }
    /**
     * Check if this event happens at an earlier time than the given timeslot.
     * @return true if indeed earlier.
     */
    public boolean happensBefore(Timeslot slot) {
        int comparison = this.getTimeslot().compareTo(slot);
        return comparison < 0;
    }

    /**
     * Check if this event happens at a later time than the given timeslot.
     * @return true if indeed earlier.
     */
    public boolean happensAfter(Timeslot slot) {
        int comparison = this.getTimeslot().compareTo(slot);
        return comparison > 0;
    }

    /**
     * Obtain the duration of the event.
     * @return a Duration object.
     */
    public Duration getDuration() {
        return Duration.ofMinutes(MINUTES.between(this.getStartTime(), this.getEndTime()));
    }

    /**
     * Obtain the start time of the event.
     * @return a LocalTime object.
     */
    public LocalTime getStartTime() {
        int start = this.getTiming().getStart();
        return LocalTime.of(start / 100, start % 100);
    }

    /**
     * Obtain the end time of the event.
     * @return a LocalTime object.
     */
    public LocalTime getEndTime() {
        int end = this.getTiming().getEnd();
        return LocalTime.of(end / 100, end % 100);
    }

    /**
     * Obtain the end time of the event.
     * @return a Date object.
     */
    public java.util.Date getEndDateTime() {
        LocalDate date = this.getDate().toLocalDate();
        LocalTime endTime = this.getEndTime();
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
        return java.util.Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Check if two events have time clash.
     * @param other Event to compare with
     * @return true if clashes.
     */
    public boolean clashesWith(Event other) {
        int ts = this.getTiming().getStart();
        int te = this.getTiming().getEnd();
        int os = other.getTiming().getStart();
        int oe = other.getTiming().getEnd();

        if (this.getDate().equals(other.getDate()) && !(ts >= oe) && !(te <= os)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Event other) {
        return this.getTimeslot().compareTo(other.getTimeslot());
    }
```
###### \java\seedu\address\model\event\EventList.java
``` java
        Event targetEvent = new Event(target);
        if (!internalMap.containsValue(targetEvent)) {
            throw new EventNotFoundException();
        }

        Event editedEvent = new Event(edited);
        editedEvent.setTemplateEvent(Optional.of(target));

        if (hasClashWith(editedEvent)) {
            throw new EventTimeClashException();
        }
        internalMap.remove(targetEvent.getTimeslot());
        internalMap.put(editedEvent.getTimeslot(), editedEvent);

```
###### \java\seedu\address\model\event\EventList.java
``` java
        final boolean eventFound = internalMap.containsValue(toRemove);
        if (!eventFound) {
            throw new EventNotFoundException();
        }
        internalMap.remove(toRemove.getTimeslot());

        return eventFound;
```
###### \java\seedu\address\model\event\EventList.java
``` java
    /**
     * Check if a given event has any time clash with any event in the EventList.
     * @param event for checking
     * @return true if there is a clashing event.
     */
    private boolean hasClashWith(Event event) {
        Iterator<Event> iterator = this.iterator();
        Optional<ReadOnlyEvent> templateEvent = event.getTemplateEvent();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            boolean isSameEvent = templateEvent.isPresent() && templateEvent.get().equals(e);
            if (e.clashesWith(event) && !isSameEvent) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the backing tree map as an {@code ObservableList}.
     */
    public ObservableList<ReadOnlyEvent> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Event> iterator() {
        Collection<Event> c = internalMap.values();
        return c.iterator();
    }

```
###### \java\seedu\address\model\event\exceptions\DuplicateEventException.java
``` java

/**
 * Signals that the operation will result in duplicate Event objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
```
###### \java\seedu\address\model\event\exceptions\EventTimeClashException.java
``` java
package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will cause a timeclash of events.
 */
public class EventTimeClashException extends Exception {
    public EventTimeClashException() {
        super("Operation would result in time clash in events.");
    }
}
```
###### \java\seedu\address\model\event\exceptions\RepetitionException.java
``` java
package seedu.address.model.event.exceptions;

/**
 * Signals that there is error with scheduling repetition of an event.
 */

public class RepetitionException extends Exception {
    public RepetitionException() {
        super("Unable to schedule repetition.");
    }
}
```
###### \java\seedu\address\model\event\ObservableTreeMap.java
``` java
package seedu.address.model.event;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.sun.javafx.collections.MapListenerHelper;

import javafx.beans.InvalidationListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

/**
 * A self-defined TreeMap class that implements observability.
 *
 */
public class ObservableTreeMap<K, V> implements ObservableMap<K, V> {
    private ObservableEntrySet entrySet;
    private ObservableKeySet keySet;
    private ObservableValues values;

    private MapListenerHelper<K, V> listenerHelper;
    private final TreeMap<K, V> backingMap;

    public ObservableTreeMap(TreeMap<K, V> map) {
        this.backingMap = map;
    }

    public SortedMap<K, V> subMap(K start, K end) {
        return backingMap.subMap(start, end);
    }


    /**
     * An adaptation of MapChangeListener.Change for ObservableTreeMap.
     */
    private class SimpleChange extends MapChangeListener.Change<K, V> {

        private final K key;
        private final V old;
        private final V added;
        private final boolean wasAdded;
        private final boolean wasRemoved;

        public SimpleChange(K key, V old, V added, boolean wasAdded, boolean wasRemoved) {
            super(ObservableTreeMap.this);
            assert(wasAdded || wasRemoved);
            this.key = key;
            this.old = old;
            this.added = added;
            this.wasAdded = wasAdded;
            this.wasRemoved = wasRemoved;
        }

        @Override
        public boolean wasAdded() {
            return wasAdded;
        }

        @Override
        public boolean wasRemoved() {
            return wasRemoved;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValueAdded() {
            return added;
        }

        @Override
        public V getValueRemoved() {
            return old;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            if (wasAdded) {
                if (wasRemoved) {
                    builder.append("replaced ").append(old).append("by ").append(added);
                } else {
                    builder.append("added ").append(added);
                }
            } else {
                builder.append("removed ").append(old);
            }
            builder.append(" at key ").append(key);
            return builder.toString();
        }

    }

    protected void callObservers(MapChangeListener.Change<K, V> change) {
        MapListenerHelper.fireValueChangedEvent(listenerHelper, change);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listenerHelper = MapListenerHelper.addListener(listenerHelper, listener);
    }

    @Override
    public void addListener(MapChangeListener<? super K, ? super V> observer) {
        listenerHelper = MapListenerHelper.addListener(listenerHelper, observer);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listenerHelper = MapListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> observer) {
        listenerHelper = MapListenerHelper.removeListener(listenerHelper, observer);
    }

    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return backingMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return backingMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return backingMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        V ret;
        if (backingMap.containsKey(key)) {
            ret = backingMap.put(key, value);
            if (ret == null && value != null || ret != null && !ret.equals(value)) {
                callObservers(new SimpleChange(key, ret, value, true, true));
            }
        } else {
            ret = backingMap.put(key, value);
            callObservers(new SimpleChange(key, ret, value, true, false));
        }
        return ret;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        if (!backingMap.containsKey(key)) {
            return null;
        }
        V ret = backingMap.remove(key);
        callObservers(new SimpleChange((K) key, ret, null, false, true));
        return ret;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        for (Iterator<Entry<K, V>> i = backingMap.entrySet().iterator(); i.hasNext(); ) {
            Entry<K, V> e = i.next();
            K key = e.getKey();
            V val = e.getValue();
            i.remove();
            callObservers(new SimpleChange(key, val, null, false, true));
        }
    }

    @Override
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new ObservableKeySet();
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        if (values == null) {
            values = new ObservableValues();
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        if (entrySet == null) {
            entrySet = new ObservableEntrySet();
        }
        return entrySet;
    }

    @Override
    public String toString() {
        return backingMap.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return backingMap.equals(obj);
    }

    @Override
    public int hashCode() {
        return backingMap.hashCode();
    }

    /**
     * An adaptation of KeySet implementing observability.
     */
    private class ObservableKeySet implements Set<K> {

        @Override
        public int size() {
            return backingMap.size();
        }

        @Override
        public boolean isEmpty() {
            return backingMap.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return backingMap.keySet().contains(o);
        }

        @Override
        public Iterator<K> iterator() {
            return new Iterator<K>() {

                private Iterator<Entry<K, V>> entryIt = backingMap.entrySet().iterator();
                private K lastKey;
                private V lastValue;
                @Override
                public boolean hasNext() {
                    return entryIt.hasNext();
                }

                @Override
                public K next() {
                    Entry<K, V> last = entryIt.next();
                    lastKey = last.getKey();
                    lastValue = last.getValue();
                    return last.getKey();
                }

                @Override
                public void remove() {
                    entryIt.remove();
                    callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
                }

            };
        }

        @Override
        public Object[] toArray() {
            return backingMap.keySet().toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return backingMap.keySet().toArray(a);
        }

        @Override
        public boolean add(K e) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean remove(Object o) {
            return ObservableTreeMap.this.remove(o) != null;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return backingMap.keySet().containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return removeRetain(c, false);
        }


        /**
         * Remove the collection of values specified.
         */
        private boolean removeRetain(Collection<?> c, boolean remove) {
            boolean removed = false;
            for (Iterator<Entry<K, V>> i = backingMap.entrySet().iterator(); i.hasNext();) {
                Entry<K, V> e = i.next();
                if (remove == c.contains(e.getKey())) {
                    removed = true;
                    K key = e.getKey();
                    V value = e.getValue();
                    i.remove();
                    callObservers(new SimpleChange(key, value, null, false, true));
                }
            }
            return removed;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return removeRetain(c, true);
        }

        @Override
        public void clear() {
            ObservableTreeMap.this.clear();
        }

        @Override
        public String toString() {
            return backingMap.keySet().toString();
        }

        @Override
        public boolean equals(Object obj) {
            return backingMap.keySet().equals(obj);
        }

        @Override
        public int hashCode() {
            return backingMap.keySet().hashCode();
        }

    }


    /**
     * An adaptation of Values class implementing observability.
     */
    private class ObservableValues implements Collection<V> {

        @Override
        public int size() {
            return backingMap.size();
        }

        @Override
        public boolean isEmpty() {
            return backingMap.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return backingMap.values().contains(o);
        }

        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>() {

                private Iterator<Entry<K, V>> entryIt = backingMap.entrySet().iterator();
                private K lastKey;
                private V lastValue;
                @Override
                public boolean hasNext() {
                    return entryIt.hasNext();
                }

                @Override
                public V next() {
                    Entry<K, V> last = entryIt.next();
                    lastKey = last.getKey();
                    lastValue = last.getValue();
                    return lastValue;
                }

                @Override
                public void remove() {
                    entryIt.remove();
                    callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
                }

            };
        }

        @Override
        public Object[] toArray() {
            return backingMap.values().toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return backingMap.values().toArray(a);
        }

        @Override
        public boolean add(V e) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean remove(Object o) {
            for (Iterator<V> i = iterator(); i.hasNext();) {
                if (i.next().equals(o)) {
                    i.remove();
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return backingMap.values().containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return removeRetain(c, true);
        }

        /**
         * Remove the collection of values specified.
         */
        private boolean removeRetain(Collection<?> c, boolean remove) {
            boolean removed = false;
            for (Iterator<Entry<K, V>> i = backingMap.entrySet().iterator(); i.hasNext();) {
                Entry<K, V> e = i.next();
                if (remove == c.contains(e.getValue())) {
                    removed = true;
                    K key = e.getKey();
                    V value = e.getValue();
                    i.remove();
                    callObservers(new SimpleChange(key, value, null, false, true));
                }
            }
            return removed;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return removeRetain(c, false);
        }

        @Override
        public void clear() {
            ObservableTreeMap.this.clear();
        }

        @Override
        public String toString() {
            return backingMap.values().toString();
        }

        @Override
        public boolean equals(Object obj) {
            return backingMap.values().equals(obj);
        }

        @Override
        public int hashCode() {
            return backingMap.values().hashCode();
        }

    }


    /**
     * An adaptation of Entry class implementing observability.
     */
    private class ObservableEntry implements Entry<K, V> {

        private final Entry<K, V> backingEntry;

        public ObservableEntry(Entry<K, V> backingEntry) {
            this.backingEntry = backingEntry;
        }

        @Override
        public K getKey() {
            return backingEntry.getKey();
        }

        @Override
        public V getValue() {
            return backingEntry.getValue();
        }

        @Override
        public V setValue(V value) {
            V oldValue = backingEntry.setValue(value);
            callObservers(new SimpleChange(getKey(), oldValue, value, true, true));
            return oldValue;
        }

        @Override
        public final boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public final int hashCode() {
            return (getKey() == null ? 0 : getKey().hashCode())
                    ^ (getValue() == null ? 0 : getValue().hashCode());
        }

        @Override
        public final String toString() {
            return getKey() + "=" + getValue();
        }

    }


    /**
     * An adaptation of EntrySet class implementing observability.
     */
    private class ObservableEntrySet implements Set<Entry<K, V>> {

        @Override
        public int size() {
            return backingMap.size();
        }

        @Override
        public boolean isEmpty() {
            return backingMap.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return backingMap.entrySet().contains(o);
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new Iterator<Entry<K, V>>() {

                private Iterator<Entry<K, V>> backingIt = backingMap.entrySet().iterator();
                private K lastKey;
                private V lastValue;
                @Override
                public boolean hasNext() {
                    return backingIt.hasNext();
                }

                @Override
                public Entry<K, V> next() {
                    Entry<K, V> last = backingIt.next();
                    lastKey = last.getKey();
                    lastValue = last.getValue();
                    return new ObservableEntry(last);
                }

                @Override
                public void remove() {
                    backingIt.remove();
                    callObservers(new SimpleChange(lastKey, lastValue, null, false, true));
                }
            };
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object[] toArray() {
            Object[] array = backingMap.entrySet().toArray();
            for (int i = 0; i < array.length; ++i) {
                array[i] = new ObservableEntry((Entry<K, V>) array[i]);
            }
            return array;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            T[] array = backingMap.entrySet().toArray(a);
            for (int i = 0; i < array.length; ++i) {
                array[i] = (T) new ObservableEntry((Entry<K, V>) array[i]);
            }
            return array;
        }

        @Override
        public boolean add(Entry<K, V> e) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean remove(Object o) {
            boolean ret = backingMap.entrySet().remove(o);
            if (ret) {
                Entry<K, V> entry = (Entry<K, V>) o;
                callObservers(new SimpleChange(entry.getKey(), entry.getValue(), null, false, true));
            }
            return ret;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return backingMap.entrySet().containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends Entry<K, V>> c) {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return removeRetain(c, false);
        }

        /**
         * Remove the collection of values specified.
         */
        private boolean removeRetain(Collection<?> c, boolean remove) {
            boolean removed = false;
            for (Iterator<Entry<K, V>> i = backingMap.entrySet().iterator(); i.hasNext();) {
                Entry<K, V> e = i.next();
                if (remove == c.contains(e)) {
                    removed = true;
                    K key = e.getKey();
                    V value = e.getValue();
                    i.remove();
                    callObservers(new SimpleChange(key, value, null, false, true));
                }
            }
            return removed;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return removeRetain(c, true);
        }

        @Override
        public void clear() {
            ObservableTreeMap.this.clear();
        }

        @Override
        public String toString() {
            return backingMap.entrySet().toString();
        }

        @Override
        public boolean equals(Object obj) {
            return backingMap.entrySet().equals(obj);
        }

        @Override
        public int hashCode() {
            return backingMap.entrySet().hashCode();
        }

    }

}
```
###### \java\seedu\address\model\event\Period.java
``` java
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an Event's period of repetition in the address book.
 * Guarantees: is valid as declared in {@link #isValidPeriod(String)}
 */
public class Period {
    public static final String MESSAGE_PERIOD_CONSTRAINTS =
            "Period should be positive integers smaller than 366 (days)";

    /*
     *
     */
    public static final String PERIOD_VALIDATION_REGEX = "([0-9]|[1-9][0-9]|[1-2][0-9][0-9]|3[0-5][0-9]|36[0-6])";

    public final String period;

    /**
     * Validates given period.
     *
     * @throws IllegalValueException if given period string is invalid.
     */
    public Period(String period) throws IllegalValueException {
        requireNonNull(period);
        String trimmedPeriod = period.trim();
        if (!isValidPeriod(trimmedPeriod)) {
            throw new IllegalValueException(MESSAGE_PERIOD_CONSTRAINTS);
        }
        this.period = trimmedPeriod;
    }

    /**
     * Returns true if a given string is a valid event period.
     */
    public static boolean isValidPeriod(String test) {
        return !test.equals("") && test.matches(PERIOD_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return period;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Period // instanceof handles nulls
                && this.period.equals(((Period) other).period)); // state check
    }

    @Override
    public int hashCode() {
        return period.hashCode();
    }
}
```
###### \java\seedu\address\model\event\ReadOnlyEvent.java
``` java

    ObjectProperty<Period> periodProperty();

    Period getPeriod();

    Optional<ReadOnlyEvent> getTemplateEvent();

    void setTemplateEvent(Optional<ReadOnlyEvent> templateEvent);

    boolean happensBefore(Timeslot slot);

    boolean happensAfter(Timeslot slot);

    Duration getDuration();

    LocalTime getStartTime();

    java.util.Date getEndDateTime();
```
###### \java\seedu\address\model\event\RepeatEventTimerTask.java
``` java
package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.TimerTask;

import javafx.application.Platform;
import seedu.address.model.Model;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * Add a new instance of the event after the specified period.
 */
public class RepeatEventTimerTask extends TimerTask {

    private ReadOnlyEvent targetEvent;
    private int period;
    private Model model;

    /**
     * @param event event to edit
     * @param int period of repetition
     */
    public RepeatEventTimerTask(Model model, ReadOnlyEvent event, int period) {
        requireNonNull(event);

        this.model = model;
        this.targetEvent = event;
        this.period = period;
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}.
     */
    private Event createEditedEvent(ReadOnlyEvent eventToEdit) {
        assert eventToEdit != null;
        Event event = new Event(eventToEdit);
        Timeslot currentTimeslot = event.getTimeslot();

        // If the eventToEdit is far in the past, only add the repeated event in the next week to avoid adding too
        // many event at the same time
        while (currentTimeslot.isBefore(Timeslot.getNow())) {
            currentTimeslot = currentTimeslot.plusDays(period);
        }
        event.setTimeslot(currentTimeslot);

        return event;
    }

    @Override
    public void run() {
        Event editedEvent = createEditedEvent(targetEvent);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addEvent(editedEvent);
                } catch (EventTimeClashException etce) {
                    model.scheduleRepeatedEvent(editedEvent);
                }
            }
        });

    }
}
```
###### \java\seedu\address\model\event\timeslot\Date.java
``` java
    public Date(LocalDate localDate) {
        this.day = localDate.getDayOfMonth();
        this.month = localDate.getMonthValue();
        this.year = localDate.getYear();
    }
```
###### \java\seedu\address\model\event\timeslot\Date.java
``` java
    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }
```
###### \java\seedu\address\model\event\timeslot\Timeslot.java
``` java
    /**
     * Increase the date by specified number of days.
     * @return the new timeslot
     */
    public Timeslot plusDays(int days) {
        Date date = this.getDate().addDays(days);
        Timing timing = this.getTiming();
        return new Timeslot(date, timing);
    }

    /**
     * Get the current time as Timeslot.
     * @return the current timesot.
     */
    public static Timeslot getNow() {
        Date dateNow = new Date(LocalDate.now());
        Timing timeNow = new Timing(LocalTime.now());
        return new Timeslot(dateNow, timeNow);
    }

    /**
     * Check if the timeslot is before another timeslot.
     * @return true if it is.
     */
    public boolean isBefore(Timeslot slot) {
        return this.compareTo(slot) < 0;
    }

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Schedule a repeated event.
     */
    void scheduleRepeatedEvent(ReadOnlyEvent event);
}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns the last changed event.
     */
    ReadOnlyEvent getLastChangedEvent();

    /**
     * Returns the last changed event.
     */
    ReadOnlyEvent getNewlyAddedEvent();

    Date getCurrentDate();
}
```
###### \java\seedu\address\storage\XmlAdaptedEvent.java
``` java
        Period period = new Period("0"); //to handle legacy versions where the optional field is not present
        if (this.period != null) {
            period = new Period(this.period);
        }
```
###### \java\seedu\address\ui\CalendarView.java
``` java

package seedu.address.ui;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.timeslot.Timeslot;


/**
 * The calendar view to show all the scheduled events.
 */

public class CalendarView extends UiPart<Region> {

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass
            .getPseudoClass("selected");
    private static final String FXML = "CalendarView.fxml";

    private static Supplier<LocalDate> today = LocalDate::now;
    private static Supplier<LocalDate> startOfWeek = () -> today.get()
            .minusDays(today.get().getDayOfWeek().getValue() - 1);
    private static Supplier<LocalDate> endOfWeek = () -> startOfWeek.get()
            .plusDays(6);
    private static final int SLOT_LENGTH = 30;
    private static final int LAST_PAGE = -1;
    private static final int NEXT_PAGE = 1;

    private static final DataFormat paneFormat = new DataFormat("DraggingPane");

    private static LocalDate currentToday;
    private static LocalDate currentStartOfWeek;
    private static LocalDate currentEndOfWeek;
    private static Timeslot startOfWeekTimeslot;
    private static Timeslot endOfWeekTimeslot;

    private final LocalTime firstSlotStart = LocalTime.of(7, 0);
    private final Duration slotLength = Duration.ofMinutes(SLOT_LENGTH);
    private final LocalTime lastSlotStart = LocalTime.of(23, 59);
    private final int lastSlotIndex = (int) MINUTES.between(firstSlotStart, lastSlotStart) / SLOT_LENGTH + 1;

    private final List<TimeSlot> timeSlots = new ArrayList<>();

    private final TreeMap<ReadOnlyEvent, Node> addedEvents = new TreeMap<>();

    private final Logger logger = LogsCenter.getLogger(CalendarView.class);
    private final Logic logic;
    private ObservableList<ReadOnlyEvent> eventList;

    //Used to store details of dragging event
    private StackPane draggingPane;
    private int draggingPaneSpan;
    private ReadOnlyEvent draggedEvent;

    @FXML
    private VBox calendarViewPlaceHolder;

    @FXML
    private GridPane calendarView;

    @FXML
    private GridPane headers;

    public CalendarView(ObservableList<ReadOnlyEvent> eventList, Logic logic) {
        super(FXML);

        this.logic = logic;

        init(eventList);

        registerAsAnEventHandler(this);
    }


    /**
     * Initialize all values and views.
     *
     * @param eventList all existing events list
     */
    private void init(ObservableList<ReadOnlyEvent> eventList) {
        currentToday = today.get();
        currentStartOfWeek = startOfWeek.get();
        currentEndOfWeek = endOfWeek.get();

        initSlots(calendarView);
        initPageTurnerButtons(headers);
        initDateHeader(headers);
        initDateTimeHeader(calendarView);
        initEvents(calendarView, eventList, null);
        initScrollPanes();
    }


    /**
     * Initialize all timeslots
     *
     * @param calendarView gridPane of the calendar
     */
    private void initSlots(GridPane calendarView) {
        ObjectProperty<TimeSlot> mouseAnchor = new SimpleObjectProperty<>();

        for (LocalDate date = currentStartOfWeek; !date.isAfter(currentEndOfWeek);
             date = date.plusDays(1)) {
            int slotIndex = 1;

            for (LocalDateTime startTime = date.atTime(firstSlotStart);
                 !startTime.isAfter(date.atTime(lastSlotStart));
                 startTime = startTime.plus(slotLength)) {

                TimeSlot timeSlot = new TimeSlot(startTime, slotLength);
                timeSlots.add(timeSlot);
                registerSelectionHandler(timeSlot, mouseAnchor);

                int rowIndex = (int) MINUTES.between(firstSlotStart, timeSlot.getStartDateTime()) / SLOT_LENGTH + 1;
                addDropHandling(timeSlot, rowIndex);

                calendarView.add(timeSlot.getView(), timeSlot.getDayOfWeek().getValue(), slotIndex);
                slotIndex++;
            }
        }
    }

    /**
     * Initialize the page-turn buttons
     *
     * @param headers gridPane of the calendar header
     */

    private void initPageTurnerButtons(GridPane headers) {
        Button lastPage = new Button();
        Button nextPage = new Button();
        lastPage.setPrefSize(80, 30);
        lastPage.setMinSize(80, 30);
        nextPage.setPrefSize(60, 30);
        nextPage.setMinSize(40, 30);
        lastPage.setStyle("-fx-background-color: transparent");
        nextPage.setStyle("-fx-background-color: transparent");
        lastPage.setGraphic(new ImageView(new Image(getClass().getResource("/images/lastPage.png").toExternalForm
                (), 25, 25, true, true)));
        nextPage.setGraphic(new ImageView(new Image(getClass().getResource("/images/nextPage.png").toExternalForm
                (), 25, 25, true, true)));

        lastPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentStartOfWeek = currentStartOfWeek.minusDays(7);
                currentEndOfWeek = currentEndOfWeek.minusDays(7);
                initSlots(calendarView);
                initDateHeader(headers);
                initEvents(calendarView, eventList, null);
            }
        });
        nextPage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentStartOfWeek = currentStartOfWeek.plusDays(7);
                currentEndOfWeek = currentEndOfWeek.plusDays(7);
                initSlots(calendarView);
                initDateHeader(headers);
                initEvents(calendarView, eventList, null);
            }
        });

        headers.add(lastPage, 0, 0);
        headers.add(nextPage, 8, 0);
    }



    /**
     * Initialize header dates (horizontal axis)
     *
     * @param headers gridPane of the calendar header
     */
    private void initDateHeader(GridPane headers) {
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E\nMMM d");
        StackPane[] headerPanes = new StackPane[8];

        for (LocalDate date = currentStartOfWeek; !date.isAfter(currentEndOfWeek);
             date = date.plusDays(1)) {
            int columnIndex = date.getDayOfWeek().getValue();

            Label label = new Label(date.format(dayFormatter));
            label.setPadding(new Insets(1));
            label.setTextAlignment(TextAlignment.CENTER);
            StackPane pane = new StackPane();
            pane.setPrefSize(135, 50);
            pane.setMinSize(100, 50);
            pane.setStyle("-fx-background-color:#FEDFE1");
            pane.getChildren().add(label);
            GridPane.setHalignment(label, HPos.CENTER);
            if (headers.getChildren().remove(headerPanes[columnIndex])) {
            }
            headers.add(pane, columnIndex, 0);
            headerPanes[columnIndex] = pane;
        }
    }

    /**
     * Initialize header time (vertical axis)
     *
     * @param calendarView gridPane of the calendar
     */
    private void initDateTimeHeader(GridPane calendarView) {
        int slotIndex = 1;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        for (LocalDateTime startTime = currentToday.atTime(firstSlotStart);
             !startTime.isAfter(currentToday.atTime(lastSlotStart));
             startTime = startTime.plus(slotLength)) {
            Label label = new Label(startTime.format(timeFormatter));
            label.setPadding(new Insets(2));
            StackPane pane = new StackPane(label);
            pane.setPrefSize(80, 20);
            GridPane.setHalignment(label, HPos.RIGHT);
            calendarView.add(pane, 0, slotIndex);
            slotIndex++;
        }
    }

    /**
     * Select events that take place in the current week and place them on the calendar accordingly.
     *
     * @param calendarView gridPane of the calendar
     * @param eventList list of all events
     */
    private void initEvents(GridPane calendarView, ObservableList<ReadOnlyEvent> eventList, ReadOnlyEvent
            lastChangedEvent) {
        this.eventList = eventList;
        initStartEndOfWeek();
        ObservableList<ReadOnlyEvent> eventsThisWeek = extractEvents(eventList);

        clearCurrentPanes(calendarView);

        //Iteratively add the events to the calendar view
        for (ReadOnlyEvent event:eventsThisWeek) {
            if (!addedEvents.containsKey(event)) {
                StackPane eventPane = createPane(event);
                addEventPaneToCalendarView(calendarView, event, eventPane);
            }
        }

    }

    /**
     * Initialize scroll panes to wrap around calendar and headers to enable scrolling.
     */
    private void initScrollPanes() {
        ScrollPane scrollableCalendar = new ScrollPane(calendarView);
        ScrollPane scrollableHeader = new ScrollPane(headers);
        scrollableHeader.setMinHeight(80);

        calendarViewPlaceHolder.getChildren().addAll(scrollableHeader, scrollableCalendar);
        scrollableCalendar.hvalueProperty().bindBidirectional(scrollableHeader.hvalueProperty());
    }

    /**
     * Updates information of last changed event
     *
     * @param calendarView gridPane of the calendar
     * @param eventList list of all events
     * @param lastChangedEvent event edited
     * @param newlyAddedEvent new event added
     *
     */
    private void updateEvents(GridPane calendarView, ObservableList<ReadOnlyEvent> eventList, ReadOnlyEvent
            lastChangedEvent, ReadOnlyEvent newlyAddedEvent) {
        this.eventList = eventList;

        // Remove existing pane that corresponds to last changed event
        if (lastChangedEvent != null
                && lastChangedEvent.happensAfter(startOfWeekTimeslot)
                && lastChangedEvent.happensBefore(endOfWeekTimeslot)) {
            removeDuplicatedPane(calendarView, lastChangedEvent);
        }

        // Add pane for the newly added event
        if (newlyAddedEvent != null
                && newlyAddedEvent.happensAfter(startOfWeekTimeslot)
                && newlyAddedEvent.happensBefore(endOfWeekTimeslot)) {
            StackPane eventPane = createPane(newlyAddedEvent);
            addEventPaneToCalendarView(calendarView, newlyAddedEvent, eventPane);
        }
    }

    /**
     * Initialize the current start and end of week
     */
    private void initStartEndOfWeek() {
        String startOfThisWeek = CalendarView.currentStartOfWeek.toString();
        String endOfThisWeek = CalendarView.currentEndOfWeek.toString();
        String[] startofWeekTokens = startOfThisWeek.split("-");
        String[] endofWeekTokens = endOfThisWeek.split("-");

        try {
            startOfWeekTimeslot = new Timeslot(startofWeekTokens[2] + "/" + startofWeekTokens[1] + "/"
                    + startofWeekTokens[0] + " " + "0700-0701");
            endOfWeekTimeslot = new Timeslot(endofWeekTokens[2] + "/" + endofWeekTokens[1] + "/"
                    + endofWeekTokens[0] + " " + "2358-2359");
        } catch (IllegalValueException ive) {
            throw new RuntimeException("Start and end of current week are not correctly initialized.");
        }
    }


    /**
     * Extract events that are scheduled in the current week.
     * @param eventList a list of all the events in the address book
     * @return an ObservableList of events scheduled in the current week
     */
    private ObservableList<ReadOnlyEvent> extractEvents(ObservableList<ReadOnlyEvent> eventList) {
        return eventList.stream().filter(event -> event.happensBefore(endOfWeekTimeslot)).filter(event -> event
                .happensAfter(startOfWeekTimeslot)).collect(Collectors.toCollection
                (FXCollections::observableArrayList));
    }

    /**
     * When updating events, remove the existing pane first before adding a new one.
     * @param calendarView where events are added
     * @param lastChangedEvent event to be removed (if found)
     */

    private void removeDuplicatedPane(GridPane calendarView, ReadOnlyEvent lastChangedEvent) {
        if (calendarView.getChildren().remove(addedEvents.get(lastChangedEvent))) {
            addedEvents.remove(lastChangedEvent);
        }
    }

    /**
     * When initializing, remove the existing panes before adding new ones.
     * @param calendarView where events are added
     */

    private void clearCurrentPanes(GridPane calendarView) {
        calendarView.getChildren().removeAll(addedEvents.values());
        addedEvents.clear();
    }

    /**
     * Draw a stack pane that displays the information of a given event.
     * @param event Event to be shown.
     * @return the stack pane created
     */
    private StackPane createPane(ReadOnlyEvent event) {
        String[] colors = {"#81C7D4", "#FEDFE1", "#D7C4BB", "#D7B98E"};
        int randomColor = (int) (Math.random() * 4);

        //Create the label
        Label eventTitle = new Label();
        eventTitle.setWrapText(true);
        eventTitle.setStyle("-fx-alignment: CENTER; -fx-font-size: 10pt; -fx-text-style: bold;");
        eventTitle.setText(event.getTitle().toString() + "\n");

        //Create the pane
        StackPane eventPane = new StackPane();
        eventPane.setMaxWidth(135.0);
        eventPane.setStyle("-fx-background-color: " + colors[randomColor] + "; -fx-alignment: CENTER; "
                + "-fx-border-color: " + "white");

        //Add listener to mouse-click event to show detail of the event
        eventPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                try {
                    logic.execute("eventfind " + event.getTitle().toString());
                } catch (CommandException | ParseException e) {
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            }
        });

        //Add the label to the pane
        eventPane.getChildren().addAll(eventTitle);

        return eventPane;
    }

    /**
     * Add a stack pane displaying event details to the calendar view.
     * @param calendarView where events will be added
     * @param event the event to be displayed
     * @param eventPane the stack pane to be edded
     */
    private void addEventPaneToCalendarView(GridPane calendarView, ReadOnlyEvent event, StackPane eventPane) {
        LocalDate date = event.getDate().toLocalDate();
        int columnIndex = date.getDayOfWeek().getValue();
        int rowIndex = (int) MINUTES.between(firstSlotStart, event.getStartTime()) / SLOT_LENGTH + 1;
        int rowSpan = (((int) event.getDuration().toMinutes() + SLOT_LENGTH - 1)) / SLOT_LENGTH;

        //Add event pane to the corresponding slots on Calendar
        calendarView.add(eventPane, columnIndex, rowIndex, 1, rowSpan);

        //Add on drag listener
        registerDragHandler(eventPane, event, rowSpan);

        //Store events that have been added for future reference
        addedEvents.put(event, eventPane);

    }

    /**
     * Registers handlers on the time slot to manage selecting a range of
     * slots in the grid.
     *
     * @param timeSlot    selected
     * @param mouseAnchor where the mouse is at
     */

    private void registerSelectionHandler(TimeSlot timeSlot, ObjectProperty<TimeSlot> mouseAnchor) {
        timeSlot.getView().setOnDragDetected(event -> {
            mouseAnchor.set(timeSlot);
            timeSlot.getView().startFullDrag();
            timeSlots.forEach(slot ->
                    slot.setSelected(slot == timeSlot));
        });

        timeSlot.getView().setOnMouseDragEntered(event -> {
            TimeSlot startSlot = mouseAnchor.get();
            timeSlots.forEach(slot ->
                    slot.setSelected(isBetween(slot, startSlot, timeSlot)));
        });

        timeSlot.getView().setOnMouseReleased(event -> mouseAnchor.set(null));
    }


    /**
     * Registers dragging handlers on the time slots to enable changing event scheduling through drag-and-drop.
     *
     * @param eventPane event pane to be dragged
     * @param event event being re-scheduled
     * @param rowSpan span of the event pane in the gridpane
     */
    private void registerDragHandler(StackPane eventPane, ReadOnlyEvent event, int rowSpan) {
        eventPane.setOnDragDetected(e -> {
            Dragboard dragBoard = eventPane.startDragAndDrop(TransferMode.MOVE);
            dragBoard.setDragView(eventPane.snapshot(null, null));
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.put(paneFormat, " ");
            dragBoard.setContent(clipboardContent);

            //Register the detail of the dragged event pane
            draggingPane = eventPane;
            draggedEvent = event;
            draggingPaneSpan = rowSpan;
        });
    }

    /**
     * Registers dropping handlers on the time slots to enable changing event scheduling through drag-and-drop.
     *
     * @param timeSlot Starting TimeSlot to drop the event at
     * @param rowIndex row of the dropped TimeSlot
     */
    private void addDropHandling(TimeSlot timeSlot, int rowIndex) {
        StackPane pane = timeSlot.getView();
        pane.setOnDragOver(e -> {
            Dragboard dragBoard = e.getDragboard();
            if (dragBoard.hasContent(paneFormat) && draggingPane != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        pane.setOnDragDropped(e -> {
            Dragboard dragBoard = e.getDragboard();
            if (dragBoard.hasContent(paneFormat) && rowIndex + draggingPaneSpan <= lastSlotIndex) {
                try {
                    int eventIndex = eventList.indexOf(draggedEvent) + 1;
                    String date = timeSlot.getDateAsString();
                    String startTime = timeSlot.getStartTimeAsString();
                    String endTime = timeSlot.getEndTimeAsString(draggingPaneSpan);

                    //Update event's new date and time information through an edit command
                    CommandResult commandResult = logic.execute("eventedit " + eventIndex + " t/" + date
                            + " " + startTime + "-" + endTime);
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
                } catch (CommandException | ParseException exc) {
                    raise(new NewResultAvailableEvent(exc.getMessage(), true));
                }

                e.setDropCompleted(true);

                //Dropping finished, reset the details of dragged pane
                draggingPane = null;
                draggedEvent = null;
                draggingPaneSpan = 0;
            }
        });

    }

    /**
     * Upon receiving an AddressBookChangedEvent, update the event list accordingly.
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        logger.info("LastChangedEvent is " + abce.data.getLastChangedEvent());
        logger.info("NewlyAddedEvent is " + abce.data.getNewlyAddedEvent());
        updateEvents(calendarView, abce.data.getEventList(), abce.data.getLastChangedEvent(), abce.data
                .getNewlyAddedEvent());
    }


    // Utility method that checks if testSlot is "between" startSlot and endSlot
    // Here "between" means in the visual sense in the grid: i.e. does the time slot
    // lie in the smallest rectangle in the grid containing startSlot and endSlot
    //
    // Note that start slot may be "after" end slot (either in terms of day, or time, or both).

    // The strategy is to test if the day for testSlot is between that for startSlot and endSlot,
    // and to test if the time for testSlot is between that for startSlot and endSlot,
    // and return true if and only if both of those hold.

    // Finally we note that x <= y <= z or z <= y <= x if and only if (y-x)*(z-y) >= 0.

    /**
     * Check whether a time slot is between the other two time slots
     *
     * @param testSlot  slot used for testing
     * @param startSlot starting time slot
     * @param endSlot   ending time slot
     */
    private boolean isBetween(TimeSlot testSlot, TimeSlot startSlot, TimeSlot endSlot) {

        boolean daysBetween = testSlot.getDayOfWeek().compareTo(startSlot.getDayOfWeek())
                * endSlot.getDayOfWeek().compareTo(testSlot.getDayOfWeek())
                >= 0;

        boolean timesBetween = testSlot.getStartTime().compareTo(startSlot.getStartTime())
                * endSlot.getStartTime().compareTo(testSlot.getStartTime()) >= 0;

        return daysBetween && timesBetween;
    }

    /**
     * Class representing a time interval, or "Time Slot", along with a view.
     * View is just represented by a region with minimum size, and style class.
     * Has a selected property just to represent selection.
     */

    public static class TimeSlot {

        private final LocalDateTime start;
        private final Duration duration;
        private final StackPane view;

        private final BooleanProperty selected = new SimpleBooleanProperty();

        public TimeSlot(LocalDateTime start, Duration duration) {
            this.start = start;
            this.duration = duration;

            view = new StackPane();
            view.setPrefSize(135, 15);
            view.setMinSize(100, 15);
            view.getStyleClass().add("time-slot");

            selectedProperty().addListener((obs, wasSelected, isSelected) ->
                    view.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected));

        }

        public final BooleanProperty selectedProperty() {
            return selected;
        }

        public final boolean isSelected() {
            return selectedProperty().get();
        }

        public final void setSelected(boolean selected) {
            selectedProperty().set(selected);
        }

        public LocalDateTime getStartDateTime() {
            return start;
        }

        public LocalTime getStartTime() {
            return start.toLocalTime();
        }

        public String getDateAsString() {
            LocalDate date = start.toLocalDate();
            String[] tokens = date.toString().split("-");
            return tokens[2] + "/" + tokens[1] + "/"
                    + tokens[0];
        }

        public String getStartTimeAsString() {
            LocalTime startTime = getStartTime();
            String[] tokens = startTime.toString().split(":");
            return tokens[0] + tokens[1];
        }

        public String getEndTimeAsString(int span) {
            LocalTime endTime = getStartTime().plus(SLOT_LENGTH * span, ChronoUnit.MINUTES);
            String[] tokens = endTime.toString().split(":");
            return tokens[0] + tokens[1];
        }

        public DayOfWeek getDayOfWeek() {
            return start.getDayOfWeek();
        }

        public Duration getDuration() {
            return duration;
        }

        public StackPane getView() {
            return view;
        }

    }

}
```
###### \java\seedu\address\ui\EventCard.java
``` java

/**
 * An UI component that displays information of a {@code Event}.
 *
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable titles cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyEvent event;

    private Index index;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label timing;
    @FXML
    private Label date;
    @FXML
    private Label description;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        this.event = event;
        this.index = Index.fromZeroBased(displayedIndex);
        id.setText(displayedIndex + ". ");
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        title.textProperty().bind(Bindings.convert(event.titleProperty()));
        timing.textProperty().bind(Bindings.convert(event.timingProperty()));
        date.textProperty().bind(Bindings.convert(event.dateProperty()));
        description.textProperty().bind(Bindings.convert(event.descriptionProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }

    public Index getIndex() {
        return index;
    }
}
```
###### \java\seedu\address\ui\EventListPanel.java
``` java
/**
 * Panel containing the list of events.
 *
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<EventCard> eventListView;

    public EventListPanel(ObservableList<ReadOnlyEvent> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyEvent> eventList) {
        ObservableList<EventCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        logger.info("UI ------ Got eventList with " + eventList.size() + " events.");
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Upon receiving an AddressBookChangedEvent, update the event list accordingly.
     */
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        ObservableList<ReadOnlyEvent> eventList = abce.data.getEventList();
        ObservableList<EventCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code EventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }


    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
        //When calendar button is clicked, the browserPlaceHolder will switch
        // to the calendar view
        calendarView = new CalendarView(logic.getFilteredEventList(), logic);
        calendarButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!browserPlaceholder.getChildren().contains(calendarView
                        .getRoot())) {
                    browserPlaceholder.getChildren().add(calendarView.getRoot());
                    raise(new ShowCalendarEvent());
                } else {
                    browserPlaceholder.getChildren().remove(calendarView.getRoot());
                }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the calendar view.
     */
    @FXML
    public void handleShowCalendar() {
        if (!browserPlaceholder.getChildren().contains(calendarView.getRoot()
        )) {
            browserPlaceholder.getChildren().add(calendarView
                    .getRoot());
        }
    }

    /**
     * Hides the calendar view.
     */
    @FXML
    public void handleHideCalendar() {
        if (browserPlaceholder.getChildren().contains(calendarView.getRoot())) {
            browserPlaceholder.getChildren().remove(calendarView
                    .getRoot());
        }
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowCalendarEvent(ShowCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleShowCalendar();
    }

    @Subscribe
    private void handleHideCalendarEvent(HideCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHideCalendar();
    }

    /**
     * On receiving ShowPhotoSelectionEvent, display a file chooser window to choose photo from local file system and
     * update the photo of specified person.
     */
    @Subscribe
    private void handleShowPhotoSelectionEvent(ShowPhotoSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJpg = new FileChooser
                .ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterJpeg = new FileChooser
                .ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterPng = new FileChooser
                .ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJpg,
                extFilterJpeg, extFilterPng);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(primaryStage.getScene().getWindow());

        try {
            logic.execute("edit " + event.index + " ph/"
                    + file.toURI().getPath());
        } catch (CommandException | ParseException e) {
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
    /**
     * Register the image import button for click event.
     */

    private void registerImageSelectionButton(Index index) {
        //Set onClickListener for the image import button
        photoSelectionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilterJpg = new FileChooser
                        .ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                FileChooser.ExtensionFilter extFilterJpeg = new FileChooser
                        .ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");
                FileChooser.ExtensionFilter extFilterPng = new FileChooser
                        .ExtensionFilter ("PNG files (*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(extFilterJpg,
                        extFilterJpeg, extFilterPng);

                //Show open file dialog
                File file = fileChooser.showOpenDialog(((Node) t.getTarget())
                        .getScene().getWindow());

                //Update photo field in addressbook through an edit command
                try {
                    logger.fine("Person Panel register button for index "
                            + index.getZeroBased());
                    CommandResult commandResult = logic.execute("edit " + index.getZeroBased() + " ph/"
                            + file.toURI().getPath());
                    raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
                } catch (CommandException | ParseException e) {
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            }
        });
    }
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
        //Load the photo of the contact
        String imagePath = person.getPhoto().toString();
        Image image = new Image(new File(imagePath).toURI().toString());
        photo.setImage(image);
        storedPerson = person;
```
###### \resources\view\BrightTheme.css
``` css

.background {
    -fx-background-color: derive(white, 100%);
    background-color: white; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #343434;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #343434;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #343434;
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: white;
    -fx-control-inner-background: white;
    -fx-background-color: white;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color: transparent transparent derive(-fx-base, 80%) transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal *.split-pane-divider {
    -fx-background-color: derive(white, 20%);
    -fx-border-color: transparent transparent transparent transparent;
    -fx-padding: 0 1 0 1;
    -fx-background-insets: 0;
}

.split-pane *.horizontal-grabber {
    -fx-padding: 0;
    -fx-background-color: transparent;
    -fx-background-insets: 0;
    -fx-shape: " ";
}

.split-pane {
    -fx-background-color: derive(white, 100%);
    -fx-divider-width: 0;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: white;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap: 0;
    -fx-padding: 0 0 0 0;
    -fx-background-color: derive(white, 100%);
}

.list-cell:filled:even {
    -fx-background-color: #eeeeee;
}

.list-cell:filled:odd {
    -fx-background-color: #d3d3d3;
}

.list-cell:filled:selected {
    -fx-background-color: #f2f3f4;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: #343434;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
    -fx-background-color: derive(white, 100%);
}

.pane-with-border {
    -fx-background-color: derive(white, 100%);
    -fx-border-color: derive(white, 10%);
    -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: #00bfff;
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: white;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: #343434;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #343434;
}

.status-bar-with-border {
    -fx-border-color: derive(#00bfff, 25%);
    -fx-background-color: #00bfff;
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: #343434;
}

.grid-pane {
    -fx-background-color: derive(white, 100%);
    -fx-border-color: derive(white, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(white, 100%);
}

.context-menu {
    -fx-background-color: derive(#00bfff, 50%);
}

.context-menu .label {
    -fx-text-fill: #343434;
}

.menu-bar {
    -fx-background-color: derive(white, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #343434;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: white;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 5 5 5;
    -fx-border-color: transparent;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #00bfff;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: white;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.importButton {
    -fx-padding: 5 5 5 5;
    -fx-border-color: white;
    -fx-border-width: 1;
    -fx-background-radius: 0;
    -fx-background-color: #00bfff;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 8pt;
    -fx-text-fill: white;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: #6495ed;
    -fx-text-fill: white;
}

.button:focused {
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: white;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: white;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: white;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: #343434;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(white, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: #343434;
}

.scroll-bar {
    -fx-background-color: derive(#f2f3f4, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(white, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}
```
###### \resources\view\EventListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>
<HBox xmlns:fx="http://javafx.com/fxml/1" id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150"/>
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15"/>
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE"/>
                    </minWidth>
                </Label>
                <Label fx:id="title" styleClass="cell_big_label" text="\$title"/>
            </HBox>
            <HBox>
                <Label fx:id="date" styleClass="cell_small_label" text="\$date"/>
                <Label fx:id="timing" text="\$timing" styleClass="cell_small_label"/>
            </HBox>
            <Label fx:id="description" styleClass="cell_small_label" text="\$description"/>
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\EventListPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8" VBox.vgrow="ALWAYS">
    <ListView fx:id="eventListView"/>
</VBox>
```
###### \resources\view\MainWindow.fxml
``` fxml

<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.control.Tab?>
<?import javafx.scene.control.TabPane?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@BrightTheme.css" />
        <URL value="@Extensions.css" />
        <URL value="@CalendarView.css" />
    </stylesheets>

<VBox xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@BrightTheme.css" />
        <URL value="@Extensions.css" />
    </stylesheets>

    <MenuBar fx:id="menuBar" VBox.vgrow="NEVER">
        <Menu mnemonicParsing="false" text="File">
            <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
        </Menu>
        <Menu mnemonicParsing="false" text="Help">
            <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
        </Menu>
    </MenuBar>

    <SplitPane dividerPositions="0.15, 0.85" prefHeight="50.0" prefWidth="200.0">
        <items>
            <StackPane alignment="CENTER">
                <padding>
                    <Insets bottom="20" left="20" right="10" top="10" />
                </padding>
                <AnchorPane fx:id="calendarButton" maxWidth="35" prefHeight="30">
                    <ImageView fitHeight="30.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true">
                        <Image url="/images/calendar.png" />
                    </ImageView>
                </AnchorPane>
            </StackPane>
            <StackPane fx:id="commandBoxPlaceholder" minHeight="60.0" styleClass="commandBox" VBox.vgrow="NEVER">
                <padding>
                    <Insets bottom="5" left="10" right="10" top="5" />
                </padding>
            </StackPane>
            <StackPane alignment="CENTER">
                <padding>
                    <Insets bottom="10" left="10" right="20" top="10" />
                </padding>
                <AnchorPane fx:id="notificationButton" maxWidth="35" prefHeight="30">
                    <ImageView fitHeight="30.0" fitWidth="30.0" pickOnBounds="true" preserveRatio="true">
                        <Image url="/images/notification.png" />
                    </ImageView>
                </AnchorPane>
            </StackPane>
        </items>
    </SplitPane>

```
