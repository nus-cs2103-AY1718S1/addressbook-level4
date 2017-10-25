package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.PersonHaveParticipateException;
import seedu.address.model.event.exceptions.PersonNotParticipateException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.HaveParticipateEventException;
import seedu.address.model.person.exceptions.NotParticipateEventException;

/**
 * Shows a person does not participate an event any more
 */
public class DisJoinCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "disjoin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Disconnect a person to an event "
            + "Parameters: "
            + PREFIX_PERSON + "Person index"
            + PREFIX_EVENT + "Event index (index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON + "1 "
            + PREFIX_EVENT + "2";

    public static final String MESSAGE_DISJOIN_SUCCESS = "Person \"%1$s\" does not participate Event \"%2$s\"";
    public static final String MESSAGE_PERSON_NOT_PARTICIPATE = "This person already does not participate this event";

    private final Index personIndex;
    private final Index eventIndex;
    private Person personToRemove;
    private Event eventToRemove;

    public DisJoinCommand(Index personIndex, Index eventIndex) {
        this.personIndex = personIndex;
        this.eventIndex = eventIndex;
    }


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownPersonList = model.getFilteredPersonList();
        List<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()
                || eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToRemove = (Person) lastShownPersonList.get(personIndex.getZeroBased());
        eventToRemove = (Event) lastShownEventList.get(eventIndex.getZeroBased());
        try {
            model.quitEvent(personToRemove, eventToRemove);
            return new CommandResult(String.format(MESSAGE_DISJOIN_SUCCESS, personToRemove.getName(),
                    eventToRemove.getEventName()));
        } catch (PersonNotParticipateException pnpe) {
            return new CommandResult(MESSAGE_PERSON_NOT_PARTICIPATE);
        } catch (NotParticipateEventException npee) {
            return new CommandResult(MESSAGE_PERSON_NOT_PARTICIPATE);
        }
    }

    @Override
    protected void undo() {
        try {
            model.joinEvent(personToRemove, eventToRemove);
        } catch (PersonHaveParticipateException pnpe) {
            throw new AssertionError("Undo is to revert a stage; "
                    + "it should not fail");
        } catch (HaveParticipateEventException hpee) {
            throw new AssertionError("Undo is to revert a stage; "
                    + "it should not fail now");
        }
    }

    @Override
    protected void redo() {
        try {
            model.quitEvent(personToRemove, eventToRemove);
        } catch (PersonNotParticipateException pnpe) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        } catch (NotParticipateEventException npee) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }

    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof DisJoinCommand
                && this.eventIndex.equals(((DisJoinCommand) other).eventIndex)
                && this.personIndex.equals(((DisJoinCommand) other).personIndex));
    }
}
