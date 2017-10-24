package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.LessonCardHandle;
import guitests.guihandles.LessonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(LessonCardHandle expectedCard, LessonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getCode(), actualCard.getCode());
        assertEquals(expectedCard.getClassType(), actualCard.getClassType());
        assertEquals(expectedCard.getGroup(), actualCard.getGroup());
        assertEquals(expectedCard.getLocation(), actualCard.getLocation());
        assertEquals(expectedCard.getTimeSlot(), actualCard.getTimeSlot());
        assertEquals(expectedCard.getLecturers(), actualCard.getLecturers());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedLesson}.
     */
    public static void assertCardDisplaysLesson(ReadOnlyLesson expectedLesson, LessonCardHandle actualCard) {
        assertEquals(expectedLesson.getCode().fullCodeName, actualCard.getCode());
        assertEquals(expectedLesson.getClassType().value, actualCard.getClassType());
        assertEquals(expectedLesson.getLocation().value, actualCard.getLocation());
        assertEquals(expectedLesson.getGroup().value, actualCard.getGroup());
        assertEquals(expectedLesson.getTimeSlot().value, actualCard.getTimeSlot());
        assertEquals(expectedLesson.getLecturers().stream().map(lecturer -> lecturer.lecturerName)
                        .collect(Collectors.toList()), actualCard.getLecturers());
    }

    /**
     * Asserts that the list in {@code lessonListPanelHandle} displays the details of {@code lessons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(LessonListPanelHandle lessonListPanelHandle, ReadOnlyLesson... lessons) {
        for (int i = 0; i < lessons.length; i++) {
            assertCardDisplaysLesson(lessons[i], lessonListPanelHandle.getLessonListCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code lessonListPanelHandle} displays the details of {@code lessons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(LessonListPanelHandle lessonListPanelHandle, List<ReadOnlyLesson> lessons) {
        assertListMatching(lessonListPanelHandle, lessons.toArray(new ReadOnlyLesson[0]));
    }

    /**
     * Asserts the size of the list in {@code lessonListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(LessonListPanelHandle lessonListPanelHandle, int size) {
        int numberOfLessons = lessonListPanelHandle.getListSize();
        assertEquals(size, numberOfLessons);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
