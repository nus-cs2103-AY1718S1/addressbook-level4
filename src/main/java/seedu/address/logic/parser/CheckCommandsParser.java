package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//@@author huiyiiih

/**
 * Parses the command entered by the user
 * and checks it with the synonyms provided
 * so that the user does not have to remember specific commands
 */
public class CheckCommandsParser {
    /**
     * Parsers user input command and match it with the synonyms/aliases
     *
     * @param userCommand user input command string
     * @return the relevant command it matches
     */
    public String matchCommand(String userCommand) {
        /**
         * sets the initial finalUserCommand to error
         */
        String finalUserCommand = "error";

        /**
         * used to store the userCommand in a set
         */
        Set<String> userInputCommand = new HashSet<>();
        userInputCommand.add(userCommand);

        /**
         * All the synonyms/aliases of the different commands
         */
        final String[] subAddCommands = new String[] { "add", "a", "input", "create", "insert" };
        final String[] subClearCommands = new String[] { "clear", "c", "empty", "clean" };
        final String[] subDeleteCommands = new String[] { "delete", "d", "remove", "throw", "erase" };
        final String[] subEditCommands = new String[] { "edit", "e", "change", "revise", "modify" };
        final String[] subExitCommands = new String[] { "exit", "quit" };
        final String[] subFindCommands = new String[] { "find", "f", "look", "search", "check" };
        final String[] subHelpCommands = new String[] { "help", "info" };
        final String[] subHistoryCommands = new String[] { "history", "h", "past" };
        final String[] subListAllCommands =
                new String[] { "listall", "list all", "la", "show all", "display all", "list", "refresh" };
        final String[] subListPersonsCommands =
                new String[] { "listpersons", "list persons", "lp", "show persons", "display persons" };
        final String[] subListEventsCommands =
                new String[] { "listevents", "list events", "le", "show events", "display events" };
        final String[] subRedoCommands = new String[] { "redo", "r" };
        final String[] subSelectCommands = new String[] { "select", "s", "choose", "pick" };
        final String[] subSortCommands = new String[] { "sort", "arrange", "organise" };
        final String[] subUndoCommands = new String[] { "undo", "u" };
        final String[] subCheckScheduleCommands = new String[] {"thisweek", "schedule", "checkschedule", "tw", "cs"};
        final String[] subAddEventsCommands = new String[] { "eventadd", "addevent", "ae", "ea" };
        final String[] subDeleteEventsCommands =
                new String[] { "eventdel", "delevent", "deleteevent", "eventdelete", "de", "ed" };
        final String[] subEditEventsCommands = new String[] { "eventedit", "editevent", "ee" };
        final String[] subFindEventsCommands = new String[] { "eventfind", "findevent", "fe", "ef" };
        final String[] subUpdatePhotoCommands = new String[] { "updatephoto", "up" };
        final String[] subSetRelCommands = new String[] { "set", "rel", "setrel" };
        final String[] subRepeatCommands = new String[] {"repeat", "rp"};
        final String[] subToggleTimetableCommands = new String[] { "timetable", "tt" };


        /**
         * Sets all the strings in each command into a HashSet
         */
        final Set<String> commandsForAdd = new HashSet<>(Arrays.asList(subAddCommands));
        final Set<String> commandsForClear = new HashSet<>(Arrays.asList(subClearCommands));
        final Set<String> commandsForDelete = new HashSet<>(Arrays.asList(subDeleteCommands));
        final Set<String> commandsForEdit = new HashSet<>(Arrays.asList(subEditCommands));
        final Set<String> commandsForExit = new HashSet<>(Arrays.asList(subExitCommands));
        final Set<String> commandsForFind = new HashSet<>(Arrays.asList(subFindCommands));
        final Set<String> commandsForHelp = new HashSet<>(Arrays.asList(subHelpCommands));
        final Set<String> commandsForHistory = new HashSet<>(Arrays.asList(subHistoryCommands));
        final Set<String> commandsForListAll = new HashSet<>(Arrays.asList(subListAllCommands));
        final Set<String> commandsForListPersons = new HashSet<>(Arrays.asList(subListPersonsCommands));
        final Set<String> commandsForListEvents = new HashSet<>(Arrays.asList(subListEventsCommands));
        final Set<String> commandsForRedo = new HashSet<>(Arrays.asList(subRedoCommands));
        final Set<String> commandsForSelect = new HashSet<>(Arrays.asList(subSelectCommands));
        final Set<String> commandsForSort = new HashSet<>(Arrays.asList(subSortCommands));
        final Set<String> commandsForUndo = new HashSet<>(Arrays.asList(subUndoCommands));
        final Set<String> commandsForCheckSchedule = new HashSet<>(Arrays.asList
                (subCheckScheduleCommands));
        final Set<String> commandsForAddEvent = new HashSet<>(Arrays.asList(subAddEventsCommands));
        final Set<String> commandsForDeleteEvent = new HashSet<>(Arrays.asList(subDeleteEventsCommands));
        final Set<String> commandsForEditEvent = new HashSet<>(Arrays.asList(subEditEventsCommands));
        final Set<String> commandsForFindEvent = new HashSet<>(Arrays.asList(subFindEventsCommands));
        final Set<String> commandsForUpdatePhoto = new HashSet<>(Arrays.asList(subUpdatePhotoCommands));
        final Set<String> commandsForSetRel = new HashSet<>(Arrays.asList(subSetRelCommands));
        final Set<String> commandsForRepeat = new HashSet<>(Arrays.asList(subRepeatCommands));
        final Set<String> commandsForToggleTimetable = new HashSet<>(Arrays.asList(subToggleTimetableCommands));

        /**
         * Compares the userInputCommand with the different commands set
         */
        if (!Collections.disjoint(userInputCommand, commandsForAdd)) {
            finalUserCommand = "add";
        } else if (!Collections.disjoint(userInputCommand, commandsForClear)) {
            finalUserCommand = "clear";
        } else if (!Collections.disjoint(userInputCommand, commandsForDelete)) {
            finalUserCommand = "delete";
        } else if (!Collections.disjoint(userInputCommand, commandsForEdit)) {
            finalUserCommand = "edit";
        } else if (!Collections.disjoint(userInputCommand, commandsForExit)) {
            finalUserCommand = "exit";
        } else if (!Collections.disjoint(userInputCommand, commandsForFind)) {
            finalUserCommand = "find";
        } else if (!Collections.disjoint(userInputCommand, commandsForHelp)) {
            finalUserCommand = "help";
        } else if (!Collections.disjoint(userInputCommand, commandsForHistory)) {
            finalUserCommand = "history";
        } else if (!Collections.disjoint(userInputCommand, commandsForListAll)) {
            finalUserCommand = "listall";
        } else if (!Collections.disjoint(userInputCommand, commandsForRedo)) {
            finalUserCommand = "redo";
        } else if (!Collections.disjoint(userInputCommand, commandsForSelect)) {
            finalUserCommand = "select";
        } else if (!Collections.disjoint(userInputCommand, commandsForSort)) {
            finalUserCommand = "sort";
        } else if (!Collections.disjoint(userInputCommand, commandsForUndo)) {
            finalUserCommand = "undo";
        } else if (!Collections.disjoint(userInputCommand, commandsForCheckSchedule)) {
            finalUserCommand = "thisweek";
        } else if (!Collections.disjoint(userInputCommand, commandsForAddEvent)) {
            finalUserCommand = "eventadd";
        } else if (!Collections.disjoint(userInputCommand, commandsForDeleteEvent)) {
            finalUserCommand = "eventdel";
        } else if (!Collections.disjoint(userInputCommand, commandsForEditEvent)) {
            finalUserCommand = "eventedit";
        } else if (!Collections.disjoint(userInputCommand, commandsForFindEvent)) {
            finalUserCommand = "eventfind";
        } else if (!Collections.disjoint(userInputCommand, commandsForUpdatePhoto)) {
            finalUserCommand = "updatephoto";
        } else if (!Collections.disjoint(userInputCommand, commandsForSetRel)) {
            finalUserCommand = "set";
        } else if (!Collections.disjoint(userInputCommand, commandsForToggleTimetable)) {
            finalUserCommand = "timetable";
        } else if (!Collections.disjoint(userInputCommand, commandsForRepeat)) {
            finalUserCommand = "repeat";
        } else if (!Collections.disjoint(userInputCommand, commandsForListPersons)) {
            finalUserCommand = "listpersons";
        } else if (!Collections.disjoint(userInputCommand, commandsForListEvents)) {
            finalUserCommand = "listevents";
        }
        return finalUserCommand;
    }
}
//@@author
