//@@author keloysiusmak
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ALIAS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMAND;

import java.util.stream.Stream;

import seedu.address.logic.commands.SetAliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetAliasCommand object
 */
public class SetAliasCommandParser implements Parser<SetAliasCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SetAliasCommand
     * and returns an SetAliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetAliasCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMMAND, PREFIX_ALIAS);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS, PREFIX_COMMAND)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAliasCommand.MESSAGE_USAGE));
        }


        String command = ParserUtil.parseCommand(argMultimap.getValue(PREFIX_COMMAND)).get();
        String alias = ParserUtil.parseAlias(argMultimap.getValue(PREFIX_ALIAS)).get();

        if (!(command.equals("add") || command.equals("clear") || command.equals("delete") || command.equals("edit")
                || command.equals("exit") || command.equals("find") || command.equals("help")
                || command.equals("history") || command.equals("list") || command.equals("order")
                || command.equals("redo") || command.equals("remark") || command.equals("select")
                || command.equals("undo") || command.equals("viewalias") || command.equals("setalias")
                || command.equals("settheme") || command.equals("deleteevent") || command.equals("editevent")
                || command.equals("export") || command.equals("findevent") || command.equals("group")
                || command.equals("lock") || command.equals("listevent") || command.equals("currentuser")
                || command.equals("orderevent") || command.equals("selectevent") || command.equals("transfer")
                || command.equals("switch") || command.equals("remove"))) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        if ((alias.equals("add") || alias.equals("clear") || alias.equals("delete") || alias.equals("edit")
                || alias.equals("exit") || alias.equals("find") || alias.equals("help") || alias.equals("history")
                || alias.equals("list") || alias.equals("order") || alias.equals("redo") || alias.equals("remark")
                || alias.equals("select") || alias.equals("undo") || alias.equals("viewalias")
                || alias.equals("setalias") || command.equals("settheme") || command.equals("editevent")
                || command.equals("export") || command.equals("findevent") || command.equals("group")
                || command.equals("lock") || command.equals("listevent") || command.equals("currentuser")
                || command.equals("orderevent") || command.equals("selectevent") || command.equals("transfer")
                || command.equals("switch") || command.equals("remove"))) {
            throw new ParseException(MESSAGE_DUPLICATE_ALIAS);
        }

        return new SetAliasCommand(command, alias);
    }

}
