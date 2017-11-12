# limyongsong
###### \java\seedu\address\commons\core\PossibleLinks.java
``` java
package seedu.address.commons.core;

/**
 * Container for possible links for link command.
 */
public class PossibleLinks {

    public static final String POSSIBLE_LINK_1 =  "twitter.com/";
    public static final String POSSIBLE_LINK_2 =  "https://www.twitter.com/";
    public static final String POSSIBLE_LINK_3 =  "https://twitter.com/";

    public static final String SEARCH_URL_PREFIX_1 = "https://twitter.com/search?q=";
    public static final String SEARCH_URL_SUFFIX_1 = "&src=typd";

}
```
###### \java\seedu\address\commons\events\ui\NewResultAvailableEvent.java
``` java
    public NewResultAvailableEvent(String message, Boolean isError) {
        this.message = message;
        this.isError = isError;
    }
```
###### \java\seedu\address\logic\commands\AddRemarkCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds remarks to a person in the address book.
 */
public class AddRemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addremark";
    public static final String COMMAND_ALIAS = "ar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a remark to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "REMARK...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Get charger back from him";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person. %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final ArrayList<Remark> remarkArrayList;

    /**
     * @param index of the person in the filtered person list to edit
     * @param remarkArrayList details to add remarks
     */
    public AddRemarkCommand(Index index, ArrayList<Remark> remarkArrayList) {
        requireNonNull(index);
        requireNonNull(remarkArrayList);

        this.index = index;
        this.remarkArrayList = remarkArrayList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        ArrayList<Remark> tempRemarkList;
        tempRemarkList = new ArrayList<>(personToEdit.getRemark());

        for (int i = 0; i < remarkArrayList.size(); i++) {
            tempRemarkList.add(remarkArrayList.get(i));
        }
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), tempRemarkList, personToEdit.getFavouriteStatus(), personToEdit.getTags(),
                personToEdit.getLink());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     *
     * @param personToEdit
     * @return String that shows whether add was successfully done
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_ADD_REMARK_SUCCESS, "\nRemarks: " + personToEdit.getRemark());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddRemarkCommand)) {
            return false;
        }
        // state check
        AddRemarkCommand e = (AddRemarkCommand) other;
        return index.equals(e.index)
                && remarkArrayList.equals(e.remarkArrayList);
    }
}
```
###### \java\seedu\address\logic\commands\LinkCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.PossibleLinks;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Link;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Changes the facebook link of an existing person in the address book.
 */
public class LinkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a facebook link to person identified "
            + "by the index number used in the last person listing. "
            + "Existing links will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LINK + "[LINK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINK + "twitter.com/KingJames";

    public static final String MESSAGE_ADD_LINK_SUCCESS = "Added link to Person: %1$s";
    public static final String MESSAGE_DELETE_LINK_SUCCESS = "Removed link from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Link link;

    /**
     * @param index of the person in the filtered person list to edit the link
     * @param link of the person
     */
    public LinkCommand(Index index, Link link) {
        requireNonNull(index);
        requireNonNull(link);

        this.index = index;
        this.link = link;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!(link.value.startsWith(PossibleLinks.POSSIBLE_LINK_1)
                || link.value.startsWith(PossibleLinks.POSSIBLE_LINK_2)
                || link.value.startsWith(PossibleLinks.POSSIBLE_LINK_3)) && !link.value.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LINK_FORMAT);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getFavouriteStatus(),
                personToEdit.getTags(), link);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     *
     * @param personToEdit
     * @return String that shows whether add or delete was successfully done
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!link.value.isEmpty()) {
            return String.format(MESSAGE_ADD_LINK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_LINK_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LinkCommand)) {
            return false;
        }

        // state check
        LinkCommand e = (LinkCommand) other;
        return index.equals(e.index)
                && link.equals(e.link);
    }
}
```
###### \java\seedu\address\logic\commands\RemoveRemarkCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Removes remarks to a person in the address book.
 */
public class RemoveRemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeremark";
    public static final String COMMAND_ALIAS = "rr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a remark from the person identified by the index number used in the last person listing."
            + "Index of remark to be removed also needs to be identified in the listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "REMARKINDEX...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "2";

    public static final String MESSAGE_REMOVE_REMARK_SUCCESS = "Removed remark from Person. %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final ArrayList<Integer> remarkIndexArrayList;

    /**
     * @param index of the person in the filtered person list to edit
     * @param remarkIndexArrayList details to remove remarks
     */
    public RemoveRemarkCommand(Index index, ArrayList<Integer> remarkIndexArrayList) {
        requireNonNull(index);
        requireNonNull(remarkIndexArrayList);

        this.index = index;
        this.remarkIndexArrayList = remarkIndexArrayList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }


        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        ArrayList<Remark> tempRemarkList = new ArrayList<>(personToEdit.getRemark());
        ArrayList<Remark> emptyRemarkList = new ArrayList<>();
        Person editedPerson;
        if (remarkIndexArrayList.isEmpty()) { //clears all the remarks if no specific remark index is provided
            editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), emptyRemarkList, personToEdit.getFavouriteStatus(),
                    personToEdit.getTags(), personToEdit.getLink());
        } else {
            for (int i = 0; i < remarkIndexArrayList.size(); i++) {
                if (Index.fromOneBased(remarkIndexArrayList.get(i)).getZeroBased() >= tempRemarkList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT);
                }
                tempRemarkList.remove(Index.fromOneBased(remarkIndexArrayList.get(i)).getZeroBased());
            }
            editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), tempRemarkList, personToEdit.getFavouriteStatus(),
                    personToEdit.getTags(), personToEdit.getLink());
        }

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     *
     * @param personToEdit
     * @return String that shows whether remove was successfully done
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_REMOVE_REMARK_SUCCESS, "\nRemarks left: " + personToEdit.getRemark());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemoveRemarkCommand)) {
            return false;
        }
        // state check
        RemoveRemarkCommand e = (RemoveRemarkCommand) other;
        return index.equals(e.index)
                && remarkIndexArrayList.equals(e.remarkIndexArrayList);
    }
}
```
###### \java\seedu\address\logic\parser\AddRemarkCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddRemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new AddRemarkCommand object
 */
public class AddRemarkCommandParser implements Parser<AddRemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddRemarkCommand
     * and returns an AddRemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            ArrayList<Remark> remarkArrayList = ParserUtil.parseRemarks(argMultimap.getAllValues(PREFIX_REMARK));
            return new AddRemarkCommand(index, remarkArrayList);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarkCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\LinkCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Link;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns an LinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LINK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        String link = argMultimap.getValue(PREFIX_LINK).orElse("");

        return new LinkCommand(index, new Link(link));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code Collection<String> remarks} into a {@code ArrayList<Remark>}.
     */
    public static ArrayList<Remark> parseRemarks(Collection<String> remarks) throws IllegalValueException {
        requireNonNull(remarks);
        final ArrayList<Remark> remarkArrayList = new ArrayList<>();
        for (String remarkString : remarks) {
            remarkArrayList.add(new Remark(remarkString));
        }
        if (remarkArrayList.size() < ONE_OBJECT) {
            remarkArrayList.add(new Remark(""));
        }
        return remarkArrayList;
    }

    /**
     * Parses {@code Collection<Integer> indexes} into a {@code ArrayList<Integer>}.
     */
    public static ArrayList<Integer> parseIndexes(Collection<Integer> indexes) throws IllegalValueException {
        requireNonNull(indexes);
        final ArrayList<Integer> indexArrayList = new ArrayList<>();
        for (Integer index : indexes) {
            indexArrayList.add(index);
        }

        return indexArrayList;
    }
```
###### \java\seedu\address\logic\parser\RemoveRemarkCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REMARK_INDEX_FORMAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveRemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveRemarkCommand object
 */
public class RemoveRemarkCommandParser implements Parser<RemoveRemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveRemarkCommand
     * and returns an RemoveRemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveRemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;
        Scanner scan;
        String checkedString;
        String tempString;
        int tempIndex = 0;
        try {
            List<Integer> integerList = new ArrayList<Integer>();
            scan = new Scanner(args);
            if (!scan.hasNextInt()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RemoveRemarkCommand.MESSAGE_USAGE));
            }
            index = ParserUtil.parseIndex(scan.next());
            while (scan.hasNext()) {
                tempString = scan.next();
                //Makes sure index accepted are integers
                if (!tempString.matches("\\d+")) {
                    throw new ParseException(String.format(MESSAGE_INVALID_REMARK_INDEX_FORMAT,
                            RemoveRemarkCommand.MESSAGE_USAGE));
                }
                tempIndex = Integer.parseInt(tempString);
                if (!integerList.contains(tempIndex)) { //makes sure there are no duplicate index in the list
                    integerList.add(tempIndex);
                }

            }

            ArrayList<Integer> remarkIndexArrayList = ParserUtil.parseIndexes(integerList);
            //Sort is used to make sure index parsed is always in descending order, so that when remarks are removed
            // in RemoveRemarkCommand, there will be no errors
            Collections.sort(remarkIndexArrayList, Collections.reverseOrder());
            return new RemoveRemarkCommand(index, remarkIndexArrayList);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRemarkCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\person\Link.java
``` java
package seedu.address.model.person;

/**
 * Represents a Person's link in the address book.
 * Guarantees: immutable; is always valid
 */
public class Link {

    public static final String MESSAGE_LINK_CONSTRAINTS =
            "Links need to be a facebook.com link";

    public final String value;

    public Link(String link) {
        if (link == null) {
            this.value = "";
        } else {
            this.value = link;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && this.value.equals(((Link) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setRemark(ArrayList<Remark> remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<ArrayList<Remark>> remarkProperty() {
        return remark;
    }

    @Override
    public ArrayList<Remark> getRemark() {
        ArrayList<Remark> readOnlyRemarkList = new ArrayList<>(remark.get());
        return readOnlyRemarkList;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setLink(Link link) {
        this.link.set(requireNonNull(link));
    }

    @Override
    public ObjectProperty<Link> linkProperty() {
        return link;
    }

    @Override
    public Link getLink() {
        return link.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remark can take any values, and it can be blank";


    public final String value;

    /**
     * Validates given remark.
     *
     */
    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * Saves the given {@link ReadOnlyAddressBook} to the a backup storage location.
     * @param addressBook cannot be null
     * @throws IOException
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        logger.fine("Attempting to write to backup data file: "
                + addressBookStorage.getAddressBookFilePath() + "-backup.xml");
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads the twitter page based on Person link.value or if person does not have link,
     * loads a twitter search for the Person name.fullName
     * @param person
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        if (person.getLink().value.contains(PossibleLinks.POSSIBLE_LINK_2)
                || person.getLink().value.contains(PossibleLinks.POSSIBLE_LINK_3)) {
            loadPage(person.getLink().value);
        } else if (person.getLink().value.contains(PossibleLinks.POSSIBLE_LINK_1)) {
            loadPage("https://www." + person.getLink().value);
        } else {
            loadPage(PossibleLinks.SEARCH_URL_PREFIX_1 + person.getName().fullName.replaceAll(" ", "+")
                    + PossibleLinks.SEARCH_URL_SUFFIX_1);
        }
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Assigns different colour to tags w/ different names
     * Defaults to Grey colour when all colours are used up
     * @param tagValue is the tagName
     * @return
     */
    private static String getColorForTag(String tagValue) {

        if ((!tagColors.containsKey(tagValue)) && (colourNum < colors.length)) {
            tagColors.put(tagValue, colors[colourNum++]);
        } else if ((colourNum >= colors.length) && (!tagColors.containsKey(tagValue))) {
            colourNum = 0; //Resets the color num for reuse
            tagColors.put(tagValue, colors[colourNum++]);
        } else if (tagColors.containsKey(tagValue)) {
            //if the tag already has a colour in the hasmap, we do not need to do anything
        } else {
            tagColors.put(tagValue, "grey"); //just in case anything gets past
        }
        return tagColors.get(tagValue);

    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Get the tags from a person and assign a colour to each tag
     * before add the tag as a children (on scenebuilder) of the person on the app list
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            String setColour = "-fx-background-color: " + getColorForTag(tag.tagName);
            tagLabel.setStyle("-fx-border-color:black; -fx-border-width: 1; -fx-border-style: solid; " + setColour);
            tags.getChildren().add(tagLabel);
        });
    }
```
###### \java\seedu\address\ui\RemarkListPanel.java
``` java
package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the todolist of a person.
 */
public class RemarkListPanel extends UiPart<Region> {
    private static final String FXML = "RemarkListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RemarkListPanel.class);
    private final Integer firstIndexOfArray = 0;

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea remarkListPanel;

    private int totalPersonsWithRemarks;
    private ArrayList<String> personListWithRemarks;
    private ObservableList<ReadOnlyPerson> personObservableList;

    public RemarkListPanel(ObservableList<ReadOnlyPerson> personObservableList) {
        super(FXML);
        remarkListPanel.textProperty().bind(displayed);
        this.personObservableList = personObservableList;
        setTotalPersonsWithRemarks();

        registerAsAnEventHandler(this);
    }

    /**
     * Displays the total number of persons on list on the TextArea of the RemarkListPanel.fxml
     */
    private void setTotalPersonsWithRemarks() {
        totalPersonsWithRemarks = 0;
        personListWithRemarks = new ArrayList<>();
        for (int i = 0; i < personObservableList.size(); i++) {
            if (!personObservableList.get(i).getRemark().isEmpty()) {
                totalPersonsWithRemarks++;
                personListWithRemarks.add(personObservableList.get(i).getName().fullName);
            }
        }
        String printedString = totalPersonsWithRemarks + " person(s) with pending remarks: \n";
        for (int i = 0; i < personListWithRemarks.size(); i++) {
            printedString = printedString.concat(personListWithRemarks.get(i) + "\n");
        }
        final String finalString = printedString;
        Platform.runLater(() -> displayed.setValue(finalString));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        personObservableList = event.data.getPersonList();
        setTotalPersonsWithRemarks();
    }
}
```
###### \java\seedu\address\ui\RemarkPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the todolist of a person.
 */
public class RemarkPanel extends UiPart<Region> {
    private static final String FXML = "RemarkPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RemarkPanel.class);

    private final StringProperty displayed = new SimpleStringProperty("---Remarks---\n");

    @FXML
    private TextArea remarkPanel;

    private ReadOnlyPerson tempPerson;

    public RemarkPanel() {
        super(FXML);
        remarkPanel.textProperty().bind(displayed);

        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        tempPerson = event.getNewSelection().person;
        String printedString = "Remarks regarding " + String.valueOf(event.getNewSelection().person.getName()) + ":\n"
                + "---------------------------------------------------\n";
        for (int i = 0; i < event.getNewSelection().person.getRemark().size(); i++) {
            printedString = printedString.concat(i + 1 + "). " //Shows a list of remark numbered from 1 to size()
                    + event.getNewSelection().person.getRemark().get(i).value + "\n");
        }
        final String finalString = printedString;
        Platform.runLater(() -> displayed.setValue(finalString));
    }


    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ObservableList<ReadOnlyPerson> personList = event.data.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getName().fullName.equals(tempPerson.getName().fullName)) {
                String printedString = "Remarks regarding " + String.valueOf(personList.get(i).getName()) + ":\n"
                        + "---------------------------------------------------\n";
                for (int j = 0; j < personList.get(i).getRemark().size(); j++) {
                    printedString = printedString.concat(j + 1 + "). "
                            + personList.get(i).getRemark().get(j).value + "\n");
                }
                final String finalString = printedString;
                Platform.runLater(() -> displayed.setValue(finalString));
                break;
            }
        }
    }
}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
        if (event.isError) {
            resultDisplay.setStyle("-fx-text-fill:" + "red");
        } else {
            resultDisplay.setStyle("-fx-text-fill:" + "black");
        }

    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    /**
     * Displays the total number of persons on list on StatusBarFooter along with syncstatus and savelocation
     * Placement determine by @see StatusBarFooter.fxml
     * @param totalPersons
     */
    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(totalPersons + " person(s) total");
    }
```
###### \resources\view\BlueTheme.css
``` css
  /*
   * DarkTheme
   * Author: se-edu
   * https://github.com/se-edu/addressbook-level4
   */
  .background {
      -fx-background-color: derive(#E6ECF0, 0%);
      background-color: #ffffff; /* Used in the default.html file */
  }

  .label {
      -fx-font-size: 11pt;
      -fx-font-family: "Segoe UI Semibold";
      -fx-text-fill: #555555;
      -fx-opacity: 0.9;
  }

  .label-bright {
      -fx-font-size: 11pt;
      -fx-font-family: "Segoe UI Semibold";
      -fx-text-fill: black;
      -fx-opacity: 1;
  }

  .label-header {
      -fx-font-size: 32pt;
      -fx-font-family: "Segoe UI Light";
      -fx-text-fill: black;
      -fx-opacity: 1;
  }

  .text-field {
      -fx-font-size: 12pt;
      -fx-font-family: "Segoe UI Semibold";
  }

  .tab-pane {
      -fx-padding: 0 0 0 1;
  }

  .tab-pane .tab-header-area {
      -fx-padding: 0 0 0 0;
      -fx-min-height: 0;
      -fx-max-height: 0;
  }

  .table-view {
      -fx-base: #E6ECF0;
      -fx-control-inner-background: #E6ECF0;
      -fx-background-color: #E6ECF0;
      -fx-table-cell-border-color: transparent;
      -fx-table-header-border-color: transparent;
      -fx-padding: 5;
  }

  .table-view .column-header-background {
      -fx-background-color: transparent;
  }

  .table-view .column-header, .table-view .filler {
      -fx-size: 35;
      -fx-border-width: 0 0 1 0;
      -fx-background-color: transparent;
      -fx-border-color:
          transparent
          transparent
          derive(-fx-base, 80%)
          transparent;
      -fx-border-insets: 0 10 1 0;
  }

  .table-view .column-header .label {
      -fx-font-size: 20pt;
      -fx-font-family: "Segoe UI Light";
      -fx-text-fill: black;
      -fx-alignment: center-left;
      -fx-opacity: 1;
  }

  .table-view:focused .table-row-cell:filled:focused:selected {
      -fx-background-color: -fx-focus-color;
  }

  .split-pane:horizontal .split-pane-divider {
      -fx-background-color: derive(#E6ECF0, 0%);
      -fx-border-color: transparent transparent transparent transparent;
  }

  .split-pane {
      -fx-border-radius: 1;
      -fx-border-width: 1;
      -fx-background-color: derive(#E6ECF0, 0%);
  }

  .list-view {
      -fx-background-insets: 0;
      -fx-padding: 0;
  }

  .list-cell {
      -fx-label-padding: 0 0 0 0;
      -fx-graphic-text-gap : 0;
      -fx-padding: 0 0 0 0;
  }

  .list-cell:filled:even {
      -fx-background-color: #1DA1F2;
            -fx-border-color: #3e7b91;
            -fx-border-width: 1;
  }

  .list-cell:filled:odd {
      -fx-background-color: #ffffff;
  }

  .list-cell:filled:selected {
      -fx-background-color: #008080;
  }

  .list-cell:filled:selected #cardPane {
      -fx-border-color: #ffffff;
      -fx-border-width: 1;
  }

  .list-cell:empty {
      -fx-background-color:#E6ECF0;
  }

  .list-cell .label {
      -fx-text-fill: black;
  }

  .cell_big_label {
      -fx-font-family: "Segoe UI Semibold";
      -fx-font-size: 16px;
      -fx-text-fill: #010504;
  }

  .cell_small_label {
      -fx-font-family: "Segoe UI";
      -fx-font-size: 13px;
      -fx-text-fill: #010504;
  }

  .anchor-pane {
       -fx-background-color: derive(#E6ECF0, 0%);
  }

  .pane-with-border {
       -fx-background-color: derive(#E6ECF0, 0%);
       -fx-border-color: derive(#E6ECF0, 0%);
       -fx-border-top-width: 1px;
  }

  .status-bar {
      -fx-background-color: derive(#E6ECF0, 0%);
      -fx-text-fill: black;
  }

  .result-display {
      -fx-background-color: transparent;
      -fx-font-family: "Segoe UI Light";
      -fx-font-size: 13pt;
      -fx-text-fill: black;
  }

  .result-display .label {
      -fx-text-fill: black !important;
  }

  .status-bar .label {
      -fx-font-family: "Segoe UI Light";
      -fx-text-fill: black;
  }

  .status-bar-with-border {
      -fx-background-color: derive(#E6ECF0, 0%);
      -fx-border-color: derive(#E6ECF0, 0%);
      -fx-border-width: 1px;
  }

  .status-bar-with-border .label {
      -fx-text-fill: black;
  }

  .grid-pane {
      -fx-background-color: derive(#E6ECF0, 0%);
      -fx-border-color: derive(#E6ECF0, 0%);
      -fx-border-width: 1px;
  }

  .grid-pane .anchor-pane {
      -fx-background-color: derive(#E6ECF0, 0%);
  }

  .context-menu {
      -fx-background-color: derive(#E6ECF0, 0%);
  }

  .context-menu .label {
      -fx-text-fill: black;
  }

  .menu-bar {
      -fx-background-color: derive(#E6ECF0, 0%);
  }

  .menu-bar .label {
      -fx-font-size: 14pt;
      -fx-font-family: "Segoe UI Light";
      -fx-text-fill: black;
      -fx-opacity: 0.9;
  }

  .menu .left-container {
      -fx-background-color: black;
  }

  /*
   * Metro style Push Button
   * Author: Pedro Duque Vieira
   * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
   */
  .button {
      -fx-padding: 5 22 5 22;
      -fx-border-color: #e2e2e2;
      -fx-border-width: 2;
      -fx-background-radius: 0;
      -fx-background-color: #E6ECF0;
      -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
      -fx-font-size: 11pt;
      -fx-text-fill: #d8d8d8;
      -fx-background-insets: 0 0 0 0, 0, 1, 2;
  }

  .button:hover {
      -fx-background-color: #3a3a3a;
  }

  .button:pressed, .button:default:hover:pressed {
    -fx-background-color: black;
    -fx-text-fill: #E6ECF0;
  }

  .button:focused {
      -fx-border-color: black, black;
      -fx-border-width: 1, 1;
      -fx-border-style: solid, segments(1, 1);
      -fx-border-radius: 0, 0;
      -fx-border-insets: 1 1 1 1, 0;
  }

  .button:disabled, .button:default:disabled {
      -fx-opacity: 0.4;
      -fx-background-color: #E6ECF0;
      -fx-text-fill: black;
  }

  .button:default {
      -fx-background-color: -fx-focus-color;
      -fx-text-fill: #000000;
  }

  .button:default:hover {
      -fx-background-color: derive(-fx-focus-color, 0%);
  }

  .dialog-pane {
      -fx-background-color: #E6ECF0;
  }

  .dialog-pane > *.button-bar > *.container {
      -fx-background-color: #E6ECF0;
  }

  .dialog-pane > *.label.content {
      -fx-font-size: 14px;
      -fx-font-weight: bold;
      -fx-text-fill: black;
  }

  .dialog-pane:header *.header-panel {
      -fx-background-color: derive(#E6ECF0, 0%);
  }

  .dialog-pane:header *.header-panel *.label {
      -fx-font-size: 18px;
      -fx-font-style: italic;
      -fx-fill: black;
      -fx-text-fill: black;
  }

  .scroll-bar {
      -fx-background-color: derive(#E6ECF0, 0%);
  }

  .scroll-bar .thumb {
      -fx-background-color: derive(#E6ECF0, 50%);
      -fx-background-insets: 3;
  }

  .scroll-bar .increment-button, .scroll-bar .decrement-button {
      -fx-background-color: transparent;
      -fx-padding: 0 0 0 0;
  }

  .scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
      -fx-shape: " ";
  }

  .scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
      -fx-padding: 1 8 1 8;
  }

  .scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
      -fx-padding: 8 1 8 1;
  }

  #cardPane {
      -fx-background-color: transparent;
      -fx-border-width: 0;
  }

  #commandTypeLabel {
      -fx-font-size: 11px;
      -fx-text-fill: #F70D1A;
  }

  #commandTextField {
      -fx-background-color: transparent #ffffff transparent #ffffff;
      -fx-background-insets: 0;
      -fx-border-color: #ffffff #ffffff #ffffff #ffffff;
      -fx-border-insets: 0;
      -fx-border-width: 1;
      -fx-font-family: "Segoe UI Light";
      -fx-font-size: 13pt;
      -fx-text-fill: black;
  }

  #filterField, #personListPanel, #personWebpage {
      -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
  }

  #resultDisplay .content {
      -fx-background-color: transparent, #ffffff, transparent, #ffffff;
      -fx-background-radius: 0;
  }

  #remarkPanel .content {
      -fx-background-color: transparent, #ffffff, transparent, #ffffff;
      -fx-background-radius: 0;
  }

  #remarkListPanel .content {
      -fx-background-color: transparent, #ffffff, transparent, #ffffff;
      -fx-background-radius: 0;
  }

  #tags {
      -fx-hgap: 7;
      -fx-vgap: 3;
  }

  #tags .label {
      -fx-text-fill: black;
      -fx-background-color: #3e7b91;
      -fx-padding: 1 3 1 3;
      -fx-border-radius: 2;
      -fx-background-radius: 2;
      -fx-font-size: 11;
  }
```
###### \resources\view\DarkTheme.css
``` css
#remarkPanel .content {
    -fx-background-color: transparent, #383838, transparent, #383838;
    -fx-background-radius: 0;
}

#remarkListPanel .content {
    -fx-background-color: transparent, #383838, transparent, #383838;
    -fx-background-radius: 0;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
            <padding>
              <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
            </padding>
            <StackPane fx:id="remarkListDisplayPlaceholder" maxHeight="200.0" maxWidth="380.0" minHeight="0.0" prefHeight="100.0" prefWidth="380.0" />
            <StackPane fx:id="remarkDisplayPlaceholder" maxWidth="400" minHeight="40.0" prefHeight="300.0" prefWidth="400.0" />
```
###### \resources\view\RemarkListPanel.fxml
``` fxml
<?import javafx.scene.web.WebView?>
<StackPane fx:id="placeHolder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/8"
           xmlns:fx="http://javafx.com/fxml/1">
    <TextArea fx:id="remarkListPanel" editable="false" styleClass="result-display"/>
</StackPane>
```
###### \resources\view\RemarkPanel.fxml
``` fxml
<?import javafx.scene.web.WebView?>
<StackPane fx:id="placeHolder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/8"
           xmlns:fx="http://javafx.com/fxml/1">
    <TextArea fx:id="remarkPanel" editable="false" styleClass="result-display"/>
</StackPane>
```
