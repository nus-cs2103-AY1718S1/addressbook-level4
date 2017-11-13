package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

//@@author reginleiff
/**
 * Lists all persons and/or events in Sales Navigator to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "listall";
    public static final String COMMAND_WORD_ALL = "listall";
    public static final String COMMAND_WORD_PERSONS = "listpersons";
    public static final String COMMAND_WORD_EVENTS = "listevents";
    public static final String MESSAGE_SUCCESS_PERSONS = "Listed all persons.";
    public static final String MESSAGE_SUCCESS_EVENTS = "Listed all events.";
    public static final String MESSAGE_SUCCESS_ALL = "Listed all persons and events.";
    public static final String MESSAGE_ERROR = "Error: Invalid Option for ListCommand";

    /**
     * Options available for a list command: list all persons, all events or all persons and events
     */
    public enum Option { PERSONS, EVENTS, ALL }

    private Option option;

    public ListCommand (Option op) {
        this.option = op;
    }


    @Override
    public CommandResult execute() {
        switch(option) {
        case ALL:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            return new CommandResult(MESSAGE_SUCCESS_ALL);

        case PERSONS:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_PERSONS);

        case EVENTS:
            model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            return new CommandResult(MESSAGE_SUCCESS_EVENTS);

        default:
            return new CommandResult(MESSAGE_ERROR);
        }
    }
}