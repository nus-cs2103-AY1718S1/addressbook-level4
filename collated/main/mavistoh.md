# mavistoh
###### \java\seedu\address\commons\core\index\Index.java
``` java
    /**
     * Creates a new {@code Index[]} using a one-based index.
     */
    public static Index[] arrayFromOneBased(int[] oneBasedIndex) {
        Index[] arrayIndex = new Index[oneBasedIndex.length];
        for (int i = 0; i < oneBasedIndex.length; i++) {
            arrayIndex[i] = new Index(oneBasedIndex[i] - 1);
        }
        return arrayIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Index // instanceof handles nulls
                && this.zeroBasedIndex == ((Index) other).zeroBasedIndex); // state check
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be positive integers in ascending order, separated by a comma)\n"
            + "Example: " + COMMAND_WORD + " 1, 4";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: ";

    private final Index[] targetIndexes;

    public DeleteCommand(Index... targetIndex) {
        this.targetIndexes = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        if (targetIndexes.length > 1) {
            for (int i = 1; i < targetIndexes.length; i++) {
                if (targetIndexes[i].getZeroBased() < targetIndexes[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_ORDER_PERSONS_INDEX);
                } else if (targetIndexes[i].getZeroBased() == targetIndexes[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_REPEATED_INDEXES);
                }
            }
        }

        ReadOnlyPerson[] personsToDelete = new ReadOnlyPerson[targetIndexes.length];
        ReadOnlyPerson personToDelete;
        String[] personDeleteMessage = new String[targetIndexes.length];
        StringBuilder deleteMessage = new StringBuilder();

        for (int i = (targetIndexes.length - 1); i >= 0; i--) {
            int target = targetIndexes[i].getZeroBased();
            personToDelete = lastShownList.get(target);
            personsToDelete[i] = personToDelete;
            personDeleteMessage[i] = MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;
        }

        try {
            model.deletePerson(personsToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        for (String message : personDeleteMessage) {
            deleteMessage.append(message);
            deleteMessage.append("\n");
        }

        return new CommandResult(deleteMessage.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && Arrays.equals(this.targetIndexes, ((DeleteCommand) other).targetIndexes)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        Phone phone;
        Birthday birthday;
        Email email;
        Address address;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_BIRTHDAY, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Optional<Phone> checkPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            if (!checkPhone.isPresent()) {
                phone = new Phone(null);
            } else {
                phone = checkPhone.get();
            }
            Optional<Birthday> checkBirthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY));
            if (!checkBirthday.isPresent()) {
                birthday = new Birthday(null);
            } else {
                birthday = checkBirthday.get();
            }
            Optional<Email> checkEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            if (!checkEmail.isPresent()) {
                email = new Email(null);
            } else {
                email = checkEmail.get();
            }
            Optional<Address> checkAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            if (!checkAddress.isPresent()) {
                address = new Address(null);
            } else {
                address = checkAddress.get();
            }
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, birthday, email, address, false,  tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index[] index = ParserUtil.parseDeleteIndex(args);
            return new DeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code oneBasedIndex} into an {@code Index[]} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseDeleteIndex(String oneBasedIndex) throws IllegalValueException {
        String[] parts = oneBasedIndex.split(",");
        String[] trimmedIndex = new String[parts.length];
        int[] trimmedIntIndex = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            trimmedIndex[i] = parts[i].trim();
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex[i])) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
        }

        for (int i = 0; i < trimmedIndex.length; i++) {
            trimmedIntIndex[i] = Integer.parseInt(trimmedIndex[i]);
        }

        return Index.arrayFromOneBased(trimmedIntIndex);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Address(String address) throws IllegalValueException {
        if (address == null) {
            this.value = ADDRESS_EMPTY;
        } else {
            if (!isValidAddress(address)) {
                throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
            }
            this.value = address;
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX) || test.matches(ADDRESS_EMPTY);
    }
```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday must be in the format DD.MM.YYYY, DD/MM/YYYY or DD-MM-YYYY";
    //public static final String BIRTHDAY_VALIDATION_REGEX = "(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)
    // (\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]
    // \\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.
    // )(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";
    //public static final String BIRTHDAY_VALIDATION_REGEX = "(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)
    // [\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04
    // |08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)";
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?=\\d{2}([-.,\\/])\\d{2}\\1\\d{4}$)"
            + "(?:0[1-9]|1\\d|[2][0-8]|29(?!.02.(?!(?!(?:[02468][1-35-79]|[13579][0-13-57-9])00)\\d{2}"
            + "(?:[02468][048]|[13579][26])))|30(?!.02)|31(?=.(?:0[13578]|10|12))).(?:0[1-9]|1[012]).\\d{4}$";
    public static final String BIRTHDAY_EMPTY = "-";
    public final String value;


    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        if (birthday == null) {
            this.value = BIRTHDAY_EMPTY;
        } else {
            String trimmedBirthday = birthday.trim();
            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.matches(BIRTHDAY_EMPTY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Email.java
``` java
    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Email(String email) throws IllegalValueException {
        if (EMAIL_EMPTY.equals(email) || email == null) {
            this.value = EMAIL_EMPTY;
        } else {
            String trimmedEmail = email.trim();
            if (!isValidEmail(trimmedEmail)) {
                throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
            }
            this.value = trimmedEmail;
            String[] splitEmail = trimmedEmail.split("@");
            userName = splitEmail[0];
            domainName = splitEmail[1];
        }
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX) || test.matches(EMAIL_EMPTY);
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(phone);
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setBirthday(Birthday birthday) {
        this.birthday.set(birthday);
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }

    public void setEmail(Email email) {
        this.email.set(email);
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

```
###### \java\seedu\address\model\person\Phone.java
``` java
    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Phone(String phone) throws IllegalValueException {
        if (phone == null) {
            this.value = PHONE_EMPTY;
        } else {
            String trimmedPhone = phone.trim();
            if (!isValidPhone(trimmedPhone)) {
                throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
            }
            this.value = trimmedPhone;
        }
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX) || test.matches(PHONE_EMPTY);
    }
```
