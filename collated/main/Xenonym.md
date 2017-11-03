# Xenonym
###### \java\seedu\address\storage\XmlAdaptedRelationship.java
``` java
/**
 * JAXB-friendly adapted version of Relationship.
 */
public class XmlAdaptedRelationship {

    @XmlElement(required = true)
    private int fromIndex;

    @XmlElement(required = true)
    private int toIndex;

    @XmlElement(required = true)
    private boolean isDirected;

    /**
     * Constructs an XmlAdaptedRelationship.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRelationship() {}

    /**
     * Converts a given Relationship attributes into this class for JAXB use.
     */
    public XmlAdaptedRelationship(int fromIndex, int toIndex, RelationshipDirection direction) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.isDirected = direction.isDirected();
    }

    /**
     * Converts this jaxb-friendly adapted relationship object into the model's Relationship object,
     * then adds it into the model.
     * @param persons current Person list without relationships
     */
    public void addToModel(List<ReadOnlyPerson> persons) {
        Person fromPerson = (Person) persons.get(fromIndex);
        Person toPerson = (Person) persons.get(toIndex);

        try {
            fromPerson.addRelationship(new Relationship(fromPerson, toPerson, getRelationshipDirection()));
        } catch (DuplicateRelationshipException dre) {
            throw new AssertionError("impossible to have duplicate relationships in storage", dre);
        }

    }

    private RelationshipDirection getRelationshipDirection() {
        return isDirected ? RelationshipDirection.DIRECTED : RelationshipDirection.UNDIRECTED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        XmlAdaptedRelationship that = (XmlAdaptedRelationship) o;

        if (fromIndex != that.fromIndex) {
            return false;
        }
        if (toIndex != that.toIndex) {
            return false;
        }
        return isDirected == that.isDirected;
    }

    @Override
    public int hashCode() {
        int result = fromIndex;
        result = 31 * result + toIndex;
        result = 31 * result + (isDirected ? 1 : 0);
        return result;
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        List<ReadOnlyPerson> persons = src.getPersonList();
        Set<XmlAdaptedRelationship> rels = new HashSet<>(); // prevent duplicate relationships from being added
        for (int i = 0; i < persons.size(); i++) {
            for (Relationship r : persons.get(i).getRelationships()) {
                rels.add(new XmlAdaptedRelationship(persons.indexOf(r.getFromPerson()),
                        persons.indexOf(r.getToPerson()), r.getDirection()));
            }
        }
        relationships.addAll(rels);
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        for (XmlAdaptedRelationship xre : relationships) {
            xre.addToModel(persons);
        }
```
###### \java\seedu\address\commons\core\GuiSettings.java
``` java
    public Map<Tag, String> getTagColours() {
        return Collections.unmodifiableMap(tagColours);
    }

    public void setTagColours(Map<Tag, String> newTagColours) {
        tagColours = newTagColours;
    }
```
###### \java\seedu\address\logic\CommandHistory.java
``` java
    /**
     * Clear the command history.
     */
    public void clear() {
        userInputHistory.clear();
    }
```
###### \java\seedu\address\logic\commands\BackupCommand.java
``` java
/**
 * Backs up the address book to a fixed location (current file name).bak.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "bak";
    public static final String MESSAGE_SUCCESS = "Backup created.";
    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD;

    @Override
    public CommandResult execute() throws CommandException {
        try {
            storage.backupAddressBook(storage.readAddressBook().get());
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Storage storage) {
        super.setData(model, history, undoRedoStack, storage);
        this.storage = storage;
    }
}
```
###### \java\seedu\address\logic\commands\ClearHistoryCommand.java
``` java
/**
 * Clears the command history and the undo/redo stack.
 */
public class ClearHistoryCommand extends Command {

    public static final String COMMAND_WORD = "clearhistory";
    public static final String COMMAND_ALIAS = "ch";
    public static final String MESSAGE_SUCCESS = "Command history cleared.";
    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD;

    @Override
    public CommandResult execute() throws CommandException {
        undoRedoStack.clear();
        history.clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, Storage storage) {
        super.setData(model, history, undoRedoStack, storage);
        this.history = history;
        this.undoRedoStack = undoRedoStack;
    }
}
```
###### \java\seedu\address\logic\commands\ColourTagCommand.java
``` java
/**
 * Changes the colour of a tag to a given colour.
 */
public class ColourTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "colourtag";
    public static final String COMMAND_ALIAS = "ct";

    public static final String COMMAND_PARAMETERS = "TAG (must be alphanumeric) COLOUR";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the colour of the given tag to the given colour.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " friend red";
    public static final String MESSAGE_COLOUR_TAG_SUCCESS = "Colour of %1$s will be %2$s on next start.";

    private final Tag tag;
    private final String colour;

    public ColourTagCommand(Tag tag, String colour) {
        this.tag = tag;
        this.colour = colour;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        ModelManager m = ((ModelManager) model);
        Map<Tag, String> currentTagColours = new HashMap<>(m.getUserPrefs().getGuiSettings().getTagColours());
        currentTagColours.put(tag, colour);
        m.getUserPrefs().getGuiSettings().setTagColours(currentTagColours);

        return new CommandResult(String.format(MESSAGE_COLOUR_TAG_SUCCESS, tag, colour));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ColourTagCommand // instanceof handles nulls
                && this.tag.equals(((ColourTagCommand) other).tag)
                && this.colour.equals(((ColourTagCommand) other).colour)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\ColourTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new {@code ColourTagCommand} object.
 */
public class ColourTagCommandParser implements Parser<ColourTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ColourTagCommand
     * and returns an ColourTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ColourTagCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColourTagCommand.MESSAGE_USAGE));
        }

        String[] args = trimmedArgs.split(" ");
        if (args.length != 2 && Tag.isValidTagName(args[0])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColourTagCommand.MESSAGE_USAGE));
        }

        try {
            return new ColourTagCommand(new Tag(args[0]), args[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ColourTagCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\UndoRedoStack.java
``` java
    /**
     * Clears the stack of all commands.
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Returns a mapping of tags to colours.
     */
    public static Map<Tag, String> getSampleTagColours() {
        HashMap<Tag, String> sampleTagColours = new HashMap<>();
        try {
            sampleTagColours.put(new Tag("friends"), "red");
            sampleTagColours.put(new Tag("colleagues"), "green");
            sampleTagColours.put(new Tag("family"), "yellow");
            sampleTagColours.put(new Tag("neighbours"), "blue");
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }

        return sampleTagColours;
    }
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * Returns the file path of the backup data file.
     */
    String getBackupAddressBookFilePath();
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * Returns backup AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if backup file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException;
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * Backs up the given {@link ReadOnlyAddressBook} to a fixed temporary location.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public String getBackupAddressBookFilePath() {
        return addressBookStorage.getBackupAddressBookFilePath();
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        return addressBookStorage.readBackupAddressBook();
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, getBackupAddressBookFilePath());
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public String getBackupAddressBookFilePath() {
        return getAddressBookFilePath() + BACKUP_SUFFIX;
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        return readAddressBook(getBackupAddressBookFilePath());
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, getBackupAddressBookFilePath());
    }
```
