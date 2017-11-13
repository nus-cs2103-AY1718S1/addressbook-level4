# WangJieee
###### /java/seedu/address/logic/commands/AddTagCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds the specified tags to the tag list of an existing person in the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the entered tag(s) to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[TAG]..."
            + "Example: " + COMMAND_WORD + " 1 " + "friends classmates ";

    public static final String MESSAGE_ADD_TAG_PERSON_SUCCESS = "Tags updated!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TAG = "You have entered a duplicate tag! Tag name: ";

    private final Index index;
    private final Set<Tag> tagsToAdd;

    /**
     * @param index of the person in the filtered person list to edit
     * @param tagsToAdd details to add existing tags with
     */
    public AddTagCommand(Index index, Set<Tag> tagsToAdd) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);

        this.index = index;
        this.tagsToAdd = tagsToAdd;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createUpdatedPerson(personToEdit, tagsToAdd);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TAG_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code tagsToAdd}.
     */
    private static Person createUpdatedPerson(ReadOnlyPerson personToEdit,
                                             Set<Tag> tagsToAdd) throws CommandException {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Avatar updatedAvatar = personToEdit.getAvatar();
        NokName updatedNokName = personToEdit.getNokName();
        NokPhone updatedNokPhone = personToEdit.getNokPhone();

        Set<Tag> existingTags = personToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();
        try {
            updatedTags = getUpdatedTags(existingTags, tagsToAdd);
        } catch (DuplicateTagException dte) {
            throw new CommandException(dte.getMessage());
        }
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedAvatar,
                          updatedNokName, updatedNokPhone, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand a = (AddTagCommand) other;
        return index.equals(a.index)
                && tagsToAdd.equals(a.tagsToAdd);
    }

    /**
     * Creates and returns a new tag list that combines existing tags and new tags added
     * with no duplicate tags
     */
    public static HashSet<Tag> getUpdatedTags(Set<Tag> oldTags, Set<Tag> tagsToAdd) throws DuplicateTagException {
        HashSet<Tag> updatedTags = new HashSet<>(oldTags);
        for (Tag toAdd : tagsToAdd) {
            requireNonNull(toAdd);
            if (oldTags.contains(toAdd)) {
                throw new DuplicateTagException(toAdd.tagName);
            }
            updatedTags.add(toAdd);
        }
        return updatedTags;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException(String tagName) {
            super(MESSAGE_DUPLICATE_TAG + tagName);
        }

        @Override
        public String getMessage() {
            return super.getMessage();
        }
    }
}
```
###### /java/seedu/address/logic/commands/FilterCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.PersonHasTagPredicate;

/**
 * Filters and lists all persons in address book whose tag list contains any of the argument tags
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tag lists contain any of "
            + "the specified tags(case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friends families";

    private final PersonHasTagPredicate predicate;

    public FilterCommand(PersonHasTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/RemoveTagCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds the specified tags to the tag list of an existing person in the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the entered tag(s) to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[TAG]..."
            + "Example: " + COMMAND_WORD + " 1 " + "friends classmates ";

    public static final String MESSAGE_REMOVE_TAG_PERSON_SUCCESS = "Tags updated!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_NOT_FOUND = "This person does not have the tag! Tag name: ";

    private final Index index;
    private final Set<Tag> tagsToRemove;

    /**
     * @param index of the person in the filtered person list to edit
     * @param tagsToRemove details to remove existing tags with
     */
    public RemoveTagCommand(Index index, Set<Tag> tagsToRemove) {
        requireNonNull(index);
        requireNonNull(tagsToRemove);

        this.index = index;
        this.tagsToRemove = tagsToRemove;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createUpdatedPerson(personToEdit, tagsToRemove);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code tagsToRemove}.
     */
    private static Person createUpdatedPerson(ReadOnlyPerson personToEdit,
                                              Set<Tag> tagsToRemove) throws CommandException {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Avatar updatedAvatar = personToEdit.getAvatar();
        NokName updatedNokName = personToEdit.getNokName();
        NokPhone updatedNokPhone = personToEdit.getNokPhone();

        Set<Tag> existingTags = personToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();
        try {
            updatedTags = getUpdatedTags(existingTags, tagsToRemove);
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(tnfe.getMessage());
        }

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedAvatar,
                          updatedNokName, updatedNokPhone, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        // state check
        RemoveTagCommand a = (RemoveTagCommand) other;
        return index.equals(a.index)
                && tagsToRemove.equals(a.tagsToRemove);
    }

    /**
     * Creates and returns a new tag list that removes specified tags from existing tags
     */
    public static HashSet<Tag> getUpdatedTags(Set<Tag> oldTags, Set<Tag> tagsToRemove) throws TagNotFoundException {
        HashSet<Tag> updatedTags = new HashSet<>(oldTags);
        for (Tag toRemove : tagsToRemove) {
            requireNonNull(toRemove);
            if (!oldTags.contains(toRemove)) {
                throw new TagNotFoundException(toRemove.tagName);
            }
            updatedTags.remove(toRemove);
        }
        return updatedTags;
    }

    /**
     * Signals that an operation does not fulfill the constraint that all the tags entered must exist in the person
     */
    public static class TagNotFoundException extends IllegalValueException {
        protected TagNotFoundException(String tagName) {
            super(MESSAGE_TAG_NOT_FOUND + tagName);
        }

        @Override
        public String getMessage() {
            return super.getMessage();
        }
    }
}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObjectProperty<UniqueTagList> getRealTagList() {
        return model.getRealTagList();
    }
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case RemoveTagCommand.COMMAND_WORD:
            return new RemoveTagCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/AddTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Index index;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split("\\s+");
        List<String> argList = Arrays.asList(arguments);

        try {
            index = ParserUtil.parseIndex(argList.get(0));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        if (argList.size() == 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        }

        List<String> tagList = argList.subList(1, argList.size());
        Set<Tag> tagSetToAdd;

        try {
            tagSetToAdd = parseTagsToAdd(tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddTagCommand(index, tagSetToAdd);
    }

    private Set<Tag> parseTagsToAdd(List<String> tagList) throws IllegalValueException {
        assert tagList != null;
        return ParserUtil.parseTags(tagList);
    }
}
```
###### /java/seedu/address/logic/parser/FilterCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasTagPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<Command> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] tags = trimmedArgs.split("\\s+");

        return new FilterCommand(new PersonHasTagPredicate(Arrays.asList(tags)));
    }
}
```
###### /java/seedu/address/logic/parser/RemoveTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Index index;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split("\\s+");
        List<String> argList = Arrays.asList(arguments);

        try {
            index = ParserUtil.parseIndex(argList.get(0));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        if (argList.size() == 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        List<String> tagList = argList.subList(1, argList.size());
        Set<Tag> tagSetToRemove;

        try {
            tagSetToRemove = parseTagsToRemove(tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new RemoveTagCommand(index, tagSetToRemove);
    }

    private Set<Tag> parseTagsToRemove(List<String> tagList) throws IllegalValueException {
        assert tagList != null;
        return ParserUtil.parseTags(tagList);
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Updates the {@code tagPersonMap} with {@code updatedPerson}.
     */
    private void updateTagPersonMap(Person updatedPerson) {
        final UniqueTagList personTags = new UniqueTagList(updatedPerson.getTags());
        Set<Tag> tagSet = tagPersonMap.keySet();
        for (Tag tag: personTags) {
            if (!tagSet.contains(tag)) {
                //add a new key to the tagPersonMap if a new tag is introduced
                ArrayList<Person> newPersonList = new ArrayList<>();
                newPersonList.add(updatedPerson);
                tagPersonMap.put(tag, newPersonList);
            } else {
                //add the person to the tagPersonMap for the tag
                if (!tagPersonMap.get(tag).contains(updatedPerson)) {
                    tagPersonMap.get(tag).add(updatedPerson);
                }
            }
        }

        for (Tag tag: tagSet) {
            if (tagPersonMap.get(tag).contains(updatedPerson) && !personTags.contains(tag)) {
                //remove the person from the tagPersonMap for the tag
                tagPersonMap.get(tag).remove(updatedPerson);
            }
        }

        for (Iterator<Map.Entry<Tag, ArrayList<Person>>> itr = tagPersonMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry<Tag, ArrayList<Person>> entry = itr.next();
            if (entry.getValue().isEmpty()) {
                itr.remove();
            }
        }
    }

    /**
     * Updates the {@code tagPersonMap} with {@code removedPerson}.
     */
    private void updateTagPersonMapRemovePerson(ReadOnlyPerson removedPerson) {
        Set<Tag> tagSet = tagPersonMap.keySet();
        for (Tag tag: tagSet) {
            if (tagPersonMap.get(tag).contains(removedPerson)) {
                //remove the person from the tagPersonMap for the tag
                tagPersonMap.get(tag).remove(removedPerson);
            }
        }

        for (Iterator<Map.Entry<Tag, ArrayList<Person>>> itr = tagPersonMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry<Tag, ArrayList<Person>> entry = itr.next();
            if (entry.getValue().isEmpty()) {
                itr.remove();
            }
        }
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * Update {@code tagPersonMap}
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            updateTagPersonMapRemovePerson(key);
            realTags.set(new UniqueTagList(tagPersonMap.keySet()));
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Returns a tag list containing the existing tags
     */
    public ObjectProperty<UniqueTagList> getRealTagList() {
        return realTags;
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /** Returns an unmodifiable view of the list containing existing tags */
    ObjectProperty<UniqueTagList> getRealTagList();
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public ObjectProperty<UniqueTagList> getRealTagList() {
        return addressBook.getRealTagList();
    }
```
###### /java/seedu/address/model/person/PersonHasTagPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code UniqueTagList} contains the specific tag.
 */
public class PersonHasTagPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> tags;

    public PersonHasTagPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tagSet = person.getTags();
        for (Tag t: tagSet) {
            if (tags.stream().anyMatch(tag -> StringUtil.containsWordIgnoreCase(t.tagName, tag))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonHasTagPredicate // instanceof handles nulls
                && this.tags.equals(((PersonHasTagPredicate) other).tags)); // state check
    }
}
```
###### /java/seedu/address/model/tag/Tag.java
``` java
    private static String[] colors = {"CornflowerBlue", "Tomato", "DarkSlateGray", "Crimson", "DarkBlue", "DarkGreen",
                                      "FireBrick", "OrangeRed", "Orchid", "blue", "Gold", "red", "MediumSeaGreen",
                                      "PaleVioletRed", "Peru", "RebeccaPurple", "RoyalBlue", "SeaGreen", "Coral",
                                      "DarkOrange", "DarkOliveGreen", "DarkRed", "DarkSalmon", "DarkSeaGreen", "Teal"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static int colourIndex = 0;
    public final String tagName;
    public final String tagColour;
```
###### /java/seedu/address/model/tag/Tag.java
``` java
    /**
     * Assign a color to a tag if it does not have an existing color.
     * @return the color assigned to that tag
     */
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[colourIndex]);
            updateColourIndex();
        }

        return tagColors.get(tagValue);
    }

    /**
     * update the index of colour
     */
    private static void updateColourIndex() {
        if (colourIndex == colors.length - 1) {
            colourIndex = 0;
        } else {
            colourIndex++;
        }
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
        tagPane = new TagPane(logic.getRealTagList());
        tagPanePlaceholder.getChildren().add(tagPane.getRoot());
```
###### /java/seedu/address/ui/TagPane.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Panel containing the list of all tags.
 */
public class TagPane extends UiPart<Region> {
    private static final String FXML = "TagPane.fxml";
    private static final Logger logger = LogsCenter.getLogger(TagPane.class);

    private final ObjectProperty<UniqueTagList> tagList;

    @FXML
    private FlowPane totalTags;

    public TagPane(ObjectProperty<UniqueTagList> tagListCopy) {
        super(FXML);
        tagList = tagListCopy;
        initTags();
        bindListener();
    }

    /**
     * Creates a tag label for every unique {@code Tag} and sets a color for each tag label.
     */
    private void initTags() {
        for (Tag tag: tagList.get()) {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + tag.tagColour + ";");
            totalTags.getChildren().add(tagLabel);
        }
    }

    /**
     * Binds the tags
     * so that they will be notified of any changes.
     */
    public void bindListener() {
        tagList.addListener((v, oldValue, newValue) -> {
            totalTags.getChildren().clear();
            initTags();
        });
    }
}


```
###### /resources/view/CrayonTheme.css
``` css
.background {
    -fx-background-color: derive(#f5f5f5, 20%);
    background-image: url(../images/bg2.png); /* Used in the default.html file */
    background-size: 900px 700px;
}

.container {
    padding-top: 20px;
    padding-right: 100px;
    padding-bottom: 100px;
    padding-left: 100px;
}

.header {
    font-family: "fantasy";
    font-size: 45px;
    color: white;
}

.text {
    font-family: "Verdana";
    color: white;
}
```
###### /resources/view/CrayonTheme.css
``` css
#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 5;
    -fx-font-size: 15;
    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );
}

#totalTags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#totalTags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 5;
    -fx-font-size: 20;
    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );
}
```
###### /resources/view/default.html
``` html
<html>
<head>
    <link rel="stylesheet" href="CrayonTheme.css">
</head>

<body class="background">
    <div class="container">
        <h1 class="header" align="center" >Welcome to AB&B!</h1>
        <p class="text"  align="center">Your No.1 personal contacts manager</p>
    </div>

    <div class="container" align="center">
        <p class="text">
            <img src="../images/gps.png" alt="gps" style="float:left;width:20px;height:20px;">
            All you need is to type simple commands
        </p>
        <p class="text">
            <img src="../images/gps.png" alt="gps" style="float:left;width:20px;height:20px;">
            In-built Google Map to view your contact's address
        </p>
        <p class="text">
            <img src="../images/gps.png" alt="gps" style="float:left;width:20px;height:20px;">
            New user? Type [help] to open help window
        </p>
    </div>

</body>
</html>
```
###### /resources/view/TagPane.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.StackPane?>

<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <FlowPane fx:id="totalTags" prefHeight="20.0">
      <StackPane.margin>
         <Insets left="5.0" right="5.0" />
      </StackPane.margin>
      <padding>
         <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
      </padding>
    </FlowPane>

</StackPane>
```
