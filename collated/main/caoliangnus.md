# caoliangnus
###### /java/seedu/address/commons/events/ui/ColorKeywordEvent.java
``` java
/**
 * Indicates a request to enable highlighting the command keywords in the application
 */
public class ColorKeywordEvent extends BaseEvent {

    public final boolean isEnabled;

    public ColorKeywordEvent(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/ColorKeywordCommand.java
``` java
/**
 * Color the command keywords in the application
 */
public class ColorKeywordCommand extends Command {

    public static final String COMMAND_WORD = "color";
    public static final String MESSAGE_SUCCESS = " highlighting of keyword.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Highlighting the command keywords "
            + "Parameters: enable/disable\n"
            + "Example: " + COMMAND_WORD + " enable";

    public static final String DISABLE_COLOR = "Disable";
    public static final String ENABLE_COLOR = "Enable";

    private final boolean isEnableColor;

    public ColorKeywordCommand(String attributeName) {
        if (attributeName.equalsIgnoreCase("disable")) {
            isEnableColor = false;
        } else {
            isEnableColor = true;
        }
    }

    @Override
    public CommandResult execute() {
        if (isEnableColor) {
            EventsCenter.getInstance().post(new ColorKeywordEvent(true));
            return new CommandResult(ENABLE_COLOR + MESSAGE_SUCCESS);
        }

        EventsCenter.getInstance().post(new ColorKeywordEvent(false));
        return new CommandResult(DISABLE_COLOR + MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ColorKeywordCommand // instanceof handles nulls
                && this.isEnableColor == (((ColorKeywordCommand) other).isEnableColor)); // state check
    }
}
```
###### /java/seedu/address/logic/parser/ColorKeywordCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ColorKeywordCommand object
 */
public class ColorKeywordCommandParser implements Parser<ColorKeywordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ColorKeywordCommand
     * and returns an ColorKeywordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ColorKeywordCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (isValidAttribute(trimmedArgs)) {
            return new ColorKeywordCommand(trimmedArgs);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColorKeywordCommand.MESSAGE_USAGE));
        }

    }

    private boolean isValidAttribute(String args) {
        return args.equalsIgnoreCase(ColorKeywordCommand.DISABLE_COLOR)
                || args.equalsIgnoreCase(ColorKeywordCommand.ENABLE_COLOR);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> code} into an {@code Optional<Code>} if {@code code} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Code> parseCode(Optional<String> code) throws IllegalValueException {
        requireNonNull(code);
        return code.isPresent() ? Optional.of(new Code(code.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> location} into an {@code Optional<Location>} if {@code location} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Location> parseLocation(Optional<String> location) throws IllegalValueException {
        requireNonNull(location);
        return location.isPresent() ? Optional.of(new Location(location.get())) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> group} into an {@code Optional<Group>} if {@code group} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Group> parseGroup(Optional<String> group) throws IllegalValueException {
        requireNonNull(group);
        return group.isPresent() ? Optional.of(new Group(group.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> timeSlot} into an {@code Optional<TimeSlot>} if {@code timeSlot} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TimeSlot> parseTimeSlot(Optional<String> timeSlot) throws IllegalValueException {
        requireNonNull(timeSlot);
        return timeSlot.isPresent() ? Optional.of(new TimeSlot(timeSlot.get())) : Optional.empty();
    }


    /**
     * Parses a {@code Optional<String> classType} into an {@code Optional<ClassType>} if {@code classType} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ClassType> parseClassType(Optional<String> classType) throws IllegalValueException {
        requireNonNull(classType);
        return classType.isPresent() ? Optional.of(new ClassType(classType.get())) : Optional.empty();
    }


    /**
     * Parses {@code Collection<String> lecturer} into a {@code Set<Lecturer>}.
     */
    public static Set<Lecturer> parseLecturer(Collection<String> lecturers) throws IllegalValueException {
        requireNonNull(lecturers);
        final Set<Lecturer> lecturerSet = new HashSet<>();
        for (String lecturerName : lecturers) {
            lecturerSet.add(new Lecturer(lecturerName));
        }
        return lecturerSet;
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
/**
 * The class is Addressbook, but this class wraps all data at module level
 * A module contains lessons, lecturers and remarks
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
     * Creates an AddressBook in the {@code toBeCopied}
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

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setLessons(newData.getLessonList());
            setLecturers(new HashSet<>(newData.getLecturerList()));
            setRemarks(new HashSet<>(newData.getRemarkList()));
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
     * Also checks the new lesson's lecturers {@link #lecturers} with any new lecturers found,
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

```
###### /java/seedu/address/model/AddressBook.java
``` java
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

```
###### /java/seedu/address/model/AddressBook.java
``` java
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

```
###### /java/seedu/address/model/AddressBook.java
``` java
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
```
###### /java/seedu/address/model/Model.java
``` java
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

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

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
    void unBookmarkLesson(ReadOnlyLesson lesson) throws NotRemarkedLessonException;

```
###### /java/seedu/address/model/Model.java
``` java
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

```
###### /java/seedu/address/model/Model.java
``` java
    /** Returns an unmodifiable view of the list of remarks */
    ObservableList<Remark> getFilteredRemarkList();

    /**
     * Updates location list according to previous predicate.
     */
    void updateLocationList();

    /**
     * Updates module code list according to previous predicate.
     */
    void updateModuleList();



}
```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateLesson(ReadOnlyLesson target, ReadOnlyLesson editedLesson)
            throws DuplicateLessonException, LessonNotFoundException {
        requireAllNonNull(target, editedLesson);
        addressBook.updateLesson(target, editedLesson);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/module/ClassType.java
``` java
/**
 * Represents a Lesson's class type in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidClassType(String)}
 */
public class ClassType {
    public static final String MESSAGE_CLASSTYPE_CONSTRAINTS =
            "Class type can only be 'lec' or 'tut' ";
    public static final String CLASSTYPE_VALIDATION_REGEX = "[a-zA-Z]{3}";
    public final String value;

    /**
     * Validates given class type.
     *
     * @throws IllegalValueException if given class type string is invalid.
     */
    public ClassType(String classType) throws IllegalValueException {
        requireNonNull(classType);
        String trimmedClassType = classType.trim();
        if (!isValidClassType(trimmedClassType)) {
            throw new IllegalValueException(MESSAGE_CLASSTYPE_CONSTRAINTS);
        }
        this.value = trimmedClassType;
    }

    /**
     * Returns true if a given string is a valid lesson class type.
     */
    public static boolean isValidClassType(String test) {
        return test.matches(CLASSTYPE_VALIDATION_REGEX) && containKeyword(test);
    }

    private static boolean containKeyword(String test) {
        return test.equalsIgnoreCase("lec") || test.equalsIgnoreCase("tut");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassType // instanceof handles nulls
                && this.value.equals(((ClassType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/module/Code.java
``` java
/**
 * Represents a Module code in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Code {
    public static final String MESSAGE_CODE_CONSTRAINTS =
            "Module code should start with letters and followed by alphanumeric characters, and it should not be blank";

    /*
     * The valid code should be 2 or 3 letters, 4 digits and followed by 1 or 0 letter.
     */
    public static final String CODE_VALIDATION_REGEX = "[a-zA-Z]{2,3}\\d{4}[a-zA-Z]?";

    public final String fullCodeName;

    /**
     * Validates given module code.
     *
     * @throws IllegalValueException if given code string is invalid.
     */
    public Code(String code) throws IllegalValueException {
        requireNonNull(code);
        String trimmedCode = code.trim().toUpperCase();
        if (!isValidCode(trimmedCode)) {
            throw new IllegalValueException(MESSAGE_CODE_CONSTRAINTS);
        }
        this.fullCodeName = trimmedCode;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidCode(String test) {
        return test.matches(CODE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullCodeName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Code // instanceof handles nulls
                && this.fullCodeName.equals(((Code) other).fullCodeName)); // state check
    }

    @Override
    public int hashCode() {
        return fullCodeName.hashCode();
    }

}
```
###### /java/seedu/address/model/module/Group.java
``` java
/**
 * Represents a Lesson's group number in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroup(String)}
 */
public class Group {

    public static final String MESSAGE_GROUP_CONSTRAINTS =
            "Group numbers can only contain numbers, and should be at least 1 digits long";
    public static final String GROUP_VALIDATION_REGEX = "[\\d]+";
    public final String value;

    /**
     * Validates given group number.
     *
     * @throws IllegalValueException if given group string is invalid.
     */
    public Group(String group) throws IllegalValueException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!isValidGroup(trimmedGroup)) {
            throw new IllegalValueException(MESSAGE_GROUP_CONSTRAINTS);
        }
        this.value = trimmedGroup;
    }

    /**
     * Returns true if a given string is a valid lesson group number.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Group // instanceof handles nulls
                && this.value.equals(((Group) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/module/Lesson.java
``` java
/**
 * Represents a Lesson in the application.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Lesson implements ReadOnlyLesson {
    private ObjectProperty<ClassType> classType;
    private ObjectProperty<Group> group;
    private ObjectProperty<Location> location;
    private ObjectProperty<TimeSlot> timeSlot;
    private ObjectProperty<Code> code;
    private ObjectProperty<Boolean> isMarked;

    private ObjectProperty<UniqueLecturerList> lecturers;

    /**
     * Every field must be present and not null.
     */
    public Lesson(ClassType classType, Location location, Group group, TimeSlot timeSlot, Code code,
                  Set<Lecturer> lecturers) {
        requireAllNonNull(classType, location, group, timeSlot, lecturers);
        this.classType = new SimpleObjectProperty<>(classType);
        this.location = new SimpleObjectProperty<>(location);
        this.group = new SimpleObjectProperty<>(group);
        this.code = new SimpleObjectProperty<>(code);
        this.timeSlot = new SimpleObjectProperty<>(timeSlot);
        this.isMarked = new SimpleObjectProperty<>(false);
        // protect internal lecturers from changes in the arg list
        this.lecturers = new SimpleObjectProperty<>(new UniqueLecturerList(lecturers));
    }

    /**
     * Creates a copy of the given ReadOnlyLesson.
     */
    public Lesson(ReadOnlyLesson source) {
        this(source.getClassType(), source.getLocation(), source.getGroup(), source.getTimeSlot(),
                source.getCode(), source.getLecturers());
        if (source.isMarked()) {
            isMarked = new SimpleObjectProperty<>(true);
        }
    }

    public void setLocation(Location location) {
        this.location.set(requireNonNull(location));
    }

    @Override
    public ObjectProperty<Location> locationProperty() {
        return location;
    }

    @Override
    public Location getLocation() {
        return location.get();
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot.set(requireNonNull(timeSlot));
    }

    @Override
    public ObjectProperty<TimeSlot> timeSlotProperty() {
        return timeSlot;
    }

    @Override
    public TimeSlot getTimeSlot() {
        return timeSlot.get();
    }

    public void setClassType(ClassType classType) {
        this.classType.set(requireNonNull(classType));
    }

    @Override
    public ObjectProperty<ClassType> classTypeProperty() {
        return classType;
    }

    @Override
    public ClassType getClassType() {
        return classType.get();
    }

    public void setGroupType(Group group) {
        this.group.set(requireNonNull(group));
    }

    @Override
    public ObjectProperty<Group> groupProperty() {
        return group;
    }

    @Override
    public Group getGroup() {
        return group.get();
    }

    public void setCodeType(Code code) {
        this.code.set(requireNonNull(code));
    }

    @Override
    public ObjectProperty<Code> codeProperty() {
        return code;
    }

    @Override
    public Code getCode() {
        return code.get();
    }

    @Override
    public ObjectProperty<Boolean> isMarkedProperty() {
        return isMarked;
    }

    @Override
    public boolean isMarked() {
        return isMarked.get();
    }

    @Override
    public void setAsMarked() {
        isMarked = new SimpleObjectProperty<>(true);
    }

    @Override
    public void setAsUnmarked() {
        isMarked = new SimpleObjectProperty<>(false);
    }

    @Override
    public Set<Lecturer> getLecturers() {
        return Collections.unmodifiableSet(lecturers.get().toSet());
    }

    @Override
    public ObjectProperty<UniqueLecturerList> lecturersProperty() {
        return lecturers;
    }

    /**
     * Replaces this lesson's lecturers with the lecturers in the argument lecturer set.
     */
    public void setLecturers(Set<Lecturer> replacement) {
        lecturers.set(new UniqueLecturerList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyLesson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyLesson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(classType, location, group, timeSlot, code, lecturers);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
```
###### /java/seedu/address/model/module/Location.java
``` java
/**
 * Represents a Lesson's location in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {
    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Lesson location can take any values, and it should not be blank";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given location.
     *
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        requireNonNull(location);
        if (!isValidLocation(location)) {
            throw new IllegalValueException(MESSAGE_LOCATION_CONSTRAINTS);
        }
        this.value = location;
    }

    /**
     * Get a StringProperty of the location
     */
    public StringProperty getAddressProperty() {
        return new SimpleStringProperty(value);
    }

    /**
     * Returns true if a given string is a valid lesson location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/module/ReadOnlyLesson.java
``` java
/**
 * A read-only immutable interface for a Lesson in the application.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyLesson {

    ObjectProperty<Location> locationProperty();
    Location getLocation();
    ObjectProperty<TimeSlot> timeSlotProperty();
    TimeSlot getTimeSlot();
    ObjectProperty<ClassType> classTypeProperty();
    ClassType getClassType();
    ObjectProperty<Group> groupProperty();
    Group getGroup();
    ObjectProperty<Code> codeProperty();
    Code getCode();
    ObjectProperty<Boolean> isMarkedProperty();
    void setAsMarked();
    void setAsUnmarked();
    boolean isMarked();
    ObjectProperty<UniqueLecturerList> lecturersProperty();
    Set<Lecturer> getLecturers();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyLesson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getLocation().equals(this.getLocation()) // state checks here onwards
                && other.getTimeSlot().equals(this.getTimeSlot())
                && other.getClassType().equals(this.getClassType())
                && other.getGroup().equals(this.getGroup())
                && other.getCode().equals(this.getCode()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Module Code: ")
                .append(getCode())
                .append(" Class Type: ")
                .append(getClassType())
                .append(" Location: ")
                .append(getLocation())
                .append(" Group: ")
                .append(getGroup())
                .append(" Time Slot: ")
                .append(getTimeSlot())
                .append(" Lecturers: ");
        getLecturers().forEach(builder::append);
        return builder.toString();
    }

}
```
###### /java/seedu/address/model/module/TimeSlot.java
``` java
/**
 * Represents a Lesson time slot in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidTimeSLot(String)}
 */
public class TimeSlot {
    public static final String MESSAGE_TIMESLOT_CONSTRAINTS =
            "Lesson time slot should contain 4 parts: " + ""
                    + "3 letters abbreviations for the week days, "
                    + " [4 digits 24-hour clock format "
                    + " - "
                    + " 4 digits 24-hour clock format]"
                    + " eg. FRI[1000-1200]";
    public static final String TIMESLOT_VALIDATION_REGEX = "[a-zA-Z]{3}\\[[\\d]{4}-[\\d]{4}]";

    public final String value;

    /**
     * Validates given time slot.
     *
     * @throws IllegalValueException if given time slot string is invalid.
     */
    public TimeSlot(String timeSlot) throws IllegalValueException {
        requireNonNull(timeSlot);
        String trimmedTimeSlot = timeSlot.trim();
        if (!isValidTimeSLot(trimmedTimeSlot)) {
            throw new IllegalValueException(MESSAGE_TIMESLOT_CONSTRAINTS);
        }

        this.value = trimmedTimeSlot;
    }

    /**
     * Returns if a given string is a valid lesson time slot.
     */
    public static boolean isValidTimeSLot(String test) {
        if (test.matches(TIMESLOT_VALIDATION_REGEX)) {
            String weekDay = test.substring(0, 3);
            String startHour = test.substring(4, 6);
            String startMin = test.substring(6, 8);
            String endHour = test.substring(9, 11);
            String endMin = test.substring(11, 13);
            boolean isWeekValid = weekValid(weekDay);
            boolean isTimeValid = timeValid(startHour, startMin, endHour, endMin);

            if (isWeekValid && isTimeValid) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if the given week text is valid
     * @param weekStr
     * @return true if the text is valid
     */
    private static boolean weekValid(String weekStr) {
        String[] capitalDays = {"SUN", "MON", "TUE", "WED", "THU", "FRI",
            "SAT" };
        for (int i = 0; i < capitalDays.length; i++) {
            if (weekStr.equalsIgnoreCase(capitalDays[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is to check whether for the validity of time.
     * Start hour should be less than end hour
     * @param sh start hour
     * @param sm start minute
     * @param et end hour
     * @param em end minute
     * @return true if the time matches the time format
     */
    private static boolean timeValid(String sh, String sm, String et, String em) {
        int startHr = Integer.parseInt(sh);
        int startMin = Integer.parseInt(sm);
        int endHr = Integer.parseInt(et);
        int endMin = Integer.parseInt(em);
        int startTemp = 0;
        int endTemp = -1;
        if (hourValid(startHr) && hourValid(endHr) && minValid(startMin) && minValid(endMin)) {
            startTemp = startHr * 100 + startMin;
            endTemp = endHr * 100 + endMin;
        }
        return startTemp < endTemp;
    }

    private static boolean hourValid(int hour) {
        return (hour <= 23 && hour >= 0);
    }

    private static boolean minValid(int minute) {
        return (minute <= 59 && minute >= 0);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeSlot // instanceof handles nulls
                && this.value.equals(((TimeSlot) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/module/UniqueLessonList.java
``` java
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

```
###### /java/seedu/address/model/module/UniqueLessonList.java
``` java
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
```
###### /java/seedu/address/model/ReadOnlyAddressBook.java
``` java
/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the lesson list.
     * This list will not contain any duplicate lessons.
     */
    ObservableList<ReadOnlyLesson> getLessonList();

    /**
     * Returns an unmodifiable view of the lecturers list.
     * This list will not contain any duplicate lecturers.
     */
    ObservableList<Lecturer> getLecturerList();

```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Lesson[] getSampleLessons() {
        try {
            return new Lesson[]{
                new Lesson(new ClassType("LEC"), new Location("LT19"), new Group("1"),
                        new TimeSlot("FRI[1400-1600]"), new Code("CS2103T"), getLecturerSet("Damith")),
                new Lesson(new ClassType("TUT"), new Location("COM1-0207"), new Group("1"),
                        new TimeSlot("TUE[1000-1100]"), new Code("CS2103"), getLecturerSet("David")),
                new Lesson(new ClassType("LEC"), new Location("LT27"), new Group("1"),
                        new TimeSlot("WED[1200-1400]"), new Code("MA1101R"), getLecturerSet("Ma Siu Lun")),
                new Lesson(new ClassType("TUT"), new Location("LT27"), new Group("1"),
                        new TimeSlot("MON[0900-1000]"), new Code("MA1101A"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("TUT"), new Location("LT27"), new Group("2"),
                        new TimeSlot("MON[1000-1100]"), new Code("MA1101B"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("LEC"), new Location("LT27"), new Group("2"),
                        new TimeSlot("THU[1600-1800]"), new Code("MA1101C"), getLecturerSet("Smith")),

                new Lesson(new ClassType("LEC"), new Location("LT19"), new Group("71"),
                        new TimeSlot("WED[1400-1600]"), new Code("CS2103T"), getLecturerSet("Damith")),
                new Lesson(new ClassType("TUT"), new Location("COM1-0207"), new Group("71"),
                        new TimeSlot("TUE[1200-1300]"), new Code("CS2103"), getLecturerSet("David")),
                new Lesson(new ClassType("LEC"), new Location("LT27"), new Group("71"),
                        new TimeSlot("WED[1800-2000]"), new Code("MA1101R"), getLecturerSet("Ma Siu Lun")),
                new Lesson(new ClassType("TUT"), new Location("COM1-02-3"), new Group("71"),
                        new TimeSlot("MON[0800-0900]"), new Code("MA1101A"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("TUT"), new Location("LT27"), new Group("72"),
                        new TimeSlot("MON[0800-0900]"), new Code("MA1101B"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("LEC"), new Location("LT37"), new Group("72"),
                        new TimeSlot("THU[1600-1800]"), new Code("MA1101C"), getLecturerSet("Smith")),

                new Lesson(new ClassType("LEC"), new Location("LT19"), new Group("51"),
                        new TimeSlot("MON[1400-1600]"), new Code("CS2103T"), getLecturerSet("Damith")),
                new Lesson(new ClassType("TUT"), new Location("COM1-0207"), new Group("51"),
                        new TimeSlot("WED[1200-1300]"), new Code("CS2103"), getLecturerSet("David")),
                new Lesson(new ClassType("LEC"), new Location("LT27"), new Group("51"),
                        new TimeSlot("FRI[1800-2000]"), new Code("MA1101R"), getLecturerSet("Ma Siu Lun")),
                new Lesson(new ClassType("TUT"), new Location("COM1-02-3"), new Group("51"),
                        new TimeSlot("THU[0800-0900]"), new Code("MA1101A"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("LEC"), new Location("LT27"), new Group("42"),
                        new TimeSlot("FRI[1000-1200]"), new Code("MA1101B"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("LEC"), new Location("LT37"), new Group("22"),
                        new TimeSlot("FRI[1600-1800]"), new Code("MA1101C"), getLecturerSet("Smith")),

                new Lesson(new ClassType("LEC"), new Location("LT19"), new Group("11"),
                        new TimeSlot("MON[0800-1000]"), new Code("CS2103T"), getLecturerSet("Damith")),
                new Lesson(new ClassType("LEC"), new Location("LT33"), new Group("11"),
                        new TimeSlot("WED[1200-1400]"), new Code("CS2103"), getLecturerSet("David")),
                new Lesson(new ClassType("TUT"), new Location("LT27"), new Group("11"),
                        new TimeSlot("TUE[0900-1000]"), new Code("MA1101R"), getLecturerSet("Ma Siu Lun")),
                new Lesson(new ClassType("TUT"), new Location("COM1-02-3"), new Group("11"),
                        new TimeSlot("THU[1800-1900]"), new Code("MA1101A"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("LEC"), new Location("LT17"), new Group("32"),
                        new TimeSlot("TUE[0800-1000]"), new Code("MA1101B"), getLecturerSet("LI Sheng")),
                new Lesson(new ClassType("LEC"), new Location("LT37"), new Group("12"),
                        new TimeSlot("TUE[1000-1200]"), new Code("MA1101C"), getLecturerSet("Smith")),

            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Lesson sampleLesson : getSampleLessons()) {

                sampleAb.addLesson(sampleLesson);
            }
            return sampleAb;
        } catch (DuplicateLessonException e) {
            throw new AssertionError("sample data cannot contain duplicate lessons", e);
        }
    }

    /**
     * Returns a lecturer set containing the list of strings given.
     */
    public static Set<Lecturer> getLecturerSet(String... strings) throws IllegalValueException {
        HashSet<Lecturer> lecturers = new HashSet<>();
        for (String s : strings) {
            lecturers.add(new Lecturer(s));
        }

        return lecturers;
    }

}
```
###### /java/seedu/address/storage/XmlAdaptedLecturer.java
``` java
/**
 * Stores lecturer data in an XML file
 */
public class XmlAdaptedLecturer {

    @XmlValue
    private String name;

    /**
     * Constructs an XmlAdaptedLecturer.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLecturer() {}

    /**
     * Converts a given Lecturer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedLecturer(Lecturer source) {
        name = source.lecturerName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Lecturer object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Lecturer
     */
    public Lecturer toModelType() throws IllegalValueException {
        return new Lecturer(name);
    }

}
```
###### /java/seedu/address/storage/XmlAdaptedLesson.java
``` java
/**
 * Stores lesson data in an XML file
 */
public class XmlAdaptedLesson {
    @XmlElement(required = true)
    private String classType;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String group;
    @XmlElement(required = true)
    private String code;
    @XmlElement(required = true)
    private String timeSlot;
    @XmlElement(required = true)
    private Boolean isMarked;

    @XmlElement
    private List<XmlAdaptedLecturer> lecturerList = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedLesson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLesson() {}


    /**
     * Converts a given Lesson into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLesson
     */
    public XmlAdaptedLesson(ReadOnlyLesson source) {
        classType = source.getClassType().value;
        location = source.getLocation().value;
        group = source.getGroup().value;
        code = source.getCode().fullCodeName;
        timeSlot = source.getTimeSlot().value;
        isMarked = source.isMarked();

        lecturerList = new ArrayList<>();
        for (Lecturer lecturer : source.getLecturers()) {
            lecturerList.add(new XmlAdaptedLecturer(lecturer));
        }
    }

    /**
     * Converts this jaxb-friendly adapted lesson object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson
     */
    public Lesson toModelType() throws IllegalValueException {
        final ClassType classType = new ClassType(this.classType);
        final Location location = new Location(this.location);
        final Group group = new Group(this.group);
        final Code code = new Code(this.code);
        final TimeSlot timeSLot = new TimeSlot(this.timeSlot);
        final Boolean isMarked = this.isMarked;

        final List<Lecturer> lecturers = new ArrayList<>();
        for (XmlAdaptedLecturer lecturer : lecturerList) {
            lecturers.add(lecturer.toModelType());
        }
        final Set<Lecturer> lecturerSet = new HashSet<>(lecturers);
        Lesson lesson = new Lesson(classType, location, group, timeSLot, code, lecturerSet);
        if (isMarked) {
            lesson.setAsMarked();
        }
        return lesson;
    }
}
```
###### /java/seedu/address/ui/CombinePanel.java
``` java
/**
 * The UI component that is responsible for combining the web browser panel, the timetable panel and sticky notes panel.
 */
public class CombinePanel extends UiPart<Region> {


    public static final String DEFAULT_PAGE = "default.html";
    public static final String NUS_MAP_SEARCH_URL_PREFIX = "http://map.nus.edu.sg/#page=search&type=by&qword=";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1";

    private static final String FXML = "CombinePanel.fxml";
    private static final String LESSON_NODE_ID = "lessonNode";
    private static final String STICKY_NOTE = "stickyNote";
    private static final String HEADER = "header";
    private static final int ROW = 6;
    private static final int COL = 13;
    private static final int START_TIME = 8;

    private static final Pattern LOCATION_KEYWORD_PATTERN = Pattern.compile(".*(?=-)");

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;
    private GridData[][] gridData;
    private int[][]  gridDataCheckTable;
    private String[][]noteData;
    private ReadOnlyLesson selectedModule;

    @FXML
    private StackPane stackPane;
    @FXML
    private WebView browser;
    @FXML
    private HBox timeBox;
    @FXML
    private GridPane timetableGrid;
    @FXML
    private HBox timeHeader;
    @FXML
    private HBox noteBox;
    @FXML
    private GridPane noteGrid;

    public CombinePanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        gridData = new GridData[ROW][COL];
        gridDataCheckTable = new int[ROW][COL];
        initGridData();
        generateTimeTableGrid();
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultPage();
        timeBox.setVisible(false);
        browser.setVisible(false);
        registerAsAnEventHandler(this);
        selectedModule = null;

        noteBox.setVisible(true);
        stickyNotesInit();

    }

    @Subscribe
    private void handleViewedLessonEvent(ViewedLessonEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        generateTimeTableGrid();
        if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.LESSON)) {
            timeBox.setVisible(true);
            browser.setVisible(false);
            noteBox.setVisible(false);
        } else if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.LOCATION)) {
            timeBox.setVisible(false);
            browser.setVisible(false); //Only set as visible when location is selected
            noteBox.setVisible(false);
        } else {
            timeBox.setVisible(false);
            browser.setVisible(false);
            noteBox.setVisible(true);
        }
    }


    /**
     * Initialize grid data.
     */
    private void initGridData() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                gridData[i][j] = new GridData();
                gridDataCheckTable[i][j] = 0;
            }
        }
    }

    /**
     * Generate time slot header
     */
    public void generateTimeslotHeader() {
        String text;
        int k = START_TIME;
        for (int i = 1; i < COL + 1; i++) {

            if (k < 10) {
                text = "0" + k;
            } else {
                text = Integer.toString(k);
            }
            text += "00";
            Label header = new Label(text);
            header.setId(HEADER);
            timetableGrid.setHalignment(header, HPos.CENTER);
            timetableGrid.add(header, i, 0);
            k++;
        }
    }

    /**
     * Generate week day row header
     */
    public void generateWeekDay() {
        for (int i = 1; i < ROW; i++) {
            String dayOfWeek = DayOfWeek.of(i).toString().substring(0, 3); //The first 3 characters of WeekDay, eg MON
            Label label = new Label(dayOfWeek);
            label.setId(HEADER);
            timetableGrid.setValignment(label, VPos.CENTER);
            timetableGrid.setHalignment(label, HPos.CENTER);
            timetableGrid.add(label, 0, i);
        }
    }

    /**
     * Generate timetable data
     */
    public void generateTimeTableData() {
        ObservableList<ReadOnlyLesson> lessons = logic.getFilteredLessonList();
        initGridData();

        //This code allows the gridline to stay appear
        Node node = timetableGrid.getChildren().get(0);
        timetableGrid.getChildren().clear();
        timetableGrid.getChildren().add(0, node);

        for (int i = 0; i < lessons.size(); i++) {
            ReadOnlyLesson lesson = lessons.get(i);
            String text = lesson.getCode() + " " + lesson.getClassType()
                    + "(" + lesson.getGroup() + ") " + lesson.getLocation();
            String timeText = lesson.getTimeSlot().toString();
            int weekDayRow = getWeekDay(timeText.substring(0, 3)); //First 3 characters are Weekday
            int startHourCol = getTime(timeText.substring(4, 6));  //Next 2 characters are StartHour
            int endHourSpan = getTime(timeText.substring(9, 11)) - startHourCol; //find the number of hours for lesson
            boolean isAvailable = false;
            if (!isOccupy(weekDayRow, startHourCol, endHourSpan)) {
                isAvailable = true;
            }

            if (isAvailable && gridData[weekDayRow][startHourCol].getCount() == 0) {
                gridData[weekDayRow][startHourCol] = new GridData(text, weekDayRow, startHourCol,
                        endHourSpan, 1); //1 represents no other lessons occupy this time slot
            } else {
                int count = gridData[weekDayRow][startHourCol].getCount();
                gridData[weekDayRow][startHourCol] = new GridData(text, weekDayRow, startHourCol,
                        endHourSpan, count + 2); //count + 2 to indicate another lesson occupying this time slot
            }

            //Update gridDataCheckTable
            for (int j = 0; j < endHourSpan; j++) {
                gridDataCheckTable[weekDayRow][startHourCol + j] = 1;
            }
        }
    }

    /**
     * Check for timetable grid.
     */
    public boolean isOccupy(int row, int col, int span) {
        for (int i = 0; i < span; i++) {
            if (gridDataCheckTable[row][col + i] == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * Generate timetable grid.
     */
    public void generateTimeTableGrid() {

        generateTimeTableData();
        generateTimeslotHeader();
        generateWeekDay();

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                GridData data = gridData[i][j];
                String text = data.getText();
                int weekDayRow = data.getWeekDayRow();
                int startHourCol = data.getStartHourCol();
                int endHourSpan = data.getEndHourSpan();
                int count = data.getCount();
                if (i == weekDayRow &&  j == startHourCol) {
                    TextArea lbl = new TextArea(text);
                    lbl.setWrapText(true);
                    lbl.setEditable(false);
                    lbl.setId(LESSON_NODE_ID);
                    timetableGrid.setGridLinesVisible(true);
                    if (endHourSpan == 1) {
                        lbl.setStyle("-fx-font-size: small");
                    }
                    timetableGrid.add(lbl, j + 1, i + 1, endHourSpan, 1);
                    if (count > 1) {
                        lbl.setStyle("-fx-control-inner-background: red");
                    }
                }
            }
        }
    }


    private int getWeekDay(String textInput) {
        textInput = textInput.toUpperCase();
        switch (textInput) {
        case "MON":
            return 0;
        case "TUE":
            return 1;
        case "WED":
            return 2;
        case "THU":
            return 3;
        case "FRI":
            return 4;
        default:
            return -1;
        }
    }

    private int getTime(String text) {
        int time = Integer.parseInt(text);
        return time - START_TIME;
    }


    /************* BROWSER PANNEL *********/

    private void loadLessonPage(ReadOnlyLesson lesson) {
        loadPage(NUS_MAP_SEARCH_URL_PREFIX + getImportantKeywords(lesson.getLocation().toString()));
    }

    /**
     * Get substring before hyphen.
     */
    private String getImportantKeywords(String location) {
        Matcher matcher = LOCATION_KEYWORD_PATTERN.matcher(location);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return location;
        }
    }


    /**
     * Load page for given url
     * @param url
     */
    public void loadPage(String url) {
        WebEngine engine = browser.getEngine();
        engine.setUserAgent(USER_AGENT);
        Platform.runLater(() -> engine.load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleLessonPanelSelectionChangedEvent(LessonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.LOCATION)) {
            loadLessonPage(event.getNewSelection().lesson);
            timeBox.setVisible(false);
            browser.setVisible(true);
            noteBox.setVisible(false);
        }

        if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.MODULE)) {
            selectedModule = event.getNewSelection().lesson;
            logic.setRemarkPredicate(new SelectedStickyNotePredicate(event.getNewSelection().lesson.getCode()));
            stickyNotesInit();
        }
    }


    /***************** Sticky Note *****************/
```
###### /java/seedu/address/ui/CombinePanel.java
``` java
    /**
     * This method will initialize StickyNote screen
     */
    public void stickyNotesInit() {
        noteGrid.getChildren().clear();
        noteDataInit();
        //noteGrid.setGridLinesVisible(true);
        noteGrid.setHgap(20); //horizontal gap in pixels => that's what you are asking for
        noteGrid.setVgap(20); //vertical gap in pixels

        //Only display 9 notes, so 3 x 3 Matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String text = noteData[i][j];
                if (text == null) {
                    return;
                }

                //Generate random RGB color
                int x = 120 + (int) (Math.random() * 255);
                int y = 120 + (int) (Math.random() * 255);
                int z = 120 + (int) (Math.random() * 255);

                TextArea ta = new TextArea(text);

                ta.setWrapText(true);
                ta.setEditable(false);

                StackPane noteStackPane = new StackPane();
                noteStackPane.setStyle("-fx-background-color: rgba(" + x + "," + y + ", " + z + ", 0.5);"
                        + "-fx-effect: dropshadow(gaussian, red, " + 20 + ", 0, 0, 0);"
                        + "-fx-background-insets: " + 10 + ";");
                ta.setId(STICKY_NOTE);
                noteStackPane.getChildren().add(ta);
                noteGrid.add(noteStackPane, j, i);
                FontSizeUnit currFontSize = FontSizeUnit.getCurrentFontSizeUnit();
                setFontSizeUnit(currFontSize);
            }
        }
    }

```
###### /java/seedu/address/ui/CombinePanel.java
``` java
/**
 * Contains data related to timetable grid object in JavaFX.
 */
class GridData {
    private String text;
    private Integer weekDayRow;
    private Integer startHourCol;
    private Integer endHourSpan;
    private Integer count;

    public GridData() {
        this("", -1, -1, -1, 0);
    }

    public GridData(String text, int weekDayRow, int startHourCol, int endHourSpan, int count) {
        this.text = text;
        this.weekDayRow = weekDayRow;
        this.startHourCol = startHourCol;
        this.endHourSpan = endHourSpan;
        this.count = count;
    }

    public String getText() {
        return text;
    }

    public Integer getEndHourSpan() {
        return endHourSpan;
    }

    public Integer getStartHourCol() {
        return startHourCol;
    }

    public Integer getWeekDayRow() {
        return weekDayRow;
    }

    public Integer getCount() {
        return count;
    }


    @Override
    public int hashCode() {
        return Objects.hash(text, weekDayRow, startHourCol, endHourSpan);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GridData other = (GridData) obj;
        if (!text.equals(other.text)) {
            return false;
        }
        if (!weekDayRow.equals(other.weekDayRow)) {
            return false;
        }
        if (!startHourCol.equals(other.startHourCol)) {
            return false;
        }
        if (!endHourSpan.equals(other.endHourSpan)) {
            return false;
        }
        return true;
    }
}

```
###### /java/seedu/address/ui/CommandBox.java
``` java
/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final String TAG_PREFIX = "prefix";

    private static final int CODE = 0;
    private static final int CLASSTYPE = 1;
    private static final int VENUE = 2;
    private static final int GROUP = 3;
    private static final int TIMESLOT = 4;
    private static final int LECTURER = 5;
    private static final int FONT_SIZE = 6;

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    private final AddressBookParser tester;

    private HashMap<String, String> keywordColorMap;
    private ArrayList<String> prefixList;
    private int fontIndex = 0;
    private boolean enableHighlight = false;
    private String userPrefFontSize = "-fx-font-size: " + FONT_SIZE_NORMAL_NUMBER + ";";

    private final ImageView tick = new ImageView("/images/tick.png");
    private final ImageView cross = new ImageView("/images/cross.png");


    @FXML
    private TextField commandTextField;

    @FXML
    private Text commandTextDefault;

    @FXML
    private Text commandTextXsmall;

    @FXML
    private Text commandTextSmall;

    @FXML
    private Text commandTextLarge;

    @FXML
    private Text commandTextXLarge;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label keywordLabel;

    @FXML
    private Label checkBox;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        configInactiveKeyword();
        configPrefixList();
        keywordLabel.getStyleClass().add("keyword-label-default");
        keywordColorMap = getCommandKeywordColorMap();
        String[] commands = {"help", "add", "list", "edit", "find", "delete", "select", "history", "undo", "redo",
            "clear", "exit", "customise", "view", "theme", "mark", "unmark", "remark", "color"};
        TextFields.bindAutoCompletion(commandTextField, commands); // controlsfx
        tick.setFitHeight(30);
        tick.setFitWidth(30);
        cross.setFitHeight(25);
        cross.setFitWidth(25);
        historySnapshot = logic.getHistorySnapshot();
        tester = new AddressBookParser();
        registerAsAnEventHandler(this);
    }


    /**
     * This method create a list of prefix used in the command
     */
    private void configPrefixList() {
        prefixList = new ArrayList<String>();
        prefixList.add(CliSyntax.PREFIX_MODULE_CODE.getPrefix());
        prefixList.add(CliSyntax.PREFIX_CLASS_TYPE.getPrefix());
        prefixList.add(CliSyntax.PREFIX_VENUE.getPrefix());
        prefixList.add(CliSyntax.PREFIX_GROUP.getPrefix());
        prefixList.add(CliSyntax.PREFIX_TIME_SLOT.getPrefix());
        prefixList.add(CliSyntax.PREFIX_LECTURER.getPrefix());
        prefixList.add(CliSyntax.PREFIX_FONT_SIZE.getPrefix());
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Handles the key released event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        default:
            listenCommandInputChanged();
            break;
        }
    }


    /**
     * Handles the Command input changed event.
     */
    private void listenCommandInputChanged() {
        if (enableHighlight) {
            String allTextInput = commandTextField.getText();
            String[] inputArray = allTextInput.split(" ");
            int index = 0;

            configInActiveTag();
            configInactiveKeyword();

            configBorderColor(allTextInput);

            for (int i = 0; i < inputArray.length; i++) {
                String text = inputArray[i];

                //Command Keyword
                if (i == 0 && validCommandKeyword(text)) {
                    configActiveKeyword(text);
                }

                //Code
                if (text.contains(prefixList.get(CODE))) {
                    index = allTextInput.indexOf(prefixList.get(CODE));
                    configActiveTag(index, prefixList.get(CODE));
                }

                //Class type
                if (text.contains(prefixList.get(CLASSTYPE))) {
                    index = allTextInput.indexOf(prefixList.get(CLASSTYPE));
                    configActiveTag(index, prefixList.get(CLASSTYPE));
                }

                //Venue
                if (text.contains(prefixList.get(VENUE))) {
                    index = allTextInput.indexOf(prefixList.get(VENUE));
                    configActiveTag(index, prefixList.get(VENUE));
                }

                //Group
                if (text.contains(prefixList.get(GROUP))) {
                    index = allTextInput.indexOf(prefixList.get(GROUP));
                    configActiveTag(index, prefixList.get(GROUP));
                }

                //Time slot
                if (text.contains(prefixList.get(TIMESLOT))) {
                    index = allTextInput.indexOf(prefixList.get(TIMESLOT));
                    configActiveTag(index, prefixList.get(TIMESLOT));
                }

                //Lecturer
                if (text.contains(prefixList.get(LECTURER))) {
                    ArrayList<Integer> tagList = getTagIndexList(allTextInput);
                    for (int j = 0; j < tagList.size(); j++) {
                        index = tagList.get(j);
                        configActiveTag(index, index + prefixList.get(LECTURER));
                    }
                }

                //font size
                if (text.contains(prefixList.get(FONT_SIZE))) {
                    index = allTextInput.indexOf(prefixList.get(FONT_SIZE));
                    configActiveTag(index, prefixList.get(FONT_SIZE));
                }
            }
        } else {
            commandTextField.setStyle(userPrefFontSize);
            checkBox.setVisible(false);
        }

    }


    public ArrayList<Integer> getTagIndexList(String allTextInput) {
        ArrayList<Integer> tagList = new ArrayList<>();
        int index = 0;
        while (index < allTextInput.length()) {
            int newIndex = allTextInput.indexOf(prefixList.get(LECTURER), index);
            if (newIndex == -1) {
                break;
            }
            tagList.add(newIndex);
            index = newIndex + 1;
        }
        return tagList;
    }


    /**
     * Check if keyword is a valid command keyword
     * @param keyWord
     * @return
     */
    private boolean validCommandKeyword(String keyWord) {
        return keywordColorMap.containsKey(keyWord);
    }


    /**
     * Configure words that are not command keyword
     */
    private void configInactiveKeyword() {
        keywordLabel.setVisible(false);
        keywordLabel.toBack();
        commandTextField.toFront();
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Configure command keyword when appeared on Command Box
     * @param commandKeyword
     */
    private void configActiveKeyword(String commandKeyword) {
        keywordLabel.setId("keywordLabel");
        keywordLabel.setText(commandKeyword);
        keywordLabel.setVisible(true);

        keywordLabel.getStyleClass().clear();

        //These magic numbers are used for handling different font size
        Insets leftInset = new Insets(0, 0, 0, 17);

        switch (fontIndex) {
        case 1:
            keywordLabel.getStyleClass().add("keyword-label-xsmall");
            leftInset = new Insets(0, 0, 0, 10);
            break;
        case 2:
            keywordLabel.getStyleClass().add("keyword-label-small");
            leftInset = new Insets(0, 0, 0, 12);
            break;
        case 3:
            keywordLabel.getStyleClass().add("keyword-label-default");
            leftInset = new Insets(0, 0, 0, 17);
            break;
        case 4:
            keywordLabel.getStyleClass().add("keyword-label-large");
            leftInset = new Insets(0, 0, 0, 21);
            break;
        case 5:
            keywordLabel.getStyleClass().add("keyword-label-xlarge");
            leftInset = new Insets(0, 0, 0, 26);
            break;
        default:
            keywordLabel.getStyleClass().add("keyword-label-default");
            break;
        }

        stackPane.setAlignment(keywordLabel, Pos.CENTER_LEFT);
        stackPane.setMargin(keywordLabel, leftInset);

        String color = keywordColorMap.get(commandKeyword);
        // keywordLabel.setStyle("-fx-background-color: " + color + ";\n"
        // + "-fx-text-fill: red;");
        keywordLabel.setStyle(("-fx-text-fill: " + color));
        keywordLabel.setOpacity(0.7);
        keywordLabel.toFront();
    }


    /**
     * Configure tag that appear in the text field
     */
    private void configActiveTag(int index, String tag) {
        String allTextInput = commandTextField.getText();
        String inputText = allTextInput.substring(0, index);

        String tagName = tag.replaceAll("[0-9]", "");
        Label tagLabel = new Label(tagName);
        tagLabel.setId(TAG_PREFIX + tag);

        tagLabel.getStyleClass().clear();
        double margin = computeMargin(0, inputText);

        //These magic numbers are used for handling different font size
        Insets leftInset = new Insets(0, 0, 0, margin + 17);

        switch (fontIndex) {
        case 1:
            tagLabel.getStyleClass().add("keyword-label-xsmall");
            margin = computeMargin(1, inputText);
            leftInset = new Insets(0, 0, 0, margin + 10);
            break;
        case 2:
            tagLabel.getStyleClass().add("keyword-label-small");
            margin = computeMargin(2, inputText);
            leftInset = new Insets(0, 0, 0, margin + 12);
            break;
        case 3:
            tagLabel.getStyleClass().add("keyword-label-default");
            margin = computeMargin(3, inputText);
            leftInset = new Insets(0, 0, 0, margin + 17);
            break;
        case 4:
            tagLabel.getStyleClass().add("keyword-label-large");
            margin = computeMargin(4, inputText);
            leftInset = new Insets(0, 0, 0, margin + 21);
            break;
        case 5:
            tagLabel.getStyleClass().add("keyword-label-xlarge");
            margin = computeMargin(5, inputText);
            leftInset = new Insets(0, 0, 0, margin + 26);
            break;
        default:
            tagLabel.getStyleClass().add("keyword-label-default");
            break;
        }

        stackPane.getChildren().add(tagLabel);
        stackPane.setAlignment(tagLabel, Pos.CENTER_LEFT);
        stackPane.setMargin(tagLabel, leftInset);

        // tagLabel.setStyle("-fx-background-color:yellow;\n"
        // + "-fx-text-fill: red; ");

        tagLabel.setStyle("-fx-text-fill: yellow");
        tagLabel.setOpacity(0.7);
        tagLabel.setVisible(true);
        tagLabel.toFront();
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    @Subscribe
    private void handleColorKeywordEvent(ColorKeywordEvent event) {
        setEnableHighlight(event.isEnabled);
    }



    /**
     * This method only remove all tag label in stack pane
     */
    private void configInActiveTag() {
        ObservableList<Node> list = stackPane.getChildren();
        final List<Node> removalCandidates = new ArrayList<>();

        Iterator<Node> iter = list.iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node.getId().contains(TAG_PREFIX)) {
                node.setVisible(false);
                removalCandidates.add(node);
            }
        }
        stackPane.getChildren().removeAll(removalCandidates);
    }


    /**
     * This method compute the margin for label
     * @param index the type of font size used in command box
     * @param str the text used to compute the width
     * @return
     */
    public double computeMargin(int index, String str) {
        Text text = new Text(str);
        text.getStyleClass().clear();
        switch (index) {
        case 1:
            text.setFont(commandTextXsmall.getFont());
            break;
        case 2:
            text.setFont(commandTextSmall.getFont());
            break;
        case 3:
            text.setFont(commandTextDefault.getFont());
            break;
        case 4:
            text.setFont(commandTextLarge.getFont());
            break;
        case 5:
            text.setFont(commandTextXLarge.getFont());
            break;
        default:
            text.setFont(commandTextDefault.getFont());
            break;

        }

        return text.getBoundsInLocal().getWidth();
    }


```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Sets the command box to enable highlighting of command keywords
     */
    public void setEnableHighlight(boolean enableHighlight) {
        this.enableHighlight = enableHighlight;
    }

```
###### /java/seedu/address/ui/CommandBox.java
``` java
    public HashMap<String, String> getCommandKeywordColorMap() {
        HashMap<String, String> keywordColorMap = new HashMap<>();
        keywordColorMap.put(AddCommand.COMMAND_WORD, "#ff0000");
        keywordColorMap.put(DeleteCommand.COMMAND_WORD, "red");
        keywordColorMap.put(EditCommand.COMMAND_WORD, "#ff7f7f");
        keywordColorMap.put(ExitCommand.COMMAND_WORD, "red");
        keywordColorMap.put(FindCommand.COMMAND_WORD, "#cc0000");
        keywordColorMap.put(HelpCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ListCommand.COMMAND_WORD, "red");
        keywordColorMap.put(SelectCommand.COMMAND_WORD, "#b20000");
        keywordColorMap.put(SortCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ClearCommand.COMMAND_WORD, "red");
        keywordColorMap.put(UndoCommand.COMMAND_WORD, "red");
        keywordColorMap.put(RedoCommand.COMMAND_WORD, "red");
        keywordColorMap.put(CustomiseCommand.COMMAND_WORD, "#990000");
        keywordColorMap.put(HistoryCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ViewCommand.COMMAND_WORD, "#ff6666");
        keywordColorMap.put(ColorKeywordCommand.COMMAND_WORD, "#660000");
        return keywordColorMap;
    }

}
```
###### /java/seedu/address/ui/HelpWindow.java
``` java
    public HelpWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(true);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        WebEngine engine = browser.getEngine();
        engine.setUserAgent(USER_AGENT);
        String userGuideUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        engine.load(userGuideUrl);
    }
    //@author

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        dialogStage.showAndWait();
    }
}
```
###### /resources/view/CombinePanel.fxml
``` fxml

<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.web.WebView?>

<StackPane fx:id="stackPane" stylesheets="@LightTheme.css" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox fx:id="timeBox" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" minHeight="0.0" minWidth="0.0" prefHeight="500.0" prefWidth="1000.0">
         <children>
            <VBox maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" minHeight="0.0" minWidth="0.0" prefHeight="600.0" prefWidth="850.0" HBox.hgrow="ALWAYS">
               <children>
                  <GridPane fx:id="timetableGrid" gridLinesVisible="true" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" minHeight="0.0" minWidth="900.0" prefHeight="450.0" prefWidth="900.0" VBox.vgrow="ALWAYS">
                     <columnConstraints>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="70.0" prefWidth="100.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" percentWidth="50.0" />
                     </columnConstraints>
                     <rowConstraints>
                        <RowConstraints minHeight="18.0" percentHeight="30.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="90.0" percentHeight="50.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="90.0" percentHeight="50.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="90.0" percentHeight="50.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="90.0" percentHeight="50.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="90.0" percentHeight="50.0" vgrow="SOMETIMES" />
                     </rowConstraints>
                  </GridPane>
               </children>
            </VBox>
         </children>
      </HBox>
      <WebView fx:id="browser" prefHeight="480.0" prefWidth="1000.0" />
      <HBox fx:id="noteBox" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" minHeight="0.0" minWidth="0.0" prefHeight="500.0" prefWidth="1000.0">
         <children>
            <VBox maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" minHeight="0.0" minWidth="0.0" prefHeight="600.0" prefWidth="850.0" HBox.hgrow="ALWAYS">
               <children>
                  <GridPane fx:id="noteGrid" minWidth="900.0" VBox.vgrow="ALWAYS">
                    <columnConstraints>
                      <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" percentWidth="50.0" />
                      <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" percentWidth="50.0" />
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="90.0" percentWidth="50.0" />
                    </columnConstraints>
                    <rowConstraints>
                      <RowConstraints minHeight="90.0" prefHeight="30.0" vgrow="SOMETIMES" />
                      <RowConstraints minHeight="90.0" prefHeight="30.0" vgrow="SOMETIMES" />
                      <RowConstraints minHeight="90.0" prefHeight="30.0" vgrow="SOMETIMES" />
                    </rowConstraints>
                  </GridPane>
               </children>
            </VBox>
         </children>
      </HBox>
   </children>
</StackPane>
```
###### /resources/view/CommandBox.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.text.Text?>

<StackPane fx:id="stackPane" maxHeight="37.0" minHeight="-Infinity" prefHeight="37.0" prefWidth="310.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <Label fx:id="keywordLabel" maxHeight="-Infinity" maxWidth="-Infinity" text="Label" />
   <Text fx:id="commandTextDefault" strokeType="OUTSIDE" strokeWidth="0.0" textAlignment="JUSTIFY" StackPane.alignment="CENTER_LEFT">
      <StackPane.margin>
         <Insets left="13.0" />
      </StackPane.margin>
   </Text>
   <Text fx:id="commandTextXsmall" strokeType="OUTSIDE" strokeWidth="0.0" textAlignment="JUSTIFY" />
   <Text fx:id="commandTextSmall" strokeType="OUTSIDE" strokeWidth="0.0" textAlignment="JUSTIFY" />
   <Text fx:id="commandTextLarge" strokeType="OUTSIDE" strokeWidth="0.0" textAlignment="JUSTIFY" />
   <Text fx:id="commandTextXLarge" strokeType="OUTSIDE" strokeWidth="0.0" textAlignment="JUSTIFY" />
  <TextField fx:id="commandTextField" onAction="#handleCommandInputChanged" onKeyPressed="#handleKeyPress" onKeyReleased="#handleKeyReleased" promptText="Enter command here..." styleClass="text-field" StackPane.alignment="CENTER_LEFT" />
   <Label fx:id="checkBox" StackPane.alignment="CENTER_RIGHT">
      <padding>
         <Insets right="2.0" />
      </padding></Label>
</StackPane>
```
###### /resources/view/DarkTheme.css
``` css
#commandTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: monospace;
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#commandTextFieldKeyword {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #383838 #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: monospace;
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#commandTextDefault {
    -fx-font-family: monospace;
    -fx-font-size: 25;
    -fx-text-fill: white;
}

#commandTextXsmall{
    -fx-font-family: monospace;
    -fx-font-size: 12;
    -fx-text-fill: white;
}

#commandTextSmall{
    -fx-font-family: monospace;
    -fx-font-size: 17;
    -fx-text-fill: white;
}

#commandTextLarge{
    -fx-font-family: monospace;
    -fx-font-size: 32;
    -fx-text-fill: white;
}

#commandTextXLarge{
    -fx-font-family: monospace;
    -fx-font-size: 40;
    -fx-text-fill: white;
}


#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: derive(#282939, 20%);
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#lessonNode {
    -fx-font-family: monospace;
    -fx-font-size: large;
    -fx-font-weight: bolder;
    -fx-control-inner-background: #6B4A40;
    -fx-text-fill: derive(#d4cbb3, 50%);
    -fx-text-alignment: center;
}

#stickyNote {
    -fx-background-color: transparent;
    -fx-padding:5px 5px 5px;
    -fx-font:20px monospace;
    -fx-line-height:1.5;
}

#stickyNote .scroll-pane {
    -fx-background-color: transparent;
}

#stickyNote .scroll-pane .viewport {
    -fx-background-color: transparent;
}


#stickyNote .scroll-pane .content {
    -fx-background-color: transparent;
}

#header {
    -fx-text-fill: white;
    -fx-font-size: 20px;
}
```
