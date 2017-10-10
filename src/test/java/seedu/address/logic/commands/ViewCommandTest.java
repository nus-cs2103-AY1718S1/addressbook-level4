package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.AddressBook;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ViewCommandTest {

    private Model model;
    private Model expectedModel;
    private ViewCommand viewCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        viewCommand = new ViewCommand(INDEX_FIRST_PERSON);
        viewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_viewSpecifiedPerson() {

        ListingUnit.setCurrentListingUnit(ListingUnit.PERSON);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_PERSON_SUCCESS,
                model.getFilteredPersonList().get(0)), Arrays.asList(ALICE));
    }

    @Test
    public void execute_viewAllPerson_withSpecifiedAddress() {

        ListingUnit.setCurrentListingUnit(ListingUnit.ADDRESS);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_ADDRESS_SUCCESS,
                model.getFilteredPersonList().get(0).getAddress()), Arrays.asList(ALICE));
    }

    @Test
    public void execute_viewAllPerson_withSpecifiedEmail() {

        ListingUnit.setCurrentListingUnit(ListingUnit.EMAIL);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_EMAIL_SUCCESS,
                model.getFilteredPersonList().get(0).getEmail()), Arrays.asList(ALICE));
    }

    @Test
    public void execute_viewAllPerson_withSpecifiedPhone() {

        ListingUnit.setCurrentListingUnit(ListingUnit.PHONE);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_PHONE_SUCCESS,
                model.getFilteredPersonList().get(0).getPhone()), Arrays.asList(ALICE));
    }




    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ViewCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = null;
        try {
            commandResult = command.execute();
        } catch (CommandException e) {
            assert false : "The exception is unexpected";
        }

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
