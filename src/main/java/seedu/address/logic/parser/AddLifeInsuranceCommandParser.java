package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BENEFICIARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIGNING_DATE;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddLifeInsuranceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author OscarWang114
/**
 * Parses input arguments and creates a new AddLifeInsuranceCommand object
 */
public class AddLifeInsuranceCommandParser implements Parser<AddLifeInsuranceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLifeInsuranceCommand
     * and returns an AddLifeInsuranceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLifeInsuranceCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap;
        argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY, PREFIX_PREMIUM,
                PREFIX_CONTRACT, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY,
                PREFIX_PREMIUM, PREFIX_CONTRACT, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLifeInsuranceCommand.MESSAGE_USAGE));
        }

        try {
            String owner = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_OWNER)).get();
            String insured = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_INSURED)).get();
            String beneficiary = ParserUtil.parseNameForInsurance(argMultimap.getValue(PREFIX_BENEFICIARY)).get();
            Double premium = ParserUtil.parsePremium(argMultimap.getValue(PREFIX_PREMIUM)).get();
            String contract = ParserUtil.parseContract(argMultimap.getValue(PREFIX_CONTRACT)).get();
            String signingDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_SIGNING_DATE)).get();
            String expiryDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_EXPIRY_DATE)).get();

            return new AddLifeInsuranceCommand(owner, insured, beneficiary, premium, contract, signingDate, expiryDate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the name prefixes does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
