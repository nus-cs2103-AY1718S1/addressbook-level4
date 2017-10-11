package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class TagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags multiple people using the same tag. ";
    
    public static final String MESSAGE_SUCCESS = "New tags added to: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "One or more person(s) already has this tag";
    
    private final String tag;
    
    public TagCommand(String tag) {
        this.tag = tag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && tag.equals(((TagCommand) other).tag));
    }
    
}
