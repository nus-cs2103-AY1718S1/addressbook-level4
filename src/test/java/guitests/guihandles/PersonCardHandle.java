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
    private static final String PHONE_FIELD_ID = "#phone";
    //@@author sebtsh
    private static final String COMPANY_FIELD_ID = "#company";
    private static final String POSITION_FIELD_ID = "#position";
    private static final String PRIORITY_FIELD_ID = "#priority";
    //@@author
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    //@@author sebtsh
    private final Label companyLabel;
    private final Label positionLabel;
    private final Label priorityLabel;
    //@@author
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        //@@author sebtsh
        this.companyLabel = getChildNode(COMPANY_FIELD_ID);
        this.positionLabel = getChildNode(POSITION_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);
        //@@author

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
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

    public String getPhone() {
        return phoneLabel.getText();
    }

    //@@author sebtsh
    public String getCompany() {
        return companyLabel.getText();
    }

    public String getPosition() {
        return positionLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }
    //@@author

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
