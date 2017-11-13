# risashindo7
###### \main\java\seedu\address\logic\commands\AppointCommand.java
``` java
public class AppointCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "appoint";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the appoint of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing appoint will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPOINT + "[APPOINT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINT + "20/10/2017 14:30";

    public static final String MESSAGE_ADD_APPOINT_SUCCESS = "Added appoint to Person: %1$s";
    public static final String MESSAGE_DELETE_APPOINT_SUCCESS = "Removed appoint from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String INPUT_DATE_INVALID = "Invalid Input: The input date is before the current time";

    private final Index index;
    private final Appoint appoint;
    private boolean isLate;

    /**
     * @param index   of the person in the filtered person list to edit the appoint
     * @param appoint of the person
     */
    public AppointCommand(Index index, Appoint appoint) {
        requireNonNull(index);
        requireNonNull(appoint);

        this.index = index;
        this.appoint = appoint;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!appoint.value.isEmpty()) {
            try {
                Calendar current = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy h:mm");
                String currentDate = formatter.format(current.getTime());
                String appointDate = this.appoint.value;
                Date newDate = formatter.parse(appointDate);
                Calendar appointCal = Calendar.getInstance();
                appointCal.setTime(newDate);
                isLate = current.before(appointCal);
            } catch (ParseException e) {
                throw new CommandException(INPUT_DATE_INVALID); //should not happen
            }
            if (!isLate) {
                throw new CommandException(INPUT_DATE_INVALID);
            }
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getComment(), appoint, personToEdit.getAvatar(),
                personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredListToShowAll();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Produces success/failure messages when adding an appointment
     *
     * @param personToEdit the person making the appointment for
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!appoint.value.isEmpty()) {
            return String.format(MESSAGE_ADD_APPOINT_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_APPOINT_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointCommand)) {
            return false;
        }

        // state check
        AppointCommand e = (AppointCommand) other;
        return index.equals(e.index)
                && appoint.equals(e.appoint);
    }
}
```
###### \main\java\seedu\address\logic\commands\AppointCommand.java
``` java

```
###### \main\java\seedu\address\logic\commands\CommentCommand.java
``` java
/**
 * Changes the comment of an existing person in the address book.
 */
public class CommentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "comment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the comment of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing comment will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_COMMENT + "[COMMENT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COMMENT + "Likes to swim.";

    public static final String MESSAGE_ADD_COMMENT_SUCCESS = "Added comment to Person: %1$s";
    public static final String MESSAGE_DELETE_COMMENT_SUCCESS = "Removed comment from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Comment comment;

    /**
     * @param index   of the person in the filtered person list to edit the comment
     * @param comment of the person
     */
    public CommentCommand(Index index, Comment comment) {
        requireNonNull(index);
        requireNonNull(comment);

        this.index = index;
        this.comment = comment;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), comment, personToEdit.getAppoint(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredListToShowAll();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * @param personToEdit the person adding the comment for
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!comment.value.isEmpty()) {
            return String.format(MESSAGE_ADD_COMMENT_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_COMMENT_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommentCommand)) {
            return false;
        }

        // state check
        CommentCommand e = (CommentCommand) other;
        return index.equals(e.index)
                && comment.equals(e.comment);
    }
}
```
###### \main\java\seedu\address\logic\commands\CommentCommand.java
``` java

```
###### \main\java\seedu\address\logic\parser\AppointCommandParser.java
``` java
/**
 * Parser class for the Appoint feature
 */
public class AppointCommandParser implements Parser<AppointCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AppointCommand
     * and returns an AppointCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPOINT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointCommand.MESSAGE_USAGE));
        }

        String appoint = argMultimap.getValue(PREFIX_APPOINT).orElse("");
        return new AppointCommand(index, new Appoint(appoint));

    }
}
```
###### \main\java\seedu\address\logic\parser\CommentCommandParser.java
``` java
/**
 * Parser class for the Comment feature
 */
public class CommentCommandParser implements Parser<CommentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CommentCommand
     * and returns an CommentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CommentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMMENT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE));
        }

        String comment = argMultimap.getValue(PREFIX_COMMENT).orElse("");

        return new CommentCommand(index, new Comment(comment));
    }
}
```
###### \main\java\seedu\address\model\person\Appoint.java
``` java

/**
 * Represents a Person's appoint in the address book.
 * Guarantees: immutable; is always valid
 */
public class Appoint {

    public static final String MESSAGE_APPOINT_CONSTRAINTS =
            "Person appointments should be recorded as DD/MM/YYYY TT:TT, but can be left blank";

    public final String value;

    public Appoint(String appoint) {
        requireNonNull(appoint);
        this.value = appoint;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appoint // instanceof handles nulls
                && this.value.equals(((Appoint) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \main\java\seedu\address\model\person\Comment.java
``` java
/**
 * Represents a Person's comment in the address book.
 * Guarantees: immutable; is always valid
 */
public class Comment {

    public static final String MESSAGE_COMMENT_CONSTRAINTS =
            "Person comments can take any values, can even be blank";

    public final String value;

    public Comment(String comment) {
        requireNonNull(comment);
        this.value = comment;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Comment // instanceof handles nulls
                && this.value.equals(((Comment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \main\java\seedu\address\model\person\Person.java
``` java
    public void setComment(Comment comment) {
        this.comment.set(requireNonNull(comment));
    }

    @Override
    public ObjectProperty<Comment> commentProperty() {
        return comment;
    }

    @Override
    public Comment getComment() {
        return comment.get();
    }


    public void setAppoint(Appoint appoint) {
        this.appoint.set(requireNonNull(appoint));
    }

    @Override
    public ObjectProperty<Appoint> appointProperty() {
        return appoint;
    }

    @Override
    public Appoint getAppoint() {
        return appoint.get();
    }
```
###### \main\java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label comment;
    @FXML
    private Label appoint;
```
###### \test\java\seedu\address\logic\commands\AppointCommandTest.java
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
###### \test\java\seedu\address\logic\commands\CommentCommandTest.java
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
###### \test\java\seedu\address\logic\parser\AppointCommandParserTest.java
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
###### \test\java\seedu\address\logic\parser\CommentCommandParserTest.java
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
###### \test\java\seedu\address\model\person\AppointTest.java
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
###### \test\java\seedu\address\model\person\CommentTest.java
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
