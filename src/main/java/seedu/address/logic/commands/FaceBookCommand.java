package seedu.address.logic.commands;
//@@author RonakLakhotia
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 *  Shows a person's Facebook profile page in the browser panel.
 */
public class FaceBookCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "facebook";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Shows the profile of the user whose"
            + " index is entered\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_FACEBOOK_SHOWN_SUCCESS = "Profile of Person: %1$s";
    public static final String MESSAGE_NO_USERNAME = "This Person has no Facebook username!\n";

    private static final Logger logger = LogsCenter.getLogger(FaceBookCommand.class);
    public final Index index;


    public FaceBookCommand (Index index) {
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToShow = lastShownList.get(index.getZeroBased());

        if (personToShow.getUsername().toString().equalsIgnoreCase("")) {
            throw new CommandException(String.format(MESSAGE_NO_USERNAME, personToShow));
        }

        try {
            assert index.getZeroBased() >= 0;
            model.faceBook(personToShow);
        } catch (PersonNotFoundException pnfe) {
            logger.warning("Person is missing");
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_FACEBOOK_SHOWN_SUCCESS, personToShow));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FaceBookCommand // instanceof handles nulls
                && this.index.equals(((FaceBookCommand) other).index)); // state check
    }
}
