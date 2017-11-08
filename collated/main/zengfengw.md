# zengfengw
###### \java\seedu\address\logic\commands\BirthdayCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Age;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 + * Changes the birthday of an existing person in the address book.
 + */

public class BirthdayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "birthday";
    public static final String COMMAND_ALIAS = "b";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the birthday of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing birthday will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_BIRTHDAY + "[BIRTHDAY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BIRTHDAY + "01-01-1995";

    public static final String MESSAGE_ADD_BIRTHDAY_SUCCESS = "Added birthday to Person: %1$s";
    public static final String MESSAGE_DELETE_BIRTHDAY_SUCCESS = "Removed birthday from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Birthday birthday;

    /**
     * @param index of the person in the filtered person list to edit birthday
     * @param birthday of the person
     */
    public BirthdayCommand(Index index, Birthday birthday) {
        requireNonNull(index);
        requireNonNull(birthday);

        this.index = index;
        this.birthday = birthday;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Age age = new Age(birthday.value);
        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), birthday, age,
                personToEdit.getPhoto(), personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing.");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * @param personToEdit
     * @return
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!birthday.value.isEmpty()) {
            return String.format(MESSAGE_ADD_BIRTHDAY_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_BIRTHDAY_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }


        //instance of handle nulls
        if (!(other instanceof BirthdayCommand)) {
            return false;
        }

        //state check
        BirthdayCommand e = (BirthdayCommand) other;
        return index.equals(e.index)
                && birthday.equals(e.birthday);
    }
}
```
###### \java\seedu\address\logic\commands\UpcomingBirthdayCommand.java
``` java
package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists the birthdays in chronological order from the current date.
 */
public class UpcomingBirthdayCommand extends Command {
    public static final String COMMAND_WORD = "upcomingbirthday";
    public static final String COMMAND_ALIAS = "ub";

    public static final String MESSAGE_SUCCESS = "Upcoming birthdays are shown.";
    private static final String MESSAGE_EMPTY_LIST = "Contact list is empty.";

    private ArrayList<ReadOnlyPerson> contactList;

    public UpcomingBirthdayCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        Boolean ifListEmpty = model.ifListIsEmpty(contactList);
        if (!ifListEmpty) {
            model.sortListByUpcomingBirthday(contactList);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_EMPTY_LIST);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case BirthdayCommand.COMMAND_ALIAS:
        case BirthdayCommand.COMMAND_WORD:
            return new BirthdayCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case UpcomingBirthdayCommand.COMMAND_ALIAS:
        case UpcomingBirthdayCommand.COMMAND_WORD:
            return new UpcomingBirthdayCommand();
```
###### \java\seedu\address\logic\parser\BirthdayCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BirthdayCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Birthday;

/**
 * Birthday Command Parser
 */
public class BirthdayCommandParser implements Parser<BirthdayCommand> {
    /**
     * Parse the given {@code String} of arguments in the context of the BirthdayCommand
     * and returns a BirthdayCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BIRTHDAY);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdayCommand.MESSAGE_USAGE));
        }

        String birthday = argMultimap.getValue(PREFIX_BIRTHDAY).orElse("");

        try {
            Birthday temp = new Birthday(birthday);
            return new BirthdayCommand(index, temp);
        } catch (IllegalValueException ive) {
            throw new ParseException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }

    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Sorts the address book in chronological order according to the birthday from the current date.
     * @param contactList
     * @throws CommandException
     */
    public void sortListByUpcomingBirthday(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
        contactList.addAll(filteredPersons);
        Collections.sort(contactList, new BirthdayComparator());
        ArrayList<ReadOnlyPerson> tempList = new ArrayList<>();
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        for (ReadOnlyPerson person : contactList) {
            if ((person.getBirthday().value.length() != 0)
                    && (Integer.parseInt(person.getBirthday().value.substring(0, 2)) >= day)
                    && (Integer.parseInt(person.getBirthday().value.substring(3, 5)) == (month + 1))) {
                tempList.add(person);
            }

        }
        for (ReadOnlyPerson person : contactList) {
            if ((person.getBirthday().value.length() != 0)
                    && (Integer.parseInt(person.getBirthday().value.substring(3, 5)) > (month + 1))) {
                tempList.add(person);
            }

        }
        for (ReadOnlyPerson person : contactList) {
            if ((person.getBirthday().value.length() != 0)
                    && (Integer.parseInt(person.getBirthday().value.substring(3, 5)) < (month + 1))) {
                tempList.add(person);
            }

        }
        for (ReadOnlyPerson person : contactList) {
            if ((person.getBirthday().value.length() != 0)
                    && (Integer.parseInt(person.getBirthday().value.substring(0, 2)) < day)
                    && (Integer.parseInt(person.getBirthday().value.substring(3, 5)) == (month + 1))) {
                tempList.add(person);
            }

        }
        for (ReadOnlyPerson person : contactList) {
            if ((person.getBirthday().value.length() == 0)) {
                tempList.add(person);
            }

        }

        contactList = tempList;

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }
```
###### \java\seedu\address\model\person\Age.java
``` java
package seedu.address.model.person;

import java.util.Calendar;

/**
 * Represents a Person's age in the address book.
 */
public class Age {

    public final String value;

    public Age(String birthday) {
        if (birthday.length() == 0) {
            this.value = "";
            return;

        } else {
            String result = birthday.substring(6);
            int birthYear = Integer.parseInt(result);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            String howOld = Integer.toString(year - birthYear);

            if (year - birthYear == 1) {
                this.value = "(" + howOld + " year old" + ")";
            } else {
                this.value = "(" + howOld + " years old" + ")";
            }
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Age //instanceof handles nulls
                && this.value.equals(((Age) other).value)); //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is always valid
 */

public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person's birthday have to be in the format DD-MM-YYYY";
    public static final String BIRTHDAY_VALIDATION_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])[-](0[1-9]|1[012])[-](19|20)\\d\\d$";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */

    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (!isValidBirthday(birthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = birthday;
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String birthday) {
        if (birthday.matches(BIRTHDAY_VALIDATION_REGEX) || birthday.matches("")) {
            if (birthday.matches("")) {
                return true;
            }
            if (Integer.parseInt(birthday.substring(6, 10)) > 2016) {
                return false;
            }
            if ((Integer.parseInt(birthday.substring(6, 10)) == 2016)
                && (Integer.parseInt(birthday.substring(3, 5)) > 10)) {
                return false;
            }
            return true;

        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); //state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\BirthdayComparator.java
``` java
package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares two birthday string according to lexicographical order.
 */
public class BirthdayComparator implements Comparator<ReadOnlyPerson> {

    @Override
    public int compare(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
        String newFirstNum = getNewStringBirthdayFormat(firstPerson);
        String newSecondNum = getNewStringBirthdayFormat(secondPerson);
        if (newFirstNum.equals("") || newSecondNum.equals("")) {
            return newSecondNum.compareTo(newFirstNum);
        } else {
            return newFirstNum.compareTo(newSecondNum);
        }
    }

    public static String getNewStringBirthdayFormat(ReadOnlyPerson person) {
        if (person.getBirthday().toString().equals("")) {
            return "";
        } else {
            String dayString = person.getBirthday().toString().substring(0, 2);
            String monthString = person.getBirthday().toString().substring(3, 5);
            return monthString + dayString; // Return String format mmdd
        }
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    private ObjectProperty<Birthday> birthday;
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.birthday = new SimpleObjectProperty<>(birthday);
        this.age = new SimpleObjectProperty<>(age);
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
###### \java\seedu\address\model\person\Person.java
``` java
    //@Override
    public void setAge(Age age) {
        this.age.set(requireNonNull(age));
    }

    @Override
    public ObjectProperty<Age> ageProperty() {
        return age;
    }

    @Override
    public Age getAge() {
        return age.get();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
        this.setBirthday(replacement.getBirthday());
        this.setAge(replacement.getAge());
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
    ObjectProperty<Age> ageProperty();
    Age getAge();
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                && other.getBirthday().equals(this.getBirthday())
                && other.getAge().equals(this.getAge());
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                .append(" Birthday: ")
                .append(getBirthday())
                .append(" Age: ")
                .append(getAge())
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String birthday;
    @XmlElement(required = true)
    private String age;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        birthday = source.getBirthday().value;
        age = source.getAge().value;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Birthday birthday = new Birthday(this.birthday);
        final Age age = new Age(this.birthday);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        age.textProperty().bind(Bindings.convert(person.ageProperty()));
```
