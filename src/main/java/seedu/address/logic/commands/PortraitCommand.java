package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PORTRAIT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.PortraitPath;

/**
 * A command that add an head portrait to a person
 */
public class PortraitCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "portrait";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Attach a head portrait to a person"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_PORTRAIT + "PICTURE_FILE_NAME]\n"
            + "Example: " + COMMAND_WORD + "1 "
            + PREFIX_PORTRAIT + "sample picture.png";
    public static final String MESSAGE_ADD_PORTRAIT_SUCCESS = "Attached a head portrait to Person: %1$s";
    public static final String MESSAGE_DELETE_PORTRAIT_SUCCESS = "Removed head portrait from Person: %1$s";

    private Index index;
    private PortraitPath filePath;

    public PortraitCommand (Index index, PortraitPath filePath) {
        this.index = index;
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }

    @Override
    protected void undo() {

    }

    @Override
    protected void redo() {

    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof PortraitCommand
                && this.index.equals(((PortraitCommand) other).index)
                && this.filePath.equals(((PortraitCommand) other).filePath));
    }
}
