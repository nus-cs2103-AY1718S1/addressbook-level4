# Affalen
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    private static final String REMARK_FIELD_ID = "#remark";
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    private final Label remarkLabel;
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public String getRemark() {
        return remarkLabel.getText();
    }
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Food";
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
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
import seedu.address.model.person.Person;
//import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);
        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }
    /*@Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("Some remark").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);
        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }*/
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));
        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }
    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
                FindCommand.COMMAND_WORD + " " + "n/" + keywords.stream().collect(Collectors.joining(" ")));
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "n/Alice", expectedFindNameCommand);

        FindCommand expectedFindNumberCommand =
                new FindCommand(new NumberContainsKeywordsPredicate(Arrays.asList("98765432")));
        assertParseSuccess(parser, "p/98765432", expectedFindNumberCommand);

        FindCommand expectedFindAddressCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Clementi")));
        assertParseSuccess(parser, "a/Clementi", expectedFindAddressCommand);

        FindCommand expectedFindBirthdayCommand =
                new FindCommand(new BirthdayContainsKeywordsPredicate(Arrays.asList("10-10-1995")));
        assertParseSuccess(parser, "b/10-10-1995", expectedFindBirthdayCommand);

        FindCommand expectedFindEmailCommand =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList("john@example.com")));
        assertParseSuccess(parser, "e/john@example.com", expectedFindEmailCommand);

        FindCommand expectedFindRemarkCommand =
                new FindCommand(new RemarkContainsKeywordsPredicate(Arrays.asList("Swimmer")));
        assertParseSuccess(parser, "r/Swimmer", expectedFindRemarkCommand);

        FindCommand expectedFindTagCommand =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends")));
        assertParseSuccess(parser, "t/friends", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t  \t", expectedFindNameCommand);

        assertParseSuccess(parser, " \n p/98765432 \n \t  \t", expectedFindNumberCommand);

        assertParseSuccess(parser, " \n a/Clementi \n \t  \t", expectedFindAddressCommand);

        assertParseSuccess(parser, " \n b/10-10-1995 \n \t  \t", expectedFindBirthdayCommand);

        assertParseSuccess(parser, " \n e/john@example.com \n \t  \t", expectedFindEmailCommand);

        assertParseSuccess(parser, " \n r/Swimmer \n \t  \t", expectedFindRemarkCommand);

        assertParseSuccess(parser, " \n t/friends \n \t  \t", expectedFindTagCommand);
    }

}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();
    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Remark remark = new Remark("Some remark.");
        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);
        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        // nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {
    @Test
    public void equals() {
        Remark remark = new Remark("Hello");
        // same object -> returns true
        assertTrue(remark.equals(remark));
        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));
        // different types -> returns false
        assertFalse(remark.equals(1));
        // null -> returns false
        assertFalse(remark.equals(null));
        // different person -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final String KEYWORD_MATCHING_MEIER = "n/Meier"; // A keyword that matches MEIER
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Carl";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Benson Daniel";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Daniel Benson";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Daniel Benson Daniel";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Daniel Benson NonMatchingKeyWord";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/MeIeR";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Mei";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Meiers";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Mark";
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " " + "n/Daniel" + DANIEL.getPhone().value;
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " " + "n/Daniel" + DANIEL.getAddress().value;
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " " + "n/Daniel" + DANIEL.getEmail().value;
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " " + "n/Daniel" + tags.get(0).tagName;
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        command = FindCommand.COMMAND_WORD + " n/Daniel";
```
