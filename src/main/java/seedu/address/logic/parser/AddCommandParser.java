package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.isParsableAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.isParsableEmail;
import static seedu.address.logic.parser.ParserUtil.isParsableName;
import static seedu.address.logic.parser.ParserUtil.isParsablePhone;
import static seedu.address.logic.parser.ParserUtil.parseAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.parseFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseFirstPhone;
import static seedu.address.logic.parser.ParserUtil.parseRemainingName;
import static seedu.address.logic.parser.ParserUtil.parseRemoveAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstPhone;
import static seedu.address.logic.parser.ParserUtil.parseRemoveTags;
import static seedu.address.model.ModelManager.hasAnyExistingTags;
import static seedu.address.model.ModelManager.isExistingTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseArgsException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseArgsException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseArgsException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseArgsException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).orElse(new Remark(""));
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, email, address, remark, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseArgsException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns a formatted argument string given unformatted {@code rawArgs}
     * or a {@code null} {@code String} if not formattable.
     * Uses a simple flow by checking the {@code Model}'s data in the following order:
     * 1. {@code Phone} (mandatory)
     * 2. {@code Email} (mandatory)
     * 3. {@code Tags} (optional)
     * 4. {@code Address} (mandatory)
     * 5. {@code Name} (remaining)
     */
    public static String parseArguments(String rawArgs) {
        String remaining = rawArgs;

        // Check for Phone & Email
        String phone;
        String email;
        if (isParsablePhone(remaining) && isParsableEmail(remaining)) {
            phone = parseFirstPhone(remaining);
            remaining = parseRemoveFirstPhone(remaining).trim().replaceAll(PREFIX_PHONE.toString(), "");
            email = parseFirstEmail(remaining);
            remaining = parseRemoveFirstEmail(remaining).trim().replaceAll(PREFIX_EMAIL.toString(), "");
        } else {
            return null;
        }

        // Check for Existing Tags
        StringBuilder tags = new StringBuilder();
        String[] words = remaining.split(" ");
        if (hasAnyExistingTags(words)) {
            List<String> tagsAdded = new ArrayList<>();
            Arrays.stream(words).forEach(word -> {
                if (isExistingTag(word) && !tagsAdded.contains(word)) {
                    tags.append(" " + PREFIX_TAG + word);
                    tagsAdded.add(word);
                }
            });
            remaining = parseRemoveTags(remaining, tagsAdded);
        }

        // Check for Address till end of remainder string
        String address;
        if (isParsableAddressTillEnd(remaining)) {
            address = parseAddressTillEnd(remaining);
            remaining = parseRemoveAddressTillEnd(remaining).trim().replaceAll(PREFIX_ADDRESS.toString(), "");
        } else {
            return null;
        }

        // Check for alphanumeric Name in remainder string
        String name;
        if (isParsableName(remaining)) {
            name = parseRemainingName(remaining);
        } else {
            return null;
        }

        return " ".concat(PREFIX_NAME.toString()).concat(name)
                .concat(" ").concat(PREFIX_PHONE.toString()).concat(phone)
                .concat(" ").concat(PREFIX_EMAIL.toString()).concat(email)
                .concat(" ").concat(PREFIX_ADDRESS.toString()).concat(address)
                .concat(tags.toString());
    }

}
