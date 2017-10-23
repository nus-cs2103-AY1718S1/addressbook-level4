package seedu.address.model.module.predicates;

import seedu.address.model.ListingUnit;
import seedu.address.model.module.ReadOnlyLesson;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone Number} matches any of the keywords given.
 */
public class LessonContainsKeywordsPredicate extends FindPredicate {
    private final List<String> keywords;
    private final List<String> oldKeywords;
    private ArrayList<ReadOnlyLesson> filteredLesson;
    private ReadOnlyLesson currentViewingLesson;
    private ArrayList<String> inforList;

    public LessonContainsKeywordsPredicate(List<String> keywords, List<String> oldKeyword, ReadOnlyLesson lesson) {

        this.keywords = keywords;
        this.oldKeywords = oldKeyword;
        this.currentViewingLesson = lesson;
        filteredLesson = new ArrayList<ReadOnlyLesson>();
        inforList = new ArrayList<>();

    }

    public void initializeInforList(ReadOnlyLesson lesson) {
        inforList.add(lesson.getCode().fullCodeName);
        inforList.add(lesson.getLocation().value);
        inforList.add(lesson.getTimeSlot().value);
        inforList.add(lesson.getGroup().value);
        inforList.add(lesson.getClassType().value);
    }

    public List<String> getKeywords() {
        return keywords;
    }

    void filterOldKeyWords(ReadOnlyLesson lesson) {

        initializeInforList(lesson);
        boolean valid = true;

        for (int i = 0; i < oldKeywords.size(); i++) {

            if (!inforList.contains(oldKeywords.get(i))) {
                valid = false;
                break;
            }
        }

        if (valid)
            filteredLesson.add(lesson);

    }

    @Override
    public boolean test(ReadOnlyLesson lesson) {

        if (!oldKeywords.isEmpty()) {

            filterOldKeyWords(lesson);


            for (int i = 0; i < keywords.size(); i++) {

                switch (ListingUnit.getCurrentListingUnit()) {
                case LESSON_MODULE:
                case MODULE:
                    if ((lesson.getCode().equals(currentViewingLesson.getCode())) && filteredLesson.contains(lesson)
                            && (lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase()))) {

                        return true;

                    }
                case LESSON_LOCATION:
                    if (lesson.getLocation().equals(currentViewingLesson.getLocation()) && filteredLesson.contains(lesson)
                            && (lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase()))) {

                        return true;
                    }

                }

            }

        } else {

            for (int i = 0; i < keywords.size(); i++) {

                switch (ListingUnit.getCurrentListingUnit()) {
                case LESSON_MODULE:
                case MODULE:
                    if ((lesson.getCode().equals(currentViewingLesson.getCode()))
                            && (lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getLocation().value.toLowerCase().contains(keywords.get(i).toLowerCase()))) {

                        return true;

                    }
                case LESSON_LOCATION:
                    if (lesson.getLocation().equals(currentViewingLesson.getLocation())
                            && (lesson.getTimeSlot().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getClassType().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getGroup().value.toLowerCase().contains(keywords.get(i).toLowerCase())
                            || lesson.getCode().fullCodeName.toLowerCase().contains(keywords.get(i).toLowerCase()))) {

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
