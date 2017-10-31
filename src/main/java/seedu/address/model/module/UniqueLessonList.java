package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.LessonNotFoundException;

/**
 * A list of lessons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Lesson #equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueLessonList implements Iterable<Lesson> {

    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyLesson> mappedList = EasyBind.map(internalList, (lesson) -> lesson);

    /**
     * Returns true if the list contains an equivalent lesson as the given argument.
     */
    public boolean contains(ReadOnlyLesson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a lesson to the list.
     *
     * @throws DuplicateLessonException if the lesson to add is a duplicate of an existing lesson in the list.
     */
    public void add(ReadOnlyLesson toAdd) throws DuplicateLessonException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLessonException();
        }
        internalList.add(new Lesson(toAdd));
    }

    /**
     * Replaces the lesson {@code target} in the list with {@code editedLesson}.
     *
     * @throws DuplicateLessonException if the replacement is equivalent to another existing lesson in the list.
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
     * Removes the equivalent lesson from the list.
     *
     * @throws LessonNotFoundException if no such lesson could be found in the list.
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

    //@@author angtianlannus
    /**
     * This method will sort the lessons
     */
    public void sortLessons() {
        switch (ListingUnit.getCurrentListingUnit()) {
        case LESSON:
            FXCollections.sort(internalList, new Comparator<ReadOnlyLesson>() {
                @Override
                public int compare(ReadOnlyLesson firstLesson, ReadOnlyLesson secondLesson) {
                    return firstLesson.getGroup().value.compareToIgnoreCase(secondLesson.getGroup().value);
                }
            });
            break;

        case LOCATION:
            FXCollections.sort(internalList, new Comparator<ReadOnlyLesson>() {
                @Override
                public int compare(ReadOnlyLesson firstLesson, ReadOnlyLesson secondLesson) {
                    return firstLesson.getLocation().value.compareToIgnoreCase(secondLesson.getLocation().value);
                }
            });
            break;

        default:
            FXCollections.sort(internalList, new Comparator<ReadOnlyLesson>() {
                @Override
                public int compare(ReadOnlyLesson firstLesson, ReadOnlyLesson secondLesson) {
                    return firstLesson.getCode().fullCodeName.compareToIgnoreCase(secondLesson.getCode().fullCodeName);
                }
            });
            break;
        }
    }
    //@@author

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
