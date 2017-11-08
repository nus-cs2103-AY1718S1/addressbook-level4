//@@author PokkaKiyo
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a Person has a tag with a tag name matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        String allTagNames = getStringOfAllTagNamesOfPerson(person);

        final List<String> keywordsToInclude = new ArrayList<String>();
        final List<String> keywordsToExclude = new ArrayList<String>();
        separateKeywordsToIncludeAndExclude(keywordsToInclude, keywordsToExclude);

        boolean onlyKeywordsToExcludeAreSpecified =
                checkIfOnlyKeywordsToExcludeAreSpecified(keywordsToInclude, keywordsToExclude);

        //if only tags to be excluded are specified, return all persons that do not contain the specified tag,
        //even if the person does not have any tags
        if (onlyKeywordsToExcludeAreSpecified) {
            return !(keywordsToExclude.stream()
                    .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))));
        }

        return keywordsToInclude.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))
                && !(keywordsToExclude.stream()
                .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))));
    }

    /**
     * Checks if there are only keywords to exclude but not keywords to include
     * @param keywordsToInclude list of keywords to include for finding tags
     * @param keywordsToExclude list of keywords to explicitly exclude for finding tags
     * @return true if keywordsToInclude is empty and keywordsToExclude is not empty, false otherwise
     */
    private boolean checkIfOnlyKeywordsToExcludeAreSpecified(
            List<String> keywordsToInclude, List<String> keywordsToExclude) {

        if (keywordsToInclude.isEmpty() && !keywordsToExclude.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtains and updates the appropriate list of keywords to include and exclude
     * @param keywordsToInclude list of keywords to include for search
     * @param keywordsToExclude list of keywords to explicitly exclude for search
     */
    private void separateKeywordsToIncludeAndExclude(List<String> keywordsToInclude, List<String> keywordsToExclude) {

        for (String eachKeyword : keywords) {
            if (!eachKeyword.startsWith("-")) {
                keywordsToInclude.add(eachKeyword);
            } else {
                keywordsToExclude.add(eachKeyword.substring(1));
            }
        }

    }

    private String getStringOfAllTagNamesOfPerson(ReadOnlyPerson person) {
        Set<Tag> personTags = getAllTagsOfPerson(person);

        StringBuilder allTagNames = new StringBuilder();
        for (Tag tag : personTags) {
            allTagNames.append(tag.getTagName() + " ");
        }

        return allTagNames.toString().trim();
    }

    private Set<Tag> getAllTagsOfPerson(ReadOnlyPerson person) {
        return person.getTags();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords)); // state check
    }

}
