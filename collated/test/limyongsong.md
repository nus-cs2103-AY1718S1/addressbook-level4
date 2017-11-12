# limyongsong
###### \java\seedu\address\logic\commands\AddRemarkCommandTest.java
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

import java.util.ArrayList;

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
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

public class AddRemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        //"" added in front of "Some remark" because empty remarks are also saved in the remark arraylist
        // and default sample data tested has "" remark
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("", "Some remark").build();

        AddRemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, "Some remark");

        String expectedMessage = String.format(AddRemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, "\nRemarks: "
                + editedPerson.getRemark());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("", "Some remark").build();
        AddRemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, "Some remark");

        String expectedMessage = String.format(AddRemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, "\nRemarks: "
                + editedPerson.getRemark());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddRemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

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

        AddRemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index} and {@code remark}
     */
    private AddRemarkCommand prepareCommand(Index index, String remark) {
        ArrayList<Remark> remarks = new ArrayList<>();
        remarks.add(new Remark(remark));
        AddRemarkCommand remarkCommand = new AddRemarkCommand(index, remarks);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

    @Test
    public void equals() {
        ArrayList<Remark> remarksAmy = new ArrayList<>();
        remarksAmy.add(new Remark(VALID_REMARK_AMY));
        final AddRemarkCommand standardCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, remarksAmy);

        // same values -> returns true
        AddRemarkCommand commandWithSameValues = new AddRemarkCommand(INDEX_FIRST_PERSON, remarksAmy);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddRemarkCommand(INDEX_SECOND_PERSON, remarksAmy)));

        ArrayList<Remark> remarksBob = new ArrayList<>();
        remarksBob.add(new Remark(VALID_REMARK_BOB));
        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddRemarkCommand(INDEX_FIRST_PERSON, remarksBob)));
    }
}
```
###### \java\seedu\address\logic\commands\LinkCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_BOB;
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
import seedu.address.model.person.Link;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LinkCommand.
 */
public class LinkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLink_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withLink("twitter.com/").build();

        LinkCommand linkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getLink().value);

        String expectedMessage = String.format(LinkCommand.MESSAGE_ADD_LINK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteLink_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setLink(new Link(""));

        LinkCommand linkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getLink().toString());

        String expectedMessage = String.format(LinkCommand.MESSAGE_DELETE_LINK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LinkCommand linkCommand = prepareCommand(outOfBoundIndex, VALID_LINK_BOB);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        LinkCommand linkCommand = prepareCommand(outOfBoundIndex, VALID_LINK_BOB);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final LinkCommand standardCommand = new LinkCommand(INDEX_FIRST_PERSON, new Link(VALID_LINK_AMY));

        // same values -> returns true
        LinkCommand commandWithSameValues = new LinkCommand(INDEX_FIRST_PERSON, new Link(VALID_LINK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new LinkCommand(INDEX_SECOND_PERSON, new Link(VALID_LINK_AMY))));

        // different links -> returns false
        assertFalse(standardCommand.equals(new LinkCommand(INDEX_FIRST_PERSON, new Link(VALID_LINK_BOB))));
    }

    /**
     * Returns an {@code LinkCommand} with parameters {@code index} and {@code link}
     */
    private LinkCommand prepareCommand(Index index, String link) {
        LinkCommand linkCommand = new LinkCommand(index, new Link(link));
        linkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return linkCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemoveRemarkCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

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
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class RemoveRemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("").build();

        RemoveRemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON,  1);

        String expectedMessage = String.format(RemoveRemarkCommand.MESSAGE_REMOVE_REMARK_SUCCESS, "\nRemarks left: "
                + editedPerson.getRemark());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("").build();
        RemoveRemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, 1);

        String expectedMessage = String.format(RemoveRemarkCommand.MESSAGE_REMOVE_REMARK_SUCCESS, "\nRemarks left: "
                + editedPerson.getRemark());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveRemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_INDEX_BOB);

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

        RemoveRemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_INDEX_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index} and {@code remark}
     */
    private RemoveRemarkCommand prepareCommand(Index index, Integer remarkIndex) {
        ArrayList<Integer> indexArrayList = new ArrayList<>();
        indexArrayList.add(remarkIndex);
        RemoveRemarkCommand remarkCommand = new RemoveRemarkCommand(index, indexArrayList);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

    @Test
    public void equals() {
        ArrayList<Integer> remarkIndexAmy = new ArrayList<>();
        remarkIndexAmy.add(VALID_INDEX_AMY);
        final RemoveRemarkCommand standardCommand = new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarkIndexAmy);

        // same values -> returns true
        RemoveRemarkCommand commandWithSameValues = new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarkIndexAmy);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemoveRemarkCommand(INDEX_SECOND_PERSON, remarkIndexAmy)));

        ArrayList<Integer> remarkIndexBob = new ArrayList<>();
        remarkIndexBob.add(VALID_INDEX_BOB);
        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarkIndexBob)));
    }
}
```
###### \java\seedu\address\logic\parser\AddRemarkCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddRemarkCommand;
import seedu.address.model.person.Remark;

public class AddRemarkCommandParserTest {
    private AddRemarkCommandParser parser = new AddRemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        ArrayList<Remark> remarksNothing = new ArrayList<>();
        remarksNothing.add(new Remark(""));

        //if no remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        AddRemarkCommand expectedCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, remarksNothing);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, AddRemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addRemarkCommandWord_returnsRemarkCommand() throws Exception {
        ArrayList<Remark> remarks = new ArrayList<>();
        remarks.add(new Remark("Some remark."));
        AddRemarkCommand command = (AddRemarkCommand) parser.parseCommand(AddRemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + "Some remark.");
        assertEquals(new AddRemarkCommand(INDEX_FIRST_PERSON, remarks), command);
    }

    @Test
    public void parseCommand_removeRemarkCommandWord() throws Exception {
        ArrayList<Integer> remarkIndexes = new ArrayList<>();
        remarkIndexes.add(1);
        RemoveRemarkCommand command = (RemoveRemarkCommand) parser.parseCommand(RemoveRemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + "1");
        assertEquals(new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarkIndexes), command);
    }

    @Test
    public void parseCommand_link() throws Exception {
        final Link link = new Link("twitter.com/");
        LinkCommand command = (LinkCommand) parser.parseCommand(LinkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_LINK + " " + link.value);
        assertEquals(new LinkCommand(INDEX_FIRST_PERSON, link), command);
    }
    //@@Houjisan
    @Test
    public void parseCommand_favourite() throws Exception {
        final FavouriteStatus favouriteStatus = new FavouriteStatus(true);
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(FavouriteCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new FavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " name");
        assertEquals(new SortCommand("name", false, false), command);
    }

    @Test
    public void parseCommand_tags() throws Exception {
        assertTrue(parser.parseCommand(TagsCommand.COMMAND_WORD) instanceof TagsCommand);
        assertTrue(parser.parseCommand(TagsCommand.COMMAND_WORD + " 2") instanceof TagsCommand);
    }
```
###### \java\seedu\address\logic\parser\LinkCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.model.person.Link;

public class LinkCommandParserTest {
    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Link link = new Link("facebook.com");

        // have link
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_LINK.toString() + " " + link;
        LinkCommand expectedCommand = new LinkCommand(INDEX_FIRST_PERSON, link);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no link
        userInput = targetIndex.getOneBased() + " " + PREFIX_LINK.toString();
        expectedCommand = new LinkCommand(INDEX_FIRST_PERSON, new Link(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, LinkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\RemoveRemarkCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveRemarkCommand;

public class RemoveRemarkCommandParserTest {
    private RemoveRemarkCommandParser parser = new RemoveRemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        ArrayList<Integer> remarksIndexNothing = new ArrayList<>();
        remarksIndexNothing.add(1);

        //if no remark indexes
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + "1";
        RemoveRemarkCommand expectedCommand = new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarksIndexNothing);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemoveRemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\model\person\LinkTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LinkTest {

    @Test
    public void equals() {
        Link link = new Link("facebook.com");

        // same object -> returns true
        assertTrue(link.equals(link));

        // same values -> returns true
        Link linkCopy = new Link(link.value);
        assertTrue(link.equals(linkCopy));

        // different types -> returns false
        assertFalse(link.equals(1));

        // null -> returns false
        assertFalse(link.equals(null));

        // different person -> returns false
        Link differentLink = new Link("facebook.com/gg");
        assertFalse(link.equals(differentLink));
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
     * Sets the {@code Link} of the {@code Person} that we are building.
     */
    public PersonBuilder withLink(String link) {
        this.person.setLink(new Link(link));
        return this;
    }
```
