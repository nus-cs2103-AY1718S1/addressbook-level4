package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.ListingUnit.LOCATION;
import static seedu.address.model.ListingUnit.MODULE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.RefreshPanelEvent;
import seedu.address.model.module.BookedSlot;
import seedu.address.model.module.Code;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;
import seedu.address.model.module.exceptions.DuplicateBookedSlotException;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.DuplicateRemarkException;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.exceptions.NotRemarkedLessonException;
import seedu.address.model.module.exceptions.RemarkNotFoundException;
import seedu.address.model.module.predicates.LessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.LocationContainsKeywordsPredicate;
import seedu.address.model.module.predicates.MarkedLessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.ModuleContainsKeywordsPredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

//@@author caoliangnus
/**
 * Represents the in-memory model of the ModU data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyLesson> filteredLessons;
    private final FilteredList<Remark> filteredRemarks;
    private final ArrayList<BookedSlot> bookedList;
    private ReadOnlyLesson currentViewingLesson;
    private String currentViewingAttribute;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with ModU: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredLessons = new FilteredList<>(this.addressBook.getLessonList());
        filteredRemarks = new FilteredList<Remark>(this.addressBook.getRemarkList());
        filteredRemarks.setPredicate(PREDICATE_SHOW_ALL_REMARKS);
        Predicate predicate = new UniqueModuleCodePredicate(getUniqueCodeSet());
        ListingUnit.setCurrentPredicate(predicate);
        bookedList = new ArrayList<BookedSlot>();
        initializeBookedSlot();
        currentViewingAttribute = "default";
    }
    //@@author

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //@@author junming403
    @Override
    public HashSet<Location> getUniqueLocationSet() {
        HashSet<Location> set = new HashSet<>();

        ObservableList<ReadOnlyLesson> lessonLst = getFilteredLessonList();
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        for (ReadOnlyLesson l : lessonLst) {
            if (!set.contains(l.getLocation())) {
                set.add(l.getLocation());
            }
        }
        return set;
    }

    @Override
    public HashSet<Code> getUniqueCodeSet() {
        HashSet<Code> set = new HashSet<>();

        ObservableList<ReadOnlyLesson> lessonLst = getFilteredLessonList();
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        for (ReadOnlyLesson l : lessonLst) {
            if (!set.contains(l.getCode())) {
                set.add(l.getCode());
            }
        }
        return set;
    }
    //@@author

    //@@author caoliangnus
    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deleteLesson(ReadOnlyLesson target) throws LessonNotFoundException {
        addressBook.removeLesson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteLessonSet(List<ReadOnlyLesson> lessonList) throws LessonNotFoundException {

        for (ReadOnlyLesson lesson : lessonList) {
            addressBook.removeLesson(lesson);
        }
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addLesson(ReadOnlyLesson lesson) throws DuplicateLessonException {
        addressBook.addLesson(lesson);
        indicateAddressBookChanged();
    }
    //@@author

    //@@author junming403
    @Override
    public void bookmarkLesson(ReadOnlyLesson target) throws DuplicateLessonException {
        addressBook.bookmarkLesson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void unBookmarkLesson(ReadOnlyLesson target) throws NotRemarkedLessonException {
        addressBook.unBookmarkLesson(target);
        indicateAddressBookChanged();
    }
    //@@author

    //@@author angtianlannus
    /**
     * This method initialize the booked slot
     */
    public void initializeBookedSlot() {
        filteredLessons.setPredicate(PREDICATE_SHOW_ALL_LESSONS);

        for (int i = 0; i < filteredLessons.size(); i++) {
            bookedList.add(new BookedSlot(filteredLessons.get(i).getLocation(), filteredLessons.get(i).getTimeSlot()));
        }

        filteredLessons.setPredicate(new UniqueModuleCodePredicate(getUniqueCodeSet()));

    }

    @Override
    public void unbookBookedSlot(BookedSlot target) {
        if (bookedList.contains(target)) {
            bookedList.remove(target);
        }

    }

    @Override
    public void bookingSlot(BookedSlot target) throws DuplicateBookedSlotException {

        for (int i = 0; i < bookedList.size(); i++) {
            if (bookedList.get(i).equals(target)) {
                throw new DuplicateBookedSlotException();
            }
        }
        bookedList.add(target);
    }

    @Override
    public void updateBookedSlot(BookedSlot target, BookedSlot toReplace) throws DuplicateBookedSlotException {
        if (target.equals(toReplace) || !bookedList.contains(toReplace)) {
            bookedList.remove(target);
            bookedList.add(toReplace);
        } else {
            throw new DuplicateBookedSlotException();
        }
    }

    @Override
    public void updateBookedSlotSet() {
        bookedList.clear();
        for (ReadOnlyLesson lesson : addressBook.getLessonList()) {
            BookedSlot slot = new BookedSlot(lesson.getLocation(), lesson.getTimeSlot());
            bookedList.add(slot);
        }
    }

    @Override
    public void unbookAllSlot() {
        bookedList.clear();
    }
    //@@author

    //@@author caoliangnus
    @Override
    public void updateLesson(ReadOnlyLesson target, ReadOnlyLesson editedLesson)
            throws DuplicateLessonException, LessonNotFoundException {
        requireAllNonNull(target, editedLesson);
        addressBook.updateLesson(target, editedLesson);
        indicateAddressBookChanged();
    }
    //@@author

    //@@author angtianlannus
    @Override
    public void sortLessons() {
        addressBook.sortLessons();
    }

    @Override
    public void setCurrentViewingLesson(ReadOnlyLesson lesson) {
        this.currentViewingLesson = lesson;
    }

    @Override
    public ReadOnlyLesson getCurrentViewingLesson() {
        return this.currentViewingLesson;
    }

    @Override
    public void setViewingPanelAttribute(String attribute) {
        this.currentViewingAttribute = attribute;
    }

    @Override
    public String getCurrentViewingAttribute() {
        return this.currentViewingAttribute;
    }
    //@@author


    //=========== Filtered Module List Accessors =============================================================

    //@@author caoliangnus
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyLesson}
     */
    @Override
    public ObservableList<ReadOnlyLesson> getFilteredLessonList() {
        return FXCollections.unmodifiableObservableList(filteredLessons);
    }

    @Override
    public void updateFilteredLessonList(Predicate<ReadOnlyLesson> predicate) {
        requireNonNull(predicate);
        filteredLessons.setPredicate(predicate);
    }
    //@@author

    //@@author junming403
    @Override
    public void updateFilteredRemarkList(Predicate<Remark> predicate) {
        requireNonNull(predicate);
        filteredRemarks.setPredicate(predicate);
    }

    @Override
    public void addRemark(Remark r) throws DuplicateRemarkException {
        addressBook.addRemark(r);
        indicateAddressBookChanged();
    }

    @Override
    public void updateRemark(Remark target, Remark editedRemark)
            throws DuplicateRemarkException, RemarkNotFoundException {
        addressBook.updateRemark(target, editedRemark);
        indicateAddressBookChanged();
    }

    @Override
    public ObservableList<Remark> getFilteredRemarkList() {
        return FXCollections.unmodifiableObservableList(filteredRemarks);
    }

    @Override
    public synchronized void deleteRemark(Remark target) throws RemarkNotFoundException {
        addressBook.removeRemark(target);
        indicateAddressBookChanged();
    }

    @Override
    public void updateLocationList() {
        if (ListingUnit.getCurrentPredicate() instanceof LocationContainsKeywordsPredicate) {
            updateFilteredLessonList(
                    new LocationContainsKeywordsPredicate(((LocationContainsKeywordsPredicate)
                            ListingUnit.getCurrentPredicate()).getKeywords()));
        } else {
            updateFilteredLessonList(new UniqueLocationPredicate(getUniqueLocationSet()));
        }
    }


    @Override
    public void updateModuleList() {
        if (ListingUnit.getCurrentPredicate() instanceof ModuleContainsKeywordsPredicate) {
            updateFilteredLessonList(
                    new ModuleContainsKeywordsPredicate(((ModuleContainsKeywordsPredicate)
                            ListingUnit.getCurrentPredicate()).getKeywords()));
        } else {
            updateFilteredLessonList(new UniqueModuleCodePredicate(getUniqueCodeSet()));
        }
    }

    //@@author

    //@@author caoliangnus
    @Override
    public void handleListingUnit() {
        ListingUnit unit = ListingUnit.getCurrentListingUnit();

        if (unit.equals(LOCATION)) {
            if (ListingUnit.getCurrentPredicate() instanceof LocationContainsKeywordsPredicate) {
                updateFilteredLessonList(
                        new LocationContainsKeywordsPredicate(((LocationContainsKeywordsPredicate)
                                ListingUnit.getCurrentPredicate()).getKeywords()));
            } else {
                UniqueLocationPredicate predicate = new UniqueLocationPredicate(getUniqueLocationSet());
                updateFilteredLessonList(predicate);
            }
        } else if (unit.equals(MODULE)) {
            if (ListingUnit.getCurrentPredicate() instanceof ModuleContainsKeywordsPredicate) {
                updateFilteredLessonList(
                        new ModuleContainsKeywordsPredicate(((ModuleContainsKeywordsPredicate)
                                ListingUnit.getCurrentPredicate()).getKeywords()));
            } else {
                UniqueModuleCodePredicate predicate = new UniqueModuleCodePredicate(getUniqueCodeSet());
                updateFilteredLessonList(predicate);
            }
        } else if (ListingUnit.getCurrentPredicate() instanceof MarkedLessonContainsKeywordsPredicate) {
            updateFilteredLessonList(
                    new MarkedLessonContainsKeywordsPredicate(((MarkedLessonContainsKeywordsPredicate)
                            ListingUnit.getCurrentPredicate()).getKeywords()));
        } else if (ListingUnit.getCurrentPredicate() instanceof LessonContainsKeywordsPredicate) {
            LessonContainsKeywordsPredicate predicate =
                    (LessonContainsKeywordsPredicate) ListingUnit.getCurrentPredicate();
            updateFilteredLessonList(new LessonContainsKeywordsPredicate(predicate.getKeywords(),
                    predicate.getTargetLesson(), predicate.getAttribute()));
        } else {
            updateFilteredLessonList(ListingUnit.getCurrentPredicate());

            if (getFilteredLessonList().isEmpty()) {
                UniqueModuleCodePredicate predicate = new UniqueModuleCodePredicate(getUniqueCodeSet());
                updateFilteredLessonList(predicate);
                ListingUnit.setCurrentPredicate(predicate);
                ListingUnit.setCurrentListingUnit(MODULE);
                EventsCenter.getInstance().post(new RefreshPanelEvent());
            }
        }
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
        return addressBook.equals(other.addressBook)
                && filteredLessons.equals(other.filteredLessons)
                && filteredRemarks.equals(other.filteredRemarks);
    }


}
