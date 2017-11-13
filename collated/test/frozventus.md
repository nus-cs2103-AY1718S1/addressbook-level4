# frozventus
###### \java\seedu\address\logic\commands\BinDeleteCommandTest.java
``` java
    @Before
    public void setUp() {
        model = new ModelManager(new AddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BinDeleteCommand binDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = BinDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.deleteFromBin(personToDelete);

        assertCommandSuccess(binDeleteCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\BinDeleteCommandTest.java
``` java
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model, true);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BinDeleteCommand binDeleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = BinDeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.deleteFromBin(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(binDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model, true);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        BinDeleteCommand binDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(binDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### \java\seedu\address\logic\commands\BinListCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for BinListCommand.
 */
public class BinListCommandTest {

    private Model model;
    private Model expectedModel;
    private BinListCommand binListCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
        expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();

        binListCommand = new BinListCommand();
        binListCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(binListCommand, model, BinListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model, true);
        assertCommandSuccess(binListCommand, model, BinListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\RestoreCommandTest.java
``` java
    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToRestore = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RestoreCommand restoreCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS + personToRestore;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.restorePerson(personToRestore);

        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\RestoreCommandTest.java
``` java
    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model, true);

        ReadOnlyPerson personToRestore = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RestoreCommand restoreCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = RestoreCommand.MESSAGE_RESTORE_PERSON_SUCCESS + personToRestore;

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();
        expectedModel.restorePerson(personToRestore);
        showNoPerson(expectedModel);

        assertCommandSuccess(restoreCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model, true);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        RestoreCommand restoreCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
    @Test
    public void isValidBirthday() throws Exception {
        // blank Birthday
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only

        // missing parts
        assertFalse(Birthday.isValidBirthday("02-1995")); // missing day
        assertFalse(Birthday.isValidBirthday("21-1992")); // missing month
        assertFalse(Birthday.isValidBirthday("01-01")); // missing year

        // valid Birthday
        assertTrue(Birthday.isValidBirthday("~")); // there is no input for birthday field
        assertTrue(Birthday.isValidBirthday("02-03-1995"));
        assertTrue(Birthday.isValidBirthday("12-12-1999"));
        assertTrue(Birthday.isValidBirthday("05-07-2005"));
    }
}
```
###### \java\seedu\address\TestApp.java
``` java
    /**
     * Returns a defensive copy of the model in Bin configuration.
     */
    public Model getModelBinMode() {
        Model copy = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        copy.setBinDisplay();
        ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
        return copy;
    }

```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    /**
     * Returns a {@code RecycleBin} with three of the typical persons.
     */
    public static RecycleBin getTypicalRecycleBin() {
        RecycleBin rb = new RecycleBin();
        for (ReadOnlyPerson person : getTypicalPersonsForBin()) {
            try {
                rb.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return rb;
    }

    public static List<ReadOnlyPerson> getTypicalPersonsForBin() {
        return new ArrayList<>(Arrays.asList(JEAN, KEN, LINDA));
    }

    public static AddressBookData getTypicalData() {
        return new AddressBookData(getTypicalAddressBook(), getTypicalRecycleBin());
    }

}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Displays all persons in the recycle bin.
     */
    protected void showAllPersonsInBin() {
        executeCommand(BinListCommand.COMMAND_WORD);
        assert getModel().getRecycleBin().getPersonList().size() == getModelWithBin().getFilteredPersonList().size();
    }

    /**
     * Displays all persons in recycle bin with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsInBinWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + FindCommand.KEYWORD_NAME + " " + keyword);
        assert getModelWithBin().getFilteredPersonList().size() <= getModel().getRecycleBin().getPersonList().size();
    }

```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the model and storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     * For use when displaying Recycle Bin
     */
    protected void assertApplicationDisplaysExpectedWithBin(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(expectedModel, getModelWithBin());
        assertEquals(expectedModel.getAddressBook(), testApp.readStorageAddressBook());
        assertEquals(expectedModel.getRecycleBin(), testApp.readStorageRecycleBin());
    }

```
###### \java\systemtests\BinClearCommandSystemTest.java
``` java
public class BinClearCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void clear() {
        executeCommand(BinListCommand.COMMAND_WORD);
        final Model defaultModel = getModelWithBin();

        /* Case: clear non-empty recycle bin, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + BinClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardDeselected();

        /* Case: undo clearing recycle bin -> original recycle bin restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        Model binClearedModel = getModelWithBin();
        assertCommandSuccess(command,  expectedResultMessage, defaultModel);
        assertSelectedCardDeselected();

        /* Case: redo clearing recyclebin -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, binClearedModel);
        assertSelectedCardDeselected();

        /* Case: selects first card in person list and clears recycle bin -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original recycle bin
        selectPerson(Index.fromOneBased(1));
        command = BinClearCommand.COMMAND_WORD;
        expectedResultMessage = BinClearCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, binClearedModel);
        assertSelectedCardDeselected();

        /* Case: filters the recycle bin before clearing -> entire recycle bin cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        command = BinClearCommand.COMMAND_WORD;
        expectedResultMessage = BinClearCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, binClearedModel);
        assertSelectedCardDeselected();

        /* Case: clear empty recycle bin -> cleared */
        command = BinClearCommand.COMMAND_WORD;
        expectedResultMessage = BinClearCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, binClearedModel);
        assertSelectedCardDeselected();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("bINClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code BinClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        Model binClearedModel = new ModelManager(getTypicalAddressBook(), new RecycleBin(), new UserPrefs());
        binClearedModel.setBinDisplay();
        assertCommandSuccess(command, BinClearCommand.MESSAGE_SUCCESS, binClearedModel);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see BinClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModelWithBin();

        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\BinDeleteCommandSystemTest.java
``` java
public class BinDeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, BinDeleteCommand.MESSAGE_USAGE);

    @Test
    public void binDelete() {
        /* ---------------- Performing restore operation while an unfiltered list is being shown ------------------- */

        /* Sets display to binMode */
        String command = BinListCommand.COMMAND_WORD;
        executeCommand(command);

        /* Case: delete the first and last person in the list */
        Model modelBeforeDeletingFirstLast = getModelWithBin();
        Index[] arrayIndex = new Index[2];
        arrayIndex[0] = INDEX_FIRST_PERSON;
        arrayIndex[1] = getLastIndexFromBin(modelBeforeDeletingFirstLast);
        assertCommandSuccess(arrayIndex);

        /* Case: undo deleting the first and last person in the list -> first and last person back in bin */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingFirstLast, expectedResultMessage);

        /* Case: delete the last person in the list -> deleted */
        Model modelBeforeDeletingLast = getModelWithBin();
        Index lastPersonIndex = getLastIndexFromBin(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last person in the list -> last person back in bin */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        binDeletePerson(modelBeforeDeletingLast, lastPersonIndex);
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered bin list, delete index within bounds of recycle bin and bin list -> deleted */
        showPersonsInBinWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModelWithBin().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered bin list, delete index within bounds of recycle bin but out of bounds of bin list
         * -> rejected
         */
        executeCommand(UndoCommand.COMMAND_WORD);
        executeCommand(BinListCommand.COMMAND_WORD);
        showPersonsInBinWithName(KEYWORD_MATCHING_MUELLER);
        int invalidIndex = getModel().getRecycleBin().getPersonList().size();
        command = BinDeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        executeCommand(UndoCommand.COMMAND_WORD);

        /* --------------------- Performing delete operation while a person card is selected ------------------------ */

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        showAllPersonsInBin();
        Model expectedModel = getModelWithBin();
        Index selectedIndex = getLastIndexFromBin(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = BinDeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        ReadOnlyPerson deletedPerson = binDeletePerson(expectedModel, selectedIndex);
        expectedResultMessage = MESSAGE_DELETE_PERSON_SUCCESS + deletedPerson;
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = BinDeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = BinDeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getRecycleBin().getPersonList().size() + 1);
        command = BinDeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid indexes (not in ascending order) -> rejected */
        command = BinDeleteCommand.COMMAND_WORD + " 2, 1";
        assertCommandFailure(command, MESSAGE_INVALID_ORDER_PERSONS_INDEX);

        /* Case: invalid indexes (identical indexes found) */
        command = BinDeleteCommand.COMMAND_WORD + " 1, 1";
        assertCommandFailure(command, MESSAGE_REPEATED_INDEXES);

        /* Case: invalid index (some valid, some invalid) -> rejected */
        command = BinDeleteCommand.COMMAND_WORD + " 1, " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(BinDeleteCommand.COMMAND_WORD + " abc",
                             MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(BinDeleteCommand.COMMAND_WORD + " 1 abc",
                             MESSAGE_INVALID_BIN_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("BiNDelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Deletes the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed person
     */
    private ReadOnlyPerson binDeletePerson (Model model, Index index) {
        ReadOnlyPerson targetPerson = getPersonFromBin(model, index);
        try {
            model.deleteFromBin(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        }
        return targetPerson;
    }

    /**
     * Restores the person at {@code toDeleteFromBin} by creating a default
     * {@code BinDeleteCommand} using {@code toDeleteFromBin} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see BinDeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDeleteFromBin) {
        Model expectedModel = getModelWithBin();
        ReadOnlyPerson deletedPerson = binDeletePerson(expectedModel, toDeleteFromBin);
        String expectedResultMessage = MESSAGE_DELETE_PERSON_SUCCESS + deletedPerson;

        assertCommandSuccess(
                BinDeleteCommand.COMMAND_WORD + " " + toDeleteFromBin.getOneBased(),
                expectedModel, expectedResultMessage);
    }

    /**
     * Deletes the persons at {@code toDeleteFromBin[]} by creating a default {@code BinDeleteCommand} using
     * {@code toDeleteFromBin[]} and performs the same verification as
     * {@code assertCommandSuccess(String, Model, String)}.
     * @see BinDeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index[] toDeleteFromBin) {
        Model expectedModel = getModelWithBin();
        ReadOnlyPerson deletedPerson1 = binDeletePerson(expectedModel, toDeleteFromBin[1]);
        ReadOnlyPerson deletedPerson2 = binDeletePerson(expectedModel, toDeleteFromBin[0]);
        String expectedResultMessage = MESSAGE_DELETE_PERSON_SUCCESS + deletedPerson2 + "\n"
                + MESSAGE_DELETE_PERSON_SUCCESS + deletedPerson1;

        assertCommandSuccess(BinDeleteCommand.COMMAND_WORD + " " + toDeleteFromBin[0].getOneBased() + ", "
                                     + toDeleteFromBin[1].getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see BinDeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin("", expectedResultMessage, expectedModel);
        assertSelectedCardDeselected();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModelWithBin();

        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

```
###### \java\systemtests\RestoreCommandSystemTest.java
``` java
public class RestoreCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_RESTORE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE);

    @Test
    public void restore() {
        /* ---------------- Performing restore operation while an unfiltered list is being shown ------------------- */

        /* Sets display to binMode */
        String command = BinListCommand.COMMAND_WORD;
        executeCommand(command);

        /* Case: restore the first and last person in the list */
        Model modelBeforeRestoringFirstLast = getModelWithBin();
        Index[] arrayIndex = new Index[2];
        arrayIndex[0] = INDEX_FIRST_PERSON;
        arrayIndex[1] = getLastIndexFromBin(modelBeforeRestoringFirstLast);
        assertCommandSuccess(arrayIndex);

        /* Case: undo restoring the first and last person in the list -> first and last person back in bin */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeRestoringFirstLast, expectedResultMessage);

        /* Case: restore the last person in the list -> restored */
        Model modelBeforeRestoringLast = getModelWithBin();
        Index lastPersonIndex = getLastIndexFromBin(modelBeforeRestoringLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo restoring the last person in the list -> last person back in bin */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeRestoringLast, expectedResultMessage);

        /* Case: redo restoring the last person in the list -> last person restored again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        restorePerson(modelBeforeRestoringLast, lastPersonIndex);
        assertCommandSuccess(command, modelBeforeRestoringLast, expectedResultMessage);

        /* ----------------- Performing restore operation while a filtered list is being shown ---------------------- */

        /* Case: filtered bin list, delete index within bounds of recycle bin and bin list -> deleted */
        showPersonsInBinWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModelWithBin().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered bin list, delete index within bounds of recycle bin but out of bounds of bin list
         * -> rejected
         */
        executeCommand(UndoCommand.COMMAND_WORD);
        executeCommand(BinListCommand.COMMAND_WORD);
        showPersonsInBinWithName(KEYWORD_MATCHING_MUELLER);
        int invalidIndex = getModel().getRecycleBin().getPersonList().size();
        command = RestoreCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        executeCommand(UndoCommand.COMMAND_WORD);


        /* -------------------- Performing restore operation while a person card is selected ------------------------ */

        /* Case: restore the selected person -> person list panel selects the person before restoring the person */
        showAllPersonsInBin();
        Model expectedModel = getModelWithBin();
        Index selectedIndex = getLastIndexFromBin(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = RestoreCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        ReadOnlyPerson restoredPerson = restorePerson(expectedModel, selectedIndex);
        expectedResultMessage = MESSAGE_RESTORE_PERSON_SUCCESS + restoredPerson;
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* -------------------------------- Performing invalid restore operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_RESTORE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_RESTORE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getRecycleBin().getPersonList().size() + 1);
        command = RestoreCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid indexes (not in ascending order) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " 2, 1";
        assertCommandFailure(command, MESSAGE_INVALID_ORDER_PERSONS_INDEX);

        /* Case: invalid indexes (identical indexes found) */
        command = RestoreCommand.COMMAND_WORD + " 1, 1";
        assertCommandFailure(command, MESSAGE_REPEATED_INDEXES);

        /* Case: invalid index (some valid, some invalid) -> rejected */
        command = RestoreCommand.COMMAND_WORD + " 1, " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(RestoreCommand.COMMAND_WORD + " abc",
                             MESSAGE_INVALID_RESTORE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(RestoreCommand.COMMAND_WORD + " 1 abc",
                             MESSAGE_INVALID_RESTORE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ReStOrE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Restores the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed person
     */
    private ReadOnlyPerson restorePerson(Model model, Index index) {
        ReadOnlyPerson targetPerson = getPersonFromBin(model, index);
        try {
            model.restorePerson(targetPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (DuplicatePersonException dpe) {
            assert false : "Duplicate person will not be added to AddressBook.";
        }
        return targetPerson;
    }

    /**
     * Restores the person at {@code toRestore} by creating a default {@code RestoreCommand} using {@code toRestore} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see RestoreCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toRestore) {
        Model expectedModel = getModelWithBin();
        ReadOnlyPerson restoredPerson = restorePerson(expectedModel, toRestore);
        String expectedResultMessage = MESSAGE_RESTORE_PERSON_SUCCESS + restoredPerson;

        assertCommandSuccess(
                RestoreCommand.COMMAND_WORD + " " + toRestore.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Restores the persons at {@code toRestore[]} by creating a default {@code RestoreCommand} using
     * {@code toRestore[]} and performs the same verification as {@code assertCommandSuccess (String, Model, String)}.
     * @see RestoreCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index[] toRestore) {
        Model expectedModel = getModelWithBin();
        ReadOnlyPerson restoredPerson2 = restorePerson(expectedModel, toRestore[1]);
        ReadOnlyPerson restoredPerson1 = restorePerson(expectedModel, toRestore[0]);
        String expectedResultMessage = MESSAGE_RESTORE_PERSON_SUCCESS + restoredPerson1 + "\n"
                + MESSAGE_RESTORE_PERSON_SUCCESS + restoredPerson2;

        assertCommandSuccess(RestoreCommand.COMMAND_WORD + " " + toRestore[0].getOneBased() + ", "
                                     + toRestore[1].getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see RestoreCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin("", expectedResultMessage, expectedModel);
        assertSelectedCardDeselected();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModelWithBin();

        executeCommand(command);
        assertApplicationDisplaysExpectedWithBin(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

```
