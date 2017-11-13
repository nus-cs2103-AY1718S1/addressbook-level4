package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_REDO_ASSERTION_ERROR;
import static seedu.address.commons.core.Messages.MESSAGE_UNDO_ASSERTION_ERROR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

//@@author Adoby7
/**
 * Edits the details of an existing event in the address book.
 */
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

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";
    public static final String MESSAGE_EVENT_BEING_PARTICIPATED_FAIL = "Some person participates this event,"
            + "please disjoin all participated persons before deleting this event";

    private Index index;
    private EditEventDescriptor editEventDescriptor;
    private ReadOnlyEvent editedEvent;
    private ReadOnlyEvent eventToEdit;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editEventDescriptor details to edit the person with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        eventToEdit = lastShownList.get(index.getZeroBased());
        editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        try {
            model.updateEvent(eventToEdit, editedEvent);
        } catch (DuplicateEventException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } catch (EventNotFoundException pnfe) {
            throw new AssertionError("The target event cannot be missing");
        } catch (DeleteOnCascadeException doce) {
            throw new CommandException(MESSAGE_EVENT_BEING_PARTICIPATED_FAIL);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    @Override
    protected void undo() {
        try {
            model.updateEvent(editedEvent, eventToEdit);
        } catch (DuplicateEventException | EventNotFoundException | DeleteOnCascadeException e) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        try {
            model.updateEvent(eventToEdit, editedEvent);
        } catch (DuplicateEventException | EventNotFoundException | DeleteOnCascadeException e) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
            && editEventDescriptor.equals(e.editEventDescriptor);
    }
    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                             EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventName updatedName = editEventDescriptor.getEventName().orElse(eventToEdit.getEventName());
        EventDescription updatedDescription = editEventDescriptor.getEventDescription().orElse(
                eventToEdit.getDescription());
        EventTime updatedTime = editEventDescriptor.getEventTime().orElse(eventToEdit.getEventTime());

        return new Event(updatedName, updatedDescription, updatedTime);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private EventName name;
        private EventDescription description;
        private EventTime time;

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.name = toCopy.name;
            this.description = toCopy.description;
            this.time = toCopy.time;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.description, this.time);
        }

        public void setEventName(EventName name) {
            this.name = name;
        }

        public Optional<EventName> getEventName() {
            return Optional.ofNullable(name);
        }

        public void setEventDescription(EventDescription description) {
            this.description = description;
        }

        public Optional<EventDescription> getEventDescription() {
            return Optional.ofNullable(description);
        }

        public void setEventTime(EventTime time) {
            this.time = time;
        }

        public Optional<EventTime> getEventTime() {
            return Optional.ofNullable(time);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getEventName().equals(e.getEventName())
                    && getEventDescription().equals(e.getEventDescription())
                    && getEventTime().equals(e.getEventTime());
        }
    }
}
