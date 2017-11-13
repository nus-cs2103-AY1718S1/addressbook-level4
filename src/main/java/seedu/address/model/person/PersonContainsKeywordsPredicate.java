//@@author tbhbhbh
package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson} matches any or all of the keywords given depending on the number of keywords.
 * Case 1: Given only 1 keyword, {@code ReadOnlyPerson}'s {@code Name} OR {@code Tag} must match the keyword
 * Case 2: Given >1 keywords, {@code ReadOnlyPerson}'s {@code Name} AND {@code Tag} must match the keywords
 * More to be added later including Birthday
 */
public class PersonContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;
    private List<String> keywordsLower;
    private List<String> personTagsLower;
    private String[] nameArr;
    private List<Tag> tagList = new ArrayList<>();
    private List<String> masterList = new ArrayList<>();

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        keywordsLower = new ArrayList<>();
        personTagsLower = new ArrayList<>();
        setUpCaseInsensitiveKeywords();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {

        clearMasterAndTagLists();
        setUpMasterList(person);

        if (keywords.size() == 1) {

            /* Case 1: keyword is a character (aka initial)
             * searches for people with names (Both first and last names) that start with the character
             */
            if (keywordIsSingleCharacter()) {
                return findNamesWithFirstChar(keywordsLower.toString().charAt(1));
            }

            /* Case 2: keyword can either be a name or a tag
             * searches for the keyword in the person's name or tags
             */
            return keywordsLower.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                    || personTagsLower.contains(keywordsLower.get(0));
        }

        /* Case 3: more than 1 keyword
         * Only supports 1 name, but multiple tags
         * a) name + tag
         * b) tag + tag + tag...
         */
        return masterList.containsAll(keywordsLower);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

    private boolean findNamesWithFirstChar(char firstChar) {
        return (nameStartsWith(firstChar));
    }

    /**
     * This method returns true if the keyword given in the command is a single character
     */
    private boolean keywordIsSingleCharacter() {
        if (keywords.toString().length() == 3) {
            return true;
        }
        return false;
    }

    /**
     * This method returns true if any part of a person's name begins with the given character
     */
    private boolean nameStartsWith(char character) {
        for (String name : nameArr) {
            if (name.toLowerCase().charAt(0) == character) {
                return true;
            }
        }
        return false;
    }

    private void clearMasterAndTagLists() {
        masterList.clear();
        tagList.clear();
    }

    private void setUpCaseInsensitiveKeywords() {
        keywordsLower.clear();
        for (int i = 0; i < keywords.size(); i++) {
            keywordsLower.add(keywords.get(i).toLowerCase());
        }
    }

    private void setUpTagList(ReadOnlyPerson person) {
        tagList.clear();
        personTagsLower.clear();
        tagList.addAll(person.getTags());
        for (int i = 0; i < tagList.size(); i++) {
            String tagNameToAdd = tagList.get(i).toString().substring(1, tagList.get(i).toString().length() - 1);
            personTagsLower.add(tagNameToAdd.toLowerCase());
            masterList.add(tagNameToAdd.toLowerCase());
        }
    }

    private void setUpNameList(ReadOnlyPerson person) {
        nameArr = person.getName().toString().split("\\s+");
        for (int i = 0; i < nameArr.length; i++) {
            masterList.add(nameArr[i].toLowerCase());
        }
    }

    private void setUpMasterList(ReadOnlyPerson person) {
        setUpTagList(person);
        setUpNameList(person);
    }
}
