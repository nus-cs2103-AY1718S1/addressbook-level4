package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BENEFICIARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTRACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIGNING_DATE;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author Juxarius
public class AddLifeInsuranceCommandParserTest {

    AddLifeInsuranceCommandParser parser = new AddLifeInsuranceCommandParser();

    @Test
    public void testParse() {
        // parser works
        String command = " " + PREFIX_OWNER + "Me";
        assertCommandFail(parser, command);
        command += " " + PREFIX_INSURED + "You";
        assertCommandFail(parser, command);
        command += " " + PREFIX_BENEFICIARY + "Us";
        assertCommandFail(parser, command);
        command += " " + PREFIX_PREMIUM + 493.34;
        assertCommandFail(parser, command);
        command += " " + PREFIX_CONTRACT + "contract.pdf";
        assertCommandFail(parser, command);
        command += " " + PREFIX_SIGNING_DATE + "01 11 2011";
        assertCommandFail(parser, command);
        command += " " + PREFIX_EXPIRY_DATE + "01 11 2020";
        assertCommandSuccess(parser, command);
    }

    public void assertCommandFail(AddLifeInsuranceCommandParser parser, String command) {
        try {
            parser.parse(command);
            throw new IllegalArgumentException("Parser is not working as intended.");
        } catch (ParseException e) {
            assert(true);
        }
    }

    public void assertCommandSuccess(AddLifeInsuranceCommandParser parser, String command) {
        try {
            parser.parse(command);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Parser is not working as intended.");
        }
    }
}
