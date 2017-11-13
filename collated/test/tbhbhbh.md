# tbhbhbh
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void editTag(Tag oldTag, Tag newTag) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("first"));
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand searchFirstCommand = new FindCommand(firstPredicate);
        FindCommand searchSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindCommand command = prepareSearchKeywordsCommand(" ");
        assertCommandSuccess(command, expectedMessage, model.getFilteredPersonList());
    }

    @Test
    public void execute_tagThenName_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchKeywordsCommand("owesMoney Benson");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(BENSON));

    }

    @Test
    public void execute_nameThenTag_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchKeywordsCommand("Benson owesMoney");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(BENSON));
    }

    @Test
    public void execute_nameAlternatingUpperAndLowerCases_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchKeywordsCommand("BeNsOn");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(BENSON));
    }

    @Test
    public void execute_tagAlternatingUpperAndLowerCases_success() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareSearchKeywordsCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(BENSON));
    }

    @Test
    public void execute_initials_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareSearchKeywordsCommand("b");
        assertCommandSuccess(command, expectedMessage, model.getFilteredPersonList());
    }

```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    /**
     * Parses {@code userInput} which are keywords into a {@code FindCommand}.
     */
    public FindCommand prepareSearchKeywordsCommand(String userInput) {
        FindCommand command =
                new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command,
                                      String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\SocialCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.SocialCommand.MESSAGE_IF_MISSING;
import static seedu.address.logic.commands.SocialCommand.MESSAGE_INSTAGRAM;
import static seedu.address.logic.commands.SocialCommand.MESSAGE_SOCIAL_UNSUPPORTED;
import static seedu.address.logic.commands.SocialCommand.MESSAGE_TWITTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SocialCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    //Model provides required names of recipients and are not modified in any way
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_socialRequestInstagramEvent_success() throws CommandException {
        ReadOnlyPerson personToCheckSocial = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        SocialCommand socialCommand = prepareCommand(INDEX_FIRST_PERSON, "ig");

        String expectedMessagePrefix = String.format(socialCommand.getMessageSocialSuccess(),
                personToCheckSocial.getName().toString());
        String expectedMessage = expectedMessagePrefix
                + MESSAGE_INSTAGRAM
                + MESSAGE_IF_MISSING;

        CommandResult result = socialCommand.execute();
        assertEquals(expectedMessage, result.feedbackToUser);

    }

    @Test
    public void execute_socialRequestTwitterEvent_success() throws CommandException {
        ReadOnlyPerson personToCheckSocial = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        SocialCommand socialCommand = prepareCommand(INDEX_FIRST_PERSON, "tw");

        String expectedMessagePrefix = String.format(socialCommand.getMessageSocialSuccess(),
                personToCheckSocial.getName().toString());
        String expectedMessage = expectedMessagePrefix
                + MESSAGE_TWITTER
                + MESSAGE_IF_MISSING;

        CommandResult result = socialCommand.execute();
        assertEquals(expectedMessage, result.feedbackToUser);

    }

    @Test
    public void execute_validIndexWithInvalidSocialMedia_throwsCommandException() throws Exception {
        SocialCommand socialCommand = prepareCommand(INDEX_FIRST_PERSON, "fb");

        String expectedMessage = MESSAGE_SOCIAL_UNSUPPORTED + "fb";

        assertCommandFailure(socialCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SocialCommand socialCommand = prepareCommand(outOfBoundIndex, "ig");
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + outOfBoundIndex.getOneBased();

        assertCommandFailure(socialCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        SocialCommand socialFirstInstagramCommand = prepareCommand(INDEX_FIRST_PERSON, "ig");
        SocialCommand socialSecondInstagramCommand = prepareCommand(INDEX_SECOND_PERSON, "ig");
        SocialCommand socialFirstTwitterCommand = prepareCommand(INDEX_FIRST_PERSON, "tw");

        // same object -> returns true
        assertTrue(socialFirstInstagramCommand.equals(socialFirstInstagramCommand));

        // same vaues -> returns true
        SocialCommand socialFirstInstagramCommandCopy = prepareCommand(INDEX_FIRST_PERSON,
                "ig");
        assertTrue(socialFirstInstagramCommand.equals(socialFirstInstagramCommandCopy));

        // different types -> returns false
        assertFalse(socialFirstInstagramCommand.equals(1));

        // null -> returns false
        assertFalse(socialFirstInstagramCommand.equals(null));

        // different social media -> returns false
        assertFalse(socialFirstInstagramCommand.equals(socialFirstTwitterCommand));

        // different person -> returns false
        assertFalse(socialFirstInstagramCommand.equals(socialSecondInstagramCommand));

    }

    /**
     * Returns a {@code SocialCommand} using two parameters {@code index, string}
     */
    private SocialCommand prepareCommand(Index index, String socialMedia) {
        SocialCommand socialCommand = new SocialCommand(index, socialMedia);
        socialCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return socialCommand;
    }
}
```
###### \java\seedu\address\logic\commands\TagDeleteCommandTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

public class TagDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteValidTagUnfilteredList_success() throws Exception {
        Set<Tag> tagSet = new HashSet<>(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getTags());
        String tagName = tagSet.toArray()[0].toString().substring(1, 8);
        TagDeleteCommand tagDeleteCommand = prepareCommand(tagName);

        String expectedMessage = String.format(TagDeleteCommand.MESSAGE_DELETE_TAG_SUCCESS, tagName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(new Tag(tagSet.toArray()[0].toString().substring(1, 8)));

        assertCommandSuccess(tagDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteValidTagUnfilteredList_failure() throws Exception {
        Set<Tag> tagSet = new HashSet<>(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getTags());
        String tagName = "nonexistent";
        TagDeleteCommand tagDeleteCommand = prepareCommand(tagName);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_TAG_NAME);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(new Tag(tagName));

        assertCommandFailure(tagDeleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteNonAlphanumericTag_failure() throws Exception {
        Set<Tag> tagSet = new HashSet<>(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getTags());
        String tagName = "non-existent";

        TagDeleteCommand tagDeleteCommand = prepareCommand(tagName);
        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;

        assertCommandFailure(tagDeleteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        TagDeleteCommand deleteFirstTagCommand = new TagDeleteCommand("testone");
        TagDeleteCommand deleteSecondTagCommand = new TagDeleteCommand("testtwo");

        // same object -> returns true
        assertTrue(deleteFirstTagCommand.equals(deleteFirstTagCommand));

        // same values -> returns true
        TagDeleteCommand deleteFirstTagCommandCopy = new TagDeleteCommand("testone");
        assertTrue(deleteFirstTagCommand.equals(deleteFirstTagCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstTagCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstTagCommand.equals(null));

        // different tags -> returns false
        assertFalse(deleteFirstTagCommand.equals(deleteSecondTagCommand));
    }

    /**
     * Returns a {@code TagDeleteCommand} with the parameter {@code tagName}.
     */
    private TagDeleteCommand prepareCommand(String tagName) {
        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(tagName);
        tagDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagDeleteCommand;
    }
}
```
###### \java\seedu\address\logic\commands\TagEditCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TagEditCommand.MESSAGE_EDIT_TAG_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagEditCommandTest {

    public static final String DUMMY_TAG = "dummy";
    public static final String DUMMY_TAG_TWO = "dummytwo";
    public static final String TAG_THAT_FAILS_ALPHANUMERIC = "fa-il";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editValidTagUnfilteredList_success() throws Exception {
        Set<Tag> tagSet = new HashSet<>(model.getFilteredPersonList()
                .get(INDEX_SECOND_PERSON.getZeroBased()).getTags());
        String oldTagName = tagSet.toArray()[0].toString().substring(1, 10);
        Tag oldTag = new Tag(oldTagName);
        String newTagName = DUMMY_TAG;
        Tag newTag = new Tag(newTagName);

        TagEditCommand tagEditCommand = prepareCommand(oldTagName, newTagName);

        String expectedMessage = String.format(MESSAGE_EDIT_TAG_SUCCESS, oldTag.toString(), newTag.toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.editTag(oldTag, newTag);

        assertCommandSuccess(tagEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editInvalidTagUnfilteredList_throwsCommandException() throws Exception {
        String oldTagName = DUMMY_TAG;
        Tag oldTag = new Tag(oldTagName);
        String newTagName = DUMMY_TAG_TWO;
        Tag newTag = new Tag(newTagName);

        TagEditCommand tagEditCommand = prepareCommand(oldTagName, newTagName);

        String expectedMessage = Messages.MESSAGE_UNKNOWN_TAG_NAME;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.editTag(oldTag, newTag);

        assertCommandFailure(tagEditCommand, model, expectedMessage);
    }

    @Test
    public void execute_editIntoNonAlphanumericTag_failure() throws Exception {
        String oldTagName = DUMMY_TAG;
        String newTagName = TAG_THAT_FAILS_ALPHANUMERIC;

        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        TagEditCommand tagEditCommand = prepareCommand(oldTagName, newTagName);

        assertCommandFailure(tagEditCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Person aliceFriend = new PersonBuilder().withName("Alice").withTags("friends").build();
        Person bobColleague = new PersonBuilder().withName("Bob").withTags("colleagues").build();
        TagEditCommand editFriendsCommand = new TagEditCommand("friends", DUMMY_TAG);
        TagEditCommand editColleaguesCommand = new TagEditCommand("colleagues", DUMMY_TAG);

        // same object -> returns true
        assertTrue(editFriendsCommand.equals(editFriendsCommand));

        // same values -> returns true
        TagEditCommand editFriendsCommandCopy = new TagEditCommand("friends", DUMMY_TAG);
        assertTrue(editFriendsCommand.equals(editFriendsCommandCopy));

        // different types -> returns false
        assertFalse(editColleaguesCommand.equals(1));

        // null -> returns false
        assertFalse(editColleaguesCommand.equals(null));

        // different person -> returns false
        assertFalse(editColleaguesCommand.equals(editFriendsCommand));
    }

    /**
     * Returns a {@code TagEditCommand} with the parameters {@code oldTagName, newTagName}.
     */
    private TagEditCommand prepareCommand(String oldTagName, String newTagName) {
        TagEditCommand tagEditCommand = new TagEditCommand(oldTagName, newTagName);
        tagEditCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagEditCommand;
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsBirthdayPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "102",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "friends")));
        assertParseSuccess(parser, "Alice friends", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t friends  \t", expectedFindCommand);

        // single birthday month represented by 2 digits
        expectedFindCommand = new FindCommand(new PersonContainsBirthdayPredicate("05"));
        assertParseSuccess(parser, "05", expectedFindCommand);
    }
}
```
###### \java\seedu\address\logic\parser\SocialCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SocialCommand;

public class SocialCommandParserTest {

    private SocialCommandParser parser = new SocialCommandParser();


    @Test
    public void parse_validArgs_returnsSocialCommand() {
        assertParseSuccess(parser, "1 ig", prepareCommand(INDEX_FIRST_PERSON, "ig"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format((MESSAGE_INVALID_COMMAND_FORMAT + SocialCommand.MESSAGE_USAGE), ""));
    }

    /**
     * Returns a {@code SocialCommand} with the parameters {@code index and socialMedia}.
     */
    private SocialCommand prepareCommand(Index index, String socialMedia) {

        SocialCommand socialCommand = new SocialCommand(index, socialMedia);

        return socialCommand;
    }

}
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(
                firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        // Zero keywords, returns empty list
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // One name
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // One tag
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // One initial
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("a"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("aLIce"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Mixed order keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("colleagues", "Alice", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("colleagues", "friends").build()));

        // Mixed order with mixed case keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("colLEAgUes", "ALiCe", "fRiENds"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("colleagues", "friends").build()));
    }

    @Test
    public void test_personDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Keyword: initial does not exist
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("c"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        //Keyword: invalid tag
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("-ta!@"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));

        // Keywords: matches name, but not tag
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol", "friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Carol").withTags("ilovecs2103t").build()));

        // Keywords: matches tag, but not name
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol", "friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withTags("friends").build()));


    }
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
        String instagramName = getInstagramNameFromFullName(selectedCardName);
        URL expectedUrl;
        try {
            expectedUrl = new URL(INSTAGRAM_URL_PREFIX + instagramName + "/");
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.");
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Returns the person's instagram name from his/her full name.
     */
    private String getInstagramNameFromFullName(String fullName) {

        switch(fullName) {
        case "Alice Pauline":
            return "alice_pauline";
        case "Benson Meier":
            return "meier";
        case "Carl Kurz":
            return "kurz";
        case "Daniel Meier":
            return "meier_dan";
        case "Elle Meyer":
            return "meyer_elle";
        case "Fiona Kunz":
            return "kunz";
        case "George Best":
            return "iamthebest";
        case "Amy Bee":
            return "amy_bee";
        default:
            return "failed";
        }
    }
```
