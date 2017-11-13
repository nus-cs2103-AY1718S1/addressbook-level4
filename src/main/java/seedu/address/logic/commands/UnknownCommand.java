package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.AddEventCommandParser;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.DeleteEventCommandParser;
import seedu.address.logic.parser.DeleteProfilePicCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.EditEventCommandParser;
import seedu.address.logic.parser.EmailCommandParser;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.LocationCommandParser;
import seedu.address.logic.parser.RemoveTagCommandParser;
import seedu.address.logic.parser.SelectCommandParser;
import seedu.address.logic.parser.UpdateProfilePicCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author philemontan
/**
 * This class is used to process all unknown commands, i.e user input that does not match any existing COMMAND_WORD
 * this is done by searching for COMMAND_WORDs with a Levenshtein distance <= the set ACCEPTABLE_LEVENSHTEIN_DISTANCE,
 * away from the user input command word
 *
 * The Levenshtein distance calculator implementation is adapted entirely from:
 * https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
 * Full credits have been given in the method comment:{@link #levenshteinDistance(CharSequence, CharSequence)}
 */
public class UnknownCommand extends Command {
    /**
     * These constants can be varied to allow wider similarity detection, which will come at the cost of performance.
     * They should not be chosen arbitrarily. For example,
     * the ACCEPTABLE_LEVENSHTEIN_DISTANCE should be less than the shortest system-recognized COMMAND_WORD available,
     * although this is not a strict rule.
     * the ACCEPTABLE_MAXIMUM_COMMAND_WORD_LENGTH should be computed by summing the longest system-recognized
     * COMMAND_WORD available, and the set ACCEPTABLE_LEVENSHTEIN_DISTANCE, to prevent needless checking
     */
    public static final int ACCEPTABLE_LEVENSHTEIN_DISTANCE = 2;
    public static final int ACCETABLE_MAXIMUM_COMMAND_WORD_LENGTH = 18;

    //A constant String[] of all known COMMAND WORDS; this list should be extended when new Command types are created
    private static final String[] ALL_COMMAND_WORDS = {
        AddCommand.COMMAND_WORD, AddEventCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD,
        DeleteEventCommand.COMMAND_WORD, DeleteProfilePicCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
        EditEventCommand.COMMAND_WORD, EmailCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD, LocationCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, RemoveTagCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD, SortCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
        UpdateProfilePicCommand.COMMAND_WORD
    };

    private String commandWord;
    private String arguments;
    private String promptToUser;
    private Command suggestedCommand;
    private boolean suggestionFoundHasBeenExecuted = false;

    public UnknownCommand(String inputCommandWord, String inputArguments) {
        commandWord = inputCommandWord;
        arguments = inputArguments;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert suggestionFoundHasBeenExecuted : "Invalid behaviour: UnknownCommand.execute() has been called before"
                + "UnknownCommand.suggestionFound()";
        return new CommandResult(promptToUser);
    }

    public Command getSuggestedCommand() {
        return suggestedCommand;
    }

    /**
     * This method initiates the computation of Levenshtein distance between the user input commandWord, and each of
     * the system-recognized COMMAND_WORDs, in the constant String[] ALL_COMMAND_WORDS.
     * This method should always be executed before the execute() method is called
     * @return true if minimum distance found is <= the ACCEPTABLE_LEVENSHTEIN_DISTANCE constant set, else false
     */
    public boolean suggestionFound() throws ParseException {
        suggestionFoundHasBeenExecuted = true;

        //Checks for invalid commandWord length, i.e 19 and above (To prevent performance issues)
        if (invalidLengthDetected()) {
            return false;
        }

        String closestCommandWord = searchMinDistanceCommand();

        //closestCommandWord is set to null if no acceptable match is found
        if (closestCommandWord == null) {
            return false;
        } else {
            setSuggestedCommand(closestCommandWord);
            setPromptToUser(closestCommandWord);
            return true;
        }
    }

    //Sets the prompt to the user based on the match found
    private void setPromptToUser(String closestCommandWord) {
        promptToUser = "Did you mean: " + closestCommandWord + arguments + " ?" + "\n" + "Respond: "
                + "'yes' or 'y' to accept the suggested command." + "\n"
                + "Suggested command will be discarded otherwise.";
    }

    /**
     * Iterative search for an acceptable & minimum distance match to a known command.
     * @return null if no acceptable match found (i.e Levenshtein distance between commandWord and all known Commands
     * is more than the ACCEPTABLE_LEVENSTHEIN_DISTANCE), else returns the match with smallest Levenshtein distance.
     */
    private String searchMinDistanceCommand() {
        int min = ACCEPTABLE_LEVENSHTEIN_DISTANCE + 1;
        int tempDistance;
        String closestCommandWord = null;

        for (String s: ALL_COMMAND_WORDS) {
            tempDistance = levenshteinDistance(s, commandWord);
            if (tempDistance < min) { // tempDistance within acceptable range
                min = tempDistance;
                closestCommandWord = s;
            }
        }
        return closestCommandWord;
    }

    /**
     * This method checks for an invalid commandWord length
     * @return true if commandWord.length is more than the ACCEPTABLE_MAXIMUM_COMMAND_WORD_LENGTH
     * @throws ParseException if user input command length is 0
     */
    private boolean invalidLengthDetected () {
        return commandWord.length() > ACCETABLE_MAXIMUM_COMMAND_WORD_LENGTH || commandWord.length() == 0;
    }

    //@@author philemontan-reused
    /**
     * This switch is adapted from the original AddressBookParser.
     * If a match is found, but input arguments are invalid, the user will be prompted with the MESSAGE_USAGE of the
     * matched Command
     * @param closestCommandWord
     * @throws ParseException
     */
    private void setSuggestedCommand(String closestCommandWord) throws ParseException {
        switch (closestCommandWord) {
        case AddCommand.COMMAND_WORD:
            suggestedCommand = new AddCommandParser().parse(arguments);
            break;

        case AddEventCommand.COMMAND_WORD:
            suggestedCommand = new AddEventCommandParser().parse(arguments);
            break;

        case EditCommand.COMMAND_WORD:
            suggestedCommand = new EditCommandParser().parse(arguments);
            break;

        case EditEventCommand.COMMAND_WORD:
            suggestedCommand = new EditEventCommandParser().parse(arguments);
            break;

        case UpdateProfilePicCommand.COMMAND_WORD:
            suggestedCommand = new UpdateProfilePicCommandParser().parse(arguments);
            break;

        case DeleteProfilePicCommand.COMMAND_WORD:
            suggestedCommand = new DeleteProfilePicCommandParser().parse(arguments);
            break;

        case SelectCommand.COMMAND_WORD:
            suggestedCommand = new SelectCommandParser().parse(arguments);
            break;

        case DeleteCommand.COMMAND_WORD:
            suggestedCommand = new DeleteCommandParser().parse(arguments);
            break;

        case DeleteEventCommand.COMMAND_WORD:
            suggestedCommand = new DeleteEventCommandParser().parse(arguments);
            break;

        case ClearCommand.COMMAND_WORD:
            suggestedCommand = new ClearCommand();
            break;

        case FindCommand.COMMAND_WORD:
            suggestedCommand = new FindCommandParser().parse(arguments);
            break;

        case LocationCommand.COMMAND_WORD:
            suggestedCommand = new LocationCommandParser().parse(arguments);
            break;

        case ListCommand.COMMAND_WORD:
            suggestedCommand = new ListCommand();
            break;

        case HistoryCommand.COMMAND_WORD:
            suggestedCommand = new HistoryCommand();
            break;

        case ExitCommand.COMMAND_WORD:
            suggestedCommand = new ExitCommand();
            break;

        case HelpCommand.COMMAND_WORD:
            suggestedCommand = new HelpCommand();
            break;

        case UndoCommand.COMMAND_WORD:
            suggestedCommand = new UndoCommand();
            break;

        case RedoCommand.COMMAND_WORD:
            suggestedCommand = new RedoCommand();
            break;

        case RemoveTagCommand.COMMAND_WORD:
            suggestedCommand = new RemoveTagCommandParser().parse(arguments);
            break;

        case ImportCommand.COMMAND_WORD:
            suggestedCommand = new ImportCommand();
            break;

        case ExportCommand.COMMAND_WORD:
            suggestedCommand = new ExportCommand();
            break;

        case EmailCommand.COMMAND_WORD:
            suggestedCommand = new EmailCommandParser().parse(arguments);
            break;

        case SortCommand.COMMAND_WORD:
            suggestedCommand = new SortCommand();
            break;
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@@author philemontan-reused
    /**
     * Levenshtein distance algorithm implementation taken from:
     * https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java,
     * under the Creative Commons Attribution-ShareAlike License.
     * No logical changes were made. Minor checkstyle formatting changes applied.
     * @param lhs
     * @param rhs
     * @return
     */
    public int levenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) {
            cost[i] = i;
        }

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int costReplace = cost[i - 1] + match;
                int costInsert  = cost[i] + 1;
                int costDelete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(costInsert, costDelete), costReplace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }
}
