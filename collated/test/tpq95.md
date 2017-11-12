# tpq95
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void deleteTag(ReadOnlyPerson person, Tag oldTag)
            throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called.");
        }
```
###### /java/seedu/address/logic/commands/DetagCommandTest.java
``` java
/**
 * Contains integration tests (Interaction with the Model) and unit tests for {@code DetagCommand}.
 */
public class DetagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Index[] indices1 = {fromOneBased(1), fromOneBased(2)};
    private final Index[] indices2 = {fromOneBased(2), fromOneBased(5)};

    @Test
    public void executeValidIndexSuccess() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagList = personToDelete.getTags();
        Tag tag = tagList.iterator().next();

        DetagCommand detagCommand = prepareCommand(indices1, tag);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        for (Index index: indices1) {
            ReadOnlyPerson person = model.getFilteredPersonList().get(index.getZeroBased());
            expectedModel.deleteTag(person, tag);
        }
        assertCommandSuccess(detagCommand, model, String.format(MESSAGE_DETAG_PERSONS_SUCCESS, tag),
                expectedModel);
    }

    @Test
    public void executeInvalidIndexThrowsCommandException() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagList = personToDelete.getTags();
        Tag tag = tagList.iterator().next();

        Index outOfBoundIndex = fromOneBased(model.getFilteredPersonList().size() + 1);
        Index[] indicesOutOfBound = {outOfBoundIndex};
        DetagCommand detagCommand = prepareCommand(indicesOutOfBound, tag);

        assertCommandFailure(detagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        ReadOnlyPerson personFirst = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tagList1 = personFirst.getTags();
        Tag tag1 = tagList1.iterator().next();
        Tag tag2 = setupTestTag(tag1);

        assertFalse(tag1.equals(tag2));

        DetagCommand detagFirstCommand = new DetagCommand(indices1, tag1);
        DetagCommand detagSecondCommand = new DetagCommand(indices2, tag2);

        // same object -> returns true
        assertTrue(detagFirstCommand.equals(detagFirstCommand));

        // same values -> returns true
        DetagCommand detagFirstCommandCopy = new DetagCommand(indices1, tag1);
        assertTrue(detagFirstCommand.equals(detagFirstCommandCopy));

        // different types -> returns false
        assertFalse(detagFirstCommand.equals(1));

        // different person -> returns false
        assertFalse(detagFirstCommand.equals(detagSecondCommand));
    }

    /**
     * Return a different tag of a person other than First Person in the model
     * @param tagBase
     * @return
     */
    private Tag setupTestTag(Tag tagBase) {
        for (ReadOnlyPerson personNext : model.getFilteredPersonList()) {
            Set<Tag> tagList = personNext.getTags();
            Tag tag = tagList.iterator().next();
            if (!tag.equals(tagBase)) {
                return tag;
            }
        }
        return null;
    }

    /**
     * Returns an {@code DetagCommand}.
     */
    private DetagCommand prepareCommand(Index[] indices, Tag tag) {
        DetagCommand detagCommand = new DetagCommand(indices, tag);
        detagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return detagCommand;
    }
}
```
###### /java/seedu/address/logic/commands/FindCommandTest.java
``` java
    @Test
    public void executeMultipleBirthdaysMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("01-03-2005 01-03-1995 01-03-1996");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void executeMultipleTagsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand("family colleague");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL));
    }

    @Test
    public void executeMultipleTypeKeywordMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Alice 01-03-1996 buff");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, FIONA, GEORGE));
    }
```
###### /java/seedu/address/logic/commands/FindTaskCommandTest.java
``` java
    @Test
    public void executeMultipleDeadlinesMultipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASK_LISTED_OVERVIEW, 2);
        FindTaskCommand command = prepareCommand("01-11-2017 20-11-2017");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(QUIZ, BUY_TICKETS));
    }

    @Test
    public void executeMultipleKeywordTypeMultipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASK_LISTED_OVERVIEW, 3);
        FindTaskCommand command = prepareCommand("code 01-11-2017 18-11-2017");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ASSIGNMENT, QUIZ, SUBMISSION));
    }
```
###### /java/systemtests/DetagCommandSystemTest.java
``` java
public class DetagCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DetagCommand.MESSAGE_USAGE);

    @Test
    public void detag() throws CommandException {
        /* ----------------- Performing detag operation while an unfiltered list is being shown -------------------- */

        /* Case: detag first tag of the first person in the list, command with leading spaces and trailing spaces
         *  -> detag 1 tag of first person
         *  */
        Model expectedModel = getModel();
        Tag deletedPersonTag = removeTag(expectedModel, INDEX_FIRST_PERSON);
        String command = "     " + DetagCommand.COMMAND_WORD + "   " + INDEX_FIRST_PERSON.getOneBased() + "  "
                + PREFIX_TAG + deletedPersonTag.tagName;
        String expectedResultMessage = String.format(MESSAGE_DETAG_PERSONS_SUCCESS, deletedPersonTag);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* Case: detag the first tag of last person in the list
         * -> detag first tag
         * */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertTrue(expectedModel.equals(modelBeforeDeletingLast));
        assertTrue(lastPersonIndex.getZeroBased() == 6);
        assertFalse(modelBeforeDeletingLast.getFilteredPersonList().size() == lastPersonIndex.getZeroBased());
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo detagging the last person in the list -> last person's tag restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: mixed case command word -> deleted */
        Model modelAfterDeletingLast = getModel();
        Tag removedPersonTag = removeTag(modelAfterDeletingLast, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_DETAG_PERSONS_SUCCESS, removedPersonTag);
        assertCommandSuccess("DeTaG " + modelAfterDeletingLast.getFilteredPersonList().size()
                        + " t/" + removedPersonTag.tagName, modelAfterDeletingLast,
                expectedResultMessage);

        /* Case: undo detagging the last person in the list -> last person's tag restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo detagging the last person in the list -> last person detagged again */
        command = RedoCommand.COMMAND_WORD;
        removeTag(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: detag first tag the middle person in the list -> detagged */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing detag operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, detag index within bounds of address book and person list -> detagged */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        //assertCommandSuccess(command, expectedModel, MESSAGE_DETAG_PERSONS_SUCCESS);

        /* Case: filtered person list, detag index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DetagCommand.COMMAND_WORD + " " + invalidIndex + " " + PREFIX_TAG + deletedPersonTag.tagName;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid detag operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DetagCommand.COMMAND_WORD + " 0 " + PREFIX_TAG + deletedPersonTag;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DetagCommand.COMMAND_WORD + " -1 " + PREFIX_TAG + deletedPersonTag;
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DetagCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased() + " " + PREFIX_TAG
                + deletedPersonTag.tagName;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DetagCommand.COMMAND_WORD + " abc" + PREFIX_TAG
                + deletedPersonTag, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DetagCommand.COMMAND_WORD + " 1 abc" + PREFIX_TAG
                + deletedPersonTag, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

    }

    /**
     * Select the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the string of tag of person
     */
    private Tag removeTag(Model model, Index index) throws CommandException {
        ReadOnlyPerson person = getPerson(model, index);
        Tag targetTag = person.getTags().iterator().next();

        try {
            model.deleteTag(person, targetTag);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (DuplicatePersonException edea) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_MISSING_TAG);
        }
        return targetTag;
    }
```
###### /java/systemtests/FindTaskCommandSystemTest.java
``` java
        /* Case: find task with deadline in address book, 1 keyword -> 1 task found */
        command = FindTaskCommand.COMMAND_WORD + " 01-11-2017";
        ModelHelper.setFilteredTaskList(expectedModel, QUIZ);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        // TODO: 26/10/17 Find out why 26-10-2017 01-11-2017 gives 3 tasks listed
        /* Case: find multiple tasks in address book, 2 keywords -> 2 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " 20-11-2017 01-11-2017";
        ModelHelper.setFilteredTaskList(expectedModel, BUY_TICKETS, QUIZ);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find multiple tasks in address book, 2 keywords with 1 repeat -> 2 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " 20-11-2017 01-11-2017 20-11-2017";
        ModelHelper.setFilteredTaskList(expectedModel, BUY_TICKETS, QUIZ);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find multiple tasks in address book, 2 keywords of different type -> 2 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " 01-11-2017 gym";
        ModelHelper.setFilteredTaskList(expectedModel, QUIZ, GYM);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();
```
###### /java/systemtests/FindTaskCommandSystemTest.java
``` java
        /* Case: find deadline of task in address book, keyword is substring of deadline -> 0 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " 20-10";
        ModelHelper.setFilteredTaskList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find task in address book, keyword is in wrong format -> 0 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " 20102017";
        ModelHelper.setFilteredTaskList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();

        /* Case: find task not in address book -> 0 tasks found */
        command = FindTaskCommand.COMMAND_WORD + " 01-13-1111";
        assertCommandSuccess(command, expectedModel);
        assertSelectedTaskCardUnchanged();
```
