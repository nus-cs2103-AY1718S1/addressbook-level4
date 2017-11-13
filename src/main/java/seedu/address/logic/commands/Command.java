package seedu.address.logic.commands;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.tag.Tag;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
    }

    //@@author ReneeSeet
    /**
     * Constructs a feedback message to summarise an operation for mass emailing
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */

    public static String getMessageForMassEmail(int displaySize, ArrayList<String> emails) {
        if (displaySize != 0) {
            StringBuilder mess = new StringBuilder(String.format(Messages.MESSAGE_EMAIL_CONFIRMATION, displaySize));
            mess.append("\n");
            for (String email : emails) {
                mess.append(email);
                mess.append("\n");
            }
            return mess.toString();
        } else {
            return Messages.MESSAGE_NOBODY_FOUND;
        }
    }
    //@@author

    //@@author yilun-zhu
    public static String getMessageForCalendar() {
        StringBuilder mess = new StringBuilder(String.format(Messages.CALENDAR_MESSAGE));
        return mess.toString();
    }

    public static String getMessageForAddEvent(CalendarEvent toAdd) {
        StringBuilder mess = new StringBuilder(String.format(Messages.MESSAGE_ADD_EVENT_SUCCESS, toAdd));
        return mess.toString();
    }
    //@@author

    //@@author zameschua
    /**
     * Constructs a feedback message to summarise an operation for mass emailing
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */

    public static String getMessageForSms(int displaySize, ArrayList<String> phoneNumbers) {
        if (displaySize != 0) {
            StringBuilder mess = new StringBuilder(String.format(Messages.MESSAGE_SMS_CONFIRMATION, displaySize));
            mess.append("\n");
            for (String phoneNumber : phoneNumbers) {
                mess.append(phoneNumber);
                mess.append("\n");
            }
            return mess.toString();
        } else {
            return Messages.MESSAGE_NOBODY_FOUND;
        }
    }
    //@@author

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of all tags.
     *
     * @return summary message for tags displayed
     */
    public static String getMessageForTagListShownSummary(ObservableList<Tag> allTags) {
        return ListAllTagsCommand.MESSAGE_SUCCESS;
    }
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }
}
