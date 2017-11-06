# fustilio
###### \java\seedu\address\logic\commands\DeleteTagCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagInternalErrorException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Deletes a tag from all parcels from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag from all parcels in the address book.\n"
            + "Parameters: Tag name\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_INVALID_DELETE_TAG_NOT_FOUND = "Tag not found: %1$s";

    private final Tag targetTag;

    public DeleteTagCommand(Tag targetTag) {
        this.targetTag = targetTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        Tag tagToDelete = targetTag;

        try {
            model.deleteTag(tagToDelete);
        } catch (TagInternalErrorException tiee) {
            throw new CommandException(MESSAGE_USAGE);
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(String.format(MESSAGE_INVALID_DELETE_TAG_NOT_FOUND, tagToDelete));
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, tagToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.targetTag.equals(((DeleteTagCommand) other).targetTag)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\DeleteTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteTagCommand object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        try {
            Tag tag = ParserUtil.parseTag(args);
            return new DeleteTagCommand(tag);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Function that sorts the lists of parcels
     */
    public void sort() {
        try {
            this.setParcels(parcels.getSortedList());
        } catch (DuplicateParcelException e) {
            e.printStackTrace();
        }
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Deletes the given tag from every parcel.
     */
    void deleteTag(Tag target) throws TagNotFoundException, TagInternalErrorException;
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Method to sort the lists of addresses by delivery date with the earliest date in front
     */
    void maintainSorted();

    /**
     * Method to check if there is a parcel selected.
     */
    boolean hasSelected();

    /**
     * Method to toggle whether or not a parcel has been selected
     */
    void select();

    /**
     * Method to toggle whether or not a parcel has been selected
     */
    void unselect();

    /**
     * Method to set the prevIndex attribute to the specified target.
     */
    void setPrevIndex(Index target);

    /**
     * Method to retrieve Index of last selected Parcel Card.
     */
    Index getPrevIndex();

    /**
     * Method to force the model to select a card without using the select command.
     */
    void forceSelect(Index target);

    /**
     * Method to reselect a parcel card if there is a card selected.
     */
    void reselect(ReadOnlyParcel parcel);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Deletes the tag from every parcel in the address book */
    public void deleteTag(Tag target) throws TagNotFoundException, TagInternalErrorException {

        int tagsFound = 0;
        Iterator it = addressBook.getParcelList().iterator();
        while (it.hasNext()) {
            Parcel oldParcel = (Parcel) it.next();
            Parcel newParcel = new Parcel(oldParcel);
            Set<Tag> newTags = new HashSet<>(newParcel.getTags());
            if (newTags.contains(target)) {
                newTags.remove(target);
                tagsFound++;
            }

            newParcel.setTags(newTags);

            try {
                addressBook.updateParcel(oldParcel, newParcel);
            } catch (DuplicateParcelException | ParcelNotFoundException dpe) {
                throw new TagInternalErrorException();
            }
        }

        if (tagsFound == 0) {
            throw new TagNotFoundException();
        }

        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void maintainSorted() {
        addressBook.sort();
        indicateAddressBookChanged();
    }

    @Override
    public boolean hasSelected() {
        return selected;
    }

    @Override
    public void select() {
        selected = true;
    }

    @Override
    public void unselect() {
        selected = false;
    }

    @Override
    public void forceSelect(Index target) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(target));
    }

    @Override
    public void reselect(ReadOnlyParcel parcel) {
        // With sorting, we lose our selected card. As such we have to reselect the
        // parcel that was previously selected. This leads to the need to have some way of
        // keeping track of which card had been previously selected. Hence the prevIndex
        // attribute in the ModelManager class and also it's corresponding to get and set it.
        // We first get the identity of the previously selected parcel.
        ReadOnlyParcel previous = getActiveList().get(getPrevIndex().getZeroBased());
        // if the previous parcel belongs after the editedParcel, we just reselect the parcel
        // at the previous index because all the parcels get pushed down.
        if (previous.compareTo(parcel) > 0) {
            forceSelect(getPrevIndex());
        } else {
            // otherwise the parcel toAdd belongs before the previously selected parcel
            // so we select the parcel with the next index.
            forceSelect(Index.fromZeroBased(findIndex(previous)));
        }
    }

    private int findIndex(ReadOnlyParcel target) {
        return getActiveList().indexOf(target);
    }

    @Override
    public void setPrevIndex(Index newIndex) {
        prevIndex = newIndex;
    }

    @Override
    public Index getPrevIndex() {
        return prevIndex;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredParcels.equals(other.filteredParcels)
                && filteredDeliveredParcels.equals(other.filteredDeliveredParcels)
                && filteredUndeliveredParcels.equals(other.filteredUndeliveredParcels)
                && activeFilteredList.equals(other.activeFilteredList);
    }
```
###### \java\seedu\address\model\parcel\DeliveryDate.java
``` java
package seedu.address.model.parcel;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Parcel's delivery date in the address book.
 * Guarantees: mutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DeliveryDate {


    public static final String MESSAGE_DELIVERY_DATE_CONSTRAINTS =
            "Delivery dates should be in the format dd-mm-yyyy or references to date such as "
            + "\"today\" or \"next week.\"";
    public static final List<String> VALID_STRING_FORMATS = Arrays.asList(
            "dd-MM-yyyy", "d-MM-yyyy", "d-M-yyyy", "dd-M-yyyy",
            "dd/MM/yyyy", "d/MM/yyyy", "d/M/yyyy", "dd/M/yyyy",
            "dd.MM.yyyy", "d.MM.yyyy", "d.M.yyyy", "dd/M.yyyy");
    public static final String DATE_FORMAT_VALIDATION_REGEX = "^(\\d{1,2}[./-]\\d{1,2}[./-]\\d{4})$";
    public final String value;
    private Date date;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    /**
     * Validates given delivery date.
     *
     * @throws IllegalValueException if given delivery date string is invalid.
     */
    public DeliveryDate(String deliveryDate) throws IllegalValueException {
        requireNonNull(deliveryDate);
        String trimmedDate = deliveryDate.trim();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        df.setLenient(false);

        // Check if input is in a format we can understand
        if (!isValidDateFormat(trimmedDate)) {
            // Check if input is in a format that PrettyTime(NLP) can understand
            if (!isValidPrettyTimeDate(trimmedDate)) {
                throw new IllegalValueException(MESSAGE_DELIVERY_DATE_CONSTRAINTS);
            } else { // NLP appears to understand the intention, so we accept the input
                List<Date> dates = new PrettyTimeParser().parse(trimmedDate);
                this.date = dates.get(0);
            }
        } else { // We understand the intention, so we accept the input
            try {
                this.date = formatDate(trimmedDate);
            } catch (ParseException e) { // date is in correct format, but not a valid date.
                throw new IllegalValueException(MESSAGE_DELIVERY_DATE_CONSTRAINTS);
            }
        }

        // Format date correctly
        this.value = df.format(this.date);
    }

    /**
     * Formats the input date according to the list VALID_STRING_FORMATS and returns it.
     */
    private Date formatDate(String inputDate) throws ParseException {

        for (String formatString : VALID_STRING_FORMATS) {
            DateFormat df = new SimpleDateFormat(formatString);
            df.setLenient(false);
            try {
                return df.parse(inputDate);
            } catch (ParseException e) {
                logger.info("Failed to fit input delivery date in current format, trying next format...");
            }
        }

        logger.warning("Exhausted all formats, not a valid input.");

        throw new ParseException(inputDate, 0);

    }

    /**
     * Returns true if a given string is a valid date for delivery.
     */
    public static boolean isValidDate(String test) {
        DeliveryDate result;
        try {
            result = new DeliveryDate(test);
        } catch (IllegalValueException e) {
            return false;
        }
        return !result.equals(null);
    }

    public static boolean isValidDateFormat(String test) {
        return test.matches(DATE_FORMAT_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid date for delivery.
     */
    public static boolean isValidPrettyTimeDate(String test) {
        List<Date> dates = new PrettyTimeParser().parse(test);

        return dates.size() > 0;
    }

    private Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliveryDate // instanceof handles nulls
                && this.value.equals(((DeliveryDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(DeliveryDate deliveryDate) {
        return this.date.compareTo(deliveryDate.getDate());
    }
}
```
###### \java\seedu\address\model\tag\exceptions\TagInternalErrorException.java
``` java
package seedu.address.model.tag.exceptions;

/**
 * Signals that there is an error within a Tag operation.
 */
public class TagInternalErrorException extends Exception {}
```
###### \java\seedu\address\model\tag\exceptions\TagNotFoundException.java
``` java
package seedu.address.model.tag.exceptions;

/**
 * Signals that the operation is unable to find the tag of a specified parcel.
 */
public class TagNotFoundException extends Exception {}
```
