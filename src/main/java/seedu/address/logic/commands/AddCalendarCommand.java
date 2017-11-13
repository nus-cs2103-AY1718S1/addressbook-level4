package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALENDAR_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.googlecalendarutil.EventParserUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.model.tag.Tag;

/**
 * Pulls Google Calendar events of a person.
 */
public class AddCalendarCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cadd";
    public static final String COMMAND_ALT = "ca";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds Google Calendar events or a schedule to a person."
            + "Parameters: "
            + "p/PERSON INDEX "
            + PREFIX_CALENDAR_ID + "GOOGLE_CALENDAR_ID" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "p/2 " + PREFIX_CALENDAR_ID + "xderek105243x@gmail.com";

    public static final String MESSAGE_CALENDAR_PULL_SUCCESS = "Added %1$s Google Calendar Event(s) to %2$s.";
    public static final String MESSAGE_INVALID_CALENDAR_ID = "Google Calendar ID is invalid.";
    public static final String MESSAGE_CALENDAR_PULL_FAIL = "Unable to pull events from Google Calendar.";
    public static final String MESSAGE_DATETIME_ERROR = "Error parsing datetime.";
    public static final String MESSAGE_NO_UPDATE = "%1$s's schedule list is up-to-date.";

    private final Index personIndex;
    private final String calendarId;

    private int addedEvents;
    /**
     * Creates an CreateGroupCommand to add the specified {@code ReadOnlyGroup}
     */
    public AddCalendarCommand(Index personIndex, String calendarId) {
        this.personIndex = personIndex;
        this.calendarId = calendarId;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyPerson> lastShownPersonList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson toEdit = lastShownPersonList.get(personIndex.getZeroBased());
        String personName = toEdit.getName().toString();

        Person editedPerson;
        try {
            editedPerson = addCalendarToPerson(toEdit);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_CALENDAR_PULL_FAIL);
        }

        try {
            model.updatePerson(toEdit, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot exist in address book");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (addedEvents == 0) {
            return new CommandResult(String.format(MESSAGE_NO_UPDATE, personName));
        } else {
            return new CommandResult(String.format(MESSAGE_CALENDAR_PULL_SUCCESS, addedEvents, personName));
        }

    }

    /**
     * Creates and returns a {@code Person} with a schedule list.
     */
    private Person addCalendarToPerson(ReadOnlyPerson personToEdit) throws ParseException, CommandException {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Favourite updatedFavourite = personToEdit.getFavourite();
        Set<Tag> updatedTags = personToEdit.getTags();
        UniqueScheduleList updatedScheduleList = personToEdit.scheduleProperty().get();

        try {
            UniqueScheduleList scheduleList = EventParserUtil.getScheduleList(this.calendarId);

            for (Schedule s: scheduleList) {
                try {
                    updatedScheduleList.add(s);
                    addedEvents++;
                } catch (DuplicateScheduleException dse) {
                    continue;
                }
            }

            if (addedEvents > 0) {
                updatedScheduleList.sort();
            }

        } catch (IOException e) {
            throw new CommandException(MESSAGE_INVALID_CALENDAR_ID);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_DATETIME_ERROR);
        }

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedFavourite, updatedTags, updatedScheduleList.toSet());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCalendarCommand // instanceof handles nulls
                && personIndex.equals(((AddCalendarCommand) other).personIndex));

    }
}
