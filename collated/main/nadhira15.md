# nadhira15
###### \java\seedu\address\logic\commands\AddTagCommand.java
``` java
/**
 * Adds tag(s) to a person in the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add tag(s) to a person in the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[TAG] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "neighbours owesMoney";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tag: %2$s";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Tag added!";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists for this person.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the address book.";

    private final Index index;
    private final Set<Tag> addedTag;

    /**
     * @param index of the person in the filtered person list to add tag
     * @param addedTag tag of the person
     */

    public AddTagCommand(Index index, Set<Tag> addedTag) {
        requireNonNull(index);
        requireNonNull(addedTag);

        this.index = index;
        this.addedTag = addedTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, addedTag);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, editedPerson));
    }
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             Set<Tag> addedTag) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Birthday birthday = personToEdit.getBirthday();
        Avatar avatar = personToEdit.getAvatar();

        Set<Tag> initialTags = personToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();

        try {
            updatedTags = getUpdatedTags(initialTags, addedTag);
        } catch (DuplicateTagException e) {
            throw new CommandException(e.getMessage());
        }

        return new Person(name, phone, email, address, birthday, avatar, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index)
                && addedTag.equals(e.addedTag);
    }

    public static HashSet<Tag> getUpdatedTags(Set<Tag> initialTag, Set<Tag> addedTag) throws DuplicateTagException {
        HashSet<Tag> updatedTags = new HashSet<>(initialTag);
        for (Tag toAdd : addedTag) {
            requireNonNull(toAdd);
            if (initialTag.contains(toAdd)) {
                throw new DuplicateTagException();
            }
            updatedTags.add(toAdd);
        }
        return updatedTags;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super(MESSAGE_DUPLICATE_TAG);
        }

        public String getMessage() {
            return super.getMessage();
        }
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTagCommand.java
``` java
/**
 * Deletes a person's tag(s) in the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a person's tag in the address book. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[TAG] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "neighbours owesMoney";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tag: %2$s";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag deleted!";
    public static final String MESSAGE_NOT_EXIST_TAG = "This tag does not exist for this person.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the address book.";

    private final Index index;
    private final Set<Tag> deletedTag;

    /**
     * @param index of the person in the filtered person list to delete tag
     * @param deletedTag tag of the person
     */

    public DeleteTagCommand(Index index, Set<Tag> deletedTag) {
        requireNonNull(index);
        requireNonNull(deletedTag);

        this.index = index;
        this.deletedTag = deletedTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, deletedTag);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, editedPerson));
    }
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             Set<Tag> deletedTag) throws CommandException {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Birthday birthday = personToEdit.getBirthday();
        Avatar ava = personToEdit.getAvatar();

        Set<Tag> initialTags = personToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();

        try {
            updatedTags = getUpdatedTags(initialTags, deletedTag);
        } catch (CommandException e) {
            throw new CommandException(e.getMessage());
        }

        return new Person(name, phone, email, address, birthday, ava, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        // state check
        DeleteTagCommand e = (DeleteTagCommand) other;
        return index.equals(e.index)
                && deletedTag.equals(e.deletedTag);
    }

    public static HashSet<Tag> getUpdatedTags(Set<Tag> initialTag, Set<Tag> deletedTag) throws CommandException {
        HashSet<Tag> updatedTags = new HashSet<>(initialTag);
        for (Tag toDelete : deletedTag) {
            requireNonNull(toDelete);
            if (!initialTag.contains(toDelete)) {
                throw new CommandException(MESSAGE_NOT_EXIST_TAG);
            }
            updatedTags.remove(toDelete);
        }
        return updatedTags;
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            if (birthday.isNotDefault()) {
                this.birthday = birthday;
            }
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case AddTagCommand.COMMAND_WORD:
        case AddTagCommand.COMMAND_ALIAS:
            return new AddTagCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
        case DeleteTagCommand.COMMAND_ALIAS:
            return new DeleteTagCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        try {
            requireNonNull(args);
        } catch (NullPointerException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        String trimmedArgs = args.trim();
        Index index;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split("\\s+");
        List<String> argsList = Arrays.asList(arguments);

        // when there's only 1 arguments (can be index or anything), throw exception
        if (arguments.length == 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(arguments[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        List<String> tagList = argsList.subList(1, argsList.size());
        Set<Tag> tagToAdd;

        try {
            tagToAdd = parseTagToAdd(tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddTagCommand(index, tagToAdd);
    }

    /**
     * Parses the given {@code Tag} in the context of the AddTagCommand
     * and returns a set of tag object for execution.
     * @throws IllegalValueException if the user input does not conform the expected format
     */
    public Set<Tag> parseTagToAdd(List<String> tagToAdd) throws IllegalValueException {
        assert tagToAdd != null;
        return ParserUtil.parseTags(tagToAdd);
    }

}
```
###### \java\seedu\address\logic\parser\DeleteTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        try {
            requireNonNull(args);
        } catch (NullPointerException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        String trimmedArgs = args.trim();
        Index index;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split("\\s+");
        List<String> argsList = Arrays.asList(arguments);

        // when there's only 1 arguments (can be index or anything), throw exception
        if (arguments.length == 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(arguments[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        List<String> tagList = argsList.subList(1, argsList.size());
        Set<Tag> tagToDelete;

        try {
            tagToDelete = parseTagToDelete(tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new DeleteTagCommand(index, tagToDelete);
    }

    /**
     * Parses the given {@code Tag} in the context of the DeleteTagCommand
     * and returns a set of tag object for execution.
     * @throws IllegalValueException if the user input does not conform the expected format
     */
    public Set<Tag> parseTagToDelete(List<String> tagToDelete) throws IllegalValueException {
        assert tagToDelete != null;
        return ParserUtil.parseTags(tagToDelete);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\AddressContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_ADDRESS.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil
                .containsWordPartialIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return defaultSortOrder; //no sorting for address
    }
}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */

public class Birthday {

    public static final String DEFAULT_VALUE = "-";

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday should contain 8 digit number, first 2 digit for day (01-30), second 2 digit for month (01-12),"
                    + " and last 4 digit for year, separated by slash (/)";
    public static final String BIRTHDAY_VALIDATION_REGEX = "[0-3][\\d][/][0-1][\\d][/]\\d{4}";
    public final String value;

    /**
     * Validates given birthday number.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.equals(DEFAULT_VALUE);
    }

    /**
     * Returns true if value is not the default value (No Birthday)
     * @return
     */
    public boolean isNotDefault() {
        return !DEFAULT_VALUE.equals(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\BirthdayContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday} matches any of the keywords given.
 */
public class BirthdayContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public BirthdayContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_BIRTHDAY.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil
                .containsWordPartialIgnoreCase(person.getBirthday().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((BirthdayContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return defaultSortOrder; //no sorting for birthday
    }
}
```
###### \java\seedu\address\model\person\EmailContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_EMAIL.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil
                .containsWordPartialIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return defaultSortOrder; //no sorting for email
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
```
###### \java\seedu\address\model\person\PhoneContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate extends FieldContainsKeywordsPredicate {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.fieldToSearch = PREFIX_PHONE.getPrefix();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream().anyMatch(keyword -> StringUtil
                .containsWordPartialIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public Comparator<ReadOnlyPerson> sortOrderComparator() {
        return defaultSortOrder; //no sorting for phone
    }
}
```
