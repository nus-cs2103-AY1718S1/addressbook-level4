package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;

public class AutocompleterTest extends GuiUnitTest {

    private static final String EMPTY_STRING = "";
    private static final String ADD_COMMAND_WORD = "add";
    private static final String EDIT_COMMAND_WORD = "edit";
    private static final String MULTIPLE_RESULTS_MESSAGE = "Multiple matches found:" + "\n" + "edit" + "\t" + "exit";
    private static final String PROMPT_USER_TO_USE_HELP_MESSAGE = "To see what commands are available, type 'help' "
            + "into the command box";

    private ResultDisplayHandle resultDisplayHandle;
    private Autocompleter autocompleter;

    @Before
    public void setUp() {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));
        autocompleter = new Autocompleter();
    }

    @Test
    public void autocomplete() throws Exception {
        // default result text
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete with empty string
        String autocompleteResult = autocompleter.autocomplete(EMPTY_STRING);
        assertEquals(autocompleteResult, EMPTY_STRING);
        guiRobot.pauseForEvent();
        assertEquals(PROMPT_USER_TO_USE_HELP_MESSAGE, resultDisplayHandle.getText());

        // lowercase autocomplete with only one autocomplete option
        autocompleteResult = autocompleter.autocomplete("a");
        assertEquals(autocompleteResult, ADD_COMMAND_WORD);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // uppercase autocomplete with only one autocomplete option
        autocompleteResult = autocompleter.autocomplete("A");
        assertEquals(autocompleteResult, ADD_COMMAND_WORD);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // mix uppercase and lowercase autocomplete with only one autocomplete option
        autocompleteResult = autocompleter.autocomplete("Ed");
        assertEquals(autocompleteResult, EDIT_COMMAND_WORD);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // lowercase autocomplete with multiple autocomplete options
        autocompleteResult = autocompleter.autocomplete("e");
        assertEquals(autocompleteResult, "e");
        guiRobot.pauseForEvent();
        assertEquals(MULTIPLE_RESULTS_MESSAGE, resultDisplayHandle.getText());

        // uppercase autocomplete with multiple autocomplete options
        autocompleteResult = autocompleter.autocomplete("E");
        assertEquals(autocompleteResult, "E");
        guiRobot.pauseForEvent();
        assertEquals(MULTIPLE_RESULTS_MESSAGE, resultDisplayHandle.getText());

        // autocomplete with no possible options
        autocompleteResult = autocompleter.autocomplete("Z");
        assertEquals(autocompleteResult, "Z");
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

    }

}
