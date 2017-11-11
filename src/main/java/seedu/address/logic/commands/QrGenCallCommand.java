package seedu.address.logic.commands;

/**
 * Generates PhoneCall QR Link in forms of String
 */
//@@author danielweide
public class QrGenCallCommand {
    /**
     * Generate PhoneCall QRCode
     */
    public String qrCall(String phoneNum) {
        String qrCodeA = "http://";
        String qrCodeB = "api.qrserver.com/";
        String qrCodeC = "v1/";
        String qrCodeD = "create-qr-code/";
        String qrCodeE = "?color=000000";
        String qrCodeF = "&bgcolor=FFFFFF";
        String qrCodeG = "&data";
        String qrCodeH = "=tel";
        String qrCodeI = "%3A";
        String qrCodeJ = "&qzone";
        String qrCodeK = "=1";
        String qrCodeL = "&margin";
        String qrCodeM = "=0";
        String qrCodeN = "&size";
        String qrCodeO = "=500x500";
        String qrCodeP = "&ecc";
        String qrCodeQ = "=L";
        String qrLineA = qrCodeA + qrCodeB + qrCodeC + qrCodeD + qrCodeE + qrCodeF
                + qrCodeG + qrCodeH + qrCodeI;
        String qrLineB = qrCodeJ + qrCodeK + qrCodeL + qrCodeM + qrCodeN + qrCodeO
                + qrCodeP + qrCodeQ;
        String fullQr = qrLineA + phoneNum + qrLineB;
        return fullQr;
    }
}
