package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddBirthdayCommand.
 */

public class AddBirthdayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addBirthday_success() throws PersonNotFoundException, IllegalValueException {
        //actual model
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withBirthday(VALID_BIRTHDAY_AMY).build();
        Birthday toAdd = new Birthday (VALID_BIRTHDAY_AMY);

        AddBirthdayCommand addBirthdayCommand = prepareCommand(INDEX_FIRST_PERSON, toAdd);
        String expectedMessage = String.format(AddBirthdayCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, toAdd);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addBirthdayCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code AddBirthdayCommand} with parameter {@code index}
     */
    private AddBirthdayCommand prepareCommand (Index index, Birthday toAdd) {
        AddBirthdayCommand addBirthdayCommand = new AddBirthdayCommand(index, toAdd);
        addBirthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return addBirthdayCommand;
    }
}
