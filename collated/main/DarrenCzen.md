# DarrenCzen
###### /java/seedu/address/commons/events/ui/AccessLocationRequestEvent.java
``` java
/**
 * Indicates a request to load the location of a person on Google Maps Search in the Browser.
 */
public class AccessLocationRequestEvent extends BaseEvent {
    public final String location;

    public AccessLocationRequestEvent(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/AccessWebsiteRequestEvent.java
``` java
/**
 * Indicates a request to load the website of a person in the Browser.
 */
public class AccessWebsiteRequestEvent extends BaseEvent {

    public final String website;

    public AccessWebsiteRequestEvent(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/EventPanelUnselectEvent.java
``` java
/**
 * Indicates a request to unselect an event card in the case of a delete or switching of platforms
 */
public class EventPanelUnselectEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/TogglePanelEvent.java
``` java

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to toggle the list panel
 */
public class TogglePanelEvent extends BaseEvent {

    public final String selectedPanel;

    public TogglePanelEvent(String selectedPanel) {
        this.selectedPanel = selectedPanel;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/AccessCommand.java
``` java
/**
 * Accesses a person's website in the address book.
 */
public class AccessCommand extends Command {
    public static final String COMMAND_WORD = "access";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Accesses the website of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ACCESS_PERSON_SUCCESS = "Accessed website of Person: %2$s at Index %1$s";

    private final Index targetIndex;

    public AccessCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
        String name = person.getName().toString();
        String website = person.getWebsite().toString();

        if (website.equals("NIL")) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEBSITE);
        }

        EventsCenter.getInstance().post(new AccessWebsiteRequestEvent(website));
        return new CommandResult(String.format(MESSAGE_ACCESS_PERSON_SUCCESS, targetIndex.getOneBased(), name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccessCommand // instanceof handles nulls
                && this.targetIndex.equals(((AccessCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setWebsite(Website website) {
            this.website = website;
        }

        public Optional<Website> getWebsite() {
            return Optional.ofNullable(website);
        }

```
###### /java/seedu/address/logic/commands/LocationCommand.java
``` java
/**
 * Accesses a person's location in the address book.
 */
public class LocationCommand extends Command {
    public static final String COMMAND_WORD = "locate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Accesses the location of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Accessed location of Person: %2$s at Index %1$s";

    private final Index targetIndex;

    public LocationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
        String name = person.getName().toString();
        String location = person.getAddress().toString();

        if (location.equals("NIL")) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOCATION);
        }

        EventsCenter.getInstance().post(new AccessLocationRequestEvent(location));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, targetIndex.getOneBased(), name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocationCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sorts all persons in the address book alphabetically for the user.
 */

public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons in the list";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/parser/AccessCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AccessCommand object
 */
public class AccessCommandParser implements Parser<AccessCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AccessCommand
     * and returns an AccessCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AccessCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AccessCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AccessCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        Address address;
        Birthday birthday;
        HomeNumber homeNumber;
        SchEmail schEmail;
        Website website;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_HOME_NUMBER,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_WEBSITE, PREFIX_SCH_EMAIL,
                        PREFIX_BIRTHDAY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            // Optional Address Field
            Optional<Address> tempAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            address = (tempAddress.isPresent()) ? tempAddress.get() : new Address(null);

            // Optional Birthday Field
            Optional<Birthday> tempBirthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY));
            birthday = (tempBirthday.isPresent()) ? tempBirthday.get() : new Birthday(null);

            // Optional HomeNumber Field
            Optional<HomeNumber> tempHomeNumber = ParserUtil.parseHomeNumber(argMultimap.getValue(PREFIX_HOME_NUMBER));
            homeNumber = (tempHomeNumber.isPresent()) ? tempHomeNumber.get() : new HomeNumber(null);

            // Optional SchEmail Field
            Optional<SchEmail> tempSchEmail = ParserUtil.parseSchEmail(argMultimap.getValue(PREFIX_SCH_EMAIL));
            schEmail = (tempSchEmail.isPresent()) ? tempSchEmail.get() : new SchEmail(null);

            // Optional Website
            Optional<Website> tempWebsite = ParserUtil.parseWebsite(argMultimap.getValue(PREFIX_WEBSITE));
            website = (tempWebsite.isPresent()) ? tempWebsite.get() : new Website(null);

            ReadOnlyPerson person = new Person(name, phone, homeNumber,
                    email, schEmail, website, address, birthday, false, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used to control lock mechanism for different commands between person and events platforms.
     */
    private static Boolean personListActivated = true;

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddCommand.COMMAND_WORD: case AddCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new AddCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case AccessCommand.COMMAND_WORD:
            if (personListActivated) {
                return new AccessCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case EditCommand.COMMAND_WORD: case EditCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new EditCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case SelectCommand.COMMAND_WORD: case SelectCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new SelectCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case DeleteCommand.COMMAND_WORD: case DeleteCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new DeleteCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case ClearCommand.COMMAND_WORD: case ClearCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new ClearCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case FindCommand.COMMAND_WORD: case FindCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new FindCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case FindTagCommand.COMMAND_WORD: case FindTagCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new FindTagCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case LocationCommand.COMMAND_WORD:
            if (personListActivated) {
                return new LocationCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case SortCommand.COMMAND_WORD:
            if (personListActivated) {
                return new SortCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case ListCommand.COMMAND_WORD: case ListCommand.COMMAND_ALIAS:
            personListActivated = true;
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD: case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD: case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD: case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD: case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD: case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case ThemeListCommand.COMMAND_WORD:
            return new ThemeListCommand();

        case SwitchThemeCommand.COMMAND_WORD: case SwitchThemeCommand.COMMAND_ALIAS:
            return new SwitchThemeCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD: case AddEventCommand.COMMAND_ALIAS:
            if (!personListActivated) {
                return new AddEventCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_EVENT_PLATFORM);
            }
        case DeleteEventCommand.COMMAND_WORD: case DeleteEventCommand.COMMAND_ALIAS:
            if (!personListActivated) {
                return new DeleteEventCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_EVENT_PLATFORM);
            }
        case EventsCommand.COMMAND_WORD:
            personListActivated = false;
            return new EventsCommand();

        case FavouriteCommand.COMMAND_WORD: case FavouriteCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new FavouriteCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case UnfavouriteCommand.COMMAND_WORD: case UnfavouriteCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new UnfavouriteCommandParser().parse(arguments);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case FavouriteListCommand.COMMAND_WORD:
            if (personListActivated) {
                return new FavouriteListCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        case BirthdaysCommand.COMMAND_WORD: case BirthdaysCommand.COMMAND_ALIAS:
            if (personListActivated) {
                return new BirthdaysCommand();
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_PLATFORM);
            }
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
```
###### /java/seedu/address/logic/parser/LocationCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AccessCommand object
 */
public class LocationCommandParser implements Parser<LocationCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LocationCommand
     * and returns an LocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public LocationCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LocationCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
        }

    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /** Ensures that every person in the AddressBook
     *  is sorted in an alphabetical order.
     */
    public void sort() {
        persons.sort();
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void sort() {
        addressBook.sort();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

```
###### /java/seedu/address/model/person/Address.java
``` java
    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address) throws IllegalValueException {
        if (address == null) {
            this.value = ADDRESS_TEMPORARY;
        } else {
            if (!isValidAddress(address)) {
                throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
            }
            this.value = address;
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX)
                || test.matches(ADDRESS_TEMPORARY);
    }

```
###### /java/seedu/address/model/person/HomeNumber.java
``` java
    /**
     * Validates given home number.
     *
     * @throws IllegalValueException if given home string is invalid.
     */
    public HomeNumber(String homeNumber) throws IllegalValueException {
        if (homeNumber == null) {
            this.value = HOME_NUMBER_TEMPORARY;
        } else {
            String trimmedHomeNumber = homeNumber.trim();
            if (!isValidHomeNumber(trimmedHomeNumber)) {
                throw new IllegalValueException(MESSAGE_HOME_NUMBER_CONSTRAINTS);
            }
            this.value = trimmedHomeNumber;
        }
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidHomeNumber(String test) {
        return test.matches(HOME_NUMBER_VALIDATION_REGEX)
                || test.matches(HOME_NUMBER_TEMPORARY);
    }

```
###### /java/seedu/address/model/person/Name.java
``` java
    /**
     * This method was adapted from user, scottb, on StackOverFlow.
     * Code Implementation: https://stackoverflow.com/a/15738441
     * This method converts a name to become capitalized fully.
     * e.g. from "dArrEn cHiN" to "Darren Chin"
     */
    public static String toCapitalized(String s) {

        final String delimiters = " ";
        StringBuilder newString = new StringBuilder();
        boolean isCapital = true;

        for (char c : s.toCharArray()) {
            c = (isCapital) ? Character.toUpperCase(c) : Character.toLowerCase(c);
            newString.append(c);

            isCapital = (delimiters.indexOf((int) c) >= 0);
        }
        return newString.toString();
    }
```
###### /java/seedu/address/model/person/Person.java
``` java

    public void setWebsite(Website website) {
        this.website.set(requireNonNull(website));
    }
```
###### /java/seedu/address/model/person/SchEmail.java
``` java
    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public SchEmail(String schEmail) throws IllegalValueException {
        if (schEmail == null) {
            this.value = SCH_EMAIL_TEMPORARY;
        } else {
            String trimmedSchEmail = schEmail.trim();
            if (!isValidSchEmail(trimmedSchEmail)) {
                throw new IllegalValueException(MESSAGE_SCH_EMAIL_CONSTRAINTS);
            }
            this.value = trimmedSchEmail;
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidSchEmail(String test) {
        return test.matches(SCH_EMAIL_VALIDATION_REGEX)
                || test.matches(SCH_EMAIL_TEMPORARY);
    }

```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts every person in the list alphabetically.
     */
    public void sort() {
        internalList.sort((r1, r2) -> (
                r1.getName().toString().compareTo(r2.getName().toString())));
    }

```
###### /java/seedu/address/model/person/Website.java
``` java
/**
 * Represents a Person's website information in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */

public class Website {
    public static final String MESSAGE_WEBSITE_CONSTRAINTS =
            "Website inputted should follow format https://www.anyName.com/anyContent"
                    + " where both anyName and anyContent can be alphanumeric."
                    + " You must have https://www. and a domain name like .com";
    public static final String WEBSITE_TEMPORARY = "NIL";

    public final String value;

    /**
     * Validates given website.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Website(String websiteName) throws IllegalValueException {
        if (websiteName == null) {
            this.value =  WEBSITE_TEMPORARY;
        } else {
            String trimmedWebsite = websiteName.trim();

            if (!isValidWebsite(trimmedWebsite)) {
                throw new IllegalValueException(MESSAGE_WEBSITE_CONSTRAINTS);
            }

            this.value = trimmedWebsite;
        }
    }

    /**
     * Returns if a given string is a valid person website.
     */
    public static boolean isValidWebsite(String test) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        return urlValidator.isValid(test) || test.matches(WEBSITE_TEMPORARY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Website // instanceof handles nulls
                && this.value.equals(((Website) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /**
     * Access website through browser panel based on person's link
     * @param website
     */
    public void handleWebsiteAccess(String website) {
        loadPage(website);
    }

    private void loadPersonLocation(String location) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + location.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);
    }

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handleAccessWebsiteEvent(AccessWebsiteRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleWebsiteAccess(event.website);
    }

    @Subscribe
    private void handleAccessLocationEvent(AccessLocationRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonLocation(event.location);
    }
}
```
###### /java/seedu/address/ui/EventsDetailsPanel.java
``` java
/**
 * The Details Panel of the App that displays full information of a {@code Event}.
 */
public class EventsDetailsPanel extends UiPart<Region> {

    private static final String FXML = "EventsDetailsPanel.fxml";
    private static final String PREFIX_ADDRESS_FIELD = "Address: ";
    private static final String PREFIX_DATE_FIELD = "Date: ";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Logic logic;


    @FXML
    private Pane pane;
    @FXML
    private Label name;
    @FXML
    private Text addressField;
    @FXML
    private Label address;
    @FXML
    private Text dateField;
    @FXML
    private Label date;

    public EventsDetailsPanel() {
        super(FXML);
        this.logic = logic;
        loadBlankPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the full info of the Event
     * @param event the selected event to display the full info of.
     */
    public void loadEventInfo(ReadOnlyEvent event) {
        addressField.setText(PREFIX_ADDRESS_FIELD);
        dateField.setText(PREFIX_DATE_FIELD);
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        name.textProperty().bind(Bindings.convert(event.nameProperty()));
        address.textProperty().bind(Bindings.convert(event.addressProperty()));
        date.textProperty().bind(Bindings.convert(event.dateProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventsDetailsPanel)) {
            return false;
        }

        EventsDetailsPanel eventsDetailsPanel = (EventsDetailsPanel) other;
        return name.getText().equals(eventsDetailsPanel.name.getText())
                && address.getText().equals(eventsDetailsPanel.address.getText())
                && date.getText().equals(eventsDetailsPanel.date.getText());
    }

    /**
     * Sets all info fields to not display anything when the app is just started.
     */
    public void loadBlankPage() {
        Label label;
        Text text;
        for (Node node: pane.getChildren()) {
            if (node instanceof Label) {
                label = (Label) node;
                label.setText("");
            } else if (node instanceof Text) {
                text = (Text) node;
                text.setText("");
            } else if (node instanceof TextFlow) {
                for (Node subNode: ((TextFlow) node).getChildren()) {
                    if (subNode instanceof Text) {
                        text = (Text) subNode;
                        text.setText("");
                    }
                    if (subNode instanceof Label) {
                        label = (Label) subNode;
                        label.setText("");
                    }
                }
            }
        }
    }

    @Subscribe
    private void handleEventPanelSelectionChangedEvent(EventPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadEventInfo(event.getNewSelection().event);
    }

    @Subscribe
    private void handleUnselectOfEventCardEvent(EventPanelUnselectEvent event) {
        unregisterAsAnEventHandler(this);
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleToggleEvent(TogglePanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleToggle(event.selectedPanel);
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleUnselectOfEventCardEvent(EventPanelUnselectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventsDetailsPanel = new EventsDetailsPanel();
        eventsDetailsPanelPlaceholder.getChildren().clear();
        eventsDetailsPanelPlaceholder.getChildren().add(eventsDetailsPanel.getRoot());
    }
```
###### /resources/view/EventsDetailsPanel.fxml
``` fxml

<StackPane fx:id="eventsDetailsPanel" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <VBox fx:id="pane" prefHeight="600.0" prefWidth="800.0">
        <children>
            <Label fx:id="name" alignment="TOP_LEFT" prefHeight="50.0" prefWidth="360.0" styleClass="window_big_label" text="\$name" wrapText="true">
            <VBox.margin>
               <Insets left="10.0" top="10.0" />
            </VBox.margin></Label>
            <TextFlow prefHeight="50.0" prefWidth="400.0">
                <children>
                    <Text fx:id="dateField" styleClass="window_small_text" text="Date:" />
                    <Label fx:id="date" styleClass="window_small_label" text="\$date" />
                </children>
            <padding>
               <Insets left="10.0" />
            </padding>
            </TextFlow>
            <TextFlow prefHeight="50.0" prefWidth="400.0">
                <children>
                    <Text fx:id="addressField" styleClass="window_small_text" text="Address: " />
                    <Label fx:id="address" alignment="TOP_LEFT" maxHeight="800.0" styleClass="window_small_label" text="\$address" wrapText="true" />
                </children>
            <padding>
               <Insets left="10.0" />
            </padding>
            </TextFlow>
        </children>
    </VBox>
</StackPane>
```
