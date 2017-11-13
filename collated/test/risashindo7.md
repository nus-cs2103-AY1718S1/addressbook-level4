# risashindo7
###### \java\seedu\address\logic\commands\AppointCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for AppointCommand.
 */
public class AppointCommandTest extends GuiUnitTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute() throws Exception {
        final AppointCommand standardCommand = new AppointCommand(INDEX_FIRST_PERSON, new Appoint(VALID_APPOINT_AMY));

        // same values -> returns true
        AppointCommand commandWithSameValues = new AppointCommand(INDEX_FIRST_PERSON, new Appoint(VALID_APPOINT_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AppointCommand(INDEX_SECOND_PERSON, new Appoint(VALID_APPOINT_AMY))));

        // different appoints -> returns false
        assertFalse(standardCommand.equals(new AppointCommand(INDEX_FIRST_PERSON, new Appoint(VALID_APPOINT_BOB))));
    }

    /**
     * Returns an {@code AppointCommand} with parameters {@code index} and {@code appoint}.
     */
    /*
    private AppointCommand prepareCommand(Index index, String appoint) {
        AppointCommand appointCommand = new AppointCommand(index, new Appoint(appoint));
        appointCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return appointCommand;
    }
    */
}
```
###### \java\seedu\address\logic\commands\CommentCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for CommentCommand.
 */
public class CommentCommandTest extends GuiUnitTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addComment_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withComment("Some comment").build();

        CommentCommand commentCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getComment().value);

        String expectedMessage = String.format(CommentCommand.MESSAGE_ADD_COMMENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteComment_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setComment(new Comment(""));

        CommentCommand commentCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getComment().toString());

        String expectedMessage = String.format(CommentCommand.MESSAGE_DELETE_COMMENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withComment("Some comment").build();
        CommentCommand commentCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getComment().value);

        String expectedMessage = String.format(CommentCommand.MESSAGE_ADD_COMMENT_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CommentCommand commentCommand = prepareCommand(outOfBoundIndex, VALID_COMMENT_BOB);

        assertCommandFailure(commentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final CommentCommand standardCommand = new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_AMY));

        // same values -> returns true
        CommentCommand commandWithSameValues = new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CommentCommand(INDEX_SECOND_PERSON, new Comment(VALID_COMMENT_AMY))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_BOB))));
    }

    /**
     * Returns an {@code CommentCommand} with parameters {@code index} and {@code comment}
     */
    private CommentCommand prepareCommand(Index index, String comment) {
        CommentCommand commentCommand = new CommentCommand(index, new Comment(comment));
        commentCommand.setData(model, new CommandHistory(), new UndoRedoStack(), null);
        return commentCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AppointCommandParserTest.java
``` java
public class AppointCommandParserTest extends GuiUnitTest {
    private AppointCommandParser parser = new AppointCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Appoint appoint = new Appoint("Some appointment.");

        // have appoints
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_APPOINT.toString() + " " + appoint;
        AppointCommand expectedCommand = new AppointCommand(INDEX_FIRST_PERSON, appoint);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no appoints
        userInput = targetIndex.getOneBased() + " " + PREFIX_APPOINT.toString();
        expectedCommand = new AppointCommand(INDEX_FIRST_PERSON, new Appoint(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, AppointCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\CommentCommandParserTest.java
``` java
public class CommentCommandParserTest extends GuiUnitTest {
    private CommentCommandParser parser = new CommentCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Comment comment = new Comment("Some comment.");

        // have comments
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_COMMENT.toString() + " " + comment;
        CommentCommand expectedCommand = new CommentCommand(INDEX_FIRST_PERSON, comment);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no comments
        userInput = targetIndex.getOneBased() + " " + PREFIX_COMMENT.toString();
        expectedCommand = new CommentCommand(INDEX_FIRST_PERSON, new Comment(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, CommentCommand.COMMAND_WORD, expectedMessage);
    }
}
```
###### \java\seedu\address\model\person\AppointTest.java
``` java
public class AppointTest {

    @Test
    public void equals() {
        Appoint appoint = new Appoint("20/12/2018 13:30");

        // same object -> returns true
        assertTrue(appoint.equals(appoint));

        // same values -> returns true
        Appoint appointCopy = new Appoint(appoint.value);
        assertTrue(appoint.equals(appointCopy));

        // different types -> returns false
        assertFalse(appoint.equals(1));

        // null -> returns false
        assertFalse(appoint.equals(null));

        // different person -> returns false
        Appoint differentAppoint = new Appoint("20/11/2018 13:30");
        assertFalse(appoint.equals(differentAppoint));
    }
}
```
###### \java\seedu\address\model\person\CommentTest.java
``` java
public class CommentTest {

    @Test
    public void equals() {
        Comment comment = new Comment("Hello");

        // same object -> returns true
        assertTrue(comment.equals(comment));

        // same values -> returns true
        Comment commentCopy = new Comment(comment.value);
        assertTrue(comment.equals(commentCopy));

        // different types -> returns false
        assertFalse(comment.equals(1));

        // null -> returns false
        assertFalse(comment.equals(null));

        // different person -> returns false
        Comment differentComment = new Comment("Bye");
        assertFalse(comment.equals(differentComment));
    }
}
```
