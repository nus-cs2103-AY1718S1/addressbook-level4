package seedu.address.logic.autocomplete;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.autocomplete.parser.AutoCompleteCommandParser;
import seedu.address.logic.autocomplete.parser.AutoCompleteNameParser;
import seedu.address.logic.autocomplete.parser.AutoCompleteParser;
import seedu.address.logic.autocomplete.parser.IdentityParser;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Manages cache and memoization for autocomplete possibilities.
 * Not implemented yet and is consider area for future enhancement.
 */
public class AutoCompleteManager {

    private final Logger logger = LogsCenter.getLogger(AutoCompleteManager.class);

    private final List<Prefix> allPrefixes = Arrays.asList(new Prefix[] {
        PREFIX_NAME,
        PREFIX_PHONE,
        PREFIX_EMAIL,
        PREFIX_ADDRESS,
        PREFIX_TAG,
        PREFIX_REMARK
    });
    private final Model model;
    private final IdentityParser identity = new IdentityParser();
    private final AutoCompleteCommandParser commandParser = new AutoCompleteCommandParser();
    private final AutoCompleteNameParser nameParser;
    private final LinkedList<AutoCompletePossibilities> cache = new LinkedList<AutoCompletePossibilities>();
    private final int maxSize;

    public AutoCompleteManager(Model model, int size) {
        this.model = model;
        nameParser = new AutoCompleteNameParser(model);
        maxSize = size;
    }

    /**
     * Searches the cache for old AutoCompletePossibilities that has already been evaluated and stored,
     * based on the command stub specified.
     * @param stub incomplete user input given
     * @return AutoCompletePossibilities object that contains all autocomplete options,
     * new object will be generated if not found in cache
     */
    public AutoCompletePossibilities search(String stub) {
        for (AutoCompletePossibilities entryInCache : cache) {
            if (stub.equals(entryInCache.getStub())) {
                return entryInCache;
            }
        }
        return insert(new AutoCompletePossibilities(stub, chooseParser(stub)));
    }

    /**
     * Inserts new entry into the cache, remove oldest entry if max size is hit
     * @param entry newly generated AutoCompletePossibilities
     * @return entry that has been added
     */
    private AutoCompletePossibilities insert(AutoCompletePossibilities entry) {
        cache.addFirst(entry);
        if (cache.size() > maxSize) {
            cache.removeLast();
        }
        return entry;
    }

    /**
     * Chooses an AutoCompleteParser based on the user input stub,
     * more specifically the parser used is determined by:
     *  > number words in the user input
     *  > closest prefix on the left of the input
     * @param stub user input stub
     * @return AutoCompleteParser that should be used to complete the user input
     */
    private AutoCompleteParser chooseParser(String stub) {
        // empty input should parse back empty input as well
        if ("".equals(stub)) {
            return identity;
        }

        int numberOfWordsInStub = stub.split(" ").length;

        if (numberOfWordsInStub == 1) {
            return commandParser;
        } else {
            List<Integer> prefixPositions = allPrefixes.stream()
                    .map(i -> AutoCompleteUtils.findPrefixPosition(stub, i.toString())).collect(Collectors.toList());
            int closestPrefixIndex = prefixPositions.indexOf(
                    prefixPositions.stream().max((a, b) -> Integer.compare(a, b)).get());
            // return appropriate parser based on closest prefix
            switch (closestPrefixIndex) {

            //PREFIX_NAME
            case 0:
                return nameParser;

            default:
                // if the right parser can't be found, return an identity function parser
                return identity;

            }
        }

    }

}
