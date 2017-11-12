# rushan-khor
###### /java/seedu/address/logic/parser/BatchCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class BatchCommandParser implements Parser<BatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BatchCommand parse(String args) throws ParseException {
        final Set<String> tagNames = new HashSet<>();
        Scanner tagNameScanner = new Scanner(args);

        while (tagNameScanner.hasNext()) {
            String nextTagName = tagNameScanner.next();
            tagNames.add(nextTagName);
        }

        try {
            Set<Tag> tags = ParserUtil.parseTags(tagNames);
            return new BatchCommand(tags);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/CopyCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CopyCommand object
 */
public class CopyCommandParser implements Parser<CopyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyCommand
     * and returns an CopyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CopyCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/commands/CopyCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {
        String targetEmail = getTargetEmail();
        String commandResultMessage = "";

        boolean emailIsValid = isEmailValid(targetEmail);
        if (emailIsValid) {
            putIntoClipboard(targetEmail);
            commandResultMessage = String.format(MESSAGE_COPY_PERSON_SUCCESS, targetPerson.getName());
        } else {
            commandResultMessage = String.format(MESSAGE_COPY_PERSON_EMPTY, targetPerson.getName());
        }

        return new CommandResult(commandResultMessage);
    }

    /**
     * Gets the target person's email address.
     * @return     the email address of the person at the list {@code targetIndex}
     * @exception  CommandException if the {@code targetIndex}
     *             argument is greater than or equal to the {@code lastShownList} size.
     */
    public String getTargetEmail() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean indexIsOutOfBounds = targetIndex.getZeroBased() >= lastShownList.size();
        if (indexIsOutOfBounds) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        targetPerson = lastShownList.get(targetIndex.getZeroBased());
        return targetPerson.getEmail().toString();
    }

    public boolean isEmailValid(String email) {
        return !"null@null.com".equalsIgnoreCase(email) && !"".equals(email);
    }

    /**
     * Puts target person's email address into the system clipboard.
     */
    private void putIntoClipboard(String resultantEmailAddress) {
        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        ClipboardContent systemClipboardContent = new ClipboardContent();

        systemClipboardContent.putString(resultantEmailAddress);
        systemClipboard.setContent(systemClipboardContent);
    }
```
###### /java/seedu/address/logic/commands/BatchCommand.java
``` java
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class BatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "batch";
    public static final String COMMAND_ALIAS = "b"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all persons with the given tags.\n"
            + COMMAND_ALIAS + ": Shorthand equivalent for batch delete. \n"
            + "Parameters: TAG (must be a tag that at least one person has)\n"
            + "Example 1: " + COMMAND_ALIAS + " friends \n"
            + "Example 2: " + COMMAND_WORD + " colleagues";

    public static final String MESSAGE_BATCH_DELETE_SUCCESS = "Deleted Persons with Tags: %1$s";
    public static final String MESSAGE_BATCH_DELETE_TAG_NOT_FOUND =
            "One of the tags is not in use. Remove it and try again.";
    private final Set<Tag> tagsToDelete;

    public BatchCommand(Set<Tag> tagsToDelete) {
        this.tagsToDelete = tagsToDelete;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.deletePersonsByTags(tagsToDelete);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_BATCH_DELETE_TAG_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_BATCH_DELETE_SUCCESS, tagsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchCommand // instanceof handles nulls
                && this.tagsToDelete.equals(((BatchCommand) other).tagsToDelete)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/DuplicatesCommand.java
``` java
/**
 * Finds and lists persons in this {@code AddressBook} with possible duplicate entries (by name).
 * Keyword matching is case insensitive.
 */
public class DuplicatesCommand extends Command {

    public static final String COMMAND_WORD = "duplicates";
    public static final String COMMAND_ALIAS = "dups"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are possible duplicates "
            + " and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_ALIAS;

    @Override
    public CommandResult execute() {
        model.updateDuplicatePersonList();
        String commandResultMessage = makeCommandResultMessage();
        return new CommandResult(commandResultMessage);
    }

    /**
     * Makes the command result message for this command.
     * @return String The command result message.
     */
    public String makeCommandResultMessage() {
        int filteredPersonListSize = model.getFilteredPersonList().size();
        return getMessageForPersonListShownSummary(filteredPersonListSize);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DuplicatesCommand); // instanceof handles nulls
    }
}
```
###### /java/seedu/address/model/person/HasPotentialDuplicatesPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Bloodtype} matches any of the keywords given.
 */
public class HasPotentialDuplicatesPredicate implements Predicate<ReadOnlyPerson> {
    private final HashSet<Name> duplicateNames;

    public HasPotentialDuplicatesPredicate(HashSet<Name> duplicateNames) {
        this.duplicateNames = duplicateNames;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return duplicateNames.contains(person.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HasPotentialDuplicatesPredicate // instanceof handles nulls
                && this.duplicateNames.equals(((HasPotentialDuplicatesPredicate) other).duplicateNames)); // state check
    }

}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Deletes all persons in the {@code AddressBook} who have a particular {@code tag}.
     *
     * @param tag all persons containing this tag will be deleted
     */
    public void deletePersonsWithTag(Tag tag) throws PersonNotFoundException {
        ArrayList<Person> personsToRemove = new ArrayList<>();
        for (Person person : persons) {
            if (person.hasTag(tag)) {
                personsToRemove.add(person);
            }
        }

        if (personsToRemove.isEmpty()) {
            throw new PersonNotFoundException();
        }
        for (Person person : personsToRemove) {
            removePerson(person);
            removeUnusedTags(person.getTags());
        }
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes {@code tagsToRemove} from this {@code AddressBook} if and only if they are not help by any persons.
     */
    public void removeUnusedTags(Set<Tag> tagsToRemove) {
        Set<Tag> cleanedTagList = getTagsExcluding(tagsToRemove);
        tags.setTags(cleanedTagList);
        syncMasterTagListWith(persons);
    }

    /**
     * Returns tag list from this {@code AddressBook} excluding {@code excludedTags}.
     */
    public Set<Tag> getTagsExcluding(Set<Tag> excludedTags) {
        Set<Tag> results = tags.toSet();
        for (Tag excludedTag : excludedTags) {
            results.remove(excludedTag);
        }
        return results;
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Deletes all persons in the {@code AddressBook} who have a particular {@code tag}.
     *
     * @param tag all persons containing this tag will be deleted
     */
    public void deletePersonsWithTag(Tag tag) throws PersonNotFoundException {
        addressBook.deletePersonsWithTag(tag);
        indicateAddressBookChanged();
    }

    /**
     * Deletes all persons in the {@code AddressBook} who have a particular {@code tag}.
     *
     * @param tags all persons containing this tag will be deleted
     */
    @Override
    public void deletePersonsByTags(Set<Tag> tags) throws PersonNotFoundException {
        for (Tag tag : tags) {
            deletePersonsWithTag(tag);
        }
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /**
     * Gets a list of duplicate names
     */
    private HashSet<Name> getDuplicateNames() {
        HashSet<Name> examinedNames = new HashSet<>();
        HashSet<Name> duplicateNames = new HashSet<>();

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> allPersonsInAddressBook = getFilteredPersonList();

        for (ReadOnlyPerson person : allPersonsInAddressBook) {
            Name currentName = person.getName();

            if (examinedNames.contains(currentName)) {
                duplicateNames.add(currentName);
            } else {
                examinedNames.add(currentName);
            }
        }
        return duplicateNames;
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateDuplicatePersonList() {
        HashSet<Name> duplicateNames = getDuplicateNames();
        HasPotentialDuplicatesPredicate predicate = new HasPotentialDuplicatesPredicate(duplicateNames);
        updateFilteredPersonList(predicate);
    }
```
