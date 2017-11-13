package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BENEFICIARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIGNING_DATE;

import java.time.LocalDate;
import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddLifeInsuranceCommand;
import seedu.address.logic.parser.exceptions.MissingPrefixException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.insurance.ContractFileName;
import seedu.address.model.insurance.InsuranceName;
import seedu.address.model.insurance.InsurancePerson;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.Premium;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author OscarWang114
/**
 * Parses input arguments and creates a new {@code AddLifeInsuranceCommand} object
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
                args, PREFIX_NAME, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY, PREFIX_PREMIUM,
                PREFIX_CONTRACT_FILE_NAME, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE);

        if (!arePrefixesPresentAndFilled(argMultimap, PREFIX_NAME, PREFIX_OWNER, PREFIX_INSURED, PREFIX_BENEFICIARY,
                PREFIX_PREMIUM, PREFIX_CONTRACT_FILE_NAME, PREFIX_SIGNING_DATE, PREFIX_EXPIRY_DATE)) {
            throw new MissingPrefixException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLifeInsuranceCommand.MESSAGE_USAGE));
        }

        try {
            InsuranceName insuranceName = ParserUtil.parseInsuranceName(argMultimap.getValue(PREFIX_NAME)).get();
            InsurancePerson owner = ParserUtil.parseInsurancePerson(argMultimap.getValue(PREFIX_OWNER)).get();
            InsurancePerson insured = ParserUtil.parseInsurancePerson(argMultimap.getValue(PREFIX_INSURED)).get();
            InsurancePerson beneficiary =
                    ParserUtil.parseInsurancePerson(argMultimap.getValue(PREFIX_BENEFICIARY)).get();
            Premium premium = ParserUtil.parsePremium(argMultimap.getValue(PREFIX_PREMIUM)).get();
            ContractFileName contractFileName =
                    ParserUtil.parseContractFileName(argMultimap.getValue(PREFIX_CONTRACT_FILE_NAME)).get();
            LocalDate signingDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_SIGNING_DATE)).get();
            LocalDate expiryDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_EXPIRY_DATE)).get();

            ReadOnlyInsurance lifeInsurance = new LifeInsurance(insuranceName, owner, insured, beneficiary, premium,
                    contractFileName, signingDate, expiryDate);

            return new AddLifeInsuranceCommand(lifeInsurance);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if the name prefixes does not contain empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresentAndFilled(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).allMatch(prefix -> {
            if (argumentMultimap.getValue(prefix).isPresent() && !argumentMultimap.getValue(prefix).get().isEmpty()) {
                return true;
            } else {
                return false;
            }
        });
    }
}
