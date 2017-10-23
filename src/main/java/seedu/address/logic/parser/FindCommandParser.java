package seedu.address.logic.parser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.LessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.LocationContainsKeywordsPredicate;
import seedu.address.model.module.predicates.ModuleContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.function.Predicate;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private Predicate<ReadOnlyLesson> previousPredicate = ListingUnit.getCurrentPredicate();

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        switch (ListingUnit.getCurrentListingUnit()) {
        case LOCATION:
            return new FindCommand(new LocationContainsKeywordsPredicate(Arrays.asList(keywords)));
        case MODULE:
            return new FindCommand(new ModuleContainsKeywordsPredicate(Arrays.asList(keywords)));
        default:
            return new FindCommand(new LessonContainsKeywordsPredicate(Arrays.asList(keywords)));
        }
    }

}
