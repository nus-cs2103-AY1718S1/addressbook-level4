# LimeFallacie
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
/**
 * Exports the contacts list in file format to a provided directory in the provided filename
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports contacts list to chosen directory.\n"
            + "Parameters: directory\n"
            + "Example for export: " + COMMAND_WORD + " C:\\desktop\\circles.xml\n";

    public static final String MESSAGE_SUCCESS = "Contacts list exported successfully.";

    public static final String MESSAGE_WRITE_ERROR = "The file could not be exported.";

    private String fileLocation;

    public ExportCommand(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            storage.saveAddressBook(model.getAddressBook(), fileLocation);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ExportCommand) other).fileLocation)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
/**
 *  Imports a contacts list as an .xml document and resets the current addressbook into the written one
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a contacts list from chosen directory and\n"
            + "resets the current directory to the provided list.\n"
            + "Parameters: directory\\filename.xml\n"
            + "Example for export: " + COMMAND_WORD + " C:\\desktop\\addressbook.xml\n";

    public static final String MESSAGE_SUCCESS = "Contacts list imported successfully.";

    public static final String MESSAGE_WRITE_ERROR = "The file could not be imported.";

    private String fileLocation;
    private Optional<ReadOnlyAddressBook> importData;

    public ImportCommand(String fileLocation) {
        this.fileLocation = fileLocation;
        //Importing slave object here
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            importData = storage.readAddressBook(fileLocation);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        } catch (DataConversionException dce) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }

        ReadOnlyAddressBook newAddressBook = importData.orElse(null);

        if (newAddressBook != null) {
            model.resetData(newAddressBook);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRITE_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ImportCommand) other).fileLocation)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists Address Book contacts with specified tag.\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " friend";

    public static final String MESSAGE_SUCCESS = "Listed all persons%s";

    private String tagToList;

    public ListCommand(String tag) {
        this.tagToList = tag;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if ("all".equalsIgnoreCase(tagToList)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, "."));
        }

        try {
            Predicate <ReadOnlyPerson> predicateShowAllTagged = model.getPredicateForTags(tagToList);
            model.updateFilteredPersonList(predicateShowAllTagged);
            String concat = " with " + tagToList + " tag.";
            return new CommandResult(String.format(MESSAGE_SUCCESS, concat));
        } catch (IllegalValueException ive) {
            throw new CommandException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && this.tagToList.equals(((ListCommand) other).tagToList)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts list according to sort type entered.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String ARGUMENT_NAME = "name";
    public static final String ARGUMENT_PHONE = "phone";
    public static final String ARGUMENT_EMAIL = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts Address Book contacts according to specified field.\n"
            + "Parameters: TYPE (must be either 'name', 'phone, or 'email')\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORT_SUCCESS = "Sorted all persons by %s.";

    public static final String MESSAGE_NO_CONTACTS_TO_SORT = "There are no contacts available to sort";

    private String sortType;

    public SortCommand(String type) {
        this.sortType = type;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.sort(sortType);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_USAGE); //It will never reach here.
        } catch (EmptyAddressBookException eabe) {
            throw new CommandException(MESSAGE_NO_CONTACTS_TO_SORT);
        }

        //lists all contacts after sorting
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (!(ARGUMENT_PHONE.equals(sortType))) {
            return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortType));
        } else {
            return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, (sortType + " number")));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }

}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    private final Storage storage;

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.history = new CommandHistory();
        this.addressBookParser = new AddressBookParser();
        this.undoRedoStack = new UndoRedoStack();
        this.storage = storage;
    }

```
###### \java\seedu\address\logic\parser\ExportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        String filePath = ParserUtil.parseFilePath(args);
        if (filePath.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        if (!(filePath.endsWith(".xml"))) {
            filePath = String.format(filePath + ".xml");
        }
        return new ExportCommand(filePath);
    }
}
```
###### \java\seedu\address\logic\parser\ListCommandParser.java
``` java
/**
 * Parses ListCommand arguments and creates a ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String listTag = ParserUtil.parseTag(args);
        if (listTag.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(listTag);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a String and checks for validity. Leading and trailing whitespaces will be removed
     * @throws IllegalValueException if specified string is invalid (not 1 of 3 options)
     */
    public static String parseSortType(String sortType) throws IllegalValueException {
        String trimmedSortType = sortType.trim();
        switch (trimmedSortType) {
        case "name":
        case "phone":
        case "email":
            return trimmedSortType;
        default:
            throw new IllegalValueException(MESSAGE_INVALID_SORT);
        }
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a String argument for tag. Leading and trailing whitespaces will be removed
     */
    public static String parseTag(String tag) {
        String trimmedTag = tag.trim();
        return trimmedTag;

    }

    /**
     * Parses a String argument for a file path destination for Export.
     * Leading and trailing whitespaces will be removed
     */
    public static String parseFilePath(String path) {
        String trimmedPath = path.trim();
        return trimmedPath;
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses SortCommand arguments and creates a SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            String sortType = ParserUtil.parseSortType(args);
            return new SortCommand(sortType);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    @Override
    public void init() throws Exception {
        logger.info("=============================[ Drawing Circles ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(userPrefs.getAddressBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);
        if (!model.getFilteredPersonList().isEmpty()) {
            model.sort("name");
        }

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    private UserPrefs colourPrefs;

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGroups = new FilteredList<>(this.addressBook.getGroupList());
        colourPrefs = userPrefs;
        HashMap<String, String> stringColourMap = userPrefs.getColourMap();
        if (stringColourMap != null) {
            try {
                for (HashMap.Entry<String, String> entry : stringColourMap.entrySet()) {
                    tagColours.put(new Tag(entry.getKey()), entry.getValue());
                }

            } catch (IllegalValueException ive) {
                //it shouldn't ever reach here
            }
        }
        updateAllPersons(tagColours);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    private void updateAllPersons(HashMap<Tag, String> allTagColours) {
        colourPrefs.updateColorMap(allTagColours);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sort(String sortType) throws DuplicatePersonException, EmptyAddressBookException {
        switch (sortType) {
        case SortCommand.ARGUMENT_NAME:
            addressBook.setPersons(sortBy(COMPARATOR_SORT_BY_NAME));
            break;

        case SortCommand.ARGUMENT_PHONE:
            addressBook.setPersons(sortBy(COMPARATOR_SORT_BY_PHONE));
            break;

        case SortCommand.ARGUMENT_EMAIL:
            addressBook.setPersons(sortBy(COMPARATOR_SORT_BY_EMAIL));
            break;

        default:
            break;

        }
        indicateAddressBookChanged();
    }

    /**
     * Sort the addressbook by the comparator given
     * @return ArrayList<ReadOnlyPerson> sorted list</ReadOnlyPerson>
     */
    private ArrayList<ReadOnlyPerson> sortBy(Comparator<ReadOnlyPerson> comparator) throws EmptyAddressBookException {
        ArrayList<ReadOnlyPerson> newList = new ArrayList<>();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        SortedList<ReadOnlyPerson> sortedList =
                getFilteredPersonList().filtered(PREDICATE_SHOW_PINNED_PERSONS).sorted(comparator);
        newList.addAll(sortedList);
        sortedList = getFilteredPersonList().filtered(PREDICATE_SHOW_UNPINNED_PERSONS).sorted(comparator);
        newList.addAll(sortedList);

        if (newList.isEmpty()) {
            throw new EmptyAddressBookException();
        }

        return newList;
    }

```
###### \java\seedu\address\model\UserPrefs.java
``` java
    private HashMap<String, String> colourMap;
    private String addressBookName = "Circles";

    public UserPrefs() {
        this.setGuiSettings(1600, 900, 10, 10);
        colourMap = new HashMap<>();
    }

```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public HashMap<String, String> getColourMap() {
        return colourMap;
    }

    public void setColourMap(HashMap<String, String> colourMap) {
        this.colourMap = colourMap;
    }

    /**
     * updates colormap with the new hashmap
     * @param newMap
     */
    public void updateColorMap(HashMap<Tag, String> newMap) {
        colourMap.clear();
        for (HashMap.Entry<Tag, String> newEntry : newMap.entrySet()) {
            colourMap.put(newEntry.getKey().tagName, newEntry.getValue());
        }
    }

```
