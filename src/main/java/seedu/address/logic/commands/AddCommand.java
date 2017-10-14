package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.DuplicateLessonException;

/**
 * Adds a lesson to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the address book. "
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
    public static final String MESSAGE_DUPLICATE_PERSON = "This lesson already exists in the address book";

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
            model.addLesson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateLessonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
