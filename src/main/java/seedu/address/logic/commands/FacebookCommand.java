package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowFacebookRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Displays Facebook Log In page.
 */
public class FacebookCommand extends Command {


    //@@author LeeYingZheng
    public static final String COMMAND_WORDVAR_1 = "facebook";
    public static final String COMMAND_WORDVAR_2 = "fb";
    //@@author

    //author taojiashu
    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + ": Searches the name of the person specified by the index number in Facebook. "
            + "The login page will pop up if it is the first time this command is invoked.\n"
            + "Example: "
            + COMMAND_WORDVAR_1 + " 1 "
            + "OR "
            + COMMAND_WORDVAR_2 + " 1";

    public static final String SHOWING_FACEBOOK_MESSAGE = "Opened facebook window.";

    private final Index targetIndex;

    public FacebookCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
        String name = person.getName().toString();

        EventsCenter.getInstance().post(new ShowFacebookRequestEvent(name));
        return new CommandResult(SHOWING_FACEBOOK_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacebookCommand // instanceof handles nulls
                && this.targetIndex.equals(((FacebookCommand) other).targetIndex)); // state check
    }
    //@@author
}
