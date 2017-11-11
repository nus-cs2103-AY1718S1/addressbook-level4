package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InsuranceProfilePanelHandle;
import guitests.guihandles.ProfilePanelHandle;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.model.insurance.InsurancePerson;
import seedu.address.model.person.ReadOnlyPerson;

//@@author RSJunior37
public class InsuranceProfilePanelTest extends GuiUnitTest {
    private PersonNameClickedEvent personNameClickedEventStub;

    private InsuranceProfilePanel insuranceProfilePanel;
    private InsuranceProfilePanelHandle insuranceProfilePanelHandle;

    @Before
    public void setUp() {
        InsurancePerson insurancePerson = new InsurancePerson(ALICE);
        personNameClickedEventStub = new PersonNameClickedEvent(insurancePerson);

        guiRobot.interact(() -> insuranceProfilePanel = new InsuranceProfilePanel());
        uiPartRule.setUiPart(insuranceProfilePanel);

        insuranceProfilePanelHandle = new InsuranceProfilePanelHandle(insuranceProfilePanel.getRoot());

    }

    @Test
    public void display() throws Exception {
        // default profile page
        final String expectedDefaultName = ProfilePanel.DEFAULT_MESSAGE;

        assertNull(insuranceProfilePanelHandle.getOwner());
        assertNull(insuranceProfilePanelHandle.getBeneficiary());
        assertNull(insuranceProfilePanelHandle.getBeneficiary());
        assertNull(insuranceProfilePanelHandle.getContractPathId());
        assertNull(insuranceProfilePanelHandle.getSigningDateId());
        assertNull(insuranceProfilePanelHandle.getExpiryDateId());

        // select Stub Person
        postNow(personNameClickedEventStub);

        ReadOnlyPerson expectedSelectedPerson = ALICE;

    }
}
