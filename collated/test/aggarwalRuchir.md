# aggarwalRuchir
###### /java/seedu/address/logic/commands/ListCommandTest.java
``` java
    @Test
    public void execute_listIsFiltered_showOnlyNecessaryPersons() throws Exception {
        PersonContainsKeywordsPredicate firstPredicate = createNewPersonPredicateForSingleTag(new Tag(VALID_TAG_1));

        PersonContainsKeywordsPredicate secondPredicate = createNewPersonPredicateForMultipleTags(Arrays.asList(new
                Tag(VALID_TAG_2), new Tag(VALID_TAG_3)));

        // test command with single valid tag argument
        ListCommand firstListCommand = new ListCommand(firstPredicate);
        firstListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(firstListCommand, model, ListCommand.MESSAGE_SUCCESS_FILTEREDLIST
                + TAG_DESC_FRIENDS, expectedModel);

        firstPredicate = createNewPersonPredicateForSingleTag(new Tag(VALID_TAG_2));
        ListCommand secondListCommand = new ListCommand(firstPredicate);
        secondListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList("Benson")));
        assertCommandSuccess(secondListCommand, model, ListCommand.MESSAGE_SUCCESS_FILTEREDLIST
                + TAG_DESC_OWESMONEY, expectedModel);

        // test command with multiple valid tag arguments
        ListCommand thirdListCommand = new ListCommand(secondPredicate);
        reInitializeModels();
        thirdListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList("Benson", "Fiona",
                "George")));
        assertCommandSuccess(thirdListCommand, model, ListCommand.MESSAGE_SUCCESS_FILTEREDLIST
                + TAG_DESC_OWESMONEY + TAG_DESC_FAMILY, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showNoPersons() throws Exception {
        PersonContainsKeywordsPredicate firstPredicate = createNewPersonPredicateForSingleTag(new Tag(INVALID_TAG_1));
        PersonContainsKeywordsPredicate secondPredicate = createNewPersonPredicateForMultipleTags(Arrays.asList(new
                Tag(INVALID_TAG_1), new Tag(INVALID_TAG_2)));

        reInitializeModels();

        // test command with single invalid tag argument
        ListCommand firstListCommand = new ListCommand(firstPredicate);
        firstListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList()));
        assertCommandSuccess(firstListCommand, model, ListCommand.MESSAGE_NOENTRIESFOUND, expectedModel);

        reInitializeModels();

        // test command with multiple invalid tag arguments
        ListCommand secondListCommand = new ListCommand(secondPredicate);
        secondListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList()));
        assertCommandSuccess(secondListCommand, model, ListCommand.MESSAGE_NOENTRIESFOUND, expectedModel);
    }


    /**
     * re initializes the models to values given at Set up time
     */
    public void reInitializeModels () {
        this.model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        this.expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    /**
     * return a new PersonContainsKeywordsPredicate with input tag
     */
    private PersonContainsKeywordsPredicate createNewPersonPredicateForSingleTag(Tag tag) throws Exception {
        Set<Tag> singleTagSet = new HashSet<Tag>(Arrays.asList(tag));
        PersonContainsKeywordsPredicate newPredicate = new PersonContainsKeywordsPredicate(new
                ArrayList<>(singleTagSet));
        return newPredicate;
    }

    /**
     * return a new PersonContainsKeywordsPredicate with list of input tags
     */
    private PersonContainsKeywordsPredicate createNewPersonPredicateForMultipleTags(List<Tag> tagList) {
        Set<Tag> multipleTagSet = new HashSet<Tag>(tagList);
        PersonContainsKeywordsPredicate newPredicate = new PersonContainsKeywordsPredicate(new
                ArrayList<>(multipleTagSet));
        return newPredicate;
    }
}
```
###### /java/seedu/address/logic/parser/ListCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class ListCommandParserTest {

    private static final String INVALID_TAG_1 = "invalid";
    private static final String INVALID_TAG_2 = "wrong";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "criminal";
    private static final String VALID_TAG_3 = "teammate";
    private static final String VALID_TAG_4 = "family";

    private static final String TAG_DESC_CRIMINAL = " " + PREFIX_TAG + VALID_TAG_2;
    private static final String TAG_DESC_FAMILY = " " + PREFIX_TAG + VALID_TAG_4;
    private static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_1;
    private static final String TAG_DESC_INVALID = " " + PREFIX_TAG + INVALID_TAG_1;
    private static final String TAG_DESC_NOARGUMENT = " " + PREFIX_TAG;
    private static final String TAG_DESC_TEAMMATE = " " + PREFIX_TAG + VALID_TAG_3;
    private static final String TAG_DESC_WRONG = " " + PREFIX_TAG + INVALID_TAG_2;

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_returnsListCommand() {
        assertParseSuccess(parser, ListCommand.COMMAND_WORD, new ListCommand());
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + "  ", new ListCommand());
    }

    @Test
    public void parse_validArgs_returnsListCommand() throws Exception {
        // Valid tags as arguments
        Set<Tag> singleTagSetOne = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));
        Set<Tag> singleTagSetTwo = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_2)));
        Set<Tag> multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        // List command with one argument
        ListCommand expectedListCommand =
                new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSetOne)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_FRIEND, expectedListCommand);

        expectedListCommand =  new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSetTwo)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_CRIMINAL, expectedListCommand);


        // List command with multiple arguments
        expectedListCommand =  new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_FRIEND + TAG_DESC_CRIMINAL,
                expectedListCommand);

        multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_3),
                new Tag(VALID_TAG_4)));
        expectedListCommand =  new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_FRIEND + TAG_DESC_TEAMMATE
                + TAG_DESC_FAMILY, expectedListCommand);

        multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2),
                new Tag(VALID_TAG_3), new Tag(VALID_TAG_4)));
        expectedListCommand =  new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_FAMILY + TAG_DESC_CRIMINAL
                + TAG_DESC_FRIEND + TAG_DESC_TEAMMATE, expectedListCommand);

    }

    @Test
    public void parse_invalidArgs_returnsListCommand() throws Exception {
        Set<Tag> invalidTagSet = new HashSet<Tag>(Arrays.asList(new Tag(INVALID_TAG_1)));
        ListCommand expectedListCommand =
                new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(invalidTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_INVALID, expectedListCommand);

        invalidTagSet = new HashSet<Tag>(Arrays.asList(new Tag(INVALID_TAG_2)));
        expectedListCommand = new ListCommand(new PersonContainsKeywordsPredicate(new ArrayList<>(invalidTagSet)));
        assertParseSuccess(parser, ListCommand.COMMAND_WORD + TAG_DESC_WRONG, expectedListCommand);
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, ListCommand.COMMAND_WORD + TAG_DESC_NOARGUMENT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/model/person/PersonContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "criminal";

    @Test
    public void equals() throws Exception {
        Set<Tag> singleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));
        Set<Tag> multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(new
                ArrayList<>(singleTagSet));
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(new
                ArrayList<>(multipleTagSet));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(new
                ArrayList<>(singleTagSet));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsTags_returnsTrue() throws Exception {
        Set<Tag> singleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));
        Set<Tag> multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSet));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "criminal").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet));
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "classmate").build()));
    }

    @Test
    public void test_personDoesNotContainTags_returnsFalse() throws Exception {
        Set<Tag> emptyTagSet = new HashSet<Tag>(Arrays.asList());
        Set<Tag> singleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));
        Set<Tag> multipleTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        //No match because no tags in person
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSet));
        assertFalse(predicate.test(new PersonBuilder().withTags().build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(singleTagSet));
        assertFalse(predicate.test(new PersonBuilder().withTags("criminal").build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("criminal", "classmate").build()));

        predicate = new PersonContainsKeywordsPredicate(new ArrayList<>(multipleTagSet));
        assertFalse(predicate.test(new PersonBuilder().withTags("classmate").build()));
        assertFalse(predicate.test(new PersonBuilder().withTags("classmate", "family").build()));

    }

}
```
###### /java/seedu/address/model/person/PhoneTest.java
``` java
    @Test
    public void isPhoneFormattingCorrect() {
        assertEquals(Phone.formatPhone("911"), "911");

        assertEquals(Phone.formatPhone("6593121534"), "65-9312-1534"); //Singapore number
        assertEquals(Phone.formatPhone("9191121444"), "91-9112-1444"); //Indian number
        assertEquals(Phone.formatPhone("17651230101"), "176-5123-0101"); //US number
        assertEquals(Phone.formatPhone("447881234567"), "4478-8123-4567"); //UK number

        assertEquals(Phone.formatPhone("124293842033123"), "124-2938-4203-3123");
    }
```
