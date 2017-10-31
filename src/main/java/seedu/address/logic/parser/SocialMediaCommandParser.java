package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SocialMediaCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kenpaxtonlim
/**
 * Parses input arguments and creates a new SocialMediaCommand object
 */
public class SocialMediaCommandParser implements Parser<SocialMediaCommand> {

    private static final int ARGUMENT_START_INDEX = 1;
    private static final int TYPE_ARGUMENT_INDEX = 0;
    private static final int INDEX_ARGUMENT_INDEX = 1;

    @Override
    public SocialMediaCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }

        List<String> argsList = Arrays.asList(args.substring(ARGUMENT_START_INDEX).split(" "));

        if (argsList.size() < INDEX_ARGUMENT_INDEX + 1 || argsList.contains("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }

        String type = argsList.get(TYPE_ARGUMENT_INDEX);
        if (!(type.equals(SocialMediaCommand.TYPE_FACEBOOK)
                || type.equals(SocialMediaCommand.TYPE_TWITTER)
                || type.equals(SocialMediaCommand.TYPE_INSTAGRAM))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argsList.get(INDEX_ARGUMENT_INDEX));
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SocialMediaCommand.MESSAGE_USAGE));
        }

        return new SocialMediaCommand(index, type);
    }
}
