package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_WRONG_LISTING_UNIT_FAILURE;
import static seedu.address.model.ListingUnit.MODULE;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RemarkChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.Remark;
import seedu.address.model.module.exceptions.RemarkNotFoundException;

//@@author junming403
/**
 * Deletes the remark with specified index.
 */
public class DeleteRemarkCommand extends UndoableCommand {

    public static final String MESSAGE_DELETE_REMARK_MODULE_SUCCESS = "Deleted remark: %1$s";

    private final Index index;
    /**
     * @param index       of the remark in the remark list to delete.
     */
    public DeleteRemarkCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<Remark> lastShownList = model.getFilteredRemarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Remark remarkToDelete = lastShownList.get(index.getZeroBased());

        if (ListingUnit.getCurrentListingUnit().equals(MODULE)) {
            try {
                model.deleteRemark(remarkToDelete);
            } catch (RemarkNotFoundException e) {
                throw new CommandException(e.getMessage());
            }
            EventsCenter.getInstance().post(new RemarkChangedEvent());
            return new CommandResult(String.format(MESSAGE_DELETE_REMARK_MODULE_SUCCESS, remarkToDelete));
        } else {
            throw new CommandException(MESSAGE_WRONG_LISTING_UNIT_FAILURE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRemarkCommand // instanceof handles nulls
                && this.index.equals(((DeleteRemarkCommand) other).index)); // state check
    }
}
