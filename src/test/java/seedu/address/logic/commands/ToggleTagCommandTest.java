package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;

public class ToggleTagCommandTest {


    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {

        ToggleTagColorCommand testCommand = new ToggleTagColorCommand(true, "", "");
        ToggleTagColorCommand testCommandTwo = new ToggleTagColorCommand(true, "", "");
        //Test to ensure command is strictly a RemarkCommand
        assertFalse(testCommand.equals(new AddCommand(CARL)));
        assertFalse(testCommand.equals(new ClearCommand()));
        assertFalse(testCommand.equals(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(testCommand.equals(new HistoryCommand()));
        assertFalse(testCommand.equals(new HelpCommand()));
        assertFalse(testCommand.equals(new RedoCommand()));
        assertFalse(testCommand.equals(new UndoCommand()));
        assertFalse(testCommand.equals(new ListCommand()));
        assertFalse(testCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_AMY)));

        //Test for same object
        assertTrue(testCommand.equals(testCommand));
        assertTrue(testCommandTwo.equals(testCommandTwo));

        //Test to check for null
        assertFalse(testCommand == null);
        assertFalse(testCommandTwo == null);

        //Test to check different booleans returns false
        assertFalse(testCommand.equals(new ToggleTagColorCommand(false, "", "")));
        assertFalse(testCommandTwo.equals(new ToggleTagColorCommand(false, "", "")));

        //Test to check different tag string returns false
        assertFalse(testCommand.equals(new ToggleTagColorCommand(true, "aaa", "")));
        assertFalse(testCommandTwo.equals(new ToggleTagColorCommand(true, "abc", "")));

        //Test to check different color string returns false
        assertFalse(testCommand.equals(new ToggleTagColorCommand(true, "", "aaa")));
        assertFalse(testCommandTwo.equals(new ToggleTagColorCommand(true, "", "abc")));
    }

    @Test
    public void checkCommandResult() throws CommandException {

        //Check if the result message is correct when there is no tags found
        ToggleTagColorCommand command = new ToggleTagColorCommand(true, "nosuchtag", "blue");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertTrue(command.execute().feedbackToUser.equals("No such tag"));

        resetAddressBook();

        //When tag can be found in addressBook
        command = new ToggleTagColorCommand(true, "friends", "blue");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertFalse(command.execute().feedbackToUser.equals("No such tag"));
        assertTrue(command.execute().feedbackToUser.equals("friends tagColor set to blue"));

        resetAddressBook();

        //Check if friends tags are set to color
        command = new ToggleTagColorCommand(true, "friends", "blue");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertTrue(command.execute().feedbackToUser.equals("friends tagColor set to blue"));
        for (Tag tag : model.getAddressBook().getTagList()) {
            if ("friends".equals(tag.tagName)) {
                assertTrue(tag.getTagColor().equals("blue"));
                assertFalse(tag.getTagColor().equals("pink"));
            }
        }

        //Check if color tag will off properly
        command = new ToggleTagColorCommand(false, "", "");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = command.execute();
        for (Tag tag : model.getAddressBook().getTagList()) {
            assertTrue(tag.getTagColor().equals("grey"));
            assertFalse(tag.getTagColor().equals("blue"));
        }
        assertTrue("tagColor set to off".equals(commandResult.feedbackToUser));

        //Check if color will set to random
        command = new ToggleTagColorCommand(true, "", "");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        commandResult = command.execute();
        assertTrue("tagColor set to random".equals(commandResult.feedbackToUser));
    }

    @Test
    public void checkNotNull() throws CommandException {

        ToggleTagColorCommand command = new ToggleTagColorCommand(true, "nosuchtag", "blue");
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        assertNotNull(command.execute());
        assertNotNull(command);

    }

    private void resetAddressBook() {
        model = new ModelManager(new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build(),
                new UserPrefs());
    }


}
