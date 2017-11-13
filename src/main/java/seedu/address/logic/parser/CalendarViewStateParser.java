package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.AliasSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.UpdateCalendarView;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.OrderEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectEventCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.ui.CalendarView;

//@@author kaiyu92

/**
 * Parses user input for the calendar UI state.
 */
public class CalendarViewStateParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static UserPrefs userPrefs;
    private final Model model;
    private CalendarView calendarView;


    public CalendarViewStateParser(UserPrefs userPrefs, Model model, CalendarView calendarView) {
        this.userPrefs = userPrefs;
        this.model = model;
        this.calendarView = calendarView;
    }

    /**
     * update the state of the calendar UI object with reference to the user input
     *
     * @param userInput
     * @throws ParseException
     */
    public void updateViewState(String userInput) throws ParseException {

        //Check whether CalendarView is a null object
        requireNonNull(calendarView);

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        AliasSettings aliasSettings = userPrefs.getAliasSettings();

        if (commandWord.equals(AddEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getAddEventCommand().getAlias())
                || commandWord.equals(DeleteEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getDeleteEventCommand().getAlias())
                || commandWord.equals(EditEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getEditEventCommand().getAlias())
                || commandWord.equals(ListEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getListEventCommand().getAlias())
                || commandWord.equals(OrderEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getOrderEventCommand().getAlias())
                || commandWord.equals(ClearCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getClearCommand().getAlias())
                || commandWord.equals(UndoCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getUndoCommand().getAlias())
                || commandWord.equals(RedoCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getRedoCommand().getAlias())) {
            UpdateCalendarView.updateViewState(calendarView);
        } else if (commandWord.equals(FindEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getFindEventCommand().getAlias())) {
            UpdateCalendarView.updateFindState(calendarView, model);
        } else if (commandWord.equals(SelectEventCommand.COMMAND_WORD)
                || commandWord.equals(aliasSettings.getSelectEventCommand().getAlias())) {
            try {
                Index index = ParserUtil.parseIndex(arguments);
                UpdateCalendarView.updateSelectState(calendarView, model, index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectEventCommand.MESSAGE_USAGE));
            }
        }
    }
}
