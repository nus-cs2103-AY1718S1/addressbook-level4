package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.BookedSlot;
import seedu.address.model.module.ClassType;
import seedu.address.model.module.Code;
import seedu.address.model.module.Group;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.Location;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.TimeSlot;
import seedu.address.model.module.exceptions.DuplicateBookedSlotException;
import seedu.address.model.module.exceptions.DuplicateLessonException;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

//@@author junming403
/**
 * Edits the details of an existing lesson/module/location in ModU.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lesson identified "
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
            + PREFIX_GROUP + "SL2 "
            + PREFIX_VENUE + "LT25";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_EDIT_LOCATION_SUCCESS = "Edited Location: %1$s";
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Location: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_BOOKEDSLOT = "This time slot have already "
            + "been booked in this location";

    private final Index index;
    private final EditLessonDescriptor editLessonDescriptor;
    private final String attributeValue;


    /**
     * @param index                of the lesson in the filtered lesson list to edit
     * @param editLessonDescriptor details to edit the lesson with
     */
    public EditCommand(Index index, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(editLessonDescriptor);

        this.index = index;
        this.editLessonDescriptor = new EditLessonDescriptor(editLessonDescriptor);
        attributeValue = null;
    }

    /**
     * @param index       of the address in the filtered address list to edit
     * @param attributeValue the new edited address
     */
    public EditCommand(Index index, String attributeValue) {
        requireNonNull(index);
        requireNonNull(attributeValue);

        this.index = index;
        this.editLessonDescriptor = null;
        this.attributeValue = attributeValue;
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
            model.updateLesson(lessonToEdit, editedLesson);
        } catch (DuplicateLessonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        } catch (LessonNotFoundException pnfe) {
            throw new AssertionError("The target lesson cannot be missing");
        } catch (DuplicateBookedSlotException s) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKEDSLOT);
        }
        EventsCenter.getInstance().post(new ViewedLessonEvent());
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, editedLesson));
    }


    /**
     * Edit the address, all lessons with the edited address is updated with a new given address.
     */
    private CommandResult executeEditLocation(Location addressToEdit) throws CommandException {
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        Location editedAddress = null;
        BookedSlot bookedSlotToEdit;
        BookedSlot editedBookedSlot;

        try {
            editedAddress = new Location(attributeValue);
            for (ReadOnlyLesson p : lessonList) {

                ReadOnlyLesson curEditedLesson;
                if (p.getLocation().equals(addressToEdit)) {
                    curEditedLesson = new Lesson(p.getClassType(), editedAddress, p.getGroup(),
                            p.getTimeSlot(), p.getCode(), p.getLecturers());
                    bookedSlotToEdit = new BookedSlot(p.getLocation(), p.getTimeSlot());
                    editedBookedSlot = new BookedSlot(editedAddress, p.getTimeSlot());
                    model.updateBookedSlot(bookedSlotToEdit, editedBookedSlot);
                    model.updateLesson(p, curEditedLesson);
                }
            }
            model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
            return new CommandResult(String.format(MESSAGE_EDIT_LOCATION_SUCCESS, editedAddress));
        } catch (DuplicateBookedSlotException s) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKEDSLOT);
        } catch (IllegalValueException ive) {
            model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
            throw new CommandException(ive.getMessage());
        } catch (LessonNotFoundException pnfe) {
            throw new AssertionError("The target lesson cannot be missing");
        }
    }


    /**
     * Edit the Module Code, all lessons with the edited code is updated with a new given code.
     */
    private CommandResult executeEditModule(Code codeToEdit) throws CommandException {
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        ObservableList<ReadOnlyLesson> lessonList = model.getFilteredLessonList();
        Code editedCode = null;
        try {
            editedCode = new Code(attributeValue);
            for (ReadOnlyLesson p : lessonList) {

                ReadOnlyLesson curEditedLesson;
                if (p.getCode().equals(codeToEdit)) {
                    curEditedLesson = new Lesson(p.getClassType(), p.getLocation(), p.getGroup(),
                            p.getTimeSlot(), editedCode, p.getLecturers());
                    model.updateLesson(p, curEditedLesson);
                }
            }
            model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
            return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedCode));
        } catch (IllegalValueException ive) {
            model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
            throw new CommandException(ive.getMessage());
        } catch (LessonNotFoundException pnfe) {
            throw new AssertionError("The target lesson cannot be missing");
        }

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

        // state check
        EditCommand e = (EditCommand) other;
        if (editLessonDescriptor != null && e.editLessonDescriptor != null) {
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
