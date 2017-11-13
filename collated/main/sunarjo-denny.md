# sunarjo-denny
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.EmptyPersonListException;

/**
 * Sorts the persons list according to the attribute specified
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String REVERSE_SEQUENCE = "reverse";

    public static final String PREFIX_NAME = "name";
    public static final String PREFIX_PHONE = "phone";

    public static final String MESSAGE_SUCCESS = "list sorted by %1$s in %2$s order!";
    public static final String MESSAGE_PERSONS_LIST_EMPTY = "The list is empty!";
    public static final String MESSAGE_MULTIPLE_ATTRIBUTE_ERROR = "Only accepts one attribute!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort the person list in ascending or descending order according to the attribute specified.\n"
            + "Parameters: "
            + "[attribute [reverse]]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " " + REVERSE_SEQUENCE;

    private final String attribute;
    private final boolean isReversed;

    // Default values for sorting
    private String sortBy = "name";
    private String sequence = "ascending";

    public SortCommand(String attribute, boolean isReversed) {
        requireNonNull(attribute);
        requireNonNull(isReversed);

        this.attribute = attribute;
        this.isReversed = isReversed;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Comparator<ReadOnlyPerson> comparator = sortComparatorByPrefix(this.attribute);
        try {
            model.sortPersonList(comparator, isReversed);
        } catch (EmptyPersonListException eple) {
            throw new CommandException(MESSAGE_PERSONS_LIST_EMPTY);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (isReversed) {
            this.sequence = "descending";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortBy, sequence));
    }

    /**
     * Comparator depending on the attribute specified
     */
    private Comparator<ReadOnlyPerson> sortComparatorByPrefix(String attribute) {
        switch (attribute) {
        case PREFIX_NAME:
            this.sortBy = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
        case PREFIX_PHONE:
            this.sortBy = "phone";
            return (o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(o2.getPhone().toString());
        default:
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return new RemarkCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case SearchCommand.COMMAND_WORD:
        case SearchCommand.COMMAND_ALIAS:
            return new SearchCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_FOR_SORTING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_FOR_SORTING;

import java.util.function.Consumer;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private String attribute;
    private Boolean isReversed;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME_FOR_SORTING, PREFIX_PHONE_FOR_SORTING);

        if (argMultimap.size() > 2) {
            throw new ParseException(
                    String.format(SortCommand.MESSAGE_MULTIPLE_ATTRIBUTE_ERROR, SortCommand.MESSAGE_USAGE)
            );
        }

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME_FOR_SORTING)
                || arePrefixesPresent(argMultimap, PREFIX_PHONE_FOR_SORTING))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        /**
         * Invalid command arguments would result in a loaded preamble
         */
```
###### \java\seedu\address\model\person\Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday should be in the format YYYY/MM/DD";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d\\d\\d\\d/\\d\\d/\\d\\d";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (birthday.isEmpty()) {
            this.value = "";
            return;
        }
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Creates Birthday that is empty.
     */
    private Birthday() {
        this.value = "";
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

```
###### \java\seedu\address\model\person\Birthday.java
``` java

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \resources\view\LightTheme.css
``` css
.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #1d1d1d;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #1d1d1d;
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
    -fx-base: #ffffff;
    -fx-control-inner-background: #ffffff;
    -fx-background-color: #ffffff;
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
    -fx-text-fill: #1d1d1d;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#ffffff, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#ffffff, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 10 10 10 10;
}

.list-cell #cardPane {
    -fx-border-color: #aaaaaa;
    -fx-border-width: 1;
}

.list-cell:filled:even {
    -fx-background-color: #ffffff;
}

.list-cell:filled:odd {
    -fx-background-color: #ffffff;
}

.list-cell:filled:selected {
    -fx-background-color: #ffffff;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #00ddff;
    -fx-border-width: 2;
}

.list-cell .label {
    -fx-text-fill: #1d1d1d;
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
     -fx-background-color: derive(#ffffff, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#ffffff, 20%);
     -fx-border-color: derive(#ffffff, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#ffffff, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-border-color: #aaaaaa;
    -fx-border-width: 1px;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: #1d1d1d;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #1d1d1d;
}

.status-bar-with-border {
    -fx-background-color: derive(#ffffff, 30%);
    -fx-border-color: derive(#ffffff, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: #1d1d1d;
}

.grid-pane {
    -fx-background-color: derive(#ffffff, 30%);
    -fx-border-color: derive(#ffffff, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#ffffff, 30%);
}

.context-menu {
    -fx-background-color: derive(#ffffff, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
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
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#aaaaaa, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 1;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 3 1 3;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 3 1 3 1;
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
    -fx-background-color: transparent;
    -fx-background-insets: 0;
    -fx-border-color: #aaaaaa;
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
    -fx-background-color: transparent;
    -fx-background-radius: 0;
}

.profile-wrapper {
    -fx-border-color: #aaaaaa;
    -fx-border-width: 1;
    -fx-border-style: solid;
}

.name {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-color: #1d1d1d
    -fx-font-size: 20;
}

.birthday {
    -fx-border-color: #aaaaaa;
    -fx-border-width: 1;
    -fx-border-style: solid;
}

.birthday-field {
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 15;
}

.remarks {
    -fx-border-color: #aaaaaa;
    -fx-border-width: 1;
    -fx-border-style: solid;
}

.remarks-content {
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 15;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: black;
    -fx-background-color: transparent;
    -fx-border-color:#aaaaaa;
    -fx-border-width: 1;
    -fx-border-style: solid;
    -fx-padding: 1 3 1 3;
    -fx-background-radius: 2;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 15;
}

.tags-box {
    -fx-border-color: #aaaaaa;
    -fx-border-width: 1;
    -fx-border-style: solid;
}
```
###### \resources\view\MainWindow.fxml
``` fxml

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <stylesheets>
    <URL value="@LightTheme.css" />
    <URL value="@Extensions.css" />
  </stylesheets>

  <MenuBar fx:id="menuBar" VBox.vgrow="NEVER">
    <Menu mnemonicParsing="false" text="File">
      <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
    </Menu>
    <Menu mnemonicParsing="false" text="Help">
      <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
    </Menu>
  </MenuBar>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.5" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" minWidth="340" prefWidth="340" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
    </VBox>
      <VBox prefHeight="200.0" prefWidth="100.0">
         <children>
           <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
             <padding>
               <Insets bottom="5" left="10" right="10" top="5" />
             </padding>
           </StackPane>
           <StackPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="pane-with-border" VBox.vgrow="NEVER">
             <padding>
               <Insets bottom="5" left="10" right="10" top="5" />
             </padding>
           </StackPane>
            <StackPane fx:id="personProfilePlaceholder" alignment="TOP_LEFT" prefWidth="600.0">
               <padding>
                  <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
               </padding>
               <VBox.margin>
                  <Insets />
               </VBox.margin>
            </StackPane>
         </children>
      </VBox>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\PersonListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="29.0" prefHeight="29.0" prefWidth="150.0" GridPane.columnIndex="0">
      <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
      </padding>
      <HBox alignment="CENTER_LEFT" spacing="5">
        <Label fx:id="id" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
      </HBox>
    </VBox>
      <HBox prefHeight="100.0" prefWidth="200.0" GridPane.rowIndex="1">
         <children>
            <ImageView fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true" translateX="4.0" translateY="2.0">
               <image>
                  <Image url="@../images/home.jpeg" />
               </image>
            </ImageView>
         <Label fx:id="address" styleClass="cell_small_label" text="\$address" translateX="7.0" translateY="4.0" />
         </children>
      </HBox>
      <HBox prefHeight="100.0" prefWidth="200.0" GridPane.rowIndex="2">
         <children>
            <ImageView fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true" translateX="4.0" translateY="3.0">
               <image>
                  <Image url="@../images/phone.jpeg" />
               </image>
            </ImageView>
         <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" translateX="7.0" translateY="4.0" />
         </children>
      </HBox>
      <HBox prefHeight="100.0" prefWidth="200.0" GridPane.rowIndex="3">
         <children>
            <ImageView fitHeight="20.0" fitWidth="20.0" pickOnBounds="true" preserveRatio="true" translateX="4.0" translateY="3.0">
               <image>
                  <Image url="@../images/mail.jpeg" />
               </image>
            </ImageView>
         <Label fx:id="email" styleClass="cell_small_label" text="\$email" translateX="7.0" translateY="4.0" />
         </children>
      </HBox>
      <rowConstraints>
         <RowConstraints prefHeight="25.0" />
         <RowConstraints minHeight="10.0" prefHeight="25.0" />
         <RowConstraints minHeight="10.0" prefHeight="25.0" />
         <RowConstraints minHeight="10.0" prefHeight="25.0" />
      </rowConstraints>
  </GridPane>
</HBox>
```
