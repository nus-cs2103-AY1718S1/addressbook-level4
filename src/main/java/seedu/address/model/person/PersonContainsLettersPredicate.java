package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Predicate;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class PersonContainsLettersPredicate implements Predicate<ReadOnlyPerson> {
    private final HashMap<String, String> keywords;

    public PersonContainsLettersPredicate(HashMap<String, String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson readOnlyPerson) {
        boolean result = true;
        if (keywords.containsKey(PREFIX_NAME.toString())) {
            String keyword = keywords.get(PREFIX_NAME.toString());
            String name = readOnlyPerson.getName().fullName;
            result = name.toLowerCase().contains(keyword.toLowerCase());
        }

        if (keywords.containsKey(PREFIX_PHONE.toString())) {
            String keyword = keywords.get(PREFIX_PHONE.toString());
            String phone = readOnlyPerson.getPhone().toString();
            result = result && phone.toLowerCase().contains(keyword.toLowerCase());
        }

        if (keywords.containsKey(PREFIX_EMAIL.toString())) {
            String keyword = keywords.get(PREFIX_EMAIL.toString());
            String email = readOnlyPerson.getEmail().toString();
            result = result && email.toLowerCase().contains(keyword.toLowerCase());
        }

        if (keywords.containsKey(PREFIX_ADDRESS.toString())) {
            String keyword = keywords.get(PREFIX_ADDRESS.toString());
            String address = readOnlyPerson.getAddress().toString();
            result = result && address.toLowerCase().contains(keyword.toLowerCase());
        }

        if (keywords.containsKey(PREFIX_TAG.toString())) {
            String keyword = keywords.get(PREFIX_TAG.toString());
            Set<Tag> tagSet = readOnlyPerson.getTags();
            result = result && tagSet.stream().anyMatch(tag -> tag.toString().toLowerCase().
                                                            contains(keyword.toLowerCase()));
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsLettersPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsLettersPredicate) other).keywords)); // state check
    }
}
