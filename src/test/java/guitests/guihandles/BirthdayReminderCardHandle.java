package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author justinpoh
/**
 * Provides a handle to a birthday reminder card in the birthday reminder list panel.
 */
public class BirthdayReminderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String NICKNAME_FIELD_ID = "#nickname";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label nicknameLabel;
    private final Label birthdayLabel;

    public BirthdayReminderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.nicknameLabel = getChildNode(NICKNAME_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getNickname() {
        return nicknameLabel.getText();
    }
    public String getBirthday() {
        return birthdayLabel.getText();
    }

}
