package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalLessons.MA1101R_L1;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysLesson;

import org.junit.Test;

import guitests.guihandles.LessonCardHandle;

import seedu.address.model.module.Lesson;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.testutil.LessonBuilder;

public class LessonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        // Lesson lessonWithNoLecturers = new LessonBuilder().withLecturers(new String[0]).build();
        // LessonListCard lessonListCard = new LessonListCard(lessonWithNoLecturers, 1);
        // uiPartRule.setUiPart(lessonListCard);
        // assertCardDisplay(lessonListCard, lessonWithNoLecturers, 1);

        // with tags
        Lesson lessonWithLecturers = new LessonBuilder().build();
        LessonListCard lessonListCard = new LessonListCard(lessonWithLecturers, 2);
        uiPartRule.setUiPart(lessonListCard);
        assertCardDisplay(lessonListCard, lessonWithLecturers, 2);

        // changes made to Lesson reflects on card
        guiRobot.interact(() -> {
            lessonWithLecturers.setCodeType(MA1101R_L1.getCode());
            lessonWithLecturers.setClassType(MA1101R_L1.getClassType());
            lessonWithLecturers.setLocation(MA1101R_L1.getLocation());
            lessonWithLecturers.setGroupType(MA1101R_L1.getGroup());
            lessonWithLecturers.setTimeSlot(MA1101R_L1.getTimeSlot());
            lessonWithLecturers.setLecturers(MA1101R_L1.getLecturers());
        });
        assertCardDisplay(lessonListCard, lessonWithLecturers, 2);
    }

    @Test
    public void equals() {
        Lesson lesson = new LessonBuilder().build();
        LessonListCard lessonListCard = new LessonListCard(lesson, 0);

        // same lesson, same index -> returns true
        LessonListCard copy = new LessonListCard(lesson, 0);
        assertTrue(lessonListCard.equals(copy));

        // same object -> returns true
        assertTrue(lessonListCard.equals(lessonListCard));

        // null -> returns false
        assertFalse(lessonListCard.equals(null));

        // different types -> returns false
        assertFalse(lessonListCard.equals(0));

        // different lesson, same index -> returns false
        Lesson differentLesson = new LessonBuilder().withCode("CS2101").build();
        assertFalse(lessonListCard.equals(new LessonListCard(differentLesson, 0)));

        // same lesson, different index -> returns false
        assertFalse(lessonListCard.equals(new LessonListCard(lesson, 1)));
    }

    /**
     * Asserts that {@code lessonListCard} displays the details of {@code expectedLesson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(LessonListCard lessonListCard, ReadOnlyLesson expectedLesson, int expectedId) {
        guiRobot.pauseForHuman();

        LessonCardHandle lessonCardHandle = new LessonCardHandle(lessonListCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", lessonCardHandle.getId());

        // verify lesson details are displayed correctly
        assertCardDisplaysLesson(expectedLesson, lessonCardHandle);
    }
}
