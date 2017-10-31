package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An UI component that displays information of a {@code Person} upon selection.
 */
public class PersonInformationPanel extends UiPart<Region> {
    private static final String FXML = "PersonInformationPanel.fxml";

    private InstagramBrowserPanel instagramBrowserPanel;
    private GoogleMapBrowserPanel googleMapBrowserPanel;

    @FXML
    private AnchorPane personDetailsPlaceholder;

    @FXML
    private StackPane instagramBrowserPlaceholder;

    @FXML
    private StackPane googleMapBrowserPaceholder;

    public PersonInformationPanel() {
        super(FXML);
        PersonDetailsPanel personDetailsPanel = new PersonDetailsPanel();
        personDetailsPlaceholder.getChildren().add(personDetailsPanel.getRoot());

        instagramBrowserPanel = new InstagramBrowserPanel();
        instagramBrowserPlaceholder.getChildren().add(instagramBrowserPanel.getRoot());

        googleMapBrowserPanel = new GoogleMapBrowserPanel();
        googleMapBrowserPaceholder.getChildren().add(googleMapBrowserPanel.getRoot());
    }

    /**
     * Release the resources used in the web browsers
     */
    public void releaseResources() {
        instagramBrowserPanel.freeResources();
        googleMapBrowserPanel.freeResources();
    }
}

