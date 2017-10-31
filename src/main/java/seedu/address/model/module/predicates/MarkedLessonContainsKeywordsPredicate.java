package seedu.address.model.module.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;

//@@author angtianlannus
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class MarkedLessonContainsKeywordsPredicate implements Predicate<ReadOnlyLesson> {
    private final List<String> keywords;
    private ArrayList<ReadOnlyLesson> duplicateLessons = new ArrayList<>();

    public MarkedLessonContainsKeywordsPredicate(List<String> keywords) {

        this.keywords = keywords;

    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        if (!lesson.isMarked()) {
            return false;
        }

        for (int i = 0; i < keywords.size(); i++) {

            if (lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase())) {

                if (duplicateLessons.contains(lesson)) {
                    return false;
                } else {
                    duplicateLessons.add(lesson);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkedLessonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((MarkedLessonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
