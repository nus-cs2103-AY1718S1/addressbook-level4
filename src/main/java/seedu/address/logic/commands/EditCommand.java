package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REM_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEB_LINK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.weblink.WebLink;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            //@@author zhoukai07
            + "[" + PREFIX_ADD_TAG + "TAG]..."
            + "[" + PREFIX_REM_TAG + "TAG]..."
            //@@author
            + PREFIX_CLEAR_TAG + "\n"
            + "[" + PREFIX_WEB_LINK + "WEB LINK]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_WEB_LINK + "https://www.facebook.com/jiasheng.an"
            + PREFIX_CLEAR_TAG;
    //@@author hansiang93
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {Index} "
            + PREFIX_PHONE + "{phone} "
            + PREFIX_EMAIL + "{email} "
            + PREFIX_TAG + "{tag} "
            + PREFIX_WEB_LINK + "{weblink}";
    //@@author
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    public EditPersonDescriptor getEditPersonDescriptor() {
        return editPersonDescriptor;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                            EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        ArrayList<Email> updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Remark updatedRemark = personToEdit.getRemark();
        Set<WebLink> updatedWebLinks = editPersonDescriptor.getWebLinks().orElse(personToEdit.getWebLinks());
        final Set<Tag> updatedTags = new HashSet<>();
        if (!editPersonDescriptor.shouldClear()) {
            updatedTags.addAll(personToEdit.getTags());
        }
        editPersonDescriptor.getToAdd().ifPresent(updatedTags::addAll);
        editPersonDescriptor.getToRemove().ifPresent(updatedTags::removeAll);
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedRemark, updatedTags, updatedWebLinks);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private ArrayList<Email> email;
        private Address address;
        //@@author zhoukai07
        private boolean clearTags = false;
        private Set<Tag> toAdd;
        private Set<Tag> toRemove;
        //@@author
        private Set<WebLink> webLinks;

        public EditPersonDescriptor() {
        }

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.clearTags = toCopy.clearTags;
            this.toAdd = toCopy.toAdd;
            this.toRemove = toCopy.toRemove;
            this.webLinks = toCopy.webLinks;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address,
                    this.toAdd, this.toRemove, this.webLinks) || this.clearTags;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(ArrayList<Email> email) {
            this.email = email;
        }

        public Optional<ArrayList<Email>> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        //@@author zhoukai07
        public void setToAdd(Set<Tag> toAdd) {
            this.toAdd = toAdd;
        }
        public void setToRemove(Set<Tag> toRemove) {
            this.toRemove = toRemove;
        }
        public void setClearTags(boolean clearTags) {
            this.clearTags = clearTags;
        }
        //@@author
        public void setWebLinks(Set<WebLink> webLinks) {
            this.webLinks = webLinks;
        }

        public Optional<Set<Tag>> getToAdd() {
            return Optional.ofNullable(toAdd);
        }
        public Optional<Set<Tag>> getToRemove() {
            return Optional.ofNullable(toRemove);
        }
        public boolean shouldClear() {
            return clearTags;
        }

        public Optional<Set<WebLink>> getWebLinks() {
            return Optional.ofNullable(webLinks);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getToAdd().equals(e.getToAdd())
                    && getToRemove().equals(e.getToRemove())
                    && clearTags == e.clearTags
                    && getWebLinks().equals(e.getWebLinks());
        }
    }
}
