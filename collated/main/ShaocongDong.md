# ShaocongDong
###### /resources/view/TaskListPanel.fxml
``` fxml
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <ListView fx:id="taskListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### /resources/view/TaskListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

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
        <Label fx:id="name" styleClass="cell_big_label" text="\$name" />
            <Label fx:id="priority" alignment="CENTER_RIGHT" style="-fx-border-color: red; -fx-border-style: dotted; -fx-background-color: lightblue; -fx-text-fill: rgb(193, 66, 66);" textAlignment="RIGHT" textFill="#ff7803">
               <font>
                  <Font name="Courier Bold" size="18.0" />
               </font>
            </Label>
```
###### /java/seedu/address/ui/TaskCard.java
``` java
package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.task.ReadOnlyTask;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static ArrayList<String> colors = new ArrayList<String>(
            Arrays.asList("Tomato", "Orange", "DodgerBlue", "MediumSeaGreen", "SlateBlue", "Violet", "Maroon"));
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static final String ICON = "/images/click.png";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyTask task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private FlowPane tags;
    @FXML
    private Label priority;
    @FXML
    private ImageView mark;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        initTags(task);
        initMark(task);
        bindListeners(task);

    }

    public ReadOnlyTask getTask () {
        return task;
    }

    private static String getTagColor(String tagName) {
        if (!tagColors.containsKey(tagName)) {
            String color = colors.get(0);
            tagColors.put(tagName, color);
            colors.remove(0);
            colors.add(color);
        }

        return tagColors.get(tagName);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Task} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyTask task) {
        name.textProperty().bind(Bindings.convert(task.nameProperty()));
        description.textProperty().bind(Bindings.convert(task.descriptionProperty()));
        startDateTime.textProperty().bind(Bindings.convert(task.startTimeProperty()));
        endDateTime.textProperty().bind(Bindings.convert(task.endTimeProperty()));
        priority.textProperty().bind(Bindings.convert(
                new SimpleObjectProperty<>(priorityStringValueConverter(task.priorityProperty().get()))));
        //mark.textProperty().bind(Bindings.convert(task.nameProperty()));
        task.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(task);
        });


    }

    /**
     * Priority value string converter for converting integer value to String literals
     * @param priorityValue ,  the inputted priority value
     * @return the string literal
     */
    private String priorityStringValueConverter (Integer priorityValue) {
        switch (priorityValue) {
        case 1: return " Super Important";
        case 2: return " Important";
        case 3: return " Normal";
        case 4: return " Trivial";
        case 5: return " Super Trivial";
        default: return "";
        }
    }

    /**
     * initialize tags and assign them with a color. tags haven't be met before will be given a new color from the list.
     * @param task
     */
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

```
###### /java/seedu/address/ui/TaskListPanel.java
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
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private static final String listSuccessful = "Listed all tasks";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    private boolean showAllTask = false;

    @FXML
    private ListView<TaskCard> taskListView;
    private ObservableList<ReadOnlyTask> uiList;

    public TaskListPanel(ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        uiList = taskList;
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyTask> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TaskCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        if (event.message.equals(listSuccessful)) {
            this.showAllTask = true;
        } else {
            this.showAllTask = false;
        }
        setConnections(uiList);
    }

```
###### /java/seedu/address/commons/events/ui/TaskPanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TaskCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final TaskCard newSelection;

    public TaskPanelSelectionChangedEvent(TaskCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TaskCard getNewSelection() {
        return newSelection;
    }
}
```
###### /java/seedu/address/commons/events/model/TaskBookChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskBook;

/** Indicates the AddressBook in the model has changed*/
public class TaskBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;

    public TaskBookChangedEvent(ReadOnlyTaskBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
```
###### /java/seedu/address/logic/parser/DeleteTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteTaskCommandParser implements Parser<DeleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTaskCommand
     * and returns an DeleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/AddTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * A parser class for addTask Command
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTask Command
     * and returns an AddTask Command object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION,
                        PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME, PREFIX_PRIORITY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESCRIPTION,
                PREFIX_START_DATE_TIME, PREFIX_END_DATE_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            Name name = new Name(ParserUtil.parseString(argMultimap.getValue(PREFIX_NAME)).get());
            Description description =
                    new Description(ParserUtil.parseString(argMultimap.getValue(PREFIX_DESCRIPTION)).get());
            DateTime startDateTime =
                    new DateTime(ParserUtil.parseString(argMultimap.getValue(PREFIX_START_DATE_TIME)).get());
            DateTime endDateTime =
                    new DateTime(ParserUtil.parseString(argMultimap.getValue(PREFIX_END_DATE_TIME)).get());

            if (startDateTime.compareTo(endDateTime) == -1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
            }

            Optional<Integer> priority = ParserUtil.parseInteger(argMultimap.getValue(PREFIX_PRIORITY));

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Boolean complete = false;
            ReadOnlyTask task;
            // Renew the task object with the priority parameter specially set if given
            if (priority.isPresent()) {
                Integer priorityValue = priority.get();
                task = new Task(name, description, startDateTime, endDateTime, tagList, complete, priorityValue);
            } else {
                task = new Task(name, description, startDateTime, endDateTime, tagList, complete);
            }

            return new AddTaskCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/address/logic/parser/SetPriorityCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SetPriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the SetPriorityCommand
 * and returns an SetPriorityCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class SetPriorityCommandParser implements Parser<SetPriorityCommand> {
    @Override
    public SetPriorityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRIORITY);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriorityCommand.MESSAGE_USAGE));
        }

        String priorityString = argMultimap.getValue(PREFIX_PRIORITY).orElse(null);
        if (priorityString == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriorityCommand.MESSAGE_USAGE));
        }

        Integer priority = Integer.parseInt(priorityString);

        return new SetPriorityCommand(index, priority);
    }
}
```
###### /java/seedu/address/logic/parser/SelectTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectTaskCommandParser implements Parser<SelectTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/EditTaskCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_START_DATE_TIME,
                        PREFIX_END_DATE_TIME, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            Optional<String> parserName = ParserUtil.parseString(argMultimap.getValue(PREFIX_NAME));
            if (parserName.isPresent()) {
                editTaskDescriptor.setName(new Name(parserName.get()));
            }
            Optional<String> parserDescription = ParserUtil.parseString(argMultimap.getValue(PREFIX_DESCRIPTION));
            if (parserDescription.isPresent()) {
                editTaskDescriptor.setDescription(new Description(parserDescription.get()));
            }

            Optional<String> parserStart = ParserUtil.parseString(argMultimap.getValue(PREFIX_START_DATE_TIME));
            DateTime startDateTime = null;
            if (parserStart.isPresent()) {
                //editTaskDescriptor.setStart(new DateTime(parserStart.get()));
                startDateTime = new DateTime(parserStart.get());
                editTaskDescriptor.setStart(new DateTime(parserStart.get()));
            }

            Optional<String> parserEnd = ParserUtil.parseString(argMultimap.getValue(PREFIX_END_DATE_TIME));
            DateTime endDateTime = null;
            if (parserEnd.isPresent()) {
                //editTaskDescriptor.setEnd(new DateTime(parserEnd.get()));
                endDateTime = new DateTime(parserEnd.get());
                editTaskDescriptor.setEnd(new DateTime(parserEnd.get()));
            }

            // additional checking for dateTime validity
            if (startDateTime != null && endDateTime != null) {
                if (startDateTime.compareTo(endDateTime) == -1) {
                    throw new ParseException(EditTaskCommand.MESSAGE_DATE_TIME_TASK);
                }
            }

            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editTaskDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

```
###### /java/seedu/address/logic/commands/TaskByPriorityCommand.java
``` java
package seedu.address.logic.commands;

/**
 * Task sorted by priority value from 1 to 5
 */
public class TaskByPriorityCommand extends Command {

    public static final String COMMAND_WORD = "taskByPriority";
    public static final String COMMAND_ALIAS = "tbp";

    public static final String MESSAGE_SUCCESS = "Tasks have been sorted by priority.";


    @Override
    public CommandResult execute() {
        model.taskByPriority();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### /java/seedu/address/logic/commands/DeleteTaskCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTask";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getSortedTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteTaskCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/EditTaskCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getSortedTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(index.getZeroBased());

        Task editedTask = null;
        try {
            editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_DATE_TIME_TASK);
        }

        try {
            model.updateTask(taskToEdit, editedTask);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedTaskName = editTaskDescriptor.getName().orElse(taskToEdit.getName());
        Description updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());
        DateTime updatedStartDateTime = editTaskDescriptor.getStartDateTime().orElse(taskToEdit.getStartDateTime());
        DateTime updatedEndDateTime = editTaskDescriptor.getEndDateTime().orElse(taskToEdit.getEndDateTime());
        if (updatedStartDateTime.compareTo(updatedEndDateTime) == -1) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_TASK);
        }
        Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(taskToEdit.getTags());
        Boolean updateComplete = editTaskDescriptor.getComplete().orElse(taskToEdit.getComplete());
        //Remark updatedRemark = taskToEdit.getRemark(); // edit command does not allow editing remarks
        Integer originalPriority = taskToEdit.getPriority(); // edit command is not used to update priority
        Integer id = taskToEdit.getId();
        ArrayList<Integer> peopleIds = taskToEdit.getPeopleIds();
        return new Task(updatedTaskName, updatedDescription, updatedStartDateTime, updatedEndDateTime,
                updatedTags, updateComplete, originalPriority, id, peopleIds);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Name taskName;
        private Description description;
        private DateTime start;
        private DateTime end;
        private Set<Tag> tags;
        private Boolean complete;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.taskName = toCopy.taskName;
            this.description = toCopy.description;
            this.start = toCopy.start;
            this.end = toCopy.end;
            this.tags = toCopy.tags;
            this.complete = toCopy.complete;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.taskName, this.description, this.start, this.end,
                    this.tags, this.complete);
        }

        public void setName(Name taskName) {
            this.taskName = taskName;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(taskName);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setStart(DateTime start) {
            this.start = start;
        }

        public Optional<DateTime> getStartDateTime() {
            return Optional.ofNullable(start);
        }

        public void setEnd(DateTime end) {
            this.end = end;
        }

        public Optional<DateTime> getEndDateTime() {
            return Optional.ofNullable(end);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        public void setComplete(Boolean complete) {
            this.complete = complete;
        }

        public Optional<Boolean> getComplete() {
            return Optional.ofNullable(complete);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getName().equals(e.getName())
                    && getDescription().equals(e.getDescription())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getTags().equals(e.getTags())
                    && getComplete().equals(e.getComplete());
        }
    }
}

```
###### /java/seedu/address/logic/commands/SelectTaskCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from the task book.
 */
public class SelectTaskCommand extends Command {

    public static final String COMMAND_WORD = "selectTask";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    private final Index targetIndex;

    public SelectTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getSortedTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectTaskCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ClearTaskCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.TaskBook;

/**
 * Clears the address book.
 */
public class ClearTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clearTask";
    public static final String COMMAND_ALIAS = "ct";
    public static final String MESSAGE_SUCCESS = "Task book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new TaskBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/AddTaskCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a person to the address book.
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addTask";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "Description "
            + PREFIX_START_DATE_TIME + "START TIME "
            + PREFIX_END_DATE_TIME + "END TIME "
            + PREFIX_PRIORITY + "INTEGER[1~5] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "picnic "
            + PREFIX_DESCRIPTION + "have fun at Botanic Garden "
            + PREFIX_START_DATE_TIME + "26-11-2017 11:00am "
            + PREFIX_END_DATE_TIME + "26-11-2017 12:00am "
            + PREFIX_PRIORITY + "3 "
            + PREFIX_TAG + "friends ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddTaskCommand(ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
```
###### /java/seedu/address/logic/commands/SetPriorityCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;


/**
 * Sets the priority of a task identified as completed using it's last displayed index from the address book.
 */
public class SetPriorityCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "setPriority";
    public static final String COMMAND_ALIAS = "stp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the priority by the new value as the user specified, which is between 1~5\n"
            + "Parameters: INDEX (must be a positive integer) c/PRIORITY (must be an integer between 1 and 5\n"
            + "Example: " + COMMAND_WORD + " 1 c/2";

    public static final String MESSAGE_UPDATE_TASK_PRIORITY_SUCCESS = "Updated the Priority of Task %1$s";
    public static final String MESSAGE_UPDATE_TASK_PRIORITY_OUT_OF_RANGE = "Priority value should be 1~5";

    private final Index targetIndex;
    private final Integer priority;

    public SetPriorityCommand(Index targetIndex, Integer priority) {
        this.targetIndex = targetIndex;
        this.priority = priority;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getSortedTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Task updatedTask = createUpdatedTask(taskToUpdate, priority);

        // Checking if the priority value inputted is out of range
        if (updatedTask == null) {
            throw new CommandException(MESSAGE_UPDATE_TASK_PRIORITY_OUT_OF_RANGE);
        }

        try {
            model.updateTask(taskToUpdate, updatedTask);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        } catch (DuplicateTaskException dte) {
            assert false : "The target updated can cause duplication";
        }

        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_UPDATE_TASK_PRIORITY_SUCCESS, taskToUpdate.getName()));
    }

    /**
     * Create an updated task by only modifying its priority property
     * @param target , the targeted task to be edited
     * @param value , the new priority value
     * @return the updated task or null
     */
    public Task createUpdatedTask (ReadOnlyTask target, Integer value) {
        // Preliminary checking
        if (value < 0 || value > 5) {
            return null;
        }

        Task updatedTask = new Task(
                target.getName(),
                target.getDescription(),
                target.getStartDateTime(),
                target.getEndDateTime(),
                target.getTags(),
                target.getComplete(),
                value,
                target.getId(),
                target.getPeopleIds());

        return updatedTask;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPriorityCommand // instanceof handles nulls
                && this.targetIndex.equals(((SetPriorityCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/storage/XmlSerializableTaskBook.java
``` java
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "taskbook")
public class XmlSerializableTaskBook implements ReadOnlyTaskBook {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskBook() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTaskBook(ReadOnlyTaskBook src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<ReadOnlyTask> tasks = this.tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tasks);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(tags);
    }

}
```
###### /java/seedu/address/storage/XmlTaskBookStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskBook;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTaskBookStorage implements TaskBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlTaskBookStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getTaskBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(filePath);
    }

    /**
     * Similar to {@link #readTaskBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File taskBookFile = new File(filePath);

        if (!taskBookFile.exists()) {
            logger.info("TaskBook file "  + taskBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskBook taskBookOptional = XmlFileStorage.loadTaskDataFromSaveFile(new File(filePath));

        return Optional.of(taskBookOptional);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskBook(ReadOnlyTaskBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        requireNonNull(taskBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveTaskDataToFile(file, new XmlSerializableTaskBook(taskBook));
    }

    @Override
    public void backupTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        backupTaskBook(taskBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskBook(ReadOnlyTaskBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void backupTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        requireNonNull(taskBook);
        requireNonNull(filePath);
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveTaskDataToFile(file, new XmlSerializableTaskBook(taskBook));
    }

}
```
###### /java/seedu/address/storage/XmlAdaptedTask.java
``` java
package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String taskName;
    @XmlElement(required = true)
    private String taskDescription;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement
    private Integer id;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement(required = true)
    private Boolean complete;
    @XmlElement
    private List<Integer> peopleIndices = new ArrayList<>();
    @XmlElement(required = true)
    private Integer priority;


    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        taskName = source.getName().toString();
        taskDescription = source.getDescription().toString();
        startDateTime = source.getStartDateTime().toString();
        endDateTime = source.getEndDateTime().toString();
        tagged = new ArrayList<>();
        id = source.getId();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        complete = source.getComplete();
        peopleIndices = source.getPeopleIds();
        priority = source.getPriority();
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name taskName = new Name(this.taskName);
        final Description taskDescription = new Description(this.taskDescription);
        final DateTime startDateTime = new DateTime(this.startDateTime);
        final DateTime endDateTime = new DateTime(this.endDateTime);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Boolean complete = this.complete;
        final ArrayList<Integer> peopleIndices = new ArrayList<>(this.peopleIndices);
        final Integer priority = this.priority;
        final Integer id = this.id;
        return new Task(taskName, taskDescription, startDateTime, endDateTime, tags, complete, priority, id,
                peopleIndices);
    }

}
```
###### /java/seedu/address/storage/TaskBookStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTaskBook;

/**
 * Represents a storage for task class
 */
public interface TaskBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTaskBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    /**
     * @see #getTaskBookFilePath()
     */
    Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskBook} to the storage.
     * @param taskBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    /**
     * @see #saveTaskBook(ReadOnlyTaskBook)
     */
    void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException;

    void backupTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

}
```
###### /java/seedu/address/model/ReadOnlyTaskBook.java
``` java
package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
```
###### /java/seedu/address/model/task/ReadOnlyTask.java
``` java
package seedu.address.model.task;

import java.util.ArrayList;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a task in the taskBook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    // Content property
    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Description> descriptionProperty();
    Description getDescription();
    ObjectProperty<DateTime> startTimeProperty();
    DateTime getStartDateTime();
    ObjectProperty<DateTime> endTimeProperty();
    DateTime getEndDateTime();

    // functional property
    ObjectProperty<Integer> priorityProperty();
    Integer getPriority();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Boolean> completeProperty();
    Boolean getComplete();
    ObjectProperty<Integer> idProperty();
    Integer getId();
    ObjectProperty<ArrayList<Integer>> peopleIdsProperty();
    ArrayList<Integer> getPeopleIds();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     * state checking involves content property and functional property (priority, tag, and complete)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getStartDateTime().equals(this.getStartDateTime())
                && other.getEndDateTime().equals(this.getEndDateTime())
                && other.getComplete().equals(this.getComplete())
                //&& other.getId().equals(this.getId())
                && other.getPriority().equals(this.getPriority())
                && other.getTags().equals(this.getTags()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Start: ")
                .append(getStartDateTime())
                .append(" End: ")
                .append(getEndDateTime())
                .append(" Complete: ")
                .append(getComplete())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * return true if the underlying two tasks have the same content.
     * @param other
     * @return
     */
    boolean equals(Object other);

}
```
###### /java/seedu/address/model/task/TaskNameContainsKeywordsPredicate.java
``` java
package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyTask}'s {@code Name} matches any of the keywords given.
 */
public class TaskNameContainsKeywordsPredicate implements Predicate<ReadOnlyTask> {
    private final List<Name> keywords;

    public TaskNameContainsKeywordsPredicate(List<Name> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().toString(), keyword.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskNameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TaskNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### /java/seedu/address/model/task/exceptions/TaskNotFoundException.java
``` java
package seedu.address.model.task.exceptions;

/**
 * For exception when a task not found (operation happens)
 */
public class TaskNotFoundException extends Exception {
}
```
###### /java/seedu/address/model/task/exceptions/DuplicateTaskException.java
``` java
package seedu.address.model.task.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * This is an exception for duplicate tasks operations
 */
public class DuplicateTaskException extends DuplicateDataException {

    /**
     * default constructor
     */
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
```
###### /java/seedu/address/model/task/Task.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.SampleDataUtil;

/**
 * This is a task class with only a name
 */
public class Task implements ReadOnlyTask {

    private ObjectProperty<Name> taskName;
    private ObjectProperty<Description> taskDescription;
    private ObjectProperty<DateTime> startDateTime;
    private ObjectProperty<DateTime> endDateTime;
    private ObjectProperty<Integer> taskPriority;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Boolean> complete;
    private ObjectProperty<Integer> id;
    private ObjectProperty<ArrayList<Integer>> peopleIds;

    /**
     * Default constructor may not be used
     */
    public Task () {
        this.tags = new SimpleObjectProperty<>(new UniqueTagList());
        this.complete = new SimpleObjectProperty<>(false);
        this.taskPriority = new SimpleObjectProperty<>(1);
        this.id = new SimpleObjectProperty<>(this.hashCode());
        this.peopleIds = new SimpleObjectProperty<>(new ArrayList<Integer>());
    }

    /**
     * Constructor with also a time only to be passed in (third type, start and end)
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     */
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime) {
        this();
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(false);
        this.taskPriority = new SimpleObjectProperty<>(1);
    }

    /**
     * Constructor for taking also a priority set by the user
     * @param name
     * @param description
     * @param startDateTime
     * @param endDateTime
     * @param priority
     */
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime,
                 int priority) {
        this();
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        setPriority(priority);
    }

    /**
     * Constructor with also a time only to be passed in (third type, start and end) and the complete state
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     * @param tags, the tag set
     */
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime,
                 Set<Tag> tags, Boolean state) {
        this();
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);

        this.complete = new SimpleObjectProperty<>(false);
        this.id = new SimpleObjectProperty<>();
    }

    /**
     * Constructor
     * with also a time only to be passed in (third type, start and end) and the complete state
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     * @param tags, the tag set
     * @param priority, the priority value
     */
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime,
                 Set<Tag> tags, Boolean state, Integer priority) {
        this();
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(state);
        setPriority(priority);
    }


    /**
     * Constructor for copy constructor
     * with also a time only to be passed in (third type, start and end) and the complete state
     * @param name, the name of this task
     * @param description, the description of this task
     * @param startDateTime, the start date and time of this task
     * @param endDateTime, the end date and time of this task
     * @param tags, the tag set
     * @param priority, the priority value
     */
    public Task (Name name, Description description, DateTime startDateTime, DateTime endDateTime,
                 Set<Tag> tags, Boolean state, Integer priority, Integer id, ArrayList<Integer> peopleIds) {
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.taskName = new SimpleObjectProperty<>(name);
        this.taskDescription = new SimpleObjectProperty<>(description);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.complete = new SimpleObjectProperty<>(false);
        this.id = new SimpleObjectProperty<>(id);
        this.peopleIds = new SimpleObjectProperty<>(peopleIds);
        this.complete = new SimpleObjectProperty<>(state);
        this.taskPriority = new SimpleObjectProperty<>();
        setPriority(priority);
    }

    /**
     * Copy constructor for task class
     * @param task
     */
    public Task (ReadOnlyTask task) {
        this(task.getName(), task.getDescription(), task.getStartDateTime(),
                task.getEndDateTime(), task.getTags(), task.getComplete(), task.getPriority(), task.getId(),
                task.getPeopleIds());
    }

    /**
     * get name from this task
     * @return name
     */
    public Name getName () {
        return taskName.get();
    }

    /**
     * get description from this task
     * @return description
     */
    public Description getDescription () {
        return taskDescription.get();
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public DateTime getStartDateTime () {
        return startDateTime.get();
    }

    public DateTime getEndDateTime () {
        return endDateTime.get();
    }

    public Boolean getComplete () {
        return complete.get();
    }

    @Override
    public ObjectProperty<Integer> idProperty() {
        return id;
    }

    @Override
    public Integer getId() {
        return id.get();
    }
    public Integer getPriority () {
        return taskPriority.get();
    }

    public ObjectProperty<Name> nameProperty() {
        return taskName;
    }

    public ObjectProperty<Description> descriptionProperty() {
        return taskDescription;
    }

    public ObjectProperty<DateTime> startTimeProperty() {
        return startDateTime;
    }

    public ObjectProperty<DateTime> endTimeProperty() {
        return endDateTime;
    }

    public ObjectProperty<Integer> priorityProperty() {
        return taskPriority;
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    public ObjectProperty<Boolean> completeProperty() {
        return complete;
    }

    public void setName(Name name) {
        this.taskName.set(requireNonNull(name));
    }

    public void setDescription(Description description) {
        this.taskDescription.set(requireNonNull(description));
    }

    public void setStartDateTime(DateTime startDateTime) {
        this.startDateTime.set(requireNonNull(startDateTime));
    }

    public void setEndDateTime(DateTime endDateTime) {
        this.endDateTime.set(requireNonNull(endDateTime));
    }

    /**
     * Setter for priority property (functional)
     * In all priority settings we will use this function, to prevent illegal values
     * @param priority
     */
    public void setPriority(Integer priority) {
        if (priority > 5 || priority < 0) {
            this.taskPriority.set(requireNonNull(1));
        } else {
            this.taskPriority.set(requireNonNull(priority));
        }
    }

    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    public void setComplete() {
        this.complete.set(requireNonNull(true));
    }

    public ObjectProperty<ArrayList<Integer>> peopleIdsProperty() {
        return peopleIds;
    }

    public ArrayList<Integer> getPeopleIds() {
        return peopleIds.get();
    }

    public void setPeopleIds(ArrayList<Integer> peopleIds) {
        this.peopleIds.set(requireNonNull(peopleIds));
    }

    public void setRemark(ArrayList<Integer> peopleIndices) {
        this.peopleIds.set(requireNonNull(peopleIndices));
    }
    /**
     * Set a new tag set along with the new task construction
     * This method should not be usd if the
     * @param tags , to be set
     * @return the newly constructed task
     */
    public Task withTags(String ... tags) {
        try {
            this.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### /java/seedu/address/model/task/UniqueTaskList.java
``` java
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyTask> mappedList = EasyBind.map(internalList, (task) -> task);

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(ReadOnlyTask toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(new Task(toAdd));
    }

```
###### /java/seedu/address/model/task/UniqueTaskList.java
``` java
    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyTask> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### /java/seedu/address/model/task/DateTime.java
``` java
package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Generic dateTime Class specially catered for our task (start time and end time)
 * we apply abstraction design pattern here and we check differently from how current public package does
 * Current format is very rigid, changing it to be more flexible will be a future enhancement
 */
public class DateTime {
    public static final String MESSAGE_DATE_TIME_FORMAT_CONSTRAINTS =
            "The date time input should follow this format: dd-mm-YYYY "
                    + "hh:mm[am/pm] day-month-year hour(12):minute am/pm";

    public static final String MESSAGE_DATE_TIME_VALUE_CONSTRAINTS =
            "The format is correct but the values are not: dd-mm-YYYY "
                    + "hh:mm[am/pm] day-month-year hour(12):minute am/pm";

    /**
     * The data time input in our app currently have following rigit format
     * dd-mm-YYYY hh:mm[am/pm] day-month-year hour:minute am/pm
     */
    public static final String DATE_TIME_VALIDATION_REGEX =
            "\\d{2}-\\d{2}-\\d{4}\\s{1}\\d{2}:\\d{2}pm|\\d{2}-\\d{2}-\\d{4}\\s{1}\\d{2}:\\d{2}am";

    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private String state;

    /**
     * Constructor and checker for the date time object
     *
     * @param dateTime
     * @throws IllegalValueException
     */
    public DateTime(String dateTime) throws IllegalValueException {
        //check the format:
        if (!isValidDateTime(dateTime)) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_FORMAT_CONSTRAINTS);
        }

        //Now we can safely proceed to decompose the inputs
        day = Integer.parseInt(dateTime.substring(0, 2));
        month = Integer.parseInt(dateTime.substring(3, 5));
        year = Integer.parseInt(dateTime.substring(6, 10));
        hour = Integer.parseInt(dateTime.substring(11, 13));
        minute = Integer.parseInt(dateTime.substring(14, 16));
        state = dateTime.substring(16);

        // value checking helper
        boolean isLeapYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));

        //check the values:
        if (month < 0 || month > 12) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        } else if (day < 0) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if (day > 31) {
                    throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
                }
            } else if (month == 2) {
                if ((isLeapYear && day > 29) || (!isLeapYear && day > 28)) {
                    throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
                }
            } else {
                if (day > 30) {
                    throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
                }
            }
        }

        if (hour < 0 || hour > 12) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        }

        if (minute < 0 || minute > 60) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        }

        if (!(state.equals("am") || state.equals("pm"))) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_VALUE_CONSTRAINTS);
        }
        // At this point, the values and formats are both correct.
    }

    public static boolean isValidDateTime(String test) {
        return test.matches(DATE_TIME_VALIDATION_REGEX);
    }

    /**
     * Convert our date time object to String
     *
     * @return
     */
    public String toString() {
        String dayString = helperFormat(Integer.toString(day));
        String monthString = helperFormat(Integer.toString(month));
        String yearString = Integer.toString(year);
        String hourString = helperFormat(Integer.toString(hour));
        String minuteString = helperFormat(Integer.toString(minute));
        return dayString + "-" + monthString + "-" + yearString + " " + hourString + ":" + minuteString + state;
    }

    /**
     * Hashcode getter for our date time object
     *
     * @return the string representation's hash code
     */
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Helper for making toString format correct
     *
     * @param input
     * @return
     */
    private String helperFormat(String input) {
        if (input.length() < 2) {
            return "0" + input;
        }
        return input;
    }

    public int getDay () {
        return day;
    }

    public int getMonth () {
        return month;
    }

    public int getYear () {
        return year;
    }

    public int getHour () {
        return hour;
    }

    public int getMinute () {
        return minute;
    }

    public String getState () {
        return state;
    }

    /**
     * comparing two date time object part by part
     * @param others , a dateTime object
     * @return 1 if the argument DateTime is bigger
     */
    public int compareTo(DateTime others) {
        int othersDay = others.getDay();
        int othersMonth = others.getMonth();
        int othersYear = others.getYear();
        int othersHour = others.getHour();
        int othersMinute = others.getMinute();
        String othersState = others.getState();
        if (year < othersYear) {
            return 1;
        } else if (year > othersYear) {
            return -1;
        } else {
            if (month < othersMonth) {
                return 1;
            } else if (month > othersMonth) {
                return -1;
            } else {
                if (day < othersDay) {
                    return 1;
                } else if (day > othersDay) {
                    return -1;
                } else {
                    // at this point, the two has exactly the same day
                    if (state.equals("am") && othersState.equals("pm")) {
                        return 1;
                    } else if (state.equals("pm") && othersState.equals("am")) {
                        return -1;
                    } else {
                        // same state, now we compare the time
                        if (hour < othersHour) {
                            return 1;
                        } else if (hour > othersHour) {
                            return -1;
                        } else {
                            if (minute < othersMinute) {
                                return 1;
                            } else if (minute > othersMinute) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }
                }
            }
        }
    }


}
```
###### /java/seedu/address/model/TaskBook.java
``` java
package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskBook() {}

    /**
     * Creates an AddressBook using the tasks and Tags in the {@code toBeCopied}
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyTaskBook newData) {
        requireNonNull(newData);
        try {
            setTasks(newData.getTaskList());
        } catch (DuplicateTaskException e) {
            assert false : "AddressBooks should not have duplicate tasks";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(tasks);
    }

    //// task-level operations

    /**
     * Adds a task to the task book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(ReadOnlyTask p) throws DuplicateTaskException {
        Task newTask = new Task(p);
        syncMasterTagListWith(newTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.add(newTask);
    }

```
###### /java/seedu/address/model/TaskBook.java
``` java
    /**
     * Replaces the given task {@code target} in the list with {@code editedReadOnlyTask}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Task)
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedReadOnlyTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedReadOnlyTask);

        Task editedTask = new Task(editedReadOnlyTask);
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.setTask(target, editedTask);
    }

    /**
     * Updates the given task with the new priority value provided.
     * @param target , the target task to be updated
     * @param value , the value to be updated for the task's new priority
     * @throws TaskNotFoundException
     * @throws DuplicateTaskException
     */
    public void updateTaskPriority(ReadOnlyTask target, Integer value)
            throws TaskNotFoundException, DuplicateTaskException {

        // Preliminary checking
        if (value < 0 || value > 5) {
            return;
        }

        Task updatedTask = new Task(
                target.getName(),
                target.getDescription(),
                target.getStartDateTime(),
                target.getEndDateTime(),
                target.getTags(),
                target.getComplete(),
                value,
                target.getId(),
                target.getPeopleIds());

        // syncMasterTagListWith(updatedTask);
        // Don't update master tags for now because this method doesn't modify the tags property.

        tasks.setTask(target, updatedTask);

    }


    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = new UniqueTagList(task.getTags());
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these tasks:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws TaskNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return tasks.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                && this.tasks.equals(((TaskBook) other).tasks)
                && this.tags.equalsOrderInsensitive(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
```
