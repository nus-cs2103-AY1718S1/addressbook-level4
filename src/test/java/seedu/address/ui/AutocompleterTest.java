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
import seedu.address.ui.autocompleter.Autocompleter;

//@@author Kowalski985
public class AutocompleterTest extends GuiUnitTest {

    private static final String EMPTY_STRING = "";
    private static final String ADD_COMMAND_WORD = "add";
    private static final String EDIT_COMMAND_WORD = "edit";
    private static final String EXIT_COMMAND_WORD = "exit";
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
    public void autocomplete_forNoIndexesOrPrefixes() throws Exception {
        autocompleter.updateAutocompleter("li");
        String autocompleteResult = autocompleter.autocomplete();
        assertEquals("list", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

    }

    @Test
    public void autocomplete_forCommand() throws Exception {
        // default result text
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete with empty string
        assertAutocompleteSuccess(EMPTY_STRING, EMPTY_STRING, PROMPT_USER_TO_USE_HELP_MESSAGE);

        // lowercase autocomplete with only one autocomplete option
        assertAutocompleteSuccess("a", ADD_COMMAND_WORD, EMPTY_STRING);

        // uppercase autocomplete with only one autocomplete option
        assertAutocompleteSuccess("A", ADD_COMMAND_WORD, EMPTY_STRING);

        // mix uppercase and lowercase autocomplete with only one autocomplete option
        assertAutocompleteSuccess("Ed", EDIT_COMMAND_WORD, EMPTY_STRING);

        // lowercase autocomplete with multiple autocomplete options
        assertAutocompleteSuccess("e", EDIT_COMMAND_WORD, MULTIPLE_RESULTS_MESSAGE);

        // uppercase autocomplete with multiple autocomplete options
        autocompleter.updateAutocompleter("");
        assertAutocompleteSuccess("E", EDIT_COMMAND_WORD, MULTIPLE_RESULTS_MESSAGE);

        // lowercase autocomplete with multiple options and cycling
        autocompleter.updateAutocompleter("");
        assertAutocompleteSuccess("E", EDIT_COMMAND_WORD, MULTIPLE_RESULTS_MESSAGE);
        assertAutocompleteSuccess(EDIT_COMMAND_WORD, EXIT_COMMAND_WORD, MULTIPLE_RESULTS_MESSAGE);

        // autocomplete with no possible options
        assertAutocompleteSuccess("Z", "Z", EMPTY_STRING);
    }

    @Test
    public void autocomplete_forPrefixesOnly() throws Exception {

        // autocomplete prefix with first letter of prefix filled in
        assertAutocompleteSuccess("add #", "add #/", EMPTY_STRING);

        // autocomplete first prefix after command word
        assertAutocompleteSuccess("add", "add #/", EMPTY_STRING);

        // autocomplete second prefix
        assertAutocompleteSuccess("add #/RR123456789SG",
                "add #/RR123456789SG n/", EMPTY_STRING);

        // autocomplete cycle first prefix
        assertAutocompleteSuccess("add #/", "add n/", EMPTY_STRING);

        // autocomplete cycle second prefix
        assertAutocompleteSuccess("add #/RR123456789SG",
                "add #/RR123456789SG n/", EMPTY_STRING);
        assertAutocompleteSuccess("add #/RR123456789SG n/",
                "add #/RR123456789SG a/", EMPTY_STRING);
    }

    @Test
    public void autocomplete_forIndexesOnly() throws Exception {
        // autocomplete index after command
        assertAutocompleteSuccess("select", "select 1", EMPTY_STRING);

        // autocomplete cycle to next index after command
        assertAutocompleteSuccess("select 1", "select 2", EMPTY_STRING);

        // autocomplete cycle wrap around
        assertAutocompleteSuccess("select 2", "select 1", EMPTY_STRING);

        // autocomplete when letters are entered into the command box
        assertAutocompleteSuccess("select abc", "select 2", EMPTY_STRING);

        // autocomplete when mix of letters and numbers are entered into command box
        assertAutocompleteSuccess("select abc123", "select 1", EMPTY_STRING);
    }

    @Test
    public void autocomplete_forIndexesAndPrefixes() throws Exception {
        // autocomplete first index after command
        assertAutocompleteSuccess(EDIT_COMMAND_WORD, "edit 1", EMPTY_STRING);

        // autocomplete number out of range of index
        assertAutocompleteSuccess("edit 999", "edit 2", EMPTY_STRING);

        // autocomplete with letters instead and no index
        assertAutocompleteSuccess("edit abc", "edit 1", EMPTY_STRING);

        // autocomplete with prefix without
        assertAutocompleteSuccess("edit #/", "edit 2", EMPTY_STRING);

        // autocomplete with prefix and parameters without index
        assertAutocompleteSuccess("edit #/RR123456789SG", "edit 1", EMPTY_STRING);

        // cycle to next index
        assertAutocompleteSuccess("edit 1", "edit 2", EMPTY_STRING);

        // fill in prefix
        assertAutocompleteSuccess("edit 2 ", "edit 2 #/", EMPTY_STRING);

        // cycle to next prefix
        assertAutocompleteSuccess("edit 2 #/", "edit 2 n/", EMPTY_STRING);

        // move to next prefix
        assertAutocompleteSuccess("edit 2 #/RR123456789SG",
                "edit 2 #/RR123456789SG n/", EMPTY_STRING);
    }


    private void assertAutocompleteSuccess(String commandBoxText, String expectedResult, String expectedMessage) {
        autocompleter.updateAutocompleter(commandBoxText);
        String autocompleteResult = autocompleter.autocomplete();
        assertEquals(expectedResult, autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(expectedMessage, resultDisplayHandle.getText());
    }
}
