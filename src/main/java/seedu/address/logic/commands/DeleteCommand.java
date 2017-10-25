package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.meeting.MeetingContainsFullWordPredicate;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
        String personToDeleteName = personToDelete.getName().toString();
        String[] nameArray = {personToDeleteName};
        model.updateFilteredMeetingList(new MeetingContainsFullWordPredicate(Arrays.asList(nameArray)));
        List<ReadOnlyMeeting> lastShownMeetingList = model.getFilteredMeetingList();

        while (true) {
            try {
                Index firstIndex = ParserUtil.parseIndex("1");
                ReadOnlyMeeting meetingToDelete = lastShownMeetingList.get(firstIndex.getZeroBased());
                model.deleteMeeting(meetingToDelete);
            } catch (IllegalValueException ive) {
                assert false : "Error in deleting first item";
            } catch (MeetingNotFoundException mnfe) {
                assert false : "The target meeting cannot be missing";
            } catch (IndexOutOfBoundsException ioobe) {
                break;
            }
        }

        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);

        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
