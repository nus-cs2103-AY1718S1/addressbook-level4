package seedu.address.model.module;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fxmisc.easybind.EasyBind;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueModuleList implements Iterable<Module>{
    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyModule> mappedList = EasyBind.map(internalList, (module) -> module);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyModule toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(ReadOnlyModule toAdd) throws DuplicateLessonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLessonException();
        }
        internalList.add(new Module(toAdd));
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicateLessonException if the replacement is equivalent to another existing person in the list.
     * @throws LessonNotFoundException if {@code target} could not be found in the list.
     */
    public void setLesson(ReadOnlyLesson target, ReadOnlyLesson editedLesson)
            throws DuplicateLessonException, LessonNotFoundException {
        requireNonNull(editedLesson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LessonNotFoundException();
        }

        if (!target.equals(editedLesson) && internalList.contains(editedLesson)) {
            throw new DuplicateLessonException();
        }

        internalList.set(index, new Lesson(editedLesson));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyLesson toRemove) throws LessonNotFoundException {
        requireNonNull(toRemove);
        final boolean lessonFoundAndDeleted = internalList.remove(toRemove);
        if (!lessonFoundAndDeleted) {
            throw new LessonNotFoundException();
        }
        return lessonFoundAndDeleted;
    }

    public void setLessons(UniqueLessonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setLessons(List<? extends ReadOnlyLesson> lessons) throws DuplicateLessonException {
        final UniqueLessonList replacement = new UniqueLessonList();
        for (final ReadOnlyLesson lesson : lessons) {
            replacement.add(new Lesson(lesson));
        }
        setLessons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyLesson> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Lesson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLessonList // instanceof handles nulls
                && this.internalList.equals(((UniqueLessonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
