package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.phone.Phone;
import seedu.address.testutil.PersonBuilder;

public class PhoneCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_updatePhoneListSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withPhoneList("2333").build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "add", new Phone("2333"));

        String expectedMessage = "Phone number 2333 has been added, the updated phone list now has 2 phone numbers, " +
                "and the primary phone number is 85355255";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code CustomCommand} with the parameters {@code index + CustomFieldName + CustomFieldValue}.
     */
    private PhoneCommand prepareCommand(Index index, String action, Phone phone) {
        PhoneCommand command = new PhoneCommand(index, action, phone);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
