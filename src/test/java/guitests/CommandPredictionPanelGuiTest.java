package guitests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandPredictionPanelHandle;
import seedu.address.ui.CommandPredictionPanel;

//@@author zameschua
public class CommandPredictionPanelGuiTest extends AddressBookGuiTest {
    private CommandPredictionPanelHandle commandPredictionPanelHandle;
    private String testCaseText = "";
    private List<String> expectedResults;
    private List<String> actualResults;

    @Before
    public void setUp() {
        CommandPredictionPanel commandPredictionPanel = new CommandPredictionPanel();
        commandPredictionPanelHandle = new CommandPredictionPanelHandle(commandPredictionPanel.getListView());
    }

    @Test
    public void execute_emptyString() {
        // Test case: String with empty string
        testCaseText = "";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_singleWhitespace() {
        // Test case: String with 1 whitespace
        testCaseText = " ";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_singleValidLetter() {
        // Test case: String with 1 valid letter
        testCaseText = "e";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }
    @Test
    public void execute_twoValidLetters() {
        // Test case: String with 2 valid letters
        testCaseText = "ex";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_wholeCommand() {
        // Test case: String with a whole command
        testCaseText = "exit";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_singleInvalidCharacter() {
        // Test case: String with 1 invalid character
        testCaseText = "*";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_startsWithInvalidCharacter() {
        // Test case: String starting with invalid character
        testCaseText = "*exit";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    @Test
    public void execute_startsWithWhitespace() {
        // Test case: String starting with whitespace
        testCaseText = " exit";
        enterText(testCaseText);
        expectedResults = (List) commandPredictionPanelHandle.getPredictionResults();
        actualResults = (List) CommandPredictionPanel.filterPredictionResults(testCaseText);
        assertEquals(expectedResults, actualResults);
    }

    /**
     * Helper method to clear the {@link seedu.address.ui.CommandBox}, then fill it with {@code inputText}
     * @param inputText the String to fill the {@code CommandBox} with
     */
    private void enterText(String inputText) {
        getCommandBox().enterText("");
        getCommandBox().enterText(inputText);
    }
}
