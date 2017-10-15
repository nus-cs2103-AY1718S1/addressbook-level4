package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

//@@author khooroko
/**
 * Contains utility methods for retrieving (@code Cluster) from (@code PostalCode).
 */
public class ClusterUtil {

    public static final String CLUSTER_POSTAL_DISTRICT_01 = "Raffles Place, Cecil, Marina, People's Park";
    public static final String CLUSTER_POSTAL_DISTRICT_02 = "Anson, Tanjong Pagar";
    public static final String CLUSTER_POSTAL_DISTRICT_03 = "Queenstown, Tiong Bahru";
    public static final String CLUSTER_POSTAL_DISTRICT_04 = "Telok Blangah, Harbourfront";
    public static final String CLUSTER_POSTAL_DISTRICT_05 = "Pasir Panjang, Hong Leong Garden, Clementi New Town";
    public static final String CLUSTER_POSTAL_DISTRICT_06 = "High Street, Beach Road";
    public static final String CLUSTER_POSTAL_DISTRICT_07 = "Middle Road, Golden Mile";
    public static final String CLUSTER_POSTAL_DISTRICT_08 = "Little India";
    public static final String CLUSTER_POSTAL_DISTRICT_09 = "Orchard, Cairnhill, River Valley";
    public static final String CLUSTER_POSTAL_DISTRICT_10 = "Ardmore, Bukit Timah, Holland Road, Tanglin";
    public static final String CLUSTER_POSTAL_DISTRICT_11 = "Watten Estate, Novena, Thomson";
    public static final String CLUSTER_POSTAL_DISTRICT_12 = "Balestier, Toa Payoh, Serangoon";
    public static final String CLUSTER_POSTAL_DISTRICT_13 = "Macpherson, Braddell";
    public static final String CLUSTER_POSTAL_DISTRICT_14 = "Geylang, Eunos";
    public static final String CLUSTER_POSTAL_DISTRICT_15 = "Katong, Joo Chiat, Amber Road";
    public static final String CLUSTER_POSTAL_DISTRICT_16 = "Bedok, Upper East Coast, Eastwood, Kew Drive";
    public static final String CLUSTER_POSTAL_DISTRICT_17 = "Loyang, Changi";
    public static final String CLUSTER_POSTAL_DISTRICT_18 = "Tampines, Pasir Ris";
    public static final String CLUSTER_POSTAL_DISTRICT_19 = "Serangoon Garden, Hougang, Ponggol";
    public static final String CLUSTER_POSTAL_DISTRICT_20 = "Bishan, Ang Mo Kio";
    public static final String CLUSTER_POSTAL_DISTRICT_21 = "Upper Bukit Timah, Clementi Park, Ulu Pandan";
    public static final String CLUSTER_POSTAL_DISTRICT_22 = "Jurong";
    public static final String CLUSTER_POSTAL_DISTRICT_23 = "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang";
    public static final String CLUSTER_POSTAL_DISTRICT_24 = "Lim Chu Kang, Tengah";
    public static final String CLUSTER_POSTAL_DISTRICT_25 = "Kranji, Woodgrove";
    public static final String CLUSTER_POSTAL_DISTRICT_26 = "Upper Thomson, Springleaf";
    public static final String CLUSTER_POSTAL_DISTRICT_27 = "Yishun, Sembawang";
    public static final String CLUSTER_POSTAL_DISTRICT_28 = "Seletar";
    public static final String CLUSTER_POSTAL_DISTRICT_UNKNOWN = "Unknown";

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
