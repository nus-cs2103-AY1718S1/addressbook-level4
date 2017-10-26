package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.customField.UniqueCustomFieldList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicatePhoneException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.PhoneNotFoundException;
import seedu.address.model.person.phone.Phone;
import seedu.address.model.person.phone.UniquePhoneList;
import seedu.address.model.tag.Tag;

/**
 * Adds or updates a custom field of a person identified using it's last displayed index from the address book.
 */
public class PhoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "updatePhone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates one person's additional phone identified by the index"
            + " number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "ACTION \n"
            + "VALUE"
            + "Example: " + COMMAND_WORD + " 1" + " add" + " 6583609887";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final Phone phone;

    private final String action;


    public PhoneCommand(Index targetIndex, String action,  Phone phone) {
        this.targetIndex = targetIndex;
        this.action = action;
        this.phone = phone;
    }

    /**
     * Adds or Updates a Person's phoneNumber list
     */
    private Person updatePersonPhoneList(ReadOnlyPerson personToUpdatePhoneList, String action, Phone phone) {
        Name name = personToUpdatePhoneList.getName();
        Phone primaryPhone = personToUpdatePhoneList.getPhone();
        UniquePhoneList uniquePhoneList = personToUpdatePhoneList.getPhoneList();
        Email email = personToUpdatePhoneList.getEmail();
        Address address = personToUpdatePhoneList.getAddress();
        Set<Tag> tags = personToUpdatePhoneList.getTags();
        UniqueCustomFieldList customFields = personToUpdatePhoneList.getCustomFieldList();

        if (action.equals("remove")) {
            try {
                uniquePhoneList.remove(phone);
            } catch (PhoneNotFoundException e) {
                throw new AssertionError("phone number cannot be found");
            }
        } else if (action.equals("add")) {
            try {
                uniquePhoneList.add(phone);
            } catch (DuplicatePhoneException e) {
                throw new AssertionError("number adding already in the list");
            }
        } else if (action.equals("showAllPhones")) {

        }

        Person personUpdated = new Person(name, primaryPhone, uniquePhoneList, email,
                address, tags, customFields.toSet());

        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdatePhoneList = lastShownList.get(targetIndex.getZeroBased());
        Person personUpdated = updatePersonPhoneList(personToUpdatePhoneList, action, phone);
        UniquePhoneList uniquePhoneList = personUpdated.getPhoneList();
        Phone primaryPhone = personUpdated.getPhone();
        try {
            model.updatePerson(personToUpdatePhoneList, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (action.equals("showAllPhones")) {
            String str = "The primary number is " + primaryPhone + "\n";
            return new CommandResult(str + uniquePhoneList.getAllPhone());
        } else if (action.equals("add")) {
            String successMessage = "Phone number " + phone.number + " has been added, ";
            String info = "the updated phone list now has " + (uniquePhoneList.getSize() + 1)
                    + " phone numbers, and the primary phone number is " + primaryPhone;
            return new CommandResult(successMessage + info);
        } else {
            String successMessage = "Phone number " + phone.number + " has been removed, ";
            String info = "the updated phone list now has " + (uniquePhoneList.getSize() + 1)
                    + " phone numbers, and the primary phone number is " + primaryPhone;
            return new CommandResult(successMessage + info);
        }
    }
}
