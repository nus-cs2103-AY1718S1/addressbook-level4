package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertPanelDisplaysStatistics;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StatisticsPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.model.FilteredPersonListChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author lincredibleJC
public class StatisticsPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private StatisticsPanel statisticsPanel;
    private StatisticsPanelHandle statisticsPanelHandle;

    @Before
    public void setUp() throws Exception {
        try {
            guiRobot.interact(() -> statisticsPanel = new StatisticsPanel(TYPICAL_PERSONS));
            uiPartRule.setUiPart(statisticsPanel);
            statisticsPanelHandle = new StatisticsPanelHandle(statisticsPanel.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Test
    public void display() throws Exception {
        guiRobot.pauseForHuman();
        postNow(new FilteredPersonListChangedEvent(TYPICAL_PERSONS));
        assertPanelDisplaysStatistics(TYPICAL_PERSONS, statisticsPanelHandle);
    }

}
