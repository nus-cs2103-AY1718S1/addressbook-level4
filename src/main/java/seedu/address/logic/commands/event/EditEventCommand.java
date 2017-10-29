package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Name;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editE";
    public static final String COMMAND_ALIAS = "eE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE_TIME + "DATE] "
            + "[" + PREFIX_ADDRESS + "VENUE]"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE_TIME + "12052015 08:30 "
            + PREFIX_ADDRESS + "JK street";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

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
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        ReadOnlyEvent eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        try {
            model.updateEvent(eventToEdit, editedEvent);
        } catch (DuplicateEventException dee) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError("The target event cannot be missing");
        }
        model.updateFilteredEventsList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code personToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                             EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Name updatedName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        DateTime updatedTime = editEventDescriptor.getTime().orElse(eventToEdit.getTime());
        Address updatedVenue = editEventDescriptor.getVenue().orElse(eventToEdit.getVenue());

        return new Event(updatedName, updatedTime, updatedVenue);
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
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditEventDescriptor {
        private Name name;
        private DateTime time;
        private Address venue;

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.name = toCopy.name;
            this.time = toCopy.time;
            this.venue = toCopy.venue;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.time, this.venue);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setTime(DateTime time) {
            this.time = time;
        }

        public Optional<DateTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setVenue(Address venue) {
            this.venue = venue;
        }

        public Optional<Address> getVenue() {
            return Optional.ofNullable(venue);
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

            return getName().equals(e.getName())
                    && getTime().equals(e.getTime())
                    && getVenue().equals(e.getVenue());
        }
    }
}

