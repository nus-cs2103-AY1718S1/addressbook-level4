# Houjisan
###### \java\seedu\address\commons\events\ui\ShowPersonListViewEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the person list.
 */
public class ShowPersonListViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowTagListViewEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the tags list.
 */
public class ShowTagListViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\FavouriteCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Favourites/Unfavourites a person identified using it's last displayed index from the address book.
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites/Unfavourites the person identified by the index number"
            + " used in the last person listing if he is currently unfavourited/favourited.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITED_PERSON = "Favourited Person: %1$s";
    public static final String MESSAGE_UNFAVOURITED_PERSON = "Unfavourited Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToToggleFavourite = lastShownList.get(targetIndex.getZeroBased());
        boolean newFavouriteStatus = !personToToggleFavourite.getFavouriteStatus().getStatus();
        Person favouriteToggledPerson = new Person(personToToggleFavourite.getName(),
                personToToggleFavourite.getPhone(), personToToggleFavourite.getEmail(),
                personToToggleFavourite.getAddress(), personToToggleFavourite.getRemark(),
                new FavouriteStatus(newFavouriteStatus), personToToggleFavourite.getTags(),
                personToToggleFavourite.getLink());

        try {
            model.updatePerson(personToToggleFavourite, favouriteToggledPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(generateSuccessMessage(favouriteToggledPerson));
    }

    /**
     * @param favouriteToggledPerson
     * @return String that shows whether person was favourited or unfavourited
     */
    private String generateSuccessMessage(ReadOnlyPerson favouriteToggledPerson) {
        if (favouriteToggledPerson.getFavouriteStatus().getStatus()) {
            return String.format(MESSAGE_FAVOURITED_PERSON, favouriteToggledPerson);
        } else {
            return String.format(MESSAGE_UNFAVOURITED_PERSON, favouriteToggledPerson);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((FavouriteCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the contact list according to the specified data field.\n"
            + "Parameters: DATAFIELD (Possible fields: Name, Phone, Email, Address)\n"
            + "Example: " + COMMAND_WORD + " address";

    public static final String MESSAGE_SORT_LIST_SUCCESS = "Sorted list according to %1$s";

    private final String dataField;

    public SortCommand(String dataField) {
        this.dataField = dataField;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.sortByDataFieldFirst(dataField);
        model.getFilteredPersonList();

        return new CommandResult(String.format(MESSAGE_SORT_LIST_SUCCESS, dataField));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.dataField.equals(((SortCommand) other).dataField)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\TagsCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowTagListViewEvent;

/**
 * Lists all persons in the address book to the user.
 */
public class TagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_SUCCESS = "Listed all tags";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowTagListViewEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\FavouriteCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FavouriteCommand object
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavouriteCommand
     * and returns an FavouriteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FavouriteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String DATA_FIELD_NAME = "name";
    public static final String DATA_FIELD_PHONE = "phone";
    public static final String DATA_FIELD_EMAIL = "email";
    public static final String DATA_FIELD_ADDRESS = "address";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String dataFieldToSortByFirst = trimmedArgs.toLowerCase();

        switch (dataFieldToSortByFirst) {
        case DATA_FIELD_NAME:
        case DATA_FIELD_PHONE:
        case DATA_FIELD_EMAIL:
        case DATA_FIELD_ADDRESS:
            return new SortCommand(dataFieldToSortByFirst);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

    }

}

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * After a person has been removed/replaced in the list,
     * check if the person's tags are still in use and remove them if they aren't
     */
    private void cleanUpTagListAfterRemovalOf(Person person) {
        final UniqueTagList removedPersonTags = new UniqueTagList(person.getTags());
        for (Tag t : removedPersonTags) {
            boolean tagExists = false;
            for (ReadOnlyPerson p : persons) {
                if (p.getTags().contains(t)) {
                    tagExists = true;
                    break;
                }
            }
            if (!tagExists) {
                tags.remove(t);
            }
        }
    }

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
        this.tags = new SortedList<Tag>(this.addressBook.getTagList());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedFilteredPersons = new SortedList<>(filteredPersons);
        // Sort contacts by favourite status, then name, then phone, then email, then address
        sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenNameFirst());
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Raises an event to indicate that the UI needs to switch to viewing the Person List
     */
    private void indicateChangedToPersonListView() {
        raise(new ShowPersonListViewEvent());
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<Tag> getTagList() {
        return FXCollections.unmodifiableObservableList(tags);
    }

    @Override
    public void sortByDataFieldFirst(String dataField) {
        indicateChangedToPersonListView();
        switch (dataField) {
        case DATA_FIELD_NAME:
            sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenNameFirst());
            break;
        case DATA_FIELD_PHONE:
            sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenPhoneFirst());
            break;
        case DATA_FIELD_EMAIL:
            sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenEmailFirst());
            break;
        case DATA_FIELD_ADDRESS:
            sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenAddressFirst());
            break;
        default:
            break;
        }
    }

```
###### \java\seedu\address\model\person\FavouriteStatus.java
``` java
package seedu.address.model.person;

/**
 * Represents a Person's favourite status in the address book.
 * Guarantees: immutable; is always valid
 */
public class FavouriteStatus {

    public final boolean isFavourite;

    public FavouriteStatus(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public boolean getStatus() {
        return isFavourite;
    }

    @Override
    public String toString() {
        return (isFavourite ? "Favourite" : "");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteStatus // instanceof handles nulls
                && this.isFavourite == ((FavouriteStatus) other).isFavourite); // state check
    }

}
```
###### \java\seedu\address\model\util\ComparatorUtil.java
``` java
package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains utility methods for getting Comparators to sort {@code AddressBook}
 */
public class ComparatorUtil {

    // Comparator that sorts ReadOnlyPerson objects by their favourite status, favourited first
    private static final Comparator<ReadOnlyPerson> favouriteComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            boolean v1 = p1.getFavouriteStatus().getStatus();
            boolean v2 = p2.getFavouriteStatus().getStatus();
            if (v1 && !v2) {
                return -1;
            } else if (!v1 && v2) {
                return 1;
            }
            return 0;
        }
    };

    // Comparator that sorts ReadOnlyPerson objects by their name
    private static final Comparator<ReadOnlyPerson> nameComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            return p1.getName().toString().compareTo(p2.getName().toString());
        }
    };

    // Comparator that sorts ReadOnlyPerson objects by their phone
    private static final Comparator<ReadOnlyPerson> phoneComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            return p1.getPhone().toString().compareTo(p2.getPhone().toString());
        }
    };

    // Comparator that sorts ReadOnlyPerson objects by their email
    private static final Comparator<ReadOnlyPerson> emailComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            return p1.getEmail().toString().compareTo(p2.getEmail().toString());
        }
    };

    // Comparator that sorts ReadOnlyPerson objects by their address
    private static final Comparator<ReadOnlyPerson> addressComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            return p1.getAddress().toString().compareTo(p2.getAddress().toString());
        }
    };

    public static Comparator<ReadOnlyPerson> getFavouriteComparator() {
        return favouriteComparator;
    }

    public static Comparator<ReadOnlyPerson> getNameComparator() {
        return nameComparator;
    }

    public static Comparator<ReadOnlyPerson> getPhoneComparator() {
        return phoneComparator;
    }

    public static Comparator<ReadOnlyPerson> getEmailComparator() {
        return emailComparator;
    }

    public static Comparator<ReadOnlyPerson> getAddressComparator() {
        return addressComparator;
    }

    // Returns a lexicographic-order comparator that compares by favourite status first,
    // followed by name, phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsFavThenNameFirst() {
        return favouriteComparator.thenComparing(nameComparator).thenComparing(phoneComparator)
                .thenComparing(emailComparator).thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by favourite status first,
    // followed by name, phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsFavThenPhoneFirst() {
        return favouriteComparator.thenComparing(phoneComparator).thenComparing(nameComparator)
                .thenComparing(emailComparator).thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by favourite status first,
    // followed by name, phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsFavThenEmailFirst() {
        return favouriteComparator.thenComparing(emailComparator).thenComparing(nameComparator)
                .thenComparing(phoneComparator).thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by favourite status first,
    // followed by name, phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsFavThenAddressFirst() {
        return favouriteComparator.thenComparing(addressComparator).thenComparing(nameComparator)
                .thenComparing(phoneComparator).thenComparing(emailComparator);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowPersonListEvent(ShowPersonListViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        personListPanel.setVisible(true);
        tagListPanel.setVisible(false);
    }

    @Subscribe
    private void handleShowTagListEvent(ShowTagListViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        personListPanel.setVisible(false);
        tagListPanel.setVisible(true);
    }
}
```
###### \java\seedu\address\ui\TagCard.java
``` java
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TagCard extends UiPart<Region> {

    private static final String FXML = "TagListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Tag tag;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public TagCard(Tag tag, int displayedIndex) {
        super(FXML);
        this.tag = tag;
        id.setText(displayedIndex + ". ");
        name.setText(tag.tagName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCard)) {
            return false;
        }

        // state check
        TagCard card = (TagCard) other;
        return id.getText().equals(card.id.getText())
                && tag.equals(card.tag);
    }
}
```
###### \java\seedu\address\ui\TagListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of persons.
 */
public class TagListPanel extends UiPart<Region> {
    private static final String FXML = "TagListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TagListPanel.class);

    @FXML
    private ListView<TagCard> tagListView;

    public TagListPanel(ObservableList<Tag> tagList) {
        super(FXML);
        setConnections(tagList);
        registerAsAnEventHandler(this);
    }

    public void setVisible(boolean isVisible) {
        tagListView.setVisible(isVisible);
    }

    private void setConnections(ObservableList<Tag> tagList) {
        ObservableList<TagCard> mappedList = EasyBind.map(
                tagList, (tag) -> new TagCard(tag, tagList.indexOf(tag) + 1));
        tagListView.setItems(mappedList);
        tagListView.setCellFactory(listView -> new TagListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TagCard}.
     */
    class TagListViewCell extends ListCell<TagCard> {

        @Override
        protected void updateItem(TagCard tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(tag.getRoot());
            }
        }
    }

}
```
###### \resources\view\TagListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <padding>
      <Insets bottom="5" left="15" right="5" top="5" />
    </padding>
    <HBox alignment="CENTER_LEFT" spacing="5">
      <Label fx:id="id" styleClass="cell_big_label">
        <minWidth>
          <!-- Ensures that the label text is never truncated -->
        </minWidth>
      </Label>
      <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
    </HBox>
    <rowConstraints>
      <RowConstraints />
    </rowConstraints>
  </GridPane>
</HBox>
```
###### \resources\view\TagListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <ListView fx:id="tagListView" VBox.vgrow="ALWAYS" />
</VBox>
```
