package seedu.address.logic.commands.hints;

/**
 * Generates hint and tab auto complete for unalias command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete unalias command.
 */
public class UnaliasCommandHint extends Hint {

    public UnaliasCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        this.argumentHint = "";
    }

    @Override
    public String autocomplete() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        return userInput + whitespace;
    }

    @Override
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
