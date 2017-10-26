package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ViewedLessonEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.module.BookedSlot;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.DuplicateBookedSlotException;
import seedu.address.model.module.exceptions.DuplicateLessonException;

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
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateLessonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        } catch (DuplicateBookedSlotException s) {
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
