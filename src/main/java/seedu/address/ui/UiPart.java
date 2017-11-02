package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Represents a distinct part of the UI. e.g. Windows, dialogs, panels, status bars, etc.
 * It contains a scene graph with a root node of type {@code T}.
 */
public abstract class UiPart<T> {

    /** Resource folder where FXML files are stored. */
    public static final String FXML_FILE_FOLDER = "/view/";
    /**
     * Resource folder where Theme files are stored.
     */
    public static final String THEME_FILE_FOLDER = "themes";

    /**
     * Individual theme files url
     */
    public static final String THEME_CSS_DARKTHEME = "/darktheme/DarkTheme.css";
    public static final String THEME_CSS_BOOTSTRAP3 = "/bootstrap3/bootstrap3.css";
    public static final String THEME_CSS_CASPIAN = "/caspian/caspian.css";
    public static final String THEME_CSS_MODENA = "/modena/modena.css";
    public static final String THEME_CSS_MODENA_BLACKONWHITE = "/modena/blackOnWhite.css";
    public static final String THEME_CSS_MODENA_WHITEONBLACK = "/modena/whiteOnBlack.css";
    public static final String THEME_CSS_MODENA_YELLOWONBLACK = "/modena/yellowOnBlack.css";

    public static final List<String> THEME_LIST_DIR = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(THEME_CSS_DARKTHEME);
                add(THEME_CSS_BOOTSTRAP3);
                add(THEME_CSS_CASPIAN);
                add(THEME_CSS_MODENA);
                add(THEME_CSS_MODENA_BLACKONWHITE);
                add(THEME_CSS_MODENA_WHITEONBLACK);
                add(THEME_CSS_MODENA_YELLOWONBLACK);
            }});

    private FXMLLoader fxmlLoader;

    /**
     * Constructs a UiPart with the specified FXML file URL.
     * The FXML file must not specify the {@code fx:controller} attribute.
     */
    public UiPart(URL fxmlFileUrl) {
        requireNonNull(fxmlFileUrl);
        fxmlLoader = new FXMLLoader(fxmlFileUrl);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Constructs a UiPart using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     * @see #UiPart(URL)
     */
    public UiPart(String fxmlFileName) {
        this(fxmlFileName != null ? MainApp.class.getResource(FXML_FILE_FOLDER + fxmlFileName) : null);
    }

    /**
     * Returns theme name based on index
     *
     * @param index
     */
    public static String getThemeNameByIndex(int index) {
        String themeName = THEME_LIST_DIR.get(index);
        themeName = themeName.replaceAll(".css", "");
        themeName = themeName.substring(themeName.lastIndexOf("/") + 1);
        themeName = themeName.substring(0, 1).toUpperCase() + themeName.substring(1);

        if (THEME_LIST_DIR.get(index).contains("modena") && !THEME_LIST_DIR.get(index).contains("modena.css")) {
            themeName = "(Modena) " + themeName;
        }
        return themeName;
    }

    /**
     * Returns the root object of the scene graph of this UiPart.
     */
    public T getRoot() {
        return fxmlLoader.getRoot();
    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    protected void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Creates a modal dialog.
     * @param title Title of the dialog.
     * @param parentStage The owner stage of the dialog.
     * @param scene The scene that will contain the dialog.
     * @return the created dialog, not yet made visible.
     */
    protected Stage createDialogStage(String title, Stage parentStage, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setScene(scene);
        return dialogStage;
    }

}
