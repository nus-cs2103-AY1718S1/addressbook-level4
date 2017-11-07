# lancehoah
###### \java\seedu\address\logic\commands\DeleteTagCommandTest.java
``` java
    @Test
    /**
     * Tests if delete tag command can be successfully autocorrected
     * up to maximum of 2 character substitutions
     *
     */
    public void executeDeleteTagCommandWrongSpellingCommandCorrected() {
        Command commandWithOneSubstitution = parseWronglySpeltDeleteTagCommand("deletatag friends");

        // Verify if command was parse to search command
        assertTrue(commandWithOneSubstitution instanceof DeleteTagCommand);

        // Check if able to execute this search command
        try {
            commandWithOneSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithOneSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        Command commandWithTwoSubstitution = parseWronglySpeltDeleteTagCommand("deletetgf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithTwoSubstitution instanceof DeleteTagCommand);

        // Check if able to execute this search command
        try {
            commandWithTwoSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithTwoSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

```
###### \java\seedu\address\logic\commands\SearchCommandTest.java
``` java
    @Test
    /**
     * Tests if search command can be successfully autocorrected
     * up to maximum of 2 character substitutions
     */
    public void executeSearchCommandWrongSpellingCommandCorrected() {
        Command commandWithOneSubstitution = parseWronglySpeltSearchCommand("searcf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithOneSubstitution instanceof SearchCommand);

        // Check if able to execute this search command
        try {
            commandWithOneSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithOneSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }

        Command commandWithTwoSubstitution = parseWronglySpeltSearchCommand("searf friends");

        // Verify if command was parse to search command
        assertTrue(commandWithTwoSubstitution instanceof SearchCommand);

        // Check if able to execute this search command
        try {
            commandWithTwoSubstitution.setData(model, new CommandHistory(), new UndoRedoStack());
            commandWithTwoSubstitution.execute();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

```
