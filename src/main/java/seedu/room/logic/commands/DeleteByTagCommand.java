package seedu.room.logic.commands;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.exceptions.CommandException;

import seedu.room.model.tag.Tag;

/**
 * Deletes a person identified by a tag supplied
 */
public class DeleteByTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletebytag";
    public static final String COMMAND_ALIAS = "dbt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the tag supplied in this argument\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + "friends";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Persons with the following tag: %1$s";

    private final Tag toRemove;

    public DeleteByTagCommand(String tagName) throws IllegalValueException {
        this.toRemove = new Tag(tagName);
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            model.deleteByTag(toRemove);
        } catch (IllegalValueException e) {
            assert false : "Tag provided must be valid";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, toRemove));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByTagCommand // instanceof handles nulls
                && this.toRemove.equals(((DeleteByTagCommand) other).toRemove)); // state check
    }
}


