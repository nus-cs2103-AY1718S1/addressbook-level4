# mingruiii
###### /java/guitests/guihandles/CommandBoxHandle.java
``` java
    /**
     * Enters the given command in the Command Box, wait for the drop-down list to appear and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean inputAndEnter(String input) {
        click();
        guiRobot.interact(() -> getRootNode().setText(input));
        guiRobot.pauseForDropDownList();

        guiRobot.type(KeyCode.ENTER);

        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

```
###### /java/guitests/GuiRobot.java
``` java
    /**
     * Pauses execution for {@code PAUSE_FOR_DROP_DOWN_LIST_TO_APPEAR} milliseconds for the auto-completion drop-down
     * list to appear. This method will be disabled when the GUI tests are executed in headless mode to avoid
     * unnecessary delays.
     */
    public void pauseForDropDownList() {
        sleep(PAUSE_FOR_DROP_DOWN_LIST_TO_APPEAR);
    }

```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    /**
     * Executes the given {@code command}, press enter to select OK on the confirmation alert, then confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccessAfterEnter(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            guiRobot.push(KeyCode.ENTER);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    @Test
    public void addCommandAutoComplete() {
        assertAutoComplete("a", AddCommand.COMMAND_WORD);
        assertInputHistory(KeyCode.TAB, AddCommand.FORMAT);
    }

    @Test
    public void selectCommandAutoComplete() {
        assertAutoComplete("sel", SelectCommand.COMMAND_WORD);
        assertInputHistory(KeyCode.TAB, SelectCommand.FORMAT);
    }

```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    /**
     * Types in {@code input} and presses enter to select the first option from the drop-down list,
     * then checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertAutoComplete(String input, String expectedCommand) {
        commandBoxHandle.inputAndEnter(input);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }
}
```
###### /java/systemtests/ClearCommandSystemTest.java
``` java
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String, Model)} except that enter key is
     * pressed to select OK on the confirmation alert
     */
    private void assertCommandSuccessAfterPressingEnter(String command, String expectedResultMessage,
                                                        Model expectedModel) {
        executeCommand(command);
        guiRobot.push(KeyCode.ENTER);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

```
###### /java/systemtests/DeleteCommandSystemTest.java
``` java
    private void assertCommandSuccessAfterPressingEnter(String command, Model expectedModel,
                                                        String expectedResultMessage) {
        assertCommandSuccessAfterPressingEnter(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)}, except that enter
     * key is pressed to select OK on the confirmation alert
     */
    private void assertCommandSuccessAfterPressingEnter(String command, Model expectedModel,
                                                        String expectedResultMessage, Index expectedSelectedCardIndex) {
        executeCommand(command);
        guiRobot.push(KeyCode.ENTER);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
```
