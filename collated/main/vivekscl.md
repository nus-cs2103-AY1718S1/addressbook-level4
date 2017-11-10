# vivekscl
###### /java/seedu/address/model/Model.java
``` java
    /** Deletes given tag from every of the given persons */
    void removeTag(ArrayList<Index> targetIndexes, Tag toRemove) throws PersonNotFoundException,
            DuplicatePersonException;

```
###### /java/seedu/address/model/Model.java
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
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Uses the JaroWinklerDistance function from the Apache Commons library to find the closest matching name when
     * given keywords that are not found in the FilteredPersonList.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public String getClosestMatchingName(NameContainsKeywordsPredicate predicate);

```
###### /java/seedu/address/model/ModelManager.java
``` java
    /* JaroWinklerDistance method uses double values ranging from 0 to 1. Set initial value to match very similar
     * names only. Setting the value to any value less than or equal to 0 will match the first name in filteredPersons
     */
    private final double initialToleranceValue = 0.5;

```
###### /java/seedu/address/model/ModelManager.java
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
###### /java/seedu/address/model/ModelManager.java
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
     * The default value for the result is the first keyword given. Since no such name can be found for the given
     * input, the keyword itself is returned.
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
        String result = keywords.get(0);
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
        return result.isEmpty() ? keywords.get(0) : String.join(" ", result);
    }

```
###### /java/seedu/address/model/person/NameContainsKeywordsPredicate.java
``` java
    public List<String> getKeywords() {
        return this.keywords;
    }

    @Override
    public String toString() {
        String[] resultArray = new String[keywords.size()];
        return String.join(" ", keywords.toArray(resultArray));
    }
}
```
###### /java/seedu/address/logic/commands/UndoCommand.java
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && this.numberOfCommands == ((UndoCommand) other).numberOfCommands); // state check
    }

```
###### /java/seedu/address/logic/commands/ChangeWindowSizeCommand.java
``` java
/**
 * Changes the window windowSize according to predefined sizes that the user can choose from
 */
public class ChangeWindowSizeCommand extends Command {

    // These are commonly used sizes
    public static final double DEFAULT_HEIGHT = 600;
    public static final double DEFAULT_WIDTH = 740;
    public static final double SMALL_HEIGHT = 600;
    public static final double SMALL_WIDTH = 800;
    public static final double MEDIUM_HEIGHT = 720;
    public static final double MEDIUM_WIDTH = 1024;
    public static final double BIG_HEIGHT = 1024;
    public static final double BIG_WIDTH = 1600;

    public static final String SMALL_WINDOW_SIZE_PARAM = "small";
    public static final String MEDIUM_WINDOW_SIZE_PARAM = "med";
    public static final String BIG_WINDOW_SIZE_PARAM = "big";
    public static final String INVALID_WINDOW_SIZE_PARAM = "invalid window size";

    public static final String COMMAND_WORD = "ws";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Modifies window windowSize"
            + "Parameters: WINDOWSIZE (Allowed sizes are small, med, big)\n"
            + "Example 1: " + COMMAND_WORD + " small\n"
            + "Example 2: " + COMMAND_WORD + " big";

    public static final String MESSAGE_SUCCESS = "Window sized has been changed to: ";
    public static final String MESSAGE_WINDOW_SIZE_CONSTRAINTS = "The only allowed sizes are small, med and big";

    private final String windowSize;

    public ChangeWindowSizeCommand(String windowSize) {
        this.windowSize = windowSize;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (isValidWindowSize(windowSize)) {
            double newWindowWidth = getUserDefinedWindowWidth(windowSize);
            double newWindowHeight = getUserDefinedWindowHeight(windowSize);
            EventsCenter.getInstance().post(new ChangeWindowSizeRequestEvent(newWindowWidth, newWindowHeight));
            return new CommandResult(MESSAGE_SUCCESS + newWindowWidth + " x " + newWindowHeight);
        } else {
            throw new CommandException(MESSAGE_WINDOW_SIZE_CONSTRAINTS);
        }

    }

    /*
     * Returns the appropriate width according to the given windowSize.
     */
    private double getUserDefinedWindowWidth(String windowSize) {
        double width = DEFAULT_WIDTH;

        switch (windowSize) {
        case SMALL_WINDOW_SIZE_PARAM:
            width = SMALL_WIDTH;
            break;
        case MEDIUM_WINDOW_SIZE_PARAM:
            width = MEDIUM_WIDTH;
            break;
        case BIG_WINDOW_SIZE_PARAM:
            width = BIG_WIDTH;
            break;
        default:
            assert false : "Window size must be specified";
            break;
        }

        return width;
    }

    /*
     * Returns the appropriate height according to the given windowSize.
     */
    private double getUserDefinedWindowHeight(String windowSize) {
        double height = DEFAULT_HEIGHT;

        switch (windowSize) {
        case SMALL_WINDOW_SIZE_PARAM:
            height = SMALL_HEIGHT;
            break;
        case MEDIUM_WINDOW_SIZE_PARAM:
            height = MEDIUM_HEIGHT;
            break;
        case BIG_WINDOW_SIZE_PARAM:
            height = BIG_HEIGHT;
            break;
        default:
            assert false : "Window size must be specified";
            break;
        }

        return height;
    }

    /*
     * Checks if the given windowSize is allowed.
     */
    private boolean isValidWindowSize(String windowSize) {
        return (windowSize.equalsIgnoreCase(SMALL_WINDOW_SIZE_PARAM)
                || windowSize.equalsIgnoreCase(MEDIUM_WINDOW_SIZE_PARAM)
                || windowSize.equalsIgnoreCase(BIG_WINDOW_SIZE_PARAM));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeWindowSizeCommand // instanceof handles nulls
                && windowSize.equals(((ChangeWindowSizeCommand) other).windowSize));

    }
}
```
###### /java/seedu/address/logic/commands/RedoCommand.java
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && this.numberOfCommands == ((RedoCommand) other).numberOfCommands); // state check
    }

```
###### /java/seedu/address/logic/commands/RemoveTagCommand.java
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
###### /java/seedu/address/logic/commands/FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        int numberOfPersonsShown = model.getFilteredPersonList().size();

        if (numberOfPersonsShown == 0 && !predicate.getKeywords().isEmpty()) {
            String targets = model.getClosestMatchingName(predicate);
            List<String> targetsAsList = Arrays.asList(targets.split("\\s+"));
            if (targetsAsList.equals(predicate.getKeywords())) {
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(Messages.MESSAGE_NO_MATCHING_NAME_FOUND, predicate));
            }
            model.updateFilteredPersonList(new NameContainsKeywordsPredicate(targetsAsList));

            return new CommandResult(String.format(Messages.MESSAGE_NO_PERSON_FOUND, predicate,
                    String.join(", ", targetsAsList)));
        }

        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

```
###### /java/seedu/address/logic/commands/AddTagCommand.java
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
###### /java/seedu/address/logic/parser/RemoveTagCommandParser.java
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
###### /java/seedu/address/logic/parser/UndoCommandParser.java
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
###### /java/seedu/address/logic/parser/AddTagCommandParser.java
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
###### /java/seedu/address/logic/parser/RedoCommandParser.java
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
###### /java/seedu/address/commons/events/ui/ChangeWindowSizeRequestEvent.java
``` java
/**
 * Indicates a request for a change in window size.
 */
public class ChangeWindowSizeRequestEvent extends BaseEvent {

    private double windowWidth;
    private double windowHeight;

    public ChangeWindowSizeRequestEvent(double windowWidth, double windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /*
     * Sets the window size to the user defined size.
     */
    private void setWindowUserDefinedSize(double newWidth, double newHeight) {
        primaryStage.setWidth(newWidth);
        primaryStage.setHeight(newHeight);
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Changes window size to small.
     */
    @FXML
    private void handleSmallWindowSize() {
        raise(new ChangeWindowSizeRequestEvent(ChangeWindowSizeCommand.SMALL_WIDTH,
                ChangeWindowSizeCommand.SMALL_HEIGHT));
    }

    /**
     * Changes window size to medium.
     */
    @FXML
    private void handleMediumWindowSize() {
        raise(new ChangeWindowSizeRequestEvent(ChangeWindowSizeCommand.MEDIUM_WIDTH,
                ChangeWindowSizeCommand.MEDIUM_HEIGHT));
    }

    /**
     * Changes window size to big.
     */
    @FXML
    private void handleBigWindowSize() {
        raise(new ChangeWindowSizeRequestEvent(ChangeWindowSizeCommand.BIG_WIDTH,
                ChangeWindowSizeCommand.BIG_HEIGHT));
    }

    /**
     * Handles a change to the size of the window.
     */
    @Subscribe
    private void handleChangeWindowSizeEvent(ChangeWindowSizeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setWindowUserDefinedSize(event.getWindowWidth(), event.getWindowHeight());
    }

```
###### /resources/view/MainWindow.fxml
``` fxml
      <Menu mnemonicParsing="false" text="Window">
        <items>
          <MenuItem mnemonicParsing="false" onAction="#handleSmallWindowSize" text="Small (800x600)" />
            <MenuItem mnemonicParsing="false" onAction="#handleMediumWindowSize" text="Medium (1024x720)" />
            <MenuItem mnemonicParsing="false" onAction="#handleBigWindowSize" text="Big (1600x1024)" />
        </items>
      </Menu>
  </MenuBar>

  <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
    <padding>
      <Insets bottom="5" left="10" right="10" top="5" />
    </padding>
  </StackPane>

  <StackPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="pane-with-border" VBox.vgrow="NEVER">
    <padding>
      <Insets bottom="5" left="10" right="10" top="5" />
    </padding>
  </StackPane>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4, 0.5" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" minWidth="250" maxWidth="300" prefWidth="250" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
    </VBox>

```
###### /resources/view/DarkTheme.css
``` css
#personListView {
    -fx-background-color: #383838;
}

```
