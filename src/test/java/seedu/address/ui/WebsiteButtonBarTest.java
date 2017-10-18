package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonListPanelHandle;
import javafx.scene.control.Button;


public class WebsiteButtonBarTest extends GuiUnitTest {

    private Button mapsButton;
    private Button searchButton;

    @Before
    public void setUp() {
        WebsiteButtonBar websiteButtonBar = new WebsiteButtonBar();
        uiPartRule.setUiPart(websiteButtonBar);
    }

    // To implement dynamically added Button Tests
}
