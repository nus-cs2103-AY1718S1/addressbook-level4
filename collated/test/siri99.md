# siri99
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label birthdayLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getBirthday()  {
        return birthdayLabel.getText();
    }
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
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
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.getDetails(null);
    }


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
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_BIRTHDAY_AMY = "21/04/1973";
    public static final String VALID_BIRTHDAY_BOB = "16/08/1968";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_SCORE_AMY = "8";
    public static final String VALID_SCORE_BOB = "2";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String SCORE_DESC_AMY = " " + PREFIX_SCORE + VALID_SCORE_AMY;
    public static final String SCORE_DESC_BOB = " " + PREFIX_SCORE + VALID_SCORE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    // words not allowed for birthday
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "5/March/1997";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_SCORE_DESC = " " + PREFIX_SCORE + "A"; // letters not allowed for score
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
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
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }

    /**
     * Updates {@code model}'s favourite filtered list to show only the first person in the {@code model}'s
     * address book.
     */
    public static void showFirstFavouritePersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getFavouritePersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredFavouritePersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredFavouritePersonList().size() == 1;
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
}
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
###### \java\seedu\address\logic\commands\FavCommandTest.java
``` java

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
###### \java\seedu\address\logic\commands\FavouriteListCommandTest.java
``` java

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
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java

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
###### \java\seedu\address\logic\commands\UnfavCommandTest.java
``` java

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
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCORE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCORE_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB + SCORE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB + SCORE_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_SCORE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCORE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCORE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        //invalid birthday
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_BIRTHDAY_DESC
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + SCORE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + SCORE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        //birthday
        userInput = targetIndex.getOneBased() + BIRTHDAY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthday(VALID_BIRTHDAY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
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
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String VALID_BIRTHDAY = "17/07/2006";
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of(INVALID_NAME));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValue_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Optional<Name> actualName = ParserUtil.parseName(Optional.of(VALID_NAME));

        assertEquals(expectedName, actualName.get());
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePhone(null);
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Optional<Phone> actualPhone = ParserUtil.parsePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

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
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java

    @Test
    public void parseAddress_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAddress(null);
    }

    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddress_validValue_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of(VALID_ADDRESS));

        assertEquals(expectedAddress, actualAddress.get());
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmail(null);
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmail(Optional.of(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Optional<Email> actualEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
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
###### \java\seedu\address\model\person\BirthdayTest.java
``` java

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
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        try {
            ParserUtil.parseEmail(Optional.of(email)).ifPresent(descriptor::setEmail);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Score} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withScore(String score) {
        try {
            ParserUtil.parseScore(Optional.of(score)).ifPresent(descriptor::setScore);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("score is expected to be unique");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_BIRTHDAY = "11/01/1999";
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SCORE = "7";
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
            Birthday defaultBirthday = new Birthday(DEFAULT_BIRTHDAY);
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Score defaultScore = new Score(DEFAULT_SCORE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.person = new Person(defaultName, defaultPhone, defaultBirthday, defaultEmail, defaultAddress,
                    defaultScore, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default person's values are invalid.");
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(ReadOnlyPerson personToCopy) {
        this.person = new Person(personToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        try {
            this.person.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        try {
            this.person.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        try {
            this.person.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        try {
            this.person.setPhone(new Phone(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

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
###### \java\seedu\address\testutil\PersonBuilder.java
``` java

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        try {
            this.person.setEmail(new Email(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     *Sets the {@code Score} of the {@code Person} that we are building.
     */
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
        assertEquals(expectedPerson.getBirthday().value, actualCard.getBirthday());
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, ReadOnlyPerson... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<ReadOnlyPerson> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new ReadOnlyPerson[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
```
