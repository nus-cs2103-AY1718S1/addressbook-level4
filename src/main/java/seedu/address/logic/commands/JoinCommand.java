package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.model.association.exceptions.DuplicateParticipationException;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.*;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.Model;
import seedu.address.model.person.*;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;



/**
 * adds a participant to an event, and adds this event to the participant
 */
public class JoinCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "join";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Connect a person to an event "
            + "Parameters: "
            + PREFIX_PERSON + "Person index"
            + PREFIX_EVENT + "Event index (index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON + "1 "
            + PREFIX_EVENT + "2";
    public static final String MESSAGE_JOIN_SUCCESS = "Person \"%1$s\" now participates Event \"%2$s\"";
    public static final String MESSAGE_DUPLICATE_EVENT = "This person has already participated the event ";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private Index personIdx;
    private Index eventIdx;
    private Person personToJoin;
    private ReadOnlyPerson editedPerson;
    private Event eventToJoin;
    private ReadOnlyEvent editedEvent;

    /**
     * creates a new commands to add the specified {@code ReadOnlyPerson}
     */
    public JoinCommand(Index personIdx, Index eventIdx){
        requireAllNonNull(personIdx,eventIdx);

        this.personIdx = personIdx;
        this.eventIdx = eventIdx;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyPerson> lastShownPersonList = model.getFilteredPersonList();
        List<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
        if(personIdx.getZeroBased() >= lastShownPersonList.size()) {
            throw new  CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if(eventIdx.getZeroBased() >= lastShownEventList.size()) {
            throw new  CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToJoin = (Person) lastShownPersonList.get(personIdx.getZeroBased());
        eventToJoin = (Event) lastShownEventList.get(eventIdx.getZeroBased());
        try {
            editedPerson = createEditedPerson(personToJoin, eventToJoin);
            editedEvent = createEditedEvent(eventToJoin, personToJoin);
            model.updatePerson(personToJoin, editedPerson);
            model.updateEvent(eventToJoin, editedEvent);
        } catch (DuplicateParticipationException dpe){
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DuplicateEventException dee) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } catch (EventNotFoundException ene) {
            throw new AssertionError("The target event cannot be missing");
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_JOIN_SUCCESS, personToJoin.getName(),
                eventToJoin.getEventName()));
    }
    private static Person createEditedPerson(Person personToJoin, Event eventToJoin) throws DuplicateParticipationException, DuplicateEventException {
        assert personToJoin != null;

        Name name = personToJoin.getName();
        Phone phone = personToJoin.getPhone();
        Email email = personToJoin.getEmail();
        Address address = personToJoin.getAddress();
        Birthday birthday = personToJoin.getBirthday();
        Set<Tag> tags = personToJoin.getTags();
        ParticipationList updatedEvents = personToJoin.getParticipation();
        if(updatedEvents.contains(eventToJoin)) {
            throw new DuplicateParticipationException();
        }
        updatedEvents.add(eventToJoin);
        return new Person(name, phone, email, address, birthday, tags, updatedEvents);
    }
    private static Event createEditedEvent(Event eventToJoin, Person personToJoin) throws DuplicateParticipationException, DuplicatePersonException {
        assert eventToJoin != null;

        EventName name = eventToJoin.getEventName();
        EventDescription desc = eventToJoin.getDescription();
        EventTime time = eventToJoin.getEventTime();
        ParticipantList updatedParticipants = eventToJoin.getParticipantList();
        if(updatedParticipants.contains(personToJoin)) {
            throw  new DuplicateParticipationException();
        }
        updatedParticipants.add(personToJoin);
        return new Event(name, desc, time, updatedParticipants);
    }
    @Override
    protected void undo() {
        try {
            model.updatePerson(editedPerson, personToJoin);
            model.updateEvent(editedEvent, editedEvent);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (DuplicateEventException dee) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (EventNotFoundException ene) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    protected void redo() {
        try {
            model.updateEvent(eventToJoin, editedEvent);
            model.updatePerson(personToJoin, editedPerson);
        } catch (DuplicateEventException dee) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (EventNotFoundException ene) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }
}
