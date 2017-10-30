# Juxarius
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
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
        } catch (EmptyFieldException efe) {
            // index check was bypassed, this checks the index before filling empty prefix
            if (efe.getIndex().getOneBased() > model.getFilteredPersonList().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            commandText = getAutoFilledCommand(commandText, efe.getIndex());
            throw efe;
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
    public static LocalDate parser(String dob) throws IllegalValueException {
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
    private static String getValidYear(String year) throws IllegalValueException {
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

    private static String getValidDay(String day) throws IllegalValueException {
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

    private static String getValidMonth(String month) throws IllegalValueException {
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
    private static int getMonth(String monthName) throws IllegalValueException {
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
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
            parseDetagsForEdit(argMultimap.getAllValues(PREFIX_DELTAG)).ifPresent(editPersonDescriptor::setTagsToDel);
        } catch (EmptyFieldException efe) {
            throw new EmptyFieldException(efe, index);
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
        this.dateOfBirth = DateParser.parser(dob);
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
###### \java\seedu\address\model\person\Email.java
``` java
        if (trimmedEmail.isEmpty()) {
            throw new EmptyFieldException(PREFIX_EMAIL);
        }
```
###### \java\seedu\address\model\person\Name.java
``` java
        if (trimmedName.isEmpty()) {
            throw new EmptyFieldException(PREFIX_NAME);
        }
```
###### \java\seedu\address\model\person\Phone.java
``` java
        if (trimmedPhone.isEmpty()) {
            throw new EmptyFieldException(PREFIX_PHONE);
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
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
        UniquePersonList listToReplace = new UniquePersonList();
        for (ReadOnlyPerson person : listToSort) {
            listToReplace.add(person);
        }
        setPersons(listToReplace);
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
        if (trimmedName.isEmpty()) {
            throw new EmptyFieldException(PREFIX_TAG);
        }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        } catch (EmptyFieldException efe) {
            initHistory();
            // autofill function triggered
            logger.info("Autofill triggered: " + commandTextField.getText());
            historySnapshot.next();
            commandTextField.setText(historySnapshot.previous());
            commandTextField.positionCaret(commandTextField.getText().length());
            raise(new NewResultAvailableEvent("Autofilled!", false));
```
