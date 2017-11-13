package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOGLEPLUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTAGRAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TWITTER;

import java.util.function.Consumer;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddSocialMediaCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddScheduleCommand object
 */
public class AddSocialMediaCommandParser implements Parser<AddSocialMediaCommand> {

    public static final Prefix PREFIX_PERSON = new Prefix("p/");
    private String socialMediaUrl;
    /**
     * Parses the given {@code String} of arguments in the context of the AddScheduleCommand
     * and returns an AddScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    /** Parse AddSchedueCommand Arguments */
    public AddSocialMediaCommand parse(String args) throws ParseException {
        if (!args.matches("^( (p/\\d+){1} (fb|tw|in|gp){1}/"
                + "((https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]){1})")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddSocialMediaCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_FACEBOOK, PREFIX_TWITTER,
                        PREFIX_INSTAGRAM, PREFIX_GOOGLEPLUS);

        argMultimap.getValue(PREFIX_FACEBOOK).ifPresent(getUrl(argMultimap, PREFIX_FACEBOOK));
        argMultimap.getValue(PREFIX_TWITTER).ifPresent(getUrl(argMultimap, PREFIX_TWITTER));
        argMultimap.getValue(PREFIX_INSTAGRAM).ifPresent(getUrl(argMultimap, PREFIX_INSTAGRAM));
        argMultimap.getValue(PREFIX_GOOGLEPLUS).ifPresent(getUrl(argMultimap, PREFIX_GOOGLEPLUS));


        try {
            Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
            return new AddSocialMediaCommand(personIndex, socialMediaUrl);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private Consumer<String> getUrl(ArgumentMultimap argMultimap, Prefix prefix) {
        return s -> {

            this.socialMediaUrl = argMultimap.getValue(prefix).get();
        };

    }
}
