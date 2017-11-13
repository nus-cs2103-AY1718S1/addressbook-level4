# Syabil
###### \java\seedu\address\logic\commands\DeleteMeetingCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;

/**
 * Deletes a meeting in the address book
 */
public class DeleteMeetingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletemeeting";
    public static final String COMMAND_ALIAS = "dm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting identified by the index number used in the meeting list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Delete meeting: %1$s";

    private final Index targetIndex;

    public DeleteMeetingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyMeeting> lastShownList = model.getFilteredMeetingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        ReadOnlyMeeting meetingToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteMeeting(meetingToDelete);
        } catch (MeetingNotFoundException mnfe) {
            assert false : "The target meeting cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMeetingCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteMeetingCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FindMeetingCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;

/**
 * Finds and lists all meetings in address book whose meeting name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindMeetingCommand extends Command {

    public static final String COMMAND_WORD = "findmeeting";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meetings which contains any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_ALIAS + " Lunch";

    private final MeetingContainsKeywordsPredicate predicate;

    public FindMeetingCommand(MeetingContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredMeetingList(predicate);
        return new CommandResult(getMessageForMeetingListShownSummary(model.getFilteredMeetingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMeetingCommand // instanceof handles nulls
                && this.predicate.equals(((FindMeetingCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\DeleteMeetingCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteMeetingCommand object
 */
public class DeleteMeetingCommandParser implements Parser<DeleteMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMeetingCommand
     * and returns an DeleteMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMeetingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMeetingCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\FindMeetingCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.MeetingContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindMeetingCommandParser implements Parser<FindMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMeetingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMeetingCommand.MESSAGE_USAGE));
        }

        String[] meetingKeywords = trimmedArgs.split("\\s+");

        return new FindMeetingCommand(new MeetingContainsKeywordsPredicate(Arrays.asList(meetingKeywords)));
    }
}
```
###### \java\seedu\address\model\meeting\MeetingContainsKeywordsPredicate.java
``` java
package seedu.address.model.meeting;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Tests that a {@code ReadOnlyMeeting}'s {@code Meeting} matches any of the keywords given.
 */
public class MeetingContainsKeywordsPredicate implements Predicate<ReadOnlyMeeting> {
    private final List<String> keywords;

    public MeetingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }


```
###### \java\seedu\address\model\meeting\MeetingContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(ReadOnlyMeeting meeting) {
        for (int index = 0; index < meeting.getPersonsMeet().size(); index++) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(meeting.getName().fullName, keyword))
                    || personListContainsKeyword(keywords, meeting.getPersonsMeet())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MeetingContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static Date date = new Date();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar totalPersons;
    @FXML
    private StatusBar saveLocationStatus;


    public StatusBarFooter(String saveLocation, int totalPersons) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        setTotalPersons(totalPersons);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> this.saveLocationStatus.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(totalPersons + " person(s) total");
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setTotalPersons(abce.data.getPersonList().size());
    }
}
```
###### \resources\view\DarkTheme.css
``` css
.background {
    background-image: url(../images/splash.jpg); /* Used in the default.html file */
    background-size: cover;
    -fx-background-color: derive(#3d4691, 50%);
}
```
