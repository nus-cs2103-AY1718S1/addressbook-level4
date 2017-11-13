package seedu.address.logic.commands;

import seedu.address.model.meeting.MeetingContainsFullWordPredicate;

//@@author kyngyi
/**
 * Finds and lists all meetings in address book whose meeting name contains all of the keywords.
 * Keyword matching is case sensitive.
 */
public class FindExactMeetingCommand extends Command {

    public static final String COMMAND_WORD = "findexactmeeting";
    public static final String COMMAND_ALIAS = "fem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meetings which contains the exactly of "
            + "the specified words (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_ALIAS + " Tuition Class";

    private final MeetingContainsFullWordPredicate predicate;

    public FindExactMeetingCommand(MeetingContainsFullWordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredMeetingList(predicate);
        return new CommandResult(getMessageForMeetingListShownSummary(model.getFilteredMeetingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindExactMeetingCommand // instanceof handles nulls
                && this.predicate.equals(((FindExactMeetingCommand) other).predicate)); // state check
    }
}
