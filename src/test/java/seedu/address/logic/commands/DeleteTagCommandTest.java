package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //@@author lancehaoh
    @Test
    /**
     * Ensure that after a tag is properly deleted from all contacts in the address book
     */
    public void executeFindDeletedTagFailure() {
        ObservableList<ReadOnlyPerson> listOfPersons = model.getFilteredPersonList();

        // Delete a sample tag
        try {
            deleteTagHelper("friends");
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        // Check if the the tag is still present in anyone in the address book
        for (ReadOnlyPerson person : listOfPersons) {
            Set<Tag> tags = person.getTags();
            for (Tag t : tags) {
                String tagName = t.toString();
                assertNotEquals(tagName, "friends");
            }
        }
    }

    //@@author lancehaoh
    @Test
    /**
     * Check if a non-existent tag name can be properly handled
     */
    public void executeDeleteFakeTagNoActionPerformed() {
        // Delete a tag twice to simulate a non-existent tag being deleted
        try {
            deleteTagHelper("friends");
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        List<Tag> oldListOfTags = model.getAddressBook().getTagList();

        try {
            deleteTagHelper("friends");
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        List<Tag> newListOfTags = model.getAddressBook().getTagList();

        assertEquals(oldListOfTags, newListOfTags);
    }

    //@@author lancehoah
    @Test
    /**
     * Tests if delete tag command can be successfully autocorrected
     * up to maximum of 2 character substitutions
     *
     */
    public void executeDeleteTagCommandWrongSpellingCommandCorrected() {
        Command commandWithOneSubstitution = parseWronglySpeltDeleteTagCommand("deletatag friends");

        // Verify if command was parse to search command
        assertTrue(commandWithOneSubstitution instanceof DeleteTagCommand);

        // Check if able to execute this search command
        try {
            commandWithOneSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithOneSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        Command commandWithTwoSubstitution = parseWronglySpeltDeleteTagCommand("deletetgf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithTwoSubstitution instanceof DeleteTagCommand);

        // Check if able to execute this search command
        try {
            commandWithTwoSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithTwoSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    //@@author lancehaoh
    /**
     * Helper method to autocorrect wrongly spelt commands
     */
    private Command parseWronglySpeltDeleteTagCommand(String userinput) {
        AddressBookParser parser = new AddressBookParser();
        Command parsedCommand = null;
        // Parse a search command with small spelling error
        try {
            parsedCommand = parser.parseCommand(userinput);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return parsedCommand;
    }

    //@@author lancehaoh
    /**
     * Returns a {@code DeleteTagCommand} with the parameter {@code tag_name}.
     */
    private DeleteTagCommand prepareCommand(String tagName) {
        DeleteTagCommand deleteTagCommand = null;
        try {
            deleteTagCommand = new DeleteTagCommand(new Tag(tagName));
            deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteTagCommand;
    }

    /**
     * Helper method for deleting a given tagName
     *
     * @param tagName An arbitrary string
     *
     * Executes a Delete Tag command to delete tags
     * with matching tag name from all contacts
     */
    private void deleteTagHelper(String tagName) throws CommandException {
        DeleteTagCommand deleteTagCommand = prepareCommand(tagName);
        deleteTagCommand.execute();
    }
}
