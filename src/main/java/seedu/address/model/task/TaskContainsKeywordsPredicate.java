package seedu.address.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ParserUtil;

/**
 * Tests that a {@code ReadOnlyTask}'s {@code Description} and/or {@code Deadline} matches any of the keywords given.
 */
public class TaskContainsKeywordsPredicate implements Predicate<ReadOnlyTask> {
    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyTask task) {
        String testDate = "";
        try {
            Date date = ParserUtil.parseDate(task.getDeadline().date);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            testDate = dateFormat.format(date);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        String finalTestDate = testDate;
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().taskDescription, keyword)
                || StringUtil.containsWordIgnoreCase(finalTestDate, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.task
                .TaskContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((seedu.address.model.task.TaskContainsKeywordsPredicate) other)
                .keywords)); // state check
    }
}
