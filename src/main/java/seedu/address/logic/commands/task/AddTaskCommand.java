package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DateTimeParserUtil;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Adds the given user input as a task in the application
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String COMMAND_FORMAT = "add [HEADER]\n"
            + "add [HEADER] by [DEADLINE] \n"
            + "add [HEADER] from [START TIME] to <[END TIME]>"
            + "add '[full text to be used as header]'";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + COMMAND_FORMAT + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "Go Fishing from 9am to 11am";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private ReadOnlyTask toAdd = null;


    /**
     * Empty Constructor
     */
    public AddTaskCommand() {
    }

    /**
     * Copy Constructor
     */
    public AddTaskCommand(ReadOnlyTask task) {
        this.toAdd = new Task(task);
    }

    /**
     * Creates an AddTaskCommand to add a task with only a header
     *
     * @throws IllegalValueException if the String specified{@code header} is invalid
     */
    public AddTaskCommand(String header) throws IllegalValueException {
        this.toAdd = new Task(new Header(header));
    }

    /**
     * Creates an AddTaskCommand to add a task with a header and deadline
     *
     * @throws IllegalValueException if input values are invalid
     */
    public AddTaskCommand(String header, Optional<LocalDateTime> deadLineDate)
            throws IllegalValueException {
        this.toAdd = new Task(new Header(header), deadLineDate);
    }

    /**
     * Creates an AddTaskCommand to add a task with header, start date time and end end date time
     *
     * @throws IllegalValueException if input values are invalid
     */
    public AddTaskCommand(String header, Optional<LocalDateTime> startDateTime, Optional<LocalDateTime> endDateTime)
            throws IllegalValueException {
        Optional<LocalDateTime> balancedEndDateTime = endDateTime;
        if (startDateTime.isPresent() && endDateTime.isPresent()) {
            balancedEndDateTime = Optional.of(DateTimeParserUtil.balanceStartAndEndDateTime(
                    startDateTime.get(), endDateTime.get()));
        }
        this.toAdd = new Task(new Header(header), startDateTime, balancedEndDateTime);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
