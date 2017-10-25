package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class FindCommandPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public FindCommandPredicate(List<String> keywords) {
        
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean name = keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        
        boolean number = keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
        
        boolean address = keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().toString(), keyword));
        
        boolean email = keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().toString(), keyword));
        
        if (name || number || address || email) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommandPredicate // instanceof handles nulls
            && this.keywords.equals(((FindCommandPredicate) other).keywords)); // state check
    }

}
