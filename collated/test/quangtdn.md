# quangtdn
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getProfilePage() {
        return profileLabel.getText();
    }
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;



/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        List<ReadOnlyPerson> listPersonsToDelete = new ArrayList<ReadOnlyPerson>();
        listPersonsToDelete.add(personToDelete);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, listPersonsToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);
        List<ReadOnlyPerson> listPersonsToDelete = new ArrayList<ReadOnlyPerson>();
        listPersonsToDelete.add(personToDelete);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, listPersonsToDelete);


        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        List<Index> deleteList = new ArrayList<>();
        deleteList.add(INDEX_FIRST_PERSON);
        deleteList.add(INDEX_SECOND_PERSON);
        DeleteCommand deleteListCommand = new DeleteCommand(deleteList);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteListCommand.equals(deleteListCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        DeleteCommand deleteListCommandCopy = new DeleteCommand(deleteList);
        assertTrue(deleteListCommand.equals(deleteListCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteListCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
        assertFalse(deleteListCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different number of input arguments -> return false
        assertFalse(deleteFirstCommand.equals(deleteListCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
```
###### \java\seedu\address\logic\commands\FindPhoneCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsPhonePredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindPhoneCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsPhonePredicate firstPredicate =
                new NameContainsPhonePredicate(Collections.singletonList("12345678"));
        NameContainsPhonePredicate secondPredicate =
                new NameContainsPhonePredicate(Collections.singletonList("87654321"));

        FindPhoneCommand findPhoneFirstCommand = new FindPhoneCommand(firstPredicate);
        FindPhoneCommand findPhoneSecondCommand = new FindPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findPhoneFirstCommand.equals(findPhoneFirstCommand));

        // same values -> returns true
        FindPhoneCommand findPhoneFirstCommandCopy = new FindPhoneCommand(firstPredicate);
        assertTrue(findPhoneFirstCommand.equals(findPhoneFirstCommandCopy));

        // different types -> returns false
        assertFalse(findPhoneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findPhoneFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findPhoneFirstCommand.equals(findPhoneSecondCommand));
    }

    @Test
    public void execute_noInputNumber_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindPhoneCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleNumbers_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindPhoneCommand command = prepareCommand(
                CARL.getPhone().toString() + " " + ELLE.getPhone().toString() + " 1111222");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindPhoneCommand prepareCommand(String userInput) {
        FindPhoneCommand command =
                new FindPhoneCommand(new NameContainsPhonePredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPhoneCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\FindPhoneCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.model.person.NameContainsPhonePredicate;

public class FindPhoneCommandParserTest {

    private FindPhoneCommandParser parser = new FindPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        String firstPersonPhone = ALICE.getPhone().toString();
        String secondPersonPhone = BENSON.getPhone().toString();
        FindPhoneCommand expectedFindPhoneCommand =
                new FindPhoneCommand(new NameContainsPhonePredicate(Arrays.asList(firstPersonPhone,
                        secondPersonPhone)));
        assertParseSuccess(parser, firstPersonPhone + " " + secondPersonPhone, expectedFindPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + firstPersonPhone + " \n \t " + secondPersonPhone + "  \t",
                expectedFindPhoneCommand);
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code ProfilePage} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withProfilePage(String profile) {
        try {
            ParserUtil.parseProfilePage(Optional.of(profile)).ifPresent(descriptor::setProfilePage);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Profile Page is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_PROFILEPAGE = "www.facebook.com";
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code ProfilePage} of the {@code Person} that we are building.
     */
    public PersonBuilder withProfilePage(String profile) {
        try {
            this.person.setProfilePage(new ProfilePage(profile));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("profile page is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL("https://" + ALICE.getProfilePage().toString());
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the browser's url is changed to display the details of the person in the person list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        String selectedCardName = getPersonListPanel().getHandleToSelectedCard().getName();
        String selectedCardProfile = getPersonListPanel().getHandleToSelectedCard().getProfilePage();
        URL expectedUrl;
        try {
            if (selectedCardProfile.equals("")) {
                expectedUrl = new URL(GOOGLE_SEARCH_URL_PREFIX + selectedCardName.replaceAll(" ", "+")
                        + GOOGLE_SEARCH_URL_SUFFIX);
            } else if (selectedCardProfile.contains("facebook")) {
                //Facebook will link to the login page
                expectedUrl = new URL("https://m.facebook.com/?refsrc=https%3A%2F%2Fwww.facebook.com%2F&_rdr");
            } else {
                expectedUrl = new URL("http://" + selectedCardProfile);
            }
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.");
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
    @Test
    public void deleteMultiplePersons() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first and second persons in the list, command with leading spaces and trailing spaces
        * -> deleted
        */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "    "
                + INDEX_SECOND_PERSON.getOneBased() + "  ";
        Index firstIndex = INDEX_FIRST_PERSON;
        Index secondIndex = INDEX_SECOND_PERSON;
        List<Index> indicesToDelete = new ArrayList<>();
        indicesToDelete.add(firstIndex);
        indicesToDelete.add(secondIndex);
        List<ReadOnlyPerson> listPersonsToDelete = removePersonsList(expectedModel, indicesToDelete); ;
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, listPersonsToDelete);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, delete the first 2 indices within bounds of address book and person list
        *-> deleted*/

        showPersonsWithName("Me");
        firstIndex = INDEX_FIRST_PERSON;
        secondIndex = INDEX_SECOND_PERSON;
        assertTrue((firstIndex.getZeroBased() < getModel().getFilteredPersonList().size())
                    && (secondIndex.getZeroBased() < getModel().getFilteredPersonList().size()));
        List<Index> deletedIndices = new ArrayList<>();
        deletedIndices.add(firstIndex);
        deletedIndices.add(secondIndex);
        assertCommandSuccess(deletedIndices);

        /* Case: filtered person list, delete 2 indeces where one index is valid, and the other is within bounds of
        *address book but out of bounds of person list
        * -> rejected
        */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DeleteCommand.COMMAND_WORD + " 1 " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);


        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) at the beginning of index list -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0 1 2";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (0) at the middle of index list -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 1 0 2";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1)  at the beginning of index list-> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1 2 3";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) at the middle of index list -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 2 -1 3";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) at the beginning of index list-> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased() + " 1 2";
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid index (size + 1) at the middle of index list-> rejected */

        outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " 2 " + outOfBoundsIndex.getOneBased() + " 1";
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1 2", MESSAGE_UNKNOWN_COMMAND);
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
     * Removes all the {@code ReadOnlyPerson}'s at the specified {@code index}'s in {@code model}'s address book.
     * @return the removed person
     */
    private List<ReadOnlyPerson> removePersonsList(Model model, List<Index> listTargetIndices) {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        List<ReadOnlyPerson> listPersonsToDelete = new ArrayList<ReadOnlyPerson>();
        for (Index targetIndex: listTargetIndices) {
            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
            listPersonsToDelete.add(personToDelete);
        }

        try {
            for (ReadOnlyPerson personToDelete : listPersonsToDelete) {
                model.deletePerson(personToDelete);
            }
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }

        return listPersonsToDelete;
    }

    /**
     * Deletes the person at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ReadOnlyPerson deletedPerson = removePerson(expectedModel, toDelete);
        List<ReadOnlyPerson> listPersonsToDelete = new ArrayList<ReadOnlyPerson>();
        listPersonsToDelete.add(deletedPerson);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, listPersonsToDelete);
        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(),
                expectedModel, expectedResultMessage);
    }

    /**
     * Deletes a list of persons at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(List<Index> toDelete) {
        Model expectedModel = getModel();
        String command = DeleteCommand.COMMAND_WORD;
        List<ReadOnlyPerson> listPersonsToDelete = removePersonsList(expectedModel, toDelete);
        for (Index personToDelete: toDelete) {
            command = command + " " + personToDelete.getOneBased();
        }

        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, listPersonsToDelete);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
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
###### \java\systemtests\FindPhoneCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.PARTIAL_PHONE_MATCHING;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class FindPhoneCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple phones in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindPhoneCommand.COMMAND_WORD + " " + PARTIAL_PHONE_MATCHING + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // phones of Benson and DANIEL contain "8535"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find phone command where person list is displaying the persons with the phone that
         * we are finding -> 2 persons found
         */
        command = FindPhoneCommand.COMMAND_WORD + " " + PARTIAL_PHONE_MATCHING;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone where person list is not displaying the person with the phone that we are finding
         * -> 1 person found
         */
        command = FindPhoneCommand.COMMAND_WORD + " " + CARL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 phones -> 2 persons found */
        command = FindPhoneCommand.COMMAND_WORD + " " + BENSON.getPhone().value + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 phones in reversed order -> 2 persons found */
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getPhone().value + " " + BENSON.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 phones with 1 repeat -> 2 persons found */
        command = FindPhoneCommand.COMMAND_WORD +  " " + DANIEL.getPhone().value + " "
                + BENSON.getPhone().value + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple phones in address book, 2 matching phones and 1 non-matching phone
         * -> 2 persons found
         */
        String nonMatchingPhone = "91919191";
        command = FindPhoneCommand.COMMAND_WORD +  " " + DANIEL.getPhone().value + " "
                + BENSON.getPhone().value + " " + nonMatchingPhone;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find phone command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find phone command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons with previously specified phone in address book after deleting 1 of them
        * -> 1 person found
        */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindPhoneCommand.COMMAND_WORD + " " + PARTIAL_PHONE_MATCHING;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone in address book, input is substring of phone -> 2 persons found */
        command = FindPhoneCommand.COMMAND_WORD + " " + PARTIAL_PHONE_MATCHING;
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone in address book, phone is substring of input -> 0 persons found */
        command = FindPhoneCommand.COMMAND_WORD + " " + PARTIAL_PHONE_MATCHING + "000";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone not in address book -> 0 persons found */
        command = FindPhoneCommand.COMMAND_WORD + " 000000";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book -> 0 persons found */
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getName().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 0 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindPhoneCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindPhoneCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find phone in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindPhoneCommand.COMMAND_WORD + " " + PARTIAL_PHONE_MATCHING;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "pHOne " + PARTIAL_PHONE_MATCHING;
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
