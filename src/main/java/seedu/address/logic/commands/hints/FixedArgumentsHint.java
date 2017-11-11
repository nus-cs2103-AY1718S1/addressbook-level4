package seedu.address.logic.commands.hints;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import seedu.address.commons.util.StringUtil;

public abstract class FixedArgumentsHint extends Hint {

    String autoCorrectInput;

    protected String descriptionFromArg(String arg) {
        return "";
    }

    @Override
    public String autocomplete() {
        return autoCorrectInput;
    }

    protected String nextArg(String arg, String[] args) {
        List<String> argsList = Arrays.asList(args);
        assert argsList.contains(arg);

        int index = argsList.indexOf(arg);
        return argsList.get((index + 1) % argsList.size());
    }

    protected void offerHint(String arg, String autoCorrectInput) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        argumentHint = whitespace + arg;
        this.autoCorrectInput = autoCorrectInput;
        description = descriptionFromArg(arg);
    }

    protected boolean isValidFixedArg(String arg, String[] args) {
        for (String s : args) {
            if (arg.equals(s)) {
                return true;
            }
        }

        return false;
    }

    protected void handleCompletingArg(String arg, String autoCompletedArg, String autoCorrectInput) {
        requireNonNull(autoCompletedArg);
        argumentHint = StringUtil.difference(arg, autoCompletedArg);
        description = descriptionFromArg(autoCompletedArg);
        this.autoCorrectInput = autoCorrectInput;
    }

    protected void handleNextArg(String arg, String[] args, String inputBeforeArg) {
        argumentHint = "";
        autoCorrectInput = inputBeforeArg + " " + nextArg(arg, args);
        description = descriptionFromArg(arg);
    }

    protected void handleFinishedArgs(String finalArg) {
        argumentHint = "";
        description = descriptionFromArg(finalArg);
        autoCorrectInput = userInput;
    }


}
