package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.SearchBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class SearchBoxTest extends GuiUnitTest {
    private static final String COMMAND = "abc";
    private static final String BACKSPACE = "\u0008";
    private static final String DELETE = "\u007f";
    private ArrayList<String> defaultStyleOfSearchBox;
    private SearchBoxHandle searchBoxHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        SearchBox searchBox = new SearchBox(logic);
        searchBoxHandle = new SearchBoxHandle(getChildNode(searchBox.getRoot(),
                SearchBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(searchBox);

        defaultStyleOfSearchBox = new ArrayList<>(searchBoxHandle.getStyleClass());
    }

    @Test
    public void searchBox_successfulCommand() {
        assertBehaviorForSuccessfulCommand();
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text remains <br>
     */
    private void assertBehaviorForSuccessfulCommand() {
        guiRobot.type(KeyCode.A);
        guiRobot.type(KeyCode.B);
        guiRobot.type(KeyCode.C);
        assertEquals(COMMAND, searchBoxHandle.getInput());
    }
}
