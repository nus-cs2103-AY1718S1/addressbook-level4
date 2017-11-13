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
    private static final String DEBT_FIELD_ID = "#debt";
    private static final String TOTAL_DEBT_FIELD_ID = "#totalDebt";
    private static final String DEADLINE_FIELD_ID = "#deadline";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label debtLabel;
    private final Label totalDebtLabel;
    private final Label deadlineLabel;
    private final List<Label> tagLabels;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.debtLabel = getChildNode(DEBT_FIELD_ID);
        this.totalDebtLabel = getChildNode(TOTAL_DEBT_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);

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

    public String getDebt() {
        return debtLabel.getText();
    }

    public String getTotalDebt() {
        return totalDebtLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
