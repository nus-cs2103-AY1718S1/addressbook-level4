# fongwz
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    /**
     * Testing the command box helper
     */
    @Test
    public void commandBoxHelperTest() {
        // Testing populating command box
        guiRobot.push(KeyCode.A);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, AddCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing DOWN arrow key on command box helper
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing UP arrow key on command box helper
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.UP);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing whether command box helper disappears or appears appropriately
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.A);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, "ea");
        clearCommandBox();

        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.D);
        guiRobot.push(KeyCode.I);
        guiRobot.push(KeyCode.T);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();
    }

    /**
     * Clears the text in current command field
     */
    private void clearCommandBox() {
        while (!commandBoxHandle.getInput().isEmpty()) {
            guiRobot.push(KeyCode.BACK_SPACE);
        }
    }
```
###### /java/seedu/address/logic/commands/ChooseCommandTest.java
``` java
public class ChooseCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validArgs_success() {
        assertExecutionSuccess("linkedin");
        assertExecutionSuccess("meeting");
    }

    @Test
    public void execute_invalidArgs_failure() {
        assertExecutionFailure("gibberish", Messages.MESSAGE_INVALID_BROWSER_INDEX);
    }

    /**
     * Executes a {@code ChooseCommand} with the given {@code arguments},
     * and checks that {@code JumpToBrowserListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(String args) {
        ChooseCommand chooseCommand = new ChooseCommand(args);

        try {
            CommandResult commandResult = chooseCommand.execute();
            assertEquals(ChooseCommand.MESSAGE_SUCCESS + args,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Assert Execution Failed: ", ce);
        }

        JumpToBrowserListRequestEvent event =
                (JumpToBrowserListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(args, event.browserItem);
    }

    /**
     * Executes a {@code ChooseCommand} with the given {@code arguments},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String args, String expectedMessage) {
        ChooseCommand chooseCommand = new ChooseCommand(args);

        try {
            chooseCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
```
