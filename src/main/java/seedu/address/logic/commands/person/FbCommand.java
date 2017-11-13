package seedu.address.logic.commands.person;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dennaloh
/**
 * Searches for your contact on Facebook
 */
public class FbCommand extends Command {

    public static final String COMMAND_WORD = "facebook";
    public static final String COMMAND_ALIAS = "fb";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for the person identified by the index "
            + "number used in the last person listing on Facebook.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Opened Facebook to search for %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to search on Facebook for
     */
    public FbCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSearch = lastShownList.get(targetIndex.getZeroBased());

        String fbUrl = model.getFbUrl(personToSearch);
        model.openUrl(fbUrl);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToSearch));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FbCommand // instanceof handles nulls
                && this.targetIndex.equals(((FbCommand) other).targetIndex)); // state check
    }
}
