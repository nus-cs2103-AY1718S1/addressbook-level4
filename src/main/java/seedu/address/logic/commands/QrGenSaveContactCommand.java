package seedu.address.logic.commands;

/**
 * Method for application to call out for different QR Functions
 */
//@@author danielweide
public class QrGenSaveContactCommand {
    /**
     * Method to Generate SaveContact for Phone QRCode
     */
    public String qrSaveContact(String phoneNum, String contactName, String contactEmail) {

        String qrCodeA = "http://";
        String qrCodeB = "api.qrserver.com/";
        String qrCodeC = "v1/";
        String qrCodeD = "create-qr-code/";
        String qrCodeE = "?color=000000";
        String qrCodeF = "&bgcolor=FFFFFF";
        String qrCodeG = "&data";
        String qrCodeH = "=BEGIN";
        String qrCodeI = "%3A";
        String qrCodeJ = "VCARD";
        String qrCodeK = "%0";
        String qrCodeL = "AVERSION";
        String qrCodeM = "%3A2.1";
        String qrCodeN = "%0AFN";
        String qrCodeO = "%3A";
        String qrLineA = qrCodeA + qrCodeB + qrCodeC + qrCodeD + qrCodeE + qrCodeF
                + qrCodeG + qrCodeH + qrCodeI + qrCodeJ + qrCodeK + qrCodeL
                + qrCodeM + qrCodeN + qrCodeO;

        String qrpartbCodeA = "%0";
        String qrpartbCodeB = "AN";
        String qrpartbCodeC = "%3A";
        String qrpartbCodeD = "%3B";
        String qrLineB = qrpartbCodeA + qrpartbCodeB + qrpartbCodeC + qrpartbCodeD;
        String qrpartcCodeA = "%0";
        String qrpartcCodeB = "ATEL";
        String qrpartcCodeC = "%3BWORK";
        String qrpartcCodeD = "%3BVOICE";
        String qrpartcCodeE = "%3A";
        String qrLineC = qrpartcCodeA + qrpartcCodeB + qrpartcCodeC + qrpartcCodeD + qrpartcCodeE;
        String qrpartdCodeA = "%0";
        String qrpartdCodeB = "AEMAIL";
        String qrpartdCodeC = "%3";
        String qrpartdCodeD = "BWORK";
        String qrpartdCodeE = "%3";
        String qrpartdCodeF = "BINTERNET";
        String qrpartdCodeG = "%3A";
        String qrLineD = qrpartdCodeA + qrpartdCodeB + qrpartdCodeC + qrpartdCodeD + qrpartdCodeE + qrpartdCodeF
                + qrpartdCodeG;
        String qrparteCodeA = "%0";
        String qrparteCodeB = "AORG";
        String qrparteCodeC = "%3A";
        String qrLineE = qrparteCodeA + qrparteCodeB + qrparteCodeC;
        String qrpartfCodeA = "%0";
        String qrpartfCodeB = "AEND";
        String qrpartfCodeC = "%3";
        String qrpartfCodeD = "AVCARD";
        String qrpartfCodeE = "%0A";
        String qrpartfCodeF = "&qzone";
        String qrpartfCodeG = "=1";
        String qrpartfCodeH = "&margin";
        String qrpartfCodeI = "=0";
        String qrpartfCodeJ = "&size";
        String qrpartfCodeK = "500x500";
        String qrpartfCodeL = "&ecc";
        String qrpartfCodeM = "=L";
        String qrLineF = qrpartfCodeA + qrpartfCodeB + qrpartfCodeC + qrpartfCodeD + qrpartfCodeE + qrpartfCodeF
                + qrpartfCodeG + qrpartfCodeH + qrpartfCodeI + qrpartfCodeJ + qrpartfCodeK + qrpartfCodeL
                + qrpartfCodeM;

        String fullQr = qrLineA + contactName + qrLineB + contactName + qrLineC + phoneNum + qrLineD + contactEmail
                + qrLineE + qrLineF;
        return fullQr;
    }
}
