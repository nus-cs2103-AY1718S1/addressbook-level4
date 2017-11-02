# majunting
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class DeleteTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String firstArg = "first";
        String secondArg = "second";
        String thirdArg = "first";

        DeleteTagCommand deleteFirstCommand = new DeleteTagCommand(firstArg);
        DeleteTagCommand deleteSecondCommand = new DeleteTagCommand(secondArg);
        DeleteTagCommand deleteThirdCommand = new DeleteTagCommand(thirdArg);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteFirstCommandCopy = new DeleteTagCommand(firstArg);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different predicate -> returns false
        assertFalse(deleteThirdCommand.equals(deleteSecondCommand));
    }
}
```
###### /java/seedu/address/logic/commands/ListAlphabetCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameStartsWithAlphabetPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code ListAlphabetCommand}.
 */
public class ListAlphabetCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameStartsWithAlphabetPredicate firstPredicate =
                new NameStartsWithAlphabetPredicate("f");
        NameStartsWithAlphabetPredicate secondPredicate =
                new NameStartsWithAlphabetPredicate("s");

        ListAlphabetCommand listFirstCommand = new ListAlphabetCommand(firstPredicate);
        ListAlphabetCommand listSecondCommand = new ListAlphabetCommand(secondPredicate);

        assertTrue(listFirstCommand.equals(listFirstCommand));

        ListAlphabetCommand listFirstCommandCopy = new ListAlphabetCommand(firstPredicate);
        assertTrue(listFirstCommand.equals(listFirstCommandCopy));

        assertFalse(listFirstCommand.equals(1));
        assertFalse(listFirstCommand.equals(null));
        assertFalse(listFirstCommand.equals(listSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ListAlphabetCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code ListAlphabetCommand}.
     */
    private ListAlphabetCommand prepareCommand(String input) {
        ListAlphabetCommand command =
                new ListAlphabetCommand(new NameStartsWithAlphabetPredicate(input));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ListAlphabetCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        int firstArg = 1;
        int secondArg = 2;

        SortCommand firstCommand = new SortCommand(firstArg);
        SortCommand secondCommand = new SortCommand(secondArg);

        assertTrue(firstCommand.equals(firstCommand));

        SortCommand firstCommandCopy = new SortCommand(firstArg);
        assertTrue(firstCommand.equals(firstCommandCopy));

        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
    }
}
```
###### /java/seedu/address/logic/parser/DeleteTagCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTagCommand;


public class DeleteTagCommandParserTest {
    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parser_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ",
                MESSAGE_INVALID_COMMAND_FORMAT + DeleteTagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsDeleteTagCommand() {
        DeleteTagCommand expectedDeleteTagCommand =
                new DeleteTagCommand("a");
        assertParseSuccess(parser, "a", expectedDeleteTagCommand);
    }
}
```
###### /java/seedu/address/logic/parser/ListAlphabetCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListAlphabetCommand;
import seedu.address.model.person.NameStartsWithAlphabetPredicate;

public class ListAlphabetCommandParserTest {
    private ListAlphabetCommandParser parser = new ListAlphabetCommandParser();

    @Test
    public void parser_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ", MESSAGE_INVALID_COMMAND_FORMAT
                + ListAlphabetCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsListAlphabetCommand() {
        ListAlphabetCommand expectedAlphabetCommand =
                new ListAlphabetCommand(new NameStartsWithAlphabetPredicate(("a")));
        assertParseSuccess(parser, "a", expectedAlphabetCommand);
    }
}
```
###### /java/seedu/address/logic/parser/SortCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void invalid_input_format () {
        // no input
        assertParseFailure(parser, " ", MESSAGE_INVALID_COMMAND_FORMAT + SortCommand.MESSAGE_USAGE);

        // input is not one of the attributes
        assertParseFailure(parser, "wrong input", MESSAGE_INVALID_COMMAND_FORMAT + SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void valid_input_format () throws ParseException {
        SortCommand expectedCommand;
        SortCommand actualCommand;

        expectedCommand = new SortCommand(0);
        actualCommand = parser.parse("name");
        assertEquals(expectedCommand.getKeyword(), actualCommand.getKeyword());

        expectedCommand = new SortCommand(1);
        actualCommand = parser.parse("phone");
        assertEquals(expectedCommand.getKeyword(), actualCommand.getKeyword());

        expectedCommand = new SortCommand(2);
        actualCommand = parser.parse("email");
        assertEquals(expectedCommand.getKeyword(), actualCommand.getKeyword());

        expectedCommand = new SortCommand(3);
        actualCommand = parser.parse("address");
        assertEquals(expectedCommand.getKeyword(), actualCommand.getKeyword());
    }
}
```
###### /java/seedu/address/model/person/NameStartsWithAlphabetPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameStartsWithAlphabetPredicateTest {

    @Test
    public  void equals() {
        String firstPredicateKeyword = "f";
        String secondPredicateKeyword = "s";

        NameStartsWithAlphabetPredicate firstPredicate = new NameStartsWithAlphabetPredicate(firstPredicateKeyword);
        NameStartsWithAlphabetPredicate secondPredicate = new NameStartsWithAlphabetPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameStartsWithAlphabetPredicate firstPredicateCopy = new NameStartsWithAlphabetPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameStartsWithKeyword_returnsTrue() {
        NameStartsWithAlphabetPredicate predicate = new NameStartsWithAlphabetPredicate("a");
        assertTrue(predicate.test(new PersonBuilder().withName("alice bob").build()));

        predicate = new NameStartsWithAlphabetPredicate("A");
        assertTrue(predicate.test(new PersonBuilder().withName("AliCE").build()));
    }

    @Test
    public void test_nameDoesNotStartWithKeyword_returnsFalse() {
        NameStartsWithAlphabetPredicate predicate = new NameStartsWithAlphabetPredicate(" ");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new NameStartsWithAlphabetPredicate("C");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        predicate = new NameStartsWithAlphabetPredicate("a");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }
}
```
###### /java/seedu/address/model/person/predicates/FavoritePersonsPredicateTest.java
``` java
package seedu.address.model.person.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FavoritePersonsPredicateTest {
    private boolean Favorite = true;
    private boolean Unfavorite = false;
    @Test
    public void test_equal() {
        String firstPredicateKeyword = "favorite";
        String secondPredicateKeyword = "unfavorite";

        FavoritePersonsPredicate firstPredicate = new FavoritePersonsPredicate(firstPredicateKeyword);
        FavoritePersonsPredicate secondPredicate = new FavoritePersonsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FavoritePersonsPredicate firstPredicateCopy = new FavoritePersonsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ableToFindPersons_returnsTrue() {
        // favorite_returnsTrue
        FavoritePersonsPredicate predicate = new FavoritePersonsPredicate("favorite");
        assertTrue(predicate.test(new PersonBuilder().withFavorite(Favorite).build()));

        //unfavorite_returnsTrue
        predicate = new FavoritePersonsPredicate("unfavorite");
        assertTrue(predicate.test(new PersonBuilder().withFavorite(Unfavorite).build()));
    }

    @Test
    public void test_unableToFindPerson_returnsFalse() {
        // favorite_returnsFalse
        FavoritePersonsPredicate predicate = new FavoritePersonsPredicate("unfavorite");
        assertFalse(predicate.test(new PersonBuilder().withFavorite(Favorite).build()));

        // unfavorite_returnFalse
        predicate = new FavoritePersonsPredicate("favorite");
        assertFalse(predicate.test(new PersonBuilder().withFavorite(Unfavorite).build()));
    }
}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Favorite} of the {@code Person} that we are building
     */
    public PersonBuilder withFavorite(boolean favorite) {
        this.person.setFavorite(new Favorite(favorite));
        return this;
    }
```
