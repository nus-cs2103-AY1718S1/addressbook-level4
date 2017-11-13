package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.customField.UniqueCustomFieldList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicatePhoneException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.PhoneNotFoundException;
import seedu.address.model.person.phone.Phone;
import seedu.address.model.person.phone.UniquePhoneList;
import seedu.address.model.tag.Tag;

//@@author eeching
/**
 * Adds or updates a custom field of a person identified using it's last displayed index from the address book.
 */
public class PhoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "updatePhone";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates one person's additional phone identified by the index"
            + " number used in the last person listing or the name of the person.\n"
            + "if index is used to identify the person\n"
            + "Parameters: \n"
            + "INDEX (must be a positive integer) "
            + "ACTION (either add or remove) "
            + "PHONE (must be at least 3 positive digits.)\n"
            + "Example: " + COMMAND_WORD + " 1" + " add" + " 6583609887\n"
            + "if name is used to identify the person\n"
            + "Parameters: \n"
            + "byName "
            + "ACTION "
            + "PHONE "
            + "NAME (must be the full name saved in the Contact Book)\n"
            + "Example: " + COMMAND_WORD + " byName" + " add" + " 6583609887 " + "Alex Yeoh";

    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_REMOVE = "remove";
    private static final String PERSON_NOT_FOUND_EXCEPTION_MESSAGE = "The target person cannot be missing.\n";
    private static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.\n";
    private static final String PHONE_NOT_FOUND_EXCEPTION_MESSAGE = "Phone number to be removed is not found in"
            + " the list.\n";
    private static final String DUPLICATE_PHONE_EXCEPTION_MESSAGE = "Phone number to be added already exists in"
            + " the list.\n";
    private static final String INVALID_COMMAND_MESSAGE = "Command is invalid, please check again.\n";
    private static final String PRIMARY_PHONE_MESSAGE = "The primary phone number is %s.\n";
    private static final String ADD_PHONE_SUCCESS_MESSAGE = "Phone number %s has been added.\n";
    private static final String REMOVE_PHONE_SUCCESS_MESSAGE = "Phone number %s has been removed.\n";
    private static final String TOTAL_NUMBER_OF_PHONES = "The updated phone list now has %s phone numbers.\n";
    private static final String UNSPECIFIED_NAME = "unspecified";

    private final Logger logger = LogsCenter.getLogger(PhoneCommand.class);

    private final Index targetIndex;

    private final Phone phone;

    private final String action;

    private final String name;


    public PhoneCommand(Index targetIndex, String action,  Phone phone) {
        this.targetIndex = targetIndex;
        this.action = action;
        this.phone = phone;
        this.name = UNSPECIFIED_NAME;
    }

    public PhoneCommand(String name, String action, Phone phone) {
        this.name = name;
        this.action = action;
        this.phone = phone;
        this.targetIndex = new Index(999999999); //indicating invalid index
    }

    /**
     * Adds or Updates a Person's phoneNumber list
     */
    private Person updatePersonPhoneList(ReadOnlyPerson personToUpdatePhoneList, String action, Phone phone)
            throws PhoneNotFoundException, DuplicatePhoneException {
        Name name = personToUpdatePhoneList.getName();
        Phone primaryPhone = personToUpdatePhoneList.getPhone();
        UniquePhoneList uniquePhoneList = personToUpdatePhoneList.getPhoneList();
        Email email = personToUpdatePhoneList.getEmail();
        Address address = personToUpdatePhoneList.getAddress();
        Photo photo = personToUpdatePhoneList.getPhoto();
        Set<Tag> tags = personToUpdatePhoneList.getTags();
        UniqueCustomFieldList customFields = personToUpdatePhoneList.getCustomFieldList();

        if (action.equals(COMMAND_REMOVE)) {
            uniquePhoneList.remove(phone);
        } else if (action.equals(COMMAND_ADD)) {
            uniquePhoneList.add(phone);
        }

        Person personUpdated = new Person(name, primaryPhone, email, address,
                photo, uniquePhoneList, tags, customFields.toSet());

        return personUpdated;
    }

    public Index getIndex() {
        return targetIndex;
    }

    public String getAction() {
        return action;
    }

    public Phone getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneCommand // instanceof handles nulls
                && (this.targetIndex.equals(((PhoneCommand) other).getIndex())
                || this.name.equals(((PhoneCommand) other).getName()))
                && this.action.equals(((PhoneCommand) other).getAction())
                && this.phone.equals(((PhoneCommand) other).getPhone()));
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson personToUpdatePhoneList = null;

        logger.info("Get the person of the specified index.");
        if (name.equals(UNSPECIFIED_NAME)) {

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                logger.warning(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            personToUpdatePhoneList = lastShownList.get(targetIndex.getZeroBased());
        } else {

            for (int i = 0; i < lastShownList.size(); i++) {
                if (lastShownList.get(i).getName().toString().equals(name)) {
                    personToUpdatePhoneList = lastShownList.get(i);
                    break;
                }
            }
            if (personToUpdatePhoneList == null) {
                throw new CommandException(Messages.MESSAGE_UNFOUND_PERSON_NAME);
            }

        }

        try {
            Person personUpdated = updatePersonPhoneList(personToUpdatePhoneList, action, phone);
            UniquePhoneList uniquePhoneList = personUpdated.getPhoneList();
            Phone primaryPhone = personUpdated.getPhone();
            try {
                model.updatePerson(personToUpdatePhoneList, personUpdated);
            } catch (DuplicatePersonException dpe) {
                logger.warning("Invalid person " + MESSAGE_DUPLICATE_PERSON);
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                logger.warning("Invalid person " + PERSON_NOT_FOUND_EXCEPTION_MESSAGE);
                throw new CommandException(PERSON_NOT_FOUND_EXCEPTION_MESSAGE);
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

            logger.info("Execute update phone command");
            CommandResult commandResult;
            switch (action) {

            case COMMAND_ADD:
                String successAdditionMessage = String.format(ADD_PHONE_SUCCESS_MESSAGE, phone.number);
                String infoAddition = String.format(TOTAL_NUMBER_OF_PHONES, uniquePhoneList.getSize() + 1)
                        + String.format(PRIMARY_PHONE_MESSAGE, primaryPhone);
                commandResult = new CommandResult(successAdditionMessage + infoAddition);
                break;
            case COMMAND_REMOVE:
                String successRemovalMessage = String.format(REMOVE_PHONE_SUCCESS_MESSAGE, phone.number);
                String infoRemoval = String.format(TOTAL_NUMBER_OF_PHONES, uniquePhoneList.getSize() + 1)
                        + String.format(PRIMARY_PHONE_MESSAGE, primaryPhone);
                commandResult = new CommandResult(successRemovalMessage + infoRemoval);
                break;
            default :
                commandResult = new CommandResult(INVALID_COMMAND_MESSAGE);
            }
            logger.info("Result: " + commandResult.feedbackToUser);
            return commandResult;
        } catch (PhoneNotFoundException e) {
            logger.warning(PHONE_NOT_FOUND_EXCEPTION_MESSAGE);
            return new CommandResult(PHONE_NOT_FOUND_EXCEPTION_MESSAGE);
        } catch (DuplicatePhoneException e) {
            logger.warning(DUPLICATE_PHONE_EXCEPTION_MESSAGE);
            return new CommandResult(DUPLICATE_PHONE_EXCEPTION_MESSAGE);
        }
    }
}
