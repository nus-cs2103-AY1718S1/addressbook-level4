package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a remark to an existing person in the rolodex.
 */
public class RemarkCommand extends UndoCommand {

    public static final String COMMAND_WORD = "remark";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "note", "comment"));

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";


}
