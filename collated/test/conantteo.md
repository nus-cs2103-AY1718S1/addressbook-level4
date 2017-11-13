# conantteo
###### /java/guitests/AddressBookGuiTest.java
``` java
    protected GroupListPanelHandle getGroupListPanel() {
        return mainWindowHandle.getGroupListPanel();
    }

```
###### /java/guitests/guihandles/GroupLabelHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a {@code groupLabel} in the GroupListPanel.
 */
public class GroupLabelHandle extends NodeHandle<Node> {
    private static final String GROUP_FIELD_ID = "#groupName";

    private final Label groupLabel;

    private boolean isGroupLabelClicked = false;

    public GroupLabelHandle(Node labelNode) {
        super(labelNode);
        this.isGroupLabelClicked = false;
        this.groupLabel = getChildNode(GROUP_FIELD_ID);
    }

    // isGroupLabelClicked returns true after guiRobot has click the rootNode
    @Override
    public void click() {
        guiRobot.clickOn(getRootNode());
        this.isGroupLabelClicked = true;
    }

    public boolean isClicked() {
        return isGroupLabelClicked;
    }

    public String getGroupName() {
        return groupLabel.getText();
    }
}
```
###### /java/guitests/guihandles/GroupListPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.tag.Tag;
import seedu.address.ui.GroupLabel;

/**
 * Provides a handle for {@code GroupListPanel} containing a list of {@code GroupLabel}
 */
public class GroupListPanelHandle extends NodeHandle<ListView<GroupLabel>> {
    public static final String GROUP_LIST_VIEW_ID = "#groupListView";

    public GroupListPanelHandle(ListView<GroupLabel> groupListPanelNode) {
        super(groupListPanelNode);
    }

    /**
     * Navigates the list view to display and select the particular tag {@param toTag}
     */
    public void navigateToTag(Tag toTag) {
        List<GroupLabel> groupLabels = getRootNode().getItems();
        Optional<GroupLabel> matchingTag = groupLabels.stream().filter(groupLabel -> groupLabel
                .getTag().equals(toTag)).findFirst();

        if (!matchingTag.isPresent()) {
            throw new IllegalArgumentException("Tag does not exists");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingTag.get());
            getRootNode().getSelectionModel().select(matchingTag.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the GroupLabelHandle of a tag specified by {@code index} in this list.
     */
    public GroupLabelHandle getGroupLabelHandle(int index) {
        return getGroupLabelHandle(getRootNode().getItems().get(index).getTag());
    }

    public GroupLabelHandle getGroupLabelHandle(Tag tag) {
        Optional<GroupLabelHandle> handle = getRootNode().getItems().stream()
                .filter(label -> label.getTag().equals(tag))
                .map(label -> new GroupLabelHandle(label.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Tag does not exist."));
    }
}
```
###### /java/seedu/address/commons/util/IndexArrayUtilTest.java
``` java
package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;

public class IndexArrayUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void compareIndexArrays_atLeastOneNullArray_throwException() {
        assertExceptionThrown(NullPointerException.class, new Index[1], null, Optional.empty());
    }

    @Test
    public void compareIndexArrays_differentLengthArrays_invalidComparison() {
        assertFalse(assertComparison(new Index[0], new Index[1]));
    }

    @Test
    public void compareIndexArrays_sameLengthDiffElements_invalidComparison() {
        Index[] arr1 = {INDEX_FIRST_PERSON};
        Index[] arr2 = {INDEX_SECOND_PERSON};
        assertFalse(assertComparison(arr1, arr2));
    }

    @Test
    public void compareIndexArrays_sameLengthSameElements_validComparison() {
        Index[] arr1 = {INDEX_FIRST_PERSON};
        Index[] arr2 = {INDEX_FIRST_PERSON};
        assertTrue(assertComparison(arr1, arr2));
    }

    @Test
    public void swapFirstWithSecond_positionChanged_successful() {
        Index[] beforeSwap = {INDEX_THIRD_PERSON, INDEX_SECOND_PERSON, INDEX_FIRST_PERSON};
        Index[] afterSwap = {INDEX_SECOND_PERSON, INDEX_THIRD_PERSON, INDEX_FIRST_PERSON};
        IndexArrayUtil.swapElements(beforeSwap, 0);
        assertTrue(beforeSwap[0].equals(afterSwap[0]));
        assertTrue(beforeSwap[1].equals(afterSwap[1]));
        assertTrue(beforeSwap[2].equals(afterSwap[2]));
    }

    @Test
    public void arrayElements_areUnique() {
        Index[] distinctArray = {INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON};
        assertTrue(IndexArrayUtil.indexAreUnique(distinctArray));
    }

    @Test
    public void arrayElements_areNotUnique() {
        Index[] notDistinctArray = {INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, INDEX_THIRD_PERSON};
        assertFalse(IndexArrayUtil.indexAreUnique(notDistinctArray));
    }

    private void assertExceptionThrown(Class<? extends Throwable> exceptionClass, Index[] arr1, Index[] arr2,
                                       Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        IndexArrayUtil.compareIndexArrays(arr1, arr2);
    }

    private boolean assertComparison(Index[] arr1, Index[] arr2) {
        return IndexArrayUtil.compareIndexArrays(arr1, arr2);
    }
}
```
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
    @Test
    public void getSubstringFromIndexTwo_success() {
        // valid substring result
        assertEquals(StringUtil.getSubstringFromIndexTwo("aaa bbb ccc"), "a bbb ccc");

        // valid substring result with white spaces in between
        assertEquals(StringUtil.getSubstringFromIndexTwo(", aaa bbb ccc"), "aaa bbb ccc");

        // valid substring with trailing white spaces trimmed first
        assertEquals(StringUtil.getSubstringFromIndexTwo("  aaa bbb ccc"), "a bbb ccc");
    }

    @Test
    public void getSubStringFromIndexTwo_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.getSubstringFromIndexTwo(null);
    }

    @Test
    public void replaceWhiteSpaceWithComma_success() {
        // valid result single white space but trailing white spaces are trimmed first
        assertEquals(StringUtil.replaceWhiteSpaceWithComma(" "), "");

        // valid result multiple white spaces replaced with commas
        assertEquals(StringUtil.replaceWhiteSpaceWithComma("  a b c d  "), "a,b,c,d");

        // valid result with no white spaces to replace
        assertEquals(StringUtil.replaceWhiteSpaceWithComma("abc"), "abc");
    }

    @Test
    public void replaceWhiteSpaceWithComma_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.replaceWhiteSpaceWithComma(null);
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.EmailRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

public class EmailCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    //Model simply provides the required names of recipients and are not modified in any way.
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index[] indexArray;

    @Test
    public void execute_emailRequestEvent_success() throws CommandException {
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);
        CommandResult result = emailCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof EmailRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);

    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        ReadOnlyPerson personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS,
                personToEmail.getName().toString());
        CommandResult result = emailCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundIndex.getOneBased();

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS,
                personToEmail.getName().toString());
        CommandResult result = emailCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundIndex.getOneBased();

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void execute_multipleValidIndex_success() throws CommandException {
        ReadOnlyPerson firstPersonToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondPersonToEmail = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS,
                firstPersonToEmail.getName().toString() + ", " + secondPersonToEmail.getName().toString());
        CommandResult result = emailCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_validIndexWithoutEmail_throwsCommandException() {
        EmailCommand emailCommand = prepareCommand(INDEX_THIRD_PERSON);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_TO_EMAIL
                + INDEX_THIRD_PERSON.getOneBased();

        assertCommandFailure(emailCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        EmailCommand emailFirstCommand = prepareCommand(INDEX_FIRST_PERSON);
        EmailCommand emailSecondCommand = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(emailFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }

    /**
     * Returns a {@code EmailCommand} using one index parameter {@code index}
     */
    private EmailCommand prepareCommand(Index index) {
        indexArray = new Index[1];
        indexArray[0] = index;

        EmailCommand emailCommand = new EmailCommand(indexArray);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return emailCommand;
    }
    /**
     * Returns a {@code EmailCommand} using two index parameter {@code index1, index2}
     */
    private EmailCommand prepareCommand(Index index1, Index index2) {
        indexArray = new Index[2];
        indexArray[0] = index1;
        indexArray[1] = index2;

        EmailCommand emailCommand = new EmailCommand(indexArray);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return emailCommand;
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.MULTIPLE_INDEX;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ExportCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    //Model simply provides the required details of recipients and are not modified in any way.
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index[] indexArray;

    @Test
    public void execute_exportRequestEvent_success() throws CommandException {
        ExportCommand exportCommand = prepareCommand(INDEX_FIRST_PERSON);
        CommandResult result = exportCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExportRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        ReadOnlyPerson personToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ExportCommand exportCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                personToExport.getName().toString());
        CommandResult result = exportCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ExportCommand exportCommand = prepareCommand(outOfBoundIndex);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundIndex.getOneBased());

        assertCommandFailure(exportCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ExportCommand exportCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                personToExport.getName().toString());
        CommandResult result = exportCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ExportCommand exportCommand = prepareCommand(outOfBoundIndex);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundIndex.getOneBased();

        assertCommandFailure(exportCommand, model, expectedMessage);
    }

    @Test
    public void execute_multipleValidIndex_success() throws CommandException {
        ReadOnlyPerson firstPersonToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondPersonToExport = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        ExportCommand exportCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                firstPersonToExport.getName().toString() + ", " + secondPersonToExport.getName().toString());
        CommandResult result = exportCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_all_success() throws CommandException {
        // Preparing command to export all contacts
        ExportCommand exportCommand = new ExportCommand();
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        // StringBuilder for building the names of each person
        StringBuilder allPerson = new StringBuilder();
        model.getFilteredPersonList().stream().forEach((
                person) -> allPerson.append(person.getName().toString() + ", "));
        // expectedMessage to remove the trailing ',' when building the message
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                allPerson.toString().substring(0, allPerson.length() - 2));
        CommandResult result = exportCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }
    @Test
    public void equals() {
        ExportCommand exportAllCommand = new ExportCommand();
        ExportCommand exportSomeCommand = new ExportCommand(MULTIPLE_INDEX);

        // same object -> returns true
        assertTrue(exportAllCommand.equals(exportAllCommand));
        assertTrue(exportSomeCommand.equals(exportSomeCommand));

        // same values -> returns true
        ExportCommand exportSomeCommandCopy = new ExportCommand(MULTIPLE_INDEX);
        assertTrue(exportSomeCommand.equals(exportSomeCommandCopy));

        // different types -> returns false
        assertFalse(exportSomeCommand.equals(1));

        //null -> returns false
        assertFalse(exportSomeCommand.equals(null));

        // different commands -> returns false
        assertFalse(exportAllCommand.equals(exportSomeCommand));
    }

    /**
     * Returns a {@code ExportCommand} using one index parameter {@code index}
     */
    private ExportCommand prepareCommand(Index index) {
        indexArray = new Index[1];
        indexArray[0] = index;

        ExportCommand exportCommand = new ExportCommand(indexArray);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return exportCommand;
    }

    /**
     * Returns a {@code ExportCommand} using two index parameter {@code index1, index2}
     */
    private ExportCommand prepareCommand(Index index1, Index index2) {
        indexArray = new Index[2];
        indexArray[0] = index1;
        indexArray[1] = index2;

        ExportCommand exportCommand = new ExportCommand(indexArray);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return exportCommand;
    }
}
```
###### /java/seedu/address/logic/commands/FindCommandTest.java
``` java
    @Test
    public void execute_birthdayMonth_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchBirthdayCommand("05");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(CARL));
    }

    /**
     * Parses {@code userInput} which is a birthday month represented by 2 digits into a {@code FindCommand}.
     */
    public FindCommand prepareSearchBirthdayCommand(String userInput) {
        FindCommand command = new FindCommand(new PersonContainsBirthdayPredicate(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

```
###### /java/seedu/address/logic/parser/EmailCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EmailCommand;

public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();
    private Index[] indexArray;

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        assertParseSuccess(parser, "1", prepareCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "1 2", prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatedArgs_throwsParseException() {
        assertParseFailure(parser, "1 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ParserUtil.MESSAGE_INDEX_DUPLICATES));
    }

    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index index) {
        indexArray = new Index[1];
        indexArray[0] = index;

        EmailCommand emailCommand = new EmailCommand(indexArray);

        return emailCommand;
    }

    /**
     * Returns a {@code EmailCommand} with more than one parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index firstIndex, Index secondIndex) {
        indexArray = new Index[2];
        indexArray[0] = firstIndex;
        indexArray[1] = secondIndex;

        EmailCommand emailCommand = new EmailCommand(indexArray);

        return emailCommand;
    }
}
```
###### /java/seedu/address/logic/parser/ExportCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.MULTIPLE_INDEX;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        assertParseSuccess(parser, "1", prepareCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "all", new ExportCommand());
        assertParseSuccess(parser, "1 2 3", new ExportCommand(MULTIPLE_INDEX));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatedArgs_throwsParseException() {
        assertParseFailure(parser, "1 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ParserUtil.MESSAGE_INDEX_DUPLICATES));
    }

    /**
     * Returns a {@code ExportCommand} with the parameter {@code index}.
     * @param index is an index specified by user input.
     * @return a ExportCommand.
     */
    private ExportCommand prepareCommand(Index index) {
        Index[] indexArray = new Index[1];
        indexArray[0] = index;

        ExportCommand exportCommand = new ExportCommand(indexArray);

        return exportCommand;
    }
}
```
###### /java/seedu/address/model/ModelManagerTest.java
``` java
    @Test
    public void handleSearchTagEvent_updateFilteredPersonList_success() {
        // AddressBook with Alice with tags{friends} & Benson with tags{friends, oweMoney}
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ModelManager modelManager = new ModelManager(addressBook, new UserPrefs());

        EventsCenter.getInstance().post(SEARCH_OWE_MONEY_TAG);

        String[] tagNameArr = {OWE_MONEY.tagName};
        modelManager.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Arrays.asList(tagNameArr)));
        // FilteredPersonList should only show Benson now
        assertTrue(modelManager.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).equals(BENSON));
    }
```
###### /java/seedu/address/model/person/BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {
        // invalid birthday format
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("02/03/190")); // less than 8 digits
        assertFalse(Birthday.isValidBirthday("02/03/19000")); // more than 8 digits
        assertFalse(Birthday.isValidBirthday("phone")); // non-numeric
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphanumeric
        assertFalse(Birthday.isValidBirthday("02.03-1990")); // invalid characters between digits
        assertFalse(Birthday.isValidBirthday("02031990")); // exactly 8 digits without any forward slash
        assertFalse(Birthday.isValidBirthday("29/02/1900")); // invalid leap day

        // valid birthday format
        assertTrue(Birthday.isValidBirthday("29/02/2000")); // valid leap day
        assertTrue(Birthday.isValidBirthday("02/03/1990")); // DD/MM/YYYY format consists of 8 digits and 2 '/'
    }

    @Test
    public void isValidBirthdayMonth() {
        // invalid birthday month
        assertFalse(Birthday.isValidMonth("111")); // more than 2 digits
        assertFalse(Birthday.isValidMonth("1")); // less than 2 digits
        assertFalse(Birthday.isValidMonth("00")); // exactly 2 digits but less than the range of [01 to 12]
        assertFalse(Birthday.isValidMonth("13")); // exactly 2 digits but above the range of [01 to 12]

        // valid birthday month
        assertTrue(Birthday.isValidMonth("01")); // exactly 2 digits between 01 to 12
    }
}
```
###### /java/seedu/address/model/person/VcardTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.Test;

public class VcardTest {

    private final String vCardBirthdayRegex = "\\b\\d{4}\\b\\-\\b\\d{2}\\b\\-\\b\\d{2}\\b";

    @Test
    public void valid_buildBirthdayString() {
        // invalid birthday string built
        assertFalse("".matches(vCardBirthdayRegex)); // empty string
        assertFalse(" ".matches(vCardBirthdayRegex)); // spaces only
        assertFalse("03071990".matches(vCardBirthdayRegex)); // only numeric characters
        assertFalse("03/07/1990".matches(vCardBirthdayRegex)); // contains invalid characters.
        assertFalse("03-07-1990".matches(vCardBirthdayRegex)); // wrong DD-MM-YYYY format
        assertFalse("03-07-199000".matches(vCardBirthdayRegex)); // contains invalid number of digits

        // valid birthday string
        assertTrue("1990-07-03".matches(vCardBirthdayRegex)); // YYYY-MM-DD format
    }

    @Test
    public void equals() {
        // same values -> return true
        Vcard uniqueVcard = createVcard(ALICE);
        Vcard uniqueVcardCopy = createVcard(ALICE);
        assertTrue(uniqueVcard.equals(uniqueVcardCopy));

        // same object -> return true
        assertTrue(uniqueVcard.equals(uniqueVcard));

        // null -> returns false
        assertFalse(uniqueVcard.equals(null));

        // different types -> returns false
        assertFalse(uniqueVcard.equals(5));

        // different person -> return false
        Vcard anotherUnqiueVcard = createVcard(BENSON);
        assertFalse(uniqueVcard.equals(anotherUnqiueVcard));
    }

    /**
     * Creates a new Vcard for a {@code person} using the person's details.
     * @param person is a valid person.
     * @return the a Vcard for a person.
     */
    private Vcard createVcard(ReadOnlyPerson person) {
        Vcard vCard = new Vcard(person);
        return vCard;
    }
}
```
###### /java/seedu/address/storage/XmlAddressBookStorageTest.java
``` java
        //Save in a backup file and read back
        xmlAddressBookStorage.backupAddressBook(original);
        readBack = xmlAddressBookStorage.readAddressBook(filePath + "-backup.xml").get();
        assertEquals(original, new AddressBook(readBack));

    }
```
###### /java/seedu/address/testutil/TagBuilder.java
``` java
package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to create Tag objects
 */
public class TagBuilder {

    public static final String DEFAULT_TAG_NAME = "friends";

    private Tag tag;

    public TagBuilder() {
        try {
            this.tag = new Tag(DEFAULT_TAG_NAME);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default tag name is invalid");
        }
    }

    /**
     * Create a new Tag {@code tag} with the specified tag name {@code tagName}
     * @param tagName must be non-null and is a valid tag name
     * @return a new Tag of specified name.
     */
    public Tag withSpecifiedTagName(String tagName) {
        requireNonNull(tagName);
        try {
            tag = new Tag(tagName);
        } catch (IllegalValueException e) {
            throw new AssertionError("Specified tag name is invalid");
        }
        return tag;
    }

}
```
###### /java/seedu/address/testutil/TypicalIndexes.java
``` java
    public static final Index[] MULTIPLE_INDEX = {INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON};
}
```
###### /java/seedu/address/testutil/TypicalTags.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {

    public static final Tag GOOGLE = new TagBuilder().withSpecifiedTagName("Google");

    public static final Tag FACEBOOK = new TagBuilder().withSpecifiedTagName("Facebook");

    public static final Tag CLASSMATES = new TagBuilder().withSpecifiedTagName("Classmates");

    public static final Tag FRIENDS = new TagBuilder().withSpecifiedTagName("friends");

    public static final Tag OWE_MONEY = new TagBuilder().withSpecifiedTagName("owesMoney");

    private TypicalTags() {} // prevents instantiation

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(GOOGLE, FACEBOOK, CLASSMATES, FRIENDS, OWE_MONEY));
    }
}
```
###### /java/seedu/address/ui/GroupLabelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalTags.CLASSMATES;
import static seedu.address.testutil.TypicalTags.FACEBOOK;
import static seedu.address.testutil.TypicalTags.FRIENDS;
import static seedu.address.testutil.TypicalTags.GOOGLE;
import static seedu.address.testutil.TypicalTags.OWE_MONEY;
import static seedu.address.ui.testutil.GuiTestAssert.assertGroupLabelDisplayTag;

import org.junit.Test;

import guitests.guihandles.GroupLabelHandle;
import seedu.address.model.tag.Tag;

public class GroupLabelTest extends GuiUnitTest {

    private Tag testTag;
    private GroupLabel testGroupLabel;

    @Test
    public void display() {
        // Label with a tag
        testTag = GOOGLE;
        testGroupLabel = new GroupLabel(testTag);
        uiPartRule.setUiPart(testGroupLabel);

        assertTagBoxDisplay(testGroupLabel, testTag);

        // Changes label to reflect another new tag
        Tag anotherTag = FACEBOOK;
        testGroupLabel = new GroupLabel(anotherTag);
        uiPartRule.setUiPart(testGroupLabel);

        assertTagBoxDisplay(testGroupLabel, anotherTag);
    }

    @Test
    public void mouseEventHandler_tagBoxIsClicked() {
        testTag = OWE_MONEY;
        testGroupLabel = new GroupLabel(testTag);
        uiPartRule.setUiPart(testGroupLabel);

        GroupLabelHandle groupLabelHandle = new GroupLabelHandle(testGroupLabel.getRoot());
        groupLabelHandle.click();

        guiRobot.waitForEvent(groupLabelHandle::isClicked);

        assertTrue(groupLabelHandle.isClicked());
    }

    @Test
    public void equals() {
        testTag = FRIENDS;
        GroupLabel groupLabel = new GroupLabel(testTag);

        // same object -> returns true
        assertTrue(groupLabel.equals(groupLabel));

        // null -> returns false
        assertFalse(groupLabel.equals(null));

        // different types -> returns false
        Tag differentTag = CLASSMATES;
        assertFalse(groupLabel.equals(new GroupLabel(differentTag)));
    }

    /**
     * Asserts that {@code groupLabel} displays the {@code tag} correctly.
     */
    private void assertTagBoxDisplay(GroupLabel groupLabel, Tag tag) {
        guiRobot.pauseForHuman();

        GroupLabelHandle groupLabelHandle = new GroupLabelHandle(groupLabel.getRoot());

        assertGroupLabelDisplayTag(tag, groupLabelHandle);
    }
}
```
###### /java/seedu/address/ui/GroupListPanelTest.java
``` java
package seedu.address.ui;

import static seedu.address.testutil.TypicalTags.getTypicalTags;
import static seedu.address.ui.testutil.GuiTestAssert.assertGroupLabelDisplayTag;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GroupLabelHandle;
import guitests.guihandles.GroupListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;

public class GroupListPanelTest extends GuiUnitTest {
    private static final ObservableList<Tag> TYPICAL_TAGS = FXCollections.observableList(getTypicalTags());
    private GroupListPanelHandle groupListPanelHandle;

    @Before
    public void setUp() {
        GroupListPanel groupListPanel = new GroupListPanel(TYPICAL_TAGS);
        uiPartRule.setUiPart(groupListPanel);

        groupListPanelHandle = new GroupListPanelHandle(getChildNode(groupListPanel.getRoot(),
                GroupListPanelHandle.GROUP_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TAGS.size(); i++) {
            groupListPanelHandle.navigateToTag(TYPICAL_TAGS.get(i));
            Tag expectedTag = TYPICAL_TAGS.get(i);
            GroupLabelHandle actualTagBox = groupListPanelHandle.getGroupLabelHandle(i);

            assertGroupLabelDisplayTag(expectedTag, actualTagBox);
        }
    }
}
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that the list in {@code groupListPanelHandle} displays the name of the {@code tags} correctly.
     */
    public static void assertGroupListMatching(GroupListPanelHandle groupListPanelHandle, List<Tag> tags) {
        assertGroupListMatching(groupListPanelHandle, tags.toArray(new Tag[0]));
    }

    /**
     * Asserts that the list in {@code groupListPanelHandle} displays the name of the {@code tags} correctly.
     */
    public static void assertGroupListMatching(GroupListPanelHandle groupListPanelHandle, Tag... tags) {
        for (int i = 0; i < tags.length; i++) {
            assertGroupLabelDisplayTag(tags[i], groupListPanelHandle.getGroupLabelHandle(tags[i]));
        }
    }

    //@@ author
    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    public static void assertGroupLabelDisplayTag(Tag expectedTag, GroupLabelHandle groupLabel) {
        assertEquals(expectedTag.tagName, groupLabel.getGroupName());
    }

```
