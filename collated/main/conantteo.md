# conantteo
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid: ";
    public static final String MESSAGE_INVALID_PERSON_TO_EMAIL = "The person may have missing email address "
            + "at specified index provided: ";
    public static final String MESSAGE_NO_PERSONS_FOUND = "There are no contacts found!";

}
```
###### \java\seedu\address\commons\events\model\SearchTagEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tag.Tag;

/** Indicates that users want to search for this particular {@code tag} */
public class SearchTagEvent extends BaseEvent {

    public final Tag tag;

    public SearchTagEvent(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\EmailRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event to request for sending email to one or more email addresses
 */
public class EmailRequestEvent extends BaseEvent {

    private final String allEmailAddresses;

    public EmailRequestEvent(String allEmailAddresses) {
        this.allEmailAddresses = allEmailAddresses;
    }

    public String getAllEmailAddresses() {
        return allEmailAddresses;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ExportRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to export one or more contacts as a Vcard.
 */
public class ExportRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\util\IndexArrayUtil.java
``` java
package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;

/**
 * Helper functions for handling arrays containing one or more Index.
 */
public class IndexArrayUtil {

    /**
     * Compare two arrays {@code arr1} & {@code arr2} if they have the same Index number(s).
     * Index in both arrays do not have to be in ascending or descending order.
     * @param arr1 cannot be null but can be empty
     * @param arr2 cannot be null but can be empty
     * @return true if two arrays have the same Index number(s)
     */
    public static boolean compareIndexArrays(Index[] arr1, Index[] arr2) {
        requireNonNull(arr1);
        requireNonNull(arr2);

        if (arr1.length != arr2.length) {
            return false;
        }

        Index[] sortedArr1 = sortArray(arr1);
        Index[] sortedArr2 = sortArray(arr2);

        for (int i = 0; i < sortedArr1.length; i++) {
            if (!sortedArr1[i].equals(sortedArr2[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if IndexArray {@code arr} has unique Index with no duplicates.
     * @return false if there are at least one repeated index in the array.
     */
    public static boolean indexAreUnique(Index[] arr) {
        boolean isDistinct = true;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].equals(arr[j])) {
                    isDistinct = false;
                }
            }
        }
        return isDistinct;
    }

    /**
     * Swap elements in an array by its position.
     * @param arr is a given array, it cannot be null
     * @param pos is a valid position to be swap with the next element.
     */
    public static void swapElements(Index[] arr, int pos) {

        Index temp = arr[pos];
        arr[pos] = arr[pos + 1];
        arr[pos + 1] = temp;
    }

    /**
     * Sort Index elements in a Index array by its index value in one-based.
     * @param arr is a valid Index array.
     * @return a sorted Index array.
     */
    private static Index[] sortArray(Index[] arr) {

        for (int k = 0; k < arr.length; k++) {
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i].getOneBased() > arr[i + 1].getOneBased()) {
                    swapElements(arr, i);
                }
            }
        }
        return arr;
    }
}
```
<<<<<<< HEAD
###### \java\seedu\address\logic\commands\EmailCommand.java
=======
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns a string after removing the first two characters at the start of the string {@code s}
     * {@code s} string is first trimmed before obtaining its substring.
     * Example: ", person1, person2" will return "person1, person2"
     * @throws NullPointerException if {@code s} is null.
     */
    public static String getSubstringFromIndexTwo(String s) {
        requireNonNull(s);
        String trimmedString = s.trim();
        return trimmedString.substring(2, trimmedString.length());
    }

    /**
     * Returns a string after removing the white spaces in the string {@code s} with a comma
     * Example: "email1 email2 email3" will return "email1,email2,email3"
     * @throws NullPointerException if {@code s} is null.
     */
    public static String replaceWhiteSpaceWithComma(String s) {
        requireNonNull(s);
        return s.trim().replaceAll(" ", ",");
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
=======
###### /java/seedu/address/logic/commands/EmailCommand.java
>>>>>>> bd7f7c248711b469ccd96ffcda72d62ab2bae828
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.EmailRequestEvent;
import seedu.address.commons.util.IndexArrayUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Email one or more persons identified using it's last displayed index from the address book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Email one or more persons identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX [INDEX]... (must be a positive and no repeated integers)\n"
            + "Example: " + COMMAND_WORD + " 1" + " [2]" + " [3]";

    public static final String MESSAGE_EMAIL_PERSON_SUCCESS = "Email Person: %1$s";

    private final Index[] targetIndices;

    public EmailCommand(Index[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        StringBuilder addresses = new StringBuilder();
        StringBuilder persons = new StringBuilder();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                        + targetIndex.getOneBased());
            }
            ReadOnlyPerson personToEmail = lastShownList.get(targetIndex.getZeroBased());
            if (personToEmail.getEmail().toString().isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_TO_EMAIL
                        + targetIndex.getOneBased());
            }
            // Concatenate the names and email addresses of each person
            persons.append(", " + personToEmail.getName().toString());
            addresses.append(" " + personToEmail.getEmail().toString());
        }

        String allPersons = StringUtil.getSubstringFromIndexTwo(persons.toString());
        String allEmailAddresses = StringUtil.replaceWhiteSpaceWithComma(addresses.toString());

        EventsCenter.getInstance().post(new EmailRequestEvent(allEmailAddresses));
        return new CommandResult(String.format(MESSAGE_EMAIL_PERSON_SUCCESS, allPersons));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && IndexArrayUtil.compareIndexArrays(this.targetIndices, (
                        (EmailCommand) other).targetIndices)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.util.FileUtil.createIfMissing;
import static seedu.address.commons.util.FileUtil.writeToFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.commons.util.IndexArrayUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Vcard;

/**
 * Exports all contacts or specified person identified using it's last displayed index from the address book.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Export all contacts or specified person identified by the index number "
            + "used in the last person listing into a vCard file.\n"
            + "Parameters: all or INDEX [INDEX]... (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2" + " or " + COMMAND_WORD + " all";

    public static final String MESSAGE_EXPORT_PERSON_SUCCESS = "Export Person: %1$s\n"
            + "Please close the app before moving the contacts.vcf file to another location.";

    public static final String DEFAULT_FILE_DIR = "./data/";
    public static final String DEFAULT_FILE_NAME = "contacts.vcf";

    private final Index[] targetIndices;

    public ExportCommand() {
        this.targetIndices = new Index[0];
    }

    public ExportCommand(Index[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> fullList = model.getAddressBook().getPersonList();
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        StringBuilder persons = new StringBuilder();
        List<ReadOnlyPerson> listToExport = new ArrayList<>();

        // Checks if user has specified the index of persons to email
        if (targetIndices.length > 0) {
            for (Index targetIndex : targetIndices) {
                if (targetIndex.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                            + targetIndex.getOneBased());
                }
                ReadOnlyPerson personToExport = lastShownList.get(targetIndex.getZeroBased());
                listToExport.add(personToExport);
            }
        } else {
            if (!fullList.isEmpty()) {
                listToExport = fullList;
            } else {
                throw new CommandException(Messages.MESSAGE_NO_PERSONS_FOUND);
            }
        }

        // Constructs the String for all persons names
        for (ReadOnlyPerson person : listToExport) {
            persons.append(", ");
            persons.append(person.getName().toString());
        }
        String allPersons = StringUtil.getSubstringFromIndexTwo(persons.toString());

        // Creates a new vCard file to store all the VCard information.
        File file = new File(DEFAULT_FILE_DIR, DEFAULT_FILE_NAME);
        try {
            createIfMissing(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creates the content to be written into the vCard file
        StringBuilder content = new StringBuilder();
        for (ReadOnlyPerson person : listToExport) {
            Vcard personCard = new Vcard(person);
            content.append(personCard.getCardDetails());
        }

        // Writes the content into the vCard file.
        try {
            writeToFile(file, content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventsCenter.getInstance().post(new ExportRequestEvent());
        return new CommandResult(String.format(MESSAGE_EXPORT_PERSON_SUCCESS, allPersons));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && IndexArrayUtil.compareIndexArrays(this.targetIndices, (
                (ExportCommand) other).targetIndices)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public FindCommand(PersonContainsBirthdayPredicate searchPredicate) {
        this.searchPredicate = searchPredicate;
    }
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of all tags in the address book */
    ObservableList<Tag> getAllTags();
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<Tag> getAllTags() {
        return model.getAddressBook().getTagList();
    }
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.IndexArrayUtil;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] indexes = args.trim().split(" ");
        Index[] indexArray = new Index[indexes.length];

        try {
            for (int i = 0; i < indexes.length; i++) {
                Index index = ParserUtil.parseIndex(indexes[i]);
                indexArray[i] = index;
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
        if (!IndexArrayUtil.indexAreUnique(indexArray)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INDEX_DUPLICATES));
        }
        return new EmailCommand(indexArray);
    }

}
```
###### \java\seedu\address\logic\parser\ExportCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.IndexArrayUtil;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parsers input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.matches("all")) {
            return new ExportCommand();
        } else {
            String[] indices = args.trim().split(" ");
            Index[] indexArray = new Index[indices.length];
            try {
                for (int i = 0; i < indices.length; i++) {
                    Index index = ParserUtil.parseIndex(indices[i]);
                    indexArray[i] = index;
                }
            } catch (IllegalValueException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
            }
            if (!IndexArrayUtil.indexAreUnique(indexArray)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INDEX_DUPLICATES));
            }
            return new ExportCommand(indexArray);
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
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
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Ensures that all tags of a {@code person} is deleted away from the master tag list.
     */
    private void deleteMasterTagListWith(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.deleteFrom(personTags);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Subscribe
    private void handleSearchTagEvent(SearchTagEvent event)  {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String[] tagNameArr = {event.tag.tagName};
        Predicate<ReadOnlyPerson> predicate = new PersonContainsKeywordsPredicate(Arrays.asList(tagNameArr));
        updateFilteredPersonList(predicate);
    }
```
###### \java\seedu\address\model\person\Birthday.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday format should be 'DD/MM/YYYY', and it should not be blank\n"
                    + "Please check if the birthday is valid and is a valid leap day";
    // This regex guarantees that a Birthday format is DD/MM/YYYY and is not leap day.
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)"
            + "(/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(/)0?2\\3(?:(?:"
            + "(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?"
            + "[1-9]|1\\d|2[0-8])(/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    // This regex guarantees that a Birthday month is MM and the range of values is from [01-12].
    public static final String BIRTHDAY_MONTH_REGEX = "([0][1-9])|([1][0-2])";

    public final String value;
    private String birthdayMonth;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();
        if (birthday.length() != 0 && !isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        } else if (isValidBirthday(trimmedBirthday)) {
            this.birthdayMonth = trimmedBirthday.split("/")[1];
        }
        this.value = trimmedBirthday;
    }


    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given String matches exactly 2 digits from 01 to 12 which is a valid birthday month.
     */
    public static boolean isValidMonth(String test) {
        return test.matches(BIRTHDAY_MONTH_REGEX);
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
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
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }

    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }
```
###### \java\seedu\address\model\person\PersonContainsBirthdayPredicate.java
``` java
package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Birthday} matches any of the birthday month given
 */
public class PersonContainsBirthdayPredicate implements Predicate<ReadOnlyPerson> {
    private final String birthdayMonth;

    public PersonContainsBirthdayPredicate(String birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return birthdayMonth.equals(person.getBirthday().getBirthdayMonth());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsBirthdayPredicate// instanceof handles nulls
                && this.birthdayMonth.equals(((PersonContainsBirthdayPredicate) other)
                .birthdayMonth)); // state check
    }

}
```
###### \java\seedu\address\model\person\Vcard.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Vcard of a Person in the address book.
 * Contains user information in a Vcard format stored in a String {@code cardDetails}
 * This Vcard is only created when user wants to export his/her contacts' information.
 */
public class Vcard {

    private String cardDetails;

    /**
     * Constructs empty Vcard.
     */
    public Vcard() {}

    /**
     * Creates a Vcard using a given person.
     * @param person enforces no nulls person.
     * Store information of a given person in a string {@code cardDetails}
     * Note that Vcard version used is 3.0:
     * BEING, VERSION, FN, END fields in cardDetails are required.
     * The rest of the fields are not required and can be empty Strings.
     */
    public Vcard(ReadOnlyPerson person) {
        requireNonNull(person);
        String name = person.getName().toString();
        String phone = person.getPhone().toString();
        String address = person.getAddress().toString();
        String email = person.getEmail().toString();
        String birthday = person.getBirthday().toString();
        birthday = buildBirthdayString(birthday);
        cardDetails = "BEGIN:VCARD\n"
                + "VERSION:3.0\n"
                + "FN:" + name + "\n"
                + "TEL;TYPE=MOBILE:" + phone + "\n"
                + "EMAIL;TYPE=WORK:" + email + "\n"
                + "BDAY:" + birthday + "\n"
                + "ADR;TYPE=HOME:;;" + address + "\n"
                + "END:VCARD" + "\n";
    }

    public String getCardDetails() {
        return cardDetails;
    }

    /**
     * This method builds a valid birthday format for Vcard.
     * {@code bday} is a StringBuilder that appends the Year, followed by Month and Day
     * of a particular birthday separated by a dash.
     * @param birthday is a valid birthday string of a person in the format: DD/MM/YYYY.
     * @return a new birthday string format: YYYY-MM-DD
     */
    private String buildBirthdayString(String birthday) {
        if (!birthday.equals("")) {
            String[] birthdayField = birthday.split("/");
            StringBuilder bday = new StringBuilder();
            bday.append(birthdayField[2]);
            bday.append("-");
            bday.append(birthdayField[1]);
            bday.append("-");
            bday.append(birthdayField[0]);
            return bday.toString();
        } else {
            // If there is no birthday information, return empty string
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Vcard // instanceof handles nulls
                && this.cardDetails.equals(((Vcard) other).cardDetails));
    }

    @Override
    public int hashCode() {
        return cardDetails.hashCode();
    }

}
```
###### \java\seedu\address\model\tag\UniqueTagList.java
``` java
    /**
     * Deletes the Tags in this list with those in the argument tag list.
     */
    public void deleteFrom(UniqueTagList from) {
        from.internalList.stream()
                .forEach(internalList::remove);
        assert CollectionUtil.elementsAreUnique(internalList);
    }
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
    * Saves the given {@link ReadOnlyAddressBook} in a temporary location
    * @param addressBook cannot be null
    * @throws IOException if there was any problem writing to the file.
    */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }

}
```
###### \java\seedu\address\ui\GroupLabel.java
``` java
package seedu.address.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.SearchTagEvent;
import seedu.address.model.tag.Tag;

/**
 * An UI component that display the name of a {@code Tag}.
 */
public class GroupLabel extends UiPart<Region> {

    private static final String FXML = "GroupLabel.fxml";
    private final Tag tag;

    @FXML
    private HBox cardPane;
    @FXML
    private Label groupName;

    public GroupLabel(Tag tag) {
        super(FXML);
        this.tag = tag;
        initTags(tag);
        registerAsAnEventHandler(this);
        setEventHandlerForMouseClick();
    }

    private void initTags(Tag tag) {
        groupName.setText(tag.tagName);
    }

    /**
     * Register the Label {@tagsName} for MouseEvent to display the persons with the tag that user wants to see.
     */
    private void setEventHandlerForMouseClick() {
        groupName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                raise(new SearchTagEvent(tag));
            }
        });
    }

    /** Returns the tag associated with this GroupLabel **/
    public Tag getTag() {
        return this.tag;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof GroupLabel)) {
            return false;
        }
        // state check
        return tag.equals(((GroupLabel) other).tag);
    }
}
```
###### \java\seedu\address\ui\GroupListPanel.java
``` java
package seedu.address.ui;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of unique tags
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupList.fxml";

    @FXML
    private ListView<GroupLabel> groupListView;

    public GroupListPanel(ObservableList<Tag> allTagsList) {
        super(FXML);
        bindTags(allTagsList);
    }

    /**
     * Creating bindings for each tag to each {@code GroupListViewCell} in the ListView
     * @param allTagsList is a valid list of all unique tags to be displayed.
     */
    private void bindTags(ObservableList<Tag> allTagsList) {
        ObservableList<GroupLabel> mappedList = EasyBind.map(
                allTagsList, (tag) -> new GroupLabel(tag));
        groupListView.setItems(mappedList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());

    }

    /**
     * Custom {@code GroupListViewCell} that displays the graphics of a {@code GroupLabel}.
     */
    class GroupListViewCell extends ListCell<GroupLabel> {

        @Override
        protected void updateItem(GroupLabel groupLabel, boolean empty) {
            super.updateItem(groupLabel, empty);

            Platform.runLater(() -> {
                if (empty || groupLabel == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(groupLabel.getRoot());
                }

            });

        }
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * This method will call the user's default mail application and set the recipients field with all the
     * email addresses specified by the user.
     * @param allEmailAddresses is a string of all valid email addresses user request to email to.
     * @throws IOException when java Desktop class is not supported in this platform.
     */
    private void handleEmail(String allEmailAddresses) {

        URI mailTo = null;
        try {
            mailTo = new URI(EMAIL_URI_PREFIX + allEmailAddresses);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Checks if Desktop class is supported in the current platform
        if (Desktop.isDesktopSupported()) {
            Desktop userDesktop = Desktop.getDesktop();
            try {
                logger.info("Showing user's default mail client");
                userDesktop.mail(mailTo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens a file directory which shows the folder where contacts.vcf file is located.
     * The file directory is is guaranteed to exist before showing.
     * @throws IOException when java Desktop class is not supported in this platform.
     */
    private void handleExport() {
        File file = new File(EXPORT_FILE_PATH);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop userDesktop = Desktop.getDesktop();
                logger.info("Showing user's folder for contacts.vcf");
                userDesktop.open(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleEmailRequestEvent(EmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleEmail(event.getAllEmailAddresses());
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleExportRequestEvent(ExportRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleExport();
    }
}
```
