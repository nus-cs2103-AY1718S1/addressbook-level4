package guitests;

import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Test;

import seedu.address.commons.events.ui.OpenFaceBookWebViewEvent;
import seedu.address.commons.events.ui.OpenGithubWebViewEvent;
import seedu.address.commons.events.ui.OpenInstagramWebViewEvent;
import seedu.address.commons.events.ui.OpenNusModsWebViewEvent;
import seedu.address.commons.events.ui.OpenTwitterWebViewEvent;

//@@author dalessr
public class PersonDetailsPanelTest extends AddressBookGuiTest {

    @Test
    public void openTwitterTabView() {

        OpenTwitterWebViewEvent event = new OpenTwitterWebViewEvent();
        postNow(event);
        assertTrue(true);
    }

    @Test
    public void openFacebookTabView() {

        OpenFaceBookWebViewEvent event = new OpenFaceBookWebViewEvent();
        postNow(event);
        assertTrue(true);
    }

    @Test
    public void openGithubTabView() {

        OpenGithubWebViewEvent event = new OpenGithubWebViewEvent();
        postNow(event);
        assertTrue(true);
    }

    @Test
    public void openInstagramTabView() {

        OpenInstagramWebViewEvent event = new OpenInstagramWebViewEvent();
        postNow(event);
        assertTrue(true);
    }

    @Test
    public void openNusmodsTabView() {

        OpenNusModsWebViewEvent event = new OpenNusModsWebViewEvent();
        postNow(event);
        assertTrue(true);
    }
}
