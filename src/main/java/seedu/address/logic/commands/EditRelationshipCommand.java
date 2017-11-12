package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIDENCE_ESTIMATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.RelationshipNotFoundException;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;

//@@author joanneong
/**
 * Edits a relationship between two persons.
 */
public class EditRelationshipCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editRelationship";
    public static final String COMMAND_ALIAS = "editRel";

    public static final String COMMAND_PARAMETERS = "FROM_INDEX, TO_INDEX (must be positive integers), "
            + "(Optional) " + PREFIX_CONFIDENCE_ESTIMATE + "CONFIDENCE_ESTIMATE, "
            + "(Optional) " + PREFIX_NAME + "NAME ";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a relationship between the two persons specified "
            + "by the index numbers used in the last person listing. "
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " 1 2 n/newName";

    public static final String MESSAGE_EDIT_RELATIONSHIP_SUCCESS = "Edited relationship between : %1$s and %2$s";
    public static final String MESSAGE_NONEXISTENT_RELATIONSHIP = "This relationship does not exist "
            + "in the address book.";
    public static final String MESSAGE_DUPLICATED_RELATIONSHIP = "This relationship already exists "
            + "in the address book.";

    private final Index indexFromPerson;
    private final Index indexToPerson;

    private final Name name;
    private final ConfidenceEstimate confidenceEstimate;

    /**
     * @param indexFrom of the person from whom the relationship starts in the filtered person list
     * @param indexTo of the person to whom the relationship is directed in the filtered person list
     */
    public EditRelationshipCommand(Index indexFrom, Index indexTo,
                                   Name name, ConfidenceEstimate confidenceEstimate) {
        requireAllNonNull(indexFrom, indexTo);
        this.indexFromPerson = indexFrom;
        this.indexToPerson = indexTo;
        this.name = name;
        this.confidenceEstimate = confidenceEstimate;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.editRelationship(indexFromPerson, indexToPerson, name, confidenceEstimate);
        } catch (IllegalValueException ive) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (RelationshipNotFoundException re) {
            throw new CommandException(MESSAGE_NONEXISTENT_RELATIONSHIP);
        } catch (DuplicateRelationshipException dre) {
            throw new CommandException(MESSAGE_DUPLICATED_RELATIONSHIP);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_RELATIONSHIP_SUCCESS, indexToPerson.toString(),
                indexFromPerson.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditRelationshipCommand // instanceof handles nulls
                && this.indexFromPerson.equals(((EditRelationshipCommand) other).indexFromPerson)
                && this.indexToPerson.equals(((EditRelationshipCommand) other).indexToPerson))
                && this.name.equals((((EditRelationshipCommand) other).name))
                && this.confidenceEstimate.equals(((EditRelationshipCommand) other).confidenceEstimate); // state check
    }

}