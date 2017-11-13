# jeffreygohkw
###### \java\seedu\address\logic\commands\ChangePrivacyCommandTest.java
``` java
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonPrivacySettingsBuilder;

public class ChangePrivacyCommandTest {

    private Model model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());

    @Test
    public void personPrivacySettingsTests() {
        PersonPrivacySettings pps = new PersonPrivacySettings();

        assertFalse(pps.isAnyFieldNonNull());

        PersonPrivacySettings ppsByBuilder = new PersonPrivacySettingsBuilder().setNamePrivate("true")
            .setPhonePrivate("false").setEmailPrivate("true").setAddressPrivate("true")
            .setRemarkPrivate("false").build();
        pps.setNameIsPrivate(true);
        pps.setPhoneIsPrivate(false);
        pps.setEmailIsPrivate(true);
        pps.setAddressIsPrivate(true);
        pps.setRemarkIsPrivate(false);

        assertEquals(ppsByBuilder.getAddressIsPrivate(), pps.getAddressIsPrivate());
        assertEquals(ppsByBuilder.getEmailIsPrivate(), pps.getEmailIsPrivate());
        assertEquals(ppsByBuilder.getNameIsPrivate(), pps.getNameIsPrivate());
        assertEquals(ppsByBuilder.getPhoneIsPrivate(), pps.getPhoneIsPrivate());
        assertEquals(ppsByBuilder.getRemarkIsPrivate(), pps.getRemarkIsPrivate());
        assertEquals(ppsByBuilder.isAnyFieldNonNull(), pps.isAnyFieldNonNull());

        PersonPrivacySettings ppsCopy = new PersonPrivacySettings(pps);

        assertEquals(ppsCopy.getAddressIsPrivate(), pps.getAddressIsPrivate());
        assertEquals(ppsCopy.getEmailIsPrivate(), pps.getEmailIsPrivate());
        assertEquals(ppsCopy.getNameIsPrivate(), pps.getNameIsPrivate());
        assertEquals(ppsCopy.getPhoneIsPrivate(), pps.getPhoneIsPrivate());
        assertEquals(ppsCopy.getRemarkIsPrivate(), pps.getRemarkIsPrivate());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person newPerson = new PersonBuilder().withEmail("alice@example.com").build();
        newPerson.getName().setPrivate(true);
        newPerson.getPhone().setPrivate(true);
        newPerson.getEmail().setPrivate(true);
        newPerson.getAddress().setPrivate(true);
        newPerson.setRemark(model.getFilteredPersonList().get(0).getRemark());
        newPerson.getRemark().setPrivate(true);

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder(newPerson).setNamePrivate("true")
                .setPhonePrivate("true").setEmailPrivate("true").setAddressPrivate("true").setRemarkPrivate("true")
                .build();
        ChangePrivacyCommand changePrivacyCommand = new ChangePrivacyCommand(INDEX_FIRST_PERSON, pps);
        changePrivacyCommand.model = model;

        String expectedMessage = String.format(ChangePrivacyCommand.MESSAGE_CHANGE_PRIVACY_SUCCESS, newPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), newPerson);

        assertCommandSuccess(changePrivacyCommand, model, expectedMessage, expectedModel);

        PersonPrivacySettings ppsPublic = new PersonPrivacySettingsBuilder(newPerson).setNamePrivate("false")
                .setPhonePrivate("false").setEmailPrivate("false").setAddressPrivate("false").setRemarkPrivate("false")
                .build();

        newPerson.getName().setPrivate(false);
        newPerson.getPhone().setPrivate(false);
        newPerson.getEmail().setPrivate(false);
        newPerson.getAddress().setPrivate(false);
        newPerson.getRemark().setPrivate(false);

        ChangePrivacyCommand changePrivacyCommandPublic = new ChangePrivacyCommand(INDEX_FIRST_PERSON, ppsPublic);
        changePrivacyCommandPublic.setData(model, new CommandHistory(), new UndoRedoStack());

        String expectedMessagePublic = String.format(ChangePrivacyCommand.MESSAGE_CHANGE_PRIVACY_SUCCESS, newPerson);

        expectedModel.updatePerson(model.getFilteredPersonList().get(0), newPerson);

        assertCommandSuccess(changePrivacyCommandPublic, model, expectedMessagePublic, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        ReadOnlyPerson lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        Person personInList = new PersonBuilder().withName(lastPerson.getName().toString())
                .withPhone(lastPerson.getPhone().toString()).withEmail(lastPerson.getEmail().toString())
                .withAddress(lastPerson.getAddress().toString()).withRemark(lastPerson.getRemark().toString())
                .withFavourite(lastPerson.getFavourite().toString())
                .build();

        personInList.setTags(lastPerson.getTags());
        personInList.getName().setPrivate(true);
        personInList.getPhone().setPrivate(true);


        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder(personInList).setNamePrivate("true")
                .setPhonePrivate("true").build();
        ChangePrivacyCommand changePrivacyCommand = new ChangePrivacyCommand(indexLastPerson, pps);
        changePrivacyCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        String expectedMessage = String.format(ChangePrivacyCommand.MESSAGE_CHANGE_PRIVACY_SUCCESS, personInList);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.updatePerson(lastPerson, personInList);

        assertCommandSuccess(changePrivacyCommand, model, expectedMessage, expectedModel);

        PersonPrivacySettings ppsPublic = new PersonPrivacySettingsBuilder(personInList).setNamePrivate("false")
                .setPhonePrivate("false").build();


        personInList.getName().setPrivate(false);
        personInList.getPhone().setPrivate(false);

        ChangePrivacyCommand changePrivacyCommandPublic = new ChangePrivacyCommand(indexLastPerson, ppsPublic);
        changePrivacyCommandPublic.setData(model, new CommandHistory(), new UndoRedoStack());

        String expectedMessagePublic = String.format(ChangePrivacyCommand.MESSAGE_CHANGE_PRIVACY_SUCCESS, personInList);

        expectedModel.updatePerson(lastPerson, personInList);

        assertCommandSuccess(changePrivacyCommandPublic, model, expectedMessagePublic, expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\EditPersonCommandTest.java
``` java
    @Test
    public void execute_privateFields_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personInFilteredList.getName().setPrivate(true);
        Name originalName = personInFilteredList.getName();

        personInFilteredList.getPhone().setPrivate(true);
        Phone originalPhone = personInFilteredList.getPhone();

        personInFilteredList.getEmail().setPrivate(true);
        Email originalEmail = personInFilteredList.getEmail();

        personInFilteredList.getAddress().setPrivate(true);
        Address originalAddress = personInFilteredList.getAddress();

        personInFilteredList.getRemark().setPrivate(true);
        Remark originalRemark = personInFilteredList.getRemark();

        EditPersonCommand editPersonCommand = prepareCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_ALL_FIELDS_PRIVATE);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personInFilteredList);

        assertCommandFailure(editPersonCommand, model, expectedMessage);

        assertEquals(personInFilteredList.getName(), originalName);
        assertEquals(personInFilteredList.getPhone(), originalPhone);
        assertEquals(personInFilteredList.getEmail(), originalEmail);
        assertEquals(personInFilteredList.getAddress(), originalAddress);
        assertEquals(personInFilteredList.getRemark(), originalRemark);

        personInFilteredList.getName().setPrivate(false);
        personInFilteredList.getPhone().setPrivate(false);
        personInFilteredList.getEmail().setPrivate(false);
        personInFilteredList.getAddress().setPrivate(false);
        personInFilteredList.getRemark().setPrivate(false);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditPersonCommand editPersonCommand = prepareCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());

        String expectedMessage = String.format(EditPersonCommand.MESSAGE_ALL_FIELDS_PRIVATE);

        assertCommandFailure(editPersonCommand, model, expectedMessage);
    }

```
###### \java\seedu\address\logic\commands\LocateCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code LocateCommand}.
 */
public class LocateCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LocateCommand locateFirstCommand = new LocateCommand(INDEX_FIRST_PERSON);
        LocateCommand locateSecondCommand = new LocateCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(locateFirstCommand.equals(locateFirstCommand));

        // same values -> returns true
        LocateCommand locateFirstCommandCopy = new LocateCommand(INDEX_FIRST_PERSON);
        assertTrue(locateFirstCommand.equals(locateFirstCommandCopy));

        // different types -> returns false
        assertFalse(locateFirstCommand.equals(1));

        // null -> returns false
        assertFalse(locateFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(locateFirstCommand.equals(locateSecondCommand));
    }

    /**
     * Executes a {@code LocateCommand} with the given {@code index}, and checks that {@code BrowserPanelLocateEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        LocateCommand locateCommand = prepareCommand(index);
        ReadOnlyPerson p = model.getFilteredPersonList().get(index.getZeroBased());
        try {
            CommandResult commandResult = locateCommand.execute();
            assertEquals(String.format(LocateCommand.MESSAGE_LOCATE_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        BrowserPanelLocateEvent lastEvent =
                (BrowserPanelLocateEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(p, lastEvent.getNewSelection());
    }

    /**
     * Executes a {@code LocateCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        LocateCommand locateCommand = prepareCommand(index);
        try {
            locateCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code LocateCommand} with parameters {@code index}.
     */
    private LocateCommand prepareCommand(Index index) {
        LocateCommand locateCommand = new LocateCommand(index);
        locateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locateCommand;
    }
}
```
###### \java\seedu\address\logic\commands\NavigateCommandTest.java
``` java
import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Location;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code NavigateCommand}.
 */
public class NavigateCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_fromAddress_success() throws IllegalValueException {
        Location from = new Location("NUS");
        Location to = new Location("Sentosa");
        NavigateCommand navi = prepareCommand(from, to, null, null, false, false);
        assertExecutionSuccess(navi);
    }

    @Test
    public void execute_fromPersons_success() throws IllegalValueException {
        NavigateCommand navi = prepareCommand(null, null,
                INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, false, false);

        assertExecutionSuccess(navi);
    }

    @Test
    public void execute_fromTasks_success() throws IllegalValueException {
        NavigateCommand navi = prepareCommand(null, null,
                INDEX_THIRD_TASK, INDEX_FIRST_TASK, true, true);

        assertExecutionSuccess(navi);
    }


    @Test
    public void execute_invalidArgs_failure() throws IllegalValueException {
        Location from = new Location("NUS");
        Location to = new Location("Sentosa");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(NavigateCommand.MESSAGE_MULTIPLE_FROM_ERROR);
        NavigateCommand navi = prepareCommand(from, to,
                INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, false, false);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(NavigateCommand.MESSAGE_MULTIPLE_TO_ERROR);
        NavigateCommand naviOne = prepareCommand(from, to,
                null, INDEX_SECOND_PERSON, false, false);
    }

    /**
     * Executes the input (@code NavigateCommand) and checks that (@code BrowserPanelNavigateEvent) is raised with
     * the correct locations
     */
    private void assertExecutionSuccess(NavigateCommand navi) throws IllegalValueException {
        Location from;
        Location to;
        try {

            if (navi.getLocationFrom() != null) {
                from = navi.getLocationFrom();
            } else if (navi.isFromIsTask()) {
                from = new Location(model.getFilteredTaskList().get(navi.getFromIndex().getZeroBased())
                        .getTaskAddress().toString());
            } else {
                from = new Location(model.getFilteredPersonList().get(navi.getFromIndex().getZeroBased())
                        .getAddress().toString());
            }

            if (navi.getLocationTo() != null) {
                to = navi.getLocationTo();
            } else if (navi.isToIsTask()) {
                to = new Location(model.getFilteredTaskList().get(navi.getToIndex().getZeroBased())
                        .getTaskAddress().toString());
            } else {
                to = new Location(model.getFilteredPersonList().get(navi.getToIndex().getZeroBased())
                        .getAddress().toString());
            }
            CommandResult commandResult = navi.execute();
            assertEquals(String.format(NavigateCommand.MESSAGE_NAVIGATE_SUCCESS,
                    from, to),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        BrowserPanelNavigateEvent lastEvent =
                (BrowserPanelNavigateEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(from, lastEvent.getFromLocation());
        assertEquals(to, lastEvent.getToLocation());
    }
    /**
     * Returns a {@code NavigateCommand} based on input parameters.
     */
    private NavigateCommand prepareCommand(Location locationFrom, Location locationTo, Index fromIndex, Index toIndex,
                                           boolean fromIsTask, boolean toIsTask) throws IllegalValueException {
        NavigateCommand navi = new NavigateCommand(locationFrom, locationTo, fromIndex, toIndex, fromIsTask, toIsTask);
        navi.setData(model, new CommandHistory(), new UndoRedoStack());
        return navi;
    }
}
```
###### \java\seedu\address\logic\commands\OpenCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.OpenCommand.OPEN_COMMAND_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.OpenRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class OpenCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_exit_success() {
        CommandResult result = new OpenCommand().execute();
        assertEquals(OPEN_COMMAND_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof OpenRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\PrivacyLevelCommandTest.java
``` java
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalPersonsAddressBook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code PrivacyLevelCommand}.
 */
public class PrivacyLevelCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPersonsAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        //valid levels
        PrivacyLevelCommand onePlc = prepareCommand(1);
        PrivacyLevelCommand twoPlc = prepareCommand(2);
        PrivacyLevelCommand threePlc = prepareCommand(3);

        assertExecutionSuccess(onePlc);
        assertExecutionSuccess(twoPlc);
        assertExecutionSuccess(threePlc);
    }

    @Test
    public void execute_invalidIndex_failure() {
        //Negative level
        PrivacyLevelCommand negativePlc = prepareCommand(-1);
        //Zero level
        PrivacyLevelCommand zeroPlc = prepareCommand(0);
        //Level greater than the maximum level allowed
        PrivacyLevelCommand tooBigPlc = prepareCommand(5);

        assertExecutionFailure(negativePlc, PrivacyLevelCommand.WRONG_PRIVACY_LEVEL_MESSAGE);
        assertExecutionFailure(zeroPlc, PrivacyLevelCommand.WRONG_PRIVACY_LEVEL_MESSAGE);
        assertExecutionFailure(tooBigPlc, PrivacyLevelCommand.WRONG_PRIVACY_LEVEL_MESSAGE);
    }

    /**
     * Executes a {@code PrivacyLevelCommand} and checks that the privacy level of the model and each person has been
     * changed to that of the level as specified in the input (@code PrivacyLevelCommand)
     */
    private void assertExecutionSuccess(PrivacyLevelCommand plc) {
        CommandResult commandResult;
        try {
            commandResult = plc.execute();
            assertEquals(String.format(PrivacyLevelCommand.CHANGE_PRIVACY_LEVEL_SUCCESS, plc.getLevel()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        //Check the model's privacy level
        assertTrue(model.getPrivacyLevel() == plc.getLevel());

        //Iterate through the list of persons in the model
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            //Check the person's Privacy Level
            ReadOnlyPerson p = model.getAddressBook().getPersonList().get(i);

            //Check Privacy Level of all fields of each person that can be private
            assertTrue(p.getName().getPrivacyLevel() == plc.getLevel());
            assertTrue(p.getPhone().getPrivacyLevel() == plc.getLevel());
            assertTrue(p.getEmail().getPrivacyLevel() == plc.getLevel());
            assertTrue(p.getAddress().getPrivacyLevel() == plc.getLevel());
            assertTrue(p.getRemark().getPrivacyLevel() == plc.getLevel());
        }
    }

    /**
     * Executes a {@code PrivacyLevelCommand} and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}
     */
    private void assertExecutionFailure(PrivacyLevelCommand plc, String expectedMessage) {
        try {
            plc.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            Assert.assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code LocateCommand} with parameters {@code index}.
     */
    private PrivacyLevelCommand prepareCommand(int level) {
        PrivacyLevelCommand privacyLevelCommand = new PrivacyLevelCommand(level);
        privacyLevelCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return privacyLevelCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SaveAsCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.SaveAsCommand.SAVE_AS_COMMAND_SUCCESS;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.SaveAsRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SaveAsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_exit_success() {
        CommandResult result = new SaveAsCommand().execute();
        assertEquals(SAVE_AS_COMMAND_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof SaveAsRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandChangePrivacy() throws Exception {
        Person person = new PersonBuilder().build();
        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder(person).build();

        ChangePrivacyCommand command = (ChangePrivacyCommand) parser.parseCommand(
                ChangePrivacyCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_NAME + String.valueOf(person.getName().getIsPrivate())
                        + " " + PREFIX_PHONE + String.valueOf(person.getPhone().getIsPrivate())
                        + " " + PREFIX_EMAIL + String.valueOf(person.getEmail().getIsPrivate())
                        + " " + PREFIX_ADDRESS + String.valueOf(person.getAddress().getIsPrivate()),
                DEFAULT_STATE_LOCK);
        ChangePrivacyCommand actualCommand = new ChangePrivacyCommand(INDEX_FIRST_PERSON, pps);

        assertTrue(changePrivacyCommandsEqual(command, actualCommand));
    }

    @Test
    public void parseCommandAliasChangePrivacy() throws Exception {
        Person person = new PersonBuilder().build();
        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder(person).build();

        ChangePrivacyCommand command = (ChangePrivacyCommand) parser.parseCommand(
                ChangePrivacyCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_NAME + String.valueOf(person.getName().getIsPrivate())
                        + " " + PREFIX_PHONE + String.valueOf(person.getPhone().getIsPrivate())
                        + " " + PREFIX_EMAIL + String.valueOf(person.getEmail().getIsPrivate())
                        + " " + PREFIX_ADDRESS + String.valueOf(person.getAddress().getIsPrivate()),
                DEFAULT_STATE_LOCK);
        ChangePrivacyCommand actualCommand = new ChangePrivacyCommand(INDEX_FIRST_PERSON, pps);

        assertTrue(changePrivacyCommandsEqual(command, actualCommand));
    }

    @Test
    public void parseCommandPrivacyLevel() throws Exception {
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_WORD + " 1", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_WORD + " 2", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_WORD + " 3", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
    }

    @Test
    public void parseCommandAliasPrivacyLevel() throws Exception {
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_ALIAS + " 1", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_ALIAS + " 2", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_ALIAS + " 3", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
    }

    @Test
    public void parseCommandTheme() throws Exception {
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD + " dark", DEFAULT_STATE_LOCK)
                instanceof ThemeCommand);
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD + " light", DEFAULT_STATE_LOCK)
                instanceof ThemeCommand);
    }

    @Test
    public void parseCommandAliasTheme() throws Exception {
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_ALIAS + " dark", DEFAULT_STATE_LOCK)
                instanceof ThemeCommand);
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_ALIAS + " light", DEFAULT_STATE_LOCK)
                instanceof ThemeCommand);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandOpen() throws Exception {
        assertTrue(parser.parseCommand(OpenCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof OpenCommand);
        assertTrue(parser
                .parseCommand(OpenCommand.COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof OpenCommand);
    }

    @Test
    public void parseCommandSaveAs() throws Exception {
        assertTrue(parser.parseCommand(SaveAsCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof SaveAsCommand);
        assertTrue(parser
                .parseCommand(SaveAsCommand.COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof SaveAsCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandLocate() throws Exception {
        LocateCommand command = (LocateCommand) parser.parseCommand(
                LocateCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new LocateCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAliasLocate() throws Exception {
        LocateCommand command = (LocateCommand) parser.parseCommand(
                LocateCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new LocateCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandNavigate() throws Exception {
        NavigateCommand command = (NavigateCommand) parser.parseCommand(
                NavigateCommand.COMMAND_WORD + " " + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                        + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", DEFAULT_STATE_LOCK);
        Location from = new Location("NUS");
        Location to = new Location("Sentosa");
        assertEquals(new NavigateCommand(from, to, null, null, false, false),
                command);
    }

    @Test
    public void parseCommandAliasNavigate() throws Exception {
        NavigateCommand command = (NavigateCommand) parser.parseCommand(
                NavigateCommand.COMMAND_ALIAS + " " + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                        + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", DEFAULT_STATE_LOCK);
        Location from = new Location("NUS");
        Location to = new Location("Sentosa");
        assertEquals(new NavigateCommand(from, to, null, null, false, false),
                command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    /**
     * Checks if 2 ChangePrivacyCommands are equal
     * @param command the expected command
     * @param actualCommand the actual command
     * @return true if all the data are equal
     */
    private boolean changePrivacyCommandsEqual(ChangePrivacyCommand command, ChangePrivacyCommand actualCommand) {
        assertEquals(command.getIndex(), actualCommand.getIndex());
        assertEquals(command.getPps().getAddressIsPrivate(), actualCommand.getPps().getAddressIsPrivate());
        assertEquals(command.getPps().getNameIsPrivate(), actualCommand.getPps().getNameIsPrivate());
        assertEquals(command.getPps().getEmailIsPrivate(), actualCommand.getPps().getEmailIsPrivate());
        assertEquals(command.getPps().getPhoneIsPrivate(), actualCommand.getPps().getPhoneIsPrivate());
        return true;
    }
}
```
###### \java\seedu\address\logic\parser\ChangePrivacyCommandParserTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangePrivacyCommand;
import seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.PersonPrivacySettingsBuilder;

public class ChangePrivacyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE);

    private ChangePrivacyCommandParser parser = new ChangePrivacyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ChangePrivacyCommand.MESSAGE_NO_FIELDS);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Non boolean argument
        assertParseFailure(parser, "1" + " " + PREFIX_NAME + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_PHONE + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_EMAIL + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_ADDRESS + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_REMARK + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));

        // valid value followed by invalid value. The test case for invalid value  followed by valid value
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + " " + PREFIX_NAME + "true" + " " + PREFIX_NAME + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_PHONE + "true" + " " + PREFIX_PHONE + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_EMAIL + "true" + " " + PREFIX_EMAIL + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_ADDRESS + "true" + " " + PREFIX_ADDRESS + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_REMARK + "true" + " " + PREFIX_REMARK + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "true" + " " + PREFIX_EMAIL + "false"
                + " " + PREFIX_ADDRESS + "true" + " " + PREFIX_PHONE + "false";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("true")
                .setEmailPrivate("false").setAddressPrivate("true").setPhonePrivate("false").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);

    }

    @Test
    public void parse_someFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "true" + " " + PREFIX_EMAIL + "true";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("true")
                .setEmailPrivate("true").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "true";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("true").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws ParseException {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "true" + " " + PREFIX_EMAIL + "false"
                + " " + PREFIX_ADDRESS + "true" + " " + PREFIX_PHONE + "false" + " " + PREFIX_NAME + "false" + " "
                + PREFIX_EMAIL + "true" + " " + PREFIX_ADDRESS + "false" + " " + PREFIX_PHONE + "true";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("false")
                .setEmailPrivate("true").setAddressPrivate("false").setPhonePrivate("true").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws ParseException {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "notBoolean" + " " + PREFIX_NAME + "true";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("true").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);
    }

    /**
     * Checks if two ChangePrivacyCommands are equal by comparing their contents
     * @param expectedCommand The expected ChangePrivacyCommand
     * @param actualCommand The actual ChangePrivacyCommand
     */
    private void compareChangePrivacyCommand(ChangePrivacyCommand expectedCommand, ChangePrivacyCommand actualCommand) {
        assertEquals(expectedCommand.getIndex(), actualCommand.getIndex());
        assertEquals(expectedCommand.getPps().getAddressIsPrivate(), actualCommand.getPps().getAddressIsPrivate());
        assertEquals(expectedCommand.getPps().getNameIsPrivate(), actualCommand.getPps().getNameIsPrivate());
        assertEquals(expectedCommand.getPps().getEmailIsPrivate(), actualCommand.getPps().getEmailIsPrivate());
        assertEquals(expectedCommand.getPps().getPhoneIsPrivate(), actualCommand.getPps().getPhoneIsPrivate());
        assertEquals(expectedCommand.getPps().getRemarkIsPrivate(), actualCommand.getPps().getRemarkIsPrivate());
    }
}
```
###### \java\seedu\address\logic\parser\LocateCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.LocateCommand;

/**
 * Test scope: similar to {@code SelectPersonCommandParserTest}.
 * @see SelectPersonCommandParserTest
 */
public class LocateCommandParserTest {

    private LocateCommandParser parser = new LocateCommandParser();

    @Test
    public void parse_validArgs_returnsLocateCommand() {
        assertParseSuccess(parser, "1", new LocateCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\PrivacyLevelCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.PrivacyLevelCommand;

public class PrivacyLevelCommandParserTest {

    private PrivacyLevelCommandParser parser = new PrivacyLevelCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new PrivacyLevelCommand(INDEX_FIRST_PERSON.getOneBased()));
        assertParseSuccess(parser, "2", new PrivacyLevelCommand(INDEX_SECOND_PERSON.getOneBased()));
        assertParseSuccess(parser, "3", new PrivacyLevelCommand(INDEX_THIRD_PERSON.getOneBased()));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PrivacyLevelCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "???", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PrivacyLevelCommand.MESSAGE_USAGE));

    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ACCEPTED_FIELD_PARAMETERS;
import static seedu.address.logic.commands.SortCommand.ACCEPTED_LIST_PARAMETERS;
import static seedu.address.logic.commands.SortCommand.ACCEPTED_ORDER_PARAMETERS;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void no_arguments_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    //author charlesgoh
    @Test
    public void parse_wrongArguments_failure() {
        // no list specified
        assertParseFailure(parser,  ACCEPTED_FIELD_PARAMETERS.get(0) + " " + ACCEPTED_ORDER_PARAMETERS.get(0),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser,  ACCEPTED_LIST_PARAMETERS.get(0) + " " + ACCEPTED_ORDER_PARAMETERS.get(0),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // no order specified
        assertParseFailure(parser,  ACCEPTED_LIST_PARAMETERS.get(0) + " " + ACCEPTED_FIELD_PARAMETERS.get(0),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // list is person but field is for tasks
        assertParseFailure(parser,  ACCEPTED_LIST_PARAMETERS.get(0) + " " + ACCEPTED_FIELD_PARAMETERS.get(5)
                + " " + ACCEPTED_ORDER_PARAMETERS.get(0), String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // list is task but field is for person
        assertParseFailure(parser,  ACCEPTED_LIST_PARAMETERS.get(1) + " " + ACCEPTED_FIELD_PARAMETERS.get(0)
                + " " + ACCEPTED_ORDER_PARAMETERS.get(0), String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Incorrect test
        assertParseFailure(parser, "random text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

    }
```
###### \java\seedu\address\logic\parser\ThemeCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

public class ThemeCommandParserTest {
    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ThemeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ThemeCommand expectedFindPersonCommand =
                new ThemeCommand("light");
        assertParseSuccess(parser, "light", expectedFindPersonCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " light     ", expectedFindPersonCommand);
    }
}
```
###### \java\seedu\address\model\person\AddressTest.java
``` java
    @Test
    public void privateAddressIsHidden_success() throws IllegalValueException {
        Address a = new Address("Any Address", true);
        assertTrue(a.getIsPrivate());
        assertEquals(a.toString(), "<Private Address>");
    }
}
```
###### \java\seedu\address\model\person\EmailTest.java
``` java
    @Test
    public void privateEmailIsHidden_success() throws IllegalValueException {
        Email e = new Email("AnyEmail@example.com", true);
        assertTrue(e.getIsPrivate());
        assertEquals(e.toString(), "<Private Email>");
    }
}
```
###### \java\seedu\address\model\person\NameTest.java
``` java
    @Test
    public void privateNameIsHidden_success() throws IllegalValueException {
        Name n = new Name("Any Name", true);
        assertTrue(n.getIsPrivate());
        assertEquals(n.toString(), "<Private Name>");
    }
}
```
###### \java\seedu\address\model\person\PhoneTest.java
``` java
    @Test
    public void privatePhoneIsHidden_success() throws IllegalValueException {
        Phone p = new Phone("999", true);
        assertTrue(p.getIsPrivate());
        assertEquals(p.toString(), "<Private Phone>");
    }
}
```
###### \java\seedu\address\testutil\ModelStub.java
``` java
    @Override
    public void setPrivacyLevel(int level) {
        fail("This method should not be called.");
    }

    @Override
    public int getPrivacyLevel() {
        fail("This method should not be called.");
        return 0;
    }

    @Override
    public ReadOnlyPerson getPersonAtIndexFromAddressBook(int index) {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void setTheme(String theme) {
        fail("This method should not be called.");
    }

    @Override
    public String getTheme() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public HashMap<String, String> getStyleMap() {
        fail("This method should not be called.");
        return null;
    }
```
###### \java\seedu\address\testutil\PersonPrivacySettingsBuilder.java
``` java
import seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building PersonPrivacySettings objects.
 */
public class PersonPrivacySettingsBuilder {

    private PersonPrivacySettings pps;
    public PersonPrivacySettingsBuilder() {
        pps = new PersonPrivacySettings();
    }

    public PersonPrivacySettingsBuilder(PersonPrivacySettings pps) {
        this.pps = new PersonPrivacySettings(pps);
    }

    /**
     * Returns an {@code PersonPrivacySettings} with fields containing {@code person}'s privacy details
     */
    public PersonPrivacySettingsBuilder(ReadOnlyPerson person) {
        pps = new PersonPrivacySettings();
        pps.setNameIsPrivate(person.getName().getIsPrivate());
        pps.setPhoneIsPrivate(person.getPhone().getIsPrivate());
        pps.setEmailIsPrivate(person.getEmail().getIsPrivate());
        pps.setAddressIsPrivate(person.getAddress().getIsPrivate());
        pps.setRemarkIsPrivate(person.getRemark().getIsPrivate());
    }

    /**
     * Sets the {@code nameIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setNamePrivate(String name) {
        if (name.equals("Optional[true]") || name.equals("true")) {
            pps.setNameIsPrivate(true);
        } else if (name.equals("Optional[false]") || name.equals("false")) {
            pps.setNameIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of name should be true or false");
        }
        return this;
    }

    /**
     * Sets the {@code phoneIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setPhonePrivate(String phone) {
        if (phone.equals("Optional[true]") || phone.equals("true")) {
            pps.setPhoneIsPrivate(true);
        } else if (phone.equals("Optional[false]") || phone.equals("false")) {
            pps.setPhoneIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of phone should be true or false");
        }
        return this;
    }

    /**
     * Sets the {@code emailIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setEmailPrivate(String email) {
        if (email.equals("Optional[true]") || email.equals("true")) {
            pps.setEmailIsPrivate(true);
        } else if (email.equals("Optional[false]") || email.equals("false")) {
            pps.setEmailIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of email should be true or false");
        }
        return this;
    }

    /**
     * Sets the {@code addressIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setAddressPrivate(String address) {
        if (address.equals("Optional[true]") || address.equals("true")) {
            pps.setAddressIsPrivate(true);
        } else if (address.equals("Optional[false]") || address.equals("false")) {
            pps.setAddressIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of address should be true or false");
        }
        return this;
    }

    /**
     * Sets the {@code remarkIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setRemarkPrivate(String remark) {
        if (remark.equals("Optional[true]") || remark.equals("true")) {
            pps.setRemarkIsPrivate(true);
        } else if (remark.equals("Optional[false]") || remark.equals("false")) {
            pps.setRemarkIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of remark should be true or false");
        }
        return this;
    }

    public PersonPrivacySettings build() {
        return pps;
    }
}
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // google maps page of a person
        postNow(panelLocateEventStub);
        URL expectedMapUrl = new URL(GOOGLE_MAPS_URL_PREFIX
                + BOB.getAddress().toString().replaceAll(" ", "+") + GOOGLE_MAPS_URL_SUFFIX
                + "?dg=dbrw&newdg=1");

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedMapUrl, browserPanelHandle.getLoadedUrl());

        // google maps page of a person
        postNow(panelNavigateEventStub);
        URL expectedDirUrl = new URL(GOOGLE_MAPS_DIRECTIONS_PREFIX
                + "&origin="
                + panelNavigateEventStub.getFromLocation().toString().replaceAll("#(\\w+)\\s*", "")
                .replaceAll(" ", "+").replaceAll("-(\\w+)\\s*", "")
                + "&destination="
                + panelNavigateEventStub.getToLocation().toString().replaceAll("#(\\w+)\\s*", "")
                .replaceAll(" ", "+").replaceAll("-(\\w+)\\s*", "")
                + GOOGLE_MAPS_DIRECTIONS_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedDirUrl, browserPanelHandle.getLoadedUrl());
    }
}
```
