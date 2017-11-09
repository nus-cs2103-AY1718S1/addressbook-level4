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
###### \java\seedu\address\logic\commands\UndoableCommand.java
``` java
    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        this.previousActiveListIsAll = model.getActiveIsAllBool();
    }

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered parcel list to
     * show all parcels.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
        model.setActiveList(!previousActiveListIsAll);
        if (previousActiveListIsAll) {
            model.uiJumpToTabAll();
        } else {
            model.uiJumpToTabCompleted();
        }
    }

    /**
     * Executes the command and updates the filtered parcel
     * list to show all parcels.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }

        model.updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
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
     * Method to force the model to select a card without using the select command.
     */
    void forceSelect(Index target);


    /**
     * Method to force the model to select a card without using the select command.
     */
    void forceSelectParcel(ReadOnlyParcel target);

    /**
     * Method to set tabIndex attribute in Model.
     */
    void setTabIndex(Index index);

    /**
     * Method to get tabIndex attribute in Model.
     */
    Index getTabIndex();

    /**
     * Method to encapsulate all the sub methods to be executed when AddCommand is executed.
     * @param parcel the parcel to add
     * @throws DuplicateParcelException if parcel is already inside the list of parcels, reject the input
     */
    void addParcelCommand(ReadOnlyParcel parcel) throws DuplicateParcelException;

    /**
     * Method to encapsulate all the sub methods to be executed when EditCommand is executed.
     * @param parcelToEdit the parcel to edit
     * @param editedParcel the edited parcel to replace the parcel to edit.
     * @throws DuplicateParcelException if editedParcel already exists unless the parcelToEdit is the same entity.
     * @throws ParcelNotFoundException if parcelToEdit cannot be found in the list
     */
    void editParcelCommand(ReadOnlyParcel parcelToEdit, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException;

    /**
     * Method to retrieve flag that represents whether the current tab selected is all parcels.
     */
    boolean getActiveIsAllBool();

    /**
     * Method to forcefully raise the event to switch tabs to all parcels.
     */
    void uiJumpToTabAll();

    /**
     * Method to forcefully rasie the event to switch tabs to completed parcels.
     */
    void uiJumpToTabCompleted();
```
###### \java\seedu\address\model\ModelListener.java
``` java
package seedu.address.model;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;

/**
 * SelectionListener listens for events that select a parcel card.
 */
public class ModelListener {

    private Model model = null;

    /**
     * Initializes a SelectionLister with the given model.
     */
    public ModelListener(Model model) {
        this.model = model;
        registerAsAnEventHandler(this);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Triggers when there is a JumpToTabRequestEvent and sets the tabIndex in the model
     * to keep track of which tab the model is "on".
     */
    @Subscribe
    private void handleJumpToTabEvent(JumpToTabRequestEvent event) {
        model.setTabIndex(Index.fromZeroBased(event.targetIndex));
    }
}
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
    public void forceSelect(Index target) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(target));
    }

    @Override
    public void forceSelectParcel(ReadOnlyParcel target) {
        forceSelect(Index.fromZeroBased(findIndex(target)));
    }

    @Override
    public void setTabIndex(Index index) {
        this.tabIndex = index;
    }

    @Override
    public Index getTabIndex() {
        return this.tabIndex;
    }

    @Override
    public void addParcelCommand(ReadOnlyParcel toAdd) throws DuplicateParcelException {
        this.addParcel(toAdd);
        this.maintainSorted();
        this.handleTabChange(toAdd);
        this.forceSelectParcel(toAdd);
        indicateAddressBookChanged();
    }

    @Override
    public void editParcelCommand(ReadOnlyParcel parcelToEdit, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException {
        this.updateParcel(parcelToEdit, editedParcel);
        this.maintainSorted();
        this.handleTabChange(editedParcel);
        this.forceSelectParcel(editedParcel);
        indicateAddressBookChanged();
    }

    @Override
    public boolean getActiveIsAllBool() {
        return tabIndex.equals(TAB_ALL_PARCELS);
    }

    /**
     * Method to internally change the active list to the correct tab according to the changed parcel.
     * @param targetParcel
     */

    private void handleTabChange(ReadOnlyParcel targetParcel) {
        try {
            if (targetParcel.getStatus().equals(Status.getInstance("COMPLETED"))) {
                if (this.getTabIndex().equals(TAB_ALL_PARCELS)) {
                    this.setActiveList(true);
                    uiJumpToTabCompleted();
                }
            } else {
                if (this.getTabIndex().equals(TAB_COMPLETED_PARCELS)) {
                    this.setActiveList(false);
                    uiJumpToTabAll();
                }
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void uiJumpToTabAll() {
        EventsCenter.getInstance().post(new JumpToTabRequestEvent(TAB_ALL_PARCELS));
    }

    @Override
    public void uiJumpToTabCompleted() {
        EventsCenter.getInstance().post(new JumpToTabRequestEvent(TAB_COMPLETED_PARCELS));
    }

    /**
     * Method to retrieve the index of a given parcel in the active list.
     */
    private int findIndex(ReadOnlyParcel target) {
        return getActiveList().indexOf(target);
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
            if (isValidPrettyTimeDate(trimmedDate)
                    && hasMinimumLength(trimmedDate)
                    && !containsAllNumbers(trimmedDate)) {
                // NLP appears to understand the intention, so we accept the input
                List<Date> dates = new PrettyTimeParser().parse(trimmedDate);
                this.date = dates.get(0);
            } else {
                throw new IllegalValueException(MESSAGE_DELIVERY_DATE_CONSTRAINTS);
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

    /**
     * Returns true if a given string is of a minimum length, more than 2 chars
     */
    public static boolean hasMinimumLength(String test) {
        return test.length() > 2;
    }

    /**
     * Returns true if a given string contains all numbers.
     */
    public static boolean containsAllNumbers(String test) {
        String regex = "\\d+";
        return test.matches(regex);
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
###### \java\seedu\address\model\parcel\Parcel.java
``` java
    /**
     * We choose to order parcels first by delivery date, next by tracking number if the delivery dates
     * are the same, and lastly by name if the tracking numbers are the same as well.
     */
    @Override
    public int compareTo(Object o) {
        Parcel other = (Parcel) o;
        if (other == this) { // short circuit if same object
            return 0;
        } else if (this.getDeliveryDate().compareTo(other.getDeliveryDate()) == 0) { // delivery dates are equal
            if (this.getName().compareTo(other.getName()) == 0) { // names are equal
                return this.getTrackingNumber().compareTo(other.getTrackingNumber()); // compare tracking numbers
            } else {
                return this.getName().compareTo(other.getName()); // compare names
            }
        } else {
            return this.getDeliveryDate().compareTo(other.getDeliveryDate()); // compare delivery dates
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
