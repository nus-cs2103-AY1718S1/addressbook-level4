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
import seedu.address.logic.autocomplete.parser.AutoCompleteByPrefixModelParser;
import seedu.address.logic.autocomplete.parser.AutoCompleteCommandParser;
import seedu.address.logic.autocomplete.parser.AutoCompleteParser;
import seedu.address.logic.autocomplete.parser.AutoCompleteSetStringParser;
import seedu.address.logic.autocomplete.parser.AutoCompleteTagParser;
import seedu.address.logic.autocomplete.parser.AutoCompleteWordInNameParser;
import seedu.address.logic.autocomplete.parser.IdentityParser;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

//@@author john19950730
/**
 * Manages autocomplete logic, as well as optimizations such as memoization.
 */
public class AutoCompleteManager implements AutoCompleteLogic {

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
    private final AutoCompleteWordInNameParser wordInNameParser;
    private final AutoCompleteTagParser tagParser;
    private final AutoCompleteByPrefixModelParser modelParser;
    private final AutoCompleteSetStringParser sortFieldParser =
            new AutoCompleteSetStringParser(Arrays.asList(new String[] {"name", "phone", "email"}));
    private final AutoCompleteSetStringParser sortOrderParser =
            new AutoCompleteSetStringParser(Arrays.asList(new String[] {"asc", "dsc"}));
    private final AutoCompleteSetStringParser themeParser =
            new AutoCompleteSetStringParser(Arrays.asList(new String[] {"DarkTheme", "RedTheme"}));
    private final LinkedList<AutoCompletePossibilities> cache = new LinkedList<AutoCompletePossibilities>();
    private final int maxSize;

    public AutoCompleteManager(Model model, int size) {
        this.model = model;
        modelParser = new AutoCompleteByPrefixModelParser(model);
        wordInNameParser = new AutoCompleteWordInNameParser(model);
        tagParser = new AutoCompleteTagParser(model);
        maxSize = size;
    }

    @Override
    public AutoCompletePossibilities generateAutoCompletePossibilities(String stub) {
        return search(stub);
    }

    /**
     * Searches the cache for old AutoCompletePossibilities that has already been evaluated and stored,
     * based on the command stub specified.
     * @param stub incomplete user input given
     * @return AutoCompletePossibilities object that contains all autocomplete options,
     * new object will be generated if not found in cache
     */
    private AutoCompletePossibilities search(String stub) {
        for (AutoCompletePossibilities entryInCache : cache) {
            if (stub.equals(entryInCache.getStub())) {
                logger.info("Found memoized autocomplete options.");
                return entryInCache;
            }
        }

        logger.info("No memoized autocomplete options found, parsing new list of autocomplete matches.");
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
     *  > closest prefix on the left of the input if present
     *  > command word of the user input if present
     * @param stub user input stub
     * @return AutoCompleteParser that should be used to complete the user input
     */
    private AutoCompleteParser chooseParser(String stub) {
        // empty input should parse back empty input as well
        if ("".equals(stub)) {
            logger.info("Parsing back user input as-is");
            return identity;
        }

        int numberOfWordsInStub = stub.split(" ").length;

        if (numberOfWordsInStub == 1) {
            logger.info("Parsing [Commands]");
            return commandParser;
        } else {

            switch (AutoCompleteUtils.getCommandWordInStub(stub)) {

            case AddCommand.COMMAND_WORD:
            case EditCommand.COMMAND_WORD:
            case RemarkCommand.COMMAND_WORD:
                logger.info("Parsing [Model attributes by Prefix]");
                return chooseParserFromPrefix(stub);
            case ChangeThemeCommand.COMMAND_WORD:
                logger.info("Parsing [Themes]");
                return themeParser;
            case FindCommand.COMMAND_WORD:
                logger.info("Parsing [Words in Name in Model]");
                return wordInNameParser;
            case FindTagCommand.COMMAND_WORD:
            case RemoveTagCommand.COMMAND_WORD:
                logger.info("Parsing [Tags in Model]");
                return tagParser;
            case SortCommand.COMMAND_WORD:
                if (numberOfWordsInStub == 2) {
                    logger.info("Parsing [Sort Fields]");
                    return sortFieldParser;
                } else if (numberOfWordsInStub == 3) {
                    logger.info("Parsing [Sort Orders]");
                    return sortOrderParser;
                } else {
                    logger.info("Parsing back user input as-is");
                    return identity;
                }
            default:
                logger.info("Parsing back user input as-is");
                return identity;
            }

        }

    }

    /**
     * Sets up and returns the parser based on the prefix closest to the end of input stub
     * @param stub incomplete user input
     * @return model parser that has been set to parse based on prefix found in stub
     */
    private AutoCompleteParser chooseParserFromPrefix(String stub) {
        List<Integer> prefixPositions = allPrefixes.stream()
                .map(i -> AutoCompleteUtils.findFirstPrefixPosition(stub, i.toString()))
                .collect(Collectors.toList());
        int maxPrefixPosition = prefixPositions.stream().max((a, b) -> Integer.compare(a, b)).get();

        // no prefixes are found, do not autocomplete
        if (maxPrefixPosition == -1) {
            logger.info("No prefix found, parsing back user input as-is");
            return identity;
        }
        Prefix closestPrefix = allPrefixes.get(prefixPositions.indexOf(maxPrefixPosition));

        // check for any subsequent PREFIX_TAG,
        // since it is the only prefix that can occur multiple times normally
        if (AutoCompleteUtils.findLastPrefixPosition(stub, PREFIX_TAG.toString()) > maxPrefixPosition) {
            closestPrefix = PREFIX_TAG;
        }

        logger.info("Parsing by Prefix: " + closestPrefix.toString());
        modelParser.setPrefix(closestPrefix);
        return modelParser;
    }

}
