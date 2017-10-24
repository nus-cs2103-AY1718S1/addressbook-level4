package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.LinkCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Link;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LinkCommand.
 */
public class LinkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String link = "facebook.com";

        assertCommandFailure(prepareCommand(INDEX_FIRST_PERSON, link), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), link));
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

        // different remarks -> returns false
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