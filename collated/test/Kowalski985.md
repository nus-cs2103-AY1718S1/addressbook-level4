# Kowalski985
###### \java\seedu\address\ui\AutocompleteCommandTest.java
``` java
public class AutocompleteCommandTest {
    @Test
    public void getInstance() throws Exception {
        assertEquals(AutocompleteCommand.ADD, AutocompleteCommand.getInstance("add"));
        assertEquals(AutocompleteCommand.CLEAR, AutocompleteCommand.getInstance("clear"));
        assertEquals(AutocompleteCommand.DELETE, AutocompleteCommand.getInstance("delete"));
        assertEquals(AutocompleteCommand.DELETE_TAG, AutocompleteCommand.getInstance("deleteTag"));
        assertEquals(AutocompleteCommand.EDIT, AutocompleteCommand.getInstance("edit"));
        assertEquals(AutocompleteCommand.EXIT, AutocompleteCommand.getInstance("exit"));
        assertEquals(AutocompleteCommand.FIND, AutocompleteCommand.getInstance("find"));
        assertEquals(AutocompleteCommand.HELP, AutocompleteCommand.getInstance("help"));
        assertEquals(AutocompleteCommand.HISTORY, AutocompleteCommand.getInstance("history"));
        assertEquals(AutocompleteCommand.IMPORT, AutocompleteCommand.getInstance("import"));
        assertEquals(AutocompleteCommand.LIST, AutocompleteCommand.getInstance("list"));
        assertEquals(AutocompleteCommand.NONE, AutocompleteCommand.getInstance("none"));
        assertEquals(AutocompleteCommand.REDO, AutocompleteCommand.getInstance("redo"));
        assertEquals(AutocompleteCommand.SELECT, AutocompleteCommand.getInstance("select"));
        assertEquals(AutocompleteCommand.TAB, AutocompleteCommand.getInstance("tab"));
        assertEquals(AutocompleteCommand.UNDO, AutocompleteCommand.getInstance("undo"));
    }

}
```
###### \java\seedu\address\ui\AutocompleterTest.java
``` java
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
    public void autocomplete_forNoIndexesOrPrefixes() throws Exception {
        autocompleter.updateState("li");
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

        // autocomplete prefix with first letter of prefix filled in
        autocompleter.updateState("add #");
        String autocompleteResult = autocompleter.autocomplete();
        assertEquals("add #/", autocompleteResult);
        guiRobot.pauseForEvent();
        assertEquals(EMPTY_STRING, resultDisplayHandle.getText());

        // autocomplete first prefix after command word
        autocompleter.updateState("add");
        autocompleteResult = autocompleter.autocomplete();
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
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void tabAutoCompleteTest_withNoMatchingCommands() {
        // text field is empty
        assertInputHistory(KeyCode.TAB, "");

        // one letter in text field
        guiRobot.push(KeyCode.Y);
        assertInputHistory(KeyCode.TAB, "y");

        // two letters in text field
        guiRobot.push(KeyCode.T);
        assertInputHistory(KeyCode.TAB, "yt");

        // current text in text field is longer than some commands
        guiRobot.push(KeyCode.Y);
        guiRobot.push(KeyCode.SPACE);
        guiRobot.push(KeyCode.Y);
        assertInputHistory(KeyCode.TAB, "yty y");
    }

    @Test
    public void tabAutoCompleteTest_withOneMatchingCommand() {
        // text in text filed is in lowercase
        guiRobot.push(KeyCode.L);
        assertInputHistory(KeyCode.TAB, COMMAND_THAT_SUCCEEDS);
        guiRobot.push(KeyCode.ENTER);

        // text in text filed is in uppercase
        guiRobot.push(new KeyCodeCombination(KeyCode.L, KeyCombination.SHIFT_DOWN));
        assertInputHistory(KeyCode.TAB, COMMAND_THAT_SUCCEEDS);
        guiRobot.push(KeyCode.ENTER);

        // text in text filed is in mix of uppercase and lowercase
        guiRobot.push(new KeyCodeCombination(KeyCode.E, KeyCombination.SHIFT_DOWN));
        guiRobot.push(KeyCode.D);
        assertInputHistory(KeyCode.TAB, "edit");
    }

    @Test
    public void tabAutoCompleteTest_withMultipleMatchingCommands() {
        guiRobot.push(KeyCode.E);
        assertInputHistory(KeyCode.TAB, "edit");
    }
```
