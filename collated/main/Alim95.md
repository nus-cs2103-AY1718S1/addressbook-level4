# Alim95
###### \java\seedu\address\commons\core\TutorialMessages.java
``` java

/**
 * Container for tutorial messages.
 */
public class TutorialMessages {

    public static final int TOTAL_NUM_STEPS = 8;

    /* Introductory Messages */
    public static final String INTRO_BEGIN = "Welcome to Bluebird! Would you like to go through the tutorial?";
    public static final String INTRO_TWO = "This is the command box, where you will enter your commands.";
    public static final String INTRO_THREE = "This is the result display, where "
            + "I will display the outcome of your commands.";
    public static final String INTRO_FOUR = "This is the sort menu, where you can select how to sort the list.";
    public static final String INTRO_FIVE = "This is the search box, where "
            + "you are able to search for the person you want.";
    public static final String INTRO_SIX = "This is the person list panel, where you will see your list of contacts";
    public static final String INTRO_END = "Features of Bluebird:\n"
            + "1. Add a contact\n"
            + "2. Delete a contact\n"
            + "3. Edit a contact\n"
            + "4. Find a contact\n"
            + "5. Select a contact\n"
            + "6. Pin a contact\n"
            + "7. Unpin a contact\n"
            + "8. Hide a contact\n"
            + "9. Clear all contacts\n"
            + "10. List all contacts\n"
            + "11. Sort list of contacts\n"
            + "12. Help window\n"
            + "13. History of command inputs\n"
            + "14. Undo a command\n"
            + "15. Redo a command\n";

    /* Command usage messages */
    public static final String USAGE_BEGIN = "Let's try out the different commands of Bluebird! Press F2 to view "
            + "the list of commands and enter the commands on the command box to execute it."
            + " A parameter in [ ] means it is optional.";

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
###### \java\seedu\address\commons\events\ui\SwitchToBrowserEvent.java
``` java

/**
 * An event requesting to switch to browser panel.
 */
public class SwitchToBrowserEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ToggleListAllStyleEvent.java
``` java

/**
 * An event requesting to toggle the style of All tab.
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
###### \java\seedu\address\commons\events\ui\ValidResultDisplayEvent.java
``` java

/**
 * Indicates that a valid command is entered.
 */
public class ValidResultDisplayEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
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
 * Lists all persons in the address book to the user.
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

        List<ReadOnlyPerson> pinnedList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= pinnedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnpin = pinnedList.get(targetIndex.getZeroBased());

        if (!personToUnpin.isPinned()) {
            throw new CommandException(Messages.MESSAGE_PERSON_ALREADY_UNPINNED);
        }

        try {
            model.unpinPerson(personToUnpin);
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

    public PersonHasKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
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
        return isPersonMatch(person, nameParts, tagParts);
    }

    /**
     * Returns true if the person has properties that matches the keywords.
     */
    private boolean isPersonMatch(ReadOnlyPerson person, String[] nameParts, ArrayList<String> tagParts) {
        for (String tag : tagParts) {
            if (keywords.stream().anyMatch(keyword -> tag.startsWith(keyword.toLowerCase()))) {
                return true;
            }
        }
        for (String name : nameParts) {
            if (keywords.stream().anyMatch(keyword -> name.toLowerCase().startsWith(keyword.toLowerCase()))) {
                return true;
            }
        }
        if (keywords.size() != 0 && person.getAddress().toString().toLowerCase().contains(fullWord)) {
            return true;
        }
        if (keywords.stream().anyMatch(keyword -> person.getEmail().toString().toLowerCase()
                .startsWith(keyword.toLowerCase()))) {
            return true;
        }
        return keywords.stream().anyMatch(keyword -> person.getPhone().toString().startsWith(keyword.toLowerCase()));
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
            internalList.sort((p1, p2) -> p1.getName().toString().compareToIgnoreCase(p2.getName().toString()));
            break;
        case "phone":
            internalList.sort((p1, p2) -> p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString()));
            break;
        case "email":
            internalList.sort((p1, p2) -> p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString()));
            break;
        case "address":
            internalList.sort((p1, p2) -> p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString()));
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
     * Opens the help overlay
     */
    @FXML
    private void handleOverlay() {
        helpOverlay.setVisible(true);
    }

    /**
     * Closes the help overlay
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
        listAllToggleStyle();
        try {
            logic.execute("list");
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to list all using label");
        }
    }

    /**
     * Lists pinned Person in Bluebird.
     */
    @FXML
    private void handleListPinnedClicked() {
        listPinToggleStyle();
        try {
            logic.execute("listpin");
        } catch (CommandException | ParseException e) {
            logger.warning("Failed to list pinned using label");
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleSwitchToBrowserEvent(SwitchToBrowserEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToBrowser();
    }

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
        sortedByLabel.setText(event.toString());
    }

    private void switchToBrowser() {
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
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
        pinLabel.setStyle("-fx-text-fill: #555555");
        allLabel.setStyle("-fx-text-fill: white");
    }

    private void listPinToggleStyle() {
        pinLabel.setStyle("-fx-text-fill: white");
        allLabel.setStyle("-fx-text-fill: #555555");
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
    @Subscribe
    private void handleValidResultDisplayEvent(ValidResultDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        imageDisplay.setImage(new Image("/images/success.png"));
    }

    @Subscribe
    private void handleInvalidResultDisplayEvent(InvalidResultDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        imageDisplay.setImage(new Image("/images/error.png"));
    }

    public void highlight() {
        this.resultDisplay.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    public void unhighlight() {
        this.resultDisplay.setStyle("");
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
    private static final String SORT_COMMAND_WORD = "sort";
    private static final String FIND_COMMAND_WORD = "find";
    private static final String LIST_COMMAND_WORD = "list";

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
    }

    /**
     * Handles search field changed event.
     */
    @FXML
    private void handleSearchFieldChanged() {
        try {
            if (searchBox.getText().trim().isEmpty()) {
                logic.execute(LIST_COMMAND_WORD);
            } else {
                logic.execute(FIND_COMMAND_WORD + " " + searchBox.getText());
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
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + nameItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
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
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + phoneItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
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
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + emailItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
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
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + addressItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort address using sort menu");
        }
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
            mainWindow.highlightSortMenu();
            break;
        case 3:
            mainWindow.highlightSearchBox();
            break;
        case 4:
            mainWindow.highlightPersonListPanel();
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
        raise(new SwitchToBrowserEvent());
    }
}
```
###### \resources\view\MainWindow.fxml
``` fxml
                        <GridPane minWidth="-Infinity" prefWidth="340.0">
                          <columnConstraints>
                            <ColumnConstraints hgrow="SOMETIMES" maxWidth="130.0" minWidth="0.0" prefWidth="61.0" />
                            <ColumnConstraints hgrow="SOMETIMES" maxWidth="368.0" minWidth="0.0" prefWidth="141.0" />
                              <ColumnConstraints hgrow="SOMETIMES" maxWidth="312.0" minWidth="10.0" prefWidth="30.0" />
                              <ColumnConstraints hgrow="SOMETIMES" maxWidth="353.0" minWidth="10.0" prefWidth="157.0" />
                              <ColumnConstraints hgrow="SOMETIMES" maxWidth="353.0" minWidth="10.0" prefWidth="69.0" />
                              <ColumnConstraints hgrow="SOMETIMES" maxWidth="353.0" minWidth="10.0" prefWidth="71.0" />
                          </columnConstraints>
                          <rowConstraints>
                            <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                          </rowConstraints>
                           <children>
                              <Label style="-fx-text-fill: white;" text="Person">
                                 <GridPane.margin>
                                    <Insets bottom="5.0" left="5.0" />
                                 </GridPane.margin>
                              </Label>
                              <Label text="Task" GridPane.columnIndex="1">
                                 <GridPane.margin>
                                    <Insets bottom="5.0" left="10.0" />
                                 </GridPane.margin>
                              </Label>
                              <Label fx:id="allLabel" onMouseReleased="#handleListAllClicked" style="-fx-text-fill: white;" text="All" GridPane.columnIndex="2">
                                 <GridPane.margin>
                                    <Insets bottom="5.0" left="10.0" />
                                 </GridPane.margin>
                              </Label>
                              <Label fx:id="pinLabel" onMouseReleased="#handleListPinnedClicked" text="Pinned" GridPane.columnIndex="3">
                                 <GridPane.margin>
                                    <Insets bottom="5.0" left="10.0" />
                                 </GridPane.margin></Label>
                              <Label prefHeight="21.0" prefWidth="76.0" style="-fx-text-fill: white;" text="Sorted By:" GridPane.columnIndex="4">
                                 <GridPane.margin>
                                    <Insets bottom="5.0" />
                                 </GridPane.margin>
                              </Label>
                              <Label fx:id="sortedByLabel" prefHeight="21.0" prefWidth="74.0" style="-fx-text-fill: white;" text="Name" GridPane.columnIndex="5">
                                 <GridPane.margin>
                                    <Insets bottom="5.0" left="2.0" />
                                 </GridPane.margin>
                              </Label>
                           </children>
                        </GridPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
      <ScrollPane fx:id="helpOverlay" fitToHeight="true" fitToWidth="true" opacity="0.9" prefHeight="200.0" prefWidth="200.0" visible="false" StackPane.alignment="TOP_CENTER">
         <content>
            <GridPane alignment="TOP_CENTER" blendMode="SRC_ATOP" gridLinesVisible="true" minWidth="-Infinity" prefHeight="855.0" prefWidth="1249.0" style="-fx-background-color: white;">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="179.0" minWidth="0.0" prefWidth="178.0" />
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="1064.0" minWidth="10.0" prefWidth="1051.0" />
              </columnConstraints>
              <rowConstraints>
                  <RowConstraints maxHeight="40.0" minHeight="32.0" prefHeight="32.0" vgrow="SOMETIMES" />
                  <RowConstraints maxHeight="40.0" minHeight="32.0" prefHeight="32.0" vgrow="SOMETIMES" />
                <RowConstraints maxHeight="58.0" minHeight="50.0" prefHeight="58.0" vgrow="SOMETIMES" />
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
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Add" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="2">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="730.0" text="Clear" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="3">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="769.0" text="Delete" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="4">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="586.0" text="Command" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="1">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" maxWidth="1.7976931348623157E308" prefHeight="45.0" prefWidth="1346.0" text="Usage" textFill="#8b71f2" GridPane.columnIndex="1" GridPane.rowIndex="1">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <TextField alignment="CENTER" editable="false" text="add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/tag]" GridPane.columnIndex="1" GridPane.rowIndex="2">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="clear" GridPane.columnIndex="1" GridPane.rowIndex="3">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="delete INDEX [MORE_INDEX]" GridPane.columnIndex="1" GridPane.rowIndex="4">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Edit" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="5">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Find" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="6">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="List" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="7">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="List pinned" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="8">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Help" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="9">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <TextField alignment="CENTER" editable="false" text="edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG] …​" GridPane.columnIndex="1" GridPane.rowIndex="5">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="find KEYWORD [MORE_KEYWORDS]" GridPane.columnIndex="1" GridPane.rowIndex="6">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="list" GridPane.columnIndex="1" GridPane.rowIndex="7">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="listpin" GridPane.columnIndex="1" GridPane.rowIndex="8">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="help" GridPane.columnIndex="1" GridPane.rowIndex="9">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="select INDEX" GridPane.columnIndex="1" GridPane.rowIndex="10">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="history" GridPane.columnIndex="1" GridPane.rowIndex="11">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Select" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="10">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="History" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="11">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Sort" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="12">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <TextField alignment="CENTER" editable="false" text="sort KEYWORD" GridPane.columnIndex="1" GridPane.rowIndex="12">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="undo" GridPane.columnIndex="1" GridPane.rowIndex="13">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="redo" GridPane.columnIndex="1" GridPane.rowIndex="14">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="hide INDEX" GridPane.columnIndex="1" GridPane.rowIndex="15">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Undo" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="13">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Redo" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="14">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Hide " textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="15">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="586.0" text="Alias" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="16">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Unalias" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="17">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Pin" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="18">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <Label alignment="CENTER" prefHeight="45.0" prefWidth="740.0" text="Unpin" textAlignment="CENTER" textFill="#8b71f2" GridPane.rowIndex="19">
                     <font>
                        <Font size="24.0" />
                     </font>
                  </Label>
                  <TextField alignment="CENTER" editable="false" text="alias k/KEYWORD s/REPRESENTATION" GridPane.columnIndex="1" GridPane.rowIndex="16">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="unalias k/KEYWORD" GridPane.columnIndex="1" GridPane.rowIndex="17">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="pin INDEX" GridPane.columnIndex="1" GridPane.rowIndex="18">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <TextField alignment="CENTER" editable="false" text="unpin INDEX" GridPane.columnIndex="1" GridPane.rowIndex="19">
                     <font>
                        <Font size="20.0" />
                     </font>
                  </TextField>
                  <Label text="Press Esc to close">
                     <font>
                        <Font size="20.0" />
                     </font>
                     <GridPane.margin>
                        <Insets left="10.0" />
                     </GridPane.margin>
                  </Label>
               </children>
            </GridPane>
         </content>
         <StackPane.margin>
            <Insets bottom="8.0" left="10.0" right="10.0" top="165.0" />
         </StackPane.margin>
      </ScrollPane>
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
      <ImageView fx:id="tutorialImage" fitHeight="167.0" fitWidth="173.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="3" GridPane.rowIndex="4">
         <image>
            <Image url="@../images/blue_bird_tutorial.png" />
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
