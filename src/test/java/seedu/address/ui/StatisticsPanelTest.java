package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.StatisticsPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.FilteredListChangedEvent;
import seedu.address.logic.statistics.Statistics;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

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

        // findtags friends
        ObservableList<ReadOnlyPerson> actualList =
                TYPICAL_PERSONS.filtered(new TagsContainsKeywordsPredicate(Arrays.asList("friends")));
        postNow(new FilteredListChangedEvent(actualList));
        assertStatisticsPanelDisplay(actualList, statisticsPanelHandle);
    }

    //======================== Helper methods ===============================

    /**
     * Asserts that {@code statisticsPanel} displays the statistics correctly
     */
    private void assertStatisticsPanelDisplay(ObservableList<ReadOnlyPerson> actualList,
                                              StatisticsPanelHandle statisticsPanelHandle) {
        Statistics expectedStatistics = new Statistics(actualList);
        guiRobot.pauseForHuman();
        assertEquals(expectedStatistics.getMeanString(), statisticsPanelHandle.getMeanLabel());
        assertEquals(expectedStatistics.getMedianString(), statisticsPanelHandle.getMedianLabel());
        assertEquals(expectedStatistics.getModeString(), statisticsPanelHandle.getModeLabel());
        assertEquals(expectedStatistics.getVarianceString(), statisticsPanelHandle.getVarianceLabel());
        assertEquals(expectedStatistics.getStdDevString(), statisticsPanelHandle.getStandardDeviationLabel());
        assertEquals(expectedStatistics.getQuartile3String(), statisticsPanelHandle.getQuartile3Label());
        assertEquals(expectedStatistics.getQuartile1String(), statisticsPanelHandle.getQuartile1Label());
        assertEquals(expectedStatistics.getInterquartileRangeString(), statisticsPanelHandle.getInterquartileLabel());
    }

}
