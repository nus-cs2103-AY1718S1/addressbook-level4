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
###### \GroupCard.java
``` java
    /**
     * Color getter for a tag
     */
    private static String colorGetterForPerson(String personValue) {
        int colorCode;
        boolean isUsedUpAllColors = true;
        for (int i = 0; i < colors.length; i++) {
            if (usedColors[i] == 0) {
                isUsedUpAllColors = false;
                break;
            }
        }
        if (isUsedUpAllColors) {
            for (int j = 0; j < colors.length; j++) {
                usedColors[j] = 0;
            }
        }

        if (!tagColors.containsKey(personValue)) {
            do {
                colorCode = random.nextInt(colors.length);
            } while(usedColors[colorCode] == 1);
            usedColors[colorCode] = 1;
            tagColors.put(personValue, colors[colorCode]);
        }

        return tagColors.get(personValue);
    }
```
###### \GroupCard.java
``` java
    /**
     * Distribute colors for tags
     */
    private void initPersons(Group group) {
        group.getPersonList().forEach(person -> {
            Label personLabel = new Label(person.getName().fullName);
            personLabel.setStyle("-fx-background-color: " + colorGetterForPerson(person.getName().fullName));
            persons.getChildren().add(personLabel);
        });
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
