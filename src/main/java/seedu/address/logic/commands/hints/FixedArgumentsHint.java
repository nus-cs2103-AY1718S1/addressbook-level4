package seedu.address.logic.commands.hints;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Template class for hints that have fixed arguments
 * Specifies autocomplete to return the {@code autoCorrectInput}
 * As the arguments are not variable, we can easily help the user autocorrect his input based on the situation.
 */
public abstract class FixedArgumentsHint extends Hint {

    protected String autoCorrectInput;

    protected String descriptionFromArg(String arg) {
        return "";
    }

    @Override
    public String autocomplete() {
        return autoCorrectInput;
    }

    /**
     * return the next argument after {@code arg} in {@code args}
     * asserts that args contains arg
     */
    protected String nextArg(String arg, String[] args) {
        List<String> argsList = Arrays.asList(args);
        assert argsList.contains(arg);

        int index = argsList.indexOf(arg);
        return argsList.get((index + 1) % argsList.size());
    }

    /**
     * offers argument hint based on {@code arg}
     * sets tab to return {@code autoCorrectInput}
     */
    protected void offerHint(String arg, String autoCorrectInput) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        argumentHint = whitespace + arg;
        this.autoCorrectInput = autoCorrectInput;
        description = descriptionFromArg(arg);
    }

    /**
     * returns true if {@code args} contains {@code arg}
     */
    protected boolean isValidFixedArg(String arg, String[] args) {
        for (String s : args) {
            if (arg.equals(s)) {
                return true;
            }
        }

        return false;
    }
    /**
     * Should be used when argument is half completed
     * {@code autoCompletedArg} is the completed argument of {@code arg}
     * updates {@code autoCorrectInput} with given {@code autoCorrectInput}
     */
    protected void handleCompletingArg(String arg, String autoCompletedArg, String autoCorrectInput) {
        requireNonNull(autoCompletedArg);
        argumentHint = StringUtil.difference(arg, autoCompletedArg);
        description = descriptionFromArg(autoCompletedArg);
        this.autoCorrectInput = autoCorrectInput;
    }
    /**
     * Should be used when argument completed and is cyclable
     * {@code arg} is the current completed argument
     * {@code args} is an array of other possible arguments
     * {@code inputBeforeArg} specifies the userInput before the argument that will remain the same on tab
     */
    protected void handleNextArg(String arg, String[] args, String inputBeforeArg) {
        argumentHint = "";
        autoCorrectInput = inputBeforeArg + " " + nextArg(arg, args);
        description = descriptionFromArg(arg);
    }

    /**
     * Should be used when arguments are completed and are not cyclable
     * {@code finalArg} is the last completed argument
     */
    protected void handleFinishedArgs(String finalArg) {
        argumentHint = "";
        description = descriptionFromArg(finalArg);
        autoCorrectInput = userInput;
    }

}
