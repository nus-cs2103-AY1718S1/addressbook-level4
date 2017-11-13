package seedu.address.logic.commands.hints;

/**
 * Generates hint and tab auto complete for alias command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete alias command.
 */
public class AliasCommandHint extends Hint {

    public AliasCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        argumentHint = "";
        parse();
    }

    @Override
    public String autocomplete() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        return userInput + whitespace;
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}
     * for an Alias Command
     */
    private void parse() {

        if (arguments.length() == 0 && !userInput.endsWith(" ")) {
            description = " shows all aliases";
            return;
        }
        int delimiterPosition = arguments.trim().indexOf(' ');

        if ((delimiterPosition == -1 && userInput.endsWith(" ") && !arguments.isEmpty()) || delimiterPosition > 0) {
            int fakeDelimiterPosition = (arguments + " pad").trim().indexOf(' ');
            description = " - set what "
                    + (arguments + " pad").trim().substring(0, fakeDelimiterPosition).trim() + " represents";
            return;
        }

        description = " - set your new command word";
        return;
    }
}
