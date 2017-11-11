//@@author qihao27
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using its name from the address book.
 */
public class DeleteByNameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name.\n"
            + "Parameters: [NAME]\n"
            + "Example: " + COMMAND_WORD + " john";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_MULTIPLE_PERSON_FOUND = "Multiple contacts with specified name found!\n"
        + "Please add more details for distinction or use the following command:\n" + DeleteCommand.MESSAGE_USAGE;
    public static final String MESSAGE_PERSON_NAME_ABSENT = "The person's name provided can not be found.";

    private final Predicate<ReadOnlyPerson> predicate;

    public DeleteByNameCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<ReadOnlyPerson> predicateList = model.getFilteredPersonList();

        if (predicateList.size() > 1) {
            throw new CommandException(MESSAGE_MULTIPLE_PERSON_FOUND);
        } else if (predicateList.size() == 0) {
            throw new CommandException(MESSAGE_PERSON_NAME_ABSENT);
        } else {
            ReadOnlyPerson personToDelete = predicateList.get(0);

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                throw new CommandException(MESSAGE_PERSON_NAME_ABSENT);
            }

            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByNameCommand // instanceof handles nulls
                && this.predicate.equals(((DeleteByNameCommand) other).predicate)); // state check
    }
}
