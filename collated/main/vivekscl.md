# vivekscl
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String MESSAGE_NO_PERSON_FOUND = " Did you mean %1$s" + "?";

}
```
###### \java\seedu\address\logic\commands\AddTagCommand.java
``` java
/**
 * Adds a tag to the identified persons using the last displayed indexes from the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORDVAR_1 = "addtag";
    public static final String COMMAND_WORDVAR_2 = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Adds the given tag to the persons identified by the list of index numbers used in the last person "
            + "listing."
            + " Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] [MORE INDEXES] (every index must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " 1 2 3 t/friends \n"
            + "Example 2: " + COMMAND_WORDVAR_2.toUpperCase() + " 2 5 t/classmate \n";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in all of the given persons.";

    private final ArrayList<Index> targetIndexes;
    private final Tag toAdd;

    /**
     * @param targetIndexes of the persons in the filtered person list to edit
     * @param toAdd tag to add to given target indexes
     */
    public AddTagCommand(ArrayList<Index> targetIndexes, Tag toAdd) {

        requireNonNull(targetIndexes);
        requireNonNull(toAdd);

        this.targetIndexes = targetIndexes;
        this.toAdd = toAdd;
    }

    /**
     * First checks if all target indexes are not out of bounds and then checks if the tag exists in all of
     * the given target indexes of person. If not, add the tag to each target person that doesn't have the given tag.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean allPersonsContainsTagToAdd = true;

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson readOnlyPerson = lastShownList.get(targetIndex);
            if (!readOnlyPerson.getTags().contains(toAdd)) {
                allPersonsContainsTagToAdd = false;
            }
        }

        if (allPersonsContainsTagToAdd) {
            throw  new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        try {
            model.addTag(this.targetIndexes, this.toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return targetIndexes.equals(e.targetIndexes)
                && toAdd.equals(e.toAdd);
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        int numberOfPersonsShown = model.getFilteredPersonList().size();
        if (numberOfPersonsShown == 0 && !predicate.getKeywords().isEmpty()) {
            model.updateFilteredPersonList(predicate);
            return new CommandResult(String.format(getMessageForPersonListShownSummary(numberOfPersonsShown)
                    + Messages.MESSAGE_NO_PERSON_FOUND, model.getClosestMatchingName(predicate)));
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

```
###### \java\seedu\address\logic\commands\RedoCommand.java
``` java
    private final int numberOfCommands;

    public RedoCommand() {
        this(1);
    }

    public RedoCommand(int numberOfCommands) {
        this.numberOfCommands = numberOfCommands;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);
        for (int i = 1; i <= numberOfCommands; i++) {
            if (!undoRedoStack.canRedo()) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            undoRedoStack.popRedo().redo();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

```
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
/**
 * Removes a tag from identified persons using the last displayed indexes from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORDVAR_1 = "removetag";
    public static final String COMMAND_WORDVAR_2 = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Removes the given tag from identified person by the list of index numbers used in the last person "
            + "listing."
            + " Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] [MORE INDEXES] (every index must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " 1 2 3 t/friends \n"
            + "Example 2: " + COMMAND_WORDVAR_2.toUpperCase() + " 2 5 t/classmate \n";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NO_SUCH_TAG = "This tag does not exist in any of the given persons.";

    private final ArrayList<Index> targetIndexes;
    private final Tag toRemove;

    /**
     * @param targetIndexes of the persons in the filtered person list to edit
     * @param toRemove tag to remove from given target indexes
     */
    public RemoveTagCommand(ArrayList<Index> targetIndexes, Tag toRemove) {

        requireNonNull(targetIndexes);
        requireNonNull(toRemove);

        this.targetIndexes = targetIndexes;
        this.toRemove = toRemove;
    }

    /**
     * First checks if all target indexes are not out of bounds and then checks if the tag exists among
     * the given target indexes of person and then removes tag from each target person that has the given tag.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean noPersonContainsTagToRemove = true;

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson readOnlyPerson = lastShownList.get(targetIndex);
            if (readOnlyPerson.getTags().contains(toRemove)) {
                noPersonContainsTagToRemove = false;
            }
        }

        if (noPersonContainsTagToRemove) {
            throw  new CommandException(MESSAGE_NO_SUCH_TAG);
        }

        try {
            model.removeTag(this.targetIndexes, this.toRemove);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, toRemove));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        return targetIndexes.equals(e.targetIndexes)
                && toRemove.equals(e.toRemove);
    }
}
```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
    private final int numberOfCommands;

    public UndoCommand() {
        this(1);
    }

    public UndoCommand(int numberOfCommands) {
        this.numberOfCommands = numberOfCommands;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);
        for (int i = 1; i <= numberOfCommands; i++) {
            if (!undoRedoStack.canUndo()) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            undoRedoStack.popUndo().undo();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

```
###### \java\seedu\address\logic\parser\AddTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns a AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        try {
            String indexes = argMultimap.getPreamble();
            ArrayList<Index> indexList = convertToArrayList(indexes);

            String tagName = argMultimap.getValue(PREFIX_TAG).orElse("");
            Tag toAdd = new Tag(tagName);

            return new AddTagCommand(indexList, toAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns an ArrayList of the indexes in the given {@code String}.
     *
     */
    private static ArrayList<Index> convertToArrayList(String indexes) throws IllegalValueException {
        ArrayList<Index> indexList = new ArrayList<Index>();
        String[] arrayOfIndexes = indexes.split(" ");
        for (String s: arrayOfIndexes) {
            indexList.add(ParserUtil.parseIndex(s));
        }
        return indexList;
    }
}
```
###### \java\seedu\address\logic\parser\RedoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RedoCommand object
 */

public class RedoCommandParser implements Parser<RedoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the seedu.address.logic.commands.RedoCommand
     * and returns an seedu.address.logic.commands.RedoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        try {
            int numberOfCommands = Integer.parseInt(args.trim());
            return new RedoCommand(numberOfCommands);
        } catch (NumberFormatException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns a RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        try {
            String indexes = argMultimap.getPreamble();
            ArrayList<Index> indexList = convertToArrayList(indexes);

            String tagName = argMultimap.getValue(PREFIX_TAG).orElse("");
            Tag toRemove = new Tag(tagName);

            return new RemoveTagCommand(indexList, toRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns an ArrayList of the indexes in the given {@code String}.
     *
     */
    private static ArrayList<Index> convertToArrayList(String indexes) throws IllegalValueException {
        ArrayList<Index> indexList = new ArrayList<Index>();
        String[] arrayOfIndexes = indexes.split(" ");
        for (String s: arrayOfIndexes) {
            indexList.add(ParserUtil.parseIndex(s));
        }
        return indexList;
    }
}
```
###### \java\seedu\address\logic\parser\UndoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the seedu.address.logic.commands.UndoCommand
     * and returns an seedu.address.logic.commands.UndoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        try {
            int numberOfCommands = Integer.parseInt(args.trim());
            return new UndoCommand(numberOfCommands);
        } catch (NumberFormatException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes given tag from every of the given persons */
    void removeTag(ArrayList<Index> targetIndexes, Tag toRemove) throws PersonNotFoundException,
            DuplicatePersonException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds given tag to every of the given persons */
    void addTag(ArrayList<Index> targetIndexes, Tag toAdd) throws PersonNotFoundException,
            DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Uses the JaroWinklerDistance function from the Apache Commons library to find the closest matching name when
     * given keywords that are not found in the FilteredPersonList.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public String getClosestMatchingName(NameContainsKeywordsPredicate predicate);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /* JaroWinklerDistance method uses double values ranging from 0 to 1. Set initial value to match very similar
     * names only as setting the value to any value less than or equal to 0 will match the first name in filteredPersons
     */
    private final double initialToleranceValue = 0.5;

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Removes given tag from the given indexes of the target persons shown in the last person listing.
     */
    @Override
    public synchronized void removeTag(ArrayList<Index> targetIndexes, Tag toRemove) throws PersonNotFoundException,
            DuplicatePersonException {

        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson oldPerson = this.getFilteredPersonList().get(targetIndex);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<Tag>(newPerson.getTags());
            newTags.remove(toRemove);
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
            indicateAddressBookChanged();
        }
    }

    /**
     * Adds given tag to the given indexes of the target persons shown in the last person listing.
     */
    @Override
    public synchronized void addTag(ArrayList<Index> targetIndexes, Tag toAdd) throws PersonNotFoundException,
            DuplicatePersonException {

        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson oldPerson = this.getFilteredPersonList().get(targetIndex);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<Tag>(newPerson.getTags());
            newTags.add(toAdd);
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
            indicateAddressBookChanged();
        }
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public String getClosestMatchingName(NameContainsKeywordsPredicate predicate) {

        requireNonNull(predicate);
        ArrayList<String> allNames = getListOfAllFirstAndLastNames(predicate);
        List<String> keywords = predicate.getKeywords();
        return keywords.size() == 1 ? getClosestMatchingNameForOneKeyword(keywords, allNames, initialToleranceValue)
                : getClosestMatchingNameForMultipleKeywords(keywords, allNames, initialToleranceValue);
    }

    /**
     * This helper method gets a list of all the names, separates them and returns a list of first and last names.
     */
    private ArrayList<String> getListOfAllFirstAndLastNames(NameContainsKeywordsPredicate predicate) {

        updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> fullList = getFilteredPersonList();
        ArrayList<String> allNames = fullList.stream().map(p -> p.getName().toString().split(" "))
                .flatMap(Arrays::stream).collect(Collectors.toCollection(ArrayList::new));
        // switches filteredPersonList back from showing all persons to original according to the given predicate
        updateFilteredPersonList(predicate);
        return allNames;
    }

    /**
     * If there is only one keyword given, this helper method gets the closest matching name from that keyword.
     */
    private String getClosestMatchingNameForOneKeyword(List<String> keywords,
                                                       ArrayList<String> allNames, double maxDistance) {

        JaroWinklerDistance currentJaroWinklerDistance = new JaroWinklerDistance();
        String target = keywords.get(0);
        String result = "";
        for (String s : allNames) {
            if (maxDistance < currentJaroWinklerDistance.apply(target, s)) {
                maxDistance = currentJaroWinklerDistance.apply(target, s);
                result = s;
            }
        }
        return result;
    }

    /**
     * If there are multiple keywords given, this helper method gets the closest matching list of names from the
     * keywords and converts them into a readable string.
     */
    private String getClosestMatchingNameForMultipleKeywords(List<String> keywords,
                                                             ArrayList<String> allNames, double maxDistance) {

        JaroWinklerDistance currentJaroWinklerDistance = new JaroWinklerDistance();
        ArrayList<String> result = new ArrayList<String>();
        for (String target : keywords) {
            for (String s : allNames) {
                if (maxDistance < currentJaroWinklerDistance.apply(target, s)) {
                    maxDistance = currentJaroWinklerDistance.apply(target, s);
                    if (!result.contains(s)) {
                        result.add(s);
                    }
                }
            }
        }
        return String.join(" OR ", result);
    }

```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
    public List<String> getKeywords() {
        return this.keywords;
    }

}
```
###### \resources\view\DarkTheme.css
``` css
#personListView {
    -fx-background-color: #383838;
}

```
