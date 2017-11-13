# jeffreygohkw
###### \java\seedu\address\commons\events\ui\BrowserPanelLocateEvent.java
``` java
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a selection change in the Person List Panel
 */
public class BrowserPanelLocateEvent extends BaseEvent {

    private final ReadOnlyPerson person;

    public BrowserPanelLocateEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyPerson getNewSelection() {
        return person;
    }
}
```
###### \java\seedu\address\commons\events\ui\BrowserPanelNavigateEvent.java
``` java

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Location;

/**
 * Represents a selection change in the Person List Panel
 */
public class BrowserPanelNavigateEvent extends BaseEvent {

    private final Location fromLocation;
    private final Location toLocation;

    public BrowserPanelNavigateEvent(Location fromLocation, Location toLocation) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }
}
```
###### \java\seedu\address\commons\events\ui\ChangeThemeRequestEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    private final String styleSheet;

    public ChangeThemeRequestEvent(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getStyleSheet() {
        return styleSheet;
    }
}
```
###### \java\seedu\address\commons\events\ui\OpenRequestEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An Event for the opening of a save file from a selected location.
 */
public class OpenRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\SaveAsRequestEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An Event for the saving of data to a selected location.
 */
public class SaveAsRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\ChangePrivacyCommand.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Changes the privacy setting of a person's details in the address book
 */
public class ChangePrivacyCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "changeprivacy";
    public static final String COMMAND_ALIAS = "cp";

    public static final String TRUE_WORD = "true";
    public static final String FALSE_WORD = "false";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the privacy of the details of the person"
            + " identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + TRUE_WORD + " OR " + FALSE_WORD + "]"
            + "[" + PREFIX_PHONE + TRUE_WORD + " OR " + FALSE_WORD + "]"
            + "[" + PREFIX_EMAIL + TRUE_WORD + " OR " + FALSE_WORD + "]"
            + "[" + PREFIX_ADDRESS + TRUE_WORD + " OR " + FALSE_WORD + "]\n"
            + "[" + PREFIX_AVATAR + TRUE_WORD + " OR " + FALSE_WORD + "]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + TRUE_WORD + " "
            + PREFIX_PHONE + FALSE_WORD + " "
            + PREFIX_EMAIL + TRUE_WORD + " "
            + PREFIX_ADDRESS + FALSE_WORD + " "
            + PREFIX_AVATAR + FALSE_WORD;

    public static final String MESSAGE_CHANGE_PRIVACY_SUCCESS = "Changed the Privacy of the Person: %1$s";
    public static final String MESSAGE_NO_FIELDS = "At least one field to change must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final PersonPrivacySettings pps;

    /**
     * @param index of the person in the filtered person list to change the privacy of
     */
    public ChangePrivacyCommand(Index index, PersonPrivacySettings pps) {
        requireNonNull(index);
        requireNonNull(pps);

        this.index = index;
        this.pps = pps;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToChange = lastShownList.get(index.getZeroBased());

        Person newPerson = null;
        try {
            newPerson = createPersonWithChangedPrivacy(personToChange, pps);
            newPerson.setPrivacyLevel(model.getPrivacyLevel());
        } catch (IllegalValueException e) {
            throw new AssertionError("Person must have all fields initialised.");
        }

        try {
            model.updatePerson(personToChange, newPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_CHANGE_PRIVACY_SUCCESS, newPerson));
    }

    /**
     * Changes a person's fields' privacy
     * @param person the person whose privacy we would like to change
     * @param pps the settings of privacy for each field
     */
    private static Person createPersonWithChangedPrivacy(ReadOnlyPerson person, PersonPrivacySettings pps)
            throws IllegalValueException {
        assert person != null;

        Name name = createNameWithPrivacy(person, pps);
        Phone phone = createPhoneWithPrivacy(person, pps);
        Email email = createEmailWithPrivacy(person, pps);
        Address address = createAddressWithPrivacy(person, pps);
        Remark remark = createRemarkWithPrivacy(person, pps);
        Avatar avatar = person.getAvatar();
        Boolean favourite = person.getFavourite();
        Set<Tag> tag = person.getTags();

        return new Person(name, phone, email, address, favourite, remark, avatar, tag);
    }

    /**
     * Creates a new (@code Name) based on the input (@code Person) and (@code PersonPrivacySettings)
     * @return A (@code Name) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code PersonPrivacySettings)
     */
    private static Name createNameWithPrivacy(ReadOnlyPerson person, PersonPrivacySettings pps) {
        Name name;
        try {
            if (person.getName().getIsPrivate()) {
                person.getName().setPrivate(false);
                name = new Name(person.getName().toString());
                person.getName().setPrivate(true);
            } else {
                name = new Name(person.getName().toString());
            }
        } catch (IllegalValueException e) {
            throw new AssertionError("Invalid Name");
        }
        if (pps.getNameIsPrivate() != null) {
            name.setPrivate(pps.getNameIsPrivate());
        } else {
            name.setPrivate(person.getName().getIsPrivate());
        }
        return name;
    }


    /**
     * Creates a new (@code Phone) based on the input (@code Person) and (@code PersonPrivacySettings)
     * @return A (@code Phone) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code PersonPrivacySettings)
     */
    private static Phone createPhoneWithPrivacy(ReadOnlyPerson person, PersonPrivacySettings pps) {
        Phone phone;
        try {
            if (person.getPhone().getIsPrivate()) {
                person.getPhone().setPrivate(false);
                phone = new Phone(person.getPhone().toString());
                person.getPhone().setPrivate(true);
            } else {
                phone = new Phone(person.getPhone().toString());
            }
        } catch (IllegalValueException e) {
            throw new AssertionError("Invalid Phone");
        }
        if (pps.getPhoneIsPrivate() != null) {
            phone.setPrivate(pps.getPhoneIsPrivate());
        } else {
            phone.setPrivate(person.getPhone().getIsPrivate());
        }
        return phone;
    }


    /**
     * Creates a new (@code Email) based on the input (@code Person) and (@code PersonPrivacySettings)
     * @return A (@code Email) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code PersonPrivacySettings)
     */
    private static Email createEmailWithPrivacy(ReadOnlyPerson person, PersonPrivacySettings pps) {
        Email email;
        try {
            if (person.getEmail().getIsPrivate()) {
                person.getEmail().setPrivate(false);
                email = new Email(person.getEmail().toString());
                person.getEmail().setPrivate(true);
            } else {
                email = new Email(person.getEmail().toString());
            }
        } catch (IllegalValueException e) {
            throw new AssertionError("Invalid Email");
        }
        if (pps.getEmailIsPrivate() != null) {
            email.setPrivate(pps.getEmailIsPrivate());
        } else {
            email.setPrivate(person.getEmail().getIsPrivate());
        }
        return email;
    }

    /**
     * Creates a new (@code Address) based on the input (@code Person) and (@code PersonPrivacySettings)
     * @return A (@code Address) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code PersonPrivacySettings)
     */
    private static Address createAddressWithPrivacy(ReadOnlyPerson person, PersonPrivacySettings pps) {
        Address address;
        try {
            if (person.getAddress().getIsPrivate()) {
                person.getAddress().setPrivate(false);
                address = new Address(person.getAddress().toString());
                person.getAddress().setPrivate(true);
            } else {
                address = new Address(person.getAddress().toString());
            }
        } catch (IllegalValueException e) {
            throw new AssertionError("Invalid Address");
        }
        if (pps.getAddressIsPrivate() != null) {
            address.setPrivate(pps.getAddressIsPrivate());
        } else {
            address.setPrivate(person.getAddress().getIsPrivate());
        }
        return address;
    }

    /**
     * Creates a new (@code Remark) based on the input (@code Person) and (@code PersonPrivacySettings)
     * @return A (@code Remark) with the same value as that of the (@code Person)'s but with the privacy set to that
     * of the (@code PersonPrivacySettings)
     */
    private static Remark createRemarkWithPrivacy(ReadOnlyPerson person, PersonPrivacySettings pps) {
        Remark remark;
        try {
            if (person.getRemark().getIsPrivate()) {
                person.getRemark().setPrivate(false);
                remark = new Remark(person.getRemark().toString());
                person.getRemark().setPrivate(true);
            } else {
                remark = new Remark(person.getRemark().toString());
            }
        } catch (IllegalValueException e) {
            throw new AssertionError("Invalid Remark");
        }
        if (pps.getRemarkIsPrivate() != null) {
            remark.setPrivate(pps.getRemarkIsPrivate());
        } else {
            remark.setPrivate(person.getRemark().getIsPrivate());
        }
        return remark;
    }

    public Index getIndex() {
        return index;
    }

    public PersonPrivacySettings getPps() {
        return pps;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangePrivacyCommand)) {
            return false;
        }

        // state check
        ChangePrivacyCommand c = (ChangePrivacyCommand) other;
        return index.equals(c.index)
                && pps.equals(c.pps);
    }

    /**
     * Stores the privacy settings for each field of a person.
     */
    public static class PersonPrivacySettings {
        private Boolean nameIsPrivate;
        private Boolean phoneIsPrivate;
        private Boolean emailIsPrivate;
        private Boolean addressIsPrivate;
        private Boolean remarkIsPrivate;

        public PersonPrivacySettings() {}

        public PersonPrivacySettings(PersonPrivacySettings toCopy) {
            this.nameIsPrivate = toCopy.nameIsPrivate;
            this.phoneIsPrivate = toCopy.phoneIsPrivate;
            this.emailIsPrivate = toCopy.emailIsPrivate;
            this.addressIsPrivate = toCopy.addressIsPrivate;
            this.remarkIsPrivate = toCopy.remarkIsPrivate;
        }

        /**
         * Returns true if at least one field is not null.
         */
        public boolean isAnyFieldNonNull() {
            return CollectionUtil.isAnyNonNull(this.nameIsPrivate, this.phoneIsPrivate,
                    this.emailIsPrivate, this.addressIsPrivate, this.remarkIsPrivate);
        }

        /**
         * Returns the value of nameIsPrivate
         */
        public Boolean getNameIsPrivate() {
            return nameIsPrivate;
        }

        public void setNameIsPrivate(boolean nameIsPrivate) {
            requireNonNull(nameIsPrivate);
            this.nameIsPrivate = nameIsPrivate;
        }

        /**
         * Returns the value of phoneIsPrivate
         */
        public Boolean getPhoneIsPrivate() {
            return phoneIsPrivate;
        }

        public void setPhoneIsPrivate(boolean phoneIsPrivate) {
            requireNonNull(phoneIsPrivate);
            this.phoneIsPrivate = phoneIsPrivate;
        }

        /**
         * Returns the value of emailIsPrivate
         */
        public Boolean getEmailIsPrivate() {
            return emailIsPrivate;
        }

        public void setEmailIsPrivate(boolean emailIsPrivate) {
            requireNonNull(emailIsPrivate);
            this.emailIsPrivate = emailIsPrivate;
        }

        /**
         * Returns the value of addressIsPrivate
         */
        public Boolean getAddressIsPrivate() {
            return addressIsPrivate;
        }

        public void setAddressIsPrivate(boolean addressIsPrivate) {
            requireNonNull(addressIsPrivate);
            this.addressIsPrivate = addressIsPrivate;
        }
```
###### \java\seedu\address\logic\commands\EditPersonCommand.java
``` java
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     * A person with private fields cannot be edited
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor)
            throws IllegalArgumentException {
        assert personToEdit != null;

        Name updatedName;
        Phone updatedPhone;
        Email updatedEmail;
        Address updatedAddress;
        Remark updatedRemark;
        Set<Tag> updatedTags;
        Boolean updateFavourite;
        Avatar updatedAvatar;

        areFieldsAllPrivate = true;
        updatedName = createUpdatedName(personToEdit, editPersonDescriptor);

        updatedPhone = createUpdatedPhone(personToEdit, editPersonDescriptor);

        updatedEmail = createUpdatedEmail(personToEdit, editPersonDescriptor);

        updatedAddress = createUpdatedAddress(personToEdit, editPersonDescriptor);

        updatedRemark = createUpdatedRemark(personToEdit, editPersonDescriptor);

        updatedTags = createUpdatedTags(personToEdit, editPersonDescriptor);

        updateFavourite = createUpdatedFavourite(personToEdit, editPersonDescriptor);

        updatedAvatar = createUpdatedAvatar(personToEdit, editPersonDescriptor);

        if (areFieldsAllPrivate) {
            throw new IllegalArgumentException();
        }
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updateFavourite, updatedRemark, updatedAvatar, updatedTags);
    }

    /**
     * Creates an updated (@code Name) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Name) from either the personToEdit or the editPersonDescriptor depending on privacy
     */
    private static Name createUpdatedName(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Name updatedName;
        if (!personToEdit.getName().getIsPrivate()) {
            updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
            if (editPersonDescriptor.getName().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedName = personToEdit.getName();
        }
        return updatedName;
    }

    /**
     * Creates an updated (@code Phone) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Phone) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Phone createUpdatedPhone(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Phone updatedPhone;
        if (!personToEdit.getPhone().getIsPrivate()) {
            updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
            if (editPersonDescriptor.getPhone().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedPhone = personToEdit.getPhone();
        }
        return updatedPhone;
    }

    /**
     * Creates an updated (@code Email) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Email) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Email createUpdatedEmail(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Email updatedEmail;
        if (!personToEdit.getEmail().getIsPrivate()) {
            updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
            if (editPersonDescriptor.getEmail().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedEmail = personToEdit.getEmail();
        }
        return updatedEmail;
    }

    /**
     * Creates an updated (@code Address) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Address) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Address createUpdatedAddress(ReadOnlyPerson personToEdit,
                                                EditPersonDescriptor editPersonDescriptor) {
        Address updatedAddress;
        if (!personToEdit.getAddress().getIsPrivate()) {
            updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
            if (editPersonDescriptor.getAddress().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedAddress = personToEdit.getAddress();
        }
        return updatedAddress;
    }
    //**author charlesgoh
    /**
     * Creates an updated (@code Remark) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Remark) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Remark createUpdatedRemark(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Remark updatedRemark;
        if (!personToEdit.getRemark().getIsPrivate()) {
            updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());
            if (editPersonDescriptor.getRemark().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedRemark = personToEdit.getRemark();
        }
        return updatedRemark;
    }

    /**
     * Creates an updated (@code Avatar) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Avatar) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Avatar createUpdatedAvatar(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Avatar updatedAvatar = editPersonDescriptor.getAvatar().orElse(personToEdit.getAvatar());
        if (editPersonDescriptor.getAvatar().isPresent()) {
            areFieldsAllPrivate = false;
        }
        return updatedAvatar;
    }
    //author
    /**
     * Creates an updated (@code Tag) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Tag) from either the personToEdit or the editPersonDescriptor depending on the input
     */
    private static Set<Tag> createUpdatedTags(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        if (editPersonDescriptor.getTags().isPresent()) {
            areFieldsAllPrivate = false;
        }
        return updatedTags;
    }

    /**
     * Creates an updated (@code Favourite) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Favourite) from either the personToEdit or the editPersonDescriptor depending on the input
     */
    private static Boolean createUpdatedFavourite(ReadOnlyPerson personToEdit,
                                                  EditPersonDescriptor editPersonDescriptor) {
        Boolean updateFavourite = editPersonDescriptor.getFavourite().orElse(personToEdit.getFavourite());
        if (editPersonDescriptor.getFavourite().isPresent()) {
            areFieldsAllPrivate = false;
        }
        return updateFavourite;
    }

```
###### \java\seedu\address\logic\commands\LocateCommand.java
``` java
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Locates a person's address on Google Maps identified using it's last displayed index from the address book.
 */
public class LocateCommand extends Command {

    public static final String COMMAND_WORD = "locate";
    public static final String COMMAND_ALIAS = "loc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Locates the address of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Searching for Person at Index: %1$s";
    public static final String MESSAGE_PRIVATE_ADDRESS_FAILURE = "Person %1$s has a Private Address";

    private final Index targetIndex;

    public LocateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (model.getFilteredPersonList().get(targetIndex.getZeroBased()).getAddress().getIsPrivate()) {
            throw new CommandException(String.format(MESSAGE_PRIVATE_ADDRESS_FAILURE, targetIndex.getOneBased()));
        }
        EventsCenter.getInstance().post(new BrowserPanelLocateEvent(
                model.getFilteredPersonList().get(targetIndex.getZeroBased())));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocateCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocateCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\NavigateCommand.java
``` java
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Location;


/**
 * Navigates from one address to another with the help of Google Maps
 */
public class NavigateCommand extends Command {

    public static final String COMMAND_WORD = "navigate";
    public static final String COMMAND_ALIAS = "nav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Get directions from one address to another.\n"
            + "Parameters: [fp/INDEX] OR [ft/INDEX] (must be a positive integer) OR [fa/ADDRESS] (Only one of three)"
            + " AND [tp/INDEX] OR [tt/INDEX] (must be a positive integer) OR [ta/ADDRESS] (Only one of three)\n"
            + "Example: " + COMMAND_WORD + " fp/2 ta/University Town";

    public static final String MESSAGE_NAVIGATE_SUCCESS = "Navigating from %1$s to %2$s";
    public static final String MESSAGE_MULTIPLE_FROM_ERROR = "Only one type of From prefix allowed.";
    public static final String MESSAGE_MULTIPLE_TO_ERROR = "Only one type of To prefix allowed.";
    public static final String MESSAGE_PRIVATE_PERSON_ADDRESS_ERROR = "Address of the Person at index %1$s is private.";
    public static final String MESSAGE_PERSON_HAS_NO_ADDRESS_ERROR = "Person at index %1$s does not have an address.";
    public static final String MESSAGE_TASK_HAS_NO_ADDRESS_ERROR = "Task at index %1$s does not have an address.";

    private final Location locationFrom;
    private final Location locationTo;
    private final Index fromIndex;
    private final Index toIndex;
    private final boolean fromIsTask;
    private final boolean toIsTask;
    public NavigateCommand(Location locationFrom, Location locationTo, Index fromIndex, Index toIndex,
                           boolean fromIsTask, boolean toIsTask) throws IllegalValueException {
        Location from = null;
        Location to = null;
        checkDuplicateFromAndToLocation(locationFrom, locationTo, fromIndex, toIndex);

        if (locationFrom != null) {
            from = locationFrom;
        }
        if (locationTo != null) {
            to = locationTo;
        }

        this.locationFrom = from;
        this.locationTo = to;
        this.toIndex = toIndex;
        this.fromIndex = fromIndex;
        this.toIsTask = toIsTask;
        this.fromIsTask = fromIsTask;
    }

    /**
     * Throws an IllegalArgumentException if there is both locationFrom and fromIndex are not null,
     * or if both locationTo and toIndex are not null.
     */
    private void checkDuplicateFromAndToLocation(Location locationFrom, Location locationTo,
                                                 Index fromIndex, Index toIndex) throws IllegalArgumentException {
        if (locationFrom != null && fromIndex != null) {
            throw new IllegalArgumentException(MESSAGE_MULTIPLE_FROM_ERROR);
        }
        if (locationTo != null && toIndex != null) {
            throw new IllegalArgumentException(MESSAGE_MULTIPLE_TO_ERROR);
        }
    }

    private Location setLocationByIndex(Index index, boolean isTask) throws IllegalValueException, CommandException {
        if (isTask) {
            if (model.getFilteredTaskList().get(index.getZeroBased()).getTaskAddress().toString().equals("")) {
                throw new CommandException(String.format(MESSAGE_TASK_HAS_NO_ADDRESS_ERROR, index.getOneBased()));
            } else {
                return new Location(model.getFilteredTaskList().get(index.getZeroBased()).getTaskAddress().toString());
            }
        } else {
            if (model.getFilteredPersonList().get(index.getZeroBased()).getAddress().toString().equals("")) {
                throw new CommandException(String.format(MESSAGE_PERSON_HAS_NO_ADDRESS_ERROR, index.getOneBased()));
            } else if (model.getFilteredPersonList().get(index.getZeroBased()).getAddress().getIsPrivate()) {
                throw new CommandException(String.format(MESSAGE_PRIVATE_PERSON_ADDRESS_ERROR, index.getOneBased()));
            } else {
                return new Location(model.getFilteredPersonList().get(index.getZeroBased())
                        .getAddress().toString());
            }
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        Location from;
        Location to;
        if (fromIndex != null) {
            try {
                from = setLocationByIndex(fromIndex, fromIsTask);
            } catch (IllegalValueException e) {
                throw new IllegalArgumentException(MESSAGE_PRIVATE_PERSON_ADDRESS_ERROR);
            }
        } else {
            from = locationFrom;
        }
        if (toIndex != null) {
            try {
                to = setLocationByIndex(toIndex, toIsTask);
            } catch (IllegalValueException e) {
                throw new IllegalArgumentException(MESSAGE_PRIVATE_PERSON_ADDRESS_ERROR);
            }
        } else {
            to = locationTo;
        }
        EventsCenter.getInstance().post(new BrowserPanelNavigateEvent(from, to));
        return new CommandResult(String.format(MESSAGE_NAVIGATE_SUCCESS, from, to));
    }

    public Location getLocationFrom() {
        return locationFrom;
    }

    public Location getLocationTo() {
        return locationTo;
    }

    public Index getFromIndex() {
        return fromIndex;
    }

    public Index getToIndex() {
        return toIndex;
    }

    public boolean isFromIsTask() {
        return fromIsTask;
    }

    public boolean isToIsTask() {
        return toIsTask;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NavigateCommand // instanceof handles nulls
                && equalsLocationFrom(other)
                && equalsLocationTo(other)
                && equalsFromIndex(other)
                && equalsToIndex(other)
                && equalsFromIsTask(other)
                && equalsToIsTask(other)); // state check
    }

    /**
     * Checks if the (@codde locationFrom) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsLocationFrom(Object other) {
        if (this.locationFrom == null) {
            return ((NavigateCommand) other).locationFrom == null;
        } else {
            return this.locationFrom.equals(((NavigateCommand) other).locationFrom);
        }
    }

    /**
     * Checks if the (@codde locationTo) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsLocationTo(Object other) {
        if (this.locationTo == null) {
            return ((NavigateCommand) other).locationTo == null;
        } else {
            return this.locationTo.equals(((NavigateCommand) other).locationTo);
        }
    }

    /**
     * Checks if the (@codde fromIndex) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsFromIndex(Object other) {
        if (this.fromIndex == null) {
            return ((NavigateCommand) other).fromIndex == null;
        } else {
            return this.fromIndex.equals(((NavigateCommand) other).fromIndex);
        }
    }

    /**
     * Checks if the (@codde toIndex) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsToIndex(Object other) {
        if (this.toIndex == null) {
            return ((NavigateCommand) other).toIndex == null;
        } else {
            return this.toIndex.equals(((NavigateCommand) other).toIndex);
        }
    }

    /**
     * Checks if the (@codde fromIsTask) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsFromIsTask(Object other) {
        return this.fromIsTask == (((NavigateCommand) other).fromIsTask);
    }

    /**
     * Checks if the (@codde toIsTask) of this object is equal to that of the other Object
     * @param other The other Object we are comparing against
     * @return True if both are null or both have the same value
     */
    private boolean equalsToIsTask(Object other) {
        return this.toIsTask == (((NavigateCommand) other).toIsTask);
    }
}
```
###### \java\seedu\address\logic\commands\OpenCommand.java
``` java

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.OpenRequestEvent;

/**
 * Opens the data from a desired location
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";
    public static final String COMMAND_ALIAS = "o";

    public static final String OPEN_COMMAND_SUCCESS = "Successfully opened file.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new OpenRequestEvent());
        return new CommandResult(OPEN_COMMAND_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\PrivacyLevelCommand.java
``` java
import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.ShowAllPrivacyLevelPredicate;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Handles the changing of Privacy Levels in the address book
 */
public class PrivacyLevelCommand extends Command {

    public static final String COMMAND_WORD = "privacylevel";
    public static final String COMMAND_ALIAS = "pl";

    public static final String CHANGE_PRIVACY_LEVEL_SUCCESS = "Successfully change privacy level to %1$s.";
    public static final String WRONG_PRIVACY_LEVEL_MESSAGE = "Privacy Level can only be 1, 2 or 3";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final int MIN_PRIVACY_LEVEL = 1;
    public static final int MAX_PRIVACY_LEVEL = 3;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the privacy level of the address book. Level 1 shows all data, level 2 hides private fields"
            + " and level 3 hides persons with at least 1 private field.\n"
            + "Parameters: LEVEL (must be 1, 2 or 3)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final int level;

    public PrivacyLevelCommand(int level) {
        this.level = level;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        if (level < MIN_PRIVACY_LEVEL || level > MAX_PRIVACY_LEVEL) {
            throw new CommandException(WRONG_PRIVACY_LEVEL_MESSAGE);
        }
        model.setPrivacyLevel(level);
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            ReadOnlyPerson toReplace = model.getPersonAtIndexFromAddressBook(i);
            Person newPerson = new Person(toReplace);
            newPerson.setPrivacyLevel(level);
            try {
                model.updatePerson(toReplace, newPerson);
            } catch (DuplicatePersonException e) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException e) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
        if (level == 3) {
            model.updateFilteredPersonList(new ShowAllPrivacyLevelPredicate());
        } else {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }
        return new CommandResult(String.format(CHANGE_PRIVACY_LEVEL_SUCCESS, Integer.toString(level)));
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrivacyLevelCommand // instanceof handles nulls
                && this.level == ((PrivacyLevelCommand) other).level); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SaveAsCommand.java
``` java

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SaveAsRequestEvent;

/**
 * Saves the data to a new save file at a desired location
 */
public class SaveAsCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_ALIAS = "sa";

    public static final String SAVE_AS_COMMAND_SUCCESS = "Successfully saved file.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new SaveAsRequestEvent());
        return new CommandResult(SAVE_AS_COMMAND_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 *
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "th";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the theme based on the specified style.\n"
            + "Parameters: STYLE\n"
            + "Example: " + COMMAND_WORD + " dark";

    public static final String MESSAGE_THEME_CHANGE_SUCCESS = "Theme Changed to: %1$s";
    public static final String MESSAGE_THEME_NOT_AVAILABLE = "Theme %1$s is not available.";
    public static final String MESSAGE_THEME_IN_USE = "Theme %1$s is currently in use.";


    private final String style;

    public ThemeCommand (String style) {
        this.style = style;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        String styleSheet;

        if (model.getStyleMap().containsKey(style)) {
            styleSheet = model.getStyleMap().get(style);
        } else {
            throw new CommandException(String.format(MESSAGE_THEME_NOT_AVAILABLE, style));
        }

        if (model.getTheme().equals(styleSheet)) {
            throw new CommandException(String.format(MESSAGE_THEME_IN_USE, style));
        }

        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(styleSheet));
        model.setTheme(styleSheet);
        return new CommandResult(String.format(MESSAGE_THEME_CHANGE_SUCCESS, style));
    }

    @Override
    public String toString() {
        return style;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.style.equals(((ThemeCommand) other).style)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddPersonCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK_PRIVATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_PRIVATE;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ReadOnlyPerson object in the context of AddPersonCommand.
 */
public class AddPersonCommandParser extends AddCommandParser {
    /**
     * Constructs a ReadOnlyPerson from the arguments provided.
     */
    public static ReadOnlyPerson constructPerson(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK,
                        PREFIX_AVATAR, PREFIX_TAG, PREFIX_NAME_PRIVATE, PREFIX_PHONE_PRIVATE, PREFIX_EMAIL_PRIVATE,
                        PREFIX_ADDRESS_PRIVATE, PREFIX_REMARK_PRIVATE, PREFIX_TAG_PRIVATE, PREFIX_AVATAR_PRIVATE);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME)
                || (arePrefixesPresent(argMultimap, PREFIX_NAME_PRIVATE)))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));
        }

        try {
            Name name;
            Phone phone;
            Email email;
            Address address;
            Remark remark;
            Avatar avatar;

            name = makeName(argMultimap);

            phone = makePhone(argMultimap);

            email = makeEmail(argMultimap);

            address = mmakeAddress(argMultimap);

            avatar = makeAvatar(argMultimap);

            remark = makeRemark(argMultimap);

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            ReadOnlyPerson person = new Person(name, phone, email, address, false, remark, avatar, tagList);
            return person;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Creates a Name based on the input arguments
     */
    private static Name makeName(ArgumentMultimap argMultimap) throws IllegalValueException {
        Name name;
        if ((arePrefixesPresent(argMultimap, PREFIX_NAME))) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
        } else {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME_PRIVATE), true).get();
        }
        return name;
    }

    /**
     * Creates a Phone based on the input arguments
     */
    private static Phone makePhone(ArgumentMultimap argMultimap) throws IllegalValueException {
        Phone phone;
        if ((arePrefixesPresent(argMultimap, PREFIX_PHONE))) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
        } else if (arePrefixesPresent(argMultimap, PREFIX_PHONE_PRIVATE)) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE_PRIVATE), true).get();
        } else {
            phone = new Phone(null);
        }
        return phone;
    }

    /**
     * Creates an Email based on the input arguments
     */
    private static Email makeEmail(ArgumentMultimap argMultimap) throws IllegalValueException {
        Email email;
        if ((arePrefixesPresent(argMultimap, PREFIX_EMAIL))) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
        } else if (arePrefixesPresent(argMultimap, PREFIX_EMAIL_PRIVATE)) {
            email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL_PRIVATE), true).get();
        } else {
            email = new Email(null);
        }
        return email;
    }

    /**
     * Creates an Address based on the input arguments
     */
    private static Address mmakeAddress(ArgumentMultimap argMultimap) throws IllegalValueException {
        Address address;
        if ((arePrefixesPresent(argMultimap, PREFIX_ADDRESS))) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
        } else if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS_PRIVATE)) {
            address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS_PRIVATE), true).get();
        } else {
            address = new Address(null);
        }
        return address;
    }

    /**
     * Creates an Avatar based on the input arguments
     */
    private static Avatar makeAvatar(ArgumentMultimap argMultimap) throws IllegalValueException {
        Avatar avatar;
        if ((arePrefixesPresent(argMultimap, PREFIX_AVATAR))) {
            avatar = ParserUtil.parseAvatar(argMultimap.getValue(PREFIX_AVATAR)).get();
        } else {
            avatar = new Avatar(null);
        }
        return avatar;
    }

    /**
     * Creates a Remark based on the input arguments
     */
    private static Remark makeRemark(ArgumentMultimap argMultimap) throws IllegalValueException {
        Remark remark;
        if ((arePrefixesPresent(argMultimap, PREFIX_REMARK))) {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
        } else if (arePrefixesPresent(argMultimap, PREFIX_REMARK_PRIVATE)) {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK_PRIVATE), true).get();
        } else {
            remark = new Remark(null);
        }
        return remark;
    }
}
```
###### \java\seedu\address\logic\parser\ChangePrivacyCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangePrivacyCommand;
import seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangePrivacyCommand object
 */
public class ChangePrivacyCommandParser implements Parser<ChangePrivacyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangePrivacyCommand
     * and returns an ChangePrivacyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePrivacyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        }

        PersonPrivacySettings pps = new PersonPrivacySettings();

        checkName(argMultimap, pps);
        checkPhone(argMultimap, pps);
        checkEmail(argMultimap, pps);
        checkAddress(argMultimap, pps);
        checkRemark(argMultimap, pps);

        if (!pps.isAnyFieldNonNull()) {
            throw new ParseException(ChangePrivacyCommand.MESSAGE_NO_FIELDS);
        }

        return new ChangePrivacyCommand(index, pps);
    }

    /**
     * Checks the input under the name prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkName(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            if (argMultimap.getValue(PREFIX_NAME).toString().equals("Optional[true]")) {
                pps.setNameIsPrivate(true);

            } else if (argMultimap.getValue(PREFIX_NAME).toString().equals("Optional[false]")) {
                pps.setNameIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the input under the phone prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkPhone(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            if (argMultimap.getValue(PREFIX_PHONE).toString().equals("Optional[true]")) {
                pps.setPhoneIsPrivate(true);
            } else if (argMultimap.getValue(PREFIX_PHONE).toString().equals("Optional[false]")) {
                pps.setPhoneIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the input under the email prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkEmail(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            if (argMultimap.getValue(PREFIX_EMAIL).toString().equals("Optional[true]")) {
                pps.setEmailIsPrivate(true);
            } else if (argMultimap.getValue(PREFIX_EMAIL).toString().equals("Optional[false]")) {
                pps.setEmailIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the input under the address prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkAddress(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            if (argMultimap.getValue(PREFIX_ADDRESS).toString().equals("Optional[true]")) {
                pps.setAddressIsPrivate(true);
            } else if (argMultimap.getValue(PREFIX_ADDRESS).toString().equals("Optional[false]")) {
                pps.setAddressIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * Checks the input under the address prefix and sets the PersonPrivacySettings depending on the input
     * @param argMultimap The input arguments of the Command
     * @param pps The PersonPrivacySettings to modify
     * @throws ParseException if the input is neither true nor false
     */
    private void checkRemark(ArgumentMultimap argMultimap, PersonPrivacySettings pps) throws ParseException {
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            if (argMultimap.getValue(PREFIX_REMARK).toString().equals("Optional[true]")) {
                pps.setRemarkIsPrivate(true);
            } else if (argMultimap.getValue(PREFIX_REMARK).toString().equals("Optional[false]")) {
                pps.setRemarkIsPrivate(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ChangePrivacyCommand.MESSAGE_USAGE));
            }
        }
    }
}
```
###### \java\seedu\address\logic\parser\LocateCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LocateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LocateCommand object
 */
public class LocateCommandParser implements Parser<LocateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocateCommand
     * and returns an LocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LocateCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LocateCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\NavigateCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_TASK;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NavigateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Location;

/**
 * Parses input arguments and creates a new NavigateCommand object
 */
public class NavigateCommandParser implements Parser<NavigateCommand> {

    private Location from = null;
    private Location to = null;
    private Index fromIndex = null;
    private Index toIndex = null;
    /**
     * Parses the given {@code String} of arguments in the context of the NavigateCommand
     * and returns an NavigateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NavigateCommand parse(String args) throws ParseException {
        resetValues();
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAVIGATE_FROM_PERSON,
                PREFIX_NAVIGATE_FROM_TASK, PREFIX_NAVIGATE_FROM_ADDRESS, PREFIX_NAVIGATE_TO_PERSON,
                PREFIX_NAVIGATE_TO_TASK, PREFIX_NAVIGATE_TO_ADDRESS);

        boolean fromAddress = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_FROM_ADDRESS);
        boolean fromPerson = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_FROM_PERSON);
        boolean fromTask = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_FROM_TASK);

        boolean toAddress = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_TO_ADDRESS);
        boolean toPerson = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_TO_PERSON);
        boolean toTask = arePrefixesPresent(argumentMultimap, PREFIX_NAVIGATE_TO_TASK);

        checkFrom(argumentMultimap, fromAddress, fromPerson, fromTask);

        checkTo(argumentMultimap, toAddress, toPerson, toTask);

        try {
            return new NavigateCommand(from, to, fromIndex, toIndex, fromTask, toTask);
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Resets the values stored in NavigateCommandParser object to null
     */
    private void resetValues() {
        from = null;
        to = null;
        fromIndex = null;
        toIndex = null;
    }

    /**
     * Checksif only 1 To argument is provided
     * @throws ParseException if there are no To arguments or there are more than 1 To arguements
     */
    private void checkTo(ArgumentMultimap argumentMultimap, boolean toAddress, boolean toPerson, boolean toTask)
            throws ParseException {
        if (!(toAddress || toPerson || toTask)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NavigateCommand.MESSAGE_USAGE));
        } else if ((toAddress && (toPerson || toTask)) || (toPerson && toTask)) {
            // If 2 or more to prefixes are present
            throw new ParseException(NavigateCommand.MESSAGE_MULTIPLE_TO_ERROR);
        } else {
            try {
                setArgsForNavigateCommand(argumentMultimap, toAddress, toPerson, false);
            } catch (IllegalValueException e) {
                throw new ParseException(e.getMessage(), e);
            }
        }
    }
    /**
     * Checks if only 1 From argument is provided
     * @throws ParseException if there are no From arguments or there are more than 1 From arguments
     */
    private void checkFrom(ArgumentMultimap argumentMultimap, boolean fromAddress, boolean fromPerson, boolean fromTask)
            throws ParseException {
        if (!(fromAddress || fromPerson || fromTask)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NavigateCommand.MESSAGE_USAGE));
        } else if ((fromAddress && (fromPerson || fromTask)) || (fromPerson && fromTask)) {
            // If 2 or more from prefixes are present
            throw new ParseException(NavigateCommand.MESSAGE_MULTIPLE_FROM_ERROR);
        } else {
            try {
                setArgsForNavigateCommand(argumentMultimap, fromAddress, fromPerson, true);
            } catch (IllegalValueException e) {
                throw new ParseException(e.getMessage(), e);
            }
        }
    }

    private void setArgsForNavigateCommand(ArgumentMultimap argumentMultimap, boolean address, boolean person,
                                           boolean forFrom) throws IllegalValueException {
        if (address) {
            if (forFrom) {
                from = new Location(ParserUtil.parseLocationFromAddress(
                        argumentMultimap.getValue(PREFIX_NAVIGATE_FROM_ADDRESS)).get().toString());
            } else {
                to = new Location(ParserUtil.parseLocationFromAddress(
                        argumentMultimap.getValue(PREFIX_NAVIGATE_TO_ADDRESS)).get().toString());
            }
        } else if (person) {
            if (forFrom) {
                fromIndex = ParserUtil.parseIndex(argumentMultimap
                        .getValue(PREFIX_NAVIGATE_FROM_PERSON).get());
            } else {
                toIndex = ParserUtil.parseIndex(argumentMultimap
                        .getValue(PREFIX_NAVIGATE_TO_PERSON).get());
            }
        } else {
            if (forFrom) {
                fromIndex = ParserUtil.parseIndex(argumentMultimap
                        .getValue(PREFIX_NAVIGATE_FROM_TASK).get());
            } else {
                toIndex = ParserUtil.parseIndex(argumentMultimap
                        .getValue(PREFIX_NAVIGATE_TO_TASK).get());
            }
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
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * Takes in a (@code boolean getIsPrivate) which will set the Name to be private if true.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name, boolean isPrivate) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get(), isPrivate)) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * Takes in a (@code boolean getIsPrivate) which will set the Phone to be private if true.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone, boolean isPrivate) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get(), isPrivate)) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * Takes in a (@code boolean getIsPrivate) which will set the Address to be private if true.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address, boolean isPrivate)
            throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get(), isPrivate)) : Optional.empty();
    }
    //author charlesgoh
    /**
     * Parses a {@code Optional<String> avatar} into an {@code Optional<Address>} if {@code avatar} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Avatar> parseAvatar(Optional<String> avatar) throws IllegalValueException {
        requireNonNull(avatar);
        return avatar.isPresent() ? Optional.of(new Avatar(avatar.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * Takes in a (@code boolean getIsPrivate) which will set the Remark to be private if true.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark, boolean isPrivate)
            throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get(), isPrivate)) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * Takes in a (@code boolean getIsPrivate) which will set the Email to be private if true.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email, boolean isPrivate) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get(), isPrivate)) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a string into a {@code TaskAddress} if it is present.
     */
    public static Optional<TaskAddress> parseTaskAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new TaskAddress(address.get())) : Optional.empty();
    }

    /**
     * Parses a string into a (@code Location) if it is present.
     */
    public static Optional<Location> parseLocationFromAddress(Optional<String> location) throws IllegalValueException {
        requireNonNull(location);
        return location.isPresent() ? Optional.of(new Location(location.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\PrivacyLevelCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PrivacyLevelCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PrivacyLevelCommand object
 */
public class PrivacyLevelCommandParser implements Parser<PrivacyLevelCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PrivacyLevelCommand
     * and returns an PrivacyLevelCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PrivacyLevelCommand parse(String args) throws ParseException {
        try {
            int level = ParserUtil.parseIndex(args).getOneBased();
            return new PrivacyLevelCommand(level);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrivacyLevelCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParser.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns an ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty() || trimmed == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        } else {
            return new ThemeCommand(trimmed);
        }
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
        MainWindow mw = ui.getMainWindow();
        mw.setMainApp(this);
        mw.setStorage(storage);
        mw.setModel(model);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// task-level operations

    /**
     * Initialises the style map by adding the key value pairs
     * for the strings that will be input in ThemeCommand and the file name of the .css file
     */
    private void initialiseStyleMap() {
        styleMap.put("dark", "DarkTheme.css");
        styleMap.put("Dark", "DarkTheme.css");
        styleMap.put("light", "LightTheme.css");
        styleMap.put("Light", "LightTheme.css");
    }

    public HashMap<String, String> getStyleMap() {
        return styleMap;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public ReadOnlyPerson getPersonAtIndexFromPersonList(int index) {
        return persons.asObservableList().get(index);
    }

```
###### \java\seedu\address\model\Location.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Location can take any value, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given location.
     *
     * @throws IllegalValueException if given location string is invalid.
     */
    public Location(String location) throws IllegalValueException {
        if (location == null) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        if (!isValidLocation(location)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    void setPrivacyLevel(int level);

    int getPrivacyLevel();

    ReadOnlyPerson getPersonAtIndexFromAddressBook(int index);

    void setTheme(String theme);

    String getTheme();

    HashMap<String, String> getStyleMap();
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        if (privacyLevel == 3) {
            if (predicate instanceof NameContainsKeywordsPredicate) {
                this.updateFilteredPersonList(new NameContainsKeywordsPrivacyLevelPredicate(((
                        NameContainsKeywordsPredicate) predicate).getKeywords()));
            } else if (predicate instanceof NameContainsTagsPredicate) {
                this.updateFilteredPersonList(new NameContainsTagsPrivacyLevelPredicate(((
                        NameContainsTagsPredicate) predicate).getTags()));
            } else if (predicate instanceof NameContainsFavouritePredicate) {
                this.updateFilteredPersonList(new NameContainsFavouritePrivacyLevelPredicate());
            } else if (predicate == PREDICATE_SHOW_ALL_PERSONS) {
                this.updateFilteredPersonList(new ShowAllPrivacyLevelPredicate());
            } else {
                filteredPersons.setPredicate(predicate);
            }
        } else {
            filteredPersons.setPredicate(predicate);
        }
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void setPrivacyLevel(int level) {
        if (level < 1 || level > 3) {
            throw new IllegalArgumentException("Privacy Level can only be 0, 1 or 2");
        } else {
            this.privacyLevel = level;
        }
    }

    @Override
    public int getPrivacyLevel() {
        return this.privacyLevel;
    }

    @Override
    public ReadOnlyPerson getPersonAtIndexFromAddressBook(int index) {
        return addressBook.getPersonAtIndexFromPersonList(index);
    }

    @Override
    public void setTheme(String theme) {
        this.theme = theme;
        userPrefs.setTheme(theme);
    }

    @Override
    public String getTheme() {
        return theme;
    }

    @Override
    public HashMap<String, String> getStyleMap() {
        return addressBook.getStyleMap();
    }

}
```
###### \java\seedu\address\model\person\Address.java
``` java
    public Address(String address, boolean isPrivate) throws IllegalValueException {
        this(address);
        this.setPrivate(isPrivate);
    }

```
###### \java\seedu\address\model\person\Address.java
``` java
    @Override
    public String toString() {
        if (privacyLevel == 1) {
            return value;
        } else {
            if (isPrivate) {
                return "<Private Address>";
            }
            return value;
        }
    }

```
###### \java\seedu\address\model\person\Address.java
``` java
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setPrivacyLevel(int level) {
        this.privacyLevel = level;
    }

    public int getPrivacyLevel() {
        return this.privacyLevel;
    }
}
```
###### \java\seedu\address\model\person\Avatar.java
``` java
    @Override
    public String toString() {
        return value;
    }
```
###### \java\seedu\address\model\person\Email.java
``` java
    public Email(String email, boolean isPrivate) throws IllegalValueException {
        this(email);
        this.setPrivate(isPrivate);
    }

```
###### \java\seedu\address\model\person\Email.java
``` java
    @Override
    public String toString() {
        if (privacyLevel == 1) {
            return value;
        } else {
            if (isPrivate) {
                return "<Private Email>";
            }
            return value;
        }
    }

```
###### \java\seedu\address\model\person\Email.java
``` java
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setPrivacyLevel(int level) {
        this.privacyLevel = level;
    }

    public int getPrivacyLevel() {
        return this.privacyLevel;
    }
}
```
###### \java\seedu\address\model\person\Name.java
``` java
    public Name(String name, boolean isPrivate) throws IllegalValueException {
        this(name);
        this.setPrivate(isPrivate);
    }

```
###### \java\seedu\address\model\person\Name.java
``` java
    @Override
    public String toString() {
        if (privacyLevel == 1) {
            return value;
        } else {
            if (isPrivate) {
                return "<Private Name>";
            }
            return value;
        }
    }

```
###### \java\seedu\address\model\person\Name.java
``` java
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setPrivacyLevel(int level) {
        this.privacyLevel = level;
    }

    public int getPrivacyLevel() {
        return this.privacyLevel;
    }
}
```
###### \java\seedu\address\model\person\NameContainsFavouritePrivacyLevelPredicate.java
``` java
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} are favoured and has no private fields
 */
public class NameContainsFavouritePrivacyLevelPredicate implements Predicate<ReadOnlyPerson> {

    public NameContainsFavouritePrivacyLevelPredicate() {
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return (person.getFavourite() && !person.hasPrivateField());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsFavouritePrivacyLevelPredicate); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
``` java
    public List<String> getKeywords() {
        return keywords;
    }
```
###### \java\seedu\address\model\person\NameContainsKeywordsPrivacyLevelPredicate.java
``` java
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given
 * and has no private fields
 */
public class NameContainsKeywordsPrivacyLevelPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsKeywordsPrivacyLevelPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().value, keyword))
                        && !person.hasPrivateField());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPrivacyLevelPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPrivacyLevelPredicate) other).keywords)); //state check
    }

}
```
###### \java\seedu\address\model\person\NameContainsTagsPredicate.java
``` java
    public List<String> getTags() {
        return tags;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public void setPrivacyLevel(int level) {
        this.getName().setPrivacyLevel(level);
        this.getPhone().setPrivacyLevel(level);
        this.getEmail().setPrivacyLevel(level);
        this.getAddress().setPrivacyLevel(level);
        this.getRemark().setPrivacyLevel(level);
        this.privacyLevel = level;
    }

    public int getPrivacyLevel() {
        return this.privacyLevel;
    }

    /**
     * Returns true if the Person has at least one private field and false otherwise
     */
    @Override
    public boolean hasPrivateField() {
        return (this.getName().getIsPrivate() || this.getPhone().getIsPrivate() || this.getAddress().getIsPrivate()
                || this.getEmail().getIsPrivate() || this.getRemark().getIsPrivate());
    }
}
```
###### \java\seedu\address\model\person\Phone.java
``` java
    public Phone(String phone, boolean isPrivate) throws IllegalValueException {
        this(phone);
        this.setPrivate(isPrivate);
    }

```
###### \java\seedu\address\model\person\Phone.java
``` java
    @Override
    public String toString() {
        if (privacyLevel == 1) {
            return value;
        } else {
            if (isPrivate) {
                return "<Private Phone>";
            }
            return value;
        }
    }

```
###### \java\seedu\address\model\person\Phone.java
``` java
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setPrivacyLevel(int level) {
        this.privacyLevel = level;
    }

    public int getPrivacyLevel() {
        return this.privacyLevel;
    }
}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    void setPrivacyLevel(int level);
    int getPrivacyLevel();
    boolean hasPrivateField();
```
###### \java\seedu\address\model\person\Remark.java
``` java
    @Override
    public String toString() {
        if (privacyLevel == 1) {
            return value;
        } else {
            if (isPrivate) {
                return "<Private Remark>";
            }
            return value;
        }
    }
```
###### \java\seedu\address\model\person\Remark.java
``` java
    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setPrivacyLevel(int level) {
        this.privacyLevel = level;
    }

    public int getPrivacyLevel() {
        return this.privacyLevel;
    }
}
```
###### \java\seedu\address\model\person\ShowAllPrivacyLevelPredicate.java
``` java

import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} are favoured after taking into account privacy level.
 */
public class ShowAllPrivacyLevelPredicate implements Predicate<ReadOnlyPerson> {

    public ShowAllPrivacyLevelPredicate() {
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return !person.hasPrivateField();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowAllPrivacyLevelPredicate); // instanceof handles nulls
    }

}
```
###### \java\seedu\address\model\task\TaskAddress.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskAddress(String)}
 */
public class TaskAddress {
    public static final String MESSAGE_TASK_ADDRESS_CONSTRAINTS =
            "Task addresses can take any values, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";
    public static final String ADDRESS_PLACEHOLDER_VALUE = "";

    public final String taskAddress;
    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public TaskAddress(String address) throws IllegalValueException {
        if (address == null) {
            this.taskAddress = ADDRESS_PLACEHOLDER_VALUE;
            return;
        }
        if (!isValidTaskAddress(address)) {
            throw new IllegalValueException(MESSAGE_TASK_ADDRESS_CONSTRAINTS);
        }
        this.taskAddress = address;
    }

    /**
     * Returns true if a given string is a valid task address.
     */
    public static boolean isValidTaskAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX) || test.equals(ADDRESS_PLACEHOLDER_VALUE);
    }

    @Override
    public String toString() {
        return taskAddress;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskAddress // instanceof handles nulls
                && this.taskAddress.equals(((TaskAddress) other).taskAddress)); // state check
    }

    @Override
    public int hashCode() {
        return taskAddress.hashCode();
    }
}

```
###### \java\seedu\address\model\UserPrefs.java
``` java
    private String theme;
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getTheme() {
        if (theme == null) {
            return "/view/DarkTheme.css";
        } else {
            return theme;
        }
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        favourite = source.getFavourite().toString();
        remark = source.getRemark().value;
        avatar = source.getAvatar().value;

        nameIsPrivate = source.getName().getIsPrivate();
        phoneIsPrivate = source.getPhone().getIsPrivate();
        emailIsPrivate = source.getEmail().getIsPrivate();
        addressIsPrivate = source.getAddress().getIsPrivate();
        remarkIsPrivate = source.getRemark().getIsPrivate();

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        if (nameIsPrivate == null) {
            nameIsPrivate = false;
        }
        if (phoneIsPrivate == null) {
            phoneIsPrivate = false;
        }
        if (emailIsPrivate == null) {
            emailIsPrivate = false;
        }
        if (addressIsPrivate == null) {
            addressIsPrivate = false;
        }
        if (remarkIsPrivate == null) {
            remarkIsPrivate = false;
        }
        final Name name = new Name(this.name, this.nameIsPrivate);
        final Phone phone = new Phone(this.phone, this.phoneIsPrivate);
        final Email email = new Email(this.email, this.emailIsPrivate);
        final Address address = new Address(this.address, this.addressIsPrivate);
        final Boolean favourite = new Boolean(this.favourite);
        final Remark remark = new Remark(this.remark, this.remarkIsPrivate);
        final Avatar avatar = new Avatar(this.avatar);
        final Set<Tag> tags = new HashSet<>(personTags);
        return new Person(name, phone, email, address, favourite, remark, avatar, tags);
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads a google search for a person's address if their address is not private
     * Prints out a message on the result display otherwise
     * @param person The person's address we want to search for
     */
    private void loadMapsPage(ReadOnlyPerson person) {
        if (person.getAddress().getIsPrivate()) {
            raise(new NewResultAvailableEvent(PRIVATE_ADDRESS_CANNOT_SEARCH));
        } else {
            loadPage(GOOGLE_MAPS_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+")
                + GOOGLE_MAPS_URL_SUFFIX);
        }
    }

    /**
     * Loads Google Maps with directions on how to go from one location to another
     * @param fromLocation The location we want to start from
     * @param toLocation The location we want to reach
     */
    private void loadDirectionsPage(String fromLocation, String toLocation) {
        loadPage(GOOGLE_MAPS_DIRECTIONS_PREFIX + "&origin="
                + fromLocation.replaceAll("#(\\w+)\\s*", "").replaceAll(" ", "+")
                .replaceAll("-(\\w+)\\s*", "")
                + "&destination="
                + toLocation.replaceAll("#(\\w+)\\s*", "").replaceAll(" ", "+")
                .replaceAll("-(\\w+)\\s*", "")
                + GOOGLE_MAPS_DIRECTIONS_SUFFIX);
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleBrowserPanelLocateEvent(BrowserPanelLocateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadMapsPage(event.getNewSelection());
    }

    @Subscribe
    private void handleBrowserPanelNavigateEvent(BrowserPanelNavigateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDirectionsPage(event.getFromLocation().toString(), event.getToLocation().toString());
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(openMenuItem, KeyCombination.valueOf("SHORTCUT+O"));
        setAccelerator(saveMenuItem, KeyCombination.valueOf("SHORTCUT+S"));
        setAccelerator(exitMenuItem, KeyCombination.valueOf("ALT+F4"));
        setAccelerator(increaseSizeMenuItem, KeyCombination.valueOf("SHORTCUT+W"));
        setAccelerator(decreaseSizeMenuItem, KeyCombination.valueOf("SHORTCUT+E"));
        setAccelerator(resetSizeMenuItem, KeyCombination.valueOf("SHORTCUT+R"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp the MainApp itself
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Is called by the main application to provide MainWindow with Storage
     *
     * @param s the Storage used by MainApp
     */
    public void setStorage(Storage s) {
        this.storage = s;
    }

    /**
     * Is called by the main application to  provide MainWindow with Model
     *
     * @param m the Model used by MainApp
     */
    public void setModel(Model m) {
        this.model = m;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel();
        viewTaskPanel = new ViewTaskPanel();
        viewPersonPanel = new ViewPersonPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        String preferredFilePath = this.prefs.getAddressBookFilePath();
        StatusBarFooter statusBarFooter = new StatusBarFooter(preferredFilePath, logic.getFilteredPersonList().size());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Sets the default theme based on user preferences.
     */
    private void setWindowDefaultTheme(UserPrefs prefs) {
        getRoot().getStylesheets().add(prefs.getTheme());
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    public String getTheme() {
        return getRoot().getStylesheets().get(1);
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the data from a desired location
     */
    @FXML
    private void handleOpen() throws IOException, DataConversionException {
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            // Change file path to the opened file
            storage.changeFilePath(file.getPath(), prefs);
            // Reset data in the model to the data from the opened file
            model.resetData(XmlFileStorage.loadDataFromSaveFile(file));
            // Update the UI
            fillInnerParts();
        }
    }

    @Subscribe
    private void handleOpenRequestEvent(OpenRequestEvent event) throws IOException, DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleOpen();
    }

    /**
     * Saves the data at a desired location
     */
    @FXML
    private void handleSaveAs() throws IOException {
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            // Change file path to the new save file
            storage.changeFilePath(file.getPath(), prefs);
            // Save the address book data and the user preferences
            storage.saveAddressBook(model.getAddressBook());
            storage.saveUserPrefs(prefs);
            // Update the UI
            fillInnerParts();
        }
    }

    @Subscribe
    private void handleSaveAsRequestEvent(SaveAsRequestEvent event) throws IOException, DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleSaveAs();
    }

    /**
     * Changes the existing theme to the input theme
     */
    public void handleChangeTheme(String theme) {
        if (getRoot().getStylesheets().size() > 1) {
            getRoot().getStylesheets().remove(1);
        }
        getRoot().getStylesheets().add(VIEW_PATH + theme);
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleChangeTheme(event.getStyleSheet());
    }
}
```
