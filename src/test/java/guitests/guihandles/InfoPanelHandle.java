package guitests.guihandles;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A handler for the {@code InfoPanel} of the UI.
 */
public class InfoPanelHandle extends NodeHandle<Node> {
    public static final String INFO_PANEL_ID = "#infoPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String DISPLAY_POSTAL_CODE_FIELD_ID = "#displayPostalCode";
    private static final String DEBT_FIELD_ID = "#debt";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String ADDRESS_FIELD_FIELD_ID = "#addressField";
    private static final String PHONE_FIELD_FIELD_ID = "#phoneField";
    private static final String EMAIL_FIELD_FIELD_ID = "#emailField";
    private static final String DISPLAY_POSTAL_CODE_FIELD_FIELD_ID = "#displayPostalCodeField";
    private static final String DEBT_FIELD_FIELD_ID = "#debtField";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label displayPostalCodeLabel;
    private final Label debtLabel;
    private final List<Label> tagLabels;
    private final Text addressText;
    private final Text phoneText;
    private final Text emailText;
    private final Text displayPostalCodeText;
    private final Text debtText;

    public InfoPanelHandle(Node infoPanelNode) {
        super(infoPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.displayPostalCodeLabel = getChildNode(DISPLAY_POSTAL_CODE_FIELD_ID);
        this.debtLabel = getChildNode(DEBT_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        this.addressText = getChildNode(ADDRESS_FIELD_FIELD_ID);
        this.phoneText = getChildNode(PHONE_FIELD_FIELD_ID);
        this.emailText = getChildNode(EMAIL_FIELD_FIELD_ID);
        this.displayPostalCodeText = getChildNode(DISPLAY_POSTAL_CODE_FIELD_FIELD_ID);
        this.debtText = getChildNode(DEBT_FIELD_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getDebt() {
        return debtLabel.getText();
    }

    public String getDisplayPostalCode() {
        return  displayPostalCodeLabel.getText();
    }

    public String getAddressField() {
        return addressText.getText();
    }

    public String getPhoneField() {
        return phoneText.getText();
    }

    public String getEmailField() {
        return emailText.getText();
    }

    public String getDisplayPostalCodeField() {
        return displayPostalCodeText.getText();
    }

    public String getDebtField() {
        return debtText.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
