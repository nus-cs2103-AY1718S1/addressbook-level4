package seedu.address.model.util;

import java.util.HashMap;

/**
 * Contains utility methods for retrieving (@code Cluster) from (@code PostalCode).
 */
public class ClusterUtil {
    private static HashMap<String, String> clusterMap = new HashMap<String, String>() {
        {
            put("01", "Raffles Place, Cecil, Marina, People's Park");
            put("02", "Raffles Place, Cecil, Marina, People's Park");
            put("03", "Raffles Place, Cecil, Marina, People's Park");
            put("04", "Raffles Place, Cecil, Marina, People's Park");
            put("05", "Raffles Place, Cecil, Marina, People's Park");
            put("06", "Raffles Place, Cecil, Marina, People's Park");
            put("07", "Anson, Tanjong Pagar");
            put("08", "Anson, Tanjong Pagar");
            put("09", "Telok Blangah, Harbourfront");
            put("10", "Telok Blangah, Harbourfront");
            put("11", "Pasir Panjang, Hong Leong Garden, Clementi New Town");
            put("12", "Pasir Panjang, Hong Leong Garden, Clementi New Town");
            put("13", "Pasir Panjang, Hong Leong Garden, Clementi New Town");
            put("14", "Queenstown, Tiong Bahru");
            put("15", "Queenstown, Tiong Bahru");
            put("16", "Queenstown, Tiong Bahru");
            put("17", "High Street, Beach Road");
            put("18", "Middle Road, Golden Mile");
            put("19", "Middle Road, Golden Mile");
            put("20", "Little India");
            put("21", "Little India");
            put("22", "Orchard, Cairnhill, River Valley");
            put("23", "Orchard, Cairnhill, River Valley");
            put("24", "Ardmore, Bukit Timah, Holland Road, Tanglin");
            put("25", "Ardmore, Bukit Timah, Holland Road, Tanglin");
            put("26", "Ardmore, Bukit Timah, Holland Road, Tanglin");
            put("27", "Ardmore, Bukit Timah, Holland Road, Tanglin");
            put("28", "Watten Estate, Novena, Thomson");
            put("29", "Watten Estate, Novena, Thomson");
            put("30", "Watten Estate, Novena, Thomson");
            put("31", "Balestier, Toa Payoh, Serangoon");
            put("32", "Balestier, Toa Payoh, Serangoon");
            put("33", "Balestier, Toa Payoh, Serangoon");
            put("34", "Macpherson, Braddell");
            put("35", "Macpherson, Braddell");
            put("36", "Macpherson, Braddell");
            put("37", "Macpherson, Braddell");
            put("38", "Geylang, Eunos");
            put("39", "Geylang, Eunos");
            put("40", "Geylang, Eunos");
            put("41", "Geylang, Eunos");
            put("42", "Katong, Joo Chiat, Amber Road");
            put("43", "Katong, Joo Chiat, Amber Road");
            put("44", "Katong, Joo Chiat, Amber Road");
            put("45", "Katong, Joo Chiat, Amber Road");
            put("46", "Bedok, Upper East Coast, Eastwood, Kew Drive");
            put("47", "Bedok, Upper East Coast, Eastwood, Kew Drive");
            put("48", "Bedok, Upper East Coast, Eastwood, Kew Drive");
            put("49", "Loyang, Changi");
            put("50", "Loyang, Changi");
            put("51", "Tampines, Pasir Ris");
            put("52", "Tampines, Pasir Ris");
            put("53", "Serangoon Garden, Hougang, Ponggol");
            put("54", "Serangoon Garden, Hougang, Ponggol");
            put("55", "Serangoon Garden, Hougang, Ponggol");
            put("56", "Bishan, Ang Mo Kio");
            put("57", "Bishan, Ang Mo Kio");
            put("58", "Upper Bukit Timah, Clementi Park, Ulu Pandan");
            put("59", "Upper Bukit Timah, Clementi Park, Ulu Pandan");
            put("60", "Jurong");
            put("61", "Jurong");
            put("62", "Jurong");
            put("63", "Jurong");
            put("64", "Jurong");
            put("65", "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
            put("66", "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
            put("67", "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
            put("68", "Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang");
            put("69", "Lim Chu Kang, Tengah");
            put("70", "Lim Chu Kang, Tengah");
            put("71", "Lim Chu Kang, Tengah");
            put("72", "Kranji, Woodgrove");
            put("73", "Kranji, Woodgrove");
            put("75", "Yishun, Sembawang");
            put("76", "Yishun, Sembawang");
            put("77", "Upper Thomson, Springleaf");
            put("78", "Upper Thomson, Springleaf");
            put("79", "Seletar");
            put("80", "Seletar");
            put("81", "Loyang, Changi");
            put("82", "Serangoon Garden, Hougang, Ponggol");
        }
    };

    public static String getCluster(String key) {
        return clusterMap.get(key);
    }

}
