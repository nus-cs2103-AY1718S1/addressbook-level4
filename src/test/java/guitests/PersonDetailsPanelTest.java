package guitests;

import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.PersonDetailsPanel.FACEBOOK_DEFAULT_URL;
import static seedu.address.ui.PersonDetailsPanel.GITHUB_DEFAULT_URL;
import static seedu.address.ui.PersonDetailsPanel.INSTAGRAM_DEFAULT_URL;
import static seedu.address.ui.PersonDetailsPanel.NUSMODS_DEFAULT_URL;
import static seedu.address.ui.PersonDetailsPanel.TWITTER_DEFAULT_URL;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

import seedu.address.commons.events.ui.OpenFaceBookWebViewEvent;
import seedu.address.commons.events.ui.OpenGithubWebViewEvent;
import seedu.address.commons.events.ui.OpenInstagramWebViewEvent;
import seedu.address.commons.events.ui.OpenNusModsWebViewEvent;
import seedu.address.commons.events.ui.OpenTwitterWebViewEvent;

//@@author dalessr
public class PersonDetailsPanelTest extends AddressBookGuiTest {

    @Test
    public void openTwitterTabView() throws MalformedURLException {

        OpenTwitterWebViewEvent event = new OpenTwitterWebViewEvent();
        postNow(event);
        URL expectedTwitterUrl = new URL(TWITTER_DEFAULT_URL);
        assertTrue(expectedTwitterUrl.toString().contains("twitter"));
    }

    @Test
    public void openFacebookTabView() throws MalformedURLException {

        OpenFaceBookWebViewEvent event = new OpenFaceBookWebViewEvent();
        postNow(event);
        URL expectedFacebookUrl = new URL(FACEBOOK_DEFAULT_URL);
        assertTrue(expectedFacebookUrl.toString().contains("facebook"));
    }

    @Test
    public void openGithubTabView() throws MalformedURLException {

        OpenGithubWebViewEvent event = new OpenGithubWebViewEvent();
        postNow(event);
        URL expectedGithubUrl = new URL(GITHUB_DEFAULT_URL);
        assertTrue(expectedGithubUrl.toString().contains("github"));
    }

    @Test
    public void openInstagramTabView() throws MalformedURLException {

        OpenInstagramWebViewEvent event = new OpenInstagramWebViewEvent();
        postNow(event);
        URL expectedInstagramUrl = new URL(INSTAGRAM_DEFAULT_URL);
        assertTrue(expectedInstagramUrl.toString().contains("instagram"));
    }

    @Test
    public void openNusmodsTabView() throws MalformedURLException {

        OpenNusModsWebViewEvent event = new OpenNusModsWebViewEvent();
        postNow(event);
        URL expectedNusmodsUrl = new URL(NUSMODS_DEFAULT_URL);
        assertTrue(expectedNusmodsUrl.toString().contains("nusmods"));
    }
}
