package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

import static java.util.Objects.requireNonNull;


public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_SAMPLE_REMARK_INFORMATION = "The module CS2103T introduces the " +
            "necessary conceptual and analytical tools for systematic and rigorous development of software systems.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remark the module with some supplementary " +
            "information.\n "
            + "Parameters: INDEX (must be a positive integer) "
            + "[ADDITIONAL INFORMATION]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + MESSAGE_SAMPLE_REMARK_INFORMATION;

    public static final String MESSAGE_REMARK_MODULE_SUCCESS = "Remarked Module: %1$s";

    private final String remarkContent;

    /**
     * @param index       of the module in the module list to remark.
     * @param content the new remark content.
     */
    public RemarkCommand(Index index, String content) {
        requireNonNull(index);
        requireNonNull(content);

        this.remarkContent = content;
    }
    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }
}
