package seedu.address.model.module.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.module.ReadOnlyLesson;

//@@author angtianlannus
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class LessonContainsKeywordsPredicate implements Predicate<ReadOnlyLesson> {
    private final List<String> keywords;
    private ArrayList<ReadOnlyLesson> duplicateLessons = new ArrayList<>();
    private ReadOnlyLesson lesson;
    private String attribute;

    public LessonContainsKeywordsPredicate(List<String> keywords, ReadOnlyLesson targetLesson, String attribute) {

        this.keywords = keywords;
        this.lesson = targetLesson;
        this.attribute = attribute;
    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        int attribute = -1; // 1 is for module, 2 is for location

        if (lesson.getCode().fullCodeName.equals(this.lesson.getCode().fullCodeName)
                && this.attribute.equals("module")) {
            attribute = 1;
        } else if (lesson.getLocation().value.equals(this.lesson.getLocation().value)
                && this.attribute.equals("location")) {
            attribute = 2;
        } else {

            return false; // not this lesson
        }

        for (int i = 0; i < keywords.size(); i++) {

            if (attribute == 1) { //search for attribute module code
                if (lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())) {

                    if (duplicateLessons.contains(lesson)) {

                        return false;
                    } else {

                        duplicateLessons.add(lesson);
                        return true;
                    }

                }
            } else if (attribute == 2) { //search for attribute location

                if (lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                        || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())) {

                    if (duplicateLessons.contains(lesson)) {
                        return false;
                    } else {
                        duplicateLessons.add(lesson);
                        return true;
                    }
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
