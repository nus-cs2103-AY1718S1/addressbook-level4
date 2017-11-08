# PokkaKiyo
###### \main\java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setFacebookAddress(FacebookAddress facebookAddress) {
            this.facebookAddress = facebookAddress;
        }

        public Optional<FacebookAddress> getFacebookAddress() {
            return Optional.ofNullable(facebookAddress);
        }
```
###### \main\java\seedu\address\logic\commands\FindPersonsWithTagsCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Finds and lists all persons in address book who has a tag that contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindPersonsWithTagsCommand extends Command {

    public static final String COMMAND_WORD = "findtags";
    public static final String COMMAND_ALIAS1 = "findtag";
    public static final String COMMAND_ALIAS2 = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose has tags containing any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " classmates colleagues";

    private final PersonContainsTagsPredicate predicate;

    public FindPersonsWithTagsCommand(PersonContainsTagsPredicate predicate) {
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
                || (other instanceof FindPersonsWithTagsCommand // instanceof handles nulls
                && this.predicate.equals(((FindPersonsWithTagsCommand) other).predicate)); // state check
    }
}
```
###### \main\java\seedu\address\logic\commands\ListTagsCommand.java
``` java
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * List all tags in the addressbook to the user
 */
public class ListTagsCommand extends Command {
    public static final String COMMAND_WORD = "listtags";
    public static final String COMMAND_WORD_ALIAS1 = "lt";
    public static final String COMMAND_WORD_ALIAS2 = "ltags";

    public static final String MESSAGE_FAILURE = "You do not have any tags!";
    public static final String MESSAGE_SUCCESS = "You have the following tags: ";


    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(getFeedbackForUser());
    }

    private String getFeedbackForUser() {

        String feedbackForUser;

        ArrayList<Tag> listOfAllTags = getAllTagsWithNoDuplicates();

        if (listOfAllTags.isEmpty()) {
            feedbackForUser = MESSAGE_FAILURE;
        } else {
            feedbackForUser = MESSAGE_SUCCESS + getListOfAllTagsInString(listOfAllTags);
        }

        return feedbackForUser;
    }

    private ArrayList<Tag> getAllTagsWithNoDuplicates() {
        ArrayList<Tag> listOfAllTags = new ArrayList<Tag>();

        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {
            for (Tag tag : p.getTags()) {
                if (!listOfAllTags.contains(tag)) {
                    listOfAllTags.add(tag);
                }
            }
        }
        return listOfAllTags;
    }

    private String getListOfAllTagsInString(ArrayList<Tag> listOfAllTags) {

        ArrayList<String> listOfAllTagNames = getAllTagNamesWithNoDuplicates(listOfAllTags);

        sortListOfAllTagNamesAlphabetically(listOfAllTagNames);

        return getSortedTagNamesAsString(listOfAllTagNames);
    }

    private ArrayList<String> getAllTagNamesWithNoDuplicates(ArrayList<Tag> listOfAllTags) {
        ArrayList<String> listOfAllTagNames = new ArrayList<String>();

        for (Tag tag : listOfAllTags) {
            listOfAllTagNames.add(tag.getTagName());
        }
        return listOfAllTagNames;
    }

    private void sortListOfAllTagNamesAlphabetically(ArrayList<String> listOfAllTagNames) {
        Collections.sort(listOfAllTagNames);
    }

    private String getSortedTagNamesAsString(ArrayList<String> listOfAllTagNames) {
        StringBuilder tagNamesInListOfAllTags = new StringBuilder("");
        for (String tagName : listOfAllTagNames) {
            tagNamesInListOfAllTags.append("[").append(tagName).append("] ");
        }

        return tagNamesInListOfAllTags.toString().trim();
    }

}
```
###### \main\java\seedu\address\logic\parser\AddCommandParser.java
``` java
            FacebookAddress facebookAddress = new FacebookAddress("");
            Optional<String> facebookAddressOptional = argMultimap.getValue(PREFIX_FACEBOOKADDRESS);
            if (facebookAddressOptional.isPresent()) {
                facebookAddress = ParserUtil.parseFacebookAddress(argMultimap.getValue(PREFIX_FACEBOOKADDRESS)).get();
            }
```
###### \main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindPersonsWithTagsCommand.COMMAND_WORD:
        case FindPersonsWithTagsCommand.COMMAND_ALIAS1:
        case FindPersonsWithTagsCommand.COMMAND_ALIAS2:
            return new FindPersonsWithTagsCommandParser().parse(arguments);
```
###### \main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ListTagsCommand.COMMAND_WORD:
        case ListTagsCommand.COMMAND_WORD_ALIAS1:
        case ListTagsCommand.COMMAND_WORD_ALIAS2:
            return new ListTagsCommand();
```
###### \main\java\seedu\address\logic\parser\FindPersonsWithTagsCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.logic.commands.FindPersonsWithTagsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Parses input arguments and creates a new FindPersonWithTagsCommand object
 */
public class FindPersonsWithTagsCommandParser implements Parser<FindPersonsWithTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonWithTagsCommand
     * and returns an FindPersonWithTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonsWithTagsCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        checkForNonEmptyArguments(trimmedArgs);

        String[] tagKeywords = getListOfTagKeywords(trimmedArgs);

        ArrayList<String> tagKeywordsImproved = getImprovedList(tagKeywords);

        return new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(tagKeywordsImproved));
    }

    private String[] getListOfTagKeywords(String trimmedArgs) {
        return trimmedArgs.split("\\s+");
    }

    /**
     * Checks if the given String of arguments is empty
     * @param trimmedArgs
     * @throws ParseException if the provided String does not conform to expected format
     */
    private void checkForNonEmptyArguments(String trimmedArgs) throws ParseException {
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindPersonsWithTagsCommand.MESSAGE_USAGE));
        }
    }

    /**
     * From the original list of keywords, generate more keywords
     * corresponding to the singular/plural form of the word,
     * but might generate words that are not in proper English,
     * for example, "memory" to "memorys" instead of "memories".
     * @param originalKeywords
     * @return a new list of keywords that includes the original keywords and
     * singular/plural differences
     */
    private ArrayList<String> getImprovedList(String[] originalKeywords) {
        ArrayList<String> improvedListOfKeywords = new ArrayList<String>();
        for (String originalKeyword : originalKeywords) {
            improvedListOfKeywords.add(originalKeyword);
            if (originalKeyword.endsWith("s")) {
                improvedListOfKeywords.add(originalKeyword.substring(0, originalKeyword.length() - 1));
            } else {
                improvedListOfKeywords.add (originalKeyword.concat("s"));
            }
        }
        return improvedListOfKeywords;
    }
}
```
###### \main\java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> facebookAddress} into an {@code Optional<FacebookAddress>}
     * if {@code facebookAddress} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<FacebookAddress> parseFacebookAddress (Optional<String> facebookAddress)
            throws IllegalValueException {
        requireNonNull(facebookAddress);
        return facebookAddress.isPresent()
                ? Optional.of(new FacebookAddress(facebookAddress.get())) : Optional.empty();
    }
```
###### \main\java\seedu\address\model\person\FacebookAddress.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.net.URL;

/**
 * Represents a person's Facebook address
 * Guarantees: immutable; is always valid
 */
public class FacebookAddress {

    public static final String MESSAGE_FACEBOOKADDRESS_CONSTRAINTS =
            "Person Facebook address can take any values, can even be blank";

    public final String value;

    public FacebookAddress(String facebookAddress) {
        requireNonNull(facebookAddress);

        this.value = getInUrlFormIfNeeded(facebookAddress);
    }

    /**
     * If user's input is not in valid URL format, assume that the input is the facebook profile name of the contact,
     * and thus, append the necessary URL prefixes
     * @param facebookAddress
     * @return
     */
    private String getInUrlFormIfNeeded(String facebookAddress) {
        if (isValidUrl(facebookAddress)) {
            return facebookAddress;
        } else {
            return "https://www.facebook.com/" + facebookAddress;
        }
    }

    /**
     * Checks if a given string is in valid URL format
     * @param facebookAddress
     * @return
     */
    private boolean isValidUrl(String facebookAddress) {
        try {
            URL url = new URL(facebookAddress);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof FacebookAddress //instanceof handles nulls
                && this.value.equals(((FacebookAddress) other).value));

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \main\java\seedu\address\model\person\Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }
```
###### \main\java\seedu\address\model\person\PersonContainsTagsPredicate.java
``` java
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a Person has a tag with a tag name matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        String allTagNames = getStringOfAllTagNamesOfPerson(person);

        final List<String> keywordsToInclude = new ArrayList<String>();
        final List<String> keywordsToExclude = new ArrayList<String>();
        separateKeywordsToIncludeAndExclude(keywordsToInclude, keywordsToExclude);

        boolean onlyKeywordsToExcludeAreSpecified =
                checkIfOnlyKeywordsToExcludeAreSpecified(keywordsToInclude, keywordsToExclude);

        //if only tags to be excluded are specified, return all persons that do not contain the specified tag,
        //even if the person does not have any tags
        if (onlyKeywordsToExcludeAreSpecified) {
            return !(keywordsToExclude.stream()
                    .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))));
        }

        return keywordsToInclude.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))
                && !(keywordsToExclude.stream()
                .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))));
    }

    /**
     * Checks if there are only keywords to exclude but not keywords to include
     * @param keywordsToInclude list of keywords to include for finding tags
     * @param keywordsToExclude list of keywords to explicitly exclude for finding tags
     * @return true if keywordsToInclude is empty and keywordsToExclude is not empty, false otherwise
     */
    private boolean checkIfOnlyKeywordsToExcludeAreSpecified(
            List<String> keywordsToInclude, List<String> keywordsToExclude) {

        if (keywordsToInclude.isEmpty() && !keywordsToExclude.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtains and updates the appropriate list of keywords to include and exclude
     * @param keywordsToInclude list of keywords to include for search
     * @param keywordsToExclude list of keywords to explicitly exclude for search
     */
    private void separateKeywordsToIncludeAndExclude(List<String> keywordsToInclude, List<String> keywordsToExclude) {

        for (String eachKeyword : keywords) {
            if (!eachKeyword.startsWith("-")) {
                keywordsToInclude.add(eachKeyword);
            } else {
                keywordsToExclude.add(eachKeyword.substring(1));
            }
        }

    }

    private String getStringOfAllTagNamesOfPerson(ReadOnlyPerson person) {
        Set<Tag> personTags = getAllTagsOfPerson(person);

        StringBuilder allTagNames = new StringBuilder();
        for (Tag tag : personTags) {
            allTagNames.append(tag.getTagName() + " ");
        }

        return allTagNames.toString().trim();
    }

    private Set<Tag> getAllTagsOfPerson(ReadOnlyPerson person) {
        return person.getTags();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords)); // state check
    }

}
```
###### \test\java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getFacebookAddress() {
        return facebookAddressLabel.getText();
    }
```
###### \test\java\seedu\address\logic\commands\FindPersonsWithTagsCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
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
import seedu.address.model.person.PersonContainsTagsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

public class FindPersonsWithTagsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsTagsPredicate firstPredicate =
                new PersonContainsTagsPredicate(Collections.singletonList("first"));
        PersonContainsTagsPredicate secondPredicate =
                new PersonContainsTagsPredicate(Collections.singletonList("second"));

        FindPersonsWithTagsCommand findFirstCommand = new FindPersonsWithTagsCommand(firstPredicate);
        FindPersonsWithTagsCommand findSecondCommand = new FindPersonsWithTagsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPersonsWithTagsCommand findFirstCommandCopy = new FindPersonsWithTagsCommand(firstPredicate);
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
        FindPersonsWithTagsCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindPersonsWithTagsCommand command = prepareCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_oneKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FindPersonsWithTagsCommand command = prepareCommand("friends");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_nonexistingTag_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindPersonsWithTagsCommand command = prepareCommand("sometag");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code FindPersonsWithTagsCommand}.
     */
    private FindPersonsWithTagsCommand prepareCommand(String userInput) {
        FindPersonsWithTagsCommand command =
                new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPersonsWithTagsCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \test\java\seedu\address\logic\commands\ListTagsCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNIQUETAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNIQUETAG2;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CORNIE;
import static seedu.address.testutil.TypicalPersons.CORNIE_NEW_NON_UNIQUE_TAG;
import static seedu.address.testutil.TypicalPersons.CORNIE_NEW_UNIQUE_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

public class ListTagsCommandTest {
    private Model model;
    private Model expectedModel;
    private Model modelWithNoTags;
    private ListTagsCommand listTagsCommand;
    private List<Tag> listOfAllTags;
    private String expectedMessage;
    private String expectedMessageForNoTags;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        listOfAllTags = new ArrayList<Tag>();

        listTagsCommand = new ListTagsCommand();
        listTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        //creating the expected message for all tags in typical addressbook
        StringBuilder expectedMessageInStringBuilder = new StringBuilder(listTagsCommand.MESSAGE_SUCCESS);
        for (ReadOnlyPerson p : model.getAddressBook().getPersonList()) {
            for (Tag tag : p.getTags()) {
                if (!listOfAllTags.contains(tag)) {
                    listOfAllTags.add(tag);
                    expectedMessageInStringBuilder.append("[").append(tag.getTagName()).append("] ");
                }
            }
        }
        expectedMessage = expectedMessageInStringBuilder.toString().trim();

    }

    @Test
    public void execute_showsCorrectTagListWhenThereAreContactsWithTags() {
        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewPersonWithNewTag_showsCorrectTagList() throws DuplicatePersonException {
        model.addPerson(CORNIE);
        expectedModel.addPerson(CORNIE);
        String newExpectedMessage = expectedMessage + " [" + VALID_TAG_UNIQUETAG + "]";

        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);
    }

    @Test
    public void execute_removePersonWithParticularUniqueTag_showsCorrectTagList() throws
            PersonNotFoundException, DuplicatePersonException {
        model.addPerson(CORNIE);
        expectedModel.addPerson(CORNIE);
        String newExpectedMessage = expectedMessage + " [" + VALID_TAG_UNIQUETAG + "]";

        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);


        model.deletePerson(CORNIE);
        expectedModel.deletePerson(CORNIE);

        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editPersonWithParticularTag_showsCorrectTagList() throws
            PersonNotFoundException, DuplicatePersonException {
        model.addPerson(CORNIE);
        expectedModel.addPerson(CORNIE);
        String newExpectedMessage = expectedMessage + " [" + VALID_TAG_UNIQUETAG + "]";

        assertCommandSuccess(listTagsCommand, model, newExpectedMessage, expectedModel);

        //editing unique tag to another unique tag
        model.updatePerson(CORNIE, CORNIE_NEW_UNIQUE_TAG);
        expectedModel.updatePerson(CORNIE, CORNIE_NEW_UNIQUE_TAG);
        String newExpectedMessage2 = expectedMessage + " [" + VALID_TAG_UNIQUETAG2 + "]";

        assertCommandSuccess(listTagsCommand, model, newExpectedMessage2, expectedModel);

        //editing unique tag to non-unique tag
        model.updatePerson(CORNIE_NEW_UNIQUE_TAG, CORNIE_NEW_NON_UNIQUE_TAG);
        expectedModel.updatePerson(CORNIE_NEW_UNIQUE_TAG, CORNIE_NEW_NON_UNIQUE_TAG);

        assertCommandSuccess(listTagsCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_noTagsShowsEmptyTagList() throws DuplicatePersonException {
        modelWithNoTags = new ModelManager();
        expectedMessageForNoTags = listTagsCommand.MESSAGE_FAILURE;
        ListTagsCommand listTagsCommandForNoTags = new ListTagsCommand();
        listTagsCommandForNoTags.setData(modelWithNoTags, new CommandHistory(), new UndoRedoStack());

        assertCommandSuccess(listTagsCommandForNoTags, modelWithNoTags, expectedMessageForNoTags, modelWithNoTags);

    }

}
```
###### \test\java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // no facebook address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withFacebookAddress("").withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        //no tags and no facebook address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withFacebookAddress("")
                .withBirthday(VALID_BIRTHDAY_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY, new AddCommand(expectedPerson));

        // zero tags
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withFacebookAddress(VALID_FACEBOOKADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BIRTHDAY_DESC_AMY + FACEBOOK_ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

```
###### \test\java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_listTags() throws Exception {
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD_ALIAS1) instanceof ListTagsCommand);
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD_ALIAS1 + " 3") instanceof ListTagsCommand);
    }

    @Test
    public void parseCommand_listTags_alias1() throws Exception {
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD_ALIAS2) instanceof ListTagsCommand);
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD_ALIAS2 + " 3") instanceof ListTagsCommand);
    }

    @Test
    public void parseCommand_listTags_alias2() throws Exception {
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD) instanceof ListTagsCommand);
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD + " 3") instanceof ListTagsCommand);
    }
```
###### \test\java\seedu\address\logic\parser\FindPersonsWithTagsCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPersonsWithTagsCommand;
import seedu.address.model.person.PersonContainsTagsPredicate;

public class FindPersonsWithTagsCommandParserTest {

    private FindPersonsWithTagsCommandParser parser = new FindPersonsWithTagsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonsWithTagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_singularToPluralLeniency() {
        FindPersonsWithTagsCommand expectedFindPersonsWithTagsCommand =
                new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(
                        Arrays.asList("friend", "friends")));
        //input of args in singular form should return command with both singular and plural form
        assertParseSuccess(parser, "friend", expectedFindPersonsWithTagsCommand);
    }

    @Test
    public void parse_pluralToSingularLeniency() {
        FindPersonsWithTagsCommand expectedFindPersonsWithTagsCommand =
                new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(
                        Arrays.asList("friends", "friend")));
        //input of args in plural form should return command with both singular and plural form
        assertParseSuccess(parser, "friends", expectedFindPersonsWithTagsCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindPersonsWithTagsCommand expectedFindPersonsWithTagsCommand =
                new FindPersonsWithTagsCommand(new PersonContainsTagsPredicate(
                        Arrays.asList("friend", "friends", "colleague", "colleagues")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "friend colleague", expectedFindPersonsWithTagsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friend \n \t colleague  \t", expectedFindPersonsWithTagsCommand);
    }
}
```
###### \test\java\seedu\address\model\person\FacebookAddressTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FacebookAddressTest {

    @Test
    public void equals() {
        FacebookAddress facebookAddress = new FacebookAddress("https://www.facebook.com/somefacebookaddress/");

        // same object -> returns true
        assertTrue(facebookAddress.equals(facebookAddress));

        // same values -> returns true
        FacebookAddress facebookAddressCopy = new FacebookAddress(facebookAddress.value);
        assertTrue(facebookAddress.equals(facebookAddressCopy));

        // different types -> returns false
        assertFalse(facebookAddress.equals(1));

        // null -> returns false
        assertFalse(facebookAddress.equals(null));

        // different Facebook address -> returns false
        FacebookAddress differentFacebookAddress =
                new FacebookAddress("https://www.facebook.com/someOTHERfacebookaddress/");
        assertFalse(facebookAddress.equals(differentFacebookAddress));
    }
}
```
###### \test\java\seedu\address\model\person\PersonContainsTagsPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsTagsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsTagsPredicate firstPredicate = new PersonContainsTagsPredicate(firstPredicateKeywordList);
        PersonContainsTagsPredicate secondPredicate = new PersonContainsTagsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsTagsPredicate firstPredicateCopy = new PersonContainsTagsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasTagsMatchingKeywordsToInclude_returnsTrue() {
        // One keyword
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(Collections.singletonList("colleagues"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues").build()));

        // Multiple keywords
        predicate = new PersonContainsTagsPredicate(Arrays.asList("colleagues", "schoolmates"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues", "friends", "schoolmates").build()));

        // Only one matching keyword
        predicate = new PersonContainsTagsPredicate(Arrays.asList("colleagues", "schoolmates"));
        assertTrue(predicate.test(new PersonBuilder().withTags("colleagues").build()));

        // Mixed-case keywords
        predicate = new PersonContainsTagsPredicate(Arrays.asList("cOLLeaguEs", "FrIeNdS"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

    }

    @Test
    public void test_personDoesNotHaveTagsContainingKeywordsToInclude_returnsFalse() {
        // Zero keywords
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("whateverTag").build()));

        // Non-matching keyword
        predicate = new PersonContainsTagsPredicate(Arrays.asList("roommate"));
        assertFalse(predicate.test(new PersonBuilder().withTags("schoolmate", "classmate").build()));

        // Keywords match name, phone, email, address, birthday, facebook address, but does not match any tag name
        predicate = new PersonContainsTagsPredicate(Arrays.asList("12345", "alice@email.com", "Main",
                "Street", "Alice", "14051998", "https://www.facebook.com/default_address_for_testing/"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street")
                .withName("Alice").withFacebookAddress("https://www.facebook.com/default_address_for_testing/")
                .withBirthday("14051998").build()));
    }

    @Test
    public void test_personHasNoTags() {
        //Person with no tags for inclusion
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Arrays.asList("roommates"));
        assertFalse(predicate.test(new PersonBuilder().withTags().build()));

        // Person with no tags for exclusion
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates"));
        assertTrue(predicate.test(new PersonBuilder().withTags().build()));

        //Person with no tags for both exclusion and inclusion
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates", "friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags().build()));
    }

    @Test
    public void test_personHasTagsForExclusion() {
        // Has one tag matching tags to exclude
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("roommates").build()));

        // Has at least one tag matching tags to exclude
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("roommates", "schoolmates").build()));

        // More keywords to exclude than tags of person
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates", "-schoolmates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("roommates").build()));

        // More tags on person than keywords to exclude
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates", "-schoolmates"));
        assertFalse(predicate.test(new PersonBuilder().withTags("roommates", "schoolmates", "classmates").build()));

    }

    @Test
    public void test_personDoesNotHaveTagsForExclusion() {
        // Does not have matching tags to exclude
        PersonContainsTagsPredicate predicate = new PersonContainsTagsPredicate(Arrays.asList("-roommates"));
        assertTrue(predicate.test(new PersonBuilder().withTags("classmates").build()));

        // Keywords to exclude match name, phone, email, address, birthday, and facebook address,
        // but does not match any tag name
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-12345", "-alice@email.com", "-Main",
                "-Street", "-Alice", "-14051998", "-https://www.facebook.com/default_address_for_testing/"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street")
                .withName("Alice").withFacebookAddress("https://www.facebook.com/default_address_for_testing/")
                .withBirthday("14051998").build()));
    }

    @Test
    public void test_personHasTagsForInclusionAndTagsForExclusion() {
        // Person has tags to include but also to exclude
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(Arrays.asList("friends", "-colleagues"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        // Person has tags to include but also to exclude, in mixed order
        predicate = new PersonContainsTagsPredicate(Arrays.asList("-colleagues", "friends"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));

        // Person has tags to include but also to exclude, in mixed case
        predicate = new PersonContainsTagsPredicate(Arrays.asList("fRiENds", "-colLEaguEs"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));
    }
}
```
###### \test\java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code facebookAddress} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFacebookAddress(String facebookAddress) {
        try {
            ParserUtil.parseFacebookAddress(Optional.of(facebookAddress)).ifPresent(descriptor::setFacebookAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("facebookAddress is expected to be unique.");
        }
        return this;
    }
```
###### \test\java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code FacebookAddress} of the {@code Person} that we are building.
     */
    public PersonBuilder withFacebookAddress(String facebookAddress) {
        this.person.setFacebookAddress(new FacebookAddress(facebookAddress));
        return this;
    }
```
###### \test\java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book
        except facebook address -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_AMY)
                .withFacebookAddress(VALID_FACEBOOKADDRESS_BOB).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_AMY + FACEBOOK_ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        assertCommandSuccess(command, toAdd);
```
