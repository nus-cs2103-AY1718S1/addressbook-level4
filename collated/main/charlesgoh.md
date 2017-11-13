# charlesgoh
###### \java\seedu\address\commons\events\ui\BackupRequestEvent.java
``` java
/**
 * An Event for backing up of data to a selected location.
 */
public class BackupRequestEvent extends BaseEvent {

    private Model model;
    private String args;

    public BackupRequestEvent(Model model, String args) {
        this.model = model;
        this.args = args;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\BackupCommand.java
``` java
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "bk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Backs up data to a user input "
            + "location field [FILEPATH]\n"
            + "Parameter: KEYWORD [FILEPATH]\n"
            + "Example: " + COMMAND_WORD + "MyBackUpFile";

    public static final String MESSAGE_INVALID_INPUT = "Invalid Input.\n";

    public static final String MESSAGE_SUCCESS = "AddressBook++ data backed up successfully.";

    private String args;

    public BackupCommand(String trimmedArgs) {
        super();
        this.args = trimmedArgs;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireNonNull(model.getAddressBook());
        EventsCenter.getInstance().post(new BackupRequestEvent(model, args));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ChangePasswordCommand.java
``` java
/**
 * Changes user's password provided old password is correct and new passwords match.
 */
public class ChangePasswordCommand extends Command {
    public static final String COMMAND_WORD = "changepassword";
    public static final String COMMAND_ALIAS = "cpw";

    public static final String MESSAGE_SUCCESS = "Password changed successfully";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes user password. \n"
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_NEW_PASSWORD + "NEWPASSWORD "
            + PREFIX_CONFIRM_PASSWORD + "CONFIRMPASSWORD \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PASSWORD + "password "
            + PREFIX_NEW_PASSWORD + "mynewpassword111 " + PREFIX_CONFIRM_PASSWORD + "mynewpassword111\n"
            + "Example 2: " + COMMAND_ALIAS + " " + PREFIX_PASSWORD + "password "
            + PREFIX_NEW_PASSWORD + "mynewpassword111 " + PREFIX_CONFIRM_PASSWORD + "mynewpassword111\n";

    public static final String MESSAGE_INVALID_INPUT = "Invalid Input.\n";

    public static final String MESSAGE_OLD_NEW_PS_SAME = "New password must be different from your old password";
    public static final String MESSAGE_ERROR_OCCURED = "An error occured. Please try again.\n";
    public static final String MESSAGE_PASSWORD_INCORRECT = "Your password is incorrect. Please try again.\n";
    public static final String MESSAGE_PASSWORD_CONFIRMATION_INCORRECT = "Your new password and confirmation password "
            + "do not match. Please try again\n";

    private final Logger logger = LogsCenter.getLogger(ChangePasswordCommand.class);

    private String oldPassword;
    private String newPassword;
    private String confirmationPassword;

    /**
     * Takes in old password, new password and confirmation password from parser and creates a new
     * ChangePasswordCommand object
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    public ChangePasswordCommand(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmationPassword = confirmPassword;
    }

    /**
     * Forward hashes string using SHA256 encryption and returns hashed string
     * @param argument
     */
    private String forwardHash(String argument) {
        return Hashing.sha256().hashString(argument, StandardCharsets.UTF_8).toString();
    }

    /**
     * Forward hashes the user input password and checks if it matches with the encrypted password saved
     */
    private boolean isOldPasswordCorrect() {
        String forwardHashedInputPassword = forwardHash(oldPassword);
        String forwardHashActualPassword;
        UserPrefs userPrefs = model.getUserPrefs();
        forwardHashActualPassword = userPrefs.getAddressBookEncryptedPassword();
        if (forwardHashActualPassword.equals(forwardHashedInputPassword)) {
            logger.info("Actual password and input password matches");
            return true;
        } else {
            logger.warning("Actual password and input password do not match");
            return false;
        }
    }

    /**
     * Takes new input passwords and checks them against one another.
     */
    private boolean isNewPasswordInputsSame() {
        return newPassword.equals(confirmationPassword);
    }

    /**
     * Checks if old password and new password are the same
     */
    private boolean isOldAndNewPasswordTheSame() {
        return oldPassword.equals(newPassword);
    }

    @Override
    public CommandResult execute() throws CommandException {
        // Case where old password is incorrect
        if (!isOldPasswordCorrect()) {
            logger.warning("Password is incorrect. Note: Default password is 'password' ");
            throw new CommandException(MESSAGE_PASSWORD_INCORRECT);
        }

        // Case where new password and confirmation password do not match
        if (!isNewPasswordInputsSame()) {
            logger.warning("New password and confirmation password do not match");
            throw new CommandException(MESSAGE_PASSWORD_CONFIRMATION_INCORRECT);
        }

        // Case where old and new passwords are the same
        if (isOldAndNewPasswordTheSame()) {
            logger.warning("Old password and new password cannot be the same");
            throw new CommandException(MESSAGE_OLD_NEW_PS_SAME);
        }

        // Case where user input passes both checks. Password is changed and UserPrefs saved
        UserPrefs userPrefs;

        // Get user prefs file
        userPrefs = model.getUserPrefs();

        // Set new password to user prefs
        userPrefs.setAddressBookEncryptedPassword(newPassword);

        // Logs new password and saved password for debugging purposes
        String hashedNewPassword = forwardHash(newPassword);
        String userPrefsHashedPassword = userPrefs.getAddressBookEncryptedPassword();
        logger.info("New Password: " + newPassword
                + "\nEncrypted New Password: " + hashedNewPassword
                + "\nEncrypted Password From UserPrefs:" + userPrefsHashedPassword
                + "\nCommand's Password and UserPrefs saved password matches: "
                + Boolean.toString(hashedNewPassword.equals(userPrefsHashedPassword)) + "\n");

        // Return command result
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ChangePrivacyCommand.java
``` java
        /**
         * Returns the value of remarkIsPrivate
         */
        public Boolean getRemarkIsPrivate() {
            return remarkIsPrivate;
        }

        public void setRemarkIsPrivate(boolean remarkIsPrivate) {
            requireNonNull(remarkIsPrivate);
            this.remarkIsPrivate = remarkIsPrivate;
        }
```
###### \java\seedu\address\logic\commands\FontSizeCommand.java
``` java
/**
 * Sorts all persons in address book by any field. Sorting can be done in ascending or descending order
 */
public class FontSizeCommand extends Command {
    public static final int MINIMUM_FONT_SIZE_MULTIPLIER = 0;
    public static final int MAXIMUM_FONT_SIZE_MULTIPLIER = 5;
    public static final String COMMAND_WORD = "fontsize";
    public static final String COMMAND_ALIAS = "fs";
    public static final String INCREASE_SIZE_PARAMETER = "increase";
    public static final String DECREASE_SIZE_PARAMETER = "decrease";
    public static final String RESET_SIZE_PARAMETER = "reset";

    public static final ArrayList<String> ACCEPTED_PARAMETERS = new ArrayList<>(Arrays.asList(
            INCREASE_SIZE_PARAMETER, DECREASE_SIZE_PARAMETER, RESET_SIZE_PARAMETER));

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increases, decreases or resets font sizes \n"
                + "Parameters: KEYWORD [OPTION]\n"
                + "Example: " + COMMAND_WORD + " increase\n"
                + "Example 2: " + COMMAND_ALIAS + " reset\n";

    public static final String MESSAGE_INVALID_INPUT = "Invalid Input.\n" + MESSAGE_USAGE;

    public static final String MESSAGE_SUCCESS_INCREASE_FONT = "Font size increased successfully";
    public static final String MESSAGE_SUCCESS_DECREASE_FONT = "Font size decreased successfully";
    public static final String MESSAGE_SUCCESS_RESET_FONT = "Font size reset successfully";

    private final String option;

    public FontSizeCommand(String option) {
        this.option = option;
    }

    public String getOption() {
        return this.option;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        switch (option) {
        case INCREASE_SIZE_PARAMETER:
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(ChangeFontSizeEvent.getIncreaseSizeEventIndex()));
            return new CommandResult(MESSAGE_SUCCESS_INCREASE_FONT);
        case DECREASE_SIZE_PARAMETER:
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(ChangeFontSizeEvent.getDecreaseSizeEventIndex()));
            return new CommandResult(MESSAGE_SUCCESS_DECREASE_FONT);
        case RESET_SIZE_PARAMETER:
            EventsCenter.getInstance().post(new ChangeFontSizeEvent(ChangeFontSizeEvent.getResetSizeEventIndex()));
            return new CommandResult(MESSAGE_SUCCESS_RESET_FONT);
        default:
            System.err.println("Parameter is invalid");
            throw new CommandException(MESSAGE_INVALID_INPUT + MESSAGE_USAGE);
        }
    }

}
```
###### \java\seedu\address\logic\commands\LockCommand.java
``` java
/**
 * Sets lock in model to true or false, which allows or restricts the usage of all commands
 */
public class LockCommand extends Command {

    public static final String COMMAND_WORD = "lock";
    public static final String COMMAND_ALIAS = "lk";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Locks the application. "
            + "No commands can be executed\n"
            + "Parameters: "
            + PREFIX_PASSWORD + "PASSWORD ";

    public static final String MESSAGE_SUCCESS = "Address++ locked successfully";
    public static final String MESSAGE_PASSWORD_INCORRECT = "Password is incorrect. Please try again";

    private final Logger logger = LogsCenter.getLogger(UnlockCommand.class);

    private String password;

    public LockCommand(String password) {
        this.password = password;
    }

    /**
     * Checks if input password matches the one saved in user prefs.
     */
    private boolean isPasswordCorrect() {
        UserPrefs userPrefs = model.getUserPrefs();
        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8).toString();
        return hashedPassword.equals(userPrefs.getAddressBookEncryptedPassword());
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (isPasswordCorrect()) {
            // Case where password is correct

            // Access model to lock
            model.lockAddressBookFromModel();

            // Logs current state
            logger.info("Lock state is now: " + Boolean.toString(model.getLockState()));

            //Return command result
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            // Case where password is incorrect
            throw new CommandException(MESSAGE_PASSWORD_INCORRECT);
        }
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts all persons in address book by any field. Sorting can be done in ascending or descending order
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "so";

    public static final ArrayList<String> ACCEPTED_LIST_PARAMETERS = new ArrayList<>(Arrays.asList(
            "person", "task"));

    public static final ArrayList<String> ACCEPTED_FIELD_PARAMETERS = new ArrayList<>(Arrays.asList(
            "name", "phone", "email", "address", "priority", "deadline"));

    public static final ArrayList<String> ACCEPTED_ORDER_PARAMETERS = new ArrayList<>(Arrays.asList(
            "asc", "desc"));

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons or all tasks by chosen "
                + "field [NAME/PHONE/EMAIL/ADDRESS -- PRIORITY/DEADLINE] by [ASC/DESC] order. Case insensitive\n"
                + "Parameters: KEYWORD [LIST] [FIELD] [ORDER]\n"
                + "Example: " + COMMAND_WORD + " person email desc\n"
                + "Example 2: " + COMMAND_WORD + " task deadline desc\n";

    public static final String MESSAGE_INVALID_INPUT = "Invalid Input.\n"
            + "Accepted Person Field Values: NAME, PHONE, EMAIL, ADDRESS \n"
            + "Accepted Task Field Values: DEADLINE, PRIORITY\n"
            + "Accepted Order Values: ASC, DESC";

    public static final String MESSAGE_SUCCESS_PERSONS = "All persons in address book successfully sorted";
    public static final String MESSAGE_SUCCESS_TASKS = "All tasks in address book successfully sorted";

    private final String list;
    private final String field;
    private final String order;

    public SortCommand(String list, String field, String order) {
        this.field = field;
        this.order = order;
        this.list = list;
    }

    /**
     * Checks if argument command has the same parameter properties
     * @param commandB
     */
    public boolean sameCommandAs(SortCommand commandB) {
        return commandB.getList().equals(this.list) && commandB.getField()
                .equals(this.field) && commandB.getOrder().equals(this.order);
    }

    public String getField() {
        return this.field;
    }

    public String getOrder() {
        return this.order;
    }

    public String getList() {
        return this.list;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        if (this.list.equals(ACCEPTED_LIST_PARAMETERS.get(0))) {
            model.sortPersons(getField(), getOrder());
            return new CommandResult(MESSAGE_SUCCESS_PERSONS);
        } else {
            model.sortTasks(getField(), getOrder());
            return new CommandResult(MESSAGE_SUCCESS_TASKS);
        }
    }

}
```
###### \java\seedu\address\logic\commands\UnlockCommand.java
``` java
/**
 * Sets lock in model to true or false, which allows or restricts the usage of all commands
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";
    public static final String COMMAND_ALIAS = "ul";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlocks the application. "
            + "No commands can be executed\n"
            + "Parameters: "
            + PREFIX_PASSWORD + "PASSWORD ";

    public static final String MESSAGE_SUCCESS = "Address++ unlocked successfully";
    public static final String MESSAGE_PASSWORD_INCORRECT = "Password is incorrect. Please try again";

    private final Logger logger = LogsCenter.getLogger(UnlockCommand.class);

    private String password;

    public UnlockCommand(String password) {
        this.password = password;
    }

    /**
     * Checks if input password matches the one saved in user prefs.
     */
    private boolean isPasswordCorrect() {
        UserPrefs userPrefs = model.getUserPrefs();
        String hashedPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8).toString();
        return hashedPassword.equals(userPrefs.getAddressBookEncryptedPassword());
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (isPasswordCorrect()) {
            // Case where password is correct

            // Access model to unlocked
            model.unlockAddressBookFromModel();

            // Logs current state
            logger.info("Lock state is now: " + Boolean.toString(model.getLockState()));

            //Return command result
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            // Case where password is incorrect
            throw new CommandException(MESSAGE_PASSWORD_INCORRECT);
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    /**
     * Checks for non crud command words or aliases and returns the relevant commmand if there is one.
     * Otherwise control is returned back to the original parseCommand method.
     * Note: Code was refactored due to codacy's recommendation that there was a problem with parseCommand's
     * NPath complexity (i.e. it the code was too long. Makes it less readable and prone to errors)
     * @param commandWord
     * @param arguments
     */
    private Command processNonCrudCommands(String commandWord, String arguments) throws ParseException {
        // Cases for CRUD related functionality and for locking and unlocking
        switch (commandWord) {
        case OpenCommand.COMMAND_WORD:
        case OpenCommand.COMMAND_ALIAS:
            return new OpenCommand();

        case SaveAsCommand.COMMAND_WORD:
        case SaveAsCommand.COMMAND_ALIAS:
            return new SaveAsCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case LocateCommand.COMMAND_WORD:
        case LocateCommand.COMMAND_ALIAS:
            return new LocateCommandParser().parse(arguments);

        case NavigateCommand.COMMAND_WORD:
        case NavigateCommand.COMMAND_ALIAS:
            return new NavigateCommandParser().parse(arguments);

        case BackupCommand.COMMAND_WORD:
        case BackupCommand.COMMAND_ALIAS:
            return new BackupCommandParser().parse(arguments);

        case FontSizeCommand.COMMAND_WORD:
        case FontSizeCommand.COMMAND_ALIAS:
            return new FontSizeCommandParser().parse(arguments);

        case LockCommand.COMMAND_WORD:
        case LockCommand.COMMAND_ALIAS:
            return new LockCommandParser().parse(arguments);

        case UnlockCommand.COMMAND_WORD:
        case UnlockCommand.COMMAND_ALIAS:
            return new UnlockCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case FindCommand.COMMAND_WORD:
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        default:
            return null;
        }
    }
```
###### \java\seedu\address\logic\parser\BackupCommandParser.java
``` java
public class BackupCommandParser implements Parser<BackupCommand> {

    public static final int SIZE_OF_ARG_ARRAY = 1;

    /**
     * Parses the given user input and backs up data into a separate file name.
     * @param userInput
     * @throws ParseException
     */
    public BackupCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
        }

        // Converts arg arrays to lower case to account for caps entries
        String[] argKeywords = trimmedArgs.split("\\s");

        if (argKeywords.length != SIZE_OF_ARG_ARRAY) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BackupCommand.MESSAGE_USAGE));
        }

        return new BackupCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\logic\parser\ChangePasswordCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ChangePassword object
 */
public class ChangePasswordCommandParser implements Parser<ChangePasswordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand
     * and returns a ChangePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_PASSWORD, PREFIX_NEW_PASSWORD, PREFIX_CONFIRM_PASSWORD);

        // Check and split arguments before passing them to ChangePasswordCommand
        if (!argMultimap.getValue(PREFIX_PASSWORD).isPresent()
                || !argMultimap.getValue(PREFIX_NEW_PASSWORD).isPresent()
                || !argMultimap.getValue(PREFIX_CONFIRM_PASSWORD).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePasswordCommand.MESSAGE_USAGE));
        } else {
            String password = argMultimap.getValue(PREFIX_PASSWORD).get();
            String newPassword = argMultimap.getValue(PREFIX_NEW_PASSWORD).get();
            String confirmPassword = argMultimap.getValue(PREFIX_CONFIRM_PASSWORD).get();
            return new ChangePasswordCommand(password, newPassword, confirmPassword);
        }
    }
}
```
###### \java\seedu\address\logic\parser\FontSizeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object based on the field and order parameters provided
 */
public class FontSizeCommandParser implements Parser<FontSizeCommand> {
    public static final int PARAMETER_POSITION = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the FontSizeCommand
     * and returns a FontSizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FontSizeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FontSizeCommand.MESSAGE_USAGE));
        }

        // Converts arg arrays to lower case to account for caps entries
        String[] argKeywords = trimmedArgs.split("\\s");
        for (int i = 0; i < argKeywords.length; i++) {
            argKeywords[i] = argKeywords[i].toLowerCase();
        }

        if (argKeywords.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FontSizeCommand.MESSAGE_USAGE));
        }

        if (!FontSizeCommand.ACCEPTED_PARAMETERS.contains(argKeywords[PARAMETER_POSITION])
                || !FontSizeCommand.ACCEPTED_PARAMETERS.contains(argKeywords[PARAMETER_POSITION])
                || !FontSizeCommand.ACCEPTED_PARAMETERS.contains(argKeywords[PARAMETER_POSITION])) {
            throw new ParseException(String.format(MESSAGE_INVALID_INPUT, FontSizeCommand.MESSAGE_USAGE));
        }

        // If there are no problems with the input, return a new instance of SortCommand
        return new FontSizeCommand(argKeywords[PARAMETER_POSITION]);

    }

}
```
###### \java\seedu\address\logic\parser\LockCommandParser.java
``` java
/**
 * Parses input arguments and returns a new LockCommand object
 */
public class LockCommandParser implements Parser<LockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LockCommand
     * and returns a LockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LockCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD);
        String trimmedArgs = args.trim();

        if (!argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
            }
            String[] passwordKeywords = trimmedArgs.split("\\s+");
            return new LockCommand(passwordKeywords[0]);
        } else {
            String[] passwordKeywords = trimmedArgs.split(PREFIX_PASSWORD.getPrefix());
            return new LockCommand(passwordKeywords[1]);
        }
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object based on the field and order parameters provided
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final int LIST_ARG_POSITION = 0;
    public static final int FIELD_ARG_POSITION = 1;
    public static final int ORDER_ARG_POSITION = 2;
    public static final int SIZE_OF_ARG_ARRAY = 3;

    public static final List<String> PERSON_FIELD_ARGS = ACCEPTED_FIELD_PARAMETERS.subList(0, 4);
    public static final List<String> TASK_FIELD_ARGS = ACCEPTED_FIELD_PARAMETERS.subList(4, 6);

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        // Eliminate spaces
        String[] argKeywords = trimmedArgs.split("\\s");

        // Converts arg arrays to lower case to account for caps entries
        for (int i = 0; i < argKeywords.length; i++) {
            argKeywords[i] = argKeywords[i].toLowerCase();
        }

        if (argKeywords.length != SIZE_OF_ARG_ARRAY
                || (argKeywords[0].equals(ACCEPTED_LIST_PARAMETERS.get(0))
                && TASK_FIELD_ARGS.contains(argKeywords[1]))
                || (argKeywords[0].equals(ACCEPTED_LIST_PARAMETERS.get(1))
                && PERSON_FIELD_ARGS.contains(argKeywords[1]))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (!SortCommand.ACCEPTED_FIELD_PARAMETERS.contains(argKeywords[FIELD_ARG_POSITION])
                    || !SortCommand.ACCEPTED_ORDER_PARAMETERS.contains(argKeywords[ORDER_ARG_POSITION])
                    || !SortCommand.ACCEPTED_LIST_PARAMETERS.contains(argKeywords[LIST_ARG_POSITION])) {
            throw new ParseException(String.format(MESSAGE_INVALID_INPUT, SortCommand.MESSAGE_USAGE));
        }

        // If there are no problems with the input, return a new instance of SortCommand
        return new SortCommand(argKeywords[LIST_ARG_POSITION],
                argKeywords[FIELD_ARG_POSITION], argKeywords[ORDER_ARG_POSITION]);

    }

}
```
###### \java\seedu\address\logic\parser\UnlockCommandParser.java
``` java
/**
 * Parses input arguments and returns a new LockCommand object
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Password is required";

    /**
     * Parses the given {@code String} of arguments in the context of the UnlockCommand
     * and returns a UnlockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnlockCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD);
        String trimmedArgs = args.trim();

        if (!argMultimap.getValue(PREFIX_PASSWORD).isPresent()) {
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
            }
            String[] passwordKeywords = trimmedArgs.split("\\s+");
            return new UnlockCommand(passwordKeywords[0]);
        } else {
            String[] passwordKeywords = trimmedArgs.split(PREFIX_PASSWORD.getPrefix());
            return new UnlockCommand(passwordKeywords[1]);
        }
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sorts persons in person list by any field, in either ascending or descending order
     *
     * @param field
     * @param order
     */
    public void sortPersonsBy(String field, String order) {
        persons.sortBy(field, order);
    }

    /**
     * Sorts persons in person list by any field, in either ascending or descending order
     *
     * @param field
     * @param order
     */
    public void sortTasksBy(String field, String order) {
        tasks.sortBy(field, order);
    }

```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Sorts all persons in person list by chosen field in ascending (asc) or descending (desc) order
     * @param field
     * @param order
     */
    void sortPersons(String field, String order);

    /**
     * Sorts all tasks in task list by chosen field in ascending (asc) or descending (desc) order
     * @param field
     * @param order
     */
    void sortTasks(String field, String order);

    /**
     * Returns lock state. True means locked. False means unlocked
     */
    boolean getLockState();

    /**
     * Locks address book from model
     */
    void lockAddressBookFromModel();

    /**
     * Unlock address book from model
     */
    void unlockAddressBookFromModel();

    /**
     * Returns a copy of the UserPrefs
     */
    UserPrefs getUserPrefs();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortPersons(String field, String order) {
        addressBook.sortPersonsBy(field, order);
        Index[] updatedIndexes = addressBook.getMappings();
        System.out.println(updatedIndexes.length);
        addressBook.updateTaskAssigneeMappings(updatedIndexes);
        indicateAddressBookChanged();
    }

    @Override
    public void sortTasks(String field, String order) {
        addressBook.sortTasksBy(field, order);
        indicateAddressBookChanged();
    }

    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    public boolean getLockState() {
        return getUserPrefs().getAddressBookLockState();
    }

    public void lockAddressBookFromModel() {
        getUserPrefs().lockAddressBook();
    }

    public void unlockAddressBookFromModel() {
        getUserPrefs().unlockAddressBook();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    @Override
    public Remark getRemark() {
        return remark.get();
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.set(requireNonNull(avatar));
    }

    @Override
    public ObjectProperty<Avatar> avatarProperty() {
        return avatar;
    }

    @Override
    public Avatar getAvatar() {
        return avatar.get();
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
public class Remark {
    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person remarks can take any values, and it should not be blank";

    /*
     * The first character of the remark must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String REMARK_VALIDATION_REGEX = "[^\\s].*";
    public static final String REMARK_PLACEHOLDER_VALUE = "";

    public final String value;
    private boolean isPrivate = false;
    private int privacyLevel = 2;

    /**
     * Validates given remark.
     *
     * @throws IllegalValueException if given remark string is invalid.
     */
    public Remark(String remark) throws IllegalValueException {
        if (remark == null) {
            this.value = REMARK_PLACEHOLDER_VALUE;
            return;
        }
        if (!isValidRemark(remark)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = remark;
    }

    public Remark(String remark, boolean isPrivate) throws IllegalValueException {
        this(remark);
        this.setPrivate(isPrivate);
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(REMARK_VALIDATION_REGEX) || test.equals(REMARK_PLACEHOLDER_VALUE);
    }

```
###### \java\seedu\address\model\person\Remark.java
``` java

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    public void sortBy(String field, String order) {
        //sortyBy first chooses the right comparator
        System.out.println(internalList.size());
        internalCopy.clear();
        for (Person p : internalList) {
            internalCopy.add(p);
        }
        System.out.println(internalCopy.size());
        Comparator<Person> comparator = null;

        /**
         * Comparators for the various fields available for sorting
         */
        Comparator<Person> personNameComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().value.compareTo(o2.getName().value);
            }
        };

        Comparator<Person> personPhoneComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getPhone().value.compareTo(o2.getPhone().value);
            }
        };

        Comparator<Person> personEmailComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getEmail().value.compareTo(o2.getEmail().value);
            }
        };

        Comparator<Person> personAddressComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAddress().value.compareTo(o2.getAddress().value);
            }
        };

        switch (field) {
        case "name":
            comparator = personNameComparator;
            break;

        case "phone":
            comparator = personPhoneComparator;
            break;

        case "email":
            comparator = personEmailComparator;
            break;

        case "address":
            comparator = personAddressComparator;
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }

        //sortBy then chooses the right ordering
        switch (order) {
        case "asc":
            Collections.sort(internalList, comparator);
            break;

        case "desc":
            Collections.sort(internalList, Collections.reverseOrder(comparator));
            break;

        default:
            throw new AssertionError("Invalid field parameter entered...\n");
        }
    }
```
###### \java\seedu\address\model\task\UniqueTaskList.java
``` java
    public void sortBy(String field, String order) {
        //sortyBy first chooses the right comparator
        Comparator<Task> comparator = null;

        /*
         * Comparators for the various fields available for sorting
         */
        Comparator<Task> priorityComparator = Comparator.comparingInt(o -> o.getPriority().value);

        Comparator<Task> deadlineComparator = (o1, o2) -> {
            if (o1.getDeadline().date == null || o2.getDeadline().date == null) {
                return 0;
            } else {
                return o1.getDeadline().date.compareTo(o2.getDeadline().date);
            }
        };

        switch (field) {
        case "priority":
            comparator = priorityComparator;
            break;

        case "deadline":
            comparator = deadlineComparator;
            break;

        default:
            try {
                System.out.println("An error occured");
                throw new Exception("Invalid field parameter entered...\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //sortBy then chooses the right ordering
        switch (order) {
        case "asc":
            Collections.sort(internalList, comparator);
            break;

        case "desc":
            Collections.sort(internalList, Collections.reverseOrder(comparator));
            break;

        default:
            try {
                System.out.println("An error occured");
                throw new Exception("Invalid field parameter entered...\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getAddressBookEncryptedPassword() {
        return addressBookEncryptedPassword;
    }

    public void setAddressBookEncryptedPassword(String addressBookPasswordInput) {
        this.addressBookEncryptedPassword = Hashing.sha256()
                .hashString(addressBookPasswordInput, StandardCharsets.UTF_8).toString();
    }

    public void lockAddressBook() {
        this.addressBookLockState = true;
    }

    public void unlockAddressBook() {
        this.addressBookLockState = false;
    }

    public boolean getAddressBookLockState() {
        return this.addressBookLockState;
    }
```
###### \java\seedu\address\storage\Storage.java
``` java
    /**
     * Backs up data to a remote location. Subscribes to BackupRequestEvent
     */
    void handleBackupAddressBook(BackupRequestEvent event) throws IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleBackupAddressBook(BackupRequestEvent event) throws IOException {
        logger.fine("Attempting to write to backup data file in custom location");
        ReadOnlyAddressBook addressBook = event.getModel().getAddressBook();
        String args = event.getArgs();
        this.saveAddressBook(addressBook, args);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Handle increase font size command for menu item
     */
    @FXML
    public void handleIncreaseFontSize() {
        logger.info("Handling increase in font size");
        raise(new ChangeFontSizeEvent(ChangeFontSizeEvent.getIncreaseSizeEventIndex()));
    }

    /**
     * Handle decrease font size command for menu item
     */
    @FXML
    public void handleDecreaseFontSize() {
        logger.info("Handling decrease in font size");
        raise(new ChangeFontSizeEvent(ChangeFontSizeEvent.getDecreaseSizeEventIndex()));
    }

    /**
     * Handle reset font size command
     */
    @FXML
    public void handleResetFontSize() {
        logger.info("Handling reset in font size");
        raise(new ChangeFontSizeEvent(ChangeFontSizeEvent.getResetSizeEventIndex()));
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Set default size for all attributes
     */
    public void updateAttributeSizes() {
        nameSize = DEFAULT_NAME_SIZE + (fontSizeMultiplier * FONT_SIZE_EXTENDER);
        attributeSize = DEFAULT_ATTRIBUTE_SIZE + (fontSizeMultiplier * FONT_SIZE_EXTENDER);

        // Set styles using set name and attribute sizes
        name.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        id.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        phone.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        address.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        remark.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        email.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    public Label getName() {
        return name;
    }

    public Label getId() {
        return id;
    }

    public Label getPhone() {
        return phone;
    }

    public Label getAddress() {
        return address;
    }

    public Label getRemark() {
        return remark;
    }

    public Label getEmail() {
        return email;
    }

    public int getFontSizeMultiplier() {
        return fontSizeMultiplier;
    }

    public void setFontSizeMultiplier(int fontSizeMultiplier) {
        this.fontSizeMultiplier = fontSizeMultiplier;
    }
    //author
}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * Increases all person cards' font sizes in person list
     */
    public void increaseFontSize() {
        logger.info("PersonListPanel: Increasing font sizes");
        setFontSizeMultiplier(this.fontSizeMultiplier + 1);
        setConnections(personList);
    }

    /**
     * Decreases all person cards' font sizes in person list
     */
    public void decreaseFontSize() {
        logger.info("PersonListPanel: Decreasing font sizes");
        setFontSizeMultiplier(this.fontSizeMultiplier - 1);
        setConnections(personList);
    }

    /**
     * Resets all person cards' font sizes in person list
     */
    public void resetFontSize() {
        logger.info("PersonListPanel: Resetting font sizes");
        fontSizeMultiplier = MINIMUM_FONT_SIZE_MULTIPLIER;
        setConnections(personList);
    }

    /**
     * Gets integer value of font size multiplier
     */
    public int getFontSizeMultiplier() {
        return fontSizeMultiplier;
    }

    /**
     * Set integer value of font size multiplier
     */
    public void setFontSizeMultiplier(int fontSizeMultiplier) {
        // Set new font size
        this.fontSizeMultiplier = fontSizeMultiplier;

        // Restrict from minimum
        this.fontSizeMultiplier = Math.max(MINIMUM_FONT_SIZE_MULTIPLIER, this.fontSizeMultiplier);

        // Restrict from maximum
        this.fontSizeMultiplier = Math.min(MAXIMUM_FONT_SIZE_MULTIPLIER, this.fontSizeMultiplier);

        logger.info("New person font size multiplier: " + Integer.toString(this.fontSizeMultiplier));
    }

    /**
     * Handles command induced change font size event for person cards
     * @param event
     */
    @Subscribe
    private void handlePersonCardChangeFontSizeEvent (ChangeFontSizeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.getTriggerOption()) {
        case 0:
            logger.info("Attempting to increase font size");
            increaseFontSize();
            break;
        case 1:
            decreaseFontSize();
            logger.info("Attempting to decrease font size");
            break;
        case 2:
            resetFontSize();
            logger.info("Attempting to reset font size");
            break;
        default:
            logger.info("Unable to handle change font size event. Stopping execution now");
        }
    }
```
###### \java\seedu\address\ui\TaskCard.java
``` java
    /**
     * Set default size for all attributes
     */
    public void updateAttributeSizes() {
        nameSize = DEFAULT_NAME_SIZE + (fontSizeMultipler * FONT_SIZE_EXTENDER);
        attributeSize = DEFAULT_ATTRIBUTE_SIZE + (fontSizeMultipler * FONT_SIZE_EXTENDER);

        // Set styles using set name and attribute sizes
        taskName.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        id.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        description.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        deadline.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        priority.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        taskAddress.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        assignCount.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        state.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
    }
```
###### \java\seedu\address\ui\TaskCard.java
``` java

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }

    public Label getTaskName() {
        return taskName;
    }

    public Label getId() {
        return id;
    }

    public Label getDescription() {
        return description;
    }

    public Label getDeadline() {
        return deadline;
    }

    public Label getPriority() {
        return priority;
    }

    public Label getTaskAddress() {
        return taskAddress;
    }

    public void setFontSizeMultiplier(int fontSizeMultipler) {
        this.fontSizeMultipler = fontSizeMultipler;
    }

    public int getFontSizeMultiplier() {
        return this.fontSizeMultipler;
    }
}
```
###### \java\seedu\address\ui\TaskListPanel.java
``` java
    /**
     * Increases all task cards' font sizes in person list
     */
    public void increaseFontSize() {
        logger.info("TaskListPanel: Increasing font sizes");
        setFontSizeMultiplier(fontSizeMultiplier + 1);
        setConnections(taskList);
    }

    /**
     * Decreases all task cards' font sizes in person list
     */
    public void decreaseFontSize() {
        logger.info("TaskListPanel: Decreasing font sizes");
        setFontSizeMultiplier(fontSizeMultiplier - 1);
        setConnections(taskList);
    }

    /**
     * Resets all task cards' font sizes in person list
     */
    public void resetFontSize() {
        logger.info("TaskListPanel: Resetting font sizes");
        fontSizeMultiplier = MINIMUM_FONT_SIZE_MULTIPLIER;
        setConnections(taskList);
    }

    /**
     * Gets integer value of font size multiplier
     */
    public int getFontSizeMultiplier() {
        return fontSizeMultiplier;
    }

    /**
     * Set integer value of font size multiplier
     */
    public void setFontSizeMultiplier(int fontSizeMultiplier) {
        // Set new font size
        this.fontSizeMultiplier = fontSizeMultiplier;

        // Restrict from minimum
        this.fontSizeMultiplier = Math.max(MINIMUM_FONT_SIZE_MULTIPLIER, this.fontSizeMultiplier);

        // Restrict from maximum
        this.fontSizeMultiplier = Math.min(MAXIMUM_FONT_SIZE_MULTIPLIER, this.fontSizeMultiplier);

        logger.info("New task font size multiplier: " + Integer.toString(this.fontSizeMultiplier));
    }

    /**
     * Handles command induced change font size event for task cards
     * @param event
     */
    @Subscribe
    private void handleTaskCardChangeFontSizeEvent (ChangeFontSizeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.getTriggerOption()) {
        case 0:
            logger.info("Attempting to increase font size");
            increaseFontSize();
            break;
        case 1:
            decreaseFontSize();
            logger.info("Attempting to decrease font size");
            break;
        case 2:
            resetFontSize();
            logger.info("Attempting to reset font size");
            break;
        default:
            logger.info("Unable to handle change font size event. Stopping execution now");
        }
    }
```
###### \java\seedu\address\ui\ViewPersonPanel.java
``` java
    public static final int DEFAULT_NAME_SIZE = 15;
    public static final int DEFAULT_ATTRIBUTE_SIZE = 10;
    public static final int FONT_SIZE_EXTENDER = 5;
    public static final int DEFAULT_FONT_SIZE_MULTIPLIER = 0;
```
###### \java\seedu\address\ui\ViewPersonPanel.java
``` java
    /**
     * Set default size for all attributes
     */
    public void updateAttributeSizes() {
        nameSize = DEFAULT_NAME_SIZE + (fontSizeMultipler * FONT_SIZE_EXTENDER);
        attributeSize = DEFAULT_ATTRIBUTE_SIZE + (fontSizeMultipler * FONT_SIZE_EXTENDER);

        // Set styles using set name and attribute sizes
        name.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        phone.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        address.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        remark.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        email.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
    }
```
###### \java\seedu\address\ui\ViewPersonPanel.java
``` java
    public int getFontSizeMultipler() {
        return fontSizeMultipler;
    }

    public void setFontSizeMultipler(int fontSizeMultipler) {
        this.fontSizeMultipler = fontSizeMultipler;
    }
```
###### \resources\view\MainWindow.fxml
``` fxml
      <Menu mnemonicParsing="false" text="Font Size">
        <items>
          <MenuItem fx:id="increaseSizeMenuItem" mnemonicParsing="false" onAction="#handleIncreaseFontSize" text="Increase +" />
            <MenuItem fx:id="decreaseSizeMenuItem" mnemonicParsing="false" onAction="#handleDecreaseFontSize" text="Decrease -" />
            <MenuItem fx:id="resetSizeMenuItem" mnemonicParsing="false" onAction="#handleResetFontSize" text="Reset" />
        </items>
      </Menu>
  </MenuBar>

  <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
    <padding>
      <Insets bottom="5" left="10" right="10" top="5" />
    </padding>
  </StackPane>

  <StackPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="pane-with-border" VBox.vgrow="NEVER">
    <padding>
      <Insets bottom="5" left="10" right="10" top="5" />
    </padding>
  </StackPane>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4, 0.5" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
    </VBox>
      <VBox fx:id="taskList" prefHeight="262.0" prefWidth="121.0">
         <children>
            <StackPane fx:id="taskListPanelPlaceholder" alignment="TOP_CENTER" VBox.vgrow="ALWAYS">
               <opaqueInsets>
                  <Insets />
               </opaqueInsets></StackPane>
         </children>
         <padding>
            <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
         </padding>
      </VBox>

    <StackPane fx:id="browserPlaceholder">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
    </StackPane>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
