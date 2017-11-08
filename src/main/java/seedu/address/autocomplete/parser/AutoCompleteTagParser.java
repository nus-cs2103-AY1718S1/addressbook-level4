package seedu.address.autocomplete.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.LinkedList;
import java.util.List;

import seedu.address.autocomplete.AutoCompleteUtils;
import seedu.address.model.Model;

//@@author john19950730
/** Represents a parser that specifically parses only tags based on last word of incomplete user input. */
public class AutoCompleteTagParser extends AutoCompleteByPrefixModelParser {

    public AutoCompleteTagParser(Model model) {
        super(model);
    }

    @Override
    public List<String> parseForPossibilities(String stub) {
        final LinkedList<String> possibleMatches = new LinkedList<String>();
        setPrefix(PREFIX_TAG);

        possibleMatches.addAll(AutoCompleteUtils.generateListOfMatches(allPossibleMatches,
                AutoCompleteUtils.getStaticSection(stub),
                AutoCompleteUtils.getAutoCompleteSection(stub)));
        possibleMatches.add(stub);

        return possibleMatches;
    }

}
