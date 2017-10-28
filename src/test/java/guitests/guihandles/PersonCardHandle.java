package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONES_FIELD_ID = "#phones";
    private static final String EMAILS_FIELD_ID = "#emails";
    private static final String MODS_FIELD_ID = "#mods";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final List<Label> phoneLabels;
    private final List<Label> emailLabels;
    private final List<Label> modLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);

        Region phonesContainer = getChildNode(PHONES_FIELD_ID);
        this.phoneLabels = phonesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region emailsContainer = getChildNode(EMAILS_FIELD_ID);
        this.emailLabels = emailsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region modsContainer = getChildNode(MODS_FIELD_ID);
        this.modLabels = modsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getPhones() {
        return phoneLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getEmails() {
        return emailLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTags() {
        return modLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
