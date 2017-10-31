# Juxarius
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        DateOfBirth updatedDateOfBirth = editPersonDescriptor.getDateOfBirth().orElse(personToEdit.getDateOfBirth());

        Set<Tag> updatedTags = personToEdit.getTags();

        if (editPersonDescriptor.getTagsToDel().isPresent()) {
            for (Tag tag : editPersonDescriptor.getTagsToDel().get()) {
                if (tag.getTagName().equals("all")) {
                    updatedTags.clear();
                }
            }
            updatedTags.removeAll(editPersonDescriptor.getTagsToDel().get());
        }

        if (editPersonDescriptor.getTags().isPresent()) {
            updatedTags.addAll(editPersonDescriptor.getTags().get());
        }

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedDateOfBirth, updatedTags);
    }
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setTagsToDel(Set<Tag> tagsToDel) {
            this.tagsToDel = tagsToDel;
        }

        public Optional<Set<Tag>> getTagsToDel() {
            return Optional.ofNullable(tagsToDel);
        }
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;

        } catch (EmptyFieldException efe) {
            // index check was bypassed, this checks the index before filling empty prefix
            if (efe.getIndex().getOneBased() > model.getFilteredPersonList().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            commandText = getAutoFilledCommand(commandText, efe.getIndex());
            throw efe;
        } finally {
            history.add(commandText);
        }
    }
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    /**
     * Replaces the given command text with filled command text
     * @param commandText original input command text
     * @param index index of person to edit
     * @return filled command
     */
    private String getAutoFilledCommand(String commandText, Index index) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(index.getZeroBased());
        for (Prefix prefix : PREFIXES_PERSON) {
            String prefixInConcern = prefix.getPrefix();
            if (commandText.contains(prefixInConcern)) {
                String replacementText = prefixInConcern + person.getDetailByPrefix(prefix) + " ";
                commandText = commandText.replaceFirst(prefixInConcern, replacementText);
            }
        }
        if (commandText.contains(PREFIX_TAG.getPrefix())) {
            String formattedTags = PREFIX_TAG.getPrefix()
                    + person.getDetailByPrefix(PREFIX_TAG).replaceAll(" ", " t/") + " ";
            commandText = commandText.replaceFirst(PREFIX_TAG.getPrefix(), formattedTags);
        }
        if (commandText.contains(PREFIX_DELTAG.getPrefix())) {
            String formattedTags = PREFIX_DELTAG.getPrefix()
                    + person.getDetailByPrefix(PREFIX_DELTAG).replaceAll(" ", " t/") + " ";
            commandText = commandText.replaceFirst(PREFIX_DELTAG.getPrefix(), formattedTags);
        }
        return commandText.trim();
    }
```
###### \java\seedu\address\logic\parser\DateParser.java
``` java
/**
 * Parses a string into a LocalDate
 */
public class DateParser {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date input must have at least 2 arguments.";
    public static final String MESSAGE_INVALID_MONTH = "Month input is invalid.";
    public static final String MESSAGE_INVALID_DAY = "Day input is invalid.";
    public static final String MESSAGE_INVALID_YEAR = "Year input is invalid.";

    public static final String[] MONTH_NAME_SHORT = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String[] MONTH_NAME_LONG = {"january", "february", "march",
        "april", "may", "june", "july", "august", "september", "october", "november", "december"};
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    /**
     * Parses the given {@code String} of arguments to produce a LocalDate object. Input string
     * must be in the Day-Month-Year format where the month can be a number or the name
     * and the year can be input in 2-digit or 4-digit format.
     * @throws IllegalValueException if the format is not correct.
     */
    /**
     * Parses input dob string
     */
    public LocalDate parse(String dob) throws IllegalValueException {
        List<String> arguments = Arrays.asList(dob.split("[\\s-/.,]"));
        if (arguments.size() < 2) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        String day = arguments.get(0);
        String month = arguments.get(1);
        String year = arguments.size() > 2 ? arguments.get(2) : String.valueOf(LocalDate.now().getYear());
        return LocalDate.parse(getValidDay(day) + " " + getValidMonth(month) + " " + getValidYear(year),
                DATE_FORMAT);
    }

    /**
     *
     * @param year 2 or 4 digit string
     * @return
     * @throws IllegalValueException
     */
    private String getValidYear(String year) throws IllegalValueException {
        int currYear = LocalDate.now().getYear();
        if (year.length() > 4) {
            year = year.substring(0, 4);
        }
        if (!year.matches("\\d+") || (year.length() != 2 && year.length() != 4)) {
            throw new IllegalValueException(MESSAGE_INVALID_YEAR);
        } else if (year.length() == 2) {
            int iYear = Integer.parseInt(year);
            if (iYear > currYear % 100) {
                return Integer.toString(iYear + (currYear / 100 - 1) * 100);
            } else {
                return Integer.toString(iYear + currYear / 100 * 100);
            }
        } else {
            return year;
        }
    }

    private String getValidDay(String day) throws IllegalValueException {
        if (Integer.parseInt(day) > 31) {
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
        if (day.length() == 1) {
            return "0" + day;
        } else if (day.length() == 2) {
            return day;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
    }

    private String getValidMonth(String month) throws IllegalValueException {
        int iMonth;
        if (month.matches("\\p{Alpha}+")) {
            iMonth = getMonth(month);
        } else {
            iMonth = Integer.parseInt(month);
        }
        if (iMonth > 12 || iMonth < 1) {
            throw new IllegalValueException(MESSAGE_INVALID_MONTH);
        } else {
            return MONTH_NAME_SHORT[iMonth - 1];
        }
    }

    /**
     * finds int month from string month name
     */
    private int getMonth(String monthName) throws IllegalValueException {
        for (int i = 0; i < MONTH_NAME_LONG.length; i++) {
            if (monthName.toLowerCase().equals(MONTH_NAME_LONG[i].toLowerCase())
                    || monthName.toLowerCase().equals(MONTH_NAME_SHORT[i].toLowerCase())) {
                return i + 1;
            }
        }
        throw new IllegalValueException(MESSAGE_INVALID_MONTH);
    }
}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_DOB, PREFIX_TAG, PREFIX_DELTAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).ifPresent(editPersonDescriptor::setName);
            ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).ifPresent(editPersonDescriptor::setPhone);
            ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).ifPresent(editPersonDescriptor::setEmail);
            ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).ifPresent(editPersonDescriptor::setAddress);
            ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB))
                    .ifPresent(editPersonDescriptor::setDateOfBirth);
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
            parseDetagsForEdit(argMultimap.getAllValues(PREFIX_DELTAG)).ifPresent(editPersonDescriptor::setTagsToDel);
        } catch (EmptyFieldException efe) {
            throw new EmptyFieldException(efe, index);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * return an EmptyFieldException which will trigger an autofill
     */
    private Optional<Set<Tag>> parseDetagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        if (tags.size() == 1 && tags.contains("")) {
            throw new EmptyFieldException(PREFIX_DELTAG);
        }
        return Optional.of(ParserUtil.parseTags(tags));
    }

}
```
###### \java\seedu\address\logic\parser\exceptions\EmptyFieldException.java
``` java
/**
 * Signifies that a certain field is empty but the prefix is specified.
 */

public class EmptyFieldException extends ParseException {

    private Prefix emptyFieldPrefix;
    private Index index;
    /**
     * @param message should contain information on the empty field.
     */
    public EmptyFieldException(String message) {
        super(message);
    }
    /**
     * @param emptyFieldPrefix contains the prefix of the field that is empty.
     */
    public EmptyFieldException(Prefix emptyFieldPrefix) {
        super(emptyFieldPrefix.getPrefix() + " field is empty");
        this.emptyFieldPrefix = emptyFieldPrefix;
    }

    /**
     * @param index is the oneBasedIndex of the person in concern
     */
    public EmptyFieldException(EmptyFieldException efe, Index index) {
        super(efe.getMessage());
        this.emptyFieldPrefix = efe.getEmptyFieldPrefix();
        this.index = index;
    }

    public Prefix getEmptyFieldPrefix() {
        return emptyFieldPrefix;
    }

    public Index getIndex() {
        return index;
    }
}
```
###### \java\seedu\address\model\insurance\LifeInsurance.java
``` java
    private LocalDate signingDate;
    private LocalDate expiryDate;
```
###### \java\seedu\address\model\person\Address.java
``` java
        if (address.isEmpty()) {
            throw new EmptyFieldException(PREFIX_ADDRESS);
        }
```
###### \java\seedu\address\model\person\DateOfBirth.java
``` java
    public static final String MESSAGE_DOB_CONSTRAINTS =
            "Please enter in Day Month Year format where the month can be a number or the name"
                    + " and the year can be input in 2-digit or 4-digit format.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DOB_VALIDATION_REGEX = "\\d+[\\s-./,]\\p{Alnum}+[\\s-./,]\\d+.*";

    public final LocalDate dateOfBirth;
    private boolean dateSet;

    /**
     * Initialise a DateOfBirth object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public DateOfBirth() {
        this.dateOfBirth = LocalDate.now();
        this.dateSet = false;
    }

    /**
     * Validates given Date of Birth.
     *
     * @throws IllegalValueException if given date of birth string is invalid.
     */
    public DateOfBirth(String dob) throws IllegalValueException {
        requireNonNull(dob);
        if (dob.isEmpty()) {
            throw new EmptyFieldException(PREFIX_DOB);
        }
        if (!isValidDateOfBirth(dob)) {
            throw new IllegalValueException(MESSAGE_DOB_CONSTRAINTS);
        }
        this.dateOfBirth = new DateParser().parse(dob);
        this.dateSet = true;
    }

    /**
     * Returns true if a given string is a valid person date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        return test.matches(DOB_VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return dateSet ? dateOfBirth.format(DateParser.DATE_FORMAT) : "";
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the internal list of people
     */
    public void sortPersons() throws DuplicatePersonException {
        ObservableList<Person> listToSort = FXCollections.observableArrayList(internalList);
        listToSort.sort((ReadOnlyPerson first, ReadOnlyPerson second)-> {
            int x = String.CASE_INSENSITIVE_ORDER.compare(first.getName().fullName, second.getName().fullName);
            if (x == 0) {
                x = (first.getName().fullName).compareTo(second.getName().fullName);
            }
            return x;
        });
        UniquePersonList listToReplace = new UniquePersonList();
        for (ReadOnlyPerson person : listToSort) {
            listToReplace.add(person);
        }
        setPersons(listToReplace);
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
        } catch (EmptyFieldException efe) {
            initHistory();
            // autofill function triggered
            logger.info("Autofill triggered: " + commandTextField.getText());
            historySnapshot.next();
            commandTextField.setText(historySnapshot.previous());
            commandTextField.positionCaret(commandTextField.getText().length());
            raise(new NewResultAvailableEvent("Autofilled!", false));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }
```
###### \resources\view\DarkTheme.css
``` css
#insuranceListView {
    -fx-background-color: derive(#1d1d1d, 20%);
}
```
