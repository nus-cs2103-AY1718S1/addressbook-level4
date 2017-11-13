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
        parse();

    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Delete Command
     */
    private void parse() {
        //case : delete *|
        if (!HintUtil.hasIndex(arguments)) {
            handleOfferIndex(userInput);
            return;
        }

        if (Character.isDigit(userInput.charAt(userInput.length() - 1))) {
            //case delete 1|
            handleIndexTabbing(HintUtil.getIndex(arguments));
            return;
        }

        description = "";
        argumentHint = "";
        onTab = userInput;
    }
}
