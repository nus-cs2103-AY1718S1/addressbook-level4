# wenmogu
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        /**
         * This method is called as the construction of a new graph needs the FilteredPersonList.
         * Therefore a dummy list is given.
         */
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            UniquePersonList dummyList = new UniquePersonList();
            return FXCollections.unmodifiableObservableList(dummyList.asObservableList());
        }

```
###### \java\seedu\address\logic\commands\RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagNameWhichExists_success() throws Exception {
        String tagNameToBeRemoved = "owesMoney";
        RemoveTagCommand removeTagCommand = prepareCommand("owesMoney");

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagNameToBeRemoved);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(tagNameToBeRemoved);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagNameWhichDoesNotExists_throwsCommandException() throws Exception {
        String tagNameToBeRemoved = VALID_TAG_FRIEND + "ddd";
        RemoveTagCommand removeTagCommand = prepareCommand(tagNameToBeRemoved);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND);
    }


    @Test
    public void equals() {
        RemoveTagCommand removeFirstCommand = new RemoveTagCommand(VALID_TAG_FRIEND);
        RemoveTagCommand removeSecondCommand = new RemoveTagCommand(VALID_TAG_HUSBAND);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveTagCommand removeFirstCommandCopy = new RemoveTagCommand(VALID_TAG_FRIEND);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * Returns a {@code RemoveTagCommand} with the parameter {@code index}.
     */
    private RemoveTagCommand prepareCommand(String tagToBeRemoved) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagToBeRemoved);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return removeTagCommand;
    }
}
```
###### \java\seedu\address\model\UniqueRelationshipListTest.java
``` java
public class UniqueRelationshipListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRelationshipList uniqueRelationshipList = new UniqueRelationshipList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueRelationshipList.asObservableList().remove(0);
    }
}
```
