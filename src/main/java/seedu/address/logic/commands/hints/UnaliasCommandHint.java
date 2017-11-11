package seedu.address.logic.commands.hints;

import seedu.address.logic.commands.UnaliasCommand;

public class UnaliasCommandHint extends Hint {

    public UnaliasCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        this.argumentHint = "";
    }

    public String autocomplete() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        return userInput + whitespace;
    }
    public void parse() {

        String[] args = arguments.trim().split("\\s+");

        if (args[0].isEmpty()) {

            if (userInput.endsWith(" ")) {
                description = " alias to remove";
            } else {
                description = " removes alias";
            }
        } else {
            description = " removes " + args[0] + " from aliases";
        }
    }
}
