package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Removes an event from a person's schedule.
 */
public class DeleteEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edelete";
    public static final String COMMAND_ALT = "ed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes event(s) from a person's schedule. "
            + " \nParameters: "
            + "p/PERSON INDEX "
            + "e/EVENT INDEX [EVENT INDEX...]\n"
            + "Example: " + COMMAND_WORD + " p/2 "
            + "e/1 2 3";

    public static final String MESSAGE_SUCCESS = "Removed %1$s event(s) from %2$s.";
    public static final String MESSAGE_FAIL = "Unable to remove event from %1$s's schedule.";
    public static final String MESSAGE_NO_EVENTS = "%1$s's schedule list is empty.";
    public static final String MESSAGE_NO_SUCH_EVENT = "Event does not exist.";
    private final Index personIndex;
    private Index[] eventIndexes;

    /**
     * Creates an AddEventCommand to add the specified {@code ReadOnlySchedule}
     */
    public DeleteEventCommand(Index personIndex, Index[] eventIndexes) {
        requireNonNull(personIndex);
        requireNonNull(eventIndexes);
        this.personIndex = personIndex;
        this.eventIndexes = eventIndexes;
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
        int numberOfEvents = this.eventIndexes.length;

        Person editedPerson;
        try {
            editedPerson = removeEventFromPerson(toEdit);
            model.updatePerson(toEdit, editedPerson);
        } catch (ParseException e) {
            throw new CommandException(String.format(MESSAGE_FAIL, personName));
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot exist in address book");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfEvents, personName));

    }

    /**
     * Creates and returns a {@code Person} with a schedule list.
     */
    private Person removeEventFromPerson(ReadOnlyPerson personToEdit) throws ParseException {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Favourite updatedFavourite = personToEdit.getFavourite();
        ProfPic updatedProfPic = personToEdit.getProfPic();
        Set<Tag> updatedTags = personToEdit.getTags();
        Set<Group> updatedGroups = personToEdit.getGroups();
        UniqueScheduleList updatedScheduleList = personToEdit.scheduleProperty().get();
        Set<SocialMedia> updatedSocialMediaList = personToEdit.getSocialMedia();

        if (updatedScheduleList.asObservableList().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_EVENTS, updatedName.fullName));
        }

        ReadOnlySchedule[] schedulesToDelete = new ReadOnlySchedule[eventIndexes.length];
        for (int i = 0; i < eventIndexes.length; i++) {
            try {
                schedulesToDelete[i] = updatedScheduleList.asObservableList().get(eventIndexes[i].getZeroBased());
            } catch (IndexOutOfBoundsException e) {
                throw new ParseException(MESSAGE_NO_SUCH_EVENT);
            }
        }

        for (int i = 0; i < eventIndexes.length; i++) {
            try {
                updatedScheduleList.remove(schedulesToDelete[i]);
            } catch (ScheduleNotFoundException e) {
                throw new ParseException(MESSAGE_NO_SUCH_EVENT);
            }
        }

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedFavourite, updatedProfPic, updatedTags, updatedGroups,
                updatedScheduleList.toSet(), updatedSocialMediaList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && eventIndexes.equals(((DeleteEventCommand) other).eventIndexes)
                && personIndex.equals(((DeleteEventCommand) other).personIndex));

    }
}
