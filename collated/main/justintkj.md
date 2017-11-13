# justintkj
###### \java\seedu\address\logic\commands\CommandUtil.java
``` java
/**
 * Contains utility methods used for common used command in the various *Command classes.
*/
public class CommandUtil {

    /**
     * Validates current index is smaller than total number of persons
     * @param lastShownList List of ReadOnlyPerson
     * @throws CommandException index is higher than total number of person.(zero based)
     */
    public static void checksIndexSmallerThanList(List<ReadOnlyPerson> lastShownList,
                                                  Index index) throws CommandException {
        assert lastShownList != null;
        assert index != null;
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
/**
 * Email a person chosen by index
 * API usage referenced from https://www.javatpoint.com/example-of-sending-email-using-java-mail-api
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_SUCCESS = "Email sent to :";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Email the person identified "
            + "by the index number used in the last person listing. "
            + "An email will be sent to the chosen person.\n"
            + "Parameters: INDEX (must be a positive integer),SUBJECT,MESSAGE "
            + "[Email Index,Subject,Message]\n"
            + "Example: " + COMMAND_WORD + " 1"
            + ",subjectmessage,textmessage";
    public static final String INCORRECT_EMAIL_FORMAT = "Incorrect Email format";
    public static final String CORRECT_EMAIL_FORMAT = "Email successfully sent to : ";
    public static final String HOST_EMAIL = "smtp.gmail.com";
    public static final String TEAM_EMAIL_ADDRESS = "cs2103f09b3@gmail.com";
    public static final String TEAM_EMAIL_PASSWORD = "pocketbook";
    public static final String SMTP_ENABLE = "mail.smtp.starttls.enable";
    public static final String TRUE = "true";
    public static final String SMTP_HOST = "mail.smtp.host";
    public static final String SMTP_PORT = "mail.smtp.port";
    public static final String PORT_NUMBER = "587";
    public static final String SMTP_AUTHENTICATION = "mail.smtp.auth";
    public static final String SMTP_STARTTLS = "mail.smtp.starttls.required";
    public static final String SMTP = "smtp";
    public final Index index;
    public final String subject;
    public final String message;

    private String host;
    private String user;
    private String pass;
    private String to;
    private String from;

    private Session mailSession;
    private Message composedMessage;

    public EmailCommand(Index index, String subject, String message) {
        requireNonNull(index);
        requireNonNull(subject);
        requireNonNull(message);
        this.index = index;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        CommandUtil.checksIndexSmallerThanList(lastShownList, index);

        ReadOnlyPerson personToEmail = lastShownList.get(index.getZeroBased());

        try {
            //Initial Login credentials
            generateInitialLoginCred(personToEmail);

            //Adds signature to user's text
            String messageText = teamSignatureGenerator();
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            //updates the properties of the session
            updateProperties(host, props);

            //Compose the message
            composeMethod(messageText, sessionDebug, props);

            //Sends the Message
            sendMessage(host, user, pass, mailSession, composedMessage);

        } catch (Exception ex) {
            throw new CommandException(INCORRECT_EMAIL_FORMAT);
        }
        return new CommandResult(CORRECT_EMAIL_FORMAT + personToEmail.getName());
    }

    /**
     * Creates the Email object
     *
     * @param messageText The message to be sent out
     * @param sessionDebug
     * @param props
     * @throws MessagingException
     */
    private void composeMethod(String messageText, boolean sessionDebug, Properties props) throws MessagingException {
        Provider provider = new com.sun.net.ssl.internal.ssl.Provider();
        java.security.Security.addProvider(provider);
        mailSession = Session.getDefaultInstance(props, null);
        mailSession.setDebug(sessionDebug);
        composedMessage = composeMessage(to, from, subject, messageText, mailSession);
    }

    /**
     *  Initialises basic sending credentials
     *
     *  @param personToEmail Person to recieve the email
     */
    private void generateInitialLoginCred(ReadOnlyPerson personToEmail) {
        host = HOST_EMAIL;
        user = TEAM_EMAIL_ADDRESS;
        pass = TEAM_EMAIL_PASSWORD;
        to = personToEmail.getEmail().toString();
        from = TEAM_EMAIL_ADDRESS;
    }

    /**
     * Creates initial properties of javaMail
     *
     * @param host DNS name of a server
     * @param props Properties of javamail API
     */
    private void updateProperties(String host, Properties props) {
        props.put(SMTP_ENABLE, TRUE);
        props.put(SMTP_HOST, host);
        props.put(SMTP_PORT, PORT_NUMBER);
        props.put(SMTP_AUTHENTICATION, TRUE);
        props.put(SMTP_STARTTLS, TRUE);
    }

    /**
     * Sends the message to the email chosen
     *
     * @param host DNS name of a server
     * @param user Email of CS2103F09B3 Team
     * @param pass Password of CS2103F09B3 Team
     * @param mailSession Contains properties and defaults used by Mail API
     * @param msg Message to be sent out
     * @throws MessagingException Invalid parameters used
     */
    private void sendMessage(String host, String user, String pass, Session mailSession,
                             Message msg) throws MessagingException {
        assert host == HOST_EMAIL;
        assert user == TEAM_EMAIL_ADDRESS;
        assert pass == TEAM_EMAIL_PASSWORD;
        Transport transport = mailSession.getTransport(SMTP);
        transport.connect(host, user, pass);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    /**
     * Creates the message using the valid parameters.
     *
     * @param to Email of person to send
     * @param from Email of Sender
     * @param subject Message to be attached in subject of Email
     * @param messageText Message to be attached in body of Email
     * @param mailSession Contains properties and defaults used by Mail API
     * @return Message compressed into Message objet
     * @throws MessagingException Invalid parameters used in composing Email
     */
    private Message composeMessage(String to, String from, String subject, String messageText,
                                   Session mailSession) throws MessagingException {
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(messageText);
        return msg;
    }

    /**
     * Creates the signature at the end of the email provided by CS2103F03B3 Team
     *
     * @return New message attached with signature
     */
    private String teamSignatureGenerator() {
        String newLine = "";
        for (int i = 0; i < 5; i++) {
            newLine += System.getProperty("line.separator");
        }
        return this.message + newLine + "This is a generated mail provided by CS2103F09B3 Team.";
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof EmailCommand)) {
            return false;
        }

        // state check
        EmailCommand e = (EmailCommand) other;
        return index.equals(e.index) && subject.equals(e.subject) && message.equals(e.message);
    }
}
```
###### \java\seedu\address\logic\commands\FavouriteCommand.java
``` java
/**
 * Favourites a person identified using it's last displayed index from the address book.
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites/Highlights the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_SUCCESS = "Favourite Person: %1$s";

    private final Index targetIndex;
    private final Favourite favourite;

    public FavouriteCommand(Index targetIndex, Favourite favourite) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.favourite = favourite;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        CommandUtil.checksIndexSmallerThanList(lastShownList, targetIndex);

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());
        ReadOnlyPerson editedPerson = personToEdit;
        toggleColor(editedPerson);
        editedPerson.setFavourite(favourite);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_MISSING_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_FAVOURITE_SUCCESS, editedPerson));

    }

    /**
     * Toggles the current color state of the person selected
     *
     * @param editedPerson selected person to edit
     */
    private void toggleColor(ReadOnlyPerson editedPerson) {
        if (editedPerson.getFavourite().toString().equals(Favourite.COLOR_SWITCH)) {
            favourite.inverse();
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof FavouriteCommand)) {
            return false;
        }

        // state check
        FavouriteCommand e = (FavouriteCommand) other;
        return targetIndex.equals(e.targetIndex) && favourite.equals(e.favourite);
    }
}
```
###### \java\seedu\address\logic\commands\RedoCommand.java
``` java
    public RedoCommand(int numRedo) {
        requireNonNull(numRedo);
        this.numRedo = numRedo;
    }
    public RedoCommand() throws IllegalValueException {
        this.numRedo = ParserUtil.parseNumber(INDEX_ONE);
    }
    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        checksStackNotEmpty();
        checksRedoSizeNotBiggerThanStack();

        redoMultipleTimes();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Repeats numRedo commands number of times
     *
     * @throws CommandException if redo while stack is empty
     */
    private void redoMultipleTimes() throws CommandException {
        for (int i = INDEX_ZERO; i < numRedo; i++) {
            if (!undoRedoStack.canRedo()) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            undoRedoStack.popRedo().redo();
        }
    }

    /**
     * Checks if number of redos is not bigger than current avaliable number of redos
     *
     * @throws CommandException if redo while stack is empty
     */
    private void checksRedoSizeNotBiggerThanStack() throws CommandException {
        if (numRedo > undoRedoStack.getRedoStackSize()) {
            throw new CommandException(TOO_MANY_REDO_FORMAT + undoRedoStack.getRedoStackSize());
        }
    }

    /**
     * Checks if current number of redo avaliable is zero
     *
     * @throws CommandException if current stack size is zero
     */
    private void checksStackNotEmpty() throws CommandException {
        if (undoRedoStack.getRedoStackSize() == EMPTY_STACK) {
            throw new CommandException(EMPTY_STACK_ERROR_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RedoCommand // instanceof handles nulls
                && this.numRedo == (((RedoCommand) other).numRedo)); // state check
    }

```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Edits the remark of the person identified by index number in person listing.
 */

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    private static final String MESSAGE_REMARK_SUCCESS = "Remark edited for Person: ";

    private final Index index;
    private final Remark remark;


    public RemarkCommand (Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    public RemarkCommand (Index index, String remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = new Remark(remark);
    }
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        CommandUtil.checksIndexSmallerThanList(lastShownList, index);

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = generateNewEditedPerson(personToEdit);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_MISSING_PERSON);
        }

        model.updateListToShowAll();
        return new CommandResult(MESSAGE_REMARK_SUCCESS + personToEdit.getName().toString());
    }

    /**
     * Changes the remark field of the selected person to edit
     *
     * @param personToEdit Selected person to edit
     * @return A new person with remark field edited
     */
    private Person generateNewEditedPerson(ReadOnlyPerson personToEdit) {
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), remark, personToEdit.getBirthday(), personToEdit.getTags(),
                    personToEdit.getPicture(), personToEdit.getFavourite());
    }


    @Override
     public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index) && remark.equals(e.remark);
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts a the list of persons in ascending alphabetical order
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SORT_SUCCESS = "Sorted in ascending order: ";
    public static final String MESSAGE_SORT_FAILURE = "Invalid command format!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the person identified by the index number used in the last person listing.\n"
            + "Parameters: name/num/address/email (the type of sort to be executed)\n"
            + "Example: " + COMMAND_WORD + " name"
            + "Example: " + COMMAND_WORD + " number"
            + "Example: " + COMMAND_WORD + " address"
            + "Example: " + COMMAND_WORD + " remark"
            + "Example: " + COMMAND_WORD + " email"
            + "Example: " + COMMAND_WORD + " birthday"
            + "Example: " + COMMAND_WORD + " numTimesSearched"
            + "Example: " + COMMAND_WORD + " favourite";


    private String sortType;

    public SortCommand(String sortType) {
        this.sortType = sortType.toLowerCase();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.sortPerson(sortType);
        return new CommandResult(MESSAGE_SORT_SUCCESS + sortType);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
    public UndoCommand(int numUndo) {
        this.numUndo = numUndo;
    }

    public UndoCommand() throws IllegalValueException {
        numUndo = ParserUtil.parseNumber(NUMBER_ONE);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireAllNonNull(model, undoRedoStack);

        checkStackNotEmpty();
        checkUndoSizeNotBiggerThanStack();

        undoMultipleTimes();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    /**
     * Remove commands for numUndo number of times
     *
     * @throws CommandException if undo while stack is empty
     */
    private void undoMultipleTimes() throws CommandException {
        for (int i = INDEX_ZERO; i < numUndo; i++) {
            if (!undoRedoStack.canUndo()) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            undoRedoStack.popUndo().undo();
        }
    }

    /**
     * Checks if number of undos is not bigger than current avaliable number of undo
     *
     * @throws CommandException if redo while stack is empty
     */
    private void checkUndoSizeNotBiggerThanStack() throws CommandException {
        if (numUndo > undoRedoStack.getUndoStackSize()) {
            throw new CommandException(MESSAGE_TOO_MANY_UNDO + undoRedoStack.getUndoStackSize());
        }
    }

    /**
     * Checks if current number of redo avaliable is zero
     *
     * @throws CommandException if current stack size is zero
     */
    private void checkStackNotEmpty() throws CommandException {
        if (undoRedoStack.getUndoStackSize() == EMPTY_STACK) {
            throw new CommandException(MESSAGE_EMPTYSTACK);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && this.numUndo == (((UndoCommand) other).numUndo)); // state check
    }

```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final int SIZE_2 = 2;
    public static final String EMAIL_REGEX = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
    public static final String EMAIL_EXCEPTION_MESSAGE = "invalid email\n Example: Jason@example.com";
    public static final String BLOCK_REGEX = "block \\d{1,3}";
    public static final String BLOCK_EXCEPTION_MESSAGE = "invalid address, Block Number. \nExample: Block 123"
            + AddCommand.MESSAGE_USAGE_ALT;
    public static final String STREET_REGEX = "[a-zA-z]+ street \\d{1,2}";
    public static final String STREET_EXCEPTION_MESSAGE = "invalid address, Street. \nExample: Jurong Street 11"
            + AddCommand.MESSAGE_USAGE_ALT;
    public static final String UNIT_REGEX = "#\\d\\d-\\d{1,3}[a-zA-Z]{0,1}";
    public static final String UNIT_EXCEPTION_MESSAGE = "invalid address, Unit. \n Example: #01-12B"
            + AddCommand.MESSAGE_USAGE_ALT;
    public static final String POSTAL_REGEX = "singapore \\d{6,6}";
    public static final String PHONE_REGEX = "\\d{8}";
    public static final String PHONE_EXCEPTION_MESSAGE = "Number should be 8 digits long!\n"
            + AddCommand.MESSAGE_USAGE_ALT;
    public static final String BIRTHDAY_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4,4}";

    public static final String BIRTHDAY_EXCEPTION_MESSAGE = "invalid birthday,\n Example: 12-09-1994";
    public static final String NAME_EXCEPTION_MESSAGE = "Missing Name!\n" + AddCommand.MESSAGE_USAGE_ALT;
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
        checkNameFormat(allArgs);

        //Initial person's details
        Name name = new Name(allArgs[INDEX_ZERO]);
        Remark remark = new Remark(EMPTY_STRING);
        Birthday birthday = new Birthday(EMPTY_STRING);
        Email email;
        Phone phone;
        String blocknum;
        String streetnum;
        String unitnum;
        String postalnum = "";
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
    private void checkNameFormat(String[] allArgs) throws IllegalValueException {
        if (allArgs.length < SIZE_2) {
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
        Remark remark;
        Birthday birthday;
        if (argMultimap.getValue(PREFIX_REMARK).equals(Optional.empty())) {
            remark = new Remark("");
        } else {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
        }
        if (argMultimap.getValue(PREFIX_BIRTHDAY).equals(Optional.empty())) {
            birthday = new Birthday("");
        } else {
            birthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get();
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Favourite favourite = new Favourite(Favourite.COLOR_OFF);
        ProfilePicture picture = new ProfilePicture(DEFAULT);
        ReadOnlyPerson person = new Person(name, phone, email, address, remark, birthday, tagList, picture,
                favourite);
        return new AddCommand(person);
    }

    /**
     * Checks if all prefixes are present
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
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string, adds suggestion to Autocomplete.xml if valid command
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case EmailCommand.COMMAND_WORD:
            Command emailCommand = new EmailCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return emailCommand;

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            Command addCommand = new AddCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return addCommand;

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            Command editCommand = new EditCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return editCommand;

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            Command selectCommand = new SelectCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return selectCommand;

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            Command deleteCommand = new DeleteCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return deleteCommand;

        case RemoveTagCommand.COMMAND_WORD:
            Command removeTagCommand = new RemoveTagCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return removeTagCommand;

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            Command findCommand = new FindCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return findCommand;

        case FuzzyfindCommand.COMMAND_WORD:
        case FuzzyfindCommand.COMMAND_ALIAS:
            Command fuzzyfindCommand = new FuzzyfindCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return fuzzyfindCommand;

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            Command undoCommand = new UndoCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return undoCommand;

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            Command redoCommand = new RedoCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return redoCommand;

        case RemarkCommand.COMMAND_WORD:
            Command remarkCommand = new RemarkCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return remarkCommand;

        case BirthdayCommand.COMMAND_WORD:
            Command birthdayCommand = new BirthdayCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return birthdayCommand;

        case MapCommand.COMMAND_WORD:
            Command mapCommand = new MapCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return mapCommand;

        case ImageCommand.COMMAND_WORD:
            Command imageCommand = new ImageCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return imageCommand;
        case FavouriteCommand.COMMAND_WORD:
            Command favouriteCommand = new FavouriteCommandParser().parse(arguments);
            addValidInputToAutocomplete(userInput);
            return favouriteCommand;

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Updates Storage for autocomplete with the valid command
     * @param userInput User's command
     * @throws ParseException storagefile is missing
     */
    private void addValidInputToAutocomplete(String userInput) throws ParseException {
        try {
            CommandBox.setAddSuggestion(userInput);
        } catch (Exception ex) {
            throw new ParseException(MISSING_AUTOCOMPLETEFILE);
        }
    }

}
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    public static final int FIRST_PART_MESSAGE = 0;
    public static final int SECOND_PART_MESSAGE = 1;
    public static final int THIRD_PART_MESSAGE = 2;
    public static final int THREE_PARTS = 3;

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns a Email object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        requireNonNull(args);
        assert args != EMPTY_STRING;
        Index index;
        String subject;
        String message;
        try {

            String[] messages = args.trim().split(COMMA_STRING);
            checkValidNumberOfArguments(messages);
            String[] splitArgs = messages[FIRST_PART_MESSAGE].trim().split(SPACE_STRING);
            index = ParserUtil.parseIndex(splitArgs[FIRST_PART_MESSAGE]);
            subject = (messages[SECOND_PART_MESSAGE]);
            message = (messages[THIRD_PART_MESSAGE]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
        return new EmailCommand(index, subject, message);
    }


    /**
     * Validates there are three different message parts
     *
     * @param messages Arrays of string message
     * @throws ParseException if messsages is not of three parts
     */
    private void checkValidNumberOfArguments(String[] messages) throws ParseException {
        if (messages.length != THREE_PARTS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\FavouriteCommandParser.java
``` java
/**
 * Parses arguments and returns FavouriteCommand
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns a BirthdayCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        Favourite favourite;
        try {
            index = ParserUtil.parseIndex(args);
            favourite = new Favourite(Favourite.COLOR_SWITCH);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FavouriteCommand.MESSAGE_USAGE), ive);
        }

        return new FavouriteCommand(index, favourite);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code number} into an {@code Integer} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified number is invalid (not non-zero unsigned integer).
     */
    public static int parseNumber(String number) throws IllegalValueException {
        String trimmedNumber = number.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedNumber)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Integer.parseInt(trimmedNumber);
    }

```
###### \java\seedu\address\logic\parser\RedoCommandParser.java
``` java
/**
* Parses input arguments and creates a new RedoCommand object
*/
public class RedoCommandParser implements Parser<RedoCommand> {

    public static final String NUMBER_ONE = "1";
    public static final int FIRST_PART_MESSAGE = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an RedoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RedoCommand parse(String args) throws ParseException {
        String[] splitArgs = args.trim().split(" ");

        int numRedo;
        try {
            numRedo = getNumberRedoToBeDone(splitArgs[FIRST_PART_MESSAGE]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE), ive);
        }

        return new RedoCommand(numRedo);
    }
    /**
     * Generates number of redo to be done
     *
     * @param splitArg Message given by user
     * @return Number of redo to be done
     * @throws IllegalValueException invalid number of redo to be done
     */
    private int getNumberRedoToBeDone(String splitArg) throws IllegalValueException {
        int numRedo;
        if (splitArg.trim().equals("")) {
            numRedo = ParserUtil.parseNumber(NUMBER_ONE);
        } else {
            numRedo = ParserUtil.parseNumber(splitArg);
        }
        return numRedo;
    }

}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
        Remark remark = new Remark(argMultimap.getValue(PREFIX_REMARK).orElse(EMPTY_STRING));
        return new RemarkCommand(index, remark);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        try {
            args = args.toLowerCase();
            String toSort = ParserUtil.parseSortType(args);
            return new SortCommand(toSort);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\UndoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UndoCommand object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    public static final int FIRST_PART_MESSAGE = 0;
    public static final String EMPTY_MESSAGE = "";
    public static final int DEFAULT_CHOSEN_ONE = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UndoCommand parse(String args) throws ParseException {

        String[] splitArgs = args.trim().split(" ");

        int numUndo;
        try {
            numUndo = getNumberOfUndoToBeDone(splitArgs[FIRST_PART_MESSAGE]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE), ive);
        }

        return new UndoCommand(numUndo);
    }

    /**
     * Generates number of undo to be done
     *
     * @param splitArg Message given by user
     * @return Number of undo to be done
     * @throws IllegalValueException invalid number of undo to be done
     */
    private int getNumberOfUndoToBeDone(String splitArg) throws IllegalValueException {
        int numUndo;
        if (splitArg.trim().equals(EMPTY_MESSAGE)) {
            numUndo = DEFAULT_CHOSEN_ONE;
        } else {
            numUndo = ParserUtil.parseNumber(splitArg);
        }
        return numUndo;
    }

}
```
###### \java\seedu\address\logic\UndoRedoStack.java
``` java
    /**
     * Gets number of undoable tasks in integer
     */
    public int getUndoStackSize() {
        return undoStack.size();
    }

    /**
     * Gets number of redoable tasks in integer
     */
    public int getRedoStackSize() {
        return redoStack.size();
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     *     Sort Persons according to sortType
     */
    public void sortPersons(String sortType) {
        persons.sort(sortType);
        syncMasterTagListWith(persons);
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**Sorts all the people in the current database*/
    void sortPerson(String sortType);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateListToShowAll() {
        filteredPersons.setPredicate(null);
    }
```
###### \java\seedu\address\model\person\Favourite.java
``` java
/**
 * Represents a person's Favourite/color on in the addressBook
 * Guarantees: immutable; is valid as declared in {@link #isValidInput(String)}
 */
public class Favourite implements Comparable {

    public static final String MESSAGE_FAVOURITE_CONSTRAINTS =
            "Person favourite should only be true or false, and it should not be blank";
    public static final String COLOR_SWITCH = "true";
    public static final String COLOR_OFF = "false";
    private Boolean isColorOn = false;

    /**
     * Validates given Favourite.
     *
     * @throws IllegalValueException if given favourite string is invalid.
     */
    public Favourite(String input) throws IllegalValueException {
        input = processNoInput(input);
        String trimmedinput = input.trim();
        isValidInput(trimmedinput);
        //Confirms the input is legal, COLOR_SWITCH or COLOR_OFF
        assert input.equals(COLOR_SWITCH) || input.equals(COLOR_OFF);
        updateColor(trimmedinput);
    }

    /**
     * Generates Color to be OFF if not input given
     * @param input input given, true or false
     * @return color to be on or off
     */
    private String processNoInput(String input) {
        if (input == null) {
            input = COLOR_OFF;
        }
        return input;
    }

    /**
     * Changes the isColorOn state if input is true
     * @param trimmedinput input given as true or false
     */
    private void updateColor(String trimmedinput) {
        if (trimmedinput.equals(COLOR_SWITCH) && !isColorOn) {
            this.isColorOn = true;
        } else {
            this.isColorOn = false;
        }
    }

    /**
     * Inverses the current state of Favourite
     */
    public void inverse() {
        isColorOn = false;
    }

    /**
     * Returns true if a given string is a favourite type
     * Throws Exception if invalid input.
     */
    public void isValidInput(String input) throws IllegalValueException {
        if (input.toLowerCase().equals(COLOR_SWITCH) || input.toLowerCase().equals(COLOR_OFF)) {
            return;
        } else {
            throw new IllegalValueException(MESSAGE_FAVOURITE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        if (isColorOn) {
            return COLOR_SWITCH;
        } else {
            return COLOR_OFF;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite // instanceof handles nulls
                && this.isColorOn.equals(((Favourite) other).isColorOn)); // state check
    }

    @Override
    public int compareTo(Object o) {
        Favourite comparedFavourite = (Favourite) o;
        return (comparedFavourite.toString()).compareTo(this.toString());
    }
}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Installs the comparator for the different sortType into comparatorMap
     */
    public UniquePersonList () {
        comparatorMap = new HashMap<String, Comparator<Person>>();
        for (String arg:SORTNAME_ARGS) {
            comparatorMap.put(arg, Comparator.comparing(Person::getName));
        }
        for (String arg:SORTNUM_ARGS) {
            comparatorMap.put(arg, Comparator.comparing(Person::getPhone));
        }
        for (String arg:SORTADD_ARGS) {
            comparatorMap.put(arg, Comparator.comparing(Person::getAddress));
        }
        for (String arg:SORTEMAIL_ARGS) {
            comparatorMap.put(arg, Comparator.comparing(Person::getEmail));
        }
        for (String arg:SORTREMARK_ARGS) {
            comparatorMap.put(arg, Comparator.comparing(Person::getRemark));
        }
        for (String arg:SORTBIRTHDAY_ARGS) {
            comparatorMap.put(arg, Comparator.comparing(Person::getBirthday));
        }
        for (String arg:SORTFAVOURITE_ARGS) {
            comparatorMap.put(arg, Comparator.comparing(Person::getFavourite));
        }
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the internalList as declared by the arguments
     */
    public void sort(String sortType) {
        if (stringContainsItemFromList(sortType, SORTNUMTIMESSEARCHED_ARGS)) {
            Collections.sort(internalList, (Person p1, Person p2) ->
                    p2.getNumTimesSearched().getValue() - p1.getNumTimesSearched().getValue());
        } else {
            Collections.sort(internalList, comparatorMap.get(sortType));
        }
    }
```
###### \java\seedu\address\Sound.java
``` java
/**
 * Plays a beep Sound.
 */
public class Sound {
    public static final String EMPTY = "";
    public static final String ERROR_SOUND_LOG_MESSAGE = "Error with playing sound.";

    private static final Logger logger = LogsCenter.getLogger("Error Sound");

    private static ArrayList<String> musicList = new ArrayList<String>(Arrays.asList("ErrorSound.mp3"));
    private static int curr = 0;
    private static String bip;
    private static Media hit;
    private static MediaPlayer mediaPlayer;

    /**
     * start playing the first error invalidCommandSound on the playlist.
     */
    public static void invalidCommandSound() {
        try {
            createsNewMediaPlayer();
            mediaPlayer.play();
        } catch (Exception ex) {
            logger.info(ERROR_SOUND_LOG_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Generates a new MediaPlayer
     * @throws URISyntaxException if media file cannot be found
     */
    private static void createsNewMediaPlayer() throws URISyntaxException {
        bip = musicList.get(curr);
        //must be a valid file name before begin searching
        assert bip != EMPTY;
        hit = new Media(Thread.currentThread().getContextClassLoader().getResource(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
    }
}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    /**
     * Extracts and Returns an Arraylist of strings to be used in autocomplete from XML
     *
     * @return ArrayList of String with valid inputs
     * @throws IOException unable to create new XML file
     */
    public ArrayList<String> updateAutocomplete() throws IOException {
        try {
            return XmlAutocomplete.updateAutocompleteWithStorageFile();
        } catch (Exception ex) {
            return XmlAutocomplete.createNewStorageFile(ex);
        }
    }

    /**
     * Adds a new command string to the XML file for autocomplete
     * @param commandWord command to be added
     * @return new ArrayList including the new valid command
     * @throws CommandException if autocomplete.xml cannot be made.
     */
    public ArrayList<String> setAddSuggestion(String commandWord) throws CommandException {
        return XmlAutocomplete.setAddSuggestion(commandWord);
    }
```
###### \java\seedu\address\storage\XmlAutocomplete.java
``` java
/**
 * Stores Autocomplete data in an XML file
 */
public class XmlAutocomplete {
    private static String[] possibleSuggestion = {"add", "birthday", "clear", "list", "help", "removetag", "image",
        "edit", "find", "delete", "select", "favourite", "history", "undo", "redo", "email", "sort", "sort name",
        "map", "sort number", "sort email", "sort address", "sort remark", "sort birthday", "sort favourite",
        "exit", "fuzzyfind"};
    private static ArrayList<String> mainPossibleSuggestion = new ArrayList<String>(Arrays.asList(possibleSuggestion));

    /**
     * Creates a new storage file named Autocomplete.xml
     *
     * @param ex exception to be handled
     */
    public static ArrayList<String> createNewStorageFile(Exception ex) throws IOException {
        File file = new File(STORAGE_FILE_NAME);
        file.createNewFile();
        addNewDataInStorage(EMPTY_STRING);
        return  mainPossibleSuggestion;
    }

    /**
     * Overloads the createNewStorageFile method
     *
     * @throws CommandException unable to create new file
     */
    public static void createNewStorageFile() throws CommandException {
        try {
            File file = new File(STORAGE_FILE_NAME);
            file.createNewFile();
            addNewDataInStorage(EMPTY_STRING);
        } catch (IOException ioe) {
            throw new CommandException(ERROR_MESSAGE_CREATE_FILE_FAILED);
        }
    }
    /**
     * Updates Storagefile with new content, used as refresh if no content
     *
     * @param commandWord input to be added to storage
     * @throws FileNotFoundException file cannot be found
     */
    public static void addNewDataInStorage(String commandWord) throws FileNotFoundException {
        if (!commandWord.equals(EMPTY_STRING)) {
            mainPossibleSuggestion.add(commandWord.trim());
        }
        XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(AUTOCOMPLETE_FILE_NAME)));
        e.writeObject(mainPossibleSuggestion);
        e.close();
    }
    /**
     * Reads the storage file to update mainpossiblesuggestion
     *
     * @throws FileNotFoundException storage file cannot be found
     */
    public static ArrayList<String> updateAutocompleteWithStorageFile() throws FileNotFoundException {
        XMLDecoder e = readStorageFile();
        mainPossibleSuggestion = ((ArrayList<String>) e.readObject());
        e.close();
        return mainPossibleSuggestion;
    }

    /**
     * Reads the storage file
     *
     * @return  an input stream for reading archive
     * @throws FileNotFoundException storagefile cannot be found
     */
    public static XMLDecoder readStorageFile() throws FileNotFoundException {
        return new XMLDecoder(new FileInputStream(STORAGE_FILE_NAME));
    }

    /**
     * Adds in a valid command string into autocomplete.xml storage
     *
     * @param commandWord
     * @throws CommandException if autocomplete.xml cannot be made.
     */
    public static ArrayList<String> setAddSuggestion(String commandWord) throws CommandException {
        if (mainPossibleSuggestion.contains(commandWord)) {
            return mainPossibleSuggestion;
        }
        try {
            addNewDataInStorage(commandWord);
        } catch (Exception ex) {
            createNewStorageFile();
        }
        return mainPossibleSuggestion;
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {
    public static final String AUTOCOMPLETE_FILE_NAME = "Autocomplete.xml";
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String STORAGE_FILE_NAME = "Autocomplete.xml";
    public static final String ERROR_MESSAGE_CREATE_FILE_FAILED = "Unable to create file Autocomplete.xml";

    private static final String FXML = "CommandBox.fxml";

    private static ArrayList<String> mainPossibleSuggestion;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    protected Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

    @FXML
    private TextField commandTextField;
    private ArrayList<String> prevText = new ArrayList<String>();
    private AutoCompletionBinding autocompletionbinding;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        updateAutocompleteTextField();
        autocompletionbinding = TextFields.bindAutoCompletion(commandTextField, mainPossibleSuggestion);
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * updates the autocompleteTextField
     */
    private void updateAutocompleteTextField() {
        try {
            mainPossibleSuggestion = storage.updateAutocomplete();
        } catch (IOException ioe) {
            raise(new DataSavingExceptionEvent(ioe));
        }
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java

    /**
     * Adds in a valid command string into autocomplete.xml storage
     * @param commandWord
     * @throws CommandException if autocomplete.xml cannot be made.
     */
    public static void setAddSuggestion(String commandWord) throws CommandException {
        Storage storage = generateStorage();
        mainPossibleSuggestion = storage.setAddSuggestion(commandWord);
    }

    /**
     * Creates an empty addressbook storageManager instance to be used for autocomplete
     * @return StorageManager
     */
    private static Storage generateStorage() {
        AddressBookStorage addressBookStorage = null;
        UserPrefsStorage userPrefsStorage = null;
        return new StorageManager(addressBookStorage, userPrefsStorage);
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     * Changes the color code of the person if favourite.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        if (person.getFavourite().toString().equals(TRUE_STRING)) {
            name.setStyle(RED_COLOUR);
        } else if (person.getFavourite().toString().equals(FALSE_STRING)) {
            name.setStyle(NO_COLOUR);
        }
    }
```
