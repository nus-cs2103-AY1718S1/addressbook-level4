package seedu.address.logic.commands;

import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;

/**
 * Finds and lists all meetings in address book whose meeting name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindMeetingCommand extends Command {

    public static final String COMMAND_WORD = "findmeeting";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meetings which contains any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_ALIAS + " Lunch";

    private final MeetingContainsKeywordsPredicate predicate;

    public FindMeetingCommand(MeetingContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindMeetingCommand // instanceof handles nulls
                && this.predicate.equals(((FindMeetingCommand) other).predicate)); // state check
    }
}
