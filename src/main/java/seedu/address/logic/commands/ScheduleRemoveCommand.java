package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INDEXES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.EventNotFoundException;
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
 * Remove multiple Events from address book
 */
public class ScheduleRemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "s-remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove events from the address book. "
            + "Parameters: "
            + PREFIX_EVENT_INDEXES + "[INDEX]... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_INDEXES + "1 2 3 ";

    public static final String MESSAGE_SUCCESS = "Event(s) removed: \n%1$s";
    public static final String ERROR_EVENT_NOT_FOUND = "Invalid event detected.";
    public static final String ERROR_INVALID_INDEX = "Invalid index detected.";
    public static final String ERROR_FAIL_TO_UPDATE_MEMBER = "An update error has occured.";

    private final ArrayList<Index> uniqueEventIndexes;

    public ScheduleRemoveCommand(Set<Index> eventListIndex) {
        this.uniqueEventIndexes = new ArrayList<>(eventListIndex);
    }

    public String getRemovedEventsString(ArrayList<Event> removedEvents) {
        ArrayList<String> outputString = new ArrayList<>();
        removedEvents.forEach(e -> outputString.add(e.toString()));

        return StringUtil.multiStringPrint(outputString, "\n");
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<Event> lastShownEventList = model.getEventList();

        ArrayList<Event> toRemoveEvents = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toUpdatePersons = new ArrayList<>();
        ArrayList<ReadOnlyPerson> toReplacePersons = new ArrayList<>();


        for (Index index : uniqueEventIndexes) {
            if (index.getZeroBased() >= lastShownEventList.size()) {
                throw new CommandException(ERROR_INVALID_INDEX);
            }
            toRemoveEvents.add(lastShownEventList.get(index.getZeroBased()));
        }

        model.getAddressBook().getPersonList().stream().filter(p ->
                !Collections.disjoint(p.getEvents(), toRemoveEvents)).forEach(toUpdatePersons::add);

        toUpdatePersons.forEach(p -> {
            EditEventListPersonDescriptor toEditPerson = new EditEventListPersonDescriptor(
                    p, toRemoveEvents);
            toReplacePersons.add(toEditPerson.createUpdatedPerson());
        });

        try {
            model.removeEvents(toUpdatePersons, toReplacePersons, toRemoveEvents);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            Index defaultIndex = new Index(0);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));

            return new CommandResult(String.format(MESSAGE_SUCCESS, getRemovedEventsString(toRemoveEvents)));

        } catch (DuplicatePersonException dpe) {
            //Should not reach this point
            throw new CommandException(ERROR_FAIL_TO_UPDATE_MEMBER);
        } catch (PersonNotFoundException pnfe) {
            //Should not reach this point
            throw new AssertionError("The target person cannot be missing");
        } catch (EventNotFoundException enf) {
            //Should not reach this point
            throw new CommandException(ERROR_EVENT_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleRemoveCommand // instanceof handles nulls
                && uniqueEventIndexes.equals(((ScheduleRemoveCommand) other).uniqueEventIndexes));
    }


    /**
     * Stores the details of modified person with updated event list.
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

        public EditEventListPersonDescriptor(ReadOnlyPerson toCopy, ArrayList<Event> toRemoveEvents) {
            this.name = toCopy.getName();
            this.phone = toCopy.getPhone();
            this.email = toCopy.getEmail();
            this.address = toCopy.getAddress();
            this.tags = toCopy.getTags();
            this.dateAdded = toCopy.getDateAdded();

            this.events = createModifiableEventList(toCopy.getEvents());
            this.birthday = toCopy.getBirthday();

            removeEvents(toRemoveEvents);
        }

        private void removeEvents(ArrayList<Event> toRemoveEvents) {
            toRemoveEvents.stream().filter(e -> events.contains(e)).forEach(e -> events.remove(e));
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
