package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.testutil.GuiTestAssert.assertBrowserCardDisplay;
import static seedu.address.ui.testutil.GuiTestAssert.assertBrowserCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserSelectorCardHandle;
import guitests.guihandles.BrowserSettingsSelectorHandle;
import seedu.address.commons.events.ui.JumpToBrowserListRequestEvent;

//@@author fongwz
public class BrowserSettingsSelectorTest extends GuiUnitTest {
    private static final JumpToBrowserListRequestEvent JUMP_TO_LINKEDIN_EVENT =
            new JumpToBrowserListRequestEvent("linkedin");

    private BrowserSettingsSelectorHandle browserSettingsSelectorHandle;
    private SettingsSelector settingsSelector;

    @Before
    public void setUp() {
        settingsSelector = new SettingsSelector();
        uiPartRule.setUiPart(settingsSelector);

        browserSettingsSelectorHandle = new BrowserSettingsSelectorHandle(settingsSelector.getBrowserSelectorList());
    }

    @Test
    public void display() {
        for (int i = 0; i < 3; i++) {
            browserSettingsSelectorHandle.navigateToCard(settingsSelector.getBrowserItems().get(i));
            BrowserSelectorCard expectedBrowser = settingsSelector.getBrowserItems().get(i);
            BrowserSelectorCardHandle actualCard = browserSettingsSelectorHandle.getBrowserSelectorCardHandle(i);

            assertBrowserCardDisplay(expectedBrowser, actualCard);
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_LINKEDIN_EVENT);
        guiRobot.pauseForHuman();

        BrowserSelectorCardHandle expectedCard = browserSettingsSelectorHandle.getBrowserSelectorCardHandle(0);
        BrowserSelectorCardHandle selectedCard = browserSettingsSelectorHandle.getHandleToSelectedCard();
        assertBrowserCardEquals(expectedCard, selectedCard);
    }
}
