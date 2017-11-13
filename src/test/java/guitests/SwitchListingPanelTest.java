package guitests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class SwitchListingPanelTest extends AddressBookGuiTest {
    @Test
    public void switchListingPanel_fromPersonListingToEventListing_checkCorrectness() {
        // By default, person listing should be loaded at the initial stage.
        assertNotNull(getPersonListPanel());
        assertNull(getEventListPanel());
    }
}
