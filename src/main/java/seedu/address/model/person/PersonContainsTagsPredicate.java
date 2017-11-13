//@@author PokkaKiyo
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a Person has a tag with a tag name matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private static final Logger logger = LogsCenter.getLogger(PersonContainsTagsPredicate.class);

    private final List<String> keywords;
    private final List<String> keywordsToInclude;
    private final List<String> keywordsToExclude;

    public PersonContainsTagsPredicate(List<String> keywords) {
        logger.info("------Creating PersonContainsTagsPredicate");
        this.keywords = keywords;
        keywordsToInclude = new ArrayList<String>();
        keywordsToExclude = new ArrayList<String>();
        separateKeywordsToIncludeAndExclude(keywordsToInclude, keywordsToExclude);
        logger.info("-------Number of keywords to include: " + keywordsToInclude.size() / 2);
        logger.info("-------Number of keywords to exclude: " + keywordsToExclude.size() / 2);

    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String allTagNames = getStringOfAllTagNamesOfPerson(person);

        assert !(keywordsToExclude.isEmpty() && keywordsToInclude.isEmpty()) : "at least one keyword must be specified";

        boolean onlyKeywordsToExcludeAreSpecified =
                checkIfOnlyKeywordsToExcludeAreSpecified(keywordsToInclude, keywordsToExclude);

        //Return all persons that do not contain the specified tag if only exclusion tags are specified
        if (onlyKeywordsToExcludeAreSpecified) {
            return !(keywordsToExclude.stream()
                    .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagNames, keyword))));
        }

        assert !keywordsToInclude.isEmpty() : "there must be at least one keyword to include here";

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
            assert !keywordsToInclude.isEmpty() : "there must be at least one keyword to include here";
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
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords) //state check
                && this.keywordsToInclude.equals(((PersonContainsTagsPredicate) other).keywordsToInclude)
                && this.keywordsToExclude.equals(((PersonContainsTagsPredicate) other).keywordsToExclude));
    }

}
