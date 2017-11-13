# CindyTsai1
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose information contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: ["
            + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_BIRTHDAY + "21051994 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_NOT_FOUND = "At least one field to find must be provided.";
    private ArrayList<String> predicate;

    public FindCommand(ArrayList<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        List<String> predicateList = new ArrayList<>();

        for (int i = 0; i < predicate.size() - 1; i++) {
            String predicates = predicate.get(i);

            if (predicates.equals(PREFIX_NAME.getPrefix())) {
                ArrayList<String> nameList = findName(predicate.get(i + 1));
                for (int j = 0; j < nameList.size(); j++) {
                    if (!predicateList.contains(nameList.get(j))) {
                        predicateList.add(nameList.get(j));
                    }
                }
            }

            if (predicates.equals(PREFIX_PHONE.getPrefix())) {
                ArrayList<String> phoneList = findPhone(predicate.get(i + 1));
                for (int j = 0; j < phoneList.size(); j++) {
                    if (!predicateList.contains(phoneList.get(j))) {
                        predicateList.add(phoneList.get(j));
                    }
                }
            }

            if (predicates.equals(PREFIX_ADDRESS.getPrefix())) {
                ArrayList<String> addressList = findAddress(predicate.get(i + 1));
                for (int j = 0; j < addressList.size(); j++) {
                    if (!predicateList.contains(addressList.get(j))) {
                        predicateList.add(addressList.get(j));
                    }
                }
            }

            if (predicates.equals(PREFIX_EMAIL.getPrefix())) {
                ArrayList<String> emailList = findEmail(predicate.get(i + 1));
                for (int j = 0; j < emailList.size(); j++) {
                    if (!predicateList.contains(emailList.get(j))) {
                        predicateList.add(emailList.get(j));
                    }
                }
            }

            if (predicates.equals(PREFIX_TAG.getPrefix())) {
                ArrayList<String> tagList = findTags(predicate.get(i + 1));
                for (int j = 0; j < tagList.size(); j++) {
                    if (!predicateList.contains(tagList.get(j))) {
                        predicateList.add(tagList.get(j));
                    }
                }
            }

            if (predicates.equals(PREFIX_BIRTHDAY.getPrefix())) {
                ArrayList<String> birthdayList = findBirthday(predicate.get(i + 1));
                for (int j = 0; j < birthdayList.size(); j++) {
                    if (!predicateList.contains(birthdayList.get(j))) {
                        predicateList.add(birthdayList.get(j));
                    }
                }
            }
        }

        NameContainsKeywordsPredicate predicates = new NameContainsKeywordsPredicate(predicateList);
        model.updateFilteredPersonList(predicates);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    /**
     * Search for persons that contain the {@String keyword} in their name
     * @param name
     */
    public ArrayList<String> findName(String name) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] nameKeyword = name.split(" ");
        ArrayList<String> nameList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : nameKeyword) {
                String names = person.getName().toString().toLowerCase();
                keyword = keyword.toLowerCase();
                if (names.contains(keyword)) {
                    nameList.add(person.getName().toString(0));
                }
            }
        }
        return nameList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their phone number
     * @param phone
     */
    public ArrayList<String> findPhone(String phone) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] phoneKeyword = phone.split(" ");
        ArrayList<String> phoneList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : phoneKeyword) {
                String phones = person.getPhone().toString();
                if (phones.contains(keyword)) {
                    phoneList.add(person.getName().toString(0));
                }
            }
        }
        return phoneList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their email
     * @param email
     */
    public ArrayList<String> findEmail(String email) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] emailKeyword = email.split(" ");
        ArrayList<String> emailList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : emailKeyword) {
                String emails = person.getEmail().toString().toLowerCase();
                keyword = keyword.toLowerCase();
                if (emails.contains(keyword)) {
                    emailList.add(person.getName().toString(0));
                }
            }
        }
        return emailList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their address
     * @param address
     */
    public ArrayList<String> findAddress(String address) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] addressKeyword = address.split(" ");
        ArrayList<String> addressList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : addressKeyword) {
                String addresses = person.getAddress().toString().toLowerCase();
                keyword = keyword.toLowerCase();
                if (addresses.contains(keyword)) {
                    addressList.add(person.getName().toString(0));
                }
            }
        }
        return addressList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their tag
     * @param tags
     */
    public ArrayList<String> findTags(String tags) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] tagKeyword = tags.split(" ");
        ArrayList<String> tagList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : tagKeyword) {
                for (Tag tagging : person.getTags()) {
                    String tag1 = tagging.toString().substring(1, tagging.toString().length()).toLowerCase();
                    keyword = keyword.toLowerCase();
                    if (tag1.contains(keyword)) {
                        tagList.add(person.getName().toString(0));
                    }
                }
            }
        }
        return tagList;
    }

    /**
     * Search for persons that contain the {@String keyword} in their birthday
     * @param birthday
     */
    public ArrayList<String> findBirthday(String birthday) {
        ObservableList<ReadOnlyPerson> personList = model.getAddressBook().getPersonList();
        String[] birthdayKeyword = birthday.split(" ");
        ArrayList<String> birthdayList = new ArrayList<>();
        for (ReadOnlyPerson person : personList) {
            for (String keyword : birthdayKeyword) {
                String birthdays = person.getBirthday().toString().substring(2, 4);
                if (birthdays.equals(keyword)) {
                    birthdayList.add(person.getName().toString(0));
                }
            }
        }
        return birthdayList;
    }
}
```
###### \java\seedu\address\logic\commands\SuggestCommand.java
``` java
/**
 * Suggests a correct command
 */
public class SuggestCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Do you mean %1$s?";

    private final String possibleCommand;

    /**
     * Creates an SuggestCommand to suggest the specified {@code String} for command
     */
    public SuggestCommand(String command) {
        this.possibleCommand = command;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(String.format(MESSAGE_SUCCESS, possibleCommand));
    }
}
```
###### \java\seedu\address\logic\commands\UniqueCommandList.java
``` java
/**
 * A list of all command words, including alias and secondary word
 */
public class UniqueCommandList {
    private static ArrayList<String> commandList;
    private static TreeSet<String> possibleCommandList;

    public static ArrayList<String> getCommandList() {
        commandList = new ArrayList<>();
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(AddCommand.COMMAND_ALIAS);
        commandList.add(AddCommand.COMMAND_SECONDARY);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_ALIAS);
        commandList.add(ClearCommand.COMMAND_SECONDARY);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_ALIAS);
        commandList.add(DeleteCommand.COMMAND_SECONDARY);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_ALIAS);
        commandList.add(EditCommand.COMMAND_SECONDARY_ONE);
        commandList.add(EditCommand.COMMAND_SECONDARY_TWO);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_ALIAS);
        commandList.add(ExitCommand.COMMAND_SECONDARY);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_ALIAS);
        commandList.add(FindCommand.COMMAND_SECONDARY);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_ALIAS);
        commandList.add(HistoryCommand.COMMAND_SECONDARY);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_ALIAS);
        commandList.add(ListCommand.COMMAND_SECONDARY_ONE);
        commandList.add(ListCommand.COMMAND_SECONDARY_TWO);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_ALIAS);
        commandList.add(RemarkCommand.COMMAND_WORD);
        commandList.add(RemarkCommand.COMMAND_ALIAS);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_ALIAS);
        commandList.add(SelectCommand.COMMAND_SECONDARY);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_ALIAS);
        return commandList;
    }

    public static TreeSet<String> getPossibleCommandList(String command) {
        possibleCommandList = new TreeSet<>();

        // Swapping i with i+1
        for (int i = 1; i < command.length() - 1; i++) {
            possibleCommandList.add(command.substring(0, i) + command.charAt(i + 1)
                    + command.charAt(i) + command.substring(i + 2));
        }

        // deleting one char, skipping i
        for (int i = 0; i < command.length(); i++) {
            possibleCommandList.add(command.substring(0, i) + command.substring(i + 1));
        }

        // inserting one char
        for (int i = 0; i < command.length() + 1; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                possibleCommandList.add(command.substring(0, i) + j + command.substring(i));
                // replacing one char
                if (i < command.length()) {
                    possibleCommandList.add(command.substring(0, i) + j + command.substring(i + 1));
                }
            }
        }
        return possibleCommandList;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_MATRIC_NO,
                        PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_BIRTHDAY, PREFIX_TIMETABLE);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Gender gender = (arePrefixesPresent(argMultimap, PREFIX_GENDER))
                    ? ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER)).get() : new Gender("");
            MatricNo matricNo = (arePrefixesPresent(argMultimap, PREFIX_MATRIC_NO))
                    ? ParserUtil.parseMatricNo(argMultimap.getValue(PREFIX_MATRIC_NO)).get() : new MatricNo("");
            Phone phone = (arePrefixesPresent(argMultimap, PREFIX_PHONE))
                    ? ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get() : new Phone("");
            Email email = (arePrefixesPresent(argMultimap, PREFIX_EMAIL))
                    ? ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get() : new Email("");
            Address address = (arePrefixesPresent(argMultimap, PREFIX_ADDRESS))
                    ? ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get() : new Address("");
            Timetable timetable = (arePrefixesPresent(argMultimap, PREFIX_TIMETABLE))
                    ? ParserUtil.parseTimetable(argMultimap.getValue(PREFIX_TIMETABLE)).get() : new Timetable("");
            Remark remark = new Remark("");
            PhotoPath photoPath = new PhotoPath(DEFAULT_PHOTO_PATH);
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Birthday birthday = (arePrefixesPresent(argMultimap, PREFIX_BIRTHDAY))
                    ? ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY)).get() : new Birthday("");

            ReadOnlyPerson person = new Person(name, gender, matricNo, phone, email, address, timetable,
                    remark, photoPath, tagList, birthday);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        ArgumentMultimap argsMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_TAG, PREFIX_BIRTHDAY);

        if (args.isEmpty() || args.substring(1, 2).equals("g") || args.substring(1, 2).equals("m")
                || args.substring(1, 3).equals("tt") || !args.substring(2, 3).equals("/")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArrayList<String> predicate = new ArrayList<String>();

        String nameList = new String();
        String phoneList = new String();
        String emailList = new String();
        String addressList = new String();
        String tagList = new String();
        String birthdayList = new String();

        if (argsMultimap.getValue(PREFIX_NAME).isPresent()) {
            nameList = argsMultimap.getValue(PREFIX_NAME).get();
            predicate.add(PREFIX_NAME.getPrefix());
            predicate.add(nameList);
        }

        if (argsMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phoneList = argsMultimap.getValue(PREFIX_PHONE).get();
            predicate.add(PREFIX_PHONE.getPrefix());
            predicate.add(phoneList);
        }

        if (argsMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            emailList = argsMultimap.getValue(PREFIX_EMAIL).get();
            predicate.add(PREFIX_EMAIL.getPrefix());
            predicate.add(emailList);
        }

        if (argsMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            addressList = argsMultimap.getValue(PREFIX_ADDRESS).get();
            predicate.add(PREFIX_ADDRESS.getPrefix());
            predicate.add(addressList);
        }

        if (argsMultimap.getValue(PREFIX_TAG).isPresent()) {
            tagList = argsMultimap.getValue(PREFIX_TAG).get();
            predicate.add(PREFIX_TAG.getPrefix());
            predicate.add(tagList);
        }

        if (argsMultimap.getValue(PREFIX_BIRTHDAY).isPresent()) {
            birthdayList = argsMultimap.getValue(PREFIX_BIRTHDAY).get();
            predicate.add(PREFIX_BIRTHDAY.getPrefix());
            predicate.add(birthdayList);
        }

        if (nameList.isEmpty() && phoneList.isEmpty() && emailList.isEmpty() && addressList.isEmpty()
                && tagList.isEmpty() && birthdayList.isEmpty()) {
            throw new ParseException(FindCommand.MESSAGE_NOT_FOUND);
        }
        return new FindCommand(predicate);

    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code Optional<int> birthday} into a {@code HashMap<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(new Birthday(birthday.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\SuggestCommandParser.java
``` java
/**
 * Suggest user input command
 */
public class SuggestCommandParser implements Parser<SuggestCommand> {

    private UniqueCommandList list;

    /**
     * Parses the given {@code String} of commandWord in the context of the SuggestCommand
     * and returns an SuggestCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format or if a suggested spelling is found
     */
    public SuggestCommand parse (String commandWord) throws ParseException {
        list = new UniqueCommandList();
        TreeSet<String> possibleCommand = list.getPossibleCommandList(commandWord);
        ArrayList<String> commandList = list.getCommandList();

        for (String command: possibleCommand) {
            if (commandList.contains(command)) {
                return new SuggestCommand(command);
            }
        }
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }
}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person's birthday should be in the format of DDMMYYYY, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d{2}\\d{2}\\d{4}";

    public static final String DATE_FORMAT = "ddMMyyyy";

    public final String date;

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
        this.date = birthday;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        if (test.equals("")) {
            return true;
        }
        if (test.matches(BIRTHDAY_VALIDATION_REGEX)) {
            try {
                DateFormat df = new SimpleDateFormat(DATE_FORMAT);
                df.setLenient(false);
                df.parse(test);
                return true;
            } catch (ParseException pe) {
                return false;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.date.equals(((Birthday) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "_backup.xml");
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    private void setDefaultStyle() {
        resultDisplay.getStyleClass().remove("error");
    }

    private void setErrorStyle() {
        ObservableList<String> style = resultDisplay.getStyleClass();
        if (style.contains("error")) {
            return;
        }

        style.add("error");
    }

}
```
