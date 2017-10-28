package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.mod.Mod;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class ModContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ModContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        List<Mod> mods = new ArrayList<>(person.getMods());
        for (Mod mod : mods) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(mod.modName, keyword))) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModContainsKeywordPredicate // instanceof handles nulls
                && this.keywords.equals(((ModContainsKeywordPredicate) other).keywords)); // state check
    }
}
