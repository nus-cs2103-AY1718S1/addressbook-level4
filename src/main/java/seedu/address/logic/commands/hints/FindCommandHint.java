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
import java.util.stream.Collectors;

import seedu.address.commons.util.HintUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Generates hint and tab auto complete for find command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete find command.
 */
public class FindCommandHint extends ArgumentsHint {

    private static final List<Prefix> PREFIXES =
            Arrays.asList(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);

    public FindCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Find Command
     */
    private void parse() {
        Optional<String> prefixCompletionOptional = HintUtil.findPrefixCompletionHint(arguments, PREFIXES);

        // are we completing a hint?
        // case: find n|/
        if (prefixCompletionOptional.isPresent()) {
            handlePrefixCompletion(prefixCompletionOptional.get(), PREFIXES);
            return;
        }

        // can we tab through prefixes
        // case: find n/|
        Optional<Prefix> endPrefix = HintUtil.findEndPrefix(userInput, PREFIXES);
        if (endPrefix.isPresent()) {

            //we can offer duplicates, so offer every other prefix
            List<Prefix> otherPrefixes =
                    PREFIXES
                            .stream()
                            .filter(p -> !p.equals(endPrefix.get()))
                            .collect(Collectors.toList());

            handlePrefixTabbing(endPrefix.get(), PREFIXES, otherPrefixes);
            return;
        }

        // we should offer a hint
        // case: find n/* |

        //TODO: never exhaust the prefix offers
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
        case PREFIX_TAG_STRING:
            return "tag";
        case PREFIX_REMARK_STRING:
            return "remark";
        default:
            return "";
        }
    }
}
