//@@author zenghou
package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that either one of {@code ReadOnlyPerson}'s {@code Name, Phone, Address, Email, Tag, Remarks} matches,
 * partially or in full, any of the keywords given.
 */
public class DetailsContainKeyphrasePredicate implements Predicate<ReadOnlyPerson> {
    public final String keyphrase;

    public DetailsContainKeyphrasePredicate(String keyphrase)  {
        this.keyphrase = keyphrase;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return personDetailsContainsKeyphrase(person, keyphrase);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsContainKeyphrasePredicate // instanceof handles nulls
                && this.keyphrase.equals(((DetailsContainKeyphrasePredicate) other).keyphrase)); // state check
    }

    /**
     * Tests if the {@code keyphrase} entered matches, partially or in full, any attribute of a {@code person}
     * @param person
     * @param keyphrase
     * @return true if any attribute of the {@code person}'s details match the keyphrase
     */
    private boolean personDetailsContainsKeyphrase(ReadOnlyPerson person, String keyphrase) {
        boolean nameContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getName().fullName, keyphrase);
        boolean emailContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getEmail().value, keyphrase);
        boolean phoneContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getPhone().value, keyphrase);
        boolean addressContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getAddress().value, keyphrase);
        boolean facebookContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getFacebook().value, keyphrase);
        boolean remarkContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getRemark().value, keyphrase);
        boolean tagContainsKeyphrase = StringUtil.caseInsensitiveContains(person.getTags().toString(), keyphrase);

        return nameContainsKeyphrase || emailContainsKeyphrase || phoneContainsKeyphrase || addressContainsKeyphrase
                || facebookContainsKeyphrase || remarkContainsKeyphrase || tagContainsKeyphrase;
    }
}
