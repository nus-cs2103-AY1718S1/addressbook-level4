package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * A utility class for Lesson.
 */
public class LessonUtil {

    /**
     * Returns an add command string for adding the {@code lesson}.
     */
    public static String getAddCommand(ReadOnlyLesson lesson) {
        return AddCommand.COMMAND_WORD + " " + getLessonDetails(lesson);
    }

    /**
     * Returns the part of command string for the given {@code lesson}'s details.
     */
    public static String getLessonDetails(ReadOnlyLesson lesson) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE_CODE + lesson.getCode().fullCodeName + " ");
        sb.append(PREFIX_CLASS_TYPE + lesson.getClassType().value + " ");
        sb.append(PREFIX_VENUE + lesson.getLocation().value + " ");
        sb.append(PREFIX_GROUP + lesson.getGroup().value + " ");
        sb.append(PREFIX_TIME_SLOT + lesson.getTimeSlot().value + " ");
        lesson.getLecturers().stream().forEach(
            s -> sb.append(PREFIX_LECTURER + s.lecturerName + " ")
        );
        return sb.toString();
    }
}
