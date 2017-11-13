package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author justintkj
/**
 * Contains utility methods used for common used command in the various *Command classes.
*/
public class CommandUtil {

    /**
     * Validates current index is smaller than total number of persons
     * @param lastShownList List of ReadOnlyPerson
     * @throws CommandException index is higher than total number of person.(zero based)
     */
    public static void checksIndexSmallerThanList(List<ReadOnlyPerson> lastShownList,
                                                  Index index) throws CommandException {
        assert lastShownList != null;
        assert index != null;
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }
}
