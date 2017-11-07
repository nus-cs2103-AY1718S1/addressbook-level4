package seedu.address.logic.commands.hints;

import seedu.address.commons.util.HintUtil;

/**
 * Generates hint and tab auto complete for delete command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete delete command.
 */
public class DeleteCommandHint extends ArgumentsHint {

    public DeleteCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
    }

    @Override
    public void parse() {
        //case : edit *|
        if (!HintUtil.hasIndex(arguments)) {
            handleOfferIndex(userInput);
            return;
        }

        if (Character.isDigit(userInput.charAt(userInput.length() - 1))) {
            //case edit 1|
            handleIndexTabbing(HintUtil.getIndex(arguments));
            return;
        }

        description = "";
        argumentHint = "";
        onTab = userInput;
        assertRequiredIsNonNull();
    }
}
