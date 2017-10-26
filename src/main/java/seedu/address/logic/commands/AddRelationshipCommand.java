package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.relationship.RelationshipDirection;
import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;

/**
 * This class is to specify a command for adding relationship between two persons
 */
public class AddRelationshipCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addRelationship";
    public static final String COMMAND_ALIAS = "addre";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a relationship between the two persons specified. "
            + "by the index numbers used in the last person listing. "
            + "Direction of the relationship is specified by the direction in user input.\n"
            + "Parameters: FROM_INDEX, TO_INDEX (must be positive integers), "
            + "DIRECTION (either \"directed\" or \"undirected\". "
            + "[INDEXOFFROMPERSON] "
            + "[INDEXOFTOPERSON] "
            + "[DIRECTION]\n"
            + "Example: " + COMMAND_WORD + " 1 2 directed";

    public static final String MESSAGE_ADD_RELATIONSHIP_SUCCESS = "Added a %3$s relationship between : %1$s and %2$s";
    public static final String MESSAGE_DUPLICATED_RELATIONSHIP = "This relationship already exists"
            + "in the address book.";

    private final Index indexFromPerson;
    private final Index indexToPerson;
    private final RelationshipDirection direction;

    /**
     * @param indexFrom of the person from whom the relationship starts in the filtered person list
     * @param indexTo of the person to whom the relationship is directed in the filtered person list
     * @param direction of the relationship
     */
    public AddRelationshipCommand(Index indexFrom, Index indexTo, RelationshipDirection direction) {
        requireAllNonNull(indexFrom, indexTo, direction);
        this.indexFromPerson = indexFrom;
        this.indexToPerson = indexTo;
        this.direction = direction;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addRelationship(indexFromPerson, indexToPerson, direction);
            graphWrapper.buildGraph(model);
        } catch (IllegalValueException ive) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (DuplicateRelationshipException dre) {
            throw new CommandException(MESSAGE_DUPLICATED_RELATIONSHIP);
        }
        return new CommandResult(String.format(MESSAGE_ADD_RELATIONSHIP_SUCCESS, indexToPerson.toString(),
                indexFromPerson.toString(), direction));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRelationshipCommand)) {
            return false;
        }

        // state check
        AddRelationshipCommand addre = (AddRelationshipCommand) other;
        return indexFromPerson.equals(addre.indexFromPerson)
                && indexToPerson.equals(addre.indexToPerson)
                && direction.equals(addre.direction);
    }

}
