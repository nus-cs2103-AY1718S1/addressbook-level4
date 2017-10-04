package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import com.sun.org.apache.regexp.internal.RE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d Remark %2$s";

    private final Index index;
    private final String remark;

    public RemarkCommand(Index index, String remark){
        requireNonNull(index);
        requireNonNull(remark);

        this.remark = remark;
        this.index = index;

    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS,index.getOneBased(),remark));
    }

    @Override
    public boolean equals(Object compare){
        if(compare == this){
            return true;
        } else if (!(compare instanceof RemarkCommand)){
            return false;
        }

        RemarkCommand other = (RemarkCommand) compare;
        return other.index.equals(index) && other.remark.equals(remark);
    }
}
