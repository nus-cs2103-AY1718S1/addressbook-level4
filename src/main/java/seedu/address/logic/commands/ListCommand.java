package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person is not in the address book";

    //@@author aver0214
    @Override
    public CommandResult execute() throws CommandException {
        try {
            listInOrder();
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pne) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /** Sort the contacts in alphabetical order before listing. */
    public void listInOrder() throws PersonNotFoundException, DuplicatePersonException {
        this.model.sortAllPersons();
        this.model.filterImportantTag();
    }
    //@@author
}
