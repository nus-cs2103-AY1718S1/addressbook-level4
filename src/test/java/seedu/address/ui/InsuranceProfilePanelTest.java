package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalInsurances.COMMON_INSURANCE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InsuranceProfilePanelHandle;
import seedu.address.commons.events.ui.InsurancePanelSelectionChangedEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.person.ReadOnlyPerson;

//@@author RSJunior37
public class InsuranceProfilePanelTest extends GuiUnitTest {
    private InsurancePanelSelectionChangedEvent insuranceClickedEventStub;

    private InsuranceProfilePanel insuranceProfilePanel;
    private InsuranceProfilePanelHandle insuranceProfilePanelHandle;

    @Before
    public void setUp() {
        ReadOnlyInsurance insurance = COMMON_INSURANCE;
        insuranceClickedEventStub = new InsurancePanelSelectionChangedEvent(insurance);

        guiRobot.interact(() -> insuranceProfilePanel = new InsuranceProfilePanel());
        uiPartRule.setUiPart(insuranceProfilePanel);

        insuranceProfilePanelHandle = new InsuranceProfilePanelHandle(insuranceProfilePanel.getRoot());

    }

    @Test
    public void display() throws Exception {

        assertNull(insuranceProfilePanelHandle.getOwner());
        assertNull(insuranceProfilePanelHandle.getBeneficiary());
        assertNull(insuranceProfilePanelHandle.getInsured());
        assertNull(insuranceProfilePanelHandle.getPremium());
        assertNull(insuranceProfilePanelHandle.getContractPathId());
        assertNull(insuranceProfilePanelHandle.getSigningDateId());
        assertNull(insuranceProfilePanelHandle.getExpiryDateId());

        // select Stub Person
        postNow(insuranceClickedEventStub);

        ReadOnlyPerson expectedOwner = ALICE;
        ReadOnlyPerson expectedBeneficiary = BENSON;
        ReadOnlyPerson expectedInsured = CARL;

        Double expectedPremiumDouble = 123.45;
        String expectedPremium = new String("S$ " + String.format("%.2f", expectedPremiumDouble));
        String expectedContractPath = "common.pdf";
        String expectedSigningDate = "01 Jan 2017";
        String expectedExpiryDate = "31 Dec 2020";

        assertEquals(expectedOwner.getName().toString(), insuranceProfilePanelHandle.getOwner());
        assertEquals(expectedBeneficiary.getName().toString(), insuranceProfilePanelHandle.getBeneficiary());
        assertEquals(expectedInsured.getName().toString(), insuranceProfilePanelHandle.getInsured());
        assertEquals(expectedContractPath, insuranceProfilePanelHandle.getContractPathId());
        assertEquals(expectedPremium, insuranceProfilePanelHandle.getPremium());
        assertEquals(expectedSigningDate, insuranceProfilePanelHandle.getSigningDateId());
        assertEquals(expectedExpiryDate, insuranceProfilePanelHandle.getExpiryDateId());
    }
}

