//@@author yanji1221
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ComingBirthdayListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class ComingBirthdayListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private ComingBirthdayListPanelHandle comingBirthdayListPanelHandle;

    private boolean isMoreThanOne = false;

    @Before
    public void setUp() {
        ComingBirthdayListPanel comingBirthdayListPanel = new ComingBirthdayListPanel(TYPICAL_PERSONS);
        uiPartRule.setUiPart(comingBirthdayListPanel);

        comingBirthdayListPanelHandle = new ComingBirthdayListPanelHandle(getChildNode(comingBirthdayListPanel
                        .getRoot(),
                ComingBirthdayListPanelHandle.COMING_BIRTHDAY_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        List<ReadOnlyPerson> comingBirthdayList = TYPICAL_PERSONS.stream().collect(Collectors.toList());
        boolean isRemoved = false;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);

        if (((month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) && date == 31)
                || ((month == 4 || month == 6 || month == 9
                || month == 11) && date == 30)
                || (month == 2 && date == 29)) {
            month += 1;
            if (month == 13) {
                month = 1;
            }
            date = 0;
        }
        for (int i = 0; i < comingBirthdayList.size(); i++) {
            if (!(Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(5, 7)) == month)) {
                comingBirthdayList.remove(i);
                isRemoved = true;
            } else if ((Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(5, 7)) == month)
                    && Integer.parseInt(comingBirthdayList.get(i).getBirthday().toString()
                    .substring(8)) < date) {
                comingBirthdayList.remove(i);
                isRemoved = true;
            }
            if (isRemoved) {
                i--;
                isRemoved = false;
            }
        }

        if (comingBirthdayList.size() > 1) {
            isMoreThanOne = true;
        }

        for (int i = 0; i < comingBirthdayList.size(); i++) {
            comingBirthdayListPanelHandle.navigateToCard(comingBirthdayList.get(i));
            ReadOnlyPerson expectedPerson = comingBirthdayList.get(i);
            PersonCardHandle actualCard = comingBirthdayListPanelHandle.getPersonCardHandle(i);

            assertCardDisplaysPerson(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        if (isMoreThanOne) {
            PersonCardHandle expectedCard = comingBirthdayListPanelHandle
                    .getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
            PersonCardHandle selectedCard = comingBirthdayListPanelHandle.getHandleToSelectedCard();
            assertCardEquals(expectedCard, selectedCard);
        }
    }
}
