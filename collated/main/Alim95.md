# Alim95
###### \java\seedu\address\commons\core\TutorialMessages.java
``` java

/**
 * Contains the tutorial messages.
 */
public class TutorialMessages {

    public static final int TOTAL_NUM_STEPS = 9;

    /* Introductory Messages */
    public static final String INTRO_BEGIN = "Welcome to Bluebird! Would you like to go through the tutorial?";
    public static final String INTRO_TWO = "This is the command box, where you will enter your commands.";
    public static final String INTRO_THREE = "This is the result display, where "
            + "I will display the outcome of your commands.";
    public static final String INTRO_FOUR = "This is the person and task list panel, "
            + "where you will see your list of contacts and tasks";
    public static final String INTRO_FIVE = "This is the sort menu, where you can select how to sort the list.";
    public static final String INTRO_SIX = "This is the search box, where "
            + "you are able to search for the person or tasks you want.";
    public static final String INTRO_SEVEN = "Features of Bluebird:\n"
            + "1. Add contacts/tasks\n"
            + "2. Delete contacts/tasks\n"
            + "3. Edit contacts/tasks\n"
            + "4. Find contacts/tasks\n"
            + "5. Add remark to contacts\n"
            + "6. Select a contact\n"
            + "7. Pin/Unpin a contact\n"
            + "8. Alias/Unalias\n"
            + "9. List all alias\n"
            + "10. Hide a contact\n"
            + "11. Clear all contacts and tasks\n"
            + "12. List all contacts and tasks\n"
            + "13. List all pinned contacts\n"
            + "14. Sort list of contacts\n"
            + "15. Help window\n"
            + "16. History of command inputs\n"
            + "17. Undo a command\n"
            + "18. Redo a command\n"
            + "19. Toggle to person/task mode\n"
            + "20. Toggle to parent/child mode\n"
            + "21. Exit Bluebird";

    public static final String INTRO_END = "Bluebird is set to Child Mode by default every time Bluebird "
            + "is launched. To activate all commands, toggle to parent mode!\n"
            + "The only commands available in Child Mode are:\n"
            + "1. add\n"
            + "2. select\n"
            + "3. find\n"
            + "4. findpin\n"
            + "5. list\n"
            + "6. listpin\n"
            + "7. remark\n"
            + "8. sort\n"
            + "9. history\n"
            + "10. undo\n"
            + "11. redo\n"
            + "12. person\n"
            + "13. task\n"
            + "14. parent\n"
            + "15. exit\n";

    /* Command usage messages */
    public static final String USAGE_BEGIN = "Let's try out the different commands of Bluebird! Activate Parent Mode "
            + "by typing \"parent\" into the command box and pressing enter to enable all commands! "
            + "Click on command box and Press F2 to view the list of commands and enter the commands "
            + "on the command box to execute it. A parameter in [ ] means it is optional.";

    /* Concluding message */
    public static final String CONCLUSION = "That's it for the tutorial! If you still need help, you can "
            + "type \"help\" on the command box or press F1 for the user guide.";

    /* List of all introductory messages */
    public static final ArrayList<String> INTRO_LIST = new ArrayList<String>() {
        {
            add(INTRO_TWO);
            add(INTRO_THREE);
            add(INTRO_FOUR);
            add(INTRO_FIVE);
            add(INTRO_SIX);
            add(INTRO_SEVEN);
            add(INTRO_END);
            add(USAGE_BEGIN);
        }
    };
}
```
###### \java\seedu\address\commons\events\ui\InvalidResultDisplayEvent.java
``` java

/**
 * Indicates that an invalid command is entered.
 */
public class InvalidResultDisplayEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\JumpToNewPersonRequestEvent.java
``` java

/**
 * Indicates a request to jump to new person in person list
 */
public class JumpToNewPersonRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToNewPersonRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\JumpToNewTaskRequestEvent.java
``` java

/**
 * Indicates a request to jump to new task in task list
 */
public class JumpToNewTaskRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToNewTaskRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleListAllStyleEvent.java
``` java

/**
 * Indicates a request to toggle the style of All tab.
 */
public class ToggleListAllStyleEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleListPinStyleEvent.java
``` java

/**
 * An event requesting to toggle the style of Pin tab.
 */
public class ToggleListPinStyleEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleParentChildModeEvent.java
``` java

/**
 * Indicates a request to toggle to ParentMode.
 */
public class ToggleParentChildModeEvent extends BaseEvent {

    public final boolean isSetToParentMode;

    public ToggleParentChildModeEvent(boolean isSetToParentMode) {
        this.isSetToParentMode = isSetToParentMode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleSearchBoxStyle.java
``` java

/**
 * Indicates a request to toggle the view to PersonPanel.
 */
public class ToggleSearchBoxStyle extends BaseEvent {

    private final boolean isPinnedStyle;

    public ToggleSearchBoxStyle(boolean isPinnedStyle) {
        this.isPinnedStyle = isPinnedStyle;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public boolean isPinnedStyle() {
        return isPinnedStyle;
    }
}
```
###### \java\seedu\address\commons\events\ui\ToggleSortByLabelEvent.java
``` java

/**
 * Toggles the sort by label every time list is sorted.
 */
public class ToggleSortByLabelEvent extends BaseEvent {

    public final String sortBy;

    public ToggleSortByLabelEvent(String input) {
        this.sortBy = input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    @Override
    public String toString() {
        return this.sortBy;
    }
}
```
###### \java\seedu\address\commons\events\ui\ToggleToAliasViewEvent.java
``` java

/**
 * Indicates a request to toggle the view to AliasPanel.
 */
public class ToggleToAliasViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleToAllPersonViewEvent.java
``` java

/**
 * Indicates a request to toggle the view to PersonPanel.
 */
public class ToggleToAllPersonViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleToTaskViewEvent.java
``` java

/**
 * Indicates a request to toggle the view to TaskPanel.
 */
public class ToggleToTaskViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\UpdatePinnedPanelEvent.java
``` java

/**
 * Indicates a request to update the pinned panel.
 */
public class UpdatePinnedPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ValidResultDisplayEvent.java
``` java

/**
 * Indicates that a valid command is entered.
 */
public class ValidResultDisplayEvent extends BaseEvent {

    public final String message;

    public ValidResultDisplayEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\ParentModeCommand.java
``` java
/**
 * Enables all commands for the user
 */
public class ParentModeCommand extends Command {

    public static final String COMMAND_WORD = "parent";

    public static final String MESSAGE_SUCCESS = "All commands are enabled!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.parentEnabled));
        EventsCenter.getInstance().post(new ToggleParentChildModeEvent(true));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\person\AddCommand.java
``` java
            EventsCenter.getInstance()
                    .post(new JumpToNewPersonRequestEvent(Index.fromOneBased(model.getFilteredPersonList().size())));
```
###### \java\seedu\address\logic\commands\person\FindPinnedCommand.java
``` java
/**
 * Finds and lists all pinned persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPinnedCommand extends Command {

    public static final String COMMAND_WORD = "findpinned";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all pinned persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final PersonHasKeywordsPredicate predicate;

    public FindPinnedCommand(PersonHasKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new ListSizeEvent(model.getFilteredPersonList().size()));
        EventsCenter.getInstance().post(new ToggleListPinStyleEvent());
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPinnedCommand // instanceof handles nulls
                && this.predicate.equals(((FindPinnedCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\person\ListAliasCommand.java
``` java
/**
 * Lists all aliases in the address book to the user.
 */
public class ListAliasCommand extends Command {

    public static final String COMMAND_WORD = "listalias";

    public static final String MESSAGE_SUCCESS = "Listed all alias";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ToggleToAliasViewEvent());
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.aliasEnabled));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\person\ListPinCommand.java
``` java

/**
 * Lists all pinned persons in the address book to the user.
 */
public class ListPinCommand extends Command {

    public static final String COMMAND_WORD = "listpin";

    public static final String MESSAGE_SUCCESS = "Listed all pinned person";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(new PersonIsPinnedPredicate());
        EventsCenter.getInstance().post(new ToggleListPinStyleEvent());
        EventsCenter.getInstance().post(new ListSizeEvent(model.getFilteredPersonList().size()));
        EventsCenter.getInstance().post(new ToggleSearchBoxStyle(true));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\person\PinCommand.java
``` java

/**
 * Pins a person identified using it's last displayed index from the address book.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the person identified by the index number in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";

    private final Index targetIndex;

    public PinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToPin = lastShownList.get(targetIndex.getZeroBased());

        if (personToPin.isPinned()) {
            throw new CommandException(Messages.MESSAGE_PERSON_ALREADY_PINNED);
        }

        try {
            model.pinPerson(personToPin);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, personToPin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PinCommand // instanceof handles nulls
                && this.targetIndex.equals(((PinCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\person\SortCommand.java
``` java

/**
 * Sorts the address book based on given keyword.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "List has been sorted by %s.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list by name, phone, email or address \n"
            + "Parameters: KEYWORD\n"
            + "Example for name sort: " + COMMAND_WORD + " name\n"
            + "Example for phone sort: " + COMMAND_WORD + " phone";

    private final String toSort;

    public SortCommand(String toSort) {
        this.toSort = toSort;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortList(toSort);
        EventsCenter.getInstance().post(new ListSizeEvent(model.getFilteredPersonList().size()));
        EventsCenter.getInstance().post(new ToggleSortByLabelEvent(toSort));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSort));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.toSort.equals(((SortCommand) other).toSort)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\person\UnpinCommand.java
``` java

/**
 * Unpins a person identified using it's last displayed index from the address book.
 */
public class UnpinCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unpins the person identified by the index number in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPIN_PERSON_SUCCESS = "Unpinned Person: %1$s";

    private final Index targetIndex;

    public UnpinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnpin = lastShownList.get(targetIndex.getZeroBased());

        if (!personToUnpin.isPinned()) {
            throw new CommandException(Messages.MESSAGE_PERSON_ALREADY_UNPINNED);
        }

        try {
            model.unpinPerson(personToUnpin);
            EventsCenter.getInstance().post(new UpdatePinnedPanelEvent());
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_UNPIN_PERSON_SUCCESS, personToUnpin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnpinCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnpinCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\task\AddTaskCommand.java
``` java
            EventsCenter.getInstance()
                    .post(new JumpToNewTaskRequestEvent(Index.fromOneBased(model.getFilteredTaskList().size())));
```
###### \java\seedu\address\logic\parser\person\FindPinnedCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindPinnedCommand object
 */
public class FindPinnedCommandParser implements Parser<FindPinnedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPinnedCommand
     * and returns an FindPinnedCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPinnedCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPinnedCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindPinnedCommand(new PersonHasKeywordsPredicate(Arrays.asList(nameKeywords), true));
    }

    @Override
    public String getCommandWord() {
        return FindPinnedCommand.COMMAND_WORD;
    }
}
```
###### \java\seedu\address\logic\parser\person\PinCommandParser.java
``` java

/**
 * Parses input arguments and creates a new PinCommand object
 */
public class PinCommandParser implements Parser<PinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PinCommand
     * and returns an PinCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public PinCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PinCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public String getCommandWord() {
        return PinCommand.COMMAND_WORD;
    }
}
```
###### \java\seedu\address\logic\parser\person\SortCommandParser.java
``` java

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();
        if (trimmedArgs.isEmpty() || !"name".equals(trimmedArgs) && !"phone".equals(trimmedArgs)
                && !"email".equals(trimmedArgs) && !"address".equals(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(trimmedArgs);
    }

    @Override
    public String getCommandWord() {
        return SortCommand.COMMAND_WORD;
    }
}
```
###### \java\seedu\address\logic\parser\person\UnpinCommandParser.java
``` java

/**
 * Parses input arguments and creates a new UnpinCommand object
 */
public class UnpinCommandParser implements Parser<UnpinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnpinCommand
     * and returns an UnpinCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnpinCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnpinCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public String getCommandWord() {
        return UnpinCommand.COMMAND_WORD;
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts the list.
     */
    public void sortList(String toSort) {
        persons.sort(toSort);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Pins (@code toPin) in this {@code AddressBook}.
     *
     * @throws PersonNotFoundException if the {@code toPin} is not in this {@code AddressBook}.
     */
    public boolean pinPerson(ReadOnlyPerson toPin) throws PersonNotFoundException {
        if (persons.pin(toPin)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Unpins (@code toUnpin) from this {@code AddressBook}.
     *
     * @throws PersonNotFoundException if the {@code toUnpin} is not in this {@code AddressBook}.
     */
    public boolean unpinPerson(ReadOnlyPerson toUnpin) throws PersonNotFoundException {
        if (persons.unpin(toUnpin)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }
```
###### \java\seedu\address\model\Model.java
``` java
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ONLY_PINNED = person -> person.isPinned();
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Pins the given person.
     */
    void pinPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Unpins the given person.
     */
    void unpinPerson(ReadOnlyPerson target) throws PersonNotFoundException;

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Sorts the AddressBook.
     */
    void sortList(String toSort);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortList(String toSort) {
        addressBook.sortList(toSort);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void pinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.pinPerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void unpinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.unpinPerson(target);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<Boolean> pinProperty() {
        return isPinned;
    }

    public boolean isPinned() {
        return isPinned.get();
    }

    public boolean setPinned(boolean isPinned) {
        this.isPinned.set(isPinned);
        return true;
    }
```
###### \java\seedu\address\model\person\PersonHasKeywordsPredicate.java
``` java

/**
 * Tests that a {@code ReadOnlyPerson}'s details matches any of the keywords given.
 */
public class PersonHasKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;
    private final String fullWord;
    private final boolean isFindPinned;

    public PersonHasKeywordsPredicate(List<String> keywords, boolean isFindPinned) {
        this.keywords = keywords;
        this.isFindPinned = isFindPinned;
        StringJoiner joiner = new StringJoiner(" ");
        for (String key : keywords) {
            joiner.add(key);
        }
        this.fullWord = joiner.toString().toLowerCase();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String[] nameParts = person.getName().fullName.split(" ");
        ArrayList<String> tagParts = getTags(person);
        return !person.isPrivate() && isPersonMatch(person, nameParts, tagParts);
    }

    /**
     * Returns true if the person has properties that matches the keywords.
     */
    private boolean isPersonMatch(ReadOnlyPerson person, String[] nameParts, ArrayList<String> tagParts) {
        for (String tag : tagParts) {
            if (keywords.stream().anyMatch(keyword -> tag.startsWith(keyword.toLowerCase()))) {
                return !isFindPinned || person.isPinned();
            }
        }
        for (String name : nameParts) {
            if (keywords.stream().anyMatch(keyword -> name.toLowerCase().startsWith(keyword.toLowerCase()))) {
                return !isFindPinned || person.isPinned();
            }
        }
        if (keywords.size() != 0 && person.getAddress().toString().toLowerCase().contains(fullWord)) {
            return !isFindPinned || person.isPinned();
        }
        if (keywords.stream().anyMatch(keyword -> person.getEmail().toString().toLowerCase()
                .startsWith(keyword.toLowerCase()))) {
            return !isFindPinned || person.isPinned();
        }
        return keywords.stream().anyMatch(keyword -> person.getPhone()
                .toString().startsWith(keyword.toLowerCase())) && (!isFindPinned || person.isPinned());
    }

    private ArrayList<String> getTags(ReadOnlyPerson person) {
        Set<Tag> tagList = person.getTags();
        ArrayList<String> tagParts = new ArrayList<>();
        for (Tag tag : tagList) {
            tagParts.add(tag.toTextString().toLowerCase());
        }
        return tagParts;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonHasKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonHasKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\PersonIsPinnedPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson} is pinned.
 */
public class PersonIsPinnedPredicate implements Predicate<ReadOnlyPerson> {

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.isPinned() && !person.isPrivate();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonIsPinnedPredicate); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Boolean> pinProperty();
    boolean isPinned();

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java

    /**
     * Sorts the list in order.
     */
    public void sort(String toSort) {
        switch (toSort) {
        case "name":
            internalList.sort((p1, p2) -> p1.getName().toString()
                    .compareToIgnoreCase(p2.getName().toString()));
            break;
        case "phone":
            internalList.sort((p1, p2) -> p1.getPhone().toString()
                    .compareToIgnoreCase(p2.getPhone().toString()));
            break;
        case "email":
            internalList.sort((p1, p2) -> p1.getEmail().toString()
                    .compareToIgnoreCase(p2.getEmail().toString()));
            break;
        case "address":
            internalList.sort((p1, p2) -> p1.getAddress().toString()
                    .compareToIgnoreCase(p2.getAddress().toString()));
            break;
        default:
            break;
        }
    }

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java

    /**
     * Pins the equivalent person in the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean pin(ReadOnlyPerson toPin) throws PersonNotFoundException {
        requireNonNull(toPin);
        final int indexToPin = internalList.indexOf(toPin);
        final boolean personFoundAndPinned = internalList.get(indexToPin).setPinned(true);
        if (!personFoundAndPinned) {
            throw new PersonNotFoundException();
        }
        return personFoundAndPinned;
    }

    /**
     * Unpins the equivalent person in the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean unpin(ReadOnlyPerson toUnpin) throws PersonNotFoundException {
        requireNonNull(toUnpin);
        final int indexToPin = internalList.indexOf(toUnpin);
        final boolean personFoundAndUnpinned = internalList.get(indexToPin).setPinned(false);
        if (!personFoundAndUnpinned) {
            throw new PersonNotFoundException();
        }
        return personFoundAndUnpinned;
    }

```
###### \java\seedu\address\ui\AliasCard.java
``` java
/**
 * An UI component that displays information of a {@code Alias}.
 */
public class AliasCard extends UiPart<Region> {

    private static final String FXML = "AliasListCard.fxml";

    public final ReadOnlyAliasToken alias;

    @FXML
    private HBox cardPane;
    @FXML
    private Label keyword;
    @FXML
    private Label id;
    @FXML
    private Label representation;

    public AliasCard(ReadOnlyAliasToken alias, int displayedIndex) {
        super(FXML);
        this.alias = alias;
        id.setText(displayedIndex + ". ");
        bindListeners(alias);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Alias} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyAliasToken alias) {
        keyword.textProperty().bind(Bindings.convert(alias.keywordProperty()));
        representation.textProperty().bind(Bindings.convert(alias.representationProperty()));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AliasCard)) {
            return false;
        }

        // state check
        AliasCard card = (AliasCard) other;
        return id.getText().equals(card.id.getText())
                && alias.equals(card.alias);
    }
}
```
###### \java\seedu\address\ui\AliasListPanel.java
``` java
/**
 * Panel containing the list of Alias.
 */
public class AliasListPanel extends UiPart<Region> {
    private static final String FXML = "AliasListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AliasListPanel.class);

    @FXML
    private ListView<AliasCard> aliasListView;

    public AliasListPanel(ObservableList<ReadOnlyAliasToken> aliasList) {
        super(FXML);
        setConnections(aliasList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyAliasToken> aliasList) {
        ObservableList<AliasCard> mappedList = EasyBind.map(
                aliasList, (alias) -> new AliasCard(alias, aliasList.indexOf(alias) + 1));
        aliasListView.setItems(mappedList);
        aliasListView.setCellFactory(listView -> new AliasListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AliasCard}.
     */
    class AliasListViewCell extends ListCell<AliasCard> {

        @Override
        protected void updateItem(AliasCard alias, boolean empty) {
            super.updateItem(alias, empty);

            if (empty || alias == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(alias.getRoot());
            }
        }
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    public void highlight() {
        this.commandTextField.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    public void unhighlight() {
        this.commandTextField.setStyle("-fx-border-color: #383838 #383838 #ffffff #383838; -fx-border-width: 1");
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java

    /**
     * Opens the help overlay for parent commands
     */
    @FXML
    private void handleOverlay() {
        helpOverlay.setVisible(true);
    }

    /**
     * Closes the help overlay for parent commands
     */
    @FXML
    private void handleOverlayExit() {
        helpOverlay.setVisible(false);
    }

    /**
     * Lists all Person in Bluebird.
     */
    @FXML
    private void handleListAllClicked() {
        try {
            CommandResult result = logic.execute(ListCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(ListCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to list all using label");
        }
    }

    /**
     * Lists pinned Person in Bluebird.
     */
    @FXML
    private void handleListPinnedClicked() {
        try {
            CommandResult result = logic.execute(ListPinCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(ListPinCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to list pinned using label");
        }
    }

    /**
     * Toggles to task view.
     */
    @FXML
    private void handleTaskViewClicked() {
        try {
            CommandResult result = logic.execute(EnableTaskCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(EnableTaskCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to toggle to task view using label");
        }
    }

    /**
     * Toggles to person view.
     */
    @FXML
    private void handlePersonViewClicked() {
        try {
            CommandResult result = logic.execute(EnablePersonCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(EnablePersonCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to toggle to person view using label");
        }
    }

    /**
     * Toggles to alias view.
     */
    @FXML
    private void handleAliasViewClicked() {
        try {
            CommandResult result = logic.execute(ListAliasCommand.COMMAND_WORD);
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(ListAliasCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to toggle to alias view using label");
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java

    @Subscribe
    private void handleShowPinnedListEvent(ToggleListPinStyleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        listPinToggleStyle();
    }

    @Subscribe
    private void handleShowAllListEvent(ToggleListAllStyleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        listAllToggleStyle();
    }

    @Subscribe
    private void handleSortByLabelEvent(ToggleSortByLabelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        organizedByLabel.setText(event.toString());
    }

    @Subscribe
    private void handleToggleParentChildModeEvent(ToggleParentChildModeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        helpMenu.setVisible(event.isSetToParentMode);
        aliasViewLabel.setVisible(event.isSetToParentMode);
    }

    @Subscribe
    private void handleToggleToTaskViewEvent(ToggleToTaskViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToTaskView();
    }

    @Subscribe
    private void handleToggleToAliasViewEvent(ToggleToAliasViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToAliasView();
    }

    @Subscribe
    private void handleToggleToAllPersonViewEvent(ToggleToAllPersonViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToPersonView();
    }

    @Subscribe
    private void handleUpdatePinnedPanelEvent(UpdatePinnedPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (listPinLabel.getStyle().equals(BRIGHT_LABEL)) {
            try {
                logic.execute(ListPinCommand.COMMAND_WORD);
            } catch (CommandException | ParseException e) {
                logger.warning("Unable to list pin when unpinning");
            }
        }
    }

    /**
     * Switches style to person view.
     */
    private void switchToPersonView() {
        addSelectedPanel(personListPanel.getRoot());
        setListLabelsVisible(true);
        dimAllViewLabels();
        personViewLabel.setStyle(BRIGHT_LABEL);
        organizerLabel.setText("Sorted By:");
        organizedByLabel.setText(lastSorted);
        lastSorted = organizedByLabel.getText();
        setOrganizerLabelsVisible(true);
    }

    /**
     * Switches style to task view.
     */
    private void switchToTaskView() {
        addSelectedPanel(taskListPanel.getRoot());
        setListLabelsVisible(false);
        dimAllViewLabels();
        taskViewLabel.setStyle(BRIGHT_LABEL);
        organizerLabel.setText("Showing:");
        organizedByLabel.setText("All");
        setOrganizerLabelsVisible(true);
    }

    /**
     * Switches style to alias view.
     */
    private void switchToAliasView() {
        addSelectedPanel(aliasListPanel.getRoot());
        setListLabelsVisible(false);
        dimAllViewLabels();
        aliasViewLabel.setStyle(BRIGHT_LABEL);
        setOrganizerLabelsVisible(false);
    }

    private void setOrganizerLabelsVisible(boolean isVisible) {
        organizerLabel.setVisible(isVisible);
        organizedByLabel.setVisible(isVisible);
    }

    private void setListLabelsVisible(boolean isVisible) {
        listAllLabel.setVisible(isVisible);
        listPinLabel.setVisible(isVisible);
    }

    private void dimAllViewLabels() {
        personViewLabel.setStyle(DIM_LABEL);
        aliasViewLabel.setStyle(DIM_LABEL);
        taskViewLabel.setStyle(DIM_LABEL);
    }

    /**
     * Removes current panel in personListPanelPlaceHolder and adds {@code toAdd} into it.
     */
    private void addSelectedPanel(Region toAdd) {
        personListPanelPlaceholder.getChildren()
                .removeAll(personListPanel.getRoot(), aliasListPanel.getRoot(), taskListPanel.getRoot());
        personListPanelPlaceholder.getChildren().add(toAdd);
    }

    /**
     * Unhighlights all the UIs during tutorial.
     */
    public void unhighlightAll() {
        personListPanel.unhighlight();
        commandBox.unhighlight();
        resultDisplay.unhighlight();
        sortFindPanel.unhighlight();
    }

    public void highlightCommandBox() {
        commandBox.highlight();
    }

    public void highlightResultDisplay() {
        resultDisplay.highlight();
    }

    public void highlightSortMenu() {
        sortFindPanel.highlightSortMenu();
    }

    public void highlightSearchBox() {
        sortFindPanel.highlightSearchBox();
    }

    public void highlightPersonListPanel() {
        personListPanel.highlight();
    }

    private void listAllToggleStyle() {
        listPinLabel.setStyle(DIM_LABEL);
        listAllLabel.setStyle(BRIGHT_LABEL);
    }

    private void listPinToggleStyle() {
        listPinLabel.setStyle(BRIGHT_LABEL);
        listAllLabel.setStyle(DIM_LABEL);
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java

    /**
     * Sets the image for pinned person
     */
    private void initPin(ReadOnlyPerson person) {
        if (person.isPinned()) {
            pinImage.setImage(new Image("/images/pin.png"));
        } else {
            pinImage.setImage(null);
        }
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * Scrolls to the {@code PersonCard} at the {@code index}.
     */
    private void scrollToNewPerson(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
        });
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handleJumpToNewPersonRequestEvent(JumpToNewPersonRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollToNewPerson(event.targetIndex);
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    public void highlight() {
        this.personListView.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    public void unhighlight() {
        this.personListView.setStyle("");
    }
}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    private static final String DELETE_ICON = "/images/DeleteBird.png";
    private static final String EDIT_ICON = "/images/EditBird.png";
    private static final String ERROR_ICON = "/images/ErrorBird.png";
    private static final String FIND_ICON = "/images/FindBird.png";
    private static final String HIDE_ICON = "/images/HideBird.png";
    private static final String SUCCESS_ICON = "/images/SuccessBird.png";
    private static final String TASK_ICON = "/images/TaskBird.png";
    private static final String UNDO_ICON = "/images/UndoBird.png";
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleValidResultDisplayEvent(ValidResultDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayResultIcon(event);
    }

    /**
     * Displays icon feedback for result.
     */
    private void displayResultIcon(ValidResultDisplayEvent event) {
        switch (event.message.trim()) {
        case "delete":
            listSizeDisplay.setVisible(true);
            imageDisplay.setImage(new Image(DELETE_ICON));
            break;
        case "edit":
            listSizeDisplay.setVisible(false);
            imageDisplay.setImage(new Image(EDIT_ICON));
            break;
        case "find":
        case "findpinned":
            listSizeDisplay.setVisible(true);
            imageDisplay.setImage(new Image(FIND_ICON));
            break;
        case "hide":
            listSizeDisplay.setVisible(true);
            imageDisplay.setImage(new Image(HIDE_ICON));
            break;
        case "task":
            listSizeDisplay.setVisible(false);
            imageDisplay.setImage(new Image(TASK_ICON));
            break;
        case "undo":
            listSizeDisplay.setVisible(false);
            imageDisplay.setImage(new Image(UNDO_ICON));
            break;
        case "sort":
        case "list":
        case "listpin":
            listSizeDisplay.setVisible(true);
            imageDisplay.setImage(new Image(SUCCESS_ICON));
            break;
        default:
            listSizeDisplay.setVisible(false);
            imageDisplay.setImage(new Image(SUCCESS_ICON));
        }
    }

    @Subscribe
    private void handleInvalidResultDisplayEvent(InvalidResultDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        listSizeDisplay.setVisible(false);
        imageDisplay.setImage(new Image(ERROR_ICON));
    }

    public void highlight() {
        this.placeHolder.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    public void unhighlight() {
        this.placeHolder.setStyle("");
    }
}
```
###### \java\seedu\address\ui\SortFindPanel.java
``` java

/**
 * The panel for sort menu and search box of the App.
 */
public class SortFindPanel extends UiPart<Region> {

    private static final String FXML = "SortFindPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(SortFindPanel.class);
    private final Logic logic;

    @FXML
    private TextField searchBox;

    @FXML
    private MenuButton sortMenu;

    @FXML
    private MenuItem nameItem;

    @FXML
    private MenuItem phoneItem;

    @FXML
    private MenuItem emailItem;

    @FXML
    private MenuItem addressItem;

    public SortFindPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    /**
     * Handles search field changed event.
     */
    @FXML
    private void handleSearchFieldChanged() {
        try {
            if (searchBox.getPromptText().contains("Person") || searchBox.getPromptText().contains("Task")) {
                if (searchBox.getText().trim().isEmpty()) {
                    logic.execute(ListCommand.COMMAND_WORD);
                } else {
                    logic.execute(FindCommand.COMMAND_WORD + " " + searchBox.getText());
                    raise(new ValidResultDisplayEvent(FindCommand.COMMAND_WORD));
                }
            } else if (searchBox.getPromptText().contains("Pinned")) {
                if (searchBox.getText().trim().isEmpty()) {
                    logic.execute(ListPinCommand.COMMAND_WORD);
                } else {
                    logic.execute(FindPinnedCommand.COMMAND_WORD + " " + searchBox.getText());
                    raise(new ValidResultDisplayEvent(FindPinnedCommand.COMMAND_WORD));
                }
            }
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to find person in search box");
        }
    }

    /**
     * Handles name item pressed event.
     */
    @FXML
    private void handleNameItemPressed() {
        try {
            CommandResult result = logic.execute(SortCommand.COMMAND_WORD + " " + nameItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(SortCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort name using sort menu");
        }
    }

    /**
     * Handles phone item pressed event.
     */
    @FXML
    private void handlePhoneItemPressed() {
        try {
            CommandResult result = logic.execute(SortCommand.COMMAND_WORD + " " + phoneItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(SortCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort phone using sort menu");
        }
    }

    /**
     * Handles email item pressed event.
     */
    @FXML
    private void handleEmailItemPressed() {
        try {
            CommandResult result = logic.execute(SortCommand.COMMAND_WORD + " " + emailItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(SortCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort email using sort menu");
        }
    }

    /**
     * Handles address item pressed event.
     */
    @FXML
    private void handleAddressItemPressed() {
        try {
            CommandResult result = logic.execute(SortCommand.COMMAND_WORD + " " + addressItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(SortCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort address using sort menu");
        }
    }

    /**
     * Handles switch to task view event
     */
    @Subscribe
    private void handleToggleToTaskViewEvent(ToggleToTaskViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToTaskView();
    }

    /**
     * Handles switch to all person view event
     */
    @Subscribe
    private void handleToggleToAllPersonViewEvent(ToggleToAllPersonViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToPersonView();
    }

    /**
     * Handles switch to alias view event
     */
    @Subscribe
    private void handleToggleToAliasViewEvent(ToggleToAliasViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToAliasView();
    }

    /**
     * Handles switch to pinned person view event
     */
    @Subscribe
    private void handleToggleSearchBoxStyleEvent(ToggleSearchBoxStyle event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.isPinnedStyle()) {
            switchToPinnedPersonSearchStyle();
        } else {
            switchToAllPersonSearchStyle();
        }
    }

    /**
     * Switches style to alias view.
     */
    private void switchToAliasView() {
        searchBox.setVisible(false);
        sortMenu.setVisible(false);
    }

    /**
     * Switches style to pinned person search.
     */
    private void switchToPinnedPersonSearchStyle() {
        searchBox.setText("");
        searchBox.setPromptText("Search Pinned...");
    }

    /**
     * Switches style to all person search.
     */
    private void switchToAllPersonSearchStyle() {
        searchBox.setText("");
        searchBox.setPromptText("Search Person...");
    }

    /**
     * Switches style to task view.
     */
    private void switchToTaskView() {
        searchBox.setText("");
        searchBox.setPromptText("Search Task...");
        sortMenu.setVisible(false);
        searchBox.setVisible(true);
    }

    /**
     * Switches style to person view.
     */
    private void switchToPersonView() {
        searchBox.setText("");
        searchBox.setPromptText("Search Person...");
        sortMenu.setVisible(true);
        searchBox.setVisible(true);
    }

    public MenuButton getSortMenu() {
        return sortMenu;
    }

    public TextField getSearchBox() {
        return searchBox;
    }

    public void highlightSortMenu() {
        sortMenu.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    public void highlightSearchBox() {
        searchBox.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    /**
     * Unhighlights the sort menu and search box.
     */
    public void unhighlight() {
        sortMenu.setStyle("");
        searchBox.setStyle("");
    }
}
```
###### \java\seedu\address\ui\Tutorial.java
``` java

/**
 * The tutorial of the address book.
 */
public class Tutorial {

    private MainWindow mainWindow;
    private TextArea tutorialText;
    private ArrayList<TutSteps> tutorialSteps = new ArrayList<>();
    private int currentStepNum = 0;

    public Tutorial(MainWindow mainWindow, TextArea tutorialText) {
        this.mainWindow = mainWindow;
        this.tutorialText = tutorialText;

        setUpTutorial();
    }

    private void setUpTutorial() {

        /* Steps for introduction to Bluebird */
        for (String introMessages : TutorialMessages.INTRO_LIST) {
            tutorialSteps.add(new TutSteps(introMessages));
        }

        /* Steps for conclusion */
        tutorialSteps.add(new TutSteps(TutorialMessages.CONCLUSION));
    }

    /**
     * Executes the next tutorial step.
     */
    public void executeNextStep() {
        TutSteps stepToExecute = tutorialSteps.get(currentStepNum);
        mainWindow.unhighlightAll();
        switch (currentStepNum++) {
        case 0:
            mainWindow.highlightCommandBox();
            break;
        case 1:
            mainWindow.highlightResultDisplay();
            break;
        case 2:
            mainWindow.highlightPersonListPanel();
            break;

        case 3:
            mainWindow.highlightSortMenu();
            break;

        case 4:
            mainWindow.highlightSearchBox();
            break;

        default:
            break;
        }
        tutorialText.setText(stepToExecute.getTextDisplay());
    }

    /**
     * Executes the previous tutorial step.
     */
    public void executePreviousStep() throws CommandException, ParseException {
        if (currentStepNum - 2 >= 0) {
            currentStepNum -= 2;
            executeNextStep();
        }
    }

    public boolean isLastStep() {
        return currentStepNum == TutorialMessages.TOTAL_NUM_STEPS;
    }
}

/**
 * The steps of the tutorial.
 */
class TutSteps {

    private String textDisplay;

    public TutSteps(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public String getTextDisplay() {
        return textDisplay;
    }
}
```
###### \java\seedu\address\ui\TutorialPanel.java
``` java
/**
 * The panel for tutorial of the App.
 */
public class TutorialPanel extends UiPart<Region> {

    private static final String FXML = "TutorialPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(TutorialPanel.class);

    private Tutorial newTutorial;
    private MainWindow mainWindow;
    private StackPane browserPlaceHolder;
    private boolean tutorialIntro = true;

    @FXML
    private Button rightButton;

    @FXML
    private Button leftButton;

    @FXML
    private Button skipButton;

    @FXML
    private ImageView tutorialImage;

    @FXML
    private TextArea tutorialText;

    public TutorialPanel(MainWindow mainWindow, StackPane browserPlaceHolder) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.browserPlaceHolder = browserPlaceHolder;
        tutorialText.setText(TutorialMessages.INTRO_BEGIN);
        initTutorial();
    }

    private void initTutorial() {
        newTutorial = new Tutorial(mainWindow, tutorialText);
    }

    /**
     * Handles the left button pressed event.
     */
    @FXML
    private void handleLeftButtonPressed() throws CommandException, ParseException {
        if (tutorialIntro) {
            tutorialIntro = false;
            leftButton.setText("Back");
            rightButton.setText("Next");
            skipButton.setVisible(true);
            newTutorial.executeNextStep();
        } else {
            newTutorial.executePreviousStep();
        }
    }

    /**
     * Handles the right button pressed event.
     */
    @FXML
    private void handleRightButtonPressed() {
        if (tutorialIntro) {
            endTutorial();
        } else if (!newTutorial.isLastStep()) {
            newTutorial.executeNextStep();
        } else if (newTutorial.isLastStep()) {
            endTutorial();
        }
    }

    /**
     * Handles the skip button pressed event.
     */
    @FXML
    private void handleSkipButtonPressed() {
        endTutorial();
    }

    /**
     * Removes tutorial panel and replace with browser panel
     */
    private void endTutorial() {
        mainWindow.unhighlightAll();
        browserPlaceHolder.getChildren().remove(this.getRoot());
    }
}
```
###### \resources\view\AliasListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<GridPane gridLinesVisible="true" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="150.0" />
   </columnConstraints>
   <rowConstraints>
      <RowConstraints />
   </rowConstraints>
   <children>
      <VBox alignment="CENTER_LEFT" minHeight="27.0" prefHeight="27.0" prefWidth="150.0" GridPane.columnIndex="0">
         <padding>
            <Insets bottom="5" left="15" right="5" top="5" />
         </padding>
         <children>
            <HBox alignment="CENTER_LEFT" spacing="5">
               <children>
                  <Label fx:id="id" styleClass="cell_big_label">
                     <minWidth>
                        <Region fx:constant="USE_PREF_SIZE" />
                     </minWidth>
                  </Label>
                  <Label fx:id="representation" styleClass="cell_big_label" text="\$first" />
               </children>
               <VBox.margin>
                  <Insets />
               </VBox.margin>
            </HBox>
         </children>
      </VBox>
      <Label fx:id="keyword" styleClass="cell_big_label" text="\$first" GridPane.columnIndex="1">
         <GridPane.margin>
            <Insets left="10.0" />
         </GridPane.margin>
      </Label>
   </children>
</GridPane>
```
###### \resources\view\AliasListPanel.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane gridLinesVisible="true">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="-Infinity" prefHeight="30.0" vgrow="ALWAYS" />
        </rowConstraints>
         <children>
            <Label style="-fx-text-fill: white;" text="  Representation" />
            <Label style="-fx-text-fill: white;" text="  Keyword" GridPane.columnIndex="1" />
         </children>
      </GridPane>
      <ListView fx:id="aliasListView" VBox.vgrow="ALWAYS" />
   </children>
</VBox>
```
###### \resources\view\MainWindow.fxml
``` fxml
                              <GridPane cacheShape="false" centerShape="false" minHeight="-Infinity" prefWidth="601.0">
                                <columnConstraints>
                                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="310.0" minWidth="0.0" prefWidth="59.0" />
                                  <ColumnConstraints hgrow="SOMETIMES" maxWidth="614.0" minWidth="0.0" prefWidth="47.0" />
                                    <ColumnConstraints hgrow="SOMETIMES" maxWidth="614.0" minWidth="10.0" prefWidth="230.0" />
                                    <ColumnConstraints hgrow="SOMETIMES" maxWidth="339.0" minWidth="0.0" prefWidth="40.0" />
                                    <ColumnConstraints hgrow="SOMETIMES" maxWidth="583.0" minWidth="10.0" prefWidth="234.0" />
                                    <ColumnConstraints hgrow="SOMETIMES" maxWidth="149.0" minWidth="0.0" prefWidth="86.0" />
                                    <ColumnConstraints hgrow="SOMETIMES" maxWidth="156.0" minWidth="10.0" prefWidth="49.0" />
                                </columnConstraints>
                                <rowConstraints>
                                  <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                                </rowConstraints>
                                 <children>
                                    <Label fx:id="personViewLabel" onMouseReleased="#handlePersonViewClicked" style="-fx-text-fill: white;" text="Person">
                                       <GridPane.margin>
                                          <Insets bottom="5.0" left="5.0" />
                                       </GridPane.margin>
                                    </Label>
                                    <Label fx:id="taskViewLabel" onMouseReleased="#handleTaskViewClicked" text="Task" GridPane.columnIndex="1">
                                       <GridPane.margin>
                                          <Insets bottom="5.0" left="10.0" />
                                       </GridPane.margin>
                                    </Label>
                                    <Label fx:id="listAllLabel" onMouseReleased="#handleListAllClicked" style="-fx-text-fill: white;" text="All" GridPane.columnIndex="3">
                                       <GridPane.margin>
                                          <Insets bottom="5.0" left="10.0" />
                                       </GridPane.margin>
                                    </Label>
                                    <Label fx:id="listPinLabel" onMouseReleased="#handleListPinnedClicked" text="Pinned" GridPane.columnIndex="4">
                                       <GridPane.margin>
                                          <Insets bottom="5.0" left="10.0" />
                                       </GridPane.margin>
                                    </Label>
                                    <Label fx:id="organizerLabel" prefHeight="21.0" prefWidth="76.0" style="-fx-text-fill: white;" text="Sorted By:" GridPane.columnIndex="5">
                                       <GridPane.margin>
                                          <Insets bottom="5.0" />
                                       </GridPane.margin>
                                    </Label>
                                    <Label fx:id="organizedByLabel" prefHeight="21.0" prefWidth="149.0" style="-fx-text-fill: white;" text="Name" GridPane.columnIndex="6">
                                       <GridPane.margin>
                                          <Insets bottom="5.0" left="2.0" />
                                       </GridPane.margin>
                                    </Label>
                                    <Label fx:id="aliasViewLabel" onMouseReleased="#handleAliasViewClicked" text="Alias" visible="false" GridPane.columnIndex="2">
                                       <GridPane.margin>
                                          <Insets bottom="5.0" left="10.0" />
                                       </GridPane.margin>
                                    </Label>
                                 </children>
                              </GridPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
      <ScrollPane fx:id="helpOverlay" fitToHeight="true" fitToWidth="true" opacity="0.9" pannable="true" visible="false" StackPane.alignment="TOP_CENTER">
         <StackPane.margin>
            <Insets bottom="310.0" left="8.0" right="8.0" top="40.0" />
         </StackPane.margin>
         <content>
            <GridPane fx:id="overlayGrid" alignment="TOP_CENTER" blendMode="SRC_ATOP" maxWidth="1.7976931348623157E308" style="-fx-background-color: derive(#1d1d1d, 20%);">
               <columnConstraints>
                  <ColumnConstraints hgrow="ALWAYS" maxWidth="200.0" minWidth="200.0" />
                  <ColumnConstraints hgrow="ALWAYS" minWidth="1100.0" />
               </columnConstraints>
               <rowConstraints>
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
                  <RowConstraints vgrow="SOMETIMES" />
               </rowConstraints>
               <children>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Add contact" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="2">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="730.0" text="Clear" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="5">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="769.0" text="Delete" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="6">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="586.0" text="Command" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="1">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label alignment="CENTER" maxWidth="1.7976931348623157E308" prefHeight="45.0" prefWidth="1346.0" text="Example" textFill="#bdadff" GridPane.columnIndex="1" GridPane.rowIndex="1">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="clear" GridPane.columnIndex="1" GridPane.rowIndex="5">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="delete 2" GridPane.columnIndex="1" GridPane.rowIndex="6">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Rename Task" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="8">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Find" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="16">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="List" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="20">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="List pinned" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="21">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Help" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="24">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="edit 1 p/91234567 e/johndoe@example.com" GridPane.columnIndex="1" GridPane.rowIndex="7">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="find alice bob charlie" GridPane.columnIndex="1" GridPane.rowIndex="16">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="list" GridPane.columnIndex="1" GridPane.rowIndex="20">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="listpin" GridPane.columnIndex="1" GridPane.rowIndex="21">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="help" GridPane.columnIndex="1" GridPane.rowIndex="24">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="select 3" GridPane.columnIndex="1" GridPane.rowIndex="26">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="history" GridPane.columnIndex="1" GridPane.rowIndex="27">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Select" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="26">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="History" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="27">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Sort" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="28">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="sort phone" GridPane.columnIndex="1" GridPane.rowIndex="28">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="undo" GridPane.columnIndex="1" GridPane.rowIndex="29">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="redo" GridPane.columnIndex="1" GridPane.rowIndex="30">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="hide 3" GridPane.columnIndex="1" GridPane.rowIndex="31">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Undo" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="29">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Redo" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="30">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Hide " textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="31">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="586.0" text="Alias" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="18">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Unalias" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="19">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Pin a contact" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="14">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Unpin a contact" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="15">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="alias k/ph s/ Public Holiday" GridPane.columnIndex="1" GridPane.rowIndex="18">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="unalias k/ph" GridPane.columnIndex="1" GridPane.rowIndex="19">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="pin 2" GridPane.columnIndex="1" GridPane.rowIndex="14">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="unpin 1" GridPane.columnIndex="1" GridPane.rowIndex="15">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label text="Press Esc to close" textFill="#bdadff">
                     <font>
                        <Font size="20.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Person View" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="32">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="person" GridPane.columnIndex="1" GridPane.rowIndex="32">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Task View" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="33">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="task" GridPane.columnIndex="1" GridPane.rowIndex="33">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Parent Mode" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="35">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="parent" GridPane.columnIndex="1" GridPane.rowIndex="35">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="child" GridPane.columnIndex="1" GridPane.rowIndex="36">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Child Mode" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="36">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Add a task" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="4">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="add n/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 b/22/08/1993 t/friends t/owesMoney" GridPane.columnIndex="1" GridPane.rowIndex="2">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="add Go Fishing from 9am to 11am" GridPane.columnIndex="1" GridPane.rowIndex="4">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Edit a contact" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="7">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Reschedule Task" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="10">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="rename 2 Football training" GridPane.columnIndex="1" GridPane.rowIndex="8">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="reschedule 1 from 8am to 12pm" GridPane.columnIndex="1" GridPane.rowIndex="10">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="List Alias" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="22">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="listalias" GridPane.columnIndex="1" GridPane.rowIndex="22">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Mark Task" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="11">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Unmark Task" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="12">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="mark 1~3" GridPane.columnIndex="1" GridPane.rowIndex="11">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="unmark 1~3" GridPane.columnIndex="1" GridPane.rowIndex="12">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Find pinned" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="17">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="findpinned bala" GridPane.columnIndex="1" GridPane.rowIndex="17">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Exit" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="37">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="exit" GridPane.columnIndex="1" GridPane.rowIndex="37">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label prefHeight="45.0" prefWidth="740.0" text="Add remarks" textAlignment="CENTER" textFill="#bdadff" GridPane.rowIndex="3">
                     <font>
                        <Font size="24.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
                  <TextField alignment="CENTER" editable="false" style="-fx-background-color: transparent; -fx-text-fill: #bdadff;" text="remark 1" GridPane.columnIndex="1" GridPane.rowIndex="3">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
               </children>
            </GridPane>
         </content>
      </ScrollPane>
```
###### \resources\view\ResultDisplay.fxml
``` fxml
   <ImageView fx:id="imageDisplay" fitHeight="106.0" fitWidth="138.0" pickOnBounds="true" preserveRatio="true" StackPane.alignment="TOP_CENTER">
      <StackPane.margin>
         <Insets top="5.0" />
      </StackPane.margin>
   </ImageView>
```
###### \resources\view\SortFindPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.MenuButton?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.HBox?>
<HBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="39.0" prefWidth="600.0" spacing="10.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <MenuButton fx:id="sortMenu" minHeight="-Infinity" minWidth="-Infinity" mnemonicParsing="false" prefHeight="32.0" prefWidth="97.0" text="Sort By:">
        <items>
          <MenuItem fx:id="nameItem" mnemonicParsing="false" onAction="#handleNameItemPressed" text="Name" />
          <MenuItem fx:id="phoneItem" mnemonicParsing="false" onAction="#handlePhoneItemPressed" text="Phone" />
            <MenuItem fx:id="emailItem" mnemonicParsing="false" onAction="#handleEmailItemPressed" text="Email" />
            <MenuItem fx:id="addressItem" mnemonicParsing="false" onAction="#handleAddressItemPressed" text="Address" />
        </items>
      </MenuButton>
      <TextField fx:id="searchBox" minHeight="-Infinity" minWidth="-Infinity" onKeyReleased="#handleSearchFieldChanged" prefHeight="32.0" prefWidth="149.0" promptText="Search Person..." />
   </children>
   <padding>
      <Insets left="10.0" top="3.0" />
   </padding>
</HBox>
```
###### \resources\view\TutorialPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.ButtonBar?>
<?import javafx.scene.control.TextArea?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.text.Font?>

<GridPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <columnConstraints>
    <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
    <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
  </columnConstraints>
  <rowConstraints>
    <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
    <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
    <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
  </rowConstraints>
   <children>
      <ImageView fx:id="tutorialImage" fitHeight="230.0" fitWidth="180.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="3" GridPane.rowIndex="4">
         <image>
            <Image url="@../images/TutorialBird.png" />
         </image>
      </ImageView>
      <ButtonBar minWidth="-Infinity" prefHeight="50.0" prefWidth="291.0" GridPane.columnIndex="1" GridPane.rowIndex="7">
        <buttons>
          <Button fx:id="leftButton" mnemonicParsing="false" onAction="#handleLeftButtonPressed" prefHeight="25.0" prefWidth="201.0" text="Yes" />
            <Button fx:id="rightButton" mnemonicParsing="false" onAction="#handleRightButtonPressed" text="Skip" />
        </buttons>
         <GridPane.margin>
            <Insets bottom="50.0" />
         </GridPane.margin>
         <padding>
            <Insets right="20.0" />
         </padding>
      </ButtonBar>
      <TextArea fx:id="tutorialText" editable="false" minHeight="-Infinity" minWidth="-Infinity" prefHeight="238.0" prefWidth="292.0" wrapText="true" GridPane.columnIndex="1" GridPane.rowIndex="3">
         <font>
            <Font size="18.0" />
         </font>
      </TextArea>
      <Button fx:id="skipButton" alignment="CENTER" minHeight="-Infinity" minWidth="-Infinity" mnemonicParsing="false" onAction="#handleSkipButtonPressed" prefHeight="31.0" prefWidth="93.0" text="Skip" visible="false" GridPane.columnIndex="3" GridPane.rowIndex="7">
         <GridPane.margin>
            <Insets bottom="50.0" left="23.0" />
         </GridPane.margin>
      </Button>
   </children>
</GridPane>
```
