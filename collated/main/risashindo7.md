# risashindo7
###### \java\seedu\address\logic\commands\AppointCommand.java
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
###### \java\seedu\address\logic\commands\AppointCommand.java
``` java

```
###### \java\seedu\address\logic\commands\CommentCommand.java
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
###### \java\seedu\address\logic\commands\CommentCommand.java
``` java

```
###### \java\seedu\address\logic\parser\AppointCommandParser.java
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
###### \java\seedu\address\logic\parser\CommentCommandParser.java
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
###### \java\seedu\address\model\person\Appoint.java
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
###### \java\seedu\address\model\person\Comment.java
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
###### \java\seedu\address\model\person\Person.java
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
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label comment;
    @FXML
    private Label appoint;
```
