# PokkaKiyo
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getFacebookAddress() {
        return facebookAddressLabel.getText();
    }
```
###### \java\seedu\address\logic\commands\FindPersonsWithTagsCommandTest.java
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
###### \java\seedu\address\logic\commands\ListTagsCommandTest.java
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
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
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
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
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
###### \java\seedu\address\logic\parser\FindPersonsWithTagsCommandParserTest.java
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
###### \java\seedu\address\model\person\FacebookAddressTest.java
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
###### \java\seedu\address\model\person\PersonContainsTagsPredicateTest.java
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
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
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
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code FacebookAddress} of the {@code Person} that we are building.
     */
    public PersonBuilder withFacebookAddress(String facebookAddress) {
        this.person.setFacebookAddress(new FacebookAddress(facebookAddress));
        return this;
    }
```
###### \java\systemtests\AddCommandSystemTest.java
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
