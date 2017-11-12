package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@@author icehawker
/**
 * Corresponding alias list of each country code and its corresponding .countryName().
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Country {

    public static final String COMMAND_WORD = "codes";
    public static final String DEFAULT_COUNTRY_CODE = "";
    public static final String DEFAULT_COUNTRY = "Country Unavailable";
    private static List<String> codeList;
    private static Map <String, String> countryMap;
    public final String value;


    /**
     * Converts a country code to country name.
     */
    public Country(String countryCode) {
        requireNonNull(countryCode);
        this.countryMap = createMap();
        this.value = getName(countryCode); // value is a country name
    }

    /**
     * Creates map of country codes and names.
     */
    public static Map <String, String> createMap() {
        Map <String, String> countries = new HashMap<>();
        // NOTE: all country codes with prefix "1-" and "44-" are not yet allowed by regex.
        countries.put(DEFAULT_COUNTRY_CODE, DEFAULT_COUNTRY);
        countries.put("65", "Singapore");
        countries.put("93", "Afghanistan");
        countries.put("355", "Albania");
        countries.put("213", "Algeria");
        countries.put("1-684", "American Samoa");
        countries.put("376", "Andorra");
        countries.put("244", "Angola");
        countries.put("1-264", "Anguilla");
        countries.put("672", "Antarctica");
        countries.put("1-268", "Antigua and Barbuda");
        countries.put("54", "Argentina");
        countries.put("374", "Armenia");
        countries.put("297", "Aruba");
        countries.put("61", "Australia");
        countries.put("43", "Austria");
        countries.put("994", "Azerbaijan");
        countries.put("1-242", "Bahamas");
        countries.put("973", "Bahrain");
        countries.put("880", "Bangladesh");
        countries.put("1-246", "Barbados");
        countries.put("375", "Belarus");
        countries.put("32", "Belgium");
        countries.put("501", "Belize");
        countries.put("229", "Benin");
        countries.put("1-441", "Bermuda");
        countries.put("975", "Bhutan");
        countries.put("591", "Bolivia");
        countries.put("387", "Bosnia and Herzegovina");
        countries.put("267", "Botswana");
        countries.put("55", "Brazil");
        countries.put("246", "British Indian Ocean Territory");
        countries.put("1-284", "British Virgin Islands");
        countries.put("673", "Brunei");
        countries.put("359", "Bulgaria");
        countries.put("226", "Burkina Faso");
        countries.put("257", "Burundi");
        countries.put("855", "Cambodia");
        countries.put("237", "Cameroon");
        countries.put("1", "Canada");
        countries.put("238", "Cape Verde");
        countries.put("1-345", "Cayman Islands");
        countries.put("236", "Central African Republic");
        countries.put("235", "Chad");
        countries.put("56", "Chile");
        countries.put("86", "China");
        countries.put("61", "Christmas Island");
        countries.put("61", "Cocos Islands");
        countries.put("57", "Colombia");
        countries.put("269", "Comoros");
        countries.put("682", "Cook Islands");
        countries.put("506", "Costa Rica");
        countries.put("385", "Croatia");
        countries.put("53", "Cuba");
        countries.put("599", "Curacao");
        countries.put("357", "Cyprus");
        countries.put("420", "Czech Republic");
        countries.put("243", "Democratic Republic of the Congo");
        countries.put("45", "Denmark");
        countries.put("253", "Djibouti");
        countries.put("1-767", "Dominica");
        countries.put("1-809", "Dominican Republic");
        countries.put("1-829", "Dominican Republic");
        countries.put("1-849", "Dominican Republic");
        countries.put("670", "East Timor");
        countries.put("593", "Ecuador");
        countries.put("20", "Egypt");
        countries.put("503", "El Salvador");
        countries.put("240", "Equatorial Guinea");
        countries.put("291", "Eritrea");
        countries.put("372", "Estonia");
        countries.put("251", "Ethiopia");
        countries.put("500", "Falkland Islands");
        countries.put("298", "Faroe Islands");
        countries.put("679", "Fiji");
        countries.put("358", "Finland");
        countries.put("33", "France");
        countries.put("689", "French Polynesia");
        countries.put("241", "Gabon");
        countries.put("220", "Gambia");
        countries.put("995", "Georgia");
        countries.put("49", "Germany");
        countries.put("233", "Ghana");
        countries.put("350", "Gibraltar");
        countries.put("30", "Greece");
        countries.put("299", "Greenland");
        countries.put("1-473", "Grenada");
        countries.put("1-671", "Guam");
        countries.put("502", "Guatemala");
        countries.put("44-1481", "Guernsey");
        countries.put("224", "Guinea");
        countries.put("245", "Guinea-Bissau");
        countries.put("592", "Guyana");
        countries.put("509", "Haiti");
        countries.put("504", "Honduras");
        countries.put("852", "Hong Kong");
        countries.put("36", "Hungary");
        countries.put("354", "Iceland");
        countries.put("91", "India");
        countries.put("62", "Indonesia");
        countries.put("98", "Iran");
        countries.put("964", "Iraq");
        countries.put("353", "Ireland");
        countries.put("44-1624", "Isle of Man");
        countries.put("972", "Israel");
        countries.put("39", "Italy");
        countries.put("225", "Ivory Coast");
        countries.put("1-876", "Jamaica");
        countries.put("81", "Japan");
        countries.put("44-1534", "Jersey");
        countries.put("962", "Jordan");
        countries.put("7", "Kazakhstan");
        countries.put("254", "Kenya");
        countries.put("686", "Kiribati");
        countries.put("383", "Kosovo");
        countries.put("965", "Kuwait");
        countries.put("996", "Kyrgyzstan");
        countries.put("856", "Laos");
        countries.put("371", "Latvia");
        countries.put("961", "Lebanon");
        countries.put("266", "Lesotho");
        countries.put("231", "Liberia");
        countries.put("218", "Libya");
        countries.put("423", "Liechtenstein");
        countries.put("370", "Lithuania");
        countries.put("352", "Luxembourg");
        countries.put("853", "Macau");
        countries.put("389", "Macedonia");
        countries.put("261", "Madagascar");
        countries.put("265", "Malawi");
        countries.put("60", "Malaysia");
        countries.put("960", "Maldives");
        countries.put("223", "Mali");
        countries.put("356", "Malta");
        countries.put("692", "Marshall Islands");
        countries.put("222", "Mauritania");
        countries.put("230", "Mauritius");
        countries.put("262", "Mayotte");
        countries.put("52", "Mexico");
        countries.put("691", "Micronesia");
        countries.put("373", "Moldova");
        countries.put("377", "Monaco");
        countries.put("976", "Mongolia");
        countries.put("382", "Montenegro");
        countries.put("1-664", "Montserrat");
        countries.put("212", "Morocco");
        countries.put("258", "Mozambique");
        countries.put("95", "Myanmar");
        countries.put("264", "Namibia");
        countries.put("674", "Nauru");
        countries.put("977", "Nepal");
        countries.put("31", "Netherlands");
        countries.put("599", "Netherlands Antilles");
        countries.put("687", "New Caledonia");
        countries.put("64", "New Zealand");
        countries.put("505", "Nicaragua");
        countries.put("227", "Niger");
        countries.put("234", "Nigeria");
        countries.put("683", "Niue");
        countries.put("850", "North Korea");
        countries.put("1-670", "Northern Mariana Islands");
        countries.put("47", "Norway");
        countries.put("968", "Oman");
        countries.put("92", "Pakistan");
        countries.put("680", "Palau");
        countries.put("970", "Palestine");
        countries.put("507", "Panama");
        countries.put("675", "Papua New Guinea");
        countries.put("595", "Paraguay");
        countries.put("51", "Peru");
        countries.put("63", "Philippines");
        countries.put("64", "Pitcairn");
        countries.put("48", "Poland");
        countries.put("351", "Portugal");
        countries.put("1-787, 1-939", "Puerto Rico");
        countries.put("974", "Qatar");
        countries.put("242", "Republic of the Congo");
        countries.put("262", "Reunion");
        countries.put("40", "Romania");
        countries.put("7", "Russia");
        countries.put("250", "Rwanda");
        countries.put("590", "Saint Barthelemy");
        countries.put("290", "Saint Helena");
        countries.put("1-869", "Saint Kitts and Nevis");
        countries.put("1-758", "Saint Lucia");
        countries.put("590", "Saint Martin");
        countries.put("508", "Saint Pierre and Miquelon");
        countries.put("1-784", "Saint Vincent and the Grenadines");
        countries.put("685", "Samoa");
        countries.put("378", "San Marino");
        countries.put("239", "Sao Tome and Principe");
        countries.put("966", "Saudi Arabia");
        countries.put("221", "Senegal");
        countries.put("381", "Serbia");
        countries.put("248", "Seychelles");
        countries.put("232", "Sierra Leone");
        countries.put("65", "Singapore");
        countries.put("1-721", "Sint Maarten");
        countries.put("421", "Slovakia");
        countries.put("386", "Slovenia");
        countries.put("677", "Solomon Islands");
        countries.put("252", "Somalia");
        countries.put("27", "South Africa");
        countries.put("82", "South Korea");
        countries.put("211", "South Sudan");
        countries.put("34", "Spain");
        countries.put("94", "Sri Lanka");
        countries.put("249", "Sudan");
        countries.put("597", "Suriname");
        countries.put("47", "Svalbard and Jan Mayen");
        countries.put("268", "Swaziland");
        countries.put("46", "Sweden");
        countries.put("41", "Switzerland");
        countries.put("963", "Syria");
        countries.put("886", "Taiwan");
        countries.put("992", "Tajikistan");
        countries.put("255", "Tanzania");
        countries.put("66", "Thailand");
        countries.put("228", "Togo");
        countries.put("690", "Tokelau");
        countries.put("676", "Tonga");
        countries.put("1-868", "Trinidad and Tobago");
        countries.put("216", "Tunisia");
        countries.put("90", "Turkey");
        countries.put("993", "Turkmenistan");
        countries.put("1-649", "Turks and Caicos Islands");
        countries.put("688", "Tuvalu");
        countries.put("1-340", "U.S. Virgin Islands");
        countries.put("256", "Uganda");
        countries.put("380", "Ukraine");
        countries.put("971", "United Arab Emirates");
        countries.put("44", "United Kingdom");
        countries.put("1", "United States");
        countries.put("598", "Uruguay");
        countries.put("998", "Uzbekistan");
        countries.put("678", "Vanuatu");
        countries.put("379", "Vatican");
        countries.put("58", "Venezuela");
        countries.put("84", "Vietnam");
        countries.put("681", "Wallis and Futuna");
        countries.put("212", "Western Sahara");
        countries.put("967", "Yemen");
        countries.put("260", "Zambia");
        countries.put("263", "Zimbabwe");
        codeList = new ArrayList<String>(countries.keySet());
        Collections.sort(codeList);
        return countries;
    }

    public static String getCodeList() {
        int count = 0;
        String output = "Valid codes:";
        for (String code:codeList) {
            if (count < 29) {
                output += code + ", ";
            } else {
                output += code + ", \n";
            }
            count += 1; // record iterations until next newline
            count = count % 30; // repeat to 30
        }
        return output;
    }

    /**
     * Generic code-to-name lookup (Does not require Country object instantiation).
     */
    public static String getName(String code) {
        return countryMap.getOrDefault(code, DEFAULT_COUNTRY);
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
