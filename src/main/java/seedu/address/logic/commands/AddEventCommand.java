package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfPic;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.ReadOnlySchedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.model.schedule.exceptions.DuplicateScheduleException;
import seedu.address.model.tag.Tag;

//@@author cjianhui
/**
 * Adds a person to the address book.
 */
public class AddEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "eadd";
    public static final String COMMAND_ALT = "ea";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to a person's schedule. "
            + " \nParameters: "
            + "p/PERSON INDEX "
            + PREFIX_NAME + "EVENT NAME "
            + PREFIX_START_DATE + "EVENT START DATE "
            + PREFIX_END_DATE + "EVENT END DATE "
            + "[" + PREFIX_DETAILS + "EVENT DETAILS]\n"
            + "Date Format: YYYY-MM-DD HH:MM\n"
            + "Example: " + COMMAND_WORD + " p/2 "
            + PREFIX_NAME + "CS2103 Meeting "
            + PREFIX_START_DATE + "2017-11-23 10:30 "
            + PREFIX_END_DATE + "2017-11-23 11:45 "
            + PREFIX_DETAILS + "Prepare for Demo";

    public static final String MESSAGE_SUCCESS = "Added %1$s to %2$s's schedule.";
    public static final String MESSAGE_FAIL = "Unable to add event to %1$s's schedule.";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This event already exists in %1$s's schedule.";
    public static final String MESSAGE_INVALID_DURATION = "Please ensure that event "
            + "start time is before event end time.";
    public static final String MESSAGE_INVALID_START_TIME = "Please enter a start time after %1$s.";
    public static final String MESSAGE_INVALID_TIME = "Please enter a valid time.";

    private final Index personIndex;
    private final ReadOnlySchedule schedule;

    /**
     * Creates an AddEventCommand to add the specified {@code ReadOnlySchedule}
     */
    public AddEventCommand(Index personIndex, ReadOnlySchedule toAdd) {
        requireNonNull(personIndex);
        requireNonNull(toAdd);
        this.personIndex = personIndex;
        this.schedule = toAdd;
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
        String scheduleName = this.schedule.getName().fullName;
        Person editedPerson;
        try {
            editedPerson = addEventToPerson(toEdit, personName);
            model.updatePerson(toEdit, editedPerson);
        } catch (ParseException e) {
            throw new CommandException(String.format(MESSAGE_FAIL, personName));
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot exist in address book");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, scheduleName, personName));

    }

    /**
     * Creates and returns a {@code Person} with a schedule list.
     */
    private Person addEventToPerson(ReadOnlyPerson personToEdit, String personName) throws ParseException {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        ProfPic updatedProfPic = personToEdit.getProfPic();
        Favourite updatedFavourite = personToEdit.getFavourite();
        Set<Tag> updatedTags = personToEdit.getTags();
        Set<Group> updatedGroups = personToEdit.getGroups();
        UniqueScheduleList updatedScheduleList = personToEdit.scheduleProperty().get();

        try {
            updatedScheduleList.add(this.schedule);
        } catch (DuplicateScheduleException e) {
            throw new ParseException(String.format(MESSAGE_DUPLICATE_SCHEDULE, personName));
        }

        /** Ensure scheduleList is in order **/
        updatedScheduleList.sort();
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedFavourite,
                updatedProfPic, updatedTags, updatedGroups, updatedScheduleList.toSet());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && schedule.equals(((AddEventCommand) other).schedule)
                && personIndex.equals(((AddEventCommand) other).personIndex));

    }
}
