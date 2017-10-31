# dalessr
###### \java\seedu\address\commons\events\ui\BrowserPanelFindRouteEvent.java
``` java
/**
 * Represents a display of route from start location to end location in the Browser Panel
 */
public class BrowserPanelFindRouteEvent extends BaseEvent {

    private final ReadOnlyPerson person;
    private final String address;

    public BrowserPanelFindRouteEvent(ReadOnlyPerson person, String address) {
        this.person = person;
        this.address = address;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getSelectedPerson() {
        return person;
    }

    public String getAddress() {
        return address;
    }
}
```
###### \java\seedu\address\commons\events\ui\BrowserPanelShowLocationEvent.java
``` java
/**
 * Represents a display of location in the Browser Panel
 */
public class BrowserPanelShowLocationEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public BrowserPanelShowLocationEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getNewSelection() {
        return person;
    }
}
```
###### \java\seedu\address\logic\commands\BirthdayAddCommand.java
``` java
/**
 * Add a birthday to an existing person in the address book.
 */
public class BirthdayAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "b-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add birthday to the person selected "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "BIRTHDAY[DD/MM/YYYY] (BIRTHDAY Should be in valid format).\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "18/10/1994";

    public static final String MESSAGE_ADD_BIRTHDAY_SUCCESS = "Added Birthday to Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private final Birthday birthdayToAdd;

    public BirthdayAddCommand(Index index, Birthday birthday) {
        targetIndex = index;
        birthdayToAdd = birthday;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson currentPerson = lastShownList.get(targetIndex.getZeroBased());
        Person personToEdit = (Person) lastShownList.get(targetIndex.getZeroBased());
        personToEdit.setBirthday(birthdayToAdd);

        try {
            model.updatePerson(currentPerson, personToEdit);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_ADD_BIRTHDAY_SUCCESS, personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayAddCommand // instanceof handles nulls
                && this.targetIndex.equals(((BirthdayAddCommand) other).targetIndex))
                && (other instanceof BirthdayAddCommand
                && this.birthdayToAdd.equals(((BirthdayAddCommand) other).birthdayToAdd)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        String[] parameters = (String[]) predicate.getKeywords().toArray();
        //Create list to store the keywords of different keywords
        ArrayList<String> nameKeywords = new ArrayList<>();
        ArrayList<String> phoneKeywords = new ArrayList<>();
        ArrayList<String> emailKeywords = new ArrayList<>();
        ArrayList<String> addressKeywords = new ArrayList<>();
        ArrayList<String> currentKeywords = nameKeywords;
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].equals(PREFIX_NAME.getPrefix())) {
                currentKeywords = nameKeywords;
            } else if (parameters[i].equals(PREFIX_PHONE.getPrefix())) {
                currentKeywords = phoneKeywords;
            } else if (parameters[i].equals(PREFIX_EMAIL.getPrefix())) {
                currentKeywords = emailKeywords;
            } else if (parameters[i].equals(PREFIX_ADDRESS.getPrefix())) {
                currentKeywords = addressKeywords;
            } else if (!parameters[i].equals("")) {
                currentKeywords.add(parameters[i]);
            }
        }
        //Go through each keywords list and get the names to search in the storage
        List<String> namesToSearch = new ArrayList<>();
        if (nameKeywords.size() != 0) {
            ArrayList<String> namesMatched = getNamesFromNameKeywords(nameKeywords);
            if (namesMatched != null) {
                namesToSearch.addAll(namesMatched);
            }
        }
        if (phoneKeywords.size() != 0) {
            ArrayList<String> namesMatched = getNamesFromPhoneKeywords(phoneKeywords);
            if (namesMatched != null) {
                namesToSearch.addAll(namesMatched);
            }
        }
        if (emailKeywords.size() != 0) {
            ArrayList<String> namesMatched = getNamesFromEmailKeywords(emailKeywords);
            if (namesMatched != null) {
                namesToSearch.addAll(namesMatched);
            }
        }
        if (addressKeywords.size() != 0) {
            ArrayList<String> namesMatched = getNamesFromAddressKeywords(addressKeywords);
            if (namesMatched != null) {
                namesToSearch.addAll(namesMatched);
            }
        }

        NameContainsKeywordsPredicate updatedPredicate = new NameContainsKeywordsPredicate(namesToSearch);
        model.updateFilteredPersonList(updatedPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    /**
     * Get a list of names whose contact details contain all the address keywords provided by user.
     * @param addressKeywords a list of address keywords to search for
     * @return a list of names found by searching the corresponding address keywords
     */
    private ArrayList<String> getNamesFromAddressKeywords(ArrayList<String> addressKeywords) {
        ArrayList<String> matchedNames = new ArrayList<>();
        ArrayList<String> addressList = new ArrayList<>();
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            addressList.add(model.getAddressBook().getPersonList().get(i).getAddress().toString().toLowerCase());
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < addressKeywords.size(); i++) {
            stringBuffer.append(addressKeywords.get(i) + " ");
        }
        String addressValue = stringBuffer.toString().toLowerCase().trim();
        for (int i = 0; i < addressList.size(); i++) {
            if (addressList.get(i).contains(addressValue)) {
                matchedNames.add(model.getAddressBook().getPersonList().get(i).getName().toString());
            }
        }
        if (matchedNames.size() == 0) {
            return null;
        } else {
            return matchedNames;
        }
    }

    /**
     * Get a list of names whose contact details contain at least one of the email keywords provided by user.
     * @param emailKeywords a list of email keywords to search for
     * @return a list of names found by searching the corresponding email keywords
     */
    private ArrayList<String> getNamesFromEmailKeywords(ArrayList<String> emailKeywords) {
        ArrayList<String> matchedNames = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            emailList.add(model.getAddressBook().getPersonList().get(i).getEmail().toString().toLowerCase());
        }
        for (int i = 0; i < emailKeywords.size(); i++) {
            for (int j = 0; j < emailList.size(); j++) {
                if (emailList.get(j).contains(emailKeywords.get(i).toLowerCase())) {
                    matchedNames.add(model.getAddressBook().getPersonList().get(j).getName().toString());
                }
            }
        }
        if (matchedNames.size() == 0) {
            return null;
        } else {
            return matchedNames;
        }
    }

    /**
     * Get a list of names whose contact details contain at least one of the phone keywords provided by user.
     * @param phoneKeywords a list of phone keywords to search for
     * @return a list of names found by searching the corresponding phone keywords
     */
    private ArrayList<String> getNamesFromPhoneKeywords(ArrayList<String> phoneKeywords) {
        ArrayList<String> matchedNames = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            phoneList.add(model.getAddressBook().getPersonList().get(i).getPhone().toString());
        }
        for (int i = 0; i < phoneKeywords.size(); i++) {
            for (int j = 0; j < phoneList.size(); j++) {
                if (phoneList.get(j).contains(phoneKeywords.get(i))) {
                    matchedNames.add(model.getAddressBook().getPersonList().get(j).getName().toString());
                }
            }
        }
        if (matchedNames.size() == 0) {
            return null;
        } else {
            return matchedNames;
        }
    }

    /**
     * Get a list of names whose contact details contain at least one of the name keywords provided by user.
     * @param nameKeywords a list of name keywords to search for
     * @return a list of names found by searching the corresponding name keywords
     */
    private ArrayList<String> getNamesFromNameKeywords(ArrayList<String> nameKeywords) {
        ArrayList<String> matchedNames = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            nameList.add(model.getAddressBook().getPersonList().get(i).getName().toString().toLowerCase());
        }
        for (int i = 0; i < nameKeywords.size(); i++) {
            for (int j = 0; j < nameList.size(); j++) {
                if (nameList.get(j).contains(nameKeywords.get(i).toLowerCase())) {
                    matchedNames.add(nameList.get(j));
                }
            }
        }
        if (matchedNames.size() == 0) {
            return null;
        } else {
            return matchedNames;
        }
    }

```
###### \java\seedu\address\logic\commands\MapRouteCommand.java
``` java
/**
 * Shows the route from the entered location to the selected person's address on Google map.
 */
public class MapRouteCommand extends Command {

    public static final String COMMAND_WORD = "m-route";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the route from the location provided to the selected person's address on Google map.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ADDRESS + "ADDRESS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADDRESS + "Blk 30 Clementi Street 29";

    public static final String MESSAGE_FIND_ROUTE_SUCCESS = "Found Route to Person: %1$s";

    private final Index targetIndex;
    private final String startLocation;

    public MapRouteCommand(Index targetIndex, String startLocation) {
        this.targetIndex = targetIndex;
        this.startLocation = startLocation;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new BrowserPanelFindRouteEvent(
                model.getFilteredPersonList().get(targetIndex.getZeroBased()), startLocation));
        return new CommandResult(String.format(MESSAGE_FIND_ROUTE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapRouteCommand // instanceof handles nulls
                && this.targetIndex.equals(((MapRouteCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\MapShowCommand.java
``` java
/**
 * Shows the location of a person on Google map identified using it's last displayed index from the address book.
 */
public class MapShowCommand extends Command {

    public static final String COMMAND_WORD = "m-show";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the location of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Located Person: %1$s";

    private final Index targetIndex;

    public MapShowCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new BrowserPanelShowLocationEvent(
                model.getFilteredPersonList().get(targetIndex.getZeroBased())));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapShowCommand // instanceof handles nulls
                && this.targetIndex.equals(((MapShowCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\BirthdayAddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BirthdayAddCommand object
 */
public class BirthdayAddCommandParser implements Parser<BirthdayAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayAddCommand
     * and returns an BirthdayAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String[] parameters = trimmedArgs.split(" ");
        if (parameters.length <= 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayAddCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(parameters[0]);
            Birthday birthday = new Birthday(parameters[parameters.length - 1]);
            return new BirthdayAddCommand(index, birthday);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayAddCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        //Throw an error if there is no argument followed by the command word
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        //Get the index range of different keywords (distinguished by attributes) from trimmedArgs
        int indexOfName = trimmedArgs.indexOf(PREFIX_NAME.getPrefix());
        int indexOfPhone = trimmedArgs.indexOf(PREFIX_PHONE.getPrefix());
        int indexOfEmail = trimmedArgs.indexOf(PREFIX_EMAIL.getPrefix());
        int indexOfAddress = trimmedArgs.indexOf(PREFIX_ADDRESS.getPrefix());
        //Throw an error if there is no prefixes to specify the type of the keywords
        if ((indexOfName == -1) && (indexOfPhone == -1) && (indexOfEmail == -1) && (indexOfAddress == -1)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String symbolAtStart = trimmedArgs.substring(0, 2);
        //Throw an error if there is some dummy values before the first prefix after the command word
        if ((!symbolAtStart.equals(PREFIX_NAME.getPrefix())) && (!symbolAtStart.equals(PREFIX_PHONE.getPrefix()))
                && (!symbolAtStart.equals(PREFIX_EMAIL.getPrefix()))
                && (!symbolAtStart.equals(PREFIX_ADDRESS.getPrefix()))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        int[] attributeIndexArray = {indexOfName, indexOfPhone, indexOfEmail, indexOfAddress, trimmedArgs.length()};
        Arrays.sort(attributeIndexArray);
        //Put different types of keywords into separate strings
        String trimmedNames = null;
        if (indexOfName != -1) {
            int index = 0;
            for (int i = 0; i < attributeIndexArray.length; i++) {
                if (attributeIndexArray[i] == indexOfName) {
                    index = i;
                    break;
                }
            }
            trimmedNames = trimmedArgs.substring(indexOfName + 2, attributeIndexArray[index + 1]).trim();
            //Throw an error if there is no keyword provided for the specified type of attribute.
            //Same below.
            if (trimmedNames.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        String trimmedPhones = null;
        if (indexOfPhone != -1) {
            int index = 0;
            for (int i = 0; i < attributeIndexArray.length; i++) {
                if (attributeIndexArray[i] == indexOfPhone) {
                    index = i;
                    break;
                }
            }
            trimmedPhones = trimmedArgs.substring(indexOfPhone + 2, attributeIndexArray[index + 1]).trim();
            if (trimmedPhones.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        String trimmedEmails = null;
        if (indexOfEmail != -1) {
            int index = 0;
            for (int i = 0; i < attributeIndexArray.length; i++) {
                if (attributeIndexArray[i] == indexOfEmail) {
                    index = i;
                    break;
                }
            }
            trimmedEmails = trimmedArgs.substring(indexOfEmail + 2, attributeIndexArray[index + 1]).trim();
            if (trimmedEmails.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        String trimmedAddress = null;
        if (indexOfAddress != -1) {
            int index = 0;
            for (int i = 0; i < attributeIndexArray.length; i++) {
                if (attributeIndexArray[i] == indexOfAddress) {
                    index = i;
                    break;
                }
            }
            trimmedAddress = trimmedArgs.substring(indexOfAddress + 2, attributeIndexArray[index + 1]).trim();
            if (trimmedAddress.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }
        //Add all the keywords to a list distinguished by prefixes in order to parse them to FindCommand class
        List<String> keyWordsToSearch = new ArrayList<>();

        if (trimmedNames != null) {
            String[] nameKeywords = trimmedNames.split(" ");
            keyWordsToSearch.add(PREFIX_NAME.getPrefix());
            for (int i = 0; i < nameKeywords.length; i++) {
                keyWordsToSearch.add(nameKeywords[i]);
            }
        }

        if (trimmedPhones != null) {
            String[] phoneKeywords = trimmedPhones.split(" ");
            keyWordsToSearch.add(PREFIX_PHONE.getPrefix());
            for (int i = 0; i < phoneKeywords.length; i++) {
                keyWordsToSearch.add(phoneKeywords[i]);
            }
        }

        if (trimmedEmails != null) {
            String[] emailKeywords = trimmedEmails.split(" ");
            keyWordsToSearch.add(PREFIX_EMAIL.getPrefix());
            for (int i = 0; i < emailKeywords.length; i++) {
                keyWordsToSearch.add(emailKeywords[i]);
            }
        }

        if (trimmedAddress != null) {
            String[] addressKeywords = trimmedAddress.split(" ");
            keyWordsToSearch.add(PREFIX_ADDRESS.getPrefix());
            for (int i = 0; i < addressKeywords.length; i++) {
                keyWordsToSearch.add(addressKeywords[i]);
            }
        }

        String[] parameters = new String[keyWordsToSearch.size()];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = keyWordsToSearch.get(i);
        }
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList(parameters));
        return new FindCommand(predicate);
    }
}
```
###### \java\seedu\address\logic\parser\MapRouteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MapRouteCommand object
 */
public class MapRouteCommandParser implements Parser<MapRouteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MapRouteCommand
     * and returns an MapRouteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapRouteCommand parse(String args) throws ParseException {
        try {
            if (!args.contains(PREFIX_ADDRESS.getPrefix())) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
            }
            String[] argsArray = args.trim().split(PREFIX_ADDRESS.getPrefix());
            if (argsArray.length <= 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
            }
            Index index = ParserUtil.parseIndex(argsArray[0].trim());
            return new MapRouteCommand(index, argsArray[1].trim());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapRouteCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\MapShowCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MapShowCommand object
 */
public class MapShowCommandParser implements Parser<MapShowCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MapShowCommand
     * and returns an MapShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MapShowCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MapShowCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapShowCommand.MESSAGE_USAGE));
        }
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

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday can only contain numbers and slashes, and it can be blank";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (!isValidBirthday(birthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = birthday;
    }

    public Birthday() {
        this.value = "01/01/1900";
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {

        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int currentYear = now.get(Calendar.YEAR);       // The current year

        String[] date = test.split("/");
        if (date.length != 3) {
            return false;
        }

        for (int i = 0; i < date.length; i++) {
            for (int j = 0; j < date[i].length(); j++) {
                if (date[i].charAt(j) > 57 || date[i].charAt(j) < 48) {
                    return false;
                }
            }
        }

        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        if (day > 0 && day < 32 && month > 0 && month < 13 && year >= 1900 && year <= currentYear) {
            return true;
        }

        return false;
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
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads the google map page on the browser specifying the location of the person selected.
     * @param person the person whose location is to be shown on the map
     */
    public void loadLocationPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAP_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+")
                + GOOGLE_MAP_SEARCH_URL_SUFFIX);
    }

    /**
     * Loads the google map page on the browser specifying the direction from current location to
     * the selected person's address.
     * @param person whose location is the destination
     * @param address starting location
     */
    public void loadRoutePage(ReadOnlyPerson person, String address) {
        String startLocation = address.trim().replaceAll(" ", "+");
        String endLocation = person.getAddress().toString().trim().replaceAll(" ", "+");
        loadPage(GOOGLE_MAP_DIRECTION_URL_PREFIX + startLocation
                + GOOGLE_MAP_SEARCH_URL_SUFFIX + endLocation
                + GOOGLE_MAP_SEARCH_URL_SUFFIX);
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleBrowserPanelShowLocationEvent(BrowserPanelShowLocationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadLocationPage(event.getNewSelection());
    }

    @Subscribe
    private  void handleBrowserPanelFindRouteEvent(BrowserPanelFindRouteEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadRoutePage(event.getSelectedPerson(), event.getAddress());
    }
}
```
###### \java\seedu\address\ui\PersonDetailsPanel.java
``` java
/**
 * The Contact Details Panel of the App.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    private ObservableList<ReadOnlyPerson> personList;

    private final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);

    @FXML
    private GridPane personDetailsGrid;

    @FXML
    private Label nameLabel;

    @FXML
    private Label birthdayLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label addressLabelContinue;

    @FXML
    private VBox eventsBox;

    @FXML
    private TextArea eventsArea;

    public PersonDetailsPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        this.personList = personList;
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleClearPersonListEvent(ClearPersonListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        nameLabel.setText("Person Name");
        birthdayLabel.setText("");
        phoneLabel.setText("");
        emailLabel.setText("");
        addressLabel.setText("");
        addressLabelContinue.setText("");
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = personList.get(event.targetIndex);
        nameLabel.setText(person.getName().fullName);
        if (person.getBirthday().value.equals("01/01/1900")) {
            birthdayLabel.setText("");
        } else {
            birthdayLabel.setText(person.getBirthday().toString());
        }
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());

        addressLabelContinue.setText("");
        String[] address = person.getAddress().toString().split(" ");
        StringBuffer firstBuffer = new StringBuffer();
        int index = 0;
        while (index < address.length && address[index].length() <= 32 - firstBuffer.length()) {
            firstBuffer.append(address[index]);
            firstBuffer.append(" ");
            index++;
        }
        String firstAddress = firstBuffer.toString();
        addressLabel.setText(firstAddress);
        StringBuffer secondBuffer = new StringBuffer();
        for (; index < address.length; index++) {
            secondBuffer.append(address[index]);
            secondBuffer.append(" ");
        }
        if (secondBuffer.length() != 0) {
            String secondAddress = secondBuffer.toString();
            addressLabelContinue.setText(secondAddress);
        }

        eventsArea.setText("");
        StringBuffer stringBuffer = new StringBuffer();
        int counter = 1;
        for (Event e: person.getEvents()) {
            stringBuffer.append(counter + ". " + e.getEventName().fullName + " -- "
                    + e.getEventTime().toString() + "\n");
            counter++;
        }
        eventsArea.setText(stringBuffer.toString());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyPerson person = event.getNewSelection().person;
        nameLabel.setText(person.getName().fullName);
        if (person.getBirthday().value.equals("01/01/1900")) {
            birthdayLabel.setText("");
        } else {
            birthdayLabel.setText(person.getBirthday().toString());
        }
        phoneLabel.setText(person.getPhone().toString());
        emailLabel.setText(person.getEmail().toString());

        addressLabelContinue.setText("");
        String[] address = person.getAddress().toString().split(" ");
        StringBuffer firstBuffer = new StringBuffer();
        int index = 0;
        while (index < address.length && address[index].length() <= 32 - firstBuffer.length()) {
            firstBuffer.append(address[index]);
            firstBuffer.append(" ");
            index++;
        }
        String firstAddress = firstBuffer.toString();
        addressLabel.setText(firstAddress);
        StringBuffer secondBuffer = new StringBuffer();
        for (; index < address.length; index++) {
            secondBuffer.append(address[index]);
            secondBuffer.append(" ");
        }
        if (secondBuffer.length() != 0) {
            String secondAddress = secondBuffer.toString();
            addressLabelContinue.setText(secondAddress);
        }

        eventsArea.setText("");
        StringBuffer stringBuffer = new StringBuffer();
        int counter = 1;
        for (Event e: person.getEvents()) {
            stringBuffer.append(counter + ". " + e.getEventName().fullName + " -- "
                    + e.getEventTime().toString() + "\n");
            counter++;
        }
        eventsArea.setText(stringBuffer.toString());
    }
}
```
###### \resources\view\PersonDetailsPanel.fxml
``` fxml

<StackPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane fx:id="personDetailsGrid" style="-fx-background-image: url(images/wallpaper1.png);">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0">
               <children>
                  <ImageView fitHeight="150.0" fitWidth="200.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="images/profile.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="nameLabel" alignment="BOTTOM_CENTER" contentDisplay="CENTER" style="-fx-font-size: 36px; -fx-font-weight: bold;" text="Person Name" textAlignment="CENTER" underline="true" wrapText="true">
                     <VBox.margin>
                        <Insets top="10.0" />
                     </VBox.margin>
                  </Label>
               </children>
            </VBox>
            <VBox prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="1">
               <children>
                  <HBox prefHeight="50.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 20;" text="Birthday:" />
                        <Label fx:id="birthdayLabel" style="-fx-font-size: 20;">
                           <HBox.margin>
                              <Insets left="5.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                     <VBox.margin>
                        <Insets top="45.0" />
                     </VBox.margin>
                  </HBox>
                  <HBox prefHeight="50.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 20;" text="Phone:" />
                        <Label fx:id="phoneLabel" style="-fx-font-size: 20;">
                           <HBox.margin>
                              <Insets left="5.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                  </HBox>
                  <HBox prefHeight="50.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 20;" text="Email:" />
                        <Label fx:id="emailLabel" style="-fx-font-size: 20;">
                           <HBox.margin>
                              <Insets left="5.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                  </HBox>
                  <HBox prefHeight="30.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 19;" text="Address:" />
                        <Label fx:id="addressLabel" style="-fx-font-size: 20;">
                           <HBox.margin>
                              <Insets left="5.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                  </HBox>
                  <HBox prefHeight="40.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 19;" text="Address:" visible="false" />
                        <Label fx:id="addressLabelContinue" style="-fx-font-size: 20;">
                           <HBox.margin>
                              <Insets left="5.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                  </HBox>
               </children>
               <GridPane.margin>
                  <Insets left="10.0" />
               </GridPane.margin>
            </VBox>
            <GridPane GridPane.columnIndex="1">
              <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                  <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
              </columnConstraints>
              <rowConstraints>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
              </rowConstraints>
               <children>
                  <ImageView fx:id="twitterIcon" fitHeight="75.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true" GridPane.halignment="RIGHT" GridPane.valignment="BOTTOM">
                     <image>
                        <Image url="images/twitter.png" />
                     </image>
                     <GridPane.margin>
                        <Insets right="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <ImageView fx:id="nusmodsIcon" fitHeight="75.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="2" GridPane.halignment="LEFT" GridPane.valignment="BOTTOM">
                     <image>
                        <Image url="images/nusmods.png" />
                     </image>
                     <GridPane.margin>
                        <Insets left="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <ImageView fx:id="facebookIcon" fitHeight="75.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="1" GridPane.halignment="CENTER" GridPane.rowIndex="1" GridPane.valignment="CENTER">
                     <image>
                        <Image url="images/facebook.png" />
                     </image>
                  </ImageView>
                  <ImageView fx:id="instagramIcon" fitHeight="75.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true" GridPane.halignment="RIGHT" GridPane.rowIndex="2" GridPane.valignment="TOP">
                     <image>
                        <Image url="images/instagram.png" />
                     </image>
                     <GridPane.margin>
                        <Insets right="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <ImageView fx:id="githubIcon" fitHeight="75.0" fitWidth="100.0" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="2" GridPane.halignment="LEFT" GridPane.rowIndex="2" GridPane.valignment="TOP">
                     <image>
                        <Image url="images/github.png" />
                     </image>
                     <GridPane.margin>
                        <Insets left="5.0" />
                     </GridPane.margin>
                  </ImageView>
               </children>
            </GridPane>
            <VBox fx:id="eventsBox" prefHeight="200.0" prefWidth="100.0" GridPane.columnIndex="1" GridPane.halignment="CENTER" GridPane.rowIndex="1" GridPane.valignment="CENTER">
               <children>
                  <Label style="-fx-font-size: 32px;" text="Events: ">
                     <VBox.margin>
                        <Insets left="20.0" />
                     </VBox.margin></Label>
                  <TextArea fx:id="eventsArea" editable="false" opacity="0.5" prefWidth="200.0" style="-fx-font-size: 20px; -fx-font-family: fantasy; -fx-border-color: #003366; -fx-border-width: 8;" />
               </children>
            </VBox>
         </children>
      </GridPane>
   </children>
</StackPane>
```
