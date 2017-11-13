// @@author tanchc
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.module.Module;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Module} matches any of the keywords given.
 */
public class ModuleContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ModuleContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        List<Module> modules = new ArrayList<>(person.getModules());
        for (Module m : modules) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(m.moduleName, keyword))) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleContainsKeywordPredicate // instanceof handles nulls
                && this.keywords.equals(((ModuleContainsKeywordPredicate) other).keywords)); // state check
    }
}
