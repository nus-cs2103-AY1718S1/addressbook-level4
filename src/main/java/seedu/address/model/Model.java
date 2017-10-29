package seedu.address.model;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.module.BookedSlot;
import seedu.address.model.module.Code;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;
import seedu.address.model.module.exceptions.DuplicateBookedSlotException;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.DuplicateRemarkException;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.exceptions.RemarkNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyLesson> PREDICATE_SHOW_ALL_LESSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Remark> PREDICATE_SHOW_ALL_REMARKS = unused -> true;

    /**
     * Get a hash set of all the distinct locations
     */
    HashSet<Location> getUniqueLocationSet();

    /**
     * Get a hash set of all the distinct module codes
     */
    HashSet<Code> getUniqueCodeSet();

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * update the set of BookedList
     */
    void updateBookedSlotSet();

    /**
     * Deletes the given lesson.
     */
    void deleteLesson(ReadOnlyLesson target) throws LessonNotFoundException;

    /**
     * Deletes the given list of lessons.
     */
    void deleteLessonSet(List<ReadOnlyLesson> lessonList) throws LessonNotFoundException;

    /**
     * Adds the given lesson
     */
    void addLesson(ReadOnlyLesson lesson) throws DuplicateLessonException;

    /**
     * Bookmarks the given lesson into favourite list
     */
    void bookmarkLesson(ReadOnlyLesson lesson) throws DuplicateLessonException;

    /**
     * Unbookmarks the given lesson from favourite list
     */
    void unBookmarkLesson(ReadOnlyLesson lesson);

    /**
     * Booked a location with a given timeslot
     */
    void bookingSlot(BookedSlot booking) throws DuplicateBookedSlotException;

    /**
     * Unbook a slot at a location
     */
    void unbookBookedSlot(BookedSlot booking);

    /**
     * update a booked slot of a location
     */
    void updateBookedSlot(BookedSlot target, BookedSlot newBookingSlot) throws DuplicateBookedSlotException;

    /**
     * clear all booked slot of a location
     */
    void unbookAllSlot();

    /**
     * Replaces the given lesson {@code target} with {@code editedLesson}.
     *
     * @throws DuplicateLessonException if updating the lesson's details causes the lesson to be equivalent to
     *                                  another existing lesson in the list.
     * @throws LessonNotFoundException  if {@code target} could not be found in the list.
     */
    void updateLesson(ReadOnlyLesson target, ReadOnlyLesson editedLesson)
            throws DuplicateLessonException, LessonNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered lesson list
     */
    ObservableList<ReadOnlyLesson> getFilteredLessonList();

    /**
     * Updates the filter of the filtered lesson list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLessonList(Predicate<ReadOnlyLesson> predicate);

    /**
     * Updates the filter of the filtered remark list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRemarkList(Predicate<Remark> predicate);

    /**
     * Adds the given remark.
     * @throws DuplicateRemarkException
     */
    void addRemark(Remark r) throws DuplicateRemarkException;

    /**
     * Deletes the given remark.
     */
    void deleteRemark(Remark target) throws RemarkNotFoundException;

    /**
     * Update the given remark.
     * @throws DuplicateRemarkException
     * @throws RemarkNotFoundException
     */
    void updateRemark(Remark target, Remark editedRemark) throws DuplicateRemarkException, RemarkNotFoundException;

    /**
     * handle different ListingUnit after redo and undo
     */
    void handleListingUnit();

    /**
     * Sort the filtered lesson/module/location list regarding different listing unit.
     */
    void sortLessons();

    /** Set lesson that is viewing currently **/
    void setCurrentViewingLesson(ReadOnlyLesson lesson);

    /** Get the lesson  viewing currently **/
    ReadOnlyLesson getCurrentViewingLesson();

    /** Get the viewing panel attribute**/
    void setViewingPanelAttribute(String attribute);

    /** Get the current viewing panel attribute **/
    String getCurrentViewingAttribute();

    /** Returns an unmodifiable view of the list of remarks */
    ObservableList<Remark> getFilteredRemarkList();

}
