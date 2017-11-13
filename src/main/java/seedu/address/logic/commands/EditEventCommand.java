package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.event.Desc;
import seedu.address.model.person.event.Event;
import seedu.address.model.person.event.EventDate;
import seedu.address.model.person.event.Header;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.model.person.exceptions.EventNotFoundException;

//@@author royceljh
/**
 * Edits the details of an existing event in the address book.
 */
public class EditEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the last event listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HEADER + "HEADER] "
            + "[" + PREFIX_DESC + "DESC] "
            + "[" + PREFIX_EVENT_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HEADER + "birthday party "
            + PREFIX_EVENT_DATE + "2017-01-12";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
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
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(ReadOnlyEvent eventToEdit,
                                           EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Header updatedHeader = editEventDescriptor.getHeader().orElse(eventToEdit.getHeader());
        Desc updatedDesc = editEventDescriptor.getDesc().orElse(eventToEdit.getDesc());
        EventDate updatedEventDate = editEventDescriptor.getEventDate().orElse(eventToEdit.getEventDate());

        return new Event(updatedHeader, updatedDesc, updatedEventDate);
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
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private Header header;
        private Desc desc;
        private EventDate eventDate;

        public EditEventDescriptor() {}

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            this.header = toCopy.header;
            this.desc = toCopy.desc;
            this.eventDate = toCopy.eventDate;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.header, this.desc, this.eventDate);
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Optional<Header> getHeader() {
            return Optional.ofNullable(header);
        }

        public void setDesc(Desc desc) {
            this.desc = desc;
        }

        public Optional<Desc> getDesc() {
            return Optional.ofNullable(desc);
        }

        public void setEventDate(EventDate eventDate) {
            this.eventDate = eventDate;
        }

        public Optional<EventDate> getEventDate() {
            return Optional.ofNullable(eventDate);
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

            return getHeader().equals(e.getHeader())
                    && getDesc().equals(e.getDesc())
                    && getEventDate().equals(e.getEventDate());
        }
    }
}
