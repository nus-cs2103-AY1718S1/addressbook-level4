package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

//@@author sofarsophie
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByName_success() throws Exception {
        Prefix prefix = PREFIX_ADDRESS;
        SortCommand sortCommand = prepareCommand(model, prefix);
        String expectedMessage = SortCommand.MESSAGE_SORT_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(model.getFilteredPersonList(), prefix);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortTwoPersonsList_success() throws Exception {
        Prefix prefix = PREFIX_NAME;
        AddressBook ab = new AddressBookBuilder().withPerson(getTypicalPersons().get(1))
                .withPerson(getTypicalPersons().get(0)).build();
        Model modelWithTwoPersons = new ModelManager(ab, new UserPrefs());
        SortCommand sortCommand = prepareCommand(modelWithTwoPersons, prefix);
        String expectedMessage = SortCommand.MESSAGE_SORT_SUCCESS;
        Model expectedModel = new ModelManager(new AddressBook(modelWithTwoPersons.getAddressBook()), new UserPrefs());
        expectedModel.sortFilteredPersonList(model.getFilteredPersonList(), prefix);

        assertCommandSuccess(sortCommand, modelWithTwoPersons, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortEmptyList_throwsCommandException() {
        Prefix prefix = PREFIX_EMAIL;
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        String expectedMessage = Messages.MESSAGE_EMPTY_PERSON_LIST;
        assertCommandFailure(prepareCommand(emptyModel, prefix), model, expectedMessage);
    }

    /**
     * Generates a new {@code SortCommand} with the Model and prefix given.
     */
    private SortCommand prepareCommand(Model model, Prefix prefix) {
        SortCommand sortCommand = new SortCommand(prefix);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
//@@author
