package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PLACES;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.place.Address;
import seedu.address.model.place.Name;
import seedu.address.model.place.Phone;
import seedu.address.model.place.Place;
import seedu.address.model.place.PostalCode;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.Website;
import seedu.address.model.place.exceptions.DuplicatePlaceException;
import seedu.address.model.place.exceptions.PlaceNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing place in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMANND_WORD_ALIAS = "ed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the place identified "
            + "by the index number used in the last place listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_WEBSITE + "WEBSITE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_POSTAL_CODE + "POSTAL CODE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_WEBSITE + "www.marinabaysands.com "
            + PREFIX_POSTAL_CODE + "639304";

    public static final String MESSAGE_EDIT_PLACE_SUCCESS = "Edited Place: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PLACE = "This place already exists in the address book.";

    private final Index index;
    private final EditPlaceDescriptor editPlaceDescriptor;

    /**
     * @param index of the place in the filtered place list to edit
     * @param editPlaceDescriptor details to edit the place with
     */
    public EditCommand(Index index, EditPlaceDescriptor editPlaceDescriptor) {
        requireNonNull(index);
        requireNonNull(editPlaceDescriptor);

        this.index = index;
        this.editPlaceDescriptor = new EditPlaceDescriptor(editPlaceDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPlace> lastShownList = model.getFilteredPlaceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        ReadOnlyPlace placeToEdit = lastShownList.get(index.getZeroBased());
        Place editedPlace = createEditedPlace(placeToEdit, editPlaceDescriptor);

        try {
            model.updatePlace(placeToEdit, editedPlace);
        } catch (DuplicatePlaceException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PLACE);
        } catch (PlaceNotFoundException pnfe) {
            throw new AssertionError("The target place cannot be missing");
        }
        model.updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);
        return new CommandResult(String.format(MESSAGE_EDIT_PLACE_SUCCESS, editedPlace));
    }

    /**
     * Creates and returns a {@code Place} with the details of {@code placeToEdit}
     * edited with {@code editPlaceDescriptor}.
     */
    private static Place createEditedPlace(ReadOnlyPlace placeToEdit,
                                           EditPlaceDescriptor editPlaceDescriptor) {
        assert placeToEdit != null;

        Name updatedName = editPlaceDescriptor.getName().orElse(placeToEdit.getName());
        Phone updatedPhone = editPlaceDescriptor.getPhone().orElse(placeToEdit.getPhone());
        Website updatedWebsite = editPlaceDescriptor.getWebsite().orElse(placeToEdit.getWebsite());
        Address updatedAddress = editPlaceDescriptor.getAddress().orElse(placeToEdit.getAddress());
        PostalCode updatedPostalCode = editPlaceDescriptor.getPostalCode().orElse(placeToEdit.getPostalCode());
        Set<Tag> updatedTags = editPlaceDescriptor.getTags().orElse(placeToEdit.getTags());

        return new Place(updatedName, updatedPhone, updatedWebsite, updatedAddress, updatedPostalCode, updatedTags);
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
                && editPlaceDescriptor.equals(e.editPlaceDescriptor);
    }

    /**
     * Stores the details to edit the place with. Each non-empty field value will replace the
     * corresponding field value of the place.
     */
    public static class EditPlaceDescriptor {
        private Name name;
        private Phone phone;
        private Website website;
        private Address address;
        private PostalCode postalCode;
        private Set<Tag> tags;

        public EditPlaceDescriptor() {}

        public EditPlaceDescriptor(EditPlaceDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.website = toCopy.website;
            this.address = toCopy.address;
            this.postalCode = toCopy.postalCode;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                    this.name,
                    this.phone,
                    this.website,
                    this.address,
                    this.postalCode,
                    this.tags);
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

        public void setWebsite(Website website) {
            this.website = website;
        }

        public Optional<Website> getWebsite() {
            return Optional.ofNullable(website);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setPostalCode(PostalCode postalCode) {
            this.postalCode = postalCode;
        }

        public Optional<PostalCode> getPostalCode() {
            return Optional.ofNullable(postalCode);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPlaceDescriptor)) {
                return false;
            }

            // state check
            EditPlaceDescriptor e = (EditPlaceDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getWebsite().equals(e.getWebsite())
                    && getAddress().equals(e.getAddress())
                    && getPostalCode().equals(e.getPostalCode())
                    && getTags().equals(e.getTags());
        }
    }
}
