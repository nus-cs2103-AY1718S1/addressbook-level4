package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.sun.org.apache.regexp.internal.RE;
import javafx.collections.ObservableList;

import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.lecturer.UniqueLecturerList;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;
import seedu.address.model.module.UniqueLessonList;
import seedu.address.model.module.UniqueRemarkList;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.DuplicateRemarkException;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.exceptions.RemarkNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueLessonList lessons;
    private final UniqueLecturerList lecturers;
    private final UniqueRemarkList remarks;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        lessons = new UniqueLessonList();
        lecturers = new UniqueLecturerList();
        remarks = new UniqueRemarkList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setLessons(List<? extends ReadOnlyLesson> lessons) throws DuplicateLessonException {
        this.lessons.setLessons(lessons);
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers.setLectuers(lecturers);
    }

    public void setRemarks(Set<Remark> remarks) {
        this.remarks.setRemarks(remarks);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setLessons(newData.getLessonList());
        } catch (DuplicateLessonException e) {
            assert false : "AddressBooks should not have duplicate lessons";
        }

        setLecturers(new HashSet<>(newData.getLecturerList()));
        setRemarks(new HashSet<>(newData.getRemarkList()));
        syncMasterLecturerListWith(lessons);
    }

    //// lesson-level operations

    /**
     * Adds a lesson to the address book.
     * Also checks the new lesson's lecturers and Ls {@link #lecturers} with any new lecturers found,
     * and updates the lecturer objects in the lesson to point to those in {@link #lecturers}.
     *
     * @throws DuplicateLessonException if an equivalent lesson already exists.
     */
    public void addLesson(ReadOnlyLesson m) throws DuplicateLessonException {
        Lesson newLesson = new Lesson(m);
        try {
            lessons.add(newLesson);
        } catch (DuplicateLessonException e) {
            throw e;
        }

        syncMasterLecturerListWith(newLesson);
    }

    /**
     * Adds a lesson to the favourite list.
     * Only person exists in the Address Book can be added into the favourite list.
     *
     * @throws DuplicateLessonException if an equivalent lesson already exists.
     */
    public void bookmarkLesson(ReadOnlyLesson m) throws DuplicateLessonException {
        if (m.isMarked()) {
            throw new DuplicateLessonException();
        } else {
            m.setAsMarked();
        }
    }

    /**
     * Removes a lesson from the favourite list.
     * Only person exists in the favourite List can be unbookmarked from the favourite list.
     */
    public void unBookmarkLesson(ReadOnlyLesson m) {
        m.setAsUnmarked();

    }

    /**
     * Replaces the given lesson {@code target} in the list with {@code editedReadOnlyLesson}.
     * {@code AddressBook}'s lecturers list will be updated with the lecturers of {@code editedReadOnlyLesson}.
     *
     * @throws DuplicateLessonException if updating the lesson's details causes the lesson to be equivalent to
     *      another existing lesson in the list.
     * @throws LessonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterLecturerListWith(Lesson)
     */
    public void updateLesson(ReadOnlyLesson target, ReadOnlyLesson editedReadOnlyLesson)
            throws DuplicateLessonException, LessonNotFoundException {
        requireNonNull(editedReadOnlyLesson);

        Lesson editedLesson = new Lesson(editedReadOnlyLesson);

        try {
            lessons.setLesson(target, editedLesson);
        } catch (DuplicateLessonException e) {
            throw e;
        } catch (LessonNotFoundException e) {
            throw e;
        }

        syncMasterLecturerListWith(editedLesson);
    }

    /**
     * Ensures that every lecturer in this lesson:
     *  - exists in the master list {@link #lessons}
     *  - points to a lecturer object in the master list
     */
    private void syncMasterLecturerListWith(Lesson lesson) {
        final UniqueLecturerList lessonLecturers = new UniqueLecturerList(lesson.getLecturers());
        lecturers.mergeFrom(lessonLecturers);

        // Create map with values = tag object references in the master list
        // used for checking lesson lecturers references
        final Map<Lecturer, Lecturer> masterTagObjects = new HashMap<>();
        lecturers.forEach(lecturer -> masterTagObjects.put(lecturer, lecturer));

        // Rebuild the list of lesson lecturers to point to the relevant lecturers in the master lecturer list.
        final Set<Lecturer> correctTagReferences = new HashSet<>();
        lessonLecturers.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        lessonLecturers.setLectuers(correctTagReferences);
    }

    /**
     * Ensures that every lecturer in these lessons:
     *  - exists in the master list {@link #lessons}
     *  - points to a Lecturer object in the master list
     *  @see #syncMasterLecturerListWith(Lesson)
     */
    private void syncMasterLecturerListWith(UniqueLessonList lessons) {
        lessons.forEach(this::syncMasterLecturerListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws LessonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeLesson(ReadOnlyLesson key) throws LessonNotFoundException {
        if (lessons.remove(key)) {
            return true;
        } else {
            throw new LessonNotFoundException();
        }
    }

    //// lecturer-level operations

    public void addLecturer(Lecturer t) throws UniqueLecturerList.DuplicateLecturerException {
        lecturers.add(t);
    }

    //// remark-level operations
    public void addRemark(Remark r) throws DuplicateRemarkException {
        remarks.add(r);
    }

    public void updateRemark(Remark target, Remark editedRemark) throws
            DuplicateRemarkException, RemarkNotFoundException {
        remarks.setRemark(target, editedRemark);
    }

    /**
     * Sort the filtered lesson/module/location list regarding different listing unit.
     */
    public void sortLessons() {
        lessons.sortLessons();
    }

    //// util methods

    @Override
    public String toString() {
        return lessons.asObservableList().size() + " lessons, " + lecturers.asObservableList().size() +  " lecturers";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyLesson> getLessonList() {
        return lessons.asObservableList();
    }

    @Override
    public ObservableList<Lecturer> getLecturerList() {
        return lecturers.asObservableList();
    }

    @Override
    public ObservableList<Remark> getRemarkList() { return remarks.asObservableList(); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.lessons.equals(((AddressBook) other).lessons)
                && this.lecturers.equalsOrderInsensitive(((AddressBook) other).lecturers));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(lessons, lecturers);
    }
}
