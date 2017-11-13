package seedu.address.logic.commands.hints;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_STRING;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.HintUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Generates hint and tab auto complete for edit command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete edit command.
 */
public class EditCommandHint extends ArgumentsHint {

    private static final List<Prefix> PREFIXES =
            Arrays.asList(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);

    private List<Prefix> possiblePrefixesToComplete;

    public EditCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Edit Command
     */
    private void parse() {
        //case : edit *|
        if (!HintUtil.hasPreambleIndex(arguments)) {
            handleOfferIndex(userInput);
            return;
        }

        if (HintUtil.hasIndex(arguments) && Character.isDigit(userInput.charAt(userInput.length() - 1))) {
            handleIndexTabbing(HintUtil.getPreambleIndex(arguments, PREFIXES));
            return;
        }

        possiblePrefixesToComplete = HintUtil.getUncompletedPrefixes(arguments, PREFIXES);
        Optional<String> prefixCompletionOptional =
                HintUtil.findPrefixCompletionHint(arguments, possiblePrefixesToComplete);

        // are we completing a hint?
        // case: edit 1 n|/
        if (prefixCompletionOptional.isPresent()) {
            handlePrefixCompletion(prefixCompletionOptional.get(), possiblePrefixesToComplete);
            return;
        }

        // can we tab through prefixes
        // case: edit 1 n/|
        Optional<Prefix> endPrefix = HintUtil.findEndPrefix(userInput, PREFIXES);
        if (endPrefix.isPresent()) {
            handlePrefixTabbing(endPrefix.get(), PREFIXES, possiblePrefixesToComplete);
            return;
        }

        // we should offer a hint
        // case: edit 1 n/* |
        handleOfferHint(PREFIXES);
    }

    @Override
    protected String getDescription(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_NAME_STRING:
            return "name";
        case PREFIX_PHONE_STRING:
            return "phone";
        case PREFIX_ADDRESS_STRING:
            return "address";
        case PREFIX_EMAIL_STRING:
            return "email";
        case PREFIX_REMARK_STRING:
            return "remark";
        case PREFIX_TAG_STRING:
            return "tag";
        default:
            return "";
        }
    }
}
