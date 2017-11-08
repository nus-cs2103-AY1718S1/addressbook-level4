# aaronyhsoh
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void favouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\FavouriteCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
//import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.person.Person;
//import seedu.address.model.person.ReadOnlyPerson;
//import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FavouriteCommand.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final FavouriteCommand standardCommand = new FavouriteCommand(INDEX_FIRST_PERSON);

        // same values -> returns true

        FavouriteCommand commandWithSameValues = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new FavouriteCommand(INDEX_SECOND_PERSON)));

    }

    /**
     * Returns an {@code FavouriteCommand} with parameters {@code index} and {@code descriptor}
     */
    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    public static final String EMPTY_PHONE = "-";
    public static final String EMPTY_EMAIL = "-";
    public static final String EMPTY_ADDRESS = "-";
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // missing phone number
        Person expectedPersonWithoutPhone = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(EMPTY_PHONE)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPersonWithoutPhone));

        // missing address
        Person expectedPersonWithoutAddress = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(EMPTY_ADDRESS).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY, new AddCommand(expectedPersonWithoutAddress));

        // missing email
        Person expectedPersonWithoutEmail = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(EMPTY_EMAIL).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPersonWithoutEmail));
```
###### \java\seedu\address\logic\parser\FavouriteCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.FavouriteCommand;

public class FavouriteCommandParserTest {

    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        assertParseSuccess(parser, "1", new FavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }
}

```
###### \java\systemtests\AddCommandSystemTest.java
``` java
    public static final String EMPTY_PHONE = "-";
    public static final String EMPTY_EMAIL = "-";
    public static final String EMPTY_ADDRESS = "-";
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing phone -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(EMPTY_PHONE).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(EMPTY_EMAIL)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(EMPTY_ADDRESS).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
