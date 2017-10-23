package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.DuplicatePropertyException;

/**
 * JAXB-friendly adapted version of the {@link PropertyManager}.
 */
public class XmlAdaptedPropertyManager {
    @XmlElement
    private List<XmlAdaptedPropertyInfo> property;

    public XmlAdaptedPropertyManager() {
        property = new ArrayList<>();
        for (String shortName: PropertyManager.getAllShortNames()) {
            XmlAdaptedPropertyInfo info = new XmlAdaptedPropertyInfo(shortName,
                    PropertyManager.getPropertyFullName(shortName),
                    PropertyManager.getPropertyConstraintMessage(shortName),
                    PropertyManager.getPropertyValidationRegex(shortName));
            property.add(info);
        }
    }

    /**
     * Initialize all properties by adding them to {@link PropertyManager}.
     */
    public void initializeProperties() {
        try {
            for (XmlAdaptedPropertyInfo info: property) {
                info.toModelType();
            }
        } catch (DuplicatePropertyException dpe) {
            // TODO: better error handling
            dpe.printStackTrace();
        }
    }
}
