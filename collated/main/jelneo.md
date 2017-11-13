# jelneo
###### \java\seedu\address\commons\events\ui\ChangeToCommandBoxView.java
``` java
/**
 * Indicate request to change to command box view
 */
public class ChangeToCommandBoxView extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeToLoginViewEvent.java
``` java
/**
 * Indicates a request to display login text fields
 */
public class ChangeToLoginViewEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\LoginAppRequestEvent.java
``` java
/**
 * Indicates a request for App login.
 */
public class LoginAppRequestEvent extends BaseEvent {

    private boolean hasLoginSuccessfully = false;

    public LoginAppRequestEvent(boolean hasLoginSuccessfully) {
        this.hasLoginSuccessfully = hasLoginSuccessfully;
        LoginCommand.setLoginStatus(hasLoginSuccessfully);
    }

    public boolean getLoginStatus() {
        return hasLoginSuccessfully;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\LoginInputChangeEvent.java
``` java

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that login inputs have changed
 */
public class LoginInputChangeEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\LogoutAppRequestEvent.java
``` java
/**
 * Indicates a request for App logout.
 */
public class LogoutAppRequestEvent extends BaseEvent {

    private boolean hasLogoutSuccessfully = false;

    public LogoutAppRequestEvent(boolean hasLogoutSuccessfully) {
        this.hasLogoutSuccessfully = hasLogoutSuccessfully;
        LogoutCommand.setLogoutStatus(hasLogoutSuccessfully);
    }

    public boolean getLogoutStatus() {
        return hasLogoutSuccessfully;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\exceptions\UserNotFoundException.java
``` java
/**
 * Signals that the user does not exist
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super();
    }
}
```
###### \java\seedu\address\logic\commands\BorrowCommand.java
``` java
/**
 * Updates debt field when a person borrows more money
 */
public class BorrowCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "borrow";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": increase the debt of a person by "
            + "the amount of money entered.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "AMOUNT (must have at least 1 digit and either a positive integer or a positive number with "
            + "two decimal places)\n"
            + "Example 1: " + COMMAND_WORD + " 1 120.50\n"
            + "Example 2: " + COMMAND_WORD + " 120.50";
    public static final String MESSAGE_BORROW_SUCCESS = "%1$s has borrowed $%2$s";

    private final Index targetIndex;
    private final Debt amount;

    public BorrowCommand(Debt amount) {
        this.targetIndex = null;
        this.amount = amount;
    }

    public BorrowCommand(Index targetIndex, Debt amount) {
        this.targetIndex = targetIndex;
        this.amount = amount;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ReadOnlyPerson personThatBorrowed = selectPerson(targetIndex);

        try {
            if (personThatBorrowed.isWhitelisted()) {
                personThatBorrowed = model.removeWhitelistedPerson(personThatBorrowed);
            }
            model.addDebtToPerson(personThatBorrowed, amount);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList
                + String.format(MESSAGE_BORROW_SUCCESS, personThatBorrowed.getName(), amount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BorrowCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((BorrowCommand) other).targetIndex == null) // both targetIndex null
                || this.targetIndex.equals(((BorrowCommand) other).targetIndex))); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
/**
 * Filters contacts by tags in masterlist
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_FILTER_ACKNOWLEDGEMENT = "Showing all contacts with the tag(s): %1$s\n%2$s ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": filters the address book by tag(s)\n"
            + "Parameters: [ TAG ]...\n"
            + "Example: " + COMMAND_WORD + " friendly tricky";

    private final List<String> tags;

    public FilterCommand(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        model.deselectPerson();
        model.updateFilteredPersonList(new PersonContainsTagPredicate(tags));

        String allTagKeywords = tags.toString();
        return new CommandResult(String.format(MESSAGE_FILTER_ACKNOWLEDGEMENT, allTagKeywords,
                getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.tags.equals(((FilterCommand) other).tags));
    }
}
```
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
/**
 * Handles login of a user
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_LOGIN_ACKNOWLEDGEMENT = "Login successful";
    public static final String MESSAGE_LOGIN_UNSUCCESSFUL = "Unable to log into Address Book";
    public static final String MESSAGE_LOGIN_REQUEST = "Please log in first";
    public static final String MESSAGE_LOGIN_FORMAT = COMMAND_WORD + " USERNAME PASSWORD";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " USERNAME PASSWORD\nExample: "
            + COMMAND_WORD + " JohnDoe hiIAmJohnDoe123";
    private static boolean isLoggedIn = false;
    private final Username username;
    private final Password password;

    public LoginCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void setLoginStatus(boolean val) {
        isLoggedIn = val;
    }

    /**
     * Verifies if user is valid
     * @throws UserNotFoundException if user is not valid
     * @throws IllegalValueException if either username or password do not meet the username or password requirements
     */
    private void verifyUser() throws UserNotFoundException, IllegalValueException {
        model.authenticateUser(username, password);
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            verifyUser();
            return new CommandResult(MESSAGE_LOGIN_ACKNOWLEDGEMENT);
        } catch (UserNotFoundException unfe) {
            throw new CommandException(MESSAGE_LOGIN_UNSUCCESSFUL);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && this.username.equals(((LoginCommand) other).username)
                && this.password.equals(((LoginCommand) other).password));
    }
}
```
###### \java\seedu\address\logic\commands\PaybackCommand.java
``` java
/**
 * Updates debt field when a person repays an amount
 */
public class PaybackCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "payback";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": decrease the debt of a person by "
            + "the amount of money entered.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "AMOUNT (must have at least 1 digit and either a positive integer or a positive number with "
            + "two decimal places)\n"
            + "Example 1: " + COMMAND_WORD + " 1 100.50\n"
            + "Example 2: " + COMMAND_WORD + " 100.50";

    public static final String MESSAGE_PAYBACK_SUCCESS = "%1$s has paid $%2$s back";
    public static final String MESSAGE_PAYBACK_FAILURE = "Amount paid back cannot be more than the debt owed.";

    private final Index targetIndex;
    private final Debt amount;

    public PaybackCommand(Debt amount) {
        this.targetIndex = null;
        this.amount = amount;
    }

    public PaybackCommand(Index targetIndex, Debt amount) {
        this.targetIndex = targetIndex;
        this.amount = amount;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ReadOnlyPerson personThatPaidBack = selectPerson(targetIndex);

        try {
            personThatPaidBack = model.deductDebtFromPerson(personThatPaidBack, amount);
            if (personThatPaidBack.getDebt().toNumber() == 0 && !personThatPaidBack.isBlacklisted()) {
                model.addWhitelistedPerson(personThatPaidBack);
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList
                + String.format(MESSAGE_PAYBACK_SUCCESS, personThatPaidBack.getName(), amount));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PaybackCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((PaybackCommand) other).targetIndex == null) // both targetIndex null
                || this.targetIndex.equals(((PaybackCommand) other).targetIndex)
                && this.amount.equals(((PaybackCommand) other).amount))); // state check
    }
}
```
###### \java\seedu\address\logic\parser\BorrowCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BorrowCommand object
 */
public class BorrowCommandParser implements Parser<BorrowCommand> {

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";
    // arguments: index and debt amount borrowed
    private static final int MAXIMUM_ARGS_LENGTH = 2;
    private static final int ARGS_LENGTH_WITHOUT_INDEX = 1;


    @Override
    public BorrowCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        Debt debtAmount;
        String[] argsList = args.trim().split(ONE_OR_MORE_SPACES_REGEX);
        if (argsList.length > MAXIMUM_ARGS_LENGTH) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
        }

        if (argsList.length == ARGS_LENGTH_WITHOUT_INDEX) {
            try {
                debtAmount = new Debt(argsList[0]);
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
            }

            return new BorrowCommand(debtAmount);
        } else {
            try {
                index = ParserUtil.parseIndex(argsList[0]);
                debtAmount = new Debt(argsList[1]);
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BorrowCommand.MESSAGE_USAGE));
            }
        }

        return new BorrowCommand(index, debtAmount);
    }
}
```
###### \java\seedu\address\logic\parser\FilterCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split(ONE_OR_MORE_SPACES_REGEX);
        for (String keyword : tagKeywords) {
            if (!Tag.isValidTagName(keyword)) {
                throw new ParseException(MESSAGE_TAG_CONSTRAINTS);
            }
        }

        return new FilterCommand(Arrays.asList(tagKeywords));
    }
}
```
###### \java\seedu\address\logic\parser\LoginCommandParser.java
``` java
/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        try {
            String[] argsList = trimmedArgs.split(ONE_OR_MORE_SPACES_REGEX);

            // if incorrect number of arguments are supplied, throw ArrayOutOfBoundsException
            if (argsList.length != 2) {
                throw new ArrayIndexOutOfBoundsException();
            }
            Username username = ParserUtil.parseUsername(argsList[0]);
            Password password = ParserUtil.parsePassword(argsList[1]);
            return new LoginCommand(username, password);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (ArrayIndexOutOfBoundsException aiofoe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code username} into a {@code Username} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the username does not meet length requirement and/or contains illegal characters
     */
    public static Username parseUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        return new Username(trimmedUsername);
    }

    /**
     * Parses {@code password} into a {@code Password} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the password does not meet length requirement and/or contains illegal characters

     */
    public static Password parsePassword(String password) throws IllegalValueException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code Optional<String> totalDebt} into an {@code Optional<Debt>} if {@code totalDebt}
     * is present.
     * Meant for parsing for Edit command.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     * @throws IllegalValueException when {@code totalDebt} is 0
     */
    public static Optional<Debt> parseTotalDebt(Optional<String> totalDebt) throws IllegalValueException {
        requireNonNull(totalDebt);
        if (totalDebt.isPresent() && Double.valueOf(totalDebt.get()) == 0) {
            throw new IllegalValueException(MESSAGE_INVALID_TOTAL_DEBT);
        }
        return totalDebt.isPresent() ? Optional.of(new Debt(totalDebt.get())) : Optional.empty();
    }
}
```
###### \java\seedu\address\logic\parser\PaybackCommandParser.java
``` java
/**
 * Parses input arguments and creates a new PaybackCommand object
 */
public class PaybackCommandParser implements Parser<PaybackCommand> {

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";
    // arguments: index and debt amount repaid
    private static final int MAXIMUM_ARGS_LENGTH = 2;
    private static final int ARGS_LENGTH_WITHOUT_INDEX = 1;


    @Override
    public PaybackCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        Debt debtAmount;
        String[] argsList = args.trim().split(ONE_OR_MORE_SPACES_REGEX);
        if (argsList.length > MAXIMUM_ARGS_LENGTH) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaybackCommand.MESSAGE_USAGE));
        }

        if (argsList.length == ARGS_LENGTH_WITHOUT_INDEX) {
            try {
                debtAmount = new Debt(argsList[0]);
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaybackCommand.MESSAGE_USAGE));
            }

            return new PaybackCommand(debtAmount);
        } else {
            try {
                index = ParserUtil.parseIndex(argsList[0]);
                debtAmount = new Debt(argsList[1]);
            } catch (IllegalValueException ive) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaybackCommand.MESSAGE_USAGE));
            }
        }

        return new PaybackCommand(index, debtAmount);
    }
}
```
###### \java\seedu\address\logic\Password.java
``` java
/**
 * Represents the password of a user account
 */
public class Password {
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final String MESSAGE_PASSWORD_CHARACTERS_CONSTRAINTS = "Password can only consist of uppercase "
            + "letters (A-Z), lowercase letters (a-z),"
            + " digits (0-9) and special characters (!@#$&()_-.+)";
    public static final String MESSAGE_PASSWORD_LENGTH_CONSTRAINTS = "Length of password cannot be less than "
            + PASSWORD_MIN_LENGTH + " characters.";
    public static final String PASSWORD_VALIDATION_REGEX = "^[a-zA-Z0-9!@#$&()_\\-.+]+$";

    private String value;

    /**
     * Validates a given password.
     *
     * @param value the password of the user
     * @throws IllegalValueException if given value string is invalid.
     */
    public Password(String value) throws IllegalValueException {
        requireNonNull(value);
        String trimmedPassword = value.trim();
        if (!isValidPasswordLength(value)) {
            throw new IllegalValueException(MESSAGE_PASSWORD_LENGTH_CONSTRAINTS);
        }
        if (!hasValidPasswordCharacters(value)) {
            throw new IllegalValueException(MESSAGE_PASSWORD_CHARACTERS_CONSTRAINTS);
        }
        this.value = trimmedPassword;
    }

    /**
     * Returns if a given string has a valid password length.
     */
    public static boolean isValidPasswordLength(String testVal) {
        return testVal.length() >= PASSWORD_MIN_LENGTH;
    }

    /**
     * Returns if a given string has valid password characters.
     */
    public static boolean hasValidPasswordCharacters(String testVal) {
        return testVal.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && this.value.equals(((Password) other).value)); // state check
    }

}
```
###### \java\seedu\address\logic\Username.java
``` java
/**
 * Represents the username of a user account
 */
public class Username {

    public static final int USERNAME_MIN_LENGTH = 6;
    public static final String MESSAGE_USERNAME_CHARACTERS_CONSTRAINTS = "Username can only consist of uppercase "
                                                                            + "letters (A-Z), lowercase letters (a-z),"
                                                                            + " digits (0-9) and underscores (_).";
    public static final String MESSAGE_USERNAME_LENGTH_CONSTRAINTS = "Length of username cannot be less than "
                                                                          + USERNAME_MIN_LENGTH + " characters.";
    public static final String USERNAME_VALIDATION_REGEX = "^[a-zA-Z0-9_]+$";

    private String value;

    /**
     * Validates a given username.
     *
     * @param value the username of the user
     * @throws IllegalValueException if given value string is invalid.
     */
    public Username(String value) throws IllegalValueException {
        requireNonNull(value);
        String trimmedUsername = value.trim();
        if (!isValidUsernameLength(value)) {
            throw new IllegalValueException(MESSAGE_USERNAME_LENGTH_CONSTRAINTS);
        }
        if (!hasValidUsernameCharacters(value)) {
            throw new IllegalValueException(MESSAGE_USERNAME_CHARACTERS_CONSTRAINTS);
        }
        this.value = trimmedUsername;
    }

    /**
     * Returns if a given string has a valid username length.
     */
    public static boolean isValidUsernameLength(String testVal) {
        return testVal.length() >= USERNAME_MIN_LENGTH;
    }

    /**
     * Returns if a given string has valid username characters.
     */
    public static boolean hasValidUsernameCharacters(String testVal) {
        return testVal.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && this.value.equals(((Username) other).value)); // state check
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Increase debts of a person by the indicated amount
     * @param target person that borrowed more money
     * @param amount amount that the person borrowed. Must be either a positive integer or positive number with
     *               two decimal places
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void addDebtToPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException {
        Person editedPerson = new Person(target);

        try {
            Debt newCurrDebt = new Debt(target.getDebt().toNumber() + amount.toNumber());
            Debt newTotalDebt = new Debt(target.getTotalDebt().toNumber() + amount.toNumber());
            editedPerson.setDebt(newCurrDebt);
            editedPerson.setTotalDebt(newTotalDebt);
            persons.setPerson(target, editedPerson);
        } catch (DuplicatePersonException dpe) {
            assert false : "There should be no duplicate when updating the debt of a person";
        } catch (IllegalValueException ive) {
            assert false : "New debt amount should not be invalid since amount and debt field in target have "
                    + "been validated";
        }
    }

    /**
     * Decrease the debt of a person by the amount indicated
     * @param target person in the address book who paid back some money
     * @param amount amount that the person paid back. Must be either a positive integer or positive number with
     *               two decimal places
     * @return ReadOnly editPerson
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     * @throws IllegalValueException if {@code amount} that is repaid by the person is more than the debt owed.
     */
    public ReadOnlyPerson deductDebtFromPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException,
            IllegalValueException {
        int index;
        index = persons.getIndexOf(target);

        Person editedPerson = new Person(target);
        double newDebtAmt = target.getDebt().toNumber() - amount.toNumber();

        if (newDebtAmt < 0) {
            throw new IllegalValueException(PaybackCommand.MESSAGE_PAYBACK_FAILURE);
        }

        try {
            Debt newDebt = new Debt(newDebtAmt);
            editedPerson.setDebt(newDebt);
            persons.setPerson(target, editedPerson);
        } catch (DuplicatePersonException dpe) {
            assert false : "There should be no duplicate when updating the debt of a person";
        } catch (IllegalValueException ive) {
            assert false : "New debt amount should not be invalid since amount and debt field in target have "
                    + "been validated";
        }

        return persons.getReadOnlyPerson(index);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Authenticates user
     * @throws UserNotFoundException if username and password does not match those in the user preference file
     * @throws IllegalValueException if username and password does not meet username and password requirements
     */
    public void authenticateUser(Username username, Password password) throws UserNotFoundException,
            IllegalValueException {
        Username fileUsername = new Username(getUsernameFromUserPref());
        if (fileUsername.equals(username) && checkAgainstPasswordFromUserPref(password.toString(),
                userPrefs.getPasswordSalt())) {
            raise(new LoginAppRequestEvent(true));
        } else {
            raise(new LoginAppRequestEvent(false));
            throw new UserNotFoundException();
        }
    }

    /**
     * Logs user out
     */
    public void logout() {
        raise(new LoginAppRequestEvent(false));
    }

    public String getUsernameFromUserPref() {
        return userPrefs.getAdminUsername();
    }

    public String getPasswordFromUserPref() {
        return userPrefs.getAdminPassword();
    }

    /**
     * Checks the entered password against the password stored in {@code UserPrefs} class
     * @param currPassword password entered by user
     * @return true if the hash generated from the entered password matches the hashed password stored
     * in {@code UserPrefs}
     */
    public boolean checkAgainstPasswordFromUserPref(String currPassword, byte[] salt) {
        String hashedPassword = getSha512SecurePassword(currPassword, salt);
        return hashedPassword.equals(userPrefs.getAdminPassword());
    }

    /**
     * Increase the debt of a person by the amount indicated
     * @param target person in the address book who borrowed more money
     * @param amount amount that the person borrowed. Must be either a positive integer or positive number with
     *               two decimal places
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    @Override
    public void addDebtToPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException {
        addressBook.addDebtToPerson(target, amount);
        indicateAddressBookChanged();
    }

    /**
     * Decrease the debt of a person by the amount indicated
     * @param target person in the address book who paid back some money
     * @param amount amount that the person paid back. Must be either a positive integer or positive number with
     *               two decimal places
     * @return repayingPerson
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     * @throws IllegalValueException if {@code amount} that is repaid by the person is more than the debt owed.
     */
    @Override
    public ReadOnlyPerson deductDebtFromPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException,
            IllegalValueException {
        ReadOnlyPerson repayingPerson = addressBook.deductDebtFromPerson(target, amount);
        indicateAddressBookChanged();
        return repayingPerson;
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Sets total debt of a person to the given Debt.
     * @param totalDebt must not be null and cannot be less than current debt
     */
    public void setTotalDebt(Debt totalDebt) throws IllegalValueException {
        requireNonNull(totalDebt);
        if (totalDebt.toNumber() < debt.get().toNumber()) {
            throw new IllegalValueException(MESSAGE_INVALID_TOTAL_DEBT);
        }
        this.totalDebt.set(totalDebt);
    }

    @Override
    public ObjectProperty<Debt> totalDebtProperty() {
        return totalDebt;
    }

    @Override
    public Debt getTotalDebt() {
        return totalDebt.get();
    }
```
###### \java\seedu\address\model\person\PersonContainsTagPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class PersonContainsTagPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> tagKeywords;

    public PersonContainsTagPredicate(List<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
    }

    /**
     * Evaluates this predicate on the given person.
     *
     * @return {@code true} if the person matches the predicate,
     * otherwise {@code false}
     */
    @Override
    public boolean test(ReadOnlyPerson person) {
        Set<Tag> tagList = person.getTags();
        for (Tag tag : tagList) {
            if (tagKeywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.tagKeywords.equals(((PersonContainsTagPredicate) other).tagKeywords)); // state check
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java

    /**
     * Masks password starting from second whitespace(if it exists) until the end of input
     */
    private void handlePasswordMasking() {
        String currentInput = commandTextField.getText();
        numOfSpaces = getNumOfSpaces(currentInput);
        prevInputLength = inputLength;
        inputLength = commandTextField.getLength();
        indexOfFirstWhitespace = currentInput.indexOf(" ");
        currentMaskFromIndex = currentInput.indexOf(" ", indexOfFirstWhitespace + 1) + 1;

        // update index indicating where to start masking from if user deletes and re-enters password
        if (maskFromIndex == 0) {
            maskFromIndex = currentMaskFromIndex;
        }
        // handles the case where user backspaces and change password input from current caret location to
        // before second whitespace
        if (!passwordFromInput.isEmpty() && inputLength < prevInputLength) {
            handleBackspaceEvent();
        }
        // user's caret is before the second whitespace backspace or after second whitespace
        // initialise fields if password is keyed in then deleted
        if (passwordFromInput.isEmpty() && prevInputLength != 0) {
            initialiseVariablesUsedInMasking();
        }
        // starts masking password after the second whitespace and prevent the reading of the asterisk after replacing
        // a character in the command box text field with an asterisk
        if (numOfSpaces >= 2 && currentInput.charAt(currentInput.length() - 1) != ' '
                && currentInput.charAt(currentInput.length() - 1) != BLACK_CIRCLE) {
            maskPasswordInput(currentInput);

        }
    }

    /**
     * Initialise variables that are used in password masking
     */
    private void initialiseVariablesUsedInMasking() {
        passwordFromInput = "";
        maskFromIndex = 0;
        inputLength = 0;
        prevInputLength = 0;
    }

    /**
     * Mask text field from second whitespace onwards
     * @param currentInput
     */
    private void maskPasswordInput(String currentInput) {
        // if user enters a new character, mask it with an asterisk
        if (currentMaskFromIndex <= maskFromIndex && maskFromIndex <= inputLength) {
            passwordFromInput += commandTextField.getText(maskFromIndex, inputLength);
            commandTextField.replaceText(maskFromIndex, currentInput.length(), Character.toString(BLACK_CIRCLE));
            maskFromIndex++;
        }
    }

    /**
     * Updates {@code passwordFromInput} field appropriately when user backspaces
     */
    private void handleBackspaceEvent() {
        if (getNumOfSpaces(commandTextField.getText()) < 2) {
            initialiseVariablesUsedInMasking();
        } else {
            passwordFromInput = passwordFromInput.substring(0, inputLength - currentMaskFromIndex);
            maskFromIndex--;
        }
    }

    /**
     * Returns an integer that represents the number of spaces in a given string
     */
    private int getNumOfSpaces(String currentInput) {
        int count = 0;
        for (int i = 0; i < currentInput.length(); i++) {
            if (currentInput.charAt(i) == ' ') {
                count++;
            }
        }
        return count;
    }
```
###### \java\seedu\address\ui\DebtRepaymentProgressBar.java
``` java
/**
 * Displays debt repayment progress bar for each person
 */
public class DebtRepaymentProgressBar extends UiPart<Region> {
    private static final String FXML = "DebtRepaymentProgressBar.fxml";
    private static final String COMPLETED_REPAYMENT_MESSAGE = "Completed";
    private static final String NO_DEADLINE_REPAYMENT_MESSAGE = "No deadline set";
    private Double totalDebt;
    private Double repaid;
    private Double ratio;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label percentage;
    @FXML
    private Label repaymentInfo;

    public DebtRepaymentProgressBar(ReadOnlyPerson person) {
        super(FXML);

        totalDebt = person.getTotalDebt().toNumber();
        repaid = totalDebt - person.getDebt().toNumber();
        ratio = repaid / totalDebt;
        progressBar.setProgress(ratio);
        repaymentInfo.textProperty().bind(Bindings.convert(getRepaymentStatus(person, ratio)));
        setRepaymentProgressInfo(person);
        percentage.textProperty().bind(Bindings.convert(getPercentage(ratio)));
        progressBar.getStyleClass().clear();
        progressBar.getStyleClass().add("progress-bar");
        registerAsAnEventHandler(this);
    }

    /**
     * Styles repayment
     */
    private void setRepaymentProgressInfo(ReadOnlyPerson person) {
        String repaymentStatus = getRepaymentStatus(person, ratio).getValue();
        if (ratio == 1.0) {
            repaymentInfo.setId("completedText");
        } else if (repaymentStatus.equals(NO_DEADLINE_REPAYMENT_MESSAGE)) {
            repaymentInfo.setId("noDeadlineText");
        } else {
            repaymentInfo.setId("repaymentInfoText");
        }
    }

    /**
     * Returns a {@code ObservableValue<String>} to bind to {@code percentage} property
     */
    private ObservableValue<String> getPercentage(Double ratio) {
        String percentage = String.format("%.2f", ratio * 100);
        return new SimpleObjectProperty<>(percentage.concat("%"));
    }

    /**
     * Returns a {@code ObservableValue<String>} to bind to {@code repaymentInfo} property
     */
    private ObservableValue<String> getRepaymentStatus(ReadOnlyPerson person, double percentage) {
        Deadline deadline = person.getDeadline();
        if (!deadline.toString().equals(NO_DEADLINE_SET)) {
            String day = deadline.getDay();
            String month = deadline.getMonth();
            String year = deadline.getYear();
            String deadlineFormatted = year + month + day;

            LocalDate deadlineDate = LocalDate.parse(deadlineFormatted, DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate today = LocalDate.now();
            return new SimpleObjectProperty<>(Long.toString(
                    ChronoUnit.DAYS.between(today, deadlineDate)) + " days left to repay debt");
        } else if (percentage == 1.0) {
            return new SimpleObjectProperty<>(COMPLETED_REPAYMENT_MESSAGE);
        }
        return new SimpleObjectProperty<>(NO_DEADLINE_REPAYMENT_MESSAGE);
    }
}
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
    /**
     * Resets the debt repayment progress bar
     * @param person the person whose person card is selected in the address book
     */
    private void resetDebtRepaymentProgressBar(ReadOnlyPerson person) {
        debtRepaymentProgressBar = new DebtRepaymentProgressBar(person);
        progressBarPlaceholder.getChildren().clear();
        progressBarPlaceholder.getChildren().add(debtRepaymentProgressBar.getRoot());
    }

```
###### \java\seedu\address\ui\LoginView.java
``` java
/**
 * Displays username and password fields
 */
public class LoginView extends UiPart<Region> {
    private static final String FXML = "LoginView.fxml";
    private static final Logger logger = LogsCenter.getLogger(LoginView.class);

    private final Logic logic;
    private ObjectProperty<Username> username;
    private ObjectProperty<Password> password;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public LoginView(Logic logic) {
        super(FXML);
        this.logic = logic;
        logger.info("Showing login view...");
        usernameField.textProperty().addListener((unused1, unused2, unused3) -> {
        });
        passwordField.textProperty().addListener((unused1, unused2, unused3) -> {
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleLoginInputChanged() {
        String usernameText = usernameField.getText();
        String passwordText = passwordField.getText();
        if (!usernameText.isEmpty() && !passwordText.isEmpty()) {
            // process login inputs

            try {
                CommandResult commandResult;
                commandResult = logic.execute(LoginCommand.COMMAND_WORD + " " + usernameText
                        + " " + passwordText);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
            } catch (CommandException | ParseException e) {
                raise(new NewResultAvailableEvent(e.getMessage(), true));
            }
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleBackToCommandView() {
        EventsCenter.getInstance().post(new ChangeToCommandBoxView());
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Fills up all the placeholders of window once the app starts up.
     * Should only display welcome page without contacts.
     */
    void fillInnerPartsForStartUp() {
        Platform.runLater(() -> {
            startUpPanel = new StartUpPanel(primaryStage);
            infoPanelPlaceholder.getChildren().clear();
            infoPanelPlaceholder.getChildren().add(startUpPanel.getRoot());

            personListStartUpPanel = new PersonListStartUpPanel();
            personListPanelPlaceholder.getChildren().clear();
            personListPanelPlaceholder.getChildren().add(personListStartUpPanel.getRoot());

            ResultDisplay resultDisplay = new ResultDisplay();
            resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

            commandBox = new CommandBox(logic);
            commandBoxPlaceholder.getChildren().clear();
            commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        });
    }

    /**
     * Fills up all the placeholders command box.
     */
    void fillInnerPartsForCommandBox() {
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Changes from command box to login view with text fields for username and password
     */
    public void fillCommandBoxWithLoginFields() {
        LoginView loginView = new LoginView(logic);
        commandBoxPlaceholder.getChildren().clear();
        commandBoxPlaceholder.getChildren().add(loginView.getRoot());
    }

```
###### \java\seedu\address\ui\PersonListStartUpPanel.java
``` java
/**
 * Empty panel to be displayed instead of PersonListPanel before login.
 * Users who have not logged in successfully should not be able to view the contacts in the address book.
 */
public class PersonListStartUpPanel extends UiPart<Region> {

    public static final String PERSON_LIST_START_UP_PANEL_ID = "#personListStartUpPane";

    private static final String FXML = "PersonListStartUpPanel.fxml";

    public PersonListStartUpPanel() {
        super(FXML);
    }
}
```
###### \java\seedu\address\ui\StartUpPanel.java
``` java
/**
 * The Start Up Panel will be loaded in place of the Browser Panel
 */
public class StartUpPanel extends UiPart<Region> {
    private static final String FXML = "StartUpPanel.fxml";

    @FXML
    private ImageView welcome;

    private final Logger logger = LogsCenter.getLogger(this.getClass());


    public StartUpPanel(Stage stage) {
        super(FXML);
        logger.info("Loading welcome page...");
        welcome.fitHeightProperty().bind(stage.heightProperty());
    }

}
```
###### \java\seedu\address\ui\UiManager.java
``` java
    /**
     * Handles login event.
     * Displays contacts in address book if login is successful
     */
    @Subscribe
    public void handleLoginAppRequestEvent(LoginAppRequestEvent event) {
        // login is successful
        if (event.getLoginStatus() == true) {
            logger.info("Login successful");
            LoginCommand.setLoginStatus(true);
            //show address book
            Platform.runLater(() -> mainWindow.fillInnerParts());
        }
    }

    /**
     * Handles login event.
     * Displays contacts in address book if login is successful
     */
    @Subscribe
    public void handleChangeToLoginViewEvent(ChangeToLoginViewEvent event) {
        // user wants to login
        Platform.runLater(() -> mainWindow.fillCommandBoxWithLoginFields());
    }

    /**
     * Handles logout event.
     * Displays login page when user logs out
     */
    @Subscribe
    public void handleLogoutAppRequestEvent(LogoutAppRequestEvent event) {
        // logout is successful
        if (event.getLogoutStatus() == true) {
            logger.info("Logout successful");
            LoginCommand.setLoginStatus(false);
            //show login page
            mainWindow.fillInnerPartsForStartUp();
        }
    }

    /**
     * Changes from login view to command box
     */
    @Subscribe
    private void handleBackToCommandViewRequest(ChangeToCommandBoxView event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.fillInnerPartsForCommandBox();
    }

```
###### \resources\view\DebtRepaymentProgressBar.fxml
``` fxml

<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ProgressBar fx:id="progressBar" prefHeight="24.0" prefWidth="160.0" progress="0.0" StackPane.alignment="CENTER_LEFT" />
      <Label fx:id="percentage" text="\$percentage" textAlignment="CENTER" StackPane.alignment="CENTER_LEFT">
         <font>
            <Font size="9.5" />
         </font>
         <StackPane.margin>
            <Insets left="60.0" />
         </StackPane.margin>
      </Label>
      <Label fx:id="repaymentInfo" text="\\$repaymentInfo" StackPane.alignment="CENTER_LEFT">
         <StackPane.margin>
            <Insets left="170.0" />
         </StackPane.margin>
         <font>
            <Font size="8.0" />
         </font>
      </Label>
   </children>
</StackPane>
```
