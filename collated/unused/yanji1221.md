# yanji1221
###### \ComingBirthdayCard.java
``` java
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of coming birthdays in certain month.
 */
public class ComingBirthdayCard extends UiPart<Region> {

    private static final String FXML = "ComingBirthdayz.fxml";

    public final ReadOnlyPerson person;

    @FXML
    private HBox birthdayListCardPane;
    @FXML
    private Label id;
    @FXML
    private Label birthday;
    @FXML
    private Label name;

    public ComingBirthdayCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ComingBirthdayCard)) {
            return false;
        }

        // state check
        ComingBirthdayCard comingBirthday = (ComingBirthdayCard) other;
        return id.getText().equals(comingBirthday.id.getText())
                && person.equals(comingBirthday.person);
    }

}
```
###### \ComingBirthdayListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>

<HBox id="birthdayListCardPane" fx:id="birthdayListCardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets top="5" right="5" bottom="5" left="15" />
            </padding>
            <HBox spacing="5" alignment="CENTER_LEFT">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
            </HBox>
            <Label fx:id="birthday" styleClass="cell_small_label" text="\$birthday" />
        </VBox>
    </GridPane>
</HBox>
```
###### \ComingBirthdayListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="birthdayListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \ComingBirthdayListPanel.java
``` java
package seedu.address.ui;

import java.lang.String;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class ComingBirthdayListPanel extends UiPart<Region> {
    private static final String FXML = "ComingBirthdayListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> comingBirthdayListView;

    public ComingBirthdayListPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        ObservableList<ReadOnlyPerson> comingBirthdayList = comingBirthdayListGetter(personList);
        setConnections(comingBirthdayList);
    }

    private ObservableList<ReadOnlyPerson> comingBirthdayListGetter(ObservableList<ReadOnlyPerson> personList) {
        List<ReadOnlyPerson> comingBirthdayList = personList.stream().collect(Collectors.toList());
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        month += 1;

        for (int i = 0; i < personList.size(); i++) {
            if (!personList.get(i).getBirthday().toString()
                    .substring(5, 7).equals(Integer.toString(month))) {
                comingBirthdayList.remove(i);
            }
        }

        return FXCollections.observableArrayList(comingBirthdayList);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> comingBirthdayList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                comingBirthdayList, (person) -> new PersonCard(person, comingBirthdayList.indexOf(person) + 1));
        comingBirthdayListView.setItems(mappedList);
        comingBirthdayListView.setCellFactory(listView -> new BirthdayListViewCell());
    }


    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            comingBirthdayListView.scrollTo(index);
            comingBirthdayListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class BirthdayListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard comingBirthday, boolean empty) {
            super.updateItem(comingBirthday, empty);

            if (empty || comingBirthday == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(comingBirthday.getRoot());
            }
        }
    }

}
```
###### \MainWindow.java
``` java
    @FXML
    private StackPane comingBirthdayListPanelPlaceholder;
```
###### \MainWindow.java
``` java
        comingBirthdayListPanel = new ComingBirthdayListPanel(logic.getFilteredPersonList());
        comingBirthdayListPanelPlaceholder.getChildren().add(comingBirthdayListPanel.getRoot());
```
###### \MainWindow.java
``` java
    public ComingBirthdayListPanel getComingBirthdayListPanel() {
        return this.comingBirthdayListPanel;
    }
```
###### \SetShortcutNumberCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Sets shortcut number of person identified using it's last displayed index from the address book.
 */
public class SetShortcutNumberCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the shortcut number of person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)NUMBER (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Person's shorcut number sets: %1$s";


    private final Index targetIndex;
    private final int shortcutNumber;

    public SetShortcutNumberCommand(Index targetIndex, int shortcutNumber) {
        this.targetIndex = targetIndex;
        this.shortcutNumber = shortcutNumber;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSet = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.setPerson(personToSet);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToSet));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetShortcutNumberCommand // instanceof handles nulls
                && this.targetIndex.equals(((SetShortcutNumberCommand) other).targetIndex)); // state check
    }

}
```
###### \ShortcutNumber.java
``` java
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's shortcut number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidShortcutNumber(String)}
 */
public class ShortcutNumber {

    public static final String MESSAGE_SHORTCUT_NUMBER_CONSTRAINTS =
            "Shortcut numbers can only contain numbers, and should be digits";
    public static final String SHORTCUT_NUMBER_VALIDATION_REGEX = "\\d";
    public final String value;

    /**
     * Validates given shortcut number.
     *
     * @throws IllegalValueException if given shortcut number string is invalid.
     */
    public ShortcutNumber(String shortcutNumber) throws IllegalValueException {
        if (!isValidShortcutNumber(shortcutNumber)) {
            throw new IllegalValueException(MESSAGE_SHORTCUT_NUMBER_CONSTRAINTS);
        }
        this.value = shortcutNumber;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidShortcutNumber(String test) {
        return test.matches(SHORTCUT_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    /*@Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShortcutNumber // instanceof handles nulls
                && this.value.equals(((ShortcutNumber) other).value)); // state check
    }*/

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
