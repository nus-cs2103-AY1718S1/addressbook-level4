package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BinclearCommand;
import seedu.address.logic.commands.BindeleteCommand;
import seedu.address.logic.commands.BinrestoreCommand;
import seedu.address.logic.commands.BirthdayAddCommand;
import seedu.address.logic.commands.BirthdayRemoveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MapRouteCommand;
import seedu.address.logic.commands.MapShowCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleAddCommand;
import seedu.address.logic.commands.ScheduleRemoveCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SwitchThemeCommand;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagFindCommand;
import seedu.address.logic.commands.TagRemoveCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    //@@author dalessr
    private static ArrayList<String> commandNames = new ArrayList<>();

    public AddressBookParser() {
        commandNames.add(AddCommand.COMMAND_WORD);
        commandNames.add(BinclearCommand.COMMAND_WORD);
        commandNames.add(BindeleteCommand.COMMAND_WORD);
        commandNames.add(BinrestoreCommand.COMMAND_WORD);
        commandNames.add(SortCommand.COMMAND_WORD);
        commandNames.add(EditCommand.COMMAND_WORD);
        commandNames.add(TagAddCommand.COMMAND_WORD);
        commandNames.add(TagFindCommand.COMMAND_WORD);
        commandNames.add(TagRemoveCommand.COMMAND_WORD);
        commandNames.add(BirthdayAddCommand.COMMAND_WORD);
        commandNames.add(BirthdayRemoveCommand.COMMAND_WORD);
        commandNames.add(SelectCommand.COMMAND_WORD);
        commandNames.add(SwitchThemeCommand.COMMAND_WORD);
        commandNames.add(MapShowCommand.COMMAND_WORD);
        commandNames.add(MapRouteCommand.COMMAND_WORD);
        commandNames.add(DeleteCommand.COMMAND_WORD);
        commandNames.add(ClearCommand.COMMAND_WORD);
        commandNames.add(FindCommand.COMMAND_WORD);
        commandNames.add(ListCommand.COMMAND_WORD);
        commandNames.add(HistoryCommand.COMMAND_WORD);
        commandNames.add(ExitCommand.COMMAND_WORD);
        commandNames.add(HelpCommand.COMMAND_WORD);
        commandNames.add(ScheduleAddCommand.COMMAND_WORD);
        commandNames.add(ScheduleRemoveCommand.COMMAND_WORD);
        commandNames.add(UndoCommand.COMMAND_WORD);
        commandNames.add(RedoCommand.COMMAND_WORD);
        commandNames.add(ExportCommand.COMMAND_WORD);
    }

    //@@author
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

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_WORD_2:
        case AddCommand.COMMAND_WORD_3:
            return new AddCommandParser().parse(arguments);

        case BindeleteCommand.COMMAND_WORD:
            return new BindeleteCommandParser().parse(arguments);

        case BinclearCommand.COMMAND_WORD:
            return new BinclearCommand();

        case BinrestoreCommand.COMMAND_WORD:
            return new BinrestoreCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD_2:
        case EditCommand.COMMAND_WORD_3:
            return new EditCommandParser().parse(arguments);

        case TagAddCommand.COMMAND_WORD:
            return new TagAddCommandParser().parse(arguments);

        case TagFindCommand.COMMAND_WORD:
            return new TagFindCommandParser().parse(arguments);

        case TagRemoveCommand.COMMAND_WORD:
            return new TagRemoveCommandParser().parse(arguments);

        case BirthdayAddCommand.COMMAND_WORD:
            return new BirthdayAddCommandParser().parse(arguments);

        case BirthdayRemoveCommand.COMMAND_WORD:
            return new BirthdayRemoveCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD_2:
            return new SelectCommandParser().parse(arguments);

        case SwitchThemeCommand.COMMAND_WORD:
            return new SwitchThemeCommandParser().parse(arguments);

        case MapShowCommand.COMMAND_WORD:
            return new MapShowCommandParser().parse(arguments);

        case MapRouteCommand.COMMAND_WORD:
            return new MapRouteCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD_2:
        case DeleteCommand.COMMAND_WORD_3:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD_2:
        case FindCommand.COMMAND_WORD_3:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD_2:
        case ListCommand.COMMAND_WORD_3:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_WORD_2:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD_2:
            return new HelpCommandParser().parse(arguments);

        case ScheduleAddCommand.COMMAND_WORD:
            return new ScheduleAddCommandParser().parse(arguments);

        case ScheduleRemoveCommand.COMMAND_WORD:
            return new ScheduleRemoveCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommand(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@@author dalessr
    public static ArrayList<String> getCommandNames() {
        return commandNames;
    }

}
