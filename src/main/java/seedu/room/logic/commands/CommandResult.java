package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
    }

    public CommandResult(String firstFeedBackToUser, String secondFeedBackToUser) {
        this.feedbackToUser = requireNonNull(firstFeedBackToUser + "\n" + secondFeedBackToUser);
    }
}
