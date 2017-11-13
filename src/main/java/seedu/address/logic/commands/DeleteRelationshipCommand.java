package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author wenmogu
/**
 * This class is to specify a command for deleting relationship between two persons
 */
public class DeleteRelationshipCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteRelationship";
    public static final String COMMAND_ALIAS = "delre";

    public static final String COMMAND_PARAMETERS = "FROM_INDEX, TO_INDEX (must be positive integers), "
            + "[INDEXOFFROMPERSON] "
            + "[INDEXOFTOPERSON] ";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a relationship between the two persons specified. "
            + "by the index numbers used in the last person listing. "
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_RELATIONSHIP_SUCCESS =  "Deleted the relationship "
            + "between : %1$s and %2$s";

    private final Index fromPersonIndex;
    private final Index toPersonIndex;

    public DeleteRelationshipCommand(Index fromPersonIndex, Index toPersonIndex) {
        this.fromPersonIndex = fromPersonIndex;
        this.toPersonIndex = toPersonIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            model.deleteRelationship(fromPersonIndex, toPersonIndex);
        } catch (IllegalValueException ive) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                fromPersonIndex.toString(), toPersonIndex.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteRelationshipCommand)) {
            return false;
        }

        // state check
        DeleteRelationshipCommand delre = (DeleteRelationshipCommand) other;
        return fromPersonIndex.equals(delre.fromPersonIndex)
                && toPersonIndex.equals(delre.toPersonIndex);
    }
}
