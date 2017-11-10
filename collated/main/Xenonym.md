# Xenonym
###### \java\org\graphstream\algorithm\WidestPath.java
``` java
/**
 * Modified Dijkstra's algorithm for computing widest paths.
 * The below algorithm is based on pseudocode from <a href="https://stackoverflow.com/a/18553217">here</a>.
 */
public class WidestPath extends Dijkstra {
    public WidestPath(Element element, String resultAttribute, String lengthAttribute) {
        super(element, resultAttribute, lengthAttribute);
    }

    @Override
    protected void makeTree() {
        // initialization
        FibonacciHeap<Double, Node> heap = new FibonacciHeap<>();
        for (Node node : graph) {
            Data data = new Data();
            double v = node == source ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            data.fn = heap.add(-v, node);
            data.edgeFromParent = null;
            node.addAttribute(resultAttribute, data);
        }

        // main loop
        while (!heap.isEmpty()) {
            Node u = heap.extractMin();
            Data dataU = u.getAttribute(resultAttribute);
            dataU.distance = -dataU.fn.getKey();
            if (dataU.distance == Double.NEGATIVE_INFINITY) {
                break;
            }
            dataU.fn = null;
            if (dataU.edgeFromParent != null) {
                edgeOn(dataU.edgeFromParent);
            }
            for (Edge e : u.getEachLeavingEdge()) {
                Node v = e.getOpposite(u);
                Data dataV = v.getAttribute(resultAttribute);
                if (dataV.fn == null) {
                    continue;
                }
                double tryDist = Math.max(dataV.distance, Math.min(dataU.distance, getLength(e, v)));
                if (tryDist > -dataV.fn.getKey()) {
                    dataV.edgeFromParent = e;
                    heap.decreaseKey(dataV.fn, -tryDist);
                }
            }
        }
    }
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

    public static final String COMMAND_WORD = "clearHistory";
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

    public static final String COMMAND_WORD = "colourTag";
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
###### \java\seedu\address\logic\commands\RelPathCommand.java
``` java
/**
 * Gets the shortest relationship path between two persons with the highest confidence.
 */
public class RelPathCommand extends Command {
    public static final String COMMAND_WORD = "relPath";
    public static final String COMMAND_ALIAS = "rp";

    public static final String COMMAND_PARAMETERS = "FROM_INDEX, TO_INDEX (must be positive integers)";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Gets the shortest relationship path with the highest confidence between the two persons specified by "
            + "the index numbers used in the last person listing.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + "1 2";

    public static final String MESSAGE_PATH_FOUND = "Path found between %1$s and %2$s!";
    public static final String MESSAGE_NO_PATH = "There is no path between %1$s and %2$s.";

    private final Index from;
    private final Index to;

    public RelPathCommand(Index from, Index to) {
        requireAllNonNull(from, to);
        this.from = from;
        this.to = to;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (from.getZeroBased() >= lastShownList.size() || to.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(from.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(to.getZeroBased());
        int nodeCount = GraphWrapper.getInstance().highlightShortestPath(fromPerson, toPerson);

        if (nodeCount > 1) {
            return new CommandResult(String.format(MESSAGE_PATH_FOUND, fromPerson.getName(), toPerson.getName()));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_PATH, fromPerson.getName(), toPerson.getName()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RelPathCommand that = (RelPathCommand) o;

        return from.equals(that.from) && to.equals(that.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
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
###### \java\seedu\address\logic\parser\RelPathCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RelPathCommand object.
 */
public class RelPathCommandParser implements Parser<RelPathCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of RelPathCommand
     * and returns an RelPathCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public RelPathCommand parse(String userInput) throws ParseException {
        String[] args = userInput.trim().split(" ");
        if (args.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelPathCommand.MESSAGE_USAGE));
        }

        try {
            return new RelPathCommand(ParserUtil.parseIndex(args[0]), ParserUtil.parseIndex(args[1]));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelPathCommand.MESSAGE_USAGE));
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
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    /**
     * Highlights the shortest path between two people with the highest confidence.
     * Paths with higher minimum confidence estimates are preferred.
     * Cancels next graph rebuild for styling to remain.
     * @return the number of nodes in the path between the two given people.
     */
    public int highlightShortestPath(ReadOnlyPerson from, ReadOnlyPerson to) {
        WidestPath widestPath = new WidestPath(Dijkstra.Element.EDGE, null, nodeAttributeCe);
        Node fromNode;
        Node toNode;

        resetGraph(); // reset graph to clear previously highlighted path first, if any
        try {
            fromNode = graph.getNode(getNodeIdFromPerson(from));
            toNode = graph.getNode(getNodeIdFromPerson(to));
        } catch (IllegalValueException ive) {
            throw new AssertionError("impossible to have nonexistent persons in graph");
        }

        widestPath.init(graph);
        widestPath.setSource(fromNode);
        widestPath.compute();

        for (Node n : widestPath.getPathNodes(toNode)) {
            n.addAttribute("ui.style", "fill-color: blue;");
        }

        for (Edge e : widestPath.getPathEdges(toNode)) {
            e.addAttribute("ui.style", "fill-color: red;");
        }

        rebuildNext = false;
        return widestPath.getPath(toNode).getNodeCount();
    }

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    private void resetGraph() {
        graph.clear();
        initiateGraphNodes();
        initiateGraphEdges();
    }
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

    @XmlElement(required = true)
    private double confidenceEstimate;

    @XmlElement(required = true)
    private String name;

    /**
     * Constructs an XmlAdaptedRelationship.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRelationship() {}

    /**
     * Converts a given Relationship attributes into this class for JAXB use.
     */
    public XmlAdaptedRelationship(int fromIndex, int toIndex, RelationshipDirection direction,
                                  ConfidenceEstimate confidenceEstimate, Name name) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.isDirected = direction.isDirected();
        this.confidenceEstimate = confidenceEstimate.value;
        this.name = name.fullName;
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
            fromPerson.addRelationship(new Relationship(fromPerson, toPerson, getRelationshipDirection(),
                    new Name(name), new ConfidenceEstimate(confidenceEstimate)));
        } catch (DuplicateRelationshipException dre) {
            throw new AssertionError("impossible to have duplicate relationships in storage", dre);
        } catch (IllegalValueException ive) {
            throw new AssertionError("impossible to have invalid values in storage", ive);
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
        if (isDirected != that.isDirected) {
            return false;
        }
        if (Double.compare(that.confidenceEstimate, confidenceEstimate) != 0) {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = fromIndex;
        result = 31 * result + toIndex;
        result = 31 * result + (isDirected ? 1 : 0);
        temp = Double.doubleToLongBits(confidenceEstimate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
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
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        List<ReadOnlyPerson> persons = src.getPersonList();
        Set<XmlAdaptedRelationship> rels = new HashSet<>(); // prevent duplicate relationships from being added
        for (int i = 0; i < persons.size(); i++) {
            for (Relationship r : persons.get(i).getRelationships()) {
                rels.add(new XmlAdaptedRelationship(persons.indexOf(r.getFromPerson()),
                        persons.indexOf(r.getToPerson()), r.getDirection(), r.getConfidenceEstimate(), r.getName()));
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
