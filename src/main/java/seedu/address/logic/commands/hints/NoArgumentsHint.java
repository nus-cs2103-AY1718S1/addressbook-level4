package seedu.address.logic.commands.hints;

public abstract class NoArgumentsHint extends Hint {

    NoArgumentsHint() {
        argumentHint = "";
    }

    @Override
    public void parse() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        this.description = whitespace + this.description;
    }

    @Override
    public String autocomplete() {
        return userInput;
    }
}
