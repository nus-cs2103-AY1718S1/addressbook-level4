package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * Autocomplete utility used in the command box
 */
public class Autocompleter {

    private static String PROMPT_USER_TO_USE_HELP = "To see what commands are available, type 'help' "
            + "into the command box";

    private final String[] commandList = {"add", "clear", "delete", "edit", "exit", "find", "help", "history", "list",
        "redo", "select", "undo"};

    public Autocompleter() {
        registerAsAnEventHandler(this);
    };

    /**
     *
     * @param commandBoxText from the command box
     * @return autocompleted text
     */
    public String autocomplete(String commandBoxText) {
        String[] commandBoxTextArray = commandBoxText.trim().split("\\s+");
        String autocompleteText = commandBoxText;
        if (commandBoxText.equals("")) {
            raise(new NewResultAvailableEvent(PROMPT_USER_TO_USE_HELP, false));
        } else if (commandBoxTextArray.length == 1) {
            autocompleteText = processOneWordAutocomplete(commandBoxTextArray[0]);
        } else {

        }
        return autocompleteText;
    }

    private String processOneWordAutocomplete(String commandBoxText) {
        ArrayList<String> possibleResults = getClosestCommands(commandBoxText);
        return possibleResults.get(0);
    }

    /**
     * Get a list of possible commands to autocomplete
     * @param commandBoxText
     * @return ArrayList of possible autocomplete results
     */
    private ArrayList<String> getClosestCommands (String commandBoxText) {
        ArrayList<String> possibleResults = new ArrayList<>();
        Arrays.stream(commandList)
                .filter(s -> isPossibleMatch(commandBoxText, s))
                .forEach(s -> possibleResults.add(s));
        return possibleResults;
    }

    private boolean isPossibleMatch(String commandBoxText, String commandWord) {
         return (commandBoxText.length() < commandWord.length()
                && commandBoxText.equals(commandWord.substring(0, commandBoxText.length())));
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    private void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }
}
