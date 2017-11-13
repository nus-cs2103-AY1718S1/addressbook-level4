//@@author arnollim
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Format the feedback to the user when asked "why" about a certain person index.
 */
public class WhyCommand extends Command {

    public static final String[] COMMAND_WORDS = {"why"};
    public static final String COMMAND_WORD = "why";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tells you why.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String SHOWING_WHY_MESSAGE = "Because %1$s lives in %2$s";
    public static final String SHOWING_WHY_MESSAGE_2 = "Because %1$s is born in %2$s";
    public static final String SHOWING_WHY_MESSAGE_3 = "Because %1$s's email is %2$s";
    public static final String SHOWING_WHY_MESSAGE_4 = "Because %1$s appreciates you";
    public static final String SHOWING_WHY_MESSAGE_5 = "Because %1$s is a really cool person";
    public static final String SHOWING_WHY_MESSAGE_6 = "Because %1$s can make the world a better place";
    public static final String SHOWING_WHY_MESSAGE_NO_ADDRESS = "Because %1$s has no address";
    public static final String SHOWING_WHY_MESSAGE_NO_DOB = "Because %1$s has no date of birth";
    public static final String SHOWING_WHY_MESSAGE_NO_EMAIL = "Because %1$s has no email";

    private final Index targetIndex;

    public WhyCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAnswer = lastShownList.get(targetIndex.getZeroBased());
        String reason = personToAnswer.getReason();
        return new CommandResult(reason);
    }
}
