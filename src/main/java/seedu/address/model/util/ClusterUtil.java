package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

//@@author khooroko
/**
 * Contains utility methods for retrieving (@code Cluster) from (@code PostalCode).
 */
public class ClusterUtil {

    private static final String CLUSTER_POSTAL_DISTRICT_01 = "Raffles Place, Cecil, Marina, People's Park";
    private static final String CLUSTER_POSTAL_DISTRICT_02 = "Anson, Tanjong Pagar";
    private static final String CLUSTER_POSTAL_DISTRICT_03 = "Queenstown, Tiong Bahru";
    private static final String CLUSTER_POSTAL_DISTRICT_04 = "Telok Blangah, Harbourfront";
    private static final String CLUSTER_POSTAL_DISTRICT_05 = "Pasir Panjang, Hong Leong Garden, Clementi New Town";
    private static final String CLUSTER_POSTAL_DISTRICT_06 = "High Street, Beach Road";
    private static final String CLUSTER_POSTAL_DISTRICT_07 = "Middle Road, Golden Mile";
    private static final String CLUSTER_POSTAL_DISTRICT_08 = "Little India";
    private static final String CLUSTER_POSTAL_DISTRICT_09 = "Orchard, Cairnhill, River Valley";
    private static final String CLUSTER_POSTAL_DISTRICT_10 = "Ardmore, Bukit Timah, Holland Road, Tanglin";
    private static final String CLUSTER_POSTAL_DISTRICT_11 = "Watten Estate, Novena, Thomson";
    private static final String CLUSTER_POSTAL_DISTRICT_12 = "Balestier, Toa Payoh, Serangoon";
    private static final String CLUSTER_POSTAL_DISTRICT_13 = "Macpherson, Braddell";
    private static final String CLUSTER_POSTAL_DISTRICT_14 = "Geylang, Eunos";
    private static final String CLUSTER_POSTAL_DISTRICT_15 = "Katong, Joo Chiat, Amber Road";
    private static final String CLUSTER_POSTAL_DISTRICT_16 = "Bedok, Upper East Coast, Eastwood, Kew Drive";
    private static final String CLUSTER_POSTAL_DISTRICT_17 = "Loyang, Changi";
    private static final String CLUSTER_POSTAL_DISTRICT_18 = "Tampines, Pasir Ris";
    private static final String CLUSTER_POSTAL_DISTRICT_19 = "Serangoon Garden, Hougang, Ponggol";
    private static final String CLUSTER_POSTAL_DISTRICT_20 = "Bishan, Ang Mo Kio";
    private static final String CLUSTER_POSTAL_DISTRICT_21 = "Upper Bukit Timah, Clementi Park, Ulu Pandan";
    private static final String CLUSTER_POSTAL_DISTRICT_22 = "Jurong";
    private static final String CLUSTER_POSTAL_DISTRICT_23 = "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang";
    private static final String CLUSTER_POSTAL_DISTRICT_24 = "Lim Chu Kang, Tengah";
    private static final String CLUSTER_POSTAL_DISTRICT_25 = "Kranji, Woodgrove";
    private static final String CLUSTER_POSTAL_DISTRICT_26 = "Upper Thomson, Springleaf";
    private static final String CLUSTER_POSTAL_DISTRICT_27 = "Yishun, Sembawang";
    private static final String CLUSTER_POSTAL_DISTRICT_28 = "Seletar";
    private static final String CLUSTER_POSTAL_DISTRICT_UNKNOWN = "Unknown";

    private static HashMap<String, String> clusterMap = new HashMap<String, String>() {
        {
//            put("01", "Raffles Place, Cecil, Marina, People's Park");
//            put("02", "Raffles Place, Cecil, Marina, People's Park");
//            put("03", "Raffles Place, Cecil, Marina, People's Park");
//            put("04", "Raffles Place, Cecil, Marina, People's Park");
//            put("05", "Raffles Place, Cecil, Marina, People's Park");
//            put("06", "Raffles Place, Cecil, Marina, People's Park");
//            put("07", "Anson, Tanjong Pagar");
//            put("08", "Anson, Tanjong Pagar");
//            put("09", "Telok Blangah, Harbourfront");
//            put("10", "Telok Blangah, Harbourfront");
//            put("11", "Pasir Panjang, Hong Leong Garden, Clementi New Town");
//            put("12", "Pasir Panjang, Hong Leong Garden, Clementi New Town");
//            put("13", "Pasir Panjang, Hong Leong Garden, Clementi New Town");
//            put("14", "Queenstown, Tiong Bahru");
//            put("15", "Queenstown, Tiong Bahru");
//            put("16", "Queenstown, Tiong Bahru");
//            put("17", "High Street, Beach Road");
//            put("18", "Middle Road, Golden Mile");
//            put("19", "Middle Road, Golden Mile");
//            put("20", "Little India");
//            put("21", "Little India");
//            put("22", "Orchard, Cairnhill, River Valley");
//            put("23", "Orchard, Cairnhill, River Valley");
//            put("24", "Ardmore, Bukit Timah, Holland Road, Tanglin");
//            put("25", "Ardmore, Bukit Timah, Holland Road, Tanglin");
//            put("26", "Ardmore, Bukit Timah, Holland Road, Tanglin");
//            put("27", "Ardmore, Bukit Timah, Holland Road, Tanglin");
//            put("28", "Watten Estate, Novena, Thomson");
//            put("29", "Watten Estate, Novena, Thomson");
//            put("30", "Watten Estate, Novena, Thomson");
//            put("31", "Balestier, Toa Payoh, Serangoon");
//            put("32", "Balestier, Toa Payoh, Serangoon");
//            put("33", "Balestier, Toa Payoh, Serangoon");
//            put("34", "Macpherson, Braddell");
//            put("35", "Macpherson, Braddell");
//            put("36", "Macpherson, Braddell");
//            put("37", "Macpherson, Braddell");
//            put("38", "Geylang, Eunos");
//            put("39", "Geylang, Eunos");
//            put("40", "Geylang, Eunos");
//            put("41", "Geylang, Eunos");
//            put("42", "Katong, Joo Chiat, Amber Road");
//            put("43", "Katong, Joo Chiat, Amber Road");
//            put("44", "Katong, Joo Chiat, Amber Road");
//            put("45", "Katong, Joo Chiat, Amber Road");
//            put("46", "Bedok, Upper East Coast, Eastwood, Kew Drive");
//            put("47", "Bedok, Upper East Coast, Eastwood, Kew Drive");
//            put("48", "Bedok, Upper East Coast, Eastwood, Kew Drive");
//            put("49", "Loyang, Changi");
//            put("50", "Loyang, Changi");
//            put("51", "Tampines, Pasir Ris");
//            put("52", "Tampines, Pasir Ris");
//            put("53", "Serangoon Garden, Hougang, Ponggol");
//            put("54", "Serangoon Garden, Hougang, Ponggol");
//            put("55", "Serangoon Garden, Hougang, Ponggol");
//            put("56", "Bishan, Ang Mo Kio");
//            put("57", "Bishan, Ang Mo Kio");
//            put("58", "Upper Bukit Timah, Clementi Park, Ulu Pandan");
//            put("59", "Upper Bukit Timah, Clementi Park, Ulu Pandan");
//            put("60", "Jurong");
//            put("61", "Jurong");
//            put("62", "Jurong");
//            put("63", "Jurong");
//            put("64", "Jurong");
//            put("65", "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
//            put("66", "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
//            put("67", "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
//            put("68", "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
//            put("69", "Lim Chu Kang, Tengah");
//            put("70", "Lim Chu Kang, Tengah");
//            put("71", "Lim Chu Kang, Tengah");
//            put("72", "Kranji, Woodgrove");
//            put("73", "Kranji, Woodgrove");
//            put("75", "Yishun, Sembawang");
//            put("76", "Yishun, Sembawang");
//            put("77", "Upper Thomson, Springleaf");
//            put("78", "Upper Thomson, Springleaf");
//            put("79", "Seletar");
//            put("80", "Seletar");
//            put("81", "Loyang, Changi");
//            put("82", "Serangoon Garden, Hougang, Ponggol");
        }
    };

    public static String getCluster(String postalCode) {
        requireNonNull(postalCode);
        int postalSector = Integer.parseInt(postalCode.substring(0, 2));
        switch (postalSector) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            return CLUSTER_POSTAL_DISTRICT_01;
        case 7:
        case 8:
            return CLUSTER_POSTAL_DISTRICT_02;
        case 14:
        case 15:
        case 16:
            return CLUSTER_POSTAL_DISTRICT_03;
        case 9:
        case 10:
            return CLUSTER_POSTAL_DISTRICT_04;
        case 11:
        case 12:
        case 13:
            return CLUSTER_POSTAL_DISTRICT_05;
        case 17:
            return CLUSTER_POSTAL_DISTRICT_06;
        case 18:
        case 19:
            return CLUSTER_POSTAL_DISTRICT_07;
        case 20:
        case 21:
            return CLUSTER_POSTAL_DISTRICT_08;
        case 22:
        case 23:
            return CLUSTER_POSTAL_DISTRICT_09;
        case 24:
        case 25:
        case 26:
        case 27:
            return CLUSTER_POSTAL_DISTRICT_10;
        case 28:
        case 29:
        case 30:
            return CLUSTER_POSTAL_DISTRICT_11;
        case 31:
        case 32:
        case 33:
            return CLUSTER_POSTAL_DISTRICT_12;
        case 34:
        case 35:
        case 36:
        case 37:
            return CLUSTER_POSTAL_DISTRICT_13;
        case 38:
        case 39:
        case 40:
        case 41:
            return CLUSTER_POSTAL_DISTRICT_14;
        case 42:
        case 43:
        case 44:
        case 45:
            return CLUSTER_POSTAL_DISTRICT_15;
        case 46:
        case 47:
        case 48:
            return CLUSTER_POSTAL_DISTRICT_16;
        case 49:
        case 50:
        case 81:
            return CLUSTER_POSTAL_DISTRICT_17;
        case 51:
        case 52:
            return CLUSTER_POSTAL_DISTRICT_18;
        case 53:
        case 54:
        case 55:
        case 82:
            return CLUSTER_POSTAL_DISTRICT_19;
        case 56:
        case 57:
            return CLUSTER_POSTAL_DISTRICT_20;
        case 58:
        case 59:
            return CLUSTER_POSTAL_DISTRICT_21;
        case 60:
        case 61:
        case 62:
        case 63:
        case 64:
            return CLUSTER_POSTAL_DISTRICT_22;
        case 65:
        case 66:
        case 67:
        case 68:
            return CLUSTER_POSTAL_DISTRICT_23;
        case 69:
        case 70:
        case 71:
            return CLUSTER_POSTAL_DISTRICT_24;
        case 72:
        case 73:
            return CLUSTER_POSTAL_DISTRICT_25;
        case 77:
        case 78:
            return CLUSTER_POSTAL_DISTRICT_26;
        case 75:
        case 76:
            return CLUSTER_POSTAL_DISTRICT_27;
        case 79:
        case 80:
            return CLUSTER_POSTAL_DISTRICT_28;
        default:
            return CLUSTER_POSTAL_DISTRICT_UNKNOWN;
        }
    }

}
