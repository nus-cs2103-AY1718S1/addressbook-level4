# justinpoh
###### \java\seedu\address\commons\events\model\RemindersChangedEvent.java
``` java
/** Indicates the reminders in the model have changed. */
public class RemindersChangedEvent extends BaseEvent {
    public final UniqueReminderList reminderList;

    public RemindersChangedEvent(UniqueReminderList reminderList) {
        this.reminderList = reminderList;
    }

    @Override
    public String toString() {
        return "number of reminders " + reminderList.size();
    }
}
```
###### \java\seedu\address\commons\events\ui\BrowserAndRemindersPanelToggleEvent.java
``` java
/**
 * Represents a toggling between the browser and reminders panels.
 */

public class BrowserAndRemindersPanelToggleEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\AddReminderCommand.java
``` java
/**
 *  Adds a reminder to the program.
 */

public class AddReminderCommand extends Command {

    public static final String COMMAND_WORD = "addreminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder.\n"
            + "Parameters: "
            + PREFIX_REMINDER + "REMINDER "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER + "Dinner with Family "
            + PREFIX_DATE + "22-11-2017 "
            + PREFIX_TIME + "1700\n";

    public static final String MESSAGE_SUCCESS = "New reminder added.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in iContacts.";

    private Reminder toAdd;

    /**
     * Creates an AddReminderCommand to add the specified {@code ReadOnlyReminder}
     */
    public AddReminderCommand(ReadOnlyReminder toAdd) {
        this.toAdd = new Reminder(toAdd);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DuplicateReminderException dre) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\DeleteReminderCommand.java
``` java
/**
 * Deletes a reminder identified using it's index.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "deletereminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the reminder listing.\n"
            + "Parameters: INDEX (must be a positive integer).\n"
            + "Example: " + COMMAND_WORD + " 1.";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted reminder: %1$s.";

    private final Index targetIndex;

    public DeleteReminderCommand(Index index) {
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyReminder> reminderListing = model.getSortedReminderList();

        if (targetIndex.getZeroBased() >= reminderListing.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToDelete = reminderListing.get(targetIndex.getZeroBased());

        try {
            model.deleteReminder(reminderToDelete);
        } catch (ReminderNotFoundException rnfe) {
            assert false : "The target reminder cannot be missing.";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReminderCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\EditReminderCommand.java
``` java
/**
 * Edits the details of an existing reminder in iContacts.
 */
public class EditReminderCommand extends Command {

    public static final String COMMAND_WORD = "editreminder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reminder identified "
            + "by the index number. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMINDER + "REMINDER]"
            + "[" + PREFIX_DATE + "DATE]"
            + "[" + PREFIX_TIME + "TIME]\n"
            + "Additionally, if REMINDER is edited, the new value must contain at least one character.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMINDER + "Changed reminder";


    public static final String MESSAGE_EDIT_REMINDER_SUCCESS = "Edited Reminder: %1$s.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in iContacts.";

    public static final String MESSAGE_REMINDER_FORMAT = "Reminder can be of any value, and cannot be empty.";

    private final Index index;
    private final EditReminderDescriptor editReminderDescriptor;

    /**
     * @param index of the reminder in the sorted reminder list to edit
     * @param editReminderDescriptor details to edit the reminder with
     */
    public EditReminderCommand(Index index, EditReminderDescriptor editReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(editReminderDescriptor);

        this.index = index;
        this.editReminderDescriptor = editReminderDescriptor;
    }


    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyReminder> reminderList = model.getSortedReminderList();

        if (index.getZeroBased() >= reminderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToEdit = reminderList.get(index.getZeroBased());
        Reminder editedReminder = createEditedReminder(reminderToEdit, editReminderDescriptor);

        try {
            model.updateReminder(reminderToEdit, editedReminder);
        } catch (DuplicateReminderException dre) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder));
    }

    /**
     * Creates and returns a {@code Reminder} with the details of {@code reminderToEdit}
     * edited with {@code editReminderDescriptor}.
     */
    private static Reminder createEditedReminder (ReadOnlyReminder reminderToEdit,
                                                  EditReminderDescriptor editReminderDescriptor) {
        assert reminderToEdit != null;

        String updatedReminder = editReminderDescriptor.getReminder().orElse(reminderToEdit.getReminder());
        Date updatedDate = editReminderDescriptor.getDate().orElse(reminderToEdit.getDate());
        Time updatedTime = editReminderDescriptor.getTime().orElse(reminderToEdit.getTime());

        return new Reminder(updatedReminder, updatedDate, updatedTime);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditReminderCommand)) {
            return false;
        }

        // state check
        EditReminderCommand e = (EditReminderCommand) other;
        return index.equals(e.index)
                && editReminderDescriptor.equals(e.editReminderDescriptor);
    }

    /**
     * Stores the details to edit the reminder with. Each non-empty field value will replace the
     * corresponding field value of the reminder.
     */
    public static class EditReminderDescriptor {
        private String reminder;
        private Time time;
        private Date date;

        public EditReminderDescriptor() {}

        public EditReminderDescriptor(EditReminderDescriptor toCopy) {
            this.reminder = toCopy.reminder;
            this.time = toCopy.time;
            this.date = toCopy.date;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.reminder, this.time, this.date);
        }

        public void setReminder(String reminder) {
            this.reminder = reminder;
        }

        public Optional<String> getReminder() {
            return Optional.ofNullable(reminder);
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReminderDescriptor)) {
                return false;
            }

            // state check
            EditReminderDescriptor e = (EditReminderDescriptor) other;

            return getReminder().equals(e.getReminder())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime());
        }
    }
}
```
###### \java\seedu\address\logic\commands\ToggleCommand.java
``` java
/**
 * Toggles between the browser and reminders.
 */

public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";
    public static final String MESSAGE_TOGGLE_SUCCESS = "Toggle successful.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new BrowserAndRemindersPanelToggleEvent());
        return new CommandResult(MESSAGE_TOGGLE_SUCCESS);
    }


}
```
###### \java\seedu\address\logic\parser\AddReminderCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final String reminder;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER, PREFIX_DATE, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER, PREFIX_DATE, PREFIX_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        reminder = argMultimap.getValue(PREFIX_REMINDER).get();
        if (reminder.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        try {
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME)).get();
            ReadOnlyReminder toAdd = new Reminder(reminder, date, time);
            return new AddReminderCommand(toAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage());
        }
    }
```
###### \java\seedu\address\logic\parser\DeleteReminderCommandParser.java
``` java
/**
 * Parses input arguments and create a new DeleteReminderCommand object.
 */
public class DeleteReminderCommandParser implements Parser<DeleteReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReminderCommand
     * and returns an DeleteReminderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteReminderCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\EditReminderCommandParser.java
``` java
/**
 * Parses input arguments and creates a new EditReminderCommand object
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the EditReminderCommand
     * and returns an EditReminderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER, PREFIX_DATE, PREFIX_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
        }

        EditReminderDescriptor editReminderDescriptor = new EditReminderDescriptor();
        Optional<String> optionalReminder = argMultimap.getValue(PREFIX_REMINDER);
        String reminder;
        if (optionalReminder.isPresent()) {
            reminder = optionalReminder.get();
            if (reminder.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditReminderCommand.MESSAGE_REMINDER_FORMAT));
            }
            editReminderDescriptor.setReminder(reminder);
        }

        try {
            ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).ifPresent(editReminderDescriptor::setDate);
            ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME)).ifPresent(editReminderDescriptor::setTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editReminderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReminderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReminderCommand(index, editReminderDescriptor);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void resetReminders(UniqueReminderList newReminders) {
        reminderList.setReminders(newReminders);
        indicateRemindersChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireAllNonNull(target, editedReminder);

        reminderList.setReminder(target, editedReminder);
        indicateRemindersChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getBirthdayPanelFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredPersonsForBirthdayListPanel);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public ObservableList<ReadOnlyReminder> getSortedReminderList() {
        return sortedReminderList;
    }

    @Override
    public ReadOnlyUniqueReminderList getUniqueReminderList() {
        return reminderList;
    }

    @Override
    public void addReminder(ReadOnlyReminder toAdd) throws DuplicateReminderException {
        reminderList.add(toAdd);
        indicateRemindersChanged();
    }

    @Override
    public void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException {
        reminderList.remove(target);
        indicateRemindersChanged();
    }

```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Birthday must be a valid date"
            + " and in the following format:\n"
            + "'.', '-' and '/' can be used to separate the day, month and year fields,"
            + " and need not be used in pairs (i.e. 21.10/1995 works as well).\n"
            + "Day field: 1 - 31.\n"
            + "Month field: 1-12.\n"
            + "Year field: 1900 - 2099.\n"
            + "Example: 21/10/1995, 21-05-1996, 8.10.1987, 01/12-1995, 01.01-1990";
    public static final int EMPTY_BIRTHDAY_FIELD_MONTH = 0;
    public static final int EMPTY_BIRTHDAY_FIELD_DAY = 0;

    private static final String EMPTY_STRING = "";

    private static final String BIRTHDAY_DASH_SEPARATOR = "-";

    private static final int DATE_DAY_INDEX = 0;
    private static final int DATE_MONTH_INDEX = 1;
    private static final int DATE_YEAR_INDEX = 2;

    private static final int DUMMY_YEAR = 2000;

    private static final String BIRTHDAY_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";
    private static final String BIRTHDAY_SPLIT_REGEX = "[///./-]";

    private static final int BIRTHDAY_TOMORROW_VALIDATOR = 1;

    public final String value;

    private final LocalDate currentDate;


    /**
     * Validates the given birthday and instantiate a LocalDate object with the date
     * this Birthday object was instantiated.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);

        if (!isValidBirthday(birthday)) {
            System.out.println(birthday);
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        String trimmedBirthday = birthday.trim();
        if (trimmedBirthday.isEmpty()) {
            this.value = EMPTY_STRING;
        } else {
            this.value = convertToDefaultDateFormat(birthday);
        }

        currentDate = LocalDate.now();
    }

    /**
     * This constructor is used for testing purposes.
     * This is because the value of {@code LocalDate.now()} is dependent on the date the tests are conducted
     * and might lead to tests failing depending on the date the tests are conducted.
     */
    private Birthday(String birthday, LocalDate currentDateForTesting) {
        this.value = convertToDefaultDateFormat(birthday);
        this.currentDate = currentDateForTesting;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String birthday) {
        String trimmedBirthday = birthday.trim();
        if (trimmedBirthday.isEmpty()) {
            return true;
        }
        if (!trimmedBirthday.matches(BIRTHDAY_VALIDATION_REGEX)) {
            return false;
        }

        String[] splitDate = getSplitDate(trimmedBirthday);

        if (!isValidDate(splitDate)) {
            return false;
        }
        return true;
    }

    /**
     * Return a Birthday instance that is used for testing.
     */
    public static Birthday getBirthdayTestInstance(String birthday, LocalDate currentDateForTesting) {
        Birthday testInstance = new Birthday(birthday, currentDateForTesting);
        return testInstance;
    }

    /**
     * Returns the month of the birthday in this Birthday object.
     * If the birthday field is empty, return EMPTY_BIRTHDAY_FIELD_MONTH
     */
    public int getMonthOfBirthday() {
        if (value.isEmpty()) {
            return EMPTY_BIRTHDAY_FIELD_MONTH;
        }
        String[] splitDate = getSplitDate(value);
        try {
            final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
            return month;
        } catch (NumberFormatException nfe) {
            throw new AssertionError("Should not happen");
        }
    }

    /**
     * Returns the day of the birthday in this Birthday object.
     * If the birthday field is empty, return EMPTY_BIRTHDAY_FIELD_DAY
     */
    public int getDayOfBirthday() {
        if (value.isEmpty()) {
            return EMPTY_BIRTHDAY_FIELD_DAY;
        }
        String[] splitDate = getSplitDate(value);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        return day;
    }

    /**
     * Returns true if the date for this Birthday object is the same
     * as the date today (relative to the date this Birthday object was instantiated).
     */
    public boolean isBirthdayToday() {
        if (value.isEmpty()) {
            return false;
        }
        final String[] splitDate = getSplitDate(value);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);

        final boolean isMonthEqual = (month == currentDate.getMonthValue());
        final boolean isDayEqual = day == (currentDate.getDayOfMonth());

        return isMonthEqual && isDayEqual;
    }

    /**
     * Returns true of the date for this Birthday object is tomorrow
     * (relative to the date this Birthday object was instantiated)
     */
    public boolean isBirthdayTomorrow() {
        if (value.isEmpty()) {
            return false;
        }
        final String[] splitDate = getSplitDate(value);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);

        final int currentMonth = currentDate.getMonthValue();
        final int currentDay = currentDate.getDayOfMonth();

        final LocalDate birthday = LocalDate.of(DUMMY_YEAR, month, day);
        final LocalDate currentDummyDate = LocalDate.of(DUMMY_YEAR, currentMonth, currentDay);

        final long daysUntilBirthday = currentDummyDate.until(birthday, ChronoUnit.DAYS);
        return daysUntilBirthday == BIRTHDAY_TOMORROW_VALIDATOR;
    }

    /**
     * Returns true if a given date is a valid date.
     * A date is valid if it exists.
     */
    private static boolean isValidDate(String[] splitDate) {
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException dte) {
            return false;
        }
        return true;
    }

    /**
     * Converts the date into the year, month and date fields.
     */
    private static String[] getSplitDate(String trimmedDate) {
        return trimmedDate.split(BIRTHDAY_SPLIT_REGEX);
    }

    /**
     * Converts date in integer array into dd-mm-yyyy String format.
     */
    private static String convertToDefaultDateFormat(String date) {
        String[] splitDate = getSplitDate(date);

        StringBuilder builder = new StringBuilder();

        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);

        builder.append(day);
        builder.append(BIRTHDAY_DASH_SEPARATOR);
        builder.append(month);
        builder.append(BIRTHDAY_DASH_SEPARATOR);
        builder.append(year);
        return builder.toString();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Birthday
                && this.value.equals(((Birthday) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Person.java
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
###### \java\seedu\address\model\person\UpcomingBirthdayInCurrentMonthPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s birthday is within this month and have not passed.
 * If a {@code ReadOnlyPerson} does not have a birthday recorded, return false.
 */
public class UpcomingBirthdayInCurrentMonthPredicate implements Predicate<ReadOnlyPerson> {
    private final int currentMonth;
    private final int currentDay;

    public UpcomingBirthdayInCurrentMonthPredicate() {
        // Added 1 because Calendar.getInstance().get(Calendar.MONTH) is zero-based.
        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * This constructor is used for testing purposes only.
     */
    private UpcomingBirthdayInCurrentMonthPredicate(int monthToFilter, int dayToFilter) {
        this.currentMonth = monthToFilter;
        this.currentDay = dayToFilter;
    }

    /**
     * Returns a UpcomingBirthdayInCurrentMonthPredicate instance that is used for testing,
     * initialized with {@code monthToFilter} and {@code dayToFilter}.
     */
    public static UpcomingBirthdayInCurrentMonthPredicate getTestInstance(int monthToFilter, int dayToFilter) {
        UpcomingBirthdayInCurrentMonthPredicate testPredicate =
                new UpcomingBirthdayInCurrentMonthPredicate(monthToFilter, dayToFilter);
        return testPredicate;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        final int month = person.getBirthday().getMonthOfBirthday();
        final int day = person.getBirthday().getDayOfBirthday();
        if (month == Birthday.EMPTY_BIRTHDAY_FIELD_MONTH) {
            return false;
        }

        if (month != currentMonth) {
            return false;
        }

        return day >= currentDay;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpcomingBirthdayInCurrentMonthPredicate // instanceof handles nulls
                && this.currentMonth == ((UpcomingBirthdayInCurrentMonthPredicate) other).currentMonth) // state check
                && this.currentDay == ((UpcomingBirthdayInCurrentMonthPredicate) other).currentDay;
    }
}
```
###### \java\seedu\address\model\reminders\Date.java
``` java
/**
 * Represents a Reminder's date in the program.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date must be valid,"
            + " and in the following format:\n"
            + "'.', '-' and '/' can be used to separate the day, month and year fields,"
            + " and need not be used in pairs (i.e. 21.10/1995 works as well).\n"
            + "Day field: 1 - 31.\n"
            + "Month field: 1-12.\n"
            + "Year field: 1900 - 2099.\n"
            + "Example: 21/10/1995, 21-05-1996, 8.10.1987, 01/12-1995, 01.01-1990";

    public static final String DATE_SPLIT_REGEX = "[///./-]";

    public static final int DATE_DAY_INDEX = 0;
    public static final int DATE_MONTH_INDEX = 1;
    public static final int DATE_YEAR_INDEX = 2;

    private static final String DATE_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";
    private static final String DATE_SEPARATOR = "-";

    public final String value;

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        requireNonNull(date);
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = convertToPresentableForm(date);
    }

    /**
     * Returns true if a given string is a valid reminder date.
     */
    public static boolean isValidDate(String date) {
        if (!date.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }
        String[] splitDate = date.split(DATE_SPLIT_REGEX);

        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException dte) {
            return false;
        }

        return true;
    }

    /**
     * Returns the date as a LocalDate object for easy comparison of chronology between reminders.
     */
    public LocalDate toLocalDate() {
        String[] splitDate = value.split(DATE_SPLIT_REGEX);
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        return LocalDate.of(year, month, day);
    }

    /**
     * Converts the date String from user into a standard and presentable form: dd-mm-yyyy.
     * @return the converted date.
     */
    private static String convertToPresentableForm(String date) {
        String[] splitDate = date.split(DATE_SPLIT_REGEX);
        StringBuilder builder = new StringBuilder();

        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);

        builder.append(day);
        builder.append(DATE_SEPARATOR);
        builder.append(month);
        builder.append(DATE_SEPARATOR);
        builder.append(year);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Date
                && this.value.equals(((Date) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\address\model\reminders\exceptions\DuplicateReminderException.java
``` java
/**
 *  Signals that the operation will result in duplicate Reminder objects.
 */
public class DuplicateReminderException extends DuplicateDataException {
    public DuplicateReminderException() {
        super("Operation would result in duplicate reminders.");
    }
}
```
###### \java\seedu\address\model\reminders\exceptions\ReminderNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified reminder.
 */
public class ReminderNotFoundException extends Exception {
}
```
###### \java\seedu\address\model\reminders\ReadOnlyReminder.java
``` java
/**
 * A read-only immutable interface for a Reminder in iContacts.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyReminder {
    ObjectProperty<String> reminderProperty();
    String getReminder();
    ObjectProperty<Date> dateProperty();
    Date getDate();
    ObjectProperty<Time> timeProperty();
    Time getTime();
    ObjectProperty<Status> statusProperty();
    LocalDateTime getLocalDateTime();
    boolean isEventToday();
    boolean isEventWithinThreeDays();
    boolean hasEventPassed();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyReminder other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getReminder().equals(this.getReminder()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getTime().equals(this.getTime()));
    }
}
```
###### \java\seedu\address\model\reminders\ReadOnlyUniqueReminderList.java
``` java
/**
 * Unmodifiable view of a UniqueReminderList
 */
public interface ReadOnlyUniqueReminderList {

    /**
     * Returns an unmodifiable view of the reminders list.
     * This list will not contain any duplicate reminders.
     */
    ObservableList<ReadOnlyReminder> asObservableList();
}
```
###### \java\seedu\address\model\reminders\Reminder.java
``` java
/**
 *  Represents a reminder in iContacts.
 */

public class Reminder implements ReadOnlyReminder {

    private ObjectProperty<String> reminder;
    private ObjectProperty<Date> date;
    private ObjectProperty<Time> time;
    private ObjectProperty<Status> status;

    /**
     * Every field must be present and not null.
     */
    public Reminder(String reminder, Date date, Time time) {
        requireAllNonNull(reminder, date, time);

        this.reminder = new SimpleObjectProperty<>(reminder);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.status = new SimpleObjectProperty<>(new Status(this));
    }

    /**
     * Creates a copy of the given ReadOnlyReminder.
     */
    public Reminder(ReadOnlyReminder source) {
        this(source.getReminder(), source.getDate(), source.getTime());
    }

    public void setReminder(String reminder) {
        this.reminder.set(requireNonNull(reminder));
    }

    public ObjectProperty<String> reminderProperty() {
        return reminder;
    }

    public String getReminder() {
        return reminder.get();
    }

    public void setTime(Time time) {
        this.time.set(requireNonNull(time));
    }

    public ObjectProperty<Time> timeProperty() {
        return time;
    }

    public Time getTime() {
        return time.get();
    }

    public void setDate(Date date) {
        this.date.set(requireNonNull(date));
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public Date getDate() {
        return date.get();
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.of(date.get().toLocalDate(), time.get().toLocalTime());
    }

    @Override
    public boolean hasEventPassed() {
        return status.get().hasEventPassed();
    }

    @Override
    public boolean isEventToday() {
        return status.get().isEventToday();
    }

    @Override
    public boolean isEventWithinThreeDays() {
        return status.get().isEventWithinThreeDays();
    }

    public ObjectProperty<Status> statusProperty() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof ReadOnlyReminder
                && this.isSameStateAs((ReadOnlyReminder) other));
    }

    @Override
    public int hashCode() {
        return Objects.hash(reminder.get(), date.get(), time.get());
    }

    @Override
    public String toString() {
        return reminder.get() + "\n" + date.get() + "\n" + time.get();
    }
}
```
###### \java\seedu\address\model\reminders\Status.java
``` java
/**
 * Represents a Reminder's status in the program.
 * Guarantees: immutable.
 */
public class Status {

    private static final String STATUS_FORMAT_SINGLE_MESSAGE = "Status: %1$s day left.";
    private static final String STATUS_FORMAT_MESSAGE = "Status: %1$s days left.";
    private static final String STATUS_TODAY_MESSAGE = "Event happening today!";
    private static final String STATUS_OVERDUE = "Event has passed.";

    private static final int THREE_DAYS = 3;
    private static final int ONE_DAY = 1;
    private static final int ZERO_DAY = 0;
    private static final int ZERO = 0;

    private final LocalDate currentDate;
    private final LocalTime currentTime;

    private final LocalDate dateOfReminder;
    private final LocalTime timeOfReminder;

    private final String status;

    /**
     * Initialize the status for this reminder.
     */
    public Status(Reminder reminder) {
        currentDate = LocalDate.now();
        currentTime = LocalTime.now();
        this.dateOfReminder = reminder.getDate().toLocalDate();
        this.timeOfReminder = reminder.getTime().toLocalTime();
        this.status = getStatus();
    }

    /**
     * This constructor is used for testing purposes only.
     * This is because the values of {@code LocalDate.now()} and {@code LocalTime.now()} is dependent on the date and
     * time the tests are conducted and might lead to tests failing depending on the date and time the tests
     * are conducted.
     */
    private Status(Reminder reminder, LocalDate defaultDate, LocalTime defaultTime) {
        this.currentDate = defaultDate;
        this.currentTime = defaultTime;
        this.dateOfReminder = reminder.getDate().toLocalDate();
        this.timeOfReminder = reminder.getTime().toLocalTime();
        this.status = getStatus();
    }

    /**
     * Return a Status instance that is used for testing, initialized with {@code reminder}, {@code defaultDate}
     * and {@code defaultTime}.
     */
    public static Status getStatusTestInstance(Reminder reminder, LocalDate defaultDate, LocalTime defaultTime) {
        Status testInstance = new Status(reminder, defaultDate, defaultTime);
        return testInstance;
    }

    /**
     * Returns true if the event has already past.
     */
    public boolean hasEventPassed() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();
        if (daysUntilEvent > ZERO_DAY) {
            return false;
        } else if (daysUntilEvent == ZERO_DAY && minutesUntilEvent >= ZERO) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the event is happening today (with respect to the date and time this object is created).
     */
    public boolean isEventToday() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();

        return daysUntilEvent == 0 && minutesUntilEvent >= 0;
    }

    /**
     * Returns true if the event is happening within three days
     * (with respect to the date and time this object is created.)
     */
    public boolean isEventWithinThreeDays() {
        final long daysUntilEvent = getDaysUntilEvent();
        final long minutesUntilEvent = getMinutesUntilEvent();
        if (daysUntilEvent < ZERO_DAY || daysUntilEvent > THREE_DAYS) {
            return false;
        }
        if (daysUntilEvent == 0 && minutesUntilEvent < 0) {
            return false;
        }
        return true;
    }

    /**
     * Returns the correct status depending on the given {@code reminder} to this Status object
     * @see Status(Reminder)
     */
    private String getStatus() {
        if (hasEventPassed()) {
            return STATUS_OVERDUE;
        } else if (isEventToday()) {
            return STATUS_TODAY_MESSAGE;
        } else {
            final long daysUntilEvent = getDaysUntilEvent();
            if (daysUntilEvent == ONE_DAY) {
                return String.format(STATUS_FORMAT_SINGLE_MESSAGE, ONE_DAY);
            }

            return String.format(STATUS_FORMAT_MESSAGE, getDaysUntilEvent());
        }
    }

    /**
     * Returns the number of days left until {@code reminderDate} (with respect to the date this object is created).
     */
    private long getDaysUntilEvent() {
        return currentDate.until(dateOfReminder, ChronoUnit.DAYS);
    }

    /**
     * Returns the difference in time between the current time and {@code reminderTime} in minutes
     * (with respect to the time this object is created).
     * Note that this method only returns the difference in time, and does not take into consideration the difference
     * in days.
     */
    private long getMinutesUntilEvent() {
        return currentTime.until(timeOfReminder, ChronoUnit.MINUTES);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, dateOfReminder, timeOfReminder);
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Status
                && this.status.equals(((Status) other).status));
    }
}
```
###### \java\seedu\address\model\reminders\Time.java
``` java
/**
 * Represents a Reminder's time in the program.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time must be in 24-hour format,"
            + " with a colon separating the hour and minute fields.\n"
            + "Example: 09:00, 23:59, 17:56";

    public static final String HOUR_MIN_SEPARATOR = ":";

    public static final int TIME_HOUR_INDEX = 0;
    public static final int TIME_MIN_INDEX = 1;

    private static final String TIME_VALIDATION_REGEX = "(0[0-9]|1[0-9]|2[0-3]):"
            + "(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])";

    public final String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        requireNonNull(time);
        if (!isValidTime(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }

        this.value = time;
    }

    /**
     * Returns true if a given string is a valid reminder time.
     */
    public static boolean isValidTime(String time) {
        if (!time.matches(TIME_VALIDATION_REGEX)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the time as a LocalTime object for easy comparison of chronology between reminders.
     */
    public LocalTime toLocalTime() {
        String[] splitTime = value.split(HOUR_MIN_SEPARATOR);
        final int hour = Integer.parseInt(splitTime[TIME_HOUR_INDEX]);
        final int min = Integer.parseInt(splitTime[TIME_MIN_INDEX]);
        return LocalTime.of(hour, min);
    }


    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Time
                && this.value.equals(((Time) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\address\model\reminders\UniqueReminderList.java
``` java
/**
 * A list of reminders that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Reminder#equals(Object)
 */
public class UniqueReminderList implements Iterable<Reminder>, ReadOnlyUniqueReminderList {
    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyReminder> mappedList = EasyBind.map(internalList, (reminder) -> reminder);

    public UniqueReminderList() {
    }

    public UniqueReminderList(UniqueReminderList uniqueReminderList) {
        this();
        setReminders(uniqueReminderList);
    }

    /**
     * Constructor used for loading reminder from storage file into program.
     */
    public UniqueReminderList(ReadOnlyUniqueReminderList xmlReminders) {
        requireNonNull(xmlReminders);
        try {
            setReminders(xmlReminders.asObservableList());
        } catch (DuplicateReminderException dre) {
            assert false : "Reminders from storage should not have duplicates";
        }
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     */
    public boolean contains(ReadOnlyReminder toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a reminder to the list.
     * @throws DuplicateReminderException if the reminder to add is a duplicate of an existing reminder in the list.
     */
    public void add(ReadOnlyReminder toAdd) throws DuplicateReminderException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReminderException();
        }
        internalList.add(new Reminder(toAdd));
    }

    /**
     * Replaces the reminder {@code target} in the list with {@code editedReminder}.
     *
     * @throws DuplicateReminderException if the replacement is equivalent to another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    public void setReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireNonNull(editedReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }

        if (!target.equals(editedReminder) && internalList.contains(editedReminder)) {
            throw new DuplicateReminderException();
        }

        internalList.set(index, new Reminder(editedReminder));
    }

    /**
     * Removes the equivalent reminder from the list.
     *
     * @throws ReminderNotFoundException if no such reminder could be found in the list.
     */
    public boolean remove(ReadOnlyReminder toRemove) throws ReminderNotFoundException {
        requireNonNull(toRemove);
        final boolean reminderFoundAndDeleted = internalList.remove(toRemove);
        if (!reminderFoundAndDeleted) {
            throw new ReminderNotFoundException();
        }
        return reminderFoundAndDeleted;
    }

    public void setReminders(UniqueReminderList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setReminders(List<? extends ReadOnlyReminder> reminders) throws DuplicateReminderException {
        final UniqueReminderList replacement = new UniqueReminderList();
        for (final ReadOnlyReminder reminder : reminders) {
            replacement.add(new Reminder(reminder));
        }
        setReminders(replacement);
    }

    /**
     * Returns the list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyReminder> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Reminder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueReminderList // instanceof handles nulls
                && this.internalList.equals(((UniqueReminderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getRemindersFilePath() {
        return remindersFilePath;
    }

    public void setRemindersFilePath(String remindersFilePath) {
        this.remindersFilePath = remindersFilePath;
    }
```
###### \java\seedu\address\storage\RemindersStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.reminders.UniqueReminderList}.
 */

public interface RemindersStorage {
    /**
     * Returns the file path of the data file for reminders.
     */
    String getRemindersFilePath();

    /**
     * Returns UniqueReminderList data as a {@link ReadOnlyUniqueReminderList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUniqueReminderList> readReminders() throws DataConversionException, IOException;

    /**
     * @see #getRemindersFilePath()
     */
    Optional<ReadOnlyUniqueReminderList> readReminders(String filePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyUniqueReminderList} to the storage.
     * @param reminderList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveReminders(ReadOnlyUniqueReminderList reminderList) throws IOException;

    /**
     * @see #saveReminders(ReadOnlyUniqueReminderList)
     */
    void saveReminders(ReadOnlyUniqueReminderList reminderList, String filePath) throws IOException;
}
```
###### \java\seedu\address\storage\Storage.java
``` java
    @Override
    Optional<ReadOnlyUniqueReminderList> readReminders() throws DataConversionException, IOException;
```
###### \java\seedu\address\storage\Storage.java
``` java
    @Override
    void saveReminders(ReadOnlyUniqueReminderList reminderList) throws IOException;
```
###### \java\seedu\address\storage\Storage.java
``` java
    /**
     * Saves the current version of reminders to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRemindersChangedEvent(RemindersChangedEvent rce);
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleRemindersChangedEvent(RemindersChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local reminders data changed,"
                + " saving to file"));

        try {
            saveReminders(event.reminderList);
        } catch (IOException e) {
            raise (new DataSavingExceptionEvent(e));
        }
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public String getRemindersFilePath() {
        return remindersStorage.getRemindersFilePath();
    }

    @Override
    public Optional<ReadOnlyUniqueReminderList> readReminders() throws DataConversionException, IOException {
        return readReminders(remindersStorage.getRemindersFilePath());
    }

    @Override
    public Optional<ReadOnlyUniqueReminderList> readReminders(String filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return remindersStorage.readReminders(filePath);
    }

    @Override
    public void saveReminders(ReadOnlyUniqueReminderList reminderList) throws IOException {
        saveReminders(reminderList, remindersStorage.getRemindersFilePath());
    }

    @Override
    public void saveReminders(ReadOnlyUniqueReminderList reminderList, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        remindersStorage.saveReminders(reminderList, filePath);
    }
```
###### \java\seedu\address\storage\XmlAdaptedReminder.java
``` java
/**
 * JAXB-friendly version of Reminder.
 */
public class XmlAdaptedReminder {

    @XmlElement(required = true)
    private String reminder;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String time;

    /**
     * Constructs an XmlAdaptedReminder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReminder() {}

    /**
     * Converts a given Reminder into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReminder
     */
    public XmlAdaptedReminder(ReadOnlyReminder source) {
        this.reminder = source.getReminder();
        this.date = source.getDate().value;
        this.time = source.getTime().value;
    }
    /**
     * Converts this jaxb-friendly adapted reminder object into the model's Reminder object.
     *
     * @throws DateTimeParseException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {
        final Date date = new Date(this.date);
        final Time time = new Time(this.time);
        return new Reminder(this.reminder, date, time);
    }


}
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    /**
     * Saves the given reminders data to the specified file.
     */
    public static void saveRemindersToFile(File file, XmlSerializableReminders reminders)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, reminders);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    /**
     * Returns reminders in the file or an empty reminder list
     */
    public static XmlSerializableReminders loadRemindersFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableReminders.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
```
###### \java\seedu\address\storage\XmlRemindersStorage.java
``` java
/**
 * A class to access reminder data stored as an xml file on the hard disk.
 */

public class XmlRemindersStorage implements RemindersStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRemindersStorage.class);

    private String filePath;

    public XmlRemindersStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getRemindersFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUniqueReminderList> readReminders() throws DataConversionException, IOException {
        return readReminders(filePath);
    }

    /**
     * Similar to {@link #readReminders()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUniqueReminderList> readReminders(String filePath) throws DataConversionException,
                                                                                    FileNotFoundException {
        requireNonNull(filePath);

        File remindersFile = new File(filePath);

        if (!remindersFile.exists()) {
            logger.info("Reminders file "  + remindersFile + " not found");
            return Optional.empty();
        }

        XmlSerializableReminders remindersOptional = XmlFileStorage.loadRemindersFromSaveFile(new File(filePath));

        return Optional.of(remindersOptional);
    }

    @Override
    public void saveReminders(ReadOnlyUniqueReminderList reminderList) throws IOException {
        saveReminders(reminderList, filePath);
    }

    /**
     * Similar to {@link #saveReminders(ReadOnlyUniqueReminderList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveReminders(ReadOnlyUniqueReminderList reminderList, String filePath) throws IOException {
        requireNonNull(reminderList);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveRemindersToFile(file, new XmlSerializableReminders(reminderList.asObservableList()));
    }
}
```
###### \java\seedu\address\storage\XmlSerializableReminders.java
``` java
/**
 * A List of reminders that is serializable to XML format.
 */
@XmlRootElement(name = "reminders")
public class XmlSerializableReminders implements ReadOnlyUniqueReminderList {

    @XmlElement
    private List<XmlAdaptedReminder> reminders;

    /**
     * Creates an empty XmlSerializableReminders.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableReminders() {
        reminders = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableReminders(List<ReadOnlyReminder> source) {
        this();
        reminders.addAll(source.stream().map(XmlAdaptedReminder::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyReminder> asObservableList() {

        final ObservableList<ReadOnlyReminder> reminders = this.reminders.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                throw new AssertionError("Data file is corrupted!");
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(reminders);
    }
}
```
###### \java\seedu\address\ui\BirthdayAndReminderListPanel.java
``` java
/**
 * Panel containing a list of persons with birthday in the current month,
 * and a list of reminders.
 */
public class BirthdayAndReminderListPanel extends UiPart<Region> {
    private static final String FXML = "BirthdayAndReminderListPanel.fxml";
    private static final String DIRECTORY_PATH = "view/";
    private static final String REMINDER_TODAY_STYLE_SHEET = DIRECTORY_PATH + "reminderToday.css";
    private static final String REMINDER_THREE_DAYS_STYLE_SHEET = DIRECTORY_PATH + "reminderWithinThreeDays.css";
    private static final String REMINDER_NORMAL_STYLE_SHEET = DIRECTORY_PATH + "reminderNormal.css";
    private final Logger logger = LogsCenter.getLogger(BirthdayAndReminderListPanel.class);

    @FXML
    private ListView<BirthdayReminderCard> birthdayListView;
    @FXML
    private ListView<ReminderCard> reminderListView;

    public BirthdayAndReminderListPanel(ObservableList<ReadOnlyPerson> birthdayList,
                                        ObservableList<ReadOnlyReminder> reminderList) {
        super(FXML);
        setConnections(birthdayList, reminderList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> birthdayList,
                                ObservableList<ReadOnlyReminder> reminderList) {
        ObservableList<BirthdayReminderCard> birthdayMappedList = EasyBind.map(
                birthdayList, (birthdayPerson) -> new BirthdayReminderCard(birthdayPerson,
                        birthdayList.indexOf(birthdayPerson) + 1));
        birthdayListView.setItems(birthdayMappedList);
        birthdayListView.setCellFactory(listView -> new BirthdayListViewCell());

        ObservableList<ReminderCard> reminderMappedList = EasyBind.map(
                reminderList, (reminder) -> new ReminderCard(reminder,
                        reminderList.indexOf(reminder) + 1));
        reminderListView.setItems(reminderMappedList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code BirthdayReminderCard}.
     */
    class BirthdayListViewCell extends ListCell<BirthdayReminderCard> {

        @Override
        protected void updateItem(BirthdayReminderCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
                return;
            } else {
                setGraphic(person.getRoot());
            }

        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ReminderCard}.
     */
    class ReminderListViewCell extends ListCell<ReminderCard> {

        @Override
        protected void updateItem(ReminderCard reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setGraphic(null);
                setText(null);
                return;
            }
            this.getStylesheets().clear();
            if (reminder.isEventToday()) {
                this.getStylesheets().add(REMINDER_TODAY_STYLE_SHEET);
            } else if (reminder.isEventWithinThreeDays()) {
                this.getStylesheets().add(REMINDER_THREE_DAYS_STYLE_SHEET);
            } else if (!reminder.hasEventPassed()) {
                this.getStylesheets().add(REMINDER_NORMAL_STYLE_SHEET);
            }

            setGraphic(reminder.getRoot());
        }
    }

}
```
###### \java\seedu\address\ui\BirthdayReminderCard.java
``` java
/**
 * An UI component that displays the name, nickname and birthday of a Person.
 */
public class BirthdayReminderCard extends UiPart<Region> {

    private static final String FXML = "BirthdayReminderCard.fxml";

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label nickname;
    @FXML
    private Label birthday;
    @FXML
    private Label icon;

    public BirthdayReminderCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        bindListeners(person);
        initIcon();
    }


    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        nickname.textProperty().bind(Bindings.convert(person.nicknameProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
    }

    /**
     * Initiates the appropriate icon depending on {@code person}'s birthday.
     */
    private void initIcon() {
        if (person.getBirthday().isBirthdayToday() || person.getBirthday().isBirthdayTomorrow()) {
            icon.setVisible(true);
        } else {
            icon.setVisible(false);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BirthdayReminderCard)) {
            return false;
        }

        // state check
        BirthdayReminderCard card = (BirthdayReminderCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
###### \java\seedu\address\ui\BrowserAndRemindersPanel.java
``` java
    /**
     * An Enumeration to differentiate between the child nodes and to keep track of which is
     * in front.
     */
    private enum Node {
        BROWSER, REMINDERS, DETAILS
    }
```
###### \java\seedu\address\ui\BrowserAndRemindersPanel.java
``` java
    /**
     * Check which child is currently at the front, and perform the appropriate toggling between the children nodes.
     */
    private void toggleBrowserPanel() {
        switch(currentlyInFront) {
        case BROWSER:
            setUpToShowRemindersPanel();
            remindersPanel.toFront();
            currentlyInFront = Node.REMINDERS;
            break;
        case REMINDERS:
            setUpToShowWebBrowser();
            browser.toFront();
            currentlyInFront = Node.BROWSER;
            break;
        case DETAILS:
            setUpToShowRemindersPanel();
            remindersPanel.toFront();
            currentlyInFront = Node.REMINDERS;
            break;
        default:
            throw new AssertionError("It should not be possible to land here");
        }
    }
```
###### \java\seedu\address\ui\BrowserAndRemindersPanel.java
``` java
    @Subscribe
    private void handleBrowserPanelToggleEvent(BrowserAndRemindersPanelToggleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        toggleBrowserPanel();
    }
```
###### \java\seedu\address\ui\ReminderCard.java
``` java
/**
 * An UI component that displays the content, date, time and status of a Reminder.
 */
public class ReminderCard extends UiPart<Region> {
    private static final String FXML = "ReminderCard.fxml";
    public final ReadOnlyReminder source;

    @FXML
    private HBox cardPane;
    @FXML
    private Label reminder;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label status;

    public ReminderCard(ReadOnlyReminder reminder, int displayedIndex) {
        super(FXML);
        source = reminder;
        id.setText(displayedIndex + ". ");
        bindListeners(reminder);
    }


    public boolean isEventToday() {
        return source.isEventToday();
    }

    public boolean isEventWithinThreeDays() {
        return source.isEventWithinThreeDays();
    }

    public boolean hasEventPassed() {
        return source.hasEventPassed();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Reminder} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyReminder source) {
        reminder.textProperty().bind(Bindings.convert(source.reminderProperty()));
        date.textProperty().bind(Bindings.convert(source.dateProperty()));
        time.textProperty().bind(Bindings.convert(source.timeProperty()));
        status.textProperty().bind(Bindings.convert(source.statusProperty()));
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCard)) {
            return false;
        }

        // state check
        ReminderCard card = (ReminderCard) other;
        return id.getText().equals(card.id.getText())
                && source.equals(card.source);
    }
}
```
###### \resources\view\BirthdayAndReminderListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox VBox.vgrow="ALWAYS" alignment="CENTER">
         <children>
            <VBox HBox.hgrow="ALWAYS" fx:id="birthdayListHolder">
               <Label text="Upcoming birthdays"/>
               <ListView fx:id="birthdayListView" VBox.vgrow="ALWAYS" HBox.hgrow="ALWAYS"/>
            </VBox>
            <VBox HBox.hgrow="ALWAYS" fx:id="reminderListHolder">
               <Label text="Reminders"/>
               <ListView fx:id="reminderListView" VBox.vgrow="ALWAYS" HBox.hgrow="ALWAYS"/>
            </VBox>
         </children>
      </HBox>
   </children>
</VBox>
```
###### \resources\view\BirthdayReminderCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="150.0" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
                <Label fx:id="nickname" styleClass="cell_small_label" text="\$nickname" />
            </HBox>
            <Label fx:id="birthday" styleClass="cell_small_label" text="\$birthday" />
        </VBox>
        <Label fx:id="icon" text="Birthday Soon" GridPane.columnIndex="1">
            <graphic>
                <MaterialDesignIconView glyphName="CAKE_VARIANT" size="20" />
            </graphic>
        </Label>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\BrowserAndRemindersPanel.fxml
``` fxml
<StackPane xmlns="http://javafx.com/javafx/8.0.102" xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="browser" />
  <StackPane fx:id="remindersPanel" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" minHeight="0.0" minWidth="0.0" prefHeight="600.0" prefWidth="800.0" />
  <AnchorPane fx:id="detailsPanel" prefHeight="700.0" prefWidth="500.0" />
</StackPane>
```
###### \resources\view\DayTheme.css
``` css
.background {
    -fx-background-color: #f2f8ff;
    background-color: #f2f8ff; /* Used in the default.html file */
}
```
###### \resources\view\DayTheme.css
``` css
.split-pane:horizontal .split-pane-divider {
    -fx-background-color: #f2f8ff;
    -fx-border-color: #f2f8ff;
}

.split-pane {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-background-color: #f2f8ff;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: #f2f8ff;
    -fx-border-width: 5;
    -fx-border-radius: 18 18 18 18;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-radius: 18 18 18 18;
    -fx-border-radius: 18 18 18 18;
    -fx-padding: 10px;
    -fx-background-insets: 10px, 10px;
    -fx-background-color: transparent, -fx-background;
}

.list-cell:filled {
    -fx-background-color: derive(#d6e5ff, +15%);
}

.list-cell:filled:selected {
    -fx-background-color: derive(#d6e5ff, +40%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: derive(#d6e5ff, -10%);
    -fx-border-width: 5;
    -fx-border-radius: 18 18 18 18;
}

.list-cell:filled:selected #popularContactPane {
    -fx-border-color: derive(#d6e5ff, -10%);
    -fx-border-width: 2;
    -fx-border-radius: 18 18 18 18;
}

.list-cell .label {
    -fx-text-fill: derive(#383838, -40%);
}

.list-cell:empty {
    /* Empty cells will not have alternating colours */
    -fx-background: #f2f8ff;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: white;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: white;
}

.anchor-pane {
     -fx-background-color: #f2f8ff;
}

.pane-with-border {
     -fx-background-color: #f2f8ff;
     -fx-border-color: #f2f8ff;
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: #f2f8ff;
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: #f2f8ff;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: derive(#383838, +30%);
}
```
###### \resources\view\DayTheme.css
``` css
.grid-pane {
    -fx-background-color: #f2f8ff;
    -fx-border-color: #f2f8ff;
    -fx-border-width: 0px;
}

.grid-pane .anchor-pane {
    -fx-background-color: #f2f8ff;
}
```
###### \resources\view\DayTheme.css
``` css
.menu-bar {
    -fx-background-color: #f2f8ff;
}
```
###### \resources\view\DayTheme.css
``` css
#resultDisplay .content {
    -fx-background-color: #f2f8ff;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 18 18 18 18;
    -fx-background-radius: 18 18 18 18;
    -fx-font-size: 16;
}

#birthdayListHolder {
    -fx-background-color: #f2f8ff;
}

#birthdayListHolder > .label {
    -fx-background-color: #f2f8ff;
}

#reminderListHolder {
    -fx-background-color: #f2f8ff;
}

#reminderListHolder > .label {
    -fx-background-color: #f2f8ff;
}

#placeHolder {
    -fx-border-color: derive(#383838, +30%);
    -fx-border-width: 2;
}

.error {
    -fx-text-fill: derive(#dc143c, 0%) !important; /* The error class should always override the default text-fill style */
}
```
###### \resources\view\NightTheme.css
``` css
.background {
    -fx-background-color: derive(#383838, -40%);
    background-color: #383838; /* Used in the default.html file */
}
```
###### \resources\view\NightTheme.css
``` css
.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#383838, -40%);
    -fx-border-color: derive(#383838, -40%);
}

.split-pane {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-background-color: derive(#383838, -40%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#383838, -40%);
    -fx-border-width: 5;
    -fx-border-radius: 18 18 18 18;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-radius: 18 18 18 18;
    -fx-border-radius: 18 18 18 18;
    -fx-padding: 10px;
    -fx-background-insets: 10px, 10px;
    -fx-background-color: transparent, -fx-background;
}

.list-cell:filled {
    -fx-background-color: derive(#383838, 20%);
}

.list-cell:filled:selected {
    -fx-background-color: derive(#383838, +50%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: derive(#383838, +100%);
    -fx-border-width: 3;
    -fx-border-radius: 18 18 18 18;
}

.list-cell:filled:selected #popularContactPane {
    -fx-border-color: derive(#383838, +100%);
    -fx-border-width: 2;
    -fx-border-radius: 18 18 18 18;
}

.list-cell .label {
    -fx-text-fill: white;
}

.list-cell:empty {
    /* Empty cells will not have alternating colours */
    -fx-background: derive(#383838, -40%);
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: white;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: white;
}

.anchor-pane {
     -fx-background-color: derive(#383838, -40%);
}

.pane-with-border {
     -fx-background-color: derive(#383838, -40%);
     -fx-border-color: derive(#383838, -40%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: derive(#383838, -40%);
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}
```
###### \resources\view\NightTheme.css
``` css
.grid-pane {
    -fx-background-color: derive(#383838, -40%);
    -fx-border-color: derive(#383838, -40%);
    -fx-border-width: 0px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#383838, -40%);
}
```
###### \resources\view\NightTheme.css
``` css
.menu-bar {
    -fx-background-color: derive(#383838, -40%);
}
```
###### \resources\view\NightTheme.css
``` css
#resultDisplay .content {
    -fx-background-color: derive(#383838, -40%);
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 18 18 18 18;
    -fx-background-radius: 18 18 18 18;
    -fx-font-size: 16;
}

#birthdayListHolder {
    -fx-background-color: derive(#383838, -40%);
}

#birthdayListHolder > .label {
    -fx-background-color: derive(#383838, -40%);
}

#reminderListHolder {
    -fx-background-color: derive(#383838, -40%);
}

#reminderListHolder > .label {
    -fx-background-color: derive(#383838, -40%);
}

.error {
    -fx-text-fill: #d06651 !important; /* The error class should always override the default text-fill style */
}
```
###### \resources\view\ReminderCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="reminder" styleClass="cell_big_label" text="\$reminder" />
            </HBox>
            <Label fx:id="date" styleClass="cell_big_label" text="\$date" />
            <Label fx:id="time" styleClass="cell_big_label" text="\$time" />
            <Label fx:id="status" styleClass="cell_big_label" />
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\reminderNormal.css
``` css
.list-cell {
     -fx-label-padding: 0 0 0 0;
     -fx-graphic-text-gap : 0;
     -fx-background-radius: 18 18 18 18;
     -fx-border-radius: 18 18 18 18;
     -fx-padding: 10px;
     -fx-background-insets: 10px, 10px;
     -fx-background-color: transparent, -fx-background;
 }

 .list-cell:filled {
     -fx-background-color: derive(#006400, 0%);
 }

 .list-cell:filled:selected {
     -fx-background-color: derive(#006400, +40%);
 }

 .list-cell:filled:selected #cardPane {
     -fx-border-color: derive(#006400, +80%);
     -fx-border-width: 3;
     -fx-border-radius: 18 18 18 18;
 }

 .list-cell .label {
     -fx-text-fill: white;
 }
```
###### \resources\view\reminderToday.css
``` css
.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-radius: 18 18 18 18;
    -fx-border-radius: 18 18 18 18;
    -fx-padding: 10px;
    -fx-background-insets: 10px, 10px;
    -fx-background-color: transparent, -fx-background;
}

.list-cell:filled {
    -fx-background-color: derive(#dc143c, 0%);
}

.list-cell:filled:selected {
    -fx-background-color: derive(#dc143c, +30%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: derive(#dc143c, +80%);
    -fx-border-width: 3;
    -fx-border-radius: 18 18 18 18;
}

.list-cell .label {
    -fx-text-fill: white;
}
```
###### \resources\view\reminderWithinThreeDays.css
``` css
.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-background-radius: 18 18 18 18;
    -fx-border-radius: 18 18 18 18;
    -fx-padding: 10px;
    -fx-background-insets: 10px, 10px;
    -fx-background-color: transparent, -fx-background;
}

.list-cell:filled {
    -fx-background-color: derive(#ff8c00, 0%);
}

.list-cell:filled:selected {
    -fx-background-color: derive(#ff8c00, +30%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: derive(#ff8c00, +80%);
    -fx-border-width: 3;
    -fx-border-radius: 18 18 18 18;
}

.list-cell .label {
    -fx-text-fill: white;
}
```
