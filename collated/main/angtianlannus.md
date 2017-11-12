# angtianlannus
###### /java/seedu/address/logic/commands/FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        Predicate<ReadOnlyLesson> predicate;

        switch (ListingUnit.getCurrentListingUnit()) {
        case LOCATION:
            predicate = new LocationContainsKeywordsPredicate(keywords);
            ListingUnit.setCurrentPredicate(new LocationContainsKeywordsPredicate(keywords));
            break;
        case LESSON:
            if (model.getCurrentViewingAttribute().equals("marked")) {
                predicate = new MarkedLessonContainsKeywordsPredicate(keywords);
                ListingUnit.setCurrentPredicate(new MarkedLessonContainsKeywordsPredicate(keywords));
                EventsCenter.getInstance().post(new ViewedLessonEvent());
                break;
            }
            predicate = new LessonContainsKeywordsPredicate(keywords, model.getCurrentViewingLesson(),
                    model.getCurrentViewingAttribute());
            ListingUnit.setCurrentPredicate(
                    new LessonContainsKeywordsPredicate(keywords, model.getCurrentViewingLesson(),
                    model.getCurrentViewingAttribute()));
            EventsCenter.getInstance().post(new ViewedLessonEvent());
            break;
        default:
            predicate = new ModuleContainsKeywordsPredicate(keywords);
            ListingUnit.setCurrentPredicate(new ModuleContainsKeywordsPredicate(keywords));
            break;
        }
        model.updateFilteredLessonList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SORT_LESSON_SUCCESS = "List sorted successfully";

    @Override
    public CommandResult execute() throws CommandException {
        model.sortLessons();
        return new CommandResult(MESSAGE_SORT_LESSON_SUCCESS);
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommand();
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Sort the filtered lesson/module/location list regarding different listing unit.
     */
    public void sortLessons() {
        lessons.sortLessons();
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * update the set of BookedList
     */
    void updateBookedSlotSet();

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
```
###### /java/seedu/address/model/Model.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/module/BookedSlot.java
``` java
/**
 * Represents a Booked time slot of a venue
 */
public class BookedSlot {

    public final Location location;
    public final TimeSlot timeSlot;

    public BookedSlot(Location newLocation, TimeSlot newTimeSlot) {
        this.location = newLocation;
        this.timeSlot = newTimeSlot;
    }

    public Location getLocation() {
        return location;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Returns true if both booked slot have the same location and time slot(case insensitive)
     */
    public boolean isSameStateAs(BookedSlot other) {
        return this.location.value.equalsIgnoreCase(other.getLocation().value)
                && this.timeSlot.value.equalsIgnoreCase(other.getTimeSlot().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookedSlot // instanceof handles nulls
                && this.isSameStateAs((BookedSlot) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(location, timeSlot);
    }

    @Override
    public String toString() {
        return location.toString() + "  " + timeSlot.toString();
    }
}
```
###### /java/seedu/address/model/module/exceptions/DuplicateBookedSlotException.java
``` java
/**
 * Signals that the operation will result in duplicate BookedSlot objects.
 */
public class DuplicateBookedSlotException extends DuplicateDataException {

    public DuplicateBookedSlotException() {
        super("Operation would result in duplicate BookedSlot");
    }

}

```
###### /java/seedu/address/model/module/predicates/ContainsKeywordsPredicate.java
``` java

/**
 * tests if the given lesson contains the keyword.
 */
public interface ContainsKeywordsPredicate extends Predicate<ReadOnlyLesson> {

    public List<String> getKeywords();

}
```
###### /java/seedu/address/model/module/predicates/LessonContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class LessonContainsKeywordsPredicate implements ContainsKeywordsPredicate {
    private final List<String> keywords;
    private ArrayList<ReadOnlyLesson> duplicateLessons = new ArrayList<>();
    private ReadOnlyLesson lesson;
    private String attribute;

    public LessonContainsKeywordsPredicate(List<String> keywords, ReadOnlyLesson targetLesson, String attribute) {

        this.keywords = keywords;
        this.lesson = targetLesson;
        this.attribute = attribute;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        int attribute = -1; // 1 is for module, 2 is for location

        if (lesson.getCode().fullCodeName.equals(this.lesson.getCode().fullCodeName)
                && this.attribute.equals("module")) {
            attribute = 1;
        } else if (lesson.getLocation().value.equals(this.lesson.getLocation().value)
                && this.attribute.equals("location")) {
            attribute = 2;
        } else {

            return false; // not this lesson
        }

        for (int i = 0; i < keywords.size(); i++) {

            if (attribute == 1) { //search for attribute module code
                if (lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())) {

                    if (duplicateLessons.contains(lesson)) {

                        return false;
                    } else {

                        duplicateLessons.add(lesson);
                        return true;
                    }

                }
            } else if (attribute == 2) { //search for attribute location

                if (lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())) {

                    if (duplicateLessons.contains(lesson)) {
                        return false;
                    } else {
                        duplicateLessons.add(lesson);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((LessonContainsKeywordsPredicate) other).keywords)); // state check
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public ReadOnlyLesson getTargetLesson() {
        return lesson;
    }

    public String getAttribute() {
        return attribute;
    }

}
```
###### /java/seedu/address/model/module/predicates/LocationContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class LocationContainsKeywordsPredicate implements ContainsKeywordsPredicate {
    private final List<String> keywords;
    private ArrayList<String> duplicateLocation = new ArrayList<String>();

    public LocationContainsKeywordsPredicate(List<String> keywords) {

        this.keywords = keywords;

    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        for (int i = 0; i < keywords.size(); i++) {
            if (lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase())) {

                if (duplicateLocation.contains(lesson.getLocation().value)) {
                    return false;
                } else {
                    duplicateLocation.add(lesson.getLocation().value);
                    return true;
                }

            }
        }
        return false;

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((LocationContainsKeywordsPredicate) other).keywords)); // state check
    }
    public List<String> getKeywords() {
        return keywords;
    }

}
```
###### /java/seedu/address/model/module/predicates/MarkedLessonContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */

public class MarkedLessonContainsKeywordsPredicate implements ContainsKeywordsPredicate {
    private final List<String> keywords;
    private ArrayList<ReadOnlyLesson> duplicateLessons = new ArrayList<>();

    public MarkedLessonContainsKeywordsPredicate(List<String> keywords) {

        this.keywords = keywords;

    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        if (!lesson.isMarked()) {
            return false;
        }

        for (int i = 0; i < keywords.size(); i++) {

            if (lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase())) {

                if (duplicateLessons.contains(lesson)) {
                    return false;
                } else {
                    duplicateLessons.add(lesson);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkedLessonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MarkedLessonContainsKeywordsPredicate) other).keywords)); // state check
    }

    public List<String> getKeywords() {
        return keywords;
    }


}
```
###### /java/seedu/address/model/module/predicates/ModuleContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class ModuleContainsKeywordsPredicate implements ContainsKeywordsPredicate {

    private final List<String> keywords;
    private ArrayList<String> duplicateCodes = new ArrayList<String>();

    public ModuleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        for (int i = 0; i < keywords.size(); i++) {
            if (lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase())) {
                if (duplicateCodes.contains(lesson.getCode().fullCodeName)) {
                    return false;
                } else {
                    duplicateCodes.add(lesson.getCode().fullCodeName);
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ModuleContainsKeywordsPredicate) other).keywords)); // state check
    }

    public List<String> getKeywords() {
        return keywords;
    }

}
```
###### /java/seedu/address/model/module/UniqueLessonList.java
``` java
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
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook implements ReadOnlyAddressBook {


    @XmlElement
    private List<XmlAdaptedLesson> lessons;
    @XmlElement
    private List<XmlAdaptedLecturer> lecturers;
    @XmlElement
    private List<XmlAdaptedRemark> remarks;


    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        lessons = new ArrayList<>();
        lecturers = new ArrayList<>();
        remarks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();

        lessons.addAll(src.getLessonList().stream().map(XmlAdaptedLesson::new).collect(Collectors.toList()));
        lecturers.addAll(src.getLecturerList().stream().map(XmlAdaptedLecturer::new).collect(Collectors.toList()));
        remarks.addAll(src.getRemarkList().stream().map(XmlAdaptedRemark::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyLesson> getLessonList() {
        final ObservableList<ReadOnlyLesson> lessons = this.lessons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(lessons);
    }

    @Override
    public ObservableList<Lecturer> getLecturerList() {
        final ObservableList<Lecturer> lecturers = this.lecturers.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(lecturers);
    }

```
