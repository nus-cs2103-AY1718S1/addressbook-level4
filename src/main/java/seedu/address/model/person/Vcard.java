//@@author conantteo
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Vcard of a Person in the address book.
 * Contains user information in a Vcard format stored in a String {@code cardDetails}
 * This Vcard is only created when user wants to export his/her contacts' information.
 */
public class Vcard {

    private String cardDetails;

    /**
     * Constructs empty Vcard.
     */
    public Vcard() {}

    /**
     * Creates a Vcard using a given person.
     * @param person enforces no nulls person.
     * Store information of a given person in a string {@code cardDetails}
     * Note that Vcard version used is 3.0:
     * BEING, VERSION, FN, END fields in cardDetails are required.
     * The rest of the fields are not required and can be empty Strings.
     */
    public Vcard(ReadOnlyPerson person) {
        requireNonNull(person);
        String name = person.getName().toString();
        String phone = person.getPhone().toString();
        String address = person.getAddress().toString();
        String email = person.getEmail().toString();
        String birthday = person.getBirthday().toString();
        birthday = buildBirthdayString(birthday);
        cardDetails = "BEGIN:VCARD\n"
                + "VERSION:3.0\n"
                + "FN:" + name + "\n"
                + "TEL;TYPE=MOBILE:" + phone + "\n"
                + "EMAIL;TYPE=WORK:" + email + "\n"
                + "BDAY:" + birthday + "\n"
                + "ADR;TYPE=HOME:;;" + address + "\n"
                + "END:VCARD" + "\n";
    }

    public String getCardDetails() {
        return cardDetails;
    }

    /**
     * This method builds a valid birthday format for Vcard.
     * {@code bday} is a StringBuilder that appends the Year, followed by Month and Day
     * of a particular birthday separated by a dash.
     * @param birthday is a valid birthday string of a person in the format: DD/MM/YYYY.
     * @return a new birthday string format: YYYY-MM-DD
     */
    private String buildBirthdayString(String birthday) {
        if (!birthday.equals("")) {
            String[] birthdayField = birthday.split("/");
            StringBuilder bday = new StringBuilder();
            bday.append(birthdayField[2]);
            bday.append("-");
            bday.append(birthdayField[1]);
            bday.append("-");
            bday.append(birthdayField[0]);
            return bday.toString();
        } else {
            // If there is no birthday information, return empty string
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Vcard // instanceof handles nulls
                && this.cardDetails.equals(((Vcard) other).cardDetails));
    }

    @Override
    public int hashCode() {
        return cardDetails.hashCode();
    }

}
