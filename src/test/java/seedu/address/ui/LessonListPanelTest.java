package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LESSON;
import static seedu.address.testutil.TypicalLessons.getTypicalLessons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysLesson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.LessonCardHandle;
import guitests.guihandles.LessonListPanelHandle;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.module.ReadOnlyLesson;

public class LessonListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyLesson> TYPICAL_LESSONS =
            FXCollections.observableList(getTypicalLessons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_LESSON);

    private LessonListPanelHandle lessonListPanelHandle;

    @Before
    public void setUp() {
        LessonListPanel lessonListPanel = new LessonListPanel(TYPICAL_LESSONS);
        uiPartRule.setUiPart(lessonListPanel);

        lessonListPanelHandle = new LessonListPanelHandle(getChildNode(lessonListPanel.getRoot(),
                LessonListPanelHandle.LESSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_LESSONS.size(); i++) {
            lessonListPanelHandle.navigateToCard(TYPICAL_LESSONS.get(i));
            ReadOnlyLesson expectedLesson = TYPICAL_LESSONS.get(i);
            LessonCardHandle actualCard = lessonListPanelHandle.getLessonListCardHandle(i);

            assertCardDisplaysLesson(expectedLesson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        LessonCardHandle expectedCard =
                lessonListPanelHandle.getLessonListCardHandle(INDEX_SECOND_LESSON.getZeroBased());
        LessonCardHandle selectedCard = lessonListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
