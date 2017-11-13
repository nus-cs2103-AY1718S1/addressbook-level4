package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.util.PersonSortingUtil.generateComparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.RolodexChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.SortArgument;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the rolodex data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static final Set<Tag> KNOWN_TAGS = new HashSet<>();
    private static int lastRolodexSize = 0;

    private final Rolodex rolodex;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final SortedList<ReadOnlyPerson> sortedPersons;
    private Predicate<ReadOnlyPerson> predicate;

    /**
     * Initializes a ModelManager with the given rolodex and userPrefs.
     */
    public ModelManager(ReadOnlyRolodex rolodex, UserPrefs userPrefs) {
        super();
        requireAllNonNull(rolodex, userPrefs);

        logger.fine("Initializing with rolodex: " + rolodex + " and user prefs " + userPrefs);

        this.rolodex = new Rolodex(rolodex);
        filteredPersons = new FilteredList<>(this.rolodex.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);
        updateSortComparator(null);
        KNOWN_TAGS.addAll(this.rolodex.getTagList());
        setLastRolodexSize(this.rolodex.getPersonList().size());
    }

    public ModelManager() {
        this(new Rolodex(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyRolodex newData) {
        rolodex.resetData(newData);
        indicateRolodexChanged();
    }

    @Override
    public ReadOnlyRolodex getRolodex() {
        return rolodex;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateRolodexChanged() {
        setLastRolodexSize(rolodex.getPersonList().size());
        raise(new RolodexChangedEvent(rolodex));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        rolodex.removePerson(target);
        indicateRolodexChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        rolodex.addPerson(person);
        updateSortComparator(null);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        KNOWN_TAGS.addAll(person.getTags());
        indicateRolodexChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        rolodex.updatePerson(target, editedPerson);
        KNOWN_TAGS.addAll(editedPerson.getTags());
        indicateRolodexChanged();
    }

    @Override
    public void removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
        for (ReadOnlyPerson person: rolodex.getPersonList()) {
            Person newPerson = new Person(person);

            Set<Tag> newTags = new HashSet<>(newPerson.getTags());
            newTags.remove(tag);
            newPerson.setTags(newTags);

            rolodex.updatePerson(person, newPerson);
        }
    }

    /**
     * Returns true if a given String array as equivalent to any known tags in the Rolodex.
     */
    public static boolean hasAnyExistingTags(String[] tags) {
        return !Collections.disjoint(Arrays.stream(tags).map(s -> {
            try {
                return new Tag(s.replaceAll(PREFIX_TAG.toString(), ""));
            } catch (IllegalValueException e) {
                return false;
            }
        }).collect(Collectors.toList()), KNOWN_TAGS);
    }

    /**
     * Returns true if a given String array as equivalent to any known tags in the Rolodex.
     */
    public static boolean isKnownTag(String tag) {
        try {
            return KNOWN_TAGS.contains(new Tag(tag));
        } catch (IllegalValueException e) {
            return false;
        }
    }

    public static int getLastRolodexSize() {
        return lastRolodexSize;
    }

    private static void setLastRolodexSize(int size) {
        lastRolodexSize = size;
    }

    //=========== Latest Person List Accessors =============================================================

    /**
     * Returns the index of the given person.
     */
    public Index getIndex(ReadOnlyPerson target) {
        return Index.fromZeroBased(sortedPersons.indexOf(target));
    }

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code rolodex}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getLatestPersonList() {
        return FXCollections.unmodifiableObservableList(sortedPersons.filtered(predicate));
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        this.predicate = predicate;
    }

    @Override
    public void updateSortComparator(List<SortArgument> sortArguments) {
        sortedPersons.setComparator(generateComparator(sortArguments));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return rolodex.equals(other.rolodex)
                && sortedPersons.equals(other.sortedPersons)
                && filteredPersons.equals(other.filteredPersons);
    }

}
