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
import seedu.address.model.customField.CustomField;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CustomCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_personAcceptedByModel_updateCustomFieldSuccessful() throws Exception {
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withCustomFields("Birthday 29/02/1996").build();

        CustomField customField = new CustomField("Birthday", "29/02/1996");
        CustomCommand customCommand = prepareCommand(INDEX_FIRST_PERSON, customField);

        String expectedMessage = String.format(CustomCommand.MESSAGE_UPDATE_PERSON_CUSTOM_FIELD_SUCCESS, updatedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(customCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code CustomCommand} with the parameters {@code index + CustomFieldName + CustomFieldValue}.
     */
    private CustomCommand prepareCommand(Index index, CustomField customField) {
        CustomCommand command = new CustomCommand(index, customField);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
