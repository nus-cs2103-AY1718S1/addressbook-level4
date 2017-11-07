# RonakLakhotia
###### \java\seedu\address\commons\events\storage\ImageStorage.java
``` java
package seedu.address.commons.events.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.parser.exceptions.ImageException;

/** Reads and stores image file
 */

public class ImageStorage {

    /** Reads and stores image file
     */

    public String execute(String path, int newPath) throws IOException {


        File fileToRead = null;
        BufferedImage image = null;

        File fileToWrite = null;

        String uniquePath = null;

        try {
            String home = System.getProperty("user.home");
            java.nio.file.Path path1 = java.nio.file.Paths.get(home, "Desktop", path);
            String url = path1 + "";
            image = new BufferedImage(963, 640, BufferedImage.TYPE_INT_ARGB);
            fileToRead = new File(url);
            image = ImageIO.read(fileToRead);
            uniquePath = Integer.toString(newPath);
            fileToWrite = new File("src/main/resources/images/" + uniquePath + ".jpg");
            ImageIO.write(image, "jpg", fileToWrite);


        } catch (IOException e) {
            throw  new ImageException(String.format(MESSAGE_INVALID_IMAGE,
                    PhotoCommand.MESSAGE_FILE_PATH_NOT_FOUND));
        }

        return uniquePath;

    }

}
```
###### \java\seedu\address\commons\events\ui\FaceBookEvent.java
``` java
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * To raise a Facebook Event
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
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

/**
 * Edits the details of an existing reminder in Weaver
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
        ChangeReminderCommand c = (ChangeReminderCommand) other;
        return index.equals(c.index)
                && changeReminderDescriptor.equals(c.changeReminderDescriptor);
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
###### \java\seedu\address\logic\commands\FaceBookCommand.java
``` java
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 *  Shows a person's address on Google Maps in browser
 */
public class FaceBookCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "facebook";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Shows the profile of the user whose"
            + " index is entered\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_FACEBOOK_SHOWN_SUCCESS = "Profile of Person: %1$s";
    public static final String MESSAGE_NO_USERNAME = "This Person has no Facebook username!\n";

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
            model.faceBook(personToShow);
        } catch (PersonNotFoundException pnfe) {
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
import java.io.IOException;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.FileImage;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a photo of the person to the addressBook
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

    public static final String MESSAGE_FILE_PATH_NOT_FOUND = "Incorrect file path";

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

        if (personToAddPhoto.getImage().getFilePath().equals("") && filePath.equalsIgnoreCase("delete")) {
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
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

/**
 * Deletes a reminder identified using it's last displayed index from the address book.
 */
public class RemoveReminderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reminder identified by the index number used in the last reminder listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

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
import seedu.address.model.person.SearchContainsKeywordsPredicate;

/**
 * Searches and lists all persons in address book whose name and DateOfBirth matches the argument keywords.
 * Keyword matching is case insensitive.
 */

public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name and Date Of Birth "
            + "contain any of the specified keyowrds and displays them as a list with index number. \n"
            + "Parameters: Name and Date O Birth\n"
            + "Example: " + COMMAND_WORD + " alice 13.10.1997";

    private final SearchContainsKeywordsPredicate predicate;

    public SearchCommand(SearchContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute() {
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
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddReminder;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;

/**
 * Parses input arguments and creates a new AddReminderCommand object
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
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeReminderCommand;
import seedu.address.logic.commands.ChangeReminderCommand.ChangeReminderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ChangeReminderCommand object
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
###### \java\seedu\address\logic\parser\FaceBookCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FaceBookCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MapCommand object
 */
public class FaceBookCommandParser implements Parser<FaceBookCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MapCommand
     * and returns an MapCommand object for execution.
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


            Index index = ParserUtil.parseIndex(Keywords[0]);

            return new FaceBookCommand(index);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\PhotoCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_IMAGE;

import java.io.File;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PhotoCommand object
 */
public class PhotoCommandParser implements Parser<PhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PhotoCommand
     * and returns an PhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PhotoCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();


        String regex = "[\\s]+";
        String[] keywords = trimmedArgs.split(regex, 2);

        if (keywords.length == 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE)
            );
        }


        String inputFile = keywords[1];
        String url = inputFile + "";

        File file = new File(url);
        boolean FileExists = file.exists();

        if (url.equalsIgnoreCase("delete")) {

            FileExists = true;
        }

        if (FileExists) {
            try {
                Index index = ParserUtil.parseIndex(keywords[0]);
                return new PhotoCommand(index, (keywords[1]));
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
            }

        }
        else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_IMAGE, PhotoCommand.MESSAGE_USAGE));
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

import java.util.Arrays;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SearchContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (trimmedArgs.isEmpty() || nameKeywords.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }


        return new SearchCommand(new SearchContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    private final UniqueReminderList reminders;
```
###### \java\seedu\address\model\AddressBook.java
``` java
        reminders = new UniqueReminderList();
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setReminders(List<? extends ReadOnlyReminder> reminders) throws DuplicateReminderException {
        this.reminders.setReminders(reminders);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a reminder to the address book.
     * @throws DuplicateReminderException if an equivalent person already exists.
     */
    public void addReminder(ReadOnlyReminder r) throws DuplicateReminderException {
        Reminder newReminder = new Reminder(r);
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
###### \java\seedu\address\model\person\FacebookUsername.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's username on Facebook
 */
public class FacebookUsername {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Person usernames should be the username of the person on Facebook";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */


    public final String username;

    /**
     * Validates given username
     *
     * @throws IllegalValueException if given name string is invalid.
     */
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

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}

```
###### \java\seedu\address\model\person\FileImage.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's name in the address book.
 */
public class FileImage {

    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "File Path must be correctly entered";

    public final String filePath;

    public FileImage(String filePath) throws IllegalValueException {
        requireNonNull(filePath);
        String trimmedName = filePath.trim();

        this.filePath = trimmedName;
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
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<DateOfBirth> dateOfBirthProperty();
    DateOfBirth getDateOfBirth();
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<FileImage> imageProperty();
    FileImage getImage();
    ObjectProperty<FacebookUsername> usernameProperty();
    FacebookUsername getUsername();
```
###### \java\seedu\address\model\person\SearchContainsKeywordsPredicate.java
``` java
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */

public class SearchContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {

    private final List<String> keywords;

    public SearchContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test (ReadOnlyPerson person) {

        if (keywords.size() <= 1) {
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
###### \java\seedu\address\model\reminder\DueDate.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Reminders DueDate in the address book.
 */

public class DueDate {

    /**
     * Represents a Reminders DueDate
     * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
     */

    public static final String DUE_DATE_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";


    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Due Date must be a Valid Date and in the following format: \n"
                    + "'.' and '/' can be used as separators. \n";

    public final String date;

    public DueDate(String date) throws IllegalValueException {

        String trimmedDate;
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
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
    public static boolean isValidDate(String dueDate) {

        String trimmedDate = dueDate.trim();
        if (trimmedDate.isEmpty()) {
            return false;
        }
        if (!trimmedDate.matches(DUE_DATE_VALIDATION_REGEX)) {
            return false;
        }

        return true;
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
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Reminders priority level
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
     * Get the priority Level of Reminder
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
     * Formats the person as text, showing all contact details.
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
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Person in the address book.
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
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

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
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

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
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;

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
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private StackPane reminderListPlaceholder;
```
###### \java\seedu\address\ui\ReminderCard.java
``` java
import static seedu.address.model.font.FontSize.getAssociateFxFontSizeString;

import java.util.Random;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.model.font.FontSize;
import seedu.address.model.reminder.ReadOnlyReminder;

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
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
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

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
        //setFontSizeForAllImages(event.getFontSize());
    }

    private void setFontSize(String newFontSize) {
        assert (FontSize.isValidFontSize(newFontSize));

        String fxFormatFontSize = getAssociateFxFontSizeString(newFontSize);
        setFontSizeForAllAttributesExceptTag(fxFormatFontSize);
    }


    private void setFontSizeForAllAttributesExceptTag(String fontSize) {
        about.setStyle(fontSize);
        id.setStyle(fontSize);
        priority.setStyle(fontSize);
        date.setStyle(fontSize);
    }


}
```
###### \java\seedu\address\ui\ReminderListPanel.java
``` java
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ReminderPanelSelectionChangedEvent;
import seedu.address.model.reminder.ReadOnlyReminder;

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
