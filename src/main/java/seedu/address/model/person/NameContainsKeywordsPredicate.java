package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String flag;
        Boolean isSelected=keywords.stream().anyMatch( keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        if(isSelected==true) return isSelected;
        for(String keyword: keywords){
            if(keyword.length()>=2 && keyword.substring(0, 2).equals("t/")) {
                String tagName= "[" + keyword.substring(2) + "]";
                for(Tag tag: person.getTags()){
                    if(tag.toString().equals(tagName)) isSelected=true;
                }
            }
        }
        return isSelected;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

    public String getSelectedTag(){
        if(keywords.get(0).substring(0,2).equals("t/"))
            return "[" + keywords.get(0).substring(2) + "]";
        else return null;
    }
}
