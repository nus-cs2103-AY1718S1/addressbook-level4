package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_REDO_ASSERTION_ERROR;
import static seedu.address.commons.core.Messages.MESSAGE_UNDO_ASSERTION_ERROR;
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

//@@author Adoby7
/**
 * Shows a person does not participate an event any more
 */
public class DisjoinCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "disjoin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Disconnect a person to an event "
            + "Parameters: "
            + PREFIX_PERSON + "Person index"
            + PREFIX_EVENT + "Event index (index must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PERSON + "1 "
            + PREFIX_EVENT + "2";

    public static final String MESSAGE_DISJOIN_SUCCESS = "Person \"%1$s\" does not participate Event \"%2$s\"";
    public static final String MESSAGE_PERSON_NOT_PARTICIPATE = "This person does not participate this event";

    private final Index personIndex;
    private final Index eventIndex;
    private Person personToRemove;
    private Event eventToRemove;

    public DisjoinCommand(Index personIndex, Index eventIndex) {
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
            throw  new CommandException(MESSAGE_PERSON_NOT_PARTICIPATE);
        } catch (NotParticipateEventException npee) {
            throw  new CommandException(MESSAGE_PERSON_NOT_PARTICIPATE);
        }
    }

    @Override
    protected void undo() {
        try {
            model.joinEvent(personToRemove, eventToRemove);
        } catch (PersonHaveParticipateException | HaveParticipateEventException e) {
            throw new AssertionError(MESSAGE_UNDO_ASSERTION_ERROR);
        }
    }

    @Override
    protected void redo() {
        try {
            model.quitEvent(personToRemove, eventToRemove);
        } catch (PersonNotParticipateException | NotParticipateEventException e) {
            throw new AssertionError(MESSAGE_REDO_ASSERTION_ERROR);
        }
    }

    /**
     * Assign the target person and event
     * Only used for testing
     */
    public void assignValueForTest(Person person, Event event) {
        this.personToRemove = person;
        this.eventToRemove = event;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof DisjoinCommand
                && this.eventIndex.equals(((DisjoinCommand) other).eventIndex)
                && this.personIndex.equals(((DisjoinCommand) other).personIndex));
    }
}
