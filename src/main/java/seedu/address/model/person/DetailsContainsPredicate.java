//@@author Hailinx
package seedu.address.model.person;

import java.util.Iterator;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.model.tag.Tag;

/**
 * Tests that every {@code ReadOnlyPerson}'s attribute matches corresponding keyword
 * in descriptor given except it is null.
 */
public class DetailsContainsPredicate implements Predicate<ReadOnlyPerson> {
    private FindDetailDescriptor descriptor;

    public DetailsContainsPredicate(FindDetailDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return !isNameNotMatchedIfPresent(person)
                && !isPhoneNotMatchedIfPresent(person)
                && !isEmailNotMatchedIfPresent(person)
                && !isAddressNotMatchedIfPresent(person)
                && !isTagNotMatchedIfPresent(person);
    }

    /**
     * @return true if name in {@code descriptor} present but not match name of {@code person}
     */
    private boolean isNameNotMatchedIfPresent(ReadOnlyPerson person) {
        String personName = person.getName().fullName.toLowerCase();
        return descriptor.getName().isPresent()
                && !personName.contains(descriptor.getName().get().toLowerCase());
    }

    /**
     * @return true if phone in {@code descriptor} present but not match phone of {@code person}
     */
    private boolean isPhoneNotMatchedIfPresent(ReadOnlyPerson person) {
        String personPhone = person.getPhone().value;
        return descriptor.getPhone().isPresent()
                && !personPhone.contains(descriptor.getPhone().get());
    }

    /**
     * @return true if email in {@code descriptor} present but not match email of {@code person}
     */
    private boolean isEmailNotMatchedIfPresent(ReadOnlyPerson person) {
        String personEmail = person.getEmail().value.toLowerCase();
        return descriptor.getEmail().isPresent()
                && !personEmail.contains(descriptor.getEmail().get().toLowerCase());
    }

    /**
     * @return true if address in {@code descriptor} present but not match address of {@code person}
     */
    private boolean isAddressNotMatchedIfPresent(ReadOnlyPerson person) {
        String personAddress = person.getAddress().value.toLowerCase();
        return descriptor.getAddress().isPresent()
                && !personAddress.contains(descriptor.getAddress().get().toLowerCase());
    }

    /**
     * @return true if tag in {@code descriptor} present but not match tag of {@code person}
     */
    private boolean isTagNotMatchedIfPresent(ReadOnlyPerson person) {
        if (!descriptor.getTags().isPresent()) {
            return false;
        }
        Iterator<Tag> descriptorIterator = descriptor.getTags().get().iterator();
        Iterator<Tag> personIterator = person.getTags().iterator();
        while (descriptorIterator.hasNext()) {
            String tagInDescriptor = descriptorIterator.next().tagName.toLowerCase();
            if (isPersonContainTag(tagInDescriptor, personIterator)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the given tag string not present in {@code personIterator}
     */
    private boolean isPersonContainTag(String tagInDescriptor, Iterator<Tag> personIterator) {
        boolean isContain = false;
        while (personIterator.hasNext()) {
            String tagInPerson = personIterator.next().tagName.toLowerCase();
            if (tagInPerson.contains(tagInDescriptor)) {
                isContain = true;
            }
        }
        return !isContain;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsContainsPredicate // instanceof handles nulls
                && this.descriptor.equals(((DetailsContainsPredicate) other).descriptor)); // state check
    }

}
