# siri99
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getBirthday()  {
        return birthdayLabel.getText();
    }
```
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
    @Test
    public void containsSubstringOfWord_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "Normal sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsSubstringOfWord_noSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "Alice", Optional.empty());
    }

    @Test
    public void containsSubstringOfWord_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrown(IllegalArgumentException.class, "Normal sentence", "Alice John",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsSubstringOfWord_validInputs_correctResult() {

        // Empty sentences
        assertFalse(StringUtil.containsSubstringOfWord("", "Alice"));
        assertFalse(StringUtil.containsSubstringOfWord("    ", "987"));

        // Matches multiple words
        assertTrue(StringUtil.containsWordIgnoreCase("Alice John Johnny", "john"));

        // Matches partial words and complete words (finding by substring is also case-insensitive)
        assertTrue(StringUtil.containsSubstringOfWord("Alice John agatha", "ag"));
        assertTrue(StringUtil.containsSubstringOfWord("Alice John agatha", "Al")); // First word in sentence
        assertTrue(StringUtil.containsSubstringOfWord("Alice John agatha", "  agatha ")); // trailing spaces
        assertTrue(StringUtil.containsSubstringOfWord("Alice John agatha", "agatha")); // Last word
        assertTrue(StringUtil.containsSubstringOfWord("Alice", "alice")); // one word
        assertFalse(StringUtil.containsSubstringOfWord("Alicia John carLo", "Johnny"));

    }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortFilteredPersonListName() {
            fail("This method should not be called.");
        }

        @Override
        public void sortFilteredPersonListBirthday() {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addFavouritePerson(ReadOnlyPerson target) {
            fail("This method should not be called.");
        }

        @Override
        public void removeFavouritePerson(ReadOnlyPerson target) {
            fail("This method should not be called.");
        }

        @Override
        public void changeListTo(String listName) {
            fail("This method should not be called.");
        }

        @Override
        public void setCurrentList(String currentList)  {
            fail("This method should not be called.");
        }

        @Override
        public String getCurrentList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredFavouritePersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredFavouritePersonList() {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_BIRTHDAY_AMY = "21/04/1973";
    public static final String VALID_BIRTHDAY_BOB = "16/08/1968";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    // words not allowed for birthday
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "5/March/1997";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withScore(VALID_SCORE_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withScore(VALID_SCORE_BOB).withTags(VALID_TAG_HUSBAND,
                        VALID_TAG_FRIEND).build();
```
###### \java\seedu\address\logic\commands\FavCommandTest.java
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
 * Contains integration tests (interaction with the Model) and unit tests for {@code FavCommand}.
 */
public class FavCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFav = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavCommand favCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavCommand.MESSAGE_FAV_PERSON_SUCCESS, personToFav);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addFavouritePerson(personToFav);

        assertCommandSuccess(favCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavCommand favCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFav = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavCommand favCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavCommand.MESSAGE_FAV_PERSON_SUCCESS, personToFav);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addFavouritePerson(personToFav);

        assertCommandSuccess(favCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FavCommand favCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FavCommand favFirstCommand = new FavCommand(INDEX_FIRST_PERSON);
        FavCommand favSecondCommand = new FavCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(favFirstCommand.equals(favFirstCommand));

        // same values -> returns true
        FavCommand favFirstCommandCopy = new FavCommand(INDEX_FIRST_PERSON);
        assertTrue(favFirstCommand.equals(favFirstCommandCopy));

        // different types -> returns false
        assertFalse(favFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(favFirstCommand.equals(favSecondCommand));
    }

    /**
         * Returns a {@code FavCommand} with the parameter {@code index}.
     */
    private FavCommand prepareCommand(Index index) {
        FavCommand favCommand = new FavCommand(index);
        favCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favCommand;
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteListCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class FavouriteListCommandTest {

    private Model model;
    private Model expectedModel;
    private FavouriteListCommand favlistCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        favlistCommand = new FavouriteListCommand();
        favlistCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(favlistCommand, model, FavouriteListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    final String invalidSortFilter = "random";
    final String sortFilterName = "name";
    final String sortFilterBirthday = "birthday";
    final String sortFilterBirthdayAlias = "b";
    final String sortFilterScore = "score";
    final String sortFilterDefault = "";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {

        // Default filter - sort by name
        assertCommandSuccess(prepareCommand(sortFilterDefault), model, SortCommand.MESSAGE_SUCCESS_NAME, model);

        // Sort in alphabhetical order of name
        assertCommandSuccess(prepareCommand(sortFilterName), model, SortCommand.MESSAGE_SUCCESS_NAME, model);

        // Sort in ascending order of birthdays
        assertCommandSuccess(prepareCommand(sortFilterBirthday), model, SortCommand.MESSAGE_SUCCESS_BIRTHDAY, model);

        // Sort in ascending order of birthdays (with shortcut 'b')
        assertCommandSuccess(prepareCommand(sortFilterBirthdayAlias), model, SortCommand.MESSAGE_SUCCESS_BIRTHDAY,
                model);

        // Sort in ascending order of birthdays (with shortcut 'b')
        assertCommandSuccess(prepareCommand(sortFilterScore), model, SortCommand.MESSAGE_SUCCESS_SCORE, model);
    }

    @Test
    public void equals() {

        final SortCommand sortNameCommand = new SortCommand(sortFilterName);
        final SortCommand sortBirthdayCommand = new SortCommand(sortFilterBirthday);
        final SortCommand sortBirthdayAliasCommand = new SortCommand(sortFilterBirthdayAlias);
        final SortCommand sortScoreCommand = new SortCommand(sortFilterScore);
        final SortCommand sortDefaultCommand = new SortCommand(sortFilterDefault);

        SortCommand commandExecuted;

        // same filterTypes (Name) -> returns true
        commandExecuted = new SortCommand(sortFilterName);
        assertTrue(sortNameCommand.equals(commandExecuted));

        // same filterTypes (Birthday) -> returns true
        commandExecuted = new SortCommand(sortFilterBirthday);
        assertTrue(sortBirthdayCommand.equals(commandExecuted));

        // same filterTypes (Birthday shortcut) -> returns true
        commandExecuted = new SortCommand(sortFilterBirthdayAlias);
        assertTrue(sortBirthdayAliasCommand.equals(commandExecuted));

        // same filterTypes (Score) -> returns true
        commandExecuted = new SortCommand(sortFilterScore);
        assertTrue(sortScoreCommand.equals(commandExecuted));

        // same filterTypes (No filter) -> returns true
        commandExecuted = new SortCommand(sortFilterDefault);
        assertTrue(sortDefaultCommand.equals(commandExecuted));

        // same object -> returns true
        assertTrue(sortBirthdayCommand.equals(sortBirthdayCommand));

        // null object-> returns false
        assertFalse(sortNameCommand.equals(null));

        // different filterTypes -> returns false
        assertFalse(sortNameCommand.equals(new SortCommand(invalidSortFilter)));

        // different command types -> returns false
        assertFalse(sortDefaultCommand.equals(new UndoCommand()));

    }

    /**
     * Returns a {@code SortCommand} with parameteres.
     */
    private SortCommand prepareCommand(String sortFilter) {
        SortCommand sortCommand = new SortCommand(sortFilter);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\commands\UnfavCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UnfavCommand}.
 */
public class UnfavCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /*
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToUnfav = model.getFilteredFavouritePersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnfavCommand unfavCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavCommand.MESSAGE_FAV_PERSON_SUCCESS, personToUnfav);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeFavouritePerson(personToUnfav);

        assertCommandSuccess(unfavCommand, model, expectedMessage, expectedModel);
    }
    */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnfavCommand unfavCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unfavCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /*
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        ReadOnlyPerson personToFav = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavCommand favCommand = prepareFavCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addFavouritePerson(personToFav);
        showFirstFavouritePersonOnly(model);

        ReadOnlyPerson personToUnfav = model.getFilteredFavouritePersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnfavCommand unfavCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavCommand.MESSAGE_FAV_PERSON_SUCCESS, personToUnfav);

        //Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeFavouritePerson(personToUnfav);
        showNoPerson(expectedModel);

        assertCommandSuccess(unfavCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnfavCommand unfavCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unfavCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnfavCommand unfavFirstCommand = new UnfavCommand(INDEX_FIRST_PERSON);
        UnfavCommand unfavSecondCommand = new UnfavCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unfavFirstCommand.equals(unfavFirstCommand));

        // same values -> returns true
        UnfavCommand unfavFirstCommandCopy = new UnfavCommand(INDEX_FIRST_PERSON);
        assertTrue(unfavFirstCommand.equals(unfavFirstCommandCopy));

        // different types -> returns false
        assertFalse(unfavFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unfavFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unfavFirstCommand.equals(unfavSecondCommand));
    }

    /**
     * Returns a {@code UnfavCommand} with the parameter {@code index}.
     */
    private UnfavCommand prepareCommand(Index index) {
        UnfavCommand unfavCommand = new UnfavCommand(index);
        unfavCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unfavCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredFavouritePersonList(p -> false);

        assert model.getFilteredFavouritePersonList().isEmpty();
    }

    /**
     * Returns a {@code FavCommand} with the parameter {@code index}.
     */
    private FavCommand prepareFavCommand(Index index) {
        FavCommand favCommand = new FavCommand(index);
        favCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        //multiple birthdays - last birthdays accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_AMY
                + BIRTHDAY_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCORE_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // birthday field missing
        Person expectedPersonNoBirthday = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withBirthday("No Birthday Listed").withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withScore(VALID_SCORE_AMY).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + SCORE_DESC_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonNoBirthday));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        //invalid birthday
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_BIRTHDAY_DESC
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        //birthday
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthday(VALID_BIRTHDAY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\FavCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.FavCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FavCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FavCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FavCommandParserTest {

    private FavCommandParser parser = new FavCommandParser();

    @Test
    public void parse_validArgs_returnsFavCommand() {
        assertParseSuccess(parser, "1", new FavCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "2", new FavCommand(INDEX_SECOND_PERSON));
        assertParseSuccess(parser, "3", new FavCommand(INDEX_THIRD_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // Fails when user attempts to add a person into favourite list by character
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavCommand.MESSAGE_USAGE));

        // Fails when user attempts to add a person into favourite list by name
        assertParseFailure(parser, "Alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FavCommandParserTest.java
``` java

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String INVALID_BIRTHDAY = "1997/05/07 ";
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String VALID_BIRTHDAY = "17/07/2006";
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseBirthday_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseBirthday(null);
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseBirthday(Optional.of(INVALID_BIRTHDAY));
    }

    @Test
    public void parseBirthday_optionalEmpty_returnsOptionalEmpty() throws Exception {
        //assertFalse(ParserUtil.parseBirthday(Optional.empty()).isPresent());
    }

    @Test
    public void parseBirthday_validValue_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        Optional<Birthday> actualBirthday = ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY));
        assertEquals(expectedBirthday, actualBirthday.get());
    }
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    final String invalidSortFilter = "people";
    final String sortFilterBirthday = "birthday";
    final String sortFilterBirthdayAlias = "b";
    final String sortFilterDefault = "";
    final String sortFilterName = "name";
    final String sortFilterScore = "score";

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() throws Exception {

        String userInput;
        SortCommand expectedCommand;

        // No sortFilter argument: default sort in alphabhetical order of names
        userInput = SortCommand.COMMAND_WORD + sortFilterDefault;
        expectedCommand = new SortCommand(sortFilterDefault);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in alphabhetical order of names
        userInput = SortCommand.COMMAND_WORD + " " + sortFilterName;
        expectedCommand = new SortCommand(sortFilterName);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in ascending order of birthdays
        userInput = SortCommand.COMMAND_WORD + " " + sortFilterBirthday;
        expectedCommand = new SortCommand(sortFilterBirthday);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in ascending order of birthdays using alias 'b' for birthdays
        userInput = SortCommand.COMMAND_WORD + " " + sortFilterBirthdayAlias;
        expectedCommand = new SortCommand(sortFilterBirthdayAlias);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in descending order of scores (9 to 0 : best to worst score)
        userInput = SortCommand.COMMAND_WORD + " " + sortFilterScore;
        expectedCommand = new SortCommand(sortFilterScore);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Sorting in alphabhetical order of names with extra spaces
        userInput = SortCommand.COMMAND_WORD + "     " + sortFilterName;
        expectedCommand = new SortCommand(sortFilterName);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_invalid_arguments() throws Exception {
        String userInput = SortCommand.COMMAND_WORD + " " + invalidSortFilter;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java

```
###### \java\seedu\address\logic\parser\UnfavCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.UnfavCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UnfavCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UnfavCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnfavCommandParserTest {

    private UnfavCommandParser parser = new UnfavCommandParser();

    @Test
    public void parse_validArgs_returnsFavCommand() {
        assertParseSuccess(parser, "1", new UnfavCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "2", new UnfavCommand(INDEX_SECOND_PERSON));
        assertParseSuccess(parser, "3", new UnfavCommand(INDEX_THIRD_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // Fails when user attempts to remove a person from favourite list by character
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavCommand.MESSAGE_USAGE));

        // Fails when user attempts to remove a person from favourite list by name
        assertParseFailure(parser, "Alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\UnfavCommandParserTest.java
``` java

```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BirthdayTest {

    @Test
    public void isValidBirthday() {

        // valid birthdays
        assertTrue(Birthday.isValidBirthday("11/01/1999"));
        assertTrue(Birthday.isValidBirthday("16/08/1968"));
        assertTrue(Birthday.isValidBirthday("21/04/1973"));
        assertTrue(Birthday.isValidBirthday("17/06/2006"));

        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty
        assertFalse(Birthday.isValidBirthday(" ")); // only space
        assertFalse(Birthday.isValidBirthday("1999/01  /11")); // extra space
        assertFalse(Birthday.isValidBirthday("1999/01/11")); // wrong format
        assertFalse(Birthday.isValidBirthday("11/January/1999")); // non-numeric
        assertFalse(Birthday.isValidBirthday("01.02#99")); // non-numeric characters
        assertFalse(Birthday.isValidBirthday("11/02/19899")); // invalid year
        assertFalse(Birthday.isValidBirthday("01/022/1973")); // invalid month
        assertFalse(Birthday.isValidBirthday("40/05/1998")); // invalid date
        assertFalse(Birthday.isValidBirthday("29/02/1989")); // invalid date(non-leap year does not have 29th Feb)

    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_BIRTHDAY = "11/01/1999";
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
            Birthday defaultBirthday = new Birthday(DEFAULT_BIRTHDAY);
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
        assertEquals(expectedPerson.getBirthday().value, actualCard.getBirthday());
```
