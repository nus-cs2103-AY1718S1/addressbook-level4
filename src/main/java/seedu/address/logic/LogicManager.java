package seedu.address.logic;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CalendarViewStateParser;
import seedu.address.logic.parser.GeneralBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.ui.CalendarView;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final UserPrefs userPrefs;
    private final CommandHistory history;
    private final GeneralBookParser generalBookParser;
    private final UndoRedoStack undoRedoStack;
    private final Config config;

    private CalendarViewStateParser calendarViewStateParser;

    private CalendarView calendarView;

    public LogicManager(Model model, UserPrefs userprefs, Config config) {
        this.model = model;
        this.userPrefs = userprefs;
        this.config = config;
        this.history = new CommandHistory();
        this.generalBookParser = new GeneralBookParser(userprefs);
        this.undoRedoStack = new UndoRedoStack();
    }

    @Override
    public void setTabPane(TabPane tabPane) {
        generalBookParser.setTabPane(tabPane);
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarViewStateParser = new CalendarViewStateParser(this.userPrefs, this.model, calendarView);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, DuplicateUserException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = generalBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack, config);
            CommandResult result = command.execute();
            undoRedoStack.push(command);

            //Update the View state of the Calendar
            if (calendarViewStateParser != null) {
                calendarViewStateParser.updateViewState(commandText);
            }

            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ArrayList<ArrayList<String>> getCommands() {
        return model.getCommands();
    }

    @Override
    public String getAliasForCommand(String commandName) {
        return model.getAliasForCommand(commandName);
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
