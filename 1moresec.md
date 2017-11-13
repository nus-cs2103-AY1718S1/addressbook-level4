# 1moresec
###### /java/seedu/address/ui/PersonCard.java
``` java
    private static final String FXML = "PersonListCard.fxml";
    private static ArrayList<String> colors = new ArrayList<String>(
        Arrays.asList("Tomato", "Orange", "DodgerBlue", "MediumSeaGreen", "SlateBlue", "Violet", "Maroon"));
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    private static String getTagColor(String tagName) {
        if (!tagColors.containsKey(tagName)) {
            String color = colors.get(0);
            tagColors.put(tagName, color);
            colors.remove(0);
            colors.add(color);
        }

        return tagColors.get(tagName);
    }
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    /**
     * initialize tags and assign them with a color. tags haven't be met before will be given a new color from the list.
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
```
###### /java/seedu/address/commons/core/Messages.java
``` java
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String[] AUTOCOMPLETE_FIELD = {"addTask", "add", "deleteTask", "delete",
        "list", "listTask", "edit", "editTask", "export", "exportTask", "select", "selectTask",
        "setPriority", "taskByEnd", "taskByPriority", "find", "findTask", "markTask", "remark", "help",
        "link", "markTask", "setPriority", "taskByEnd", "taskByPriority", "undo", "redo",
        "history", "clear", "exit"};
}
```
###### /java/seedu/address/logic/parser/ExportCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ExportCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/FindTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindTaskCommandParser implements Parser<FindTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTaskCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTaskCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.replaceAll("\\s+", "").split("\\s*/\\s*");

        return new FindTaskCommand(new TaskNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case FindTaskCommand.COMMAND_WORD:
        case FindTaskCommand.COMMAND_ALIAS:
            return new FindTaskCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case ExportCommand.COMMAND_WORD:
        case ExportCommand.COMMAND_ALIAS:
            return new ExportCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case ExportTaskCommand.COMMAND_WORD:
        case ExportTaskCommand.COMMAND_ALIAS:
            return new ExportTaskCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/ExportTaskCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ExportTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportTaskCommandParser implements Parser<ExportTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportTaskCommand
     * and returns an ExportTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ExportTaskCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportTaskCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Export the information about a person as a line of code that can be
 * recognized by the Add command of 3W, for immigration purposes.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "ep";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the details of the person identified "
            + "by the index number used in the last person listing. \n"
            + "Output will be in an add command format, which can be "
            + "directly given to 3W to execute.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "add %1$s";
    private final Index targetIndex;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public ExportCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        ReadOnlyPerson personToExport = lastShownList.get(targetIndex.getZeroBased());

        final StringBuilder builder = new StringBuilder();
        personToExport.getTags().forEach(builder::append);
        String feedBack = String.format(MESSAGE_SUCCESS,
                String.join(" ",
                        "n/" + personToExport.getName(),
                        "p/" + personToExport.getPhone(),
                        "e/" + personToExport.getEmail(),
                        "a/" + personToExport.getAddress(),
                        "r/" + personToExport.getRemark(),
                        "t/" + builder.toString()));

        return new CommandResult(feedBack);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.targetIndex.equals(((ExportCommand) other).targetIndex)); // state check
    }

}
```
###### /java/seedu/address/logic/commands/FindTaskCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTaskCommand extends Command {

    public static final String COMMAND_WORD = "findTask";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose name/description"
            + "contain any of the specified keywords (case-insensitive) and displays them "
            + "as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " hotpot/have fun";

    private final TaskNameContainsKeywordsPredicate predicate;

    public FindTaskCommand(TaskNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(predicate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTaskCommand // instanceof handles nulls
                && this.predicate.equals(((FindTaskCommand) other).predicate)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ExportTaskCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Export the information about a person as a line of code that can be
 * recognized by the Add command of 3W, for immigration purposes.
 */
public class ExportTaskCommand extends Command {

    public static final String COMMAND_WORD = "exportTask";
    public static final String COMMAND_ALIAS = "ept";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Export the details of the task identified "
            + "by the index number used in the last task listing. \n"
            + "Output will be in an addTask command format, which can be "
            + "directly given to 3W to execute.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "addTask %1$s";
    private final Index targetIndex;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public ExportTaskCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getSortedTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex));
        ReadOnlyTask taskToExport = lastShownList.get(targetIndex.getZeroBased());

        final StringBuilder builder = new StringBuilder();
        taskToExport.getTags().forEach(builder::append);
        String feedBack = String.format(MESSAGE_SUCCESS,
                String.join(" ",
                        "n/" + taskToExport.getName(),
                        "d/" + taskToExport.getDescription(),
                        "s/" + taskToExport.getStartDateTime(),
                        "f/" + taskToExport.getEndDateTime(),
                        "t/" + builder.toString()));

        return new CommandResult(feedBack);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportTaskCommand // instanceof handles nulls
                && this.targetIndex.equals(((ExportTaskCommand) other).targetIndex)); // state check
    }

}
```
###### /java/seedu/address/model/person/ContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean hasName = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        boolean hasEmail = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().toString(), keyword));
        boolean hasAddress = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().toString(), keyword));
        return hasName || hasEmail || hasAddress;
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
    private final List<String> keywords;

    public TaskNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        boolean hasName = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().toString(), keyword));
        boolean hasDescription = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().toString(), keyword));
        return hasName || hasDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskNameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TaskNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
