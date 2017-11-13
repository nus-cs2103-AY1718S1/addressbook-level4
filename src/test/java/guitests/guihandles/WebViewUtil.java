package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import guitests.GuiRobot;
import javafx.scene.web.WebView;

/**
 * Helper methods for dealing with {@code WebView}.
 */
public class WebViewUtil {

    /**
     * Returns the {@code URL} of the currently loaded page in the {@code webView}.
     */
    public static URL getLoadedUrl(WebView webView) {
        try {
            return new URL(webView.getEngine().getLocation());
        } catch (MalformedURLException mue) {
            throw new AssertionError("webView should not be displaying an invalid URL.", mue);
        }
    }

    /**
     * If the {@code instagramBrowserPanelHandle}'s {@code WebView} is loading,
     * sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilBrowserLoaded(InstagramBrowserPanelHandle instagramBrowserPanelHandle) {
        new GuiRobot().waitForEvent(instagramBrowserPanelHandle::isLoaded);
    }

    /**
     * If the {@code googleMaprowserPanelHandle}'s {@code WebView} is loading,
     * sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilBrowserLoaded(GoogleMapBrowserPanelHandle googleMapBrowserPanelHandle) {
        new GuiRobot().waitForEvent(googleMapBrowserPanelHandle::isLoaded);
    }
}
