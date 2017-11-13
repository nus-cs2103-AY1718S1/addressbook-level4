package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ModelParserUtil.buildParsedArguments;
import static seedu.address.logic.parser.ModelParserUtil.parseExistingRemarkPrefixes;
import static seedu.address.logic.parser.ModelParserUtil.parseExistingTagPrefixes;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryAddress;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryEmail;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryName;
import static seedu.address.logic.parser.ModelParserUtil.parseMandatoryPhone;
import static seedu.address.logic.parser.ModelParserUtil.parsePossibleTagWords;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javafx.util.Pair;
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
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns a formatted argument string given unformatted {@code rawArgs}
     * or a {@code null} {@code String} if not formattable.
     * Uses a simple flow by checking the {@code Model}'s data in the following order:
     * 1. {@code Phone} (mandatory)
     * 2. {@code Email} (mandatory)
     * 3. Existing {@code Tags} (optional)
     * 4. {@code Tags} prefixes (optional)
     * 5. {@code Remark} prefixes (optional)
     * 6. {@code Address} (mandatory)
     * 7. {@code Name} (remaining)
     */
    public static String parseArguments(String rawArgs) {
        String remaining = rawArgs;

        // Check for Mandatory Phone
        String phone;
        Optional<Pair<String, String>> mandatoryPhone = parseMandatoryPhone(remaining);
        if (mandatoryPhone.isPresent()) {
            phone = mandatoryPhone.get().getKey();
            remaining = mandatoryPhone.get().getValue();
        } else {
            return null;
        }

        // Check for Mandatory Email
        String email;
        Optional<Pair<String, String>> mandatoryEmail = parseMandatoryEmail(remaining);
        if (mandatoryEmail.isPresent()) {
            email = mandatoryEmail.get().getKey();
            remaining = mandatoryEmail.get().getValue();
        } else {
            return null;
        }

        // Check for possible existing Tags without prefixes
        StringBuilder tags = new StringBuilder();
        Optional<Pair<String, String>> optionalPossibleTags = parsePossibleTagWords(remaining);
        if (optionalPossibleTags.isPresent()) {
            tags.append(optionalPossibleTags.get().getKey());
            remaining = optionalPossibleTags.get().getValue();
        }

        // Check for existing tag prefix using tokenizer
        Optional<Pair<String, String>> optionalExistingTags = parseExistingTagPrefixes(remaining);
        if (optionalExistingTags.isPresent()) {
            tags.append(optionalExistingTags.get().getKey());
            remaining = optionalExistingTags.get().getValue();
        }

        // Check for existing remark prefix using tokenizer
        String remark = "";
        Optional<Pair<String, String>> optionalExistingRemarks = parseExistingRemarkPrefixes(remaining);
        if (optionalExistingRemarks.isPresent()) {
            remark = optionalExistingRemarks.get().getKey();
            remaining = optionalExistingRemarks.get().getValue();
        }

        // Check for Address till end of remainder string
        String address;
        Optional<Pair<String, String>> mandatoryAddress = parseMandatoryAddress(remaining);
        if (mandatoryAddress.isPresent()) {
            address = mandatoryAddress.get().getKey();
            remaining = mandatoryAddress.get().getValue();
        } else {
            return null;
        }

        // Check for alphanumeric Name in remainder string
        String name;
        Optional<String> mandatoryName = parseMandatoryName(remaining);
        if (mandatoryName.isPresent()) {
            name = mandatoryName.get();
        } else {
            return null;
        }

        return buildParsedArguments(name, phone, email, remark, address, tags.toString());
    }

}
