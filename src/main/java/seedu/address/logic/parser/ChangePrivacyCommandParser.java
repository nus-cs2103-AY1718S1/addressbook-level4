package seedu.address.logic.parser;

//@@author jeffreygohkw
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangePrivacyCommand;
import seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangePrivacyCommand object
 */
public class ChangePrivacyCommandParser implements Parser<ChangePrivacyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangePrivacyCommand
     * and returns an ChangePrivacyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePrivacyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        }

        PersonPrivacySettings pps = new PersonPrivacySettings();

        checkName(argMultimap, pps);
        checkPhone(argMultimap, pps);
        checkEmail(argMultimap, pps);
        checkAddress(argMultimap, pps);
        checkRemark(argMultimap, pps);

        if (!pps.isAnyFieldNonNull()) {
            throw new ParseException(ChangePrivacyCommand.MESSAGE_NO_FIELDS);
        }

        return new ChangePrivacyCommand(index, pps);
    }

    /**
     * Checks the input under the name prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkName(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            if (argMultimap.getValue(PREFIX_NAME).toString().equals("Optional[true]")) {
                pps.setNameIsPrivate(true);

            } else if (argMultimap.getValue(PREFIX_NAME).toString().equals("Optional[false]")) {
                pps.setNameIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the input under the phone prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkPhone(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            if (argMultimap.getValue(PREFIX_PHONE).toString().equals("Optional[true]")) {
                pps.setPhoneIsPrivate(true);
            } else if (argMultimap.getValue(PREFIX_PHONE).toString().equals("Optional[false]")) {
                pps.setPhoneIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the input under the email prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkEmail(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            if (argMultimap.getValue(PREFIX_EMAIL).toString().equals("Optional[true]")) {
                pps.setEmailIsPrivate(true);
            } else if (argMultimap.getValue(PREFIX_EMAIL).toString().equals("Optional[false]")) {
                pps.setEmailIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the input under the address prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkAddress(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            if (argMultimap.getValue(PREFIX_ADDRESS).toString().equals("Optional[true]")) {
                pps.setAddressIsPrivate(true);
            } else if (argMultimap.getValue(PREFIX_ADDRESS).toString().equals("Optional[false]")) {
                pps.setAddressIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the input under the address prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkRemark(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            if (argMultimap.getValue(PREFIX_REMARK).toString().equals("Optional[true]")) {
                pps.setRemarkIsPrivate(true);
            } else if (argMultimap.getValue(PREFIX_REMARK).toString().equals("Optional[false]")) {
                pps.setRemarkIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }
}
