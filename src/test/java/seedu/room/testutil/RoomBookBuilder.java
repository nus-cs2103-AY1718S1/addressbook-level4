package seedu.room.testutil;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.RoomBook;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.tag.Tag;

/**
 * A utility class to help with building Roombook objects.
 * Example usage: <br>
 *     {@code RoomBook ab = new RoomBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class RoomBookBuilder {

    private RoomBook roomBook;

    public RoomBookBuilder() {
        roomBook = new RoomBook();
    }

    public RoomBookBuilder(RoomBook roomBook) {
        this.roomBook = roomBook;
    }

    /**
     * Adds a new {@code Person} to the {@code RoomBook} that we are building.
     */
    public RoomBookBuilder withPerson(ReadOnlyPerson person) {
        try {
            roomBook.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code RoomBook} that we are building.
     */
    public RoomBookBuilder withTag(String tagName) {
        try {
            roomBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public RoomBook build() {
        return roomBook;
    }
}
