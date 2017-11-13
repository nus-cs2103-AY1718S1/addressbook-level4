package seedu.address.model.person;

//@@author wangyiming1019
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the tags given.
 */
public class NameContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> tags;

    public NameContainsTagsPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String allTagString = convertTagToString(person);
        final List<String> wantedTag = new ArrayList<>();
        final List<String> unwantedTag = new ArrayList<>();
        updateWantedTagUnwantedTag(wantedTag, unwantedTag);
        boolean isOnlyUnwantedTags = isOnlyUnwantedTagsCheck(wantedTag, unwantedTag);

        if (isOnlyUnwantedTags) {
            return !(unwantedTag.stream()
                    .anyMatch((inputTag -> StringUtil.containsWordIgnoreCase(allTagString, inputTag))));
        }

        return wantedTag.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(allTagString, keyword))
                && !(unwantedTag.stream()
                .anyMatch((keyword -> StringUtil.containsWordIgnoreCase(allTagString, keyword))));
    }

    /**
     * check only unwanted tag list has elements
     * @return a boolean value
     */

    private boolean isOnlyUnwantedTagsCheck(List<String> wantedTag,
                                            List<String> unwantedTag) {
        if (wantedTag.isEmpty() && !unwantedTag.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update the wantedTag and unwantedTag list
     * @param wantedTag list of tags to be searched
     * @param unwantedTag list of tags to not be searched
     */

    private void updateWantedTagUnwantedTag(List<String> wantedTag, List<String> unwantedTag) {
        for (String everyTag : tags) {
            if (!everyTag.startsWith("/")) {
                wantedTag.add(everyTag);
            } else {
                unwantedTag.add(everyTag.substring(1));
            }
        }

    }

    /**
     * Convert a set of tags to Strings
     */

    private String convertTagToString(ReadOnlyPerson person) {
        Set<Tag> personTags = person.getTags();
        StringBuilder allTagNames = new StringBuilder();
        for (Tag tag : personTags) {
            allTagNames.append(tag.getTagName());
            allTagNames.append(" ");
        }
        return allTagNames.toString().trim();
    }

    //@@author jeffreygohkw
    public List<String> getTags() {
        return tags;
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsTagsPredicate // instanceof handles nulls
                && this.tags.equals(((NameContainsTagsPredicate) other).tags)); // state check
    }

}
