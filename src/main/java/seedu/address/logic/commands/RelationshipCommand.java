package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Relationship;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
  * Changes the relationship of an existing person in the address book.
  */
public class RelationshipCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "relationship";
    public static final String COMMAND_ALIAS = "rel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the relationship of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing relationship will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RELATIONSHIP + "[RELATIONSHIP]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RELATIONSHIP + "John Doe";

    public static final String MESSAGE_ADD_RELATIONSHIP_SUCCESS = "Added relationship to Person: %1$s";
    public static final String MESSAGE_DELETE_RELATIONSHIP_SUCCESS = "Removed relationship from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Relationship relation;

    /**
      * @param index of the person in the filtered person list to edit the relation
      * @param relation of the person
      */
    public RelationshipCommand(Index index, Relationship relation) {
        requireNonNull(index);
        requireNonNull(relation);

        this.index = index;
        this.relation = relation;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getBloodType(),
                personToEdit.getTags(), personToEdit.getRemark(), this.relation, personToEdit.getAppointments());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.getFilteredPersonList();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Outputs success message based on whether a relationship is added or removed
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!relation.value.isEmpty()) {
            return String.format(MESSAGE_ADD_RELATIONSHIP_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_RELATIONSHIP_SUCCESS, personToEdit);
        }
    }

    /**
     * Checks if
     * (a) Object is the same object
     * (b) Object is an instance of the object and that index and relation are the same
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RelationshipCommand e = (RelationshipCommand) other;
        return index.equals(e.index)
                && relation.equals(e.relation);
    }
}
