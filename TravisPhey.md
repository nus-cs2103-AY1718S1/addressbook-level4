# TravisPhey
###### \src\main\java\seedu\address\logic\commands\DeleteMultipleCommand.java
``` java
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */

public class DeleteMultipleCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteMul";
    public static final String COMMAND_ALIAS = "delM";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Can be used to delete multiple contacts at one go.\n"
            + ": Indexes that are to be deleted must be listed in ascending order.\n"
            + ": Deletes the contacts identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " 2" + " 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final ArrayList<Index> arrayOfIndex;

    public DeleteMultipleCommand(ArrayList<Index> arrayOfIndex) {
        this.arrayOfIndex = arrayOfIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String listOfDeletedContacts = "";

        for (int n = 0; n < arrayOfIndex.size(); n++) {

            Index targetIndex = arrayOfIndex.get(n);
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
            if (n == 0) {
                listOfDeletedContacts = listOfDeletedContacts + personToDelete.getName();
            } else {
                listOfDeletedContacts = listOfDeletedContacts + ", " + personToDelete.getName();
            }

            try {
                model.deletePerson(personToDelete);
```
###### \src\main\java\seedu\address\logic\commands\DeleteMultipleCommand.java
``` java
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, listOfDeletedContacts));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMultipleCommand // instanceof handles nulls
                && this.arrayOfIndex.equals(((DeleteMultipleCommand) other).arrayOfIndex)); // state check
    }

```
###### \src\main\java\seedu\address\logic\commands\FindCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.FindCommandPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "fi";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final FindCommandPredicate predicate;

    public FindCommand(FindCommandPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
```
###### \src\main\java\seedu\address\logic\commands\FindNumberCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose number contains any of the argument keywords.
 */

public class FindNumberCommand extends Command {

    public static final String COMMAND_WORD = "findnum";
    public static final String COMMAND_ALIAS = "fin";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose numbers contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 98765432 12345678 61772655";

    private final NumberContainsKeywordsPredicate predicate;

    public FindNumberCommand(NumberContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindNumberCommand // instanceof handles nulls
                && this.predicate.equals(((FindNumberCommand) other).predicate)); // state check
    }
}
```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case DeleteMultipleCommand.COMMAND_WORD:
        case DeleteMultipleCommand.COMMAND_ALIAS:
            return new DeleteMultipleCommandParser().parse(arguments);
```
###### \src\main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindNumberCommand.COMMAND_WORD:
        case FindNumberCommand.COMMAND_ALIAS:
            return new FindNumberCommandParser().parse(arguments);
```
###### \src\main\java\seedu\address\logic\parser\DeleteMultipleCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMultipleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMultipleCommand object
 */

public class DeleteMultipleCommandParser implements Parser<DeleteMultipleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMultipleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMultipleCommand.MESSAGE_USAGE));
        }

        String[] listOfIndex = trimmedArgs.split("\\s+");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(listOfIndex));
        Collections.reverse(list);
        ArrayList<Index> arrayOfIndex = new ArrayList<Index>();
        for (int n = 0; n < list.size(); n++) {
            String indexString = list.get(n);
            int foo = Integer.parseInt(indexString) - 1;
            Index index = new Index(foo);
            arrayOfIndex.add(index);
        }

        return new DeleteMultipleCommand(arrayOfIndex);
    }
}
```
###### \src\main\java\seedu\address\logic\parser\FindCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FindCommandPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        //String[] nameKeywords = trimmedArgs;

        return new FindCommand(new FindCommandPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \src\main\java\seedu\address\logic\parser\FindNumberCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindNumberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NumberContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindNumberCommand object
 */

public class FindNumberCommandParser implements Parser<FindNumberCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindNumberCommand
     * and returns an FindNumberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNumberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNumberCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindNumberCommand(new NumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### \src\test\java\seedu\address\logic\commands\FindByNumberTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NumberContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class FindByNumberTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NumberContainsKeywordsPredicate firstPredicate =
            new NumberContainsKeywordsPredicate(Collections.singletonList("first"));
        NumberContainsKeywordsPredicate secondPredicate =
            new NumberContainsKeywordsPredicate(Collections.singletonList("second"));

        FindNumberCommand findFirstCommand = new FindNumberCommand(firstPredicate);
        FindNumberCommand findSecondCommand = new FindNumberCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindNumberCommand findFirstCommandCopy = new FindNumberCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindNumberCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindNumberCommand command = prepareCommand("95352563 9482224 9482427");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindNumberCommand}.
     */
    private FindNumberCommand prepareCommand(String userInput) {
        FindNumberCommand command =
            new FindNumberCommand(new NumberContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindNumberCommand command, String eMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(eMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \src\test\java\seedu\address\logic\commands\FindCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FindCommandPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FindCommandPredicate firstPredicate =
                new FindCommandPredicate(Collections.singletonList("first"));
        FindCommandPredicate secondPredicate =
                new FindCommandPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {
        FindCommand command =
                new FindCommand(new FindCommandPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
