package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.group.ReadOnlyGroup;

/**
 * A utility class for Group.
 */
public class GroupUtil {

    /**
     * Returns an add command string for adding the {@code group}.
     */
    public static String getAddCommand(ReadOnlyGroup group) {
        return AddCommand.COMMAND_WORD + " " + getGroupDetails(group);
    }

    /**
     * Returns the part of command string for the given {@code group}'s details.
     */
    public static String getGroupDetails(ReadOnlyGroup group) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + group.getName().fullName + " ");
        return sb.toString();
    }
}
