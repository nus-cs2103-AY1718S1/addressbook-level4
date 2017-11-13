# wenmogu
###### \java\seedu\address\logic\commands\AddRelationshipCommand.java
``` java
/**
 * This class is to specify a command for adding relationship between two persons
 */
public class AddRelationshipCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addRelationship";
    public static final String COMMAND_ALIAS = "addre";

    public static final String COMMAND_PARAMETERS = "FROM_INDEX, TO_INDEX (must be positive integers), "
        + "DIRECTION (either \"directed\" or \"undirected\"), "
        + "(Optional) " + PREFIX_CONFIDENCE_ESTIMATE + "CONFIDENCE_ESTIMATE, "
        + "(Optional) " + PREFIX_NAME + "NAME. "
        + "[INDEXOFFROMPERSON] "
        + "[INDEXOFTOPERSON] "
        + "[DIRECTION] "
        + PREFIX_CONFIDENCE_ESTIMATE + "[CONFIDENCE_ESTIMATE} "
        + PREFIX_NAME + "[NAME]";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a relationship between the two persons specified. "
            + "by the index numbers used in the last person listing. "
            + "Direction of the relationship is specified by the direction in user input.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " 1 2 directed";

    public static final String MESSAGE_ADD_RELATIONSHIP_SUCCESS = "Added a %3$s relationship between : %1$s and %2$s";
    public static final String MESSAGE_DUPLICATED_RELATIONSHIP = "This relationship already exists "
            + "in the address book.";

    private final Index indexFromPerson;
    private final Index indexToPerson;
    private final RelationshipDirection direction;

    private final Name name;
    private final ConfidenceEstimate confidenceEstimate;

    /**
     * @param indexFrom of the person from whom the relationship starts in the filtered person list
     * @param indexTo of the person to whom the relationship is directed in the filtered person list
     * @param direction of the relationship
     */
    public AddRelationshipCommand(Index indexFrom, Index indexTo, RelationshipDirection direction,
                                  Name name, ConfidenceEstimate confidenceEstimate) {
        requireAllNonNull(indexFrom, indexTo, direction, name, confidenceEstimate);
        this.indexFromPerson = indexFrom;
        this.indexToPerson = indexTo;
        this.direction = direction;
        this.name = name;
        this.confidenceEstimate = confidenceEstimate;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.addRelationship(indexFromPerson, indexToPerson, direction, name, confidenceEstimate);
        } catch (IllegalValueException ive) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (DuplicateRelationshipException dre) {
            throw new CommandException(MESSAGE_DUPLICATED_RELATIONSHIP);
        }
        return new CommandResult(String.format(MESSAGE_ADD_RELATIONSHIP_SUCCESS, indexToPerson.toString(),
                indexFromPerson.toString(), direction));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRelationshipCommand)) {
            return false;
        }

        // state check
        AddRelationshipCommand addre = (AddRelationshipCommand) other;
        return indexFromPerson.equals(addre.indexFromPerson)
                && indexToPerson.equals(addre.indexToPerson)
                && direction.equals(addre.direction);
    }

}
```
###### \java\seedu\address\logic\commands\DeleteRelationshipCommand.java
``` java
/**
 * This class is to specify a command for deleting relationship between two persons
 */
public class DeleteRelationshipCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteRelationship";
    public static final String COMMAND_ALIAS = "delre";

    public static final String COMMAND_PARAMETERS = "FROM_INDEX, TO_INDEX (must be positive integers), "
            + "[INDEXOFFROMPERSON] "
            + "[INDEXOFTOPERSON] ";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a relationship between the two persons specified. "
            + "by the index numbers used in the last person listing. "
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DELETE_RELATIONSHIP_SUCCESS =  "Deleted the relationship "
            + "between : %1$s and %2$s";

    private final Index fromPersonIndex;
    private final Index toPersonIndex;

    public DeleteRelationshipCommand(Index fromPersonIndex, Index toPersonIndex) {
        this.fromPersonIndex = fromPersonIndex;
        this.toPersonIndex = toPersonIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            model.deleteRelationship(fromPersonIndex, toPersonIndex);
        } catch (IllegalValueException ive) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_RELATIONSHIP_SUCCESS,
                fromPersonIndex.toString(), toPersonIndex.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteRelationshipCommand)) {
            return false;
        }

        // state check
        DeleteRelationshipCommand delre = (DeleteRelationshipCommand) other;
        return fromPersonIndex.equals(delre.fromPersonIndex)
                && toPersonIndex.equals(delre.toPersonIndex);
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
/**
 * Remove a tag from the tag lists of the address book and all persons in the address book
 */
public class RemoveTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "removeTag";

    public static final String COMMAND_PARAMETERS = "TAGNAME (must be alphanumeric)";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_PARAMETERS;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Removes the tag identified by the user input from the tag lists of the address book and all"
            + " persons in the address book.\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Example: " + COMMAND_WORD + " friend";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag is not found.";


    private final String targetTag;

    public RemoveTagCommand(String targetTag) {
        this.targetTag = targetTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.removeTag(targetTag);
        } catch (IllegalValueException ive) {
            assert false : MESSAGE_INVALID_INPUT;
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, targetTag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.targetTag.equals(((RemoveTagCommand) other).targetTag)); // state check
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    private final GraphWrapper graphWrapper;

```
###### \java\seedu\address\logic\parser\AddRelationshipCommandParser.java
``` java
/**
 * This is a argument parser for AddRelationshipCommand
 */
public class AddRelationshipCommandParser implements Parser<AddRelationshipCommand> {

    @Override
    public AddRelationshipCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_CONFIDENCE_ESTIMATE);

        List<String> listOfArgs = Arrays.asList(trimmedArgs.split(" "));
        String firstIndexString = listOfArgs.get(0);
        String secondIndexString = listOfArgs.get(1);


        if (firstIndexString.equals(secondIndexString)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }

        if (firstIndexString.equals("0") || secondIndexString.equals("0")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, AddRelationshipCommand.MESSAGE_USAGE));
        }

        try {
            Index firstIndex = ParserUtil.parseIndex(listOfArgs.get(0));
            Index secondIndex = ParserUtil.parseIndex(listOfArgs.get(1));
            RelationshipDirection direction = ParserUtil.parseDirection(listOfArgs.get(2));

            Name name = ParserUtil.parseRelationshipName(argMultimap.getValue(PREFIX_NAME)).get();
            ConfidenceEstimate confidenceEstimate =
                    ParserUtil.parseConfidenceEstimate(argMultimap.getValue(PREFIX_CONFIDENCE_ESTIMATE)).get();
            return new AddRelationshipCommand(firstIndex, secondIndex, direction, name, confidenceEstimate);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }
    }


}
```
###### \java\seedu\address\logic\parser\DeleteRelationshipCommandParser.java
``` java
/**
 * This is a argument parser for DeleteRelationshipCommand
 */
public class DeleteRelationshipCommandParser implements Parser<DeleteRelationshipCommand> {
    @Override
    public DeleteRelationshipCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        List<String> listOfArgs = Arrays.asList(trimmedArgs.split(" "));

        if (listOfArgs.size() != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        String firstIndexString = listOfArgs.get(0);
        String secondIndexString = listOfArgs.get(1);

        if (firstIndexString.equals(secondIndexString)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        if (firstIndexString.equals("0") || secondIndexString.equals("0")) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                            DeleteRelationshipCommand.MESSAGE_USAGE));
        }

        try {
            Index firstIndex = ParserUtil.parseIndex(listOfArgs.get(0));
            Index secondIndex = ParserUtil.parseIndex(listOfArgs.get(1));
            return new DeleteRelationshipCommand(firstIndex, secondIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRelationshipCommand.MESSAGE_USAGE));
        }
    }


}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
/**
 * Parses input argument and creates a new RemoveTagCommand object.
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns a RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (Tag.isValidTagName(trimmedArgs)) {
            System.out.println(Tag.isValidTagName(trimmedArgs));
            return new RemoveTagCommand(trimmedArgs);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Remove a Tag from tags and everyone with the tag.
     * @param tagGettingRemoved
     * @throws TagNotFoundException
     * @throws IllegalValueException
     */
    public void removeTag(String tagGettingRemoved) throws TagNotFoundException, IllegalValueException {
        try {
            Tag tagToRemove = tags.removeTag(tagGettingRemoved);
            for (Person person : persons) {
                Person temp = person;
                person.removeTag(tagToRemove);
                persons.setPerson(temp, person);
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "impossible to happen as the person from whom the tag is removed definitely exists.";
        }
    }

    //// util methods

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    private GraphWrapper() {
        this.graph = new SingleGraph(graphId);
        initialiseRenderer();
        initialiseViewer();
    }

    /**
     * Produce a graph based on model given
     */
    public SingleGraph buildGraph(Model model) {
        requireNonNull(model);
        if (rebuildNext) {
            this.clear();
            this.setData(model);
            this.initiateGraphNodes();
            this.initiateGraphEdges();
        } else {
            rebuildNext = true;
        }

        graph.setAttribute("ui.stylesheet", GraphDisplay.getGraphDisplayStylesheet());

        return graph;
    }

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    /**
     * add an edge between two persons with direction specified
     */
    public Edge addEdge(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson, RelationshipDirection direction) {
        requireAllNonNull(firstPerson, secondPerson, direction);
        if (direction.isDirected()) {
            return addDirectedEdge(firstPerson, secondPerson);
        } else {
            return addUndirectedEdge(firstPerson, secondPerson);
        }
    }

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    public static GraphWrapper getInstance() {
        return instance;
    }

    private void setData(Model model) {
        this.model = model;
        this.filteredPersons = model.getFilteredPersonList();
    }

    /**
     * Add all the persons in the last displayed list into graph
     * the ID of the node formed for a person is the person's index in the last displayed list
     *
     * Note that the graph only displays first name so that the layout is more aesthetically pleasing.
     * @return graph
     */
    private SingleGraph initiateGraphNodes() {
        try {
            for (ReadOnlyPerson person : filteredPersons) {
                String personIndexInFilteredPersons = getNodeIdFromPerson(person);
                SingleNode node = graph.addNode(personIndexInFilteredPersons);
                String shortenedPersonLabel = (Integer.parseInt(personIndexInFilteredPersons) + 1) + ". "
                        + person.getName().toString().split(" ")[0];
                styleGraphNode(node, shortenedPersonLabel);
            }
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph;
    }

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    /**
     * Read all the edges from model and store into graph
     * @return
     */
    private SingleGraph initiateGraphEdges() {
        for (ReadOnlyPerson person: filteredPersons) {
            Set<Relationship> relationshipSet = person.getRelationships();
            for (Relationship relationship: relationshipSet) {
                Edge edge = addEdge(relationship.getFromPerson(), relationship.getToPerson(),
                        relationship.getDirection());
                edge.addAttribute(nodeAttributeCe, relationship.getConfidenceEstimate().value);
                labelGraphEdge(relationship, edge);
            }
        }

        return graph;
    }

```
###### \java\seedu\address\model\graph\GraphWrapper.java
``` java
    private String getNodeIdFromPerson(ReadOnlyPerson person) throws IllegalValueException {
        requireNonNull(person);
        int indexOfThePerson = filteredPersons.indexOf(person);
        if (indexOfThePerson == -1) {
            throw new IllegalValueException(MESSAGE_PERSON_DOES_NOT_EXIST);
        } else {
            return Integer.toString(indexOfThePerson);
        }
    }

    /**
     * Standardize the format of edge ID
     */
    private String computeEdgeId(ReadOnlyPerson person1, ReadOnlyPerson person2) {
        return  Integer.toString(filteredPersons.indexOf(person1)) + "_"
                + Integer.toString(filteredPersons.indexOf(person2));
    }

    /**
     * Add a directed edge from one person to another
     * remove the previous undirected edge between the two persons (if exists) and add a directed edge
     * @return the directed edge from fromPerson to toPerson
     */
    private Edge addDirectedEdge(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson) {
        String designatedEdgeId = checkForRedundantEdgeAndRemove(fromPerson,
                toPerson, RelationshipDirection.UNDIRECTED);

        try {
            graph.addEdge(designatedEdgeId, getNodeIdFromPerson(fromPerson),
                    getNodeIdFromPerson(toPerson), true);
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph.getEdge(designatedEdgeId);
    }

    /**
     * Add an undirected edge between two persons
     * remove the previous directed edge between the two persons (if exists) and add an undirected edge
     * @return the undirected edge between firstPerson and secondPerson
     */
    private Edge addUndirectedEdge(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
        String designatedEdgeId1 = checkForRedundantEdgeAndRemove(firstPerson,
                secondPerson, RelationshipDirection.DIRECTED);
        String designatedEdgeId2 = checkForRedundantEdgeAndRemove(secondPerson,
                firstPerson, RelationshipDirection.DIRECTED);

        try {
            graph.addEdge(designatedEdgeId1, getNodeIdFromPerson(firstPerson),
                    getNodeIdFromPerson(secondPerson), false);
        } catch (IllegalValueException ive) {
            assert false : "it should not happen.";
        }

        return graph.getEdge(designatedEdgeId1);
    }

    /**
     * Remove the previous edge (if exists) with a different RelationshipDirection from
     * the edge to be added.
     * @param fromPerson
     * @param toPerson
     * @param intendedDirectionOfRedundantEdge
     * @return String
     */
    private String checkForRedundantEdgeAndRemove(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson,
                                                  RelationshipDirection intendedDirectionOfRedundantEdge) {
        requireAllNonNull(fromPerson, toPerson, intendedDirectionOfRedundantEdge);
        String redundantEdgeId1 = computeEdgeId(fromPerson, toPerson);
        String redundantEdgeId2 = computeEdgeId(toPerson, fromPerson);
        Edge redundantEdge1 = graph.getEdge(redundantEdgeId1);
        Edge redundantEdge2 = graph.getEdge(redundantEdgeId2);

        if (intendedDirectionOfRedundantEdge.isDirected()) {
            if (redundantEdge1 != null) {
                graph.removeEdge(redundantEdge1);
            }
            if (redundantEdge2 != null && !redundantEdge2.isDirected()) {
                graph.removeEdge(redundantEdge2);
            }
        } else {
            if (redundantEdge1 != null) {
                graph.removeEdge(redundantEdge1);
            }
            if (redundantEdge2 != null) {
                graph.removeEdge(redundantEdge2);
            }
        }
        return redundantEdgeId1;
    }

    private void clear() {
        graph.clear();
        this.model = null;
        this.filteredPersons = null;
    }

```
###### \java\seedu\address\model\Model.java
``` java
    void addRelationship(Index indexFromPerson, Index indexToPerson, RelationshipDirection direction,
                         Name name, ConfidenceEstimate confidenceEstimate)
        throws IllegalValueException, DuplicateRelationshipException;

    void deleteRelationship(Index indexFromPerson, Index indexToPerson) throws IllegalValueException;

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * @throws TagNotFoundException if the tag is not found in tag list of address book
     * @throws IllegalValueException if the input value is not alphanumeric
     */
    void removeTag(String tagToBeRemoved) throws TagNotFoundException, IllegalValueException;

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Removes a tag with the tagGettingRemoved string
     * @param tagGettingRemoved
     * @throws TagNotFoundException
     * @throws IllegalValueException
     */
    public void removeTag(String tagGettingRemoved) throws TagNotFoundException, IllegalValueException {
        addressBook.removeTag(tagGettingRemoved);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addRelationship(Index indexFromPerson, Index indexToPerson, RelationshipDirection direction,
                                Name name, ConfidenceEstimate confidenceEstimate)
            throws IllegalValueException, DuplicateRelationshipException {
        List<ReadOnlyPerson> lastShownList = getFilteredPersonList();

        if (indexFromPerson.getZeroBased() >= lastShownList.size()
                || indexToPerson.getZeroBased() >= lastShownList.size()
                || indexFromPerson.getZeroBased() < 0
                || indexToPerson.getZeroBased() < 0) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(indexFromPerson.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(indexToPerson.getZeroBased());
        ReadOnlyPerson fromPersonCopy = fromPerson.copy();
        ReadOnlyPerson toPersonCopy = toPerson.copy();
        Relationship relationshipForFromPerson = new Relationship(fromPersonCopy, toPersonCopy, direction,
                name, confidenceEstimate);
        Relationship relationshipForToPerson = relationshipForFromPerson;
        if (!direction.isDirected()) {
            relationshipForToPerson = new Relationship(toPersonCopy, fromPersonCopy, direction,
                    name, confidenceEstimate);
        }


        /*
         Updating the model
         */
        try {
            Person fPerson = (Person) fromPersonCopy;
            Person tPerson = (Person) toPersonCopy;
            fPerson.addRelationship(relationshipForFromPerson);
            tPerson.addRelationship(relationshipForToPerson);
            this.updatePerson(fromPerson, fPerson);
            this.updatePerson(toPerson, tPerson);
        } catch (DuplicateRelationshipException dre) {
            throw new DuplicateRelationshipException();
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("the person's relationship is unmodified. IMPOSSIBLE.");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void deleteRelationship(Index indexFromPerson, Index indexToPerson) throws IllegalValueException {
        List<ReadOnlyPerson> lastShownList = getFilteredPersonList();

        if (indexFromPerson.getZeroBased() >= lastShownList.size()
                || indexToPerson.getZeroBased() >= lastShownList.size()) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(indexFromPerson.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(indexToPerson.getZeroBased());

        Relationship relationshipToDelete1 = new Relationship(fromPerson, toPerson, RelationshipDirection.UNDIRECTED);
        Relationship relationshipToDelete2 = new Relationship(fromPerson, toPerson, RelationshipDirection.DIRECTED);

        Person fromPersonCasting = (Person) fromPerson;
        Person toPersonCasting = (Person) toPerson;
        fromPersonCasting.removeRelationship(relationshipToDelete1);
        toPersonCasting.removeRelationship(relationshipToDelete1);

        fromPersonCasting.removeRelationship(relationshipToDelete2);
        toPersonCasting.removeRelationship(relationshipToDelete2);

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    //=========== Filtered Person List Accessors =============================================================

```
###### \java\seedu\address\model\person\exceptions\RelationshipNotFoundException.java
``` java
/**
 * Signal that the operation is not able to find the specified relationship.
 */
public class RelationshipNotFoundException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public RelationshipNotFoundException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\person\exceptions\TagNotFoundException.java
``` java
/**
 * Signal that the operation is not able to find the specified tag.
 */
public class TagNotFoundException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public TagNotFoundException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Removes a relationship from a person's relationships
     */
    public boolean removeRelationship(Relationship re) {
        UniqueRelationshipList reList = relationships.get();
        return reList.removeRelationship(re);
    }

```
###### \java\seedu\address\model\relationship\ConfidenceEstimate.java
``` java
/**
 * This is a value of how confident the user is towards the information recorded.
 */
public class ConfidenceEstimate {
    public static final ConfidenceEstimate UNSPECIFIED = new ConfidenceEstimate();
    public static final String MESSAGE_CONFIDENCE_ESTIMATE_CONSTRAINTS =
            "Confidence estimates should be a single number between 0 and 100 without spaces";

    public final double value;

    /**
     * The default ConfidenceEstimate constructor when confidence estimate is not specified by the user
     */
    private ConfidenceEstimate() {
        value = 0;
    }

    /**
     * Validates a given confidence estimate.
     *
     * @throws IllegalValueException if the given confidence estimate string is invalid.
     */
    public ConfidenceEstimate(String estimate) throws IllegalValueException {
        requireNonNull(estimate);
        String trimmedEstimate = estimate.trim();
        if (!isValidConfidenceEstimate(trimmedEstimate)) {
            throw new IllegalValueException(MESSAGE_CONFIDENCE_ESTIMATE_CONSTRAINTS);
        }
        this.value = Double.parseDouble(trimmedEstimate);
    }

    public ConfidenceEstimate(double estimate) {
        value = estimate;
    }

    /**
     * Returns true if a given string is a valid confidence estimate.
     */
    public static boolean isValidConfidenceEstimate(String test) {
        double d;
        try {
            d = Double.parseDouble(test);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return d >= 0 && d <= 100;
    }


    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConfidenceEstimate // instanceof handles nulls
                && this.value == (((ConfidenceEstimate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
```
###### \java\seedu\address\model\relationship\exceptions\DuplicateRelationshipException.java
``` java
/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateRelationshipException extends Exception {
    public DuplicateRelationshipException() {
        super("Operation would result in duplicate relationships");
    }
}
```
###### \java\seedu\address\model\relationship\Relationship.java
``` java
/**
 * This class defines the relationship between two ReadOnlyPersons
 */
public class Relationship {

    private ReadOnlyPerson fromPerson;
    private ReadOnlyPerson toPerson;
    private RelationshipDirection direction;

    private Name name;
    private ConfidenceEstimate confidenceEstimate;

    public Relationship(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction) {
        requireAllNonNull(fromPerson, toPerson, direction);
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.direction = direction;
        this.name = Name.UNSPECIFIED;
        this.confidenceEstimate = ConfidenceEstimate.UNSPECIFIED;
    }

    public Relationship(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction,
                        Name name, ConfidenceEstimate confidenceEstimate) {
        this(fromPerson, toPerson, direction);
        this.setConfidenceEstimate(confidenceEstimate);
        this.setName(name);
    }

    public ReadOnlyPerson getFromPerson() {
        return fromPerson;
    }

    public ReadOnlyPerson getToPerson() {
        return toPerson;
    }

    public RelationshipDirection getDirection() {
        return direction;
    }

    /**
     * This is to make the relationship's person entries always point to the existing persons in the address book
     * used when a person is updated
     */
    public Relationship replacePerson(ReadOnlyPerson previousPerson, ReadOnlyPerson currentPerson) {
        if (this.fromPerson.equals(previousPerson)) {
            this.fromPerson = currentPerson;
        } else if (this.toPerson.equals(previousPerson)) {
            this.toPerson = currentPerson;
        }
        return this;
    }

    public Name getName() {
        return name;
    }

    public ConfidenceEstimate getConfidenceEstimate() {
        return confidenceEstimate;
    }

    public boolean isUndirected() {
        return !direction.isDirected();
    }

    /**
     * This is to find the opposite relationships of the one that the user is trying to add.
     * An opposite relationship of one relationship is defined as a relationship involving the same two persons
     * but with a different direction (DIRECTED <=> UNDIRECTED).
     * If the opposite relationship exists, remove the opposite relationship before adding the intended relationship.
     * @return an ArrayList containing two opposite relationships of this one.
     */
    public ArrayList<Relationship> oppositeRelationships() {
        ReadOnlyPerson fromPerson = getFromPerson();
        ReadOnlyPerson toPerson = getToPerson();
        ArrayList<Relationship> oppoRelationships = new ArrayList<>(2);
        if (this.isUndirected()) {
            oppoRelationships.add(new Relationship(fromPerson, toPerson, RelationshipDirection.DIRECTED));
            oppoRelationships.add(new Relationship(toPerson, fromPerson, RelationshipDirection.DIRECTED));
        } else {
            oppoRelationships.add(new Relationship(fromPerson, toPerson, RelationshipDirection.UNDIRECTED));
            oppoRelationships.add(new Relationship(toPerson, fromPerson, RelationshipDirection.UNDIRECTED));
        }

        return oppoRelationships;
    }

    public void setName(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public void setConfidenceEstimate(ConfidenceEstimate confidenceEstimate) {
        requireNonNull(confidenceEstimate);
        this.confidenceEstimate = confidenceEstimate;
    }

    /**
     * A toString method for Relationship
     */
    public String toString() {
        String nameAndConfidenceEstimate = this.name.toString() + " " + this.confidenceEstimate.toString();
        if (isUndirected()) {
            return fromPerson.toString() + " <-> " + toPerson.toString() + " " + nameAndConfidenceEstimate;
        } else {
            return fromPerson.toString() + " -> " + toPerson.toString() + " " + nameAndConfidenceEstimate;
        }
    }


    @Override
    public boolean equals(Object other) {
        boolean correspondingPersonCheck = true;
        if (this.getDirection() != ((Relationship) other).getDirection()) {
            return false;
        } else {
            if (this.isUndirected() && ((Relationship) other).isUndirected()) {
                correspondingPersonCheck = (this.getFromPerson().equals(((Relationship) other).getFromPerson())
                        && this.getToPerson().equals(((Relationship) other).getToPerson()))
                        || (this.getFromPerson().equals(((Relationship) other).getToPerson())
                                && this.getToPerson().equals(((Relationship) other).getFromPerson()));
            } else if (!this.isUndirected() && !((Relationship) other).isUndirected()) {
                correspondingPersonCheck = this.getFromPerson().equals(((Relationship) other).getFromPerson())
                        && this.getToPerson().equals(((Relationship) other).getToPerson());
            }
        }

        return other == this // short circuit if same object
                || (other instanceof Relationship // instanceof handles nulls
                && correspondingPersonCheck);
    }
}
```
###### \java\seedu\address\model\relationship\RelationshipDirection.java
``` java
/**
 * This class defines the direction of relationships for Relationship class
 */
public enum RelationshipDirection {

    UNDIRECTED,
    DIRECTED;

    public String getDirection() {
        if (isDirected()) {
            return "directed";
        } else {
            return "undirected";
        }
    }

    public boolean isDirected() {
        return this == DIRECTED;
    }

    public String toString() {
        return this.getDirection();
    }
}
```
###### \java\seedu\address\model\relationship\UniqueRelationshipList.java
``` java
/**
 * A list of relationships that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Relationship#equals(Object)
 */
public class UniqueRelationshipList implements Iterable<Relationship> {

    private final ObservableList<Relationship> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty RelationshipList.
     */
    public UniqueRelationshipList() {}

    /**
     * Creates a UniqueRelationshipList using given relationships.
     * Enforces no nulls.
     */
    public UniqueRelationshipList(Set<Relationship> relationships) {
        requireAllNonNull(relationships);
        internalList.addAll(relationships);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all relationships in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Relationship> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Relationships in this list with those in the argument relationship list.
     */
    public void setRelationships(Set<Relationship> relationships) {
        requireAllNonNull(relationships);
        internalList.setAll(relationships);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every relationship in the argument list exists in this object.
     */
    public void mergeFrom(UniqueRelationshipList from) {
        final Set<Relationship> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(relationship -> !alreadyInside.contains(relationship))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Relationship as the given argument.
     */
    public boolean contains(Relationship toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Relationship to the list.
     * @throws DuplicateRelationshipException if the Relationship to add is a duplicate
     * of an existing Relationship in the list.
     */
    public void add(Relationship toAdd) throws DuplicateRelationshipException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRelationshipException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Remove a Relationship from the list.
     * @param relationshipGettingRemoved
     */
    public boolean removeRelationship(Relationship relationshipGettingRemoved) {
        requireNonNull(relationshipGettingRemoved);
        return internalList.remove(relationshipGettingRemoved);
    }

    @Override
    public Iterator<Relationship> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Relationship> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueRelationshipList // instanceof handles nulls
                && this.internalList.equals(((UniqueRelationshipList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueRelationshipList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\tag\UniqueTagList.java
``` java
    /**
     * If the tag name is valid and the tag present in the list.
     * @param tagName
     * @return the index of the tag in the list
     * @throws IllegalValueException
     */
    public int indexOfTagWithName(String tagName) throws IllegalValueException {
        requireNonNull(tagName);
        Tag temp = new Tag(tagName);
        return internalList.indexOf(temp);
    }

```
###### \java\seedu\address\model\tag\UniqueTagList.java
``` java
    /**
     * Remove a Tag from the list. The tag is identified by its name.
     *
     * throws TagNotFoundException if the Tag to remove is not found in the list.
     * throws IllegalValueException if the Tag name input is invalid.
     */
    public Tag removeTag(String tagGettingRemoved) throws TagNotFoundException, IllegalValueException {
        requireNonNull(tagGettingRemoved);
        int tagIndexInList = indexOfTagWithName(tagGettingRemoved);
        if (tagIndexInList == -1) {
            throw new TagNotFoundException(MESSAGE_TAG_NOT_FOUND);
        } else {
            return internalList.remove(tagIndexInList);
        }
    }

    /**
     * Remove a tag from the list.
     * @param tagGettingRemoved
     */
    public boolean removeTag(Tag tagGettingRemoved) {
        requireNonNull(tagGettingRemoved);
        return internalList.remove(tagGettingRemoved);
    }

```
