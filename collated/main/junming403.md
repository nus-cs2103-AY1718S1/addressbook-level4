# junming403
###### /java/seedu/address/commons/events/ui/RefreshPanelEvent.java
``` java
/**
 * Indicates the listingUnit in the personListPanel is changed.
 */
public class RefreshPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/commons/events/ui/RemarkChangedEvent.java
``` java
/**
 * An event indicates addressbook has changed.
 */
public class RemarkChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/ViewedLessonEvent.java
``` java
/**
 * Indicates a request to view a lesson on the existing lesson list
 */
public class ViewedLessonEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/AddCommand.java
``` java
/**
 * Adds a lesson to the ModU.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the ModU. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_CLASS_TYPE + "CLASS_TYPE "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_GROUP + "GROUP "
            + PREFIX_TIME_SLOT + "TIME_SLOT "
            + PREFIX_LECTURER + "Lecturer\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "MA1101R "
            + PREFIX_CLASS_TYPE + "LEC "
            + PREFIX_VENUE + "LT27 "
            + PREFIX_GROUP + "1 "
            + PREFIX_TIME_SLOT + "FRI[1400-1600] "
            + PREFIX_LECTURER + "Ma Siu Lun";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the ModU";
    public static final String MESSAGE_DUPLICATE_BOOKEDSLOT =
            "This time slot have already been booked in this location";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    private final Lesson toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyModule}
     */
    public AddCommand(ReadOnlyLesson lesson) {
        toAdd = new Lesson(lesson);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.bookingSlot(new BookedSlot(toAdd.getLocation(), toAdd.getTimeSlot()));
            model.addLesson(toAdd);
            model.handleListingUnit();
            EventsCenter.getInstance().post(new ViewedLessonEvent());
            logger.info("---[Add success]Added lesson: " + toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateLessonException e) {
            logger.info("---[Add failure]Duplicate lesson: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        } catch (DuplicateBookedSlotException s) {
            logger.info("---[Add failure]Duplicate time slot: " + toAdd.getTimeSlot());
            throw new CommandException(MESSAGE_DUPLICATE_BOOKEDSLOT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }


}
```
###### /java/seedu/address/logic/commands/ClearCommand.java
``` java
/**
 * Clears the ModU data.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ModU has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        Predicate predicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
        ListingUnit.setCurrentListingUnit(MODULE);
        ListingUnit.setCurrentPredicate(predicate);
        model.unbookAllSlot();
        model.resetData(new AddressBook());
        model.updateFilteredLessonList(predicate);
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        EventsCenter.getInstance().post(new RemarkChangedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
/**
 * Deletes a lesson/location/module identified using it's last displayed index from ModU.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "\n: Deletes the lesson(s) by module(if current listing element is module).\n"
            + ": Deletes the lesson(s) by location(if current listing element is location).\n"
            + ": Deletes the lesson(s) identified by the index (if current listing element is lesson).\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";
    public static final String MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS = "Deleted location: %1$s";
    public static final String MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS = "Deleted Module: %1$s";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        switch (ListingUnit.getCurrentListingUnit()) {

        case MODULE:
            return deleteLessonWithModuleCode(targetIndex);

        case LOCATION:
            return deleteLessonWithLocation(targetIndex);

        default:
            return deleteSpecifiedLesson(targetIndex);
        }
    }

    /**
     * Delete all lessons with specified location
     */
    private CommandResult deleteLessonWithLocation(Index targetIndex) {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();
        Location locationToDelete = lastShownList.get(targetIndex.getZeroBased()).getLocation();
        try {
            deleteLessonsWithLocation(locationToDelete);
            logger.info("---[Delete success]Deleted lessons with location:" + locationToDelete);
        } catch (LessonNotFoundException e) {
            assert false : "The target lesson cannot be missing";
        }

        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_WITH_LOCATION_SUCCESS, locationToDelete));
    }

    /**
     * Deletes all lessons with the given location.
     */
    private void deleteLessonsWithLocation(Location locationToDelete) throws LessonNotFoundException {
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        for (int i = 0; i < lessonList.size(); i++) {
            ReadOnlyLesson l = lessonList.get(i);
            if (l.getLocation().equals(locationToDelete)) {
                model.unbookBookedSlot(new BookedSlot(l.getLocation(), l.getTimeSlot()));
                model.deleteLesson(l);
                i--;
            }
        }
        model.updateLocationList();
    }

    /**
     * Delete all lessons with specified Module Code and all relevant remarks.
     */
    private CommandResult deleteLessonWithModuleCode(Index targetIndex) {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();
        Code moduleToDelete = lastShownList.get(targetIndex.getZeroBased()).getCode();
        try {

            deleteLessonsWithCode(moduleToDelete);
            deleteRemarkWithCode(moduleToDelete);
            logger.info("---[Delete success]Deleted lessons with module code:" + moduleToDelete);

        } catch (LessonNotFoundException e) {
            assert false : "The target lesson cannot be missing";
        } catch (RemarkNotFoundException e) {
            assert false : "The target remark cannot be missing";
        }

        EventsCenter.getInstance().post(new ViewedLessonEvent());
        EventsCenter.getInstance().post(new RemarkChangedEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_WITH_MODULE_SUCCESS, moduleToDelete));
    }

    /**
     * Deletes all lessons with the given module code.
     */
    private void deleteLessonsWithCode(Code moduleToDelete) throws LessonNotFoundException {
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        for (int i = 0; i < lessonList.size(); i++) {
            ReadOnlyLesson lesson = lessonList.get(i);
            if (lesson.getCode().equals(moduleToDelete)) {
                model.unbookBookedSlot(new BookedSlot(lesson.getLocation(), lesson.getTimeSlot()));
                model.deleteLesson(lesson);
                i--;
            }
        }
        model.updateModuleList();
    }

    /**
     * Deletes all remarks with the given module code.
     */
    private void deleteRemarkWithCode(Code moduleToDelete) throws RemarkNotFoundException {
        model.updateFilteredRemarkList(PREDICATE_SHOW_ALL_REMARKS);
        ObservableList<Remark> remarkList = model.getFilteredRemarkList();
        for (int i = 0; i < remarkList.size(); i++) {
            Remark remark = remarkList.get(i);
            if (remark.moduleCode.equals(moduleToDelete)) {
                model.deleteRemark(remark);
                i--;
            }
        }
    }


    /**
     * Delete all lessons with specified Module Code.
     */
    private CommandResult deleteSpecifiedLesson(Index targetIndex) {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();
        ReadOnlyLesson lessonToDelete = lastShownList.get(targetIndex.getZeroBased());
        try {
            model.unbookBookedSlot(new BookedSlot(lessonToDelete.getLocation(), lessonToDelete.getTimeSlot()));
            model.deleteLesson(lessonToDelete);
            logger.info("---[Delete success]Deleted lesson:" + lessonToDelete);
        } catch (LessonNotFoundException pnfe) {
            assert false : "The target lesson cannot be missing";
        }
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java

/**
 * Edits the details of an existing lesson/module/location in ModU.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE_LESSON = COMMAND_WORD + ": Edits the details of the lesson identified "
            + "by the index number used in the last lesson listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MODULE_CODE + "CODE] "
            + "[" + PREFIX_CLASS_TYPE + "CLASS TYPE] "
            + "[" + PREFIX_VENUE + "VENUE] "
            + "[" + PREFIX_GROUP + "GROUP] "
            + "[" + PREFIX_TIME_SLOT + "TIME SLOT] "
            + "[" + PREFIX_LECTURER + "LECTURER]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GROUP + "2 "
            + PREFIX_VENUE + "LT25";

    public static final String MESSAGE_USAGE_MODULE = COMMAND_WORD + ": Edits the module code identified "
            + "by the index number used in the last module listing. \n"
            + "ALl existing lessons with the module code will be overwritten by the input module code values.\n"
            + "Parameters: INDEX (must be a positive integer)  CODE\n"
            + "Example: " + COMMAND_WORD + " 1  CS1102";

    public static final String MESSAGE_USAGE_LOCATION = COMMAND_WORD + ": Edits the location identified "
            + "by the index number used in the last location listing.\n "
            + "ALl existing lessons with the location will be overwritten by the input location values.\n"
            + "Parameters: INDEX (must be a positive integer)  LOCATION\n"
            + "Example: " + COMMAND_WORD + " 1  LT31";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_EDIT_LOCATION_SUCCESS = "Edited Location: %1$s";
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_BOOKEDSLOT = "This time slot have already "
            + "been booked in this location";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    private final Index index;
    private final EditLessonDescriptor editLessonDescriptor;
    private final String attributeValue;
    private final boolean isEditingLesson;


    /**
     * Creates an edit command that will edit a specific lesson when executed.
     * @param index                of the lesson in the filtered lesson list to edit
     * @param editLessonDescriptor details to edit the lesson with
     */
    public EditCommand(Index index, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(editLessonDescriptor);

        this.index = index;
        this.editLessonDescriptor = new EditLessonDescriptor(editLessonDescriptor);
        this.attributeValue = null;
        this.isEditingLesson = true;
    }

    /**
     * Creates an edit command that will edit a set of lessons with some common attribute when executed.
     * @param index          of the element in the filtered list to edit
     * @param attributeValue the new edited attribute value
     */
    public EditCommand(Index index, String attributeValue) {
        requireNonNull(index);
        requireNonNull(attributeValue);

        this.index = index;
        this.editLessonDescriptor = null;
        this.attributeValue = attributeValue;
        this.isEditingLesson = false;

    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        switch (ListingUnit.getCurrentListingUnit()) {

        case LESSON:
            return executeEditLesson(lastShownList.get(index.getZeroBased()));

        case LOCATION:
            return executeEditLocation(lastShownList.get(index.getZeroBased()).getLocation());

        default:
            return executeEditModule(lastShownList.get(index.getZeroBased()).getCode());
        }
    }

    /**
     * Edit the lesson with updated information.
     */
    private CommandResult executeEditLesson(ReadOnlyLesson lessonToEdit) throws CommandException {
        Lesson editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);
        BookedSlot bookedSlotToEdit = new BookedSlot(lessonToEdit.getLocation(), lessonToEdit.getTimeSlot());
        BookedSlot editedBookedSlot = new BookedSlot(editedLesson.getLocation(), editedLesson.getTimeSlot());

        try {
            model.updateBookedSlot(bookedSlotToEdit, editedBookedSlot);
            if (lessonToEdit.isMarked()) {
                editedLesson.setAsMarked();
            }
            model.updateLesson(lessonToEdit, editedLesson);
            logger.info("---[Edit success]Edited Lesson: " + lessonToEdit + " ----> " + editedLesson);
        } catch (DuplicateLessonException dpe) {
            logger.info("---[Edit failure]Editing will result in duplicate lesson.");
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        } catch (LessonNotFoundException pnfe) {
            throw new AssertionError("The target lesson cannot be missing");
        } catch (DuplicateBookedSlotException s) {
            logger.info("---[Edit failure]Editing will result in duplicate time slot.");
            throw new CommandException(MESSAGE_DUPLICATE_BOOKEDSLOT);
        }
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, editedLesson));
    }


    /**
     * Edit the location, all lessons with the edited location is updated with a new given location.
     */
    private CommandResult executeEditLocation(Location locationToEdit) throws CommandException {
        try {
            Location editedLocation = new Location(attributeValue);
            updateLocation(locationToEdit, editedLocation);
            logger.info("---[Edit success]Edited location : " + locationToEdit + " ----> " + editedLocation);
            return new CommandResult(String.format(MESSAGE_EDIT_LOCATION_SUCCESS, editedLocation));
        } catch (DuplicateBookedSlotException s) {
            logger.info("---[Edit failure]Editing would results in duplicate time slot.");
            throw new CommandException(MESSAGE_DUPLICATE_BOOKEDSLOT);
        } catch (IllegalValueException ive) {
            logger.info("---???Edit failure???Editing illegal value.");
            model.updateLocationList();
            throw new CommandException(ive.getMessage());
        } catch (LessonNotFoundException pnfe) {
            throw new AssertionError("The target lesson cannot be missing");
        }
    }

    /**
     * Updates all lessons with location {@code locationToEdit} to {@code editedLocation}.
     */
    private void updateLocation(Location locationToEdit, Location editedLocation)
            throws DuplicateBookedSlotException, LessonNotFoundException, DuplicateLessonException {

        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        BookedSlot bookedSlotToEdit;
        BookedSlot editedBookedSlot;
        for (ReadOnlyLesson p : lessonList) {
            ReadOnlyLesson curEditedLesson;
            if (p.getLocation().equals(locationToEdit)) {
                curEditedLesson = new Lesson(p.getClassType(), editedLocation, p.getGroup(),
                        p.getTimeSlot(), p.getCode(), p.getLecturers());
                bookedSlotToEdit = new BookedSlot(p.getLocation(), p.getTimeSlot());
                editedBookedSlot = new BookedSlot(editedLocation, p.getTimeSlot());
                model.updateBookedSlot(bookedSlotToEdit, editedBookedSlot);
                model.updateLesson(p, curEditedLesson);
            }
        }
        model.updateLocationList();
    }


    /**
     * Edit the Module Code, all lessons with the edited code is updated with a new given code.
     */
    private CommandResult executeEditModule(Code codeToEdit) throws CommandException {
        try {
            Code editedCode = new Code(attributeValue);
            updateModuleCode(codeToEdit, editedCode);
            logger.info("---[Edit success]Edited module code : " + codeToEdit + " ----> " + editedCode);
            return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedCode));
        } catch (IllegalValueException ive) {
            logger.info("---???Edit failure???Editing illegal value.");
            model.updateModuleList();
            throw new CommandException(ive.getMessage());
        } catch (LessonNotFoundException pnfe) {
            throw new AssertionError("The target lesson cannot be missing");
        }

    }

    /**
     * Updates all lessons with module code {@code codeToEdit} to module code {@code editedCode}.
     */
    private void updateModuleCode(Code codeToEdit, Code editedCode)
            throws LessonNotFoundException, DuplicateLessonException {
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();

        for (ReadOnlyLesson p : lessonList) {

            ReadOnlyLesson curEditedLesson;
            if (p.getCode().equals(codeToEdit)) {
                curEditedLesson = new Lesson(p.getClassType(), p.getLocation(), p.getGroup(),
                        p.getTimeSlot(), editedCode, p.getLecturers());
                model.updateLesson(p, curEditedLesson);
            }
        }

        model.updateModuleList();
    }


    /**
     * Creates and returns a {@code Lesson} with the details of {@code lessonToEdit}
     * edited with {@code editLessonDescriptor}.
     */
    private Lesson createEditedLesson(ReadOnlyLesson lessonToEdit,
                                      EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null;

        Code updatedCode = editLessonDescriptor.getCode().orElse(lessonToEdit.getCode());
        ClassType updatedClassType = editLessonDescriptor.getClassType().orElse(lessonToEdit.getClassType());
        Group updatedGroup = editLessonDescriptor.getGroup().orElse(lessonToEdit.getGroup());
        Location updatedLocation = editLessonDescriptor.getLocation().orElse(lessonToEdit.getLocation());
        TimeSlot updatedTimeSlot = editLessonDescriptor.getTimeSlot().orElse(lessonToEdit.getTimeSlot());
        Set<Lecturer> updatedLecturers = editLessonDescriptor.getLecturers().orElse(lessonToEdit.getLecturers());

        return new Lesson(updatedClassType, updatedLocation, updatedGroup, updatedTimeSlot,
                updatedCode, updatedLecturers);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand e = (EditCommand) other;

        if (isEditingLesson != e.isEditingLesson) {
            return false;
        }

        if (isEditingLesson) {
            // state check
            return index.equals(e.index)
                    && editLessonDescriptor.equals(e.editLessonDescriptor);
        } else {
            return index.equals(e.index)
                    && attributeValue.equals(e.attributeValue);
        }
    }

    /**
     * Stores the details to edit the lesson with. Each non-empty field value will replace the
     * corresponding field value of the lesson.
     */
    public static class EditLessonDescriptor {
        private Code code;
        private ClassType classType;
        private Group group;
        private Location location;
        private TimeSlot timeSlot;
        private Set<Lecturer> lecturers;

        public EditLessonDescriptor() {
        }

        public EditLessonDescriptor(EditLessonDescriptor toCopy) {
            this.code = toCopy.code;
            this.classType = toCopy.classType;
            this.group = toCopy.group;
            this.location = toCopy.location;
            this.timeSlot = toCopy.timeSlot;
            this.lecturers = toCopy.lecturers;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.code, this.classType, this.group, this.location,
                    this.timeSlot, this.lecturers);
        }

        public void setCode(Code code) {
            this.code = code;
        }

        public Optional<Code> getCode() {
            return Optional.ofNullable(code);
        }

        public void setClassType(ClassType classType) {
            this.classType = classType;
        }

        public Optional<ClassType> getClassType() {
            return Optional.ofNullable(classType);
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public Optional<Group> getGroup() {
            return Optional.ofNullable(group);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setTimeSlot(TimeSlot timeSlot) {
            this.timeSlot = timeSlot;
        }

        public Optional<TimeSlot> getTimeSlot() {
            return Optional.ofNullable(timeSlot);
        }

        public void setLecturer(Set<Lecturer> lecturers) {
            this.lecturers = lecturers;
        }

        public Optional<Set<Lecturer>> getLecturers() {
            return Optional.ofNullable(lecturers);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLessonDescriptor)) {
                return false;
            }

            // state check
            EditLessonDescriptor e = (EditLessonDescriptor) other;

            return getClassType().equals(e.getClassType())
                    && getGroup().equals(e.getGroup())
                    && getCode().equals(e.getCode())
                    && getLocation().equals(e.getLocation())
                    && getTimeSlot().equals(e.getTimeSlot())
                    && getLecturers().equals(e.getLecturers());
        }
    }
}
```
###### /java/seedu/address/logic/commands/ListCommand.java
``` java
/**
 * Lists unique locations or unique module codes of all lessons according to user's specification.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all locations or all module codes and "
            + "displays them as a list with index numbers.\n"
            + "Parameters: module/location/marked\n"
            + "Example: " + COMMAND_WORD + " module";

    public static final String MESSAGE_SUCCESS = "Listed %1$s(s)";

    public static final String MODULE_KEYWORD = "module";
    public static final String LOCATION_KEYWORD = "location";
    public static final String MARKED_LIST_KEYWORD = "marked";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final String parameter;


    public ListCommand(String attributeName) {
        this.parameter = attributeName;
    }

    @Override
    public CommandResult execute() {

        switch (parameter) {
        case MODULE_KEYWORD :
            logger.info("---[List success]Switching Listing element to " + MODULE_KEYWORD);
            return executeListModule();
        case LOCATION_KEYWORD:
            logger.info("---[List success]Switching Listing element to " + LOCATION_KEYWORD);
            return executeListLocation();
        case MARKED_LIST_KEYWORD:
            logger.info("---[List success]Switching to marked lesson list");
            return executeListMarked();
        default:
            assert false : "There cannot be other parameters passed in";
            return null;
        }
    }

    /**
     * execute list by module.
     */
    private CommandResult executeListModule() {
        ListingUnit.setCurrentListingUnit(MODULE);
        UniqueModuleCodePredicate codePredicate = new UniqueModuleCodePredicate(model.getUniqueCodeSet());
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        ListingUnit.setCurrentPredicate(codePredicate);
        return executeListByAttribute(codePredicate);
    }

    /**
     * execute list by location.
     */
    private CommandResult executeListLocation() {
        ListingUnit.setCurrentListingUnit(LOCATION);
        UniqueLocationPredicate locationPredicate = new UniqueLocationPredicate(model.getUniqueLocationSet());
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        ListingUnit.setCurrentPredicate(locationPredicate);
        return executeListByAttribute(locationPredicate);
    }

    /**
     * execute list all marked lessons.
     */
    private CommandResult executeListMarked() {
        ListingUnit.setCurrentListingUnit(LESSON);
        MarkedListPredicate markedListPredicate = new MarkedListPredicate();
        model.setViewingPanelAttribute(MARKED_LIST_KEYWORD);
        ListingUnit.setCurrentPredicate(markedListPredicate);
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return executeListByAttribute(markedListPredicate);
    }

    /**
     * execute the list command with different attributes.
     */
    private CommandResult executeListByAttribute(Predicate predicate) {
        model.updateFilteredLessonList(predicate);
        if (predicate instanceof MarkedListPredicate) {
            EventsCenter.getInstance().post(new ViewedLessonEvent());
        }
        EventsCenter.getInstance().post(new RefreshPanelEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, parameter));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ListCommand)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        ListCommand o = (ListCommand) other;
        if (parameter != null && o.parameter != null) {
            return parameter.equals(o.parameter);
        }

        return false;
    }
}
```
###### /java/seedu/address/logic/commands/MarkCommand.java
``` java
/**
 * Bookmark a lesson identified using it's last displayed index from the address book into the favourite list.
 */
public class MarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds the lesson into marked list identified by the index number used in the last lesson listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_BOOKMARK_LESSON_SUCCESS = "Marked Lesson:  %1$s";
    public static final String MESSAGE_WRONG_LISTING_UNIT_FAILURE = "You can only add lesson into marked list";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    private final Index targetIndex;

    public MarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyLesson lessonToCollect = lastShownList.get(targetIndex.getZeroBased());

        if (ListingUnit.getCurrentListingUnit().equals(LESSON)) {
            try {
                model.bookmarkLesson(lessonToCollect);
                logger.info("---[Mark success]Bookmark lesson" + lessonToCollect);
                EventsCenter.getInstance().post(new RefreshPanelEvent());
            } catch (DuplicateLessonException pnfe) {
                logger.info("---[Mark failure]Lesson already in marked list:" + lessonToCollect);
                throw new CommandException(pnfe.getMessage());
            }
            return new CommandResult(String.format(MESSAGE_BOOKMARK_LESSON_SUCCESS, lessonToCollect));
        } else {
            throw new CommandException(MESSAGE_WRONG_LISTING_UNIT_FAILURE);
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((MarkCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/RedoCommand.java
``` java
/**
 * Redo the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        if (!undoRedoStack.canRedo()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        undoRedoStack.popRedo().redo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
```
###### /java/seedu/address/logic/commands/RemarkCommand.java
``` java
/**
 * Add a remark to a module with specified index.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_SAMPLE_REMARK_INFORMATION = "The module CS2103T introduces the "
            + "necessary conceptual and analytical tools for systematic and rigorous development of software systems.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remark the module with some supplementary "
            + "information.\n "
            + "Parameters: INDEX (must be a positive integer) "
            + "[ADDITIONAL INFORMATION]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + MESSAGE_SAMPLE_REMARK_INFORMATION + "\n"
            + COMMAND_WORD + " -d: Delete the remark with specified index "
            + "Parameters: INDEX (must be a positive integer)..\n"
            + "Example: " + COMMAND_WORD + " -d 1 " + "\n";

    public static final String MESSAGE_REMARK_MODULE_SUCCESS = "Remarked Module: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Deleted Remark: %1$s";
    public static final String MESSAGE_WRONG_LISTING_UNIT_FAILURE = "You can only remark a module";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    private final String remarkContent;
    private final Index index;
    private final boolean isDelete;

    /**
     * @param index       of the module in the module list to remark.
     * @param content the new remark content.
     */
    public RemarkCommand(Index index, String content) {
        requireNonNull(index);
        requireNonNull(content);

        this.remarkContent = content;
        this.index = index;
        this.isDelete = false;
    }

    /**
     * @param index       of the remark in the remark list to delete.
     */
    public RemarkCommand(Index index) {
        requireNonNull(index);

        this.remarkContent = null;
        this.index = index;
        this.isDelete = true;
    }



    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (isDelete) {
            return executeDeleteRemark();
        } else {
            return executeAddRemark();
        }
    }

    /**
     * delete the remark with given index.
     */
    public CommandResult executeDeleteRemark() throws CommandException {
        List<Remark> lastShownList = model.getFilteredRemarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Remark remarkToDelete = lastShownList.get(index.getZeroBased());

        if (ListingUnit.getCurrentListingUnit().equals(MODULE)) {
            try {
                model.deleteRemark(remarkToDelete);
                logger.info("---[Remark deleting success]Deleted remark:" + remarkToDelete);
            } catch (RemarkNotFoundException e) {
                logger.info("---[Remark deleting failure]The remark to delete does not exist: " + remarkContent);
                throw new CommandException(e.getMessage());
            }
            EventsCenter.getInstance().post(new RemarkChangedEvent());
            return new CommandResult(String.format(MESSAGE_DELETE_REMARK_SUCCESS, remarkToDelete));
        } else {
            throw new CommandException(MESSAGE_WRONG_LISTING_UNIT_FAILURE);
        }
    }

    /**
     * Adds in the remark.
     */
    public CommandResult executeAddRemark() throws CommandException {
        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyLesson moduleToRemark = lastShownList.get(index.getZeroBased());

        if (ListingUnit.getCurrentListingUnit().equals(MODULE)) {
            try {
                Remark remark = new Remark(remarkContent, moduleToRemark.getCode());
                model.addRemark(remark);
                logger.info("---[Remark success]Added Remark: " + remark);
            } catch (DuplicateRemarkException e) {
                logger.info("---[Remark failure]Duplicate Remark: " + remarkContent);
                throw new CommandException(e.getMessage());
            } catch (IllegalValueException e) {
                logger.info("---[Remark failure]Invalid Remark" + remarkContent);
                throw new CommandException(e.getMessage());
            }
            EventsCenter.getInstance().post(new RemarkChangedEvent());
            return new CommandResult(String.format(MESSAGE_REMARK_MODULE_SUCCESS, moduleToRemark.getCode()));
        } else {
            throw new CommandException(MESSAGE_WRONG_LISTING_UNIT_FAILURE);
        }
    }


    @Override
    public boolean equals(Object other) {

        if (isDelete != ((RemarkCommand) other).isDelete) {
            return false;
        }

        if (isDelete) {
            return other == this // short circuit if same object
                    || (other instanceof RemarkCommand // instanceof handles nulls
                    && index.equals((((RemarkCommand) other).index)));
        } else {
            return other == this // short circuit if same object
                    || (other instanceof RemarkCommand // instanceof handles nulls
                    && remarkContent.equals(((RemarkCommand) other).remarkContent)
                    && index.equals((((RemarkCommand) other).index)));
        }
    }
}
```
###### /java/seedu/address/logic/commands/SelectCommand.java
``` java
/**
 * Selects a module/location identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the module/location identified by the index number used in the last listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_LESSON_SUCCESS = "Selected: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        if (ListingUnit.getCurrentListingUnit().equals(ListingUnit.MODULE)) {
            Code code = lastShownList.get(targetIndex.getZeroBased()).getCode();
            model.updateFilteredRemarkList(new SelectedStickyNotePredicate(code));
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_LESSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/UnmarkCommand.java
``` java
/**
 * Unbookmark a lesson identified using it's last displayed index from the address book into the favourite list.
 */
public class UnmarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": removes the lesson into marked list identified by the index number used in the last lesson listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNBOOKMARK_LESSON_SUCCESS = "Unmarked Lesson:  %1$s";
    public static final String MESSAGE_WRONG_LISTING_UNIT_FAILURE = "You can only remove lesson from marked list";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    private final Index targetIndex;

    public UnmarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyLesson lessonToUnbookmark = lastShownList.get(targetIndex.getZeroBased());

        if (ListingUnit.getCurrentListingUnit().equals(LESSON)) {
            try {
                model.unBookmarkLesson(lessonToUnbookmark);
                updateLessonList();
                logger.info("---[Unmark success]Unbookmarked lesson:" + lessonToUnbookmark);
                EventsCenter.getInstance().post(new RefreshPanelEvent());
                EventsCenter.getInstance().post(new ViewedLessonEvent());
                return new CommandResult(String.format(MESSAGE_UNBOOKMARK_LESSON_SUCCESS, lessonToUnbookmark));

            } catch (NotRemarkedLessonException e) {
                logger.info("---[Unmark failure]The lesson to unbookmark is not in the marked list:"
                        + lessonToUnbookmark);
                throw new CommandException(e.getMessage());
            }
        } else {
            throw new CommandException(MESSAGE_WRONG_LISTING_UNIT_FAILURE);
        }
    }

    /**
     * Update lesson list according to current predicate.
     */
    private void updateLessonList() {
        Predicate predicate = ListingUnit.getCurrentPredicate();
        if (ListingUnit.getCurrentPredicate() instanceof MarkedListPredicate) {
            model.updateFilteredLessonList(new MarkedListPredicate());
        } else if (ListingUnit.getCurrentPredicate() instanceof MarkedLessonContainsKeywordsPredicate) {
            List<String> keywords = ((MarkedLessonContainsKeywordsPredicate) predicate).getKeywords();
            model.updateFilteredLessonList(new MarkedLessonContainsKeywordsPredicate(keywords));
        }
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmarkCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnmarkCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ViewCommand.java
``` java
/**
 * Views all persons with the selected listing unit from the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views all lessons with the selected listing attribute from the address book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_LOCATION_SUCCESS = "Lesson(s) founded with location %1$s.";
    public static final String MESSAGE_VIEW_MODULE_SUCCESS = "Lesson(s) founded with module code %1$s.";
    public static final String MESSAGE_VIEW_LESSON_FAILURE = "You can only view module or location.";
    public static final String VIEWING_ATTRIBUTE_MODULE = "module";
    public static final String VIEWING_ATTRIBUTE_DEFAULT = "default";
    public static final String VIEWING_ATTRIBUTE_LOCATION = "location";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyLesson toView = lastShownList.get(targetIndex.getZeroBased());

        setViewingPanelAttribute();
        model.setCurrentViewingLesson(toView);

        String resultMessage = updateFilterList(toView);

        EventsCenter.getInstance().post(new RefreshPanelEvent());
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(resultMessage);
    }

    /***
     * Update the filterList that only returns lesson with the same location or module name
     * base in the current listing unit
     */
    private String updateFilterList(ReadOnlyLesson toView) throws CommandException {

        Predicate predicate;
        String result;

        switch (getCurrentListingUnit()) {

        case LOCATION:
            logger.info("---[View success]Viewing lessons with location " + toView.getLocation());
            predicate = new FixedLocationPredicate(toView.getLocation());
            result = String.format(MESSAGE_VIEW_LOCATION_SUCCESS, toView.getLocation());
            break;

        case MODULE:
            predicate = new FixedCodePredicate(toView.getCode());
            logger.info("---[View success]Viewing lessons with module code " + toView.getCode());
            result = String.format(MESSAGE_VIEW_MODULE_SUCCESS, toView.getCode());
            break;

        default:
            logger.info("---[View success]Viewing failed, invalid listing unit: LESSON");
            throw new CommandException(MESSAGE_VIEW_LESSON_FAILURE);
        }

        ListingUnit.setCurrentPredicate(predicate);
        ListingUnit.setCurrentListingUnit(LESSON);
        model.updateFilteredLessonList(predicate);
        return result;
    }

    public void setViewingPanelAttribute() {
        switch (getCurrentListingUnit()) {
        case MODULE:
            model.setViewingPanelAttribute(VIEWING_ATTRIBUTE_MODULE);
            break;
        case LOCATION:
            model.setViewingPanelAttribute(VIEWING_ATTRIBUTE_LOCATION);
            break;
        default:
            model.setViewingPanelAttribute(VIEWING_ATTRIBUTE_DEFAULT);
            break;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.targetIndex.equals(((ViewCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObservableList<Remark> getFilteredRemarkList() {
        return model.getFilteredRemarkList();
    }

    @Override
    public void setRemarkPredicate(Predicate predicate) {
        model.updateFilteredRemarkList(predicate);
    }
```
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Pattern FIRST_INT_PATTERN = Pattern.compile("^(\\d+)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditCommand parse(String args) throws ParseException {

        if (ListingUnit.getCurrentListingUnit().equals(LESSON)) {
            return parseEditLesson(args);
        } else {
            return parseEditAttribute(args);
        }
    }

    /**
     * Parse the input arguments given the current listing unit is Lesson.
     */
    public EditCommand parseEditLesson(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASS_TYPE, PREFIX_VENUE, PREFIX_GROUP, PREFIX_TIME_SLOT,
                        PREFIX_MODULE_CODE, PREFIX_LECTURER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, getEditCommandUsage()));
        }

        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        try {
            ParserUtil.parseClassType(argMultimap.getValue(PREFIX_CLASS_TYPE))
                    .ifPresent(editLessonDescriptor::setClassType);
            ParserUtil.parseLocation(argMultimap.getValue(PREFIX_VENUE))
                    .ifPresent(editLessonDescriptor::setLocation);
            ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP))
                    .ifPresent(editLessonDescriptor::setGroup);
            ParserUtil.parseTimeSlot(argMultimap.getValue(PREFIX_TIME_SLOT))
                    .ifPresent(editLessonDescriptor::setTimeSlot);
            ParserUtil.parseCode(argMultimap.getValue(PREFIX_MODULE_CODE))
                    .ifPresent(editLessonDescriptor::setCode);

            if (argMultimap.getValue(PREFIX_LECTURER).isPresent()
                    && argMultimap.getValue(PREFIX_LECTURER).get().isEmpty()) {
                throw new IllegalValueException(Lecturer.MESSAGE_LECTURER_CONSTRAINTS);
            }

            parseLecturersForEdit(argMultimap.getAllValues(PREFIX_LECTURER))
                    .ifPresent(editLessonDescriptor::setLecturer);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editLessonDescriptor);
    }

    /**
     * Parse the input arguments of given new attribute value
     */
    public EditCommand parseEditAttribute(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        Index index;
        String attributeValue;
        Matcher matcher = FIRST_INT_PATTERN.matcher(trimmedArgs);

        try {
            if (matcher.find()) {
                index = ParserUtil.parseIndex(matcher.group(0));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, getEditCommandUsage()));
            }

            attributeValue = trimmedArgs.substring(matcher.group(0).length()).trim();

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, getEditCommandUsage()));
        }

        return new EditCommand(index, attributeValue);
    }


    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Lecturer>} if {@code lecturers} is non-empty.
     * If {@code lecturer} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Lecturer>} containing zero tags.
     */
    private Optional<Set<Lecturer>> parseLecturersForEdit(Collection<String> lecturers) throws IllegalValueException {
        assert lecturers != null;

        if (lecturers.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> lecturersSet = lecturers.size() == 1
                && lecturers.contains("") ? Collections.emptySet() : lecturers;
        return Optional.of(ParserUtil.parseLecturer(lecturersSet));
    }

    /**
     * Get usage for edit command for current listing panel.
     */
    private String getEditCommandUsage() {
        switch (ListingUnit.getCurrentListingUnit()) {
        case LESSON:
            return EditCommand.MESSAGE_USAGE_LESSON;
        case LOCATION:
            return EditCommand.MESSAGE_USAGE_LOCATION;
        default:
            return EditCommand.MESSAGE_USAGE_MODULE;
        }
    }
}
```
###### /java/seedu/address/logic/parser/ListCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (isValidAttribute(trimmedArgs)) {
            return new ListCommand(trimmedArgs);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

    }

    private boolean isValidAttribute(String args) {
        return args.equals(ListCommand.LOCATION_KEYWORD) || args.equals(ListCommand.MODULE_KEYWORD)
                || args.equals(ListCommand.MARKED_LIST_KEYWORD);
    }

}
```
###### /java/seedu/address/logic/parser/MarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MarkCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BookmarkCommand
     * and returns an BookmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    private static final Pattern DELETE_INDEX_PATTERN = Pattern.compile("-d\\s*(\\d+)");
    private static final Pattern FIRST_INT_PATTERN = Pattern.compile("^(\\d+)");

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Index index;
        String remarkContent;
        Matcher matcherFirstInt = FIRST_INT_PATTERN.matcher(trimmedArgs);
        Matcher matcherDeleteRmk = DELETE_INDEX_PATTERN.matcher(trimmedArgs);

        try {

            if (matcherDeleteRmk.find()) {
                index = ParserUtil.parseIndex(matcherDeleteRmk.group(1));
                return new RemarkCommand(index);
            }

            if (matcherFirstInt.find()) {
                index = ParserUtil.parseIndex(matcherFirstInt.group(0));
                remarkContent = trimmedArgs.substring(matcherFirstInt.group(0).length()).trim();
                return new RemarkCommand(index, remarkContent);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
            }


        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

    }
}
```
###### /java/seedu/address/logic/parser/UnmarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnmarkCommand object
 */
public class UnmarkCommandParser implements Parser<UnmarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnBookmarkCommand
     * and returns an UnBookmarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnmarkCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/ViewCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/UndoRedoStack.java
``` java
    public void push(Command command) {
        if (!(command instanceof UndoCommand) && !(command instanceof RedoCommand)) {
            redoStack.clear();
        }

        if (!(command instanceof UndoableCommand)) {

            if ((command instanceof ListCommand) || (command instanceof ViewCommand)) {
                undoStack.clear();
            }
            return;
        }

        undoStack.add((UndoableCommand) command);
    }

    /**
     * Pops and returns the next {@code UndoableCommand} to be undone in the stack.
     */
    public UndoableCommand popUndo() {
        UndoableCommand toUndo = undoStack.pop();
        if (toUndo.canRedo()) {
            redoStack.push(toUndo);
        }
        return toUndo;
    }

    /**
     * Pops and returns the next {@code UndoableCommand} to be redone in the stack.
     */
    public UndoableCommand popRedo() {
        UndoableCommand toRedo = redoStack.pop();
        undoStack.push(toRedo);
        return toRedo;
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setRemarks(Set<Remark> remarks) {
        this.remarks.setRemarks(remarks);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Adds a lesson to the marked list.
     * Only lesson exists in the ModU can be added into the marked list.
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
     * Removes a lesson from the marked list.
     * Only lesson exists in the marked List can be unbookmarked from the marked list.
     */
    public void unBookmarkLesson(ReadOnlyLesson m) throws NotRemarkedLessonException {
        if (m.isMarked()) {
            m.setAsUnmarked();
        } else {
            throw new NotRemarkedLessonException();
        }
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    //// remark-level operations
    public void addRemark(Remark r) throws DuplicateRemarkException {
        remarks.add(r);
    }

    public void updateRemark(Remark target, Remark editedRemark) throws
            DuplicateRemarkException, RemarkNotFoundException {
        remarks.setRemark(target, editedRemark);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws RemarkNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeRemark(Remark key) throws RemarkNotFoundException {
        if (remarks.remove(key)) {
            return true;
        } else {
            throw new RemarkNotFoundException();
        }
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    @Override
    public ObservableList<Remark> getRemarkList() {
        return remarks.asObservableList();
    }
```
###### /java/seedu/address/model/lecturer/Lecturer.java
``` java
public class Lecturer {

    public static final String MESSAGE_LECTURER_CONSTRAINTS = "Lecturer names should be alphabetic";
    public static final String LECTURER_VALIDATION_REGEX = "[^\\s].*";

    public final String lecturerName;

    /**
     * Validates given Lecturer name.
     *
     * @throws IllegalValueException if the given lecturer name string is invalid.
     */
    public Lecturer(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidLecturerName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_LECTURER_CONSTRAINTS);
        }
        this.lecturerName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid Lecturer name.
     */
    public static boolean isValidLecturerName(String test) {
        return test.matches(LECTURER_VALIDATION_REGEX) && !test.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Lecturer // instanceof handles nulls
                && this.lecturerName.equals(((Lecturer) other).lecturerName)); // state check
    }

    @Override
    public int hashCode() {
        return lecturerName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + lecturerName + ']';
    }

}
```
###### /java/seedu/address/model/lecturer/UniqueLecturerList.java
``` java
public class UniqueLecturerList implements Iterable<Lecturer> {

    private final ObservableList<Lecturer> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty LecturerList.
     */
    public UniqueLecturerList() {}

    /**
     * Creates a UniqueLecturerList using given lecturers.
     * Enforces no nulls.
     */
    public UniqueLecturerList(Set<Lecturer> lecturers) {
        requireAllNonNull(lecturers);
        internalList.addAll(lecturers);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all lecturers in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Lecturer> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the lecturers in this list with those in the argument Lecturer list.
     */
    public void setLectuers(Set<Lecturer> lectuers) {
        requireAllNonNull(lectuers);
        internalList.setAll(lectuers);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every Lecturer in the argument list exists in this object.
     */
    public void mergeFrom(UniqueLecturerList from) {
        final Set<Lecturer> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(lecturer -> !alreadyInside.contains(lecturer))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Lecturer as the given argument.
     */
    public boolean contains(Lecturer toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Lecturer to the list.
     *
     * @throws DuplicateLecturerException if the Lecturer to add is a duplicate of an existing Lecturer in the list.
     */
    public void add(Lecturer toAdd) throws DuplicateLecturerException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLecturerException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Lecturer> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lecturer> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueLecturerList // instanceof handles nulls
                        && this.internalList.equals(((UniqueLecturerList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueLecturerList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateLecturerException extends DuplicateDataException {
        protected DuplicateLecturerException() {
            super("Operation would result in duplicate lecturers");
        }
    }

}
```
###### /java/seedu/address/model/ListingUnit.java
``` java
/**
 * A Enumeration class that consists of all possible Listing
 * Unit in the panel.
 */
public enum ListingUnit {
    MODULE, LOCATION, LESSON;

    private static ListingUnit currentListingUnit = MODULE;
    private static Predicate currentPredicate;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    /**
     * Get current Listing unit
     */
    public static ListingUnit getCurrentListingUnit() {
        return currentListingUnit;
    }

    /**
     * Reset listing unit in the panel with the new ListingUnit and set previous listing unit
     */
    public static void setCurrentListingUnit(ListingUnit unit) {
        logger.info("---Set current listing unit to: " + unit);
        currentListingUnit = unit;
    }

    /**
     * Get current predicate
     */
    public static Predicate getCurrentPredicate() {
        return currentPredicate;
    }

    /**
     * Set current predicate
     */
    public static void setCurrentPredicate(Predicate predicate) {
        logger.info("---Set current predicate to: " + predicate.getClass().getSimpleName());
        currentPredicate = predicate;
    }

}
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Get a hash set of all the distinct locations
     */
    HashSet<Location> getUniqueLocationSet();

    /**
     * Get a hash set of all the distinct module codes
     */
    HashSet<Code> getUniqueCodeSet();
```
###### /java/seedu/address/model/Model.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java
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
```
###### /java/seedu/address/model/ModelManager.java
``` java
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

```
###### /java/seedu/address/model/module/exceptions/DuplicateLessonException.java
``` java
/**
 * Signals that the operation will result in duplicate Lesson objects.
 */
public class DuplicateLessonException extends DuplicateDataException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lesson");
    }
}
```
###### /java/seedu/address/model/module/exceptions/DuplicateRemarkException.java
``` java
/**
 * Signals that the operation will result in duplicate Remark objects.
 */
public class DuplicateRemarkException extends DuplicateDataException {
    public DuplicateRemarkException() {
        super("Operation would result in duplicate remark");
    }
}
```
###### /java/seedu/address/model/module/exceptions/LessonNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified lesson.
 */
public class LessonNotFoundException extends Exception {}
```
###### /java/seedu/address/model/module/exceptions/NotRemarkedLessonException.java
``` java
/**
 * Signals that the lesson want to unmark is not in the marked list.
 */
public class NotRemarkedLessonException extends Exception {
    public NotRemarkedLessonException() {
        super("The lesson is not in the remarked list");
    }
}
```
###### /java/seedu/address/model/module/exceptions/RemarkNotFoundException.java
``` java
/**
 * Indicates the remark cannot be found.
 */
public class RemarkNotFoundException extends Exception {}
```
###### /java/seedu/address/model/module/predicates/FixedCodePredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyLesson}'s {@code location} matches the given module code.
 */
public class FixedCodePredicate implements Predicate<ReadOnlyLesson> {
    private final Code codeTotest;

    public FixedCodePredicate(Code code) {
        this.codeTotest = code;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return lesson.getCode().equals(codeTotest);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedCodePredicate // instanceof handles nulls
                && this.codeTotest.equals(((FixedCodePredicate) other).codeTotest)); // state check
    }

}
```
###### /java/seedu/address/model/module/predicates/FixedLocationPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyLesson}'s {@code location} matches the given location.
 */
public class FixedLocationPredicate implements Predicate<ReadOnlyLesson> {
    private final Location locationToTest;

    public FixedLocationPredicate(Location location) {
        this.locationToTest = location;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return lesson.getLocation().equals(locationToTest);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FixedLocationPredicate // instanceof handles nulls
                && this.locationToTest.equals(((FixedLocationPredicate) other).locationToTest)); // state check
    }

}
```
###### /java/seedu/address/model/module/predicates/MarkedListPredicate.java
``` java
/**
 * Tests that if a {@code ReadOnlyLesson} if in the marked list.
 */
public class MarkedListPredicate implements Predicate<ReadOnlyLesson> {

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return lesson.isMarked();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkedListPredicate); // instanceof handles nulls
    }

}
```
###### /java/seedu/address/model/module/predicates/SelectedStickyNotePredicate.java
``` java
/**
 * Tests that a {@code Remark}'s {@code moduleCode} matches the given module code.
 */
public class SelectedStickyNotePredicate implements Predicate<Remark> {
    private final Code codeTotest;

    public SelectedStickyNotePredicate(Code code) {
        this.codeTotest = code;
    }

    @Override
    public boolean test(Remark remark) {
        return remark.moduleCode.equals(codeTotest);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectedStickyNotePredicate // instanceof handles nulls
                && this.codeTotest.equals(((SelectedStickyNotePredicate) other).codeTotest)); // state check
    }

}
```
###### /java/seedu/address/model/module/predicates/ShowSpecifiedLessonPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyLesson} matches the given lesson.
 */
public class ShowSpecifiedLessonPredicate implements Predicate<ReadOnlyLesson> {
    private final ReadOnlyLesson lesson;

    public ShowSpecifiedLessonPredicate(ReadOnlyLesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        return this.lesson.isSameStateAs(lesson);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowSpecifiedLessonPredicate // instanceof handles nulls
                && this.lesson.isSameStateAs((((ShowSpecifiedLessonPredicate) other).lesson))); // state check
    }

}
```
###### /java/seedu/address/model/module/predicates/UniqueLocationPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} is unique in the given list.
 */
public class UniqueLocationPredicate implements Predicate<ReadOnlyLesson> {
    private final HashSet<Location> uniqueLocationSet;

    public UniqueLocationPredicate(HashSet<Location> locationSet) {
        this.uniqueLocationSet = locationSet;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        if (uniqueLocationSet.contains(lesson.getLocation())) {
            uniqueLocationSet.remove(lesson.getLocation());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLocationPredicate // instanceof handles nulls
                && this.uniqueLocationSet.equals(((UniqueLocationPredicate) other).uniqueLocationSet)); // state check
    }

}
```
###### /java/seedu/address/model/module/predicates/UniqueModuleCodePredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Code} is unique in the given list.
 */
public class UniqueModuleCodePredicate implements Predicate<ReadOnlyLesson> {
    private final HashSet<Code> uniqueCodeSet;

    public UniqueModuleCodePredicate(HashSet<Code> codeSet) {
        this.uniqueCodeSet = codeSet;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {
        if (uniqueCodeSet.contains(lesson.getCode())) {
            uniqueCodeSet.remove(lesson.getCode());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleCodePredicate // instanceof handles nulls
                && this.uniqueCodeSet.equals(((UniqueModuleCodePredicate) other).uniqueCodeSet)); // state check
    }

}
```
###### /java/seedu/address/model/module/Remark.java
``` java
/**
 * Represents a Module's remark(if any) in the application.
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remark can only be no more than 150 characters and cannot be empty";

    public static final int REMARK_LENGTH_LIMIT = 150;

    public final String value;
    public final Code moduleCode;


    /**
     * Validates given remark content.
     *
     * @throws IllegalValueException if given group string is invalid.
     */
    public Remark(String remark, Code module) throws IllegalValueException {
        requireNonNull(remark);
        if (!isValidRemark(remark)) {
            throw new IllegalValueException(MESSAGE_REMARK_CONSTRAINTS);
        }
        value = remark;
        moduleCode = module;
    }

    /**
     * Creates a copy of the given Remark.
     */
    public Remark(Remark source) {
        value = source.value;
        moduleCode = source.moduleCode;
    }

    /**
     * Returns true if a given string is a valid Remark.
     */
    public static boolean isValidRemark(String test) {
        test = test.trim();
        return test.length() <= REMARK_LENGTH_LIMIT && test.length() > 0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)
                && this.moduleCode.equals(((Remark) other).moduleCode)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### /java/seedu/address/model/module/UniqueRemarkList.java
``` java
/**
 * A list of remarks that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Remark#equals(Object)
 */
public class UniqueRemarkList implements Iterable<Remark> {

    private final ObservableList<Remark> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty LecturerList.
     */
    public UniqueRemarkList() {}

    /**
     * Creates a UniqueLecturerList using given remarks.
     * Enforces no nulls.
     */
    public UniqueRemarkList(Set<Remark> remarks) {
        requireAllNonNull(remarks);
        internalList.addAll(remarks);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all remarks in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Remark> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the remarks in this list with those in the argument Remark list.
     */
    public void setRemarks(Set<Remark> remarks) {
        requireAllNonNull(remarks);
        internalList.setAll(remarks);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces the remark {@code target} in the list with {@code editedRemark}.
     *
     * @throws DuplicateRemarkException if the replacement is equivalent to another existing lesson in the list.
     * @throws RemarkNotFoundException if {@code target} could not be found in the list.
     */
    public void setRemark(Remark target, Remark editedRemark)
            throws DuplicateRemarkException, RemarkNotFoundException {
        requireNonNull(editedRemark);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RemarkNotFoundException();
        }

        if (!target.equals(editedRemark) && internalList.contains(editedRemark)) {
            throw new DuplicateRemarkException();
        }

        internalList.set(index, new Remark(editedRemark));
    }


    /**
     * Returns true if the list contains an equivalent remark as the given argument.
     */
    public boolean contains(Remark toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Remark to the list.
     *
     * @throws DuplicateRemarkException if the Lecturer to add is a duplicate of an existing Remark in the list.
     */
    public void add(Remark toAdd) throws DuplicateRemarkException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRemarkException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes the equivalent remark from the list.
     *
     * @throws RemarkNotFoundException if no such remark could be found in the list.
     */
    public boolean remove(Remark toRemove) throws RemarkNotFoundException {
        requireNonNull(toRemove);
        final boolean remarkFoundAndDeleted = internalList.remove(toRemove);
        if (!remarkFoundAndDeleted) {
            throw new RemarkNotFoundException();
        }
        return remarkFoundAndDeleted;
    }

    @Override
    public Iterator<Remark> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Remark> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueRemarkList // instanceof handles nulls
                && this.internalList.equals(((UniqueRemarkList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueRemarkList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }


}
```
###### /java/seedu/address/model/ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the remarks list.
     * This list will not contain any duplicate remarks.
     */
    ObservableList<Remark> getRemarkList();
```
###### /java/seedu/address/storage/XmlAdaptedRemark.java
``` java
/**
 * Stores remark data in an XML file
 */
public class XmlAdaptedRemark {

    @XmlElement(required = true)
    private String content;

    @XmlElement(required = true)
    private String code;

    /**
     * Constructs an XmlAdaptedRemark.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRemark() {}

    /**
     * Converts a given Remark into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedRemark(Remark source) {
        content = source.value;
        code = source.moduleCode.fullCodeName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Remark object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Remark
     */
    public Remark toModelType() throws IllegalValueException {
        final Code code = new Code(this.code);
        return new Remark(content, code);
    }

}
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Remark> getRemarkList() {
        final ObservableList<Remark> remarks = this.remarks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(remarks);
    }

}
```
###### /java/seedu/address/ui/CombinePanel.java
``` java
    @Subscribe
    private void handleRemarkChangedEvent(RemarkChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stickyNotesInit();
    }

    /**
     * This method will initilize the data for sticky notes screen
     */
    public void noteDataInit() {
        ObservableList<Remark> remarks = logic.getFilteredRemarkList();
        int size = remarks.size();
        int count = 0;
        int index = 1;

        //Only display 9 notes, so 3 x 3 matrix
        noteData = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (count >= size) {
                    continue;
                }
                Remark remark = remarks.get(count);
                if (count < size) {
                    noteData[i][j] = index + "." + remark.moduleCode.fullCodeName + " : " + remark.value;
                    index++;
                    count++;
                }
            }
        }
    }
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Configure border colour to indicate validity of user input.
     */
    private void configBorderColor(String allTextInput) {
        checkBox.setVisible(true);
        try {
            tester.parseCommand(allTextInput);
            commandTextField.setStyle(userPrefFontSize + "-fx-border-color: green; -fx-border-width: 2");
            checkBox.setGraphic(tick);
            checkBox.toFront();
            checkBox.setVisible(true);
        } catch (ParseException e) {
            commandTextField.setStyle(userPrefFontSize + "-fx-border-color: red; -fx-border-width: 2");
            checkBox.setGraphic(cross);
            checkBox.toFront();
            checkBox.setVisible(true);
        }
    }
```
###### /java/seedu/address/ui/LessonListCard.java
``` java
    /**
     * Change the card state to hide irrelevant information and only show Module Code
     */
    private void switchToModuleCard() {
        code.setVisible(true);
        venue.setVisible(false);
        group.setVisible(false);
        timeSlot.setVisible(false);
        classType.setVisible(false);
        lecturers.setVisible(false);
    }

    /**
     * Change the card state to hide irrelevant information and only show Location
     */
    private void switchToLocationCard() {
        code.setVisible(false);
        venue.setVisible(true);
        group.setVisible(false);
        timeSlot.setVisible(false);
        classType.setVisible(false);
        lecturers.setVisible(false);
        bookmark.setVisible(false);

    }


    /**
     * Change the card state to hide irrelevant information and only show lesson
     */
    private void switchToLessonCard() {
        if (lesson.isMarked()) {
            star.setFitWidth(30);
            star.setFitHeight(30);
            bookmark.setGraphic(star);
            bookmark.setVisible(true);
        }
    }


    /**
     * Change the card state depending on the current listing unit
     */
    private void switchCard() {
        switch (ListingUnit.getCurrentListingUnit()) {
        case LOCATION:
            switchToLocationCard();
            break;

        case MODULE:
            switchToModuleCard();
            break;

        default:
            switchToLessonCard();
            break;
        }
    }
```
###### /java/seedu/address/ui/LessonListPanel.java
``` java
    private void setConnections(ObservableList<ReadOnlyLesson> infoList) {

        ObservableList<LessonListCard> mappedList = EasyBind.map(
                infoList, (person) -> new LessonListCard(person, infoList.indexOf(person) + 1));

        lessonListView.setItems(mappedList);
        lessonListView.setCellFactory(listView -> new LessonListViewCell());
        setEventHandlerForSelectionChangeEvent();

    }
```
###### /java/seedu/address/ui/LessonListPanel.java
``` java
    @Subscribe
    private void handleRefreshPanelEvent(RefreshPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(lessonList);
    }
```
