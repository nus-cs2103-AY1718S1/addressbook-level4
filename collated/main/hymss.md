# hymss
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
        }

```
###### /java/seedu/address/logic/commands/ListBirthdayCommand.java
``` java
/**
 * Lists all persons in Contags whose birthday is the current day to the user.
 */
public class ListBirthdayCommand extends Command {

    public static final String COMMAND_WORD = "listbirthday";

    public static final String MESSAGE_SUCCESS = "Listed all contacts whose birthday is today.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the persons whose birthday is the current day.\n"
            + "Parameters: KEYWORD\n"
            + "Example for birthday list: " + COMMAND_WORD;

    private BirthdayChecker checker = new BirthdayChecker();

    public ListBirthdayCommand (){

    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(checker);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/parser/MailCommandParser.java
``` java
/**
 * Parses input arguments and creates a new MailCommand object
 */
public class MailCommandParser implements Parser<MailCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MailCommand
     * and returns an MailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public MailCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MAIL_RECEPIENT, PREFIX_MAIL_TITLE, PREFIX_MAIL_MESSAGE);
        try {
            Index index = ParserUtil.parseIndex(args);
            String title = String.join("", argMultimap.getAllValues(PREFIX_MAIL_TITLE)).replace(" ", "%20");
            String message = String.join("", argMultimap.getAllValues(PREFIX_MAIL_MESSAGE)).replace(" ", "%20");

            return new MailCommand(index, title, message);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MailCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static String[] parseMailToCommand(List<String> name) throws IllegalValueException {
        requireNonNull(name);
        String trimmed = String.join(" ", name).trim();
        String[] newTrimmed = trimmed.split("\\s+");
        return newTrimmed;
    }
}
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Updates the filter of the filtered list of people to mail to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    String updateMailRecipientList(Predicate<ReadOnlyPerson> predicate);

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public String updateMailRecipientList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredMails.setPredicate(predicate);
        List<String> validPeopleList = new ArrayList<>();
        for (ReadOnlyPerson person : filteredPersons) {
            if (person.getEmail() != null && !person.getEmail().value.equalsIgnoreCase("INVALID_EMAIL@EXAMPLE.COM")
                 && !validPeopleList.contains(person.getEmail().value)) {
                validPeopleList.add(person.getEmail().value);
            }
        }
        return String.join(",", validPeopleList);
    }

```
###### /java/seedu/address/model/person/Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthdays can only contain numbers and forward slashes, and it should not be blank."
                + " The birthday must be in the form dd/mm/yy or dd/mm/yyy";

    /*
     * The first character of the birthday must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3"
            + "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$"
            + "|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = birthday;
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
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
###### /java/seedu/address/model/person/BirthdayChecker.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday} matches current date.
 */
public class BirthdayChecker implements Predicate<ReadOnlyPerson> {

    public BirthdayChecker(){

    }

    /**
    * Checks if a contact's birthday falls on the current day
    * @param person
    * @return boolean
    * * @throws ParseException
    */

    public boolean birthdayList(ReadOnlyPerson person) throws ParseException {
        String birthday = person.getBirthday().toString();
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (((calendar.get(Calendar.MONTH)) == Calendar.getInstance().get(Calendar.MONTH))
                && ((calendar.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))));
    }
    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean index = false;
        try {
            index = birthdayList(person);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdayChecker); // instanceof handles nulls
    }
}
```
###### /java/seedu/address/model/person/Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }

```
###### /java/seedu/address/storage/AddressBookStorage.java
``` java
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.saveAddressBook(addressBook,
                addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }

```
###### /java/seedu/address/storage/XmlAddressBookStorage.java
``` java
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath.concat("backup.fxml"));
    }

}
```
