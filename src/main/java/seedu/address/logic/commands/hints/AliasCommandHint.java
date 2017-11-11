package seedu.address.logic.commands.hints;

public class AliasCommandHint extends Hint {

    public AliasCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        argumentHint = "";
    }

    @Override
    public String autocomplete() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        return userInput + whitespace;
    }

    @Override
    public void parse() {

        if (arguments.length() == 0 && !userInput.endsWith(" ")) {
            description = " shows all aliases";
            return;
        }
        int delimiterPosition = arguments.trim().indexOf(' ');

        if ((delimiterPosition == -1 && userInput.endsWith(" ") && !arguments.isEmpty()) || delimiterPosition > 0) {
            int fakeDelimiterPosition = (arguments + " pad").trim().indexOf(' ');
            description = " - set what " + (arguments + " pad").trim().substring(0, fakeDelimiterPosition).trim() + " represents";
            return;
        }

        description = " - set your new command word";
        return;
    }
}
