package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelChangeActiveTabEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using it's last displayed index from the address book and displays his instagram page
 */

//@@author hengyu95
public class InstaCommand extends Command {

    public static final String COMMAND_WORD = "insta";
    public static final String COMMAND_ALIAS = "i";
    public static final int INSTA_TAB = 2;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the Instagram account of the person identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s (%2$s)";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS2 = "\nUser ID is: ";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS3 = "\nUser ID is unavailable, redirecting to Instagram "
            + "home page...";




    private final Index targetIndex;

    public InstaCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new BrowserPanelChangeActiveTabEvent(INSTA_TAB));

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));

        if (personToEdit.getUserId().value.equals("-")) {
            return new CommandResult(new StringBuilder()
                    .append(String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                            lastShownList.get(targetIndex.getZeroBased()).getName().fullName,
                            targetIndex.getOneBased()))
                    .append(MESSAGE_SELECT_PERSON_SUCCESS3).toString());
        } else {
            return new CommandResult(new StringBuilder()
                    .append(String.format(MESSAGE_SELECT_PERSON_SUCCESS,
                            lastShownList.get(targetIndex.getZeroBased()).getName().fullName,
                            targetIndex.getOneBased()))
                    .append(MESSAGE_SELECT_PERSON_SUCCESS2)
                    .append(personToEdit.getUserId().value).toString());
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InstaCommand // instanceof handles nulls
                && this.targetIndex.equals(((InstaCommand) other).targetIndex)); // state check
    }
}
