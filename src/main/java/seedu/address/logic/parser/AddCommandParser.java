package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.COMMA_SPACE_STRING;
import static seedu.address.logic.parser.ParserUtil.COMMA_STRING;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.logic.parser.ParserUtil.INDEX_ZERO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

//@@author justintkj
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final int SIZE_1 = 1;
    public static final String EMAIL_REGEX = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
    public static final String EMAIL_EXCEPTION_MESSAGE = "invalid email\nExample: Jason@example.com\n";
    public static final String BLOCK_REGEX = "block \\d{1,3}";
    public static final String BLOCK_EXCEPTION_MESSAGE = "invalid address, Block Number. \nExample: Block 123\n"
            + AddCommand.MESSAGE_USAGE_ALT;
    public static final String STREET_REGEX = "[a-zA-z]+ street \\d{1,2}";
    public static final String STREET_EXCEPTION_MESSAGE = "invalid address, Street. \nExample: Jurong Street 11\n"
            + AddCommand.MESSAGE_USAGE_ALT;
    public static final String UNIT_REGEX = "#\\d\\d-\\d{1,3}[a-zA-Z]{0,1}";
    public static final String UNIT_EXCEPTION_MESSAGE = "invalid address, Unit. \nExample: #01-12B\n"
            + AddCommand.MESSAGE_USAGE_ALT;
    public static final String POSTAL_REGEX = "singapore \\d{6,6}";
    public static final String PHONE_REGEX = "\\d{8}";
    public static final String PHONE_EXCEPTION_MESSAGE = "Number should be 8 digits long!\n"
            + AddCommand.MESSAGE_USAGE_ALT;
    public static final String BIRTHDAY_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4,4}";

    public static final String BIRTHDAY_EXCEPTION_MESSAGE = "invalid birthday,\n Example: 12-09-1994\n";
    public static final String NAME_EXCEPTION_MESSAGE = "Missing Name! Name should follow with a comma\n"
            + "Example: Johnny,\n" + AddCommand.MESSAGE_USAGE_ALT;
    public static final String FALSE = "false";
    public static final String DEFAULT = "default";
    public static final String ALTERNATIVE_METHOD_LOG_MESSAGE = "Adding a person using alternative method ";
    public static final String PREFIX_METHOD_LOG_MESSAGE = "Adding a person using prefix method";
    public static final String SPACE_REGEX = "\\ {1,1}";
    public static final String COMMA_REGEX = "\\,{1,1}";
    public static final String START_REGEX = "^";
    public static final String END_REGEX = "$";
    public static final int INDEX_ONE = 1;
    public static final int INDEX_TWO = 2;
    public static final int INDEX_THREE = 3;
    public static final int INDEX_FOUR = 4;
    public static final int INDEX_FIVE = 5;
    public static final int INDEX_SIX = 6;
    public static final int INDEX_SEVEN = 7;
    public static final int INDEX_EIGHT = 8;

    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);
    private static Level currentLogLevel = Level.INFO;
    private String[] emailPatterns = {EMAIL_REGEX};
    private String[] blockPatterns = {BLOCK_REGEX};
    private String[] streetPatterns = {STREET_REGEX};
    private String[] unitPatterns = {UNIT_REGEX};
    private String[] postalPatterns = {POSTAL_REGEX};
    private String[] phonePatterns = {PHONE_REGEX};
    private String[] birthdayPatterns = {BIRTHDAY_REGEX};
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_REMARK, PREFIX_BIRTHDAY, PREFIX_FAVOURITE);
        try {
            if (containsAnyPrefix(args)) {
                logger.info(PREFIX_METHOD_LOG_MESSAGE + currentLogLevel);
                validatesAllPrefixPresent(argMultimap);
                return createNewPerson(argMultimap);
            } else {
                logger.info(ALTERNATIVE_METHOD_LOG_MESSAGE + currentLogLevel);
                return alternativeCreateNewPerson(args);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Creates a new person without using any prefix
     *
     * @param args input string given
     * @return New AddCommand with a new person
     * @throws IllegalValueException Invalid parameter for any of person's details
     */
    private AddCommand alternativeCreateNewPerson(String args) throws IllegalValueException {
        String[] allArgs = args.split(COMMA_STRING);
        checkNameFormat(args);

        //Initial person's details
        Name name = new Name(allArgs[INDEX_ZERO]);
        Remark remark = new Remark(EMPTY_STRING);
        Birthday birthday = new Birthday(EMPTY_STRING);
        Email email;
        Phone phone;
        String blocknum;
        String streetnum;
        String unitnum;
        String postalnum = EMPTY_STRING;
        Address address;
        Favourite favourite = new Favourite(FALSE);
        ProfilePicture picture = new ProfilePicture(DEFAULT);

        //Generate person's details
        email = new Email (getOutputFromString(args, emailPatterns, EMAIL_EXCEPTION_MESSAGE));
        blocknum = getOutputFromString(args, blockPatterns, BLOCK_EXCEPTION_MESSAGE);
        streetnum = getOutputFromString(args, streetPatterns, STREET_EXCEPTION_MESSAGE);
        unitnum = getOutputFromString(args, unitPatterns, UNIT_EXCEPTION_MESSAGE);
        postalnum = getOutputFromString(args, postalPatterns, EMPTY_STRING);
        phone = new Phone(getOutputFromString(args, phonePatterns, PHONE_EXCEPTION_MESSAGE)
                .trim().replace(COMMA_STRING, EMPTY_STRING));
        birthday = validateBirthdayNotFuture(args);
        address = generatesAddress(blocknum, streetnum, unitnum, postalnum);
        Set<Tag> tagList = new HashSet<>();
        ReadOnlyPerson person = new Person(name, phone, email, address, remark, birthday, tagList, picture,
                favourite);
        return new AddCommand(person);
    }

    /**
     * Creates an Address using all it's relevant parameter
     *
     * @param blocknum Block number
     * @param streetnum Street number
     * @param unitnum Unit number
     * @param postalnum Postal Number (Optional)
     * @return Address with all valid fields
     * @throws IllegalValueException Any of the non-optional field is invalid
     */
    private Address generatesAddress(String blocknum, String streetnum, String unitnum, String postalnum)
            throws IllegalValueException {
        Address address;

        if (postalnum != EMPTY_STRING) {
            address = new Address(blocknum + COMMA_SPACE_STRING + streetnum + COMMA_SPACE_STRING
                + unitnum + COMMA_SPACE_STRING + postalnum);
        } else {
            address = new Address(blocknum + COMMA_SPACE_STRING + streetnum + COMMA_SPACE_STRING + unitnum + postalnum);
        }
        return address;
    }

    /**
     * Creates a birthday if the given birthday is not in the future.
     *
     * @param args birthday in string
     * @return birthday in past
     * @throws IllegalValueException birthday is in future
     */
    private Birthday validateBirthdayNotFuture(String args) throws IllegalValueException {
        Birthday birthday;
        String unprocessedBirthday = getOutputFromString(args, birthdayPatterns, EMPTY_STRING);
        if (unprocessedBirthday.equals(EMPTY_STRING)) {
            return new Birthday (EMPTY_STRING);
        } else if (Birthday.isValidBirthday(unprocessedBirthday)) {
            birthday = new Birthday(unprocessedBirthday);
        } else {
            throw new IllegalValueException(BIRTHDAY_EXCEPTION_MESSAGE);
        }
        return birthday;
    }

    /**
     * Goes through the argument string to look for an expression that fix regex in patterns.
     *
     * @param args user input string
     * @param patterns Regex array for all the patterns to compare
     * @param exceptionMessage Output error message
     * @return String that is valid according to patterns
     * @throws IllegalValueException no valid expression found
     */
    private String getOutputFromString(String args, String[] patterns, String exceptionMessage)
            throws IllegalValueException {

        Matcher matcher = null;
        boolean isMatchFound = false;
        boolean isAnyMatchFound = false;
        ArrayList<String> listOfValidOutput = new ArrayList<String>();
        String[] constraintpatterns = constraintPatterns(patterns[INDEX_ZERO]);

        for (int i = INDEX_ZERO; i < constraintpatterns.length; i++) {
            Pattern pattern = Pattern.compile(constraintpatterns[i], Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(args);
            isMatchFound = matcher.find();
            if (isMatchFound) {
                isAnyMatchFound = true;
                listOfValidOutput.add(matcher.group(INDEX_ZERO).trim());
            }
        }
        return processOptionalFields(args, exceptionMessage, listOfValidOutput, isAnyMatchFound);
    }

    /**
     * Creates the constaint for regex to accept space/comma in front and behind
     *
     * @param pattern Regex to process
     * @return Array string of constainted different regex
     */
    private String[] constraintPatterns(String pattern) {
        String contraintpattern = pattern;
        String[] constraintpatterns = new String[INDEX_EIGHT];
        constraintpatterns[INDEX_ZERO] = SPACE_REGEX + contraintpattern + SPACE_REGEX;
        constraintpatterns[INDEX_ONE] = COMMA_REGEX + contraintpattern + COMMA_REGEX;
        constraintpatterns[INDEX_TWO] = SPACE_REGEX + contraintpattern + COMMA_REGEX;
        constraintpatterns[INDEX_THREE] = COMMA_REGEX + contraintpattern + SPACE_REGEX;
        constraintpatterns[INDEX_FOUR] = START_REGEX + contraintpattern + SPACE_REGEX;
        constraintpatterns[INDEX_FIVE] = START_REGEX + contraintpattern + SPACE_REGEX;
        constraintpatterns[INDEX_SIX] = SPACE_REGEX + contraintpattern + END_REGEX;
        constraintpatterns[INDEX_SEVEN] = COMMA_REGEX + contraintpattern + END_REGEX;
        return constraintpatterns;
    }

    /**
     * Processes the input if match is not found, and is empty string (Optional fields)
     *
     * @param args The input message from user
     * @param exceptionMessage Exception message if match is not found and is not empty
     * @param listOfValidOuput Stores processed field if match found
     * @param matchFound Valids if match is found
     * @return processed field
     * @throws IllegalValueException not a valid processable input
     */
    private String processOptionalFields(String args, String exceptionMessage, ArrayList<String> listOfValidOuput,
                                         boolean matchFound)
            throws IllegalValueException {
        if (!matchFound) {
            if (exceptionMessage == EMPTY_STRING) {
                return EMPTY_STRING;
            } else {
                throw new IllegalValueException(exceptionMessage);
            }
        } else {
            return getLeftMostValidOutput(args, listOfValidOuput);
        }
    }

    /**
     * Generates the value that fits the regex according to left most output, without comma
     *
     * @param args input given by user
     * @param listOfValidOuput all the output that fits the regexs
     * @return The leftmost valid output
     */
    private String getLeftMostValidOutput(String args, ArrayList<String> listOfValidOuput) {
        int leftmostStringIndex = args.length();
        String leftMostOutput = EMPTY_STRING;
        for (int i = INDEX_ZERO; i < listOfValidOuput.size(); i++) {
            if (args.indexOf(listOfValidOuput.get(i)) < leftmostStringIndex) {
                leftmostStringIndex = args.indexOf(listOfValidOuput.get(i));
                leftMostOutput = listOfValidOuput.get(i);
            }
        }
        return leftMostOutput.replaceAll(COMMA_STRING, EMPTY_STRING);
    }

    /**
     * Goes through the list of string to look for a valid email parameter
     *
     * @param allArgs input string given by user
     * @throws IllegalValueException Nothing conforms to legal email format
     */
    private void checkNameFormat(String allArgs) throws IllegalValueException {
        if (!allArgs.contains(COMMA_STRING)) {
            throw new IllegalValueException(NAME_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Adds a person using fields formatted by prefixes
     *
     * @param argMultimap All the prefix that should be used.
     * @return A new add command with the new person.
     * @throws IllegalValueException invalid parameter type
     */
    private AddCommand createNewPerson(ArgumentMultimap argMultimap) throws IllegalValueException {
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
        Remark remark = new Remark(EMPTY_STRING);
        Birthday birthday = new Birthday(EMPTY_STRING);
        if (!argMultimap.getValue(PREFIX_REMARK).equals(Optional.empty())) {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Favourite favourite = new Favourite(Favourite.COLOR_OFF);
        ProfilePicture picture = new ProfilePicture(DEFAULT);
        if (!argMultimap.getValue(PREFIX_BIRTHDAY).equals(Optional.empty())) {
            birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
        }
        ReadOnlyPerson person = new Person(name, phone, email, address, remark, birthday, tagList, picture,
                favourite);
        return new AddCommand(person);
    }

    /**
     * Checks if all compulsary prefixes are present
     *
     * @param argMultimap All the prefixes to be used
     * @throws ParseException Missing prefix
     */
    private void validatesAllPrefixPresent(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if user input uses any prefix of any type
     *
     * @param args User input
     * @return true if contains prefix, false if does not
     */
    private boolean containsAnyPrefix(String args) {
        return args.contains(PREFIX_NAME.toString()) || args.contains(PREFIX_ADDRESS.toString())
            || args.contains(PREFIX_EMAIL.toString()) || args.contains(PREFIX_PHONE.toString())
            || args.contains(PREFIX_REMARK.toString()) || args.contains(PREFIX_TAG.toString())
            || args.contains(PREFIX_BIRTHDAY.toString());
    }
    //@@author
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
