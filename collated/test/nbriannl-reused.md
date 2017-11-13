# nbriannl-reused
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
    @Test
    public void equals() throws Exception {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));

        final EditCommand standardCommandForTags = new EditCommand(new Tag("old"), new Tag("new"));

        // same value -> return true
        assertTrue(standardCommandForTags.equals(new EditCommand(new Tag("old"), new Tag("new"))));

        // same object -> return true
        assertTrue(standardCommandForTags.equals(standardCommandForTags));

        // null -> return false
        assertFalse(standardCommandForTags.equals(null));

        // different types -> return false
        assertFalse(standardCommandForTags.equals(new ClearCommand()));

        // different old tag -> return false
        assertFalse(standardCommandForTags.equals(new EditCommand(new Tag("different"), new Tag("new"))));

        // different new tag -> return false
        assertFalse(standardCommandForTags.equals(new EditCommand(new Tag("old"), new Tag("different"))));

        // different everything -> return false
        assertFalse(standardCommandForTags.equals(new EditCommand(new Tag("absolutely"), new Tag("different"))));
    }

```
