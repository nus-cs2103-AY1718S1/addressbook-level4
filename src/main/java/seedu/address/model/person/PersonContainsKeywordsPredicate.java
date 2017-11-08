package seedu.address.model.person;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindPersonDescriptor;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    FindPersonDescriptor personDescriptor;
    private final boolean isInclusive;

    private final boolean nameExist;
    private final boolean phoneExist;
    private final boolean emailExist;
    private final boolean mrtExist;
    private final boolean addressExist;
    private final boolean tagExist;

    private final List<String> nameKeyword;
    private final List<String> phoneKeyword;
    private final List<String> emailKeyword;
    private final List<String> mrtKeyword;
    private final List<String> addressKeyword;
    private final List<String> tagKeyword;

    public PersonContainsKeywordsPredicate(boolean isInclusive, FindPersonDescriptor personDescriptor) {
        this.personDescriptor = personDescriptor;
        this.isInclusive = isInclusive;

        this.nameExist = personDescriptor.getName().isPresent();
        this.phoneExist = personDescriptor.getPhone().isPresent();
        this.emailExist = personDescriptor.getEmail().isPresent();
        this.mrtExist = personDescriptor.getMrt().isPresent();
        this.addressExist = personDescriptor.getAddress().isPresent();
        this.tagExist = personDescriptor.getTags().isPresent();

        this.nameKeyword = nameExist ? personDescriptor.getName().get() : null;
        this.phoneKeyword = phoneExist ? personDescriptor.getPhone().get() : null;
        this.emailKeyword = emailExist ? personDescriptor.getEmail().get() : null;
        this.mrtKeyword = mrtExist ? personDescriptor.getMrt().get() : null;
        this.addressKeyword = addressExist ? personDescriptor.getAddress().get() : null;
        this.tagKeyword = tagExist ? personDescriptor.getTags().get() : null;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        List<Boolean> lst = testHelper(person);

        if (isInclusive) { //an AND search
            return !lst.contains(false);
        } else { //an or search
            return lst.contains(true);
        }
    }

    private List<Boolean> testHelper(ReadOnlyPerson person) {
        //index: 0-name, 1-phone, 2-email, 3-mrt, 4-address, 5-tags
        boolean[] match = {false, false, false, false, false, false};

        if (nameExist) {
            match[0] = nameKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        }
        if (phoneExist) {
            match[1] = phoneKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
        }
        if (emailExist) {
            match[2] = emailKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
        }
        if (mrtExist) {
            match[3] = mrtKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getMrt().value, keyword));
        }
        if (addressExist) {
            match[4] = addressKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
        }
        if (tagExist) {
            Set<Tag> tagSet = person.getTags();
            Set<String> tagNameSet = new HashSet<String>();
            for (Tag t : tagSet) {
                tagNameSet.add(t.tagName);
            }
            match[5] = tagKeyword.stream().anyMatch(keyword -> (tagNameSet.contains(keyword)));
        }

        List lst = Arrays.asList(match);
        return lst;
    }

    public FindPersonDescriptor getPersonDescriptor() {
        return personDescriptor;
    }

    public boolean getIsInclusive() {
        return isInclusive;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.isInclusive == (((PersonContainsKeywordsPredicate) other).getIsInclusive()) // state check
                && this.personDescriptor.equals(((PersonContainsKeywordsPredicate) other).getPersonDescriptor()));
    }
}