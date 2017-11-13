package seedu.address.logic.commands.hints;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHARE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHARE_STRING;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.HintUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Generates hint and tab auto complete for Share command
 * Assumes that {@code userInput} and {@code arguments} provided are from
 * an incomplete/complete Share command.
 */
public class ShareCommandHint extends ArgumentsHint {

    private static final List<Prefix> PREFIXES =
            Arrays.asList(PREFIX_SHARE);

    public ShareCommandHint(String userInput, String arguments) {
        this.userInput = userInput;
        this.arguments = arguments;
        parse();
    }

    /**
     * parses {@code userInput} and {@code arguments}
     * sets appropriate {@code argumentHint}, {@code description}, {@code onTab}
     * for Share Command
     */
    private void parse() {
        if (!HintUtil.hasPreambleIndex(arguments)) {
            handleOfferIndex(userInput);
            return;
        }

        if (HintUtil.hasIndex(arguments) && Character.isDigit(userInput.charAt(userInput.length() - 1))) {
            handleIndexTabbing(HintUtil.getPreambleIndex(arguments, PREFIXES));
            return;
        }

        List<Prefix> possiblePrefixesToComplete = HintUtil.getUncompletedPrefixes(arguments, PREFIXES);

        Optional<String> prefixCompletionOptional =
                HintUtil.findPrefixCompletionHint(arguments, possiblePrefixesToComplete);

        // are we completing a hint?
        // case: share 1 s|/
        if (prefixCompletionOptional.isPresent()) {
            handlePrefixCompletion(prefixCompletionOptional.get(), possiblePrefixesToComplete);
            return;
        }

        // case: share 1 s/|
        Optional<Prefix> endPrefix = HintUtil.findEndPrefix(userInput, PREFIXES);
        if (endPrefix.isPresent()) {
            handlePrefixTabbing(endPrefix.get(), PREFIXES, possiblePrefixesToComplete);
            return;
        }

        // we should offer a hint
        // case: share 1 s/|
        handleOfferHint(PREFIXES);
    }

    @Override
    protected void handleOfferHint(List<Prefix> prefixList) {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        Prefix offeredPrefix = HintUtil.offerHint(arguments, prefixList);
        argumentHint = whitespace + offeredPrefix.toString();

        if (offeredPrefix.equals(PREFIX_EMPTY)) {
            onTab = userInput + whitespace;
            description = "next email or index";
        } else {
            onTab = userInput + argumentHint;
            description = getDescription(offeredPrefix);
        }
        LOGGER.info("ArgumentsHint - Handled Prefix Offer");
    }


    @Override
    protected String getDescription(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_SHARE_STRING:
            return "email or index";
        default:
            return "";
        }
    }
}
