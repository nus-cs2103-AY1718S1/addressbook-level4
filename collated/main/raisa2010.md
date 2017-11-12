# raisa2010
###### /java/seedu/address/logic/commands/persons/TagCommand.java
``` java
public class TagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags multiple people using the same tag(s) "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDICES (must be positive integers and may be one or more) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1, 2, 3 "
            + PREFIX_TAG + "friend";

    public static final String MESSAGE_TAG_PERSONS_SUCCESS = "New tag added.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index[] indices;
    private final Set<Tag> newTags;

    /**
     * @param indices of the people in the filtered person list to tag
     * @param tagList list of tags to tag the people with
     */
    public TagCommand(Index[] indices, Set<Tag> tagList) {
        requireNonNull(indices);
        requireNonNull(tagList);

        this.indices = indices;
        newTags = tagList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        Index[] validIndices = CommandUtil.filterValidIndices(lastShownList.size(), indices);

        if (validIndices.length == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        for (Index currentIndex : validIndices) {
            ReadOnlyPerson personToEdit = lastShownList.get(currentIndex.getZeroBased());

            try {
                model.updatePersonTags(personToEdit, newTags);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_TAG_PERSONS_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        //state check
        for (int i = 0; i < indices.length; i++) {
            if (!indices[i].equals(((TagCommand) other).indices[i])) {
                return false;
            }
        }
        return newTags.toString().equals(((TagCommand) other).newTags.toString());
    }
}
```
###### /java/seedu/address/logic/commands/tasks/AddTaskCommand.java
``` java
/**
 * Adds a task to the task manager
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: "
            + "DESCRIPTION "
            + PREFIX_STARTDATE + " START DATE "
            + PREFIX_DEADLINE_BY + "/" + PREFIX_DEADLINE_ON + " DEADLINE DATE "
            + PREFIX_TAG + "TAG";

    public static final String MESSAGE_SUCCESS = "Task has been added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code ReadOnlyTask}
     */
    public AddTaskCommand(ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/commands/tasks/TagTaskCommand.java
``` java
public class TagTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags multiple tasks using the same tag(s) "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDICES (must be positive integers and may be one or more) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1, 2, 3 "
            + PREFIX_TAG + "urgent";

    public static final String MESSAGE_TAG_TASKS_SUCCESS = "New tag added.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index[] indices;
    private final Set<Tag> newTags;

    /**
     * @param indices of the tasks in the filtered task list to tag
     * @param tagList list of tags to tag the task with
     */
    public TagTaskCommand(Index[] indices, Set<Tag> tagList) {
        requireNonNull(indices);
        requireNonNull(tagList);

        this.indices = indices;
        newTags = tagList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        Index[] validIndices = CommandUtil.filterValidIndices(lastShownList.size(), indices);

        if (validIndices.length == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        for (Index currentIndex : validIndices) {
            ReadOnlyTask taskToEdit = lastShownList.get(currentIndex.getZeroBased());

            try {
                model.updateTaskTags(taskToEdit, newTags);
            } catch (DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            } catch (TaskNotFoundException pnfe) {
                throw new AssertionError("The target task cannot be missing");
            }
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_TAG_TASKS_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagTaskCommand)) {
            return false;
        }

        //state check
        for (int i = 0; i < indices.length; i++) {
            if (!indices[i].equals(((TagTaskCommand) other).indices[i])) {
                return false;
            }
        }
        return newTags.toString().equals(((TagTaskCommand) other).newTags.toString());
    }
}
```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

```
###### /java/seedu/address/logic/parser/AddTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STARTDATE, PREFIX_DEADLINE_ON, PREFIX_DEADLINE_BY, PREFIX_TAG);

        if (!isDescriptionPresent(argMultimap) | !isSinglePrefixPresent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Description description = ParserUtil.parseDescription(argMultimap.getPreamble()).get();
            StartDate startDate = ParserUtil.parseStartDate(argMultimap.getValue(PREFIX_STARTDATE))
                    .orElse(new StartDate());
            Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE_BY, PREFIX_DEADLINE_ON))
                    .orElse(new Deadline());
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            if (!TaskDates.isStartDateBeforeDeadline(startDate, deadline)) {
                throw new IllegalValueException(TaskDates.MESSAGE_DATE_CONSTRAINTS);
            }

            ReadOnlyTask task = new Task(description, startDate, deadline, tagList);

            return new AddTaskCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the preamble (string before first valid prefix) is not empty in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isDescriptionPresent(ArgumentMultimap argumentMultimap) {
        return !argumentMultimap.getPreamble().isEmpty();
    }

    /**
     * Returns true if a single deadline prefix has been used in an unquoted string in the given
     * {@code ArgumentMultimap}
     */
    private static boolean isSinglePrefixPresent(ArgumentMultimap argumentMultimap) {
        return !(argumentMultimap.getValue(PREFIX_DEADLINE_BY).isPresent()
                && argumentMultimap.getValue(PREFIX_DEADLINE_ON).isPresent());
    }
}
```
###### /java/seedu/address/logic/parser/ArgumentTokenizer.java
``` java
    /**
     * Returns the part of the {@code argsString} that is unquoted to prevent tokenization of prefixes intended to be
     * in the description. If the argsString contains no quotes then the entire string is returned.
     * @param argsString Arguments string of the form: {@code "preamble" <prefix>value <prefix>value ...} or
     *                   Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @return           The part of the {@code argsString} that is unquoted.
     */
    private static String extractUnquotedArgsString(String argsString) {
        if (argsString.indexOf(QUOTE_REGEX) == argsString.lastIndexOf(QUOTE_REGEX)) {
            return argsString;
        }
        String[] unquotedArgsString = argsString.split(QUOTE_REGEX);
        return (unquotedArgsString.length == 2) ? "" : unquotedArgsString[2];
    }

```
###### /java/seedu/address/logic/parser/EditTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STARTDATE, PREFIX_DEADLINE_BY, PREFIX_DEADLINE_ON, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(getIndexForEdit(argMultimap.getPreamble()));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor();
        try {
            parseDescriptionForEdit(argMultimap.getPreamble()).ifPresent(editTaskDescriptor::setDescription);
            parseStartDateForEdit(argMultimap.getAllValues(PREFIX_STARTDATE))
                    .ifPresent(editTaskDescriptor::setStartDate);
            parseDeadlineForEdit(argMultimap.getAllValues(PREFIX_DEADLINE_BY, PREFIX_DEADLINE_ON))
                    .ifPresent(editTaskDescriptor::setDeadline);
            ParserUtil.parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editTaskDescriptor::setTags);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!TaskDates.isStartDateBeforeDeadline(editTaskDescriptor.getStartDate(), editTaskDescriptor.getDeadline())) {
            throw new ParseException(TaskDates.MESSAGE_DATE_CONSTRAINTS);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code List<String> dates} into a {@code Optional<StartDate>} containing the last date in the list,
     * if {@code dates} is non-empty.
     * If {@code dates} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<StartDate>} containing an empty date.
     */
    public Optional<StartDate> parseStartDateForEdit(List<String> dates) throws IllegalValueException {
        assert dates != null;

        if (dates.isEmpty()) {
            return Optional.empty();
        }

        return dates.size() == 1 && dates.contains("")
                ? Optional.of(new StartDate("", SUFFIX_NO_RECUR_INTERVAL))
                : ParserUtil.parseStartDate(Optional.of(dates.get(dates.size() - 1)));
    }

    /**
     * Parses {@code List<String> dates} into a {@code Optional<Deadline>} containing the last date in the list,
     * if {@code dates} is non-empty.
     * If {@code dates} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<Deadline>} containing an empty date.
     */
    public Optional<Deadline> parseDeadlineForEdit(List<String> dates) throws IllegalValueException {
        assert dates != null;

        if (dates.isEmpty()) {
            return Optional.empty();
        }
        return dates.size() == 1 && dates.contains("")
                ? Optional.of(new Deadline("", SUFFIX_NO_RECUR_INTERVAL))
                : ParserUtil.parseDeadline(Optional.of(dates.get(dates.size() - 1)));

    }

    /**
     * Separates the index from the description in the preamble.
     * @param preamble (string before any valid prefix) received from the argument multimap of the tokenized edit
     * task command.
     * @return the index of the task to be edited.
     */
    public String getIndexForEdit(String preamble) {
        String trimmedPreamble = preamble.trim();
        return (trimmedPreamble.indexOf(' ') == -1) ? trimmedPreamble
                : trimmedPreamble.substring(0, trimmedPreamble.indexOf(' '));
    }

    /**
     * Separates the description from the index in the preamble.
     * @param preamble (string before any valid prefix) received from the argument multimap of the tokenized edit
     * task command.
     * @return {@code Optional<Description>} (parsed) of the task to be edited if it is present in the preamble,
     * otherwise an empty Optional is returned.
     */
    public Optional<Description> parseDescriptionForEdit(String preamble)
            throws IllegalValueException {
        int indexLength = getIndexForEdit(preamble).length();
        String description = (indexLength == preamble.length()) ? ""
                : preamble.substring(indexLength, preamble.length());
        return description.isEmpty() ? Optional.empty() : ParserUtil.parseDescription(description);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> description} into an {@code Optional<Description>} if {@code description}
     * is present.
     */
    public static Optional<Description> parseDescription(String description) throws IllegalValueException {
        requireNonNull(description);
        return description.isEmpty() ? Optional.empty()
                : Optional.of(new Description(description.replace("\"", "")));
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<StartDate>} if {@code date} is present.
     */
    public static Optional<StartDate> parseStartDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        if (date.isPresent() && !TaskDates.getDottedFormat(date.get()).isEmpty()) {
            return Optional.of(new StartDate(TaskDates.formatDate(parseDottedDate(date.get())),
                    parseRecurInterval(date.get())));
        }
        return (date.isPresent() && !date.get().isEmpty())
                ? Optional.of(new StartDate(TaskDates.formatDate(parseDate(date.get())),
                parseRecurInterval(date.get()))) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Deadline} if {@code date} is present.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        if (date.isPresent() && !TaskDates.getDottedFormat(date.get()).isEmpty()) {
            return Optional.of(new Deadline(TaskDates.formatDate(parseDottedDate(date.get())),
                    parseRecurInterval(date.get())));
        }
        return (date.isPresent() && !date.get().isEmpty())
                ? Optional.of(new Deadline(TaskDates.formatDate(parseDate(date.get())), parseRecurInterval(date.get())))
                : Optional.empty();
    }

    /**
     * Parses a {@code String naturalLanguageInput} using PrettyTime NLP, into a {@code Date}.
     * @throws IllegalValueException if the date cannot be parsed from the phrase or if the given date is invalid.
     */
    public static Date parseDate(String naturalLanguageInput) throws IllegalValueException {
        List<DateGroup> dateGroup = new PrettyTimeParser().parseSyntax(naturalLanguageInput.trim());
        if (dateGroup.isEmpty() | !TaskDates.isDateValid(naturalLanguageInput)) {
            throw new IllegalValueException(TaskDates.MESSAGE_DATE_CONSTRAINTS);
        }
        List<Date> dates = dateGroup.get(dateGroup.size() - 1).getDates();
        return dates.get(dates.size() - 1);
    }

    /**
     * Parses the {@code String inputDate} into a {@code Date} if the input is given in (M)M.(d)d.(yy)yy format,
     * which cannot be parsed by the PrettyTime NLP.
     */
    public static Date parseDottedDate(String inputDate) throws IllegalValueException {
        try {
            return new SimpleDateFormat(TaskDates.getDottedFormat(inputDate)).parse(inputDate);
        } catch (ParseException p) {
            throw new IllegalValueException(TaskDates.MESSAGE_DATE_CONSTRAINTS);
        }
    }

    /**
     * Parses the {@code String dateString} of a date into a {@code Suffix} specifying its recur interval.
     */
    public static Suffix parseRecurInterval(String dateString) {
        return (dateString.contains(SUFFIX_RECURRING_DATE_WEEKLY.toString()) ? SUFFIX_RECURRING_DATE_WEEKLY
                : (dateString.contains(SUFFIX_RECURRING_DATE_MONTHLY.toString())) ? SUFFIX_RECURRING_DATE_MONTHLY
                : (dateString.contains(SUFFIX_RECURRING_DATE_YEARLY.toString())) ? SUFFIX_RECURRING_DATE_YEARLY
                : SUFFIX_NO_RECUR_INTERVAL);
    }
}
```
###### /java/seedu/address/logic/parser/Suffix.java
``` java
/**
 * A suffix that marks the end of an argument in an arguments string.
 * E.g. weekly in 'addtask do this by monday weekly'.
 */
public class Suffix {
    private final String suffix;

    public Suffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String toString() {
        return getSuffix();
    }

    @Override
    public int hashCode() {
        return suffix == null ? 0 : suffix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Suffix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Suffix otherSuffix = (Suffix) obj;
        return otherSuffix.getSuffix().equals(getSuffix());
    }
}
```
###### /java/seedu/address/logic/parser/TagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index[] parsedIndices;
        Set<Tag> tagList;

        try {
            parsedIndices = ParserUtil.parseIndices(argMultimap.getPreamble().split(","));
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
        return new TagCommand(parsedIndices, tagList);
    }
}
```
###### /java/seedu/address/logic/parser/TagTaskCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagTaskCommandParser implements Parser<TagTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TagTaskCommand
     * and returns a TagTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index[] parsedIndices;
        Set<Tag> tagList;

        try {
            parsedIndices =  ParserUtil.parseIndices(argMultimap.getPreamble().split(","));
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
        return new TagTaskCommand(parsedIndices, tagList);
    }

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void addTask(ReadOnlyTask task) throws DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        addressBook.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }

    @Override
    public void updateTaskTags(ReadOnlyTask task, Set<Tag> newTags)
            throws TaskNotFoundException, DuplicateTaskException {
        requireAllNonNull(newTags);

        addressBook.updateTaskTags(task, newTags);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyTask} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }
```
###### /java/seedu/address/model/task/Deadline.java
``` java
/**
 * Represents the deadline of a task in the task manager.
 * Guarantees: immutable.
 */
public class Deadline extends TaskDates {

    public final String date;
    public final Suffix recurInterval;

    /**
     * Creates an empty deadline with no recur interval if the deadline is not specified.
     */
    public Deadline() {
        this.date = "";
        this.recurInterval = SUFFIX_NO_RECUR_INTERVAL;
    }

    /**
     * Creates a deadline using the {@code String date} and {@code Suffix recurInterval} given.
     */
    public Deadline(String date, Suffix recurInterval) {
        requireNonNull(date);
        this.date = date.trim();
        this.recurInterval = recurInterval;
    }

    /**
     * Returns a boolean specifying whether the given deadline date is empty.
     */
    public boolean isEmpty() {
        return date.isEmpty();
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
```
###### /java/seedu/address/model/task/Description.java
``` java
/**
 * Represents a person's task in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task descriptions should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String taskDescription;

    /**
     * Validates given task description.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Description(String description) throws IllegalValueException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        this.taskDescription = trimmedDescription;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return taskDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && this.taskDescription.equals(((Description) other).taskDescription)); // state check
    }

    @Override
    public int hashCode() {
        return taskDescription.hashCode();
    }
}
```
###### /java/seedu/address/model/task/exceptions/DuplicateTaskException.java
``` java
/**
 * Signals that the operation will result in duplicate Task objects.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
```
###### /java/seedu/address/model/task/exceptions/TaskNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified task.
 */
public class TaskNotFoundException extends Exception {}
```
###### /java/seedu/address/model/task/ReadOnlyTask.java
``` java
/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    ObjectProperty<Description> descriptionProperty();
    Description getDescription();
    ObjectProperty<StartDate> startDateProperty();
    StartDate getStartDate();
    ObjectProperty<Deadline> deadlineProperty();
    Deadline getDeadline();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDescription().equals(this.getDescription()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getDeadline().equals(this.getDeadline()));
    }

    /**
     * Formats the task as text, showing all non-empty task details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription());
        if (!getStartDate().isEmpty()) {
            builder.append(" From: ").append(getStartDate());
        }
        if (!getDeadline().isEmpty()) {
            builder.append(" To: ").append(getDeadline());
        }
        if (!getTags().isEmpty()) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
```
###### /java/seedu/address/model/task/StartDate.java
``` java
/**
 * Represents the Starting Date of a given task in the application.
 * Guarantees: immutable;
 */
public class StartDate extends TaskDates {

    public final String date;
    public final Suffix recurInterval;

    /**
     * Creates an empty start date with no recur interval if the start date is not specified.
     */
    public StartDate() {
        this.date = "";
        this.recurInterval = SUFFIX_NO_RECUR_INTERVAL;
    }

    /**
     * Creates a start date using the {@code String date} and {@code Suffix recurInterval} given.
     */
    public StartDate(String date, Suffix recurInterval) {
        requireNonNull(date);
        this.date = date.trim();
        this.recurInterval = recurInterval;
    }

    /**
     * Returns a boolean specifying whether the given deadline date is empty.
     */
    public boolean isEmpty() {
        return date.isEmpty();
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.date.equals(((StartDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
```
###### /java/seedu/address/model/task/Task.java
``` java
/**
 * Represents a Task in the application.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private ObjectProperty<Description> description;
    private ObjectProperty<StartDate> startDate;
    private ObjectProperty<Deadline> deadline;
    private ObjectProperty<UniqueTagList> taskTags;

    /**
     * Description must be present and not null.
     */
    public Task(Description description, StartDate startDate, Deadline deadline,
                Set<Tag> taskTags) {
        requireAllNonNull(description, startDate, deadline);
        this.description = new SimpleObjectProperty<>(description);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.deadline = new SimpleObjectProperty<>(deadline);
        // protect internal tags from changes in the arg list
        this.taskTags = new SimpleObjectProperty<>(new UniqueTagList(taskTags));
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getStartDate(), source.getDeadline(), source.getTags());
    }

    public void setDescription(Description description) {
        this.description.set(requireNonNull(description));
    }

    @Override
    public ObjectProperty<Description> descriptionProperty() {
        return description;
    }

    @Override
    public Description getDescription() {
        return description.get();
    }

    public void setStartDate(StartDate startDate) {
        this.startDate.set(requireNonNull(startDate));
    }

    @Override
    public ObjectProperty<StartDate> startDateProperty() {
        return startDate;
    }

    @Override
    public StartDate getStartDate() {
        return startDate.get();
    }

    public void setDeadline(Deadline deadline) {
        this.deadline.set(requireNonNull(deadline));
    }

    @Override
    public ObjectProperty<Deadline> deadlineProperty() {
        return deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(taskTags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return taskTags;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        taskTags.set(new UniqueTagList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, startDate, deadline);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
```
###### /java/seedu/address/model/task/TaskDates.java
``` java
/**
 * Represents a date for a task in the task manager.
 */
public abstract class TaskDates {

    public static final String DISPLAY_DATE_FORMAT = "EEE, MMM d, ''yy";
    public static final String[] DOTTED_DATE_FORMATS = new String[]{"MM.dd.yyyy", "MM.d.yyyy", "M.d.yyyy",
        "M.d.yy", "M.dd.yy", "MM.d.yy", "MM.dd.yy", "M.dd.yyyy"};
    public static final String[] VALID_DATE_FORMATS = new String[]{"MM-dd-yyyy", "M-dd-yyyy", "M-d-yyyy",
        "MM-d-yyyy", "MM-dd-yy", "M-dd-yy", "MM-d-yy", "M-d-yy", "MM.dd.yyyy", "MM.d.yyyy", "M.d.yyyy",
        "M.d.yy", "M.dd.yy", "MM.d.yy", "MM.dd.yy", "M.dd.yyyy", "MM/dd/yyyy", "M/dd/yyyy", "M/d/yyyy",
        "MM/d/yyyy", "MM/dd/yy", "M/dd/yy", "MM/d/yy", "M/d/yy"};
    public static final int NUMBER_MONTHS = 12;
    public static final int FEBRUARY = 2;
    public static final int MAX_DAYS_IN_MONTH = 31;
    public static final int MAX_DAYS_IN_FEB = 29;

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date is invalid";

    /**
     * Formats the last date of a given {@code Date} object into a String.
     */
    public static String formatDate(Date date) throws IllegalValueException {
        SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Returns true if the {@code StartDate} is before the {@code Deadline}} or if one of the parameters is empty.
     * Otherwise, an exception is thrown.
     */
    public static boolean isStartDateBeforeDeadline(StartDate startDate, Deadline deadline) {
        if (!startDate.isEmpty() && !deadline.isEmpty()) {
            try {
                Date parsedStartDate = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(startDate.toString());
                Date parsedDeadline = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(deadline.toString());
                return parsedDeadline.after(parsedStartDate);
            } catch (ParseException p) {
                return false;
            }
        }
        return true;
    }

    /**
     * Carries out the same function as {@Link isStartDateBeforeDeadline(StartDate, Deadline)} except the parameters
     * are wrapped in an Optional.
     * Returns true if the {@code StartDate} is before the {@code Deadline}} or if one of the parameters is empty.
     * Otherwise, an exception is thrown.
     */
    public static boolean isStartDateBeforeDeadline(Optional<StartDate> startDate, Optional<Deadline> deadline) {
        if (startDate.isPresent() && !startDate.get().isEmpty() && deadline.isPresent() && !deadline.get().isEmpty()) {
            try {
                Date parsedStartDate = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(startDate.get().toString());
                Date parsedDeadline = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(deadline.get().toString());
                return parsedDeadline.after(parsedStartDate);
            } catch (ParseException p) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates a given {@code String} inputDate given in an MDY format.
     */
    public static boolean isDateValid(String inputDate) {
        String trimmedDate = inputDate.trim();
        for (String format : VALID_DATE_FORMATS) {
            if (doesDateMatchValidFormat(trimmedDate, format)) {
                int firstSeparatorIndex = Math.max(trimmedDate.indexOf('-'), trimmedDate.indexOf('/'));
                firstSeparatorIndex = Math.max(firstSeparatorIndex, trimmedDate.indexOf('.'));
                int secondSeparatorIndex = Math.max(trimmedDate.lastIndexOf('-'),
                        trimmedDate.lastIndexOf('/'));
                secondSeparatorIndex = Math.max(secondSeparatorIndex, trimmedDate.lastIndexOf('.'));
                int month = Integer.parseInt(trimmedDate.substring(0, firstSeparatorIndex));
                int day = Integer.parseInt(trimmedDate.substring(firstSeparatorIndex + 1, secondSeparatorIndex));
                if (month > NUMBER_MONTHS | day > MAX_DAYS_IN_MONTH | (month == FEBRUARY && day > MAX_DAYS_IN_FEB)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines the specific dotted date format used by the {@code String} inputDate.
     */
    public static String getDottedFormat(String inputDate) {
        for (String format : DOTTED_DATE_FORMATS) {
            if (isDateInDottedFormat(inputDate, format) && isDateValid(inputDate)) {
                return format;
            }
        }
        return "";
    }

    /**
     * Checks if a given {@code String inputDate} is in (M)M.(d)d.(yy)yy format.
     */
    public static boolean isDateInDottedFormat(String inputDate, String dateFormat) {
        try {
            new SimpleDateFormat(dateFormat).parse(inputDate);
            return true;
        } catch (ParseException p) {
            return false;
        }
    }

    /**
     * Checks if the {@code String inputDate} matches the given {@code String validDateFormat}
     */
    public static boolean doesDateMatchValidFormat(String inputDate, String validDateFormat) {
        try {
            new SimpleDateFormat(validDateFormat).parse(inputDate);
            return true;
        } catch (ParseException p) {
            return false;
        }
    }

}
```
###### /java/seedu/address/model/task/UniqueTaskList.java
``` java
/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */

public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyTask> mappedList = EasyBind.map(internalList, (task) -> task);

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(ReadOnlyTask toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(new Task(toAdd));
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if the replacement is equivalent to another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void setTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        internalList.set(index, new Task(editedTask));
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyTask> asObservableList() {
        sortTasks(internalList);
        return FXCollections.unmodifiableObservableList(mappedList);
    }

```
###### /java/seedu/address/ui/ResultDisplay.java
``` java
    /**
     * Sets the result display style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

```
