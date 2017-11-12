package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalParcels.AMY;
import static seedu.address.testutil.TypicalParcels.IDA;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

//@@author Kowalski985
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
    public void setUp() throws Exception {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        model.addParcel(AMY);
        model.addParcel(IDA);
        autocompleter = new Autocompleter(logic);

    }

    @Test
    public void autocomplete_forCommand() throws Exception {
        // default result text
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete with empty string
        autocompleter.updateState(EMPTY_STRING);
        String autocompleteResult = autocompleter.autocomplete();
        assertEquals(EMPTY_STRING, autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(PROMPT_USER_TO_USE_HELP_MESSAGE, resultDisplayHandle.getText());

        // lowercase autocomplete with only one autocomplete option
        autocompleter.updateState("a");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals(ADD_COMMAND_WORD, autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // uppercase autocomplete with only one autocomplete option
        autocompleter.updateState("A");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals(ADD_COMMAND_WORD, autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // mix uppercase and lowercase autocomplete with only one autocomplete option
        autocompleter.updateState("Ed");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals(EDIT_COMMAND_WORD, autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // lowercase autocomplete with multiple autocomplete options
        autocompleter.updateState("e");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("edit", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(MULTIPLE_RESULTS_MESSAGE, resultDisplayHandle.getText());

        // uppercase autocomplete with multiple autocomplete options
        autocompleter.updateState("");
        autocompleter.updateState("E");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("edit", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(MULTIPLE_RESULTS_MESSAGE, resultDisplayHandle.getText());

        // lowercase autocomplete with multiple options and cycling
        autocompleter.updateState("");
        autocompleter.updateState("E");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("edit", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(MULTIPLE_RESULTS_MESSAGE, resultDisplayHandle.getText());
        autocompleter.updateState(autocompleteResult);
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("exit", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(MULTIPLE_RESULTS_MESSAGE, resultDisplayHandle.getText());

        // autocomplete with no possible options
        autocompleter.updateState("Z");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("Z", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());
    }

    @Test
    public void autocomplete_forPrefixesOnly() throws Exception {

        // autocomplete first prefix after command word
        autocompleter.updateState("add");
        String autocompleteResult = autocompleter.autocomplete();
        assertEquals("add #/", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete second prefix
        autocompleter.updateState("add #/RR123456789SG");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("add #/RR123456789SG n/", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete cycle first prefix
        autocompleter.updateState("add #/");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("add n/", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete cycle second prefix
        autocompleter.updateState("add #/RR123456789SG");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("add #/RR123456789SG n/", autocompleteResult);
        autocompleter.updateState(autocompleteResult);
        autocompleteResult = autocompleter.autocomplete();
        autocompleter.updateState(autocompleteResult);
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("add #/RR123456789SG a/", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());
    }

    @Test
    public void autocomplete_forIndexesOnly() throws Exception {
        // autocomplete index after command
        autocompleter.updateState("select");
        String autocompleteResult = autocompleter.autocomplete();
        assertEquals("select 1", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete cycle to next index after command
        autocompleter.updateState("select 1");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("select 2", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete cycle wrap around
        autocompleter.updateState("select 2");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("select 1", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());
    }

    @Test
    public void autocomplete_forIndexesAndPrefixes() throws Exception {
        // autocomplete first index after command
        autocompleter.updateState("edit");
        String autocompleteResult = autocompleter.autocomplete();
        assertEquals("edit 1", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // cycle to next index
        autocompleter.updateState("edit 1");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("edit 2", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // fill in prefix
        autocompleter.updateState("edit 2 ");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("edit 2 #/", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // cycle to next prefix
        autocompleter.updateState("edit 2 #/");
        autocompleteResult = autocompleter.autocomplete();
        autocompleter.updateState(autocompleteResult);
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("edit 2 n/", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // move to next prefix
        autocompleter.updateState("edit 2 #/RR123456789SG");
        autocompleteResult = autocompleter.autocomplete();
        assertEquals("edit 2 #/RR123456789SG n/", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());
    }

}
