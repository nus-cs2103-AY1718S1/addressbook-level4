package seedu.address.logic.autocomplete;

//@@author john19950730
/**
 * Stores a pair of command word and its usage,
 * autocomplete feature will generate command skeleton based on {@code COMMAND_USAGE} specified in each command class.
 * Possible enhancement would be to support multiple command skeletons for each command.
 */
public class CommandWordUsageTuple {

    private final String commandWord;
    private final String commandUsage;

    public CommandWordUsageTuple(String commandWord, String commandUsage) {
        this.commandWord = commandWord;
        this.commandUsage = commandUsage;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getCommandUsage() {
        return commandUsage;
    }

}
