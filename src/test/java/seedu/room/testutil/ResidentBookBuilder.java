package seedu.room.testutil;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.ResidentBook;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.tag.Tag;

/**
 * A utility class to help with building Residentbook objects.
 * Example usage: <br>
 *     {@code ResidentBook ab = new ResidentBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class ResidentBookBuilder {

    private ResidentBook residentBook;

    public ResidentBookBuilder() {
        residentBook = new ResidentBook();
    }

    public ResidentBookBuilder(ResidentBook residentBook) {
        this.residentBook = residentBook;
    }

    /**
     * Adds a new {@code Person} to the {@code ResidentBook} that we are building.
     */
    public ResidentBookBuilder withPerson(ReadOnlyPerson person) {
        try {
            residentBook.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code ResidentBook} that we are building.
     */
    public ResidentBookBuilder withTag(String tagName) {
        try {
            residentBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public ResidentBook build() {
        return residentBook;
    }
}
