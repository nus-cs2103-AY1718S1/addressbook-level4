# fongwz
###### \java\seedu\address\ui\CommandBoxTest.java
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
