package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MEMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ClearPersonListEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.MemberList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author eldriclim

/**
 * Adds an Event to address book
 */
public class ScheduleAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "s-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_MEMBER + "[INDEX]... "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_TIME + "DATETIME "
            + PREFIX_EVENT_DURATION + "DURATION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_MEMBER + "1 2 3 "
            + PREFIX_EVENT_NAME + "Movie Date "
            + PREFIX_EVENT_TIME + "2017-01-01 10:00 "
            + PREFIX_EVENT_DURATION + "2h30m ";

    public static final String MESSAGE_SUCCESS = "New Event added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";
    public static final String ERROR_INVALID_INDEX = "Invalid index detected.";
    public static final String ERROR_FAIL_TO_UPDATE_MEMBER = "An update error has occured.";

    private final EventName eventName;
    private final EventTime eventTime;
    private final EventDuration eventDuration;
    private final ArrayList<Index> uniqueMemberIndexes;

    public ScheduleAddCommand(EventName eventName, EventTime eventTime, EventDuration eventDuration,
                              Set<Index> uniqueMemberIndexs) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventDuration = eventDuration;
        this.uniqueMemberIndexes = new ArrayList<>(uniqueMemberIndexs);
    }


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        ArrayList<ReadOnlyPerson> toUpdate = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toReplace = new ArrayList<>();
        Event event = new Event(new MemberList(), eventName, eventTime, eventDuration);

        for (Index index : uniqueMemberIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(ERROR_INVALID_INDEX);
            }
            EditEventListPersonDescriptor toEditPerson = new EditEventListPersonDescriptor(
                    lastShownList.get(index.getZeroBased()), event);

            toUpdate.add(lastShownList.get(index.getZeroBased()));
            toReplace.add(toEditPerson.createUpdatedPerson());
        }

        try {
            String commandResultString = "";
            if (model.hasEvenClashes(event)) {
                commandResultString += "Warning: An event clash has been detected.\n";
            }

            model.addEvent(toUpdate, toReplace, event);

            commandResultString += String.format(MESSAGE_SUCCESS, event.toString());
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            EventsCenter.getInstance().post(new ClearPersonListEvent());

            return new CommandResult(commandResultString);
        } catch (DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } catch (DuplicatePersonException dpe) {
            //Should not reach this point
            throw new CommandException(ERROR_FAIL_TO_UPDATE_MEMBER);
        } catch (PersonNotFoundException pnfe) {
            //Should not reach this point
            throw new AssertionError("The target person cannot be missing");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleAddCommand // instanceof handles nulls
                && eventName.equals(((ScheduleAddCommand) other).eventName)
                && eventTime.equals(((ScheduleAddCommand) other).eventTime)
                && eventDuration.equals(((ScheduleAddCommand) other).eventDuration)
                && uniqueMemberIndexes.equals(((ScheduleAddCommand) other).uniqueMemberIndexes));
    }

    /**
     * Stores the details of modified person with updated event list. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    private static class EditEventListPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Event> events;
        private DateAdded dateAdded;
        private Birthday birthday;

        public EditEventListPersonDescriptor(ReadOnlyPerson toCopy, Event event) {
            this.name = toCopy.getName();
            this.phone = toCopy.getPhone();
            this.email = toCopy.getEmail();
            this.address = toCopy.getAddress();
            this.tags = toCopy.getTags();
            this.dateAdded = toCopy.getDateAdded();
            this.birthday = toCopy.getBirthday();

            this.events = createModifiableEventList(toCopy.getEvents());
            this.events.add(event);
        }

        public Set<Event> createModifiableEventList(Set<Event> unmodifiableEventList) {
            Set<Event> modifiableEventList = new HashSet<>(unmodifiableEventList);
            return modifiableEventList;
        }

        public Person createUpdatedPerson() {
            return new Person(name, birthday, phone, email, address, tags, events, dateAdded);
        }
    }
}
