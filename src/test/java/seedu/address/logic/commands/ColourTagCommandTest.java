package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

//@@author Xenonym
public class ColourTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_changeColour_success() throws Exception {
        ColourTagCommand command = new ColourTagCommand(new Tag("test"), "red");
        command.setData(model, null, null, null);

        String expectedMessage = String.format(ColourTagCommand.MESSAGE_COLOUR_TAG_SUCCESS, "test", "red");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Map<Tag, String> colours = new HashMap<>(expectedModel.getUserPrefs().getGuiSettings().getTagColours());
        colours.put(new Tag("test"), "red");
        expectedModel.getUserPrefs().getGuiSettings().setTagColours(colours);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
