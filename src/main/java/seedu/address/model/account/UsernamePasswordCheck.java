//@@author cqhchan
package seedu.address.model.account;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 *
 */
public class UsernamePasswordCheck  implements Predicate<ReadOnlyAccount> {

    private final List<String> keywords;

    public UsernamePasswordCheck(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyAccount account) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(account.getUsername().fullName, keyword));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UsernamePasswordCheck // instanceof handles nulls
                && this.keywords.equals(((UsernamePasswordCheck) other).keywords)); // state check
    }

}
