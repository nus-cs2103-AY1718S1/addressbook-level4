package seedu.address.storage;

import java.util.StringTokenizer;
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.customField.CustomField;

//@@author LuLechuan
/**
 * JAXB-friendly adapted version of the Custom Field.
 */
public class XmlAdaptedCustomField {

    @XmlValue
    private String customField;

    /**
     * Constructs an XmlAdaptedCustomField.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCustomField() {}

    /**
     * Converts a given Custom Field into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCustomField(CustomField source) {
        customField = source.customFieldName + " " + source.getCustomFieldValue();
    }

    /**
     * Converts this jaxb-friendly adapted custom field object into the model's Custom Field object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public CustomField toModelType() throws IllegalValueException {
        StringTokenizer st = new StringTokenizer(customField);
        String customFieldName = st.nextToken();
        String customFieldValue;
        if (st.hasMoreTokens()) {
            customFieldValue = st.nextToken();
        } else {
            customFieldValue = "";
        }
        return new CustomField(customFieldName, customFieldValue);
    }

}
