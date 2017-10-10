package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD_ALIAS:
        case AddCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new AddCommandParser().parse(arguments);

        case BlacklistCommand.COMMAND_WORD_ALIAS:
        case BlacklistCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new BlacklistCommand();

        case BanCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new BanCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD_ALIAS:
        case EditCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD_ALIAS:
        case SelectCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD_ALIAS:
        case DeleteCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD_ALIAS:
        case ClearCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new ClearCommand();

        case FindCommand.COMMAND_WORD_ALIAS:
        case FindCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD_ALIAS:
        case ListCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD_ALIAS:
        case HistoryCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            logger.info("----------------[ACTUAL COMMAND][" + commandWord + "]");
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
