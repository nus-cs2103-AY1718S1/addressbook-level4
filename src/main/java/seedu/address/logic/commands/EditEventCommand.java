package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.ReadOnlyEvent;

import java.util.List;
import java.util.Set;

public class EditEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_NAME + "NAME] "
            + "[" + PREFIX_EVENT_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_EVENT_TIME + "TIME] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_DESCRIPTION + "Discuss how to handle Q&A "
            + PREFIX_EVENT_TIME + "02/11/2017 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This event already exists in the address book.";

    private Index index;
    private EditEventDescriptor editEventDescriptor;
    private ReadOnlyEvent editedEvent;
    private ReadOnlyEvent eventToEdit;


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }

    @Override
    protected void undo() {

    }

    @Override
    protected void redo() {

    }
}
