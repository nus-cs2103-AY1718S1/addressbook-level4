# pjunwei95
###### \java\seedu\address\commons\events\model\BackUpEvent.java
``` java
/**
 * Indicates a request to backup Weaver
 */
public class BackUpEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public BackUpEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code tagSet} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param tagSet cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsTagIgnoreCase(Set<Tag> tagSet, String word) {
        requireNonNull(tagSet);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        ArrayList<String> wordsInPreppedSet = new ArrayList<>();

        for (Tag tag: tagSet) {
            wordsInPreppedSet.add(tag.tagName);
        }

        for (String wordInTag: wordsInPreppedSet) {
            if (wordInTag.equalsIgnoreCase(preppedWord)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
```
###### \java\seedu\address\logic\commands\BackUpCommand.java
``` java
/**
 * Backup the Address Book
 */
public class BackUpCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "backup";
    public static final String MESSAGE_SUCCESS = "A backup of Weaver has been created!";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        EventsCenter.getInstance().post(new BackUpEvent(model.getAddressBook()));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BackUpCommand)) {
            return false;
        }

        // state check
        BackUpCommand e = (BackUpCommand) other;
        return model.getAddressBook().equals(e.model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\ClearCommand.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cls";
    public static final String MESSAGE_SUCCESS = "Weaver has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBook());
        model.clearBrowserPanel();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ClearPopupCommand.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.ui.ClearConfirmation;

/**
 * Pop-ups a clear confirmation window before clearing.
 * Confirming clears Weaver, otherwise cancels the clearing.
 */
public class ClearPopupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CLEAR_SUCCESS = "Weaver has been cleared!";
    public static final String MESSAGE_NOT_CLEAR_SUCCESS = "Weaver has not been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        ClearConfirmation clearConfirmation = new ClearConfirmation();
        if (clearConfirmation.isClearCommand()) {
            requireNonNull(model);
            model.resetData(new AddressBook());
            model.clearBrowserPanel();
            return new CommandResult(MESSAGE_CLEAR_SUCCESS);

        } else {
            return new CommandResult(MESSAGE_NOT_CLEAR_SUCCESS);
        }
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTagCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes tags from a person identified using it's last displayed index from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_TAG + "TAG (must match tag)\n"
            + "Example: " + COMMAND_WORD + " 1 t/friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NOT_DELETED = "At least one field to delete must be provided.";
    public static final String MESSAGE_NOT_EXISTING_TAGS = "The tag provided is invalid. Please check again.";

    private final Index index;
    private final DeleteTagDescriptor deleteTagDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param deleteTagDescriptor details of which tags to delete
     */
    public DeleteTagCommand(Index index, DeleteTagDescriptor deleteTagDescriptor) {
        requireNonNull(index);
        requireNonNull(deleteTagDescriptor);

        this.index = index;
        this.deleteTagDescriptor = new DeleteTagDescriptor(deleteTagDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        try {
            checkTagExist(personToEdit, deleteTagDescriptor);
        } catch (CommandException net) {
            throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS));
        }

        Person editedPerson = createTagDeletedPerson(personToEdit, new DeleteTagDescriptor(deleteTagDescriptor));

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS,
                deleteTagDescriptor.getTags().orElse(editedPerson.getTags())));
    }
    /**
     * Check whether a given tag exists in address book.
     * @param t tag that is to be checked
     */
    private boolean isExistingTagName(Tag t) {
        for (Tag tag : model.getAddressBook().getTagList()) {
            if (tag.tagName.equals(t.tagName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check whether a given tag exists in current database(including the person).
     * Throws {@code CommandException} if tag does not exist
     * @param personToEdit the person that is to be checked
     * @param deleteTagDescriptor details of which tags to delete
     */
    private void checkTagExist(ReadOnlyPerson personToEdit,
                               DeleteTagDescriptor deleteTagDescriptor) throws CommandException {
        Set<Tag> nonExistingTagList = new HashSet<>();
        Set<Tag> personTags = new HashSet<>(personToEdit.getTags());
        Set<Tag> tagsToDelete = deleteTagDescriptor.getTags().orElse(personToEdit.getTags());

        // Check whether tags are not existing tags in address book
        for (Tag tag: tagsToDelete) {
            if (!isExistingTagName(tag)) {
                nonExistingTagList.add(tag);
            }
        }
        //Check whether tags are not existing tags in personTags
        for (Tag tag: tagsToDelete) {
            if (!personTags.contains(tag)) {
                nonExistingTagList.add(tag);
            }
        }

        if (nonExistingTagList.size() != 0) {
            throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS));
        }
    }


    /**
     * Creates and returns a {@code Person} with the tags of {@code personToEdit}
     * deleted with {@code DeleteTagDescriptor}.
     */
    public Person createTagDeletedPerson(ReadOnlyPerson personToEdit,
                                             DeleteTagDescriptor deleteTagDescriptor) {
        assert personToEdit != null;

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        Set<Tag> tagsToDelete = deleteTagDescriptor.getTags().orElse(personToEdit.getTags());
        updatedTags.removeAll(tagsToDelete);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getDateOfBirth(), personToEdit.getRemark(),
                personToEdit.getImage(), personToEdit.getUsername(), updatedTags);
    }

    /**
     * Stores the details of which tags to be deleted.
     */
    public static class DeleteTagDescriptor {
        private Set<Tag> tags;

        public DeleteTagDescriptor() {
        }

        public DeleteTagDescriptor(DeleteTagDescriptor toCopy) {
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if tag is edited.
         */
        public boolean isTagDeleted() {
            return CollectionUtil.isAnyNonNull(this.tags);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteTagDescriptor)) {
                return false;
            }

            // state check
            DeleteTagDescriptor e = (DeleteTagDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
```
###### \java\seedu\address\logic\commands\FindTagCommand.java
``` java
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag(s) contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tag(s) contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends colleagues";

    private final TagContainsKeywordsPredicate predicate;

    public FindTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FindTagCommand.COMMAND_WORD:
            return new FindTagCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case ClearPopupCommand.COMMAND_WORD: {
            return new ClearPopupCommand();
        }

        case BackUpCommand.COMMAND_WORD:
            return new BackUpCommand();

```
###### \java\seedu\address\logic\parser\DeleteTagCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.DeleteTagCommand.DeleteTagDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + args, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        DeleteTagDescriptor deleteTagDescriptor = new DeleteTagDescriptor();


        try {
            parseTagsForDelete(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(deleteTagDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!deleteTagDescriptor.isTagDeleted()) {
            throw new ParseException(DeleteTagCommand.MESSAGE_NOT_DELETED);
        }

        return new DeleteTagCommand(index, deleteTagDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForDelete(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
```
###### \java\seedu\address\logic\parser\FindTagCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
```
###### \java\seedu\address\model\tag\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag}(s) matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsTagIgnoreCase(person.getTags(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        logger.fine("Attempting to create backup for: " + addressBook);
        addressBookStorage.backupAddressBook(addressBook);
    }

    @Subscribe
    public void handleBackUpEvent(BackUpEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Backup requested, creating backup file"));
        try {
            backupAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param addressBook data. Cannot be null
     */
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        String trimmedFilePath = filePath.substring(0, filePath.length() - 4);
        saveAddressBook(addressBook, trimmedFilePath + "-backup.xml");
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    private static final String[] suggestedWords = {"add", "delete", "edit", "find",
                                                    "select", "search", "deletetag", "findtag",
                                                    "photo", "facebook", "color",
                                                    "fs", "remark", "map", "theme"};
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    @FXML
    /**
     * Sets the command box style allow autocompletion.
     * @param suggestedWords - list of words that will autocomplete
     */
    private void initialize() {
        TextFields.bindAutoCompletion(commandTextField, suggestedWords);
    }

}
```
