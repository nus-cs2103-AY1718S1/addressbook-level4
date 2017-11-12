package seedu.address.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import seedu.address.commons.core.LogsCenter;

//@@author hansiang93

/**
 * The Browser used for Web Auth of the App.
 */
public class WebAuthBrowser extends UiPart<Region> {

    public static final String INSTAGRAM_AUTH_URL_PREFIX = "https://api.instagram.com/oauth/authorize/?client_id=";
    public static final String INSTAGRAM_AUTH_URL_SUFFIX = "&redirect_uri=http://google.com&response_type=token";
    public static final String INSTAGRAM_AUTH_CLIENT_ID = "612f1bb3c7b74acdb74e704e31c832f7";
    private static final Pattern AUTH_CLIENT_ID_REGEX = Pattern.compile("(?<=access_token=)(.*)");

    private static final String FXML = "BrowserPanel.fxml";
    private static final String TITLE = "Instagram Web Auth";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private String instagramAccessId;

    @FXML
    private WebView browser;

    private final Stage stage;

    public WebAuthBrowser() {
        super(FXML);
        registerAsAnEventHandler(this);
        Scene scene = new Scene(getRoot());
        stage = createDialogStage(TITLE, null, scene);
        setOnCloseHandler();

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        try {
            loadDefaultPage();
            browser.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
                @Override
                public void handle(WebEvent<String> event) {
                    System.out.println(event.getData());
                }
            });
        } catch (MalformedURLException ie) {
            logger.info("URL malformed Error: " + ie);
        }
        // registerAsAnEventHandler(this);
    }

    private void setOnCloseHandler() {
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        String message = "";
                        try {
                            instagramAccessId = generateClientId();
                        } catch (EmptyStackException ex) {
                            logger.info("Try logging in again");
                            return;
                        }
                        if (instagramAccessId.isEmpty()) {
                            message = "Login Failed, please try again";
                        } else {
                            message = "Login Success!";
                        }
                        logger.info("Closed Instagram Window, " + message + instagramAccessId);
                    }
                });
            }
        });
    }

    /**
     *
     */
    public void show() {
        logger.fine("Log into Instagram.");
        stage.showAndWait();
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads default Auth screen for App.
     */
    private void loadDefaultPage() throws MalformedURLException {
        URL defaultPage = new URL(INSTAGRAM_AUTH_URL_PREFIX + INSTAGRAM_AUTH_CLIENT_ID + INSTAGRAM_AUTH_URL_SUFFIX);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Gets the access token of instagram app
     */
    private String generateClientId() {
        String loadedPage = "";
        loadedPage = browser.getEngine().getLocation();
        if (loadedPage == null) {
            throw new EmptyStackException();
        }
        Matcher m = AUTH_CLIENT_ID_REGEX.matcher(loadedPage);
        if (m.find()) {
            return m.group(0);
        } else {
            return "";
        }
    }

    public String getAccessToken() {
        return instagramAccessId;
    }


    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }
}
