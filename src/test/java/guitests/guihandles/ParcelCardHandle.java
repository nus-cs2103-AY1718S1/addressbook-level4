package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a parcel card in the parcel list panel.
 */
public class ParcelCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TRACKING_NUMBER_FIELD_ID = "#trackingNumber";
    private static final String DELIVERY_DATE_FIELD_ID = "#deliveryDate";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label trackingNumberLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label deliveryDateLabel;
    private final Label statusLabel;
    private final List<Label> tagLabels;

    public ParcelCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.trackingNumberLabel = getChildNode(TRACKING_NUMBER_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.statusLabel = getChildNode(STATUS_FIELD_ID);
        this.deliveryDateLabel = getChildNode(DELIVERY_DATE_FIELD_ID);

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

    public String getTrackingNumber() {
        return trackingNumberLabel.getText();
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

    public String getDeliveryDate() {
        return deliveryDateLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
