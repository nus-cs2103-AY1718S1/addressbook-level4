package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;

//@@author cjianhui
/**
 * Adds a person to the address book.
 */
public class CreateGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "gcreate";
    public static final String COMMAND_ALT = "gc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group in address book. "
            + "Parameters: "
            + PREFIX_NAME + "GROUP NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Bamboo";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

    private final Group toAdd;

    /**
     * Creates an CreateGroupCommand to add the specified {@code ReadOnlyGroup}
     */
    public CreateGroupCommand(ReadOnlyGroup group) {
        toAdd = new Group(group);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addGroup(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateGroupException dge) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateGroupCommand // instanceof handles nulls
                && toAdd.equals(((CreateGroupCommand) other).toAdd));
    }
}
