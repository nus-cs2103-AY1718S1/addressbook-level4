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
        if (descriptor.getName().isPresent()
                && !person.getName().fullName.toLowerCase().contains(
                descriptor.getName().get().toLowerCase())) {
            return false;
        }
        if (descriptor.getPhone().isPresent()
                && !person.getPhone().value.toLowerCase().contains(
                descriptor.getPhone().get().toLowerCase())) {
            return false;
        }
        if (descriptor.getEmail().isPresent()
                && !person.getEmail().value.toLowerCase().contains(
                descriptor.getEmail().get().toLowerCase())) {
            return false;
        }
        if (descriptor.getAddress().isPresent()
                && !person.getAddress().value.toLowerCase().contains(
                descriptor.getAddress().get().toLowerCase())) {
            return false;
        }
        if (descriptor.getTags().isPresent()) {
            Iterator<Tag> descriptorIterator = descriptor.getTags().get().iterator();
            Iterator<Tag> personIterator = person.getTags().iterator();
            while (descriptorIterator.hasNext()) {
                boolean isContainIgnoreCase = false;
                while (personIterator.hasNext()) {
                    if (personIterator.next().tagName.toLowerCase().contains(
                            descriptorIterator.next().tagName.toLowerCase())) {
                        isContainIgnoreCase = true;
                    }
                }
                if (!isContainIgnoreCase) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsContainsPredicate // instanceof handles nulls
                && this.descriptor.equals(((DetailsContainsPredicate) other).descriptor)); // state check
    }

}
