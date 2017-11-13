# grantcm
###### \java\seedu\address\logic\AutoCompleteTest.java
``` java
    @Before
    public void setup() {
        commandTrie = new CommandTrie();
    }

    @Test
    public void testAutoComplete() {
        assert commandTrie.attemptAutoComplete("a").equals("add");
        assert commandTrie.attemptAutoComplete("e").equals("e");
        assert commandTrie.attemptAutoComplete("ed").equals("edit");
        assert commandTrie.attemptAutoComplete("exi").equals("exit");
        assert commandTrie.attemptAutoComplete("H").equals("H");
        assert commandTrie.attemptAutoComplete("He").equals("help");
        assert commandTrie.attemptAutoComplete("Hi").equals("history");
        assert commandTrie.attemptAutoComplete("l").equals("list");
        assert commandTrie.attemptAutoComplete("red").equals("redo");
        assert commandTrie.attemptAutoComplete("rem").equals("remark");
        assert commandTrie.attemptAutoComplete("s").equals("select");
        assert commandTrie.attemptAutoComplete("u").equals("undo");
        assert commandTrie.attemptAutoComplete("fil").equals("filter");
        assert commandTrie.attemptAutoComplete("fin").equals("find");
        assert commandTrie.attemptAutoComplete("g").equals("group");
    }

    @Test
    public void testExtendedAutoComplete() {
        assert commandTrie.attemptAutoComplete("add").equals(" n/NAME p/PHONE e/EMAIL a/ADDRESS"
                + " [t/TAG] [d/EXPIRY DATE]\n");
        assert commandTrie.attemptAutoComplete("delete").equals(" INDEX\n");
        assert commandTrie.attemptAutoComplete("edit").equals(" INDEX [n/NAME] [p/PHONE] [e/EMAIL] "
                + "[a/ADDRESS] [t/TAG]\n");
    }
```
###### \java\seedu\address\logic\commands\FilterCommandTest.java
``` java
    @Test
    public void equals() {
        //Test same equality
        FilterGroupCommand filterCommand = new FilterGroupCommand("");
        assertTrue(filterCommand.equals(filterCommand));

        //Test value equality
        FilterGroupCommand copy = new FilterGroupCommand("");
        assertTrue(filterCommand.equals(copy));

        //Text unequal values
        assertFalse(filterCommand.equals(1));
        assertFalse(filterCommand.equals("False"));
    }

    @Test
    public void execute_filterCommand() throws Exception {
        FilterGroupCommand noGroup = new FilterGroupCommand("");
        noGroup.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = noGroup.executeUndoableCommand();
        assertEquals(result.feedbackToUser, FilterGroupCommand.MESSAGE_GROUP_DOESNT_EXIST);

        model.addGroup(new Group("None"));

        FilterGroupCommand groupExists = new FilterGroupCommand("None");
        groupExists.setData(model, new CommandHistory(), new UndoRedoStack());
        result = groupExists.executeUndoableCommand();

        assertEquals(result.feedbackToUser, FilterGroupCommand.MESSAGE_GROUP_IS_EMPTY);
    }
```
###### \java\seedu\address\logic\commands\GroupCommandTest.java
``` java
    @Test
    public void equals() {
        //Test same equality
        GroupCommand groupCommand = new GroupCommand(Arrays.asList("Group", "Trip", "Alice"));
        assertTrue(groupCommand.equals(groupCommand));

        //Test value equality
        GroupCommand copy = new GroupCommand(Arrays.asList("Group", "Trip", "Alice"));
        assertTrue(groupCommand.equals(copy));

        //Test unequal values
        assertFalse(groupCommand.equals(1));
        assertFalse(groupCommand.equals("False"));
    }

    @Test
    public void executeGroupCommandAddSuccessful() throws Exception {
        GroupCommand validCommand = new GroupCommand(Arrays.asList("Group", "Trip", "Alice"));
        validCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        CommandResult result = validCommand.executeUndoableCommand();

        assertEquals(result.feedbackToUser, GroupCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void executeGroupCommandDeleteSuccesful() throws Exception {
        GroupCommand validCommand = new GroupCommand(Arrays.asList("Trip"));
        validCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        Group trip = new Group("Trip");
        model.addGroup(trip);

        CommandResult result = validCommand.executeUndoableCommand();

        assertEquals(result.feedbackToUser, GroupCommand.MESSAGE_SUCCESS);
        assertFalse(model.groupExists(trip));
    }
```
###### \java\seedu\address\model\person\GroupTest.java
``` java
    @Test
    public void isValidGroup () {
        //invalid groupName
        assertFalse(Group.isValidGroup("ab as"));
        assertFalse(Group.isValidGroup(" ab"));
        assertFalse(Group.isValidGroup(" ab ab"));
        assertFalse(Group.isValidGroup("ab ab ab "));
        assertFalse(Group.isValidGroup(" ab  "));

        //valid groupName
        assertTrue(Group.isValidGroup("ab"));
        assertTrue(Group.isValidGroup("Trip"));

    }
```
