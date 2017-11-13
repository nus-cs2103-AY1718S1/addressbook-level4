# huiyiiih
###### /resources/view/BrightTheme.css
``` css
.scroll-pane > .viewport {
   -fx-background-color: transparent;
}

.scroll-pane {
   -fx-background-color: transparent;
}
```
###### /resources/view/PersonPanel.fxml
``` fxml
         <Label layoutX="14.0" layoutY="255.0" prefHeight="100.0" prefWidth="275.0" styleClass="label-header" text="Relationships" AnchorPane.leftAnchor="14.0">
         <font>
            <Font name="System Bold" size="21.0" />
         </font>
      </Label>
         <ScrollPane fx:id="scrollPane" layoutX="383.0" layoutY="287.0" prefHeight="40.0" prefWidth="212.0">
            <content>
               <FlowPane fx:id="relationshipPane" prefHeight="52.0" prefWidth="181.0" />
            </content>
         </ScrollPane>
```
###### /java/seedu/address/logic/parser/ArgumentMultimap.java
``` java
    /**
     * Returns true if there is only one prefix present
     * and false if there are more.
     */
    public boolean containsOnePrefix(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        if (values.size() > 1) {
            return false;
        }
        return true;
    }
    /**
     * Checks if the arguMultimap contains a specfic prefix
     * if it contains, it will return a true, if it does not it returns a false
     * @param prefix            Prefix key
     * @return true || false
     */
    public boolean containsPrefix(Prefix prefix) {
        return argMultimap.containsKey(prefix);
    }
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code Collection<String> relationship} into a {@code Set<Relationship>}.
     */
    public static Set<Relationship> parseRels(Collection<String> relation) throws IllegalValueException {
        requireNonNull(relation);
        final Set<Relationship> relationSet = new HashSet<>();
        for (String relationType : relation) {
            relationSet.add(new Relationship(relationType));
        }
        return relationSet;
    }
```
###### /java/seedu/address/logic/parser/CliSyntax.java
``` java
    /* Prefix definitions for Relationship*/
    public static final Prefix PREFIX_ADD_RELATIONSHIP = new Prefix("ar/");
    public static final Prefix PREFIX_DELETE_RELATIONSHIP = new Prefix("dr/");
    public static final Prefix PREFIX_CLEAR_RELATIONSHIP = new Prefix("cr/");
    //@author
}
```
###### /java/seedu/address/logic/parser/relationship/SetRelCommandParser.java
``` java
package seedu.address.logic.parser.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.logic.commands.relationship.SetRelCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.relationship.Relationship;

/**
 * Parses all input arguments and create a new AddRelCommand object
 */
public class SetRelCommandParser implements Parser<SetRelCommand> {

    public static final String ONE_RELATIONSHIP_ALLOWED = "Only one relationship allowed when adding or deleting "
        + "relationship between two persons.";
    public static final String NULL_RELATION_INPUT = "Relationship entered should not be empty.";
    public static final String SAME_INDEX_ERROR = "Index of the two persons must be different.";
    /**
     * Parses the given {@code String} of arguments in the context of the SetRelCommand
     * and returns an SetRelCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetRelCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADD_RELATIONSHIP,
            PREFIX_DELETE_RELATIONSHIP, PREFIX_CLEAR_RELATIONSHIP);
        Index indexOne;
        Index indexTwo;
        boolean addPrefixPresent = false;
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_ADD_RELATIONSHIP, PREFIX_DELETE_RELATIONSHIP,
            PREFIX_CLEAR_RELATIONSHIP)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));
        }
        try {
            indexOne = ParserUtil.parseIndex(argMultimap.getPreamble().split("\\s+")[0]);
            indexTwo = ParserUtil.parseIndex(argMultimap.getPreamble().split("\\s+")[1]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));
        }
        if (indexOne.equals(indexTwo)) {
            throw new ParseException(SAME_INDEX_ERROR);
        }
        if (!argMultimap.containsOnePrefix(PREFIX_ADD_RELATIONSHIP)
            || !argMultimap.containsOnePrefix(PREFIX_DELETE_RELATIONSHIP)) {
            throw new ParseException(String.format(ONE_RELATIONSHIP_ALLOWED, SetRelCommand.MESSAGE_USAGE));
        }
        if (areAnyPrefixesPresent(argMultimap, PREFIX_ADD_RELATIONSHIP)) {
            addPrefixPresent = true;
            String addRelString = argMultimap.getValue(PREFIX_ADD_RELATIONSHIP).get();
            requireNonNull(addRelString);
            if (addRelString.length() == 0) {
                throw new ParseException(NULL_RELATION_INPUT);
            }
        }
        if (areAnyPrefixesPresent(argMultimap, PREFIX_DELETE_RELATIONSHIP)) {
            String addRelString = argMultimap.getValue(PREFIX_DELETE_RELATIONSHIP).get();
            requireNonNull(addRelString);
            if (addRelString.length() == 0) {
                throw new ParseException(NULL_RELATION_INPUT);
            }
        }
        EditPerson editPerson = new EditPerson();
        try {
            parseRelForEdit(argMultimap.getAllValues(PREFIX_ADD_RELATIONSHIP)).ifPresent
                (editPerson::setToAdd);
            parseRelForEdit(argMultimap.getAllValues(PREFIX_DELETE_RELATIONSHIP)).ifPresent
                (editPerson::setToDelete);
            editPerson.setClearRels(argMultimap.containsPrefix(PREFIX_CLEAR_RELATIONSHIP));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new SetRelCommand(indexOne, indexTwo, editPerson, addPrefixPresent);
    }
    /**
     * Parses {@code Collection<String> relationships} into a {@code Set<Relationship>}
     * if {@code relationships} is non-empty.
     * If {@code relationship} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Relationship>} containing zero relationships.
     */
    private Optional<Set<Relationship>> parseRelForEdit(Collection<String> rel) throws IllegalValueException {
        assert rel != null;
        if (rel.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> relSet = rel.size() == 1 && rel.contains("") ? Collections.emptySet() : rel;
        return Optional.of(ParserUtil.parseRels(relSet));
    }

    /**
     * Checks if any of the prefixes are present
     * return true if the certain prefix is present and false if the certain prefix is not present
     * @param argumentMultimap
     * @param prefixes
     * @return true || false
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/logic/parser/SortCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedValue = args.trim();
        if (trimmedValue.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(trimmedValue);
    }

}
```
###### /java/seedu/address/logic/parser/CheckCommandsParser.java
``` java
package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Parses the command entered by the user
 * and checks it with the synonyms provided
 * so that the user does not have to remember specific commands
 */
public class CheckCommandsParser {
    /**
     * Parsers user input command and match it with the synonyms/aliases
     *
     * @param userCommand user input command string
     * @return the relevant command it matches
     */
    public String matchCommand(String userCommand) {
        /**
         * sets the initial finalUserCommand to error
         */
        String finalUserCommand = "error";

        /**
         * used to store the userCommand in a set
         */
        Set<String> userInputCommand = new HashSet<>();
        userInputCommand.add(userCommand);

        /**
         * All the synonyms/aliases of the different commands
         */
        final String[] subAddCommands = new String[] { "add", "a", "input", "create", "insert" };
        final String[] subClearCommands = new String[] { "clear", "c", "empty", "clean" };
        final String[] subDeleteCommands = new String[] { "delete", "d", "remove", "throw", "erase" };
        final String[] subEditCommands = new String[] { "edit", "e", "change", "revise", "modify" };
        final String[] subExitCommands = new String[] { "exit", "quit" };
        final String[] subFindCommands = new String[] { "find", "f", "look", "search", "check" };
        final String[] subHelpCommands = new String[] { "help", "info" };
        final String[] subHistoryCommands = new String[] { "history", "h", "past" };
        final String[] subListCommands = new String[] { "list", "l", "show", "display" };
        final String[] subRedoCommands = new String[] { "redo", "r" };
        final String[] subSelectCommands = new String[] { "select", "s", "choose", "pick" };
        final String[] subSortCommands = new String[] { "sort", "arrange", "organise" };
        final String[] subUndoCommands = new String[] { "undo", "u" };
        final String[] subCheckScheduleCommands = new String[] {"thisweek",
            "schedule", "checkschedule", "tw", "cs"};
        final String[] subAddEventsCommands = new String[] { "eventadd", "addevent", "ae", "ea" };
        final String[] subDeleteEventsCommands = new String[] { "eventdel",
            "delevent", "deleteevent", "eventdelete", "de", "ed" };
        final String[] subEditEventsCommands = new String[] { "eventedit", "editevent", "ee" };
        final String[] subFindEventsCommands = new String[] { "eventfind", "findevent", "fe", "ef" };
        final String[] subUpdatePhotoCommands = new String[] { "updatephoto", "up" };
        final String[] subSetRelCommands = new String[] { "set", "rel", "setrel" };
        final String[] subToggleTimetableCommands = new String[] { "timetable", "tt" };


        /**
         * Sets all the strings in each command into a HashSet
         */
        final Set<String> commandsForAdd = new HashSet<>(Arrays.asList(subAddCommands));
        final Set<String> commandsForClear = new HashSet<>(Arrays.asList(subClearCommands));
        final Set<String> commandsForDelete = new HashSet<>(Arrays.asList(subDeleteCommands));
        final Set<String> commandsForEdit = new HashSet<>(Arrays.asList(subEditCommands));
        final Set<String> commandsForExit = new HashSet<>(Arrays.asList(subExitCommands));
        final Set<String> commandsForFind = new HashSet<>(Arrays.asList(subFindCommands));
        final Set<String> commandsForHelp = new HashSet<>(Arrays.asList(subHelpCommands));
        final Set<String> commandsForHistory = new HashSet<>(Arrays.asList(subHistoryCommands));
        final Set<String> commandsForList = new HashSet<>(Arrays.asList(subListCommands));
        final Set<String> commandsForRedo = new HashSet<>(Arrays.asList(subRedoCommands));
        final Set<String> commandsForSelect = new HashSet<>(Arrays.asList(subSelectCommands));
        final Set<String> commandsForSort = new HashSet<>(Arrays.asList(subSortCommands));
        final Set<String> commandsForUndo = new HashSet<>(Arrays.asList(subUndoCommands));
        final Set<String> commandsForCheckSchedule = new HashSet<>(Arrays.asList
                (subCheckScheduleCommands));
        final Set<String> commandsForAddEvent = new HashSet<>(Arrays.asList(subAddEventsCommands));
        final Set<String> commandsForDeleteEvent = new HashSet<>(Arrays.asList(subDeleteEventsCommands));
        final Set<String> commandsForEditEvent = new HashSet<>(Arrays.asList(subEditEventsCommands));
        final Set<String> commandsForFindEvent = new HashSet<>(Arrays.asList(subFindEventsCommands));
        final Set<String> commandsForUpdatePhoto = new HashSet<>(Arrays.asList(subUpdatePhotoCommands));
        final Set<String> commandsForSetRel = new HashSet<>(Arrays.asList(subSetRelCommands));
        final Set<String> commandsForToggleTimetable = new HashSet<>(Arrays.asList(subToggleTimetableCommands));

        /**
         * Compares the userInputCommand with the different commands set
         */
        if (!Collections.disjoint(userInputCommand, commandsForAdd)) {
            finalUserCommand = "add";
        } else if (!Collections.disjoint(userInputCommand, commandsForClear)) {
            finalUserCommand = "clear";
        } else if (!Collections.disjoint(userInputCommand, commandsForDelete)) {
            finalUserCommand = "delete";
        } else if (!Collections.disjoint(userInputCommand, commandsForEdit)) {
            finalUserCommand = "edit";
        } else if (!Collections.disjoint(userInputCommand, commandsForExit)) {
            finalUserCommand = "exit";
        } else if (!Collections.disjoint(userInputCommand, commandsForFind)) {
            finalUserCommand = "find";
        } else if (!Collections.disjoint(userInputCommand, commandsForHelp)) {
            finalUserCommand = "help";
        } else if (!Collections.disjoint(userInputCommand, commandsForHistory)) {
            finalUserCommand = "history";
        } else if (!Collections.disjoint(userInputCommand, commandsForList)) {
            finalUserCommand = "list";
        } else if (!Collections.disjoint(userInputCommand, commandsForRedo)) {
            finalUserCommand = "redo";
        } else if (!Collections.disjoint(userInputCommand, commandsForSelect)) {
            finalUserCommand = "select";
        } else if (!Collections.disjoint(userInputCommand, commandsForSort)) {
            finalUserCommand = "sort";
        } else if (!Collections.disjoint(userInputCommand, commandsForUndo)) {
            finalUserCommand = "undo";
        } else if (!Collections.disjoint(userInputCommand, commandsForCheckSchedule)) {
            finalUserCommand = "thisweek";
        } else if (!Collections.disjoint(userInputCommand, commandsForAddEvent)) {
            finalUserCommand = "eventadd";
        } else if (!Collections.disjoint(userInputCommand, commandsForDeleteEvent)) {
            finalUserCommand = "eventdel";
        } else if (!Collections.disjoint(userInputCommand, commandsForEditEvent)) {
            finalUserCommand = "eventedit";
        } else if (!Collections.disjoint(userInputCommand, commandsForFindEvent)) {
            finalUserCommand = "eventfind";
        } else if (!Collections.disjoint(userInputCommand, commandsForUpdatePhoto)) {
            finalUserCommand = "updatephoto";
        } else if (!Collections.disjoint(userInputCommand, commandsForSetRel)) {
            finalUserCommand = "set";
        } else if (!Collections.disjoint(userInputCommand, commandsForToggleTimetable)) {
            finalUserCommand = "timetable";
        }
        return finalUserCommand;
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.exceptions.InvalidSortTypeException;

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_UNKNOWN_SORT_TYPE = "Sorting type not found";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list according to either name, tag, "
            + "company, priority, status\n"
            + "Parameters: TYPE  (name, tag, company, priority, status)\n"
            + "Example: " + COMMAND_WORD + " name";
    public static final String MESSAGE_SUCCESS = "Sorted according to ";
    private final String type;

    public SortCommand(String type) {
        this.type = type;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.sortPersonList(type);
            return new CommandResult(MESSAGE_SUCCESS + type);
        } catch (InvalidSortTypeException iste) {
            return new CommandResult(MESSAGE_UNKNOWN_SORT_TYPE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.type == (((SortCommand) other).type)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/relationship/SetRelCommand.java
``` java
/**
 * Sets the relationship between two persons
 */
public class SetRelCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the relationship between two persons."
        + "by the index number used in the last person listing.\n"
        + "For adding of relationship, only one relationship is allowed. This command is able to add and delete\n "
        + "specifc relationship between two persons and clear deletes all relationships the two persons have.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_ADD_RELATIONSHIP + "RELATIONSHIP]\n"
        + "Example: " + COMMAND_WORD + " 1 2 "
        + PREFIX_ADD_RELATIONSHIP + "colleagues\n"
        + "Example: " + COMMAND_WORD + " 1 2 "
        + PREFIX_DELETE_RELATIONSHIP + "colleagues\n"
        + "Example: " + COMMAND_WORD + " 1 2 "
        + PREFIX_CLEAR_RELATIONSHIP + "\n";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Relationship Changed for Person: %1$s\n"
        + "& Person: %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NO_MULTIPLE_REL = "These two persons cannot have multiple relationships"
        + " please edit the relationship between them instead.";

    private final Index indexOne;
    private final Index indexTwo;
    private final EditPerson editPerson;
    private final boolean addPrefixPresent;

    /**
     * @param indexOne          first index of the person in the filtered person list to add relationship
     * @param indexTwo          second index of the person in the filtered person list to add relationship
     * @param editPerson        details to edit the person with
     * @param addPrefixPresent  add prefix present or not present
     */
    public SetRelCommand(Index indexOne, Index indexTwo, EditPerson editPerson, boolean addPrefixPresent) {
        requireNonNull(indexOne);
        requireNonNull(indexTwo);

        this.indexOne = indexOne;
        this.indexTwo = indexTwo;
        this.editPerson = editPerson;
        this.addPrefixPresent = addPrefixPresent;
    }
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit, EditPerson editPerson,
                                             ReadOnlyPerson personTwo) {
        assert personToEdit != null;

        Name updatedName = editPerson.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPerson.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPerson.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPerson.getAddress().orElse(personToEdit.getAddress());
        Company updatedCompany = editPerson.getCompany().orElse(personToEdit.getCompany());
        Position updatedPosition = editPerson.getPosition().orElse(personToEdit.getPosition());
        Status updatedStatus = editPerson.getStatus().orElse(personToEdit.getStatus());
        Priority updatedPriority = editPerson.getPriority().orElse(personToEdit.getPriority());
        Note updatedNote = editPerson.getNote().orElse(personToEdit.getNote());
        Photo updatedPhoto = editPerson.getPhoto().orElse(personToEdit
            .getPhoto());
        Set<Tag> updatedTags = editPerson.getTags().orElse(personToEdit.getTags());
        Set<Relationship> relation = personToEdit.getRelation();
        final Set<Relationship> updatedRel = new HashSet<>();
        if (!editPerson.shouldClear()) {
            updatedRel.addAll(relation);
        }
        editPerson.getToAddRel(personTwo).ifPresent(updatedRel::addAll);
        editPerson.getToDeleteRel(personTwo).ifPresent(updatedRel::removeAll);
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedCompany,
        updatedPosition, updatedStatus, updatedPriority, updatedNote, updatedPhoto, updatedTags, updatedRel);
    }
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if ((indexOne.getZeroBased() >= lastShownList.size()) || (indexTwo.getZeroBased() >= lastShownList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEditOne = lastShownList.get(indexOne.getZeroBased());
        ReadOnlyPerson personToEditTwo = lastShownList.get(indexTwo.getZeroBased());
        Person editedPersonOne = createEditedPerson(personToEditOne, editPerson, personToEditTwo);
        Person editedPersonTwo = createEditedPerson(personToEditTwo, editPerson, personToEditOne);

        if (addPrefixPresent && (personToEditOne.getRelation().toString().contains(personToEditTwo.getName().toString())
            || personToEditTwo.getRelation().toString().contains(personToEditOne.getName().toString()))) {
            throw new CommandException(MESSAGE_NO_MULTIPLE_REL);
        }
        try {
            model.updatePerson(personToEditOne, editedPersonOne);
            model.updatePerson(personToEditTwo, editedPersonTwo);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPersonOne, editedPersonTwo));
    }
```
###### /java/seedu/address/logic/commands/relationship/SetRelCommand.java
``` java
        public Optional<Set<Relationship>> getToAddRel(ReadOnlyPerson person) {
            Set<Relationship> relWithName = new HashSet<>();
            if ((addRel == null)) {
                return Optional.ofNullable(null);
            }
            relWithName.add(new Relationship(person.getName().toString(), addRel));

            return Optional.of(relWithName);
        }
```
###### /java/seedu/address/logic/commands/relationship/SetRelCommand.java
``` java
        public Optional<Set<Relationship>> getToDeleteRel(ReadOnlyPerson person) {
            Set<Relationship> relWithName = new HashSet<>();
            relWithName.add(new Relationship(person.getName().toString(), deleteRel));
            return Optional.of(relWithName);
        }
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @XmlElement
    private List<XmlAdaptedRelationship> relation;
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
        relation = new ArrayList<>();
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
        relation.addAll(src.getRelList().stream().map(XmlAdaptedRelationship::new).collect(Collectors.toList()));
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Relationship> getRelList() {
        final ObservableList<Relationship> rel = this.relation.stream().map(r -> {
            try {
                return r.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(rel);
    }
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
    /**
     * Returns a relationship set containing the list of strings given.
     */
    public static Set<Relationship> getRelSet(String... strings) throws IllegalValueException {
        HashSet<Relationship> rel = new HashSet<>();
        for (String s : strings) {
            rel.add(new Relationship(s));
        }
        return rel;
    }
```
###### /java/seedu/address/model/person/Status.java
``` java
    /**
     * Comparator to compare the statuses to sort them with NIL all the way at the bottom.
     */
    public int compareTo(Status statusOne) {
        if (ORDERED_ENTRIES.contains(statusOne.toString()) && ORDERED_ENTRIES.contains(this.toString())) {
            return ORDERED_ENTRIES.indexOf(statusOne.toString()) - ORDERED_ENTRIES.indexOf(this.toString());
        }
        if (ORDERED_ENTRIES.contains(statusOne.toString())) {
            return -1;
        }
        if (ORDERED_ENTRIES.contains(this.toString())) {
            return 1;
        }
        return statusOne.toString().compareTo(this.toString());
    }
    @Override
    public int compare(Status statusOne, Status statusTwo) {
        return statusOne.compareTo(statusTwo);
    }
```
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    Set<Relationship> getRelation();
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts the person list according to the type entered by user
     * @param type                          Sorting type entered by user
     * @throws InvalidSortTypeException     Indicates that the sorting type entered by user is not valid
     */
    public void sortPerson(String type) throws InvalidSortTypeException {
        final Comparator<Person> sortByName = (
            Person a, Person b) -> a.getName().toString().compareToIgnoreCase(b.getName().toString());
        final Comparator<Person> sortByTags = (Person a, Person b) -> a.getTags().toString().compareToIgnoreCase((b
                .getTags().toString()));
        final Comparator<Person> sortByCompany = (Person a, Person b) -> b.getCompany().compareTo(a
                .getCompany());
        final Comparator<Person> sortByPosition = (Person a, Person b) -> b.getPosition().compareTo(a
                .getPosition());
        final Comparator<Person> sortByPriority = (Person a, Person b) -> a.getPriority()
                .compareTo(b.getPriority());
        switch (type) {
        case "name":
            internalList.sort(sortByName);
            break;
        case "tag":
            internalList.sort(sortByTags.thenComparing(sortByName));
            break;
        case "company":
            internalList.sort(sortByCompany.thenComparing(sortByName));
            break;
        case "priority":
            internalList.sort(sortByPriority.thenComparing(sortByName));
            break;
        case "position":
            internalList.sort(sortByPosition.thenComparing(sortByName));
            break;
        default:
            throw new InvalidSortTypeException(
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
```
###### /java/seedu/address/model/person/exceptions/InvalidSortTypeException.java
``` java
package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class InvalidSortTypeException extends IllegalValueException {
    public InvalidSortTypeException(String message) {
        super(message);
    }
}
```
###### /java/seedu/address/model/person/Position.java
``` java
    /**
     * Comparator to compare the positions to sort them with NIL all the way at the bottom.
     * @param position      either a work position or NIL
     */
    public int compareTo(Position position) {
        if (ORDERED_ENTRIES.contains(position.toString()) && ORDERED_ENTRIES.contains(this.toString())) {
            return ORDERED_ENTRIES.indexOf(position.toString()) - ORDERED_ENTRIES.indexOf(this.toString());
        }
        if (ORDERED_ENTRIES.contains(position.toString())) {
            return 1;
        }
        if (ORDERED_ENTRIES.contains(this.toString())) {
            return -1;
        }
        return position.toString().compareTo(this.toString());
    }
    @Override
    public int compare(Position positionOne, Position positionTwo) {
        return positionOne.compareTo(positionTwo);
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Returns an immutable relationship set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Relationship> getRelation() {
        return Collections.unmodifiableSet(relation.get().toSet());
    }

    /**
     * Replaces this person's relationship with the relationships in the argument relationship set.
     */
    public void setRel(Set<Relationship> replacement) {
        relation.set(new UniqueRelList(replacement));
    }
```
###### /java/seedu/address/model/person/Company.java
``` java
    /**
     * Comparator to compare the company to sort them with NIL all the way at the bottom.
     * @param company       either a company name or NIL
     */
    public int compareTo(Company company) {
        if (ORDERED_ENTRIES.contains(company.toString()) && ORDERED_ENTRIES.contains(this.toString())) {
            return ORDERED_ENTRIES.indexOf(this.toString()) - ORDERED_ENTRIES.indexOf(company.toString());
        }
        if (ORDERED_ENTRIES.contains(company.toString())) {
            return 1;
        }
        if (ORDERED_ENTRIES.contains(this.toString())) {
            return -1;
        }
        return company.toString().compareTo(this.toString());
    }
    @Override
    public int compare(Company companyOne, Company companyTwo) {
        return companyOne.compareTo(companyTwo);
    }
```
###### /java/seedu/address/model/person/Priority.java
``` java
    /**
     * Comparator to compare the priorities to sort them according to H, M, L.
     * H being the top and L being the bottom.
     * @param priority      either H, M or L.
     */
    public int compareTo(Priority priority) {
        if (ORDERED_ENTRIES.contains(priority.toString()) && ORDERED_ENTRIES.contains(this.toString())) {
            return ORDERED_ENTRIES.indexOf(priority.toString()) - ORDERED_ENTRIES.indexOf(this.toString());
        }
        if (ORDERED_ENTRIES.contains(priority.toString())) {
            return -1;
        }
        if (ORDERED_ENTRIES.contains(this.toString())) {
            return 1;
        }
        return priority.toString().compareTo(this.toString());
    }
    @Override
    public int compare(Priority priorityOne, Priority priorityTwo) {
        return priorityOne.compareTo(priorityTwo);
    }
```
###### /java/seedu/address/model/ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the relationships list.
     * This list will not contain any duplicate relationships.
     */
    ObservableList<Relationship> getRelList();
```
###### /java/seedu/address/model/AddressBook.java
``` java
    private final UniqueRelList relation;
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setRel(Set<Relationship> relation) {
        this.relation.setRel(relation);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
        setRel(new HashSet<>(newData.getRelList()));
        syncMasterRelListWith(persons);
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Sorts the list according to name, tag, company, priority and position
     */
    public void sortPersonList(String type) throws InvalidSortTypeException {
        persons.sortPerson(type);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Ensures that every relationships in this person:
     * - exists in the master list {@link #relation}
     * - points to a Relationship object in the master list
     */
    private void syncMasterRelListWith(Person person) {
        final UniqueRelList personRel = new UniqueRelList(person.getRelation());
        relation.mergeFrom(personRel);

        // Create map with values = Relationship object references in the master list
        // used for checking person relation references
        final Map<Relationship, Relationship> masterRelObjects = new HashMap<>();
        relation.forEach(rel -> masterRelObjects.put(rel, rel));

        // Rebuild the list of person relations to point to the relevant relations in the master relation list.
        final Set<Relationship> correctRelReferences = new HashSet<>();
        personRel.forEach(rel -> correctRelReferences.add(masterRelObjects.get(rel)));
        person.setRel(correctRelReferences);
    }
    /**
     * Ensures that every relation in these persons:
     * - exists in the master list {@link #relation}
     * - points to a Relationship object in the master list
     *
     * @see #syncMasterRelListWith(Person)
     */
    private void syncMasterRelListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterRelListWith);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    @Override
    public ObservableList<Relationship> getRelList() {
        return relation.asObservableList();
    }
```
###### /java/seedu/address/model/relationship/UniqueRelList.java
``` java
package seedu.address.model.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of relationships that enforces no nulls and uniqueness between its elements.
 * <p>
 * Supports minimal set of list operations for the app's features.
 *
 * @see Relationship#equals(Object)
 */
public class UniqueRelList implements Iterable<Relationship> {

    private final ObservableList<Relationship> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty RelList.
     */
    public UniqueRelList() {
    }

    /**
     * Creates a UniqueRelList using given rels.
     * Enforces no nulls.
     */
    public UniqueRelList(Set<Relationship> rel) {
        requireAllNonNull(rel);
        internalList.addAll(rel);

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
    public void setRel(Set<Relationship> rel) {
        requireAllNonNull(rel);
        internalList.setAll(rel);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every rel in the argument list exists in this object.
     */
    public void mergeFrom(UniqueRelList from) {
        final Set<Relationship> alreadyInside = this.toSet();
        from.internalList.stream()
        .filter(rel -> !alreadyInside.contains(rel))
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
     *
     * @throws DuplicateRelException if the Relationship to add is a duplicate of an existing Relationship in the list.
     */
    public void add(Relationship toAdd) throws DuplicateRelException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRelException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes a Relationship from the list if it is present.
     *
     * @param toRemove Relationship to be removed
     */
    public void remove(Relationship toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
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
            || (other instanceof UniqueRelList // instanceof handles nulls
            && this.internalList.equals(((UniqueRelList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueRelList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateRelException extends DuplicateDataException {
        protected DuplicateRelException() {
            super("Operation would result in duplicate relationship");
        }
    }

}
```
###### /java/seedu/address/model/relationship/Relationship.java
``` java
package seedu.address.model.relationship;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Relationship in the address book.
 * Guarantees: immutable; name is valid as declared in
 */
public class Relationship {

    public static final String MESSAGE_REL_CONSTRAINTS = "Relationship types should be alphabetical";
    public static final String MESSAGE_REL_PREFIX_NOT_ALLOWED =
        "Relationship prefix(es) is/are not allowed in this command.";
    public static final String REL_VALIDATION_REGEX = "[a-zA-Z0-9\\- ]+\\[[a-zA-Z0-9\\-]+.\\]|[a-zA-Z0-9\\-]+";

    public final String relType;

    /**
     * Validates given relationship name.
     *
     * @throws IllegalValueException if the given relationship name string is invalid.
     */
    public Relationship(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidRelType(trimmedName)) {
            throw new IllegalValueException(MESSAGE_REL_CONSTRAINTS);
        }
        this.relType = name;
    }
    /**
     * Concatenates the name of the given person and the relationship to form a string
     * @param name          Name of the person
     * @param relation      Relationship of the person
     */
    public Relationship(String name, Set<Relationship> relation) {
        this.relType = name + " " +  relation;
    }
    /**
     * Returns true if a given string is a valid relationship name.
     */
    public static boolean isValidRelType(String test) {
        return test.matches(REL_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Relationship // instanceof handles nulls
            && this.relType.equals(((Relationship) other).relType)); // state check
    }

    @Override
    public int hashCode() {
        return relType.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return relType;
    }

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void sortPersonList(String type) throws InvalidSortTypeException {
        addressBook.sortPersonList(type);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Sorts the person list according to user input option
     */
    void sortPersonList(String type) throws InvalidSortTypeException;
    //@author
    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

```
