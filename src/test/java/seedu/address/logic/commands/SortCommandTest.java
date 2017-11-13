package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

//@@author jacoblipech
public class SortCommandTest {

    private Model model;

    @Test
    public void execute_sortEmptyAddressBook_success() {
        model = new ModelManager();
        model.getFilteredPersonList();
        assertCommandSuccess(prepareCommand(model), model, SortCommand.MESSAGE_EMPTY_LIST, model);
    }

    @Test
    public void execute_sortAddressBookByName_success() throws DuplicatePersonException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addPerson(new PersonBuilder().withName("Alex").build());;
        assertCommandSuccess(prepareCommand(model), model, SortCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_alreadySortedAddressBookByName_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, SortCommand.MESSAGE_ALREADY_SORTED, model);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the contacts by name in {@code model}.
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
