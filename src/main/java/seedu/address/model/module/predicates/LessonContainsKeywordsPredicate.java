package seedu.address.model.module.predicates;

import seedu.address.model.module.Lesson;
import seedu.address.model.module.ReadOnlyLesson;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class LessonContainsKeywordsPredicate implements Predicate<ReadOnlyLesson> {
    private final List<String> keywords;
    private ArrayList<ReadOnlyLesson> duplicateLesson = new ArrayList<ReadOnlyLesson>();

    public LessonContainsKeywordsPredicate(List<String> keywords) {

        this.keywords = keywords;
        duplicateLesson.clear();

    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        for (int i = 0; i < keywords.size(); i++) {
            if (lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                    || lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase())){

                if (duplicateLesson.contains(lesson)) {
                    return false;
                } else {
                    duplicateLesson.add(lesson);
                    return true;
                }

            }
        }
        return false;

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((LessonContainsKeywordsPredicate) other).keywords)); // state check
    }

}
