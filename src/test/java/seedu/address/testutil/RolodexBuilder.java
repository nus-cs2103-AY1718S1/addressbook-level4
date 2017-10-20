package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Rolodex;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Rolodex objects.
 * Example usage: <br>
 *     {@code Rolodex ab = new RolodexBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class RolodexBuilder {

    private Rolodex rolodex;

    public RolodexBuilder() {
        rolodex = new Rolodex();
    }

    public RolodexBuilder(Rolodex rolodex) {
        this.rolodex = rolodex;
    }

    /**
     * Adds a new {@code Person} to the {@code Rolodex} that we are building.
     */
    public RolodexBuilder withPerson(ReadOnlyPerson person) {
        try {
            rolodex.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code Rolodex} that we are building.
     */
    public RolodexBuilder withTag(String tagName) {
        try {
            rolodex.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public Rolodex build() {
        return rolodex;
    }
}
