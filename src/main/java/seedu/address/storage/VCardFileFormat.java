package seedu.address.storage;

/**
 * Format of VCard file
 */

public class VCardFileFormat {

    private static final String fullName = "FN";
    private static final String name = "N";
    private static final String addressFormat1 = "ADR";
    private static final String addressFormat2 = "item1.ADR";
    private static final String email = "EMAIL";
    private static final String begin = "BEGIN:VCARD";
    private static final String end = "END:VCARD";
    private static final String phoneFormat1 = "TEL";
    private static final String phoneFormat2 = "item1.TEL";
    private static final String birthday = "BDAY";
    private static final String label = "CATEGORIES";
    private static final String version = "Version 3.0";
    private static final String organization = "ORG";
    private static final String notes = "NOTE";

    public VCardFileFormat() {
    }

    public String getAddressFormat1() {
        return addressFormat1;
    }

    public String getAddressFormat2() {
        return addressFormat2;
    }

    public String getBegin() {
        return begin;
    }

    public String getEmail() {
        return email;
    }

    public String getLabel() {
        return label;
    }

    public String getEnd() {
        return end;
    }

    public String getFullName() {
        return fullName;
    }

    public String getName() {
        return name;
    }

    public String getPhoneFormat() {
        return phoneFormat1;
    }

    public String getPhoneFormat2() {
        return phoneFormat2;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getVersion() {
        return version;
    }

    public String getOrganization() {
        return organization;
    }

    public String getNotes() {
        return notes;
    }
}
