package seedu.address.ui;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.NewPersonInfoEvent;
import seedu.address.commons.events.ui.ResizeMainWindowEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandWordList;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResizeCommand;
import seedu.address.logic.commands.RestoreBackupCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SyncCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.person.ReadOnlyPerson;

//@@author newalter
/**
 * This class provides the relevant suggestions for Auto-Completion in TabCompleTextField
 */
public class SuggestionHeuristic {

    private final String[] sortFieldsList = {"name", "phone", "email", "address", "tag", "meeting"};

    private SortedSet<String> empty = new TreeSet<>();
    private SortedSet<String> commands = new TreeSet<>();
    private SortedSet<String> sortFields = new TreeSet<>();
    private SortedSet<String> tags = new TreeSet<>();
    private SortedSet<String> names = new TreeSet<>();
    private SortedSet<String> phones = new TreeSet<>();
    private SortedSet<String> emails = new TreeSet<>();
    private SortedSet<String> addresses = new TreeSet<>();
    private SortedSet<String> windowSizes = new TreeSet<>();

    public SuggestionHeuristic() {
        EventsCenter.getInstance().registerHandler(this);
        commands.addAll(CommandWordList.COMMAND_WORD_LIST);
        sortFields.addAll(Arrays.asList(sortFieldsList));
    }

    /**
     * Generates heuristics using information of a list of person
     * @param persons the list of person to extract information from
     */
    public void initialise(List<ReadOnlyPerson> persons) {
        persons.forEach(this::extractInfoFromPerson);
    }

    /**
     * Extracts the relevant information from a person
     * and put them into respective heuristics
     * @param person
     */
    private void extractInfoFromPerson(ReadOnlyPerson person) {
        names.addAll(Arrays.asList(person.getName().fullName.toLowerCase().split("\\s+")));
        phones.add(person.getPhone().value);
        emails.add(person.getEmail().value.toLowerCase());
        person.getTags().stream().map(tag -> tag.tagName.toLowerCase()).forEachOrdered(tags::add);
        addresses.addAll(Arrays.asList(person.getAddress().value.toLowerCase().split("\\s+")));
    }

    /**
     * Generates a SortedSet containing suggestions from the inputted text
     * @param prefixWords the prefix words in the inputted text
     * @param lastWord the last (partial) word the the inputted text
     * @return a SortedSet that contains suggestions for Auto-Completion
     */
    public SortedSet<String> getSuggestions(String prefixWords, String lastWord) {
        if (lastWord.equals("")) {
            return empty;
        }
        SortedSet<String> suggestionSet = getSuggestionSet(prefixWords);
        return suggestionSet.subSet(lastWord + Character.MIN_VALUE, lastWord + Character.MAX_VALUE);
    }

    /**
     * Returns the relevant SortedSet to generate suggestions from
     * according to the context in the prefixWords
     * @param prefixWords words that have been keyed in before the last word
     * @return a SortedSet for generating suggestions
     */
    private SortedSet<String> getSuggestionSet(String prefixWords) {
        String commandWord = prefixWords.trim().split("\\s+")[0];

        switch (commandWord) {

        // commands that uses prefixes for arguments
        case AddCommand.COMMAND_WORD: case AddCommand.COMMAND_ALIAS:
        case EditCommand.COMMAND_WORD: case EditCommand.COMMAND_ALIAS:
        case FindCommand.COMMAND_WORD: case FindCommand.COMMAND_ALIAS:
            int lastSlash = prefixWords.lastIndexOf("/");
            if (lastSlash - 1 <= 0) {
                return empty;
            }
            switch (prefixWords.substring(lastSlash - 1, lastSlash)) {
            case "n":
                return names;
            case "p":
                return phones;
            case "e":
                return emails;
            case "a":
                return addresses;
            case "t":
                return tags;
            default:
                return empty;
            }

        // commands specifying meeting
        case AddMeetingCommand.COMMAND_WORD: case AddMeetingCommand.COMMAND_ALIAS:
        case DeleteMeetingCommand.COMMAND_WORD: case DeleteMeetingCommand.COMMAND_ALIAS:
            //TODO: BETTER SUGGESTIONS FOR MEETINGS
            return empty;

        // commands specifying tag in argument
        case AddTagCommand.COMMAND_WORD: case AddTagCommand.COMMAND_ALIAS:
        case DeleteTagCommand.COMMAND_WORD: case DeleteTagCommand.COMMAND_ALIAS:
            return tags;

        // commands with no argument or single number argument
        case BackupCommand.COMMAND_WORD: case BackupCommand.COMMAND_ALIAS:
        case ClearCommand.COMMAND_WORD: case ClearCommand.COMMAND_ALIAS:
        case DeleteCommand.COMMAND_WORD: case DeleteCommand.COMMAND_ALIAS:
        case ExitCommand.COMMAND_WORD: case ExitCommand.COMMAND_ALIAS:
        case HelpCommand.COMMAND_WORD: case HelpCommand.COMMAND_ALIAS:
        case HistoryCommand.COMMAND_WORD: case HistoryCommand.COMMAND_ALIAS:
        case LoginCommand.COMMAND_WORD: case LoginCommand.COMMAND_ALIAS:
        case LogoutCommand.COMMAND_WORD: case LogoutCommand.COMMAND_ALIAS:
        case ListCommand.COMMAND_WORD: case ListCommand.COMMAND_ALIAS:
        case SyncCommand.COMMAND_WORD: case SyncCommand.COMMAND_ALIAS:
        case UndoCommand.COMMAND_WORD: case UndoCommand.COMMAND_ALIAS:
        case RedoCommand.COMMAND_WORD: case RedoCommand.COMMAND_ALIAS:
        case RestoreBackupCommand.COMMAND_WORD: case RestoreBackupCommand.COMMAND_ALIAS:
        case SelectCommand.COMMAND_WORD: case SelectCommand.COMMAND_ALIAS:
            return empty;

        // stand alone special commands
        case NoteCommand.COMMAND_WORD: case NoteCommand.COMMAND_ALIAS:
            //TODO: BETTER SUGGESTIONS FOR NOTES
            return empty;

        case ResizeCommand.COMMAND_WORD: case ResizeCommand.COMMAND_ALIAS:
            return windowSizes;

        case SortCommand.COMMAND_WORD: case SortCommand.COMMAND_ALIAS:
            return sortFields;

        // incomplete or wrong command word
        default:
            return commands;
        }
    }


    /**
     * Updates heuristic for resize command from ResizeMainWindowEvent
     */
    @Subscribe
    private void handleResizeMainWindowEvent(ResizeMainWindowEvent event) {
        windowSizes.add(Integer.toString(event.getHeight()));
        windowSizes.add(Integer.toString(event.getWidth()));
    }

    /**
     * Updates heuristic using information from a person
     */
    @Subscribe
    private void handleNewPersonInfoEvent(NewPersonInfoEvent event) {
        extractInfoFromPerson(event.getPerson());
    }
}
