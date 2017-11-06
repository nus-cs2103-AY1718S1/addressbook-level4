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
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.model.tag.Tag;

/**
 * Adds a schedule to a person.
 */
public class AddScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cadd";
    public static final String COMMAND_ALT = "ca";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds Google Calendar events or a schedule to a person."
            + "Parameters: "
            + "p/PERSON INDEX "
            + PREFIX_CALENDAR_ID + "GOOGLE_CALENDAR_ID" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "p/2 " + PREFIX_CALENDAR_ID + "b8dkov028k71krea20qbbc8f3s";

    public static final String MESSAGE_GCALENDAR_PULL_SUCCESS = "Added Google Calendar Events to %1$s.";
    public static final String MESSAGE_INVALID_CALENDAR_ID = "Google Calendar ID is invalid.";
    public static final String MESSAGE_DATETIME_ERROR = "Error parsing datetime.";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "Schedule already exists in the schedule list.";

    private final Index personIndex;
    private final String calendarId;

    /**
     * Creates an CreateGroupCommand to add the specified {@code ReadOnlyGroup}
     */
    public AddScheduleCommand(Index personIndex, String calendarId) {
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
            throw new AssertionError("Calendar ID should be valid");
        }

        try {
            model.updatePerson(toEdit, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot exist in address book");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_GCALENDAR_PULL_SUCCESS, personName));

    }

    /**
     * Creates and returns a {@code Person} with the the Favourite attribute set to true.
     */
    private Person addCalendarToPerson(ReadOnlyPerson personToEdit) throws ParseException {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Favourite updatedFavourite = personToEdit.getFavourite();
        Set<Tag> updatedTags = personToEdit.getTags();
        UniqueScheduleList scheduleList;

        try {
            scheduleList = EventParserUtil.getScheduleList(this.calendarId);
        } catch (IOException e) {
            throw new ParseException(MESSAGE_INVALID_CALENDAR_ID);
        } catch (IllegalValueException e) {
            throw new ParseException(MESSAGE_DATETIME_ERROR);
        }

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedFavourite, updatedTags, scheduleList.toSet());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && personIndex.equals(((AddScheduleCommand) other).personIndex));

    }
}
