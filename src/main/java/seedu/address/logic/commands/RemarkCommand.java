package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.ListingUnit.MODULE;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RemarkChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.Remark;
import seedu.address.model.module.exceptions.DuplicateRemarkException;
import seedu.address.model.module.exceptions.RemarkNotFoundException;

//@@author junming403
/**
 * Add a remark to a module with specified index.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_SAMPLE_REMARK_INFORMATION = "The module CS2103T introduces the "
            + "necessary conceptual and analytical tools for systematic and rigorous development of software systems.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remark the module with some supplementary "
            + "information.\n "
            + "Parameters: INDEX (must be a positive integer) "
            + "[ADDITIONAL INFORMATION]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + MESSAGE_SAMPLE_REMARK_INFORMATION + "\n"
            + COMMAND_WORD + " -d: Delete the remark with specified index "
            + "Parameters: INDEX (must be a positive integer)..\n"
            + "Example: " + COMMAND_WORD + " -d 1 " + "\n";

    public static final String MESSAGE_REMARK_MODULE_SUCCESS = "Remarked Module: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Deleted Remark: %1$s";
    public static final String MESSAGE_WRONG_LISTING_UNIT_FAILURE = "You can only remark a module";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


    private final String remarkContent;
    private final Index index;
    private final boolean isDelete;

    /**
     * @param index       of the module in the module list to remark.
     * @param content the new remark content.
     */
    public RemarkCommand(Index index, String content) {
        requireNonNull(index);
        requireNonNull(content);

        this.remarkContent = content;
        this.index = index;
        this.isDelete = false;
    }

    /**
     * @param index       of the remark in the remark list to delete.
     */
    public RemarkCommand(Index index) {
        requireNonNull(index);

        this.remarkContent = null;
        this.index = index;
        this.isDelete = true;
    }



    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (isDelete) {
            return executeDeleteRemark();
        } else {
            return executeAddRemark();
        }
    }

    /**
     * delete the remark with given index.
     */
    public CommandResult executeDeleteRemark() throws CommandException {
        List<Remark> lastShownList = model.getFilteredRemarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Remark remarkToDelete = lastShownList.get(index.getZeroBased());

        if (ListingUnit.getCurrentListingUnit().equals(MODULE)) {
            try {
                model.deleteRemark(remarkToDelete);
                logger.info("---[Remark deleting success]Deleted remark:" + remarkToDelete);
            } catch (RemarkNotFoundException e) {
                logger.info("---[Remark deleting failure]The remark to delete does not exist: " + remarkContent);
                throw new CommandException(e.getMessage());
            }
            EventsCenter.getInstance().post(new RemarkChangedEvent());
            return new CommandResult(String.format(MESSAGE_DELETE_REMARK_SUCCESS, remarkToDelete));
        } else {
            throw new CommandException(MESSAGE_WRONG_LISTING_UNIT_FAILURE);
        }
    }

    /**
     * Adds in the remark.
     */
    public CommandResult executeAddRemark() throws CommandException {
        List<ReadOnlyLesson> lastShownList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyLesson moduleToRemark = lastShownList.get(index.getZeroBased());

        if (ListingUnit.getCurrentListingUnit().equals(MODULE)) {
            try {
                Remark remark = new Remark(remarkContent, moduleToRemark.getCode());
                model.addRemark(remark);
                logger.info("---[Remark success]Added Remark: " + remark);
            } catch (DuplicateRemarkException e) {
                logger.info("---[Remark failure]Duplicate Remark: " + remarkContent);
                throw new CommandException(e.getMessage());
            } catch (IllegalValueException e) {
                logger.info("---[Remark failure]Invalid Remark" + remarkContent);
                throw new CommandException(e.getMessage());
            }
            EventsCenter.getInstance().post(new RemarkChangedEvent());
            return new CommandResult(String.format(MESSAGE_REMARK_MODULE_SUCCESS, moduleToRemark.getCode()));
        } else {
            throw new CommandException(MESSAGE_WRONG_LISTING_UNIT_FAILURE);
        }
    }


    @Override
    public boolean equals(Object other) {

        if (isDelete != ((RemarkCommand) other).isDelete) {
            return false;
        }

        if (isDelete) {
            return other == this // short circuit if same object
                    || (other instanceof RemarkCommand // instanceof handles nulls
                    && index.equals((((RemarkCommand) other).index)));
        } else {
            return other == this // short circuit if same object
                    || (other instanceof RemarkCommand // instanceof handles nulls
                    && remarkContent.equals(((RemarkCommand) other).remarkContent)
                    && index.equals((((RemarkCommand) other).index)));
        }
    }
}
