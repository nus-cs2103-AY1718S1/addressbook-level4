# hthjthtrh
###### /resources/view/MainWindow.fxml
``` fxml
    <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.5, 0.5" VBox.vgrow="ALWAYS">
      <SplitPane dividerPositions="0.5" maxWidth="280.0" minWidth="200.0" orientation="VERTICAL" prefHeight="200.0" prefWidth="200.0">
         <items>
            <Pane maxHeight="30.0" minHeight="30.0" prefHeight="30.0">
               <children>
                  <Label alignment="CENTER" contentDisplay="CENTER" layoutX="76.0" layoutY="2.0" styleClass="label-dark" stylesheets="@DarkTheme.css" text="Groups" textAlignment="CENTER" underline="true">
                     <padding>
                        <Insets bottom="2.0" top="2.0" />
                     </padding>
                  </Label>
               </children>
            </Pane>
                <VBox fx:id="groupList">
                    <padding>
                        <Insets bottom="10.0" left="5.0" right="5.0" top="10.0" />
                    </padding>
                    <children>
                        <StackPane fx:id="groupListPanelPlaceholder" VBox.vgrow="ALWAYS" />
                    </children>
                </VBox>
         </items>
      </SplitPane>
```
###### /resources/view/GroupListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <ListView fx:id="groupListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### /resources/view/GroupListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
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
                <Label fx:id="picture" />
                <Label fx:id="grpName" styleClass="cell_big_label" text="\$grpName" />
            </HBox>
            <FlowPane prefHeight="30.0" />
            <Label fx:id="firstPerson" styleClass="cell_small_label" text="\$firstPerson">
                <VBox.margin>
                    <Insets left="15.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="secondPerson" styleClass="cell_small_label" text="\$secondPerson">
                <VBox.margin>
                    <Insets left="15.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="thirdPerson" styleClass="cell_small_label" text="\$thirdPerson">
                <VBox.margin>
                    <Insets left="15.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="ellipsis" styleClass="cell_small_label" text="\$ellipsis">
                <VBox.margin>
                    <Insets left="15.0" />
                </VBox.margin>
            </Label>
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Opens an alert dialogue to inform user of the error
     * @param e exception due to parsing / execution
     */
    private void alertUser(Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        FxViewUtil.setStageIcon(alertStage, "/images/Warning-300px.png");

        if (e.getClass().equals(CommandException.class)) {
            alert.setHeaderText(((CommandException) e).getExceptionHeader());
        } else {
            alert.setHeaderText(((ParseException) e).getExceptionHeader());
        }

        TextArea txtArea = new TextArea(e.getMessage());
        txtArea.setEditable(false);
        txtArea.setWrapText(true);

        txtArea.setMaxWidth(Double.MAX_VALUE);
        txtArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(txtArea, Priority.ALWAYS);
        GridPane.setHgrow(txtArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(txtArea, 0, 0);

        alert.getDialogPane().setContent(expContent);

        alert.showAndWait();

    }
```
###### /java/seedu/address/ui/GroupCard.java
``` java
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.model.group.Group;

/**
 * An UI component that displays information of a {@code Group}.
 */
public class GroupCard extends UiPart<Region> {
    private static final String FXML = "GroupListCard.fxml";

    public final Group group;

    @FXML
    private Label id;
    @FXML
    private Label grpName;
    @FXML
    private Label firstPerson;
    @FXML
    private Label secondPerson;
    @FXML
    private Label thirdPerson;
    @FXML
    private Label ellipsis;
    @FXML
    private Label picture;


    public GroupCard(Group group, int displayedIndex) {
        this(group, displayedIndex, new IconImage());
    }

    public GroupCard(Group group, int displayedIndex, IconImage image) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        initImage(image);
        initEllipsis();
        bindPreview(group);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Group} properties
     * so that they will be notified of any changes.
     */
    private void bindPreview(Group group) {
        grpName.textProperty().bind(Bindings.convert(group.grpNameProperty()));
        firstPerson.textProperty().bind(Bindings.convert(group.firstPreviewProperty()));
        secondPerson.textProperty().bind(Bindings.convert(group.secondPreviewProperty()));
        thirdPerson.textProperty().bind(Bindings.convert(group.thirdPreviewProperty()));
        group.thirdPreviewProperty().addListener(((observable, oldValue, newValue) -> {
            ellipsis.setVisible(!"".equals(newValue));
        }));
    }

    /**
     * Initiating content and visibility of ellipsis
     */
    private void initEllipsis() {
        ellipsis.setText("...");
        ellipsis.setVisible(!group.thirdPreviewProperty().get().equals(""));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCard)) {
            return false;
        }

        // state check
        GroupCard card = (GroupCard) other;
        return id.getText().equals(card.id.getText())
                && group.equals(card.group);
    }
```
###### /java/seedu/address/ui/GroupListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeselectAllEvent;
import seedu.address.commons.events.ui.GroupPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.group.Group;

/**
 * Panel containing the list of groups
 */
public class GroupListPanel extends UiPart<Region> {

    private static final String FXML = "GroupListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    @FXML
    private ListView<GroupCard> groupListView;

    public GroupListPanel(ObservableList<Group> groupList) {
        this(groupList, new IconImage());
    }
    public GroupListPanel(ObservableList<Group> groupList, IconImage image) {
        super(FXML);
        setConnections(groupList, image);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Group> groupList, IconImage image) {
        ObservableList<GroupCard> mappedList = EasyBind.map(
                groupList, (group) -> new GroupCard(group, groupList.indexOf(group) + 1, image));
        groupListView.setItems(mappedList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
        groupListView.setStyle("-fx-background-color: white;");
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        groupListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in group list panel changed to : '" + newValue + "'");
                        raise(new GroupPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code GroupCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            groupListView.scrollTo(index);
            groupListView.getSelectionModel().clearSelection(index);
            groupListView.getSelectionModel().select(index);
        });
    }

    @Subscribe
    /**
     * handles the selection of a group
     */
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.isGroupType()) {
            scrollTo(event.targetIndex);
        }
    }

    @Subscribe
    private void handleDeselectEvent(DeselectAllEvent event) {
        groupListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code GroupCard}.
     */
    class GroupListViewCell extends ListCell<GroupCard> {

        @Override
        protected void updateItem(GroupCard group, boolean empty) {
            super.updateItem(group, empty);

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(group.getRoot());
            }
        }
    }

}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleGroupPanelSelectionChangedEvent(GroupPanelSelectionChangedEvent event) {
        logic.updateFilteredPersonList(new GroupContainsPersonPredicate(event.getNewSelection().group));
    }

    @Subscribe
    private void handleDeselectEvent(DeselectAllEvent event) {
        logic.updateFilteredPersonList(new Predicate<ReadOnlyPerson>() {
            @Override
            public boolean test(ReadOnlyPerson readOnlyPerson) {
                return true;
            }
        });
    }
```
###### /java/seedu/address/commons/events/ui/GroupPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.GroupCard;

/**
 * Represents a selection change in the Group List Panel
 */
public class GroupPanelSelectionChangedEvent extends BaseEvent {


    private final GroupCard newSelection;

    public GroupPanelSelectionChangedEvent(GroupCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public GroupCard getNewSelection() {
        return newSelection;
    }
}
```
###### /java/seedu/address/commons/events/ui/JumpToListRequestEvent.java
``` java
    private boolean isGroupType;

    public JumpToListRequestEvent(Index targetIndex, boolean isGrpType) {
        this.targetIndex = targetIndex.getZeroBased();
        this.isGroupType = isGrpType;
    }

    public boolean isGroupType() {
        return this.isGroupType;
    }
```
###### /java/seedu/address/logic/parser/DeleteGroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments in the context of DeleteGroupCommand returns the command
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {
    @Override
    public DeleteGroupCommand parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        List<String> argList = Arrays.asList(userInput.split("\\s+"));

        if (userInput.equals("") || argList.size() != 1) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
        }

        try {
            int index = Integer.parseInt(argList.get(0));
            if (index <= 0) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
            }
            return new DeleteGroupCommand(Index.fromOneBased(index), true);
        } catch (NumberFormatException e) {
            // non-integer
            return new DeleteGroupCommand(argList.get(0), false);
        }

    }
}
```
###### /java/seedu/address/logic/parser/DeleteCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VALUE_ARGUMENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        args = args.trim();

        if (args.equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        }
        List<String> indexStrs = Arrays.asList(args.split("\\s+"));
        //eliminate duplicates
        HashSet<Integer> indexIntsSet = new HashSet<>();

        for (String indexStr : indexStrs) {
            try {
                indexIntsSet.add(ParserUtil.parseInt(indexStr));
            } catch (IllegalValueException e) {
                throw new ParseException(MESSAGE_INVALID_VALUE_ARGUMENT, DeleteCommand.MESSAGE_USAGE);
            }
        }
        List<Integer> indexInts = new ArrayList<>(indexIntsSet);
        Collections.sort(indexInts);
        ArrayList<Index> indexes = new ArrayList<>();
        for (int i = 0; i < indexInts.size(); i++) {
            indexes.add(Index.fromOneBased(indexInts.get(i)));
        }
        return new DeleteCommand(indexes);
    }

}
```
###### /java/seedu/address/logic/parser/GroupingCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input argument and creates a new GroupingCommand object
 */
public class GroupingCommandParser implements Parser<GroupingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupingCommand
     * and returns an GroupingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public GroupingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        args = args.trim();
        List<String> argsList = Arrays.asList(args.split("\\s+"));

        String grpName;
        List<String> indStrList;

        if (argsList.size() >= 2) {
            try {
                Integer.parseInt(argsList.get((0)));
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, GroupingCommand.MESSAGE_USAGE);
            } catch (NumberFormatException e) {
                grpName = argsList.get(0);
            }
            indStrList = argsList.subList(1, argsList.size());
        } else {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, GroupingCommand.MESSAGE_USAGE);
        }

        // using hashset to eliminate any duplicates
        Set<Integer> indexIntsSet = new HashSet<>();

        for (String indexStr : indStrList) {
            try {
                indexIntsSet.add(ParserUtil.parseInt(indexStr));
            } catch (IllegalValueException e) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, GroupingCommand.MESSAGE_USAGE);
            }
        }

        List<Index> indexList = new ArrayList<>();

        indexIntsSet.forEach(idx -> indexList.add(Index.fromOneBased(idx)));

        return new GroupingCommand(grpName, indexList);
    }
}
```
###### /java/seedu/address/logic/parser/UndoCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {
        args = args.trim();

        if (args.isEmpty()) {
            return new UndoCommand();
        } else if (args.equals("all")) {
            return new UndoCommand(Integer.MAX_VALUE);
        } else {
            try {
                int steps = ParserUtil.parseInt(args);
                return new UndoCommand(steps);
            } catch (IllegalValueException ive) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE);
            }
        }
    }
}
```
###### /java/seedu/address/logic/parser/EditGroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditGroupCommand object
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {

    private static final Set<String> validOp = new HashSet<>(Arrays.asList("gn", "add", "delete"));
    private String grpName;
    private Index grpIndex;
    private String operation;
    private String newName;
    private Index personIndex;
    private boolean indicateByIndex;


    /**
     * Parses the given {@code String} of arguments in the context of the EditGroupCommand
     * and returns an ViewGroupCommand for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditGroupCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();
        List<String> argsList = Arrays.asList(userInput.split("\\s+"));

        if (argsList.size() != 3 || userInput.equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        }

        parseGroupIndicator(argsList.get(0));
        parseOpIndicator(argsList.get(1));
        if ("gn".equals(operation)) {
            parseNewName(argsList.get(2));
            return new EditGroupCommand(grpName, grpIndex, operation, newName, indicateByIndex);
        } else {
            parseIndex(argsList.get(2));
            return new EditGroupCommand(grpName, grpIndex, operation, personIndex, indicateByIndex);
        }
    }

    /**
     * parses the indicator to either a group name or index
     * @param grpIndicator
     * @throws ParseException
     */
    private void parseGroupIndicator(String grpIndicator) throws ParseException {
        try {
            int index = Integer.parseInt(grpIndicator);
            if (index <= 0) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
            }
            grpIndex = Index.fromOneBased(index);
            indicateByIndex = true;
        } catch (NumberFormatException e) {
            // non-integer, must be a group name
            grpName = grpIndicator;
            indicateByIndex = false;
        }
    }

    /**
     * parses the operation indicator to existing operations
     * @param opIndicator
     * @throws ParseException
     */
    private void parseOpIndicator(String opIndicator) throws ParseException {
        if (!validOp.contains(opIndicator)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        } else {
            operation = opIndicator;
        }
    }

    /**
     * parses the new group name
     * @param groupName
     * @throws ParseException if new name is an integer
     */
    private void parseNewName(String groupName) throws ParseException {
        try {
            Integer.parseInt(groupName);
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        } catch (NumberFormatException e) {
            this.newName = groupName;
        }
    }

    /**
     * parses the index string into an Index object
     * @param index
     * @throws ParseException if index is of invalid value
     */
    private void parseIndex(String index) throws ParseException {
        try {
            personIndex = ParserUtil.parseIndex(index);
        } catch (IllegalValueException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE);
        }
    }
}
```
###### /java/seedu/address/logic/parser/ViewGroupCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewGroupCommand object
 */
public class ViewGroupCommandParser implements Parser<ViewGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewGroupCommand
     * and returns an ViewGroupCommand for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewGroupCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        userInput = userInput.trim();
        List<String> argsList = Arrays.asList(userInput.split("\\s+"));

        if (argsList.size() > 1 || argsList.get(0).equals("")) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupCommand.MESSAGE_USAGE);
        }

        try {
            int index = Integer.parseInt(argsList.get(0));
            if (index <= 0) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupCommand.MESSAGE_USAGE);
            }
            return new ViewGroupCommand(Index.fromOneBased(index));
        } catch (NumberFormatException e) {
            // argument is not an index
            return new ViewGroupCommand(userInput);
        }
    }
}
```
###### /java/seedu/address/logic/commands/GroypTypeUndoableCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

/**
 * abstract class to assist UndoableCommand
 */
abstract class GroupTypeUndoableCommand extends UndoableCommand {

    protected Index undoGroupIndex;
}
```
###### /java/seedu/address/logic/commands/ListGroupsCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DeselectAllEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Shows all groups in the addressbook
 * a temporary implementation
 */
public class ListGroupsCommand extends Command {

    public static final String COMMAND_WORD = "listGroups";

    public static final String MESSAGE_SUCCESS = "Listing all groups:\n";

    public static final String MESSAGE_EMPTY_GROUP_LIST = "There is no groups yet.";

    @Override
    public CommandResult execute() throws CommandException {
        List<Group> groupList = model.getAddressBook().getGroupList();
        if (groupList.size() == 0) {
            return new CommandResult(MESSAGE_EMPTY_GROUP_LIST);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS);

        int grpListSize = model.getAddressBook().getGroupList().size();

        for (int i = 1; i <= grpListSize; i++) {
            sb.append(i + ". ");
            sb.append((model.getFilteredGroupList().get(i - 1).getGrpName()));
            if (i != grpListSize) {
                sb.append("\n");
            }
        }

        EventsCenter.getInstance().post(new DeselectAllEvent());

        return new CommandResult(sb.toString());
    }
}
```
###### /java/seedu/address/logic/commands/DeleteGroupCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DeselectAllEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Deletes a group, depending on the existence of the group
 */
public class DeleteGroupCommand extends GroupTypeUndoableCommand {

    public static final String COMMAND_WORD = "deleteGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the group name or index.\n"
            + "Parameters: GROUP_NAME\n"
            + "or: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " TestGroup\n"
            + "or: " + COMMAND_WORD + " 4";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %s.";

    public static final String MESSAGE_NONEXISTENT_GROUP = "The group '%s' does not exist.";

    public static final String MESSAGE_INDEXOUTOFBOUND_GROUP = "Provided index is out of range.";

    private String groupName;
    private Index index;
    private boolean isIndex;

    private Group grpToDelete;

    public DeleteGroupCommand(Object object, boolean isIndex) {
        if (isIndex) {
            this.index = (Index) object;
        } else {
            this.groupName = (String) object;
        }
        this.isIndex = isIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (groupExists()) {
            model.deleteGroup(grpToDelete);

            EventsCenter.getInstance().post(new DeselectAllEvent());
            return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupName));
        } else {
            if (!isIndex) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                        String.format(MESSAGE_NONEXISTENT_GROUP, groupName));
            } else {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                        MESSAGE_INDEXOUTOFBOUND_GROUP);
            }
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof DeleteGroupCommand) {
            DeleteGroupCommand temp = (DeleteGroupCommand) other;
            if (isIndex) {
                return this.index.equals(temp.index);
            } else {
                return this.groupName.equals(temp.groupName);
            }
        }
        return false;

    }


    /**
     * Checks if the group to be deleted exists
     */
    private boolean groupExists() {
        List<Group> groupList = model.getAddressBook().getGroupList();
        if (!isIndex) {
            for (Group grp : groupList) {
                if (grp.getGrpName().equals(this.groupName)) {
                    grpToDelete = grp;
                    undoGroupIndex = Index.fromZeroBased(groupList.indexOf(grp));
                    return true;
                }
            }
        } else {
            try {
                grpToDelete = groupList.get(index.getZeroBased());
                undoGroupIndex = Index.fromZeroBased(index.getZeroBased());
                groupName = grpToDelete.getGrpName();
                return true;
            } catch (IndexOutOfBoundsException iobe) {
                return false;
            }
        }
        return false;
    }
}
```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */

    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        if (!(this instanceof  GroupTypeUndoableCommand)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } else {
            Index temp = ((GroupTypeUndoableCommand) this).undoGroupIndex;
            EventsCenter.getInstance().post(new JumpToListRequestEvent(temp, true));

        }
    }


    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() throws CommandException {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            if (!(this instanceof GroupTypeUndoableCommand)) {
                throw new AssertionError("The command has been successfully executed previously; "
                        + "it should not fail now");
            } else {
                throw new CommandException(ce.getExceptionHeader(), constructNewCommandExceptionMsg(ce));
            }
        }
        if (!(this instanceof GroupTypeUndoableCommand)) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    /**
     * constructs new body message for command exception
     * @param ce
     * @return
     */
    private String constructNewCommandExceptionMsg(CommandException ce) {
        return ce.getMessage() + "\n\nCommand you tried to redo: "
                + ((EditGroupCommand) this).reconstructCommandString();
    }
```
###### /java/seedu/address/logic/commands/EditGroupCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Edits the group, either 1.change group name, 2.adds a person to the group or 3.deletes a person from the group
 */
public class EditGroupCommand extends GroupTypeUndoableCommand {

    public static final String COMMAND_WORD = "editGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": edits the group. Supports three kinds of operations: 1. change group name 2. add a person 3. delete"
            + " a person\n"
            + "Parameters: GROUP_NAME gn NEW_GROUP_NAME (new group name cannot be an integer)\n"
            + "OR: GROUP_NAME add INDEX (must be positive integer)\n"
            + "OR: GROUP_NAME delete INDEX (must be positive integer)\n"
            + "Examples: " + COMMAND_WORD + " SmartOnes gn SuperSmartOnes\n"
            + COMMAND_WORD + " SmartOnes add 3\n"
            + COMMAND_WORD + " SmartOnes delete 4";

    public static final String MESSAGE_ADD_PERSON_SUCCESS = "Added person to group '%s':\n'%s'";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person from group '%s':\n'%s'";

    public static final String MESSAGE_CHANGE_NAME_SUCCESS = "Name of group '%s' is changed to '%s'";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the group";

    public static final String MESSAGE_DUPLICATE_GROUP =
            "A group by the name of '%s' already exists in the addressbook!";

    private String grpName = null;
    private Index grpIndex = null;
    private String operation = null;
    private String newName = null;
    private Index personIndex = null;
    private boolean indicateByIndex;

    public EditGroupCommand(String grpName, Index grpIndex, String operation, String newName, boolean indicateByIdx) {
        if (indicateByIdx) {
            this.grpIndex = grpIndex;
        } else {
            this.grpName = grpName;
        }
        this.operation = operation;
        this.newName = newName;
        this.indicateByIndex = indicateByIdx;
    }

    public EditGroupCommand(String grpName, Index grpIndex, String operation, Index personIndex,
                            boolean indicateByIdx) {
        if (indicateByIdx) {
            this.grpIndex = grpIndex;
        } else {
            this.grpName = grpName;
        }
        this.operation = operation;
        this.personIndex = personIndex;
        this.indicateByIndex = indicateByIdx;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<Group> grpList = model.getAddressBook().getGroupList();
        Group targetGrp = locateTargetGrp(grpList);

        if ("gn".equals(operation)) {
            return handleNameChangeOp(targetGrp);
        } else {
            Person targetPerson = null;
            if ("add".equals(operation)) {
                List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
                targetPerson = locateTargetPerson(lastShownList);
                return handleAddOp(targetGrp, targetPerson);

            } else {
                List<ReadOnlyPerson> personListInGroup = targetGrp.getPersonList();
                targetPerson = locateTargetPerson(personListInGroup);
                return handleDeleteOp(targetGrp, targetPerson);
            }
        }
    }

    /**
     * deletes the target person from target group
     * @param targetGrp
     * @param targetPerson
     * @return
     * @throws CommandException
     */
    private CommandResult handleDeleteOp(Group targetGrp, Person targetPerson) {
        try {
            model.removePersonFromGroup(targetGrp, targetPerson);
        } catch (PersonNotFoundException e) {
            assert false : "The target person cannot be missing";
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(this.grpIndex, true));

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, grpName,
                targetPerson.toString()));
    }

    /**
     * locate and return the target person in the person list
     * @param personList
     * @return
     * @throws CommandException
     */
    private Person locateTargetPerson(List<ReadOnlyPerson> personList) throws CommandException {
        try {
            ReadOnlyPerson targetPerson = personList.get(personIndex.getZeroBased());
            Person copiedPerson = new Person(targetPerson);
            return copiedPerson;
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * adds the target person to the target group
     * @param targetGrp
     * @return
     */
    private CommandResult handleAddOp(Group targetGrp, Person targetPerson) throws CommandException {
        try {
            model.addPersonToGroup(targetGrp, targetPerson);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_PERSON);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(this.grpIndex, true));

        return new CommandResult(String.format(MESSAGE_ADD_PERSON_SUCCESS, grpName,
                targetPerson.toString()));
    }

    /**
     * updates the group name of target group
     * @param targetGrp
     * @return
     * @throws CommandException if a group by the new name already exists
     */
    private CommandResult handleNameChangeOp(Group targetGrp) throws CommandException {
        try {
            model.setGrpName(targetGrp, newName);
            return new CommandResult(String.format(MESSAGE_CHANGE_NAME_SUCCESS, grpName, newName));
        } catch (DuplicateGroupException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, String.format(MESSAGE_DUPLICATE_GROUP, newName));
        }
    }

    /**
     * locate and return the target group indicated by either index or group name
     * @param grpList
     * @return target group
     * @throws CommandException
     */
    private Group locateTargetGrp(List<Group> grpList) throws CommandException {
        Group targetGrp = null;
        if (indicateByIndex) {
            try {
                targetGrp = grpList.get(grpIndex.getZeroBased());
                undoGroupIndex = Index.fromZeroBased(grpIndex.getZeroBased());
                grpName = targetGrp.getGrpName();
                return targetGrp;
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
        } else {
            for (Group grp : grpList) {
                if (grp.getGrpName().equals(grpName)) {
                    targetGrp = grp;
                    grpIndex = Index.fromZeroBased(grpList.indexOf(grp));
                    undoGroupIndex = Index.fromZeroBased(grpList.indexOf(grp));
                    return targetGrp;
                }
            }
        }

        if (targetGrp == null) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // sorry for this extremely long thing but I have null values to take care off
        if (other instanceof EditGroupCommand) {
            EditGroupCommand temp = (EditGroupCommand) other;
            if (this.indicateByIndex == temp.indicateByIndex) {
                if (this.indicateByIndex) {
                    if (this.operation.equals(temp.operation)) {
                        if (this.operation.equals("gn")) {
                            return this.grpIndex.equals(temp.grpIndex)
                                    && this.newName.equals(temp.newName);
                        } else {
                            return this.grpIndex.equals(temp.grpIndex)
                                    && this.personIndex.equals(temp.personIndex);
                        }
                    }
                } else {
                    if (this.operation.equals(temp.operation)) {
                        if (this.operation.equals("gn")) {
                            return this.grpName.equals(temp.grpName)
                                    && this.newName.equals(temp.newName);
                        } else {
                            return this.grpName.equals(temp.grpName)
                                    && this.personIndex.equals(temp.personIndex);
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * Reconstructs command message, used by RedoCommand
     * @return command message as a string
     */
    public String reconstructCommandString() {
        StringBuilder sb = new StringBuilder();
        sb.append(COMMAND_WORD + " ");

        if (indicateByIndex) {
            sb.append(grpIndex.getOneBased());
        } else {
            sb.append(grpName);
        }
        sb.append(" " + operation + " ");

        if ("gn".equals(operation)) {
            sb.append(newName);
        } else {
            sb.append(personIndex.getOneBased());
        }
        return sb.toString();
    }
}
```
###### /java/seedu/address/logic/commands/GroupingCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class GroupingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "createGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a group for the list of person based on group name(non-integer) "
            + "and index numbers provided\n"
            + "Parameters: GROUP_NAME (must not be an integer) INDEX [INDEX]... (must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " SmartOnes 1 4 2";

    public static final String MESSAGE_GROUPING_PERSON_SUCCESS = "Created group '%s' for people:\n";

    public static final String MESSAGE_DUPLICATE_GROUP_NAME = "This group already exists!\n";

    private List<Index> targetIdxs;
    private String groupName;

    public GroupingCommand(String groupName, List<Index> targetIndex) {
        this.groupName = groupName;
        this.targetIdxs = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<Integer> executableIdx = new ArrayList<>();
        Boolean hasExecutableIdx = false;

        for (Index idx : targetIdxs) {
            int intIdx = idx.getZeroBased();
            if (intIdx < lastShownList.size()) {
                executableIdx.add(intIdx);
                hasExecutableIdx = true;
            }
        }

        if (!hasExecutableIdx) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
        }

        List<ReadOnlyPerson> personToGroup = new ArrayList<>();
        executableIdx.forEach(idx -> personToGroup.add(lastShownList.get(idx)));

        try {
            model.createGroup(groupName, personToGroup);
        } catch (DuplicateGroupException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_GROUP_NAME);
        }

        Index grpIndex = model.getGroupIndex(groupName);

        EventsCenter.getInstance().post(new JumpToListRequestEvent(grpIndex, true));

        return new CommandResult(getSb(groupName, personToGroup));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupingCommand // instanceof handles nulls
                && this.groupName.equals(((GroupingCommand) other).groupName)
                && this.targetIdxs.equals(((GroupingCommand) other).targetIdxs)); // state check
    }

    /**
     * Return a String
     * @param persons to be deleted
     * @return a String with all details listed
     */
    public static String getSb(String grpName, List<ReadOnlyPerson> persons) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_GROUPING_PERSON_SUCCESS, grpName));

        appendPersonList(sb, persons);

        return sb.toString();
    }
}
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undo the previous {@code UndoableCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes undoable commands specified by the number of steps.\n"
            + "Parameters: [STEPS]\n"
            + "Eample: to undo 3 undoable commands:\n"
            + COMMAND_WORD + " 3\n"
            + "Alternate usage: use keyword \"all\" to undo all commands in current session\n"
            + "Eample: " + COMMAND_WORD + " all";


    // default undo one command
    private int steps = 1;

    public UndoCommand(){}

    public UndoCommand(int steps) {
        this.steps = steps;
    }


    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        int occurence = 0;
        while (steps > 0) {
            if (!undoRedoStack.canUndo()) {
                if (occurence == 0) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_FAILURE);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS);
                }
            }
            undoRedoStack.popUndo().undo();
            steps--;
            occurence++;
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
```
###### /java/seedu/address/logic/commands/ViewGroupCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.UndoableCommand.appendPersonList;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all person within the group
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "viewGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": list all persons in the specified group(by group name or index)\n"
            + "Parameters: GROUP_NAME\n"
            + "or: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " SmartOnes\n"
            + "or: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_GROUPING_PERSON_SUCCESS = "Listing %d person(s) in the group '%s'";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    private Index index = null;
    private String grpName = null;
    private Predicate predicate;

    public ViewGroupCommand(Index idx) {
        this.index = idx;
    }

    public ViewGroupCommand(String grpName) {
        this.grpName = grpName;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Group> grpList = model.getAddressBook().getGroupList();

        if (this.index != null) {
            return viewByIndex(grpList);
        } else { //either index or grpName should be non-null
            return viewByGrpName(grpList);
        }
    }

    /**
     * select the group using index provided
     * @param grpList
     * @return
     * @throws CommandException
     */
    private CommandResult viewByIndex(List<Group> grpList) throws CommandException {
        try {
            Group grpToView = grpList.get(index.getZeroBased());

            EventsCenter.getInstance().post(new JumpToListRequestEvent(this.index, true));

            return new CommandResult(String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                    grpToView.getPersonList().size(), grpToView.getGrpName()));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }
    }

    /**
     * select the group using group name provided
     * @param grpList
     * @return
     * @throws CommandException
     */
    private CommandResult viewByGrpName(List<Group> grpList) throws CommandException {
        for (int i = 0; i < grpList.size(); i++) {
            if (grpList.get(i).getGrpName().equals(this.grpName)) {
                Group grpToView = grpList.get(i);

                EventsCenter.getInstance().post(new JumpToListRequestEvent(Index.fromZeroBased(i), true));

                return new CommandResult(String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                        grpToView.getPersonList().size(), grpToView.getGrpName()));
            }
        }
        throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
    }

    /**
     * creates and returns a string to represent the list of person in the group
     * @param grpToView the target group
     * @return string to represent list of person
     */
    private String personListAsString(Group grpToView) {
        List<ReadOnlyPerson> personList = grpToView.getPersonList();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_GROUPING_PERSON_SUCCESS, grpToView.getGrpName()));

        appendPersonList(sb, personList);

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ViewGroupCommand) {
            if (this.index == null) {
                return this.grpName.equals(((ViewGroupCommand) other).grpName);
            } else {
                return this.index.equals(((ViewGroupCommand) other).index);
            }
        }
        return false;
    }
}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return model.getFilteredGroupList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    /**
     * updates the filtered person list according to group
     * @param predicate
     */
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        model.updateFilteredPersonList(predicate);
    }
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        return readAddressBook(backupAddressbook.getAddressBookFilePath());
    }
```
###### /java/seedu/address/storage/StorageManager.java
``` java


    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

```
###### /java/seedu/address/storage/StorageManager.java
``` java
    private void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        String backupPath = backupAddressbook.getAddressBookFilePath();
        logger.fine("Backing up data to: " + backupPath);
        saveAddressBook(addressBook, backupPath);
    }
```
###### /java/seedu/address/storage/XmlAdaptedGroup.java
``` java
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * JAXB-friendly version of the Group.
 */
public class XmlAdaptedGroup {

    @XmlElement(required = true)
    private String groupName;
    @XmlElement
    private List<XmlAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedGroup.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGroup() {}

    /**
     * Converts a given Group into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedGroup(Group source) {
        groupName = source.getGrpName();
        persons.addAll(source.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this jaxb-friendly adapted group object into the model's Group object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Group toModelType() throws IllegalValueException {
        Group grp =  new Group(this.groupName);
        this.persons.forEach(person -> {
            try {
                grp.add(person.toModelType());
            } catch (DuplicatePersonException e) {
                throw new AssertionError("Shouldn't exist duplicate person");
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        });
        return grp;
    }
}
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Group> getGroupList() {
        final ObservableList<Group> groups = this.groups.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(groups);
    }
```
###### /java/seedu/address/MainApp.java
``` java
    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s backup address book and
     * {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelWithBackup(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readBackupAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Backup file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            storage.saveAddressBook(initialData);
        } catch (DataConversionException e) {
            logger.warning("Backup file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the backup file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }
        return new ModelManager(initialData, userPrefs);
    }
```
###### /java/seedu/address/model/group/DuplicateGroupException.java
``` java
package seedu.address.model.group;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Group objects.
 */
public class DuplicateGroupException extends DuplicateDataException {

    public DuplicateGroupException() {
        super("Operation would result in duplicate groups");
    }
}
```
###### /java/seedu/address/model/group/Group.java
``` java
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents a Group in the address book
 */

public class Group extends UniquePersonList {

    private ObjectProperty<String> grpName;
    private ObjectProperty<String> firstPreview;
    private ObjectProperty<String> secondPreview;
    private ObjectProperty<String> thirdPreview;


    public Group(String groupName) {
        requireNonNull(groupName);
        this.grpName = new SimpleObjectProperty<>(groupName);
        initPreviews();
    }

    public Group(Group grp) throws DuplicatePersonException {
        this.grpName = new SimpleObjectProperty<>(grp.getGrpName());
        setPersons(grp.getPersonList());

        initPreviews();
        updatePreviews();
    }

    private void initPreviews() {
        this.firstPreview = new SimpleObjectProperty<>(" ");
        this.secondPreview = new SimpleObjectProperty<>(" ");
        this.thirdPreview = new SimpleObjectProperty<>(" ");
    }


    public ObjectProperty<String> firstPreviewProperty() {
        return firstPreview;
    }

    public ObjectProperty<String> secondPreviewProperty() {
        return secondPreview;
    }

    public ObjectProperty<String> thirdPreviewProperty() {
        return thirdPreview;
    }

    public ObjectProperty<String> grpNameProperty() {
        return grpName;
    }

    public String getGrpName() {
        return grpName.get();
    }

    public void setGrpName(String grpName) {
        this.grpName.set(grpName);
    }

    /**
     * Helper function for updatePreviews to facilitate the use of for loop
     * @return preview properties of this group as a list
     */
    private List<ObjectProperty<String>> getPersonPreviews() {
        return Arrays.asList(firstPreview, secondPreview, thirdPreview);
    }

    public ObservableList<ReadOnlyPerson> getPersonList() {
        return this.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || ((other instanceof Group)
                && this.getGrpName().equals(((Group) other).getGrpName()));
    }

    /**
     * Update preview properties for GroupCard
     */
    public void updatePreviews() {
        int i;
        for (i = 0; i < 3 && i < this.getPersonList().size(); i++) {
            getPersonPreviews().get(i).set(this.getPersonList().get(i).getName().toString());
        }
        for (i = this.getPersonList().size(); i < 3; i++) {
            getPersonPreviews().get(i).set("");
        }
    }

}
```
###### /java/seedu/address/model/group/UniqueGroupList.java
``` java
package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();

    /**
     * Adds a group to the list.
     */
    public void add(Group grp) throws DuplicateGroupException {
        requireNonNull(grp);
        if (contains(grp)) {
            throw new DuplicateGroupException();
        }
        internalList.add(grp);
        sort();
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        for (Group group : internalList) {
            if (group.getGrpName().equals(toCheck.getGrpName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * sorts the list of groups based on alphabetical order
     */
    public void sort() {
        internalList.sort(Comparator.comparing(Group::getGrpName));
    }


    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException, DuplicatePersonException {
        final UniqueGroupList replacement = new UniqueGroupList();
        for (final Group grp : groups) {
            replacement.add(new Group(grp));
        }
        this.internalList.setAll(replacement.internalList);
        sort();
    }

    /**
     * Removes the specified group from the group list
     *
     * @param grpToDelete
     */
    public void removeGroup(Group grpToDelete) {
        internalList.remove(grpToDelete);
        sort();
    }

    /**
     * sets the group to the new name
     *
     * @param targetGrp group to change name
     * @param newName   new name to change to
     * @throws DuplicateGroupException if a group of newName already exists in the group list
     */
    public void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        for (Group grp : internalList) {
            if (grp.getGrpName().equals(newName)) {
                throw new DuplicateGroupException();
            }
            if (targetGrp.getGrpName().equals(grp.getGrpName())) {
                targetGrp = grp;
                break;
            }
        }
        targetGrp.setGrpName(newName);
        sort();
    }

    /**
     * get the index of a group in the group list
     * @param groupName
     * @return -1 if the group isn't in the group list
     */
    public int getGroupIndex(String groupName) {
        for (Group grp : internalList) {
            if (grp.getGrpName().equals(groupName)) {
                return internalList.indexOf(grp);
            }
        }
        return -1;
    }

    /**
     * removes a person from the group
     * @param targetGrp
     * @param targetPerson
     */
    public void removePersonFromGroup(Group targetGrp, ReadOnlyPerson targetPerson) {
        try {
            targetGrp.remove(targetPerson);
            targetGrp.updatePreviews();
        } catch (PersonNotFoundException e) {
            assert false : "This person should be in the group";
        }
    }

    /**
     * adds target person into target group
     * @param targetGrp
     * @param targetPerson
     * @throws DuplicatePersonException
     */
    public void addPersonToGroup(Group targetGrp, ReadOnlyPerson targetPerson) throws DuplicatePersonException {
        targetGrp.add(targetPerson);
        targetGrp.updatePreviews();
    }
}
```
###### /java/seedu/address/model/person/predicates/GroupContainsPersonPredicate.java
``` java
package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a readOnlyPerson is contained by the group
 */
public class GroupContainsPersonPredicate implements Predicate<ReadOnlyPerson> {

    private Group group;

    public GroupContainsPersonPredicate(Group grp) {
        this.group = grp;
    }

    @Override
    public boolean test(ReadOnlyPerson readOnlyPerson) {
        return group.contains(readOnlyPerson);
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException, DuplicatePersonException {
        this.groups.setGroups(groups);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Creates and adds the group into groups
     * @param groupName name of the group
     * @param personToGroup person in the group
     * @throws DuplicateGroupException
     */
    public void addGroup(String groupName, List<ReadOnlyPerson> personToGroup)
            throws DuplicateGroupException {
        Group newGroup = new Group(groupName);
        personToGroup.forEach(person -> {
            try {
                newGroup.add(new Person(person));
            } catch (DuplicatePersonException e) {
                throw new AssertionError("Shouldn't exist duplicate person");
            }
        });
        newGroup.updatePreviews();
        groups.add(newGroup);
    }

    /**
     * adds the grp to the grp list
     * @param grp
     * @throws DuplicateGroupException
     */
    public void addGroup(Group grp) throws DuplicateGroupException {
        Group newGroup;
        try {
            newGroup = new Group(grp);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Shouldn't exist duplicate person");
        }
        groups.add(newGroup);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Deletes or updates the group, if the group contains personToEdit
     * @param personToEdit original person to be updated
     * @param editedPerson the person to update to. If null, it is a deletion
     */
    public void checkPersonInGroupList(ReadOnlyPerson personToEdit, Person editedPerson, Class commandClass) {
        if (this.groups.asObservableList().size() == 0) {
            return;
        }

        if (commandClass.equals(FavoriteCommand.class)) {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.favorite(personToEdit);
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }

                    group.updatePreviews();
                }
            });
        } else if (commandClass.equals(DeleteCommand.class)) {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.remove(personToEdit);
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }

                    group.updatePreviews();
                }
            });
        } else {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.setPerson(personToEdit, editedPerson);
                    } catch (DuplicatePersonException dpe) {
                        throw new AssertionError("Shouldn't have duplicate person if"
                                + " update person is successful");
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }

                    group.updatePreviews();
                }
            });
        }
    }

    public void removeGroup(Group grpToDelete) {
        groups.removeGroup(grpToDelete);
    }

    public void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        this.groups.setGrpName(targetGrp, newName);
    }

    public Index getGroupIndex(String groupName) {
        return Index.fromZeroBased(groups.getGroupIndex(groupName));
    }

    /**
     * removes targetPerson from targetGroup
     * @param targetGrp
     * @param targetPerson
     */
    public void removePersonFromGroup(Group targetGrp, ReadOnlyPerson targetPerson) {
        groups.removePersonFromGroup(targetGrp, targetPerson);
    }

    /**
     * addes targetPerson to targetGroup
     * @param targetGrp
     * @param targetPerson
     */
    public void addPersonToGroup(Group targetGrp, ReadOnlyPerson targetPerson) throws DuplicatePersonException {
        groups.addPersonToGroup(targetGrp, targetPerson);
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void createGroup(String groupName, List<ReadOnlyPerson> personToGroup)
            throws DuplicateGroupException {
        addressBook.addGroup(groupName, personToGroup);
        indicateAddressBookChanged();
    }

    @Override
    public void propagateToGroup(ReadOnlyPerson personToEdit, Person editedPerson, Class commandClass) {
        requireNonNull(personToEdit);

        addressBook.checkPersonInGroupList(personToEdit, editedPerson, commandClass);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteGroup(Group grpToDelete) {
        requireNonNull(grpToDelete);

        addressBook.removeGroup(grpToDelete);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        requireAllNonNull(targetGrp, newName);

        addressBook.setGrpName(targetGrp, newName);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPersonToGroup(Group targetGrp, ReadOnlyPerson targetPerson)
            throws DuplicatePersonException {
        requireAllNonNull(targetGrp, targetPerson);

        addressBook.addPersonToGroup(targetGrp, targetPerson);

        indicateAddressBookChanged();
    }

    @Override
    public synchronized void removePersonFromGroup(Group targetGrp, ReadOnlyPerson targetPerson) {
        requireAllNonNull(targetGrp, targetPerson);

        addressBook.removePersonFromGroup(targetGrp, targetPerson);

        indicateAddressBookChanged();
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return FXCollections.unmodifiableObservableList(filteredGroups);
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicateShowAllGroups) {
        requireNonNull(predicateShowAllGroups);

        filteredGroups.setPredicate(predicateShowAllGroups);
    }

    /**
     * Finds the index of a group in the group list
     * @param groupName
     * @return
     */
    @Override
    public Index getGroupIndex(String groupName) {
        requireNonNull(groupName);

        return addressBook.getGroupIndex(groupName);
    }
```
