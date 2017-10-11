package seedu.address.logic.autocomplete;

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
