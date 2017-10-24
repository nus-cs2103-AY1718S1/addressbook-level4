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
import seedu.address.model.event.exceptions.PersonHaveParticipateException;
import seedu.address.model.event.exceptions.PersonNotParticipateException;
import seedu.address.model.person.*;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.HaveParticipateEventException;
import seedu.address.model.person.exceptions.NotParticipateEventException;
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
    private Person editedPerson;
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
            model.joinEvent(personToJoin, eventToJoin);
        } catch (PersonHaveParticipateException phpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        } catch (HaveParticipateEventException npee) {
            return new CommandResult(MESSAGE_DUPLICATE_EVENT);
        }
        /*model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);*/
        return new CommandResult(String.format(MESSAGE_JOIN_SUCCESS, personToJoin.getName(),
                eventToJoin.getEventName()));
    }

    @Override
    protected void undo() {
    }

    @Override
    protected void redo() {
        try {
            model.joinEvent(personToJoin, eventToJoin);
        } catch (PersonHaveParticipateException pnpe) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (HaveParticipateEventException npee) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
    }
}
