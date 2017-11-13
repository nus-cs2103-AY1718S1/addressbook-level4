package seedu.address.model.person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindPersonDescriptor;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final FindPersonDescriptor personDescriptor;
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
        boolean[] match;
        boolean res = false;
        Set<Tag> tagSet = person.getTags();
        Set<String> tagNameSet = new HashSet<String>();
        for (Tag t : tagSet) {
            tagNameSet.add(t.tagName);
        }

        if (isInclusive) { //an AND search must all return true (no false)
            if (!nameExist && !phoneExist && !emailExist && !addressExist && !mrtExist && !tagExist) {
                return false;
            } else {
                match = testHelperIn(person, tagNameSet);
                //check for any false values
                for (int i = 0; i < 6; i++) {
                    if (!match[i]) {
                        res = true;
                    }
                }
                return !res;
            }
        } else { //an or search must have a True
            match = testHelperEx(person, tagNameSet);
            //check for any true values
            for (int i = 0; i < 6; i++) {
                if (match[i]) {
                    res = true;
                }
            }
            return res;
        }
    }

    /**
     * helper function to return boolean array for 'OR' searches
     * @param person
     * @param tagNameSet
     * @return boolean[]
     */
    private boolean[] testHelperEx(ReadOnlyPerson person, Set<String> tagNameSet) {
        //index: 0-name, 1-phone, 2-email, 3-mrt, 4-address, 5-tags
        boolean[] match = {false, false, false, false, false, false};

        match[0] = nameExist && nameKeyword.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        match[1] = phoneExist && phoneKeyword.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
        match[2] = emailExist && emailKeyword.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
        match[3] = mrtExist && mrtKeyword.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getMrt().value, keyword));
        match[4] = addressExist && addressKeyword.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
        match[5] = tagExist && tagKeyword.stream().anyMatch(keyword -> (tagNameSet.contains(keyword)));

        return match;
    }

    /**
     * helper function to return boolean array for 'AND' searches
     * @param person
     * @param tagNameSet
     * @return boolean[]
     */
    private boolean[] testHelperIn(ReadOnlyPerson person, Set<String> tagNameSet) {
        //index: 0-name, 1-phone, 2-email, 3-mrt, 4-address, 5-tags
        boolean[] match = {true, true, true, true, true, true};

        match[0] = !nameExist || nameKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        match[1] = !phoneExist || phoneKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
        match[2] = !emailExist || emailKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
        match[3] = !mrtExist || mrtKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getMrt().value, keyword));
        match[4] = !addressExist || addressKeyword.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
        match[5] = !tagExist || tagKeyword.stream().anyMatch(keyword -> (tagNameSet.contains(keyword)));

        return match;
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
