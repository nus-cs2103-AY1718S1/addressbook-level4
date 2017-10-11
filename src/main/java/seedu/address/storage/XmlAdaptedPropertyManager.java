package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.property.PropertyManager;

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
}
