package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Corresponding alias list of each country code and its corresponding .countryName().
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Country {

    public static final String DEFAULT_COUNTRY_CODE = "";
    public static final String DEFAULT_COUNTRY = "Country Unavailable";
    public final String value;

    /**
     * Converts a country code to country name.
     */
    public Country(String countryCode) {
        requireNonNull(countryCode);
        this.value = countryGetter(countryCode); // value is a country name
    }

    public static String countryGetter(String code) {
        return getName(code);
    }

    public static String getName(String code) {
        switch (code) {
        case "65": return "Singapore";
        case "263": return "Zimbabwe";
        case "260": return "Zambia";
        case "967": return "Yemen";
        case "212": return "Western Sahara";
        case "681": return "Wallis and Futuna";
        case "1340": return "U.S. Virgin Islands";
        case "84": return "Vietnam";
        case "58": return "Venezuela";
        case "379": return "Vatican";
        case "678": return "Vanuatu";
        case "998": return "Uzkbekistan";
        case "1": return "United States";
        case "598": return "Uruguay";
        case "380": return "Ukraine";
        case "44": return "United Kingdom";
        case "256": return "Uganda";
        case "971": return "United Arab Emirates";
        case "688": return "Tuvalu";
        case "1649": return "Turks and Caicos Islands";
        case "993": return "Turkmenistan";
        case "90": return "Turkey";
        case "216": return "Tunisia";
        case "1868": return "Trinidad and Tobago";
        case "676": return "Tonga";
        case "690": return "Tokelau";
        case "228": return "Togo";
        case "66": return "Thailand";
        case "255": return "Tanzania";
        case "992": return "Tajikistan";
        case "886": return "Taiwan";
        case "963": return "Syria";
        case "41": return "Switzerland";
        case "46": return "Sweden";
        case "268": return "Swaziland";
        case "47": return "Svalbard and Jan Mayen";
        case "597": return "Suriname";
        case "249": return "Sudan";
        case "1784": return "Saint Vincent and the Grenadines";

        //case DEFAULT_COUNTRY_CODE: // fallthrough
        default: return DEFAULT_COUNTRY;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Country // instanceof handles nulls
                && this.value.equals(((Country) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
