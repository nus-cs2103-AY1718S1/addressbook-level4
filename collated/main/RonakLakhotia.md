# RonakLakhotia
###### \java\seedu\address\commons\events\ui\ClearBrowserPanelEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Raises a ClearBrowserPanelEvent to clear the browser panel when the clear command is executed
 */
public class ClearBrowserPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\FaceBookEvent.java
``` java
/**
 * Raises a FaceBookEvent when the user executes the facebook command to view the profile page.
 */
public class FaceBookEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public FaceBookEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

}
```
###### \java\seedu\address\commons\events\ui\ReminderPanelSelectionChangedEvent.java
``` java
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.ReminderCard;

/**
 * Represents a selection change in the Reminder List Panel
 */
public class ReminderPanelSelectionChangedEvent extends BaseEvent {


    private final ReminderCard newSelection;

    public ReminderPanelSelectionChangedEvent(ReminderCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReminderCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\logic\commands\AddReminder.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;



/**
 * Adds a reminder to the address book.
 */
public class AddReminder extends UndoableCommand {

    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the address book. "
            + "Parameters: "
            + PREFIX_REMINDER_DETAILS + "ABOUT "
            + PREFIX_REMINDER_PRIORITY + "HIGH "
            + PREFIX_REMINDER_DUE_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_REMINDER_DETAILS + "CS2103T Assignment "
            + PREFIX_REMINDER_PRIORITY + "High "
            + PREFIX_REMINDER_DUE_DATE + "12.05.2017 ";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists!";

    private static final Logger logger = LogsCenter.getLogger(AddReminder.class);
    private final Reminder toAdd;


    /**
     * Creates an AddReminder Command to add the specified {@code ReadOnlyReminder}
     */
    public AddReminder(ReadOnlyReminder reminder) {
        toAdd = new Reminder(reminder);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addReminder(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateReminderException e) {
            logger.severe(StringUtil.getDetails(e));
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminder // instanceof handles nulls
                && toAdd.equals(((AddReminder) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\ChangeReminderCommand.java
``` java
/**
 * Changes the details of an existing reminder in Weaver.
 */
public class ChangeReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the details of the reminder identified "
            + "by the index number used in the last reminder listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REMINDER_DETAILS + "DETAILS] "
            + "[" + PREFIX_REMINDER_PRIORITY + "PRIORITY LEVEL] "
            + "[" + PREFIX_REMINDER_DUE_DATE + "DATE] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMINDER_DUE_DATE + "12.11.2017 ";

    public static final String MESSAGE_CHANGE_REMINDER_SUCCESS = "Changed Reminder: %1$s";
    public static final String MESSAGE_NOT_CHANGED = "At least one field to change must be provided.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "This reminder already exists in weaver.";

    private final Index index;
    private final ChangeReminderDescriptor changeReminderDescriptor;

    /**
     * Changes the reminder at the given index with the given descriptor.
     * @param index of the reminder in the filtered reminder list to change
     * @param changeReminderDescriptor details to change the reminder with
     */
    public ChangeReminderCommand(Index index, ChangeReminderDescriptor changeReminderDescriptor) {
        requireNonNull(index);
        requireNonNull(changeReminderDescriptor);

        this.index = index;
        this.changeReminderDescriptor = new ChangeReminderDescriptor(changeReminderDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToChange = lastShownList.get(index.getZeroBased());
        Reminder changedReminder = createChangedReminder(reminderToChange, changeReminderDescriptor);

        try {
            model.updateReminder(reminderToChange, changedReminder);
        } catch (DuplicateReminderException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("The target reminder cannot be missing");
        }
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        return new CommandResult(String.format(MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder));
    }

    /**
     * Creates and returns a {@code Reminder} with the details of {@code reminderToChange}
     * edited with {@code changeReminderDescriptor}.
     */
    private static Reminder createChangedReminder(ReadOnlyReminder reminderToChange,
                                                  ChangeReminderDescriptor changeReminderDescriptor) {
        assert reminderToChange != null;

        ReminderDetails updateDetails = changeReminderDescriptor.getDetails().orElse(reminderToChange.getDetails());
        Priority updatePriority = changeReminderDescriptor.getPriority().orElse(reminderToChange.getPriority());
        DueDate updatedDate = changeReminderDescriptor.getDueDate().orElse(reminderToChange.getDueDate());

        return new Reminder(updateDetails, updatePriority, updatedDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeReminderCommand)) {
            return false;
        }

        // state check
        ChangeReminderCommand command = (ChangeReminderCommand) other;
        return index.equals(command.index)
                && changeReminderDescriptor.equals(command.changeReminderDescriptor);
    }

    /**
     * Stores the details to change the reminder with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class ChangeReminderDescriptor {
        private ReminderDetails details;
        private Priority priority;
        private DueDate dueDate;

        public ChangeReminderDescriptor() {}

        public ChangeReminderDescriptor(ChangeReminderDescriptor toCopy) {
            this.details = toCopy.details;
            this.priority = toCopy.priority;
            this.dueDate = toCopy.dueDate;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldChanged() {
            return CollectionUtil.isAnyNonNull(this.details, this.priority, this.dueDate);
        }

        public void setDetails(ReminderDetails details) {
            this.details = details;
        }

        public Optional<ReminderDetails> getDetails() {
            return Optional.ofNullable(details);
        }

        public void setDueDate(DueDate dueDate) {
            this.dueDate = dueDate;
        }

        public Optional<DueDate> getDueDate() {
            return Optional.ofNullable(dueDate);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ChangeReminderDescriptor)) {
                return false;
            }

            // state check
            ChangeReminderDescriptor c = (ChangeReminderDescriptor) other;

            return getDetails().equals(c.getDetails())
                    && getPriority().equals(c.getPriority())
                    && getDueDate().equals(c.getDueDate());
        }
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
/**
 * Sends an Email to all contacts with the specified tag.
 */
public class EmailCommand extends Command {


    public static final String characterToAppendAfterEachWordInSubjectLine = "+";
    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the default browser on the desktop with the Gmail "
            + "compose box open and specific details pre-filled.\n"
            + "Parameters: " + PREFIX_TAG + "One Tag (must match tag) \n"
            + PREFIX_SUBJECT + "Subject of the email."
            + "Example: " + COMMAND_WORD + " t/friends s/birthday.";

    public static final String MESSAGE_EMAIL_SUCCESS = "Email has been sent!";
    public static final String MESSAGE_NOT_EXISTING_TAGS = "The tag provided is invalid. Please check again.";

    private static final Logger logger = LogsCenter.getLogger(EmailCommand.class);
    private final String tag;
    private final String subject;
    private String modifiedSubject;


    /**
     * Emails a group of person with the same tag description and a given subject body.
     * @param tag     of the persons to whom the email has to be sent
     * @param subject the subject line of th email
     */

    public EmailCommand(String tag, String subject) {
        requireNonNull(tag);
        requireNonNull(subject);

        this.tag = tag;
        this.subject = subject;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            boolean isExistingTagName = checkIfExistingTagName(tag);

            if (!isExistingTagName) {
                logger.warning("Incorrect tags entered");
                throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS));
            }
            else {
                logger.info("Processing subject line and executing");
                modifiedSubject = getSubjectForBrowser(subject);
                model.sendMailToContacts(tag, modifiedSubject, model.getFilteredPersonList());
                return new CommandResult(MESSAGE_EMAIL_SUCCESS);
            }
        } catch (IOException io) {
            logger.severe(StringUtil.getDetails(io));
            throw new AssertionError("Invalid Input");
        } catch (URISyntaxException ur) {
            throw new AssertionError("urisyntax erro");
        } catch (IllegalValueException ie) {
            throw new AssertionError("Illegal values");
        }


    }
    /**
     * Gets subject with the '+' character appended after each word to match the URL requirements.
     */
    public String getSubjectForBrowser(String subject) {

        String modifiedSubject = "";

        for (int loopVariable = 0; loopVariable < subject.length(); loopVariable++) {
            if (subject.charAt(loopVariable) == ' ') {
                modifiedSubject = modifiedSubject + characterToAppendAfterEachWordInSubjectLine;
            } else {
                modifiedSubject = modifiedSubject + subject.charAt(loopVariable);
            }
        }
        return modifiedSubject;
    }

    /**
     * Checks whether a given tag exists in address book.
     * @param tagName tag that is to be checked
     */
    public boolean checkIfExistingTagName(String tagName) {

        for (Tag tag : model.getAddressBook().getTagList()) {
            if (tag.tagName.equalsIgnoreCase(tagName)) {
                return true;
            }
        }
        return false;
    }

}
```
###### \java\seedu\address\logic\commands\FaceBookCommand.java
``` java
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 *  Shows a person's Facebook profile page in the browser panel.
 */
public class FaceBookCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "facebook";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Shows the profile of the user whose"
            + " index is entered\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_FACEBOOK_SHOWN_SUCCESS = "Profile of Person: %1$s";
    public static final String MESSAGE_NO_USERNAME = "This Person has no Facebook username!\n";

    private static final Logger logger = LogsCenter.getLogger(FaceBookCommand.class);
    public final Index index;


    public FaceBookCommand (Index index) {
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToShow = lastShownList.get(index.getZeroBased());

        if (personToShow.getUsername().toString().equalsIgnoreCase("")) {
            throw new CommandException(String.format(MESSAGE_NO_USERNAME, personToShow));
        }

        try {
            assert index.getZeroBased() >= 0;
            model.faceBook(personToShow);
        } catch (PersonNotFoundException pnfe) {
            logger.warning("Person is missing");
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_FACEBOOK_SHOWN_SUCCESS, personToShow));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FaceBookCommand // instanceof handles nulls
                && this.index.equals(((FaceBookCommand) other).index)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\PhotoCommand.java
``` java
/**
 * Adds a photo of the person referenced to by the index in Weaver.
 * Deletes an existing photo of a person.
 */

public class PhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "photo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a photo of the person to the addressBook\n"
            + "Parameters: INDEX (must be a positive integer) FILE_PATH\n"
            + "File Path must be Valid\n"
            + "Example: " + COMMAND_WORD + " 1" + " /Users/ronaklakhotia/Desktop/Ronak.jpeg";

    public static final String DELETE_SUCCESS = "Deleted photo of Person: %1$s";
    public static final String MESSAGE_PHOTO_PERSON_SUCCESS = "Added Photo to Person: %1$s";

    private final Index targetIndex;
    private  String filePath;

    public PhotoCommand(Index targetIndex, String filePath) {
        this.targetIndex = targetIndex;
        this.filePath = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddPhoto = lastShownList.get(targetIndex.getZeroBased());

        boolean isThereAnyPhotoToDelete;
        isThereAnyPhotoToDelete = checkIfThereIsAnyPhotoToDelete(personToAddPhoto, filePath);

        if (!isThereAnyPhotoToDelete) {
            throw new CommandException(Messages.MESSAGE_NO_IMAGE_TO_DELETE);
        }
        if (filePath.equalsIgnoreCase("Delete")) {
            filePath = "";
        }

        try {
            Person editedPerson = new Person(personToAddPhoto.getName(), personToAddPhoto.getPhone(),
                    personToAddPhoto.getEmail(),
                    personToAddPhoto.getAddress(), personToAddPhoto.getDateOfBirth(), personToAddPhoto.getRemark(),
                    new FileImage(filePath), personToAddPhoto.getUsername(), personToAddPhoto.getTags());

            model.addPhotoPerson(personToAddPhoto, filePath, targetIndex);
            model.updatePerson(personToAddPhoto, editedPerson);


        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (IOException ioe) {
            assert false : "Correct input required";
        } catch (IllegalValueException ive) {
            assert false : "Invalid input";
        }
        if (filePath.equals("")) {
            return new CommandResult(String.format(DELETE_SUCCESS, personToAddPhoto));
        } else {
            return new CommandResult(String.format(MESSAGE_PHOTO_PERSON_SUCCESS, personToAddPhoto));
        }
    }
    /**
     * Returns tru if the person has any photo to be deleted else returns false.
     */
    public static boolean checkIfThereIsAnyPhotoToDelete(ReadOnlyPerson personToAddPhoto, String filePath) {

        if (personToAddPhoto.getImage().getFilePath().equals("") && filePath.equalsIgnoreCase("delete")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((PhotoCommand) other).targetIndex)); // state check
    }
}


```
###### \java\seedu\address\logic\commands\RemoveReminderCommand.java
``` java
/**
 * Deletes a reminder identified using it's last displayed index from Weaver.
 */
public class RemoveReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the last reminder listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    private static final Logger logger = LogsCenter.getLogger(RemoveReminderCommand.class);
    private final Index targetIndex;


    public RemoveReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyReminder> lastShownList = model.getFilteredReminderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
        }

        ReadOnlyReminder reminderToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteReminder(reminderToDelete);
        } catch (ReminderNotFoundException pnfe) {
            logger.severe(StringUtil.getDetails(pnfe));
            assert false : "The target reminder cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveReminderCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemoveReminderCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SearchCommand.java
``` java
/**
 * Searches and lists all persons in address book whose name and DateOfBirth matches the argument keywords, that
 * is persons with same name and DateOfBirth. This is to make the find command more powerful.
 * Keyword matching is case insensitive.
 */

public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name and Date Of Birth "
            + "contain any of the specified keyowrds and displays them as a list with index number. \n"
            + "Parameters: Name and Date Of Birth\n"
            + "Example: " + COMMAND_WORD + " search n/ronak b/13.10.1997";

    private static final Logger logger = LogsCenter.getLogger(SearchCommand.class);
    private final SearchContainsKeywordsPredicate predicate;


    public SearchCommand(SearchContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute() {
        logger.info("Executing seacrh command!");
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SearchCommand
                && this.predicate.equals(((SearchCommand) other).predicate));

    }

}
```
###### \java\seedu\address\logic\commands\UndoableCommand.java
``` java
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
```
###### \java\seedu\address\logic\commands\UndoableCommand.java
``` java
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
```
###### \java\seedu\address\logic\parser\AddReminderParser.java
``` java
/**
 * Parses the input arguments and creates a new AddReminderCommand object.
 */
public class AddReminderParser implements Parser<AddReminder> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminder
     * and returns an AddReminder object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminder parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DETAILS, PREFIX_REMINDER_DUE_DATE,
                        PREFIX_REMINDER_PRIORITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_REMINDER_DETAILS, PREFIX_REMINDER_DUE_DATE,
                PREFIX_REMINDER_PRIORITY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE));
        }

        try {
            ReminderDetails details = ParserUtil.parseDetails(argMultimap.getValue(PREFIX_REMINDER_DETAILS)).get();
            Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_REMINDER_PRIORITY)).get();
            DueDate dueDate = ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_REMINDER_DUE_DATE)).get();
            ReadOnlyReminder reminder = new Reminder(details, priority , dueDate);

            return new AddReminder(reminder);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\ChangeReminderCommandParser.java
``` java

/**
 * Parses input arguments and creates a new ChangeReminderCommand object.
 */
public class ChangeReminderCommandParser implements Parser<ChangeReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeReminderCommand
     * and returns an ChangeReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMINDER_DETAILS, PREFIX_REMINDER_DUE_DATE,
                        PREFIX_REMINDER_PRIORITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeReminderCommand.MESSAGE_USAGE));
        }

        ChangeReminderDescriptor changeReminderDescriptor = new ChangeReminderDescriptor();
        try {
            ParserUtil.parseDetails(argMultimap.getValue(PREFIX_REMINDER_DETAILS))
                    .ifPresent(changeReminderDescriptor::setDetails);
            ParserUtil.parsePriority(argMultimap.getValue(PREFIX_REMINDER_PRIORITY))
                    .ifPresent(changeReminderDescriptor::setPriority);
            ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_REMINDER_DUE_DATE))
                    .ifPresent(changeReminderDescriptor::setDueDate);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!changeReminderDescriptor.isAnyFieldChanged()) {
            throw new ParseException(ChangeReminderCommand.MESSAGE_NOT_CHANGED);
        }

        return new ChangeReminderCommand(index, changeReminderDescriptor);
    }


}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB))
                                        .ifPresent(editPersonDescriptor::setDateOfBirth);
            ParserUtil.parseImage(argMultimap.getValue(PREFIX_IMAGE)).ifPresent(editPersonDescriptor::setImage);
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
            ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME))
                    .ifPresent(editPersonDescriptor::setUsername);
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailSubject;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EmailCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {


    public static final String MULTIPLE_TAGS_FALIURE = "Multiple tags cannot be entered";

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {


        EmailSubject subject;
        Set<Tag> tagList;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
        try {
            subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT)).get();
            tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        if (tagList.size() > 1) {
            throw new ParseException(String.format(MULTIPLE_TAGS_FALIURE, EmailCommand.MESSAGE_USAGE));
        }

        Tag[] dummyArrayToGetTagName = tagList.toArray(new Tag[1]);
        String tagName = dummyArrayToGetTagName[0].tagName.toString();
        return new EmailCommand(tagName, subject.toString());
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
```
###### \java\seedu\address\logic\parser\FaceBookCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FacebookCommand object
 */
public class FaceBookCommandParser implements Parser<FaceBookCommand> {

    private static final Logger logger = LogsCenter.getLogger(FaceBookCommand.class);
    /**
     * Parses the given {@code String} of arguments in the context of the FacebookCommand
     * and returns an FacebookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FaceBookCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] Keywords = trimmedArgs.split("\\s+");

            if (trimmedArgs.isEmpty() || Keywords.length != 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE));
            }

            assert Keywords.length == 1;
            Index index = ParserUtil.parseIndex(Keywords[0]);

            return new FaceBookCommand(index);

        } catch (IllegalValueException ive) {
            logger.info("You have entered and invalid index");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\PhotoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new PhotoCommand object
 */
public class PhotoCommandParser implements Parser<PhotoCommand> {

    private final Logger logger = LogsCenter.getLogger(PhotoCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the PhotoCommand
     * and returns an PhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PhotoCommand parse(String args) throws ParseException {

        logger.info("----------------[USER COMMAND][" + args + "]");
        String trimmedArgs = args.trim();
        String regex = "[\\s]+";
        String[] keywords = trimmedArgs.split(regex, 2);
        boolean isInvalidNumberOfArgs = checkIfInvalidNumberOfArgs(keywords);

        if (isInvalidNumberOfArgs) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE)
            );
        }
        String inputPathForImage = keywords[1];
        boolean isFileExists = checkIfFileExists(inputPathForImage);
        if (isFileExists) {
            try {
                Index index = ParserUtil.parseIndex(keywords[0]);
                return new PhotoCommand(index, inputPathForImage);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
            }

        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_IMAGE, PhotoCommand.MESSAGE_USAGE));
        }

    }
    /**
     * Returns true if the number of arguments entered is invalid.
     */
    public static boolean checkIfInvalidNumberOfArgs(String [] keywords) {
        if (keywords.length != 2) {
            return true;
        }
        return false;
    }
    /**
     * Returns true if the image is present in the path entered, else returns false.
     */
    public static boolean checkIfFileExists(String inputFilePath) {

        String url = inputFilePath + "";
        File file = new File(url);
        boolean isFileExists = file.exists();

        if (url.equalsIgnoreCase("delete")) {

            isFileExists = true;
        }
        return isFileExists;

    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\RemoveCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoceReminderCommand object
 */
public class RemoveCommandParser implements Parser<RemoveReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveReminderCommand
     * and returns an RemoveReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveReminderCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveReminderCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveReminderCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\SearchCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Name;
import seedu.address.model.person.SearchContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    public static final String INVALID_DETAILS = "You might have entered invalid date or name with invalid characters!";
    private String name;
    private String date;


    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DOB);


        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DOB)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            DateOfBirth date = ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DOB)).get();
            this.name = name.toString();
            this.date = date.toString();

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(INVALID_DETAILS));
        }
        String [] keywords = new String[2];
        keywords[0] = this.name;
        keywords[1] = this.date;

        return new SearchCommand(new SearchContainsKeywordsPredicate(Arrays.asList(keywords)));

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setReminders(List<? extends ReadOnlyReminder> reminders) throws DuplicateReminderException {
        this.reminders.setReminders(reminders);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setReminders(newData.getReminderList());
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        } catch (DuplicateReminderException ee) {
            assert false : "AddressBooks should not have duplicate reminders";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a reminder to the address book.
     * @throws DuplicateReminderException if an equivalent reminder already exists.
     */
    public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
        Reminder newReminder = new Reminder(reminder);
        reminders.add(newReminder);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Replaces the given reminder {@code target} in the list with {@code changeReadOnlyReminder}.
     * {@code Weaver}'s tag list will be updated with the tags of {@code changeReadOnlyReminder}.
     *
     * @throws DuplicateReminderException if updating the reminder's details causes the reminder to be equivalent to
     *      another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder changeReadOnlyReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireNonNull(changeReadOnlyReminder);

        Reminder changedReminder = new Reminder(changeReadOnlyReminder);
        reminders.setReminder(target, changedReminder);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws ReminderNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeReminder(ReadOnlyReminder key) throws ReminderNotFoundException {
        if (reminders.remove(key)) {
            return true;
        } else {
            throw new ReminderNotFoundException();
        }
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyReminder> getReminderList() {
        return reminders.asObservableList();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate the model has changed */
    @Override
    public synchronized void clearBrowserPanel() {
        raise(new ClearBrowserPanelEvent());
    }

    @Override
    public synchronized void sendMailToContacts(String tag, String subject, List<ReadOnlyPerson> lastShownList) throws
            IOException, URISyntaxException, IllegalValueException {

        String appendEmailAddress = getAppendedEmailIdOfContacts(tag, lastShownList);
        openUpDesktopBrowser(appendEmailAddress, subject);
    }

    public String getAppendedEmailIdOfContacts(String tag, List<ReadOnlyPerson> lastShownList) throws
            IllegalValueException {

        ReadOnlyPerson getPerson;
        int loopVariable = 0;
        String appendEmailAddress = "";

        while (loopVariable < lastShownList.size()) {
            getPerson = lastShownList.get(loopVariable);

            if (getPerson.getTags().contains(new Tag(tag))) {
                appendEmailAddress = appendEmailAddress + getPerson.getEmail().toString() + "+";
            }
            loopVariable++;
        }
        return appendEmailAddress;
    }

    /** Opens the default browser in your desktop */
    private void openUpDesktopBrowser(String appendEmailAddress, String subject) throws IOException,
            URISyntaxException {

        appendEmailAddress = appendEmailAddress.substring(0, appendEmailAddress.length() - 1);

        String Gmail_Url = "https://mail.google.com/mail/?view=cm&fs=1&to=" + appendEmailAddress + "&su=" + subject;

        if (Desktop.isDesktopSupported()) {

            Desktop.getDesktop().browse(new URI(Gmail_Url));
        }

    }

    /** Raises an facebook event to indicate the model has changed */
    @Override
    public synchronized void faceBook(ReadOnlyPerson person) throws PersonNotFoundException {

        raise(new FaceBookEvent(person));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addReminder(ReadOnlyReminder target) throws DuplicateReminderException {

        addressBook.addReminder(target);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        indicateAddressBookChanged();

    }
    @Override
    public synchronized void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException {

        addressBook.removeReminder(target);
        indicateAddressBookChanged();

    }

    @Override
    public synchronized void addPhotoPerson(ReadOnlyPerson person, String filePath, Index targetIndex)
            throws PersonNotFoundException, IOException, IllegalValueException {


        person.imageProperty().setValue(new FileImage(filePath));
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();

    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder changedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireAllNonNull(target, changedReminder);

        addressBook.updateReminder(target, changedReminder);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyReminder} backed by the internal list of
     * {@code weaver}
     */
    @Override
    public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
        return FXCollections.unmodifiableObservableList(filteredReminders);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
    }
```
###### \java\seedu\address\model\person\DateOfBirth.java
``` java
/**
 * Represents a Person's DateOfBirth in the address book.
 */

public class DateOfBirth {

    /**
     * Represents a Person's Date Of birth
     * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
     */

    public static final String BIRTHDAY_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";


    public static final int DAYS_IN_FEBRUARY = 28;
    public static final int FEBRUARY = 2;
    public static final int FIRST_INDEX = 0;
    public static final int INVALID_NUMBER_OF_DAYS = -1;
    public static final int LAST_INDEX = 1;

    public static final int [] MONTHS_WITH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Date of Birth must be a Valid Date that is days should be less than 32 , months should be less"
                    + " than 12\n.For the month of February make sure days are less than 29 unless it is a leap year.\n"
                    + "The following format should be followed: \n"
            + "'.' and '-' can be used as separators. \n";

    private static final Logger logger = LogsCenter.getLogger(SearchContainsKeywordsPredicate.class);
    public final String date;


    public DateOfBirth(String date) throws IllegalValueException {

        String trimmedDate;
        if (!isValidBirthday(date)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        if (!date.equals("")) {
            trimmedDate = date.trim();
            this.date = trimmedDate;
        } else {
            this.date = "";
        }

    }
    @Override
    public String toString() {
        return date;
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
        boolean isValidDate = checkIfValidDate(birthday);

        return isValidDate;
    }
    /**
     * Returns true if the date has invalid conditions else returns false
     */
    public static boolean checkIfValidDate(String date) {


        int lastIndexOfSeparator = getIndexOfSeparator(date, LAST_INDEX);
        int firstIndexOfSeparator = getIndexOfSeparator(date, FIRST_INDEX);
        int yearNumber = getYearNumber(lastIndexOfSeparator, date);
        int monthNumber = getMonthNumber(firstIndexOfSeparator, lastIndexOfSeparator, date);
        boolean isLeapYear = checkIfLeapYear(yearNumber);
        int dayNumber = getDayNumber(firstIndexOfSeparator, date);
        boolean isValidDateOfFebruary = checkIfValidDateOfFebruary(
                date, monthNumber, yearNumber, dayNumber, isLeapYear);
        boolean isValidNumberOfDaysInMonth = checkIfValidNumberOfDaysInMonth(monthNumber, dayNumber);

        if (isValidDateOfFebruary && isValidNumberOfDaysInMonth) {
            return true;
        }
        return false;

    }
    /**
     * Checks if number of days in February are valid
     */
    public static boolean checkIfValidDateOfFebruary(String date, int month, int year, int day, boolean isLeapYear) {

        if (month == FEBRUARY) {

            if (isLeapYear && day > (DAYS_IN_FEBRUARY + 1) || (!isLeapYear && day > DAYS_IN_FEBRUARY)) {
                return false;
            }
        }
        logger.info("The date has valid number of days in February!");
        return true;
    }
    /**
     * Returns true if the number of days in a month are valid else returns false.
     */
    public static boolean checkIfValidNumberOfDaysInMonth(int month, int day) {

        if (MONTHS_WITH_DAYS[month - 1] < day && month != FEBRUARY) {
            return false;
        }
        return true;
    }
    /**
     * Returns true if it is a leap year else returns false.
     */
    public static boolean checkIfLeapYear(int yearNumber) {

        if (yearNumber % 400 == 0 || (yearNumber % 4 == 0 && yearNumber % 100 != 0)) {
            return true;
        }
        return false;
    }

    public static int getYearNumber(int lastIndexOfSeparator, String date) {

        String year = date.substring(lastIndexOfSeparator + 1);
        assert Integer.parseInt(year) > 0;
        return Integer.parseInt(year);
    }
    public static int getMonthNumber(int firstIndexOfSeparator, int lastIndexOfSeparator, String date) {

        String month = date.substring(firstIndexOfSeparator + 1, lastIndexOfSeparator);
        return Integer.parseInt(month);
    }
    public static int getDayNumber(int firstIndexOfSeparator, String date) {

        String dayNumber = date.substring(0, firstIndexOfSeparator);
        return Integer.parseInt(dayNumber);
    }
    /**
     * Returns the index position of the separator '-' or '.' .
     */

    public static int getIndexOfSeparator(String date, int position) {

        int storesIndex = INVALID_NUMBER_OF_DAYS;
        for (int loopVariable = 0; loopVariable < date.length(); loopVariable++) {

            if (date.charAt(loopVariable) == '.' || date.charAt(loopVariable) == '-') {
                storesIndex = loopVariable;

                if (position == FIRST_INDEX) {
                    break;
                }
            }
        }
        return storesIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateOfBirth
                && this.date.equals(((DateOfBirth) other).date));
    }

}
```
###### \java\seedu\address\model\person\EmailSubject.java
``` java
/**
 * Represents an Email's Subject line.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class EmailSubject {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Subject lines should only contain alphanumeric characters and spaces, and it should not be blank.";

    /*
     * The first character of the subject must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String subject;

    /**
     * Validates given subject
     *
     * @throws IllegalValueException if given subject string is invalid.
     */
    public EmailSubject(String subject) throws IllegalValueException {

        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!isValidSubject(trimmedSubject)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.subject = subject;
    }

    /**
     * Returns true if a given string is a valid email subject.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailSubject // instanceof handles nulls
                && this.subject.equals(((EmailSubject) other).subject)); // state check
    }

    @Override
    public String toString() {
        return subject;
    }
}
```
###### \java\seedu\address\model\person\FacebookUsername.java
``` java
/**
 * Represents a Person's username on Facebook.
 */
public class FacebookUsername {

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final String username;

    public FacebookUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedName = username.trim();
        this.username = trimmedName;
    }
    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.username.equals(((FacebookUsername) other).username)); // state check
    }

}
```
###### \java\seedu\address\model\person\FileImage.java
``` java
/**
 * Represents a Person's File Path of the image he/she is assigned
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class FileImage {

    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "File Path must be correctly entered, that is the image must exist in the path specified\n"
            + "For example: src/resources/images/clock.png";

    public final String filePath;

    public FileImage(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        if (!isValidImage(filePath)) {
            throw new IllegalValueException(MESSAGE_IMAGE_CONSTRAINTS);
        }
        String trimmedName = filePath.trim();

        this.filePath = trimmedName;
    }

    /**
     * Returns true if a given string is a valid Image path.
     */
    public static boolean isValidImage(String filePath) {

        String trimmedPath = filePath.trim();

        if (trimmedPath.isEmpty()) {
            return true;
        }
        File file = new File(trimmedPath);
        return file.exists();

    }

    @Override
    public String toString() {
        return filePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileImage // instanceof handles nulls
                && this.filePath.equals(((FileImage) other).filePath)); // state check
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }

    public String getFilePath() {
        return filePath;
    }


}
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public FileImage getImage() {
        return image.get();
    }

    public ObjectProperty<FileImage> imageProperty() {
        return image;
    }

    public void setDateOfBirth(DateOfBirth date) {
        this.date.set(requireNonNull(date));
    }

    public void setImage(FileImage image) {
        this.image.set(requireNonNull(image));
    }
    @Override
    public ObjectProperty<DateOfBirth> dateOfBirthProperty() {
        return date;
    }

    @Override
    public  DateOfBirth getDateOfBirth() {
        return date.get();
    }

    public void setUsername(FacebookUsername username) {
        this.username.set(requireNonNull(username));
    }
    @Override
    public ObjectProperty<FacebookUsername> usernameProperty() {
        return username;
    }
```
###### \java\seedu\address\model\person\SearchContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} and {@code DateOFbirth} matches the keywords given.
 */

public class SearchContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private static final Logger logger = LogsCenter.getLogger(SearchContainsKeywordsPredicate.class);
    private final List<String> keywords;

    public SearchContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


    @Override
    public boolean test (ReadOnlyPerson person) {

        if (keywords.size() <= 1) {
            logger.warning("The number of arguements entered do not match the command format");
            return false;
        }

        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keywords.get(0))
               && StringUtil.containsWordIgnoreCase(person.getDateOfBirth().date, keywords.get(1));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((SearchContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the reminders list.
     * This list will not contain any duplicate reminders.
     */

    ObservableList<ReadOnlyReminder> getReminderList();
```
###### \java\seedu\address\model\reminder\DetailsContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyReminder}'s {@code Name} matches any of the keywords given.
 */
public class DetailsContainsKeywordsPredicate implements Predicate<ReadOnlyReminder> {
    private final List<String> keywords;

    public DetailsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyReminder reminder) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(reminder.getDetails().details, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((DetailsContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\reminder\DueDate.java
``` java
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;


/**
 * Represents a Reminders DueDate in Weaver.
 */

public class DueDate {

    /**
     * Represents a Reminders DueDate
     * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
     */

    public static final int DAYS_IN_FEBRUARY = 28;
    public static final String DUE_DATE_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";

    public static final int FEBRUARY = 2;
    public static final int FIRST_INDEX = 0;
    public static final int INVALID_INDEX_OF_SEPARATOR = -1;
    public static final int LAST_INDEX = 1;
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Due Date must be a Valid Date and in the following format: \n"
                    + "'.' and '-' can be used as separators. \n";

    public static final int [] MONTHS_WITH_DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final Logger logger = LogsCenter.getLogger(DueDate.class);

    public final String date;




    public DueDate(String date) throws IllegalValueException {

        String trimmedDate;
        String nullValueOfDate = "";
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        if (!date.equals(nullValueOfDate)) {
            trimmedDate = date.trim();
            this.date = trimmedDate;
        } else {
            this.date = nullValueOfDate;
        }

    }
    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidDate(String dueDate) {

        String trimmedDate = dueDate.trim();
        if (trimmedDate.isEmpty()) {
            return false;
        }
        if (!trimmedDate.matches(DUE_DATE_VALIDATION_REGEX)) {
            logger.info("Invalid format of date.");
            return false;
        }
        boolean isValidDate = checkIfValidDate(dueDate);

        return isValidDate;

    }

    /**
     * Checks if number of days in a given month are valid
     * Returns true if the given date is valid, that is has valid number of days in a month.
     */
    public static boolean checkIfValidDate(String date) {


        int lastIndexOfSeparator = getIndexOfSeparator(date, LAST_INDEX);
        int firstIndexOfSeparator = getIndexOfSeparator(date, FIRST_INDEX);
        int yearNumber = getYearNumber(lastIndexOfSeparator, date);
        int monthNumber = getMonthNumber(firstIndexOfSeparator, lastIndexOfSeparator, date);
        boolean isLeapYear = checkIfLeapYear(yearNumber);
        int dayNumber = getDayNumber(firstIndexOfSeparator, date);
        boolean isValidDateOfFebruary = checkIfValidDateOfFebruary(
                date, monthNumber, yearNumber, dayNumber, isLeapYear);
        boolean isValidNumberOfDaysInMonth = checkIfValidNumberOfDaysInMonth(monthNumber, dayNumber);

        if (isValidDateOfFebruary && isValidNumberOfDaysInMonth) {
            return true;
        }
        return false;

    }
    /**
     * Returns true if number of days in February are valid else return false.
     */
    public static boolean checkIfValidDateOfFebruary(String date, int month, int year, int day, boolean isLeapYear) {

        if (month == FEBRUARY) {

            if (isLeapYear && day > (DAYS_IN_FEBRUARY + 1) || (!isLeapYear && day > DAYS_IN_FEBRUARY)) {
                logger.warning("Invalid number of days in February");
                return false;
            }
        }
        return true;
    }
    /**
     * Returns true number of number of days in a month are valid else returns false.
     */
    public static boolean checkIfValidNumberOfDaysInMonth(int month, int day) {

        if (MONTHS_WITH_DAYS[month - 1] < day && month != FEBRUARY) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if year is a leap year.
     */
    public static boolean checkIfLeapYear(int yearNumber) {

        if (yearNumber % 400 == 0 || (yearNumber % 4 == 0 && yearNumber % 100 != 0)) {
            return true;
        }
        return false;
    }
    /**
     * Returns the year number in the date.
     */
    public static int getYearNumber(int lastIndexOfSeparator, String date) {

        String year = date.substring(lastIndexOfSeparator + 1);
        return Integer.parseInt(year);
    }
    /**
     * Returns the month number in the date.
     */
    public static int getMonthNumber(int firstIndexOfSeparator, int lastIndexOfSeparator, String date) {

        String month = date.substring(firstIndexOfSeparator + 1, lastIndexOfSeparator);
        return Integer.parseInt(month);
    }
    /**
     * Returns the day number in the date.
     */
    public static int getDayNumber(int firstIndexOfSeparator, String date) {

        String dayNumber = date.substring(0, firstIndexOfSeparator);
        return Integer.parseInt(dayNumber);
    }
    /**
     * Returns the index of the separator '-' or '.' depending on the position.
     */

    public static int getIndexOfSeparator(String date, int position) {

        int storesIndex = INVALID_INDEX_OF_SEPARATOR;
        for (int loopVariable = 0; loopVariable < date.length(); loopVariable++) {

            if (date.charAt(loopVariable) == '.' || date.charAt(loopVariable) == '-') {
                storesIndex = loopVariable;

                if (position == FIRST_INDEX) {
                    break;
                }
            }
        }
        assert storesIndex > INVALID_INDEX_OF_SEPARATOR;
        return storesIndex;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DueDate
                && this.date.equals(((DueDate) other).date));
    }
    @Override
    public int hashCode() {
        return date.hashCode();
    }


}
```
###### \java\seedu\address\model\reminder\exceptions\DuplicateReminderException.java
``` java
import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Reminder objects.
 */
public class DuplicateReminderException extends DuplicateDataException {
    public DuplicateReminderException() {
        super("Operation would result in duplicate reminders");
    }
}
```
###### \java\seedu\address\model\reminder\exceptions\ReminderNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified reminder.
 */
public class ReminderNotFoundException extends Exception {}
```
###### \java\seedu\address\model\reminder\Priority.java
``` java
/**
 * Represents a Reminders priority level, which can be either high, medium or low.
 */

public class Priority {

    public static final String PRIORITY_CONSTRAINTS =
            "Priority must have one of the three values which are - High, Low, and Medium";

    public final String priority;

    public Priority(String priority) throws IllegalValueException {
        requireNonNull(priority);


        if (!isValidPriority(priority)) {
            throw new IllegalValueException(PRIORITY_CONSTRAINTS);
        }
        this.priority = priority;
    }

    /**
     * Returns true if a given string is a valid Priority.
     */
    public static boolean isValidPriority(String priority) {


        if (!(priority.equalsIgnoreCase("Priority Level: High")
                || priority.equalsIgnoreCase("Priority Level: Medium")
                || priority.equalsIgnoreCase("Priority Level: Low"))) {

            return false;
        }
        return true;
    }

    /**
     * Returns the priority Level of Reminder.
     */
    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return priority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.equals(((Priority) other).priority)); // state check
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }
}
```
###### \java\seedu\address\model\reminder\ReadOnlyReminder.java
``` java
import javafx.beans.property.ObjectProperty;

/**
 * A read-only immutable interface for a Reminder.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyReminder {

    ObjectProperty<ReminderDetails> detailsProperty();
    ReminderDetails getDetails();
    ObjectProperty<Priority> priorityProperty();
    Priority getPriority();
    ObjectProperty<DueDate> dueDateProperty();
    DueDate getDueDate();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyReminder other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDetails().equals(this.getDetails()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getDueDate().equals(this.getDueDate()));
    }

    /**
     * Formats and returns the reminder as text, showing all reminder details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDetails())
                .append(" Details: ")
                .append(getPriority())
                .append(" Priority: ")
                .append(getDueDate())
                .append(" DueDate: ");
        return builder.toString();
    }

}
```
###### \java\seedu\address\model\reminder\Reminder.java
``` java
/**
 * Represents a Reminder in the Weaver.
 * Guarantees: details are present and not null, field values are validated.
 */

public class Reminder implements ReadOnlyReminder {

    private ObjectProperty<ReminderDetails> details;
    private ObjectProperty<Priority> priority;
    private ObjectProperty<DueDate> dueDate;

    /**
     * Every field must be present and not null.
     */
    public Reminder(ReminderDetails details, Priority priority, DueDate dueDate) {
        requireAllNonNull(details, priority, dueDate);
        this.details = new SimpleObjectProperty<>(details);
        this.priority = new SimpleObjectProperty<>(priority);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
    }

    /**
     * Creates a copy of the given ReadOnlyReminder.
     */
    public Reminder(ReadOnlyReminder source) {
        this(source.getDetails(), source.getPriority(), source.getDueDate());
    }

    public void setDetails(ReminderDetails details) {
        this.details.set(requireNonNull(details));
    }

    @Override
    public ObjectProperty<ReminderDetails> detailsProperty() {
        return details;
    }

    @Override
    public ReminderDetails getDetails() {
        return details.get();
    }


    public void setPriority(Priority priority) {
        this.priority.set(requireNonNull(priority));
    }

    @Override
    public ObjectProperty<Priority> priorityProperty() {
        return priority;
    }

    @Override
    public Priority getPriority() {
        return priority.get();
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate.set(requireNonNull(dueDate));
    }

    @Override
    public ObjectProperty<DueDate> dueDateProperty() {
        return dueDate;
    }

    @Override
    public DueDate getDueDate() {
        return dueDate.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyReminder // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyReminder) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(details, priority, dueDate);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
```
###### \java\seedu\address\model\reminder\ReminderDetails.java
``` java
/**
 * Represents a Reminder's details
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class ReminderDetails {

    public static final String MESSAGE_REMINDER_CONSTRAINTS =
            "Reminders should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the Reminder must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String details;

    /**
     * Validates given details.
     *
     * @throws IllegalValueException if given details string is invalid.
     */
    public ReminderDetails(String details) throws IllegalValueException {
        requireNonNull(details);
        String trimmedDetails = details.trim();
        if (!isValidDetail(trimmedDetails)) {
            throw new IllegalValueException(MESSAGE_REMINDER_CONSTRAINTS);
        }
        this.details = trimmedDetails;
    }

    /**
     * Returns true if a given string is valid
     */
    public static boolean isValidDetail(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return details;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderDetails // instanceof handles nulls
                && this.details.equals(((ReminderDetails) other).details)); // state check
    }

    @Override
    public int hashCode() {
        return details.hashCode();
    }

}
```
###### \java\seedu\address\model\reminder\UniqueReminderList.java
``` java
/**
 * A list of reminders that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Reminder#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueReminderList implements Iterable<Reminder> {

    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyReminder> mappedList = EasyBind.map(internalList, (reminder) -> reminder);

    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     */
    public boolean contains(ReadOnlyReminder toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a reminder to the list.
     *
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
     * Replaces the reminder {@code target} in the list with {@code changedReminder}.
     *
     * @throws DuplicateReminderException if the replacement is equivalent to another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    public void setReminder(ReadOnlyReminder target, ReadOnlyReminder changedPerson)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireNonNull(changedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }
        if (!target.equals(changedPerson) && internalList.contains(changedPerson)) {
            throw new DuplicateReminderException();
        }

        internalList.set(index, new Reminder(changedPerson));
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
        for (final ReadOnlyReminder person : reminders) {
            replacement.add(new Reminder(person));
        }
        setReminders(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
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
###### \java\seedu\address\storage\XmlAdaptedReminder.java
``` java
/**
 * JAXB-friendly version of the Reminder.
 */
public class XmlAdaptedReminder {

    @XmlElement(required = true)
    private String details;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String dueDate;

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
        details = source.getDetails().details;
        priority = source.getPriority().priority;
        dueDate = source.getDueDate().date;
    }

    /**
     * Converts this jaxb-friendly adapted reminder object into the model's reminder object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder
     */
    public Reminder toModelType() throws IllegalValueException {

        final ReminderDetails details = new ReminderDetails(this.details);
        final Priority priority = new Priority(this.priority);
        final DueDate dueDate = new DueDate(this.dueDate);

        return new Reminder(details, priority, dueDate);
    }
}

```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @XmlElement
    private List<XmlAdaptedReminder> reminders;
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        reminders = new ArrayList<>();
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyReminder> getReminderList() {
        final ObservableList<ReadOnlyReminder> reminders = this.reminders.stream().map(r -> {
            try {
                return r.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(reminders);
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads a default HTML file with a background that matches the general theme when the clear command is executed.
     */
    private void loadDefaultPage() {
        try {

            URL defaultPage = new URL(DEFAULT_PAGE);
            loadPage(defaultPage.toExternalForm());

        } catch (MalformedURLException e) {
            logger.info("Invalid URL");
        }

    }

    private void loadDeafultPageBrowser() {

        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFUALT_PAGE_OF_BROWSER);
        loadPage(defaultPage.toExternalForm());
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleClearCommandExecutionEvent (ClearBrowserPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDeafultPageBrowser();
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Shows Facebook profile picture of user
     */
    public void loadPersonFaceBookPage(ReadOnlyPerson person) throws ParseException {

        String url = FACEBOOK_PROFILE_PAGE + person.getUsername().toString();
        loadPage(url);

    }

    @Subscribe
    public void handleFaceBookEvent(FaceBookEvent event) throws ParseException {

        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        loadPersonFaceBookPage(event.getPerson());
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Binds a photo to a persons PersonCard.
     */
    public void assignImageToPerson(String filePath) throws ParseException {

        String url;
        String Message_Image_Removed = "The image may have been removed from"
                + " the previous location!";

        if (filePath.equals("")) {
            url = "/images/user.png";
            Image Display = new Image(url);
            image.setImage(Display);

        } else {
            url = filePath + "";
            File file = new File(url);
            boolean isFileExists = file.exists();

            if (!isFileExists) {
                url = "/images/address_book_32.png";
                Image Display = new Image(url);
                image.setImage(Display);
                throw new ParseException(
                            String.format(Message_Image_Removed, PhotoCommand.MESSAGE_USAGE)
                    );
            }
            else {
                Image display = new Image(file.toURI().toString());
                image.setImage(display);
            }

        }
    }
```
###### \java\seedu\address\ui\ReminderCard.java
``` java
//import javax.swing.text.html.ImageView;


/**
 * An UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {

    private static final String FXML = "ReminderListCard.fxml";
    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */

    public final ReadOnlyReminder reminder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label about;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label date;



    public ReminderCard(ReadOnlyReminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        bindListeners(reminder);
        registerAsAnEventHandler(this);
        String currentFontSize = FontSize.getCurrentFontSizeLabel();
        setFontSize(currentFontSize);
    }


    /**
     * Binds the individual UI elements to observe their respective {@code Reminder} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyReminder reminder) {
        about.textProperty().bind(Bindings.convert(reminder.detailsProperty()));
        priority.textProperty().bind(Bindings.convert(reminder.priorityProperty()));
        date.textProperty().bind(Bindings.convert(reminder.dueDateProperty()));

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
                && reminder.equals(card.reminder);
    }
```
###### \java\seedu\address\ui\ReminderListPanel.java
``` java
/**
 * Panel containing the list of reminders.
 */
public class ReminderListPanel extends UiPart<Region> {

    private static final String FXML = "ReminderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReminderListPanel.class);

    @FXML
    private ListView<ReminderCard> reminderListView;

    public ReminderListPanel(ObservableList<ReadOnlyReminder> reminderList) {
        super(FXML);
        setConnections(reminderList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyReminder> reminderList) {
        ObservableList<ReminderCard> mappedList = EasyBind.map(
                reminderList, (reminder) -> new ReminderCard(reminder,
                        reminderList.indexOf(reminder) + 1));
        reminderListView.setItems(mappedList);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        setEventHandlerForSelectionChangeReminder();
    }

    private void setEventHandlerForSelectionChangeReminder() {
        reminderListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new ReminderPanelSelectionChangedEvent(newValue));
                    }
                });
    }


    /**
     * Scrolls to the {@code ReminderCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            reminderListView.scrollTo(index);
            reminderListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
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
            } else {
                setGraphic(reminder.getRoot());
            }
        }
    }

}
```
###### \resources\view\PersonListCard.fxml
``` fxml
                <ImageView fx:id="image" fitHeight="100.0" fitWidth="81.0" pickOnBounds="true" preserveRatio="true">
                    <image>
                        <Image url="/images/user.png" />
                    </image>
               <HBox.margin>
                  <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
               </HBox.margin>
                </ImageView>
```
###### \resources\view\ReminderListCard.fxml
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
            <HBox alignment="CENTER_LEFT" prefHeight="71.0" prefWidth="130.0" spacing="5">
                <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" HBox.hgrow="ALWAYS">
                    <children>
                        <HBox alignment="CENTER_LEFT">
                            <children>
                                <Label fx:id="id" alignment="BOTTOM_LEFT" styleClass="cell_big_label">
                                    <minWidth>
                                        <!-- Ensures that the label text is never truncated -->
                                        <Region fx:constant="USE_PREF_SIZE" />
                                    </minWidth>
                                </Label>
                                <Label fx:id="about" alignment="BOTTOM_LEFT" text="\$about" styleClass="cell_big_label"/>
                            </children>
                            <padding>
                                <Insets bottom="0.0" left="3.0" right="5.0" top="5.0" />
                            </padding>
                        </HBox>
                    </children>
                    <padding>
                        <Insets top="5.0" />
                    </padding>
                </VBox>

            </HBox>
            <HBox alignment="CENTER_LEFT" spacing="5">

                <Label fx:id="priority" styleClass="cell_small_label" text="\$priority">
                    <padding>
                        <Insets top="1.0" left="10.0" />
                    </padding>
                </Label>
                <padding>
                    <Insets top="1.0" />
                </padding>
            </HBox>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="date" styleClass="cell_small_label" text="\$date">
                    <padding>
                        <Insets top="5.0" left="4.0"/>
                    </padding>
                </Label>
                <padding>
                    <Insets top="5.0" left="4.5"/>
                </padding>
            </HBox>
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\ReminderListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="reminderListView" VBox.vgrow="ALWAYS" />
</VBox>
```
