package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIDENCE_ESTIMATE;

import java.util.List;
import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.relationship.ConfidenceEstimate;

//@@author joanneong
/**
 * Parses input arguments and creates a new EditRelationshipCommand object
 */
public class EditRelationshipCommandParser implements Parser<EditRelationshipCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRelationshipCommand
     * and returns an EditRelationshipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditRelationshipCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        List<String> listOfArgs = Arrays.asList(trimmedArgs.split(" "));

        if (listOfArgs.size() < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRelationshipCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CONFIDENCE_ESTIMATE);

        String firstIndexString = listOfArgs.get(0);
        String secondIndexString = listOfArgs.get(1);

        if (firstIndexString.equals(secondIndexString)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRelationshipCommand.MESSAGE_USAGE));
        }

        try {
            Index firstIndex = ParserUtil.parseIndex(listOfArgs.get(0));
            Index secondIndex = ParserUtil.parseIndex(listOfArgs.get(1));
            Name name = ParserUtil.parseRelationshipName(argMultimap.getValue(PREFIX_NAME)).get();
            ConfidenceEstimate confidenceEstimate =
                    ParserUtil.parseConfidenceEstimate(argMultimap.getValue(PREFIX_CONFIDENCE_ESTIMATE)).get();

            return new EditRelationshipCommand(firstIndex, secondIndex, name, confidenceEstimate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

}

