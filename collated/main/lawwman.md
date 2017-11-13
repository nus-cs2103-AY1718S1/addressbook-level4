# lawwman
###### \java\seedu\address\logic\commands\OverdueListCommand.java
``` java
/**
 * Lists all persons in the address book with overdue debt to the user.
 */
public class OverdueListCommand extends Command {

    public static final String COMMAND_WORD = "overduelist";
    public static final String COMMAND_WORD_ALIAS = "ol";

    public static final String MESSAGE_SUCCESS = "Listed all debtors with overdue debt.";

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.deselectPerson();
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS);
        String currentList = listObserver.getCurrentListName();
        return new CommandResult(currentList + MESSAGE_SUCCESS);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> debt} into an {@code Optional<Debt>} if {@code debt} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Debt> parseDebt (Optional<String> debt) throws IllegalValueException {
        requireNonNull(debt);
        return debt.isPresent() ? Optional.of(new Debt(debt.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> deadline} into an {@code Optional<Deadline>} if {@code Deadline}
     * is present.
     * Meant for parsing for Add command.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> deadline) throws IllegalValueException {
        requireNonNull(deadline);
        return deadline.isPresent() ? Optional.of(new Deadline(deadline.get()))
                : Optional.of(new Deadline(Deadline.NO_DEADLINE_SET));
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> deadline} into an {@code Optional<Deadline>} if {@code Deadline}
     * is present.
     * Meant for parsing for Edit command.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Deadline> parseDeadlineForEdit(Optional<String> deadline)
            throws IllegalValueException {
        requireNonNull(deadline);
        return deadline.isPresent() ? Optional.of(new Deadline(deadline.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> interest} into an {@code Optional<Interest>} if {@code Interest}
     * is present.
     * Meant for parsing for Add command.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Interest> parseInterest(Optional<String> interest) throws IllegalValueException {
        requireNonNull(interest);
        return interest.isPresent() ? Optional.of(new Interest(interest.get()))
                : Optional.of(new Interest(Interest.NO_INTEREST_SET));
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> Interest} into an {@code Optional<Interest} if {@code Interest}
     * is present.
     * Meant for parsing for Edit command.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Interest> parseInterestForEdit(Optional<String> interest)
            throws IllegalValueException {
        requireNonNull(interest);
        return interest.isPresent() ? Optional.of(new Interest(interest.get())) : Optional.empty();
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a person to the overdue debt list in the address book.
     * @return ReadOnly newOverduePerson
     */
    public ReadOnlyPerson addOverdueDebtPerson(ReadOnlyPerson p) {
        int index;
        index = persons.getIndexOf(p);

        Person newOverduePerson = new Person(p);
        newOverduePerson.setHasOverdueDebt(true);
        try {
            updatePerson(p, newOverduePerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot be a duplicate");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("This is not possible as prior checks have been done");
        }
        return persons.getReadOnlyPerson(index);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Updates {@code key} to exclude {@code key} from the overdue list in this {@code AddressBook}.
     * @return ReadOnly newOverdueDebtPerson
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public ReadOnlyPerson removeOverdueDebtPerson(ReadOnlyPerson key) throws PersonNotFoundException {
        int index;
        index = persons.getIndexOf(key);

        Person newOverdueDebtPerson = new Person(key);
        newOverdueDebtPerson.setHasOverdueDebt(false);
        persons.remove(key);
        try {
            persons.add(index, newOverdueDebtPerson);
        } catch (DuplicatePersonException e) {
            assert false : "This is not possible as prior checks have"
                    + " been done to ensure AddressBook does not have duplicate persons";
        }
        return persons.getReadOnlyPerson(index);
    }

    //// tag-level operations

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Deletes a specific person from overdue debt list in the AddressBook.
     * @param target to be removed from overdue list.
     * @return removedOverdueDebtPerson
     * @throws PersonNotFoundException if no person is found.
     */
    @Override
    public synchronized ReadOnlyPerson removeOverdueDebtPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        ReadOnlyPerson overdueDebtPerson = addressBook.removeOverdueDebtPerson(target);
        updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS);
        indicateAddressBookChanged();
        return overdueDebtPerson;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Adds a specific person to overdue list in the AddressBook.
     * @param person to be updated.
     * @return overdueDebtPerson
     * @throws DuplicatePersonException if this operation causes a contact to be a duplicate of another.
     */
    @Override
    public synchronized ReadOnlyPerson addOverdueDebtPerson(ReadOnlyPerson person) {
        ReadOnlyPerson overdueDebtPerson = addressBook.addOverdueDebtPerson(person);
        updateFilteredOverduePersonList(PREDICATE_SHOW_ALL_OVERDUE_PERSONS);
        indicateAddressBookChanged();
        return overdueDebtPerson;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Reads the masterlist and updates the overdue list accordingly.
     */
    public void syncOverdueList() {
        filteredOverduePersons = new FilteredList<>(this.addressBook.getOverduePersonList());
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the overdue list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredOverduePersonList() {
        setCurrentListName("overduelist");
        syncOverdueList();
        filteredOverduePersons.setPredicate(currentPredicate);
        return FXCollections.unmodifiableObservableList(filteredOverduePersons);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Filters {@code filteredOverduePersons} according to given {@param predicate}
     * @return size of current displayed filtered list.
     */
    @Override
    public int updateFilteredOverduePersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        currentPredicate = predicate;
        filteredOverduePersons.setPredicate(predicate);
        return filteredOverduePersons.size();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Subscribe
    public void handleLoginUpdateDebt(LoginAppRequestEvent event) {
        // login is successful
        if (event.getLoginStatus() == true) {
            for (ReadOnlyPerson person : allPersons) {
                if (!person.getInterest().value.equals("No interest set.")
                        && (person.checkLastAccruedDate(new Date()) != 0)) {
                    updateDebtFromInterest(person, person.checkLastAccruedDate(new Date()));
                }
            }
        }
    }
}
```
###### \java\seedu\address\model\person\DateBorrow.java
``` java
/**
 * Represents the date of when the Person was instantiated in the address book, i.e. the date
 * the Person borrows money.
 * Guarantees: immutable;
 */
public class DateBorrow {

    public final String value;

    public DateBorrow() {
        Date date = new Date();
        value = formatDate(date);
    }

    /**
     * Creates a copy of the DateBorrow object with a set date.
     * @param date must be a valid date
     */
    public DateBorrow(String date) {
        value = date;
    }

    public Date getDate() {
        return convertStringToDate(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateBorrow // instanceof handles nulls
                && this.value.equals(((DateBorrow) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Deadline.java
``` java
/**
 * Represents the deadline of the debt of a person in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */

public class Deadline {

    public static final String DASH_CHARACTER = "-";
    public static final String NO_DEADLINE_SET = "No deadline set.";
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline can only contain input of the format XX-XX-XXXX, taking X as an integer.";

    public final String value; // format of DD-MM-YYYY.
    public final String valueToDisplay; // format of DAY, DD MM, 'Year' YYYY.
    private String[] valueParsed;

    /**
     * Validates given Deadline. If no deadline was entered by user, value will read "empty" by
     * default. Else, it will store the date of the deadline.
     *
     * @throws IllegalValueException if given deadline is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (trimmedDeadline.equals(NO_DEADLINE_SET)) {
            this.value = this.valueToDisplay = trimmedDeadline;
        } else {
            if (!isValidDeadline(trimmedDeadline)) {
                throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            this.value = trimmedDeadline;
            this.valueParsed = trimmedDeadline.split(DASH_CHARACTER);
            this.valueToDisplay = formatDate(trimmedDeadline);
        }
    }

    /**
     * Validates if deadline created is before date borrowed.
     */
    public void checkDateBorrow(Date dateBorrow) throws IllegalValueException {
        if (valueToDisplay.equals(NO_DEADLINE_SET)) {
            return;
        } else if (!compareDates(dateBorrow, convertStringToDate(valueToDisplay))) {
            throw new IllegalValueException("Deadline cannot be before Date borrowed");
        }
    }

    /**
     * Returns true if a given string is a valid person deadline.
     */
    public static boolean isValidDeadline(String test) {
        return isValidDateFormat(test);
    }

    @Override
    public String toString() {
        return valueToDisplay;
    }

    public String getDay() {
        return valueParsed[0];
    }

    public String getMonth() {
        return valueParsed[1];
    }

    public String getYear() {
        return valueParsed[2];
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    /**
     * Compares two {@code Deadline} objects and returns 1 if second date is earlier, -1 if later or same.
     * No deadline set will default to be later than any given deadline.
     * @param other the {@code Deadline} object to compare to.
     * @return an integer value of 1 if second date is earlier, -1 if second date is later or same, 0 if both deadlines
     * have not been set.
     */
    public int compareTo(Deadline other) {
        if (this.valueToDisplay.equals(NO_DEADLINE_SET)) {
            if (other.valueToDisplay.equals(NO_DEADLINE_SET)) {
                return 0;
            } else {
                return 1;
            }
        } else if (other.valueToDisplay.equals(NO_DEADLINE_SET)
                || compareDates(convertStringToDate(this.valueToDisplay), convertStringToDate(other.valueToDisplay))) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Debt.java
``` java
/**
 * Represents a Person's debt or amount owed in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDebt(String)}
 */
public class Debt {

    public static final String MESSAGE_DEBT_CONSTRAINTS = "Debt must have at least 1 digit and be either "
            + "a positive integer or a positive number with two decimal places";
    public static final String MESSAGE_DEBT_MAXIMUM = "Debt cannot exceed $" + Double.MAX_VALUE;
    // validation regex validates empty string. Check for presence of at least 1 digit is needed.
    public static final String DEBT_VALIDATION_REGEX = "^(?=.*\\d)\\d*(?:\\.\\d\\d)?$";
    public static final String DEBT_ZER0_VALUE = "0";
    private String value;

    /**
     * Validates given debt.
     *
     * @throws IllegalValueException if given debt string is invalid.
     */
    public Debt(String debt) throws IllegalValueException {
        requireNonNull(debt);
        String trimmedDebt = debt.trim();
        if (!isValidDebt(trimmedDebt)) {
            throw new IllegalValueException(MESSAGE_DEBT_CONSTRAINTS);
        }
        this.value = String.format("%.2f", Double.valueOf(trimmedDebt));
    }

    /**
     * Validates given debt.
     *
     * @throws IllegalValueException if given debt double is invalid.
     */
    public Debt(Double debt) throws IllegalValueException {
        requireNonNull(debt);
        String debtInString = String.format("%.2f", Double.valueOf(debt));
        if (!isValidDebt(debtInString)) {
            throw new IllegalValueException(MESSAGE_DEBT_CONSTRAINTS);
        }
        this.value = debtInString;
    }

    /**
     * Returns true if a given string is a valid person debt.
     */
    public static boolean isValidDebt(String test) throws IllegalValueException {
        if (test.matches(DEBT_VALIDATION_REGEX) && Double.valueOf(test) > Double.MAX_VALUE) {
            throw new IllegalValueException(MESSAGE_DEBT_MAXIMUM);
        }
        return test.matches(DEBT_VALIDATION_REGEX) && test.length() >= 1;
    }

    /**
     * Returns the double value represented by the string {@code value}
     */
    public double toNumber() {
        return Double.valueOf(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Debt // instanceof handles nulls
                && this.value.equals(((Debt) other).value)); // state check
    }

    public int compareTo(Debt other) {
        return (int) (this.toNumber() - other.toNumber());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Interest.java
``` java
/**
 * Represents a Person's interest on his / her debt in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInterest(String)}
 */
public class Interest {

    public static final String NO_INTEREST_SET = "No interest set.";
    public static final String MESSAGE_INTEREST_CONSTRAINTS =
            "Interest can only contain numbers, and should have 1 or more digits";
    public static final String INTEREST_VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Validates given interest.
     *
     * @throws IllegalValueException if given interest string is invalid.
     */
    public Interest(String interest) throws IllegalValueException {
        requireNonNull(interest);
        String trimmedInterest = interest.trim();
        if (trimmedInterest.equals(NO_INTEREST_SET)) {
            this.value = trimmedInterest;
        } else {
            if (!isValidInterest(trimmedInterest)) {
                throw new IllegalValueException(MESSAGE_INTEREST_CONSTRAINTS);
            }
            this.value = trimmedInterest;
        }
    }

    /**
     * Returns true if a given string is a valid person interest.
     */
    public static boolean isValidInterest(String test) {
        return test.matches(INTEREST_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Interest // instanceof handles nulls
                && this.value.equals(((Interest) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Sets current interest of a person to the given Interest.
     * @param interest must not be null.
     */
    public void setInterest(Interest interest) {
        this.interest.set(requireNonNull(interest));
    }

    @Override
    public ObjectProperty<Interest> interestProperty() {
        return interest;
    }

    @Override
    public Interest getInterest() {
        return interest.get();
    }

    /**
     * Sets current debt of a person to the given Debt.
     * @param debt must not be null.
     */
    public void setDebt(Debt debt) {
        this.debt.set(requireNonNull(debt));
    }

    @Override
    public ObjectProperty<Debt> debtProperty() {
        return debt;
    }

    @Override
    public Debt getDebt() {
        return debt.get();
    }

    /**
     * Sets date borrowed of a person in the given {@code dateBorrow}.
     * @param dateBorrow must not be null.
     */
    public void setDateBorrow(DateBorrow dateBorrow) {
        this.dateBorrow.set(requireNonNull(dateBorrow));
    }

    @Override
    public ObjectProperty<DateBorrow> dateBorrowProperty() {
        return dateBorrow;
    }

    @Override
    public DateBorrow getDateBorrow() {
        return dateBorrow.get();
    }

    /**
     * Sets associated deadline of a person to the given Deadline.
     * @param deadline must not be null.
     */
    public void setDeadline(Deadline deadline) {
        this.deadline.set(requireNonNull(deadline));
    }

    @Override
    public ObjectProperty<Deadline> deadlineProperty() {
        return deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline.get();
    }

    /**
     * Sets date of last accrued date.
     * @param lastAccruedDate must not be null.
     */
    public void setLastAccruedDate(Date lastAccruedDate) {
        requireNonNull(lastAccruedDate);
        this.lastAccruedDate = lastAccruedDate;
    }

    @Override
    public Date getLastAccruedDate() {
        return lastAccruedDate;
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns boolean status of a person's debt status.
     */
    @Override
    public boolean hasOverdueDebt() {
        return hasOverdueDebt;
    }

    /**
     * Sets boolean status of a person's debt status using the value of {@param hasOverdueDebt}.
     */
    @Override
    public void setHasOverdueDebt(boolean hasOverdueDebt) {
        this.hasOverdueDebt = hasOverdueDebt;
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Calculates increase in debt based on interest rate and amount of months
     */
    @Override
    public String calcAccruedAmount(int differenceInMonths) {
        this.lastAccruedDate = new Date(); // update last accrued date
        double principal = this.getDebt().toNumber();
        double interestRate = (double) Integer.parseInt(this.getInterest().toString()) / 100;
        double accruedInterest = principal * Math.pow((1 + interestRate), differenceInMonths) - principal;
        return String.format("%.2f", accruedInterest);
    }

    /**
     * Compares date of last accrued against current date.
     * @return number of months the current date is ahead of last accrued date. Returns 0 if
     * there is no need to increment debt.
     */
    @Override
    public int checkLastAccruedDate(Date currentDate) {
        if (lastAccruedDate.before(currentDate)) {
            return DateUtil.getNumberOfMonthBetweenDates(currentDate, lastAccruedDate);
        } else {
            return 0;
        }
    }

```
###### \java\seedu\address\model\util\DateUtil.java
``` java
/**
 * Contains utility methods for formatting dates fields in {@code Person}.
 */
public class DateUtil {

    public static final String DATE_FORMAT = "E',' dd MMM', Year' yyyy";
    // for input format of DD-MM-YYYY
    public static final String DATE_VALIDATION_REGEX = "([0-3][0-9](-))([0-1][0-9](-))(\\d{4})";
    public static final int JAN = 1;
    public static final int FEB = 2;
    public static final int MAR = 3;
    public static final int APR = 4;
    public static final int MAY = 5;
    public static final int JUN = 6;
    public static final int JUL = 7;
    public static final int AUG = 8;
    public static final int SEP = 9;
    public static final int OCT = 10;
    public static final int NOV = 11;
    public static final int DEC = 12;

    /**
     * Formats a Date class to a string value of format DAY, DD MM, 'Year' YYYY.
     * @param date the date to format
     * @return formatted date value
     */
    public static String formatDate(Date date) {
        SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
        return ft.format(date);
    }
    /**
     * Formats String date of format DD-MM-YYYY to DAY, DD MM, 'Year' YYYY.
     * @param dateToFormat the date to format
     * @return formatted date value
     */
    public static String formatDate(String dateToFormat) {
        SimpleDateFormat ft = new SimpleDateFormat(DATE_FORMAT);
        int year = Integer.parseInt(dateToFormat.substring(6, 10));
        int day = Integer.parseInt(dateToFormat.substring(0, 2));
        int month = Integer.parseInt(dateToFormat.substring(3, 5)) - 1; // GregorianCalendar uses 0-based for month
        Date date = new GregorianCalendar(year, month, day).getTime();
        return ft.format(date);
    }

    /**
     * Converts a string of format DAY, DD MM, 'Year' YYYY to a Date class
     */
    public static Date convertStringToDate(String date) {
        int day = Integer.parseInt(date.substring(5, 7));
        int month = getMonthFromString(date.substring(8, 11)) - 1;
        int year = Integer.parseInt(date.substring(18, 22));
        Date dateToReturn = new GregorianCalendar(year, month, day).getTime();
        return dateToReturn;
    }

    /**
     * checks if date of format DD-MM-YYYY is valid.
     * @param dateToValidate the date to validate
     * @return boolean to determine if date is valid
     */
    public static boolean isValidDateFormat(String dateToValidate) {
        requireNonNull(dateToValidate);
        if (!dateToValidate.matches(DATE_VALIDATION_REGEX)) {
            return false;
        } else {
            int month = Integer.parseInt(dateToValidate.substring(3, 5));
            int day = Integer.parseInt(dateToValidate.substring(0, 2));
            int year = Integer.parseInt(dateToValidate.substring(6, 10));
            boolean valid;
            switch (month) {
            case APR:
            case JUN:
            case SEP:
            case NOV:
                if (day > 30) {
                    valid = false;
                } else {
                    valid = true;
                }
                break;
            case FEB:
                if (checkLeapYear(year)) {
                    if (day <= 29) {
                        valid = true;
                    } else {
                        valid = false;
                    }
                } else {
                    if (day <= 28) {
                        valid = true;
                    } else {
                        valid = false;
                    }
                }
                break;
            default:
                if (day > 31) {
                    valid = false;
                } else {
                    valid = true;
                }
                break;
            }
            return valid;
        }
    }


    /**
     * checks if date is a leap year
     * @param year the year to check if it is a leap year
     * @return boolean to determine if date is a leap year
     */
    public static boolean checkLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else {
            if ((year % 100 == 0) && (year % 400 != 0)) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Compares the 2 dates. If date1 is before date2, a boolean value of true will be returned.
     * Vice versa if date2 is before date1.
     * If date1 is on the same day as date2, the return value would still be true.
     */
    public static boolean compareDates(Date date1, Date date2) {
        if (date2.before(date1)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * generate a date that is 1 month behind current date
     */
    public static Date generateOutdatedDebtDate(Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * Get the difference in number of months between the 2 dates.
     * @param date1 is assumed to be AFTER date2.
     * @return return difference. If there is no difference, return 0.
     */
    public static int getNumberOfMonthBetweenDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        // if date1 is only 1 year ahead of date2
        if (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) == 1) {
            return cal1.get(Calendar.MONTH) + 1
                    + (11 - cal2.get(Calendar.MONTH));
        } else if (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) > 1) {
            return cal1.get(Calendar.MONTH) + 1
                    + (11 - cal2.get(Calendar.MONTH))
                    + (12 * (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) - 1));
        } else {
            return cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
        }
    }

    public static int getMonthFromString(String month) {
        int monthToReturn;
        switch(month) {
        case "Jan":
            monthToReturn = JAN;
            break;
        case "Feb":
            monthToReturn = FEB;
            break;
        case "Mar":
            monthToReturn = MAR;
            break;
        case "Apr":
            monthToReturn = APR;
            break;
        case "May":
            monthToReturn = MAY;
            break;
        case "Jun":
            monthToReturn = JUN;
            break;
        case "Jul":
            monthToReturn = JUL;
            break;
        case "Aug":
            monthToReturn = AUG;
            break;
        case "Sep":
            monthToReturn = SEP;
            break;
        case "Oct":
            monthToReturn = OCT;
            break;
        case "Nov":
            monthToReturn = NOV;
            break;
        case "Dec":
            monthToReturn = DEC;
            break;
        default:
            monthToReturn = 0;
            break;
        }
        return monthToReturn;
    }
}
```
