# namvd2709
###### /java/seedu/address/logic/AutocompleteManagerTest.java
``` java
package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

public class AutocompleteManagerTest {
    @Test
    public void attemptAutocomplete() {
        AutocompleteManager manager = new AutocompleteManager();

        // test commands which can be matched by 1 character
        assertEquals(manager.attemptAutocomplete("f"), FindCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("u"), UndoCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("r"), RedoCommand.COMMAND_WORD);

        // test commands which can be matched by 1 character but 2 is supplied
        assertEquals(manager.attemptAutocomplete("de"), DeleteCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("s"), SelectCommand.COMMAND_WORD);

        // test commands which can be matched by 2 characters
        assertEquals(manager.attemptAutocomplete("ed"), EditCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("ex"), ExitCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("he"), HelpCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("hi"), HistoryCommand.COMMAND_WORD);

        // test commands which can't be matched by 1 character but 1 is supplied
        assertEquals(manager.attemptAutocomplete("e"), "e");
    }
}
```
###### /java/seedu/address/logic/commands/AppointCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class AppointCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addAppointment_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAppointment("15/12/2020 00:00 60").build();
        AppointCommand appointCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getAppointment().value);
        String expectedMessage = String.format(AppointCommand.MESSAGE_APPOINT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(appointCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void  execute_removeAppointment_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withAppointment("").build();
        AppointCommand appointCommand = prepareCommand(INDEX_FIRST_PERSON, "");

        String expectedMessage = String.format(AppointCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(appointCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withAppointment(VALID_APPOINTMENT).build();
        AppointCommand appointCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getAppointment().value);

        String expectedMessage = String.format(AppointCommand.MESSAGE_APPOINT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(appointCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AppointCommand appointCommand = prepareCommand(outOfBoundIndex, VALID_APPOINTMENT);

        assertCommandFailure(appointCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws IllegalValueException {
        final AppointCommand standardCommand = new AppointCommand(INDEX_FIRST_PERSON,
                                                                    new Appointment(VALID_APPOINTMENT));

        // same values -> returns true
        String copyDescriptor = new String(VALID_APPOINTMENT);
        AppointCommand commandWithSameValues = new AppointCommand(INDEX_FIRST_PERSON, new Appointment(copyDescriptor));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AppointCommand(INDEX_SECOND_PERSON,
                new Appointment(VALID_APPOINTMENT))));
    }

    private AppointCommand prepareCommand(Index index, String appointment) throws IllegalValueException {
        AppointCommand appointCommand = new AppointCommand(index, new Appointment(appointment));
        appointCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return appointCommand;
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_appoint() throws Exception {
        AppointCommand command = (AppointCommand) parser.parseCommand(AppointCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + APPOINTMENT_DESC);
        assertEquals(new AppointCommand(INDEX_FIRST_PERSON, new Appointment(VALID_APPOINTMENT)), command);
    }
```
###### /java/seedu/address/logic/parser/AppointCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AppointCommand;

public class AppointCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointCommand.MESSAGE_USAGE);

    private AppointCommandParser parser = new AppointCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, APPOINTMENT_DESC, ParserUtil.MESSAGE_INVALID_INDEX);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-3" + APPOINTMENT_DESC, ParserUtil.MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + APPOINTMENT_DESC, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Appointment} of the {@code Person} that we are building.
     */
    public PersonBuilder withAppointment(String appointment) {
        try {
            this.person.setAppointment(new Appointment(appointment));
        } catch (IllegalValueException ive) {
            try {
                throw new IllegalValueException("appointment is expected to be unique");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public Person build() {
        return this.person;
    }

}
```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_startingWithTab() {
        assertInputHistory(KeyCode.TAB, "");

        // successfully autocomplete
        commandBoxHandle.setInput("f");
        assertInputHistory(KeyCode.TAB, "find ");
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());

        // fail autocomplete
        commandBoxHandle.setInput("e");
        assertInputHistory(KeyCode.TAB, "e");
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }
```
