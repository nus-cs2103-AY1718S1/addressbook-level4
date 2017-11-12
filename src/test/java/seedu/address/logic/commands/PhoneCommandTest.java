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
//@@author eeching
public class PhoneCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_addPhoneSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "add", new Phone("2333"));

        String expectedMessage = "Phone number 2333 has been added.\n"
                + "The updated phone list now has 3 phone numbers.\n"
                + "The primary phone number is 85355255.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personAcceptedByModel_removePhoneSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "remove", new Phone("25555"));

        String expectedMessage = "Phone number 25555 has been removed.\n"
                + "The updated phone list now has 2 phone numbers.\n"
                + "The primary phone number is 85355255.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "add", new Phone("24444"));

        String expectedMessage = "Phone number to be added already exists in the list.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_phoneNotFound_throwsCommandException() throws Exception  {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "remove", new Phone("2333"));

        String expectedMessage = "Phone number to be removed is not found in the list.\n";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(phoneCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_personAcceptedByModel_invalidCommandSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).build();

        PhoneCommand phoneCommand = prepareCommand(INDEX_FIRST_PERSON, "IHaveNoIdeaWhatToType", new Phone("000"));

        String expectedMessage = "Command is invalid, please check again.\n";

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
//@@author
