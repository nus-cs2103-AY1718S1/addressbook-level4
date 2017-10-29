# inGall
###### \java\seedu\address\logic\commands\BirthdayCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
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
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model) and unit tests for BirthdayCommand.
 */
public class BirthdayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addBirthday_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setBirthday(new Birthday("01/01/1991"));

        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getBirthday().value);

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteBirthday_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setBirthday(new Birthday(""));

        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getBirthday().toString());

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_DELETE_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }
    /*
    @Test
    public void execute_filteredList_success() throws Exception {
                showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withBirthday("01/01/1991").build();
        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getBirthday().value);

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BirthdayCommand birthdayCommand = prepareCommand(outOfBoundIndex, VALID_BIRTHDAY_BOB);

        assertCommandFailure(birthdayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     * */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        BirthdayCommand birthdayCommand = prepareCommand(outOfBoundIndex, VALID_BIRTHDAY_BOB);

        assertCommandFailure(birthdayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final BirthdayCommand standardCommand = new BirthdayCommand(INDEX_FIRST_PERSON,
                new Birthday(VALID_BIRTHDAY_AMY));

        // same values -> returns true
        BirthdayCommand commandWithSameValues = new BirthdayCommand(INDEX_FIRST_PERSON,
                new Birthday(VALID_BIRTHDAY_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new BirthdayCommand(INDEX_SECOND_PERSON, new Birthday(VALID_BIRTHDAY_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new BirthdayCommand(INDEX_FIRST_PERSON, new Birthday(VALID_BIRTHDAY_BOB))));
    }

    /**
     * Returns an {@code BirthdayCommand} with parameters {@code index} and {@code birthday}
     */
    private BirthdayCommand prepareCommand(Index index, String birthday) {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, new Birthday(birthday));
        birthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayCommand;
    }
}
```
###### \java\seedu\address\logic\parser\BirthdayCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.model.person.Birthday;

public class BirthdayCommandParserTest {
    private BirthdayCommandParser parser = new BirthdayCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Birthday birthday = new Birthday("01/01/1991");

        // have birthday
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY.toString() + " " + birthday;
        BirthdayCommand expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, birthday);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no birthday
        userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY.toString();
        expectedCommand = new BirthdayCommand(INDEX_FIRST_PERSON, new Birthday(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, BirthdayCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void equals() {
        Birthday birthday = new Birthday("01/01/1991");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday(birthday.value);
        assertTrue(birthday.equals(birthdayCopy));

        // different types -> returns false
        assertFalse(birthday.equals(1));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different person -> returns false
        Birthday differentBirthday = new Birthday("02/02/1992");
        assertFalse(birthday.equals(differentBirthday));
    }
}
```
