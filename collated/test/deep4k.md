# deep4k
###### \java\seedu\address\logic\commands\AddTaskCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;
import seedu.address.model.alias.exceptions.TokenKeywordNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class AddTaskCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        getAddCommandForTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        Task study = buildTask("Study");
        Task play = buildTask("Play");

        AddTaskCommand addStudyCommand = new AddTaskCommand(study);
        AddTaskCommand addPlayCommand = new AddTaskCommand(play);

        // same object -> returns true
        assertTrue(addStudyCommand.equals(addStudyCommand));

        // same values -> returns true
        AddTaskCommand addStudyCommandCopy = new AddTaskCommand(study);
        assertTrue(addStudyCommand.equals(addStudyCommandCopy));

        // different types -> returns false
        assertFalse(addStudyCommand.equals(1));

        // null -> returns false
        assertFalse(addStudyCommand.equals(null));

        // different person -> returns false
        assertFalse(addStudyCommand.equals(addPlayCommand));
    }

    /**
     * Builds a Task object with only the header
     */
    private static Task buildTask(String header) {
        try {
            return new TaskBuilder().withHeader(header).withIncompletionStatus().build();
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
            return null;
        }
    }

    /**
     * Generates a new AddTaskCommand with the details of the given Task.
     */
    private AddTaskCommand getAddCommandForTask(Task task, Model model) throws IllegalValueException {
        AddTaskCommand command = new AddTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void sortList(String toSort) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void hidePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void pinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void unpinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void addAliasToken(ReadOnlyAliasToken target) throws DuplicateTokenKeywordException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAliasToken(ReadOnlyAliasToken target) throws TokenKeywordNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public int getAliasTokenCount() {
            fail("This method should not be called.");
            return 0;
        }

        @Override
        public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addTask(ReadOnlyTask target) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask updatedTask)
                throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void markTasks(List<ReadOnlyTask> targets)
                throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void unmarkTasks(List<ReadOnlyTask> targets)
                throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            tasksAdded.add(new Task(task));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\AliasCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for AliasCommand.
 */
public class AliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAliasToken_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AliasCommand(null);
    }

    @Test
    public void execute_aliasAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAliasTokenAdded modelStub = new ModelStubAcceptingAliasTokenAdded();
        AliasToken validAliasToken = new AliasTokenBuilder().build();

        CommandResult commandResult = getAliasCommandForAliasToken(validAliasToken, modelStub).execute();

        assertEquals(String.format(AliasCommand.MESSAGE_SUCCESS, validAliasToken), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAliasToken), modelStub.aliasTokensAdded);
    }

    @Test
    public void execute_duplicateAliasToken_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAliasTokenException();
        AliasToken validAliasToken = new AliasTokenBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AliasCommand.MESSAGE_DUPLICATE_ALIAS);

        getAliasCommandForAliasToken(validAliasToken, modelStub).execute();
    }

    @Test
    public void equals() {
        AliasToken ph = new AliasTokenBuilder().withKeyword("ph").build();
        AliasToken st = new AliasTokenBuilder().withKeyword("st").build();
        AliasCommand aliasPhCommand = new AliasCommand(ph);
        AliasCommand aliasStCommand = new AliasCommand(st);

        // same object -> returns true
        assertTrue(aliasPhCommand.equals(aliasPhCommand));

        // same values -> returns true
        AliasCommand aliasPhCommandCopy = new AliasCommand(ph);
        assertTrue(aliasPhCommand.equals(aliasPhCommandCopy));

        // different types -> returns false
        assertFalse(aliasPhCommand.equals(1));

        // null -> returns false
        assertFalse(aliasPhCommand == null);

        // different AliasToken -> returns false
        assertFalse(aliasPhCommand.equals(aliasStCommand));
    }

    /**
     * Generates a new AliasCommand with the details of the given AliasToken
     */
    private AliasCommand getAliasCommandForAliasToken(AliasToken aliasToken, Model model) {
        AliasCommand command = new AliasCommand(aliasToken);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void sortList(String toSort) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void hidePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void pinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void unpinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void addAliasToken(ReadOnlyAliasToken target) throws DuplicateTokenKeywordException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAliasToken(ReadOnlyAliasToken target) throws TokenKeywordNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public int getAliasTokenCount() {
            fail("This method should not be called.");
            return 0;
        }

        @Override
        public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void addTask(ReadOnlyTask target) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask updatedTask)
                throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void markTasks(List<ReadOnlyTask> targets)
                throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void unmarkTasks(List<ReadOnlyTask> targets)
                throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateTokenKeywordException when trying to add an alias.
     */
    private class ModelStubThrowingDuplicateAliasTokenException extends ModelStub {
        @Override
        public void addAliasToken(ReadOnlyAliasToken aliasToken) throws DuplicateTokenKeywordException {
            throw new DuplicateTokenKeywordException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accepts the AliasToken being added
     */
    private class ModelStubAcceptingAliasTokenAdded extends ModelStub {
        private final ArrayList<AliasToken> aliasTokensAdded = new ArrayList<>();

        @Override
        public void addAliasToken(ReadOnlyAliasToken aliasToken) throws DuplicateTokenKeywordException {
            aliasTokensAdded.add(new AliasToken(aliasToken));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeAddRemarkSuccess() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeDeleteRemarkSuccess() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeFilteredListSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("Some remark").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void executeInvalidPersonIndexFilteredListFailure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == (null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\commands\UnaliasCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for UnaliasCommand.
 */
public class UnaliasCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithAlias(), new UserPrefs());

    @Test
    public void executeUnaliasCommandSuccess() throws Exception {
        Keyword toDelete = new Keyword(VALID_ALIAS_KEYWORD_MONDAY);

        ReadOnlyAliasToken aliasTokenToRemove = null;

        for (ReadOnlyAliasToken token : model.getAddressBook().getAliasTokenList()) {
            if (token.getKeyword().keyword.equalsIgnoreCase(toDelete.keyword)) {
                aliasTokenToRemove = token;
                break;
            }
        }
        String message = aliasTokenToRemove.toString();
        String expectedMessage = String.format(UnaliasCommand.MESSAGE_SUCCESS, message);

        UnaliasCommand unaliasCommand = prepareCommand(toDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAliasToken(aliasTokenToRemove);

        assertCommandSuccess(unaliasCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void executeUnaliasCommandFailure() throws Exception {
        Keyword toDelete = new Keyword("tues"); // no such alias keyword exists
        String expectedMessage = UnaliasCommand.MESSAGE_UNKNOWN_ALIAS;
        UnaliasCommand unaliasCommand = prepareCommand(toDelete);

        assertCommandFailure(unaliasCommand, model, expectedMessage);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private UnaliasCommand prepareCommand(Keyword keyword) {
        UnaliasCommand unaliasCommand = new UnaliasCommand(keyword);
        unaliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unaliasCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AliasCommandParserTest.java
``` java
public class AliasCommandParserTest {

    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_keywordAndRepresentation_success() {
        AliasToken expectedToken = new AliasTokenBuilder().withKeyword(VALID_ALIAS_KEYWORD_MONDAY).withRepresentation(
                VALID_ALIAS_REPRESENTATION_MONDAY).build();

        assertParseSuccess(parser, AliasCommand.COMMAND_WORD + ALIAS_KEYWORD_DESC_MONDAY
                + ALIAS_REPRESENTATION_DESC_MONDAY, new AliasCommand(expectedToken));
    }

    @Test
    public void parse_representationMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE);

        assertParseFailure(parser, AliasCommand.COMMAND_WORD + ALIAS_KEYWORD_DESC_MONDAY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid keyword
        assertParseFailure(parser, AliasCommand.COMMAND_WORD + INVALID_ALIAS_KEYWORD_DESC
                + ALIAS_REPRESENTATION_DESC_MONDAY, Keyword.MESSAGE_NAME_CONSTRAINTS);

        // invalid representation
        assertParseFailure(parser, AliasCommand.COMMAND_WORD + ALIAS_KEYWORD_DESC_MONDAY
                + INVALID_ALIAS_REPRESENTATION_DESC, Representation.MESSAGE_NAME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parseIndexSpecifiedFailure() throws Exception {
        final Remark remark = new Remark("Some remark.");

        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseNoFieldSpecifiedFailure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\UnaliasCommandParserTest.java
``` java
public class UnaliasCommandParserTest {

    private UnaliasCommandParser parser = new UnaliasCommandParser();

    @Test
    public void parse_keywordValid_success() throws IllegalValueException {

        Keyword expectedKeyword = new Keyword("mon");

        assertParseSuccess(parser, UnaliasCommand.COMMAND_WORD + ALIAS_KEYWORD_DESC_MONDAY,
                new UnaliasCommand(expectedKeyword));
    }

    @Test
    public void parse_keywordInvalid_failure() throws IllegalValueException {
        assertParseFailure(parser, UnaliasCommand.COMMAND_WORD + " gg",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\alias\KeywordTest.java
``` java
public class KeywordTest {

    @Test
    public void isValidKeyword() {
        // invalid keyword
        assertFalse(Keyword.isValidKeyword("")); // empty string
        assertFalse(Keyword.isValidKeyword(" ")); // spaces only
        assertFalse(Keyword.isValidKeyword("t")); // one letter keyword
        assertFalse(Keyword.isValidKeyword("ph tmr")); //two words

        // valid keyword
        assertTrue(Keyword.isValidKeyword("ph")); // 2 letter word
        assertTrue(Keyword.isValidKeyword("ttsh")); // one word
        assertTrue(Keyword.isValidKeyword("88")); // length 2 string of numbers
    }
}
```
###### \java\seedu\address\model\alias\RepresentationTest.java
``` java
public class RepresentationTest {

    @Test
    public void isValidRepresentation() {
        // invalid representation
        assertFalse(Representation.isValidRepresentation("")); // representation cannot be empty

        // valid representation
        assertTrue(Representation.isValidRepresentation("anything")); // any String that is not empty

    }
}
```
###### \java\seedu\address\model\task\HeaderTest.java
``` java
public class HeaderTest {
    @Test
    public void isValidHeader() {
        // invalid header
        assertFalse(Header.isValidHeader("")); // empty string
        assertFalse(Header.isValidHeader(" ")); // spaces only
        assertFalse(Header.isValidHeader("^")); // only non-alphanumeric characters
        assertFalse(Header.isValidHeader("lunch*")); // contains non-alphanumeric characters

        // valid header
        assertTrue(Header.isValidHeader("project meeting")); // alphabets only
        assertTrue(Header.isValidHeader("12345")); // numbers only
        assertTrue(Header.isValidHeader("travel on the 2nd")); // alphanumeric characters
        assertTrue(Header.isValidHeader("NUS conference")); // with capital letters
        assertTrue(Header.isValidHeader("Meet with David Roger Jackson Ray Jr 2nd")); // long header
    }
}
```
###### \java\seedu\address\model\task\TaskHasKeywordsPredicateTest.java
``` java
public class TaskHasKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TaskHasKeywordsPredicate firstPredicate = new TaskHasKeywordsPredicate(firstPredicateKeywordList);
        TaskHasKeywordsPredicate secondPredicate = new TaskHasKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TaskHasKeywordsPredicate firstPredicateCopy = new TaskHasKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_taskContainsKeywords_returnsTrue() {
        // One keyword
        TaskHasKeywordsPredicate predicate = new TaskHasKeywordsPredicate(Collections.singletonList("Fishing"));
        assertTrue(predicate.test(buildTask("Fishing Lake")));

        // Multiple keywords
        predicate = new TaskHasKeywordsPredicate(Arrays.asList("Fishing", "Lake"));
        assertTrue(predicate.test(buildTask("Fishing Lake")));

        // Only one matching keyword
        predicate = new TaskHasKeywordsPredicate(Arrays.asList("Skating", "Park"));
        assertTrue(predicate.test(buildTask("Ring Skating")));

        // Mixed-case keywords
        predicate = new TaskHasKeywordsPredicate(Arrays.asList("nUS", "PrOjeCT"));
        assertTrue(predicate.test(buildTask("NUS Project")));
    }

    @Test
    public void test_headerDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TaskHasKeywordsPredicate predicate = new TaskHasKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(buildTask("Sleep")));

        // Non-matching keyword
        predicate = new TaskHasKeywordsPredicate(Arrays.asList("Shopping"));
        assertFalse(predicate.test(buildTask("Cycling")));

    }

    /**
     * Returns Task object with only header
     */
    private static ReadOnlyTask buildTask(String header) {
        try {
            return new TaskBuilder().withHeader(header).withCompletionStatus().build();
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
            return null;
        }
    }
}
```
###### \java\seedu\address\model\UniqueAliasTokenListTest.java
``` java
public class UniqueAliasTokenListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAliasTokenList uniqueAliasTokenList = new UniqueAliasTokenList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAliasTokenList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\model\UniquePersonListTest.java
``` java
public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\model\UniqueTagListTest.java
``` java
public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\model\UniqueTaskListTest.java
``` java
public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\AliasTokenBuilder.java
``` java
/**
 * A utility class to help with building AliasToken objects.
 */

public class AliasTokenBuilder {

    public static final String DEFAULT_KEYWORD = "ph";
    public static final String DEFAULT_REPRESENTATION = "Public Holiday";

    private AliasToken aliasToken;

    public AliasTokenBuilder() {
        try {
            Keyword defaultKeyword = new Keyword(DEFAULT_KEYWORD);
            Representation defaultRepresentation = new Representation((DEFAULT_REPRESENTATION));
            this.aliasToken = new AliasToken(defaultKeyword, defaultRepresentation);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default alias' values are invalid.");
        }
    }

    /**
     * Initializes the AliasTokenBuilder with the data of {@code aliasToCopy}.
     */
    public AliasTokenBuilder(ReadOnlyAliasToken aliasToCopy) {
        this.aliasToken = new AliasToken(aliasToCopy);
    }

    /**
     * Sets the {@code Keyword} of the {@code AliasToken} that we are building.
     */
    public AliasTokenBuilder withKeyword(String keyword) {
        try {
            this.aliasToken.setKeyword(new Keyword(keyword));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Keyword is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Representation} of the {@code AliasToken} that we are building.
     */
    public AliasTokenBuilder withRepresentation(String representation) {
        try {
            this.aliasToken.setRepresentation(new Representation(representation));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Representation is expected to be unique.");
        }
        return this;
    }

    public AliasToken build() {
        return this.aliasToken;
    }

}

```
###### \java\seedu\address\testutil\AliasTokenUtil.java
``` java
/**
 * A utility class for AliasToken.
 */
public class AliasTokenUtil {

    /**
     * Returns an alias command string for adding the {@code AliasToken}.
     */
    public static String getAliasCommand(ReadOnlyAliasToken aliasToken) {
        return AliasCommand.COMMAND_WORD + " " + getAliasDetails(aliasToken);
    }

    /**
     * Returns the part of command string for the given {@code AliasToken}'s details.
     */
    public static String getAliasDetails(ReadOnlyAliasToken aliasToken) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ALIAS_KEYWORD + aliasToken.getKeyword().keyword + " ");
        sb.append(PREFIX_ALIAS_REPRESENTATION + aliasToken.getRepresentation().representation + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TaskBuilder.java
``` java
/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {
    public static final String DEFAULT_HEADER = "Lunch meet";

    public static final LocalDateTime DEFAULT_TIME =
            LocalDateTime.of(2017, 11, 22, 10, 18);
    private Task task;

    public TaskBuilder() {
        try {
            this.task = new Task(new Header(DEFAULT_HEADER));
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default task's values are invalid.");
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Header} of the {@code Task} that we are building.
     */
    public TaskBuilder withHeader(String header) throws IllegalValueException {
        this.task.setHeader(new Header(header));
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    /**
     * Sets the {@code CompletionStatus} of the {@code Task} that we are building.
     */
    public TaskBuilder withCompletionStatus() {
        this.task.setComplete();
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    /**
     * Sets the {@code IncompletionStatus} of the {@code Task} that we are building.
     */
    public TaskBuilder withIncompletionStatus() {
        this.task.setIncomplete();
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartTime(LocalDateTime startTime) {
        this.task.setStartDateTime(Optional.ofNullable(startTime));
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withEndTime(LocalDateTime endTime) {
        this.task.setEndDateTime(Optional.ofNullable(endTime));
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    public Task build() {
        return this.task;
    }

}
```
###### \java\seedu\address\testutil\TaskUtil.java
``` java
/**
 * A utility class for Task.
 */
public class TaskUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd HH:mm");

    /**
     * Returns an add task command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getHeader().header + " ");
        if (task.getStartDateTime().isPresent()) {
            sb.append("from ");
            sb.append(task.getStartDateTime().get().format(FORMATTER));
            sb.append("to ");
            sb.append(task.getEndDateTime().get().format(FORMATTER));
        } else if ((!task.getStartDateTime().isPresent()) && (task.getEndDateTime().isPresent())) {
            sb.append("by ");
            sb.append(task.getEndDateTime().get().format(FORMATTER));
        }
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalAliasTokens.java
``` java
/**
 * A utility class containing a list of {@code AliasTokens} objects to be used in tests.
 */
public class TypicalAliasTokens {

    public static final ReadOnlyAliasToken AKA = new AliasTokenBuilder().withKeyword("aka")
            .withRepresentation("Also Known As").build();

    public static final ReadOnlyAliasToken DIY = new AliasTokenBuilder().withKeyword("diy")
            .withRepresentation("Do It Yourself").build();

    public static final ReadOnlyAliasToken HTH = new AliasTokenBuilder().withKeyword("hth")
            .withRepresentation("Hope It Helps").build();

    public static final ReadOnlyAliasToken TTYL = new AliasTokenBuilder().withKeyword("ttyl")
            .withRepresentation("Talk To You Later").build();

    public static final ReadOnlyAliasToken TGIF = new AliasTokenBuilder().withKeyword("tgif")
            .withRepresentation("Thank God Its Friday").build();

    public static final ReadOnlyAliasToken TQ = new AliasTokenBuilder().withKeyword("tq")
            .withRepresentation("Thank You").build();

    // Manually added - Alias' details found in {@code CommandTestUtil}
    public static final ReadOnlyAliasToken MON = new AliasTokenBuilder().withKeyword(VALID_ALIAS_KEYWORD_MONDAY)
            .withRepresentation(VALID_ALIAS_REPRESENTATION_MONDAY).build();

    private TypicalAliasTokens() {
        // prevents instantiation
    }

    public static List<ReadOnlyAliasToken> getTypicalAliasTokens() {
        return new ArrayList<>(Arrays.asList(AKA, DIY, HTH, TTYL, TGIF, TQ, MON));
    }

    /**
     * Returns an {@code AddressBook} with all the typical aliases.
     */
    public static AddressBook getTypicalAddressBookWithAlias() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyAliasToken aliasToken : getTypicalAliasTokens()) {
            try {
                ab.addAliasToken(aliasToken);
            } catch (DuplicateTokenKeywordException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }
}
```
###### \java\seedu\address\testutil\TypicalTasks.java
``` java
/**
 * A utility class containing a list of {@code Tasks} objects to be used in tests.
 */
public class TypicalTasks {

    public static final LocalDateTime LUNCH_START_TIME = LocalDateTime.now().minusHours(6);
    public static final LocalDateTime LUNCH_END_TIME = LocalDateTime.now().minusHours(5);

    public static final LocalDateTime DINNER_START_TIME = LocalDateTime.now().minusHours(2);
    public static final LocalDateTime DINNER_END_TIME = LocalDateTime.now().minusHours(1);

    public static final LocalDateTime BREAKFAST_START_TIME = LocalDateTime.now().minusHours(9);
    public static final LocalDateTime BREAKFAST_END_TIME = LocalDateTime.now().minusHours(8);

    public static final LocalDateTime TOMORROW = LocalDateTime.now().plusDays(1);

    public static final ReadOnlyTask LUNCH = buildTask("LUNCH", LUNCH_START_TIME, LUNCH_END_TIME);
    public static final ReadOnlyTask DINNER = buildTask("DINNER", DINNER_START_TIME, DINNER_END_TIME);
    public static final ReadOnlyTask BREAKFAST =
            buildTask("BREAKFAST", BREAKFAST_START_TIME, BREAKFAST_END_TIME);
    public static final ReadOnlyTask WAKE_UP = buildTask("WAKE UP", TOMORROW);

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns Task object with completed start and end time using the TaskBuilder
     */
    private static ReadOnlyTask buildTask(String header, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return new TaskBuilder().withHeader(header).withStartTime(startTime)
                    .withEndTime(endTime).withCompletionStatus().build();
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
            return null;
        }
    }

    /**
     * Returns Task object with incomplete deadline only using the TaskBuilder
     */
    private static ReadOnlyTask buildTask(String header, LocalDateTime deadLine) {
        try {
            return new TaskBuilder().withHeader(header).withEndTime(deadLine).withIncompletionStatus().build();
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
            return null;
        }
    }

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyTask task : getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyTask> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(LUNCH, DINNER, BREAKFAST, WAKE_UP));
    }

}
```
###### \java\systemtests\AliasCommandSystemTest.java
``` java
public class AliasCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void alias() throws Exception {
        Model model = getModel();
        /* Case : create alias token with keyword and representation, command with leading spaces and trailing spaces
         * -> AliasToken created
         */
        ReadOnlyAliasToken toAdd = MON;
        String command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_KEYWORD_DESC_MONDAY
                + "  " + ALIAS_REPRESENTATION_DESC_MONDAY + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo creating Mon AliasToken to the list -> Mon deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo creating Mon AliasToken to the list -> Mon created again */
        command = RedoCommand.COMMAND_WORD;
        model.addAliasToken(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /*Case : invalid keyword -> rejected */
        assertCommandFailure("alias a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));

        /*Case : invalid representation -> rejected */
        assertCommandFailure("alias k/lol laugh out loud",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));

        /*Case : registered command word chosen as alias keyword -> rejected*/
        assertCommandFailure("alias k/add s/plus", AliasCommand.MESSAGE_INVALID_KEYWORD);

        /*Case : duplicate alias keyword -> rejected*/
        command = "   " + AliasCommand.COMMAND_WORD + "  " + ALIAS_KEYWORD_DESC_MONDAY
                + "  " + ALIAS_REPRESENTATION_DESC_MONDAY + " ";
        assertCommandFailure(command,
                AliasCommand.MESSAGE_DUPLICATE_ALIAS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyAliasToken)}. Executes {@code command}
     * instead.
     *
     * @see AliasCommandSystemTest #assertCommandSuccess(ReadOnlyAliasToken)
     */
    private void assertCommandSuccess(String command, ReadOnlyAliasToken toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addAliasToken(toAdd);
        } catch (DuplicateTokenKeywordException e) {
            throw new IllegalArgumentException(String.format(toAdd.toString(), "already exists in the model."));
        }
        String expectedResultMessage = String.format(AliasCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyAliasToken)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     *
     * @see AliasCommandSystemTest #assertCommandSuccess(String, ReadOnlyAliasToken)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
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
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

```
