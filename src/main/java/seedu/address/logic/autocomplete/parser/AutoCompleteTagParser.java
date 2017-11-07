package seedu.address.logic.autocomplete.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteUtils;
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

        String staticSection = AutoCompleteUtils.getStaticSection(stub);
        String autoCompleteSection = AutoCompleteUtils.getAutoCompleteSection(stub);

        possibleMatches.addAll(allPossibleMatches.stream()
                .filter(possibleMatch -> AutoCompleteUtils.startWithSameLetters(autoCompleteSection, possibleMatch))
                .map(filteredMatch -> staticSection + filteredMatch)
                .collect(Collectors.toList()));
        possibleMatches.add(stub);

        return possibleMatches;
    }

}
