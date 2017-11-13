# AceCentury
###### /java/seedu/address/logic/commands/DeleteByNameCommandTest.java
``` java

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.DeleteByNameCommand.MESSAGE_SUGGESTED_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CaseInsensitiveExactNamePredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteByNameCommand}.
 */

public class DeleteByNameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = ALICE;
        DeleteByNameCommand deleteByNameCommand = prepareCommand(ALICE.getName());

        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() throws Exception {
        Name name = new Name("Fake name");
        DeleteByNameCommand deleteByNameCommand = prepareCommand(name);

        assertCommandFailure(deleteByNameCommand, model, Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
    }

    @Test
    public void execute_validNameFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = ALICE;
        DeleteByNameCommand deleteByNameCommand = prepareCommand(ALICE.getName());

        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_partialNameUnfilteredList_throwsCommandException() throws IllegalValueException {
        String bensonFirstName = Arrays.asList(BENSON.getName().toString().split(" ")).get(0).toLowerCase();
        List<String> predicateList = Arrays.asList(bensonFirstName);
        Name name = new Name(bensonFirstName);

        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(predicateList));
        DeleteByNameCommand deleteByNameCommand = prepareCommand(name);

        assertCommandFailure(deleteByNameCommand, model, MESSAGE_SUGGESTED_PERSONS);
    }

    @Test
    public void execute_partialNameFilteredList_throwsCommandException() throws IllegalValueException {
        showFirstPersonOnly(model);

        String carlFirstName = Arrays.asList(CARL.getName().toString().split(" ")).get(0).toLowerCase();
        List<String> predicateList = Arrays.asList(carlFirstName);
        Name name = new Name(carlFirstName);

        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(predicateList));
        DeleteByNameCommand deleteByNameCommand = prepareCommand(name);

        assertCommandFailure(deleteByNameCommand, model, MESSAGE_SUGGESTED_PERSONS);
    }

    /**
     * Tests the deletion of a person not shown in filtered list.
     */

    @Test
    public void execute_validNameNotInFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = CARL;
        DeleteByNameCommand deleteByNameCommand = prepareCommand(CARL.getName());

        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() throws IllegalValueException {
        showFirstPersonOnly(model);

        Name name = new Name("Fake name");
        DeleteByNameCommand deleteByNameCommand = prepareCommand(name);

        assertCommandFailure(deleteByNameCommand, model, Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
    }

    @Test
    public void execute_multiplePersonsWithSameNameFilteredList_throwsCommandException()
            throws IllegalValueException {

        showFirstPersonOnly(model);
        Person alice2 = new Person(ALICE);
        alice2.setPhone(new Phone("12345678"));
        model.addPerson(alice2);
        model.updateFilteredPersonList(new CaseInsensitiveExactNamePredicate(alice2.getName()));

        DeleteByNameCommand deleteByNameCommand = prepareCommand(alice2.getName());

        assertCommandFailure(deleteByNameCommand, model, deleteByNameCommand.MESSAGE_MULTIPLE_PERSON_WITH_SAME_NAME);
    }

    @Test
    public void execute_multiplePersonsWithSameNameUnfilteredList_throwsCommandException()
            throws IllegalValueException {

        Person alice2 = new Person(ALICE);
        alice2.setPhone(new Phone("12345678"));
        model.addPerson(alice2);
        model.updateFilteredPersonList(new CaseInsensitiveExactNamePredicate(alice2.getName()));

        DeleteByNameCommand deleteByNameCommand = prepareCommand(alice2.getName());

        assertCommandFailure(deleteByNameCommand, model, deleteByNameCommand.MESSAGE_MULTIPLE_PERSON_WITH_SAME_NAME);
    }

    @Test
    public void equals() {
        DeleteByNameCommand deleteFirstCommand = new DeleteByNameCommand(ALICE.getName());
        DeleteByNameCommand deleteSecondCommand = new DeleteByNameCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByNameCommand deleteFirstCommandCopy = new DeleteByNameCommand(ALICE.getName());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteByNameCommand} with the parameter {@code index}.
     */
    private DeleteByNameCommand prepareCommand(Name name) {
        DeleteByNameCommand deleteByNameCommand = new DeleteByNameCommand(name);
        deleteByNameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByNameCommand;
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
###### /java/seedu/address/logic/commands/ExportCommandTest.java
``` java

package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteByNameCommand}.
 */

public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addressBookExport_success() {
        ExportCommand exportCommand = prepareCommand();
        assertCommandSuccess(exportCommand, model, ExportCommand.MESSAGE_EXPORT_SUCCESS, model);
    }

    @Test
    public void execute_emptyAddressBookExport_throwsCommandException() throws Exception {
        List<ReadOnlyPerson> persons = model.getAddressBook().getPersonList();
        while (!persons.isEmpty()) {
            ReadOnlyPerson personToRemove = persons.get(0);
            model.deletePerson(personToRemove);
        }
        ExportCommand exportCommand = prepareCommand();
        assertCommandFailure(exportCommand, model, ExportCommand.MESSAGE_EMPTY_ADDRESS_BOOK);
    }

    /**
     * Returns a {@code ExportCommand}.
     */
    private ExportCommand prepareCommand() {
        ExportCommand exportCommand = new ExportCommand();
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportCommand;
    }
}
```
###### /java/seedu/address/logic/parser/DeleteByNameCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.model.person.Name;

public class DeleteByNameCommandParserTest {

    private DeleteByNameCommandParser parser = new DeleteByNameCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByNameCommand() throws IllegalValueException {
        //Single word name
        assertParseSuccess(parser, "John", new DeleteByNameCommand(new Name("John")));

        //Multiple word name
        assertParseSuccess(parser, "a b c d e f",
                new DeleteByNameCommand(new Name("a b c d e f")));

        //2 word name with no leading and trailing whitespaces
        Name name = new Name(VALID_NAME_BOB);
        DeleteByNameCommand expectedDeleteByNameCommand = new DeleteByNameCommand(name);
        assertParseSuccess(parser, VALID_NAME_BOB, expectedDeleteByNameCommand);

        //Leading whitespaces
        assertParseSuccess(parser, (" " + VALID_NAME_BOB), expectedDeleteByNameCommand);

        //Trailing whitespaces
        assertParseSuccess(parser, (VALID_NAME_BOB + "  "), expectedDeleteByNameCommand);

        //Leading and trailing whitespaces
        assertParseSuccess(parser, (" " + VALID_NAME_BOB + "  "), expectedDeleteByNameCommand);

        //Numeric input
        assertParseSuccess(parser, ("1"), new DeleteByNameCommand(new Name("1")));

        //Alphanumeric input
        assertParseSuccess(parser, ("1abc"), new DeleteByNameCommand(new Name("1abc")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //No input case
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //White space input case
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Multiple white space input case
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special character input case
        assertParseFailure(parser, "%",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special ASCII character input case
        assertParseFailure(parser, "ƒ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special character within name input case
        assertParseFailure(parser, "J%hn",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special ASCII character within name input case
        //Note: Small o, Capital O, ASCII symbol ○
        assertParseFailure(parser, "fi○na",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special character input case before valid name
        assertParseFailure(parser, "% John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special character input case after valid name
        assertParseFailure(parser, "John %",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special ASCII character input case before valid name
        assertParseFailure(parser, "ƒ John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special ASCII character input case after valid name
        assertParseFailure(parser, "John ƒ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/model/person/CaseInsensitiveExactNamePredicateTest.java
``` java

package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.PersonBuilder;

public class CaseInsensitiveExactNamePredicateTest {

    @Test
    public void equals() throws IllegalValueException {
        Name firstName = new Name("John Doe");
        Name secondName = new Name("Jane Dane");

        CaseInsensitiveExactNamePredicate firstPredicate = new CaseInsensitiveExactNamePredicate(firstName);
        CaseInsensitiveExactNamePredicate secondPredicate = new CaseInsensitiveExactNamePredicate(secondName);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CaseInsensitiveExactNamePredicate firstPredicateCopy = new CaseInsensitiveExactNamePredicate(firstName);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_exactName_returnsTrue() throws IllegalValueException {
        // 1 word lower case input
        CaseInsensitiveExactNamePredicate predicate = new CaseInsensitiveExactNamePredicate(new Name("john"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN").build()));

        // 2 words lower case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("john doe"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN DOE").build()));

        // 1 word upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOHN"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN").build()));

        // 2 words upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOHN DOE"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN DOE").build()));

        // 1 word mixed case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JohN"));
        assertTrue(predicate.test(new PersonBuilder().withName("John").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOhn").build()));

        // 2 words upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOhN Doe"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("john doe").build()));

        assertTrue(predicate.test(new PersonBuilder().withName("JOHN DOE").build()));
    }

    @Test
    public void test_notExactName_returnsFalse() throws IllegalValueException {
        // 1 word lower case input
        CaseInsensitiveExactNamePredicate predicate = new CaseInsensitiveExactNamePredicate(new Name("john"));
        assertFalse(predicate.test(new PersonBuilder().withName("johnn").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("john doe").build()));

        // 2 words lower case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("john doe"));
        assertFalse(predicate.test(new PersonBuilder().withName("john").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("doe").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("johnn doe").build()));

        // 1 word upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOHN"));

        assertFalse(predicate.test(new PersonBuilder().withName("JOHNN").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("JOHN DOE").build()));

        // 2 words upper case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("JOHN DOE"));

        assertFalse(predicate.test(new PersonBuilder().withName("JOHN").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("DOE").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("johnn doe").build()));

        // 1 word mixed case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("John"));

        assertFalse(predicate.test(new PersonBuilder().withName("Johnn").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("john doe").build()));

        // 2 words mixed case input
        predicate = new CaseInsensitiveExactNamePredicate(new Name("John Doe"));

        assertFalse(predicate.test(new PersonBuilder().withName("John").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("doe").build()));

        assertFalse(predicate.test(new PersonBuilder().withName("johnn doe").build()));
    }
}
```
###### /java/systemtests/ClearCommandSystemTest.java
``` java
        /* Case: mixed case command word -> cleared */
        assertCommandSuccess("ClEaR");
    }

```
###### /java/systemtests/DeleteByNameCommandSystemTest.java
``` java

package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOK_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOK_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.logic.commands.DeleteByNameCommand.MESSAGE_MULTIPLE_PERSON_WITH_SAME_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.CaseInsensitiveExactNamePredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;


public class DeleteByNameCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_BY_NAME_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE);

    @Test
    public void deleteByName() throws IllegalValueException {
        /*----------------- Performing delete operation while an unfiltered list is being shown --------------------*/

        /* Case: delete ALICE (first person) in the list, command with leading and trailing spaces -> deleted */
        Model expectedModel = getModel();
        Index firstIndex = INDEX_FIRST_PERSON;
        ReadOnlyPerson firstIndexToDelete = getModel().getFilteredPersonList().get(firstIndex.getZeroBased());
        String command = "     " + DeleteByNameCommand.COMMAND_WORD + "      "
                + firstIndexToDelete.getName() + "       ";

        ReadOnlyPerson deletedPerson = removePerson(expectedModel, firstIndexToDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete GEORGE (last person) in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastIndex = getLastIndex(expectedModel);
        ReadOnlyPerson lastIndexToDelete = getModel().getFilteredPersonList().get(lastIndex.getZeroBased());
        assertCommandSuccess(lastIndexToDelete);

        /* Case: undo deleting GEORGE (last person) in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting GEORGE (last person) in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePerson(modelBeforeDeletingLast, lastIndexToDelete);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete CARL (middle person) in the list -> deleted */
        Model modelBeforeDeletingMid = getModel();
        Index midIndex = getMidIndex(modelBeforeDeletingMid);
        ReadOnlyPerson midIndexToDelete = getModel().getFilteredPersonList().get(midIndex.getZeroBased());
        assertCommandSuccess(midIndexToDelete);

        /* Case: undo deleting CARL -> CARL restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingMid, expectedResultMessage);

        /*------------------ Performing delete operation while a filtered list is being shown ----------------------*/

        /* Case: filtered person list, delete first person shown in person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index firstPersonInFilteredList = INDEX_FIRST_PERSON;
        ReadOnlyPerson deleteFirstPersonInFilteredList = getModel().getFilteredPersonList().get(
                firstPersonInFilteredList.getZeroBased());
        assertCommandSuccess(deleteFirstPersonInFilteredList);

        /* Case: filtered person list, delete person NOT shown in person list -> deleted */
        /* Pre-condition: At least 1 person present in the address book not shown in current filtered list. */
        ObservableList<ReadOnlyPerson> displayedList = getModel().getFilteredPersonList();
        ObservableList<ReadOnlyPerson> addressBookData = getModel().getAddressBook().getPersonList();
        ReadOnlyPerson personToDelete = null;

        for (ReadOnlyPerson person : addressBookData) {
            // To find a person in the address book not shown in filtered list.
            if (displayedList.contains(person)) {
                continue;
            } else {
                personToDelete = person;
                break;
            }
        }
        assertCommandSuccess(personToDelete);

        /*--------------------- Performing delete operation while a person card is selected ------------------------*/

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        ReadOnlyPerson selectedIndexToDelete = getModel().getFilteredPersonList().get(selectedIndex.getZeroBased());
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = DeleteByNameCommand.COMMAND_WORD + " " + selectedIndexToDelete.getName();
        deletedPerson = removePerson(expectedModel, selectedIndexToDelete);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /*--------------------------------- Performing invalid delete operation ------------------------------------*/

        /* Case: invalid input (blank) -> rejected */
        command = DeleteByNameCommand.COMMAND_WORD + " ";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BY_NAME_COMMAND_FORMAT);

        /* Case: invalid character (%) -> rejected */
        command = DeleteByNameCommand.COMMAND_WORD + " %";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BY_NAME_COMMAND_FORMAT);

        /* Declaring a valid person name for subsequent test cases */
        ReadOnlyPerson firstPerson = getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String personName = firstPerson.getName().toString();

        /* Case: invalid character preceding valid name(%NAME) -> rejected */
        command = DeleteByNameCommand.COMMAND_WORD + " %" + personName;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BY_NAME_COMMAND_FORMAT);

        /* Case: invalid character preceding valid name with space(% NAME) -> rejected */
        command = DeleteByNameCommand.COMMAND_WORD + " % " + personName;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BY_NAME_COMMAND_FORMAT);

        /* Case: invalid character after valid name (NAME%) -> rejected */
        command = DeleteByNameCommand.COMMAND_WORD + " " + personName + "%";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BY_NAME_COMMAND_FORMAT);

        /* Case: invalid character after valid name with space (NAME %) -> rejected */
        command = DeleteByNameCommand.COMMAND_WORD + " " + personName + " %";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BY_NAME_COMMAND_FORMAT);

        /* Although the NAME used below does not exist in the address, the string "Name" is still valid input. */
        /* Case: invalid character within valid name (N@ME) -> rejected */
        command = DeleteByNameCommand.COMMAND_WORD + " N@me";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_BY_NAME_COMMAND_FORMAT);

        /* Attempting to delete multiple persons with same name */

        //Adding Amy
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NOK_NAME_DESC_AMY + NOK_PHONE_DESC_AMY + TAG_DESC_FRIEND;
        executeCommand(command);

        //Adding another Amy with same name
        String phoneDescAmy2 = " " + PREFIX_PHONE + VALID_PHONE_AMY + "1";
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + phoneDescAmy2 + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + NOK_NAME_DESC_AMY + NOK_PHONE_DESC_AMY + TAG_DESC_FRIEND;
        executeCommand(command);

        command = DeleteByNameCommand.COMMAND_WORD + " " + VALID_NAME_AMY;

        Model displayPersonsWithSameName = getModel();
        displayPersonsWithSameName.updateFilteredPersonList(
                new CaseInsensitiveExactNamePredicate(new Name(VALID_NAME_AMY)));

        assertCommandFailure(command, displayPersonsWithSameName, MESSAGE_MULTIPLE_PERSON_WITH_SAME_NAME);
    }

    /**
     * Removes the specified {@code ReadOnlyPerson} in {@code model}'s address book.
     * @return the removed person
     */
    private ReadOnlyPerson removePerson(Model model, ReadOnlyPerson person) {
        ReadOnlyPerson targetPerson = person;
        try {
            model.deletePerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Deletes the person in {@code model}'s address book by creating a default {@code DeleteByNameCommand}
     * using {@code toDelete} and performs the same verification as
     * {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteByNameCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(ReadOnlyPerson toDelete) {
        Model expectedModel = getModel();
        ReadOnlyPerson deletedPerson = removePerson(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);

        assertCommandSuccess(
                DeleteByNameCommand.COMMAND_WORD + " " + toDelete.getName(), expectedModel, expectedResultMessage);
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
     * @see DeleteByNameCommandSystemTest#assertCommandSuccess(String, Model, String)
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

    /**
     * Performs the same verification as {@code assertCommandFailure(String, String)} except that the
     * model updates the filtered person list according to one of the scenarios below:
     * 1. Suggested persons to delete
     * 2. Listing multiple persons with same name
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
```
###### /java/systemtests/DeleteCommandSystemTest.java
``` java
        /* Case: mixed case command word -> deleted */
        expectedModel = getModel();
        command = "DelETE " + INDEX_FIRST_PERSON.getOneBased();
        deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, INDEX_FIRST_PERSON);
    }

```
###### /java/systemtests/ExportCommandSystemTest.java
``` java

package systemtests;

import static seedu.address.logic.commands.ExportCommand.MESSAGE_EMPTY_ADDRESS_BOOK;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_EXPORT_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.Model;

public class ExportCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void exportCommand() {
        /*----------------- Performing export operation while an unfiltered list is being shown --------------------*/
        assertCommandSuccess();
        Index firstIndex = INDEX_FIRST_PERSON;
        selectPerson(firstIndex);
        assertCommandSuccess();

        /*----------------- Performing export operation while a filtered list is being shown --------------------*/

        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess();

        /*----------------- Performing export operation with an empty Address Book --------------------*/
        executeCommand(ClearCommand.COMMAND_WORD);
        assertCommandFailure(ExportCommand.COMMAND_WORD, MESSAGE_EMPTY_ADDRESS_BOOK);
    }

    /**
     * Exports current Address Book Data by creating a {@code ExportCommand} and performs the same verification as
     * {@code assertCommandSuccess(String, Model, String)}.
     * @see ExportCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess() {
        Model expectedModel = getModel();
        String expectedResultMessage = MESSAGE_EXPORT_SUCCESS;

        assertCommandSuccess(
                ExportCommand.COMMAND_WORD, expectedModel, expectedResultMessage);
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
     * @see DeleteByNameCommandSystemTest#assertCommandSuccess(String, Model, String)
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
        assertStatusBarUnchanged();
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
