# dalessr
###### /java/seedu/address/commons/events/ui/BrowserPanelFindRouteEvent.java
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
###### /java/seedu/address/commons/events/ui/BrowserPanelShowLocationEvent.java
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
###### /java/seedu/address/commons/events/ui/ClearPersonListEvent.java
``` java
/**
 * Indicates a request to clear the person list
 */
public class ClearPersonListEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/OpenFaceBookWebViewEvent.java
``` java
/**
 * Indicates a request for opening facebook webview
 */
public class OpenFaceBookWebViewEvent extends BaseEvent {

    public OpenFaceBookWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/OpenGithubWebViewEvent.java
``` java
/**
 * Indicates a request for opening github webview
 */
public class OpenGithubWebViewEvent extends BaseEvent {

    public OpenGithubWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/OpenInstagramWebViewEvent.java
``` java
/**
 * Indicates a request for opening instagram webview
 */
public class OpenInstagramWebViewEvent extends BaseEvent {

    public OpenInstagramWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/OpenNusModsWebViewEvent.java
``` java
/**
 * Indicates a request for opening nusmods webview
 */
public class OpenNusModsWebViewEvent extends BaseEvent {

    public OpenNusModsWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/OpenTwitterWebViewEvent.java
``` java
/**
 * Indicates a request for opening twitter webview
 */
public class OpenTwitterWebViewEvent extends BaseEvent {

    public OpenTwitterWebViewEvent() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/commands/BirthdayAddCommand.java
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
###### /java/seedu/address/logic/commands/BirthdayRemoveCommand.java
``` java
/**
 * Remove a birthday from an existing person in the address book.
 */
public class BirthdayRemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "b-remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove birthday from the person selected "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_BIRTHDAY_SUCCESS = "Removed Birthday from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    public BirthdayRemoveCommand (Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson currentPerson = lastShownList.get(targetIndex.getZeroBased());
        Person personToEdit = (Person) lastShownList.get(targetIndex.getZeroBased());
        Birthday birthdayToAdd = new Birthday();
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
        return new CommandResult(String.format(MESSAGE_REMOVE_BIRTHDAY_SUCCESS, personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayRemoveCommand // instanceof handles nulls
                && this.targetIndex.equals(((BirthdayRemoveCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/FindCommand.java
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
        Index defaultIndex = new Index(0);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    /**
     * Get a list of names whose contact details contain all the address keywords provided by user.
     * @param addressKeywords a list of address keywords to search for
     * Returns a list of names found by searching the corresponding address keywords
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
     * Returns a list of names found by searching the corresponding email keywords
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
     * Returns a list of names found by searching the corresponding phone keywords
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
     * Returns a list of names found by searching the corresponding name keywords
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
###### /java/seedu/address/logic/commands/MapRouteCommand.java
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

    public static final String MESSAGE_FIND_ROUTE_SUCCESS = "Found Route to Person: %1$s\n"
            + "Please click on the browser tab below to view the map.";

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
###### /java/seedu/address/logic/commands/MapShowCommand.java
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

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Located Person: %1$s\n"
            + "Please click on the browser tab below to view the map.";

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
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
    private static ArrayList<String> commandNames = new ArrayList<>();

    public AddressBookParser() {
        commandNames.add(AddCommand.COMMAND_WORD);
        commandNames.add(BinclearCommand.COMMAND_WORD);
        commandNames.add(BindeleteCommand.COMMAND_WORD);
        commandNames.add(BinrestoreCommand.COMMAND_WORD);
        commandNames.add(SortCommand.COMMAND_WORD);
        commandNames.add(EditCommand.COMMAND_WORD);
        commandNames.add(TagAddCommand.COMMAND_WORD);
        commandNames.add(TagFindCommand.COMMAND_WORD);
        commandNames.add(TagRemoveCommand.COMMAND_WORD);
        commandNames.add(BirthdayAddCommand.COMMAND_WORD);
        commandNames.add(BirthdayRemoveCommand.COMMAND_WORD);
        commandNames.add(SelectCommand.COMMAND_WORD);
        commandNames.add(SwitchThemeCommand.COMMAND_WORD);
        commandNames.add(MapShowCommand.COMMAND_WORD);
        commandNames.add(MapRouteCommand.COMMAND_WORD);
        commandNames.add(DeleteCommand.COMMAND_WORD);
        commandNames.add(ClearCommand.COMMAND_WORD);
        commandNames.add(FindCommand.COMMAND_WORD);
        commandNames.add(ListCommand.COMMAND_WORD);
        commandNames.add(HistoryCommand.COMMAND_WORD);
        commandNames.add(ExitCommand.COMMAND_WORD);
        commandNames.add(HelpCommand.COMMAND_WORD);
        commandNames.add(ScheduleAddCommand.COMMAND_WORD);
        commandNames.add(ScheduleRemoveCommand.COMMAND_WORD);
        commandNames.add(UndoCommand.COMMAND_WORD);
        commandNames.add(RedoCommand.COMMAND_WORD);
        commandNames.add(ExportCommand.COMMAND_WORD);
    }

```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
    public static ArrayList<String> getCommandNames() {
        return commandNames;
    }

}
```
###### /java/seedu/address/logic/parser/BirthdayAddCommandParser.java
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
###### /java/seedu/address/logic/parser/BirthdayRemoveCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BirthdayRemoveCommand object
 */
public class BirthdayRemoveCommandParser implements Parser<BirthdayRemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayRemoveCommand
     * and returns an BirthdayRemoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayRemoveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new BirthdayRemoveCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayRemoveCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/FindCommandParser.java
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
###### /java/seedu/address/logic/parser/MapRouteCommandParser.java
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
###### /java/seedu/address/logic/parser/MapShowCommandParser.java
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
###### /java/seedu/address/model/person/Birthday.java
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

        String dateFormat = "dd/MM/yyyy";
        try {
            DateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false);
            df.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
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
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
        try {
            Person[] persons =  new Person[] {
                new Person(new Name("Alex Yeoh"),
                        new Birthday("30/09/2000"),
                        new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends", "classmates"),
                        new HashSet<>(),
                        new DateAdded("01/01/2016 11:11:53")),

                new Person(new Name("Bernice Yu"),
                        new Birthday("27/02/1983"),
                        new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends"),
                        new HashSet<>(),
                        new DateAdded("07/02/2016 12:00:01")),

                new Person(new Name("Charlotte Oliveiro"),
                        new Birthday("03/12/1992"),
                        new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours"),
                        new HashSet<>(),
                        new DateAdded("01/05/2016 12:00:01")),

                new Person(new Name("David Li"),
                        new Birthday("30/05/1976"),
                        new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("15/09/2016 12:00:01")),

                new Person(new Name("Irfan Ibrahim"),
                        new Birthday("18/11/1960"),
                        new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("profs"),
                        new HashSet<>(),
                        new DateAdded("15/09/2016 12:01:01")),

                new Person(new Name("Roy Balakrishnan"),
                        new Birthday("25/08/1996"),
                        new Phone("92624417"),
                        new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"),
                        new HashSet<>(),
                        new DateAdded("20/09/2016 12:00:01")),

                new Person(new Name("Quintin Levell"),
                        new Birthday("04/09/1997"),
                        new Phone("66012135"),
                        new Email("levellq@example.com"),
                        new Address("Blk 39 Aljunied Street 18, #03-44"),
                        getTagSet("friends", "cs2103"),
                        new HashSet<>(),
                        new DateAdded("01/10/2016 11:11:53")),

                new Person(new Name("Gino Trost"),
                        new Birthday("27/05/1987"),
                        new Phone("85154314"),
                        new Email("trostg@example.com"),
                        new Address("Blk 40 Boon Lay Street 87, #03-12"),
                        getTagSet("colleagues", "friends"),
                        new HashSet<>(),
                        new DateAdded("07/10/2016 12:00:01")),

                new Person(new Name("Honey Digiacomo"),
                        new Birthday("14/12/1994"),
                        new Phone("98500414"),
                        new Email("digiahoney@example.com"),
                        new Address("Blk 166 Ang Mo Kio Vista, #03-28"),
                        getTagSet("neighbours"),
                        new HashSet<>(),
                        new DateAdded("01/12/2016 12:00:01")),

                new Person(new Name("Fred Greenland"),
                        new Birthday("27/05/1982"),
                        new Phone("62667227"),
                        new Email("greenfd@example.com"),
                        new Address("Blk 395 Woodlands Street 10, #15-18"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("15/12/2016 12:00:01")),

                new Person(new Name("Francina Schepers"),
                        new Birthday("07/08/1997"),
                        new Phone("62667887"),
                        new Email("francina@example.com"),
                        new Address("Blk 477 Jurong East Street 28, #01-33"),
                        getTagSet("classmates", "cs2103"),
                        new HashSet<>(),
                        new DateAdded("01/02/2017 12:01:01")),

                new Person(new Name("Ima Mauffray"),
                        new Birthday("17/10/1969"),
                        new Phone("86159036"),
                        new Email("imamau@example.com"),
                        new Address("Blk 395 Lorong 6 Orchard Road, #04-36"),
                        getTagSet("profs"),
                        new HashSet<>(),
                        new DateAdded("20/02/2017 12:00:01")),

                new Person(new Name("Han Slankard"),
                        new Birthday("18/10/1970"),
                        new Phone("82034763"),
                        new Email("hans@example.com"),
                        new Address("Blk 322 Lorong 2 Paya Lebar, #15-21"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("01/05/2017 11:11:53")),

                new Person(new Name("Audry Sustaita"),
                        new Birthday("25/10/1983"),
                        new Phone("72394273"),
                        new Email("audrys@example.com"),
                        new Address("Blk 20 Jurong West Street 82, #10-02"),
                        getTagSet("colleagues", "friends"),
                        new HashSet<>(),
                        new DateAdded("07/05/2017 12:00:01")),

                new Person(new Name("Jade Dimas"),
                        new Birthday("28/01/1976"),
                        new Phone("99282463"),
                        new Email("dimasj@example.com"),
                        new Address("Blk 142 Geylang East Street 86, #18-33"),
                        getTagSet("tutors"),
                        new HashSet<>(),
                        new DateAdded("01/08/2017 12:00:01")),

                new Person(new Name("Lory Prosper"),
                        new Birthday("05/05/1978"),
                        new Phone("65029401"),
                        new Email("lory123@example.com"),
                        new Address("Blk 145 Shunfu Point, #11-05"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("15/09/2017 12:00:01")),

                new Person(new Name("Leone Tipps"),
                        new Birthday("08/12/1989"),
                        new Phone("82058265"),
                        new Email("tippsl@example.com"),
                        new Address("Blk 39 Ang Mo Kio Street 86, #07-22"),
                        getTagSet("classmates"),
                        new HashSet<>(),
                        new DateAdded("28/09/2017 12:01:01")),

                new Person(new Name("Rusty Lucena"),
                        new Birthday("12/06/1973"),
                        new Phone("93376847"),
                        new Email("lucenar@example.com"),
                        new Address("Blk 345 Geylang East Street 27, #15-03"),
                        getTagSet("tutors"),
                        new HashSet<>(),
                        new DateAdded("01/10/2017 12:00:01")),

                new Person(new Name("Rhea Vallo"),
                        new Birthday("18/09/1999"),
                        new Phone("65028849"),
                        new Email("vallor@example.com"),
                        new Address("Blk 12 Lorong 14 Marine Parade, #05-25"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("01/10/2017 11:11:53")),

                new Person(new Name("Stanford Blakemore"),
                        new Birthday("15/01/1983"),
                        new Phone("52170156"),
                        new Email("stanford@example.com"),
                        new Address("Blk 397 Serangoon Gardens Street 24, #03-26"),
                        getTagSet("colleagues", "friends"),
                        new HashSet<>(),
                        new DateAdded("10/10/2017 12:00:01")),

                new Person(new Name("Lori Bancroft"),
                        new Birthday("21/03/1992"),
                        new Phone("88305967"),
                        new Email("lorib@example.com"),
                        new Address("Blk 453 Jurong East Street 25, #10-13"),
                        getTagSet("neighbours"),
                        new HashSet<>(),
                        new DateAdded("15/10/2017 12:00:01")),

                new Person(new Name("Hollis Biles"),
                        new Birthday("29/07/1988"),
                        new Phone("92264773"),
                        new Email("bilesh@example.com"),
                        new Address("Blk 48 Lorong 10 Caldecott, #07-02"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("29/10/2017 12:00:01")),

                new Person(new Name("Linn Mcewen"),
                        new Birthday("10/10/1966"),
                        new Phone("93045568"),
                        new Email("mcewenlinn@example.com"),
                        new Address("Blk 17 Boon Lay Street 29, #13-32"),
                        getTagSet("profs"),
                        new HashSet<>(),
                        new DateAdded("01/11/2017 12:01:01")),

                new Person(new Name("Melvin Sigmund"),
                        new Birthday("02/09/1995"),
                        new Phone("66083995"),
                        new Email("melvins@example.com"),
                        new Address("Blk 29 Aljunied Street 76, #09-25"),
                        getTagSet("colleagues"),
                        new HashSet<>(),
                        new DateAdded("05/11/2017 12:00:01"))
            };

            ArrayList<ReadOnlyPerson> eventPersonOne = new ArrayList<>();
            eventPersonOne.add(persons[0]);
            eventPersonOne.add(persons[10]);
            eventPersonOne.add(persons[16]);
            EventName eventNameOne = new EventName("CS2103T Exam");
            EventTime eventTimeOne = new EventTime(LocalDateTime.of(LocalDate.of(2017, 12, 04),
                    LocalTime.of(10, 00, 00, 00)), Duration.ofMinutes(120));
            EventDuration eventDurationOne = new EventDuration(Duration.ofMinutes(120));
            Event eventOne = new Event(new MemberList(eventPersonOne), eventNameOne, eventTimeOne, eventDurationOne);

            ArrayList<ReadOnlyPerson> eventPersonTwo = new ArrayList<>();
            eventPersonTwo.add(persons[1]);
            eventPersonTwo.add(persons[6]);
            eventPersonTwo.add(persons[7]);
            eventPersonTwo.add(persons[17]);
            EventName eventNameTwo = new EventName("Meeting for Project Demo");
            EventTime eventTimeTwo = new EventTime(LocalDateTime.of(LocalDate.of(2017, 10, 24),
                    LocalTime.of(9, 00, 00, 00)), Duration.ofMinutes(60));
            EventDuration eventDurationTwo = new EventDuration(Duration.ofMinutes(60));
            Event eventTwo = new Event(new MemberList(eventPersonTwo), eventNameTwo, eventTimeTwo, eventDurationTwo);

            ArrayList<ReadOnlyPerson> eventPersonThree = new ArrayList<>();
            eventPersonThree.add(persons[3]);
            eventPersonThree.add(persons[9]);
            eventPersonThree.add(persons[12]);
            eventPersonThree.add(persons[15]);
            eventPersonThree.add(persons[18]);
            eventPersonThree.add(persons[21]);
            EventName eventNameThree = new EventName("Family Gathering and Outing");
            EventTime eventTimeThree = new EventTime(LocalDateTime.of(LocalDate.of(2017, 12, 16),
                    LocalTime.of(17, 00, 00, 00)), Duration.ofMinutes(180));
            EventDuration eventDurationThree = new EventDuration(Duration.ofMinutes(180));
            Event eventThree = new Event(new MemberList(eventPersonThree), eventNameThree, eventTimeThree,
                    eventDurationThree);

            ArrayList<ReadOnlyPerson> eventPersonFour = new ArrayList<>();
            eventPersonFour.add(persons[0]);
            eventPersonFour.add(persons[1]);
            eventPersonFour.add(persons[10]);
            eventPersonFour.add(persons[16]);
            eventPersonFour.add(persons[19]);
            eventPersonFour.add(persons[23]);
            EventName eventNameFour = new EventName("Matchmaking Event");
            EventTime eventTimeFour = new EventTime(LocalDateTime.of(LocalDate.of(2017, 9, 23),
                    LocalTime.of(18, 30, 00, 00)), Duration.ofMinutes(90));
            EventDuration eventDurationFour = new EventDuration(Duration.ofMinutes(90));
            Event eventFour = new Event(new MemberList(eventPersonFour), eventNameFour, eventTimeFour,
                    eventDurationFour);

            HashSet<Event> eventsOne = new HashSet<>();
            eventsOne.add(eventOne);
            eventsOne.add(eventFour);
            persons[0].setEvents(eventsOne);

            HashSet<Event> eventsTwo = new HashSet<>();
            eventsTwo.add(eventTwo);
            eventsTwo.add(eventFour);
            persons[1].setEvents(eventsTwo);

            HashSet<Event> eventsThree = new HashSet<>();
            //eventsThree.add();
            persons[2].setEvents(eventsThree);

            HashSet<Event> eventsFour = new HashSet<>();
            eventsFour.add(eventThree);
            persons[3].setEvents(eventsFour);

            HashSet<Event> eventsFive = new HashSet<>();
            //eventsFive.add();
            persons[4].setEvents(eventsFive);

            HashSet<Event> eventsSix = new HashSet<>();
            //eventsSix.add();
            persons[5].setEvents(eventsSix);

            HashSet<Event> eventsSeven = new HashSet<>();
            eventsSeven.add(eventTwo);
            persons[6].setEvents(eventsSeven);

            HashSet<Event> eventsEight = new HashSet<>();
            eventsEight.add(eventTwo);
            persons[7].setEvents(eventsEight);

            HashSet<Event> eventsNine = new HashSet<>();
            //eventsNine.add();
            persons[8].setEvents(eventsNine);

            HashSet<Event> eventsTen = new HashSet<>();
            eventsTen.add(eventThree);
            persons[9].setEvents(eventsTen);

            HashSet<Event> eventsEleven = new HashSet<>();
            eventsEleven.add(eventOne);
            eventsEleven.add(eventFour);
            persons[10].setEvents(eventsEleven);

            HashSet<Event> eventsTwelve = new HashSet<>();
            //eventsTwelve.add();
            persons[11].setEvents(eventsTwelve);

            HashSet<Event> eventsThirteen = new HashSet<>();
            eventsThirteen.add(eventThree);
            persons[12].setEvents(eventsThirteen);

            HashSet<Event> eventsFourteen = new HashSet<>();
            //eventsFourteen.add();
            persons[13].setEvents(eventsFourteen);

            HashSet<Event> eventsFifteen = new HashSet<>();
            //eventsFifteen.add();
            persons[14].setEvents(eventsFifteen);

            HashSet<Event> eventsSixteen = new HashSet<>();
            eventsSixteen.add(eventThree);
            persons[15].setEvents(eventsSixteen);

            HashSet<Event> eventsSeventeen = new HashSet<>();
            eventsSeventeen.add(eventOne);
            eventsSeventeen.add(eventFour);
            persons[16].setEvents(eventsSeventeen);

            HashSet<Event> eventsEighteen = new HashSet<>();
            eventsEighteen.add(eventTwo);
            persons[17].setEvents(eventsEighteen);

            HashSet<Event> eventsNineteen = new HashSet<>();
            eventsNineteen.add(eventThree);
            persons[18].setEvents(eventsNineteen);

            HashSet<Event> eventsTwenty = new HashSet<>();
            eventsTwenty.add(eventFour);
            persons[19].setEvents(eventsTwenty);

            HashSet<Event> eventsTwoOne = new HashSet<>();
            //eventsTwoOne.add();
            persons[20].setEvents(eventsTwoOne);

            HashSet<Event> eventsTwoTwo = new HashSet<>();
            eventsTwoTwo.add(eventThree);
            persons[21].setEvents(eventsTwoTwo);

            HashSet<Event> eventsTwoThree = new HashSet<>();
            //eventsTwoThree.add();
            persons[22].setEvents(eventsTwoThree);

            HashSet<Event> eventsTwoFour = new HashSet<>();
            eventsTwoFour.add(eventFour);
            persons[23].setEvents(eventsTwoFour);

            return persons;
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }


}
```
###### /java/seedu/address/ui/BrowserPanel.java
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
###### /java/seedu/address/ui/BrowserPanel.java
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
###### /java/seedu/address/ui/PersonDetailsPanel.java
``` java
/**
 * The Contact Details Panel of the App.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailsPanel.fxml";

    private ObservableList<ReadOnlyPerson> personList;
    private TabPane tabPane;

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

    public PersonDetailsPanel(ObservableList<ReadOnlyPerson> personList, TabPane tabPane) {
        super(FXML);
        this.personList = personList;
        this.tabPane = tabPane;
        registerAsAnEventHandler(this);
    }

    /**
     * Open another tab to show twitter webview
     */
    @FXML
    private void openTwitterWebView() {
        Tab tab = new Tab();
        tab.setText("twitter");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load("https://twitter.com/search?q=news&src=typd");
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    /**
     * Open another tab to show nusmods webview
     */
    @FXML
    private void openNusModsWebView() {
        Tab tab = new Tab();
        tab.setText("nusmods");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load("https://nusmods.com/timetable/2017-2018/sem1");
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    /**
     * Open another tab to show facebook webview
     */
    @FXML
    private void openFaceBookWebView() {
        Tab tab = new Tab();
        tab.setText("facebook");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load("https://www.facebook.com/people-search.php");
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    /**
     * Open another tab to show instagram webview
     */
    @FXML
    private void openInstagramWebView() {
        Tab tab = new Tab();
        tab.setText("instagram");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load("https://www.instagram.com/instagram/");
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
    }

    /**
     * Open another tab to show github webview
     */
    @FXML
    private void openGitHubWebView() {
        Tab tab = new Tab();
        tab.setText("github");
        tab.setClosable(true);
        WebView webView = new WebView();
        webView.getEngine().load("https://github.com/github");
        tab.setContent(webView);
        tabPane.getTabs().add(tab);
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
        eventsArea.setText("");
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
        StringBuilder firstBuilder = new StringBuilder();
        int index = 0;
        while (index < address.length && address[index].length() <= 32 - firstBuilder.length()) {
            firstBuilder.append(address[index]);
            firstBuilder.append(" ");
            index++;
        }
        String firstAddress = firstBuilder.toString();
        addressLabel.setText(firstAddress);
        StringBuilder secondBuilder = new StringBuilder();
        for (; index < address.length; index++) {
            secondBuilder.append(address[index]);
            secondBuilder.append(" ");
        }
        if (secondBuilder.length() != 0) {
            String secondAddress = secondBuilder.toString();
            addressLabelContinue.setText(secondAddress);
        }

        eventsArea.setText("");
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for (Event e: person.getEvents()) {
            stringBuilder.append(counter)
                    .append(". ")
                    .append(e.getEventName().fullName)
                    .append(" -- ")
                    .append(e.getEventTime().toString())
                    .append("\n");
            counter++;
        }
        eventsArea.setText(stringBuilder.toString());
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
        StringBuilder firstBuilder = new StringBuilder();
        int index = 0;
        while (index < address.length && address[index].length() <= 32 - firstBuilder.length()) {
            firstBuilder.append(address[index]);
            firstBuilder.append(" ");
            index++;
        }
        String firstAddress = firstBuilder.toString();
        addressLabel.setText(firstAddress);
        StringBuilder secondBuilder = new StringBuilder();
        for (; index < address.length; index++) {
            secondBuilder.append(address[index]);
            secondBuilder.append(" ");
        }
        if (secondBuilder.length() != 0) {
            String secondAddress = secondBuilder.toString();
            addressLabelContinue.setText(secondAddress);
        }

        eventsArea.setText("");
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 1;
        for (Event e: person.getEvents()) {
            stringBuilder.append(counter)
                    .append(". ")
                    .append(e.getEventName().fullName)
                    .append(" -- ")
                    .append(e.getEventTime().toString())
                    .append("\n");
            counter++;
        }
        eventsArea.setText(stringBuilder.toString());
    }

    @Subscribe
    private void handleOpenTwitterWebViewEvent(OpenTwitterWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openTwitterWebView();
    }

    @Subscribe
    private void handleOpenNusModsWebViewEvent(OpenNusModsWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openNusModsWebView();
    }

    @Subscribe
    private void handleOpenFaceBookWebViewEvent(OpenFaceBookWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openFaceBookWebView();
    }

    @Subscribe
    private void handleOpenInstagramWebViewEvent(OpenInstagramWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openInstagramWebView();
    }

    @Subscribe
    private void handleOpenGithubWebViewEvent(OpenGithubWebViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        openGitHubWebView();
    }
}
```
###### /resources/view/PersonDetailsPanel.fxml
``` fxml
<StackPane xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1">
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
                  <ImageView fitHeight="180.0" fitWidth="240.0" pickOnBounds="true" preserveRatio="true">
                     <image>
                        <Image url="images/profile.png" />
                     </image>
                  </ImageView>
                  <Label fx:id="nameLabel" alignment="BOTTOM_CENTER" contentDisplay="CENTER" style="-fx-font-size: 40px; -fx-font-weight: bold;" text="Person Name" textAlignment="CENTER" underline="true" wrapText="true">
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
                        <Label style="-fx-font-size: 22;" text="Birthday:" />
                        <Label fx:id="birthdayLabel" style="-fx-font-size: 22;">
                           <HBox.margin>
                              <Insets left="10.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                     <VBox.margin>
                        <Insets top="45.0" />
                     </VBox.margin>
                  </HBox>
                  <HBox prefHeight="50.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 22;" text="Phone:" />
                        <Label fx:id="phoneLabel" style="-fx-font-size: 22;">
                           <HBox.margin>
                              <Insets left="10.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                     <VBox.margin>
                        <Insets top="5.0" />
                     </VBox.margin>
                  </HBox>
                  <HBox prefHeight="50.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 22;" text="Email:" />
                        <Label fx:id="emailLabel" style="-fx-font-size: 22;">
                           <HBox.margin>
                              <Insets left="10.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                     <VBox.margin>
                        <Insets top="5.0" />
                     </VBox.margin>
                  </HBox>
                  <HBox prefHeight="30.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 22;" text="Address:" />
                        <Label fx:id="addressLabel" style="-fx-font-size: 22;">
                           <HBox.margin>
                              <Insets left="10.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                     <VBox.margin>
                        <Insets top="5.0" />
                     </VBox.margin>
                  </HBox>
                  <HBox prefHeight="40.0" prefWidth="200.0">
                     <children>
                        <Label style="-fx-font-size: 22;" text="Address:" visible="false" />
                        <Label fx:id="addressLabelContinue" style="-fx-font-size: 22;">
                           <HBox.margin>
                              <Insets left="10.0" />
                           </HBox.margin>
                        </Label>
                     </children>
                     <VBox.margin>
                        <Insets top="5.0" />
                     </VBox.margin>
                  </HBox>
               </children>
               <GridPane.margin>
                  <Insets left="25.0" top="10.0" />
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
                  <ImageView fx:id="twitterIcon" fitHeight="90.0" fitWidth="120.0" onMouseClicked="#openTwitterWebView" pickOnBounds="true" preserveRatio="true" GridPane.halignment="RIGHT" GridPane.valignment="BOTTOM">
                     <image>
                        <Image url="images/twitter.png" />
                     </image>
                     <GridPane.margin>
                        <Insets right="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <ImageView fx:id="nusmodsIcon" fitHeight="90.0" fitWidth="120.0" onMouseClicked="#openNusModsWebView" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="2" GridPane.halignment="LEFT" GridPane.valignment="BOTTOM">
                     <image>
                        <Image url="images/nusmods.png" />
                     </image>
                     <GridPane.margin>
                        <Insets left="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <ImageView fx:id="facebookIcon" fitHeight="90.0" fitWidth="120.0" onMouseClicked="#openFaceBookWebView" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="1" GridPane.halignment="CENTER" GridPane.rowIndex="1" GridPane.valignment="CENTER">
                     <image>
                        <Image url="images/facebook.png" />
                     </image>
                  </ImageView>
                  <ImageView fx:id="instagramIcon" fitHeight="90.0" fitWidth="120.0" onMouseClicked="#openInstagramWebView" pickOnBounds="true" preserveRatio="true" GridPane.halignment="RIGHT" GridPane.rowIndex="2" GridPane.valignment="TOP">
                     <image>
                        <Image url="images/instagram.png" />
                     </image>
                     <GridPane.margin>
                        <Insets right="5.0" />
                     </GridPane.margin>
                  </ImageView>
                  <ImageView fx:id="githubIcon" fitHeight="90.0" fitWidth="120.0" onMouseClicked="#openGitHubWebView" pickOnBounds="true" preserveRatio="true" GridPane.columnIndex="2" GridPane.halignment="LEFT" GridPane.rowIndex="2" GridPane.valignment="TOP">
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
                  <TextArea fx:id="eventsArea" editable="false" opacity="0.6" prefHeight="400.0" prefWidth="200.0" style="-fx-font-size: 22px; -fx-font-family: fantasy; -fx-border-color: #003366; -fx-border-width: 8;">
                     <VBox.margin>
                        <Insets />
                     </VBox.margin></TextArea>
               </children>
            </VBox>
         </children>
      </GridPane>
   </children>
</StackPane>
```
