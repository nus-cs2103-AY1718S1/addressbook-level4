package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GraphPanelHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.chart.XYChart;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nahtanojmil
public class GraphPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private GraphPanel graphPanel;
    private GraphPanelHandle graphPanelHandle;


    @Before
    public void setUp() {
        try {
            Model model = new ModelManager();
            Logic logic = new LogicManager(model);
            guiRobot.interact(() -> graphPanel = new GraphPanel(logic));
            uiPartRule.setUiPart(graphPanel);
            graphPanelHandle = new GraphPanelHandle(graphPanel.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Test
    public void display() throws Exception {
        // select ALICE
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));;
        XYChart.Series<String, Double> testSeries = new XYChart.Series<>();
        for (ReadOnlyPerson people : TYPICAL_PERSONS) {
            if (ALICE.getFormClass().equals(people.getFormClass())) {
                testSeries.getData().add(new XYChart.Data<>(people.getName().toString(),
                        Double.parseDouble(people.getGrades().toString())));
            }
        }
        assertEquals(testSeries.getData().get(0).getXValue(), TYPICAL_PERSONS.get(0).getName().toString());

        //select BOB
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertFalse(BOB.getFormClass().equals(TYPICAL_PERSONS.get(0).getFormClass()));
    }
}
