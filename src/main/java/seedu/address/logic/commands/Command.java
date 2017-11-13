package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    private static final HashMap<String, String> mapOfCommandsToFormats = new HashMap<>();
    private static final HashMap<String, String> mapOfCommandsToHelp = new HashMap<>();
    private static final HashMap<String, String> mapOfTaskCommandsToFormats = new HashMap<>();

    //@@author lancehaoh
    private static final List<String> listOfAvailableCommandAliases = Arrays.asList (
            AddCommand.COMMAND_WORD_ALIAS,
            ClearCommand.COMMAND_WORD_ALIAS,
            DeleteCommand.COMMAND_WORD_ALIAS,
            DeleteTagCommand.COMMAND_WORD_ALIAS,
            EditCommand.COMMAND_WORD_ALIAS,
            FindCommand.COMMAND_WORD_ALIAS,
            HistoryCommand.COMMAND_WORD_ALIAS,
            ListCommand.COMMAND_WORD_ALIAS,
            MultiFilterCommand.COMMAND_WORD_ALIAS,
            RedoCommand.COMMAND_WORD_ALIAS,
            SearchCommand.COMMAND_WORD_ALIAS,
            SelectCommand.COMMAND_WORD_ALIAS,
            UndoCommand.COMMAND_WORD_ALIAS
    );

    protected Model model;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    //@@author lancehaoh
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
    }

    //@@author lancehaoh
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of names of persons.
     *
     * @param displayList used to generate summary
     * @return summary message for names of persons displayed
     */
    public static String getMessageForPersonList(ObservableList<ReadOnlyPerson> displayList) {
        if (displayList.isEmpty()) {
            return Messages.MESSAGE_UNKNOWN_TAG;
        }

        StringBuilder builder = new StringBuilder();
        for (ReadOnlyPerson person : displayList) {
            builder.append(person.getName());
            builder.append("\n");
        }
        return  builder.toString();
    }

    //@@author lancehaoh
    public static Map<String, String> getMapOfCommandHelp() {
        // Initialize map the first time autocomplete is used
        if (mapOfCommandsToHelp.isEmpty()) {
            initializeCommandHelpMap();
        }
        return mapOfCommandsToHelp;
    }

    //@@author lancehaoh
    public static Map<String, String> getMapOfCommandFormats() {
        // Initialize map the first time autocomplete is used
        if (mapOfCommandsToFormats.isEmpty()) {
            initializeCommandFormatMap();
        }
        return mapOfCommandsToFormats;
    }
    public static Map<String, String> getMapofTaskCommandFormats() {
        if (mapOfTaskCommandsToFormats.isEmpty()) {
            initializeTaskCommandFormatMap();
        }
        return mapOfTaskCommandsToFormats;
    }

    //@@author lancehaoh
    public static List<String> getListOfAvailableCommandAliases() {
        return listOfAvailableCommandAliases;
    }

    //@@author lancehaoh
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    //@@author lancehaoh
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }

    //@@author lancehaoh
    /**
     *
     * Initialises the mapOfCommandsToFormats when it is being used for the first time
     * (i.e. this method is called only once throughout the program execution)
     *
     */
    private static void initializeCommandFormatMap() {
        mapOfCommandsToFormats.put(AddCommand.COMMAND_WORD, AddCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(ClearCommand.COMMAND_WORD, ClearCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(DeleteCommand.COMMAND_WORD, DeleteCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(DeleteTagCommand.COMMAND_WORD, DeleteTagCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(EditCommand.COMMAND_WORD, EditCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(ExitCommand.COMMAND_WORD, ExitCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(FindCommand.COMMAND_WORD, FindCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(HelpCommand.COMMAND_WORD, HelpCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(HistoryCommand.COMMAND_WORD, HistoryCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(ListCommand.COMMAND_WORD, ListCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(MultiFilterCommand.COMMAND_WORD, MultiFilterCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(RedoCommand.COMMAND_WORD, RedoCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(SearchCommand.COMMAND_WORD, SearchCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(SelectCommand.COMMAND_WORD, SelectCommand.AUTOCOMPLETE_FORMAT);
        mapOfCommandsToFormats.put(UndoCommand.COMMAND_WORD, UndoCommand.AUTOCOMPLETE_FORMAT);
    }
    //@@author chairz
    /**
     *  Initialises the mapofTaskCommandsToFormat when it is being used for the first time
     *  This method is seperated from the previous method to allow tabpane to work
     */
    private static void initializeTaskCommandFormatMap() {
        mapOfTaskCommandsToFormats.put(AddTaskCommand.COMMAND_WORD, AddTaskCommand.AUTOCOMPLETE_FORMAT);
        mapOfTaskCommandsToFormats.put(ListTaskCommand.COMMAND_WORD, ListTaskCommand.AUTOCOMPLETE_FORMAT);
        mapOfTaskCommandsToFormats.put(DeleteTaskCommand.COMMAND_WORD, DeleteTaskCommand.AUTOCOMPLETE_FORMAT);
        mapOfTaskCommandsToFormats.put(EditTaskCommand.COMMAND_WORD, EditTaskCommand.AUTOCOMPLETE_FORMAT);
        mapOfTaskCommandsToFormats.put(ClearTaskCommand.COMMAND_WORD, ClearTaskCommand.AUTOCOMPLETE_FORMAT);
    }

    //@@author lancehaoh
    /**
     *
     * Initialises the mapOfCommandsToHelp when it is being used for the first time
     * (i.e. this method is called only once throughout the program execution)
     *
     */
    private static void initializeCommandHelpMap() {
        mapOfCommandsToHelp.put(AddTaskCommand.COMMAND_WORD, AddTaskCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(ListTaskCommand.COMMAND_WORD, ListTaskCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(DeleteTaskCommand.COMMAND_WORD, DeleteTaskCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(DeleteTagCommand.COMMAND_WORD, DeleteTagCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(EditCommand.COMMAND_WORD, EditCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(HistoryCommand.COMMAND_WORD, HistoryCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(MultiFilterCommand.COMMAND_WORD, MultiFilterCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(SearchCommand.COMMAND_WORD, SearchCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(SelectCommand.COMMAND_WORD, SelectCommand.MESSAGE_USAGE);
        mapOfCommandsToHelp.put(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_USAGE);
    }
}
