# 17navasaw
###### \java\seedu\address\commons\core\index\Index.java
``` java
    @Override
    public int compareTo(Object o) {
        Index compareIndex = (Index) o;

        return (compareIndex).getZeroBased() - this.zeroBasedIndex;
    }
}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns a {@code String} consisting of each object in a list separated by a semicolon
     * @param list The list of objects
     * @param <E> The data type of the object involved in the list
     * @return The {@code String} consisting of each object in the list
     */
    public static <E> String convertListToString(List<E> list) {
        StringBuilder listStringBuilder = new StringBuilder();

        for (E obj: list) {
            listStringBuilder.append(obj.toString()).append("; ");
        }

        return listStringBuilder.toString();
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    private final ArrayList<Index> targetIndices;

    public DeleteCommand(ArrayList<Index> targetIndices) {
        Collections.sort(targetIndices);
        this.targetIndices = targetIndices;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (int i = 0; i < targetIndices.size(); i++) {
            Index targetIndex = targetIndices.get(i);

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(MESSAGE_DELETE_PERSON_SUCCESS);
    }

```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setEmails(Set<Email> emails) {
            this.emails = emails;
        }

        public Optional<Set<Email>> getEmails() {
            return Optional.ofNullable(emails);
        }

```
###### \java\seedu\address\logic\commands\LocateCommand.java
``` java
/**
 * Locates a person's address by showing its location on Google Maps.
 */
public class LocateCommand extends Command {

    public static final String COMMAND_WORD = "locate";
    public static final String COMMAND_ALIAS = "lc";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ": Locates the address of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Located the Address of Person %1$s";

    private final Index targetIndex;

```
###### \java\seedu\address\logic\commands\ScheduleCommand.java
``` java
    /**
     * Returns a set of person names involved in the scheduling of the activity.
     * @param model Model of address book.
     * @param indices Set of indices indicating the people from last shown list.
     * @return {@code schedulePersonNames} which contains the set of person names involved in the scheduling.
     * @throws CommandException
     */
    private Set<Name> fillSchedulePersonNamesSet(Model model, Set<Index> indices) throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        Set<Name> schedulePersonNames = new HashSet<>();

        for (Index index: indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson schedulePerson = lastShownList.get(index.getZeroBased());
            schedulePersonNames.add(schedulePerson.getName());
        }

        return schedulePersonNames;
    }

```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
    public DeleteCommand parse(String args) throws ParseException {
        String invalidCommandString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        try {
            String trimmedArgs = args.trim();
            String[] indicesInString = trimmedArgs.split("\\s+");

            ArrayList<Index> indices = new ArrayList<>();
            for (int i = 0; i < indicesInString.length; i++) {
                Index index = ParserUtil.parseIndex(indicesInString[i]);

                // Check if there are repeated indices
                if (i >= secondParsedIndex) {
                    for (Index indexInList: indices) {
                        if (indexInList.equals(index)) {
                            throw new ParseException(invalidCommandString);
                        }
                    }
                }
                indices.add(index);
            }

            return new DeleteCommand(indices);
        } catch (IllegalValueException ive) {
            throw new ParseException(invalidCommandString);
        }
    }

}
```
###### \java\seedu\address\logic\parser\LocateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new LocateCommand object
 */
public class LocateCommandParser implements Parser<LocateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocateCommand
     * and returns an LocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Set<Email> parseEmails(Collection<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final Set<Email> emailSet = new HashSet<>();
        for (String emailName : emails) {
            emailSet.add(new Email(emailName));
        }
        return emailSet;
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setSchedulesToRemind() {
        ObservableList<Schedule> scheduleList = this.schedules.asObservableList();
        Set<Schedule> schedulesToRemind = new HashSet<>();

        for (Schedule schedule: scheduleList) {
            if (Schedule.doesScheduleNeedReminder(schedule)) {
                schedulesToRemind.add(schedule);
            }
        }

        this.schedulesToRemind.setSchedules(schedulesToRemind);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Schedule> getScheduleToRemindList() {
        return schedulesToRemind.asObservableList();
    }

```
###### \java\seedu\address\model\person\address\Address.java
``` java
    private Block block;
    private Street street;
    private Unit unit;
    private PostalCode postalCode;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string follows invalid format.
     */
    public Address(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();

        if (!hasValidAddressFormat(trimmedAddress)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }

        splitAddressString(trimmedAddress);

        this.value = trimmedAddress;
    }

    /**
     * Splits Address into Block, Street, Unit[optional parameter] and PostalCode.
     */
    private void splitAddressString(String trimmedAddress) throws IllegalValueException {
        String[] tokens = trimmedAddress.split(",");
        int numTokens = tokens.length;

        assert ((numTokens == ADDRESS_TOKENS_WITHOUT_UNIT)
                || (numTokens == ADDRESS_TOKENS_WITH_UNIT)) : "The address should be split into 3 or 4 tokens.";

        block = new Block(tokens[0]);
        street = new Street(tokens[1]);

        if (numTokens == ADDRESS_TOKENS_WITHOUT_UNIT) {

            postalCode = new PostalCode(tokens[2]);

        } else if (numTokens == ADDRESS_TOKENS_WITH_UNIT) {

            unit = new Unit (tokens[2]);
            postalCode = new PostalCode(tokens[3]);
        }
    }

    /**
     * Returns true if a given string follows the valid format for address.
     */
    public static boolean hasValidAddressFormat(String test) {
        StringTokenizer tokenizer = new StringTokenizer(test, ADDRESS_FORMAT_DELIMITER);

        return ((tokenizer.countTokens() == ADDRESS_TOKENS_WITHOUT_UNIT)
                || (tokenizer.countTokens() == ADDRESS_TOKENS_WITH_UNIT));
    }

```
###### \java\seedu\address\model\person\address\Block.java
``` java
package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the block in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidBlock(String)}
 */
public class Block {

    public static final String MESSAGE_BLOCK_CONSTRAINTS =
            "Block can contain alphanumeric characters and spaces, and it should not be blank";
    private static final String BLOCK_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;

    public Block(String block) throws IllegalValueException {

        requireNonNull(block);
        String trimmedBlock = block.trim();

        if (!isValidBlock(trimmedBlock)) {
            throw new IllegalValueException(MESSAGE_BLOCK_CONSTRAINTS);
        }
        this.value = trimmedBlock;
    }

    /**
     * Returns true if a given string is a valid block in an address.
     */
    public static boolean isValidBlock(String test) {
        return test.matches(BLOCK_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\address\PostalCode.java
``` java
package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the postal code in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {

    public static final String MESSAGE_POSTALCODE_CONSTRAINTS =
            "Postal Code can contain alphanumeric characters and spaces, and it should not be blank";
    private static final String POSTALCODE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();

        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTALCODE_CONSTRAINTS);
        }
        this.value = trimmedPostalCode;
    }

    /**
     * Returns true if a given string is a valid postal code in an address.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTALCODE_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\address\Street.java
``` java
package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the street in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidStreet(String)}
 */
public class Street {
    public static final String MESSAGE_STREET_CONSTRAINTS =
            "Street can contain alphanumeric characters and spaces, and it should not be blank";
    private static final String STREET_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    public Street(String street) throws IllegalValueException {
        requireNonNull(street);
        String trimmedStreet = street.trim();

        if (!isValidStreet(trimmedStreet)) {
            throw new IllegalValueException(MESSAGE_STREET_CONSTRAINTS);
        }
        this.value = trimmedStreet;
    }

    /**
     * Returns true if a given string is a valid street in an address.
     */
    public static boolean isValidStreet (String test) {
        return test.matches(STREET_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\address\Unit.java
``` java
package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the unit in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnit(String)}
 */
public class Unit {
    public static final String MESSAGE_UNIT_CONSTRAINTS =
            "Unit must begin with '#' and be followed by 2 numeric strings separated by '-'";
    private static final String UNIT_VALIDATION_REGEX = "#[\\d\\.]+-[\\d\\.]+";

    public final String value;

    public Unit(String unit) throws IllegalValueException {
        requireNonNull(unit);
        String trimmedUnit = unit.trim();

        if (!isValidUnit(trimmedUnit)) {
            throw new IllegalValueException(MESSAGE_UNIT_CONSTRAINTS);
        }
        this.value = trimmedUnit;
    }

    /**
     * Returns true if a given string is a valid unit in an address.
     */
    public static boolean isValidUnit(String test) {
        return test.matches(UNIT_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\email\UniqueEmailList.java
``` java
package seedu.address.model.person.email;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of emails that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Email#equals(Object)
 */
public class UniqueEmailList {

    private final ObservableList<Email> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty EmailList.
     */
    public UniqueEmailList() {}

    /**
     * Creates a UniqueEmailList using given emails.
     * Enforces no nulls.
     */
    public UniqueEmailList(Set<Email> emails) {
        requireAllNonNull(emails);
        internalList.addAll(emails);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Emails in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Email> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Email> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueEmailList // instanceof handles nulls
                && this.internalList.equals(((UniqueEmailList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
    * Replaces this person's emails with the emails in the argument tag set.
    */
    public void setEmails(Set<Email> replacement) {
        emails.set(new UniqueEmailList(replacement));
    }

    @Override
    public ObjectProperty<UniqueEmailList> emailProperty() {
        return emails;
    }

    /**
     * Returns an immutable email set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Email> getEmails() {
        return Collections.unmodifiableSet(emails.get().toSet());
    }

```
###### \java\seedu\address\model\person\UniquePersonNameList.java
``` java
package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of person names that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Name#equals(Object)
 */
public class UniquePersonNameList {
    private final ObservableList<Name> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PersonNameList.
     */
    public UniquePersonNameList() {}

    /**
     * Creates a UniquePersonNameList using given names.
     * Enforces no nulls.
     */
    public UniquePersonNameList(Set<Name> personNames) {
        requireAllNonNull(personNames);
        internalList.addAll(personNames);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Names in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Name> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Name> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePersonNameList // instanceof handles nulls
                && this.internalList.equals(((UniquePersonNameList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\schedule\Schedule.java
``` java
    public void setActivity(Activity activity) {
        this.activity.set(activity);
    }

    public Set<Name> getPersonInvolvedNames() {
        return personInvolvedNames.get().toSet();
    }

    public ObjectProperty<UniquePersonNameList> getPersonInvolvedNamesProperty() {
        return personInvolvedNames;
    }

    public void setPersonInvolvedNames(Set<Name> personInvolvedNames) {
        this.personInvolvedNames.set(new UniquePersonNameList(personInvolvedNames));
    }

    /**
     * Returns true if {@code schedule} date is within 1 day from {@code currentDate}.
     */
    public static boolean doesScheduleNeedReminder(Schedule schedule) {
        LocalDate currentDate = LocalDate.now();
        logger.info("Current date: " + currentDate.toString());

        String scheduleDateString = schedule.getScheduleDate().value;

        LocalDate scheduleDateToAlter = currentDate;
        LocalDate scheduleDate = scheduleDateToAlter.withDayOfMonth(DateUtil.getDay(scheduleDateString))
                .withMonth(DateUtil.getMonth(scheduleDateString))
                .withYear(DateUtil.getYear(scheduleDateString));
        logger.info("Schedule date: " + scheduleDate.toString());

        LocalDate dayBeforeSchedule = scheduleDate.minusDays(1);
        final boolean isYearEqual = (dayBeforeSchedule.getYear() == currentDate.getYear());
        final boolean isMonthEqual = (dayBeforeSchedule.getMonthValue() == currentDate.getMonthValue());
        final boolean isDayEqual = (dayBeforeSchedule.getDayOfMonth() == currentDate.getDayOfMonth());

        if (isYearEqual && isMonthEqual && isDayEqual) {
            return true;
        } else {
            return false;
        }
    }

```
###### \java\seedu\address\model\schedule\UniqueScheduleList.java
``` java
    /**
     * Ensures every schedule in the argument list exists in this object.
     */
    public void mergeFrom(UniqueScheduleList from) {
        final Set<Schedule> alreadyInside = this.toSet();

        for (Schedule scheduleFrom : from.internalList) {
            boolean doesScheduleAlreadyExist = false;
            for (Schedule scheduleInside : alreadyInside) {
                if (scheduleFrom.equals(scheduleInside)) {
                    doesScheduleAlreadyExist = true;
                    break;
                }
            }
            if (!doesScheduleAlreadyExist) {
                this.internalList.add(scheduleFrom);
            }
        }
        assert CollectionUtil.elementsAreUnique(internalList);
    }

```
###### \java\seedu\address\model\schedule\UniqueScheduleList.java
``` java
    /**
     * Sorts the list from earliest to latest schedule.
     */
    public void sort() {
        FXCollections.sort(internalList, (schedule1, schedule2) -> {
            String schedule1DateInString = schedule1.getScheduleDate().value;
            String schedule2DateInString = schedule2.getScheduleDate().value;

            int schedule1Year = DateUtil.getYear(schedule1DateInString);
            int schedule2Year = DateUtil.getYear(schedule2DateInString);
            if (schedule1Year != schedule2Year) {
                return schedule1Year - schedule2Year;
            }

            int schedule1Month = DateUtil.getMonth(schedule1DateInString);
            int schedule2Month = DateUtil.getMonth(schedule2DateInString);
            if (schedule1Month != schedule2Month) {
                return schedule1Month - schedule2Month;
            }

            int schedule1Day = DateUtil.getDay(schedule1DateInString);
            int schedule2Day = DateUtil.getDay(schedule2DateInString);
            return schedule1Day - schedule2Day;
        });
    }

```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Returns a person names set containing the list of strings given.
     */
    public static Set<Name> getPersonNamesSet(String... strings) throws IllegalValueException {
        HashSet<Name> personNames = new HashSet<>();
        for (String s : strings) {
            personNames.add(new Name(s));
        }

        return personNames;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Email> getEmailSet(String... strings) throws IllegalValueException {
        HashSet<Email> emails = new HashSet<>();
        for (String s : strings) {
            emails.add(new Email(s));
        }

        return emails;
    }

```
###### \java\seedu\address\storage\XmlAdaptedEmail.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.email.Email;

/**
 * JAXB-friendly adapted version of the Email.
 */
public class XmlAdaptedEmail {
    @XmlValue
    private String emailName;

    /**
     * Constructs an XmlAdaptedEmail.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEmail() {}

    /**
     * Converts a given Email into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEmail(Email source) {
        emailName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Email object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Email toModelType() throws IllegalValueException {
        return new Email(emailName);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Email> personEmails = new ArrayList<>();
        for (XmlAdaptedEmail email : emails) {
            personEmails.add(email.toModelType());
        }
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        final List<Schedule> personSchedules = new ArrayList<>();
        for (XmlAdaptedSchedule schedule : scheduled) {
            personSchedules.add(schedule.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Country country = new Country(phone.getCountryCode());
        final Set<Email> emails = new HashSet<>(personEmails);
        final Address address = new Address(this.address);
        final List<Schedule> schedules = new ArrayList<>(personSchedules);
        final Set<Tag> tags = new HashSet<>(personTags);

        logger.info("Name: " + name.toString()
                    + " Phone: " + phone.value
                    + " Country: " + country.toString()
                    + " Emails: " + StringUtil.convertListToString(personEmails)
                    + " Address: " + address.toString()
                    + " Schedules: " + StringUtil.convertListToString(personSchedules)
                    + " Tags: " + StringUtil.convertListToString(personTags));

        return new Person(name, phone, country, emails, address, schedules, tags);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedSchedule.java
``` java
    public XmlAdaptedSchedule(Schedule source) {
        ScheduleDate scheduleDate = source.getScheduleDate();
        Activity activity = source.getActivity();
        Set<Name> personInvolvedNames = source.getPersonInvolvedNames();
        List<Name> personInvolvedNamesList = new ArrayList<>(personInvolvedNames);

        schedule = "Date: " + scheduleDate.toString() + " Activity: " + activity.toString()
                + " Person(s): " + StringUtil.convertListToString(personInvolvedNamesList);
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        // extract out schedule date, activity and person involved from schedule string
        int startingIndexOfDate = 6;
        int endingIndexOfDate = 16;
        int startingIndexOfActivity = schedule.indexOf("Activity: ") + 10;
        int personHeaderIndex = schedule.indexOf("Person(s): ");
        int startingIndexOfPerson = personHeaderIndex + 11;

        String scheduleDate = schedule.substring(startingIndexOfDate, endingIndexOfDate);
        String activity = schedule.substring(startingIndexOfActivity, personHeaderIndex - 1);
        String personInvolvedNamesString = schedule.substring(startingIndexOfPerson);

        logger.info("Date: " + scheduleDate + " Activity: " + activity + " Person(s): " + personInvolvedNamesString);

        String[] personInvolvedNames = personInvolvedNamesString.split("; ");
        Set<Name> scheduleModelNames = new HashSet<>();

        //extract person names into set
        for (int i = 0; i < personInvolvedNames.length; i++) {
            scheduleModelNames.add(new Name(personInvolvedNames[i].trim()));
            logger.info(personInvolvedNames[i].trim());
        }

        return new Schedule(new ScheduleDate(scheduleDate), new Activity(activity), scheduleModelNames);
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Schedule> getScheduleToRemindList() {
        final ObservableList<Schedule> schedulesToRemind = this.schedulesToRemind.stream().map(schedule -> {
            try {
                return schedule.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(schedulesToRemind);
    }

```
###### \java\seedu\address\ui\AgendaPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.schedule.Schedule;

/**
 * Panel containing the list of schedules.
 */
public class AgendaPanel extends UiPart<Region> {
    private static final String FXML = "AgendaPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AgendaPanel.class);

    @FXML
    private Label agendaHeader;
    @FXML
    private ListView<ScheduleCard> scheduleCardListView;

    public AgendaPanel(ObservableList<Schedule> scheduleList) {
        super(FXML);
        agendaHeader.getStyleClass().remove("label");
        setConnections(scheduleList);
        scheduleCardListView.setPlaceholder(new Label("No Schedules Available!"));
    }

    /**
     * Creates a list of {@code ScheduleCard} from {@code scheduleList}, sets them to the {@code scheduleCardListView}
     * and adds listener to {@code scheduleCardListView} for selection change.
     */
    private void setConnections(ObservableList<Schedule> scheduleList) {
        for (Schedule i : scheduleList) {
            logger.info(i.toString() + " Index: " + scheduleList.indexOf(i));
        }
        ObservableList<ScheduleCard> mappedList = EasyBind.map(
                scheduleList, (schedule) -> new ScheduleCard(schedule, scheduleList.indexOf(schedule) + 1));
        scheduleCardListView.setItems(mappedList);
        scheduleCardListView.setCellFactory(listView -> new ScheduleCardListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleCard}.
     */
    class ScheduleCardListViewCell extends ListCell<ScheduleCard> {

        @Override
        protected void updateItem(ScheduleCard schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(schedule.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads google maps web page locating person's address.
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        Address personAddress = person.getAddress();

        String urlEncodedAddressIntermediate = personAddress.toString().replaceAll("#", "%23");
        String urlEncodedAddressFinal = urlEncodedAddressIntermediate.replaceAll(" ", "+");

        loadPage(GOOGLE_MAPS_URL_PREFIX
                + urlEncodedAddressFinal
                + GOOGLE_MAPS_URL_SUFFIX);
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Shows reminder pop-up if there exists upcoming activities the next day.
     */
    public void openReminderWindowIfRequired() {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        ObservableList<Schedule> schedulesToRemindList = addressBook.getScheduleToRemindList();
        for (Schedule schedule : schedulesToRemindList) {
            logger.info("Schedules for reminder: " + schedule);
        }
        if (!schedulesToRemindList.isEmpty()) {
            ReminderWindow reminderWindow = new ReminderWindow(schedulesToRemindList);
            reminderWindow.show();
        }
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Sets the styles for id and name in the PersonCard.
     */
    private void setStylesToNameAndId() {
        id.getStyleClass().removeAll();
        id.getStyleClass().add("headers");
        name.getStyleClass().removeAll();
        name.getStyleClass().add("headers");
    }

```
###### \java\seedu\address\ui\ReminderWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.schedule.Schedule;

/**
 * Controller for a reminder pop-up
 */
public class ReminderWindow extends UiPart<Region> {

    private static final String FXML = "ReminderWindow.fxml";
    private static final String TITLE = "Reminder";
    private static final String ICON = "/images/info_icon.png";
    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);

    private final Stage dialogStage;

    @FXML
    private ListView<ScheduleCard> reminderSchedulesListView;
    @FXML
    private StackPane reminderWindowBottom;

    public ReminderWindow(ObservableList<Schedule> reminderSchedulesList) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        //set connections
        reminderSchedulesListView.prefWidthProperty().bind(reminderWindowBottom.prefWidthProperty());
        setConnections(reminderSchedulesList);
    }

    /**
     * Creates a list of {@code ScheduleCard} from {@code scheduleList}, sets them to the {@code scheduleCardListView}
     * and adds listener to {@code scheduleCardListView} for selection change.
     */
    private void setConnections(ObservableList<Schedule> scheduleList) {
        for (Schedule i : scheduleList) {
            logger.info(i.toString() + " Index: " + scheduleList.indexOf(i));
        }
        ObservableList<ScheduleCard> mappedList = EasyBind.map(
                scheduleList, (schedule) -> new ScheduleCard(schedule, scheduleList.indexOf(schedule) + 1));
        reminderSchedulesListView.setItems(mappedList);
        reminderSchedulesListView.setCellFactory(listView -> new ScheduleCardListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleCard}.
     */
    class ScheduleCardListViewCell extends ListCell<ScheduleCard> {

        @Override
        protected void updateItem(ScheduleCard schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(schedule.getRoot());
            }
        }
    }

    /**
     * Shows the reminder window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        // Set dimensions
        dialogStage.setWidth(620);
        dialogStage.setHeight(620);

        dialogStage.showAndWait();
    }
}
```
###### \java\seedu\address\ui\ScheduleCard.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Schedule;

/**
 * An UI component that displays information of a {@code Schedule}.
 */
public class ScheduleCard extends UiPart<Region> {
    private static final String FXML = "ScheduleCard.fxml";

    public final Schedule schedule;
    private final Logger logger = LogsCenter.getLogger(ScheduleCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label activity;
    @FXML
    private Label number;
    @FXML
    private Label date;
    @FXML
    private VBox personNames;

    @FXML
    private Label dateHeader;
    @FXML
    private Label personsInvolvedHeader;

    public ScheduleCard(Schedule schedule, int displayedIndex) {
        super(FXML);
        this.schedule = schedule;
        number.setText(displayedIndex + ". ");
        setHeaderStyles();
        initPersonNames(schedule);
        bindListeners(schedule);
    }

    /**
     * Sets the styles for headings in the ScheduleCard.
     */
    private void setHeaderStyles() {
        dateHeader.getStyleClass().remove("label");
        dateHeader.getStyleClass().add("headers");
        personsInvolvedHeader.getStyleClass().remove("label");
        personsInvolvedHeader.getStyleClass().add("headers");
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Schedule} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Schedule schedule) {
        logger.fine("Binding listeners...");

        activity.textProperty().bind(Bindings.convert(schedule.getActivityProperty()));
        date.textProperty().bind(Bindings.convert(schedule.getScheduleDateProperty()));
        schedule.getPersonInvolvedNamesProperty().addListener((observable, oldValue, newValue) -> {
            personNames.getChildren().clear();
            initPersonNames(schedule);
        });
    }

    /**
     * Sets person names involved in the scheduling to the respective UI labels upon startup.
     */
    private void initPersonNames(Schedule schedule) {
        for (Name name: schedule.getPersonInvolvedNames()) {
            logger.info(name.fullName);
        }
        schedule.getPersonInvolvedNames().forEach(personName -> {
            Label personNameLabel = new Label(personName.fullName);
            personNames.getChildren().add(personNameLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return number.getText().equals(card.number.getText())
                && schedule.equals(card.schedule);
    }
}
```
###### \resources\view\AgendaPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <Label fx:id="agendaHeader" alignment="CENTER" contentDisplay="TOP" text="My Agenda" textFill="#f7d0af">
      <font>
         <Font name="Calibri Bold Italic" size="25.0" />
      </font>
      <VBox.margin>
         <Insets left="60.0" />
      </VBox.margin>
   </Label>
  <ListView fx:id="scheduleCardListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \resources\view\MainWindow.fxml
``` fxml
      <VBox alignment="TOP_RIGHT" maxWidth="285.0" minWidth="175.0" prefWidth="225.0">
         <children>
            <StackPane fx:id="agendaPanelPlaceholder" VBox.vgrow="ALWAYS" />
         </children>
      </VBox>
    </SplitPane>

    <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\ReminderTheme.css
``` css
.label {
    -fx-font-size: 24pt;
    -fx-font-family: "Arial Narrow";
    -fx-text-fill: "white";
    -fx-opacity: 0.9;
}
```
###### \resources\view\ReminderWindow.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.text.Font?>

<SplitPane dividerPositions="0.5" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" orientation="VERTICAL" prefHeight="400.0" prefWidth="600.0" stylesheets="@DarkTheme.css" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <items>
    <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="192.0" prefWidth="598.0" stylesheets="@ReminderTheme.css">
         <children>
            <Label alignment="CENTER" contentDisplay="CENTER" layoutX="227.0" layoutY="20.0" prefHeight="37.0" prefWidth="152.0" text="REMINDER!" AnchorPane.bottomAnchor="138.0" AnchorPane.leftAnchor="227.0" AnchorPane.rightAnchor="219.0" AnchorPane.topAnchor="20.0">
               <font>
                  <Font name="Arial Narrow" size="30.0" />
               </font>
            </Label>
            <Label alignment="CENTER" contentDisplay="CENTER" layoutX="141.0" layoutY="66.0" prefHeight="29.0" prefWidth="350.0" text="1 more day to these activities: " AnchorPane.bottomAnchor="100.0" AnchorPane.leftAnchor="100.0" AnchorPane.rightAnchor="130.0">
               <font>
                  <Font size="24.0" />
               </font>
            </Label>
         </children></AnchorPane>
      <StackPane fx:id="reminderWindowBottom" prefHeight="150.0" prefWidth="200.0">
         <children>
            <ListView fx:id="reminderSchedulesListView" prefHeight="200.0" prefWidth="200.0" />
         </children>
      </StackPane>
  </items>
</SplitPane>
```
###### \resources\view\ScheduleCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

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
        <Label fx:id="number" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="activity" styleClass="cell_big_label" text="\\$Activity" />
      </HBox>
      <Label fx:id="dateHeader" styleClass="cell_small_label" stylesheets="@ScheduleCardHeaders.css" text="Date:">
            <font>
               <Font name="System Bold" size="13.0" />
            </font></Label>
      <Label fx:id="date" styleClass="cell_small_label" text="\\$date" />
         <Label fx:id="personsInvolvedHeader" stylesheets="@ScheduleCardHeaders.css" text="Contact(s) Involved:">
            <font>
               <Font name="System Bold" size="13.0" />
            </font></Label>
         <VBox fx:id="personNames" prefWidth="100.0" />
    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```
###### \resources\view\ScheduleCardHeaders.css
``` css
.headers {
    -fx-text-fill: #f7d0af;
    -fx-font-style: italic;
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
}
```
