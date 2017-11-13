package seedu.address.commons.util.digestutil;

/**
 * Provide the utilities to obtain hex strings from texts and texts from hex strings
 */
public class HexCode {

    /**
     * Return hex string of text
     */
    public String getHexFormat(String text) {
        char[] chars = text.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    /**
     * Return text of hex string
     */
    public String hexStringToByteArray(String hexString) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < hexString.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hexString.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }
        System.out.println("Decimal : " + temp.toString());

        return sb.toString();
    }
}
