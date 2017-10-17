package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.lecturer.Lecturer;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.DuplicateLessonException;


/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withLesson("MA1101R").withLecturer("Dr Tan").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Lesson} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withLesson(ReadOnlyLesson lesson) {
        try {
            addressBook.addLesson(lesson);
        } catch (DuplicateLessonException dpe) {
            throw new IllegalArgumentException("lesson is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withLecturer(String lecturerName) {
        try {
            addressBook.addLecturer(new Lecturer(lecturerName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("lecturerName is expected to be valid.");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
