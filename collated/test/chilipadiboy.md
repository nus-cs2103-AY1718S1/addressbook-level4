# chilipadiboy
###### \java\guitests\BirthdayAlarmWindowTest.java
``` java
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.BirthdayAlarmWindowHandle;
import seedu.address.logic.commands.BirthdayAlarmCommand;




public class BirthdayAlarmWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
        + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
        + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
        + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openBirthdayAlarmWindow() {
        //use menu button
        getMainMenu().openBirthdayAlarmWindowUsingMenu();
        assertBirthdayAlarmWindowOpen();

        //use command box
        runCommand(BirthdayAlarmCommand.COMMAND_WORD);
        assertBirthdayAlarmWindowOpen();
    }

    /**
     * Asserts that the Birthday Alarm window is open, and closes it after checking.
     */
    private void assertBirthdayAlarmWindowOpen() {
        assertTrue(ERROR_MESSAGE, BirthdayAlarmWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new BirthdayAlarmWindowHandle(guiRobot.getStage(BirthdayAlarmWindowHandle.BIRTHDAYALARM_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }
}
```
###### \java\guitests\guihandles\MainMenuHandle.java
``` java
    /**
     * Opens the {@code BirthdayAlarmWindow} using the menu bar in {@code MainWindow}.
     */

    public void openBirthdayAlarmWindowUsingMenu() {
        clickOnMenuItemsSequentially("Reminders", "Birthdays");
    }

    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }

    /**
     * Clicks on {@code menuItems} in order.
     */
    private void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(guiRobot::clickOn);
    }
}
```
###### \java\seedu\address\logic\commands\BirthdayAlarmCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.BirthdayAlarmCommand.SHOWING_REMINDERS_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowBirthdayAlarmRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class BirthdayAlarmCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_birthdayalarm_success() {
        CommandResult result = new BirthdayAlarmCommand().execute();
        assertEquals(SHOWING_REMINDERS_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowBirthdayAlarmRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("Some remark").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import guitests.AddressBookGuiTest;

import seedu.address.ui.testutil.EventsCollectorRule;


public class ThemeCommandTest extends AddressBookGuiTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void executethemechange() {
        CommandResult result = new ThemeCommand("light").execute();
        assertEquals(ThemeCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        CommandResult result2 = new ThemeCommand("dark").execute();
        assertEquals(ThemeCommand.MESSAGE_SUCCESS, result2.feedbackToUser);
    }
}
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_BIRTHDAY_DESC,
            Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS); // invalid birthday
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        //birthday
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthday(VALID_BIRTHDAY_AMY).build();
        expectedCommand =  new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //website
        userInput = targetIndex.getOneBased() + WEBSITE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withWebsite(VALID_WEBSITE_AMY).build();
        expectedCommand =  new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
        //@@ author
        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()  + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + WEBSITE_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + WEBSITE_DESC_AMY + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB  + BIRTHDAY_DESC_AMY + BIRTHDAY_DESC_BOB + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Remark remark = new Remark("Some remark.");

        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\systemtests\AddCommandAliasSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_EMAIL;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_PHONE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_WEBSITE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEBSITE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Website;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

/**
 * Tests add alias "a"
 */
public class AddCommandAliasSystemTest extends AddressBookSystemTest {

    @Test
    public void addAlias1() throws Exception {
        Model model = getModel();
        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyPerson toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_SHORT + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
            + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   "
            + BIRTHDAY_DESC_AMY + "   " + WEBSITE_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_SHORT;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_SHORT;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate person -> rejected */
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail is a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(ReadOnlyPerson)
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + " " + WEBSITE_DESC_AMY + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_BOB).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_BOB + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except website -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_BOB)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: filters the person list before adding -> added */
        executeCommand(FindCommand.COMMAND_SHORT + " " + KEYWORD_MATCHING_MEIER);
        assert getModel().getFilteredPersonList().size()
            < getModel().getAddressBook().getPersonList().size();
        assertCommandSuccess(IDA);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_SHORT);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_SHORT + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
            + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + WEBSITE_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        executeCommand(SelectCommand.COMMAND_SHORT + " 1");
        assert getPersonListPanel().isAnyCardSelected();
        assertCommandSuccess(CARL);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_SHORT + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(EMPTY_PHONE).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(EMPTY_EMAIL)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_BOB + PHONE_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(EMPTY_ADDRESS).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing website -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(EMPTY_WEBSITE)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(EMPTY_BIRTHDAY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_SHORT + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_SHORT + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + INVALID_BIRTHDAY_DESC + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        /* Case: invalid website -> rejected */
        command = AddCommand.COMMAND_SHORT + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + INVALID_WEBSITE_DESC;
        assertCommandFailure(command, Website.MESSAGE_WEBSITE_CONSTRAINTS);
    }

    /**
     * Tests add alias "curse"
     */
    @Test
    public void addAlias2() throws Exception {
        Model model = getModel();
        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyPerson toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_ALIAS + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
            + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   "
            + BIRTHDAY_DESC_AMY + "   " + WEBSITE_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_SHORT;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_SHORT;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate person -> rejected */
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail is a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(ReadOnlyPerson)
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + " " + WEBSITE_DESC_AMY + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_BOB).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_BOB + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except website -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_BOB)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: filters the person list before adding -> added */
        executeCommand(FindCommand.COMMAND_ALIAS + " " + KEYWORD_MATCHING_MEIER);
        assert getModel().getFilteredPersonList().size()
            < getModel().getAddressBook().getPersonList().size();
        assertCommandSuccess(IDA);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_ALIAS);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_ALIAS + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
            + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + BIRTHDAY_DESC_BOB + WEBSITE_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        executeCommand(SelectCommand.COMMAND_SHORT + " 1");
        assert getPersonListPanel().isAnyCardSelected();
        assertCommandSuccess(CARL);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_ALIAS + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(EMPTY_PHONE).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(EMPTY_EMAIL)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(EMPTY_ADDRESS).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing website -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY).withWebsite(EMPTY_WEBSITE)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withBirthday(EMPTY_BIRTHDAY).withWebsite(VALID_WEBSITE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_ALIAS + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + INVALID_BIRTHDAY_DESC + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        /* Case: invalid website -> rejected */
        command = AddCommand.COMMAND_ALIAS + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + BIRTHDAY_DESC_AMY + INVALID_WEBSITE_DESC;
        assertCommandFailure(command, Website.MESSAGE_WEBSITE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code AddCommand} with the
     * details of {@code toAdd}, and the model related components equal to the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyPerson toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
            + INVALID_BIRTHDAY_DESC + WEBSITE_DESC_AMY;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### \java\systemtests\ClearCommandAliasSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearCommandAliasSystemTest extends AddressBookSystemTest {

    /**
     * Tests add alias "c"
     */
    @Test
    public void clearAlias1() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty address book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_SHORT + " ab12   ");
        assertSelectedCardUnchanged();

        /* Case: undo clearing address book -> original address book restored */
        String command = UndoCommand.COMMAND_SHORT;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command,  expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo clearing address book -> cleared */
        command = RedoCommand.COMMAND_SHORT;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();

        /* Case: selects first card in person list and clears address book -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_SHORT); // restores the original address book
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_SHORT);
        assertSelectedCardDeselected();

        /* Case: filters the person list before clearing -> entire address book cleared */
        executeCommand(UndoCommand.COMMAND_SHORT); // restores the original address book
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(ClearCommand.COMMAND_SHORT);
        assertSelectedCardUnchanged();

        /* Case: clear empty address book -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_SHORT);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Tests add alias "purge"
     */
    @Test
    public void clearAlias2() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty address book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_ALIAS + " ab12   ");
        assertSelectedCardUnchanged();

        /* Case: undo clearing address book -> original address book restored */
        String command = UndoCommand.COMMAND_SHORT;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command,  expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo clearing address book -> cleared */
        command = RedoCommand.COMMAND_SHORT;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();

        /* Case: selects first card in person list and clears address book -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_SHORT); // restores the original address book
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_ALIAS);
        assertSelectedCardDeselected();

        /* Case: filters the person list before clearing -> entire address book cleared */
        executeCommand(UndoCommand.COMMAND_SHORT); // restores the original address book
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(ClearCommand.COMMAND_ALIAS);
        assertSelectedCardUnchanged();

        /* Case: clear empty address book -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_ALIAS);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\DeleteCommandAliasSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class DeleteCommandAliasSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    /**
     * Tests delete alias "d"
     */
    @Test
    public void deletealias1() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first person in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_SHORT + "      " + INDEX_FIRST_PERSON.getOneBased()
            + "       ";
        ReadOnlyPerson deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last person in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_SHORT;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_SHORT;
        removePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, delete index within bounds of address book and person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered person list, delete index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DeleteCommand.COMMAND_SHORT + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a person card is selected ------------------------ */

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = DeleteCommand.COMMAND_SHORT + " " + selectedIndex.getOneBased();
        deletedPerson = removePerson(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_SHORT + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_SHORT + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
            getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_SHORT + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_SHORT + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_SHORT + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Tests delete alias "purge"
     */
    @Test
    public void deletealias2() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first person in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_ALIAS + "      " + INDEX_FIRST_PERSON.getOneBased()
            + "       ";
        ReadOnlyPerson deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last person in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_SHORT;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_SHORT;
        removePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, delete index within bounds of address book and person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered person list, delete index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DeleteCommand.COMMAND_ALIAS + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a person card is selected ------------------------ */

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = DeleteCommand.COMMAND_ALIAS + " " + selectedIndex.getOneBased();
        deletedPerson = removePerson(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_ALIAS + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_ALIAS + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
            getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_ALIAS + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_ALIAS + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_ALIAS + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed person
     */
    private ReadOnlyPerson removePerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = getPerson(model, index);
        try {
            model.deletePerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Deletes the person at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ReadOnlyPerson deletedPerson = removePerson(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);

        assertCommandSuccess(
            DeleteCommand.COMMAND_SHORT + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\EditCommandAliasSystemTest.java
``` java
package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEBSITE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEBSITE_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Website;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;





public class EditCommandAliasSystemTest extends AddressBookSystemTest {

    /**
     * Tests edit alias "e"
     */
    @Test
    public void editalias1() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */

        Index index = INDEX_FIRST_PERSON;
        String command = " " + EditCommand.COMMAND_SHORT + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
            + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " "
            + WEBSITE_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Person editedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withWebsite(VALID_WEBSITE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: undo editing the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_SHORT;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_SHORT;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
            getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a person with new values same as existing values -> edited */
        command = EditCommand.COMMAND_SHORT + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + WEBSITE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_SHORT + " " + index.getOneBased() + TAG_DESC_FRIEND;
        ReadOnlyPerson personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withTags(VALID_TAG_FRIEND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_SHORT + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedPerson = new PersonBuilder(personToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPerson);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered person list, edit index within bounds of address book and person list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = EditCommand.COMMAND_SHORT + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + invalidIndex + NAME_DESC_BOB,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a person card is selected -------------------------- */

        /* Case: selects first card in the person list, edit a person -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = EditCommand.COMMAND_SHORT + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new person's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " 0" + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " -1" + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + invalidIndex + NAME_DESC_BOB,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + INDEX_FIRST_PERSON.getOneBased(),
            EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_NAME_DESC,
            Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_PHONE_DESC,
            Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_EMAIL_DESC,
            Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_ADDRESS_DESC,
            Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid website -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_WEBSITE_DESC,
            Website.MESSAGE_WEBSITE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_SHORT + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_TAG_DESC,
            Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a person with new values same as another person's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(BOB));
        assertTrue(getModel().getAddressBook().getPersonList().contains(BOB));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredPersonList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_SHORT + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + WEBSITE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different tags -> rejected */
        command = EditCommand.COMMAND_SHORT + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Tests edit alias "mutate"
     */
    @Test
    public void editalias2() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */

        Index index = INDEX_FIRST_PERSON;
        String command = " " + EditCommand.COMMAND_ALIAS + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
            + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " "
            + WEBSITE_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Person editedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withWebsite(VALID_WEBSITE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: undo editing the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_SHORT;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_SHORT;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
            getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a person with new values same as existing values -> edited */
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + WEBSITE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + TAG_DESC_FRIEND;
        ReadOnlyPerson personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withTags(VALID_TAG_FRIEND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedPerson = new PersonBuilder(personToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPerson);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered person list, edit index within bounds of address book and person list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + invalidIndex + NAME_DESC_BOB,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a person card is selected -------------------------- */

        /* Case: selects first card in the person list, edit a person -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY
            + EMAIL_DESC_AMY
            + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + WEBSITE_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new person's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " 0" + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " -1" + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + invalidIndex + NAME_DESC_BOB,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + NAME_DESC_BOB,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(),
            EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_NAME_DESC,
            Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_PHONE_DESC,
            Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_EMAIL_DESC,
            Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_ADDRESS_DESC,
            Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid website -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + INVALID_WEBSITE_DESC,
            Website.MESSAGE_WEBSITE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased()
                + INVALID_TAG_DESC,
            Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a person with new values same as another person's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(BOB));
        assertTrue(getModel().getAddressBook().getPersonList().contains(BOB));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredPersonList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + WEBSITE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different tags -> rejected */
        command = EditCommand.COMMAND_ALIAS + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
            + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB + WEBSITE_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyPerson, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyPerson, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyPerson editedPerson) {
        assertCommandSuccess(command, toEdit, editedPerson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyPerson editedPerson,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updatePerson(
                expectedModel.getFilteredPersonList().get(toEdit.getZeroBased()), editedPerson);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
                "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
            String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\FindCommandAliasSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindCommandAliasSystemTest extends AddressBookSystemTest {

    /**
     * Test find alias "f"
     */
    @Test
    public void findalias1() {
        String command;
        Model expectedModel;

        /* Test: find with one attribute */

        /* Case: find a person not in address book
         * -> 0 persons found
         */
        command = FindCommand.COMMAND_SHORT + " n/Mark";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact full name
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + CARL.getName();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_PHONE + CARL.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact email
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_EMAIL + CARL.getEmail();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact address
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_ADDRESS + CARL.getAddress();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact website
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_WEBSITE + CARL.getWebsite();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact birthday
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_BIRTHDAY + CARL.getBirthday();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: find with one attribute with extra constraints */

        /*
         * Case: find a person in address book by name (non case-sensitive)
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "cARL kUrz";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but complete keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "cArL";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but complete keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "kUrZ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but substring keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "KUr";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name and incorrect keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "Krz";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete phone number (a substring)
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_PHONE + "35256";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Test: find with multiple attributes */

        /*
         * Case: find a person in address book by exact name and exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + BENSON.getName() + " "
            + PREFIX_PHONE + BENSON.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "Meier" + " " + PREFIX_PHONE + BENSON.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: find multiple persons */

        /* Case: find multiple persons in address book by name
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "Meier";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "Meier";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, non case-sensitive
         * -> 2 person found
         */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 2 persons found */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "Mei";
        ModelHelper.setFilteredList(expectedModel, DANIEL, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Test: display screen */

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + CARL.getName();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Test: interaction with other commands */

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_SHORT;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_SHORT;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "Meier";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        executeCommand(DeleteCommand.COMMAND_SHORT + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_SHORT + " " + PREFIX_NAME + "Meier";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_SHORT + " n/Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Test find alias "summon"
     */
    @Test
    public void findalias2() {
        String command;
        Model expectedModel;

        /* Test: find with one attribute */

        /* Case: find a person not in address book
         * -> 0 persons found
         */
        command = FindCommand.COMMAND_ALIAS + " n/Mark";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact full name
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + CARL.getName();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_PHONE + CARL.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact email
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_EMAIL + CARL.getEmail();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact address
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_ADDRESS + CARL.getAddress();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact website
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_WEBSITE + CARL.getWebsite();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by exact birthday
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_BIRTHDAY + CARL.getBirthday();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: find with one attribute with extra constraints */

        /*
         * Case: find a person in address book by name (non case-sensitive)
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "cARL kUrz";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but complete keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "cArL";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but complete keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "kUrZ";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but substring keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "KUr";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name and incorrect keyword
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "Krz";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete phone number (a substring)
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_PHONE + "35256";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete phone number (a permutation)
         * -> 0 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_PHONE + "25639535";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: find with multiple attributes */

        /*
         * Case: find a person in address book by exact name and exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + BENSON.getName() + " "
            + PREFIX_PHONE + BENSON.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /*
         * Case: find a person in address book by incomplete name but exact phone number
         * -> 1 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "Meier" + " " + PREFIX_PHONE + BENSON.getPhone();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: find multiple persons */

        /* Case: find multiple persons in address book by name
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "Meier";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "Meier";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, non case-sensitive
         * -> 2 person found
         */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 2 persons found */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "Mei";
        ModelHelper.setFilteredList(expectedModel, DANIEL, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Test: display screen */

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + CARL.getName();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Test: interaction with other commands */

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_SHORT;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_SHORT;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "Meier";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        executeCommand(DeleteCommand.COMMAND_ALIAS + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_ALIAS + " " + PREFIX_NAME + "Meier";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_ALIAS + " n/Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
            MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);

        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
