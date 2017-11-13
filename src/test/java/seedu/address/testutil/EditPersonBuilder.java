//@@author huiyiiih
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building EditPerson objects.
 */
public class EditPersonBuilder {

    private EditPerson editPerson;

    public EditPersonBuilder() {
        editPerson = new EditPerson();
    }

    public EditPersonBuilder(EditPerson editPerson) {
        this.editPerson = new EditPerson(editPerson);
    }

    /**
     * Returns an {@code EditPerson} with fields containing {@code person}'s details
     */
    public EditPersonBuilder(ReadOnlyPerson person) {
        editPerson = new EditPerson();
        editPerson.setName(person.getName());
        editPerson.setPhone(person.getPhone());
        editPerson.setEmail(person.getEmail());
        editPerson.setAddress(person.getAddress());
        editPerson.setCompany(person.getCompany());
        editPerson.setPosition(person.getPosition());
        editPerson.setStatus(person.getStatus());
        editPerson.setPriority(person.getPriority());
        editPerson.setNote(person.getNote());
        editPerson.setPhoto(person.getPhoto());
        editPerson.setTags(person.getTags());
        editPerson.setToAdd(person.getRelation());
    }

    /**
     * Sets the {@code Name} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(editPerson::setName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withPhone(String phone) {
        try {
            ParserUtil.parsePhone(Optional.of(phone)).ifPresent(editPerson::setPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withEmail(String email) {
        try {
            ParserUtil.parseEmail(Optional.of(email)).ifPresent(editPerson::setEmail);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(editPerson::setAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    //@@author sebtsh
    /**
     * Sets the {@code Company} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withCompany(String company) {
        try {
            ParserUtil.parseCompany(Optional.of(company)).ifPresent(editPerson::setCompany);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("company is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withPosition(String position) {
        try {
            ParserUtil.parsePosition(Optional.of(position)).ifPresent(editPerson::setPosition);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("position is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withStatus(String status) {
        try {
            ParserUtil.parseStatus(Optional.of(status)).ifPresent(editPerson::setStatus);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("status is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(editPerson::setPriority);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withNote(String note) {
        try {
            ParserUtil.parseNote(Optional.of(note)).ifPresent(editPerson::setNote);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("note is expected to be unique.");
        }
        return this;
    }
    //@@author

    /**
     * Sets the {@code Photo} of the {@code EditPerson} that we are
     * building.
     */
    public EditPersonBuilder withPhoto(String note) {
        try {
            ParserUtil.parsePhoto(Optional.of(note)).ifPresent
                (editPerson::setPhoto);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Photo is expected to be "
            + "unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPerson}
     * that we are building.
     */
    public EditPersonBuilder withTags(String... tags) {
        try {
            editPerson.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Realtionship>} and set it to the {@code EditPerson}
     * that we are building.
     */
    public EditPersonBuilder withToAddRel(String... relation) {
        try {
            editPerson.setToAdd(ParserUtil.parseRel(Arrays.asList(relation)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("relationships are expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPerson}
     * that we are building.
     */
    public EditPersonBuilder withToDeleteRel(String... relation) {
        try {
            editPerson.setToDelete(ParserUtil.parseRel(Arrays.asList(relation)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("relationships are expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPerson}
     * that we are building.
     */
    public EditPersonBuilder withToClearRels(boolean shouldClear) {
        editPerson.setClearRels(shouldClear);
        return this;
    }

    public EditPerson build() {
        return editPerson;
    }
}
//@@author

